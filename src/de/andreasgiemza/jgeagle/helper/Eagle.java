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
package de.andreasgiemza.jgeagle.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Andreas Giemza
 */
public class Eagle {

    private Eagle() {
    }

    /**
     *
     * @param eagleBinary
     * @param countSheetsUlp
     * @param targetSheetCount
     * @param tempSchematic
     * @throws IOException
     * @throws InterruptedException
     */
    public static void countSheets(
            Path eagleBinary,
            Path countSheetsUlp,
            Path targetSheetCount,
            Path tempSchematic)
            throws IOException, InterruptedException {
        if (!Files.exists(targetSheetCount)) {
            Files.createDirectories(targetSheetCount.getParent());
        }

        runComman(eagleBinary,
                "RUN '" + countSheetsUlp.toString() + "' '" + targetSheetCount.toString() + "'; QUIT",
                tempSchematic);
    }

    /**
     *
     * @param eagleBinary
     * @param sheet
     * @param targetSheetImage
     * @param dpi
     * @param tempSchematic
     * @throws IOException
     * @throws InterruptedException
     */
    public static void extractSheetImage(
            Path eagleBinary,
            int sheet,
            Path targetSheetImage,
            int dpi,
            Path tempSchematic)
            throws IOException, InterruptedException {
        if (!Files.exists(targetSheetImage)) {
            Files.createDirectories(targetSheetImage.getParent());
        }

        runComman(
                eagleBinary,
                "EDIT .s" + sheet + "; EXPORT IMAGE '" + targetSheetImage.toString() + "' " + dpi + "; QUIT",
                tempSchematic);
    }

    /**
     *
     * @param eagleBinary
     * @param targetBoardImage
     * @param dpi
     * @param tempBoard
     * @throws IOException
     * @throws InterruptedException
     */
    public static void extractBoardImage(
            Path eagleBinary,
            Path targetBoardImage,
            int dpi,
            Path tempBoard)
            throws IOException, InterruptedException {
        if (!Files.exists(targetBoardImage)) {
            Files.createDirectories(targetBoardImage.getParent());
        }

        runComman(
                eagleBinary,
                "EXPORT IMAGE '" + targetBoardImage.toString() + "' " + dpi + "; QUIT",
                tempBoard);
    }

    /**
     *
     * @param eagleBinary
     * @param command
     * @param tempFile
     * @throws IOException
     * @throws InterruptedException
     */
    private static void runComman(
            Path eagleBinary,
            String command,
            Path tempFile)
            throws IOException, InterruptedException {
        Process p = new ProcessBuilder(
                eagleBinary.toString(),
                "-C",
                command,
                tempFile.toString()).start();
        p.waitFor();
    }
}
