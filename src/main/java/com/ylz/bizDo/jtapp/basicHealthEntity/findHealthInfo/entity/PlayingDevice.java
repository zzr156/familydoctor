/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:PlayingDevice</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 上午11:36:53
 */
public class PlayingDevice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3795178413070805929L;
	private String name;
	
	public PlayingDevice(){}
	public PlayingDevice(String name){
		this.name=name;
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
	
	

}
