package de.andreasgiemza.jgeagle.gui;

import de.andreasgiemza.jgeagle.JGeagle;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author hurik
 */
public class NewCommitsSelectionListener implements ListSelectionListener {

    private final JGeagle jGeagle;
    private final JTable oldCommitsTable;
    private final JTable newCommitsTable;

    public NewCommitsSelectionListener(
            JGeagle jGeagle,
            JTable oldCommitsTable,
            JTable newCommitsTable) {
        this.jGeagle = jGeagle;
        this.oldCommitsTable = oldCommitsTable;
        this.newCommitsTable = newCommitsTable;
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (newCommitsTable.getSelectedRow() != -1
                && lse.getValueIsAdjusting()) {
            int newCommitsTableSelectedRow
                    = newCommitsTable.convertRowIndexToModel(newCommitsTable.getSelectedRow());
            NewCommitsTableModel newCommitTableModel
                    = (NewCommitsTableModel) newCommitsTable.getModel();

            int oldCommitsTableSelectedRow
                    = oldCommitsTable.convertRowIndexToModel(oldCommitsTable.getSelectedRow());
            OldCommitsTableModel oldCommitTableModel
                    = (OldCommitsTableModel) oldCommitsTable.getModel();

            jGeagle.oldAndNewCommitSelected(
                    oldCommitTableModel.getEagleFile(),
                    oldCommitTableModel.elementAt(oldCommitsTableSelectedRow),
                    newCommitTableModel.elementAt(newCommitsTableSelectedRow));
        }
    }
}
