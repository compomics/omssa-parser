package de.proteinms.omxparser.tools;

import java.io.File;
import javax.swing.ImageIcon;

/**
 * Organizes the file filters.
 * 
 * @author  Harald Barsnes
 */
public class FileFilterUtils {

    public final static String xml = "xml";
    public final static String txt = "txt";
    public final static String omx = "omx";
    public final static String XML = "XML";
    public final static String OMX = "OMX";
    public final static String TXT = "TXT";

    /**
     * Get the extension of a file.
     *
     * @param f the file
     * @return String - the extension of the file f
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     *
     * @param path the path
     * @return ImageIcon
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = FileFilterUtils.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
