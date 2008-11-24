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

/**
 * This Class stores MSModHit specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 *
 * @author Steffen Huber
 * Modified by: Harald Barsnes (adding Javadoc)
 */
public class MSModHit {

    public int MSModHit_site;
    public MSModHit_modtype MSModHit_modtype = new MSModHit_modtype();

    /**
     * Sets the MSModHit_modtype value.
     *
     * @param s the MSModHit_modtype value
     */
    public void setMSModHit_modtype(MSModHit_modtype s) {
        this.MSModHit_modtype = s;
    }

    /**
     * Sets the MSModHit_site value.
     *
     * @param s the MSModHit_site value as a String
     */
    public void setMSModHit_site(String s) {
        this.MSModHit_site = Integer.valueOf(s);
    }
}
