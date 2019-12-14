/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;


public class RootDocument implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String reg;
	private String sec;
	private String dept;
	private String doctor;
	private DiagnosisItem diagnosis;
	private String type ="0";
	
	
 
	public RootDocument(){
		
	}
	public RootDocument(String reg,String sec,String dept,String doctor,DiagnosisItem diagnosis){
		this.reg=reg;
		this.sec=sec;
		this.dept=dept;
		this.doctor=doctor;
		this.diagnosis=diagnosis;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReg() {
		return reg;
	}
	public void setReg(String reg) {
		this.reg = reg;
	}
	public String getSec() {
		return sec;
	}
	public void setSec(String sec) {
		this.sec = sec;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public DiagnosisItem getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(DiagnosisItem diagnosis) {
		this.diagnosis = diagnosis;
	}
	
}
