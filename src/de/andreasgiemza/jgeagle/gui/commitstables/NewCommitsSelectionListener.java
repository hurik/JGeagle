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
package de.andreasgiemza.jgeagle.gui.commitstables;

import de.andreasgiemza.jgeagle.JGeagle;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Andreas Giemza
 */
public class NewCommitsSelectionListener implements ListSelectionListener {

    private final JGeagle jGeagle;
    private final JTable newCommitsTable;

    public NewCommitsSelectionListener(
            JGeagle jGeagle,
            JTable newCommitsTable) {
        this.jGeagle = jGeagle;
        this.newCommitsTable = newCommitsTable;
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (newCommitsTable.getSelectedRow() != -1
                && lse.getValueIsAdjusting()) {
            int newCommitsTableSelectedRow
                    = newCommitsTable.convertRowIndexToModel(
                            newCommitsTable.getSelectedRow());
            NewCommitsTableModel newCommitTableModel
                    = (NewCommitsTableModel) newCommitsTable.getModel();

            jGeagle.newCommitSelected(
                    newCommitTableModel.getEagleFile(),
                    newCommitTableModel.getOldCommit(),
                    newCommitTableModel.getElementAt(newCommitsTableSelectedRow));
        }
    }
}
