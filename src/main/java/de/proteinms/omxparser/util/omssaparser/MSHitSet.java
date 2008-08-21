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
 * This Class stores MSHitSet specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 * @author Steffen Huber
 */
public class MSHitSet {
  public int MSHitSet_number;
  public MSHitSet_error MSHitSet_error=new MSHitSet_error();
  public MSHitSet_hits MSHitSet_hits=new MSHitSet_hits();
  public MSHitSet_ids MSHitSet_ids=new MSHitSet_ids();
  public MSHitSet_namevalue MSHitSet_namevalue=new MSHitSet_namevalue();
  public int MSHitSet_settingid;
  public MSHitSet_userannotation MSHitSet_userannotation=new MSHitSet_userannotation();
  
  public void setMSHitSet_userannotation(MSHitSet_userannotation s){
	  this.MSHitSet_userannotation=s;
  }
  public void setMSHitSet_settingid(String s){
	  this.MSHitSet_settingid=Integer.valueOf(s);
  }
  public void setMSHitSet_namevalue(MSHitSet_namevalue s){
	  this.MSHitSet_namevalue=s;
  }
  public void setMSHitSet_ids(MSHitSet_ids s){
	  this.MSHitSet_ids=s;
  }
  public void setMSHitSet_hits(MSHitSet_hits s){
	  this.MSHitSet_hits=s;
  }
  public void setMSHitSet_error(MSHitSet_error s){
	  this.MSHitSet_error=s;
  }
  public void setMSHitSet_number(String s){
	  this.MSHitSet_number=Integer.valueOf(s);
  }
}
