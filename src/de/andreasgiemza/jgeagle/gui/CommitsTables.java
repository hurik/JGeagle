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
 * @author hurik
 */
public class CommitsTables {

    private final JGeagle jGeagle;
    private final JTable oldCommitsTable;
    private final JTable newCommitsTable;
    private final CommitsTableCellRenderer commitsTableCellRenderer 
            =new CommitsTableCellRenderer();

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
}
