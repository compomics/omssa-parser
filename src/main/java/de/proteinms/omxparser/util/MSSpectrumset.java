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

import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/**
 * Holds a set of spectra.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSSpectrumset implements Serializable {

    /**
     * A map containting the set of spectra. The key is the index number of the
     * spectrum and the value is the MSSpectrum.
     */
    public Map<Integer, MSSpectrum> MSSpectrum = new HashMap<Integer, MSSpectrum>();

    /**
     * Adds a MSSpectrum_number-MSSpectrum mapping to the MSSpectrum map.
     *
     * @param s the MSSpectrum to add
     */
    public void setMSSpectrum(MSSpectrum s) {
        this.MSSpectrum.put(s.MSSpectrum_number, s);
    }
}
