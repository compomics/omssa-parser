package de.proteinms.omxparser.util;

import com.compomics.util.Util;
import com.compomics.util.experiment.biology.AminoAcidPattern;
import com.compomics.util.experiment.biology.PTM;
import com.compomics.util.experiment.biology.Peptide;
import com.compomics.util.experiment.identification.Advocate;
import com.compomics.util.experiment.io.identifications.IdfileReader;
import com.compomics.util.experiment.identification.PeptideAssumption;
import com.compomics.util.experiment.identification.matches.ModificationMatch;
import com.compomics.util.experiment.identification.matches.SpectrumMatch;
import com.compomics.util.experiment.massspectrometry.Charge;
import com.compomics.util.experiment.massspectrometry.Spectrum;
import com.compomics.util.experiment.personalization.ExperimentObject;
import com.compomics.util.waiting.WaitingHandler;
import de.proteinms.omxparser.OmssaOmxFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * This reader will import identifications from an OMSSA omx file.
 *
 * Created by IntelliJ IDEA. User: Marc Date: Jun 23, 2010 Time: 9:45:45 AM
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

    public String getExtension() {
        return ".omx";
    }

    @Override
    public HashSet<SpectrumMatch> getAllSpectrumMatches(WaitingHandler waitingHandler) throws IOException, IllegalArgumentException, Exception {

        HashSet<SpectrumMatch> assignedSpectra = new HashSet<SpectrumMatch>();

        List<MSResponse> msSearchResponse = omxFile.getParserResult().MSSearch_response.MSResponse;
        List<MSRequest> msRequest = omxFile.getParserResult().MSSearch_request.MSRequest;

        for (int i = 0; i < msSearchResponse.size(); i++) {

            String msFile = msRequest.get(i).MSRequest_settings.MSSearchSettings.MSSearchSettings_infiles.MSInFile.MSInFile_infile;

            Map<Integer, MSHitSet> msHitSetMap = msSearchResponse.get(i).MSResponse_hitsets.MSHitSet;

            if (waitingHandler != null) {
                waitingHandler.setMaxSecondaryProgressCounter(msHitSetMap.size());
            }

            for (int spectrumIndex : msHitSetMap.keySet()) {

                MSHitSet msHitSet = msHitSetMap.get(spectrumIndex);
                List<MSHits> hitSet = msHitSet.MSHitSet_hits.MSHits;

                if (hitSet.size() > 0) {

                    HashMap<Double, ArrayList<MSHits>> hitMap = new HashMap<Double, ArrayList<MSHits>>();

                    for (MSHits msHits : hitSet) {
                        if (!hitMap.containsKey(msHits.MSHits_evalue)) {
                            hitMap.put(msHits.MSHits_evalue, new ArrayList<MSHits>());
                        }
                        hitMap.get(msHits.MSHits_evalue).add(msHits);
                    }

                    ArrayList<Double> eValues = new ArrayList<Double>(hitMap.keySet());
                    Collections.sort(eValues);

                    String tempName;
                    int tempIndex = spectrumIndex + 1;
                    if (msHitSet.MSHitSet_ids.MSHitSet_ids_E.isEmpty()) {
                        tempName = tempIndex + "";
                    } else {
                        tempName = msHitSet.MSHitSet_ids.MSHitSet_ids_E.get(0);
                    }

                    String name = fixMgfTitle(tempName);
                    SpectrumMatch currentMatch = new SpectrumMatch(Spectrum.getSpectrumKey(Util.getFileName(msFile), name));
                    currentMatch.setSpectrumNumber(tempIndex);
                    int rank = 1;

                    for (double eValue : eValues) {
                        for (MSHits msHits : hitMap.get(eValue)) {
                            currentMatch.addHit(Advocate.OMSSA.getIndex(), getPeptideAssumption(msHits, rank), false);
                        }
                        rank += hitMap.get(eValue).size();
                    }

                    assignedSpectra.add(currentMatch);
                }
            }

            if (waitingHandler != null) {
                if (!waitingHandler.isRunCanceled()) {
                    break;
                }
                waitingHandler.setSecondaryProgressCounter(i);
            }
        }

        return assignedSpectra;
    }

    /**
     * Returns a peptide assumption based on the OMSSA MSHits.
     *
     * Warning: the fixed modifications are not implemented and need to be added
     * subsequently. That can be done using the compomics utilities PTMFactory
     * (https://code.google.com/p/compomics-utilities/source/browse/trunk/src/main/java/com/compomics/util/experiment/biology/PTMFactory.java).
     *
     * @param currentMsHit the MSHits of interest
     * @param responseIndex the response index in the msrequest
     * @param rank the rank of the assumption in the spectrum match
     * @return the corresponding peptide assumption
     */
    private PeptideAssumption getPeptideAssumption(MSHits currentMsHit, int rank) {

        Charge charge = new Charge(Charge.PLUS, currentMsHit.MSHits_charge);

        List<MSModHit> msModHits = currentMsHit.MSHits_mods.MSModHit;
        ArrayList<ModificationMatch> modificationsFound = new ArrayList<ModificationMatch>();

        // inspect variable modifications
        for (MSModHit msModHit : msModHits) {
            int msMod = msModHit.MSModHit_modtype.MSMod;
            PTM currentPTM = new PTM(-1, msMod + "", -1, new AminoAcidPattern()); //@TODO: add more information to rescue the PTM when the omssa index is wrong
            int location = msModHit.MSModHit_site + 1;
            modificationsFound.add(new ModificationMatch(currentPTM.getName(), true, location));
        }

        Peptide thePeptide = new Peptide(currentMsHit.MSHits_pepstring, modificationsFound);
        return new PeptideAssumption(thePeptide, rank, Advocate.OMSSA.getIndex(), charge, currentMsHit.MSHits_evalue, getFileName());
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
            spectrumTitle = URLDecoder.decode(spectrumTitle, "utf-8");
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
    public String getSoftwareVersion() {
        List<MSResponse> msSearchResponse = omxFile.getParserResult().MSSearch_response.MSResponse;
        if (msSearchResponse.size() > 0) {
            return "OMSSA " + msSearchResponse.get(0).MSResponse_version;
        } else {
            return null;
        }
    }
}
