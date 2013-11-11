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

import de.andreasgiemza.jgeagle.repo.data.EagleFile;
import de.andreasgiemza.jgeagle.options.Options;
import de.andreasgiemza.jgeagle.panels.ImageViewerPanel;
import de.andreasgiemza.jgeagle.repo.Repo;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author Andreas Giemza
 */
public class SheetLayerDiffImage {

    private final Options options;
    private final JComboBox sheetComboBox;
    private final JButton sheetButton;
    private final JComboBox layerComboBox;
    private final JButton diffImageButton;

    public SheetLayerDiffImage(
            Options options,
            JComboBox sheetComboBox,
            JButton sheetButton,
            JComboBox layerComboBox,
            JButton diffImageButton) {
        this.options = options;
        this.sheetComboBox = sheetComboBox;
        this.sheetButton = sheetButton;
        this.layerComboBox = layerComboBox;
        this.diffImageButton = diffImageButton;
    }

    public void reset() {
        sheetComboBox.setEnabled(false);
        sheetComboBox.removeAllItems();
        sheetButton.setEnabled(false);
        diffImageButton.setEnabled(false);
    }

    public void brdSelected(Options options,
            Repo repo,
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit) {
        List<String> layers = repo.getLayers(options, repo, eagleFile, oldCommit, newCommit);
        diffImageButton.setEnabled(true);
    }

    public void schSelected(
            Repo repo,
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit) {
        reset();

        int oldCount = repo.getSheetCount(options, oldCommit, eagleFile);
        int newCount = repo.getSheetCount(options, newCommit, eagleFile);

        if (oldCount != 0 && newCount != 0) {
            for (int i = 1; i <= Math.min(oldCount, newCount); i++) {
                sheetComboBox.addItem(i);
            }

            sheetComboBox.setEnabled(true);
            diffImageButton.setEnabled(true);
        } else {
            sheetButton.setEnabled(true);
        }
    }

    public void countSheets(
            Repo repo,
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit) {
        repo.createSheetCountFile(options, eagleFile, oldCommit, "old.sch");
        repo.createSheetCountFile(options, eagleFile, newCommit, "new.sch");

        schSelected(repo, eagleFile, oldCommit, newCommit);
    }

    public void createDiffImage(
            Repo repo,
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit)
            throws IOException, InterruptedException {
        Path diffImageFile;
        String titleExtraText;

        if (eagleFile.getFileExtension().equals(EagleFile.BRD)) {
            diffImageFile = repo.getOrCreateBoardDiffImage(
                    options,
                    eagleFile,
                    oldCommit,
                    newCommit);

            titleExtraText = "";
        } else {
            int sheet = (int) sheetComboBox.getSelectedItem();

            diffImageFile = repo.getOrCreateSchematicDiffImage(
                    options,
                    eagleFile,
                    oldCommit,
                    newCommit,
                    sheet
            );

            titleExtraText = " - Sheet " + sheet;
        }

        if (diffImageFile != null) {
            ImageViewerPanel.showImageViewer(options, diffImageFile, eagleFile, titleExtraText);
        }
    }
}
