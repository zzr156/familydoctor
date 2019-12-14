/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:RedBloodCell</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-7 下午4:38:28
 */
public class RedBloodCell implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String unit;
	private String value;
	
	public RedBloodCell(){}
	public RedBloodCell(String unit,String value){
		this.unit=unit;
		this.value=value;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
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
