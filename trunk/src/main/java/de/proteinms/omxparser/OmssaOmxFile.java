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
 * Contact: 
 * s4990348@mail.inf.tu-dresden.de
 */
package de.proteinms.omxparser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import de.proteinms.omxparser.util.MSHitSet;
import de.proteinms.omxparser.util.MSHits;
import de.proteinms.omxparser.util.MSPepHit;
import de.proteinms.omxparser.util.MSSearch;
import de.proteinms.omxparser.util.MSSpectrum;
import de.proteinms.omxparser.util.OmssaModification;
import de.proteinms.omxparser.util.OmxParser;

import org.apache.log4j.Logger;

/**
 * This Class manages parsing and storing of the Omx file.
 * It provides several methods to retrieve ordered information.
 * After all it is an example how to access and handle the parsed information.
 *
 * @author Steffen Huber
 * <br>Modified by: Harald Barsnes (added support for extracting modification details and updated the Javadoc)
 */
public class OmssaOmxFile {

    /**
     * Define a static logger variable so that it references the
     * Logger instance named "OmssaOmxFile".
     */
    private static Logger logger = Logger.getLogger(OmssaOmxFile.class);
    /**
     * This HashMap contains pairs of (MSSpectrum,MSHitSet) <br>
     * It returns the corresponding MSHitSet for a specific MSSpectrum <br>
     *
     * @param key MSSpectrum
     * @param value MSHitSet
     */
    private HashMap<MSSpectrum, MSHitSet> spectrumToHitSetMap = new HashMap<MSSpectrum, MSHitSet>();
    private HashMap<MSSpectrum, HashSet<String>> spectrumToPeptideMap = new HashMap<MSSpectrum, HashSet<String>>();
    private HashMap<String, LinkedList<MSSpectrum>> peptideToSpectrumMap = new HashMap<String, LinkedList<MSSpectrum>>();
    private HashMap<String, LinkedList<MSPepHit>> peptideToProteinMap = new HashMap<String, LinkedList<MSPepHit>>();
    private HashMap<String, LinkedList<String>> proteinToPeptideMap = new HashMap<String, LinkedList<String>>();
    private MSSearch parserResult;
    private OmxParser parser;

    /**
     * Returns ALL data from the original Omx file gathered by the OmxParser as a MSSearch object.
     * <br><br>Note: To understand the structure of the MSSearch and child objects it is very helpful 
     * to read "OMSSA.mod.dtd" and "OMSSA.xsd"
     *
     * @return MSSearch
     */
    public MSSearch getParserResult() {
        return parserResult;
    }

    /**
     * Returns a HashMap where every Spectrum (key) is allocated
     * to the corresponding HitSet (value).
     * This HashMap is a good basis for searching specific information.
     *
     * @return HashMap<MSSpectrum,MSHitSet>
     */
    public HashMap<MSSpectrum, MSHitSet> getSpectrumToHitSetMap() {
        return spectrumToHitSetMap;
    }

    /**
     * Returns a HashMap where every Spectrum (key) is allocated to the corresponding found Peptides.
     * <br><br>Note: In this HashMap the Peptides are only represented through their sequences which 
     * are stored as a HashSet of Strings. For further information about the Peptide ("at what position
     * in the corresponding protein does the Peptide start/end?") you´ll need to search in the MSPepHit
     * object.
     *
     * @return HashMap<MSSpectrum,HashSet<String>>
     */
    public HashMap<MSSpectrum, HashSet<String>> getSpectrumToPeptideMap() {
        return spectrumToPeptideMap;
    }

    /**
     * Returns a HashMap where every Peptide (represented by the sequence) is allocated
     * to the corresponding Spectra.
     *
     * @return HashMap<String,LinkedList<MSSpectrum>>
     */
    public HashMap<String, LinkedList<MSSpectrum>> getPeptideToSpectrumMap() {
        return peptideToSpectrumMap;
    }

    /**
     * Returns a HashMap where every Peptide (represented by the sequence) is allocated
     * to the corresponding proteins (in most cases only one Protein).
     * <br><br>Note: In this case the Proteins are represented by a MSPepHit object. This 
     * object stores among other things the position of the Peptide in the Protein.
     *
     * @return HashMap<String,LinkedList<MSPepHit>>
     */
    public HashMap<String, LinkedList<MSPepHit>> getPeptideToProteinMap() {
        return peptideToProteinMap;
    }

    /**
     * Returns a HashMap where every Protein (represented by its accession) is allocated
     * to the corresponding Peptides (represented by their sequence), found by the omssa algorithm.
     *
     * @return HashMap<String,LinkedList<String>>
     */
    public HashMap<String, LinkedList<String>> getProteinToPeptideMap() {
        return proteinToPeptideMap;
    }

