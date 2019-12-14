/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;


/**
 *<p>Title:COPD</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-7-8 下午4:27:44
 */
public class COPD implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6236266619684957654L;
	private Symptom2 symptom;
	private BodyExam2 bodyExam;
	private Other other;
	
	public COPD(){}
	public COPD(Symptom2 symptom,BodyExam2 bodyExam,Other other){
		this.symptom=symptom;
		this.bodyExam=bodyExam;
		this.other=other;
	}
	/**
	 * @return the symptom
	 */
	public Symptom2 getSymptom() {
		return symptom;
	}
	/**
	 * @param symptom the symptom to set
	 */
	public void setSymptom(Symptom2 symptom) {
		this.symptom = symptom;
	}
	/**
	 * @return the bodyExam
	 */
	public BodyExam2 getBodyExam() {
		return bodyExam;
	}
	/**
	 * @param bodyExam the bodyExam to set
	 */
	public void setBodyExam(BodyExam2 bodyExam) {
		this.bodyExam = bodyExam;
	}
	/**
	 * @return the other
	 */
	public Other getOther() {
		return other;
	}
	/**
	 * @param other the other to set
	 */
	public void setOther(Other other) {
		this.other = other;
	}

}
