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
 * This Class stores MSChargeHandle specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 *
 * @author Steffen Huber
 * Modified by: Harald Barsnes (mainly fixing the Javadoc)
 */
public class MSChargeHandle implements Serializable {

    public MSChargeHandle_calcplusone MSChargeHandle_calcplusone = new MSChargeHandle_calcplusone();
    public MSChargeHandle_calccharge MSChargeHandle_calccharge = new MSChargeHandle_calccharge();
    public int MSChargeHandle_mincharge;
    public int MSChargeHandle_maxcharge;
    public int MSChargeHandle_considermult;
    public double MSChargeHandle_plusone;
    public int MSChargeHandle_maxproductcharge;
    public Boolean MSChargeHandle_prodlesspre;

    /**
     * Sets the MSChargeHandle_prodlesspre value.
     * (to check: prodlesspre Syntax correct?)
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
}
