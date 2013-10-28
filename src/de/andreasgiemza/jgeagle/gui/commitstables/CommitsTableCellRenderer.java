package de.andreasgiemza.jgeagle.gui.commitstables;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author hurik
 */
public class CommitsTableCellRenderer extends DefaultTableCellRenderer {

    private final SimpleDateFormat f = new SimpleDateFormat("dd.MM.yy' - 'hh:mm:ss");

    public CommitsTableCellRenderer() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        if (o instanceof Date) {
            o = f.format(o);
        }
        return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
    }

}
