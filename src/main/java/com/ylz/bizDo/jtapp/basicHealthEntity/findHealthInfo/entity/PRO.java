/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:PRO</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午3:30:04
 */
public class PRO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Value result;
	private String value;
	
	public PRO(){}
	public PRO(Value result,String value){
		this.result=result;
		this.value=value;
	}
	/**
	 * @return the result
	 */
	public Value getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(Value result) {
		this.result = result;
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
