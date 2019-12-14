package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 管理端统计(建档立卡)
 */
@Entity
@Table(name = "APP_MANAGE_ARCHIVING_ALL_COUNT")
public class AppManageArchivingAllCount extends BasePO {

	// Fields
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;
	@Column(name = "MANAGE_NOT_SIGN_COUNT", length = 20)
	private String manageNotSignCount;//未签约数
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
	@Column(name = "MANAGE_YEAR", length = 20)
	private String manageYear;//年
	@Column(name = "MANAGE_MONTH", length = 20)
	private String manageMonth;//月
	@Column(name = "MANAGE_YEAR_MONTH", length = 20)
	private String manageYearMonth;//年月
	@Column(name = "MANAGE_AREA_CODE", length = 30)
	private String manageAreaCode;//行政区划
	@Column(name = "ADDR_HOSP_ID",length = 36)
	private String addrHospId;//归管单位


	// Constructors

	/** default constructor */
	public AppManageArchivingAllCount() {
	}

	/** minimal constructor */
	public AppManageArchivingAllCount(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getManageSignCount() {
		return manageSignCount;
	}

	public void setManageSignCount(String manageSignCount) {
		this.manageSignCount = manageSignCount;
	}

	public String getManagePlainCount() {
		return managePlainCount;
	}

	public void setManagePlainCount(String managePlainCount) {
		this.managePlainCount = managePlainCount;
	}

	public String getManageChildCount() {
		return manageChildCount;
	}

	public void setManageChildCount(String manageChildCount) {
		this.manageChildCount = manageChildCount;
	}

	public String getManageMaternalCount() {
		return manageMaternalCount;
	}

	public void setManageMaternalCount(String manageMaternalCount) {
		this.manageMaternalCount = manageMaternalCount;
	}

	public String getManageOldPeopleCount() {
		return manageOldPeopleCount;
	}

	public void setManageOldPeopleCount(String manageOldPeopleCount) {
		this.manageOldPeopleCount = manageOldPeopleCount;
	}

	public String getManageBloodCount() {
		return manageBloodCount;
	}

	public void setManageBloodCount(String manageBloodCount) {
		this.manageBloodCount = manageBloodCount;
	}

	public String getManageDiabetesCount() {
		return manageDiabetesCount;
	}

	public void setManageDiabetesCount(String manageDiabetesCount) {
		this.manageDiabetesCount = manageDiabetesCount;
	}

	public String getManagePsychosisCount() {
		return managePsychosisCount;
	}

	public void setManagePsychosisCount(String managePsychosisCount) {
		this.managePsychosisCount = managePsychosisCount;
	}

	public String getManagePhthisisCount() {
		return managePhthisisCount;
	}

	public void setManagePhthisisCount(String managePhthisisCount) {
		this.managePhthisisCount = managePhthisisCount;
	}

	public String getManageDisabledPeopleCount() {
		return manageDisabledPeopleCount;
	}

	public void setManageDisabledPeopleCount(String manageDisabledPeopleCount) {
		this.manageDisabledPeopleCount = manageDisabledPeopleCount;
	}

	public String getManageServiceUnknownCount() {
		return manageServiceUnknownCount;
	}

	public void setManageServiceUnknownCount(String manageServiceUnknownCount) {
		this.manageServiceUnknownCount = manageServiceUnknownCount;
	}

	public String getManageYear() {
		return manageYear;
	}

	public void setManageYear(String manageYear) {
		this.manageYear = manageYear;
	}

	public String getManageMonth() {
		return manageMonth;
	}

	public void setManageMonth(String manageMonth) {
		this.manageMonth = manageMonth;
	}

	public String getManageYearMonth() {
		return manageYearMonth;
	}

	public void setManageYearMonth(String manageYearMonth) {
		this.manageYearMonth = manageYearMonth;
	}

	public String getManageAreaCode() {
		return manageAreaCode;
	}

	public void setManageAreaCode(String manageAreaCode) {
		this.manageAreaCode = manageAreaCode;
	}

	public String getManageKeySignCount() {
		return manageKeySignCount;
	}

	public void setManageKeySignCount(String manageKeySignCount) {
		this.manageKeySignCount = manageKeySignCount;
	}

	public String getManageNotSignCount() {
		return manageNotSignCount;
	}

	public void setManageNotSignCount(String manageNotSignCount) {
		this.manageNotSignCount = manageNotSignCount;
	}

	public String getAddrHospId() {
		return addrHospId;
	}

	public void setAddrHospId(String addrHospId) {
		this.addrHospId = addrHospId;
	}
}