package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 管理端统计
 */
@Entity
@Table(name = "APP_MANAGE_COUNT")
public class AppManageCount extends BasePO {

	// Fields
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;
	@Column(name = "MANAGE_SIGN_COUNT", length = 20)
	private String manageSignCount;//签约数
	@Column(name = "MANAGE_SIGN_WEB_COUNT", length = 20)
	private String manageSignWebCount;//web签约数
	@Column(name = "MANAGE_SIGN_APP_COUNT", length = 20)
	private String manageSignAppCount;//app签约数
	@Column(name = "MANAGE_SIGN_WECHAT_COUNT", length = 20)
	private String manageSignWechatCount;//微信签约数
	@Column(name = "MANAGE_ECONOMIC_SIGN_COUNT", length = 20)
	private String manageEconomicSignCount;//经济重点签约数
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
	private String manageServiceUnknownCount;//服务人群其他
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
	@Column(name = "MANAGE_LOW_FAMILY_COUNT", length = 20)
	private String manageLowFamilyCount;//低保户
	@Column(name = "MANAGE_DESTITUTE_FAMILY_COUNT", length = 20)
	private String manageDestituteFamilyCount;//特困户（五保户）
	@Column(name = "MANAGE_SPECIAL_PLAN_FAMILY_COUNT", length = 20)
	private String manageSpecialPlanFamilyCount;//计生独伤残家庭
	@Column(name = "MANAGE_GENERAL_POPULATION_COUNT", length = 20)
	private String manageGeneralPopulationCount;//一般人口
	@Column(name = "MANAGE_POOR_FAMILY_COUNT", length = 20)
	private String managePoorFamilyCount;//建档立卡贫困人口
	@Column(name = "MANAGE_YEAR", length = 20)
	private String manageYear;//年
	@Column(name = "MANAGE_MONTH", length = 20)
	private String manageMonth;//月
	@Column(name = "MANAGE_YEAR_MONTH", length = 20)
	private String manageYearMonth;//年月
	@Column(name = "MANAGE_TEAM_ID", length = 36)
	private String manageTeamId;//团队主键
	@Column(name = "MANAGE_HOSP_ID", length = 36)
	private String manageHospId;//医院主键
	@Column(name = "MANAGE_AREA_CODE", length = 30)
	private String manageAreaCode;//行政区划
	@Column(name = "MANAGE_RENEW",length = 20)
	private String manageRenew;//续签数
	@Column(name = "MANAGE_TOTAL_RENEW",length = 30)
	private String manageTotalRenew;//总签约数

	@Column(name = "MANAGE_RENEW_PLAIN_COUNT", length = 20)
	private String manageRenewPlainCount;//普通人群续签数
	@Column(name = "MANAGE_RENEW_CHILD_COUNT", length = 20)
	private String manageRenewChildCount;//0-6岁儿童续签数
	@Column(name = "MANAGE_RENEW_MATERNAL_COUNT", length = 20)
	private String manageRenewMaternalCount;//孕产妇续签数
	@Column(name = "MANAGE_RENEW_OLD_PEOPLE_COUNT", length = 20)
	private String manageRenewOldPeopleCount;//老年人续签数
	@Column(name = "MANAGE_RENEW_BLOOD_COUNT", length = 20)
	private String manageRenewBloodCount;//高血压续签数
	@Column(name = "MANAGE_RENEW_DIABETES_COUNT", length = 20)
	private String manageRenewDiabetesCount;//糖尿病续签数
	@Column(name = "MANAGE_RENEW_PSYCHOSIS_COUNT", length = 20)
	private String manageRenewPsychosisCount;//严重精神病患者续签数
	@Column(name = "MANAGE_RENEW_PHTHISIS_COUNT", length = 20)
	private String manageRenewPhthisisCount;//结核病续签数
	@Column(name = "MANAGE_RENEW_DISABLED_PEOPLE_COUNT", length = 20)
	private String manageRenewDisabledPeopleCount;//残疾人续签数
	@Column(name = "MANAGE_RENEW_SERVICE_UNKNOWN_COUNT", length = 20)
	private String manageRenewServiceUnknownCount;//服务人群其他续签数
	@Column(name = "MANAGE_GOTOSIGN_COUNT",length = 20)
	private String manageGoToSignCount;//转签数
	@Column(name = "MANAGE_GOTOSIGN_AREA",length = 20)
	private String manageGoToSignArea;//跨区转签数
	@Column(name = "MANAGE_GOTOSIGN_HOSP",length = 20)
	private String manageGoToSignHosp;//跨社区转签数
	@Column(name = "MANAGE_GOTOSIGN_TEAM",length = 20)
	private String manageGoToSignTeam;//跨团队转签数
	@Column(name = "MANAGE_SIGN_AIO_COUNT",length = 20)
	private String manageSignAioCount;//一体机签约数
    @Column(name = "MANAGE_SIGN_POS_COUNT",length = 20)
    private String manageSignPosCount;//pos机签约数

