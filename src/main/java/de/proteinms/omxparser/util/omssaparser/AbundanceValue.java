/**
 * Part of the OMSSA Parser (http://code.google.com/p/omssa-parser/)
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
 */

package de.proteinms.omxparser.util.omssaparser;

/**
 * This class makes it possible to retrieve both the abundance value in the omx 
 * file (MSSpectrum_abundance_E) and the real abundance value (by dividing by 1000). 
 * (Without having to store the information twice.)
 * 
 * @author Harald Barsnes
 */
public class AbundanceValue {

    /**
     * The original MSSpectrum_abundance_E from the omx file. Needs to be divied 
     * by 1000 to get the real abundance value.
     */
    private int abundanceValueAsInteger;
    
    /**
     * Takes in the original MSSpectrum_abundance_E from the omx file.
     * 
     * @param abundanceValueAsInteger
     */
    public AbundanceValue(int abundanceValueAsInteger){
        this.abundanceValueAsInteger = abundanceValueAsInteger;
    }
    
    /**
     * Returns the original MSSpectrum_abundance_E from the omx file.
     * 
     * @return the original MSSpectrum_abundance_E from the omx file
     */
    public int getMSSpectrum_mz_E(){
        return abundanceValueAsInteger;
    }
    
    /**
     * Returns the real abundance value by dividing the MSSpectrum_abundance_E 
     * value by 1000.
     * 
     * @return the real abundance value
     */
    public double getAbundanceValue(){
        return ((double) abundanceValueAsInteger)/1000;
    }
}
