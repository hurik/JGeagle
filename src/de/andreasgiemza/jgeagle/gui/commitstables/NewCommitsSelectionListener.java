package de.andreasgiemza.jgeagle.gui.commitstables;

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
                    = newCommitsTable.convertRowIndexToModel(newCommitsTable.getSelectedRow());
            NewCommitsTableModel newCommitTableModel
                    = (NewCommitsTableModel) newCommitsTable.getModel();

            jGeagle.newCommitSelected(
                    newCommitTableModel.getEagleFile(),
                    newCommitTableModel.getOldCommit(),
                    newCommitTableModel.getElementAt(newCommitsTableSelectedRow));
        }
    }
}
