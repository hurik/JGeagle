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
package de.andreasgiemza.jgeagle;

import de.andreasgiemza.jgeagle.repo.data.EagleFile;
import de.andreasgiemza.jgeagle.gui.EagleFilesTree;
import de.andreasgiemza.jgeagle.gui.CommitsTables;
import de.andreasgiemza.jgeagle.gui.SheetLayerDiffImage;
import de.andreasgiemza.jgeagle.options.Options;
import de.andreasgiemza.jgeagle.panels.AboutPanel;
import de.andreasgiemza.jgeagle.panels.CreateImagesPanel;
import de.andreasgiemza.jgeagle.panels.DeleteImagesPanel;
import de.andreasgiemza.jgeagle.panels.PreferencesPanel;
import de.andreasgiemza.jgeagle.repo.Repo;
import java.awt.Toolkit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author Andreas Giemza
 */
public class JGeagle extends javax.swing.JFrame {

    private final Options options;
    private final EagleFilesTree eagleFilesTree;
    private final CommitsTables commitsTables;
    private final SheetLayerDiffImage sheetLayerDiffImage;
    private Repo repo;

    /**
     * Creates new form JGeagle
     */
    public JGeagle() {
        initComponents();

        setLocation(new Double((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (this.getWidth() / 2)).intValue(),
                new Double((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (this.getHeight() / 2)).intValue());

        options = new Options();
        eagleFilesTree = new EagleFilesTree(this, eagleFilesJTree);
        commitsTables = new CommitsTables(this, oldCommitsTable, newCommitsTable);
        sheetLayerDiffImage = new SheetLayerDiffImage(
                options,
                sheetComboBox,
                layerComboBox,
                diffImageButton);

        if (!"".equals(options.getPropEagleBinary())
                && !Files.exists(options.getPropEagleBinaryAsPath())
                && !"".equals(options.getPropPresetRepo())) {
            openRepo(Paths.get(options.getPropPresetRepo()));
        }
    }

    public void eagleFileSelected(EagleFile eagleFile) {
        if (eagleFile == null) {
            commitsTables.reset();
            return;
        }

        repo.getEagleFileLogAndStatus(options, eagleFile);
        commitsTables.updateOldCommitsTable(eagleFile);
        commitsTables.resetNewCommitsTable();
        sheetLayerDiffImage.reset();
    }

    public void oldCommitSelected(EagleFile eagleFile, RevCommit oldCommit) {
        commitsTables.updateNewCommitsTable(eagleFile, oldCommit);
        sheetLayerDiffImage.reset();
    }

    public void newCommitSelected(
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit) {
        options.cleanTempDir();

        if (eagleFile.getFileExtension().equals(EagleFile.BRD)) {
            sheetLayerDiffImage.brdSelected(options, repo, eagleFile, oldCommit, newCommit);
        } else {
            sheetLayerDiffImage.schSelected(repo, eagleFile, oldCommit, newCommit);
        }
    }

    private void openRepo(Path directory) {
        eagleFilesTree.reset();
        commitsTables.reset();

        try {
            repo = new Repo(options, directory);
        } catch (IOException | GitAPIException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please select a valid git repository!",
                    "Not a valid git repository!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        eagleFilesTree.buildAndDisplayTree(repo);

        createImagesMenuItem.setEnabled(true);
        deleteImagesMenuItem.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        repositoryFileChooser = new javax.swing.JFileChooser();
        eagleFilesPanel = new javax.swing.JPanel();
        eagleFilesScrollPane = new javax.swing.JScrollPane();
        eagleFilesJTree = new javax.swing.JTree();
        eagleFilesExpandAllButton = new javax.swing.JButton();
        eagleFilesCollapseAllButton = new javax.swing.JButton();
        commitsPanel = new javax.swing.JPanel();
        oldCommitsPanel = new javax.swing.JPanel();
        oldCommitsScrollPane = new javax.swing.JScrollPane();
        oldCommitsTable = new javax.swing.JTable();
        newCommitsPanel = new javax.swing.JPanel();
        newCommitsScrollPane = new javax.swing.JScrollPane();
        newCommitsTable = new javax.swing.JTable();
        variousPanel = new javax.swing.JPanel();
        sheetPanel = new javax.swing.JPanel();
        sheetComboBox = new javax.swing.JComboBox();
        layerPanel = new javax.swing.JPanel();
        layerComboBox = new javax.swing.JComboBox();
        diffImagePanel = new javax.swing.JPanel();
        diffImageButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        repositoryMenu = new javax.swing.JMenu();
        repositoryMenuItem = new javax.swing.JMenuItem();
        toolsMenu = new javax.swing.JMenu();
        createImagesMenuItem = new javax.swing.JMenuItem();
        deleteImagesMenuItem = new javax.swing.JMenuItem();
        optionsMenu = new javax.swing.JMenu();
        preferencesMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        repositoryFileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JGeagle");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("de/andreasgiemza/jgeagle/gui/icons/jgeagle.png")));

        eagleFilesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Files"));

        eagleFilesJTree.setModel(null);
        eagleFilesScrollPane.setViewportView(eagleFilesJTree);

        eagleFilesExpandAllButton.setText("Expand all");
        eagleFilesExpandAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eagleFilesExpandAllButtonActionPerformed(evt);
            }
        });

        eagleFilesCollapseAllButton.setText("Collapse all");
        eagleFilesCollapseAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eagleFilesCollapseAllButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout eagleFilesPanelLayout = new javax.swing.GroupLayout(eagleFilesPanel);
        eagleFilesPanel.setLayout(eagleFilesPanelLayout);
        eagleFilesPanelLayout.setHorizontalGroup(
            eagleFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eagleFilesPanelLayout.createSequentialGroup()
                .addComponent(eagleFilesScrollPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(eagleFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(eagleFilesExpandAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(eagleFilesCollapseAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
        );
        eagleFilesPanelLayout.setVerticalGroup(
            eagleFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eagleFilesPanelLayout.createSequentialGroup()
                .addComponent(eagleFilesExpandAllButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eagleFilesCollapseAllButton))
            .addComponent(eagleFilesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        commitsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Commits"));
        commitsPanel.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        oldCommitsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Old"));
        oldCommitsPanel.setLayout(new javax.swing.BoxLayout(oldCommitsPanel, javax.swing.BoxLayout.LINE_AXIS));

        oldCommitsTable.setAutoCreateRowSorter(true);
        oldCommitsScrollPane.setViewportView(oldCommitsTable);

        oldCommitsPanel.add(oldCommitsScrollPane);

        commitsPanel.add(oldCommitsPanel);

        newCommitsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("New"));
        newCommitsPanel.setLayout(new javax.swing.BoxLayout(newCommitsPanel, javax.swing.BoxLayout.LINE_AXIS));

        newCommitsTable.setAutoCreateRowSorter(true);
        newCommitsScrollPane.setViewportView(newCommitsTable);

        newCommitsPanel.add(newCommitsScrollPane);

        commitsPanel.add(newCommitsPanel);

        variousPanel.setLayout(new java.awt.GridLayout(1, 0));

        sheetPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Sheet"));
        sheetPanel.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        sheetComboBox.setEnabled(false);
        sheetPanel.add(sheetComboBox);

        variousPanel.add(sheetPanel);

        layerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Layer"));
        layerPanel.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        layerComboBox.setEnabled(false);
        layerPanel.add(layerComboBox);

        variousPanel.add(layerPanel);

        diffImagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Diff image"));

        diffImageButton.setText("Make and/or show");
        diffImageButton.setEnabled(false);
        diffImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diffImageButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout diffImagePanelLayout = new javax.swing.GroupLayout(diffImagePanel);
        diffImagePanel.setLayout(diffImagePanelLayout);
        diffImagePanelLayout.setHorizontalGroup(
            diffImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(diffImageButton, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
        );
        diffImagePanelLayout.setVerticalGroup(
            diffImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(diffImageButton)
        );

        variousPanel.add(diffImagePanel);

        repositoryMenu.setText("Repository");

        repositoryMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        repositoryMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/andreasgiemza/jgeagle/gui/icons/open.png"))); // NOI18N
        repositoryMenuItem.setText("Open");
        repositoryMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repositoryMenuItemActionPerformed(evt);
            }
        });
        repositoryMenu.add(repositoryMenuItem);

        menuBar.add(repositoryMenu);

        toolsMenu.setText("Tools");

        createImagesMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        createImagesMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/andreasgiemza/jgeagle/gui/icons/createimages.png"))); // NOI18N
        createImagesMenuItem.setText("Create images");
        createImagesMenuItem.setEnabled(false);
        createImagesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createImagesMenuItemActionPerformed(evt);
            }
        });
        toolsMenu.add(createImagesMenuItem);

        deleteImagesMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        deleteImagesMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/andreasgiemza/jgeagle/gui/icons/deleteimages.png"))); // NOI18N
        deleteImagesMenuItem.setText("Delete images");
        deleteImagesMenuItem.setEnabled(false);
        deleteImagesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteImagesMenuItemActionPerformed(evt);
            }
        });
        toolsMenu.add(deleteImagesMenuItem);

        menuBar.add(toolsMenu);

        optionsMenu.setText("Options");

        preferencesMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        preferencesMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/andreasgiemza/jgeagle/gui/icons/preferences.png"))); // NOI18N
        preferencesMenuItem.setText("Preferences");
        preferencesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preferencesMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(preferencesMenuItem);

        menuBar.add(optionsMenu);

        helpMenu.setText("Help");

        aboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        aboutMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/andreasgiemza/jgeagle/gui/icons/about.png"))); // NOI18N
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(eagleFilesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(commitsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(variousPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(eagleFilesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commitsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(variousPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void eagleFilesExpandAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eagleFilesExpandAllButtonActionPerformed
        eagleFilesTree.expandAll();
    }//GEN-LAST:event_eagleFilesExpandAllButtonActionPerformed

    private void eagleFilesCollapseAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eagleFilesCollapseAllButtonActionPerformed
        eagleFilesTree.collapseAll();
    }//GEN-LAST:event_eagleFilesCollapseAllButtonActionPerformed

    private void diffImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diffImageButtonActionPerformed
        try {
            sheetLayerDiffImage.createDiffImage(
                    repo,
                    commitsTables.getEagleFile(),
                    commitsTables.getOldCommit(),
                    commitsTables.getNewCommit());
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(JGeagle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_diffImageButtonActionPerformed

    private void repositoryMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repositoryMenuItemActionPerformed
        if ("".equals(options.getPropEagleBinary())
                || !Files.exists(options.getPropEagleBinaryAsPath())) {
            JOptionPane.showMessageDialog(this,
                    "Please select the eagle binary in the Preferences!",
                    "Eagle binary not selected!",
                    JOptionPane.ERROR_MESSAGE);

            preferencesMenuItemActionPerformed(null);
            return;
        }

        int returnVal = repositoryFileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            openRepo(repositoryFileChooser.getSelectedFile().toPath());
        }
    }//GEN-LAST:event_repositoryMenuItemActionPerformed

    private void preferencesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preferencesMenuItemActionPerformed
        JDialog dialog = new JDialog(this, "Preferences", true);
        dialog.getContentPane().add(new PreferencesPanel(dialog, options));
        dialog.pack();
        dialog.setLocation(
                new Double((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (dialog.getWidth() / 2)).intValue(),
                new Double((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (dialog.getHeight() / 2)).intValue());
        dialog.setResizable(false);
        dialog.setVisible(true);
    }//GEN-LAST:event_preferencesMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        JDialog dialog = new JDialog(this, "About", true);
        dialog.getContentPane().add(new AboutPanel());
        dialog.pack();
        dialog.setLocation(
                new Double((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (dialog.getWidth() / 2)).intValue(),
                new Double((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (dialog.getHeight() / 2)).intValue());
        dialog.setResizable(false);
        dialog.setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void createImagesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createImagesMenuItemActionPerformed
        JDialog dialog = new JDialog(this, "Create images", true);
        dialog.getContentPane().add(new CreateImagesPanel(options, repo));
        dialog.pack();
        dialog.setLocation(
                new Double((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (dialog.getWidth() / 2)).intValue(),
                new Double((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (dialog.getHeight() / 2)).intValue());
        dialog.setResizable(false);
        dialog.setVisible(true);
    }//GEN-LAST:event_createImagesMenuItemActionPerformed

    private void deleteImagesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteImagesMenuItemActionPerformed
        JDialog dialog = new JDialog(this, "Delete images", true);
        dialog.getContentPane().add(new DeleteImagesPanel(options, repo));
        dialog.pack();
        dialog.setLocation(
                new Double((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (dialog.getWidth() / 2)).intValue(),
                new Double((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (dialog.getHeight() / 2)).intValue());
        dialog.setResizable(false);
        dialog.setVisible(true);
    }//GEN-LAST:event_deleteImagesMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Windows is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JGeagle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JGeagle().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JPanel commitsPanel;
    private javax.swing.JMenuItem createImagesMenuItem;
    private javax.swing.JMenuItem deleteImagesMenuItem;
    private javax.swing.JButton diffImageButton;
    private javax.swing.JPanel diffImagePanel;
    private javax.swing.JButton eagleFilesCollapseAllButton;
    private javax.swing.JButton eagleFilesExpandAllButton;
    private javax.swing.JTree eagleFilesJTree;
    private javax.swing.JPanel eagleFilesPanel;
    private javax.swing.JScrollPane eagleFilesScrollPane;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JComboBox layerComboBox;
    private javax.swing.JPanel layerPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel newCommitsPanel;
    private javax.swing.JScrollPane newCommitsScrollPane;
    private javax.swing.JTable newCommitsTable;
    private javax.swing.JPanel oldCommitsPanel;
    private javax.swing.JScrollPane oldCommitsScrollPane;
    private javax.swing.JTable oldCommitsTable;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JMenuItem preferencesMenuItem;
    private javax.swing.JFileChooser repositoryFileChooser;
    private javax.swing.JMenu repositoryMenu;
    private javax.swing.JMenuItem repositoryMenuItem;
    private javax.swing.JComboBox sheetComboBox;
    private javax.swing.JPanel sheetPanel;
    private javax.swing.JMenu toolsMenu;
    private javax.swing.JPanel variousPanel;
    // End of variables declaration//GEN-END:variables
}
