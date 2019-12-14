/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Sec</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-28 下午2:30:30
 */

public class Sec implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String value;
	private Type type;
	
	public Sec(){}
	public Sec(String value,Type type){
		this.value=value;
		this.type=type;
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
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	
}
