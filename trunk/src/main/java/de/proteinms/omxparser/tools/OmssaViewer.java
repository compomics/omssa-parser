package de.proteinms.omxparser.tools;

import com.compomics.util.gui.spectrum.DefaultSpectrumAnnotation;
import com.compomics.util.gui.spectrum.SpectrumPanel;
import com.compomics.util.protein.Header;
import de.proteinms.omxparser.OmssaOmxFile;
import de.proteinms.omxparser.util.MSHitSet;
import de.proteinms.omxparser.util.MSHits;
import de.proteinms.omxparser.util.MSMZHit;
import de.proteinms.omxparser.util.MSModHit;
import de.proteinms.omxparser.util.MSPepHit;
import de.proteinms.omxparser.util.MSSpectrum;
import de.proteinms.omxparser.util.OmssaModification;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import no.uib.jsparklines.extra.NimbusCheckBoxRenderer;

/**
 * A simply viewer for OMMSA omx files to show how the omssa-parser library can
 * be used.
 *
 * @author Harald Barsnes
 *
 * Created December 2008.
 */
public class OmssaViewer extends javax.swing.JFrame {

    private OmssaOmxFile omssaOmxFile;
    private ProgressDialog progressDialog;
    private SpectrumPanel spectrumPanel;
    private String omxFile, modsFile, userModsFile;
    private HashMap<Integer, ArrayList<Double>> allMzValues;
    private HashMap<Integer, ArrayList<Double>> allAbundanceValues;
    private HashMap<MSSpectrum, MSHitSet> spectrumHitSetMap;
    private HashMap<Integer, MSSpectrum> spectra;
    private Vector spectraJTableColumnToolTips;
    private Vector spectrumJTableColumnToolTips;
    private Vector identificationsJTableColumnToolTips;
    private HashMap<String, Vector<DefaultSpectrumAnnotation>> allAnnotations;
    /**
     * The hardcoded mass of a hydrogen atom.
     */
    public final double HYDROGEN_MASS = 1.00794;
    /**
     * The MSSearchSettings_msmstol used in the omx file
     */
    private double ionCoverageErrorMargin;
    /**
     * The MSResponse_scale used in the omx file.
     */
    private int omssaResponseScale;
    /**
     * The list of ionstypes used in the omx file.
     */
    private List<Integer> usedIonTypes;
    /**
     * The ion coverage legend shown at the bottom of OMSSA Viewer.
     */
    private String ionCoverageLegend = "Ion Coverage: b-ions underlined, y-ions red font";
    /**
     * The last folder opened by the user. Defaults to user.home.
     */
    private String lastSelectedFolder = "user.home";
    /**
     * If set to true all the output that is normally sent to the terminal will
     * be sent to a file called ErrorLog.txt in the Properties folder.
     */
    private static boolean useErrorLog = true;

    /**
     * First checks if a newer version of the omssa-parser is available, then
     * creates an error log file (if useErrorLog == true) and finally opens the
     * OmssaViewerFileSelection dialog.
     *
     * @param args
     */
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                try {
                    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (Exception e) {
                    // ignore error, use look and feel below
                }

                // check if a newer version of the omssa-parser is available
                try {

                    boolean deprecatedOrDeleted = false;

                    URL downloadPage = new URL(
                            "http://code.google.com/p/omssa-parser/downloads/detail?name=omssa-parser-"
                            + new Properties().getVersion() + ".zip");

                    int respons = ((java.net.HttpURLConnection) downloadPage.openConnection()).getResponseCode();

                    // 404 means that the file no longer exists, which means that
                    // the running version is no longer available for download,
                    // which again means that a never version is available.
                    if (respons == 404) {
                        deprecatedOrDeleted = true;
                        //JOptionPane.showMessageDialog(null, "Deprecated!!!!");
                    } else {

                        // also need to check if the available running version has been
                        // deprecated (but not deleted)
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(downloadPage.openStream()));

                        String inputLine = in.readLine();

                        while (inputLine != null && !deprecatedOrDeleted) {

                            //JOptionPane.showMessageDialog(null, inputLine);

                            if (inputLine.lastIndexOf("Deprecated") != -1
                                    && inputLine.lastIndexOf("Deprecated Downloads") == -1
                                    && inputLine.lastIndexOf("Deprecated downloads") == -1) {
                                deprecatedOrDeleted = true;
                                //JOptionPane.showMessageDialog(null, "Deprecated 2!!!!);
                            }

                            inputLine = in.readLine();
                        }

                        in.close();
                    }

                    if (deprecatedOrDeleted) {
                        int option = JOptionPane.showConfirmDialog(null,
                                "A newer version of OMSSA Parser is available.\n"
                                + "Do you want to upgrade?",
                                "OMSSA Parser - Upgrade Available",
                                JOptionPane.YES_NO_CANCEL_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            BareBonesBrowserLaunch.openURL("http://code.google.com/p/omssa-parser");
                            System.exit(0);
                        } else if (option == JOptionPane.CANCEL_OPTION) {
                            System.exit(0);
                        }
                    }
                } catch (MalformedURLException e) {
                    //e.printStackTrace();
                } catch (IOException e) {
                    //e.printStackTrace();
                }

