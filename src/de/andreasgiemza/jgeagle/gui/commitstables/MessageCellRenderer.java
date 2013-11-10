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
package de.andreasgiemza.jgeagle.gui.commitstables;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Andreas Giemza
 */
public class MessageCellRenderer extends DefaultTableCellRenderer {

    public MessageCellRenderer() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof String) {
            setToolTipText("<html>" + SplitString((String) value) + "</html>");
        }

        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
    }

    /**
     * Based on http://www.coderanch.com/t/338400/GUI/java/Formatting-tooltips
     * by Gregg Bolinger
     *
     * @param string
     * @return
     */
    public static String SplitString(String string) {

        StringBuilder buf = new StringBuilder();
        String tempString = string;

        if (string != null) {
            while (tempString.length() > 80) {
                String block = tempString.substring(0, 80);
                int index = block.lastIndexOf(' ');
                if (index < 0) {
                    index = tempString.indexOf(' ');
                }
                if (index >= 0) {
                    buf.append(tempString.substring(0, index)).append("<br \\>");
                }
                tempString = tempString.substring(index + 1);
            }
        } else {
            tempString = " ";
        }

        buf.append(tempString);
        return buf.toString();
    }
}
