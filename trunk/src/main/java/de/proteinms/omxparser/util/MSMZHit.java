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
 * Defines a particular ion.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSMZHit implements Serializable {

    /**
     * Ion type, e.g. b.
     */
    public MSMZHit_ion MSMZHit_ion = new MSMZHit_ion();
    /**
     * Ion charge.
     */
    public int MSMZHit_charge;
    /**
     * The sequential number of the ion.
     */
    public int MSMZHit_number;
    /**
     * Scaled m/z value in Da.
     */
    public int MSMZHit_mz;
    /**
     * The index of the peak in the original spectrum.
     */
    public int MSMZHit_index;
    /**
     * More information about the ion type.
     */
    public MSMZHit_moreion MSMZHit_moreion = new MSMZHit_moreion();
    /**
     * Annotations on the ion.
     */
    public MSMZHit_annotation MSMZHit_annotation = new MSMZHit_annotation();

    /**
     * Sets the MSMZHit_annotation value.
     *
     * @param s the MSMZHit_annotation value
     */
    public void setMSMZHit_annotation(MSMZHit_annotation s) {
        this.MSMZHit_annotation = s;
    }

    /**
     * Sets the MSMZHit_moreion value.
     *
     * @param s the MSMZHit_moreion value
     */
    public void setMSMZHit_moreion(MSMZHit_moreion s) {
        this.MSMZHit_moreion = s;
    }

    /**
     * Sets the MSMZHit_index value.
     *
     * @param s the MSMZHit_index value as a String
     */
    public void setMSMZHit_index(String s) {
        this.MSMZHit_index = Integer.valueOf(s);
    }

    /**
     * Sets the MSMZHit_mz value.
     *
     * @param s the MSMZHit_mz value as a String
     */
    public void setMSMZHit_mz(String s) {
        this.MSMZHit_mz = Integer.valueOf(s);
    }

    /**
     * Sets the MSMZHit_number value.
     *
     * @param s the MSMZHit_number value as a String
     */
    public void setMSMZHit_number(String s) {
        this.MSMZHit_number = Integer.valueOf(s);
    }

    /**
     * Sets the MSMZHit_charge value.
     *
     * @param s the MSMZHit_charge value as a String
     */
    public void setMSMZHit_charge(String s) {
        this.MSMZHit_charge = Integer.valueOf(s);
    }

    /**
     * Sets the MSMZHit_ion value.
     *
     * @param s the MSMZHit_ion value
     */
    public void setMSMZHit_ion(MSMZHit_ion s) {
        this.MSMZHit_ion = s;
    }
}
