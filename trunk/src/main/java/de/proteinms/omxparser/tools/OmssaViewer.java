package de.proteinms.omxparser.tools;

import be.proteomics.util.gui.spectrum.DefaultSpectrumAnnotation;
import be.proteomics.util.gui.spectrum.SpectrumPanel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyKrupp;
import de.proteinms.omxparser.OmssaOmxFile;
import de.proteinms.omxparser.util.MSHitSet;
import de.proteinms.omxparser.util.MSHits;
import de.proteinms.omxparser.util.MSMZHit;
import de.proteinms.omxparser.util.MSModHit;
import de.proteinms.omxparser.util.MSPepHit;
import de.proteinms.omxparser.util.MSSpectrum;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * A simply viewer for OMMSA omx files to show how the omssa-parser library
 * can be used.
 *
 * @author Harald Barsnes
 *
 * Created December 2008.
 */
public class OmssaViewer extends javax.swing.JFrame {

    private OmssaOmxFile omssaOmxFile;
    private ProgressDialog progressDialog;
    private String omxFile,  modsFile,  userModsFile;
    private HashMap<Integer, List<Integer>> allMzValues;
    private HashMap<Integer, List<Integer>> allAbundanceValues;
    private HashMap<MSSpectrum, MSHitSet> spectrumHitSetMap;
    private HashMap<Integer, MSSpectrum> spectra;
    private Vector spectraJTableColumnToolTips;
    private Vector spectrumJTableColumnToolTips;
    private Vector identificationsJTableColumnToolTips;
    /**
     * The MSSearchSettings_msmstol used in the omx file
     */
    private double ionCoverageErrorMargin;
    /**
     * The MSResponse_scale used in the omx file.
     */
    private int omssaScale;
    /**
     * The list of ionstypes used in the omx file.
     */
    private List<Integer> usedIonTypes;
    private String ionCoverageLegend = "Ion Coverage: b-ions underlined, y-ions red font";
    private String lastSelectedFolder = "user.home";
    private String ommsaViewerVersion = "v1.0";
    private static String ommsaParserVersion = "0.9.4";
    private static boolean useErrorLog = true;


    /**
     * First checks if a newer version of the omssa-parser is available,
     * then creates an error log file (if useErrorLog == true) and finally
     * opens the OmssaViewerFileSelection dialog.
     *
     * @param args
     */
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                try {
                    PlasticLookAndFeel.setPlasticTheme(new SkyKrupp());
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }

                // check if a newer version of the omssa-parser is available
                try {
                    URL downloadPage = new URL(
                            "http://code.google.com/p/omssa-parser/downloads/detail?name=omssaparser-" +
                            ommsaParserVersion + ".zip");
                    int respons =
                            ((java.net.HttpURLConnection) downloadPage.openConnection()).getResponseCode();

                    // 404 means that the file no longer exists, which means that
                    // the running version is no longer available for download,
                    // which again means that a never version is available.
                    if (respons == 404) {
                        int option = JOptionPane.showConfirmDialog(null,
                                "A newer version of the omssa-parser is available. \n" +
                                "Do you want to upgrade?\n\n" +
                                "Selecting \'Yes\' will open the omssa-parser web page\n" +
                                "where you can download the latest version.",
                                "omssa-parser - Upgrade Available",
                                JOptionPane.YES_NO_CANCEL_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            Desktop.getDesktop().browse(new URI("http://code.google.com/p/omssa-parser/"));
                            System.exit(0);
                        } else if (option == JOptionPane.CANCEL_OPTION) {
                            System.exit(0);
                        }
                    }
                } catch (MalformedURLException e) {
                    //e.printStackTrace();
                } catch (IOException e) {
                    //e.printStackTrace();
                } catch (URISyntaxException e) {
                    //e.printStackTrace();
                }

