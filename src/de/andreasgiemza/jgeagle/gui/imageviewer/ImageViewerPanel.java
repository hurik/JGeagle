package de.andreasgiemza.jgeagle.gui.imageviewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author hurik
 */
public class ImageViewerPanel extends JPanel {

    private final BufferedImage image;
    private double scale = 1.0;

    public ImageViewerPanel(Color background, Path file) throws IOException {
        setBackground(background);
        image = ImageIO.read(file.toFile());
    }

    @Override
    public Dimension getPreferredSize() {
        int w = (int) (scale * image.getWidth());
        int h = (int) (scale * image.getHeight());
        return new Dimension(w, h);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D grphcs2d = (Graphics2D) grphcs;
        AffineTransform at = AffineTransform.getTranslateInstance(
                (getWidth() - scale * image.getWidth()) / 2,
                (getHeight() - scale * image.getHeight()) / 2);
        at.scale(scale, scale);
        grphcs2d.drawRenderedImage(image, at);
    }

    /**
     * 
     * @return 
     */
    public double getScale() {
        return scale;
    }

    /**
     * 
     * @param scale 
     */
    public void setScale(double scale) {
        if (scale > 0.01) {
            this.scale = scale;
            revalidate();
            repaint();
        }
    }

    /**
     * 
     * @param size 
     */
    public void setFirstSize(Dimension size) {
        double xFactor = size.getWidth() / image.getWidth();
        double yFactor = size.getHeight() / image.getHeight();

        scale = Math.min(xFactor, yFactor);
    }
}
