package de.andreasgiemza.jgeagle.gui;

import de.andreasgiemza.jgeagle.data.EagleFile;
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

public class ImageViewer {

    private ImageViewer() {
    }

    public static void showImageViewer(Path diffImageFile, EagleFile eagleFile, String sheet) throws IOException {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("JGeagle - " + eagleFile.getRepoFile() + sheet);
        ImageViewerPanel ivp = new ImageViewerPanel(
                eagleFile.getFileExtension().equals(EagleFile.BRD) ? Color.BLACK : Color.WHITE,
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
                new Double((size.getWidth() / 2) - (jFrame.getWidth() / 2)).intValue(),
                new Double((size.getHeight() / 2) - (jFrame.getHeight() / 2)).intValue());
        
        jFrame.setVisible(true);
        
        ivp.setFirstSize(vport.getSize());
    }
}
