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
package de.andreasgiemza.jgeagle.gui;

import de.andreasgiemza.jgeagle.JGeagle;
import de.andreasgiemza.jgeagle.data.EagleFile;
import de.andreasgiemza.jgeagle.helper.DiffImage;
import de.andreasgiemza.jgeagle.helper.Eagle;
import de.andreasgiemza.jgeagle.options.Options;
import de.andreasgiemza.jgeagle.repo.JGit;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author Andreas Giemza
 */
public class SheetsAndDiffImage {

    private final Options options;
    private final JComboBox sheetComboBox;
    private final JButton sheetButton;
    private final JButton diffImageButton;

    public SheetsAndDiffImage(
            Options options,
            JComboBox sheetComboBox,
            JButton sheetButton,
            JButton diffImageButton) {
        this.options = options;
        this.sheetComboBox = sheetComboBox;
        this.sheetButton = sheetButton;
        this.diffImageButton = diffImageButton;
    }

    public void reset() {
        sheetComboBox.setEnabled(false);
        sheetComboBox.removeAllItems();
        sheetButton.setEnabled(false);
        diffImageButton.setEnabled(false);
    }

    public void brdSelected() {
        diffImageButton.setEnabled(true);
    }

    public void schSelected(
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit) {
        reset();

        Path oldCountFile = buildPath(oldCommit, eagleFile, ".txt");
        Path newCountFile = buildPath(newCommit, eagleFile, ".txt");

        if (Files.exists(oldCountFile) && Files.exists(newCountFile)) {
            try {
                int oldCount = Integer.parseInt(
                        Files.readAllLines(
                                oldCountFile,
                                Charset.defaultCharset()).get(0));
                int newCount = Integer.parseInt(
                        Files.readAllLines(
                                newCountFile,
                                Charset.defaultCharset()).get(0));

                for (int i = 1; i <= Math.min(oldCount, newCount); i++) {
                    sheetComboBox.addItem(i);
                }
            } catch (IOException ex) {
                Logger.getLogger(SheetsAndDiffImage.class.getName()).log(Level.SEVERE, null, ex);
            }

            sheetComboBox.setEnabled(true);
            diffImageButton.setEnabled(true);
        } else {
            sheetButton.setEnabled(true);
        }
    }

    public void countSheets(
            JGit jGit,
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit)
            throws IOException, InterruptedException {
        Path oldCountFile = buildPath(oldCommit, eagleFile, ".txt");

        if (!Files.exists(oldCountFile)) {
            Path oldSchematicFile = options.getTempDir().resolve("old.sch");
            jGit.extractFile(
                    oldSchematicFile,
                    oldCommit,
                    eagleFile.getRepoFile(),
                    eagleFile.getRenames());

            Eagle.countSheets(
                    options.getPropEagleBinaryAsPath(),
                    options.getCountSheetsUlp(),
                    oldCountFile,
                    oldSchematicFile);
        }

        Path newCountFile = buildPath(newCommit, eagleFile, ".txt");

        if (!Files.exists(newCountFile)) {
            if (newCommit != null) {
                Path newSchematicFile = options.getTempDir().resolve("new.sch");
                jGit.extractFile(
                        newSchematicFile,
                        newCommit,
                        eagleFile.getRepoFile(),
                        eagleFile.getRenames());

                Eagle.countSheets(
                        options.getPropEagleBinaryAsPath(),
                        options.getCountSheetsUlp(),
                        newCountFile,
                        newSchematicFile);
            } else {
                Eagle.countSheets(
                        options.getPropEagleBinaryAsPath(),
                        options.getCountSheetsUlp(),
                        newCountFile,
                        eagleFile.getFile());
            }
        }

        schSelected(eagleFile, oldCommit, newCommit);
    }

