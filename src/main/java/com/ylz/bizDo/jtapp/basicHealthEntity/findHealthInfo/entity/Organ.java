/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Organ</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午2:00:40
 */
public class Organ implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Sight sight;
	private String hearing;
	private String motorFunction;

	
	public Organ(){}
	public Organ(Sight sight,String hearing,String motorFunction){
		this.sight=sight;
		this.hearing=hearing;
		this.motorFunction=motorFunction;
	}
	/**
	 * @return the sight
	 */
	public Sight getSight() {
		return sight;
	}
	/**
	 * @param sight the sight to set
	 */
	public void setSight(Sight sight) {
		this.sight = sight;
	}
	/**
	 * @return the hearing
	 */
	public String getHearing() {
		return hearing;
	}
	/**
	 * @param hearing the hearing to set
	 */
	public void setHearing(String hearing) {
		this.hearing = hearing;
	}
	/**
	 * @return the motorFunction
	 */
	public String getMotorFunction() {
		return motorFunction;
	}
	/**
	 * @param motorFunction the motorFunction to set
	 */
	public void setMotorFunction(String motorFunction) {
		this.motorFunction = motorFunction;
	}
	
	
}
