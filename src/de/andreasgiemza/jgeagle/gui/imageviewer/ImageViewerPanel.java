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
 * @author Andreas Giemza
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
