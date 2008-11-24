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

import java.util.LinkedList;
import java.util.List;

/**
 * This Class stores MSSpectrum_abundance specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 *
 * @author Steffen Huber
 * @author Harald Barsnes (added a way of extracting the "real" abundance value, and added Javadoc)
 */
public class MSSpectrum_abundance {

    /**
     * The list of MSSpectrum_abundance_E values from the omx file.
     */
    public List<AbundanceValue> MSSpectrum_abundance_E = new LinkedList<AbundanceValue>();

    /**
     * Adds an element to the MSSpectrum_abundance_E list.
     *
     * @param s the element to add as a String
     */
    public void setMSSpectrum_abundance_E(String s) {
        MSSpectrum_abundance_E.add(new AbundanceValue(Integer.valueOf(s)));
    }
}
