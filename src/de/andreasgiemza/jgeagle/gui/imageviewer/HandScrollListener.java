package de.andreasgiemza.jgeagle.gui.imageviewer;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JViewport;

/**
 *
 * @author hurik
 *
 * Based on http://stackoverflow.com/a/13344337/2246865 by aterai
 */
public class HandScrollListener extends MouseAdapter {

    private final Point pp = new Point();

    @Override
    public void mouseDragged(MouseEvent e) {
        JViewport vport = (JViewport) e.getSource();
        JComponent comp = (JComponent) vport.getView();
        Point cp = e.getPoint();
        Point vp = vport.getViewPosition();
        vp.translate(pp.x - cp.x, pp.y - cp.y);
        comp.scrollRectToVisible(new Rectangle(vp, vport.getSize()));
        pp.setLocation(cp);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pp.setLocation(e.getPoint());
    }
}
