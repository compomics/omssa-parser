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
package de.proteinms.omxparser.util;

import java.io.Serializable;

/**
 * Contains a set of hits to a single spectrum.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSHitSet implements Serializable {

    /**
     * Unique number of spectrum.
     */
    public int MSHitSet_number;
    /**
     * Error, if any.
     */
    public MSHitSet_error MSHitSet_error = new MSHitSet_error();
    /**
     * Set of hits to spectrum.
     */
    public MSHitSet_hits MSHitSet_hits = new MSHitSet_hits();
    /**
     * Filenames or other ids of spectra searched.
     */
    public MSHitSet_ids MSHitSet_ids = new MSHitSet_ids();
    /**
     * Extra info: retention times, etc.
     */
    public MSHitSet_namevalue MSHitSet_namevalue = new MSHitSet_namevalue();
    /**
     * ID of the search setting used.
     */
    public int MSHitSet_settingid;
    /**
     * Allows users to flag certain sets.
     */
    public MSHitSet_userannotation MSHitSet_userannotation = new MSHitSet_userannotation();

    /**
     * Sets the MSHitSet_userannotation
     *
     * @param s the MSHitSet_userannotation
     */
    public void setMSHitSet_userannotation(MSHitSet_userannotation s) {
        this.MSHitSet_userannotation = s;
    }

    /**
     * Sets the MSHitSet_settingid
     *
     * @param s the MSHitSet_settingid as a String
     */
    public void setMSHitSet_settingid(String s) {
        this.MSHitSet_settingid = Integer.valueOf(s);
    }

    /**
     * Sets the MSHitSet_namevalue
     *
     * @param s the MSHitSet_namevalue
     */
    public void setMSHitSet_namevalue(MSHitSet_namevalue s) {
        this.MSHitSet_namevalue = s;
    }

    /**
     * Sets the MSHitSet_ids
     *
     * @param s the MSHitSet_ids
     */
    public void setMSHitSet_ids(MSHitSet_ids s) {
        this.MSHitSet_ids = s;
    }

    /**
     * Sets the MSHitSet_hits
     *
     * @param s the MSHitSet_hits
     */
    public void setMSHitSet_hits(MSHitSet_hits s) {
        this.MSHitSet_hits = s;
    }

    /**
     * Sets the MSHitSet_error
     *
     * @param s the MSHitSet_error
     */
    public void setMSHitSet_error(MSHitSet_error s) {
        this.MSHitSet_error = s;
    }

    /**
     * Sets the MSHitSet_number
     *
     * @param s the MSHitSet_number as a String
     */
    public void setMSHitSet_number(String s) {
        this.MSHitSet_number = Integer.valueOf(s);
    }
}
