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
 * How to handle charges.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSChargeHandle implements Serializable {

    /**
     * Do we guess charge one?
     */
    public MSChargeHandle_calcplusone MSChargeHandle_calcplusone = new MSChargeHandle_calcplusone();
    /**
     * How do we handle charges?
     */
    public MSChargeHandle_calccharge MSChargeHandle_calccharge = new MSChargeHandle_calccharge();
    /**
     * If userange, what is the min?
     */
    public int MSChargeHandle_mincharge;
    /**
     * If userange, what is the max?
     */
    public int MSChargeHandle_maxcharge;
    /**
     * At which precursor charge to consider +2 ions?
     */
    public int MSChargeHandle_considermult;
    /**
     * What % of peaks below precursor needed to call as +1.
     */
    public double MSChargeHandle_plusone;
    /**
     * Maximum product ion charge.
     */
    public int MSChargeHandle_maxproductcharge;
    /**
     * Product charge always less thanor equal to precursor?
     */
    public Boolean MSChargeHandle_prodlesspre;
    /**
     * Negative ion search if -1, positive ion if 1.
     */
    public int MSChargeHandle_negative;

    /**
     * Sets the MSChargeHandle_prodlesspre value.
     *
     * @param s the MSChargeHandle_prodlesspre value
     */
    public void setMSChargeHandle_prodlesspre(String s) {
        this.MSChargeHandle_prodlesspre = Boolean.valueOf(s);
    }

    /**
     * Sets the MSChargeHandle_maxproductcharge value.
     *
     * @param s the MSChargeHandle_maxproductcharge value
     */
    public void setMSChargeHandle_maxproductcharge(String s) {
        this.MSChargeHandle_maxproductcharge = Integer.valueOf(s);
    }

    /**
     * Sets the MSChargeHandle_plusone value.
     *
     * @param s the MSChargeHandle_plusone value
     */
    public void setMSChargeHandle_plusone(String s) {
        this.MSChargeHandle_plusone = Double.valueOf(s);
    }

    /**
     * Sets the MSChargeHandle_considermult value.
     *
     * @param s the MSChargeHandle_considermult value
     */
    public void setMSChargeHandle_considermult(String s) {
        this.MSChargeHandle_considermult = Integer.valueOf(s);
    }

    /**
     * Sets the MSChargeHandle_maxcharge value.
     *
     * @param s the MSChargeHandle_maxcharge value
     */
    public void setMSChargeHandle_maxcharge(String s) {
        this.MSChargeHandle_maxcharge = Integer.valueOf(s);
    }

    /**
     * Sets the MSChargeHandle_mincharge value.
     *
     * @param s the MSChargeHandle_mincharge value
     */
    public void setMSChargeHandle_mincharge(String s) {
        this.MSChargeHandle_mincharge = Integer.valueOf(s);
    }

    /**
     * Sets the MSChargeHandle_calccharge value.
     *
     * @param s the MSChargeHandle_calccharge value
     */
    public void setMSChargeHandle_calccharge(MSChargeHandle_calccharge s) {
        this.MSChargeHandle_calccharge = s;
    }

    /**
     * Sets the MSChargeHandle_calcplusone value.
     *
     * @param s the MSChargeHandle_calcplusone value
     */
    public void setMSChargeHandle_calcplusone(MSChargeHandle_calcplusone s) {
        this.MSChargeHandle_calcplusone = s;
    }

    /**
     * Sets the MSChargeHandle_negative value.
     * Negative ion search if -1, positive ion if 1.
     *
     * @param s the MSChargeHandle_negative value
     */
    public void setMSChargeHandle_negative(String s) {
        this.MSChargeHandle_negative = Integer.valueOf(s);
    }
}
