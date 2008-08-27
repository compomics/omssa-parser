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
package de.proteinms.omxparser.util.omssaparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EmptyStackException;
import java.util.HashMap;

import java.util.Stack;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import org.apache.log4j.Logger;

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
 * @param String[] args
 * @author Steffen Huber
 *
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
     * 
     */
    private static HashMap<String, Class<?>> classes = new HashMap<String, Class<?>>();
    /**
     * Contains all Data from the original Omx File gathered by the OmxParser
     */
    public MSSearch parserResult;
    private String attribute = "";
    private String value = "";

    /**
     * If a Class should be parsed by OmxParser, it has to be initialzed by<br>
     * writing it into the HashMap classes
     * 
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
     * Should contain the filename at args[0]
     * @param args String[]<br>
     */
    public OmxParser(String[] args) {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
                    System.getProperty(XmlPullParserFactory.PROPERTY_NAME), null);
            //factory.setNamespaceAware(true);
            factory.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);

            XmlPullParser xpp = factory.newPullParser();

            //write every class from which objects should be created into the hashmap classes
            initializeClasses();




            logger.debug("Parsing file: " + args[0]);
            xpp.setInput(new BufferedReader(new FileReader(args[0])));
            long t1 = System.currentTimeMillis();
            processDocument(xpp);
            long t2 = System.currentTimeMillis();
            long t3 = (t2 - t1) / 1000;
            logger.debug("finished after " + t3 + " seconds");


        } catch (XmlPullParserException e) {
        //not yet implemented
        } catch (IOException e) {
        //not yet implemented
        }


    }

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
                //not yet implemented
                } catch (NoSuchMethodException e) {
                //not yet implemented
                } catch (InvocationTargetException e) {
                //not yet implemented
                } catch (IllegalAccessException e) {
                //not yet implemented
                } catch (ClassCastException e) {
                //not yet implemented
                }
            }

            if (pop.getClass().equals(MSSearch.class)) {

                parserResult = (MSSearch) pop;
            }



        }

        lockStack.pop();
        nameStack.pop();


    }

    public void processStartElement(XmlPullParser xpp) {
        nameStack.push(xpp.getName());
        try {

            if (classes.get(nameStack.peek()) == null) {
                lockStack.add(true);

            } else {
                Class<?> c = classes.get(nameStack.peek());
                Object neu = c.newInstance();
                objectStack.push(neu);
                lockStack.add(false);

            }
        } catch (InstantiationException e) {
        //not yet implemented
        } catch (IllegalAccessException e) {
        //not yet implemented
        }
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

                //not yet implemented
                } catch (IllegalAccessException e) {
                //not yet implemented

                } catch (NoSuchMethodException e) {
                //not yet implemented
                } catch (InvocationTargetException e) {
                //not yet implemented

                }
            }

        }

    }

    public void processText(XmlPullParser xpp) throws XmlPullParserException {
        char ch[] = xpp.getTextCharacters(indexBuffer);
        int start = indexBuffer[0];
        int length = indexBuffer[1];
        StringBuffer buffer = new StringBuffer();

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
            //not yet implemented
            } catch (IllegalAccessException e) {
            //not yet implemented
            } catch (NoSuchMethodException e) {
            //not yet implemented
            } catch (InvocationTargetException e) {
            //not yet implemented
            }
        }

    }
}
