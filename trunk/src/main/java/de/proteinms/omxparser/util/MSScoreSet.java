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
 * Sets of scores.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSScoreSet implements Serializable {

    /**
     * The name of the score set.
     */
    public String MSScoreSet_name;
    /**
     * The value of the score set.
     */
    public double MSScoreSet_value;

    /**
     * Sets the MSScoreSet_value value.
     *
     * @param s the MSScoreSet_value value as a String
     */
    public void setMSScoreSet_value(String s) {
        this.MSScoreSet_value = Integer.valueOf(s);
    }

    /**
     * Sets the MSScoreSet_name value.
     *
     * @param s the MSScoreSet_name value as a String
     */
    public void setMSScoreSet_name(String s) {
        this.MSScoreSet_name = s;
    }
}
