package de.andreasgiemza.jgeagle.helper;

import de.andreasgiemza.jgeagle.data.EagleFile;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageViewer extends JPanel {

    private final BufferedImage image;
    private double scale = 1.0;

    public ImageViewer(Path diffImageFile, EagleFile eagleFile, String string) throws IOException {
        image = ImageIO.read(diffImageFile.toFile());

        setBackground(eagleFile.getFileExtension().equals(EagleFile.BRD) ? Color.BLACK : Color.WHITE);

        JFrame jFrame = new JFrame();
        jFrame.setTitle("JGeagle - " + eagleFile.getRepoFile() + string);
        JScrollPane scroll = new JScrollPane(this);
        jFrame.getContentPane().add(scroll);
        scroll.removeMouseWheelListener(scroll.getMouseWheelListeners()[0]);
        MouseZoomlListener mwl = new MouseZoomlListener();
        scroll.addMouseWheelListener(mwl);
        JViewport vport = scroll.getViewport();
        MouseAdapter ma = new HandScrollListener();
        vport.addMouseMotionListener(ma);
        vport.addMouseListener(ma);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setSize(
                new Double(size.getWidth() * 0.8).intValue(),
                new Double(size.getHeight() * 0.8).intValue());
        jFrame.setLocation(
                new Double((size.getWidth() / 2) - (jFrame.getWidth() / 2)).intValue(),
                new Double((size.getHeight() / 2) - (jFrame.getHeight() / 2)).intValue());
        jFrame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        int w = getWidth();
        int h = getHeight();
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        double x = (w - scale * imageWidth) / 2;
        double y = (h - scale * imageHeight) / 2;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.scale(scale, scale);
        g2.drawRenderedImage(image, at);
    }

    /**
     * For the scroll pane.
     *
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        int w = (int) (scale * image.getWidth());
        int h = (int) (scale * image.getHeight());
        return new Dimension(w, h);
    }

    public void setScale(double s) {
        scale = s;
        revalidate();      // update the scroll pane  
        repaint();
    }

    public class HandScrollListener extends MouseAdapter {

        private final Point pp = new Point();

        @Override
        public void mouseDragged(MouseEvent e) {
            JViewport vport = (JViewport) e.getSource();
            JComponent label = (JComponent) vport.getView();
            Point cp = e.getPoint();
            Point vp = vport.getViewPosition();
            vp.translate(pp.x - cp.x, pp.y - cp.y);
            label.scrollRectToVisible(new Rectangle(vp, vport.getSize()));
            //vport.setViewPosition(vp);
            pp.setLocation(cp);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            pp.setLocation(e.getPoint());
        }
    }

    public class MouseZoomlListener implements MouseWheelListener {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.getWheelRotation() < 0) {
                setScale(scale + 0.1);
            } else {
                setScale(scale - 0.1);
            }
        }
    }
}
