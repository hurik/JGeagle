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

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 *
 * @author Andreas Giemza
 */
public class DiffImage {

    private DiffImage() {
    }

    /**
     *
     * @param oldImageFile
     * @param newImageFile
     * @param diffImageFile
     * @param backgroundColorHex
     * @param alpha
     * @param addedElementColorHex
     * @param removedElementColorHex
     * @param undefinedColorHex
     * @throws IOException
     */
    public static void create(
            Path oldImageFile,
            Path newImageFile,
            Path diffImageFile,
            String backgroundColorHex,
            double alpha,
            String addedElementColorHex,
            String removedElementColorHex,
            String undefinedColorHex)
            throws IOException {
        if (!Files.exists(diffImageFile)) {
            Files.createDirectories(diffImageFile.getParent());
        }

        BufferedImage oldImage = ImageIO.read(oldImageFile.toFile());
        BufferedImage newImage = ImageIO.read(newImageFile.toFile());

        int minWidth = Math.min(oldImage.getWidth(), newImage.getWidth());
        int maxWidth = Math.max(oldImage.getWidth(), newImage.getWidth());

        int minHeight = Math.min(oldImage.getHeight(), newImage.getHeight());
        int maxHeight = Math.max(oldImage.getHeight(), newImage.getHeight());

        BufferedImage diffImageData = new BufferedImage(
                maxWidth, maxHeight, BufferedImage.TYPE_INT_RGB);

        Color background = Color.decode(backgroundColorHex);
        
        int backgroundColor = background.getRGB();
        int addedElementColor = Color.decode(addedElementColorHex).getRGB();
        int removedElementColor = Color.decode(removedElementColorHex).getRGB();
        int undefinedColor = Color.decode(undefinedColorHex).getRGB();

        int precalculatedBackgroundRed = (int) ((1 - alpha) * background.getRed());
        int precalculatedBackgroundGreen = (int) ((1 - alpha) * background.getGreen());
        int precalculatedBackgroundBlue = (int) ((1 - alpha) * background.getBlue());

        for (int x = 0; x < minWidth; x++) {
            for (int y = 0; y < minHeight; y++) {
                if (oldImage.getRGB(x, y) == newImage.getRGB(x, y)) {
                    Color color = new Color(oldImage.getRGB(x, y));

                    int red = (int) ((color.getRed() * alpha)
                            + precalculatedBackgroundRed);
                    int green = (int) ((color.getGreen() * alpha)
                            + precalculatedBackgroundGreen);
                    int blue = (int) ((color.getBlue() * alpha)
                            + precalculatedBackgroundBlue);

                    diffImageData.setRGB(
                            x, y,
                            new Color(red, green, blue).getRGB());
                } else if (oldImage.getRGB(x, y) == backgroundColor) {
                    diffImageData.setRGB(x, y, addedElementColor);
                } else if (newImage.getRGB(x, y) == backgroundColor) {
                    diffImageData.setRGB(x, y, removedElementColor);
                } else {
                    diffImageData.setRGB(x, y, undefinedColor);
                }
            }
        }

        ImageIO.write(diffImageData, "png", diffImageFile.toFile());
    }
}
