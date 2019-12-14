/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:In</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 下午1:56:12
 */
public class In implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String time;
	private Dept dept;
	private String bed;
	
	public In(){}
	public In(String time,Dept dept,String bed){
		this.time=time;
		this.dept=dept;
		this.bed=bed;
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
	/**
	 * @return the dept
	 */
	public Dept getDept() {
		return dept;
	}
	/**
	 * @param dept the dept to set
	 */
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	/**
	 * @return the bed
	 */
	public String getBed() {
		return bed;
	}
	/**
	 * @param bed the bed to set
	 */
	public void setBed(String bed) {
		this.bed = bed;
	}
	
	

}
