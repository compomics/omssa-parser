package de.proteinms.omxparser.util;

import com.compomics.util.experiment.biology.aminoacids.sequence.AminoAcidSequence;
import com.compomics.util.experiment.biology.proteins.Peptide;
import com.compomics.util.experiment.identification.Advocate;
import com.compomics.util.experiment.identification.matches.ModificationMatch;
import com.compomics.util.experiment.identification.matches.SpectrumMatch;
import com.compomics.util.experiment.identification.spectrum_assumptions.PeptideAssumption;
import com.compomics.util.experiment.io.identification.IdfileReader;
import com.compomics.util.experiment.mass_spectrometry.SpectrumProvider;
import com.compomics.util.experiment.personalization.ExperimentObject;
import com.compomics.util.io.IoUtil;
import com.compomics.util.parameters.identification.advanced.SequenceMatchingParameters;
import com.compomics.util.parameters.identification.search.SearchParameters;
import com.compomics.util.waiting.WaitingHandler;
import de.proteinms.omxparser.OmssaOmxFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;

/**
 * This reader import identifications from an OMSSA omx result file to the
 * compomics-utilities data structure.
 *
 * @author Marc Vaudel
 * @author Harald Barsnes
 */
public class OMSSAIdfileReader extends ExperimentObject implements IdfileReader {

    /**
     * The inspected OMSSA omx file.
     */
    private File identificationFile;
    /**
     * The instance of the inspected omx file.
     */
    private OmssaOmxFile omxFile;

    /**
     * Constructor for the reader.
     */
    public OMSSAIdfileReader() {
    }

    /**
     * Constructor for the reader.
     *
     * @param idFile the inspected file
     */
    public OMSSAIdfileReader(File idFile) {
        this.identificationFile = idFile;
        omxFile = new OmssaOmxFile(idFile.getPath(), false, false, false);
    }

    /**
     * Get the file name.
     *
     * @return the file name
     */
    public String getFileName() {
        return identificationFile.getName();
    }

    @Override
    public String getExtension() {
        return ".omx";
    }

    @Override
    public ArrayList<SpectrumMatch> getAllSpectrumMatches(
            SpectrumProvider spectrumProvider,
            WaitingHandler waitingHandler, 
            SearchParameters searchParameters
    ) 
            throws IOException, SQLException, ClassNotFoundException, InterruptedException, JAXBException {
        
        return getAllSpectrumMatches(
                spectrumProvider,
                waitingHandler, 
                searchParameters, 
                null, 
                true
        );
    }

    @Override
    public ArrayList<SpectrumMatch> getAllSpectrumMatches(
            SpectrumProvider spectrumProvider,
            WaitingHandler waitingHandler, 
            SearchParameters searchParameters,
            SequenceMatchingParameters sequenceMatchingParameters, 
            boolean expandAaCombinations
    ) 
            throws IOException, SQLException, ClassNotFoundException, InterruptedException, JAXBException {

        ArrayList<SpectrumMatch> result = new ArrayList<>();

        List<MSResponse> msSearchResponse = omxFile.getParserResult().MSSearch_response.MSResponse;
        List<MSRequest> msRequest = omxFile.getParserResult().MSSearch_request.MSRequest;
        int spectrumMatchCounter = 0;

        for (int i = 0; i < msSearchResponse.size(); i++) {

            String msFile = msRequest.get(i).MSRequest_settings.MSSearchSettings.MSSearchSettings_infiles.MSInFile.MSInFile_infile;

            Map<Integer, MSHitSet> msHitSetMap = msSearchResponse.get(i).MSResponse_hitsets.MSHitSet;

            if (waitingHandler != null) {
                waitingHandler.setSecondaryProgressCounterIndeterminate(false);
                waitingHandler.setMaxSecondaryProgressCounter(msHitSetMap.size());
            }

            for (int spectrumIndex : msHitSetMap.keySet()) {

                MSHitSet msHitSet = msHitSetMap.get(spectrumIndex);
                List<MSHits> hitSet = msHitSet.MSHitSet_hits.MSHits;

                if (hitSet.size() > 0) {

                    HashMap<Double, ArrayList<MSHits>> hitMap = new HashMap<>();

                    for (MSHits msHits : hitSet) {
                        if (!hitMap.containsKey(msHits.MSHits_evalue)) {
                            hitMap.put(msHits.MSHits_evalue, new ArrayList<>());
                        }
                        hitMap.get(msHits.MSHits_evalue).add(msHits);
                    }

                    ArrayList<Double> eValues = new ArrayList<>(hitMap.keySet());
                    Collections.sort(eValues);

                    String tempTitle = msHitSet.MSHitSet_ids.MSHitSet_ids_E.get(0);

                    String spectrumTitle = fixMgfTitle(tempTitle);
                    String fileName = IoUtil.getFileName(msFile);
                    SpectrumMatch currentMatch = new SpectrumMatch(fileName, spectrumTitle);
                    int rank = 1;

                    for (double eValue : eValues) {
                        for (MSHits msHits : hitMap.get(eValue)) {

                            PeptideAssumption peptideAssumption = getPeptideAssumption(msHits, rank);

                            if (expandAaCombinations && AminoAcidSequence.hasCombination(peptideAssumption.getPeptide().getSequence())) {

                                Peptide peptide = peptideAssumption.getPeptide();
                                ModificationMatch[] previousModificationMatches = peptide.getVariableModifications();

                                for (StringBuilder expandedSequence : AminoAcidSequence.getCombinations(peptide.getSequence())) {

                                    ModificationMatch[] newModificationMatches = Arrays.stream(previousModificationMatches)
                                            .map(modificationMatch -> modificationMatch.clone())
                                            .toArray(ModificationMatch[]::new);

                                    Peptide newPeptide = new Peptide(expandedSequence.toString(), newModificationMatches, true);

                                    PeptideAssumption newAssumption = new PeptideAssumption(
                                            newPeptide,
                                            peptideAssumption.getRank(), 
                                            peptideAssumption.getAdvocate(), 
                                            peptideAssumption.getIdentificationCharge(),
                                            peptideAssumption.getRawScore(),
                                            peptideAssumption.getScore(), 
                                            peptideAssumption.getIdentificationFile()
                                    );
                                    
                                    currentMatch.addPeptideAssumption(Advocate.omssa.getIndex(), newAssumption);

                                }

                            } else {
                                currentMatch.addPeptideAssumption(Advocate.omssa.getIndex(), peptideAssumption);
                            }
                        }
                        rank += hitMap.get(eValue).size();
                    }

                    result.add(currentMatch);
                }

                if (waitingHandler != null) {
                    if (waitingHandler.isRunCanceled()) {
                        break;
                    }
                    waitingHandler.setSecondaryProgressCounter(++spectrumMatchCounter);
                }
            }
        }

        return result;
    }

