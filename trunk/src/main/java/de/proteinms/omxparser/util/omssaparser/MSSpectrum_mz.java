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
 * This Class stores MSSpectrum_mz specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 * @author Steffen Huber
 * @author Harald Barsnes (added a way of extracting the "real" m/z value)
 */
public class MSSpectrum_mz {

    /**
     * The list of MSSpectrum_mz_E values from the omx file.
     */
    public List<MZValue> MSSpectrum_mz_E = new LinkedList<MZValue>();

    public void setMSSpectrum_mz_E(String s) {
        MSSpectrum_mz_E.add(new MZValue(Integer.valueOf(s)));
    }
}
