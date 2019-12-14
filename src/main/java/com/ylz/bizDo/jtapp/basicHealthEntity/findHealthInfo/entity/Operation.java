/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;
import java.util.List;


/**
 *<p>Title:Operation</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-7 下午4:11:21
 */

public class Operation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Item> item;
	
	public Operation(){}
	public Operation(List<Item> item){
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
