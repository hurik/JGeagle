package de.andreasgiemza.jgeagle.options;

import de.andreasgiemza.jgeagle.JGeagle;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;

/**
 *
 * @author hurik
 */
public class Options {

    private final Path optionsDir = Paths.get(System.getProperty("user.home")).resolve(".JGeagle");
    private final Path tempDir = optionsDir.resolve("temp");
    private final Path reposDir = optionsDir.resolve("repos");
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

            if (!Files.exists(reposDir)) {
                Files.createDirectories(reposDir);
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
        try {
            Files.walkFileTree(tempDir, new ClearDirectory());
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Path getTempDir() {
        return tempDir;
    }

    public Path getReposDir() {
        return reposDir;
    }

    public Path getCountSheetsUlp() {
        return countSheetsUlp;
    }

    public Properties getProperties() {
        return properties;
    }

    public void showOptionsPanel(JGeagle jGeagle) {
        JDialog dialog = new JDialog(jGeagle, "Options", true);
        dialog.setResizable(false);
        dialog.getContentPane().add(new OptionsPanel(dialog, this));
        dialog.pack();
        dialog.setLocation(
                new Double((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (dialog.getWidth() / 2)).intValue(),
                new Double((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (dialog.getHeight() / 2)).intValue());
        dialog.setVisible(true);
    }

    // Properties getter  
    public String getPropEagleBinary() {
        return properties.getProperty(Options.EAGLE_BINARY);
    }

    public String getPropSchematicDpi() {
        return properties.getProperty(Options.SCHEMATIC_DPI);
    }

    public String getPropBoardDpi() {
        return properties.getProperty(Options.BOARD_DPI);
    }

    public String getPropAddedElementColor() {
        return properties.getProperty(Options.ADDED_ELEMENTS_COLOR);
    }

    public String getPropRemovedElementColor() {
        return properties.getProperty(Options.REMOVED_ELEMENT_COLOR);
    }

    public String getPropUndefinedColor() {
        return properties.getProperty(Options.UNDEFINED_COLOR);
    }

    // Save options
    void save(
            String eagleBinary,
            String schematicDpi,
            String boardDpi,
            String addedElementColor,
            String removedElementColor,
            String undefinedColor) {
        properties.setProperty(EAGLE_BINARY, eagleBinary);
        properties.setProperty(SCHEMATIC_DPI, schematicDpi);
        properties.setProperty(BOARD_DPI, boardDpi);
        properties.setProperty(ADDED_ELEMENTS_COLOR, addedElementColor);
        properties.setProperty(REMOVED_ELEMENT_COLOR, removedElementColor);
        properties.setProperty(UNDEFINED_COLOR, undefinedColor);

        try {
            properties.store(new FileOutputStream(optionsFile.toFile()), null);
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
