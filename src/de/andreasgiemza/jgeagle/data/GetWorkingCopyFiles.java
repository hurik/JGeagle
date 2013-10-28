package de.andreasgiemza.jgeagle.data;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 *
 * @author hurik
 */
public class GetWorkingCopyFiles extends SimpleFileVisitor<Path> {

    private final Path repoDirectory;
    private final List<EagleFile> eagleFiles;
    
    public GetWorkingCopyFiles(Path repoDirectory, List<EagleFile> eagleFiles) {
        this.repoDirectory = repoDirectory;
        this.eagleFiles = eagleFiles;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes bfa) throws IOException {
        if (path.endsWith(".git")) {
            return FileVisitResult.SKIP_SUBTREE;
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes bfa) throws IOException {
        if (file.getFileName().toString().toLowerCase().endsWith(".brd")
                || file.getFileName().toString().toLowerCase().endsWith(".sch")) {
            Path repoFile = file.subpath(
                    repoDirectory.getNameCount(),
                    file.getNameCount());
            
            eagleFiles.add(new EagleFile(file, repoFile));
        }

        return FileVisitResult.CONTINUE;
    }
}
