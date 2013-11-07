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
package de.andreasgiemza.jgeagle.data;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 *
 * @author Andreas Giemza
 */
public class GetWorkingCopyFiles extends SimpleFileVisitor<Path> {

    private final Path repoDirectory;
    private final List<EagleFile> eagleFiles;

    public GetWorkingCopyFiles(Path repoDirectory, List<EagleFile> eagleFiles) {
        this.repoDirectory = repoDirectory;
        this.eagleFiles = eagleFiles;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes bfa)
            throws IOException {
        if (path.endsWith(".git")) {
            return FileVisitResult.SKIP_SUBTREE;
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes bfa)
            throws IOException {
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
