/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;
import java.util.List;

/**
 *<p>Title:InDiagnosis</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 下午2:10:13
 */
public class InDiagnosis implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String codeSystem;
	private String value;
	
	private List<Item> item;

	public InDiagnosis(){}
	public InDiagnosis(List<Item> item){
		this.item=item;
	}
	/**
	 * @return the item
	 */
	public List<Item> getItem() {
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(List<Item> item) {
		this.item = item;
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
