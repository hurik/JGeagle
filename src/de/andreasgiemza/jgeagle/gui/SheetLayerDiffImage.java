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
    private final JComboBox layerComboBox;
    private final JButton diffImageButton;

    public SheetLayerDiffImage(
            Options options,
            JComboBox sheetComboBox,
            JComboBox layerComboBox,
            JButton diffImageButton) {
        this.options = options;
        this.sheetComboBox = sheetComboBox;
        this.layerComboBox = layerComboBox;
        this.diffImageButton = diffImageButton;
    }

    public void reset() {
        sheetComboBox.setEnabled(false);
        sheetComboBox.removeAllItems();
        layerComboBox.setEnabled(false);
        layerComboBox.removeAllItems();
        diffImageButton.setEnabled(false);
    }

    public void brdSelected(Options options,
            Repo repo,
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit) {
        reset();

        List<String> oldLayers = repo.getOrCreateLayersFile(options, oldCommit, eagleFile, "old.brd");
        List<String> newLayers = repo.getOrCreateLayersFile(options, newCommit, eagleFile, "new.brd");

        for (String layer : oldLayers) {
            if (newLayers.contains(layer)) {
                layerComboBox.addItem(layer);
            }
        }

        layerComboBox.setEnabled(true);
        diffImageButton.setEnabled(true);
    }

    public void schSelected(
            Repo repo,
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit) {
        reset();

        int oldCount = repo.getorCreateSheetCountFile(options, oldCommit, eagleFile, "old.sch");
        int newCount = repo.getorCreateSheetCountFile(options, newCommit, eagleFile, "new.sch");

        for (int i = 1; i <= Math.min(oldCount, newCount); i++) {
            sheetComboBox.addItem(i);
        }

        sheetComboBox.setEnabled(true);
        diffImageButton.setEnabled(true);
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
            String layer = (String) layerComboBox.getSelectedItem();

            diffImageFile = repo.getOrCreateBoardDiffImage(
                    options,
                    eagleFile,
                    oldCommit,
                    newCommit,
                    layer);

            titleExtraText = " - Layer " + layer;
        } else {
            int sheet = (int) sheetComboBox.getSelectedItem();

            diffImageFile = repo.getOrCreateSchematicDiffImage(
                    options,
                    eagleFile,
                    oldCommit,
                    newCommit,
                    sheet);

            titleExtraText = " - Sheet " + sheet;
        }

        if (diffImageFile != null) {
            ImageViewerPanel.showImageViewer(options, diffImageFile, eagleFile, titleExtraText);
        }
    }
}
