/*
 * The MIT License
 *
 * Copyright 2013 Andreas Giemza.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.andreasgiemza.jgeagle.repo;

import de.andreasgiemza.jgeagle.repo.data.GetRepoFiles;
import de.andreasgiemza.jgeagle.helper.DiffImage;
import de.andreasgiemza.jgeagle.helper.Eagle;
import de.andreasgiemza.jgeagle.options.Options;
import de.andreasgiemza.jgeagle.repo.data.EagleFile;
import de.andreasgiemza.jgeagle.repo.rcs.JGit;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Andreas Giemza
 */
public class Repo {

    private final JGit jGit;
    private final String repoName;
    private final List<EagleFile> eagleFiles = new LinkedList<>();

    public Repo(Options options, Path directory)
            throws IOException, GitAPIException {
        Path repoDirectory = directory.resolve(".git");

        if (Files.exists(repoDirectory)) {
            jGit = new JGit(repoDirectory);
            repoName = directory.getName(directory.getNameCount() - 1).toString();
            Files.walkFileTree(directory, new GetRepoFiles(directory, eagleFiles));
        } else {
            throw new IOException();
        }
    }

    public String getRepoName() {
        return repoName;
    }

    public List<EagleFile> getEagleFiles() {
        return eagleFiles;
    }

