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
 * Mass container.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSMassSet implements Serializable {

    /**
     * The MSMassSet_monomass value.
     */
    public double MSMassSet_monomass;
    /**
     * The MSMassSet_averagemass value.
     */
    public double MSMassSet_averagemass;
    /**
     * The MSMassSet_n15mass value.
     */
    public double MSMassSet_n15mass;

    /**
     * Sets the MSMassSet_monomass value.
     *
     * @param s the MSMassSet_monomass value as a String
     */
    public void setMSMassSet_monomass(String s) {
        this.MSMassSet_monomass = Double.valueOf(s);
    }

    /**
     * Sets the MSMassSet_averagemass value.
     *
     * @param s the MSMassSet_averagemass value as a String
     */
    public void setMSMassSet_averagemass(String s) {
        this.MSMassSet_averagemass = Double.valueOf(s);
    }

    /**
     * Sets the MSMassSet_n15mass value.
     *
     * @param s the MSMassSet_n15mass value as a String
     */
    public void setMSMassSet_n15mass(String s) {
        this.MSMassSet_n15mass = Double.valueOf(s);
    }
}
