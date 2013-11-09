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
package de.andreasgiemza.jgeagle.options;

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
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Andreas Giemza
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
    public final static String SCHEMATIC_BACKGROUND = "schematicBackground";
    public final static String BOARD_BACKGROUND = "boardBackground";
    public final static String SCHEMATIC_DPI = "schematicDPI";
    public final static String BOARD_DPI = "boardDPI";
    public final static String UNCHANGED_ALPHA = "unchangedAlpha";
    public final static String ADDED_ELEMENTS_COLOR = "addedElementsColor";
    public final static String REMOVED_ELEMENT_COLOR = "removedElementsColor";
    public final static String UNDEFINED_COLOR = "undefinedColor";
    public final static String FOLLOW_GIT = "followGit";

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
            if (Files.exists(tempDir)) {
                FileUtils.deleteDirectory(tempDir.toFile());
            }
            Files.createDirectories(tempDir);
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

    // Properties getter  
    public String getPropEagleBinary() {
        return properties.getProperty(EAGLE_BINARY);
    }

    public Path getPropEagleBinaryAsPath() {
        return Paths.get(properties.getProperty(EAGLE_BINARY));
    }

    public String getPropSchematicBackground() {
        return properties.getProperty(SCHEMATIC_BACKGROUND);
    }

    public String getPropBoardBackground() {
        return properties.getProperty(BOARD_BACKGROUND);
    }

    public int getPropSchematicDpiAsInt() {
        return Integer.parseInt(properties.getProperty(SCHEMATIC_DPI));
    }

    public int getPropBoardDpiAsInt() {
        return Integer.parseInt(properties.getProperty(BOARD_DPI));
    }

    public double getPropUnchangedAlphaAsDouble() {
        return Double.parseDouble(properties.getProperty(UNCHANGED_ALPHA));
    }

    public String getPropAddedElementColor() {
        return properties.getProperty(ADDED_ELEMENTS_COLOR);
    }

    public String getPropRemovedElementColor() {
        return properties.getProperty(REMOVED_ELEMENT_COLOR);
    }

    public String getPropUndefinedColor() {
        return properties.getProperty(UNDEFINED_COLOR);
    }

    public Boolean getPropFollowGitAsBoolean() {
        return Boolean.parseBoolean(properties.getProperty(FOLLOW_GIT));
    }

    // Save options
    public void save(
            String eagleBinary,
            String schematicBackground,
            String boardBackground,
            String schematicDpi,
            String boardDpi,
            String unchangedAlpha,
            String addedElementColor,
            String removedElementColor,
            String undefinedColor,
            Boolean followGit) {
        properties.setProperty(EAGLE_BINARY, eagleBinary);
        properties.setProperty(SCHEMATIC_BACKGROUND, schematicBackground);
        properties.setProperty(BOARD_BACKGROUND, boardBackground);
        properties.setProperty(SCHEMATIC_DPI, schematicDpi);
        properties.setProperty(BOARD_DPI, boardDpi);
        properties.setProperty(UNCHANGED_ALPHA, unchangedAlpha);
        properties.setProperty(ADDED_ELEMENTS_COLOR, addedElementColor);
        properties.setProperty(REMOVED_ELEMENT_COLOR, removedElementColor);
        properties.setProperty(UNDEFINED_COLOR, undefinedColor);
        properties.setProperty(FOLLOW_GIT, followGit.toString());

        try {
            properties.store(new FileOutputStream(optionsFile.toFile()), null);
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
