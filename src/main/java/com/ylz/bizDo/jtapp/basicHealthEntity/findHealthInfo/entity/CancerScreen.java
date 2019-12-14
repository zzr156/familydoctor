/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:CancerScreen</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午3:19:18
 */
public class CancerScreen implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String AFP;
	private String CFA;
	
	public CancerScreen(){}
	public CancerScreen(String AFP,String CFA){
		this.AFP=AFP;
		this.CFA=CFA;
	}
	/**
	 * @return the aFP
	 */
	public String getAFP() {
		return AFP;
	}
	/**
	 * @param aFP the aFP to set
	 */
	public void setAFP(String aFP) {
		AFP = aFP;
	}
	/**
	 * @return the cFA
	 */
	public String getCFA() {
		return CFA;
	}
	/**
	 * @param cFA the cFA to set
	 */
	public void setCFA(String cFA) {
		CFA = cFA;
	}
	
	

}
