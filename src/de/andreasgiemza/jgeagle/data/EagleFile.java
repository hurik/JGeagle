package de.andreasgiemza.jgeagle.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author hurik
 */
public class EagleFile {

    private final Path file;
    private final Path repoFile;
    private Boolean workingCopychanges;
    private final List<RevCommit> commits = new LinkedList<>();
    private final Map<RevCommit, String> renames = new HashMap<>();

    public EagleFile(Path file, Path repoFile) {
        this.file = file;
        this.repoFile = repoFile;
    }

    public void getFileData(JGit jGit) throws IOException, GitAPIException {
        if (workingCopychanges == null) {
            workingCopychanges = jGit.checkForWorkingCopyChanges(repoFile);
            jGit.getFileHistory(repoFile, commits, renames);
        }
    }

    public Path getFile() {
        return file;
    }

    public Path getRepoFile() {
        return repoFile;
    }

    public Boolean isWorkingCopychanges() {
        return workingCopychanges;
    }

    public List<RevCommit> getCommits() {
        return commits;
    }

    public Map<RevCommit, String> getRenames() {
        return renames;
    }

    @Override
    public String toString() {
        return file.getFileName().toString();
    }
}
