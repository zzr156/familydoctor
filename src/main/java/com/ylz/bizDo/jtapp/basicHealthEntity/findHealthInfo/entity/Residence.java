/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Residence</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 下午1:54:09
 */
public class Residence implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serial;
	private String code;
	
	public Residence(){}
	public Residence(String serial,String code){
		this.serial=serial;
		this.code=code;
	}
	/**
	 * @return the serial
	 */
	public String getSerial() {
		return serial;
	}
	/**
	 * @param serial the serial to set
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	

}
