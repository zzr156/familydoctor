/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:BloodRoutine</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午3:13:08
 */
public class BloodRoutine implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String HGB;
	private String WBC;
	private String PLT;
	
	public BloodRoutine(){}
	public BloodRoutine(String HGB,String WBC,String PLT){
		this.HGB=HGB;
		this.WBC=WBC;
		this.PLT=PLT;
		
	}
	/**
	 * @return the hGB
	 */
	public String getHGB() {
		return HGB;
	}
	/**
	 * @param hGB the hGB to set
	 */
	public void setHGB(String hGB) {
		HGB = hGB;
	}
	/**
	 * @return the wBC
	 */
	public String getWBC() {
		return WBC;
	}
	/**
	 * @param wBC the wBC to set
	 */
	public void setWBC(String wBC) {
		WBC = wBC;
	}
	/**
	 * @return the pLT
	 */
	public String getPLT() {
		return PLT;
	}
	/**
	 * @param pLT the pLT to set
	 */
	public void setPLT(String pLT) {
		PLT = pLT;
	}

	
}