    /**
     * Returns a peptide assumption based on the OMSSA MSHits.
     *
     * Warning: the fixed modifications are not implemented and need to be added
     * subsequently. That can be done using the compomics utilities PTMFactory.
     *
     * @param currentMsHit the MSHits of interest
     * @param responseIndex the response index in the msrequest
     * @param rank the rank of the assumption in the spectrum match
     *
     * @return the corresponding peptide assumption
     */
    private PeptideAssumption getPeptideAssumption(MSHits currentMsHit, int rank) {

        int charge = currentMsHit.MSHits_charge;

        List<MSModHit> msModHits = currentMsHit.MSHits_mods.MSModHit;
        ArrayList<ModificationMatch> modificationsFound = new ArrayList<>();

        // inspect variable modifications
        for (MSModHit msModHit : msModHits) {
            int msMod = msModHit.MSModHit_modtype.MSMod;
            String name = msMod + "";
            int location = msModHit.MSModHit_site + 1;
            modificationsFound.add(new ModificationMatch(name, location));
        }

        String peptideSequence = currentMsHit.MSHits_pepstring;
        Peptide peptide = new Peptide(peptideSequence, modificationsFound.toArray(new ModificationMatch[modificationsFound.size()]), true);

        return new PeptideAssumption(
                peptide, 
                rank, 
                Advocate.omssa.getIndex(), 
                charge, 
                currentMsHit.MSHits_evalue, 
                currentMsHit.MSHits_evalue,
                getFileName()
        );
    }

    /**
     * Returns the fixed mgf title.
     *
     * @param spectrumTitle
     * @return the fixed mgf title
     */
    private String fixMgfTitle(String spectrumTitle) {

        // a special fix for mgf files with titles containing url encoding, e.g.: %3b instead of ;
        try {
            spectrumTitle = URLDecoder.decode(spectrumTitle.trim(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("An exception was thrown when trying to decode an mgf tile!");
            e.printStackTrace();
        }

        //System.out.println("before: " + spectrumTitle);
        // a special fix for mgf files with titles containing the escape character '\'
        spectrumTitle = spectrumTitle.replaceAll("\\\\\"", "\\\""); // change \" into "
        spectrumTitle = spectrumTitle.replaceAll("\\\\\\\\", "\\\\"); // change \\ into \

        //System.out.println("after: " + spectrumTitle);
        return spectrumTitle;
    }

    @Override
    public void close() throws IOException {
        omxFile = null;
    }

    @Override
    public HashMap<String, ArrayList<String>> getSoftwareVersions() {
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        ArrayList<String> versions = new ArrayList<>();
        versions.add("2.1.9");
        result.put("OMSSA", versions);
        return result;
    }

    @Override
    public boolean hasDeNovoTags() {
        return false;
    }
}
