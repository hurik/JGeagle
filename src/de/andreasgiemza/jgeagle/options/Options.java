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
    private final Properties properties = new Properties();
    // Constants
    public final static String EAGLE_BINARY = "eagleBinary";
    public final static String SCHEMATIC_BACKGROUND = "schematicBackground";
    public final static String BOARD_BACKGROUND = "boardBackground";
    public final static String SCHEMATIC_DPI = "schematicDPI";
    public final static String UNCHANGED_SCHEMATIC_ALPHA = "unchangedSchematicAlpha";
    public final static String BOARD_DPI = "boardDPI";
    public final static String UNCHANGED_BOARD_ALPHA = "unchangedBoardAlpha";
    public final static String ADDED_ELEMENTS_COLOR = "addedElementsColor";
    public final static String REMOVED_ELEMENT_COLOR = "removedElementsColor";
    public final static String UNDEFINED_COLOR = "undefinedColor";
    public final static String LAYERS_TOP = "layersTop";
    public final static String LAYERS_OTHER = "layersOther";
    public final static String LAYERS_BOTTOM = "layersBottom";
    public final static String PRESET_REPO = "presetRepo";
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

    public Properties getProperties() {
        return properties;
    }

    // Properties getter  
    public String getPropEagleBinary() {
        if (properties.getProperty(EAGLE_BINARY) != null) {
            return properties.getProperty(EAGLE_BINARY);
        } else {
            return "";
        }
    }

    public Path getPropEagleBinaryAsPath() {
        return Paths.get(properties.getProperty(EAGLE_BINARY));
    }

    public String getPropSchematicBackground() {
        if (properties.getProperty(SCHEMATIC_BACKGROUND) != null) {
            return properties.getProperty(SCHEMATIC_BACKGROUND);
        } else {
            return "#FFFFFF";
        }
    }

    public String getPropBoardBackground() {
        if (properties.getProperty(BOARD_BACKGROUND) != null) {
            return properties.getProperty(BOARD_BACKGROUND);
        } else {
            return "#000000";
        }
    }

    public int getPropSchematicDpiAsInt() {
        if (properties.getProperty(SCHEMATIC_DPI) != null) {
            return Integer.parseInt(properties.getProperty(SCHEMATIC_DPI));
        } else {
            return 150;
        }
    }

    public double getPropUnchangedSchematicAlphaAsDouble() {
        if (properties.getProperty(UNCHANGED_SCHEMATIC_ALPHA) != null) {
            return Double.parseDouble(properties.getProperty(UNCHANGED_SCHEMATIC_ALPHA));
        } else {
            return 0.3;
        }
    }

    public int getPropBoardDpiAsInt() {
        if (properties.getProperty(BOARD_DPI) != null) {
            return Integer.parseInt(properties.getProperty(BOARD_DPI));
        } else {
            return 300;
        }
    }

    public double getPropUnchangedBoardAlphaAsDouble() {
        if (properties.getProperty(UNCHANGED_BOARD_ALPHA) != null) {
            return Double.parseDouble(properties.getProperty(UNCHANGED_BOARD_ALPHA));
        } else {
            return 0.2;
        }
    }

    public String getPropAddedElementColor() {
        if (properties.getProperty(ADDED_ELEMENTS_COLOR) != null) {
            return properties.getProperty(ADDED_ELEMENTS_COLOR);
        } else {
            return "#008000";
        }
    }

    public String getPropRemovedElementColor() {

        if (properties.getProperty(REMOVED_ELEMENT_COLOR) != null) {
            return properties.getProperty(REMOVED_ELEMENT_COLOR);
        } else {
            return "#FF0000";
        }
    }

    public String getPropUndefinedColor() {
        if (properties.getProperty(UNDEFINED_COLOR) != null) {
            return properties.getProperty(UNDEFINED_COLOR);
        } else {
            return "#808080";
        }
    }

    public String getPropPresetRepo() {
        if (properties.getProperty(PRESET_REPO) != null) {
            return properties.getProperty(PRESET_REPO);
        } else {
            return "";
        }
    }

    public String getPropLayersTop() {
        if (properties.getProperty(LAYERS_TOP) != null) {
            return properties.getProperty(LAYERS_TOP);
        } else {
            return "17 18 19 20 21 23 25 51";
        }
    }

    public String getPropLayersOther() {
        if (properties.getProperty(LAYERS_OTHER) != null) {
            return properties.getProperty(LAYERS_OTHER);
        } else {
            return "17 18 19 20";
        }
    }

    public String getPropLayersBottom() {
        if (properties.getProperty(LAYERS_BOTTOM) != null) {
            return properties.getProperty(LAYERS_BOTTOM);
        } else {
            return "17 18 19 20 22 24 26 52";
        }
    }

    public Boolean getPropFollowGitAsBoolean() {
        if (properties.getProperty(FOLLOW_GIT) != null) {
            return Boolean.parseBoolean(properties.getProperty(FOLLOW_GIT));
        } else {
            return true;
        }
    }

    // Save options
    public void save(
            String eagleBinary,
            String schematicBackground,
            String boardBackground,
            String schematicDpi,
            String unchangedSchematicAlpha,
            String boardDpi,
            String unchangedBoardAlpha,
            String addedElementColor,
            String removedElementColor,
            String undefinedColor,
            String layersTop,
            String layersOther,
            String layersBottom,
            String presetRepo,
            Boolean followGit) {
        properties.clear();

        properties.setProperty(EAGLE_BINARY, eagleBinary);
        properties.setProperty(SCHEMATIC_BACKGROUND, schematicBackground);
        properties.setProperty(BOARD_BACKGROUND, boardBackground);
        properties.setProperty(SCHEMATIC_DPI, schematicDpi);
        properties.setProperty(UNCHANGED_SCHEMATIC_ALPHA, unchangedSchematicAlpha);
        properties.setProperty(BOARD_DPI, boardDpi);
        properties.setProperty(UNCHANGED_BOARD_ALPHA, unchangedBoardAlpha);
        properties.setProperty(ADDED_ELEMENTS_COLOR, addedElementColor);
        properties.setProperty(REMOVED_ELEMENT_COLOR, removedElementColor);
        properties.setProperty(UNDEFINED_COLOR, undefinedColor);
        properties.setProperty(LAYERS_TOP, layersTop);
        properties.setProperty(LAYERS_OTHER, layersOther);
        properties.setProperty(LAYERS_BOTTOM, layersBottom);
        properties.setProperty(PRESET_REPO, presetRepo);
        properties.setProperty(FOLLOW_GIT, followGit.toString());

        try {
            properties.store(new FileOutputStream(optionsFile.toFile()), null);
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
