package de.proteinms.omxparser.tools;

import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * A cell renderer for Number values. Formats all numbers under a defined limit
 * using a scientific notation, and all numbers over the limit using a standard
 * notation.
 *
 * @author Harald Barsnes
 */
public class ScientificNumberTableCellRenderer extends DefaultTableCellRenderer {

    /**
     * The scientific notation to be used.
     */
    DecimalFormat decimalFormatterScientific = new DecimalFormat("0.##E0");
    /**
     * The standard notation to be used.
     */
    DecimalFormat decimalFormatterNormal = new DecimalFormat("#.####");
    /**
     * The formatted value.
     */
    String formattedValue;
    /**
     * All number below this value are formatted using the scientific formatter,
     * while all number above are formatted using the normal formatter.
     */
    private static final float upperScientificFormattingLevel = 0.00009f;

    /**
     * Constructor that in addition to calling its super constructor also
     * right-aligns the numbers in the column.
     */
    public ScientificNumberTableCellRenderer() {
        super();
        this.setHorizontalAlignment(JLabel.RIGHT);
    }

    @Override
    public void setValue(Object value) {

        // formats the number using either a scientific or a standard notation
        if (((Number) value).floatValue() > upperScientificFormattingLevel) {
            formattedValue = decimalFormatterNormal.format(value);
        } else {
            formattedValue = decimalFormatterScientific.format(value);
        }

        setText((value == null) ? "" : formattedValue);
    }
}
