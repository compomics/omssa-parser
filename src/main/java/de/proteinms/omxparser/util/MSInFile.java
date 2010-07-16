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
 * The input files..
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class MSInFile implements Serializable {

    /**
     * Input file name.
     */
    public String MSInFile_infile;
    /**
     * Input file type.
     */
    public MSInFile_infiletype MSInFile_infiletype = new MSInFile_infiletype();

    /**
     * Sets the MSInFile_infiletype value.
     * 
     * @param s the MSInFile_infiletype
     */
    public void setMSInFile_infiletype(MSInFile_infiletype s) {
        this.MSInFile_infiletype = s;
    }

    /**
     * Sets the MSInFile_infile value
     *
     * @param s the MSInFile_infile value as a String
     */
    public void setMSInFile_infile(String s) {
        this.MSInFile_infile = s;
    }
}