    public void getEagleFileLogAndStatus(Options options, EagleFile eagleFile) {
        if (eagleFile.isWorkingCopychanges() == null
                || options.getPropFollowGitAsBoolean() != eagleFile.isFollow()) {
            eagleFile.setFollow(options.getPropFollowGitAsBoolean());
            eagleFile.clearData();

            try {
                eagleFile.setWorkingCopychanges(
                        jGit.checkForWorkingCopyChanges(eagleFile.getRepoFile()));

                if (eagleFile.isWorkingCopychanges()) {
                    eagleFile.setWorkingCopyLastModified(
                            new Date(Files.getLastModifiedTime(
                                            eagleFile.getFile()).toMillis()));
                }

                jGit.getFileHistory(
                        options.getPropFollowGitAsBoolean(),
                        eagleFile.getRepoFile(),
                        eagleFile.getCommits(),
                        eagleFile.getRenames());
            } catch (IOException | GitAPIException ex) {
                Logger.getLogger(Repo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Path buildPath(
            Options options,
            RevCommit revCommit,
            EagleFile eagleFile,
            String fileExtension) {
        Path path;

        if (revCommit != null) {
            path = options.getReposDir()
                    .resolve(repoName)
                    .resolve("images")
                    .resolve(revCommit.getName());

            if (eagleFile.getRepoFile().getParent() != null) {
                path = path.resolve(eagleFile.getRepoFile().getParent());
            }
        } else {
            path = options.getTempDir();
        }

        return path.resolve(eagleFile.getFileName() + fileExtension);
    }

    public int getSheetCount(Options options, RevCommit revCommit, EagleFile eagleFile) {
        Path countFile = buildPath(options, revCommit, eagleFile, "-SHEETCOUNT.txt");

        if (Files.exists(countFile)) {
            try {
                return Integer.parseInt(
                        Files.readAllLines(
                                countFile,
                                Charset.defaultCharset()).get(0));
            } catch (IOException ex) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public void createSheetCountFile(
            Options options,
            EagleFile eagleFile,
            RevCommit revCommit,
            String fileName) {
        Path countFile = buildPath(options, revCommit, eagleFile, "-SHEETCOUNT.txt");

        try {
            if (!Files.exists(countFile)) {
                Path schematicFile;

                if (revCommit != null) {
                    schematicFile = options.getTempDir().resolve(fileName);
                } else {
                    schematicFile = eagleFile.getFile();
                }

                if (!Files.exists(schematicFile)) {
                    jGit.extractFile(
                            schematicFile,
                            revCommit,
                            eagleFile.getRepoFile(),
                            eagleFile.getRenames());
                }

                Eagle.countSheets(
                        options.getPropEagleBinaryAsPath(),
                        options.getCountSheetsUlp(),
                        countFile,
                        schematicFile);
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Repo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Path getOrCreateBoardDiffImage(
            Options options,
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit,
            String layer) {
        Path oldImageFile = getOrCreateBoardImage(options, oldCommit, eagleFile, "old.brd", layer);
        Path newImageFile = getOrCreateBoardImage(options, newCommit, eagleFile, "new.brd", layer);

        Path diffImageFile = buildDiffPath(
                options,
                eagleFile,
                oldCommit,
                newCommit,
                "-LAYER_" + layer + "-DPI_" + options.getPropBoardDpiAsInt()
                + "-ALPHA_" + options.getPropUnchangedBoardAlphaAsDouble() + ".png");

        if (!Files.exists(diffImageFile)
                && Files.exists(oldImageFile)
                && Files.exists(newImageFile)) {
            try {
                DiffImage.create(
                        oldImageFile,
                        newImageFile,
                        diffImageFile,
                        options.getPropBoardBackground(),
                        options.getPropUnchangedBoardAlphaAsDouble(),
                        options.getPropAddedElementColor(),
                        options.getPropRemovedElementColor(),
                        options.getPropUndefinedColor());
            } catch (IOException ex) {
                return null;
            }
        }

        return diffImageFile;
    }

    public Path getOrCreateBoardImage(
            Options options,
            RevCommit revCommit,
            EagleFile eagleFile,
            String tempFile,
            String layer) {
        try {
            Path imageFile = buildPath(
                    options,
                    revCommit,
                    eagleFile,
                    eagleFile.getFileName() + "-LAYER_" + layer + "-DPI_"
                    + options.getPropBoardDpiAsInt() + ".png");

            if (!Files.exists(imageFile)) {
                Path boardFile;

                if (revCommit != null) {
                    boardFile = options.getTempDir().resolve(tempFile);
                } else {
                    boardFile = eagleFile.getFile();
                }

                if (!Files.exists(boardFile)) {
                    jGit.extractFile(
                            boardFile,
                            revCommit,
                            eagleFile.getRepoFile(),
                            eagleFile.getRenames());
                }

                String allLayers;
                switch (layer) {
                    case "1":
                        allLayers = layer + " " + options.getPropLayersTop();
                        break;
                    case "16":
                        allLayers = layer + " " + options.getPropLayersBottom();
                        break;
                    default:
                        allLayers = layer + " " + options.getPropLayersOther();
                        break;
                }

                Eagle.extractBoardImage(
                        options.getPropEagleBinaryAsPath(),
                        allLayers,
                        imageFile,
                        options.getPropBoardDpiAsInt(),
                        boardFile);
            }

            return imageFile;
        } catch (IOException | InterruptedException ex) {
            return null;
        }
    }

    public Path getOrCreateSchematicDiffImage(
            Options options,
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit,
            int sheet) {
        Path oldImageFile = getOrCreateSchematicImage(options, oldCommit, eagleFile, "old.sch", sheet);
        Path newImageFile = getOrCreateSchematicImage(options, newCommit, eagleFile, "new.sch", sheet);

        Path diffImageFile = buildDiffPath(
                options,
                eagleFile,
                oldCommit,
                newCommit,
                "-SHEET_" + sheet + "-DPI_" + options.getPropSchematicDpiAsInt()
                + "-ALPHA_" + options.getPropUnchangedSchematicAlphaAsDouble() + ".png");

        if (!Files.exists(diffImageFile)
                && Files.exists(oldImageFile)
                && Files.exists(newImageFile)) {
            try {
                DiffImage.create(
                        oldImageFile,
                        newImageFile,
                        diffImageFile,
                        options.getPropSchematicBackground(),
                        options.getPropUnchangedSchematicAlphaAsDouble(),
                        options.getPropAddedElementColor(),
                        options.getPropRemovedElementColor(),
                        options.getPropUndefinedColor());
            } catch (IOException ex) {
                return null;
            }
        }

        return diffImageFile;
    }

    public Path getOrCreateSchematicImage(
            Options options,
            RevCommit revCommit,
            EagleFile eagleFile,
            String tempFile,
            int sheet) {
        try {
            Path imageFile = buildPath(
                    options,
                    revCommit,
                    eagleFile,
                    "-SHEET_" + sheet + "-DPI_" + options.getPropSchematicDpiAsInt() + ".png");

            if (!Files.exists(imageFile)) {
                Path schematicFile;

                if (revCommit != null) {
                    schematicFile = options.getTempDir().resolve(tempFile);
                } else {
                    schematicFile = eagleFile.getFile();
                }

                if (!Files.exists(schematicFile)) {
                    jGit.extractFile(
                            schematicFile,
                            revCommit,
                            eagleFile.getRepoFile(),
                            eagleFile.getRenames());
                }

                Eagle.extractSheetImage(
                        options.getPropEagleBinaryAsPath(),
                        sheet,
                        imageFile,
                        options.getPropSchematicDpiAsInt(),
                        schematicFile);
            }

            return imageFile;
        } catch (IOException | InterruptedException ex) {
            return null;
        }
    }

    private Path buildDiffPath(
            Options options,
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit,
            String reaminingFilename) {
        Path path;

        if (newCommit != null) {
            path = options.getReposDir()
                    .resolve(repoName)
                    .resolve("diffImages")
                    .resolve(oldCommit.getName() + "-" + newCommit.getName());
            if (eagleFile.getRepoFile().getParent() != null) {
                path = path.resolve(eagleFile.getRepoFile().getParent());
            }
            path = path.resolve(eagleFile.getFileName() + reaminingFilename);
        } else {
            path = options.getTempDir()
                    .resolve("DIFF-" + eagleFile.getFileName() + reaminingFilename);
        }

        return path;
    }

    public List<String> getSameLayers(Options options, Repo repo, EagleFile eagleFile, RevCommit oldCommit, RevCommit newCommit) {
        Path oldLayersFile = buildPath(options, oldCommit, eagleFile, "-LAYERS.txt");
        if (!Files.exists(oldLayersFile)) {
            createLayersFile(options, eagleFile, oldCommit, "old.brd");
        }

        List<String> oldLayers = getLayers(options, oldCommit, eagleFile);

        Path newLayersFile = buildPath(options, newCommit, eagleFile, "-LAYERS.txt");
        if (!Files.exists(newLayersFile)) {
            createLayersFile(options, eagleFile, newCommit, "new.brd");
        }

        List<String> newLayers = getLayers(options, newCommit, eagleFile);

        List<String> layers = new LinkedList<>();

        for (String layer : oldLayers) {
            if (newLayers.contains(layer)) {
                layers.add(layer);
            }
        }

        return layers;
    }

    public List<String> getLayers(Options options, RevCommit revCommit, EagleFile eagleFile) {
        Path countFile = buildPath(options, revCommit, eagleFile, "-LAYERS.txt");

        if (Files.exists(countFile)) {
            try {
                return Arrays.asList(Files.readAllLines(
                        countFile,
                        Charset.defaultCharset()).get(0).split(";"));
            } catch (IOException ex) {
                return new LinkedList<>();
            }
        } else {
            return new LinkedList<>();
        }
    }

    public void createLayersFile(
            Options options,
            EagleFile eagleFile,
            RevCommit revCommit,
            String fileName) {
        Path layersFile = buildPath(options, revCommit, eagleFile, "-LAYERS.txt");

        try {
            if (!Files.exists(layersFile)) {
                Path boardFile;

                if (revCommit != null) {
                    boardFile = options.getTempDir().resolve(fileName);
                } else {
                    boardFile = eagleFile.getFile();
                }

                if (!Files.exists(boardFile)) {
                    jGit.extractFile(
                            boardFile,
                            revCommit,
                            eagleFile.getRepoFile(),
                            eagleFile.getRenames());
                }

                try {
                    Files.copy(
                            options.getPropEagleBinaryAsPath().getParent().resolve("..").normalize().resolve("doc").resolve("eagle.dtd"),
                            options.getTempDir().resolve("eagle.dtd"));
                } catch (FileAlreadyExistsException ex) {
                }

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(boardFile.toFile());
                document.getDocumentElement().normalize();

                NodeList layers = document.getElementsByTagName("layer");

                String layerString = "";
                boolean first = true;

                for (int i = 0; i < layers.getLength(); i++) {
                    Element layer = (Element) layers.item(i);

                    if (Integer.parseInt(layer.getAttribute("number")) <= 16
                            && "yes".equals(layer.getAttribute("active"))) {
                        if (first) {
                            first = false;
                        } else {
                            layerString += ";";
                        }

                        layerString += layer.getAttribute("number");
                    }
                }

                if (!Files.exists(layersFile)) {
                    Files.createDirectories(layersFile.getParent());
                }

                try (FileOutputStream fos = new FileOutputStream(layersFile.toFile())) {
                    fos.write(layerString.getBytes());
                    fos.flush();
                }
            }
        } catch (IOException | SAXException | ParserConfigurationException ex) {
            Logger.getLogger(Repo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
