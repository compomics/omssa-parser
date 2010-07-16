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

import java.io.Serializable;

/**
 * Generic search settings.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSSearchSettings implements Serializable {

    /**
     * Precursor ion type.
     */
    public MSSearchSettings_precursorsearchtype MSSearchSettings_precursorsearchtype = new MSSearchSettings_precursorsearchtype();
    /**
     * Product ion type.
     */
    public MSSearchSettings_productsearchtype MSSearchSettings_productsearchtype = new MSSearchSettings_productsearchtype();
    /**
     * Which ions to search?
     */
    public MSSearchSettings_ionstosearch MSSearchSettings_ionstosearch = new MSSearchSettings_ionstosearch();
    /**
     * Peptide mass tolerance.
     */
    public double MSSearchSettings_peptol;
    /**
     * MSMS mass tolerance.
     */
    public double MSSearchSettings_msmstol;
    /**
     * What is the charge dependence of the mass tolerance?
     */
    public MSSearchSettings_zdep MSSearchSettings_zdep = new MSSearchSettings_zdep();
    /**
     * Intensity fraction below which peaks will be discard.
     */
    public double MSSearchSettings_cutoff;
    /**
     * The start of the cutoff, fraction of most intense peak.
     * Intensity fraction below which peaks will be discard.
     */
    public double MSSearchSettings_cutlo;
    /**
     * The end of the cutoff.
     * Intensity fraction below which peaks will be discard.
     */
    public double MSSearchSettings_cuthi;
    /**
     * The increment of the cutoff.
     */
    public double MSSearchSettings_cutinc;
    /**
     * The size of the single charge filtering window.
     */
    public int MSSearchSettings_singlewin;
    /**
     * The size of the double charge filtering window.
     */
    public int MSSearchSettings_doublewin;
    /**
     * The number of peaks allowed in the single window.
     */
    public int MSSearchSettings_singlenum;
    /**
     * The number of peaks allowed in the double window.
     */
    public int MSSearchSettings_doublenum;
    /**
     * Fixed PTMs.
     */
    public MSSearchSettings_fixed MSSearchSettings_fixed = new MSSearchSettings_fixed();
    /**
     * Variable PTMs.
     */
    public MSSearchSettings_variable MSSearchSettings_variable = new MSSearchSettings_variable();
    /**
     * Digestion enzyme.
     */
    public MSSearchSettings_enzyme MSSearchSettings_enzyme = new MSSearchSettings_enzyme();
    /**
     * Number of missed cleaves allowed.
     */
    public int MSSearchSettings_missedcleave;
    /**
     * The number of hits kept in memory for a spectrum.
     */
    public int MSSearchSettings_hitlistlen;
    /**
     * Sequence set to search, e.g. "nr".
     */
    public String MSSearchSettings_db;
    /**
     * Number of m/z to consider in first pass.
     */
    public int MSSearchSettings_tophitnum;
    /**
     * Minimum number of m/z values for a valid hit.
     */
    public int MSSearchSettings_minhit;
    /**
     * Minimum number of m/z for a valid spectra.
     */
    public int MSSearchSettings_minspectra;
    /**
     * Scale for m/z float to integer.
     */
    public int MSSearchSettings_scale;
    /**
     * Maximum number of mass ladders per database peptide.
     */
    public int MSSearchSettings_maxmods;
    /**
     * Taxonomy ids to limit search.
     */
    public MSSearchSettings_taxids MSSearchSettings_taxids = new MSSearchSettings_taxids();
    /**
     * How to deal with charges.
     */
    public MSSearchSettings_chargehandling MSSearchSettings_chargehandling = new MSSearchSettings_chargehandling();
    /**
     * User defined modifications.
     */
    public MSSearchSettings_usermods MSSearchSettings_usermods = new MSSearchSettings_usermods();
    /**
     * Minimum number of counts per precursor bin.
     */
    public int MSSearchSettings_pseudocount;
    /**
     * Should b1 product be in search (1=no, 0=yes).
     */
    public int MSSearchSettings_searchb1;
    /**
     * Should c terminus ion be searched (1=no, 0=yes).
     */
    public int MSSearchSettings_searchctermproduct;
    /**
     * Max number of ions in each series (0=all).
     */
    public int MSSearchSettings_maxproductions;
    /**
     * Min number of AA in peptide for noenzyme search.
     */
    public int MSSearchSettings_minnoenzyme;
    /**
     * Max number of AA in peptide for noenzyme search (0=none).
     */
    public int MSSearchSettings_maxnoenzyme;
    /**
     * The threshold in Da for adding neutron.
     */
    public double MSSearchSettings_exactmass;
    /**
     * ID of the search settings.
     */
    public int MSSearchSettings_settingid;
    /**
     * Iterative search settings.
     */
    public MSSearchSettings_iterativesettings MSSearchSettings_iterativesettings = new MSSearchSettings_iterativesettings();
    /**
     * Turn on aggressive precursor culling for ETD (0=none).
     */
    public int MSSearchSettings_precursorcull;
    /**
     * Input files.
     */
    public MSSearchSettings_infiles MSSearchSettings_infiles = new MSSearchSettings_infiles();
    /**
     * Output files.
     */
    public MSSearchSettings_outfiles MSSearchSettings_outfiles = new MSSearchSettings_outfiles();
    /**
     * Turn on correlation score (1=nocorr).
     */
    public int MSSearchSettings_nocorrelationscore;
    /**
     * Probability of a consecutive ion (used in correlation).
     */
    public double MSSearchSettings_probfollowingion;
    /**
     * Should nmethionine be cleaved?
     */
    public Boolean MSSearchSettings_nmethionine;
    /**
     * Fraction allowable adjustment of product mass tolerance.
     */
    public double MSSearchSettings_automassadjust;
    /**
     * Low mass filter in Daltons, unscaled.
     */
    public double MSSearchSettings_lomasscutoff;
    /**
     * Library search settings.
     */
    public MSSearchSettings_libsearchsettings MSSearchSettings_libsearchsettings = new MSSearchSettings_libsearchsettings();
    /**
     * Which ions to use no proline rule.
     */
    public MSSearchSettings_noprolineions MSSearchSettings_noprolineions = new MSSearchSettings_noprolineions();
    /**
     * Do reverse search.
     */
    public Boolean MSSearchSettings_reversesearch;
    /**
     * Extra search settings.
     */
    public MSSearchSettings_othersettings MSSearchSettings_othersettings = new MSSearchSettings_othersettings();
    /**
     * Number of isotopic peaks to search when using MSSearchType multiisotope.
     */
    public int MSSearchSettings_numisotopes;
    /**
     * Search precursor as ppm.
     */
    public boolean MSSearchSettings_pepppm;
    /**
     * Search product as ppm.
     */
    public boolean MSSearchSettings_msmsppm;
    /**
     * The maximum number of hits to report per spectrum, 0=all.
     */
    public int MSSearchSettings_reportedhitcount;

    /**
     * Sets the MSSearchSettings_othersettings value.
     *
     * @param s the MSSearchSettings_othersettings value
     */
    public void setMSSearchSettings_othersettings(MSSearchSettings_othersettings s) {
        this.MSSearchSettings_othersettings = s;
    }

    /**
     * Sets the MSSearchSettings_reversesearch value.
     *
     * @param s the MSSearchSettings_reversesearch value as a String
     */
    public void setMSSearchSettings_reversesearch(String s) {
        this.MSSearchSettings_reversesearch = Boolean.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_noprolineions value.
     *
     * @param s the MSSearchSettings_noprolineions value
     */
    public void setMSSearchSettings_noprolineions(MSSearchSettings_noprolineions s) {
        this.MSSearchSettings_noprolineions = s;
    }

    /**
     * Sets the MSSearchSettings_libsearchsettings value.
     *
     * @param s the MSSearchSettings_libsearchsettings value
     */
    public void setMSSearchSettings_libsearchsettings(MSSearchSettings_libsearchsettings s) {
        this.MSSearchSettings_libsearchsettings = s;
    }

    /**
     * Sets the MSSearchSettings_lomasscutoff value.
     *
     * @param s the MSSearchSettings_lomasscutoff value as a String
     */
    public void setMSSearchSettings_lomasscutoff(String s) {
        this.MSSearchSettings_lomasscutoff = Double.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_automassadjust value.
     *
     * @param s the MSSearchSettings_automassadjust value as a String
     */
    public void setMSSearchSettings_automassadjust(String s) {
        this.MSSearchSettings_automassadjust = Double.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_nmethionine value.
     *
     * @param s the MSSearchSettings_nmethionine value as a String
     */
    public void setMSSearchSettings_nmethionine(String s) {
        this.MSSearchSettings_nmethionine = Boolean.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_probfollowingion value.
     *
     * @param s the MSSearchSettings_probfollowingion value as a String
     */
    public void setMSSearchSettings_probfollowingion(String s) {
        this.MSSearchSettings_probfollowingion = Double.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_nocorrelationscore value.
     *
     * @param s the MSSearchSettings_nocorrelationscore value as a String
     */
    public void setMSSearchSettings_nocorrelationscore(String s) {
        this.MSSearchSettings_nocorrelationscore = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_outfiles value.
     *
     * @param s the MSSearchSettings_outfiles value
     */
    public void setMSSearchSettings_outfiles(MSSearchSettings_outfiles s) {
        this.MSSearchSettings_outfiles = s;
    }

    /**
     * Sets the MSSearchSettings_infiles value.
     *
     * @param s the MSSearchSettings_infiles value
     */
    public void setMSSearchSettings_infiles(MSSearchSettings_infiles s) {
        this.MSSearchSettings_infiles = s;
    }

    /**
     * Sets the MSSearchSettings_precursorcull value.
     *
     * @param s the MSSearchSettings_precursorcull value as a String
     */
    public void setMSSearchSettings_precursorcull(String s) {
        this.MSSearchSettings_precursorcull = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_iterativesettings value.
     *
     * @param s the MSSearchSettings_iterativesettings value
     */
    public void setMSSearchSettings_iterativesettings(MSSearchSettings_iterativesettings s) {
        this.MSSearchSettings_iterativesettings = s;
    }

    /**
     * Sets the MSSearchSettings_settingid value.
     *
     * @param s the MSSearchSettings_settingid value as a String
     */
    public void setMSSearchSettings_settingid(String s) {
        this.MSSearchSettings_settingid = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_exactmass value.
     *
     * @param s the MSSearchSettings_exactmass value as a String
     */
    public void setMSSearchSettings_exactmass(String s) {
        this.MSSearchSettings_exactmass = Double.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_maxnoenzyme value.
     *
     * @param s the MSSearchSettings_maxnoenzyme value as a String
     */
    public void setMSSearchSettings_maxnoenzyme(String s) {
        this.MSSearchSettings_maxnoenzyme = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_minnoenzyme value.
     *
     * @param s the MSSearchSettings_minnoenzyme value as a String
     */
    public void setMSSearchSettings_minnoenzyme(String s) {
        this.MSSearchSettings_minnoenzyme = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_maxproductions value.
     *
     * @param s the MSSearchSettings_maxproductions value as a String
     */
    public void setMSSearchSettings_maxproductions(String s) {
        this.MSSearchSettings_maxproductions = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_searchctermproduct value.
     *
     * @param s the MSSearchSettings_searchctermproduct value as a String
     */
    public void setMSSearchSettings_searchctermproduct(String s) {
        this.MSSearchSettings_searchctermproduct = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_searchb1 value.
     *
     * @param s the MSSearchSettings_searchb1 value as a String
     */
    public void setMSSearchSettings_searchb1(String s) {
        this.MSSearchSettings_searchb1 = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_pseudocount value.
     *
     * @param s the MSSearchSettings_pseudocount value as a String
     */
    public void setMSSearchSettings_pseudocount(String s) {
        this.MSSearchSettings_pseudocount = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_usermods value.
     *
     * @param s the MSSearchSettings_usermods value
     */
    public void setMSSearchSettings_usermods(MSSearchSettings_usermods s) {
        this.MSSearchSettings_usermods = s;
    }

    /**
     * Sets the MSSearchSettings_chargehandling value.
     *
     * @param s the MSSearchSettings_chargehandling value
     */
    public void setMSSearchSettings_chargehandling(MSSearchSettings_chargehandling s) {
        this.MSSearchSettings_chargehandling = s;
    }

    /**
     * Sets the MSSearchSettings_taxids value.
     *
     * @param s the MSSearchSettings_taxids value
     */
    public void setMSSearchSettings_taxids(MSSearchSettings_taxids s) {
        this.MSSearchSettings_taxids = s;
    }

    /**
     * Sets the MSSearchSettings_maxmods value.
     *
     * @param s the MSSearchSettings_maxmods value as a String
     */
    public void setMSSearchSettings_maxmods(String s) {
        this.MSSearchSettings_maxmods = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_scale value.
     *
     * @param s the MSSearchSettings_scale value as a String
     */
    public void setMSSearchSettings_scale(String s) {
        this.MSSearchSettings_scale = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_minspectra value.
     *
     * @param s the MSSearchSettings_minspectra value as a String
     */
    public void setMSSearchSettings_minspectra(String s) {
        this.MSSearchSettings_minspectra = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_minhit value.
     *
     * @param s the MSSearchSettings_minhit value as a String
     */
    public void setMSSearchSettings_minhit(String s) {
        this.MSSearchSettings_minhit = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_tophitnum value.
     *
     * @param s the MSSearchSettings_tophitnum value as a String
     */
    public void setMSSearchSettings_tophitnum(String s) {
        this.MSSearchSettings_tophitnum = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_db value.
     *
     * @param s the MSSearchSettings_db value as a String
     */
    public void setMSSearchSettings_db(String s) {
        this.MSSearchSettings_db = s;
    }

    /**
     * Sets the MSSearchSettings_hitlistlen value.
     *
     * @param s the MSSearchSettings_hitlistlen value as a String
     */
    public void setMSSearchSettings_hitlistlen(String s) {
        this.MSSearchSettings_hitlistlen = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_missedcleave value.
     *
     * @param s the MSSearchSettings_missedcleave value as a String
     */
    public void setMSSearchSettings_missedcleave(String s) {
        this.MSSearchSettings_missedcleave = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_enzyme value.
     *
     * @param s the MSSearchSettings_enzyme value
     */
    public void setMSSearchSettings_enzyme(MSSearchSettings_enzyme s) {
        this.MSSearchSettings_enzyme = s;
    }

    /**
     * Sets the MSSearchSettings_variable value.
     *
     * @param s the MSSearchSettings_variable value
     */
    public void setMSSearchSettings_variable(MSSearchSettings_variable s) {
        this.MSSearchSettings_variable = s;
    }

    /**
     * Sets the MSSearchSettings_fixed value.
     *
     * @param s the MSSearchSettings_fixed value
     */
    public void setMSSearchSettings_fixed(MSSearchSettings_fixed s) {
        this.MSSearchSettings_fixed = s;
    }

    /**
     * Sets the MSSearchSettings_singlewin value.
     *
     * @param s the MSSearchSettings_singlewin value as a String
     */
    public void setMSSearchSettings_singlewin(String s) {
        this.MSSearchSettings_singlewin = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_doublewin value.
     *
     * @param s the MSSearchSettings_doublewin value as a String
     */
    public void setMSSearchSettings_doublewin(String s) {
        this.MSSearchSettings_doublewin = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_singlenum value.
     *
     * @param s the MSSearchSettings_singlenum value as a String
     */
    public void setMSSearchSettings_singlenum(String s) {
        this.MSSearchSettings_singlenum = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_doublenum value.
     *
     * @param s the MSSearchSettings_doublenum value as a String
     */
    public void setMSSearchSettings_doublenum(String s) {
        this.MSSearchSettings_doublenum = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_cutinc value.
     *
     * @param s the MSSearchSettings_cutinc value as a String
     */
    public void setMSSearchSettings_cutinc(String s) {
        this.MSSearchSettings_cutinc = Double.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_cuthi value.
     *
     * @param s the MSSearchSettings_cuthi value as a String
     */
    public void setMSSearchSettings_cuthi(String s) {
        this.MSSearchSettings_cuthi = Double.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_cutlo value.
     *
     * @param s the MSSearchSettings_cutlo value as a String
     */
    public void setMSSearchSettings_cutlo(String s) {
        this.MSSearchSettings_cutlo = Double.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_cutoff value.
     *
     * @param s the MSSearchSettings_cutoff value as a String
     */
    public void setMSSearchSettings_cutoff(String s) {
        this.MSSearchSettings_cutoff = Double.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_precursorsearchtype value.
     *
     * @param s the MSSearchSettings_precursorsearchtype value
     */
    public void setMSSearchSettings_precursorsearchtype(MSSearchSettings_precursorsearchtype s) {
        this.MSSearchSettings_precursorsearchtype = s;
    }

    /**
     * Sets the MSSearchSettings_productsearchtype value.
     *
     * @param s the MSSearchSettings_productsearchtype value
     */
    public void setMSSearchSettings_productsearchtype(MSSearchSettings_productsearchtype s) {
        this.MSSearchSettings_productsearchtype = s;
    }

    /**
     * Sets the MSSearchSettings_ionstosearch value.
     *
     * @param s the MSSearchSettings_ionstosearch value
     */
    public void setMSSearchSettings_ionstosearch(MSSearchSettings_ionstosearch s) {
        this.MSSearchSettings_ionstosearch = s;
    }

    /**
     * Sets the MSSearchSettings_zdep value.
     *
     * @param s the MSSearchSettings_zdep value
     */
    public void setMSSearchSettings_zdep(MSSearchSettings_zdep s) {
        this.MSSearchSettings_zdep = s;
    }

    /**
     * Sets the MSSearchSettings_peptol value.
     *
     * @param s the MSSearchSettings_peptol value
     */
    public void setMSSearchSettings_peptol(String s) {
        this.MSSearchSettings_peptol = Double.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_msmstol value.
     *
     * @param s the MSSearchSettings_msmstol value
     */
    public void setMSSearchSettings_msmstol(String s) {
        this.MSSearchSettings_msmstol = Double.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_numisotopes value.
     *
     * @param s the MSSearchSettings_numisotopes value as a String
     */
    public void setMSSearchSettings_numisotopes(String s) {
        this.MSSearchSettings_numisotopes = Integer.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_pepppm value.
     *
     * @param s theMSSearchSettings_pepppm value as a String
     */
    public void setMSSearchSettings_pepppm(String s) {
        this.MSSearchSettings_pepppm = Boolean.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_msmsppm value.
     *
     * @param s the MSSearchSettings_msmsppm value as a String
     */
    public void setMSSearchSettings_msmsppm(String s) {
        this.MSSearchSettings_msmsppm = Boolean.valueOf(s);
    }

    /**
     * Sets the MSSearchSettings_reportedhitcount value.
     *
     * @param s the MSSearchSettings_reportedhitcount value as a String
     */
    public void setMSSearchSettings_reportedhitcount(String s) {
        this.MSSearchSettings_reportedhitcount = Integer.valueOf(s);
    }
}