                // creates the error log file
                if (useErrorLog) {
                    try {
                        String path = "" + this.getClass().getProtectionDomain().getCodeSource().getLocation();
                        path = path.substring(5, path.lastIndexOf("/")) + File.separator + "Properties/ErrorLog.txt";
                        path = path.replace("%20", " ");

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
                        Util.writeToErrorLog("Error when creating ErrorLog!!! " +
                                e.toString());
                    }
                }

                new OmssaViewerFileSelection(null, true, null, null, null, "user.home");
            }
        });
    }

    /**
     * Creates new OmssaViewer frame, makes it visible and starts parsing the input files.
     *
     * @param aOmxFile the OMSSA omx file to parse
     * @param aModsFile the mods.xml file
     * @param aUserModsFile the usermods.xml file
     * @param lastSelectedFolder the last selected folder
     */
    public OmssaViewer(String aOmxFile, String aModsFile, String aUserModsFile, String lastSelectedFolder) {
        initComponents();

        // sets the frames icon image
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().
                getResource("/de/proteinms/omxparser/icons/omssaviewer.GIF")));

        // sets the column sizes
        spectraJTable.getColumn(" ").setMaxWidth(40);
        spectraJTable.getColumn(" ").setMinWidth(40);
        spectraJTable.getColumn("m/z").setMaxWidth(65);
        spectraJTable.getColumn("m/z").setMinWidth(65);
        spectraJTable.getColumn("Charge").setMaxWidth(65);
        spectraJTable.getColumn("Charge").setMinWidth(65);
        spectraJTable.getColumn("Identified").setMaxWidth(80);
        spectraJTable.getColumn("Identified").setMinWidth(80);

        spectrumJTable.getColumn(" ").setMaxWidth(40);
        spectrumJTable.getColumn(" ").setMinWidth(40);

        identificationsJTable.getColumn(" ").setMaxWidth(40);
        identificationsJTable.getColumn(" ").setMinWidth(40);
        identificationsJTable.getColumn("Start").setMaxWidth(45);
        identificationsJTable.getColumn("Start").setMinWidth(45);
        identificationsJTable.getColumn("End").setMaxWidth(45);
        identificationsJTable.getColumn("End").setMinWidth(45);
        identificationsJTable.getColumn("E-value").setPreferredWidth(10);
        identificationsJTable.getColumn("P-value").setPreferredWidth(10);
        identificationsJTable.getColumn("Accession").setPreferredWidth(10);

        // adds auto row sorters
        spectraJTable.setAutoCreateRowSorter(true);
        spectrumJTable.setAutoCreateRowSorter(true);
        identificationsJTable.setAutoCreateRowSorter(true);

        // diables column reordering
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
        spectrumJTableColumnToolTips.add("Charge");

        identificationsJTableColumnToolTips = new Vector();
        identificationsJTableColumnToolTips.add("HitSet Number");
        identificationsJTableColumnToolTips.add("Peptide Sequence");
        identificationsJTableColumnToolTips.add("Modified Peptide Sequence");
        identificationsJTableColumnToolTips.add("E-value");
        identificationsJTableColumnToolTips.add("P-value");
        identificationsJTableColumnToolTips.add("Peptide Start Index");
        identificationsJTableColumnToolTips.add("Peptide End Index");
        identificationsJTableColumnToolTips.add("Protein Accession Number");
        identificationsJTableColumnToolTips.add("Protein Definition");

        setLocationRelativeTo(null);
        setVisible(true);

        insertOmxFile(aOmxFile, aModsFile, aUserModsFile, lastSelectedFolder);
    }

    /**
     * Parses the given omx file (and modification files) and inserts the details
     * into the OMSSA Viewer tables.
     *
     * @param aOmxFile the OMSSA omx file to parse
     * @param aModsFile the mods.xml file
     * @param aUserModsFile the usermods.xml file
     * @param lastSelectedFolder the last selected folder
     */
    public void insertOmxFile(String aOmxFile, String aModsFile, String aUserModsFile, String lastSelectedFolder) {

        setTitle("OMSSA Viewer " + ommsaViewerVersion + "  -  [" + new File(aOmxFile).getPath() + "]");

        this.lastSelectedFolder = lastSelectedFolder;

        omxFile = aOmxFile;
        modsFile = aModsFile;
        userModsFile = aUserModsFile;

        progressDialog = new ProgressDialog(this, true);

        final Thread t = new Thread(new Runnable() {

            public void run() {
                progressDialog.setTitle("Parsing OMX File. Please wait.");
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

                modificationDetailsJLabel.setText("");

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
                            "The task used up all the available memory and had to be stopped.\n" +
                            "Memory boundaries are set in ../Properties/JavaOptions.txt.",
                            "Out of Memory Error",
                            JOptionPane.ERROR_MESSAGE);
                    Util.writeToErrorLog("OMSSA Viewer: Ran out of memory!");
                    error.printStackTrace();
                    System.exit(0);
                }


                // extract MSSearchSettings_msmstol
                ionCoverageErrorMargin =
                        omssaOmxFile.getParserResult().MSSearch_request.MSRequest.get(0).MSRequest_settings.MSSearchSettings.MSSearchSettings_msmstol;

                // extract MSResponse_scal
                omssaScale =
                        omssaOmxFile.getParserResult().MSSearch_response.MSResponse.get(0).MSResponse_scale;

                // extract the ion types used
                usedIonTypes =
                        omssaOmxFile.getParserResult().MSSearch_request.MSRequest.get(0).MSRequest_settings.MSSearchSettings.MSSearchSettings_ionstosearch.MSIonType;

                // iterate the spectrum to hitset map
                // extract and store details about the spectra
                spectrumHitSetMap = omssaOmxFile.getSpectrumToHitSetMap();
                Iterator<MSSpectrum> iterator = spectrumHitSetMap.keySet().iterator();

                allMzValues = new HashMap<Integer, List<Integer>>();
                allAbundanceValues = new HashMap<Integer, List<Integer>>();
                spectra = new HashMap<Integer, MSSpectrum>();

                while (iterator.hasNext()) {

                    MSSpectrum tempSpectrum = iterator.next();

                    spectra.put(new Integer(tempSpectrum.MSSpectrum_number), tempSpectrum);

                    // OMSSA question: possible with more than one file name per spectrum??
                    String fileName = tempSpectrum.MSSpectrum_ids.MSSpectrum_ids_E.get(0);

                    String chargeString = "" + tempSpectrum.MSSpectrum_charge.MSSpectrum_charge_E.get(0);
                    chargeString = chargeString.replaceFirst("\\+", "");

                    allMzValues.put(new Integer(tempSpectrum.MSSpectrum_number),
                            tempSpectrum.MSSpectrum_mz.MSSpectrum_mz_E);
                    allAbundanceValues.put(new Integer(tempSpectrum.MSSpectrum_number),
                            tempSpectrum.MSSpectrum_abundance.MSSpectrum_abundance_E);

                    boolean identified = false;

                    MSHitSet msHitSet = spectrumHitSetMap.get(tempSpectrum);
                    List<MSHits> allMSHits = msHitSet.MSHitSet_hits.MSHits;

                    if (allMSHits.size() > 0) {
                        identified = true;
                    }

                    ((DefaultTableModel) spectraJTable.getModel()).addRow(new Object[]{
                                new Integer(tempSpectrum.MSSpectrum_number),
                                fileName,
                                ((double) tempSpectrum.MSSpectrum_precursormz) / omssaScale,
                                chargeString,
                                new Boolean(identified)
                            });
                }

                progressDialog.setVisible(false);
                progressDialog.dispose();
            }
        }.start();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        spectraJTable = new JTable() {
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(columnModel) {
                    public String getToolTipText(MouseEvent e) {
                        String tip = null;
                        java.awt.Point p = e.getPoint();
                        int index = columnModel.getColumnIndexAtX(p.x);
                        int realIndex = columnModel.getColumn(index).getModelIndex();
                        tip = (String) spectraJTableColumnToolTips.get(realIndex);
                        return tip;
                    }
                };
            }
        };
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        identificationsJTable = new JTable() {
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(columnModel) {
                    public String getToolTipText(MouseEvent e) {
                        String tip = null;
                        java.awt.Point p = e.getPoint();
                        int index = columnModel.getColumnIndexAtX(p.x);
                        int realIndex = columnModel.getColumn(index).getModelIndex();
                        tip = (String) identificationsJTableColumnToolTips.get(realIndex);
                        return tip;
                    }
                };
            }
        };
        modificationDetailsJLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        spectrumJScrollPane = new javax.swing.JScrollPane();
        spectrumJTable = new JTable() {
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(columnModel) {
                    public String getToolTipText(MouseEvent e) {
                        String tip = null;
                        java.awt.Point p = e.getPoint();
                        int index = columnModel.getColumnIndexAtX(p.x);
                        int realIndex = columnModel.getColumn(index).getModelIndex();
                        tip = (String) spectrumJTableColumnToolTips.get(realIndex);
                        return tip;
                    }
                };
            }
        };
        spectrumJPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        openJMenuItem = new javax.swing.JMenuItem();
        exitJMenuItem = new javax.swing.JMenuItem();
        exportJMenu = new javax.swing.JMenu();
        exportSpectraJMenuItem = new javax.swing.JMenuItem();
        exportSpectrumJMenuItem = new javax.swing.JMenuItem();
        exportIdentificationsJMenuItem = new javax.swing.JMenuItem();
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Spectra Files", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0))); // NOI18N

        spectraJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                " ", "Filename", "m/z", "Charge", "Identified"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Boolean.class
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
        jScrollPane1.setViewportView(spectraJTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Identifications", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0))); // NOI18N

        identificationsJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                " ", "Sequence", "Modified Sequence", "E-value", "P-value", "Start", "End", "Accession", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
        jScrollPane2.setViewportView(identificationsJTable);

        modificationDetailsJLabel.setFont(modificationDetailsJLabel.getFont().deriveFont((modificationDetailsJLabel.getFont().getStyle() | java.awt.Font.ITALIC)));

        jLabel1.setFont(jLabel1.getFont().deriveFont((jLabel1.getFont().getStyle() | java.awt.Font.ITALIC)));
        jLabel1.setText("Legend:   ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1145, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(modificationDetailsJLabel)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(modificationDetailsJLabel)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Spectrum", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0))); // NOI18N

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

        spectrumJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        spectrumJPanel.setLayout(new javax.swing.BoxLayout(spectrumJPanel, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(spectrumJPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                    .addComponent(spectrumJScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(spectrumJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spectrumJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        fileJMenu.setMnemonic('F');
        fileJMenu.setText("File");

        openJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        openJMenuItem.setMnemonic('O');
        openJMenuItem.setText("Open");
        openJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(openJMenuItem);

        exitJMenuItem.setMnemonic('x');
        exitJMenuItem.setText("Exit");
        exitJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(exitJMenuItem);

        jMenuBar1.add(fileJMenu);

        exportJMenu.setMnemonic('E');
        exportJMenu.setText("Export");

        exportSpectraJMenuItem.setMnemonic('P');
        exportSpectraJMenuItem.setText("Spectra Properties");
        exportSpectraJMenuItem.setEnabled(false);
        exportSpectraJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportSpectraJMenuItemActionPerformed(evt);
            }
        });
        exportJMenu.add(exportSpectraJMenuItem);

        exportSpectrumJMenuItem.setMnemonic('S');
        exportSpectrumJMenuItem.setText("Spectrum");
        exportSpectrumJMenuItem.setEnabled(false);
        exportSpectrumJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportSpectrumJMenuItemActionPerformed(evt);
            }
        });
        exportJMenu.add(exportSpectrumJMenuItem);

        exportIdentificationsJMenuItem.setMnemonic('I');
        exportIdentificationsJMenuItem.setText("Identifications");
        exportIdentificationsJMenuItem.setEnabled(false);
        exportIdentificationsJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportIdentificationsJMenuItemActionPerformed(evt);
            }
        });
        exportJMenu.add(exportIdentificationsJMenuItem);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
     * When the user selects a row in the spectra files table, the identifications (if any)
     * are inserted in the identification table at the bottom of the frame, and the spectrum
     * details are shown in the spectrum panel and table to the right.
     *
     * @param evt
     */
    private void spectraJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spectraJTableMouseClicked

        this.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

        int row = spectraJTable.getSelectedRow();

        if (row != -1) {
            List<Integer> mzValues = allMzValues.get((Integer) spectraJTable.getValueAt(row, 0));
            List<Integer> abundanceValues = allAbundanceValues.get((Integer) spectraJTable.getValueAt(row, 0));

            // empty the spectrum table
            while (spectrumJTable.getRowCount() > 0) {
                ((DefaultTableModel) spectrumJTable.getModel()).removeRow(0);
            }

            // scrolls the scrollbar to the top of the table
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
                            ((double) mzValues.get(i)) / omssaScale,
                            ((double) abundanceValues.get(i)) / omssaScale
                        });

                mzValuesAsDouble[i] = ((double) mzValues.get(i)) / omssaScale;
                abundanceValuesAsDouble[i] = ((double) abundanceValues.get(i)) / omssaScale;
            }

            // updates the spectrum panel
            SpectrumPanel spectrumPanel = new SpectrumPanel(
                    mzValuesAsDouble,
                    abundanceValuesAsDouble,
                    ((Double) spectraJTable.getValueAt(row, 2)),
                    "" + spectraJTable.getValueAt(row, 3),
                    ((String) spectraJTable.getValueAt(row, 1)));

            spectrumJPanel.add(spectrumPanel);
            spectrumJPanel.validate();
            spectrumJPanel.repaint();

            // empty the identification table
            while (identificationsJTable.getRowCount() > 0) {
                ((DefaultTableModel) identificationsJTable.getModel()).removeRow(0);
            }

            // clear the modification details legend
            modificationDetailsJLabel.setText("");

            // get the list of fixed modifications
            List<Integer> fixedModifications =
                    omssaOmxFile.getParserResult().MSSearch_request.MSRequest.get(0).MSRequest_settings.MSSearchSettings.MSSearchSettings_fixed.MSMod;

            // iterate all the identifications and insert them into the identification table
            MSHitSet msHitSet = spectrumHitSetMap.get(spectra.get((Integer) spectraJTable.getValueAt(row, 0)));

            List<MSHits> allMSHits = msHitSet.MSHitSet_hits.MSHits;
            Iterator<MSHits> msHitIterator = allMSHits.iterator();

            while (msHitIterator.hasNext()) {

                MSHits tempMSHit = msHitIterator.next();

                String sequence = tempMSHit.MSHits_pepstring;
                String[] modifications = new String[sequence.length()];

                for (int i = 0; i < modifications.length; i++) {
                    modifications[i] = "";
                }

                String modifiedSequence = "";

                // fixed modifications
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

                    String modificationDetails = "";

                    for (int i = 0; i < modifications.length; i++) {
                        modifiedSequence += sequence.substring(i, i + 1) + modifications[i];

                        if (!modifications[i].equalsIgnoreCase("")) {

                            if (modificationDetails.lastIndexOf(modifications[i]) == -1) {

                                modificationDetails += modifications[i] + " " +
                                        omssaOmxFile.getModifications().get(
                                        new Integer(modifications[i].substring(1, 2))).getModName() +
                                        " (" + omssaOmxFile.getModifications().get(
                                        new Integer(modifications[i].substring(1, 2))).getModMonoMass() + "), ";
                            }
                        }
                    }

                    if (modificationDetails.endsWith(", ")) {
                        modificationDetails = modificationDetails.substring(0, modificationDetails.length() - 2);
                    }

                    if (modificationDetails.length() > 0) {
                        modificationDetailsJLabel.setText("Modifications: " + modificationDetails +
                                "    /    " + ionCoverageLegend);
                    } else {
                        modificationDetailsJLabel.setText(ionCoverageLegend);
                    }
                } else {
                    modificationDetailsJLabel.setText("Modifications: (Files with modification details were not provided. " +
                            "No modifications are shown.)" + "    /    " + ionCoverageLegend);
                    modifiedSequence = sequence;
                }

                // add ion coverage to peptide sequence
                Iterator<MSMZHit> mzHits = tempMSHit.MSHits_mzhits.MSMZHit.iterator();

                Vector annotations = new Vector();

                // annotate precursor ion
