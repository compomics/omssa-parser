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
 * Types of neutral loss.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSIon_neutralloss implements Serializable {

    /**
     * The neutral loss type.<br><br>
     * 0 - water - minus 18 Da<br>
     * 1 - ammonia - minus 17 Da<br>
     * -1 - no neutral loss<br>
     * <br>
     * Hint: To get the neutral loss as text use the OmssaEnumerators class.
     */
    public int MSIonNeutralLoss = -1;

    /**
     * Sets the MSIonNeutralLoss value.
     *
     * @param s the MSIonNeutralLoss value as a String
     */
    public void setMSIonNeutralLoss(String s) {
        this.MSIonNeutralLoss = Integer.valueOf(s);
    }
}
