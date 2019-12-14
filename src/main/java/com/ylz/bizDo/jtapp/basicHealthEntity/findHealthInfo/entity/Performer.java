/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Performer</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 上午11:26:40
 */
public class Performer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String assignedEntity;
	private String name;
	private String time;
	
	public Performer(){}
	public Performer(String assignedEntity,String name,String time){
		this.assignedEntity=assignedEntity;
		this.name=name;
		this.time=time;
	}
	/**
	 * @return the assignedEntity
	 */
	public String getAssignedEntity() {
		return assignedEntity;
	}
	/**
	 * @param assignedEntity the assignedEntity to set
	 */
	public void setAssignedEntity(String assignedEntity) {
		this.assignedEntity = assignedEntity;
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
