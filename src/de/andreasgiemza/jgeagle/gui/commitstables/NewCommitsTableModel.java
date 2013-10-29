package de.andreasgiemza.jgeagle.gui.commitstables;

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
public class NewCommitsTableModel extends AbstractTableModel {

    private final EagleFile eagleFile;
    private final RevCommit oldCommit;
    private final List<String> columnTitles = Arrays.asList(
            "Date",
            "Author",
            "Message");

    public NewCommitsTableModel(EagleFile eagleFile, RevCommit revCommit) {
        this.eagleFile = eagleFile;
        this.oldCommit = revCommit;
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
        if (eagleFile == null) {
            return 0;
        }

        int i = 0;

        for (RevCommit commit : eagleFile.getCommits()) {
            if (commit == oldCommit) {
                break;
            }

            i++;
        }

        if (eagleFile.isWorkingCopychanges()) {
            i++;
        }

        return i;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        RevCommit commit;

        if (eagleFile.isWorkingCopychanges()) {
            if (i == 0) {
                switch (i1) {
                    case 0:
                        return eagleFile.getWorkingCopyLastModified();
                    case 1:
                        return System.getProperty("user.name");
                    case 2:
                        return "Working copy change";
                }
            }

            commit = eagleFile.getCommits().get(i - 1);
        } else {
            commit = eagleFile.getCommits().get(i);
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

    public RevCommit getOldCommit() {
        return oldCommit;
    }

    public RevCommit getElementAt(int i) {
        RevCommit commit;

        if (eagleFile.isWorkingCopychanges()) {
            if (i == 0) {
                return null;
            }

            commit = eagleFile.getCommits().get(i - 1);
        } else {
            commit = eagleFile.getCommits().get(i);
        }

        return commit;
    }
}
