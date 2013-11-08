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

import de.andreasgiemza.jgeagle.repo.data.EagleFile;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author Andreas Giemza
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
        if (eagleFile == null) {
            return 0;
        }

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

    public RevCommit getElement(int i) {
        RevCommit commit;

        if (eagleFile.isWorkingCopychanges()) {
            commit = eagleFile.getCommits().get(i);
        } else {
            commit = eagleFile.getCommits().get(i + 1);
        }

        return commit;
    }
}
