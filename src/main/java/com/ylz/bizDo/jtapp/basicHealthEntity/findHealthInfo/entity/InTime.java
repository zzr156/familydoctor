/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:InTime</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 下午3:49:15
 */
public class InTime implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String value;

	public InTime(){}
	public InTime(String value){
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
