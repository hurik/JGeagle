package de.andreasgiemza.jgeagle.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author hurik
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
