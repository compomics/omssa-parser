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
 * The search request that is given to the OMSSA algorithm.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSRequest implements Serializable {

    /**
     * The set of spectra.
     */
    public MSRequest_spectra MSRequest_spectra = new MSRequest_spectra();
    /**
     * The search settings.
     */
    public MSRequest_settings MSRequest_settings = new MSRequest_settings();
    /**
     * Request ID.
     */
    public String MSRequest_rid;
    /**
     * Additional search runs.
     */
    public MSRequest_moresettings MSRequest_moresettings = new MSRequest_moresettings();
    /**
     * List of modificatinos that can be used in search.
     */
    public MSRequest_modset MSRequest_modset = new MSRequest_modset();

    /**
     * Sets the MSRequest_modset value.
     *
     * @param s the MSRequest_modset value
     */
    public void setMSRequest_modset(MSRequest_modset s) {
        this.MSRequest_modset = s;
    }

    /**
     * Sets the MSRequest_moresettings value.
     *
     * @param s the MSRequest_moresettings value
     */
    public void setMSRequest_moresettings(MSRequest_moresettings s) {
        this.MSRequest_moresettings = s;
    }

    /**
     * Sets the MSRequest_rid value.
     *
     * @param s the MSRequest_rid value as a String
     */
    public void setMSRequest_rid(String s) {
        this.MSRequest_rid = s;
    }

    /**
     * Sets the MSRequest_settings value.
     *
     * @param s the MSRequest_settings value
     */
    public void setMSRequest_settings(MSRequest_settings s) {
        this.MSRequest_settings = s;
    }

    /**
     * Sets the MSRequest_spectra value.
     *
     * @param s the MSRequest_spectra value
     */
    public void setMSRequest_spectra(MSRequest_spectra s) {
        this.MSRequest_spectra = s;
    }
}