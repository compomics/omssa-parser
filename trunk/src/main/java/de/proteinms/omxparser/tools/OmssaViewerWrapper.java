package de.proteinms.omxparser.tools;

import com.compomics.software.CompomicsWrapper;
import java.io.*;

/**
 * A wrapper class used to start the jar file with parameters. The parameters
 * are read from the JavaOptions file.
 *
 * @author Harald Barsnes
 */
public class OmssaViewerWrapper extends CompomicsWrapper {

    /**
     * Starts the launcher by calling the launch method. Use this as the main
     * class in the jar file.
     */
    public OmssaViewerWrapper() {
        this(null);
    }

    /**
     * Starts the launcher by calling the launch method. Use this as the main
     * class in the jar file.
     *
     * @param args the arguments to pass to the tool
     */
    public OmssaViewerWrapper(String[] args) {

        // get the version number set in the pom file
        String jarFileName = "omssa-parser-" + new Properties().getVersion() + ".jar";
        String path = this.getClass().getResource("OmssaViewerWrapper.class").getPath();
        // remove starting 'file:' tag if there
        if (path.startsWith("file:")) {
            path = path.substring("file:".length(), path.indexOf(jarFileName));
        } else {
            path = path.substring(0, path.indexOf(jarFileName));
        }
        path = path.replace("%20", " ");
        path = path.replace("%5b", "[");
        path = path.replace("%5d", "]");
        File jarFile = new File(path, jarFileName);
        String mainClass = "de.proteinms.omxparser.tools.OmssaViewer";

        launchTool("OMSSA Parser", jarFile, null, mainClass, args);
    }

    /**
     * Starts the main application. That's the main method for the jar file.
     *
     * @param args
     */
    public static void main(String[] args) {
        new OmssaViewerWrapper(args);
    }
}
