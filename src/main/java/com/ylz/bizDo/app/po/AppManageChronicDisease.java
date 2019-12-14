package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by zzl on 2018/8/21.
 */
@Entity
@Table(name = "APP_MANAGE_CHRONIC_DISEASE")
public class AppManageChronicDisease extends BasePO {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    private String id;
    @Column(name = "MANAGE_SIGN_COUNT", length = 20)
    private String manageSignCount;//签约数
    @Column(name = "MANAGE_NCD_COUNT",length = 20)
    private String manageNcdCount;//慢病人数(含有高血压或糖尿病)
    @Column(name = "MANAGE_BLOOD_COUNT",length = 20)
    private String manageBloodCount;//只有高血压人数
    @Column(name = "MANAGE_DIABETES_COUNT",length = 20)
    private String manageDiabetesCount;//只有糖尿病人数
    @Column(name = "MANAGE_DIS_BLOOD_COUNT",length = 20)
    private String manageDisBloodCount;//既是高血压又是糖尿病人数

    @Column(name = "MANAGE_BLOOD_RED_COUNT",length = 20)
    private String manageBloodRedCount;//高血压红标人数
    @Column(name = "MANAGE_BLOOD_YELLOW_COUNT",length = 20)
    private String manageBloodYellowCount;//高血压黄标人数
    @Column(name = "MANAGE_BLOOD_GREEN_COUNT",length = 20)
    private String manageBloodGreenCount;//高血压绿标人数
    @Column(name = "MANAGE_BLOOD_GRAY_COUNT",length = 20)
    private String manageBloodGrayCount;//高血压灰标人数


    @Column(name = "MANAGE_DIABETES_RED_COUNT",length = 20)
    private String manageDiabetesRedCount;//糖尿病红标人数
    @Column(name = "MANAGE_DIABETES_YELLOW_COUNT",length = 20)
    private String manageDiabetesYellowCount;//糖尿病黄标人数
    @Column(name = "MANAGE_DIABETES_GREEN_COUNT",length = 20)
    private String manageDiabetesGreenCount;//糖尿病绿标人数
    @Column(name = "MANAGE_DIABETES_GRAY_COUNT",length = 20)
    private String manageDiabetesGrayCount;//糖尿病灰标人数


    @Column(name = "MANAGE_LOW_FAMILY_COUNT", length = 20)
    private String manageLowFamilyCount;//低保户(慢病)
    @Column(name = "MANAGE_DESTITUTE_FAMILY_COUNT", length = 20)
    private String manageDestituteFamilyCount;//特困户（五保户）(慢病)
    @Column(name = "MANAGE_SPECIAL_PLAN_FAMILY_COUNT", length = 20)
    private String manageSpecialPlanFamilyCount;//计生特殊家庭(慢病)
    @Column(name = "MANAGE_GENERAL_POPULATION_COUNT", length = 20)
    private String manageGeneralPopulationCount;//一般人口(慢病)
    @Column(name = "MANAGE_POOR_FAMILY_COUNT", length = 20)
    private String managePoorFamilyCount;//建档立卡贫困人口(慢病)

    @Column(name = "MANAGE_YEAR",length = 20)
    private String manageYear;//年
    @Column(name = "MANAGE_MONTH",length = 20)
    private String manageMonth;//月
    @Column(name = "MANAGE_YEAR_MONTH",length = 20)
    private String manageYearMonth;//年月
    @Column(name = "MANAGE_TEAM_ID",length = 36)
    private String manageTeamId;//团队id
    @Column(name = "MANAGE_HOSP_ID",length = 36)
    private String manageHospId;//医院id
    @Column(name = "MANAGE_AREA_CODE",length = 20)
    private String manageAreaCode;//区域编号

    public String getManageSignCount() {
        return manageSignCount;
    }

    public void setManageSignCount(String manageSignCount) {
        this.manageSignCount = manageSignCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManageNcdCount() {
        return manageNcdCount;
    }

    public void setManageNcdCount(String manageNcdCount) {
        this.manageNcdCount = manageNcdCount;
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

    public String getManageBloodRedCount() {
        return manageBloodRedCount;
    }

    public void setManageBloodRedCount(String manageBloodRedCount) {
        this.manageBloodRedCount = manageBloodRedCount;
    }

    public String getManageBloodYellowCount() {
        return manageBloodYellowCount;
    }

    public void setManageBloodYellowCount(String manageBloodYellowCount) {
        this.manageBloodYellowCount = manageBloodYellowCount;
    }

    public String getManageBloodGreenCount() {
        return manageBloodGreenCount;
    }

    public void setManageBloodGreenCount(String manageBloodGreenCount) {
        this.manageBloodGreenCount = manageBloodGreenCount;
    }

    public String getManageBloodGrayCount() {
        return manageBloodGrayCount;
    }

    public void setManageBloodGrayCount(String manageBloodGrayCount) {
        this.manageBloodGrayCount = manageBloodGrayCount;
    }

    public String getManageDiabetesRedCount() {
        return manageDiabetesRedCount;
    }

    public void setManageDiabetesRedCount(String manageDiabetesRedCount) {
        this.manageDiabetesRedCount = manageDiabetesRedCount;
    }

    public String getManageDiabetesYellowCount() {
        return manageDiabetesYellowCount;
    }

    public void setManageDiabetesYellowCount(String manageDiabetesYellowCount) {
        this.manageDiabetesYellowCount = manageDiabetesYellowCount;
    }

    public String getManageDiabetesGreenCount() {
        return manageDiabetesGreenCount;
    }

    public void setManageDiabetesGreenCount(String manageDiabetesGreenCount) {
        this.manageDiabetesGreenCount = manageDiabetesGreenCount;
    }

    public String getManageDiabetesGrayCount() {
        return manageDiabetesGrayCount;
    }

    public void setManageDiabetesGrayCount(String manageDiabetesGrayCount) {
        this.manageDiabetesGrayCount = manageDiabetesGrayCount;
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

    public String getManageDisBloodCount() {
        return manageDisBloodCount;
    }

    public void setManageDisBloodCount(String manageDisBloodCount) {
        this.manageDisBloodCount = manageDisBloodCount;
    }
}
