/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 *<p>Title:Order</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 上午11:18:07
 */
public class Order implements Serializable{

	private String id;
	private Priority priority;
	
	@XmlElement
	private List<Item> item;
	
	public Order(){}
	
	public Order(List<Item> item){
		this.item=item;
	}
	public Order(String id,Priority priority){
		this.id=id;
		this.priority=priority;
	}
	public Order(String id,Priority priority,List<Item> item){
		this.id=id;
		this.priority=priority;
		this.item=item;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the priority
	 */
	public Priority getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
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
