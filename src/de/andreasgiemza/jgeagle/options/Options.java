package de.andreasgiemza.jgeagle.options;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hurik
 */
public class Options {

    private final Path optionsDir = Paths.get(System.getProperty("user.home")).resolve(".JGeagle");
    private final Path tempDir = optionsDir.resolve("temp");
    private final Path optionsFile = optionsDir.resolve("JGeagle.properties");
    private final Path countSheetsUlp = optionsDir.resolve("countSheets.ulp");
    private final Properties properties = new Properties();
    // Constants
    public final static String EAGLE_BINARY = "eagleBinary";
    public final static String SCHEMATIC_DPI = "schematicDPI";
    public final static String BOARD_DPI = "boardDPI";
    public final static String ADDED_ELEMENTS_COLOR = "addedElementsColor";
    public final static String REMOVED_ELEMENT_COLOR = "removedElementsColor";
    public final static String UNDEFINED_COLOR = "undefinedColor";

    public Options() {
        try {
            if (!Files.exists(tempDir)) {
                Files.createDirectories(tempDir);
            }

            if (!Files.exists(optionsFile)) {
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("de/andreasgiemza/jgeagle/resources/JGeagle.properties");
                Files.copy(inputStream, optionsFile);
            }

            if (!Files.exists(countSheetsUlp)) {
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("de/andreasgiemza/jgeagle/resources/countSheets.ulp");
                Files.copy(inputStream, countSheetsUlp);
            }

            properties.load(new FileInputStream(optionsFile.toFile()));
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cleanTempDir() {
        ClearDirectory pf = new ClearDirectory();
        try {
            Files.walkFileTree(tempDir, pf);
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Path getOptionsDir() {
        return optionsDir;
    }

    public Path getTempDir() {
        return tempDir;
    }

    public Path getOptionsFile() {
        return optionsFile;
    }

    public Path getCountSheetsUlp() {
        return countSheetsUlp;
    }

    public Properties getProperties() {
        return properties;
    }
}
