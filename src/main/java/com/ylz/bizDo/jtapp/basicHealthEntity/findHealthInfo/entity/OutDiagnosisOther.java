/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;
import java.util.List;

/**
 *<p>Title:OutDiagnosisOther</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-7 下午5:34:09
 */
public class OutDiagnosisOther implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Item> item;
	
	public OutDiagnosisOther(){}
	public OutDiagnosisOther(List<Item> item){
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
