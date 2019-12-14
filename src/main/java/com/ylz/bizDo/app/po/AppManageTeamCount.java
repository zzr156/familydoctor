package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 管理端统计
 */
@Entity
@Table(name = "APP_MANAGE_TEAM_COUNT")
public class AppManageTeamCount extends BasePO {

	// Fields
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;
	@Column(name = "MANAGE_SIGN_COUNT", length = 20)
	private String manageSignCount;//签约数
	@Column(name = "MANAGE_KEY_SIGN_COUNT", length = 20)
	private String manageKeySignCount;//重点签约数
	@Column(name = "MANAGE_PLAIN_COUNT", length = 20)
	private String managePlainCount;//普通人群
	@Column(name = "MANAGE_CHILD_COUNT", length = 20)
	private String manageChildCount;//0-6岁儿童
	@Column(name = "MANAGE_MATERNAL_COUNT", length = 20)
	private String manageMaternalCount;//孕产妇
	@Column(name = "MANAGE_OLD_PEOPLE_COUNT", length = 20)
	private String manageOldPeopleCount;//老年人
	@Column(name = "MANAGE_BLOOD_COUNT", length = 20)
	private String manageBloodCount;//高血压
	@Column(name = "MANAGE_DIABETES_COUNT", length = 20)
	private String manageDiabetesCount;//糖尿病
	@Column(name = "MANAGE_PSYCHOSIS_COUNT", length = 20)
	private String managePsychosisCount;//严重精神病患者
	@Column(name = "MANAGE_PHTHISIS_COUNT", length = 20)
	private String managePhthisisCount;//结核病
	@Column(name = "MANAGE_DISABLED_PEOPLE_COUNT", length = 20)
	private String manageDisabledPeopleCount;//残疾人
	@Column(name = "MANAGE_SERVICE_UNKNOWN_COUNT", length = 20)
	private String manageServiceUnknownCount;//服务人群未知
	@Column(name = "MANAGE_HEALTH_COUNT", length = 20)
	private String manageHealthCount;//健康人群
	@Column(name = "MANAGE_BEILL_COUNT", length = 20)
	private String manageBeillCount;//患病人群
	@Column(name = "MANAGE_HIGH_RISK_COUNT", length = 20)
	private String manageHighRiskCount;//高危人群
	@Column(name = "MANAGE_CONVALESCENCE_COUNT", length = 20)
	private String manageConvalescenceCount;//恢复期人群
	@Column(name = "MANAGE_HEALTH_UNKNOWN_COUNT", length = 20)
	private String manageHealthUnknownCount;//健康情况未知
	@Column(name = "MANAGE_0_6_COUNT", length = 20)
	private String manage06Count;//0~6岁
	@Column(name = "MANAGE_7_18_COUNT", length = 20)
	private String manage718Count;//7~18岁
	@Column(name = "MANAGE_19_30_COUNT", length = 20)
	private String manage1930Count;//19~30岁
	@Column(name = "MANAGE_31_50_COUNT", length = 20)
	private String manage3150Count;//31~50岁
	@Column(name = "MANAGE_51_65_COUNT", length = 20)
	private String manage5165Count;//51~65岁
	@Column(name = "MANAGE_GREATER_65_COUNT", length = 20)
	private String manageGreater65Count;//大于65岁
	@Column(name = "MANAGE_MALE_COUNT", length = 20)
	private String manageMaleCount;//男
	@Column(name = "MANAGE_FEMALE_COUNT", length = 20)
	private String manageFemaleCount;//女
	@Column(name = "MANAGE_GENDER_TOTAL_COUNT", length = 20)
	private String manageGenderTotalCount;//男女总数
	@Column(name = "MANAGE_UNPAID_COUNT", length = 20)
	private String manageUnpaidCount;//未缴费
	@Column(name = "MANAGE_ALREADY_PAID_COUNT", length = 20)
	private String manageAlreadyPaidCount;//已缴费
	@Column(name = "MANAGE_YEAR", length = 20)
	private String manageYear;//年
	@Column(name = "MANAGE_MONTH", length = 20)
	private String manageMonth;//月
	@Column(name = "MANAGE_YEAR_MONTH", length = 20)
	private String manageYearMonth;//年月
	@Column(name = "MANAGE_HOSP_ID", length = 36)
	private String manageHospId;//医院主键
	@Column(name = "MANAGE_TEAM_ID", length = 36)
	private String manageTeamId;//团队主键

	// Constructors

	/** default constructor */
	public AppManageTeamCount() {
	}

