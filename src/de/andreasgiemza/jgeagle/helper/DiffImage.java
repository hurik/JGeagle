package de.andreasgiemza.jgeagle.helper;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 *
 * @author hurik
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

        for (int x = minWidth; x < maxWidth; x++) {
            for (int y = minHeight; y < maxHeight; y++) {
                diffImageData.setRGB(x, y, backgroundColor);
            }
        }

        for (int x = 0; x < minWidth; x++) {
            for (int y = 0; y < minHeight; y++) {
                if (oldImage.getRGB(x, y) == newImage.getRGB(x, y)) {
                    Color color = new Color(oldImage.getRGB(x, y));

                    int red = (int) ((color.getRed() * alpha)
                            + (int) ((1 - alpha) * background.getRed()));
                    int green = (int) ((color.getGreen() * alpha)
                            + (int) ((1 - alpha) * background.getGreen()));
                    int blue = (int) ((color.getBlue() * alpha)
                            + (int) ((1 - alpha) * background.getBlue()));

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
