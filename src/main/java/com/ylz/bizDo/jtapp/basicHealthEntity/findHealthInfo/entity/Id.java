/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Id</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-28 下午2:13:06
 */

public class Id implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String extension;
	private String eventno;
	private String value;
	
	public Id(){}
	
	
	public Id(String extension){
		this.extension=extension;
	}
	
	public Id(String extension,String eventno,String value){
		this.extension=extension;
		this.eventno=eventno;
		this.value=value;
	}
	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	/**
	 * @return the eventno
	 */
	public String getEventno() {
		return eventno;
	}
	/**
	 * @param eventno the eventno to set
	 */
	public void setEventno(String eventno) {
		this.eventno = eventno;
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
