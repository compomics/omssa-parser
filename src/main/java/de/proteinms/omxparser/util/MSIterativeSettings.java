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
 * This Class stores MSIterativeSettings specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 *
 * @author Steffen Huber
 * Modified by: Harald Barsnes (adding Javadoc)
 */
public class MSIterativeSettings implements Serializable {

    public double MSIterativeSettings_researchthresh;
    public double MSIterativeSettings_subsetthresh;
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
