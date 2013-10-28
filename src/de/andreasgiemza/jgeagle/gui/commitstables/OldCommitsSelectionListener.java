package de.andreasgiemza.jgeagle.gui.commitstables;

import de.andreasgiemza.jgeagle.JGeagle;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author hurik
 */
public class OldCommitsSelectionListener implements ListSelectionListener {

    private final JGeagle jGeagle;
    private final JTable oldCommitsTable;

    public OldCommitsSelectionListener(
            JGeagle jGeagle,
            JTable oldCommitsTable) {
        this.jGeagle = jGeagle;
        this.oldCommitsTable = oldCommitsTable;
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (oldCommitsTable.getSelectedRow() != -1
                && lse.getValueIsAdjusting()) {
            int oldCommitsTableSelectedRow
                    = oldCommitsTable.convertRowIndexToModel(oldCommitsTable.getSelectedRow());

            OldCommitsTableModel oldCommitTableModel = (OldCommitsTableModel) oldCommitsTable.getModel();

            jGeagle.oldCommitSelected(
                    oldCommitTableModel.getEagleFile(), 
                    oldCommitTableModel.getElement(oldCommitsTableSelectedRow));
        }
    }
}
