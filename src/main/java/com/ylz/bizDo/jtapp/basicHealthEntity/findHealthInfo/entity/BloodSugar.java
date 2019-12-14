/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:BloodSugar</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午3:17:43
 */
public class BloodSugar implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String FBG;
	private String PBG;
	
	public BloodSugar(){}
	public BloodSugar(String FBG,String PBG){
		this.FBG=FBG;
		this.PBG=PBG;
		
	}
	/**
	 * @return the fBG
	 */
	public String getFBG() {
		return FBG;
	}
	/**
	 * @param fBG the fBG to set
	 */
	public void setFBG(String fBG) {
		FBG = fBG;
	}
	/**
	 * @return the pBG
	 */
	public String getPBG() {
		return PBG;
	}
	/**
	 * @param pBG the pBG to set
	 */
	public void setPBG(String pBG) {
		PBG = pBG;
	}
	
	
	

}
