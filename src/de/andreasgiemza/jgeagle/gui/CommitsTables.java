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
import de.andreasgiemza.jgeagle.repo.data.EagleFile;
import de.andreasgiemza.jgeagle.gui.commitstables.DateCellRenderer;
import de.andreasgiemza.jgeagle.gui.commitstables.MessageCellRenderer;
import de.andreasgiemza.jgeagle.gui.commitstables.TableColumnAdjuster;
import javax.swing.JTable;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author Andreas Giemza
 */
public class CommitsTables {

    private final JGeagle jGeagle;
    private final JTable oldCommitsTable;
    private final TableColumnAdjuster oldCommitsTableAdjuster;
    private final JTable newCommitsTable;
    private final TableColumnAdjuster newCommitsTableAdjuster;
    private final DateCellRenderer dateCellRenderer
            = new DateCellRenderer();
    private final MessageCellRenderer messageCellRenderer
            = new MessageCellRenderer();

    public CommitsTables(
            JGeagle jGeagle,
            JTable oldCommitsTable,
            JTable newCommitsTable) {
        this.jGeagle = jGeagle;
        this.oldCommitsTable = oldCommitsTable;
        oldCommitsTableAdjuster = new TableColumnAdjuster(oldCommitsTable);
        this.newCommitsTable = newCommitsTable;
        newCommitsTableAdjuster = new TableColumnAdjuster(newCommitsTable);

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
        oldCommitsTable.getColumnModel().getColumn(0).setCellRenderer(dateCellRenderer);
        oldCommitsTable.getColumnModel().getColumn(2).setCellRenderer(messageCellRenderer);
        oldCommitsTableAdjuster.adjustColumn(0);
        oldCommitsTableAdjuster.adjustColumn(1);
    }

    public void updateNewCommitsTable(EagleFile eagleFile, RevCommit oldCommit) {
        newCommitsTable.setModel(new NewCommitsTableModel(eagleFile, oldCommit));
        newCommitsTable.getColumnModel().getColumn(0).setCellRenderer(dateCellRenderer);
        newCommitsTable.getColumnModel().getColumn(2).setCellRenderer(messageCellRenderer);
        newCommitsTableAdjuster.adjustColumn(0);
        newCommitsTableAdjuster.adjustColumn(1);
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
