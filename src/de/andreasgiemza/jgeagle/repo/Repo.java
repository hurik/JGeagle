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

import de.andreasgiemza.jgeagle.helper.DiffImage;
import de.andreasgiemza.jgeagle.helper.Eagle;
import de.andreasgiemza.jgeagle.options.Options;
import de.andreasgiemza.jgeagle.repo.data.EagleFile;
import de.andreasgiemza.jgeagle.repo.rcs.JGit;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

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
        jGit = new JGit(directory);
        repoName = directory.getName(directory.getNameCount() - 1).toString();
        Files.walkFileTree(directory, new GetRepoFiles(directory, eagleFiles));
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
        Path countFile = buildPath(options, revCommit, eagleFile, ".txt");

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
        Path countFile = buildPath(options, revCommit, eagleFile, ".txt");

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
            RevCommit newCommit) {
        Path oldImageFile = getOrCreateBoardImage(options, oldCommit, eagleFile, "old.brd");
        Path newImageFile = getOrCreateBoardImage(options, newCommit, eagleFile, "new.brd");

        Path diffImageFile = buildDiffPath(
                options,
                eagleFile,
                oldCommit,
                newCommit,
                "-DPI_" + options.getPropBoardDpiAsInt() + ".png");

        if (!Files.exists(diffImageFile)
                && Files.exists(oldImageFile)
                && Files.exists(newImageFile)) {
            try {
                DiffImage.create(
                        oldImageFile,
                        newImageFile,
                        diffImageFile,
                        options.getPropBoardBackground(),
                        options.getPropUnchangedAlphaAsDouble(),
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
            String tempFile) {
        try {
            Path imageFile = buildPath(
                    options,
                    revCommit,
                    eagleFile,
                    eagleFile.getFileName() + "-DPI_" + options.getPropBoardDpiAsInt() + ".png");

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

                Eagle.extractBoardImage(
                        options.getPropEagleBinaryAsPath(),
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
                "-SHEET_" + sheet + "-DPI_" + options.getPropSchematicDpiAsInt() + ".png");

        if (!Files.exists(diffImageFile)
                && Files.exists(oldImageFile)
                && Files.exists(newImageFile)) {
            try {
                DiffImage.create(
                        oldImageFile,
                        newImageFile,
                        diffImageFile,
                        options.getPropSchematicBackground(),
                        options.getPropUnchangedAlphaAsDouble(),
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
}
