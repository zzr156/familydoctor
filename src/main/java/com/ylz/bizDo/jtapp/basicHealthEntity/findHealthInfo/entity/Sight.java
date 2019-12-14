/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 *<p>Title:Sight</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午2:03:39
 */
@XmlType(name="com.start.webInterface.common.dto.byXmlHealthInfo.Sight")
@XmlAccessorType(XmlAccessType.NONE)
public class Sight implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Original original;
	private Corrected corrected;
	
	public Sight(){}
	public Sight(Original original,Corrected corrected){
		this.original=original;
		this.corrected=corrected;
	}
	/**
	 * @return the original
	 */
	public Original getOriginal() {
		return original;
	}
	/**
	 * @param original the original to set
	 */
	public void setOriginal(Original original) {
		this.original = original;
	}
	/**
	 * @return the corrected
	 */
	public Corrected getCorrected() {
		return corrected;
	}
	/**
	 * @param corrected the corrected to set
	 */
	public void setCorrected(Corrected corrected) {
		this.corrected = corrected;
	}
	
	
	

}
