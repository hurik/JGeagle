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

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JComponent;
import javax.swing.JViewport;

/**
 *
 * @author Andreas Giemza
 *
 * Based on http://stackoverflow.com/a/14085161/2246865 by Absolom
 */
public class MouseZoomListener implements MouseWheelListener {

    private final ImageViewerPanel ivp;

    public MouseZoomListener(ImageViewerPanel Imadsfsdfsdfge) {
        this.ivp = Imadsfsdfsdfge;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        JViewport vport = (JViewport) e.getSource();
        JComponent comp = (JComponent) vport.getView();

        Point mp = e.getPoint();
        Point vp = vport.getViewPosition();
        Point newVP;

        if (e.getWheelRotation() < 0) {
            ivp.setScale(ivp.getScale() * 1.1);

            newVP = new Point(
                    (int) ((mp.x + vp.x) * 1.1) - mp.x,
                    (int) ((mp.y + vp.y) * 1.1) - mp.y);
        } else {
            ivp.setScale(ivp.getScale() * 0.9);

            newVP = new Point(
                    (int) ((mp.x + vp.x) * 0.9) - mp.x,
                    (int) ((mp.y + vp.y) * 0.9) - mp.y);
        }

        comp.scrollRectToVisible(new Rectangle(newVP, vport.getSize()));
    }
}
