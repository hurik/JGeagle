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

        Process p = Runtime.getRuntime().exec(
                "\"" + eagleBinary + "\" -C \"RUN " + countSheetsUlp
                + " " + targetSheetCount + "; QUIT\" " + tempSchematic);
        p.waitFor();
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

        Process p = Runtime.getRuntime().exec(
                "\"" + eagleBinary + "\" -C \"EDIT .s" + sheet
                + "; EXPORT IMAGE " + targetSheetImage + " " + dpi
                + "; QUIT\" " + tempSchematic);
        p.waitFor();
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

        Process p = Runtime.getRuntime().exec(
                "\"" + eagleBinary + "\" -C \"EXPORT IMAGE "
                + targetBoardImage + " " + dpi + "; QUIT\" " + tempBoard);
        p.waitFor();
    }
}
