/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findJzInfoItems;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *<p>Title:JzInfoDTO</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-21 下午1:02:56
 */
@XStreamAlias("JzInfoDTO")
public class JzInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 社保卡号
	 */
	private String ssnumber;
	/**
	 * 就诊城市
	 */
	private String city;
	/**
	 * 就诊日期(住院日期)
	 */
	private String jiuzhenDate;
	/**
	 * 就诊医院(就诊机构)
	 */
	private String organizationName;
	/**
	 * 就诊机构编码
	 */
	private String organizationCode;
	/**
	 * 诊疗类型(0:门急诊，1:住院)
	 */
	private String jzType;
	/**
	 * 诊断名称
	 */
	private String diagnosisDiseaseName;
	/**
	 * 诊断代码
	 */
	private String diagnosisDiseaseCode;
	/**
	 * 就诊结束时间(出院日期)
	 */
	private String jiuzhenEndDate;
	/**
	 * 门诊号
	 */
	private String ghh000;
 
	/**
	 * 病人Id
	 */
	private String patientId;
	/**
	 * 就诊类型
	 */
	private String jiuzhenCode;
	/**
	 * 就诊名称
	 */
	private String jiuzhenName;
	/**
	 * 费用
	 */
	private BigDecimal totalFee;
	/**
	 * 诊疗类型代码
	 */
	private String typecode;
	/**
	 * 就诊地区编码
	 */
	private String areacode;
	
	public JzInfoDTO(){}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	 
	/**
	 * @return the jiuzhenDate
	 */
	public String getJiuzhenDate() {
		return jiuzhenDate;
	}
	/**
	 * @param jiuzhenDate the jiuzhenDate to set
	 */
	public void setJiuzhenDate(String jiuzhenDate) {
		this.jiuzhenDate = jiuzhenDate;
	}
	/**
	 * @param jiuzhenEndDate the jiuzhenEndDate to set
	 */
	public void setJiuzhenEndDate(String jiuzhenEndDate) {
		this.jiuzhenEndDate = jiuzhenEndDate;
	}
	/**
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}
	/**
	 * @param organizationName the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	/**
	 * @return the organizationCode
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}
	/**
	 * @param organizationCode the organizationCode to set
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	
	/**
	 * @return the totalFee
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	/**
	 * @param totalFee the totalFee to set
	 */
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	/**
	 * @return the jzType
	 */
	public String getJzType() {
		return jzType;
	}
	/**
	 * @param jzType the jzType to set
	 */
	public void setJzType(String jzType) {
		this.jzType = jzType;
	}
	/**
	 * @return the ssnumber
	 */
	public String getSsnumber() {
		return ssnumber;
	}
	/**
	 * @param ssnumber the ssnumber to set
	 */
	public void setSsnumber(String ssnumber) {
		this.ssnumber = ssnumber;
	}
	/**
	 * @return the diagnosisDiseaseName
	 */
	public String getDiagnosisDiseaseName() {
		return diagnosisDiseaseName;
	}
	/**
	 * @param diagnosisDiseaseName the diagnosisDiseaseName to set
	 */
	public void setDiagnosisDiseaseName(String diagnosisDiseaseName) {
		this.diagnosisDiseaseName = diagnosisDiseaseName;
	}
	/**
	 * @return the diagnosisDiseaseCode
	 */
	public String getDiagnosisDiseaseCode() {
		return diagnosisDiseaseCode;
	}
	/**
	 * @param diagnosisDiseaseCode the diagnosisDiseaseCode to set
	 */
	public void setDiagnosisDiseaseCode(String diagnosisDiseaseCode) {
		this.diagnosisDiseaseCode = diagnosisDiseaseCode;
	}
 
	/**
	 * @return the jiuzhenEndDate
	 */
	public String getJiuzhenEndDate() {
		return jiuzhenEndDate;
	}
	 
 
	/**
	 * @return the ghh000
	 */
	public String getGhh000() {
		return ghh000;
	}
	/**
	 * @param ghh000 the ghh000 to set
	 */
	public void setGhh000(String ghh000) {
		this.ghh000 = ghh000;
	}
	/**
	 * @return the jiuzhenCode
	 */
	public String getJiuzhenCode() {
		return jiuzhenCode;
	}
	/**
	 * @param jiuzhenCode the jiuzhenCode to set
	 */
	public void setJiuzhenCode(String jiuzhenCode) {
		this.jiuzhenCode = jiuzhenCode;
	}
	/**
	 * @return the jiuzhenName
	 */
	public String getJiuzhenName() {
		return jiuzhenName;
	}
	/**
	 * @param jiuzhenName the jiuzhenName to set
	 */
	public void setJiuzhenName(String jiuzhenName) {
		this.jiuzhenName = jiuzhenName;
	}
	/**
	 * @return the patientId
	 */
	public String getPatientId() {
		return patientId;
	}
	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	/**
	 * @return the typecode
	 */
	public String getTypecode() {
		return typecode;
	}
	/**
	 * @param typecode the typecode to set
	 */
	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	/**
	 * @return the areacode
	 */
	public String getAreacode() {
		return areacode;
	}
	/**
	 * @param areacode the areacode to set
	 */
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	
	 

}

