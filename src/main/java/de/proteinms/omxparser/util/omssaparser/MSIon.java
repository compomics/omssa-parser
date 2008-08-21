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
 * This Class stores MSIon specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 * @author Steffen Huber
 */
public class MSIon {
		public MSIon_neutralloss MSIon_neutralloss=new MSIon_neutralloss();
		public MSIon_isotope MSIon_isotope=new MSIon_isotope();
		public String MSIon_internal;
		public MSIon_immonium MSIon_immonium=new MSIon_immonium();
		
		public void setMSIon_immonium(MSIon_immonium s){
			this.MSIon_immonium=s;
		}
		public void setMSIon_internal(String s){
			this.MSIon_internal=s;
		}
		public void setMSIon_isotope(MSIon_isotope s){
			this.MSIon_isotope=s;
		}
		public void setMSIon_neutralloss(MSIon_neutralloss s){
			this.MSIon_neutralloss=s;
		}
}
