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
 * Iterative search settings.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSIterativeSettings implements Serializable {

    /**
     * E-value threshold for re-searching spectra, 0 = always re-search.
     */
    public double MSIterativeSettings_researchthresh;
    /**
     * E-value threshold for picking sequence subset, 0 = all sequences.
     */
    public double MSIterativeSettings_subsetthresh;
    /**
     * E-value threshold for replacing hitset, 0 = only if.
     */
    public double MSIterativeSettings_replacethresh;

    /**
     * Sets the MSIterativeSettings_researchthresh value.
     *
     * @param s the MSIterativeSettings_researchthresh value as a String
     */
    public void setMSIterativeSettings_researchthresh(String s) {
        this.MSIterativeSettings_researchthresh = Double.valueOf(s);
    }

    /**
     * Sets the MSIterativeSettings_subsetthresh value.
     *
     * @param s the MSIterativeSettings_subsetthresh value as a String
     */
    public void setMSIterativeSettings_subsetthresh(String s) {
        this.MSIterativeSettings_subsetthresh = Double.valueOf(s);
    }

    /**
     * Sets the MSIterativeSettings_replacethresh value.
     *
     * @param s the MSIterativeSettings_replacethresh value as a String
     */
    public void setMSIterativeSettings_replacethresh(String s) {
        this.MSIterativeSettings_replacethresh = Double.valueOf(s);
    }
}
