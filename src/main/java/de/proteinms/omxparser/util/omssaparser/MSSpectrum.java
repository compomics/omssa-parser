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
 * This Class stores MSSpectrum specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 * @author Steffen Huber
 */
public class MSSpectrum {
	public int MSSpectrum_number;
	public int MSSpectrum_precursormz;
	public MSSpectrum_mz MSSpectrum_mz=new MSSpectrum_mz();
	public MSSpectrum_charge MSSpectrum_charge=new MSSpectrum_charge();
	public MSSpectrum_abundance MSSpectrum_abundance=new MSSpectrum_abundance();
	public int MSSpectrum_iscale;
	public MSSpectrum_ids MSSpectrum_ids=new MSSpectrum_ids();
	public MSSpectrum_namevalue MSSpectrum_namevalue=new MSSpectrum_namevalue();
	
	
	public void setMSSpectrum_number(String s){
		MSSpectrum_number=Integer.valueOf(s);
	}
	public void setMSSpectrum_charge(MSSpectrum_charge s){
		this.MSSpectrum_charge=s;
	}
	public void setMSSpectrum_mz(MSSpectrum_mz s){
		this.MSSpectrum_mz=s;
	}
	public void setMSSpectrum_precursormz(String s){
		MSSpectrum_precursormz=Integer.valueOf(s);
	}
	public void setMSSpectrum_abundance(MSSpectrum_abundance s){
		this.MSSpectrum_abundance=s;
	}
	public void setMSSpectrum_iscale(String s){
		this.MSSpectrum_iscale=Integer.valueOf(s);
	}
	public void setMSSpectrum_ids(MSSpectrum_ids s){
		this.MSSpectrum_ids=s;
	}
	public void setMSSpectrum_namevalue(MSSpectrum_namevalue s){
		this.MSSpectrum_namevalue=s;
	}
}