    /**
     * This constructor initializes the Parser with the file name of the omx file,
     * and the file names of the two OMSSA modification files (mods.xml and usermods.xml).
     *
     * @param omxFile the file name of the omx file to be parsed
     * @param modsFile the file name of the mods.xml file
     * @param userModsFile the file name of the usermods.xml file
     */
    public OmssaOmxFile(String omxFile, String modsFile, String userModsFile) {

//        System.out.println("Start");
//        long a = System.currentTimeMillis();

        parser = new OmxParser(omxFile, modsFile, userModsFile);
        parserResult = parser.parserResult;

        logger.debug("processing Information...");

        //process Information
        processSpectrumToHitSetMap(parserResult);
        processSpectrumToPeptideMap(parserResult);
        processPeptideToSpectrumMap(parserResult);
        processPeptideToProteineMap(parserResult);
        processProteineToPeptideMap(parserResult);

        logger.debug("parsing completed");

//        long b = System.currentTimeMillis();
//        System.out.println((b - a));
//        System.out.println(((double) (b - a) / 1000));
//        System.out.println("Done.");
    }

    /**
     * This constructor initializes the Parser with the file name of the omx file,
     * and the file name of one of the two OMSSA modification files (mods.xml or
     * usermods.xml).
     *
     * @param omxFile the file name of the omx file to be parsed
     * @param modsFile the file name of the mods.xml file
     */
    public OmssaOmxFile(String omxFile, String modsFile) {
        new OmssaOmxFile(omxFile, modsFile, null);
    }

    /**
     * This constructor initializes the Parser with the file name of the omx file.
     *
     * @param omxFile the file name of the omx file to be parsed
     */
    public OmssaOmxFile(String omxFile) {
        new OmssaOmxFile(omxFile, null, null);
    }

    /**
     * Returns a hashmap of the omssa modification details. Where the key is
     * the integer value used by omssa for the modification, and the element
     * is a OmssaModification object containing all the information about the
     * modification.
     *
     * @return HashMap<Integer, OmssaModification>
     */
    public HashMap<Integer, OmssaModification> getModifications() {
        return parser.getOmssaModificationDetails();
    }

    /**
     * Method, that creates a Map of Peptides and corresponding MSPepHit objects for the
     * given Protein (param, has to be the accession of the Protein). This HashMap contains 
     * valuable information about the location of the Peptide in the Protein etc.
     * 
     * @param protein_accession (String)
     * @return HashMap<String,MSPepHit>
     */
    public HashMap<String, MSPepHit> getPeptidesToPepHit(String protein_accession) {
        HashMap<String, MSPepHit> resultMap = new HashMap<String, MSPepHit>();
        LinkedList<String> peptides = proteinToPeptideMap.get(protein_accession);
        Iterator<String> peptideIt = peptides.iterator();
        while (peptideIt.hasNext()) {
            String thisPeptide = peptideIt.next();
            LinkedList<MSPepHit> pephits = peptideToProteinMap.get(thisPeptide);
            Iterator<MSPepHit> pephitsIt = pephits.iterator();
            while (pephitsIt.hasNext()) {
                MSPepHit thisPepHit = pephitsIt.next();
                if (thisPepHit.MSPepHit_accession.equals(protein_accession)) {
                    resultMap.put(thisPeptide, thisPepHit);
                }
            }
        }

        return resultMap;
    }

    /**
     * Processes every Request Spectrum with its corresponding HitSet into SpectrumToHitSetMap.
     * 
     * @param result MSSearch
     */
    private void processSpectrumToHitSetMap(MSSearch result) {

        for (int i = 0; i < result.MSSearch_request.MSRequest.size(); i++) {
            for (Integer key : result.MSSearch_request.MSRequest.get(i).MSRequest_spectra.MSSpectrumset.MSSpectrum.keySet()) {
                spectrumToHitSetMap.put(result.MSSearch_request.MSRequest.get(i).MSRequest_spectra.MSSpectrumset.MSSpectrum.get(
                        key), result.MSSearch_response.MSResponse.get(i).MSResponse_hitsets.MSHitSet.get(key));
            }
        }
    }

