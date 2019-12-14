/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Hypertension</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午4:21:58
 */
public class Hypertension implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2146936665090928116L;
	private BloodBiochemistry bloodBiochemistry;
	

	public Hypertension(){}
	public Hypertension(BloodBiochemistry bloodBiochemistry){
		this.bloodBiochemistry=bloodBiochemistry;
	}
	/**
	 * @return the bloodBiochemistry
	 */
	public BloodBiochemistry getBloodBiochemistry() {
		return bloodBiochemistry;
	}
	/**
	 * @param bloodBiochemistry the bloodBiochemistry to set
	 */
	public void setBloodBiochemistry(BloodBiochemistry bloodBiochemistry) {
		this.bloodBiochemistry = bloodBiochemistry;
	}
	
	
	 
	

}
