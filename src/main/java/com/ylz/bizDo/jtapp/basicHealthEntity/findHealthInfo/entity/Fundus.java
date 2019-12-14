/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Fundus</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午3:20:08
 */
public class Fundus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Value tag;
	private String detail;
	
	public Fundus(){
		
	}
	
	public Fundus(Value tag,String detail){
		this.tag=tag;
		this.detail=detail;
	}

	/**
	 * @return the tag
	 */
	public Value getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(Value tag) {
		this.tag = tag;
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
