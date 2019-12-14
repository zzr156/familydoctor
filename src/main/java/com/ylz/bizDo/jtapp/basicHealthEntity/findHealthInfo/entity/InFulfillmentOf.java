/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:InFulfillmentOf</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 上午11:16:42
 */
public class InFulfillmentOf implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Order order;
	
	public InFulfillmentOf(){}
	public InFulfillmentOf(Order order){
		this.order=order;
	}
	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	

}
