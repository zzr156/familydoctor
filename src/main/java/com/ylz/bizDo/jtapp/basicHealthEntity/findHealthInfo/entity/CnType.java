/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:CnType</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 上午10:36:15
 */
public class CnType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String codeSystem;
	private String value;
	
	public CnType(){}
	public CnType(String code,String codeSystem,String value){
		this.code=code;
		this.codeSystem=codeSystem;
		this.value=value;
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
	/**
	 * @return the codeSystem
	 */
	public String getCodeSystem() {
		return codeSystem;
	}
	/**
	 * @param codeSystem the codeSystem to set
	 */
	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
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
