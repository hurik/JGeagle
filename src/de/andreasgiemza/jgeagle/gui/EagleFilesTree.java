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
 * @author Andreas Giemza
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

            for (Path pathPart : file.getRepoFile()) {
                if (!pathPart.toString().equals(file.getRepoFile().getFileName().toString())) {
                    Boolean found = false;

                    for (int i = 0; i < lastNode.getChildCount(); i++) {
                        if (pathPart.toString().equals(lastNode.getChildAt(i).toString())) {
                            lastNode = (DefaultMutableTreeNode) lastNode.getChildAt(i);
                            found = true;
                        }
                    }

                    if (!found) {
                        DefaultMutableTreeNode newNode
                                = new DefaultMutableTreeNode(pathPart.toString());
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
