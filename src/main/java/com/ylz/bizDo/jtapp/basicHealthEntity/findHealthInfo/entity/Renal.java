/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Renal</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午3:15:44
 */
public class Renal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String BUN;
	private String Scr;
	
	public Renal(){
		
	}
	public Renal(String BUN,String Scr){
		this.BUN=BUN;
		this.Scr=Scr;
	}
	/**
	 * @return the bUN
	 */
	public String getBUN() {
		return BUN;
	}
	/**
	 * @param bUN the bUN to set
	 */
	public void setBUN(String bUN) {
		BUN = bUN;
	}
	/**
	 * @return the scr
	 */
	public String getScr() {
		return Scr;
	}
	/**
	 * @param scr the scr to set
	 */
	public void setScr(String scr) {
		Scr = scr;
	}

}
