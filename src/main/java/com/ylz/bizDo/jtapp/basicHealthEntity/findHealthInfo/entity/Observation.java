/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;

/**
 *<p>Title:Observation</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-29 上午11:38:54
 */
public class Observation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Code code;
	private Type type;
	private String value;
	private String unit;
	private ReferenceRange referenceRange;
	private String effectiveTime;
	private InterpretationCode interpretationCode;
	
	public Observation(){}
	public Observation(Code code,Type type,String value,String unit,ReferenceRange referenceRange,
			String effectiveTime,InterpretationCode interpretationCode){
		this.code=code;
		this.type=type;
		this.value=value;
		this.unit=unit;
		this.referenceRange=referenceRange;
		this.effectiveTime=effectiveTime;
		this.interpretationCode=interpretationCode;
	}
	/**
	 * @return the code
	 */
	public Code getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(Code code) {
		this.code = code;
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the referenceRange
	 */
	public ReferenceRange getReferenceRange() {
		return referenceRange;
	}
	/**
	 * @param referenceRange the referenceRange to set
	 */
	public void setReferenceRange(ReferenceRange referenceRange) {
		this.referenceRange = referenceRange;
	}
	/**
	 * @return the effectiveTime
	 */
	public String getEffectiveTime() {
		return effectiveTime;
	}
	/**
	 * @param effectiveTime the effectiveTime to set
	 */
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	/**
	 * @return the interpretationCode
	 */
	public InterpretationCode getInterpretationCode() {
		return interpretationCode;
	}
	/**
	 * @param interpretationCode the interpretationCode to set
	 */
	public void setInterpretationCode(InterpretationCode interpretationCode) {
		this.interpretationCode = interpretationCode;
	}
	
	
	

}
