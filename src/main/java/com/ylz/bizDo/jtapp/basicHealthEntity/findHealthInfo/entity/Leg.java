/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Leg</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午2:51:30
 */
public class Leg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Value type;
	
	public Leg(){}
	public Leg(Value type){
		this.type=type;
	}

	/**
	 * @return the type
	 */
	public Value getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Value type) {
		this.type = type;
	}
	
	

}
