package de.andreasgiemza.jgeagle.gui;

import de.andreasgiemza.jgeagle.JGeagle;
import de.andreasgiemza.jgeagle.data.EagleFile;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author hurik
 */
public class EagleFilesTreeSelectionListener implements TreeSelectionListener {

    private final JGeagle jGeagle;
    private final JTree jTree;

    public EagleFilesTreeSelectionListener(JGeagle jGeagle, JTree jTree) {
        this.jGeagle = jGeagle;
        this.jTree = jTree;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();

        if (node.isLeaf()) {
            jGeagle.eagleFileSelected((EagleFile) node.getUserObject());
        }
    }
}
