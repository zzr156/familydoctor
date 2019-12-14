/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;


/**
 *<p>Title:Component</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-28 下午2:28:42
 */

public class Component implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	 
	private Section section;

	public Component(){}
	public Component(Section section){
		this.section=section;
	}
	/**
	 * @return the section
	 */
	public Section getSection() {
		return section;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(Section section) {
		this.section = section;
	}

	 
	

}
