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
 * This Class stores MSIonAnnot specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 *
 * @author Steffen Huber
 * Modified by: Harald Barsnes (adding Javadoc)
 */
public class MSIonAnnot implements Serializable {

    public Boolean MSIonAnnot_suspect;
    public double MSIonAnnot_massdiff;
    public Boolean MSIonAnnot_missingisotope;

    /**
     * Sets the MSIonAnnot_missingisotope value.
     * 
     * @param s the MSIonAnnot_missingisotope value as a String
     */
    public void setMSIonAnnot_missingisotope(String s) {
        this.MSIonAnnot_missingisotope = Boolean.valueOf(s);
    }

    /**
     * Sets the MSIonAnnot_massdiff value.
     *
     * @param s the MSIonAnnot_massdiff value as a String
     */
    public void setMSIonAnnot_massdiff(String s) {
        this.MSIonAnnot_massdiff = Double.valueOf(s);
    }

    /**
     * Sets the MSIonAnnot_suspect value.
     *
     * @param s the MSIonAnnot_suspect value as a String
     */
    public void setMSIonAnnot_suspect(String s) {
        this.MSIonAnnot_suspect = Boolean.valueOf(s);
    }
}