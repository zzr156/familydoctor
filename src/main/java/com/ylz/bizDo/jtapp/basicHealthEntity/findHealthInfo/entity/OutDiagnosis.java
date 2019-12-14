/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;
import java.util.List;

/**
 *<p>Title:OutDiagnosis</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 下午2:12:31
 */
public class OutDiagnosis implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Item> item;
	
	public OutDiagnosis(){}
	public OutDiagnosis(List<Item> item){
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
