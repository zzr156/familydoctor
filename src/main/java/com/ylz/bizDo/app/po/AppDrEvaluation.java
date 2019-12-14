package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 医生评价表
 */
@Entity
@Table(name = "APP_DR_EVALUATION")
public class AppDrEvaluation extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "EVALUATION_DR_NAME", length = 20)
	private String evaluationDrName;//医生名称
	@Column(name = "EVALUATION_DR_ID", length = 36)
	private String evaluationDrId;//医生主键
	@Column(name = "EVALUATION_DR_GENDER", length = 36)
	private String evaluationDrGender;//医生性别
	@Column(name = "EVALUATION_TEAM_ID", length = 36)
	private String evaluationTeamId;//团队主键
	@Column(name = "EVALUATION_HOSP_ID", length = 36)
	private String evaluationHospId;//医院主键
	@Column(name = "EVALUATION_AREA_CODE", length = 15)
	private String evaluationAreaCode;//区域code
	@Column(name = "EVALUATION_COMPETENCE", length = 10)
	private String evaluationProfessionalAbility;//专业能力;
	@Column(name = "EVALUATION_SERVICE_ATTITUDE", length = 10)
	private String evaluationServiceAttitude;//服务态度
	@Column(name = "EVALUATION_RECOVERY_SPEED", length = 10)
	private String evaluationRecoverySpeed;//回复速度
	@Column(name = "EVALUATION_CONTENT", length = 1000)
	private String evaluationContent;//评价内容
	@Column(name = "EVALUATION_DATE", length = 19)
	private Calendar evaluationDate;//评价时间
	@Column(name = "EVALUATION_PATIENT_NAME", length = 10)
	private String evaluationPatientName;//患者名称
	@Column(name = "EVALUATION_PATIENT_GENDER", length = 10)
	private String evaluationPatientGender;//患者性别
	@Column(name = "EVALUATION_PATIENT_ID", length = 36)
	private String evaluationPatientId;//患者主键
	@Column(name = "EVALUATION_AVERAGE", length = 15)
	private String evaluationAverage;//平均分
	@Column(name = "EVALUATION_TYPE", length = 15)
	private String evaluationType;//类型 1好评,2中评,3差评
	@Column(name = "EVALUATION_METHOD_TYPE", length = 15)
	private String evaluationMethodType;//类型 1健康咨询,2服务类型,3建档,4签约,5随访


	// Constructors

	/** default constructor */
	public AppDrEvaluation() {
	}

	/** minimal constructor */
	public AppDrEvaluation(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppDrEvaluation(String id, String evaluationDrName,
			String evaluationDrId,String evaluationDrGender,
			String evaluationTeamId, String evaluationHospId,
			String evaluationAreaCode, String evaluationProfessionalAbility,
			String evaluationServiceAttitude, String evaluationContent,
			Calendar evaluationDate, String evaluationPatientName,
			String evaluationPatientGender, String evaluationPatientId,
			String evaluationAverage,String evaluationRecoverySpeed,String evaluationType) {
		this.id = id;
		this.evaluationDrName = evaluationDrName;
		this.evaluationDrId = evaluationDrId;
		this.evaluationDrGender = evaluationDrGender;
		this.evaluationTeamId = evaluationTeamId;
		this.evaluationHospId = evaluationHospId;
		this.evaluationAreaCode = evaluationAreaCode;
		this.evaluationProfessionalAbility = evaluationProfessionalAbility;
		this.evaluationServiceAttitude = evaluationServiceAttitude;
		this.evaluationContent = evaluationContent;
		this.evaluationDate = evaluationDate;
		this.evaluationPatientName = evaluationPatientName;
		this.evaluationPatientGender = evaluationPatientGender;
		this.evaluationPatientId = evaluationPatientId;
		this.evaluationAverage = evaluationAverage;
		this.evaluationRecoverySpeed = evaluationRecoverySpeed;
		this.evaluationType = evaluationType;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getEvaluationDrName() {
		return this.evaluationDrName;
	}

	public void setEvaluationDrName(String evaluationDrName) {
		this.evaluationDrName = evaluationDrName;
	}

	public String getEvaluationDrId() {
		return this.evaluationDrId;
	}

	public void setEvaluationDrId(String evaluationDrId) {
		this.evaluationDrId = evaluationDrId;
	}


	public String getEvaluationHospId() {
		return this.evaluationHospId;
	}

	public void setEvaluationHospId(String evaluationHospId) {
		this.evaluationHospId = evaluationHospId;
	}

	public String getEvaluationAreaCode() {
		return this.evaluationAreaCode;
	}

	public void setEvaluationAreaCode(String evaluationAreaCode) {
		this.evaluationAreaCode = evaluationAreaCode;
	}

	public String getEvaluationProfessionalAbility() {
		return this.evaluationProfessionalAbility;
	}

	public void setEvaluationProfessionalAbility(
			String evaluationProfessionalAbility) {
		this.evaluationProfessionalAbility = evaluationProfessionalAbility;
	}

	public String getEvaluationServiceAttitude() {
		return this.evaluationServiceAttitude;
	}

	public void setEvaluationServiceAttitude(String evaluationServiceAttitude) {
		this.evaluationServiceAttitude = evaluationServiceAttitude;
	}

	public String getEvaluationContent() {
		return this.evaluationContent;
	}

	public void setEvaluationContent(String evaluationContent) {
		this.evaluationContent = evaluationContent;
	}

	public Calendar getEvaluationDate() {
		return this.evaluationDate;
	}

	public void setEvaluationDate(Calendar evaluationDate) {
		this.evaluationDate = evaluationDate;
	}

	public String getEvaluationPatientName() {
		return this.evaluationPatientName;
	}

	public void setEvaluationPatientName(String evaluationPatientName) {
		this.evaluationPatientName = evaluationPatientName;
	}

	public String getEvaluationPatientGender() {
		return this.evaluationPatientGender;
	}

	public void setEvaluationPatientGender(String evaluationPatientGender) {
		this.evaluationPatientGender = evaluationPatientGender;
	}

	public String getEvaluationPatientId() {
		return this.evaluationPatientId;
	}

	public void setEvaluationPatientId(String evaluationPatientId) {
		this.evaluationPatientId = evaluationPatientId;
	}

	public String getEvaluationAverage() {
		return this.evaluationAverage;
	}

	public void setEvaluationAverage(String evaluationAverage) {
		this.evaluationAverage = evaluationAverage;
	}

	public String getEvaluationDrGender() {
		return evaluationDrGender;
	}

	public void setEvaluationDrGender(String evaluationDrGender) {
		this.evaluationDrGender = evaluationDrGender;
	}

	public String getEvaluationTeamId() {
		return evaluationTeamId;
	}

	public void setEvaluationTeamId(String evaluationTeamId) {
		this.evaluationTeamId = evaluationTeamId;
	}

	public String getEvaluationRecoverySpeed() {
		return evaluationRecoverySpeed;
	}

	public void setEvaluationRecoverySpeed(String evaluationRecoverySpeed) {
		this.evaluationRecoverySpeed = evaluationRecoverySpeed;
	}

	public String getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(String evaluationType) {
		this.evaluationType = evaluationType;
	}

	public String getEvaluationMethodType() {
		return evaluationMethodType;
	}

	public void setEvaluationMethodType(String evaluationMethodType) {
		this.evaluationMethodType = evaluationMethodType;
	}
}