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
package de.andreasgiemza.jgeagle.panels;

import de.andreasgiemza.jgeagle.JGeagle;
import de.andreasgiemza.jgeagle.options.Options;
import de.andreasgiemza.jgeagle.panels.imageviewer.HandScrollListener;
import de.andreasgiemza.jgeagle.panels.imageviewer.MouseZoomListener;
import de.andreasgiemza.jgeagle.repo.data.EagleFile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

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

    /**
     *
     * @param options
     * @param diffImageFile
     * @param eagleFile
     * @param sheet
     * @throws IOException
     */
    public static void showImageViewer(
            Options options,
            Path diffImageFile,
            EagleFile eagleFile,
            String sheet) throws IOException {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("JGeagle - " + eagleFile.getRepoFile() + sheet);
        jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("de/andreasgiemza/jgeagle/gui/icons/jgeagle.png")));
        
        Color background;
        if (eagleFile.getFileExtension().equals(EagleFile.SCH)) {
            background = Color.decode(options.getPropSchematicBackground());
        } else {
            background = Color.decode(options.getPropBoardBackground());
        }
        
        ImageViewerPanel ivp = new ImageViewerPanel(
                background,
                diffImageFile);
        JScrollPane scroll = new JScrollPane(ivp);
        jFrame.getContentPane().add(scroll);

        scroll.removeMouseWheelListener(scroll.getMouseWheelListeners()[0]);

        JViewport vport = scroll.getViewport();
        MouseAdapter ma = new HandScrollListener();
        MouseZoomListener mzl = new MouseZoomListener(ivp);
        vport.addMouseMotionListener(ma);
        vport.addMouseWheelListener(mzl);
        vport.addMouseListener(ma);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setSize(
                new Double(size.getWidth() * 0.8).intValue(),
                new Double(size.getHeight() * 0.8).intValue());
        jFrame.setLocation(
                new Double((size.getWidth() / 2)
                        - (jFrame.getWidth() / 2)).intValue(),
                new Double((size.getHeight() / 2)
                        - (jFrame.getHeight() / 2)).intValue());

        jFrame.setVisible(true);

        ivp.setFirstSize(vport.getSize());
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
