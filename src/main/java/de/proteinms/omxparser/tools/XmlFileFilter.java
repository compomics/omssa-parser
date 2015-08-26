package de.proteinms.omxparser.tools;

import java.io.File;
import javax.swing.filechooser.*;

/**
 * File filter for *.xml files.
 * 
 * @author  Harald Barsnes
 */
public class XmlFileFilter extends FileFilter {

    /**
     * Accept all directories, *.xml files.
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
            if (extension.equals(FileFilterUtils.xml) ||
                    extension.equals(FileFilterUtils.XML)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * The description of the filter.
     *
     * @return the description of the filter
     */
    public java.lang.String getDescription() {
        return "XML file (*.xml)";
    }
}
