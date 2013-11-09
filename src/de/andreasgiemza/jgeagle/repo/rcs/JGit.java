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
package de.andreasgiemza.jgeagle.repo.rcs;

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
 * @author Andreas Giemza
 */
public class JGit {

    private final Repository repository;
    private final Git git;

    /**
     *
     * @param directory
     * @throws IOException
     * @throws GitAPIException
     */
    public JGit(Path directory) throws IOException, GitAPIException {
        repository = new FileRepository(directory.resolve(".git").toFile());
        git = new Git(repository);
    }

    /**
     *
     * @param repoFile
     * @return
     * @throws GitAPIException
     */
    public Boolean checkForWorkingCopyChanges(Path repoFile)
            throws GitAPIException {
        Set<String> modifiedFiles = git.status().call().getModified();

        return modifiedFiles.contains(repoFile.toString().replace("\\", "/"));
    }

    /**
     *
     * Based on http://stackoverflow.com/a/11504177/2246865 by OneWorld
     *
     * @param repoFile
     * @param follow
     * @param commits
     * @param renames
     * @return
     * @throws java.io.IOException
     * @throws org.eclipse.jgit.errors.MissingObjectException
     * @throws org.eclipse.jgit.api.errors.GitAPIException
     */
    public List<RevCommit> getFileHistory(
            boolean follow,
            Path repoFile,
            List<RevCommit> commits,
            Map<RevCommit, String> renames)
            throws IOException, MissingObjectException, GitAPIException {
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
        } while ((path = getRenamedPath(follow, start, path)) != null);

        return commits;
    }

    /**
     *
     * Based on http://stackoverflow.com/a/11504177/2246865 by OneWorld
     *
     * @param follow
     * @param start
     * @param path
     * @return
     * @throws IOException
     * @throws GitAPIException
     */
    private String getRenamedPath(
            boolean follow,
            RevCommit start,
            String path)
            throws IOException, GitAPIException {
        if (!follow) {
            return null;
        }

        Iterable<RevCommit> allCommitsLater = git.log().add(start).call();

        for (RevCommit commit : allCommitsLater) {
            TreeWalk tw = new TreeWalk(repository);
            tw.addTree(commit.getTree());
            tw.addTree(start.getTree());
            tw.setRecursive(true);
            RenameDetector rd = new RenameDetector(repository);
            rd.addAll(DiffEntry.scan(tw));
            List<DiffEntry> files = rd.compute();

            for (DiffEntry deffE : files) {
                if ((deffE.getChangeType() == DiffEntry.ChangeType.RENAME
                        || deffE.getChangeType() == DiffEntry.ChangeType.COPY)
                        && deffE.getNewPath().contains(path)) {
                    return deffE.getOldPath();
                }
            }
        }

        return null;
    }

    /**
     *
     * Based on http://stackoverflow.com/a/14856330/2246865 by creinig
     *
     * @param target
     * @param commit
     * @param repoFile
     * @param renames
     * @throws java.io.IOException
     */
    public void extractFile(
            Path target,
            RevCommit commit,
            Path repoFile,
            Map<RevCommit, String> renames)
            throws IOException {
        String path = repoFile.toString().replace("\\", "/");

        if (!Files.exists(target.getParent())) {
            Files.createDirectories(target.getParent());
        }

        RevTree tree = commit.getTree();
        TreeWalk treewalk;

        if (renames.containsKey(commit)) {
            treewalk = TreeWalk.forPath(
                    repository,
                    renames.get(commit),
                    tree);
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
