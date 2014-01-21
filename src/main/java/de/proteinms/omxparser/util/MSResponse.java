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
 * Search results.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSResponse implements Serializable {

    /**
     * Hits grouped by spectrum.
     */
    public MSResponse_hitsets MSResponse_hitsets = new MSResponse_hitsets();
    /**
     * Scale to change m/z float to integer.
     */
    public int MSResponse_scale = 100; // default scale from www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd
    /**
     * Request id.
     */
    public String MSResponse_rid;
    /**
     * Error response.
     */
    public MSResponse_error MSResponse_error = new MSResponse_error();
    /**
     * Version of OMSSA.
     */
    public String MSResponse_version = "2.1.9";
    /**
     * E-mail address for notification.
     */
    public String MSResponse_email;
    /**
     * Version of db searched (usually size).
     */
    public int MSResponse_dbversion;
    /**
     * Sequences found in search. NB: References Bioseq module FROM NCBI-Sequence! Currently not supported.
     */
    public String MSResponse_bioseqs = "References Bioseq module FROM NCBI-Sequence! Currently not supported.";
    //public MSResponse_bioseqs MSResponse_bioseqs = new MSResponse_bioseqs(); // @TODO: This element references the Bioseq module FROM NCBI-Sequence.

    /**
     * Sets the MSResponse_dbversion value.
     *
     * @param s the MSResponse_dbversion value as a String
     */
    public void setMSResponse_dbversion(String s) {
        this.MSResponse_dbversion = Integer.valueOf(s);
    }

    /**
     * Sets the MSResponse_email value.
     *
     * @param s the MSResponse_email value as a String
     */
    public void setMSResponse_email(String s) {
        this.MSResponse_email = s;
    }

    /**
     * Sets the MSResponse_version value.
     *
     * @param s the MSResponse_version value as a String
     */
    public void setMSResponse_version(String s) {
        this.MSResponse_version = s;
    }

    /**
     * Sets the MSResponse_error value.
     *
     * @param s the MSResponse_error value as a String
     */
    public void setMSResponse_error(MSResponse_error s) {
        this.MSResponse_error = s;
    }

    /**
     * Sets the MSResponse_rid value.
     *
     * @param s the MSResponse_rid value as a String
     */
    public void setMSResponse_rid(String s) {
        this.MSResponse_rid = s;
    }

    /**
     * Sets the MSResponse_scale value.
     *
     * @param s the MSResponse_scale value as a String
     */
    public void setMSResponse_scale(String s) {
        this.MSResponse_scale = Integer.valueOf(s);
    }

    /**
     * Sets the MSResponse_hitsets value.
     *
     * @param s the MSResponse_hitsets value as a String
     */
    public void setMSResponse_hitsets(MSResponse_hitsets s) {
        this.MSResponse_hitsets = s;
    }

    /**
     * Sets the MSResponse_bioseqs value.
     *
     * @param s the MSResponse_hitsets
     */
//    public void MSResponse_bioseqs(MSResponse_bioseqs s) {
//        this.MSResponse_bioseqs = s;
//    }
}
