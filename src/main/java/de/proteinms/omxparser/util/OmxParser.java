/*
 * Copyright (C) 2008 - Huber Steffen
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"),
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied.
 * 
 * See the License for the specific language governing permissions 
 * and limitations under the License.
 * 
 * 
 * 
 * Contact: 
 * s4990348@mail.inf.tu-dresden.de
 */
package de.proteinms.omxparser.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EmptyStackException;
import java.util.HashMap;

import java.util.Stack;

import java.util.Vector;
import java.util.zip.GZIPInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class contains the OmxParser using a XML Pull Parser API
 * implementation<br> (in this case XPP3/MXP1). <br><br> It stores the
 * information from the specified Omx File into the Attribute "parserResult"<br>
 * The information structure is very similar to the XML structure defined in
 * "OMSSA.xsd". See <a
 * href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>.
 * <br><br> Every originally XML Tag is now represented as a name-corresponding
 * Object, except<br> the XML Tags which hold information, they are represented
 * by an Attribute inside<br> the corresponding Object.
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class OmxParser {

    /**
     * Default encoding, cf the second rule.
     */
    public static final String ENCODING = "UTF-8";
    /**
     * Define a static logger variable so that it references the Logger instance
     * named "OmxParser".
     */
    private static Logger logger = Logger.getLogger(OmxParser.class);
    private int indexBuffer[] = new int[2];
    private Stack<Object> objectStack = new Stack<Object>();
    private Stack<String> nameStack = new Stack<String>();
    //to avoid error while parsing unknown XML-subtree/branch like <MSResponse_bioseqs> (not implemented in OMXParser)
    private Stack<Boolean> lockStack = new Stack<Boolean>();
    /**
     * Classes is used by the Parser to lookup classtypes from XML Tags<br>
     * avoiding the use of slow Reflection methods like class.forName()
     */
    private static HashMap<String, Class<?>> classes = new HashMap<String, Class<?>>();
    /**
     * Contains all Data from the original Omx File gathered by the OmxParser
     */
    public MSSearch parserResult;
    private String attribute = "";
    private String value = "";
    /**
     * HashMap of the modification details where the keys are the modification
     * numbers and the elements are OmssaModification-objects
     */
    private HashMap<Integer, OmssaModification> omssaModificationDetails;

    /**
     * If a Class should be parsed by OmxParser, it has to be initialzed by<br>
     * writing it into the HashMap classes
     *
     * @param importSpectra if false, the MSSpectrumset section of the omx file
     * will be skipped
     * @param importIdDetails if false only peptide sequence, modifications and
     * e-values will be imported
     */
    public static void initializeClasses(boolean importSpectra, boolean importIdDetails) {

        classes.put("MSChargeHandle_calccharge", MSChargeHandle_calccharge.class);
        classes.put("MSChargeHandle_calcplusone", MSChargeHandle_calcplusone.class);
        classes.put("MSChargeHandle", MSChargeHandle.class);
        classes.put("MSHits_mods", MSHits_mods.class);
        classes.put("MSHits_scores", MSHits_scores.class);
        classes.put("MSHits", MSHits.class);
        classes.put("MSHitSet_error", MSHitSet_error.class);
        classes.put("MSHitSet_hits", MSHitSet_hits.class);
        classes.put("MSHitSet_ids", MSHitSet_ids.class);
        classes.put("MSHitSet_namevalue", MSHitSet_namevalue.class);
        classes.put("MSHitSet_userannotation", MSHitSet_userannotation.class);
        classes.put("MSHitSet", MSHitSet.class);
        classes.put("MSImmonium", MSImmonium.class);
        classes.put("MSInFile_infiletype", MSInFile_infiletype.class);
        classes.put("MSInFile", MSInFile.class);
        classes.put("MSIon_immonium", MSIon_immonium.class);
        classes.put("MSIon_isotope", MSIon_isotope.class);
        classes.put("MSIon_neutralloss", MSIon_neutralloss.class);
        classes.put("MSIon", MSIon.class);
        classes.put("MSIonAnnot", MSIonAnnot.class);
        classes.put("MSIterativeSettings", MSIterativeSettings.class);
        classes.put("MSLibrarySettings_libnames", MSLibrarySettings_libnames.class);
        classes.put("MSLibrarySettings", MSLibrarySettings.class);
        classes.put("MSMassSet", MSMassSet.class);
        classes.put("MSModHit_modtype", MSModHit_modtype.class);
        classes.put("MSModHit", MSModHit.class);
        classes.put("MSModSpec_mod", MSModSpec_mod.class);
        classes.put("MSModSpec_neutralloss", MSModSpec_neutralloss.class);
        classes.put("MSModSpec_residues", MSModSpec_residues.class);
        classes.put("MSModSpec", MSModSpec.class);
        classes.put("MSModSpecSet", MSModSpecSet.class);
        classes.put("MSOutFile_outfiletype", MSOutFile_outfiletype.class);
        classes.put("MSOutFile", MSOutFile.class);
        classes.put("MSRequest_modset", MSRequest_modset.class);
        classes.put("MSRequest_moresettings", MSRequest_moresettings.class);
        classes.put("MSRequest_settings", MSRequest_settings.class);
        classes.put("MSRequest", MSRequest.class);
        classes.put("MSResponse_error", MSResponse_error.class);
        classes.put("MSResponse_hitsets", MSResponse_hitsets.class);
        classes.put("MSResponse", MSResponse.class);
        classes.put("MSScoreSet", MSScoreSet.class);
        classes.put("MSSearch_request", MSSearch_request.class);
        classes.put("MSSearch_response", MSSearch_response.class);
        classes.put("MSSearch", MSSearch.class);
        classes.put("MSSearchSettings_chargehandling", MSSearchSettings_chargehandling.class);
        classes.put("MSSearchSettings_enzyme", MSSearchSettings_enzyme.class);
        classes.put("MSSearchSettings_fixed", MSSearchSettings_fixed.class);
        classes.put("MSSearchSettings_infiles", MSSearchSettings_infiles.class);
        classes.put("MSSearchSettings_ionstosearch", MSSearchSettings_ionstosearch.class);
        classes.put("MSSearchSettings_iterativesettings", MSSearchSettings_iterativesettings.class);
        classes.put("MSSearchSettings_libsearchsettings", MSSearchSettings_libsearchsettings.class);
        classes.put("MSSearchSettings_noprolineions", MSSearchSettings_noprolineions.class);
        classes.put("MSSearchSettings_othersettings", MSSearchSettings_othersettings.class);
        classes.put("MSSearchSettings_outfiles", MSSearchSettings_outfiles.class);
        classes.put("MSSearchSettings_precursorsearchtype", MSSearchSettings_precursorsearchtype.class);
        classes.put("MSSearchSettings_productsearchtype", MSSearchSettings_productsearchtype.class);
        classes.put("MSSearchSettings_taxids", MSSearchSettings_taxids.class);
        classes.put("MSSearchSettings_usermods", MSSearchSettings_usermods.class);
        classes.put("MSSearchSettings_variable", MSSearchSettings_variable.class);
        classes.put("MSSearchSettings_zdep", MSSearchSettings_zdep.class);
        classes.put("MSSearchSettings", MSSearchSettings.class);
        classes.put("MSSearchSettingsSet", MSSearchSettingsSet.class);
        classes.put("MSSpectrum_namevalue", MSSpectrum_namevalue.class);
        classes.put("NameValue", NameValue.class);
        if (importSpectra) {
            classes.put("MSRequest_spectra", MSRequest_spectra.class);
            classes.put("MSSpectrumset", MSSpectrumset.class);
            classes.put("MSSpectrum", MSSpectrum.class);
            classes.put("MSSpectrum_charge", MSSpectrum_charge.class);
            classes.put("MSSpectrum_mz", MSSpectrum_mz.class);
            classes.put("MSSpectrum_abundance", MSSpectrum_abundance.class);
            classes.put("MSSpectrum_ids", MSSpectrum_ids.class);
        }
        if (importIdDetails) {
            classes.put("MSHits_pephits", MSHits_pephits.class);
            classes.put("MSPepHit", MSPepHit.class);
            classes.put("MSHits_mzhits", MSHits_mzhits.class);
            classes.put("MSMZHit_annotation", MSMZHit_annotation.class);
            classes.put("MSMZHit_ion", MSMZHit_ion.class);
            classes.put("MSMZHit_moreion", MSMZHit_moreion.class);
            classes.put("MSMZHit", MSMZHit.class);
        }
    }

    /**
     * Initializes the parser and parses the omx file. Also parses the
     * modification files (if any).
     *
     * @param omxFile the OMX file
     * @param modsFile the modification file
     * @param userModsFile the user modification file
     */
    public OmxParser(File omxFile, File modsFile, File userModsFile) {
        this(omxFile.getAbsolutePath(), modsFile.getAbsolutePath(), userModsFile.getAbsolutePath(), true);
    }

    /**
     * Initializes the parser and parses the omx file. Also parses the
     * modification files (if any).
     *
     * @param omxFile the OMX file
     * @param modsFile the modification file
     * @param userModsFile the user modification file
     * @param importSpectra if the spectra are to be imported
     */
    public OmxParser(File omxFile, File modsFile, File userModsFile, boolean importSpectra) {
        this(omxFile.getAbsolutePath(), modsFile.getAbsolutePath(), userModsFile.getAbsolutePath(), importSpectra);
    }

    /**
     * Initializes the parser and parses the omx file. Also parses the
     * modification files (if any).
     *
     * @param omxFilePath path to the omx file
     * @param modsFilePath path to the mods.xml file
     * @param userModsFilePath path to the usermods.xml file
     * @param importSpectra boolean indicating whether spectra should be loaded
     */
    public OmxParser(String omxFilePath, String modsFilePath, String userModsFilePath, boolean importSpectra) {
        this(omxFilePath, modsFilePath, userModsFilePath, importSpectra, false);
    }

    /**
     * Initializes the parser and parses the omx file. Also parses the
     * modification files (if any).
     *
     * @param omxFilePath path to the omx file
     * @param modsFilePath path to the mods.xml file
     * @param userModsFilePath path to the usermods.xml file
     * @param importSpectra if false, the MSSpectrumset section of the omx file
     * will be skipped
     * @param importIdDetails if false only peptide sequence, modifications and
     * e-values will be imported
     */
    public OmxParser(
            String omxFilePath,
            String modsFilePath,
            String userModsFilePath,
            boolean importSpectra,
            boolean importIdDetails
    ) {

        File omxFile = null;
        File modsFile = null;
        File userModsFile = null;
        if (omxFilePath != null) {
            omxFile = new File(omxFilePath);
        }
        if (modsFilePath != null) {
            modsFile = new File(modsFilePath);
        }
        if (userModsFilePath != null) {
            userModsFile = new File(userModsFilePath);
        }
        omssaModificationDetails = new HashMap();

        // parse modification files
        if (modsFile != null && modsFile.getAbsolutePath().endsWith(".xml")) {
            parseModificationFile(modsFile);
        }

        if (userModsFile != null && userModsFile.getAbsolutePath().endsWith(".xml")) {
            parseModificationFile(userModsFile);
        }

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
                    System.getProperty(XmlPullParserFactory.PROPERTY_NAME), null);
            //factory.setNamespaceAware(true);
            factory.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);

            XmlPullParser xpp = factory.newPullParser();

            //write every class from which objects should be created into the hashmap classes
            initializeClasses(importSpectra, importIdDetails);

            logger.debug("Parsing file: " + omxFile);

            Reader reader;
            if (omxFile.getName().endsWith(".gz")) {

                InputStream fileStream = new FileInputStream(omxFile);
                InputStream gzipStream = new GZIPInputStream(fileStream);
                Reader decoder = new InputStreamReader(gzipStream, ENCODING);

                reader = new BufferedReader(decoder);

            } else {

                reader = new BufferedReader(new FileReader(omxFile));

            }

            xpp.setInput(reader);
            long t1 = System.currentTimeMillis();
            processDocument(xpp, !importSpectra, !importIdDetails);
            long t2 = System.currentTimeMillis();
            long t3 = (t2 - t1) / 1000;
            logger.debug("finished after " + t3 + " seconds");
        } catch (XmlPullParserException e) {
            logger.error("Error parsing file: " + omxFile + " " + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("Error parsing file: " + omxFile + " " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Initializes the parser and parses the omx file. Also parses the
     * modification files (if any).
     *
     * @param omxFile the OMX file
     * @param modsFile the modification file
     * @param userModsFile the user modification file
     */
    public OmxParser(String omxFile, String modsFile, String userModsFile) {
        this(omxFile, modsFile, userModsFile, true);
    }

    /**
     * Parses a mod.xml or usermod.xml file and builds a HashMap containing the
     * modification details.
     *
     * @param modsFile the path to the mods.xml or usermods.xml file
     */
    private void parseModificationFile(File modsFile) {

        try {
            //get the factory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            dbf.setValidating(false);
            dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            dbf.setAttribute("http://xml.org/sax/features/validation", false);

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            Document dom = db.parse(modsFile);

            //get the root elememt
            Element docEle = dom.getDocumentElement();

            NodeList nodes = docEle.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {

                if (nodes.item(i).getNodeName().equalsIgnoreCase("MSModSpec")) {

                    NodeList modNodes = nodes.item(i).getChildNodes();
                    int modNumber = -1;
                    int modType = 0;
                    String modName = "";
                    Double modMonoMass = 0.0;
                    Vector modResidues = new Vector();

                    for (int j = 0; j < modNodes.getLength(); j++) {

                        if (modNodes.item(j).getNodeName().equalsIgnoreCase("MSModSpec_mod")) {

                            NodeList tempNodes = modNodes.item(j).getChildNodes();

                            for (int m = 0; m < tempNodes.getLength(); m++) {
                                if (tempNodes.item(m).getNodeName().equalsIgnoreCase("MSMod")) {
                                    modNumber = new Integer(tempNodes.item(m).getTextContent());
                                }
                            }
                        } else if (modNodes.item(j).getNodeName().equalsIgnoreCase("MSModSpec_type")) {

                            NodeList tempNodes = modNodes.item(j).getChildNodes();

                            for (int m = 0; m < tempNodes.getLength(); m++) {
                                if (tempNodes.item(m).getNodeName().equalsIgnoreCase("MSModType")) {
                                    modType = new Integer(tempNodes.item(m).getTextContent());
                                }
                            }
                        } else if (modNodes.item(j).getNodeName().equalsIgnoreCase("MSModSpec_name")) {
                            modName = modNodes.item(j).getTextContent();
                        } else if (modNodes.item(j).getNodeName().equalsIgnoreCase("MSModSpec_monomass")) {
                            modMonoMass = new Double(modNodes.item(j).getTextContent());
                        } else if (modNodes.item(j).getNodeName().equalsIgnoreCase("MSModSpec_residues")) {
                            NodeList residueNodes = modNodes.item(j).getChildNodes();

                            modResidues = new Vector();

                            for (int m = 0; m
                                    < residueNodes.getLength(); m++) {

                                if (residueNodes.item(m).getNodeName().equalsIgnoreCase(
                                        "MSModSpec_residues_E")) {

                                    modResidues.add(residueNodes.item(m).getTextContent());
                                }
                            }
                        }
                    }

                    if (modMonoMass == 0.0) {
                        modMonoMass = null;
                    }

                    omssaModificationDetails.put(modNumber,
                            new OmssaModification(modNumber, modName,
                                    modMonoMass, modResidues, modType));
                }
            }
        } catch (Exception e) {
            logger.error("Error parsing the modification file: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Process the document given by the XmlPullParser.
     *
     * @param xpp the XML parser
     * @param skipMSRequest_spectra if true, the MSRequest_spectra section of
     * the omx file will be skipped
     * @param skipPeptideDetails if true, the sections MSHits_pephits,
     * MSHits_mzhits of the omx file will be skipped
     * @throws org.xmlpull.v1.XmlPullParserException if an
     * XmlPullParserException is thrown
     * @throws java.io.IOException if an IOException is thrown
     */
    public void processDocument(XmlPullParser xpp, boolean skipMSRequest_spectra, boolean skipPeptideDetails)
            throws XmlPullParserException, IOException {

        // initialize lockStack:
        lockStack.add(false);

        int eventType;

        while ((eventType = xpp.next()) != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                String name = xpp.getName();
                if (skipMSRequest_spectra && name.equals("MSRequest_spectra")) {
                    while (!(eventType == XmlPullParser.END_TAG && name.equals("MSRequest_spectra"))) {
                        eventType = xpp.next();
                        name = xpp.getName();
                    }
                } else if (skipPeptideDetails && name.equals("MSHits_pephits")) {
                    while (!(eventType == XmlPullParser.END_TAG && name.equals("MSHits_pephits"))) {
                        eventType = xpp.next();
                        name = xpp.getName();
                    }
                } else if (skipPeptideDetails && name.equals("MSHits_mzhits")) {
                    while (!(eventType == XmlPullParser.END_TAG && name.equals("MSHits_mzhits"))) {
                        eventType = xpp.next();
                        name = xpp.getName();
                    }
                } else {
                    processStartElement(xpp, !skipPeptideDetails);
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                processEndElement(xpp, !skipPeptideDetails);
            } else if (eventType == XmlPullParser.TEXT) {
                processText(xpp, !skipPeptideDetails);
            }
        }
    }

    /**
     * Process the end element for the XmlPullParser object.
     *
     * @param xpp the XML parser
     * @param importDetails if true the details will be imported
     */
    public void processEndElement(XmlPullParser xpp, boolean importDetails) {

        if (!objectStack.isEmpty() && (!lockStack.peek())) {

            Object pop = objectStack.pop();

            if (!objectStack.isEmpty()) {

                try {
                    Object peek = objectStack.peek();
                    Class<?> c = peek.getClass();
                    String method = "set" + nameStack.peek();
                    if (importDetails || shallExecute(c, method, pop)) {
                        Method setX = c.getDeclaredMethod(method, pop.getClass());
                        setX.invoke(peek, pop);
                    }
                } catch (EmptyStackException e) {
                    logger.error("Error processing the end element: " + e.toString());
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    logger.error("Error processing the end element: " + e.toString());
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    logger.error("Error processing the end element: " + e.toString());
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    logger.error("Error processing the end element: " + e.toString());
                    e.printStackTrace();
                } catch (ClassCastException e) {
                    logger.error("Error processing the end element: " + e.toString());
                    e.printStackTrace();
                }
            }

            if (pop.getClass().equals(MSSearch.class)) {
                parserResult = (MSSearch) pop;
            }
        }

        lockStack.pop();
        nameStack.pop();
    }

    /**
     * Process the start element for the XmlPullParser object.
     *
     * @param xpp the XML parser
     * @param importDetails if true the details will be imported
     */
    public void processStartElement(XmlPullParser xpp, boolean importDetails) {

        nameStack.push(xpp.getName());

        try {
            if (!classes.containsKey(nameStack.peek())) {
                lockStack.add(true);
            } else {
                Class<?> c = classes.get(nameStack.peek());
                Object neu = c.newInstance();
                objectStack.push(neu);
                lockStack.add(false);
            }
        } catch (InstantiationException e) {
            logger.error("Error processing the start element: " + e.toString());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error("Error processing the start element: " + e.toString());
            e.printStackTrace();
        }

        if (!lockStack.peek()) {

            if (xpp.getAttributeCount() > 0) {
                attribute = xpp.getAttributeName(0);
                if (attribute.equals("value")) {
                    value = xpp.getAttributeValue(0);

                    try {
                        Object peek = objectStack.peek();
                        Class<?> c = peek.getClass();
                        String method = "set" + nameStack.peek();
                        if (importDetails || shallExecute(c, method, peek)) {
                            Method setX = c.getDeclaredMethod(method, String.class);
                            setX.invoke(peek, value);
                            attribute = "";
                            value = "";
                        }
                    } catch (EmptyStackException e) {
                        logger.error("Error processing the start element: " + e.toString());
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        logger.error("Error processing the start element: " + e.toString());
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        logger.error("Error processing the start element: " + e.toString());
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        logger.error("Error processing the start element: " + e.toString());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Process the text for the XmlPullParser object.
     *
     * @param xpp the XML parser
     * @param importDetails if true the details will be imported
     * @throws org.xmlpull.v1.XmlPullParserException if an
     * XmlPullParserException is thrown
     */
    public void processText(XmlPullParser xpp, boolean importDetails) throws XmlPullParserException {

        Boolean lockStackBuffer = lockStack.pop();
        if (!lockStack.peek()) {

            String text = xpp.getText().trim();

            if (!text.equals("")) {
                try {
                    Object peek = objectStack.peek();
                    Class<?> c = peek.getClass();
                    String method = "set" + nameStack.peek();
                    if (importDetails || shallExecute(c, method, peek)) {
                        Method setX = c.getDeclaredMethod(method, String.class);
                        setX.invoke(peek, text);
                    }
                } catch (EmptyStackException e) {
                    logger.error("Error processing the text element: " + e.toString());
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    logger.error("Error processing the text element: " + e.toString());
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    logger.error("Error processing the text element: " + e.toString());
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    logger.error("Error processing the text element: " + e.toString());
                    e.printStackTrace();
                }
            }
        }

        lockStack.push(lockStackBuffer);
    }

    /**
     * Returns a HashMap of the modification details where the keys are the
     * modification numbers and the elements are OmssaModification-objects.
     *
     * @return the omssa modification details
     */
    public HashMap<Integer, OmssaModification> getOmssaModificationDetails() {
        return omssaModificationDetails;
    }

    /**
     * Indicates whether the given method shall be executed when not importing
     * id details.
     *
     * @param c the class being imported
     * @param method the method to execute
     * @param object the object to be stored
     * @return true if method shall be executed
     */
    private boolean shallExecute(Class<?> c, String method, Object object) {
        // save the file name
        if (c == MSSearch.class && method.equals("setMSSearch_request")) {
            return true;
        }
        if (c == MSSearch_request.class && method.equals("setMSRequest")) {
            return true;
        }
        if (c == MSRequest.class && method.equals("setMSRequest_settings")) {
            return true;
        }
        if (c == MSRequest_settings.class && method.equals("setMSSearchSettings")) {
            return true;
        }
        if (c == MSSearchSettings.class && method.equals("setMSSearchSettings_infiles")) {
            return true;
        }
        if (c == MSSearchSettings_infiles.class && method.equals("setMSSearchSettings_infiles")) {
            return true;
        }
        if (c == MSSearchSettings_infiles.class && method.equals("setMSInFile")) {
            return true;
        }
        if (c == MSInFile.class && method.equals("setMSInFile_infile")) {
            return true;
        }
        // save the peptide sequence, modification and e-value
        if (c == MSSearch.class && method.equals("setMSSearch_response")) {
            return true;
        }
        if (c == MSSearch_response.class && method.equals("setMSResponse")) {
            return true;
        }
        if (c == MSResponse.class && method.equals("setMSResponse_hitsets")) {
            return true;
        }
        if (c == MSResponse_hitsets.class && method.equals("setMSHitSet")) {
            MSHitSet msHitSet = (MSHitSet) object;
            return !msHitSet.MSHitSet_hits.MSHits.isEmpty();
        }
        if (c == MSHitSet.class && method.equals("setMSHitSet_number")) {
            return true;
        }
        if (c == MSHitSet.class && method.equals("setMSHitSet_hits")) {
            return true;
        }
        if (c == MSHitSet_hits.class && method.equals("setMSHits")) {
            return true;
        }
        if (c == MSHitSet.class && method.equals("setMSHitSet_ids")) {
            return true;
        }
        if (c == MSHits.class && method.equals("setMSHits_evalue")) {
            return true;
        }
        if (c == MSHits.class && method.equals("setMSHits_charge")) {
            return true;
        }
        if (c == MSHits.class && method.equals("setMSHits_pepstring")) {
            return true;
        }
        if (c == MSHits.class && method.equals("setMSHits_mods")) {
            return true;
        }
        if (c == MSHits_mods.class && method.equals("setMSModHit")) {
            return true;
        }
        if (c == MSModHit.class && method.equals("setMSModHit_site")) {
            return true;
        }
        if (c == MSModHit.class && method.equals("setMSModHit_modtype")) {
            return true;
        }
        if (c == MSModHit_modtype.class && method.equals("setMSMod")) {
            return true;
        }
        if (c == MSHitSet_ids.class && method.equals("setMSHitSet_ids_E")) {
            return true;
        }
        return false;
    }
}
