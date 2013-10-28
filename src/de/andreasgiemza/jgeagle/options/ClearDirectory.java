package de.andreasgiemza.jgeagle.options;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author hurik
 */
public class ClearDirectory extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes bfa) throws IOException {
        Files.delete(file);
        return FileVisitResult.CONTINUE;
    }
}