	@Column(name = "MANAGE_NXG_COUNT",length = 20)
	private String manageNxgCount;//脑血管签约数
	@Column(name = "MANAGE_GXB_COUNT",length = 20)
	private String manageGxbCount;//冠心病签约数
	@Column(name = "MANAGE_AZ_COUNT",length = 20)
	private String manageAzCount;//癌症签约数

	@Column(name = "MANAGE_RENEW_NXG_COUNT",length = 20)
	private String manageRenewNxgCount;//脑血管续签数
	@Column(name = "MANAGE_RENEW_GXB_COUNT",length = 20)
	private String manageRenewGxbCount;//冠心病续签数
	@Column(name = "MANAGE_RENEW_AZ_COUNT",length = 20)
	private String manageRenewAzCount;//癌症续签数

	@Column(name = "MANAGE_JSDZN_COUNT",length = 20)
	private String manageJsdznCount;// 计生独子女户
	@Column(name = "MANAGE_JSSN_COUNT",length = 20)
	private String manageJssnCount;//计生双女户
	@Column(name = "MANAGE_PKH_COUNT",length = 20)
	private String managePkhCount;//因病致贫
	@Column(name = "MANAGE_QTJJ_COUNT",length = 20)
	private String manageQtJjCount;//其他经济类型



	// Constructors

	/** default constructor */
	public AppManageCount() {
	}

	/** minimal constructor */
	public AppManageCount(String id) {
		this.id = id;
	}

