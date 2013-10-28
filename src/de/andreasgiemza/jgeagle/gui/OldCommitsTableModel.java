package de.andreasgiemza.jgeagle.gui;

import de.andreasgiemza.jgeagle.data.EagleFile;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author hurik
 */
public class OldCommitsTableModel extends AbstractTableModel {

    private final EagleFile eagleFile;
    private final List<String> columnTitles = Arrays.asList(
            "Date",
            "Author",
            "Message");

    public OldCommitsTableModel(EagleFile eagleFile) {
        this.eagleFile = eagleFile;
    }

    @Override
    public String getColumnName(int i) {
        return columnTitles.get(i);
    }

    @Override
    public int getColumnCount() {
        return columnTitles.size();
    }

    @Override
    public Class<?> getColumnClass(int i) {
        switch (i) {
            case 0:
                return Date.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
        }

        return null;
    }

    @Override
    public int getRowCount() {
        if (eagleFile.isWorkingCopychanges()) {
            return eagleFile.getCommits().size();
        } else {
            return eagleFile.getCommits().size() - 1;
        }
    }

    @Override
    public Object getValueAt(int i, int i1) {
        RevCommit commit;

        if (eagleFile.isWorkingCopychanges()) {
            commit = eagleFile.getCommits().get(i);
        } else {
            commit = eagleFile.getCommits().get(i + 1);
        }

        switch (i1) {
            case 0:
                return new Date((long) commit.getCommitTime() * 1000);
            case 1:
                return commit.getAuthorIdent().getName();
            case 2:
                return commit.getFullMessage();
        }

        return null;
    }

    public EagleFile getEagleFile() {
        return eagleFile;
    }

    public RevCommit elementAt(int i) {
        RevCommit commit;

        if (eagleFile.isWorkingCopychanges()) {
            commit = eagleFile.getCommits().get(i);
        } else {
            commit = eagleFile.getCommits().get(i + 1);
        }

        return commit;
    }
}
