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
package de.andreasgiemza.jgeagle.panels;

import de.andreasgiemza.jgeagle.repo.data.EagleFile;
import de.andreasgiemza.jgeagle.options.Options;
import de.andreasgiemza.jgeagle.repo.Repo;
import java.util.List;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author Andreas Giemza
 */
public class CreateImagesPanel extends javax.swing.JPanel {

    private final Options options;
    private final Repo repo;
    private Thread thread;
    private volatile boolean interrupted;

    /**
     * Creates new form CreateImagesPanel
     *
     * @param options
     * @param repo
     */
    public CreateImagesPanel(
            Options options,
            Repo repo) {
        initComponents();

        this.options = options;
        this.repo = repo;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        informationLabel = new javax.swing.JLabel();
        filetypeComboBox = new javax.swing.JComboBox();
        createButton = new javax.swing.JButton();
        progressPanel = new javax.swing.JPanel();
        filesProgressBar = new javax.swing.JProgressBar();
        commitsProgressBar = new javax.swing.JProgressBar();
        sheetsProgressBar = new javax.swing.JProgressBar();
        cancelButton = new javax.swing.JButton();

        informationLabel.setForeground(new java.awt.Color(204, 0, 0));
        informationLabel.setText("<html><table cols=1 width=300><td><strong>ATTENTION:</strong><br \\>This machine is not usable while performing this task, because the eagle window will constantly open and close while getting the focus.</td></table></html>");

        filetypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All images", "Schematic images", "Board images" }));

        createButton.setText("Create");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        progressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Progress"));

        filesProgressBar.setString("");
        filesProgressBar.setStringPainted(true);

        commitsProgressBar.setEnabled(false);
        commitsProgressBar.setString("");
        commitsProgressBar.setStringPainted(true);

        sheetsProgressBar.setString("");
        sheetsProgressBar.setStringPainted(true);

        cancelButton.setText("Cancel");
        cancelButton.setEnabled(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout progressPanelLayout = new javax.swing.GroupLayout(progressPanel);
        progressPanel.setLayout(progressPanelLayout);
        progressPanelLayout.setHorizontalGroup(
            progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(filesProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(commitsProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sheetsProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        progressPanelLayout.setVerticalGroup(
            progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progressPanelLayout.createSequentialGroup()
                .addComponent(filesProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commitsProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sheetsProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancelButton))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(createButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(filetypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(informationLabel))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(informationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(filetypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createButton)
                .addGap(18, 18, 18)
                .addComponent(progressPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
        filetypeComboBox.setEnabled(false);
        createButton.setEnabled(false);
        cancelButton.setEnabled(true);

        interrupted = false;

        thread = new Thread(new Worker(filetypeComboBox.getSelectedIndex()));
        thread.start();
    }//GEN-LAST:event_createButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        interrupted = true;
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JProgressBar commitsProgressBar;
    private javax.swing.JButton createButton;
    private javax.swing.JProgressBar filesProgressBar;
    private javax.swing.JComboBox filetypeComboBox;
    private javax.swing.JLabel informationLabel;
    private javax.swing.JPanel progressPanel;
    private javax.swing.JProgressBar sheetsProgressBar;
    // End of variables declaration//GEN-END:variables

    private class Worker implements Runnable {

        private final int filetype;

        private Worker(int filetype) {
            this.filetype = filetype;
        }

        @Override
        public void run() {

            filesProgressBar.setMinimum(0);

            int eagleFilesCount = 0;

            if (filetype == 0) {
                eagleFilesCount = repo.getEagleFiles().size();
            } else {
                for (EagleFile eagleFile : repo.getEagleFiles()) {
                    if (eagleFile.getFileExtension().equals(EagleFile.SCH)
                            && filetype == 1) {
                        eagleFilesCount++;
                    } else if (eagleFile.getFileExtension().equals(EagleFile.BRD)
                            && filetype == 2) {
                        eagleFilesCount++;
                    }
                }
            }

            filesProgressBar.setMaximum(eagleFilesCount);

            int currentEagleFile = 0;
            for (EagleFile eagleFile : repo.getEagleFiles()) {
                if (interrupted) {
                    updateGui();
                    return;
                }

                if (filetype != 0) {
                    if (eagleFile.getFileExtension().equals(EagleFile.SCH)
                            && filetype != 1) {
                        continue;
                    } else if (eagleFile.getFileExtension().equals(EagleFile.BRD)
                            && filetype != 2) {
                        continue;
                    }
                }

                currentEagleFile++;
                filesProgressBar.setString(currentEagleFile + " of " + eagleFilesCount + " files");
                filesProgressBar.setValue(currentEagleFile);

                repo.getEagleFileLogAndStatus(options, eagleFile);

                int commitsCount = eagleFile.getCommits().size();
                commitsProgressBar.setMinimum(0);
                commitsProgressBar.setMaximum(commitsCount);

                for (RevCommit commit : eagleFile.getCommits()) {
                    if (interrupted) {
                        updateGui();
                        return;
                    }

                    int currentCommit = eagleFile.getCommits().indexOf(commit);
                    commitsProgressBar.setString((currentCommit + 1) + " of " + commitsCount + " commits");
                    commitsProgressBar.setValue(currentCommit + 1);

                    options.cleanTempDir();

                    if (eagleFile.getFileExtension().equals(EagleFile.BRD)) {
                        List<String> layers = repo.getOrCreateLayersFile(options, commit, eagleFile, "board.brd");
                        sheetsProgressBar.setMinimum(0);
                        sheetsProgressBar.setMaximum(layers.size());

                        for (String layer : layers) {
                            if (interrupted) {
                                updateGui();
                                return;
                            }

                            sheetsProgressBar.setString(layers.indexOf(layer) + 1 + " of " + layers.size() + " layers");
                            sheetsProgressBar.setValue(layers.indexOf(layer) + 1);

                            repo.getOrCreateBoardImage(options, commit, eagleFile, "board.brd", layer);
                        }
                    } else {
                        int sheetCount = repo.getorCreateSheetCountFile(options, commit, eagleFile, "schematic.sch");
                        sheetsProgressBar.setMinimum(0);
                        sheetsProgressBar.setMaximum(sheetCount);

                        for (int sheet = 1; sheet <= sheetCount; sheet++) {
                            if (interrupted) {
                                updateGui();
                                return;
                            }

                            sheetsProgressBar.setString(sheet + " of " + sheetCount + " sheets");
                            sheetsProgressBar.setValue(sheet);

                            repo.getOrCreateSchematicImage(options, commit, eagleFile, "schematic.sch", sheet);
                        }
                    }
                }
            }

            updateGui();
        }

        private void updateGui() {
            filetypeComboBox.setEnabled(true);
            createButton.setEnabled(true);
            cancelButton.setEnabled(false);
        }
    }
}
