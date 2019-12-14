package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

public class Fee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String total;
	private String bed;
	private String tend;
	private String westmedicine;
	private String cnmedicine1;
	private String cnmedicine2;
	private String radiation;
	private String assay;
	private String oxygen;
	private String blood;
	private String diagnosis;
	private String treatment;
	private String operation;
	private String deliver;
	private String check;
	private String anaes;
	private String bady;
	private String extraBed;
	private String other1;
	private String other2;
	private String other3;
	private String other4;
	
	public Fee(){}
	public Fee(String total,String bed,String tend,String westmedicine,
			String cnmedicine1,String cnmedicine2,String radiation,
			String assay,String oxygen,String blood,String diagnosis,
			String treatment,String operation,String deliver,
			String check,String anaes,String baby,String extraBed,
			String other1,String other2,String other3,String other4){
		this.total=total;
		this.bed=bed;
		this.tend=tend;
		this.westmedicine=westmedicine;
		this.cnmedicine1=cnmedicine1;
		this.cnmedicine2=cnmedicine2;
		this.radiation=radiation;
		this.assay=assay;
		this.oxygen=oxygen;
		this.blood=blood;
		this.diagnosis=diagnosis;
		this.treatment=treatment;
		this.operation=operation;
		this.deliver=deliver;
		this.check=check;
		this.anaes=anaes;
		this.bady=baby;
		this.extraBed=extraBed;
		this.other1=other1;
		this.other2=other2;
		this.other3=other3;
		this.other4=other4;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * @return the bed
	 */
	public String getBed() {
		return bed;
	}
	/**
	 * @param bed the bed to set
	 */
	public void setBed(String bed) {
		this.bed = bed;
	}
	/**
	 * @return the tend
	 */
	public String getTend() {
		return tend;
	}
	/**
	 * @param tend the tend to set
	 */
	public void setTend(String tend) {
		this.tend = tend;
	}
	/**
	 * @return the westmedicine
	 */
	public String getWestmedicine() {
		return westmedicine;
	}
	/**
	 * @param westmedicine the westmedicine to set
	 */
	public void setWestmedicine(String westmedicine) {
		this.westmedicine = westmedicine;
	}
	/**
	 * @return the cnmedicine1
	 */
	public String getCnmedicine1() {
		return cnmedicine1;
	}
	/**
	 * @param cnmedicine1 the cnmedicine1 to set
	 */
	public void setCnmedicine1(String cnmedicine1) {
		this.cnmedicine1 = cnmedicine1;
	}
	/**
	 * @return the cnmedicine2
	 */
	public String getCnmedicine2() {
		return cnmedicine2;
	}
	/**
	 * @param cnmedicine2 the cnmedicine2 to set
	 */
	public void setCnmedicine2(String cnmedicine2) {
		this.cnmedicine2 = cnmedicine2;
	}
	/**
	 * @return the radiation
	 */
	public String getRadiation() {
		return radiation;
	}
	/**
	 * @param radiation the radiation to set
	 */
	public void setRadiation(String radiation) {
		this.radiation = radiation;
	}
	/**
	 * @return the assay
	 */
	public String getAssay() {
		return assay;
	}
	/**
	 * @param assay the assay to set
	 */
	public void setAssay(String assay) {
		this.assay = assay;
	}
	/**
	 * @return the oxygen
	 */
	public String getOxygen() {
		return oxygen;
	}
	/**
	 * @param oxygen the oxygen to set
	 */
	public void setOxygen(String oxygen) {
		this.oxygen = oxygen;
	}
	/**
	 * @return the blood
	 */
	public String getBlood() {
		return blood;
	}
	/**
	 * @param blood the blood to set
	 */
	public void setBlood(String blood) {
		this.blood = blood;
	}
	/**
	 * @return the diagnosis
	 */
	public String getDiagnosis() {
		return diagnosis;
	}
	/**
	 * @param diagnosis the diagnosis to set
	 */
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	/**
	 * @return the treatment
	 */
	public String getTreatment() {
		return treatment;
	}
	/**
	 * @param treatment the treatment to set
	 */
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	/**
	 * @return the deliver
	 */
	public String getDeliver() {
		return deliver;
	}
	/**
	 * @param deliver the deliver to set
	 */
	public void setDeliver(String deliver) {
		this.deliver = deliver;
	}
	/**
	 * @return the check
	 */
	public String getCheck() {
		return check;
	}
	/**
	 * @param check the check to set
	 */
	public void setCheck(String check) {
		this.check = check;
	}
	/**
	 * @return the anaes
	 */
	public String getAnaes() {
		return anaes;
	}
	/**
	 * @param anaes the anaes to set
	 */
	public void setAnaes(String anaes) {
		this.anaes = anaes;
	}
	/**
	 * @return the bady
	 */
	public String getBady() {
		return bady;
	}
	/**
	 * @param bady the bady to set
	 */
	public void setBady(String bady) {
		this.bady = bady;
	}
	/**
	 * @return the extraBed
	 */
	public String getExtraBed() {
		return extraBed;
	}
	/**
	 * @param extraBed the extraBed to set
	 */
	public void setExtraBed(String extraBed) {
		this.extraBed = extraBed;
	}
	/**
	 * @return the other1
	 */
	public String getOther1() {
		return other1;
	}
	/**
	 * @param other1 the other1 to set
	 */
	public void setOther1(String other1) {
		this.other1 = other1;
	}
	/**
	 * @return the other2
	 */
	public String getOther2() {
		return other2;
	}
	/**
	 * @param other2 the other2 to set
	 */
	public void setOther2(String other2) {
		this.other2 = other2;
	}
	/**
	 * @return the other3
	 */
	public String getOther3() {
		return other3;
	}
	/**
	 * @param other3 the other3 to set
	 */
	public void setOther3(String other3) {
		this.other3 = other3;
	}
	/**
	 * @return the other4
	 */
	public String getOther4() {
		return other4;
	}
	/**
	 * @param other4 the other4 to set
	 */
	public void setOther4(String other4) {
		this.other4 = other4;
	}

	
	
}
