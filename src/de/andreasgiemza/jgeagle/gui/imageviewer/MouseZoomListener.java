package de.andreasgiemza.jgeagle.gui.imageviewer;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JComponent;
import javax.swing.JViewport;

/**
 *
 * @author hurik
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
