/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;


/**
 *<p>Title:RecordTarget</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-28 下午2:13:53
 */

public class RecordTarget implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name="patient")
	private Patient patient;
	/*@XmlElement(name="id")
	private Id id;
	@XmlElement(name="name")
	private String name;
	@XmlElement(name="sex")
	private Sex sex;
	@XmlElement(name="birthDate")
	private String birthDate;
	@XmlElement(name="marriage")
	private Marriage marriage;*/
	public RecordTarget(){}
	public RecordTarget(Patient patient){
		this.patient=patient;
	}
	
	/**
	 * @return the patient
	 */
	public Patient getPatient() {
		return patient;
	}
	/**
	 * @param patient the patient to set
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	
	
}
