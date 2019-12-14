/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Section</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-28 下午2:29:07
 */


public class Section implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Code code;
	private Entry entry;
	
	public Section(){}
	public Section(Entry entry){
		this.entry=entry;
	}
	public Section(Code code,Entry entry){
		this.code=code;
		this.entry=entry;
	}
	/**
	 * @return the code
	 */
	public Code getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(Code code) {
		this.code = code;
	}
	/**
	 * @return the entry
	 */
	public Entry getEntry() {
		return entry;
	}
	/**
	 * @param entry the entry to set
	 */
	public void setEntry(Entry entry) {
		this.entry = entry;
	}
	
	

}