                new OmssaViewerFileSelection(new OmssaViewer(), false, null, null, null, "user.home");
            }
        });
    }

    /**
     * Returns the path to the jar file.
     *
     * @return the path to the jar file
     */
    public String getJarFilePath() {
        String path = this.getClass().getResource("OmssaViewer.class").getPath();

        if (path.lastIndexOf("/omssa-parser-") != -1) {
            // remove starting 'file:' tag if there
            if (path.startsWith("file:")) {
                path = path.substring("file:".length(), path.lastIndexOf("/omssa-parser-"));
            } else {
                path = path.substring(0, path.lastIndexOf("/omssa-parser-"));
            }
            path = path.replace("%20", " ");
            path = path.replace("%5b", "[");
            path = path.replace("%5d", "]");
        } else {
            path = ".";
        }

        return path;
    }

    /**
     * Set up the error log.
     */
    private void setUpErrorLog() {

        // creates the error log file
        if (useErrorLog && !getJarFilePath().equalsIgnoreCase(".")) {
            try {
                String path = getJarFilePath() + "/Properties/ErrorLog.txt";
                File file = new File(path);
                System.setOut(new java.io.PrintStream(new FileOutputStream(file, true)));
                System.setErr(new java.io.PrintStream(new FileOutputStream(file, true)));

                // creates a new error log file if it does not exist
                if (!file.exists()) {
                    file.createNewFile();

                    FileWriter w = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(w);

                    bw.close();
                    w.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "An error occured when creating the ErrorLog.\n"
                        + e.getMessage(),
                        "Error Creating ErrorLog",
                        JOptionPane.ERROR_MESSAGE);
                System.out.println("Error when creating ErrorLog: ");
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates an empty non-visible OmssaViewer frame.
     */
    public OmssaViewer() {

        // creates the error log file
        setUpErrorLog();

        initComponents();

        setMinimumSize(new java.awt.Dimension(900, 600));

        // sets the frames icon image
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().
                getResource("/de/proteinms/omxparser/icons/omssaviewer.GIF")));

        // use scientific notation for the P- and E-values in the identification table
        identificationsJTable.setDefaultRenderer(Float.class, new ScientificNumberTableCellRenderer());
        
        identificationsJTable.getColumn("Modified Sequence").setCellRenderer(new BlackWhiteTextColorRenderer());
        spectraJTable.getColumn("Identified").setCellRenderer(new NimbusCheckBoxRenderer());

        // make sure that the scroll panes are see-through
        spectraJScrollPane.getViewport().setOpaque(false);
        spectrumJScrollPane.getViewport().setOpaque(false);
        identificationsJScrollPane.getViewport().setOpaque(false);

        spectraJTable.setAutoCreateRowSorter(true);
        spectrumJTable.setAutoCreateRowSorter(true);
        identificationsJTable.setAutoCreateRowSorter(true);

        // sets the column sizes
        spectraJTable.getColumn(" ").setMaxWidth(45);
        spectraJTable.getColumn(" ").setMinWidth(45);
        spectraJTable.getColumn("m/z").setMaxWidth(65);
        spectraJTable.getColumn("m/z").setMinWidth(65);
        spectraJTable.getColumn("Charge").setMaxWidth(65);
        spectraJTable.getColumn("Charge").setMinWidth(65);
        spectraJTable.getColumn("Identified").setMaxWidth(80);
        spectraJTable.getColumn("Identified").setMinWidth(80);

        spectrumJTable.getColumn(" ").setMaxWidth(35);
        spectrumJTable.getColumn(" ").setMinWidth(35);

        identificationsJTable.getColumn(" ").setMaxWidth(45);
        identificationsJTable.getColumn(" ").setMinWidth(45);
        identificationsJTable.getColumn("Start").setMaxWidth(45);
        identificationsJTable.getColumn("Start").setMinWidth(45);
        identificationsJTable.getColumn("End").setMaxWidth(45);
        identificationsJTable.getColumn("End").setMinWidth(45);
        identificationsJTable.getColumn("Exp. Mass").setMaxWidth(75);
        identificationsJTable.getColumn("Exp. Mass").setMinWidth(75);
        identificationsJTable.getColumn("Theo. Mass").setMaxWidth(75);
        identificationsJTable.getColumn("Theo. Mass").setMinWidth(75);
        identificationsJTable.getColumn("E-value").setMinWidth(75);
        identificationsJTable.getColumn("E-value").setMaxWidth(75);
        identificationsJTable.getColumn("P-value").setMinWidth(75);
        identificationsJTable.getColumn("P-value").setMaxWidth(75);
        identificationsJTable.getColumn("Accession").setPreferredWidth(10);

        // disables column reordering
        spectraJTable.getTableHeader().setReorderingAllowed(false);
        spectrumJTable.getTableHeader().setReorderingAllowed(false);
        identificationsJTable.getTableHeader().setReorderingAllowed(false);

        // adds column header tooltips
        spectraJTableColumnToolTips = new Vector();
        spectraJTableColumnToolTips.add("Spectrum Number");
        spectraJTableColumnToolTips.add("Spectrum File Name");
        spectraJTableColumnToolTips.add("Precursor Mass Over Charge Ratio");
        spectraJTableColumnToolTips.add("Precursor Charge");
        spectraJTableColumnToolTips.add("Spectrum Identified");

        spectrumJTableColumnToolTips = new Vector();
        spectrumJTableColumnToolTips.add(null);
        spectrumJTableColumnToolTips.add("Mass Over Charge Ratio");
        spectrumJTableColumnToolTips.add("Abundance");

        identificationsJTableColumnToolTips = new Vector();
        identificationsJTableColumnToolTips.add("Spectrum Number");
        identificationsJTableColumnToolTips.add("Peptide Sequence");
        identificationsJTableColumnToolTips.add("Modified Peptide Sequence");
        identificationsJTableColumnToolTips.add("Peptide Start Index");
        identificationsJTableColumnToolTips.add("Peptide End Index");
        identificationsJTableColumnToolTips.add("Experimental Mass");
        identificationsJTableColumnToolTips.add("Theoretical Mass");
        identificationsJTableColumnToolTips.add("E-value");
        identificationsJTableColumnToolTips.add("P-value");
        identificationsJTableColumnToolTips.add("Protein Accession Number");
        identificationsJTableColumnToolTips.add("Protein Description");

        setLocationRelativeTo(null);
    }

    /**
     * Creates new OmssaViewer frame, makes it visible and starts parsing the
     * input files.
     *
     * @param aOmxFile the OMSSA omx file to parse
     * @param aModsFile the mods.xml file
     * @param aUserModsFile the usermods.xml file
     * @param lastSelectedFolder the last selected folder
     */
    public OmssaViewer(String aOmxFile, String aModsFile, String aUserModsFile, String lastSelectedFolder) {

        setUpErrorLog();

        initComponents();

        setMinimumSize(new java.awt.Dimension(900, 600));

        // sets the frames icon image
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().
                getResource("/de/proteinms/omxparser/icons/omssaviewer.GIF")));

        // use scientific notation for the P- and E-values in the identification table
        identificationsJTable.setDefaultRenderer(Float.class, new ScientificNumberTableCellRenderer());

        // sets the column sizes
        spectraJTable.getColumn(" ").setMaxWidth(35);
        spectraJTable.getColumn(" ").setMinWidth(35);
        spectraJTable.getColumn("m/z").setMaxWidth(65);
        spectraJTable.getColumn("m/z").setMinWidth(65);
        spectraJTable.getColumn("Charge").setMaxWidth(65);
        spectraJTable.getColumn("Charge").setMinWidth(65);
        spectraJTable.getColumn("Identified").setMaxWidth(80);
        spectraJTable.getColumn("Identified").setMinWidth(80);

        spectrumJTable.getColumn(" ").setMaxWidth(35);
        spectrumJTable.getColumn(" ").setMinWidth(35);

        identificationsJTable.getColumn(" ").setMaxWidth(35);
        identificationsJTable.getColumn(" ").setMinWidth(35);
        identificationsJTable.getColumn("Start").setMaxWidth(45);
        identificationsJTable.getColumn("Start").setMinWidth(45);
        identificationsJTable.getColumn("End").setMaxWidth(45);
        identificationsJTable.getColumn("End").setMinWidth(45);
        identificationsJTable.getColumn("Exp. Mass").setMaxWidth(75);
        identificationsJTable.getColumn("Exp. Mass").setMinWidth(75);
        identificationsJTable.getColumn("Theo. Mass").setMaxWidth(75);
        identificationsJTable.getColumn("Theo. Mass").setMinWidth(75);
        identificationsJTable.getColumn("E-value").setMinWidth(75);
        identificationsJTable.getColumn("E-value").setMaxWidth(75);
        identificationsJTable.getColumn("P-value").setMinWidth(75);
        identificationsJTable.getColumn("P-value").setMaxWidth(75);
        identificationsJTable.getColumn("Accession").setPreferredWidth(10);

        // disables column reordering
        spectraJTable.getTableHeader().setReorderingAllowed(false);
        spectrumJTable.getTableHeader().setReorderingAllowed(false);
        identificationsJTable.getTableHeader().setReorderingAllowed(false);

        spectraJTable.setAutoCreateRowSorter(true);
        spectrumJTable.setAutoCreateRowSorter(true);
        identificationsJTable.setAutoCreateRowSorter(true);

        // adds column header tooltips
        spectraJTableColumnToolTips = new Vector();
        spectraJTableColumnToolTips.add("Spectrum Number");
        spectraJTableColumnToolTips.add("Spectrum File Name");
        spectraJTableColumnToolTips.add("Precursor Mass Over Charge Ratio");
        spectraJTableColumnToolTips.add("Precursor Charge");
        spectraJTableColumnToolTips.add("Spectrum Identified");

        spectrumJTableColumnToolTips = new Vector();
        spectrumJTableColumnToolTips.add(null);
        spectrumJTableColumnToolTips.add("Mass Over Charge Ratio");
        spectrumJTableColumnToolTips.add("Abundance");

        identificationsJTableColumnToolTips = new Vector();
        identificationsJTableColumnToolTips.add("Spectrum Number");
        identificationsJTableColumnToolTips.add("Peptide Sequence");
        identificationsJTableColumnToolTips.add("Modified Peptide Sequence");
        identificationsJTableColumnToolTips.add("Peptide Start Index");
        identificationsJTableColumnToolTips.add("Peptide End Index");
        identificationsJTableColumnToolTips.add("Experimental Mass");
        identificationsJTableColumnToolTips.add("Theoretical Mass");
        identificationsJTableColumnToolTips.add("E-value");
        identificationsJTableColumnToolTips.add("P-value");
        identificationsJTableColumnToolTips.add("Protein Accession Number");
        identificationsJTableColumnToolTips.add("Protein Description");

        setLocationRelativeTo(null);
        setVisible(true);

        insertOmxFile(aOmxFile, aModsFile, aUserModsFile, lastSelectedFolder);
    }

    /**
     * Parses the given omx file (and modification files) and inserts the
     * details into the OMSSA Viewer tables.
     *
     * @param aOmxFile the OMSSA omx file to parse
     * @param aModsFile the mods.xml file
     * @param aUserModsFile the usermods.xml file
     * @param lastSelectedFolder the last selected folder
     */
    public void insertOmxFile(String aOmxFile, String aModsFile, String aUserModsFile, String lastSelectedFolder) {

        setTitle("OMSSA Viewer " + new Properties().getVersion() + "  -  [" + new File(aOmxFile).getPath() + "]");

        this.lastSelectedFolder = lastSelectedFolder;

        omxFile = aOmxFile;
        modsFile = aModsFile;
        userModsFile = aUserModsFile;

        exportSelectedSpectrumJMenuItem.setEnabled(false);

        progressDialog = new ProgressDialog(this, true);

        final Thread t = new Thread(new Runnable() {

            public void run() {
                progressDialog.setTitle("Parsing OMX File. Please Wait...");
                progressDialog.setIntermidiate(true);
                progressDialog.setVisible(true);
            }
        }, "ProgressDialog");

        t.start();


        new Thread("ParserThread") {

            @Override
            public void run() {

                // empty the tables and clear the spectrum panel
                while (((DefaultTableModel) spectraJTable.getModel()).getRowCount() > 0) {
                    ((DefaultTableModel) spectraJTable.getModel()).removeRow(0);
                }

                while (((DefaultTableModel) spectrumJTable.getModel()).getRowCount() > 0) {
                    ((DefaultTableModel) spectrumJTable.getModel()).removeRow(0);
                }

                while (((DefaultTableModel) identificationsJTable.getModel()).getRowCount() > 0) {
                    ((DefaultTableModel) identificationsJTable.getModel()).removeRow(0);
                }

                legendJLabel.setText("");
                ptmsJTextField.setText("");

                while (spectrumJPanel.getComponents().length > 0) {
                    spectrumJPanel.remove(0);
                }

                spectrumJPanel.validate();
                spectrumJPanel.repaint();

                // parses the file
                try {
                    omssaOmxFile = new OmssaOmxFile(omxFile, modsFile, userModsFile);
                } catch (OutOfMemoryError error) {
                    progressDialog.setVisible(false);
                    progressDialog.dispose();
                    Runtime.getRuntime().gc();
                    JOptionPane.showMessageDialog(null,
                            "The task used up all the available memory and had to be stopped.\n"
                            + "Memory boundaries are set in ../Properties/JavaOptions.txt.",
                            "Out Of Memory Error",
                            JOptionPane.ERROR_MESSAGE);
                    Util.writeToErrorLog("OMSSA Viewer: Ran out of memory!");
                    error.printStackTrace();
                    System.exit(0);
                }


                // extract MSSearchSettings_msmstol
                ionCoverageErrorMargin =
                        omssaOmxFile.getParserResult().MSSearch_request.MSRequest.get(0).MSRequest_settings.MSSearchSettings.MSSearchSettings_msmstol;

                // extract MSResponse_scale
                omssaResponseScale =
                        omssaOmxFile.getParserResult().MSSearch_response.MSResponse.get(0).MSResponse_scale;

                // extract the ion types used
                usedIonTypes =
                        omssaOmxFile.getParserResult().MSSearch_request.MSRequest.get(0).MSRequest_settings.MSSearchSettings.MSSearchSettings_ionstosearch.MSIonType;

                // iterate the spectrum to hitset map
                // extract and store details about the spectra
                spectrumHitSetMap = omssaOmxFile.getSpectrumToHitSetMap();
                Iterator<MSSpectrum> iterator = spectrumHitSetMap.keySet().iterator();

                allMzValues = new HashMap<Integer, ArrayList<Double>>();
                allAbundanceValues = new HashMap<Integer, ArrayList<Double>>();
                spectra = new HashMap<Integer, MSSpectrum>();

                // iterate the spectra
                while (iterator.hasNext()) {
                    MSSpectrum tempSpectrum = iterator.next();
                    spectra.put(new Integer(tempSpectrum.MSSpectrum_number), tempSpectrum);
                }

                // add the spectra to the table
                Object[] keys = spectra.keySet().toArray();
                Arrays.sort(keys);

                for (int i = 0; i < keys.length; i++) {
                    MSSpectrum tempSpectrum = spectra.get((Integer) keys[i]);

                    // OMSSA question: possible with more than one file name per spectrum??
                    String fileName;

                    // spectrum name is not mandatory, use spectrum number if no name is given
                    if (tempSpectrum.MSSpectrum_ids.MSSpectrum_ids_E.isEmpty()) {
                        fileName = "" + tempSpectrum.MSSpectrum_number;
                    } else {
                        fileName = tempSpectrum.MSSpectrum_ids.MSSpectrum_ids_E.get(0);
                    }

                    // extract the charge of the precursor
                    String chargeString = "0";

                    // if more than one charge is found the charge is considered as unknown and is set to 0
                    if (tempSpectrum.MSSpectrum_charge.MSSpectrum_charge_E.size() == 1) {
                        chargeString = "" + tempSpectrum.MSSpectrum_charge.MSSpectrum_charge_E.get(0);
                    }

                    chargeString = chargeString.replaceFirst("\\+", "");

                    double omssaAbundanceScale = tempSpectrum.MSSpectrum_iscale;

                    ArrayList<Double> currentRealMzValues = new ArrayList();
                    ArrayList<Double> currentRealAbundanceValues = new ArrayList();

                    List<Integer> currentMzValuesAsIntegers = tempSpectrum.MSSpectrum_mz.MSSpectrum_mz_E;
                    List<Integer> currentAbundanceValuesAsIntegers = tempSpectrum.MSSpectrum_abundance.MSSpectrum_abundance_E;

                    for (int j = 0; j < currentMzValuesAsIntegers.size(); j++) {
                        currentRealMzValues.add(currentMzValuesAsIntegers.get(j).doubleValue() / omssaResponseScale);
                        currentRealAbundanceValues.add(currentAbundanceValuesAsIntegers.get(j).doubleValue() / omssaAbundanceScale);
                    }

                    allMzValues.put(new Integer(tempSpectrum.MSSpectrum_number),
                            currentRealMzValues);
                    allAbundanceValues.put(new Integer(tempSpectrum.MSSpectrum_number),
                            currentRealAbundanceValues);

                    boolean identified = false;

                    MSHitSet msHitSet = spectrumHitSetMap.get(tempSpectrum);
                    List<MSHits> allMSHits = msHitSet.MSHitSet_hits.MSHits;

                    if (allMSHits.size() > 0) {
                        identified = true;
                    }

                    ((DefaultTableModel) spectraJTable.getModel()).addRow(new Object[]{
                                new Integer(tempSpectrum.MSSpectrum_number),
                                fileName,
                                ((double) tempSpectrum.MSSpectrum_precursormz) / omssaResponseScale,
                                new Integer(chargeString),
                                identified
                            });
                }

                // select the first spectra in the table
                if (spectraJTable.getRowCount() > 0) {
                    spectraJTable.setRowSelectionInterval(0, 0);
                    spectraJTableMouseClicked(null);
                }

                progressDialog.setVisible(false);
                progressDialog.dispose();
            }
        }.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        copySpectraJPopupMenu = new javax.swing.JPopupMenu();
        copySpectraJMenuItem = new javax.swing.JMenuItem();
        copySpectrumJPopupMenu = new javax.swing.JPopupMenu();
        copySpectrumJMenuItem = new javax.swing.JMenuItem();
        copyIdentificationsJPopupMenu = new javax.swing.JPopupMenu();
        copyIdentificationsJMenuItem = new javax.swing.JMenuItem();
        accesionDetailsButtonGroup = new javax.swing.ButtonGroup();
        backgroundPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        spectraJScrollPane = new javax.swing.JScrollPane();
        spectraJTable = new JTable() {
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(columnModel) {
                    public String getToolTipText(MouseEvent e) {
                        java.awt.Point p = e.getPoint();
                        int index = columnModel.getColumnIndexAtX(p.x);
                        int realIndex = columnModel.getColumn(index).getModelIndex();
                        return (String) spectraJTableColumnToolTips.get(realIndex);
                    }
                };
            }
        };
        jPanel2 = new javax.swing.JPanel();
        legendJLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        identificationsJScrollPane = new javax.swing.JScrollPane();
        identificationsJTable = new JTable() {
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(columnModel) {
                    public String getToolTipText(MouseEvent e) {
                        java.awt.Point p = e.getPoint();
                        int index = columnModel.getColumnIndexAtX(p.x);
                        int realIndex = columnModel.getColumn(index).getModelIndex();
                        return (String) identificationsJTableColumnToolTips.get(realIndex);
                    }
                };
            }
        };
        jLabel2 = new javax.swing.JLabel();
        ptmsJTextField = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        spectrumJPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        aIonsJCheckBox = new javax.swing.JCheckBox();
        bIonsJCheckBox = new javax.swing.JCheckBox();
        cIonsJCheckBox = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        yIonsJCheckBox = new javax.swing.JCheckBox();
        xIonsJCheckBox = new javax.swing.JCheckBox();
        zIonsJCheckBox = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JSeparator();
        chargeOneJCheckBox = new javax.swing.JCheckBox();
        chargeTwoJCheckBox = new javax.swing.JCheckBox();
        chargeOverTwoJCheckBox = new javax.swing.JCheckBox();
        spectrumJScrollPane = new javax.swing.JScrollPane();
        spectrumJTable = new JTable() {
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(columnModel) {
                    public String getToolTipText(MouseEvent e) {
                        java.awt.Point p = e.getPoint();
                        int index = columnModel.getColumnIndexAtX(p.x);
                        int realIndex = columnModel.getColumn(index).getModelIndex();
                        return (String) spectrumJTableColumnToolTips.get(realIndex);
                    }
                };
            }
        };
        jMenuBar1 = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        openJMenuItem = new javax.swing.JMenuItem();
        exitJMenuItem = new javax.swing.JMenuItem();
        exportJMenu = new javax.swing.JMenu();
        exportSpectraFilesTableJMenuItem = new javax.swing.JMenuItem();
        exportAllIdentificationsJMenuItem = new javax.swing.JMenuItem();
        exportBestIdentificationsJMenuItem = new javax.swing.JMenuItem();
        exportSelectedSpectrumJMenuItem = new javax.swing.JMenuItem();
        exportAllSpectraJMenuItem = new javax.swing.JMenuItem();
        helpJMenu = new javax.swing.JMenu();
        helpJMenuItem = new javax.swing.JMenuItem();
        aboutJMenuItem = new javax.swing.JMenuItem();

        copySpectraJMenuItem.setMnemonic('C');
        copySpectraJMenuItem.setText("Copy");
        copySpectraJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copySpectraJMenuItemActionPerformed(evt);
            }
        });
        copySpectraJPopupMenu.add(copySpectraJMenuItem);

        copySpectrumJMenuItem.setMnemonic('C');
        copySpectrumJMenuItem.setText("Copy");
        copySpectrumJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copySpectrumJMenuItemActionPerformed(evt);
            }
        });
        copySpectrumJPopupMenu.add(copySpectrumJMenuItem);

        copyIdentificationsJMenuItem.setMnemonic('C');
        copyIdentificationsJMenuItem.setText("Copy");
        copyIdentificationsJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyIdentificationsJMenuItemActionPerformed(evt);
            }
        });
        copyIdentificationsJPopupMenu.add(copyIdentificationsJMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OMSSA Viewer v1.0");

        backgroundPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Spectrum Files"));
        jPanel1.setOpaque(false);

        spectraJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                " ", "Filename", "m/z", "Charge", "Identified"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spectraJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spectraJTableMouseClicked(evt);
            }
        });
        spectraJTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                spectraJTableKeyReleased(evt);
            }
        });
        spectraJScrollPane.setViewportView(spectraJTable);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(spectraJScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(spectraJScrollPane)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Identifications"));
        jPanel2.setOpaque(false);

        legendJLabel.setFont(legendJLabel.getFont().deriveFont((legendJLabel.getFont().getStyle() | java.awt.Font.ITALIC)));
        legendJLabel.setText("-");

        jLabel1.setFont(jLabel1.getFont().deriveFont((jLabel1.getFont().getStyle() | java.awt.Font.ITALIC)));
        jLabel1.setText("Legend:   ");

        identificationsJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                " ", "Sequence", "Modified Sequence", "Start", "End", "Exp. Mass", "Theo. Mass", "E-value", "P-value", "Accession", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Float.class, java.lang.Float.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        identificationsJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                identificationsJTableMouseClicked(evt);
            }
        });
        identificationsJTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                identificationsJTableKeyReleased(evt);
            }
        });
        identificationsJScrollPane.setViewportView(identificationsJTable);

        jLabel2.setFont(jLabel2.getFont().deriveFont((jLabel2.getFont().getStyle() | java.awt.Font.ITALIC)));
        jLabel2.setText("PTMs:");

        ptmsJTextField.setEditable(false);
        ptmsJTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        ptmsJTextField.setBorder(null);
        ptmsJTextField.setOpaque(false);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(identificationsJScrollPane)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel1)
                            .add(jLabel2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(legendJLabel)
                                .add(0, 0, Short.MAX_VALUE))
                            .add(ptmsJTextField))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(identificationsJScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(legendJLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(ptmsJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Spectrum"));
        jPanel3.setOpaque(false);

        spectrumJPanel.setOpaque(false);
        spectrumJPanel.setLayout(new javax.swing.BoxLayout(spectrumJPanel, javax.swing.BoxLayout.LINE_AXIS));

        jPanel4.setOpaque(false);

        aIonsJCheckBox.setSelected(true);
        aIonsJCheckBox.setText("a");
        aIonsJCheckBox.setToolTipText("Show a-ions");
        aIonsJCheckBox.setMaximumSize(new java.awt.Dimension(39, 23));
        aIonsJCheckBox.setMinimumSize(new java.awt.Dimension(39, 23));
        aIonsJCheckBox.setOpaque(false);
        aIonsJCheckBox.setPreferredSize(new java.awt.Dimension(39, 23));
        aIonsJCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aIonsJCheckBoxActionPerformed(evt);
            }
        });

        bIonsJCheckBox.setSelected(true);
        bIonsJCheckBox.setText("b");
        bIonsJCheckBox.setToolTipText("Show b-ions");
        bIonsJCheckBox.setMaximumSize(new java.awt.Dimension(39, 23));
        bIonsJCheckBox.setMinimumSize(new java.awt.Dimension(39, 23));
        bIonsJCheckBox.setOpaque(false);
        bIonsJCheckBox.setPreferredSize(new java.awt.Dimension(39, 23));
        bIonsJCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIonsJCheckBoxActionPerformed(evt);
            }
        });

        cIonsJCheckBox.setSelected(true);
        cIonsJCheckBox.setText("c");
        cIonsJCheckBox.setToolTipText("Show c-ions");
        cIonsJCheckBox.setMaximumSize(new java.awt.Dimension(39, 23));
        cIonsJCheckBox.setMinimumSize(new java.awt.Dimension(39, 23));
        cIonsJCheckBox.setOpaque(false);
        cIonsJCheckBox.setPreferredSize(new java.awt.Dimension(39, 23));
        cIonsJCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cIonsJCheckBoxActionPerformed(evt);
            }
        });

        yIonsJCheckBox.setSelected(true);
        yIonsJCheckBox.setText("y");
        yIonsJCheckBox.setToolTipText("Show y-ions");
        yIonsJCheckBox.setMaximumSize(new java.awt.Dimension(39, 23));
        yIonsJCheckBox.setMinimumSize(new java.awt.Dimension(39, 23));
        yIonsJCheckBox.setOpaque(false);
        yIonsJCheckBox.setPreferredSize(new java.awt.Dimension(39, 23));
        yIonsJCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yIonsJCheckBoxActionPerformed(evt);
            }
        });

        xIonsJCheckBox.setSelected(true);
        xIonsJCheckBox.setText("x");
        xIonsJCheckBox.setToolTipText("Show x-ions");
        xIonsJCheckBox.setMaximumSize(new java.awt.Dimension(39, 23));
        xIonsJCheckBox.setMinimumSize(new java.awt.Dimension(39, 23));
        xIonsJCheckBox.setOpaque(false);
        xIonsJCheckBox.setPreferredSize(new java.awt.Dimension(39, 23));
        xIonsJCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xIonsJCheckBoxActionPerformed(evt);
            }
        });

        zIonsJCheckBox.setSelected(true);
        zIonsJCheckBox.setText("z");
        zIonsJCheckBox.setToolTipText("Show z-ions");
        zIonsJCheckBox.setMaximumSize(new java.awt.Dimension(39, 23));
        zIonsJCheckBox.setMinimumSize(new java.awt.Dimension(39, 23));
        zIonsJCheckBox.setOpaque(false);
        zIonsJCheckBox.setPreferredSize(new java.awt.Dimension(39, 23));
        zIonsJCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zIonsJCheckBoxActionPerformed(evt);
            }
        });

        chargeOneJCheckBox.setSelected(true);
        chargeOneJCheckBox.setText("+");
        chargeOneJCheckBox.setToolTipText("Show ions with charge 1");
        chargeOneJCheckBox.setMaximumSize(new java.awt.Dimension(39, 23));
        chargeOneJCheckBox.setMinimumSize(new java.awt.Dimension(39, 23));
        chargeOneJCheckBox.setOpaque(false);
        chargeOneJCheckBox.setPreferredSize(new java.awt.Dimension(39, 23));
        chargeOneJCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chargeOneJCheckBoxActionPerformed(evt);
            }
        });

        chargeTwoJCheckBox.setSelected(true);
        chargeTwoJCheckBox.setText("++");
        chargeTwoJCheckBox.setToolTipText("Show ions with charge 2");
        chargeTwoJCheckBox.setMaximumSize(new java.awt.Dimension(39, 23));
        chargeTwoJCheckBox.setMinimumSize(new java.awt.Dimension(39, 23));
        chargeTwoJCheckBox.setOpaque(false);
        chargeTwoJCheckBox.setPreferredSize(new java.awt.Dimension(39, 23));
        chargeTwoJCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chargeTwoJCheckBoxActionPerformed(evt);
            }
        });

        chargeOverTwoJCheckBox.setSelected(true);
        chargeOverTwoJCheckBox.setText(">2");
        chargeOverTwoJCheckBox.setToolTipText("Show ions with charge >2");
        chargeOverTwoJCheckBox.setOpaque(false);
        chargeOverTwoJCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chargeOverTwoJCheckBoxActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(chargeOverTwoJCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .add(yIonsJCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, zIonsJCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(xIonsJCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(bIonsJCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(aIonsJCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(cIonsJCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(chargeOneJCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, chargeTwoJCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jSeparator2)
                            .add(jSeparator1))
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(aIonsJCheckBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(bIonsJCheckBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cIonsJCheckBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(13, 13, 13)
                .add(xIonsJCheckBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(yIonsJCheckBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(zIonsJCheckBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(12, 12, 12)
                .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(chargeOneJCheckBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(chargeTwoJCheckBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(chargeOverTwoJCheckBox)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        spectrumJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                " ", "m/z", "Abundance"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spectrumJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spectrumJTableMouseClicked(evt);
            }
        });
        spectrumJScrollPane.setViewportView(spectrumJTable);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(spectrumJPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(spectrumJScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(spectrumJPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(spectrumJScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 175, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout backgroundPanelLayout = new org.jdesktop.layout.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(backgroundPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(backgroundPanelLayout.createSequentialGroup()
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(backgroundPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6))
        );

        fileJMenu.setMnemonic('F');
        fileJMenu.setText("File");

        openJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        openJMenuItem.setMnemonic('O');
        openJMenuItem.setText("Open");
        openJMenuItem.setToolTipText("Open a New OMX File");
        openJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(openJMenuItem);

        exitJMenuItem.setMnemonic('x');
        exitJMenuItem.setText("Exit");
        exitJMenuItem.setToolTipText("Exit OMSSA Viewer");
        exitJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(exitJMenuItem);

        jMenuBar1.add(fileJMenu);

        exportJMenu.setMnemonic('E');
        exportJMenu.setText("Export");

        exportSpectraFilesTableJMenuItem.setMnemonic('P');
        exportSpectraFilesTableJMenuItem.setText("Spectra Files Table");
        exportSpectraFilesTableJMenuItem.setToolTipText("Export the Spectra Files Table as Tab Delimited Text File");
        exportSpectraFilesTableJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportSpectraFilesTableJMenuItemActionPerformed(evt);
            }
        });
        exportJMenu.add(exportSpectraFilesTableJMenuItem);

        exportAllIdentificationsJMenuItem.setMnemonic('I');
        exportAllIdentificationsJMenuItem.setText("All Identifications (all hits)");
        exportAllIdentificationsJMenuItem.setToolTipText("Export All Identifications (all hits) as Tab Delimited Text File");
        exportAllIdentificationsJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportAllIdentificationsJMenuItemActionPerformed(evt);
            }
        });
        exportJMenu.add(exportAllIdentificationsJMenuItem);

        exportBestIdentificationsJMenuItem.setMnemonic('I');
        exportBestIdentificationsJMenuItem.setText("All Identifications (best hits only)");
        exportBestIdentificationsJMenuItem.setToolTipText("Export All Identifications (best hits only) as Tab Delimited Text File");
        exportBestIdentificationsJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportBestIdentificationsJMenuItemActionPerformed(evt);
            }
        });
        exportJMenu.add(exportBestIdentificationsJMenuItem);

        exportSelectedSpectrumJMenuItem.setMnemonic('S');
        exportSelectedSpectrumJMenuItem.setText("Selected Spectrum");
        exportSelectedSpectrumJMenuItem.setToolTipText("Export the Selected Spectrum as Tab Delimited Text File");
        exportSelectedSpectrumJMenuItem.setEnabled(false);
        exportSelectedSpectrumJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportSelectedSpectrumJMenuItemActionPerformed(evt);
            }
        });
        exportJMenu.add(exportSelectedSpectrumJMenuItem);

        exportAllSpectraJMenuItem.setMnemonic('S');
        exportAllSpectraJMenuItem.setText("All Spectra");
        exportAllSpectraJMenuItem.setToolTipText("Export all the Spectra as DTA Files");
        exportAllSpectraJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportAllSpectraJMenuItemActionPerformed(evt);
            }
        });
        exportJMenu.add(exportAllSpectraJMenuItem);

        jMenuBar1.add(exportJMenu);

        helpJMenu.setMnemonic('H');
        helpJMenu.setText("Help");

        helpJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        helpJMenuItem.setMnemonic('H');
        helpJMenuItem.setText("Help");
        helpJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpJMenuItemActionPerformed(evt);
            }
        });
        helpJMenu.add(helpJMenuItem);

        aboutJMenuItem.setMnemonic('a');
        aboutJMenuItem.setText("About");
        aboutJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutJMenuItemActionPerformed(evt);
            }
        });
        helpJMenu.add(aboutJMenuItem);

        jMenuBar1.add(helpJMenu);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(backgroundPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(backgroundPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Terminates the program.
     *
     * @param evt
     */
    private void exitJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitJMenuItemActionPerformed

    /**
     * Enables copy to clipboard functionality from a popup menu.
     *
     * @param evt
     */
    private void copySpectraJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copySpectraJMenuItemActionPerformed
        TransferHandler th = spectraJTable.getTransferHandler();
        if (th != null) {
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            th.exportToClipboard(spectraJTable, cb, TransferHandler.COPY);
        }
}//GEN-LAST:event_copySpectraJMenuItemActionPerformed

    /**
     * @see #copySpectraJMenuItemActionPerformed(java.awt.event.ActionEvent)
     */
    private void copySpectrumJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copySpectrumJMenuItemActionPerformed
        TransferHandler th = spectrumJTable.getTransferHandler();
        if (th != null) {
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            th.exportToClipboard(spectrumJTable, cb, TransferHandler.COPY);
        }
}//GEN-LAST:event_copySpectrumJMenuItemActionPerformed

    /**
     * @see #copySpectraJMenuItemActionPerformed(java.awt.event.ActionEvent)
     */
    private void copyIdentificationsJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyIdentificationsJMenuItemActionPerformed
        TransferHandler th = identificationsJTable.getTransferHandler();
        if (th != null) {
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            th.exportToClipboard(identificationsJTable, cb, TransferHandler.COPY);
        }
}//GEN-LAST:event_copyIdentificationsJMenuItemActionPerformed

    /**
     * Filters the annotations and returns the annotations matching the selected
     * list next to the spectrum panel.
     *
     * @param annotations the annotations to be filtered
     * @return the filtered annotations
     */
    private Vector<DefaultSpectrumAnnotation> filterAnnotations(Vector<DefaultSpectrumAnnotation> annotations) {

        Vector<DefaultSpectrumAnnotation> filteredAnnotations = new Vector();

        for (int i = 0; i < annotations.size(); i++) {
            String currentLabel = annotations.get(i).getLabel();

            boolean useAnnotation = true;

            // check ion type
            if (currentLabel.lastIndexOf("a") != -1) {
                if (!aIonsJCheckBox.isSelected()) {
                    useAnnotation = false;
                }
            } else if (currentLabel.lastIndexOf("b") != -1) {
                if (!bIonsJCheckBox.isSelected()) {
                    useAnnotation = false;
                }
            } else if (currentLabel.lastIndexOf("c") != -1) {
                if (!cIonsJCheckBox.isSelected()) {
                    useAnnotation = false;
                }
            } else if (currentLabel.lastIndexOf("x") != -1) {
                if (!xIonsJCheckBox.isSelected()) {
                    useAnnotation = false;
                }
            } else if (currentLabel.lastIndexOf("y") != -1) {
                if (!yIonsJCheckBox.isSelected()) {
                    useAnnotation = false;
                }
            } else if (currentLabel.lastIndexOf("z") != -1) {
                if (!zIonsJCheckBox.isSelected()) {
                    useAnnotation = false;
                }
            }

            // check ion charge
            if (useAnnotation) {
                if (currentLabel.lastIndexOf("+") == -1) {
                    if (!chargeOneJCheckBox.isSelected()) {
                        useAnnotation = false;
                    }
                } else if (currentLabel.lastIndexOf("+++") != -1) {
                    if (!chargeOverTwoJCheckBox.isSelected()) {
                        useAnnotation = false;
                    }
                } else if (currentLabel.lastIndexOf("++") != -1) {
                    if (!chargeTwoJCheckBox.isSelected()) {
                        useAnnotation = false;
                    }
                }
            }

            if (useAnnotation) {
                filteredAnnotations.add(annotations.get(i));
            }
        }

        return filteredAnnotations;
    }

    /**
     * Opens a OmssaViewerFileSelection dialog for opening a different omx file.
     *
     * @param evt
     */
    private void openJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openJMenuItemActionPerformed
        new OmssaViewerFileSelection(this, false, omxFile, modsFile, userModsFile, lastSelectedFolder);
    }//GEN-LAST:event_openJMenuItemActionPerformed

    /**
     * Export the contents of the spectra files table to a tab delimited text
     * file.
     *
     * @param evt
     */
    private void exportSpectraFilesTableJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportSpectraFilesTableJMenuItemActionPerformed
        JFileChooser chooser = new JFileChooser(lastSelectedFolder);
        chooser.setFileFilter(new TxtFileFilter());
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Export Spectra File Details");

        File selectedFile;

        int returnVal = chooser.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            selectedFile = chooser.getSelectedFile();

            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            }

            while (selectedFile.exists()) {
                int option = JOptionPane.showConfirmDialog(this,
                        "The  file " + chooser.getSelectedFile().getName()
                        + " already exists. Replace file?",
                        "Replace File?", JOptionPane.YES_NO_CANCEL_OPTION);

                if (option == JOptionPane.NO_OPTION) {
                    chooser = new JFileChooser(lastSelectedFolder);
                    chooser.setFileFilter(new TxtFileFilter());
                    chooser.setMultiSelectionEnabled(false);
                    chooser.setDialogTitle("Export Spectra File Details");

                    returnVal = chooser.showSaveDialog(this);

                    if (returnVal == JFileChooser.CANCEL_OPTION) {
                        return;
                    } else {
                        selectedFile = chooser.getSelectedFile();

                        if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                            selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                        }
                    }
                } else { // YES option
                    break;
                }
            }

            this.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

            try {

                selectedFile = chooser.getSelectedFile();

                if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                }

                if (selectedFile.exists()) {
                    selectedFile.delete();
                }

                selectedFile.createNewFile();

                FileWriter f = new FileWriter(selectedFile);

                // add the column headers
                for (int j = 0; j < spectraJTable.getColumnCount() - 1; j++) {
                    f.write(spectraJTable.getColumnName(j) + "\t");
                }

                f.write(spectraJTable.getColumnName(spectraJTable.getColumnCount() - 1) + "\n");

                // add the table contents
                for (int i = 0; i < spectraJTable.getRowCount(); i++) {
                    for (int j = 0; j < spectraJTable.getColumnCount() - 1; j++) {
                        f.write(spectraJTable.getValueAt(i, j) + "\t");
                    }

                    f.write(spectraJTable.getValueAt(i, spectraJTable.getColumnCount() - 1) + "\n");
                }

                f.close();

                lastSelectedFolder = selectedFile.getPath();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "An error occured when exporting the spectra file details.\n"
                        + "See ../Properties/ErrorLog.txt for more details.",
                        "Error Exporting Spectra Files",
                        JOptionPane.ERROR_MESSAGE);
                Util.writeToErrorLog("Error when exporting spectra file details: ");
                ex.printStackTrace();
            }

            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        }
}//GEN-LAST:event_exportSpectraFilesTableJMenuItemActionPerformed

    /**
     * Export the contents of the identification table to a tab delimited text
     * file.
     *
     * @param evt
     */
    private void exportSelectedSpectrumJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportSelectedSpectrumJMenuItemActionPerformed
        JFileChooser chooser = new JFileChooser(lastSelectedFolder);
        chooser.setFileFilter(new TxtFileFilter());
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Export Selected Spectrum");

        File selectedFile;

        int returnVal = chooser.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            selectedFile = chooser.getSelectedFile();

            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            }

            while (selectedFile.exists()) {
                int option = JOptionPane.showConfirmDialog(this,
                        "The  file " + chooser.getSelectedFile().getName()
                        + " already exists. Replace file?",
                        "Replace File?", JOptionPane.YES_NO_CANCEL_OPTION);

                if (option == JOptionPane.NO_OPTION) {
                    chooser = new JFileChooser(lastSelectedFolder);
                    chooser.setFileFilter(new TxtFileFilter());
                    chooser.setMultiSelectionEnabled(false);
                    chooser.setDialogTitle("Export Selected Spectrum");

                    returnVal = chooser.showSaveDialog(this);

                    if (returnVal == JFileChooser.CANCEL_OPTION) {
                        return;
                    } else {
                        selectedFile = chooser.getSelectedFile();

                        if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                            selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                        }
                    }
                } else { // YES option
                    break;
                }
            }

            this.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

            try {

                selectedFile = chooser.getSelectedFile();

                if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                }

                if (selectedFile.exists()) {
                    selectedFile.delete();
                }

                selectedFile.createNewFile();

                FileWriter f = new FileWriter(selectedFile);

                // add the column headers
                for (int j = 0; j < spectrumJTable.getColumnCount() - 1; j++) {
                    f.write(spectrumJTable.getColumnName(j) + "\t");
                }

                f.write(spectrumJTable.getColumnName(spectrumJTable.getColumnCount() - 1) + "\n");

                // add the table contents
                for (int i = 0; i < spectrumJTable.getRowCount(); i++) {
                    for (int j = 0; j < spectrumJTable.getColumnCount() - 1; j++) {
                        f.write(spectrumJTable.getValueAt(i, j) + "\t");
                    }

                    f.write(spectrumJTable.getValueAt(i, spectrumJTable.getColumnCount() - 1) + "\n");
                }

                f.close();

                lastSelectedFolder = selectedFile.getPath();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "An error occured when exporting the selected spectrum.\n"
                        + "See ../Properties/ErrorLog.txt for more details.",
                        "Error Exporting Selected Spectrum",
                        JOptionPane.ERROR_MESSAGE);
                Util.writeToErrorLog("Error when exporting selected spectrum: ");
                ex.printStackTrace();
            }

            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        }
}//GEN-LAST:event_exportSelectedSpectrumJMenuItemActionPerformed

    /**
     * Export all identifications (all hits) to a tab delimited text file.
     *
     * @param evt
     */
    private void exportAllIdentificationsJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportAllIdentificationsJMenuItemActionPerformed
        exportAllIdentifications(false);
}//GEN-LAST:event_exportAllIdentificationsJMenuItemActionPerformed

    /**
     * Export all identifications to a tab delimited text file.
     *
     * @param includeBestHitOnly - if true only the best hits are included,
     * otherwise all hits are included
     */
    private void exportAllIdentifications(boolean includeBestHitOnly) {

        JFileChooser chooser = new JFileChooser(lastSelectedFolder);
        chooser.setFileFilter(new TxtFileFilter());
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Export All Identifications");

        File selectedFile;

        int returnVal = chooser.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            selectedFile = chooser.getSelectedFile();

            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            }

            while (selectedFile.exists()) {
                int option = JOptionPane.showConfirmDialog(this,
                        "The  file " + chooser.getSelectedFile().getName()
                        + " already exists. Replace file?",
                        "Replace File?", JOptionPane.YES_NO_CANCEL_OPTION);

                if (option == JOptionPane.NO_OPTION) {
                    chooser = new JFileChooser(lastSelectedFolder);
                    chooser.setFileFilter(new TxtFileFilter());
                    chooser.setMultiSelectionEnabled(false);
                    chooser.setDialogTitle("Export All Identifications");

                    returnVal = chooser.showSaveDialog(this);

                    if (returnVal == JFileChooser.CANCEL_OPTION) {
                        return;
                    } else {
                        selectedFile = chooser.getSelectedFile();

                        if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                            selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                        }
                    }
                } else { // YES option
                    break;
                }
            }

            this.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

            try {

                selectedFile = chooser.getSelectedFile();

                if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                }

                if (selectedFile.exists()) {
                    selectedFile.delete();
                }

                selectedFile.createNewFile();

                FileWriter f = new FileWriter(selectedFile);

                // add the column headers
                for (int j = 0; j < identificationsJTable.getColumnCount() - 1; j++) {
                    if (j == 0) {
                        f.write("Spectrum number\t");
                        f.write("Spectrum name\t");
                    } else if (j == 2) {
                        f.write("Modified Sequence" + "\t");
                        f.write("Ion Coverage" + "\t");
                    } else {
                        f.write(identificationsJTable.getColumnName(j) + "\t");
                    }
                }

                f.write(identificationsJTable.getColumnName(identificationsJTable.getColumnCount() - 1) + "\n");


                // add the identification details

                // get the list of fixed modifications
                List<Integer> fixedModifications =
                        omssaOmxFile.getParserResult().MSSearch_request.MSRequest.get(0).MSRequest_settings.MSSearchSettings.MSSearchSettings_fixed.MSMod;


                // iterate all the identifications and print them to the file
                Iterator<MSSpectrum> iterator = spectrumHitSetMap.keySet().iterator();

                while (iterator.hasNext()) {

                    boolean allWantedHitsAdded = false;

                    MSHitSet msHitSet = spectrumHitSetMap.get(iterator.next());
                    List<MSHits> allMSHits = msHitSet.MSHitSet_hits.MSHits;
                    Iterator<MSHits> msHitIterator = allMSHits.iterator();

                    while (msHitIterator.hasNext() && !allWantedHitsAdded) {

                        MSHits tempMSHit = msHitIterator.next();

                        String sequence = tempMSHit.MSHits_pepstring;
                        String[] modifications = new String[sequence.length()];

                        for (int i = 0; i < modifications.length; i++) {
                            modifications[i] = "";
                        }

                        String modifiedSequence = "";
                        String nTerminal = "";//"NH2-";
                        String cTerminal = "";//"-COOH";

                        // handle modifications
                        if (omssaOmxFile.getModifications().size() > 0) {

                            if (fixedModifications.size() > 0) {

                                for (int i = 0; i < fixedModifications.size(); i++) {

                                    Vector<String> modifiedResidues =
                                            omssaOmxFile.getModifications().get(fixedModifications.get(i)).getModResidues();

                                    for (int j = 0; j < modifiedResidues.size(); j++) {

                                        int index = sequence.indexOf(modifiedResidues.get(j));

                                        while (index != -1) {

                                            modifications[index] +=
                                                    "<" + omssaOmxFile.getModifications().get(fixedModifications.get(i)).getModNumber() + ">";

                                            index = sequence.indexOf(modifiedResidues.get(j), index + 1);
                                        }
                                    }
                                }
                            }

                            // variable modifications
                            Iterator<MSModHit> modsIterator = tempMSHit.MSHits_mods.MSModHit.iterator();

                            while (modsIterator.hasNext()) {

                                MSModHit currentMSModHit = modsIterator.next();

                                modifications[currentMSModHit.MSModHit_site] +=
                                        "<" + currentMSModHit.MSModHit_modtype.MSMod + ">";
                            }

                            // cycle through all the modifications and extract the modification type if possible
                            for (int i = 0; i < modifications.length; i++) {

                                // add the amino acid itself to the sequence
                                modifiedSequence += sequence.substring(i, i + 1);

                                if (!modifications[i].equalsIgnoreCase("")) {

                                    // have to check for multiple modifications on one residue
                                    String[] residueMods = modifications[i].split(">");

                                    for (int j = 0; j < residueMods.length; j++) {

                                        String currentMod = residueMods[j] + ">";

                                        OmssaModification tempOmssaModification = omssaOmxFile.getModifications().get(
                                                new Integer(residueMods[j].substring(1)));

                                        if (tempOmssaModification != null) {

                                            if (tempOmssaModification.getModType() == OmssaModification.MODAA) {

                                                // "normal" modification
                                                modifiedSequence += currentMod;
                                            } else if (tempOmssaModification.getModType() == OmssaModification.MODN
                                                    || tempOmssaModification.getModType() == OmssaModification.MODNAA
                                                    || tempOmssaModification.getModType() == OmssaModification.MODNP
                                                    || tempOmssaModification.getModType() == OmssaModification.MODNPAA) {

                                                // n-terminal modification
                                                nTerminal += currentMod;
                                            } else if (tempOmssaModification.getModType() == OmssaModification.MODC
                                                    || tempOmssaModification.getModType() == OmssaModification.MODCAA
                                                    || tempOmssaModification.getModType() == OmssaModification.MODCP
                                                    || tempOmssaModification.getModType() == OmssaModification.MODCPAA) {

                                                // c-terminal modification
                                                cTerminal += currentMod;
                                            }
                                        } else {
                                            modifiedSequence += currentMod;
                                        }
                                    }
                                }
                            }
                        } else {
                            modifiedSequence = sequence;
                        }

                        // set the n terminal
                        if (nTerminal.length() == 0) {
                            nTerminal = "NH2-"; // no terminal (or terminal modification) given
                        } else {
                            nTerminal += "-"; // add the "-" at the end, i.e. "NH2-"
                        }

                        // set the c terminal
                        if (cTerminal.length() == 0) {
                            cTerminal = "-COOH"; // no terminal (or terminal modification) given
                        } else {
                            cTerminal = "-" + cTerminal; // add the "-" at the beginning, i.e. "-COOH"
                        }

                        // add ion coverage to peptide sequence
                        Iterator<MSMZHit> mzHits = tempMSHit.MSHits_mzhits.MSMZHit.iterator();

                        int[][] ionCoverage = new int[sequence.length()][2];

                        while (mzHits.hasNext()) {
                            MSMZHit tempMzHit = mzHits.next();

                            int ionType = tempMzHit.MSMZHit_ion.MSIonType;
                            int ionNumber = tempMzHit.MSMZHit_number;

                            // Note: assumes that 0 is a, 1 is b, 2 is c, 3 is x, 4 is y and 5 is z
                            if (ionType == 0) {
                            } else if (ionType == 1) {
                                ionCoverage[ionNumber][0]++;
                            } else if (ionType == 3) {
                            } else if (ionType == 4) {
                                ionCoverage[ionNumber][1]++;
                            } else if (ionType == 5) {
                            }
                        }

                        // add the ion coverage to the modified sequence
                        int[][] ionCoverageProcessed = new int[sequence.length()][2];

                        for (int i = 1; i < ionCoverage.length; i++) {
                            if (ionCoverage[i][0] > 0 && ionCoverage[i - 1][0] > 0) {
                                ionCoverageProcessed[i][0] = 1;
                            } else {
                                ionCoverageProcessed[i][0] = 0;
                            }

                            if (ionCoverage[i][1] > 0 && ionCoverage[i - 1][1] > 0) {
                                ionCoverageProcessed[ionCoverage.length - 1 - i][1] = 1;
                            } else {
                                ionCoverageProcessed[ionCoverage.length - 1 - i][1] = 0;
                            }
                        }

                        String modifiedSequenceColorCoded = "<html>";

                        // add nTerminal
                        if (!nTerminal.startsWith("<")) {
                            modifiedSequenceColorCoded += nTerminal;
                        } else {
                            modifiedSequenceColorCoded += "&lt;";
                            modifiedSequenceColorCoded += nTerminal.substring(1, nTerminal.length() - 2);
                            modifiedSequenceColorCoded += "&gt;-";
                        }

                        int aminoAcidCounter = 0;

                        for (int i = 0; i < modifiedSequence.length(); i++) {

                            if (modifiedSequence.charAt(i) == '<') {

                                if (ionCoverageProcessed[aminoAcidCounter - 1][0] > 0) {
                                    // b ions
                                    modifiedSequenceColorCoded += "<u>";
                                }

                                if (ionCoverageProcessed[aminoAcidCounter - 1][1] > 0) {
                                    // y ions
                                    modifiedSequenceColorCoded += "<font color=\"red\">";
                                }

                                modifiedSequenceColorCoded += "&lt;";
                                i++;

                                while (modifiedSequence.charAt(i) != '>') {
                                    modifiedSequenceColorCoded += modifiedSequence.charAt(i++);
                                }

                                modifiedSequenceColorCoded += "&gt;";

                                if (ionCoverageProcessed[aminoAcidCounter - 1][0] > 0) {
                                    // b ions
                                    modifiedSequenceColorCoded += "</u>";
                                }
                                if (ionCoverageProcessed[aminoAcidCounter - 1][1] > 0) {
                                    // y ions
                                    modifiedSequenceColorCoded += "</font>";
                                }
                            } else {

                                if (ionCoverageProcessed[aminoAcidCounter][0] > 0) {
                                    // b ions
                                    modifiedSequenceColorCoded += "<u>";
                                }

                                if (ionCoverageProcessed[aminoAcidCounter][1] > 0) {
                                    // y ions
                                    modifiedSequenceColorCoded += "<font color=\"red\">";
                                }

                                modifiedSequenceColorCoded += modifiedSequence.charAt(i);

                                if (ionCoverageProcessed[aminoAcidCounter][0] > 0) {
                                    // b ions
                                    modifiedSequenceColorCoded += "</u>";
                                }
                                if (ionCoverageProcessed[aminoAcidCounter][1] > 0) {
                                    // y ions
                                    modifiedSequenceColorCoded += "</font>";
                                }

                                aminoAcidCounter++;
                            }

                            modifiedSequenceColorCoded += "<font color=\"black\">";
                        }

                        // add cTerminal
                        if (!cTerminal.startsWith("-<")) {
                            modifiedSequenceColorCoded += cTerminal;
                        } else {
                            modifiedSequenceColorCoded += "-&lt;";
                            modifiedSequenceColorCoded += cTerminal.substring(2, cTerminal.length() - 1);
                            modifiedSequenceColorCoded += "&gt;";
                        }

                        modifiedSequenceColorCoded += "</html>";

                        // add terminals to modified sequence (the non-color coded version)
                        modifiedSequence = nTerminal + modifiedSequence + cTerminal;

                        List<MSPepHit> pepHits = tempMSHit.MSHits_pephits.MSPepHit;

                        Iterator<MSPepHit> pepHitIterator = pepHits.iterator();

                        while (pepHitIterator.hasNext() && !allWantedHitsAdded) {

                            MSPepHit tempPepHit = pepHitIterator.next();

                            MSSpectrum tempSpectrum = spectra.get(new Integer(msHitSet.MSHitSet_number));
                            String filename = "[no filename specified]";
                            if (!tempSpectrum.MSSpectrum_ids.MSSpectrum_ids_E.isEmpty()) {
                                filename = tempSpectrum.MSSpectrum_ids.MSSpectrum_ids_E.get(0);
                            }

                            f.write(msHitSet.MSHitSet_number + "\t"
                                    + filename + "\t"
                                    + sequence + "\t"
                                    + modifiedSequence + "\t"
                                    + modifiedSequenceColorCoded + "\t"
                                    + tempPepHit.MSPepHit_start + "\t"
                                    + tempPepHit.MSPepHit_stop + "\t"
                                    + new Double(((double) tempMSHit.MSHits_mass) / omssaResponseScale) + "\t"
                                    + new Double(((double) tempMSHit.MSHits_theomass) / omssaResponseScale) + "\t"
                                    + tempMSHit.MSHits_evalue + "\t"
                                    + tempMSHit.MSHits_pvalue + "\t"
                                    + tempPepHit.MSPepHit_accession + "\t"
                                    + tempPepHit.MSPepHit_defline + "\n");

                            if (includeBestHitOnly) {
                                allWantedHitsAdded = true;
                            }
                        }

                        if (includeBestHitOnly) {
                            allWantedHitsAdded = true;
                        }
                    }
                }

                f.close();

                lastSelectedFolder = selectedFile.getPath();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "An error occured when exporting the identifications.\n"
                        + "See ../Properties/ErrorLog.txt for more details.",
                        "Error Exporting Identifications",
                        JOptionPane.ERROR_MESSAGE);
                Util.writeToErrorLog("Error when exporting identifications: ");
                ex.printStackTrace();
            }
        }

        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }

    /**
     * Opens a frame containing the help manual for OMSSA Viewer.
     *
     * @param evt
     */
    private void helpJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpJMenuItemActionPerformed
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        new HelpWindow(this, getClass().getResource("/de/proteinms/omxparser/helpfiles/OmssaViewer.html"));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_helpJMenuItemActionPerformed

    /**
     * Opens a frame containing the About OMSSA Viewer information.
     *
     * @param evt
     */
    private void aboutJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutJMenuItemActionPerformed
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        new HelpWindow(this, getClass().getResource("/de/proteinms/omxparser/helpfiles/AboutOmssaViewer.html"));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_aboutJMenuItemActionPerformed

    /**
     * Export the all the spectra as dat files.
     *
     * @param evt
     */
    private void exportAllSpectraJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportAllSpectraJMenuItemActionPerformed

        JFileChooser chooser = new JFileChooser(lastSelectedFolder);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Export All Spectra As DTA Files");

        File selectedFolder;

        int returnVal = chooser.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            this.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

            selectedFolder = chooser.getSelectedFile();

            for (int j = 0; j < spectraJTable.getRowCount(); j++) {

                List<Double> mzValues = allMzValues.get((Integer) spectraJTable.getValueAt(j, 0));
                List<Double> abundanceValues = allAbundanceValues.get((Integer) spectraJTable.getValueAt(j, 0));

                File currentFile = new File(selectedFolder, "" + spectraJTable.getValueAt(j, 1));

                FileWriter f;

                try {
                    f = new FileWriter(currentFile);

                    // write the precursor MH+ and charge
                    // precusor MH+ has to be converted from the m/z value in the file

                    double precusorMz = ((Double) spectraJTable.getValueAt(j, 2)).doubleValue();
                    int precursorCharge = ((Integer) spectraJTable.getValueAt(j, 3)).intValue();
                    double precursorMh = precusorMz * precursorCharge - precursorCharge * HYDROGEN_MASS + HYDROGEN_MASS;

                    f.write("" + precursorMh);
                    f.write(" " + precursorCharge + "\n");

                    // write all the m/z abundance pairs
                    for (int i = 0; i < mzValues.size(); i++) {
                        f.write(mzValues.get(i) + " " + abundanceValues.get(i) + "\n");
                    }

                    f.close();

                    lastSelectedFolder = currentFile.getPath();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this,
                            "An error occured when exporting the spectra.\n"
                            + "See ../Properties/ErrorLog.txt for more details.",
                            "Error Exporting Spectra",
                            JOptionPane.ERROR_MESSAGE);
                    Util.writeToErrorLog("Error when exporting spectra: ");
                    ex.printStackTrace();
                }
            }

            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        }
}//GEN-LAST:event_exportAllSpectraJMenuItemActionPerformed

    /**
     * Updates the ion coverage annotations
     *
     * @param evt
     */
    private void aIonsJCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aIonsJCheckBoxActionPerformed
        if (identificationsJTable.getRowCount() > 0) {

            int selectedRow = 0;

            if (identificationsJTable.getRowCount() > 1
                    && identificationsJTable.getSelectedRow() != -1) {
                selectedRow = identificationsJTable.getSelectedRow();
            }

            Vector<DefaultSpectrumAnnotation> currentAnnotations = allAnnotations.get(
                    identificationsJTable.getValueAt(selectedRow, 1) + "_"
                    + identificationsJTable.getValueAt(selectedRow, 8));

            // update the ion coverage annotations
            spectrumPanel.setAnnotations(filterAnnotations(currentAnnotations));
            spectrumPanel.validate();
            spectrumPanel.repaint();
        }
    }//GEN-LAST:event_aIonsJCheckBoxActionPerformed

    /**
     * @see #aIonsJCheckBoxActionPerformed(java.awt.event.ActionEvent)
     */
    private void bIonsJCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bIonsJCheckBoxActionPerformed
        aIonsJCheckBoxActionPerformed(null);
    }//GEN-LAST:event_bIonsJCheckBoxActionPerformed

    /**
     * @see #aIonsJCheckBoxActionPerformed(java.awt.event.ActionEvent)
     */
    private void cIonsJCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cIonsJCheckBoxActionPerformed
        aIonsJCheckBoxActionPerformed(null);
    }//GEN-LAST:event_cIonsJCheckBoxActionPerformed

    /**
     * @see #aIonsJCheckBoxActionPerformed(java.awt.event.ActionEvent)
     */
    private void xIonsJCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xIonsJCheckBoxActionPerformed
        aIonsJCheckBoxActionPerformed(null);
    }//GEN-LAST:event_xIonsJCheckBoxActionPerformed

    /**
     * @see #aIonsJCheckBoxActionPerformed(java.awt.event.ActionEvent)
     */
    private void yIonsJCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yIonsJCheckBoxActionPerformed
        aIonsJCheckBoxActionPerformed(null);
    }//GEN-LAST:event_yIonsJCheckBoxActionPerformed

    /**
     * @see #aIonsJCheckBoxActionPerformed(java.awt.event.ActionEvent)
     */
    private void zIonsJCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zIonsJCheckBoxActionPerformed
        aIonsJCheckBoxActionPerformed(null);
    }//GEN-LAST:event_zIonsJCheckBoxActionPerformed

    /**
     * @see #aIonsJCheckBoxActionPerformed(java.awt.event.ActionEvent)
     */
    private void chargeOneJCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chargeOneJCheckBoxActionPerformed
        aIonsJCheckBoxActionPerformed(null);
    }//GEN-LAST:event_chargeOneJCheckBoxActionPerformed

    /**
     * @see #aIonsJCheckBoxActionPerformed(java.awt.event.ActionEvent)
     */
    private void chargeTwoJCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chargeTwoJCheckBoxActionPerformed
        aIonsJCheckBoxActionPerformed(null);
    }//GEN-LAST:event_chargeTwoJCheckBoxActionPerformed

    /**
     * @see #aIonsJCheckBoxActionPerformed(java.awt.event.ActionEvent)
     */
    private void chargeOverTwoJCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chargeOverTwoJCheckBoxActionPerformed
        aIonsJCheckBoxActionPerformed(null);
    }//GEN-LAST:event_chargeOverTwoJCheckBoxActionPerformed

    /**
     * Export all identifications (best hits only) to a tab delimited text file.
     *
     * @param evt
     */
    private void exportBestIdentificationsJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportBestIdentificationsJMenuItemActionPerformed
        exportAllIdentifications(true);
}//GEN-LAST:event_exportBestIdentificationsJMenuItemActionPerformed

    /**
     * Opens a popup menu if the user right clicks in the spectrum table.
     *
     * @param evt
     */
    private void spectraJTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spectraJTableKeyReleased
        spectraJTableMouseClicked(null);
    }//GEN-LAST:event_spectraJTableKeyReleased

    /**
     * When the user selects a row in the spectra files table, the
     * identifications (if any) are inserted in the identification table at the
     * bottom of the frame, and the spectrum details are shown in the spectrum
     * panel and table to the right.
     *
     * @param evt
     */
    private void spectraJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spectraJTableMouseClicked
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

        int row = spectraJTable.getSelectedRow();

        if (row != -1) {
            List<Double> mzValues = allMzValues.get((Integer) spectraJTable.getValueAt(row, 0));
            List<Double> abundanceValues = allAbundanceValues.get((Integer) spectraJTable.getValueAt(row, 0));

            // empty the spectrum table
            while (spectrumJTable.getRowCount() > 0) {
                ((DefaultTableModel) spectrumJTable.getModel()).removeRow(0);
            }

            // scrolls the scrollbar to the top of the spectrum table
            spectrumJTable.scrollRectToVisible(spectrumJTable.getCellRect(0, 0, false));

            // epmty the spectrum panel
            while (spectrumJPanel.getComponents().length > 0) {
                spectrumJPanel.remove(0);
            }

            // needed as input to the spectrum panel
            double[] mzValuesAsDouble = new double[mzValues.size()];
            double[] abundanceValuesAsDouble = new double[mzValues.size()];

            // insert the spectrum details in the spectrum table
            for (int i = 0; i < mzValues.size(); i++) {

                ((DefaultTableModel) spectrumJTable.getModel()).addRow(new Object[]{
                            new Integer(i + 1),
                            mzValues.get(i),
                            abundanceValues.get(i)
                        });

                mzValuesAsDouble[i] = mzValues.get(i);
                abundanceValuesAsDouble[i] = abundanceValues.get(i);
            }

            exportSelectedSpectrumJMenuItem.setEnabled(true);

            // updates the spectrum panel
            spectrumPanel = new SpectrumPanel(
                    mzValuesAsDouble,
                    abundanceValuesAsDouble,
                    ((Double) spectraJTable.getValueAt(row, 2)),
                    "" + spectraJTable.getValueAt(row, 3),
                    ((String) spectraJTable.getValueAt(row, 1)),
                    60, false);
            spectrumPanel.showAnnotatedPeaksOnly(true);

            spectrumPanel.setBorder(null);

            spectrumJPanel.add(spectrumPanel);
            spectrumJPanel.validate();
            spectrumJPanel.repaint();

            // empty the identification table
            while (identificationsJTable.getRowCount() > 0) {
                ((DefaultTableModel) identificationsJTable.getModel()).removeRow(0);
            }

            // clear the modification details legend
            legendJLabel.setText("");
            ptmsJTextField.setText("");

            // get the list of fixed modifications
            List<Integer> fixedModifications =
                    omssaOmxFile.getParserResult().MSSearch_request.MSRequest.get(0).MSRequest_settings.MSSearchSettings.MSSearchSettings_fixed.MSMod;

            // iterate all the identifications and insert them into the identification table
            MSHitSet msHitSet = spectrumHitSetMap.get(spectra.get((Integer) spectraJTable.getValueAt(row, 0)));

            allAnnotations = new HashMap();

            List<MSHits> allMSHits = msHitSet.MSHitSet_hits.MSHits;
            Iterator<MSHits> msHitIterator = allMSHits.iterator();

            String modificationDetails = "";

            while (msHitIterator.hasNext()) {

                MSHits tempMSHit = msHitIterator.next();

                String sequence = tempMSHit.MSHits_pepstring;
                String[] modifications = new String[sequence.length()];

                for (int i = 0; i < modifications.length; i++) {
                    modifications[i] = "";
                }

                String modifiedSequence = "";
                String nTerminal = "";
                String cTerminal = "";

                // handle modifications
                if (omssaOmxFile.getModifications().size() > 0) {

                    if (fixedModifications.size() > 0) {

                        for (int i = 0; i < fixedModifications.size(); i++) {

                            // @TODO: what about terminal ptms
                            OmssaModification tempOmssaModification = omssaOmxFile.getModifications().get(fixedModifications.get(i));
                            
                            if (tempOmssaModification.getModType() == OmssaModification.MODAA) {

                                // "normal" modification
                                Vector<String> modifiedResidues = tempOmssaModification.getModResidues();

                                for (int j = 0; j < modifiedResidues.size(); j++) {

                                    int index = sequence.indexOf(modifiedResidues.get(j));

                                    while (index != -1) {
                                        modifications[index] += "<" + tempOmssaModification.getModNumber() + ">";
                                        index = sequence.indexOf(modifiedResidues.get(j), index + 1);
                                    }
                                }
                                
                            } else if (tempOmssaModification.getModType() == OmssaModification.MODN
                                    || tempOmssaModification.getModType() == OmssaModification.MODNAA
                                    || tempOmssaModification.getModType() == OmssaModification.MODNP
                                    || tempOmssaModification.getModType() == OmssaModification.MODNPAA) {

                                // n-terminal modification
                                nTerminal += "<" + tempOmssaModification.getModNumber() + ">";
                                
                                // check if we've already mapped the modification
                                if (modificationDetails.lastIndexOf("<" + tempOmssaModification.getModNumber() + ">") == -1) {
                                    modificationDetails += "<" + tempOmssaModification.getModNumber() + ">" + " " + tempOmssaModification.getModName()
                                                + " (" + tempOmssaModification.getModMonoMass() + "), ";
                                }
                                
                            } else if (tempOmssaModification.getModType() == OmssaModification.MODC
                                    || tempOmssaModification.getModType() == OmssaModification.MODCAA
                                    || tempOmssaModification.getModType() == OmssaModification.MODCP
                                    || tempOmssaModification.getModType() == OmssaModification.MODCPAA) {

                                // c-terminal modification
                                cTerminal += "<" + tempOmssaModification.getModNumber() + ">";
                                
                                // check if we've already mapped the modification
                                if (modificationDetails.lastIndexOf("<" + tempOmssaModification.getModNumber() + ">") == -1) {
                                    modificationDetails += "<" + tempOmssaModification.getModNumber() + ">" + " " + tempOmssaModification.getModName()
                                                + " (" + tempOmssaModification.getModMonoMass() + "), ";
                                }
                            } 
                        }
                    }

                    // variable modifications
                    Iterator<MSModHit> modsIterator = tempMSHit.MSHits_mods.MSModHit.iterator();

                    while (modsIterator.hasNext()) {

                        MSModHit currentMSModHit = modsIterator.next();

                        modifications[currentMSModHit.MSModHit_site] +=
                                "<" + currentMSModHit.MSModHit_modtype.MSMod + ">";
                    }

                    // cycle through all the modifications and extract the modification type if possible
                    for (int i = 0; i < modifications.length; i++) {

                        // add the amino acid itself to the sequence
                        modifiedSequence += sequence.substring(i, i + 1);

                        if (!modifications[i].equalsIgnoreCase("")) {

                            // have to check for multiple modifications on one residue
                            String[] residueMods = modifications[i].split(">");

                            for (int j = 0; j < residueMods.length; j++) {

                                String currentMod = residueMods[j] + ">";

                                // check if we've already mapped the modification
                                if (modificationDetails.lastIndexOf(currentMod) == -1) {

                                    OmssaModification tempOmssaModification = omssaOmxFile.getModifications().get(
                                            new Integer(residueMods[j].substring(1)));

                                    if (tempOmssaModification != null) {

                                        modificationDetails += currentMod + " " + tempOmssaModification.getModName()
                                                + " (" + tempOmssaModification.getModMonoMass() + "), ";

                                        if (tempOmssaModification.getModType() == OmssaModification.MODAA) {

                                            // "normal" modification
                                            modifiedSequence += currentMod;
                                        } else if (tempOmssaModification.getModType() == OmssaModification.MODN
                                                || tempOmssaModification.getModType() == OmssaModification.MODNAA
                                                || tempOmssaModification.getModType() == OmssaModification.MODNP
                                                || tempOmssaModification.getModType() == OmssaModification.MODNPAA) {

                                            // n-terminal modification
                                            nTerminal += currentMod;
                                        } else if (tempOmssaModification.getModType() == OmssaModification.MODC
                                                || tempOmssaModification.getModType() == OmssaModification.MODCAA
                                                || tempOmssaModification.getModType() == OmssaModification.MODCP
                                                || tempOmssaModification.getModType() == OmssaModification.MODCPAA) {

                                            // c-terminal modification
                                            cTerminal += currentMod;
                                        }
                                    } else {
                                        modifiedSequence += currentMod;
                                        modificationDetails += currentMod + " unknown, ";
                                    }
                                } else {
                                    modifiedSequence += currentMod;
                                }
                            }
                        }
                    }
                } else {
                    ptmsJTextField.setText("(Files with modification details were not provided. "
                            + "No modifications are shown.)");
                    legendJLabel.setText(ionCoverageLegend);
                    modifiedSequence = sequence;
                }

                // set the n-terminal
                if (nTerminal.length() == 0) {
                    nTerminal = "NH2-"; // no terminal (or terminal modification) given
                } else {
                    nTerminal += "-"; // add the "-" at the end, i.e. "NH2-"
                }

                // set the c-terminal
                if (cTerminal.length() == 0) {
                    cTerminal = "-COOH"; // no terminal (or terminal modification) given
                } else {
                    cTerminal = "-" + cTerminal; // add the "-" at the beginning, i.e. "-COOH"
                }

                // add ion coverage to peptide sequence
                Iterator<MSMZHit> mzHits = tempMSHit.MSHits_mzhits.MSMZHit.iterator();

                Vector<DefaultSpectrumAnnotation> currentAnnotations = new Vector();

                // annotate precursor ion
//                annotations.add(new DefaultSpectrumAnnotation(
//                            ((Double) spectraJXTable.getValueAt(spectraJXTable.getSelectedRow(), 2)).doubleValue(),
//                            ionCoverageErrorMargin, Color.GRAY, "pre"));

                int[][] ionCoverage = new int[sequence.length()][2];

                while (mzHits.hasNext()) {
                    MSMZHit tempMzHit = mzHits.next();

                    int ionType = tempMzHit.MSMZHit_ion.MSIonType;

                    int msIonNeutralLossType = tempMzHit.MSMZHit_moreion.MSIon.MSIon_neutralloss.MSIonNeutralLoss;

                    String neturalLossTag = "";
                    String immoniumTag = "";

                    // -1 means no neutral loss reported
                    if (msIonNeutralLossType == -1) {
                        // check for immonium ions
                        // note: assumes that an immonium ion can not have a neutral loss
                        if (tempMzHit.MSMZHit_moreion.MSIon.MSIon_immonium.MSImmonium.MSImmonium_parent != null) {
                            immoniumTag = "i" + tempMzHit.MSMZHit_moreion.MSIon.MSIon_immonium.MSImmonium.MSImmonium_parent;
                        }
                    } else {
                        if (msIonNeutralLossType == 0) {
                            // water neutral loss
                            neturalLossTag = " -H2O";
                        } else if (msIonNeutralLossType == 1) {
                            // ammonia neutral loss
                            neturalLossTag = " -NH3";
                        }
                    }

                    int charge = tempMzHit.MSMZHit_charge;
                    int ionNumber = tempMzHit.MSMZHit_number;
                    double mzValue = ((double) tempMzHit.MSMZHit_mz) / omssaResponseScale;

                    String chargeAsString = "";

                    // add the charge to the label if higher than 1
                    if (charge > 1) {
                        for (int i = 0; i < charge; i++) {
                            chargeAsString += "+";
                        }
                    }

                    String unusedIon = "";

                    if (!usedIonTypes.contains(new Integer(ionType))) {
                        unusedIon = "#";
                    }

                    // from OMSSA.mod.xsd:
                    // <xs:enumeration value="a" ncbi:intvalue="0" />
                    // <xs:enumeration value="b" ncbi:intvalue="1" />
                    // <xs:enumeration value="c" ncbi:intvalue="2" />
                    // <xs:enumeration value="x" ncbi:intvalue="3" />
                    // <xs:enumeration value="y" ncbi:intvalue="4" />
                    // <xs:enumeration value="z" ncbi:intvalue="5" />
                    // <xs:enumeration value="parent" ncbi:intvalue="6" />
                    // <xs:enumeration value="internal" ncbi:intvalue="7" />
                    // <xs:enumeration value="immonium" ncbi:intvalue="8" />
                    // <xs:enumeration value="unknown" ncbi:intvalue="9" />
                    // <xs:enumeration value="max" ncbi:intvalue="10" />

                    if (ionType == 0) {
                        currentAnnotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.BLUE,
                                unusedIon + "a" + (ionNumber + 1) + chargeAsString + neturalLossTag));
                    } else if (ionType == 1) {
                        currentAnnotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.BLUE,
                                unusedIon + "b" + (ionNumber + 1) + chargeAsString + neturalLossTag));
                        ionCoverage[ionNumber][0]++;
                    } else if (ionType == 2) {
                        currentAnnotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.BLUE,
                                unusedIon + "c" + (ionNumber + 1) + chargeAsString + neturalLossTag));
                    } else if (ionType == 3) {
                        currentAnnotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.BLACK,
                                unusedIon + "x" + (ionNumber + 1) + chargeAsString + neturalLossTag));
                    } else if (ionType == 4) {
                        currentAnnotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.BLACK,
                                unusedIon + "y" + (ionNumber + 1) + chargeAsString + neturalLossTag));
                        ionCoverage[ionNumber][1]++;
                    } else if (ionType == 5) {
                        currentAnnotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.BLACK,
                                unusedIon + "z" + (ionNumber + 1) + chargeAsString + neturalLossTag));
                    } else if (ionType == 6) {
                        currentAnnotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.GRAY,
                                unusedIon + "parent" + chargeAsString + neturalLossTag));
                    } else if (ionType == 7) {
                        currentAnnotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.GRAY,
                                unusedIon + "internal" + chargeAsString + neturalLossTag));
                    } else if (ionType == 8) {
                        currentAnnotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.GRAY,
                                unusedIon + immoniumTag /*
                                 * + chargeAsString + neturalLossTag
                                 */));
                    } else if (ionType == 9) {
                        currentAnnotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.GRAY,
                                unusedIon + "unknown" + chargeAsString + neturalLossTag));
                    }

                    allAnnotations.put((sequence + "_" + new Float(tempMSHit.MSHits_pvalue)), currentAnnotations);

                    // only add the annotations for the first identification
                    if (allAnnotations.size() == 1) {
                        // add the ion coverage annotations to the spectrum panel
                        spectrumPanel.setAnnotations(filterAnnotations(currentAnnotations));
                        spectrumPanel.validate();
                        spectrumPanel.repaint();
                    }
                }

                // add the ion coverage to the modified sequence
                int[][] ionCoverageProcessed = new int[sequence.length()][2];

                for (int i = 1; i < ionCoverage.length; i++) {
                    if (ionCoverage[i][0] > 0 && ionCoverage[i - 1][0] > 0) {
                        ionCoverageProcessed[i][0] = 1;
                    } else {
                        ionCoverageProcessed[i][0] = 0;
                    }

                    if (ionCoverage[i][1] > 0 && ionCoverage[i - 1][1] > 0) {
                        ionCoverageProcessed[ionCoverage.length - 1 - i][1] = 1;
                    } else {
                        ionCoverageProcessed[ionCoverage.length - 1 - i][1] = 0;
                    }
                }

                String modifiedSequenceColorCoded = "<html><font color=\"black\">";

                // add nTerminal
                if (!nTerminal.startsWith("<")) {
                    modifiedSequenceColorCoded += nTerminal;
                } else {
                    modifiedSequenceColorCoded += "&lt;";
                    modifiedSequenceColorCoded += nTerminal.substring(1, nTerminal.length() - 2);
                    modifiedSequenceColorCoded += "&gt;-";
                }

                int aminoAcidCounter = 0;

                for (int i = 0; i < modifiedSequence.length(); i++) {

                    if (modifiedSequence.charAt(i) == '<') {

                        if (ionCoverageProcessed[aminoAcidCounter - 1][0] > 0) {
                            // b ions
                            modifiedSequenceColorCoded += "<u>";
                        }

                        if (ionCoverageProcessed[aminoAcidCounter - 1][1] > 0) {
                            // y ions
                            modifiedSequenceColorCoded += "<font color=\"red\">";
                        }

                        modifiedSequenceColorCoded += "&lt;";
                        i++;

                        while (modifiedSequence.charAt(i) != '>') {
                            modifiedSequenceColorCoded += modifiedSequence.charAt(i++);
                        }

                        modifiedSequenceColorCoded += "&gt;";

                        if (ionCoverageProcessed[aminoAcidCounter - 1][0] > 0) {
                            // b ions
                            modifiedSequenceColorCoded += "</u>";
                        }
                        if (ionCoverageProcessed[aminoAcidCounter - 1][1] > 0) {
                            // y ions
                            modifiedSequenceColorCoded += "</font>";
                        }
                    } else {

                        if (ionCoverageProcessed[aminoAcidCounter][0] > 0) {
                            // b ions
                            modifiedSequenceColorCoded += "<u>";
                        }

                        if (ionCoverageProcessed[aminoAcidCounter][1] > 0) {
                            // y ions
                            modifiedSequenceColorCoded += "<font color=\"red\">";
                        }

                        modifiedSequenceColorCoded += modifiedSequence.charAt(i);

                        if (ionCoverageProcessed[aminoAcidCounter][0] > 0) {
                            // b ions
                            modifiedSequenceColorCoded += "</u>";
                        }
                        if (ionCoverageProcessed[aminoAcidCounter][1] > 0) {
                            // y ions
                            modifiedSequenceColorCoded += "</font>";
                        }

                        aminoAcidCounter++;
                    }

                    modifiedSequenceColorCoded += "<font color=\"black\">";
                }

                // add cTerminal
                if (!cTerminal.startsWith("-<")) {
                    modifiedSequenceColorCoded += cTerminal;
                } else {
                    modifiedSequenceColorCoded += "-&lt;";
                    modifiedSequenceColorCoded += cTerminal.substring(2, cTerminal.length() - 1);
                    modifiedSequenceColorCoded += "&gt;";
                }

                modifiedSequenceColorCoded += "</font></html>";

                List<MSPepHit> pepHits = tempMSHit.MSHits_pephits.MSPepHit;

                Iterator<MSPepHit> pepHitIterator = pepHits.iterator();

                while (pepHitIterator.hasNext()) {

                    MSPepHit tempPepHit = pepHitIterator.next();

                    // parse the header
                    String accession;
                    String description;

                    if (tempPepHit.MSPepHit_accession.startsWith("BL_ORD_ID:")) {
                        Header header = Header.parseFromFASTA(tempPepHit.MSPepHit_defline);
                        accession = header.getAccession();
                        description = header.getDescription();
                    } else {
                        accession = tempPepHit.MSPepHit_accession;
                        description = tempPepHit.MSPepHit_defline;
                    }

                    ((DefaultTableModel) identificationsJTable.getModel()).addRow(new Object[]{
                                msHitSet.MSHitSet_number,
                                sequence,
                                modifiedSequenceColorCoded,
                                tempPepHit.MSPepHit_start,
                                tempPepHit.MSPepHit_stop,
                                new Double(((double) tempMSHit.MSHits_mass) / omssaResponseScale),
                                new Double(((double) tempMSHit.MSHits_theomass) / omssaResponseScale),
                                new Float(tempMSHit.MSHits_evalue),
                                new Float(tempMSHit.MSHits_pvalue),
                                accession,
                                description
                            });
                }
            }

            if (modificationDetails.endsWith(", ")) {
                modificationDetails = modificationDetails.substring(0, modificationDetails.length() - 2);
            }

            if (modificationDetails.length() > 0) {
                ptmsJTextField.setText(modificationDetails);
            } else {
                legendJLabel.setText(ionCoverageLegend);
            }
            
            legendJLabel.setText(ionCoverageLegend);

            if (evt != null && evt.getButton() == MouseEvent.BUTTON3) {
                copySpectraJPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }

        if (identificationsJTable.getRowCount() > 1) {
            identificationsJTable.setRowSelectionInterval(0, 0);
        }

        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_spectraJTableMouseClicked

    private void spectrumJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spectrumJTableMouseClicked
        if (spectrumJTable.getSelectedRow() != -1 && evt.getButton() == MouseEvent.BUTTON3) {
            copySpectrumJPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_spectrumJTableMouseClicked

    /**
     * Updates the ion coverage relative in the spectrum to the selected
     * identification.
     *
     * Right clicking opens a popup menu.
     *
     * @param evt
     */
    private void identificationsJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_identificationsJTableMouseClicked
        if (identificationsJTable.getSelectedRow() != -1) {

            if (evt.getButton() == MouseEvent.BUTTON3) {
                copyIdentificationsJPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
            } else {

                if (identificationsJTable.getRowCount() > 1) {

                    Vector<DefaultSpectrumAnnotation> currentAnnotations = allAnnotations.get(
                            identificationsJTable.getValueAt(identificationsJTable.getSelectedRow(), 1) + "_"
                            + identificationsJTable.getValueAt(identificationsJTable.getSelectedRow(), 8));

                    // update the ion coverage annotations
                    spectrumPanel.setAnnotations(filterAnnotations(currentAnnotations));
                    spectrumPanel.validate();
                    spectrumPanel.repaint();
                }
            }
        }
    }//GEN-LAST:event_identificationsJTableMouseClicked

    /**
     * Updates the ion coverage relative in the spectrum to the selected
     * identification.
     *
     * @param evt
     */
    private void identificationsJTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_identificationsJTableKeyReleased
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

        // update the ion coverage annotations
        spectrumPanel.setAnnotations(filterAnnotations(allAnnotations.get(
                identificationsJTable.getValueAt(identificationsJTable.getSelectedRow(), 1) + "_"
                + identificationsJTable.getValueAt(identificationsJTable.getSelectedRow(), 8))));
        spectrumPanel.validate();
        spectrumPanel.repaint();

        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_identificationsJTableKeyReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox aIonsJCheckBox;
    private javax.swing.JMenuItem aboutJMenuItem;
    private javax.swing.ButtonGroup accesionDetailsButtonGroup;
    private javax.swing.JCheckBox bIonsJCheckBox;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JCheckBox cIonsJCheckBox;
    private javax.swing.JCheckBox chargeOneJCheckBox;
    private javax.swing.JCheckBox chargeOverTwoJCheckBox;
    private javax.swing.JCheckBox chargeTwoJCheckBox;
    private javax.swing.JMenuItem copyIdentificationsJMenuItem;
    private javax.swing.JPopupMenu copyIdentificationsJPopupMenu;
    private javax.swing.JMenuItem copySpectraJMenuItem;
    private javax.swing.JPopupMenu copySpectraJPopupMenu;
    private javax.swing.JMenuItem copySpectrumJMenuItem;
    private javax.swing.JPopupMenu copySpectrumJPopupMenu;
    private javax.swing.JMenuItem exitJMenuItem;
    private javax.swing.JMenuItem exportAllIdentificationsJMenuItem;
    private javax.swing.JMenuItem exportAllSpectraJMenuItem;
    private javax.swing.JMenuItem exportBestIdentificationsJMenuItem;
    private javax.swing.JMenu exportJMenu;
    private javax.swing.JMenuItem exportSelectedSpectrumJMenuItem;
    private javax.swing.JMenuItem exportSpectraFilesTableJMenuItem;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JMenu helpJMenu;
    private javax.swing.JMenuItem helpJMenuItem;
    private javax.swing.JScrollPane identificationsJScrollPane;
    private javax.swing.JTable identificationsJTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel legendJLabel;
    private javax.swing.JMenuItem openJMenuItem;
    private javax.swing.JTextField ptmsJTextField;
    private javax.swing.JScrollPane spectraJScrollPane;
    private javax.swing.JTable spectraJTable;
    private javax.swing.JPanel spectrumJPanel;
    private javax.swing.JScrollPane spectrumJScrollPane;
    private javax.swing.JTable spectrumJTable;
    private javax.swing.JCheckBox xIonsJCheckBox;
    private javax.swing.JCheckBox yIonsJCheckBox;
    private javax.swing.JCheckBox zIonsJCheckBox;
    // End of variables declaration//GEN-END:variables
}
