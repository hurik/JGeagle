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
package de.andreasgiemza.jgeagle.repo.data;

import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author Andreas Giemza
 */
public class EagleFile {

    private final Path file;
    private final Path repoFile;
    private final String fileName;
    private final String fileExtension;
    private Boolean workingCopychanges;
    private Date workingCopyLastModified;
    private final List<RevCommit> commits = new LinkedList<>();
    private final Map<RevCommit, String> renames = new HashMap<>();
    private boolean follow;
    // Constants
    public final static String BRD = ".brd";
    public final static String SCH = ".sch";

    public EagleFile(Path file, Path repoFile) {
        this.file = file;
        this.repoFile = repoFile;

        fileName = repoFile.getFileName().toString().substring(
                0, repoFile.getFileName().toString().length() - 4);

        if (repoFile.toString().toLowerCase().endsWith(".brd")) {
            fileExtension = BRD;
        } else {
            fileExtension = SCH;
        }
    }

    public void clearData() {
        commits.clear();
        renames.clear();
    }

    public Path getFile() {
        return file;
    }

    public Path getRepoFile() {
        return repoFile;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public Boolean isWorkingCopychanges() {
        return workingCopychanges;
    }

    public void setWorkingCopychanges(Boolean workingCopychanges) {
        this.workingCopychanges = workingCopychanges;
    }

    public Date getWorkingCopyLastModified() {
        return workingCopyLastModified;
    }

    public void setWorkingCopyLastModified(Date workingCopyLastModified) {
        this.workingCopyLastModified = workingCopyLastModified;
    }

    public List<RevCommit> getCommits() {
        return commits;
    }

    public Map<RevCommit, String> getRenames() {
        return renames;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    @Override
    public String toString() {
        return file.getFileName().toString();
    }
}
