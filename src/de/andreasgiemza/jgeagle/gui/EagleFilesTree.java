package de.andreasgiemza.jgeagle.gui;

import de.andreasgiemza.jgeagle.JGeagle;
import de.andreasgiemza.jgeagle.data.EagleFile;
import de.andreasgiemza.jgeagle.gui.eaglefilestree.EagleFilesTreeCellRenderer;
import de.andreasgiemza.jgeagle.gui.eaglefilestree.EagleFilesTreeSelectionListener;
import java.nio.file.Path;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author hurik
 */
public class EagleFilesTree {

    private final JTree jTree;

    public EagleFilesTree(JGeagle jGeagle, JTree jTree) {
        this.jTree = jTree;

        setupJTree(jGeagle);
    }

    private void setupJTree(JGeagle jGeagle) {
        jTree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree.addTreeSelectionListener(
                new EagleFilesTreeSelectionListener(jGeagle, jTree));
        jTree.setCellRenderer(new EagleFilesTreeCellRenderer());
    }

    public void buildAndDisplayTree(
            Path repoDirectory,
            List<EagleFile> eagleFiles) {
        DefaultMutableTreeNode rootNode
                = new DefaultMutableTreeNode(repoDirectory.subpath(
                                repoDirectory.getNameCount() - 1,
                                repoDirectory.getNameCount()
                        ).toString());

        for (EagleFile file : eagleFiles) {
            DefaultMutableTreeNode lastNode = rootNode;

            for (Path test : file.getRepoFile()) {
                if (!test.toString().equals(file.getRepoFile().getFileName().toString())) {
                    Boolean found = false;

                    for (int i = 0; i < lastNode.getChildCount(); i++) {
                        if (test.toString().equals(lastNode.getChildAt(i).toString())) {
                            lastNode = (DefaultMutableTreeNode) lastNode.getChildAt(i);
                            found = true;
                        }
                    }

                    if (!found) {
                        DefaultMutableTreeNode newNode
                                = new DefaultMutableTreeNode(test.toString());
                        lastNode.add(newNode);
                        lastNode = newNode;
                    }
                } else {
                    lastNode.add(new DefaultMutableTreeNode(file));
                }
            }

        }

        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        jTree.setModel(treeModel);
    }

    public void expandAll() {
        for (int i = 0; i < jTree.getRowCount(); i++) {
            jTree.expandRow(i);
        }
    }

    public void collapseAll() {
        for (int i = jTree.getRowCount() - 1; i >= 1; i--) {
            jTree.collapseRow(i);
        }
    }
    
    public void reset() {
        jTree.setModel(null);
    }
}
