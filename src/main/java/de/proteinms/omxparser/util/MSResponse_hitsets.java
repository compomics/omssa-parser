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
import java.util.HashMap;
import java.util.Map;

/**
 * Hits grouped by spectrum.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSResponse_hitsets implements Serializable {

    /**
     * A map containting the hits for each spectrum. The key is the index number of the
     * hits and the value is the MSHitSet.
     */
    public Map<Integer, MSHitSet> MSHitSet = new HashMap<Integer, MSHitSet>();

    /**
     * Add a MSHitSet element to the MSHitSet map.
     * 
     * @param s a MSHitSet
     */
    public void setMSHitSet(MSHitSet s) {
        this.MSHitSet.put(s.MSHitSet_number, s);
    }
}
