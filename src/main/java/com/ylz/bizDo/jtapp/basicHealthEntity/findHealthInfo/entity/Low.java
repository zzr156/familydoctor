package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;


public class Low implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String value;
	private String unit;
	
	public Low(){}
	public Low(String value,String unit){
		this.value=value;
		this.unit=unit;
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
	
	
}
