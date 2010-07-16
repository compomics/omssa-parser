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
 * This Class stores MSChargeHandle_calcplusone specific information.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSChargeHandle_calcplusone implements Serializable {

    /**
     * How is charge to be handled?  Some input files are not clear
     * on this.  For example, a dta file only specifies one charge,
     * even though the charge is not really known.
     * <br><br>
     * 0: don't guess charge one<br>
     * 1: guess charge one
     */
    public Integer MSCalcPlusOne;

    /**
     * Sets the MSCalcPlusOne value.
     *
     * @param s the MSCalcPlusOne as a String
     */
    public void setMSCalcPlusOne(String s) {
        MSCalcPlusOne = (Integer.valueOf(s));
    }
}
