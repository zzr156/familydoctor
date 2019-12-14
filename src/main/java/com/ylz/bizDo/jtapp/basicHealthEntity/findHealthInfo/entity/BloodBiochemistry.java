/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:BloodBiochemistry</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午4:23:12
 */
public class BloodBiochemistry implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2357606632056268975L;
	private String k;
	private String na;
	
	public BloodBiochemistry(){}
	public BloodBiochemistry(String k,String na){
		this.k=k;
		this.na=na;
	}
	/**
	 * @return the k
	 */
	public String getK() {
		return k;
	}
	/**
	 * @param k the k to set
	 */
	public void setK(String k) {
		this.k = k;
	}
	/**
	 * @return the na
	 */
	public String getNa() {
		return na;
	}
	/**
	 * @param na the na to set
	 */
	public void setNa(String na) {
		this.na = na;
	}

	
}
