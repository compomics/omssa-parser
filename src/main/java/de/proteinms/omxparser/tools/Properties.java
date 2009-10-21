package de.proteinms.omxparser.tools;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class contains the properties that are used during the use of the tool,
 * but that are not stored between each run of the program.
 *
 * @author  Harald Barsnes
 */
public class Properties {

    /**
     * Creates a new empty Properties object.
     */
    public Properties() {
    }

    /**
     * Retrieves the version number set in the pom file.
     *
     * @return the version number of the omssa parser/viewer
     */
    public String getVersion() {

        java.util.Properties p = new java.util.Properties();

        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("omssa-parser.properties");
            p.load( is );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return p.getProperty("omssa-parser.version");
    }
}
