package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 履约统计
 */
@Entity
@Table(name = "APP_EXERCISE_COUNT")
public class AppExerciseCount extends BasePO {

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
	@Column(name = "MANAGE_LOW_FAMILY_COUNT", length = 20)
	private String manageLowFamilyCount;//低保户
	@Column(name = "MANAGE_DESTITUTE_FAMILY_COUNT", length = 20)
	private String manageDestituteFamilyCount;//特困户（五保户）
	@Column(name = "MANAGE_SPECIAL_PLAN_FAMILY_COUNT", length = 20)
	private String manageSpecialPlanFamilyCount;//计生特殊家庭
	@Column(name = "MANAGE_GENERAL_POPULATION_COUNT", length = 20)
	private String manageGeneralPopulationCount;//一般人口
	@Column(name = "MANAGE_POOR_FAMILY_COUNT", length = 20)
	private String managePoorFamilyCount;//建档立卡贫困人口
	@Column(name = "MANAGE_YEAR", length = 20)
	private String manageYear;//年
	@Column(name = "MANAGE_DR_ID", length = 36)
	private String manageDrId;//医生主键
	@Column(name = "MANAGE_TEAM_ID", length = 36)
	private String manageTeamId;//团队主键
	@Column(name = "MANAGE_HOSP_ID", length = 36)
	private String manageHospId;//医院主键
	@Column(name = "MANAGE_AREA_CODE", length = 36)
	private String manageAreaCode;//行政区划



	// Constructors

	/** default constructor */
	public AppExerciseCount() {
	}

	/** minimal constructor */
	public AppExerciseCount(String id) {
		this.id = id;
	}

	public AppExerciseCount(String manageSignCount, String manageKeySignCount, String managePlainCount,
                            String manageChildCount, String manageMaternalCount, String manageOldPeopleCount,
                            String manageBloodCount, String manageDiabetesCount, String managePsychosisCount,
                            String managePhthisisCount, String manageDisabledPeopleCount, String manageServiceUnknownCount,
                            String manageLowFamilyCount, String manageDestituteFamilyCount,String manageSpecialPlanFamilyCount,
                            String manageGeneralPopulationCount, String managePoorFamilyCount,
                            String manageYear, String manageDrId, String manageTeamId, String manageHospId, String manageAreaCode) {
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
		this.manageLowFamilyCount = manageLowFamilyCount;
		this.manageDestituteFamilyCount = manageDestituteFamilyCount;
		this.manageSpecialPlanFamilyCount = manageSpecialPlanFamilyCount;
		this.manageGeneralPopulationCount = manageGeneralPopulationCount;
		this.managePoorFamilyCount = managePoorFamilyCount;
		this.manageYear = manageYear;
		this.manageDrId = manageDrId;
		this.manageTeamId = manageTeamId;
		this.manageHospId = manageHospId;
		this.manageAreaCode = manageAreaCode;
	}

	/** full constructor */


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

	public String getManageDisabledPeopleCount() {
		return manageDisabledPeopleCount;
	}

	public void setManageDisabledPeopleCount(String manageDisabledPeopleCount) {
		this.manageDisabledPeopleCount = manageDisabledPeopleCount;
	}

	public String getManageLowFamilyCount() {
		return manageLowFamilyCount;
	}

	public void setManageLowFamilyCount(String manageLowFamilyCount) {
		this.manageLowFamilyCount = manageLowFamilyCount;
	}

	public String getManageDestituteFamilyCount() {
		return manageDestituteFamilyCount;
	}

	public void setManageDestituteFamilyCount(String manageDestituteFamilyCount) {
		this.manageDestituteFamilyCount = manageDestituteFamilyCount;
	}

	public String getManageSpecialPlanFamilyCount() {
		return manageSpecialPlanFamilyCount;
	}

	public void setManageSpecialPlanFamilyCount(String manageSpecialPlanFamilyCount) {
		this.manageSpecialPlanFamilyCount = manageSpecialPlanFamilyCount;
	}

	public String getManageGeneralPopulationCount() {
		return manageGeneralPopulationCount;
	}

	public void setManageGeneralPopulationCount(String manageGeneralPopulationCount) {
		this.manageGeneralPopulationCount = manageGeneralPopulationCount;
	}

	public String getManagePoorFamilyCount() {
		return managePoorFamilyCount;
	}

	public void setManagePoorFamilyCount(String managePoorFamilyCount) {
		this.managePoorFamilyCount = managePoorFamilyCount;
	}

	public String getManageYear() {
		return manageYear;
	}

	public void setManageYear(String manageYear) {
		this.manageYear = manageYear;
	}

	public String getManageDrId() {
		return manageDrId;
	}

	public void setManageDrId(String manageDrId) {
		this.manageDrId = manageDrId;
	}

	public String getManageTeamId() {
		return manageTeamId;
	}

	public void setManageTeamId(String manageTeamId) {
		this.manageTeamId = manageTeamId;
	}

	public String getManageHospId() {
		return manageHospId;
	}

	public void setManageHospId(String manageHospId) {
		this.manageHospId = manageHospId;
	}

	public String getManageAreaCode() {
		return manageAreaCode;
	}

	public void setManageAreaCode(String manageAreaCode) {
		this.manageAreaCode = manageAreaCode;
	}
}