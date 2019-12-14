package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**签约批次表
 * AppSignBatch entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_SIGN_BATCH")
public class AppSignBatch extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "BATCH_NUM", length = 30)
	private String batchNum;//批次号
	@Column(name = "BATCH_PATIENT_NAME", length = 20)
	private String batchPatientName;//签约成员名称（户主）
	@Column(name = "BATCH_TEAM_ID", length = 36)
	private String batchTeamId;//签约团队主键
	@Column(name = "BATCH_TEAM_NAME", length = 20)
	private String batchTeamName;//签约团队名称
	@Column(name = "BATCH_CREATE_PERSID", length = 36)
	private String batchCreatePersid;//创建者主键（户主）
	@Column(name = "BATCH_CREATE_DATE", length = 19)
	private Calendar batchCreateDate;//创建时间
	@Column(name = "BATCH_AREA_CODE", length = 19)
	private String batchAreaCode;//行政区划

	@Column(name = "BATCH_OPERATOR_ID", length = 36)
	private String batchOperatorId;//登陆人操作人id
	@Column(name = "BATCH_OPERATOR_NAME", length = 20)
	private String batchOperatorName;//登陆人操作人名称

	@Column(name = "BATCH_CHANGE_OPERATOR_ID",length = 36)
	private String batchChangeOperatorId;//变更操作人id
	@Column(name = "BATCH_CHANGE_OPERATOR_NAME",length = 20)
	private String batchChangeOperatorName;//变更操作人姓名

	// Constructors

	/** default constructor */
	public AppSignBatch() {
	}

	/** minimal constructor */
	public AppSignBatch(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppSignBatch(String id, String batchNum,
			String batchPatientName, String batchTeamId, String batchTeamName,
			String batchCreatePersid, Calendar batchCreateDate,String batchAreaCode,String batchOperatorId,String batchOperatorName) {
		this.id = id;
		this.batchNum = batchNum;
		this.batchPatientName = batchPatientName;
		this.batchTeamId = batchTeamId;
		this.batchTeamName = batchTeamName;
		this.batchCreatePersid = batchCreatePersid;
		this.batchCreateDate = batchCreateDate;
		this.batchAreaCode = batchAreaCode;
		this.batchOperatorName=batchOperatorName;
		this.batchOperatorId=batchOperatorId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatchNum() {
		return this.batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}


	public String getBatchPatientName() {
		return this.batchPatientName;
	}

	public void setBatchPatientName(String batchPatientName) {
		this.batchPatientName = batchPatientName;
	}


	public String getBatchTeamId() {
		return this.batchTeamId;
	}

	public void setBatchTeamId(String batchTeamId) {
		this.batchTeamId = batchTeamId;
	}


	public String getBatchTeamName() {
		return this.batchTeamName;
	}

	public void setBatchTeamName(String batchTeamName) {
		this.batchTeamName = batchTeamName;
	}


	public String getBatchCreatePersid() {
		return this.batchCreatePersid;
	}

	public void setBatchCreatePersid(String batchCreatePersid) {
		this.batchCreatePersid = batchCreatePersid;
	}

	public Calendar getBatchCreateDate() {
		return this.batchCreateDate;
	}

	public void setBatchCreateDate(Calendar batchCreateDate) {
		this.batchCreateDate = batchCreateDate;
	}

	public String getBatchAreaCode() {
		return batchAreaCode;
	}

	public void setBatchAreaCode(String batchAreaCode) {
		this.batchAreaCode = batchAreaCode;
	}

	public String getBatchOperatorId() {
		return batchOperatorId;
	}

	public void setBatchOperatorId(String batchOperatorId) {
		this.batchOperatorId = batchOperatorId;
	}

	public String getBatchOperatorName() {
		return batchOperatorName;
	}

	public void setBatchOperatorName(String batchOperatorName) {
		this.batchOperatorName = batchOperatorName;
	}

	public String getBatchChangeOperatorId() {
		return batchChangeOperatorId;
	}

	public void setBatchChangeOperatorId(String batchChangeOperatorId) {
		this.batchChangeOperatorId = batchChangeOperatorId;
	}

	public String getBatchChangeOperatorName() {
		return batchChangeOperatorName;
	}

	public void setBatchChangeOperatorName(String batchChangeOperatorName) {
		this.batchChangeOperatorName = batchChangeOperatorName;
	}
}