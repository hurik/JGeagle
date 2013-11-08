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
package de.andreasgiemza.jgeagle.gui;

import de.andreasgiemza.jgeagle.repo.data.EagleFile;
import de.andreasgiemza.jgeagle.gui.imageviewer.HandScrollListener;
import de.andreasgiemza.jgeagle.gui.imageviewer.ImageViewerPanel;
import de.andreasgiemza.jgeagle.gui.imageviewer.MouseZoomListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.nio.file.Path;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author Andreas Giemza
 */
public class ImageViewer {

    private ImageViewer() {
    }

    /**
     *
     * @param diffImageFile
     * @param eagleFile
     * @param sheet
     * @throws IOException
     */
    public static void showImageViewer(
            Path diffImageFile,
            EagleFile eagleFile,
            String sheet) throws IOException {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("JGeagle - " + eagleFile.getRepoFile() + sheet);
        ImageViewerPanel ivp = new ImageViewerPanel(
                eagleFile.getFileExtension().equals(
                        EagleFile.BRD) ? Color.BLACK : Color.WHITE,
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
}