	public AppManageCount(String manageSignCount, String manageKeySignCount, String managePlainCount,
						  String manageChildCount, String manageMaternalCount, String manageOldPeopleCount,
						  String manageBloodCount, String manageDiabetesCount, String managePsychosisCount,
						  String managePhthisisCount, String manageDisabledPeopleCount, String manageServiceUnknownCount,
						  String manageHealthCount, String manageBeillCount, String manageHighRiskCount,
						  String manageConvalescenceCount, String manageHealthUnknownCount, String manage06Count,
						  String manage718Count, String manage1930Count, String manage3150Count,
						  String manage5165Count, String manageGreater65Count, String manageMaleCount,
						  String manageFemaleCount, String manageGenderTotalCount, String manageUnpaidCount,
						  String manageAlreadyPaidCount, String manageLowFamilyCount, String manageDestituteFamilyCount,
						  String manageSpecialPlanFamilyCount, String manageGeneralPopulationCount, String managePoorFamilyCount,
						  String manageYear, String manageMonth, String manageYearMonth,
						  String manageTeamId, String manageHospId, String manageAreaCode,
						  String manageRenew, String manageTotalRenew, String manageRenewPlainCount,
						  String manageRenewChildCount, String manageRenewMaternalCount, String manageRenewOldPeopleCount,
						  String manageRenewBloodCount, String manageRenewDiabetesCount, String manageRenewPsychosisCount,
						  String manageRenewPhthisisCount, String manageRenewDisabledPeopleCount, String manageRenewServiceUnknownCount,
						  String manageGoToSignCount,String manageGoToSignArea,String manageGoToSignHosp,String manageGoToSignTeam,String manageSignAioCount,
                          String manageSignPosCount,String manageNxgCount,String manageGxbCount,String manageAzCount,
						  String manageJsdznCount,String manageJssnCount,String managePkhCount,String manageQtJjCount,
						  String manageRenewNxgCount,String manageRenewGxbCount,String manageRenewAzCount) {
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
		this.manageLowFamilyCount = manageLowFamilyCount;
		this.manageDestituteFamilyCount = manageDestituteFamilyCount;
		this.manageSpecialPlanFamilyCount = manageSpecialPlanFamilyCount;
		this.manageGeneralPopulationCount = manageGeneralPopulationCount;
		this.managePoorFamilyCount = managePoorFamilyCount;
		this.manageYear = manageYear;
		this.manageMonth = manageMonth;
		this.manageYearMonth = manageYearMonth;
		this.manageTeamId = manageTeamId;
		this.manageHospId = manageHospId;
		this.manageAreaCode = manageAreaCode;
		this.manageRenew = manageRenew;
		this.manageTotalRenew = manageTotalRenew;
		this.manageRenewPlainCount = manageRenewPlainCount;
		this.manageRenewChildCount = manageRenewChildCount;
		this.manageRenewMaternalCount = manageRenewMaternalCount;
		this.manageRenewOldPeopleCount = manageRenewOldPeopleCount;
		this.manageRenewBloodCount = manageRenewBloodCount;
		this.manageRenewDiabetesCount = manageRenewDiabetesCount;
		this.manageRenewPsychosisCount = manageRenewPsychosisCount;
		this.manageRenewPhthisisCount = manageRenewPhthisisCount;
		this.manageRenewDisabledPeopleCount = manageRenewDisabledPeopleCount;
		this.manageRenewServiceUnknownCount = manageRenewServiceUnknownCount;
		this.manageGoToSignCount = manageGoToSignCount;
		this.manageGoToSignArea = manageGoToSignArea;
		this.manageGoToSignHosp = manageGoToSignHosp;
		this.manageGoToSignTeam = manageGoToSignTeam;
		this.manageSignAioCount = manageSignAioCount;
		this.manageSignPosCount = manageSignPosCount;
		this.manageNxgCount = manageNxgCount;
		this.manageGxbCount = manageGxbCount;
		this.manageAzCount = manageAzCount;
		this.manageJsdznCount = manageJsdznCount;
		this.manageJssnCount = manageJssnCount;
		this.managePkhCount = managePkhCount;
		this.manageQtJjCount = manageQtJjCount;
		this.manageRenewNxgCount = manageRenewNxgCount;
		this.manageRenewGxbCount = manageRenewGxbCount;
		this.manageRenewAzCount = manageRenewAzCount;
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

	public String getManageAreaCode() {
		return this.manageAreaCode;
	}

	public void setManageAreaCode(String manageAreaCode) {
		this.manageAreaCode = manageAreaCode;
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

	public String getManageTeamId() {
		return manageTeamId;
	}

	public void setManageTeamId(String manageTeamId) {
		this.manageTeamId = manageTeamId;
	}

	public String getManageRenew() {
		return manageRenew;
	}

	public void setManageRenew(String manageRenew) {
		this.manageRenew = manageRenew;
	}

	public String getManageTotalRenew() {
		return manageTotalRenew;
	}

	public void setManageTotalRenew(String manageTotalRenew) {
		this.manageTotalRenew = manageTotalRenew;
	}

	public String getManageRenewPlainCount() {
		return manageRenewPlainCount;
	}

	public void setManageRenewPlainCount(String manageRenewPlainCount) {
		this.manageRenewPlainCount = manageRenewPlainCount;
	}

	public String getManageRenewChildCount() {
		return manageRenewChildCount;
	}

	public void setManageRenewChildCount(String manageRenewChildCount) {
		this.manageRenewChildCount = manageRenewChildCount;
	}

	public String getManageRenewMaternalCount() {
		return manageRenewMaternalCount;
	}

	public void setManageRenewMaternalCount(String manageRenewMaternalCount) {
		this.manageRenewMaternalCount = manageRenewMaternalCount;
	}

	public String getManageRenewOldPeopleCount() {
		return manageRenewOldPeopleCount;
	}

	public void setManageRenewOldPeopleCount(String manageRenewOldPeopleCount) {
		this.manageRenewOldPeopleCount = manageRenewOldPeopleCount;
	}

	public String getManageRenewBloodCount() {
		return manageRenewBloodCount;
	}

	public void setManageRenewBloodCount(String manageRenewBloodCount) {
		this.manageRenewBloodCount = manageRenewBloodCount;
	}

	public String getManageRenewDiabetesCount() {
		return manageRenewDiabetesCount;
	}

	public void setManageRenewDiabetesCount(String manageRenewDiabetesCount) {
		this.manageRenewDiabetesCount = manageRenewDiabetesCount;
	}

	public String getManageRenewPsychosisCount() {
		return manageRenewPsychosisCount;
	}

	public void setManageRenewPsychosisCount(String manageRenewPsychosisCount) {
		this.manageRenewPsychosisCount = manageRenewPsychosisCount;
	}

	public String getManageRenewPhthisisCount() {
		return manageRenewPhthisisCount;
	}

	public void setManageRenewPhthisisCount(String manageRenewPhthisisCount) {
		this.manageRenewPhthisisCount = manageRenewPhthisisCount;
	}

	public String getManageRenewDisabledPeopleCount() {
		return manageRenewDisabledPeopleCount;
	}

	public void setManageRenewDisabledPeopleCount(String manageRenewDisabledPeopleCount) {
		this.manageRenewDisabledPeopleCount = manageRenewDisabledPeopleCount;
	}

	public String getManageRenewServiceUnknownCount() {
		return manageRenewServiceUnknownCount;
	}

	public void setManageRenewServiceUnknownCount(String manageRenewServiceUnknownCount) {
		this.manageRenewServiceUnknownCount = manageRenewServiceUnknownCount;
	}

	public String getManageGoToSignCount() {
		return manageGoToSignCount;
	}

	public void setManageGoToSignCount(String manageGoToSignCount) {
		this.manageGoToSignCount = manageGoToSignCount;
	}

	public String getManageGoToSignArea() {
		return manageGoToSignArea;
	}

	public void setManageGoToSignArea(String manageGoToSignArea) {
		this.manageGoToSignArea = manageGoToSignArea;
	}

	public String getManageGoToSignHosp() {
		return manageGoToSignHosp;
	}

	public void setManageGoToSignHosp(String manageGoToSignHosp) {
		this.manageGoToSignHosp = manageGoToSignHosp;
	}

	public String getManageGoToSignTeam() {
		return manageGoToSignTeam;
	}

	public void setManageGoToSignTeam(String manageGoToSignTeam) {
		this.manageGoToSignTeam = manageGoToSignTeam;
	}

	public String getManageSignWebCount() {
		return manageSignWebCount;
	}

	public void setManageSignWebCount(String manageSignWebCount) {
		this.manageSignWebCount = manageSignWebCount;
	}

	public String getManageSignAppCount() {
		return manageSignAppCount;
	}

	public void setManageSignAppCount(String manageSignAppCount) {
		this.manageSignAppCount = manageSignAppCount;
	}

	public String getManageSignWechatCount() {
		return manageSignWechatCount;
	}

	public void setManageSignWechatCount(String manageSignWechatCount) {
		this.manageSignWechatCount = manageSignWechatCount;
	}

	public String getManageSignAioCount() {
		return manageSignAioCount;
	}

	public void setManageSignAioCount(String manageSignAioCount) {
		this.manageSignAioCount = manageSignAioCount;
	}

    public String getManageSignPosCount() {
        return manageSignPosCount;
    }

    public void setManageSignPosCount(String manageSignPosCount) {
        this.manageSignPosCount = manageSignPosCount;
    }

	public String getManageEconomicSignCount() {
		return manageEconomicSignCount;
	}

	public void setManageEconomicSignCount(String manageEconomicSignCount) {
		this.manageEconomicSignCount = manageEconomicSignCount;
	}

	public String getManageNxgCount() {
		return manageNxgCount;
	}

	public void setManageNxgCount(String manageNxgCount) {
		this.manageNxgCount = manageNxgCount;
	}

	public String getManageGxbCount() {
		return manageGxbCount;
	}

	public void setManageGxbCount(String manageGxbCount) {
		this.manageGxbCount = manageGxbCount;
	}

	public String getManageAzCount() {
		return manageAzCount;
	}

	public void setManageAzCount(String manageAzCount) {
		this.manageAzCount = manageAzCount;
	}

	public String getManageJsdznCount() {
		return manageJsdznCount;
	}

	public void setManageJsdznCount(String manageJsdznCount) {
		this.manageJsdznCount = manageJsdznCount;
	}

	public String getManageJssnCount() {
		return manageJssnCount;
	}

	public void setManageJssnCount(String manageJssnCount) {
		this.manageJssnCount = manageJssnCount;
	}

	public String getManagePkhCount() {
		return managePkhCount;
	}

	public void setManagePkhCount(String managePkhCount) {
		this.managePkhCount = managePkhCount;
	}

	public String getManageQtJjCount() {
		return manageQtJjCount;
	}

	public void setManageQtJjCount(String manageQtJjCount) {
		this.manageQtJjCount = manageQtJjCount;
	}

	public String getManageRenewNxgCount() {
		return manageRenewNxgCount;
	}

	public void setManageRenewNxgCount(String manageRenewNxgCount) {
		this.manageRenewNxgCount = manageRenewNxgCount;
	}

	public String getManageRenewGxbCount() {
		return manageRenewGxbCount;
	}

	public void setManageRenewGxbCount(String manageRenewGxbCount) {
		this.manageRenewGxbCount = manageRenewGxbCount;
	}

	public String getManageRenewAzCount() {
		return manageRenewAzCount;
	}

	public void setManageRenewAzCount(String manageRenewAzCount) {
		this.manageRenewAzCount = manageRenewAzCount;
	}

}