    /**
     * Processes every Request Spectrum with its corresponding Peptides into SpectrumToPeptideMap.
     * 
     * @param result MSSearch
     */
    private void processSpectrumToPeptideMap(MSSearch result) {
        HashSet<String> peptideSet = new HashSet<String>();
        for (int i = 0; i < result.MSSearch_request.MSRequest.size(); i++) {
            for (Integer key : result.MSSearch_request.MSRequest.get(i).MSRequest_spectra.MSSpectrumset.MSSpectrum.keySet()) {

                Iterator<MSHits> itMSHits =
                        result.MSSearch_response.MSResponse.get(i).MSResponse_hitsets.MSHitSet.get(key).MSHitSet_hits.MSHits.iterator();
                while (itMSHits.hasNext()) {
                    peptideSet.add(itMSHits.next().MSHits_pepstring);
                }
                spectrumToPeptideMap.put(
                        result.MSSearch_request.MSRequest.get(i).MSRequest_spectra.MSSpectrumset.MSSpectrum.get(key), peptideSet);
            }
        }
    }

    /**
     * Processes every Peptide with its corresponding Spectra into PeptideToSpectrumMap.
     * 
     * @param result MSSearch
     */
    private void processPeptideToSpectrumMap(MSSearch result) {

        Iterator<MSSpectrum> spectrumIt = spectrumToHitSetMap.keySet().iterator();
        while (spectrumIt.hasNext()) {
            MSSpectrum thisSpectrum = spectrumIt.next();
            MSHitSet thisHitSet = spectrumToHitSetMap.get(thisSpectrum);

            Iterator<MSHits> mshitsIt = thisHitSet.MSHitSet_hits.MSHits.iterator();
            while (mshitsIt.hasNext()) {
                String thisPepString = mshitsIt.next().MSHits_pepstring;
                if (peptideToSpectrumMap.containsKey(thisPepString)) {
                    peptideToSpectrumMap.get(thisPepString).add(thisSpectrum);
                } else {
                    LinkedList<MSSpectrum> spectrumList = new LinkedList<MSSpectrum>();
                    spectrumList.add(thisSpectrum);
                    peptideToSpectrumMap.put(thisPepString, spectrumList);
                }
            }
        }
    }

    /**
     * Processes every Peptide with its corresponding Protein into PeptideToProteineMap.
     * 
     * @param result MSSearch
     */
    private void processPeptideToProteineMap(MSSearch result) {

        for (int i = 0; i < result.MSSearch_request.MSRequest.size(); i++) {
            for (Integer key : result.MSSearch_request.MSRequest.get(i).MSRequest_spectra.MSSpectrumset.MSSpectrum.keySet()) {

                Iterator<MSHits> itMSHits = result.MSSearch_response.MSResponse.get(i).MSResponse_hitsets.MSHitSet.get(key).MSHitSet_hits.MSHits.iterator();
                while (itMSHits.hasNext()) {
                    MSHits thisMSHits = itMSHits.next();
                    String pepString = thisMSHits.MSHits_pepstring;
                    Iterator<MSPepHit> msPepHitIt = thisMSHits.MSHits_pephits.MSPepHit.iterator();
                    while (msPepHitIt.hasNext()) {
                        if (peptideToProteinMap.containsKey(pepString)) {
                            peptideToProteinMap.get(pepString).add(msPepHitIt.next());
                        } else {
                            LinkedList<MSPepHit> newList = new LinkedList<MSPepHit>();
                            newList.add(msPepHitIt.next());
                            peptideToProteinMap.put(pepString, newList);
                        }
                    }
                }
            }
        }
    }

    /**
     * Processes every Protein with its corresponding Peptides into ProteineToPeptideMap.
     * 
     * @param result MSSearch
     */
    private void processProteineToPeptideMap(MSSearch result) {

        for (int i = 0; i < result.MSSearch_request.MSRequest.size(); i++) {
            for (Integer key : result.MSSearch_request.MSRequest.get(i).MSRequest_spectra.MSSpectrumset.MSSpectrum.keySet()) {

                Iterator<MSHits> itMSHits = result.MSSearch_response.MSResponse.get(i).MSResponse_hitsets.MSHitSet.get(key).MSHitSet_hits.MSHits.iterator();
                while (itMSHits.hasNext()) {
                    MSHits thisMSHits = itMSHits.next();
                    String pepString = thisMSHits.MSHits_pepstring;
                    Iterator<MSPepHit> msPepHitIt = thisMSHits.MSHits_pephits.MSPepHit.iterator();
                    while (msPepHitIt.hasNext()) {
                        String proteinAccession = msPepHitIt.next().MSPepHit_accession;
                        if (proteinToPeptideMap.containsKey(proteinAccession)) {
                            proteinToPeptideMap.get(proteinAccession).add(pepString);
                        } else {
                            LinkedList<String> newList = new LinkedList<String>();
                            newList.add(pepString);
                            proteinToPeptideMap.put(proteinAccession, newList);

                        }
                    }
                }
            }
        }
    }
}
