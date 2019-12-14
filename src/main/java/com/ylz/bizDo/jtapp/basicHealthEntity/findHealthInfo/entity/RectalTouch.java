/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:RectalTouch</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午2:53:52
 */
public class RectalTouch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Value type;
	private String detail;
	
	public RectalTouch(){}
	public RectalTouch(Value type,String detail){
		this.type=type;
		this.detail=detail;
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
	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}
	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	

}
