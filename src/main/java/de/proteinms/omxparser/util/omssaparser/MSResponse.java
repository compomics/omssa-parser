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
 * This Class stores MSResponse specific information.
 * <br>Please read "OMSSA.mod.dtd" and "OMSSA.xsd" for further information.
 * @author Steffen Huber
 */
public class MSResponse {
		public MSResponse_hitsets MSResponse_hitsets=new MSResponse_hitsets();
		public int MSResponse_scale;
		public String MSResponse_rid;
		public MSResponse_error MSResponse_error=new MSResponse_error();
		public String MSResponse_version;
		public String MSResponse_email;
		public int MSResponse_dbversion;

		public void setMSResponse_dbversion(String s){
			this.MSResponse_dbversion=Integer.valueOf(s);
		}
		public void setMSResponse_email(String s){
			this.MSResponse_email=s;
		}
		public void setMSResponse_version(String s){
			this.MSResponse_version=s;
		}
		public void setMSResponse_error(MSResponse_error s){
			this.MSResponse_error=s;
		}
		public void setMSResponse_rid(String s){
			this.MSResponse_rid=s;
		}
		public void setMSResponse_scale(String s){
			this.MSResponse_scale=Integer.valueOf(s);
		}
		public void setMSResponse_hitsets(MSResponse_hitsets s){
			this.MSResponse_hitsets=s;
		}
}
