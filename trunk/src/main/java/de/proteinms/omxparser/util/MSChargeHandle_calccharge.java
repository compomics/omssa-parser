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
 * This Class stores MSChargeHandle_calccharge specific information.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSChargeHandle_calccharge implements Serializable {

    /**
     * User instructions on whether to believe charges in input file.
     * <br><br>
     * 0 - calculate - guess the charge(s) from the data<br>
     * 1 - usefile - use what the input file says<br>
     * 2 - userange - use the charge range specified
     */
    public Integer MSCalcCharge;

    /**
     * Sets the MSCalcCharge value.
     *
     * @param s the MSCalcCharge as a String
     */
    public void setMSCalcCharge(String s) {
        this.MSCalcCharge = Integer.valueOf(s);
    }
}
