package de.andreasgiemza.jgeagle.helper;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Eagle {

    private Eagle() {
    }

    public static void countSheets(
            File eagleBinary,
            File countSheetsUlpFile,
            File targetSheetCount,
            File tempSchematic) {
        if (!targetSheetCount.getParentFile().exists()) {
            targetSheetCount.getParentFile().mkdirs();
        }

        try {
            Process p = Runtime.getRuntime().exec(
                    "\"" + eagleBinary + "\" -C \"RUN " + countSheetsUlpFile
                    + " " + targetSheetCount + "; QUIT\" " + tempSchematic);
            p.waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Eagle.class.getName()).log(Level.SEVERE, null, ex);
        }
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
