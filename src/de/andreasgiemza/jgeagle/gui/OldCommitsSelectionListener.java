package de.andreasgiemza.jgeagle.gui;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author hurik
 */
public class OldCommitsSelectionListener implements ListSelectionListener {

    private final JTable oldCommitsTable;
    private final JTable newCommitsTable;

    public OldCommitsSelectionListener(
            JTable oldCommitsTable,
            JTable newCommitsTable) {
        this.oldCommitsTable = oldCommitsTable;
        this.newCommitsTable = newCommitsTable;
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (oldCommitsTable.getSelectedRow() != -1
                && lse.getValueIsAdjusting()) {
            int oldCommitsTableSelectedRow
                    = oldCommitsTable.convertRowIndexToModel(oldCommitsTable.getSelectedRow());

            OldCommitsTableModel oldCommitTableModel = (OldCommitsTableModel) oldCommitsTable.getModel();

            newCommitsTable.setModel(new NewCommitsTableModel(oldCommitTableModel.getEagleFile(), oldCommitTableModel.elementAt(oldCommitsTableSelectedRow)));
        }
    }
}
