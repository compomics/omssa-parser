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

import java.util.HashMap;
import java.util.Map;

/**
 * This Class stores MSSpectrumset specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 *
 * @author Steffen Huber
 * Modified by: Harald Barsnes (adding Javadoc)
 */
public class MSSpectrumset {

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
