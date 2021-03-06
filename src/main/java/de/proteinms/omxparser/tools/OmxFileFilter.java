package de.proteinms.omxparser.tools;

import java.io.File;
import javax.swing.filechooser.*;

/**
 * File filter for *.omx files.
 * 
 * Created March 2008
 * 
 * @author  Harald Barsnes
 */
public class OmxFileFilter extends FileFilter {

    /**
     * Accept all directories, *.omx files.
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
            if (extension.equals(FileFilterUtils.omx) ||
                    extension.equals(FileFilterUtils.OMX)) {
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
     * @return String
     */
    public java.lang.String getDescription() {
        return "OMSSA omx file (*.omx)";
    }
}
