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

/**
 * This Class stores MSLibrarySettings specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 *
 * @author Steffen Huber
 * Modified by: Harald Barsnes (adding Javadoc)
 */
public class MSLibrarySettings {

    public MSLibrarySettings_libnames MSLibrarySettings_libnames = new MSLibrarySettings_libnames();
    public Boolean MSLibrarySettings_presearch;
    public Boolean MSLibrarySettings_useomssascore;
    public Boolean MSLibrarySettings_usereplicatescore;
    public Boolean MSLibrarySettings_qtofscore;

    /**
     * Sets the MSLibrarySettings_qtofscore value.
     *
     * @param s the MSLibrarySettings_qtofscore value as a String
     */
    public void setMSLibrarySettings_qtofscore(String s) {
        this.MSLibrarySettings_qtofscore = Boolean.valueOf(s);
    }

    /**
     * Sets the MSLibrarySettings_usereplicatescore value.
     *
     * @param s the MSLibrarySettings_usereplicatescore value as a String
     */
    public void setMSLibrarySettings_usereplicatescore(String s) {
        this.MSLibrarySettings_usereplicatescore = Boolean.valueOf(s);
    }

    /**
     * Sets the MSLibrarySettings_useomssascore value.
     *
     * @param s the MSLibrarySettings_useomssascore value as a String
     */
    public void setMSLibrarySettings_useomssascore(String s) {
        this.MSLibrarySettings_useomssascore = Boolean.valueOf(s);
    }

    /**
     * Sets the MSLibrarySettings_presearch value.
     *
     * @param s the MSLibrarySettings_presearch value as a String
     */
    public void setMSLibrarySettings_presearch(String s) {
        this.MSLibrarySettings_presearch = Boolean.valueOf(s);
    }

    /**
     * Sets the MSLibrarySettings_libnames value.
     *
     * @param s the MSLibrarySettings_libnames value
     */
    public void setMSLibrarySettings_libnames(MSLibrarySettings_libnames s) {
        this.MSLibrarySettings_libnames = s;
    }
}
