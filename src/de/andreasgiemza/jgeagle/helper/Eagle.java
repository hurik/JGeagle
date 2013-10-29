package de.andreasgiemza.jgeagle.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Eagle {

    private Eagle() {
    }

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

    public static void extractSheet(
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

    public static void extractBoard(
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
