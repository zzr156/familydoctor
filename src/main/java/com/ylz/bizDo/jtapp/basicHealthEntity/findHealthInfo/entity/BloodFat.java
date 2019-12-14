/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:BloodFat</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午3:16:56
 */
public class BloodFat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String TG;
	private String LDL_C;
	private String HDL_C;
	private String TC;
	
	public BloodFat(){}
	public BloodFat(String TG,String LDL_C,
			String HDL_C,String TC){
		this.TG=TG;
		this.LDL_C=LDL_C;
		this.HDL_C=HDL_C;
		this.TC=TC;
	}
	/**
	 * @return the tG
	 */
	public String getTG() {
		return TG;
	}
	/**
	 * @param tG the tG to set
	 */
	public void setTG(String tG) {
		TG = tG;
	}
	/**
	 * @return the lDL_C
	 */
	public String getLDL_C() {
		return LDL_C;
	}
	/**
	 * @param lDL_C the lDL_C to set
	 */
	public void setLDL_C(String lDL_C) {
		LDL_C = lDL_C;
	}
	/**
	 * @return the hDL_C
	 */
	public String getHDL_C() {
		return HDL_C;
	}
	/**
	 * @param hDL_C the hDL_C to set
	 */
	public void setHDL_C(String hDL_C) {
		HDL_C = hDL_C;
	}
	/**
	 * @return the tC
	 */
	public String getTC() {
		return TC;
	}
	/**
	 * @param tC the tC to set
	 */
	public void setTC(String tC) {
		TC = tC;
	}
	

}
