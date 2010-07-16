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
 * Contains information about sequences with identical peptide sequences.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSPepHit implements Serializable {

    /**
     * The starting position of the peptide relative to the protein sequence. 
     * NB: The indexing starts at 0, not at 1.
     */
    public int MSPepHit_start;
    /**
     * The ending position of the peptide relative to the protein sequence. 
     * NB: The indexing starts at 0, not at 1.
     */
    public int MSPepHit_stop;
    /**
     * Genbank identifier.
     */
    public int MSPepHit_gi;
    /**
     * Sequence accession.
     */
    public String MSPepHit_accession;
    /**
     * Sequence description.
     */
    public String MSPepHit_defline;
    /**
     * Length of protein.
     */
    public int MSPepHit_protlength;
    /**
     * Blast library oid.
     */
    public int MSPepHit_oid;
    /**
     * Reversed sequence.
     */
    public Boolean MSPepHit_reversed;
    /**
     * AA before the peptide.
     */
    public String MSPepHit_pepstart;
    /**
     * AA after the peptide.
     */
    public String MSPepHit_pepstop;

    /**
     * Sets the MSPepHit_pepstop value.
     *
     * @param s the MSPepHit_pepstop value as a String
     */
    public void setMSPepHit_pepstop(String s) {
        this.MSPepHit_pepstop = s;
    }

    /**
     * Sets the MSPepHit_pepstart value.
     *
     * @param s the MSPepHit_pepstart value as a String
     */
    public void setMSPepHit_pepstart(String s) {
        this.MSPepHit_pepstart = s;
    }

    /**
     * Sets the MSPepHit_reversed value.
     *
     * @param s the MSPepHit_reversed value as a String
     */
    public void setMSPepHit_reversed(String s) {
        this.MSPepHit_reversed = Boolean.valueOf(s);
    }

    /**
     * Sets the MSPepHit_oid value.
     *
     * @param s the MSPepHit_oid value as a String
     */
    public void setMSPepHit_oid(String s) {
        this.MSPepHit_oid = Integer.valueOf(s);
    }

    /**
     * Sets the MSPepHit_protlength value.
     *
     * @param s the MSPepHit_protlength value as a String
     */
    public void setMSPepHit_protlength(String s) {
        this.MSPepHit_protlength = Integer.valueOf(s);
    }

    /**
     * Sets the MSPepHit_defline value.
     *
     * @param s the MSPepHit_defline value as a String
     */
    public void setMSPepHit_defline(String s) {
        this.MSPepHit_defline = s;
    }

    /**
     * Sets the MSPepHit_accession value.
     *
     * @param s the MSPepHit_accession value as a String
     */
    public void setMSPepHit_accession(String s) {
        this.MSPepHit_accession = s;
    }

    /**
     * Sets the MSPepHit_gi value.
     *
     * @param s the MSPepHit_gi value as a String
     */
    public void setMSPepHit_gi(String s) {
        this.MSPepHit_gi = Integer.valueOf(s);
    }

    /**
     * Sets the MSPepHit_stop value.
     *
     * @param s the MSPepHit_stop value as a String
     */
    public void setMSPepHit_stop(String s) {
        this.MSPepHit_stop = Integer.valueOf(s);
    }

    /**
     * Sets the MSPepHit_start value.
     *
     * @param s the MSPepHit_start value as a String
     */
    public void setMSPepHit_start(String s) {
        this.MSPepHit_start = Integer.valueOf(s);
    }
}
