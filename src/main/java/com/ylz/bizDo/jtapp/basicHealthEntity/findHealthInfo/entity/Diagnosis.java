package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;
import java.util.List;


public class Diagnosis implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*@XmlElements(value = { @XmlElement(name = "item", type = Group.class) })*/
	private List<Item> item;
	
	public Diagnosis(){
		
	}
	public Diagnosis(List<Item> item){
		this.item=item;		
	}

	/**
	 * @return the items
	 */
	public List<Item> getItem() {
		return item;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<Item> item) {
		this.item = item;
	}

	
}
