/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:SixMinuteWalk</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午4:44:19
 */
public class SixMinuteWalk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String value;
	
	public SixMinuteWalk(){}
	public SixMinuteWalk(String value){
		this.value=value;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	 
	 

}
