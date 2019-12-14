/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Medicine</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 上午10:35:09
 */
public class Medicine implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String offertime;
	private Value value;
	private CnType cnType;
	private Type type;
	private Form form;
	private String spec;
	private String doseQuantity;
	private String doseUnit;
	private String totalQuantity;
	private String totalUnit;
	private String notes;
	
	public Medicine(){}
	public Medicine(String offertime,Value value,CnType cnType,
			Type type,Form form,String spec,String doseQuantity,
			String doseUnit,String totalQuantity,
			String totalUnit,String notes){
		this.offertime=offertime;
		this.value=value;
		this.cnType=cnType;
		this.type=type;
		this.form=form;
		this.spec=spec;
		this.doseQuantity=doseQuantity;
		this.doseUnit=doseUnit;
		this.totalQuantity=totalQuantity;
		this.doseUnit=doseUnit;
		this.notes=notes;
		
	}
	/**
	 * @return the offertime
	 */
	public String getOffertime() {
		return offertime;
	}
	/**
	 * @param offertime the offertime to set
	 */
	public void setOffertime(String offertime) {
		this.offertime = offertime;
	}
	/**
	 * @return the value
	 */
	public Value getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Value value) {
		this.value = value;
	}
	/**
	 * @return the cnType
	 */
	public CnType getCnType() {
		return cnType;
	}
	/**
	 * @param cnType the cnType to set
	 */
	public void setCnType(CnType cnType) {
		this.cnType = cnType;
	}
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}
	/**
	 * @return the form
	 */
	public Form getForm() {
		return form;
	}
	/**
	 * @param form the form to set
	 */
	public void setForm(Form form) {
		this.form = form;
	}
	/**
	 * @return the spec
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * @param spec the spec to set
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	/**
	 * @return the doseQuantity
	 */
	public String getDoseQuantity() {
		return doseQuantity;
	}
	/**
	 * @param doseQuantity the doseQuantity to set
	 */
	public void setDoseQuantity(String doseQuantity) {
		this.doseQuantity = doseQuantity;
	}
	/**
	 * @return the doseUnit
	 */
	public String getDoseUnit() {
		return doseUnit;
	}
	/**
	 * @param doseUnit the doseUnit to set
	 */
	public void setDoseUnit(String doseUnit) {
		this.doseUnit = doseUnit;
	}
	/**
	 * @return the totalQuantity
	 */
	public String getTotalQuantity() {
		return totalQuantity;
	}
	/**
	 * @param totalQuantity the totalQuantity to set
	 */
	public void setTotalQuantity(String totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	/**
	 * @return the totalUnit
	 */
	public String getTotalUnit() {
		return totalUnit;
	}
	/**
	 * @param totalUnit the totalUnit to set
	 */
	public void setTotalUnit(String totalUnit) {
		this.totalUnit = totalUnit;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	
	
	

}
