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

import de.andreasgiemza.jgeagle.gui.commitstables.OldCommitsTableModel;
import de.andreasgiemza.jgeagle.gui.commitstables.OldCommitsSelectionListener;
import de.andreasgiemza.jgeagle.gui.commitstables.NewCommitsTableModel;
import de.andreasgiemza.jgeagle.gui.commitstables.NewCommitsSelectionListener;
import de.andreasgiemza.jgeagle.JGeagle;
import de.andreasgiemza.jgeagle.data.EagleFile;
import de.andreasgiemza.jgeagle.gui.commitstables.CommitsTableCellRenderer;
import javax.swing.JTable;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author Andreas Giemza
 */
public class CommitsTables {

    private final JGeagle jGeagle;
    private final JTable oldCommitsTable;
    private final JTable newCommitsTable;
    private final CommitsTableCellRenderer commitsTableCellRenderer
            = new CommitsTableCellRenderer();

    public CommitsTables(JGeagle jGeagle, JTable oldCommitsTable, JTable newCommitsTable) {
        this.jGeagle = jGeagle;
        this.oldCommitsTable = oldCommitsTable;
        this.newCommitsTable = newCommitsTable;

        setup();
    }

    private void setup() {
        oldCommitsTable.getSelectionModel().addListSelectionListener(
                new OldCommitsSelectionListener(
                        jGeagle,
                        oldCommitsTable));
        newCommitsTable.getSelectionModel().addListSelectionListener(
                new NewCommitsSelectionListener(
                        jGeagle,
                        newCommitsTable));

        reset();
    }

    public void resetOldCommitsTable() {
        oldCommitsTable.setModel(new OldCommitsTableModel(null));
    }

    public void resetNewCommitsTable() {
        newCommitsTable.setModel(new NewCommitsTableModel(null, null));
    }

    public void reset() {
        resetOldCommitsTable();
        resetNewCommitsTable();
    }

    public void updateOldCommitsTable(EagleFile eagleFile) {
        oldCommitsTable.setModel(new OldCommitsTableModel(eagleFile));
        oldCommitsTable.getColumnModel().getColumn(0).setCellRenderer(commitsTableCellRenderer);
    }

    public void updateNewCommitsTable(EagleFile eagleFile, RevCommit oldCommit) {
        newCommitsTable.setModel(new NewCommitsTableModel(eagleFile, oldCommit));
        newCommitsTable.getColumnModel().getColumn(0).setCellRenderer(commitsTableCellRenderer);
    }

    public EagleFile getEagleFile() {
        return ((NewCommitsTableModel) newCommitsTable.getModel()).getEagleFile();
    }

    public RevCommit getOldCommit() {
        return ((NewCommitsTableModel) newCommitsTable.getModel()).getOldCommit();
    }

    public RevCommit getNewCommit() {
        return ((NewCommitsTableModel) newCommitsTable.getModel()).getElementAt(
                newCommitsTable.convertRowIndexToModel(newCommitsTable.getSelectedRow()));
    }
}
