package de.andreasgiemza.jgeagle.helper;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public class DiffImage {

    private DiffImage() {
    }

    public static void make(
            Path oldImageFile,
            Path newImageFile,
            Path diffImageFile,
            boolean background,
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

        int minWidth = Math.min(oldImage.getWidth(),
                newImage.getWidth());
        int maxWidth = Math.max(oldImage.getWidth(),
                newImage.getWidth());

        int minHeight = Math.min(oldImage.getHeight(),
                newImage.getHeight());
        int maxHeight = Math.max(oldImage.getHeight(),
                newImage.getHeight());

        BufferedImage diffImageData = new BufferedImage(
                maxWidth, maxHeight, BufferedImage.TYPE_INT_RGB);

        int backgroundColor;

        if (background) {
            backgroundColor = Color.WHITE.getRGB();
        } else {
            backgroundColor = Color.BLACK.getRGB();
        }

        int addedElementColor
                = Color.decode(addedElementColorHex).getRGB();
        int removedElementColor
                = Color.decode(removedElementColorHex).getRGB();
        int undefinedColor
                = Color.decode(undefinedColorHex).getRGB();

        for (int x = minWidth; x < maxWidth; x++) {
            for (int y = minHeight; y < maxHeight; y++) {
                diffImageData.setRGB(x, y, backgroundColor);
            }
        }

        int backgroundPrecalculation;

        if (background) {
            backgroundPrecalculation = (int) ((1 - alpha) * 255);
        } else {
            backgroundPrecalculation = 0;
        }

        for (int x = 0; x < minWidth; x++) {
            for (int y = 0; y < minHeight; y++) {
                if (oldImage.getRGB(x, y) == newImage.getRGB(x, y)) {
                    Color color = new Color(oldImage.getRGB(x, y));

                    int red = (int) ((color.getRed() * alpha)
                            + backgroundPrecalculation);
                    int green = (int) ((color.getGreen() * alpha)
                            + backgroundPrecalculation);
                    int blue = (int) ((color.getBlue() * alpha)
                            + backgroundPrecalculation);

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
