package de.andreasgiemza.jgeagle.gui;

import de.andreasgiemza.jgeagle.data.EagleFile;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author hurik
 */
public class EagleFilesTreeCellRenderer extends DefaultTreeCellRenderer {

    private final ImageIcon schematicIcon;
    private final ImageIcon boardIcon;

    public EagleFilesTreeCellRenderer() {
        schematicIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("de/andreasgiemza/jgeagle/resources/schematic.png")));
        boardIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("de/andreasgiemza/jgeagle/resources/board.png")));
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        if (leaf) {
            DefaultMutableTreeNode currentTreeNode = (DefaultMutableTreeNode) value;
            EagleFile eagleFile = (EagleFile) currentTreeNode.getUserObject();

            if (eagleFile.getFile().toString().toLowerCase().endsWith(".sch")) {
                setLeafIcon(schematicIcon);
            } else if (eagleFile.getFile().toString().toLowerCase().endsWith(".brd")) {
                setLeafIcon(boardIcon);
            }
        }

        return super.getTreeCellRendererComponent(tree, value, sel,
                expanded, leaf, row, hasFocus);
    }
}
