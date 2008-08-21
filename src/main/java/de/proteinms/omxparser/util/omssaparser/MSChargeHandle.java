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
 * This Class stores MSChargeHandle specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 * @author Steffen Huber
 */

public class MSChargeHandle {
	public MSChargeHandle_calcplusone MSChargeHandle_calcplusone=new MSChargeHandle_calcplusone();
	public MSChargeHandle_calccharge MSChargeHandle_calccharge=new MSChargeHandle_calccharge();
	public int MSChargeHandle_mincharge;
	public int MSChargeHandle_maxcharge;
	public int MSChargeHandle_considermult;
	public double MSChargeHandle_plusone;
	public int MSChargeHandle_maxproductcharge;
	public Boolean MSChargeHandle_prodlesspre;
	
	// to check: prodlesspre Syntax correct?
	public void setMSChargeHandle_prodlesspre(String s){
		this.MSChargeHandle_prodlesspre=Boolean.valueOf(s);
	}
	
	public void setMSChargeHandle_maxproductcharge(String s){
		this.MSChargeHandle_maxproductcharge=Integer.valueOf(s);
	}
	public void setMSChargeHandle_plusone(String s){
		this.MSChargeHandle_plusone=Double.valueOf(s);
	}
	public void setMSChargeHandle_considermult(String s){
		this.MSChargeHandle_considermult=Integer.valueOf(s);
	}
	public void setMSChargeHandle_maxcharge(String s){
		this.MSChargeHandle_maxcharge=Integer.valueOf(s);
	}
	public void setMSChargeHandle_mincharge(String s){
		this.MSChargeHandle_mincharge=Integer.valueOf(s);
	}
	public void setMSChargeHandle_calccharge(MSChargeHandle_calccharge s){
		this.MSChargeHandle_calccharge=s;
	}
	public void setMSChargeHandle_calcplusone(MSChargeHandle_calcplusone s){
		this.MSChargeHandle_calcplusone=s;
	}
	
}
