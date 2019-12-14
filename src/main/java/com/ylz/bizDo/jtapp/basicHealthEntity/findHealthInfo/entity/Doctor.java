/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Doctor</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-28 下午2:31:24
 */

public class Doctor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Id id;
	private String name;
	private Title title;
	
	public Doctor(){}
	public Doctor(Id id,String name,Title title){
		this.id=id;
		this.name=name;
		this.title=title;
	}
	/**
	 * @return the id
	 */
	public Id getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Id id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the title
	 */
	public Title getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(Title title) {
		this.title = title;
	}
	
}
