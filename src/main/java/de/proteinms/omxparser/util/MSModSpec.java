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
 * Modification Definition.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSModSpec implements Serializable {

    /**
     * A reference to the MSMod element.
     */
    public MSModSpec_mod MSModSpec_mod = new MSModSpec_mod();
    /**
     * Modification type.
     */
    public MSModSpec_type MSModSpec_type = new MSModSpec_type();
    /**
     * Name of modification.
     */
    public String MSModSpec_name;
    /**
     * Monoisotopic mass.
     */
    public double MSModSpec_monomass;
    /**
     * Average mass.
     */
    public double MSModSpec_averagemass;
    /**
     * Monoisotopic n15 mass.
     */
    public double MSModSpec_n15mass;
    /**
     * Residues to apply modification to.
     */
    public MSModSpec_residues MSModSpec_residues = new MSModSpec_residues();
    /**
     * Loss after precursor mass determination.
     */
    public MSModSpec_neutralloss MSModSpec_neutralloss = new MSModSpec_neutralloss();
    /**
     * The equivalent Unimod Accession number.
     */
    public int MSModSpec_unimod;
    /**
     * The PSI-MS equivalent name.
     */
    public int MSModSpec_psi_ms;
    // @TODO: real parameter is called MSModSpec_psi-ms, but
    //        the '-' can not be used in Java variable names...

    /**
     * Sets the MSModSpec_neutralloss value.
     *
     * @param s the MSModSpec_neutralloss value
     */
    public void setMSModSpec_neutralloss(MSModSpec_neutralloss s) {
        this.MSModSpec_neutralloss = s;
    }

    /**
     * Sets the MSModSpec_residues value.
     *
     * @param s the MSModSpec_residues value
     */
    public void setMSModSpec_residues(MSModSpec_residues s) {
        this.MSModSpec_residues = s;
    }

    /**
     * Sets the MSModSpec_n15mass value.
     *
     * @param s the MSModSpec_n15mass value as a String
     */
    public void setMSModSpec_n15mass(String s) {
        this.MSModSpec_n15mass = Double.valueOf(s);
    }

    /**
     * Sets the MSModSpec_averagemass value.
     *
     * @param s the MSModSpec_averagemass value as a String
     */
    public void setMSModSpec_averagemass(String s) {
        this.MSModSpec_averagemass = Double.valueOf(s);
    }

    /**
     * Sets the MSModSpec_monomass value.
     *
     * @param s the MSModSpec_monomass value as a String
     */
    public void setMSModSpec_monomass(String s) {
        this.MSModSpec_monomass = Double.valueOf(s);
    }

    /**
     * Sets the MSModSpec_name value.
     *
     * @param s the MSModSpec_name value as a String
     */
    public void setMSModSpec_name(String s) {
        this.MSModSpec_name = s;
    }

    /**
     * Sets the MSModSpec_type value.
     *
     * @param s the MSModSpec_type value
     */
    public void setMSModSpec_type(MSModSpec_type s) {
        this.MSModSpec_type = s;
    }

    /**
     * Sets the MSModSpec_mod value.
     *
     * @param s the MSModSpec_mod value
     */
    public void setMSModSpec_mod(MSModSpec_mod s) {
        this.MSModSpec_mod = s;
    }

    /**
     * Sets the MSModSpec_unimod value.
     *
     * @param s the MSModSpec_unimod value as a String
     */
    public void setMSModSpec_unimod(String s) {
        this.MSModSpec_unimod = Integer.valueOf(s);
    }

    /**
     * Sets the MSModSpec_psi_ms value.
     *
     * @param s the MSModSpec_psi_ms value as a String
     */
    public void setMSModSpec_psi_ms(String s) {
        this.MSModSpec_psi_ms = Integer.valueOf(s);
    }
}
