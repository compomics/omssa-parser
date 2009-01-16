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
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EmptyStackException;
import java.util.HashMap;

import java.util.Stack;

import java.util.Vector;
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
 * This class contains the OmxParser using a XML Pull Parser API implementation<br>
 * (in this case XPP3/MXP1).
 * <br><br>
 * It stores the information from the specified Omx File into the Attribute "parserResult"<br>
 * The information structure is very similar to the XML structure defined in "OMSSA.xsd"
 * <br><br>
 * Every originally XML Tag is now represented as a name-corresponding Object, except<br>
 * the XML Tags which hold information, they are represented by an Attribute inside<br> the corresponding Object.
 * 
 * @author Steffen Huber
 * <br>Modified by: Harald Barsnes (adding modification support and extending Javadoc)
 */
public class OmxParser {

    /**
     * Define a static logger variable so that it references the
     * Logger instance named "OmxParser".
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
     * HashMap of the modification details where the keys are the
     * modification numbers and the elements are OmssaModification-objects
     */
    private HashMap<Integer, OmssaModification> omssaModificationDetails;

    /**
     * If a Class should be parsed by OmxParser, it has to be initialzed by<br>
     * writing it into the HashMap classes
     */
    public static void initializeClasses() {

        classes.put("MSChargeHandle_calccharge", MSChargeHandle_calccharge.class);
        classes.put("MSChargeHandle_calcplusone", MSChargeHandle_calcplusone.class);
        classes.put("MSChargeHandle", MSChargeHandle.class);
        classes.put("MSHits_mods", MSHits_mods.class);
        classes.put("MSHits_mzhits", MSHits_mzhits.class);
        classes.put("MSHits_pephits", MSHits_pephits.class);
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
        classes.put("MSMZHit_annotation", MSMZHit_annotation.class);
        classes.put("MSMZHit_ion", MSMZHit_ion.class);
        classes.put("MSMZHit_moreion", MSMZHit_moreion.class);
        classes.put("MSMZHit", MSMZHit.class);
        classes.put("MSOutFile_outfiletype", MSOutFile_outfiletype.class);
        classes.put("MSOutFile", MSOutFile.class);
        classes.put("MSPepHit", MSPepHit.class);
        classes.put("MSRequest_modset", MSRequest_modset.class);
        classes.put("MSRequest_moresettings", MSRequest_moresettings.class);
        classes.put("MSRequest_settings", MSRequest_settings.class);
        classes.put("MSRequest_spectra", MSRequest_spectra.class);
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
        classes.put("MSSpectrum_abundance", MSSpectrum_abundance.class);
        classes.put("MSSpectrum_charge", MSSpectrum_charge.class);
        classes.put("MSSpectrum_ids", MSSpectrum_ids.class);
        classes.put("MSSpectrum_mz", MSSpectrum_mz.class);
        classes.put("MSSpectrum_namevalue", MSSpectrum_namevalue.class);
        classes.put("MSSpectrum", MSSpectrum.class);
        classes.put("MSSpectrumset", MSSpectrumset.class);
        classes.put("NameValue", NameValue.class);
    }

    /**
     * Initializes the parser and parses the omx file. Also parses
     * the modification files (if any).
     *
     * @param omxFile
     * @param modsFile
     * @param userModsFile
     */
    public OmxParser(String omxFile, String modsFile, String userModsFile) {

        omssaModificationDetails = new HashMap();

        // parse modification files
        if (modsFile != null && modsFile.endsWith(".xml")) {
            parseModificationFile(modsFile);
        }

        if (userModsFile != null && userModsFile.endsWith(".xml")) {
            parseModificationFile(userModsFile);
        }

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
                    System.getProperty(XmlPullParserFactory.PROPERTY_NAME), null);
            //factory.setNamespaceAware(true);
            factory.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);

            XmlPullParser xpp = factory.newPullParser();

            //write every class from which objects should be created into the hashmap classes
            initializeClasses();

            logger.debug("Parsing file: " + omxFile);
            xpp.setInput(new BufferedReader(new FileReader(omxFile)));
            long t1 = System.currentTimeMillis();
            processDocument(xpp);
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
     * Parses a mod.xml or usermod.xml file and builds a HashMap containing the
     * modification details.
     *
     * @param modsFile the path to the mods.xml or usermods.xml file
     */
    private void parseModificationFile(String modsFile) {

        try {
            File mods = new File(modsFile);

            //get the factory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            Document dom = db.parse(mods);

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

                            for (int m = 0; m <
                                    residueNodes.getLength(); m++) {

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
     * @param xpp
     * @throws org.xmlpull.v1.XmlPullParserException
     * @throws java.io.IOException
     */
    public void processDocument(XmlPullParser xpp)
            throws XmlPullParserException, IOException {

        // initialize lockStack:
        lockStack.add(false);

        int eventType = xpp.getEventType();

        do {
            if (eventType == xpp.START_DOCUMENT) {
            } else if (eventType == xpp.END_DOCUMENT) {
            } else if (eventType == xpp.START_TAG) {
                processStartElement(xpp);
            } else if (eventType == xpp.END_TAG) {
                processEndElement(xpp);
            } else if (eventType == xpp.TEXT) {
                processText(xpp);
            }

            eventType = xpp.next();
        } while (eventType != xpp.END_DOCUMENT);
    }

    /**
     * Process the end element for the XmlPullParser object.
     *
     * @param xpp
     */
    public void processEndElement(XmlPullParser xpp) {

        if (!objectStack.isEmpty() && (!lockStack.peek())) {

            Object pop = objectStack.pop();

            if (!objectStack.isEmpty()) {

                try {
                    Object peek = objectStack.peek();
                    Class<?> c = peek.getClass();
                    Method setX = c.getDeclaredMethod("set" + nameStack.peek(), pop.getClass());
                    setX.invoke(peek, pop);
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
     * @param xpp
     */
    public void processStartElement(XmlPullParser xpp) {

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
                        Method setX = c.getDeclaredMethod("set" + nameStack.peek(), String.class);
                        setX.invoke(peek, value);
                        attribute = "";
                        value = "";
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
     * @param xpp
     * @throws org.xmlpull.v1.XmlPullParserException
     */
    public void processText(XmlPullParser xpp) throws XmlPullParserException {

        char ch[] = xpp.getTextCharacters(indexBuffer);
        int start = indexBuffer[0];
        int length = indexBuffer[1];
        StringBuffer buffer = new StringBuffer();
        Boolean lockStackBuffer = lockStack.pop();
        if (!lockStack.peek()) {

            for (int i = start; i < start + length; i++) {
                buffer.append(ch[i]);
            }

            String buffer2 = buffer.toString().trim();

            if (buffer2.equals("")) {
            } else {
                try {
                    Object peek = objectStack.peek();
                    Class<?> c = peek.getClass();
                    Method setX = c.getDeclaredMethod("set" + nameStack.peek(), String.class);
                    setX.invoke(peek, buffer2);
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
}
