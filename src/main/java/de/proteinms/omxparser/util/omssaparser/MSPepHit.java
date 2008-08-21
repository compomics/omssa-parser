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
 * This Class stores MSPepHit specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 * @author Steffen Huber
 */
public class MSPepHit {
		public int MSPepHit_start;
		public int MSPepHit_stop;
		public int MSPepHit_gi;
		public String MSPepHit_accession;
		public String MSPepHit_defline;
		public int MSPepHit_protlength;
		public int MSPepHit_oid;
		public Boolean MSPepHit_reversed;
		public String MSPepHit_pepstart;
		public String MSPepHit_pepstop;
		
		public void setMSPepHit_pepstop(String s){
			this.MSPepHit_pepstop=s;
		}
		public void setMSPepHit_pepstart(String s){
			this.MSPepHit_pepstart=s;
		}
		public void setMSPepHit_reversed(String s){
			this.MSPepHit_reversed=Boolean.valueOf(s);
		}
		public void setMSPepHit_oid(String s){
			this.MSPepHit_oid=Integer.valueOf(s);
		}
		public void setMSPepHit_protlength(String s){
			this.MSPepHit_protlength=Integer.valueOf(s);
		}
		public void setMSPepHit_defline(String s){
			this.MSPepHit_defline=s;
		}
		public void setMSPepHit_accession(String s){
			this.MSPepHit_accession=s;
		}
		public void setMSPepHit_gi(String s){
			this.MSPepHit_gi=Integer.valueOf(s);
		}
		public void setMSPepHit_stop(String s){
			this.MSPepHit_stop=Integer.valueOf(s);
		}
		public void setMSPepHit_start(String s){
			this.MSPepHit_start=Integer.valueOf(s);
		}
}
