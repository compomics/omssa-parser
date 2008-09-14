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

/**
 * This Class stores MSHits specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 * @author Steffen Huber
 * @author Harald Barsnes (added a way of extracting the "real" mass values)
 */
public class MSHits {

    public double MSHits_evalue;
    public double MSHits_pvalue;
    public int MSHits_charge;
    public MSHits_pephits MSHits_pephits = new MSHits_pephits();
    public MSHits_mzhits MSHits_mzhits = new MSHits_mzhits();
    public String MSHits_pepstring;
    public int MSHits_mass;
    public MSHits_mods MSHits_mods = new MSHits_mods();
    public String MSHits_pepstart;
    public String MSHits_pepstop;
    public int MSHits_protlength;
    public int MSHits_theomass;
    public int MSHits_oid;
    public MSHits_scores MSHits_scores = new MSHits_scores();
    public String MSHits_libaccession;

    /**
     * Returns the MSHits_mass value from the omx file.
     * 
     * @return the MSHits_mass value
     */
    public int getMSHits_mass() {
        return MSHits_mass;
    }

    /**
     * Returns the real mass of the hit by dividing the 
     * MSHits_mass value by 1000.
     * 
     * @return the real mass value
     */
    public double getMass() {
        return ((double) MSHits_mass) / 1000;
    }
    
    /**
     * Returns the MSHits_theomass value from the omx file.
     * 
     * @return the MSHits_theomass value
     */
    public int getMSHits_theomass() {
        return MSHits_theomass;
    }

    /**
     * Returns the real theoretical mass of the hit by dividing the 
     * MSHits_theomass value by 1000.
     * 
     * @return the real theoretical mass value
     */
    public double getTheoreticalMass() {
        return ((double) MSHits_theomass) / 1000;
    }

    public void setMSHits_libaccession(String s) {
        this.MSHits_libaccession = s;
    }

    public void setMSHits_scores(MSHits_scores s) {
        this.MSHits_scores = s;
    }

    public void setMSHits_oid(String s) {
        this.MSHits_oid = Integer.valueOf(s);
    }

    public void setMSHits_theomass(String s) {
        this.MSHits_theomass = Integer.valueOf(s);
    }

    public void setMSHits_protlength(String s) {
        this.MSHits_protlength = Integer.valueOf(s);
    }

    public void setMSHits_pepstop(String s) {
        this.MSHits_pepstop = s;
    }

    public void setMSHits_pepstart(String s) {
        this.MSHits_pepstart = s;
    }

    public void setMSHits_mods(MSHits_mods s) {
        this.MSHits_mods = s;
    }

    public void setMSHits_mass(String s) {
        this.MSHits_mass = Integer.valueOf(s);
    }

    public void setMSHits_pepstring(String s) {
        this.MSHits_pepstring = s;
    }

    public void setMSHits_mzhits(MSHits_mzhits s) {
        this.MSHits_mzhits = s;
    }

    public void setMSHits_pephits(MSHits_pephits s) {
        this.MSHits_pephits = s;
    }

    public void setMSHits_charge(String s) {
        this.MSHits_charge = Integer.valueOf(s);
    }

    public void setMSHits_pvalue(String s) {
        this.MSHits_pvalue = Double.valueOf(s);
    }

    public void setMSHits_evalue(String s) {
        this.MSHits_evalue = Double.valueOf(s);
    }
}
