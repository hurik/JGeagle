package de.andreasgiemza.jgeagle.data;

import de.andreasgiemza.jgeagle.repo.JGit;
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
    private final String fileName;
    private final String fileExtension;
    private final String repoName;
    private Boolean workingCopychanges;
    private final List<RevCommit> commits = new LinkedList<>();
    private final Map<RevCommit, String> renames = new HashMap<>();
    // constants
    public final static String BRD = ".brd";
    public final static String SCH = ".sch";

    public EagleFile(Path file, Path repoFile) {
        this.file = file;
        this.repoFile = repoFile;

        repoName = file.subpath(
                file.getNameCount() - (repoFile.getNameCount() + 1),
                file.getNameCount() - repoFile.getNameCount()).toString();

        fileName = repoFile.getFileName().toString().substring(
                0, repoFile.getFileName().toString().length() - 4);

        if (repoFile.toString().toLowerCase().endsWith(".brd")) {
            fileExtension = BRD;
        } else {
            fileExtension = SCH;
        }
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

    public String getFileName() {
        return fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getRepoName() {
        return repoName;
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
