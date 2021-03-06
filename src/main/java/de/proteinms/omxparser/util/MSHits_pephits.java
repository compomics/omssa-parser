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

import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;

/**
 * Peptides that match this hit.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 *
 * @author Steffen Huber
 * Modified by: Harald Barsnes (adding Javadoc)
 */
public class MSHits_pephits implements Serializable {

    /**
     * List of peptides that match this hit.
     */
    public List<MSPepHit> MSPepHit = new LinkedList<MSPepHit>();

    /**
     * Adds a MSPepHit to the MSPepHit list.
     * 
     * @param s a MSPepHit
     */
    public void setMSPepHit(MSPepHit s) {
        MSPepHit.add(s);
    }
}
