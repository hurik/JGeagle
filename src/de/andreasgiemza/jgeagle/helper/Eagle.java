package de.andreasgiemza.jgeagle.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            File eagleBinary,
            int sheet,
            File targetSheetImage,
            int dpi,
            File tempSchematic) {
        if (!targetSheetImage.getParentFile().exists()) {
            targetSheetImage.getParentFile().mkdirs();
        }

        try {
            Process p = Runtime.getRuntime().exec(
                    "\"" + eagleBinary + "\" -C \"EDIT .s" + sheet
                    + "; EXPORT IMAGE " + targetSheetImage + " " + dpi
                    + "; QUIT\" " + tempSchematic);
            p.waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Eagle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void extractBoard(
            File eagleBinary,
            File targetBoardImage,
            int dpi,
            File tempBoard) {
        if (!targetBoardImage.getParentFile().exists()) {
            targetBoardImage.getParentFile().mkdirs();
        }

        try {
            Process p = Runtime.getRuntime().exec(
                    "\"" + eagleBinary + "\" -C \"EXPORT IMAGE "
                    + targetBoardImage + " " + dpi + "; QUIT\" " + tempBoard);
            p.waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Eagle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
