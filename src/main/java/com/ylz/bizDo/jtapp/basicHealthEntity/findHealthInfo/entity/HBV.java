/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:HBV</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午3:18:23
 */
public class HBV implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Value HBeAb;
	private Value HBeAg;
	private Value HBsAb;
	private Value HBsAg;
	private Value HBcAb;
	
	public HBV(){}
	public HBV(Value HBeAb,Value HBeAg,Value HBsAb,Value HBsAg,
			Value HBcAb){
		this.HBeAb=HBeAb;
		this.HBeAg=HBeAg;
		this.HBsAb=HBsAb;
		this.HBsAg=HBsAg;
		this.HBcAb=HBcAb;
		
	}
	
	/**
	 * @return the hBeAb
	 */
	public Value getHBeAb() {
		return HBeAb;
	}
	/**
	 * @param hBeAb the hBeAb to set
	 */
	public void setHBeAb(Value hBeAb) {
		HBeAb = hBeAb;
	}
	/**
	 * @return the hBeAg
	 */
	public Value getHBeAg() {
		return HBeAg;
	}
	/**
	 * @param hBeAg the hBeAg to set
	 */
	public void setHBeAg(Value hBeAg) {
		HBeAg = hBeAg;
	}
	/**
	 * @return the hBsAb
	 */
	public Value getHBsAb() {
		return HBsAb;
	}
	/**
	 * @param hBsAb the hBsAb to set
	 */
	public void setHBsAb(Value hBsAb) {
		HBsAb = hBsAb;
	}
	/**
	 * @return the hBsAg
	 */
	public Value getHBsAg() {
		return HBsAg;
	}
	/**
	 * @param hBsAg the hBsAg to set
	 */
	public void setHBsAg(Value hBsAg) {
		HBsAg = hBsAg;
	}
	/**
	 * @return the hBcAb
	 */
	public Value getHBcAb() {
		return HBcAb;
	}
	/**
	 * @param hBcAb the hBcAb to set
	 */
	public void setHBcAb(Value hBcAb) {
		HBcAb = hBcAb;
	}
	
	
	
	
	

}
