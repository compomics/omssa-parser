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
 * Holds both search requests and results.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSSearch implements Serializable {

    /**
     * The search requests.
     */
    public MSSearch_request MSSearch_request = new MSSearch_request();
    /**
     * The search results.
     */
    public MSSearch_response MSSearch_response = new MSSearch_response();

    /**
     * Sets the MSSearch_response value.
     *
     * @param s the MSSearch_response value
     */
    public void setMSSearch_response(MSSearch_response s) {
        this.MSSearch_response = s;
    }

    /**
     * Sets the MSSearch_request value.
     *
     * @param s the MSSearch_request value
     */
    public void setMSSearch_request(MSSearch_request s) {
        this.MSSearch_request = s;
    }
}
