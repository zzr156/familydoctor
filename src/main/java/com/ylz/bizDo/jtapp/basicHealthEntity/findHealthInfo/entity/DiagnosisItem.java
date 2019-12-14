package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;


public class DiagnosisItem implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/*@XmlElements(value = { @XmlElement(name = "item", type = Group.class) })*/
	private String item;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
}
