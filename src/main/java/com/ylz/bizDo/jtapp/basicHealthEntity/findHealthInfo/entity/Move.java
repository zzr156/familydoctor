/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;
import java.util.List;

/**
 *<p>Title:Move</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-6 下午7:04:19
 */
public class Move implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*@XmlElement(name="time")
	private String time;
	@XmlElement(name="dept")
	private Dept dept;
	@XmlElement(name="bed")
	private String bed;*/
	
	private List<Item> item;
	
	public Move(){}
	
	public Move(List<Item> item){
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
	
}
