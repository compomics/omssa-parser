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
 * MS output file.
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSOutFile implements Serializable {

    /**
     * Output file name.
     */
    public String MSOutFile_outfile;
    /**
     * Output file type.
     */
    public MSOutFile_outfiletype MSOutFile_outfiletype = new MSOutFile_outfiletype();
    /**
     * Should the output include the request?
     */
    public Boolean MSOutFile_includerequest;

    /**
     * Sets the MSOutFile_includerequest value.
     *
     * @param s the MSOutFile_includerequest value as a String
     */
    public void setMSOutFile_includerequest(String s) {
        this.MSOutFile_includerequest = Boolean.valueOf(s);
    }

    /**
     * Sets the MSOutFile_outfiletype value.
     *
     * @param s the MSOutFile_outfiletype value
     */
    public void setMSOutFile_outfiletype(MSOutFile_outfiletype s) {
        this.MSOutFile_outfiletype = s;
    }

    /**
     * Sets the MSOutFile_outfile value.
     *
     * @param s the MSOutFile_outfile value as a String
     */
    public void setMSOutFile_outfile(String s) {
        this.MSOutFile_outfile = s;
    }
}
