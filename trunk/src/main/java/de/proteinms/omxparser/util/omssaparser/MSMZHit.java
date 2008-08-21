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
 * This Class stores MSMZHit specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 * @author Steffen Huber
 */
public class MSMZHit {
	public MSMZHit_ion MSMZHit_ion=new MSMZHit_ion();
	public int MSMZHit_charge;
	public int MSMZHit_number;
	public int MSMZHit_mz;
	public int MSMZHit_index;
	public MSMZHit_moreion MSMZHit_moreion=new MSMZHit_moreion();
	public MSMZHit_annotation MSMZHit_annotation=new MSMZHit_annotation();
	
	public void setMSMZHit_annotation(MSMZHit_annotation s){
		this.MSMZHit_annotation=s;
	}
	public void setMSMZHit_moreion(MSMZHit_moreion s){
		this.MSMZHit_moreion=s;
	}
	public void setMSMZHit_index(String s){
		this.MSMZHit_index=Integer.valueOf(s);
	}
	public void setMSMZHit_mz(String s){
		this.MSMZHit_mz=Integer.valueOf(s);
	}
	public void setMSMZHit_number(String s){
		this.MSMZHit_number=Integer.valueOf(s);
	}
	public void setMSMZHit_charge(String s){
		this.MSMZHit_charge=Integer.valueOf(s);
	}
	public void setMSMZHit_ion(MSMZHit_ion s){
		this.MSMZHit_ion=s;
	}
}