    public void createDiffImage(
            JGeagle jGeagle,
            JGit jGit,
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit)
            throws IOException, InterruptedException {
        Path oldImageFile, newImageFile, diffImageFile;
        String background, titleExtraText;

        if (eagleFile.getFileExtension().equals(EagleFile.BRD)) {
            oldImageFile = buildPath(
                    oldCommit,
                    eagleFile,
                    eagleFile.getFileName() + "-DPI_" + options.getPropBoardDpiAsInt() + ".png");

            if (!Files.exists(oldImageFile)) {
                Path oldBoardFile = options.getTempDir().resolve("old.brd");

                if (!Files.exists(oldBoardFile)) {
                    jGit.extractFile(
                            oldBoardFile,
                            oldCommit,
                            eagleFile.getRepoFile(),
                            eagleFile.getRenames());
                }

                Eagle.extractBoardImage(
                        options.getPropEagleBinaryAsPath(),
                        oldImageFile,
                        options.getPropBoardDpiAsInt(),
                        oldBoardFile);
            }

            newImageFile = buildPath(
                    newCommit,
                    eagleFile,
                    eagleFile.getFileName() + "-DPI_" + options.getPropBoardDpiAsInt() + ".png");

            if (!Files.exists(newImageFile)) {
                if (newCommit != null) {
                    Path newBoardFile = options.getTempDir().resolve("new.brd");

                    if (!Files.exists(newBoardFile)) {
                        jGit.extractFile(
                                newBoardFile,
                                newCommit,
                                eagleFile.getRepoFile(),
                                eagleFile.getRenames());
                    }

                    Eagle.extractBoardImage(
                            options.getPropEagleBinaryAsPath(),
                            newImageFile,
                            options.getPropBoardDpiAsInt(),
                            newBoardFile);
                } else {
                    Eagle.extractBoardImage(
                            options.getPropEagleBinaryAsPath(),
                            newImageFile,
                            options.getPropBoardDpiAsInt(),
                            eagleFile.getFile());
                }
            }

            diffImageFile = buildDiffPath(
                    eagleFile,
                    oldCommit,
                    newCommit, "-DPI_" + options.getPropBoardDpiAsInt() + ".png");
            background = options.getPropBoardBackground();
            titleExtraText = "";
        } else {
            int sheet = (int) sheetComboBox.getSelectedItem();

            oldImageFile = buildPath(
                    oldCommit,
                    eagleFile,
                    "-SHEET_" + sheet + "-DPI_" + options.getPropSchematicDpiAsInt() + ".png");

            if (!Files.exists(oldImageFile)) {
                Path oldSchematicFile = options.getTempDir().resolve("old.sch");

                if (!Files.exists(oldSchematicFile)) {
                    jGit.extractFile(
                            oldSchematicFile,
                            oldCommit,
                            eagleFile.getRepoFile(),
                            eagleFile.getRenames());
                }

                Eagle.extractSheetImage(
                        options.getPropEagleBinaryAsPath(),
                        sheet,
                        oldImageFile,
                        options.getPropSchematicDpiAsInt(),
                        oldSchematicFile);
            }

            newImageFile = buildPath(
                    newCommit,
                    eagleFile,
                    "-SHEET_" + sheet + "-DPI_" + options.getPropSchematicDpiAsInt() + ".png");

            if (!Files.exists(newImageFile)) {
                if (newCommit != null) {
                    Path newSchematicFile = options.getTempDir().resolve("new.sch");

                    if (!Files.exists(newSchematicFile)) {
                        jGit.extractFile(
                                newSchematicFile,
                                oldCommit,
                                eagleFile.getRepoFile(),
                                eagleFile.getRenames());
                    }

                    Eagle.extractSheetImage(
                            options.getPropEagleBinaryAsPath(),
                            sheet,
                            newImageFile,
                            options.getPropSchematicDpiAsInt(),
                            newSchematicFile);
                } else {
                    Eagle.extractSheetImage(
                            options.getPropEagleBinaryAsPath(),
                            sheet,
                            newImageFile,
                            options.getPropSchematicDpiAsInt(),
                            eagleFile.getFile());
                }
            }

            diffImageFile = buildDiffPath(
                    eagleFile,
                    oldCommit,
                    newCommit,
                    "-SHEET_" + sheet + "-DPI_" + options.getPropSchematicDpiAsInt() + ".png");
            background = options.getPropSchematicBackground();
            titleExtraText = " - Sheet " + sheet;
        }

        if (!Files.exists(diffImageFile)) {
            DiffImage.create(
                    oldImageFile,
                    newImageFile,
                    diffImageFile,
                    background,
                    options.getPropUnchangedAlphaAsDouble(),
                    options.getPropAddedElementColor(),
                    options.getPropRemovedElementColor(),
                    options.getPropUndefinedColor());
        }

        ImageViewer.showImageViewer(diffImageFile, eagleFile, titleExtraText);
    }

    private Path buildPath(
            RevCommit revCommit,
            EagleFile eagleFile,
            String fileExtension) {
        Path path;

        if (revCommit != null) {
            path = options.getReposDir()
                    .resolve(eagleFile.getRepoName())
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

    private Path buildDiffPath(
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit,
            String fileExtension) {
        Path path;

        if (newCommit != null) {
            path = options.getReposDir()
                    .resolve(eagleFile.getRepoName())
                    .resolve("diffImages")
                    .resolve(oldCommit.getName() + "-" + newCommit.getName());
            if (eagleFile.getRepoFile().getParent() != null) {
                path = path.resolve(eagleFile.getRepoFile().getParent());
            }
            path = path.resolve(eagleFile.getFileName() + fileExtension);
        } else {
            path = options.getTempDir()
                    .resolve("DIFF-" + eagleFile.getFileName() + fileExtension);
        }

        return path;
    }
}
