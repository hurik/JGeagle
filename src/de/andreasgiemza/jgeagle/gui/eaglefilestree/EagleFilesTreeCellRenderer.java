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
package de.andreasgiemza.jgeagle.gui.eaglefilestree;

import de.andreasgiemza.jgeagle.repo.data.EagleFile;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Andreas Giemza
 */
public class EagleFilesTreeCellRenderer extends DefaultTreeCellRenderer {

    private final ImageIcon schematicIcon;
    private final ImageIcon boardIcon;

    public EagleFilesTreeCellRenderer() {
        schematicIcon = new ImageIcon(Toolkit.getDefaultToolkit()
                .getImage(getClass().getClassLoader()
                        .getResource("de/andreasgiemza/jgeagle/gui/eaglefilestree/icons/schematic.png")));
        boardIcon = new ImageIcon(Toolkit.getDefaultToolkit()
                .getImage(getClass().getClassLoader()
                        .getResource("de/andreasgiemza/jgeagle/gui/eaglefilestree/icons/board.png")));
    }

    @Override
    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {
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
