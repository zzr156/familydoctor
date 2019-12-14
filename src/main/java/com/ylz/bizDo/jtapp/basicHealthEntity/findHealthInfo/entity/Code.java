/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;


/**
 *<p>Title:Code</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-28 下午3:37:12
 */
public class Code implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String codeSystem;
	private String codeSystemName;
	private String displayName;
	private String bodyPartCode;
	private String bodyPartName;
	private String examCode;
	private String examName;
	private String value;
	
	public Code(){}
	public Code(String code,String codeSystem,String displayName){
		this.code=code;
		this.codeSystem=codeSystem;
		this.displayName=displayName;
	}
	public Code(String code,String codeSystem,String codeSystemName,String displayName){
		this.code=code;
		this.codeSystem=codeSystem;
		this.codeSystemName=codeSystemName;
		this.displayName=displayName;
	}
	public Code(String code,String codeSystem,String codeSystemName,String displayName,String value){
		this.code=code;
		this.codeSystem=codeSystem;
		this.codeSystemName=codeSystemName;
		this.displayName=displayName;
		this.value=value;
				
	}
	public Code(String code,String displayName,String bodyPartCode,
			String bodyPartName,String examCode,String examName){
		this.code=code;
		this.displayName=displayName;
		this.bodyPartCode=bodyPartCode;
		this.bodyPartName=bodyPartName;
		this.examCode=examCode;
		this.examName=examName;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the codeSystem
	 */
	public String getCodeSystem() {
		return codeSystem;
	}
	/**
	 * @param codeSystem the codeSystem to set
	 */
	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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
	 * @return the codeSystemName
	 */
	public String getCodeSystemName() {
		return codeSystemName;
	}
	/**
	 * @param codeSystemName the codeSystemName to set
	 */
	public void setCodeSystemName(String codeSystemName) {
		this.codeSystemName = codeSystemName;
	}
	/**
	 * @return the bodyPartCode
	 */
	public String getBodyPartCode() {
		return bodyPartCode;
	}
	/**
	 * @param bodyPartCode the bodyPartCode to set
	 */
	public void setBodyPartCode(String bodyPartCode) {
		this.bodyPartCode = bodyPartCode;
	}
	/**
	 * @return the bodyPartName
	 */
	public String getBodyPartName() {
		return bodyPartName;
	}
	/**
	 * @param bodyPartName the bodyPartName to set
	 */
	public void setBodyPartName(String bodyPartName) {
		this.bodyPartName = bodyPartName;
	}
	/**
	 * @return the examCode
	 */
	public String getExamCode() {
		return examCode;
	}
	/**
	 * @param examCode the examCode to set
	 */
	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}
	/**
	 * @return the examName
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * @param examName the examName to set
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}

	
}