	/** minimal constructor */
	public AppManageTeamCount(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppManageTeamCount(String id, String manageSignCount,
                              String manageKeySignCount, String managePlainCount,
                              String manageChildCount, String manageMaternalCount,
                              String manageOldPeopleCount, String manageBloodCount,
                              String manageDiabetesCount, String managePsychosisCount,
                              String managePhthisisCount, String manageDisabledPeopleCount,
                              String manageServiceUnknownCount,
                              String manageHealthCount, String manageBeillCount,
                              String manageHighRiskCount, String manageConvalescenceCount,
                              String manageHealthUnknownCount, String manage06Count,
                              String manage718Count, String manage1930Count,
                              String manage3150Count, String manage5165Count,
                              String manageGreater65Count, String manageMaleCount,
                              String manageFemaleCount, String manageGenderTotalCount,
                              String manageAlreadyPaidCount, String manageUnpaidCount,
                              String manageYear, String manageMonth,
                              String manageHospId, String manageTeamId) {
		this.id = id;
		this.manageSignCount = manageSignCount;
		this.manageKeySignCount = manageKeySignCount;
		this.managePlainCount = managePlainCount;
		this.manageChildCount = manageChildCount;
		this.manageMaternalCount = manageMaternalCount;
		this.manageOldPeopleCount = manageOldPeopleCount;
		this.manageBloodCount = manageBloodCount;
		this.manageDiabetesCount = manageDiabetesCount;
		this.managePsychosisCount = managePsychosisCount;
		this.managePhthisisCount = managePhthisisCount;
		this.manageDisabledPeopleCount = manageDisabledPeopleCount;
		this.manageServiceUnknownCount = manageServiceUnknownCount;
		this.manageHealthCount = manageHealthCount;
		this.manageBeillCount = manageBeillCount;
		this.manageHighRiskCount = manageHighRiskCount;
		this.manageConvalescenceCount = manageConvalescenceCount;
		this.manageHealthUnknownCount = manageHealthUnknownCount;
		this.manage06Count = manage06Count;
		this.manage718Count = manage718Count;
		this.manage1930Count = manage1930Count;
		this.manage3150Count = manage3150Count;
		this.manage5165Count = manage5165Count;
		this.manageGreater65Count = manageGreater65Count;
		this.manageMaleCount = manageMaleCount;
		this.manageFemaleCount = manageFemaleCount;
		this.manageGenderTotalCount = manageGenderTotalCount;
		this.manageUnpaidCount = manageUnpaidCount;
		this.manageAlreadyPaidCount = manageAlreadyPaidCount;
		this.manageYear = manageYear;
		this.manageMonth = manageMonth;
		this.manageYearMonth = manageYearMonth;
		this.manageHospId = manageHospId;
		this.manageTeamId = manageTeamId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getManageSignCount() {
		return this.manageSignCount;
	}

	public void setManageSignCount(String manageSignCount) {
		this.manageSignCount = manageSignCount;
	}

	public String getManageKeySignCount() {
		return this.manageKeySignCount;
	}

	public void setManageKeySignCount(String manageKeySignCount) {
		this.manageKeySignCount = manageKeySignCount;
	}

	public String getManagePlainCount() {
		return this.managePlainCount;
	}

	public void setManagePlainCount(String managePlainCount) {
		this.managePlainCount = managePlainCount;
	}

	public String getManageChildCount() {
		return this.manageChildCount;
	}

	public void setManageChildCount(String manageChildCount) {
		this.manageChildCount = manageChildCount;
	}

	public String getManageMaternalCount() {
		return this.manageMaternalCount;
	}

	public void setManageMaternalCount(String manageMaternalCount) {
		this.manageMaternalCount = manageMaternalCount;
	}

	public String getManageOldPeopleCount() {
		return this.manageOldPeopleCount;
	}

	public void setManageOldPeopleCount(String manageOldPeopleCount) {
		this.manageOldPeopleCount = manageOldPeopleCount;
	}

	public String getManageBloodCount() {
		return this.manageBloodCount;
	}

	public void setManageBloodCount(String manageBloodCount) {
		this.manageBloodCount = manageBloodCount;
	}

	public String getManageDiabetesCount() {
		return this.manageDiabetesCount;
	}

	public void setManageDiabetesCount(String manageDiabetesCount) {
		this.manageDiabetesCount = manageDiabetesCount;
	}

	public String getManagePsychosisCount() {
		return this.managePsychosisCount;
	}

	public void setManagePsychosisCount(String managePsychosisCount) {
		this.managePsychosisCount = managePsychosisCount;
	}

	public String getManagePhthisisCount() {
		return this.managePhthisisCount;
	}

	public void setManagePhthisisCount(String managePhthisisCount) {
		this.managePhthisisCount = managePhthisisCount;
	}

	public String getManageServiceUnknownCount() {
		return this.manageServiceUnknownCount;
	}

	public void setManageServiceUnknownCount(String manageServiceUnknownCount) {
		this.manageServiceUnknownCount = manageServiceUnknownCount;
	}

	public String getManageHealthCount() {
		return this.manageHealthCount;
	}

	public void setManageHealthCount(String manageHealthCount) {
		this.manageHealthCount = manageHealthCount;
	}

	public String getManageBeillCount() {
		return this.manageBeillCount;
	}

	public void setManageBeillCount(String manageBeillCount) {
		this.manageBeillCount = manageBeillCount;
	}

	public String getManageHighRiskCount() {
		return this.manageHighRiskCount;
	}

	public void setManageHighRiskCount(String manageHighRiskCount) {
		this.manageHighRiskCount = manageHighRiskCount;
	}

	public String getManageConvalescenceCount() {
		return this.manageConvalescenceCount;
	}

	public void setManageConvalescenceCount(String manageConvalescenceCount) {
		this.manageConvalescenceCount = manageConvalescenceCount;
	}

	public String getManageHealthUnknownCount() {
		return this.manageHealthUnknownCount;
	}

	public void setManageHealthUnknownCount(String manageHealthUnknownCount) {
		this.manageHealthUnknownCount = manageHealthUnknownCount;
	}

	public String getManage06Count() {
		return this.manage06Count;
	}

	public void setManage06Count(String manage06Count) {
		this.manage06Count = manage06Count;
	}

	public String getManage718Count() {
		return manage718Count;
	}

	public void setManage718Count(String manage718Count) {
		this.manage718Count = manage718Count;
	}

	public String getManage1930Count() {
		return this.manage1930Count;
	}

	public void setManage1930Count(String manage1930Count) {
		this.manage1930Count = manage1930Count;
	}

	public String getManage3150Count() {
		return this.manage3150Count;
	}

	public void setManage3150Count(String manage3150Count) {
		this.manage3150Count = manage3150Count;
	}

	public String getManage5165Count() {
		return this.manage5165Count;
	}

	public void setManage5165Count(String manage5165Count) {
		this.manage5165Count = manage5165Count;
	}

	public String getManageGreater65Count() {
		return this.manageGreater65Count;
	}

	public void setManageGreater65Count(String manageGreater65Count) {
		this.manageGreater65Count = manageGreater65Count;
	}

	public String getManageMaleCount() {
		return this.manageMaleCount;
	}

	public void setManageMaleCount(String manageMaleCount) {
		this.manageMaleCount = manageMaleCount;
	}

	public String getManageFemaleCount() {
		return this.manageFemaleCount;
	}

	public void setManageFemaleCount(String manageFemaleCount) {
		this.manageFemaleCount = manageFemaleCount;
	}

	public String getManageGenderTotalCount() {
		return manageGenderTotalCount;
	}

	public void setManageGenderTotalCount(String manageGenderTotalCount) {
		this.manageGenderTotalCount = manageGenderTotalCount;
	}

	public String getManageYear() {
		return this.manageYear;
	}

	public void setManageYear(String manageYear) {
		this.manageYear = manageYear;
	}

	public String getManageMonth() {
		return this.manageMonth;
	}

	public void setManageMonth(String manageMonth) {
		this.manageMonth = manageMonth;
	}

	public String getManageHospId() {
		return this.manageHospId;
	}

	public void setManageHospId(String manageHospId) {
		this.manageHospId = manageHospId;
	}

	public String getManageTeamId() {
		return manageTeamId;
	}

	public void setManageTeamId(String manageTeamId) {
		this.manageTeamId = manageTeamId;
	}

	public String getManageUnpaidCount() {
		return manageUnpaidCount;
	}

	public void setManageUnpaidCount(String manageUnpaidCount) {
		this.manageUnpaidCount = manageUnpaidCount;
	}

	public String getManageAlreadyPaidCount() {
		return manageAlreadyPaidCount;
	}

	public void setManageAlreadyPaidCount(String manageAlreadyPaidCount) {
		this.manageAlreadyPaidCount = manageAlreadyPaidCount;
	}

	public String getManageDisabledPeopleCount() {
		return manageDisabledPeopleCount;
	}

	public void setManageDisabledPeopleCount(String manageDisabledPeopleCount) {
		this.manageDisabledPeopleCount = manageDisabledPeopleCount;
	}

	public String getManageYearMonth() {
		return manageYearMonth;
	}

	public void setManageYearMonth(String manageYearMonth) {
		this.manageYearMonth = manageYearMonth;
	}
}