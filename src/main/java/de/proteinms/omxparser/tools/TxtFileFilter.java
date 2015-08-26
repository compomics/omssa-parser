package de.proteinms.omxparser.tools;

import java.io.File;
import javax.swing.filechooser.*;

/**
 * File filter for *.txt files.
 * 
 * @author  Harald Barsnes
 */
public class TxtFileFilter extends FileFilter {

    /**
     * Accept all directories, *.txt files.
     *
     * @param f the file
     * @return boolean
     */
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = FileFilterUtils.getExtension(f);
        if (extension != null) {
            if (extension.equals(FileFilterUtils.txt) ||
                    extension.equals(FileFilterUtils.TXT)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * The description of this filter
     *
     * @return the description of the filter
     */
    public java.lang.String getDescription() {
        return "Text (Tab delimited) (*.txt)";
    }
}
