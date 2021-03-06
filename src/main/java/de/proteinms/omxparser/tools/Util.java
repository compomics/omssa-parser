package de.proteinms.omxparser.tools;

/**
 * Makes sure that all writing to the ErrorLog has a uniform appearance.
 * 
 * @author  Harald Barsnes
 */
public final class Util {

    /**
     * Writes the given String to the errorLog. 
     * Adds date and time of entry.
     *
     * @param logEntry the log entry
     */
    public static void writeToErrorLog(String logEntry) {
        System.out.println(
                new java.util.Date(System.currentTimeMillis()).toString() + ": " + logEntry);
    }
}
