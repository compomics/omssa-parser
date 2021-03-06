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

import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;

/**
 * Scaled product abundance.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSSpectrum_abundance implements Serializable {

    /**
     * The list of MSSpectrum_abundance_E values from the omx file.
     *
     * Note that this value has to be divided by MSResponse_scale to get the
     * real abundance value.
     */
    public List<Integer> MSSpectrum_abundance_E = new LinkedList<Integer>();

    /**
     * Adds an element to the MSSpectrum_abundance_E list.
     *
     * @param s the element to add as a String
     */
    public void setMSSpectrum_abundance_E(String s) {
        MSSpectrum_abundance_E.add(Integer.valueOf(s));
    }
}
