/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:DeptOfficer</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 下午3:25:23
 */
public class DeptOfficer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Id id;
	private String name;
	private String time;
	
	public DeptOfficer(){}
	
	public DeptOfficer(Id id,String name){
		this.id=id;
		this.name=name;
	}
	public DeptOfficer(Id id,String name,String time){
		this.id=id;
		this.name=name;
		this.time=time;
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
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	
	
	

}
