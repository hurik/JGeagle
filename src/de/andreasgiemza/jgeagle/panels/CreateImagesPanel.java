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
import javax.swing.SwingUtilities;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author Andreas Giemza
 */
public class CreateImagesPanel extends javax.swing.JPanel {

    private final Options options;
    private final Repo repo;

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
        createButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        progressPanel = new javax.swing.JPanel();
        filesProgressBar = new javax.swing.JProgressBar();
        commitsProgressBar = new javax.swing.JProgressBar();
        sheetsProgressBar = new javax.swing.JProgressBar();

        informationLabel.setText("<html>\n<p><strong>ATTENTION:</strong></p>\n<p>This machine is not usable while performing this task, because the eagle window while constantly open and gets the focus.</p>\n</html>");
        informationLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        createButton.setText("Create");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");

        progressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Progress"));

        filesProgressBar.setString("");
        filesProgressBar.setStringPainted(true);

        commitsProgressBar.setEnabled(false);
        commitsProgressBar.setString("");
        commitsProgressBar.setStringPainted(true);

        sheetsProgressBar.setString("");
        sheetsProgressBar.setStringPainted(true);

        javax.swing.GroupLayout progressPanelLayout = new javax.swing.GroupLayout(progressPanel);
        progressPanel.setLayout(progressPanelLayout);
        progressPanelLayout.setHorizontalGroup(
            progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(filesProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
            .addComponent(commitsProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sheetsProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        progressPanelLayout.setVerticalGroup(
            progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progressPanelLayout.createSequentialGroup()
                .addComponent(filesProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commitsProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sheetsProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(informationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(createButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(informationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(createButton)
                .addGap(18, 18, 18)
                .addComponent(cancelButton)
                .addGap(18, 18, 18)
                .addComponent(progressPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int eagleFilesCount = repo.getEagleFiles().size();
                filesProgressBar.setMinimum(0);
                filesProgressBar.setMaximum(eagleFilesCount);

                for (EagleFile eagleFile : repo.getEagleFiles()) {
                    int currentEagleFile = repo.getEagleFiles().indexOf(eagleFile);
                    filesProgressBar.setString((currentEagleFile + 1) + " of " + eagleFilesCount + " files");
                    filesProgressBar.setValue(currentEagleFile + 1);
                    filesProgressBar.update(filesProgressBar.getGraphics());

                    repo.getEagleFileLogAndStatus(eagleFile);

                    int commitsCount = eagleFile.getCommits().size();
                    commitsProgressBar.setMinimum(0);
                    commitsProgressBar.setMaximum(commitsCount);

                    for (RevCommit commit : eagleFile.getCommits()) {
                        int currentCommit = eagleFile.getCommits().indexOf(commit);
                        commitsProgressBar.setString((currentCommit + 1) + " of " + commitsCount + " commits");
                        commitsProgressBar.setValue(currentCommit + 1);
                        commitsProgressBar.update(commitsProgressBar.getGraphics());

                        options.cleanTempDir();

                        if (eagleFile.getFileExtension().equals(EagleFile.BRD)) {
                            repo.getOrCreateBoardImage(options, commit, eagleFile, "board.brd");
                        } else {
                            repo.createSheetCountFile(options, eagleFile, commit, "schematic.sch");

                            int sheetCount = repo.getSheetCount(options, commit, eagleFile);
                            sheetsProgressBar.setMinimum(0);
                            sheetsProgressBar.setMaximum(sheetCount);

                            for (int sheet = 1; sheet <= sheetCount; sheet++) {
                                sheetsProgressBar.setString(sheet + " of " + sheetCount + " sheets");
                                sheetsProgressBar.setValue(sheet);
                                sheetsProgressBar.update(sheetsProgressBar.getGraphics());

                                repo.getOrCreateSchematicImage(options, commit, eagleFile, "schematic.sch", sheet);
                            }
                        }
                    }
                }
            }
        });
    }//GEN-LAST:event_createButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JProgressBar commitsProgressBar;
    private javax.swing.JButton createButton;
    private javax.swing.JProgressBar filesProgressBar;
    private javax.swing.JLabel informationLabel;
    private javax.swing.JPanel progressPanel;
    private javax.swing.JProgressBar sheetsProgressBar;
    // End of variables declaration//GEN-END:variables

}
