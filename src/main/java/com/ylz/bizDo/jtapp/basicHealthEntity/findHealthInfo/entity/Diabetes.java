/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Diabetes</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午4:17:36
 */
public class Diabetes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7163621460054346100L;
	private String GHb;
	private Value footPulse;
	
	public Diabetes(){}
	public Diabetes(String GHb,Value footPulse){
		this.GHb=GHb;
		this.footPulse=footPulse;
	}
	/**
	 * @return the gHb
	 */
	public String getGHb() {
		return GHb;
	}
	/**
	 * @param gHb the gHb to set
	 */
	public void setGHb(String gHb) {
		GHb = gHb;
	}
	/**
	 * @return the footPulse
	 */
	public Value getFootPulse() {
		return footPulse;
	}
	/**
	 * @param footPulse the footPulse to set
	 */
	public void setFootPulse(Value footPulse) {
		this.footPulse = footPulse;
	}
	
	
	
}
