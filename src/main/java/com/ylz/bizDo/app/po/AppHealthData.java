package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 健康档案表
 */
@Entity
@Table(name = "APP_HEALTH_DATA")
public class AppHealthData extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "HEALTH_CARD", length = 30)
	private String healthCard;//社保卡
	@Column(name = "HEALTH_IDNO", length = 30)
	private String healthIdno;//身份证
	@Column(name = "HEALTH_TYPE", length = 10)
	private String healthType;//类型
	@Column(name = "PATIENT_ID", length = 50)
	private String patientId;//患者主键
	@Column(name = "ORGANIZATION_CODE", length = 50)
	private String organizationCode;//机构编码
	@Column(name = "GHH000", length = 50)
	private String ghh000;//挂号编码
	@Column(name = "AREA_CODE", length = 50)
	private String areaCode;//行政区划
	@Column(name = "HEALTH_DATA", length = 65535)
	private String healthData;//健康数据
	@Column(name = "HEALTH_DATE", length = 65535)
	private Calendar healthDate;//创建时间

	// Constructors

	/** default constructor */
	public AppHealthData() {
	}

	/** minimal constructor */
	public AppHealthData(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppHealthData(String id, String healthCard, String healthIdno,
			String healthType, String patientId,
			String organizationCode, String ghh000, String areaCode,
			String healthData,Calendar healthDate) {
		this.id = id;
		this.healthCard = healthCard;
		this.healthIdno = healthIdno;
		this.healthType = healthType;
		this.patientId = patientId;
		this.organizationCode = organizationCode;
		this.ghh000 = ghh000;
		this.areaCode = areaCode;
		this.healthData = healthData;
		this.healthDate = healthDate;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHealthCard() {
		return this.healthCard;
	}

	public void setHealthCard(String healthCard) {
		this.healthCard = healthCard;
	}

	public String getHealthIdno() {
		return this.healthIdno;
	}

	public void setHealthIdno(String healthIdno) {
		this.healthIdno = healthIdno;
	}

	public String getHealthType() {
		return this.healthType;
	}

	public void setHealthType(String healthType) {
		this.healthType = healthType;
	}

	public String getPatientId() {
		return this.patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getOrganizationCode() {
		return this.organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getGhh000() {
		return this.ghh000;
	}

	public void setGhh000(String ghh000) {
		this.ghh000 = ghh000;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getHealthData() {
		return this.healthData;
	}

	public void setHealthData(String healthData) {
		this.healthData = healthData;
	}

	public Calendar getHealthDate() {
		return healthDate;
	}

	public void setHealthDate(Calendar healthDate) {
		this.healthDate = healthDate;
	}
}