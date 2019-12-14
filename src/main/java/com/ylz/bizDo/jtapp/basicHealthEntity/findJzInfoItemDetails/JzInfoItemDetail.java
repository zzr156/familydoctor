/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findJzInfoItemDetails;

import java.io.Serializable;
import java.util.List;

/**
 *<p>Title:JzInfoItemDetail</p> 
 *<p>Description:就诊详细信息</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-23 上午11:33:54
 */
public class JzInfoItemDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 序号:1：门诊基本诊疗信息、住院基本诊疗信息；2：长期医嘱，临时医嘱；3：门诊费用明细，住院费用明细，住院病案首例；
	 * 4：门诊用药记录，住院用药记录；5：门诊检验报告，住院检验报告；
	 * 6：门诊检查报告，住院检查报告；
	 */
	private String seq;
	/**
	 * 挂号号（门诊号/住院号/检验报告单号/检查报告单号）
	 */
	private String ghh000;
	/**
	 * 病人id
	 */
	private String patientId;
	/**
	 * 机构代码
	 */
	private String organizationCode;
	/**
	 * 结果编号
	 */
	private String studyNo;
	/**
	 * 就诊详细子信息
	 */
	private List<JzInfoItemSubDetail> subDetails;
	/**
	 * 地区编号
	 */
	private String areacode;
	/**
	 * 检验或检查编号
	 */
	private String masterId;
	/**
	 * 检验或检查名称
	 */
	private String masterName;
	
	public JzInfoItemDetail(){}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(String seq) {
		this.seq = seq;
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
	 * @return the organizationCode
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * @return the subDetails
	 */
	public List<JzInfoItemSubDetail> getSubDetails() {
		return subDetails;
	}

	/**
	 * @param subDetails the subDetails to set
	 */
	public void setSubDetails(List<JzInfoItemSubDetail> subDetails) {
		this.subDetails = subDetails;
	}

	/**
	 * @param organizationCode the organizationCode to set
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * @return the studyNo
	 */
	public String getStudyNo() {
		return studyNo;
	}

	/**
	 * @param studyNo the studyNo to set
	 */
	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
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

	/**
	 * @return the masterId
	 */
	public String getMasterId() {
		return masterId;
	}

	/**
	 * @param masterId the masterId to set
	 */
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	/**
	 * @return the masterName
	 */
	public String getMasterName() {
		return masterName;
	}

	/**
	 * @param masterName the masterName to set
	 */
	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	 
	
	

}
