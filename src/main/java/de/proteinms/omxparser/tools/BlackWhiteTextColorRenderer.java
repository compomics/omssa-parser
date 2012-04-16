package de.proteinms.omxparser.tools;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * A simple renderer that makes sure that text is white when the row is 
 * selected and black when not.
 * 
 * @author Harald Barsnes
 */
public class BlackWhiteTextColorRenderer implements TableCellRenderer {

    /**
     * Default constructor.
     */
    public BlackWhiteTextColorRenderer() {  
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = (JLabel) new DefaultTableCellRenderer().getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        Color bg = label.getBackground();

        // We have to create a new color object because Nimbus returns
        // a color of type DerivedColor, which behaves strange, not sure why.
        label.setBackground(new Color(bg.getRed(), bg.getGreen(), bg.getBlue()));

        String text = (String) value;

        if (text != null) {

            // update the link color depending on if the row is selected or not
            if (isSelected) {
                text = text.replace("<font color=\"black\">", "<font color=\"white\">");
            } else {
                text = text.replace("<font color=\"white\">", "<font color=\"black\">");
            }

            label.setText(text);
        }

        return label;
    }
}
