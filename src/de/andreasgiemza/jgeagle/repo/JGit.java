package de.andreasgiemza.jgeagle.repo;

import de.andreasgiemza.jgeagle.data.EagleFile;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.TreeWalk;

/**
 *
 * @author hurik
 */
public class JGit {

    private final Repository repository;
    private final Git git;

    public JGit(Path directory) throws IOException, GitAPIException {
        repository = new FileRepository(directory.resolve(".git").toFile());
        git = new Git(repository);
    }

    public Boolean checkForWorkingCopyChanges(Path repoFile) throws GitAPIException {
        Set<String> modifiedFiles = git.status().call().getModified();

        return modifiedFiles.contains(repoFile.toString().replace("\\", "/"));
    }

    public List<RevCommit> getFileHistory(Path repoFile, List<RevCommit> commits, Map<RevCommit, String> renames) throws IOException, MissingObjectException, GitAPIException {
        final String originalPath = repoFile.toString().replace("\\", "/");
        String path = originalPath;

        RevCommit start = null;

        do {
            Iterable<RevCommit> log = git.log().addPath(path).call();
            for (RevCommit commit : log) {
                if (commits.contains(commit)) {
                    start = null;
                } else {
                    start = commit;
                    commits.add(commit);

                    if (!originalPath.equals(path)) {
                        renames.put(commit, path);
                    }
                }
            }

            if (start == null) {
                return commits;
            }
        } while ((path = getRenamedPath(start, path)) != null);

        return commits;
    }

    private String getRenamedPath(RevCommit start, String path) throws IOException, GitAPIException {
        Iterable<RevCommit> allCommitsLater = git.log().add(start).call();

        for (RevCommit commit : allCommitsLater) {
            TreeWalk tw = new TreeWalk(repository);
            tw.addTree(commit.getTree());
            tw.addTree(start.getTree());
            tw.setRecursive(true);
            RenameDetector rd = new RenameDetector(repository);
            rd.addAll(DiffEntry.scan(tw));
            List<DiffEntry> files = rd.compute();

            for (DiffEntry diffEntry : files) {
                if ((diffEntry.getChangeType() == DiffEntry.ChangeType.RENAME
                        || diffEntry.getChangeType() == DiffEntry.ChangeType.COPY)
                        && diffEntry.getNewPath().contains(path)) {
                    return diffEntry.getOldPath();
                }
            }
        }

        return null;
    }

    public void extractFile(Path target, RevCommit commit, EagleFile eagleFile) throws IOException {
        String path = eagleFile.getRepoFile().toString().replace("\\", "/");

        if (!Files.exists(target.getParent())) {
            Files.createDirectories(target.getParent());
        }

        RevTree tree = commit.getTree();
        TreeWalk treewalk;

        if (eagleFile.getRenames().containsKey(commit)) {
            treewalk = TreeWalk.forPath(repository, eagleFile.getRenames().get(commit), tree);
        } else {
            treewalk = TreeWalk.forPath(repository, path, tree);
        }

        if (treewalk != null) {
            try (FileOutputStream fos = new FileOutputStream(target.toFile())) {
                fos.write(repository.open(treewalk.getObjectId(0)).getBytes());
            }
        } else {
            System.out.println(path + " not found!");
        }
    }
}