//                annotations.add(new DefaultSpectrumAnnotation(
//                            ((Double) spectraJTable.getValueAt(spectraJTable.getSelectedRow(), 2)).doubleValue(),
//                            ionCoverageErrorMargin, Color.GRAY, "pre"));

                int[][] ionCoverage = new int[sequence.length()][2];

                while (mzHits.hasNext()) {
                    MSMZHit tempMzHit = mzHits.next();

                    int ionType = tempMzHit.MSMZHit_ion.MSIonType;
                    //int charge = tempMzHit.MSMZHit_charge;
                    int ionNumber = tempMzHit.MSMZHit_number;
                    double mzValue = ((double) tempMzHit.MSMZHit_mz) / omssaScale;

                    String label = "";

                    if (!usedIonTypes.contains(new Integer(ionType))) {
                        label = "#";
                    }

                    // Note: assumes that 0 is a, 1 is b, 2 is c, 3 is x, 4 is y and 5 is z
                    if (ionType == 0) {
                        annotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.BLUE, label + "a" + (ionNumber + 1)));
                    } else if (ionType == 1) {
                        annotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.BLUE, label + "b" + (ionNumber + 1)));
                        ionCoverage[ionNumber][0]++;
                    } else if (ionType == 2) {
                        annotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.BLUE, label + "c" + (ionNumber + 1)));
                    } else if (ionType == 3) {
                        annotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.BLACK, label + "x" + (ionNumber + 1)));
                    } else if (ionType == 4) {
                        annotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.BLACK, label + "y" + (ionNumber + 1)));
                        ionCoverage[ionNumber][1]++;
                    } else if (ionType == 5) {
                        annotations.add(new DefaultSpectrumAnnotation(
                                mzValue, ionCoverageErrorMargin, Color.BLACK, label + "z" + (ionNumber + 1)));
                    }

                    // add the ion coverage annotations to the spectrum panel
                    spectrumPanel.setAnnotations(annotations);
                    spectrumPanel.validate();
                    spectrumPanel.repaint();
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

                // Note: N-terminal always hardcoded to NH2...
                String modifiedSequenceColorCoded = "<html>NH2-";

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
                        modifiedSequenceColorCoded += modifiedSequence.charAt(i + 1);
                        modifiedSequenceColorCoded += "&gt;";
                        i += 2;

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

                // Note: C-terminal always hardcoded to COOH...
                modifiedSequenceColorCoded += "-COOH</html>";

                List<MSPepHit> pepHits = tempMSHit.MSHits_pephits.MSPepHit;

                Iterator<MSPepHit> pepHitIterator = pepHits.iterator();

                while (pepHitIterator.hasNext()) {

                    MSPepHit tempPepHit = pepHitIterator.next();

                    ((DefaultTableModel) identificationsJTable.getModel()).addRow(new Object[]{
                                msHitSet.MSHitSet_number,
                                sequence,
                                modifiedSequenceColorCoded,
                                tempMSHit.MSHits_evalue,
                                tempMSHit.MSHits_pvalue,
                                tempPepHit.MSPepHit_start,
                                tempPepHit.MSPepHit_stop,
                                tempPepHit.MSPepHit_accession,
                                tempPepHit.MSPepHit_defline
                            });
                }
            }

            if (evt != null && evt.getButton() == MouseEvent.BUTTON3) {
                copySpectraJPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }

        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_spectraJTableMouseClicked

    /**
     * See spectraJTableMouseClicked
     */
    private void spectraJTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spectraJTableKeyReleased
        spectraJTableMouseClicked(null);
    }//GEN-LAST:event_spectraJTableKeyReleased

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
     * See copySpectraJMenuItemActionPerformed
     */
    private void copySpectrumJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copySpectrumJMenuItemActionPerformed
        TransferHandler th = spectrumJTable.getTransferHandler();
        if (th != null) {
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            th.exportToClipboard(spectrumJTable, cb, TransferHandler.COPY);
        }
}//GEN-LAST:event_copySpectrumJMenuItemActionPerformed

    /**
     * See copySpectraJMenuItemActionPerformed
     */
    private void copyIdentificationsJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyIdentificationsJMenuItemActionPerformed
        TransferHandler th = identificationsJTable.getTransferHandler();
        if (th != null) {
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            th.exportToClipboard(identificationsJTable, cb, TransferHandler.COPY);
        }
}//GEN-LAST:event_copyIdentificationsJMenuItemActionPerformed

    /**
     * Opens a popup menu if the user right clicks in the spectrum table.
     *
     * @param evt
     */
    private void spectrumJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spectrumJTableMouseClicked
        if (spectrumJTable.getSelectedRow() != -1 && evt.getButton() == MouseEvent.BUTTON3) {
            copySpectrumJPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_spectrumJTableMouseClicked

    /**
     * Opens a popup menu if the user right clicks in the identification table.
     *
     * @param evt
     */
    private void identificationsJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_identificationsJTableMouseClicked
        if (identificationsJTable.getSelectedRow() != -1 && evt.getButton() == MouseEvent.BUTTON3) {
            copyIdentificationsJPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_identificationsJTableMouseClicked

    /**
     * Opens a OmssaViewerFileSelection dialog for opening a different omx file.
     *
     * @param evt
     */
    private void openJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openJMenuItemActionPerformed
        new OmssaViewerFileSelection(this, true, omxFile, modsFile, userModsFile, lastSelectedFolder);
    }//GEN-LAST:event_openJMenuItemActionPerformed

    /**
     * Exports the contents of the spectra files table to a cvs file.
     *
     * @param evt
     */
    private void exportSpectraJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportSpectraJMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exportSpectraJMenuItemActionPerformed

    /**
     * Exports the contents of the identification table to a cvs file.
     *
     * @param evt
     */
    private void exportSpectrumJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportSpectrumJMenuItemActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_exportSpectrumJMenuItemActionPerformed

    /**
     * Exports the contents of the spectrum table to a cvs file.
     *
     * @param evt
     */
    private void exportIdentificationsJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportIdentificationsJMenuItemActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_exportIdentificationsJMenuItemActionPerformed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutJMenuItem;
    private javax.swing.JMenuItem copyIdentificationsJMenuItem;
    private javax.swing.JPopupMenu copyIdentificationsJPopupMenu;
    private javax.swing.JMenuItem copySpectraJMenuItem;
    private javax.swing.JPopupMenu copySpectraJPopupMenu;
    private javax.swing.JMenuItem copySpectrumJMenuItem;
    private javax.swing.JPopupMenu copySpectrumJPopupMenu;
    private javax.swing.JMenuItem exitJMenuItem;
    private javax.swing.JMenuItem exportIdentificationsJMenuItem;
    private javax.swing.JMenu exportJMenu;
    private javax.swing.JMenuItem exportSpectraJMenuItem;
    private javax.swing.JMenuItem exportSpectrumJMenuItem;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JMenu helpJMenu;
    private javax.swing.JMenuItem helpJMenuItem;
    private javax.swing.JTable identificationsJTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel modificationDetailsJLabel;
    private javax.swing.JMenuItem openJMenuItem;
    private javax.swing.JTable spectraJTable;
    private javax.swing.JPanel spectrumJPanel;
    private javax.swing.JScrollPane spectrumJScrollPane;
    private javax.swing.JTable spectrumJTable;
    // End of variables declaration//GEN-END:variables
}
