package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**随访、健康指导、健康教育、求助、未交费统计表
 * Created by zzl on 2019/1/17.
 */
@Entity
@Table(name = "APP_MANAGE_OTHER_COUNT")
public class AppManageOtherCount extends BasePO {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    private String id;
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
    @Column(name = "MANAGE_SF_COUNT",length = 20)
    private String manageSfCount;//随访统计数
    @Column(name = "MANAGE_GUIDE_COUNT",length = 20)
    private String manageGuideCount;//健康指导统计数
    @Column(name = "MANAGE_HD_COUNT",length = 20)
    private String manageHdCount;//健康教育统计数
    @Column(name = "MANAGE_HELP_COUNT",length = 20)
    private String manageHelpCount;//求助统计数
    @Column(name = "MANAGE_NOT_PAY_COUNT",length = 20)
    private String manageNotPayCount;//未交费统计数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getManageSfCount() {
        return manageSfCount;
    }

    public void setManageSfCount(String manageSfCount) {
        this.manageSfCount = manageSfCount;
    }

    public String getManageGuideCount() {
        return manageGuideCount;
    }

    public void setManageGuideCount(String manageGuideCount) {
        this.manageGuideCount = manageGuideCount;
    }

    public String getManageHdCount() {
        return manageHdCount;
    }

    public void setManageHdCount(String manageHdCount) {
        this.manageHdCount = manageHdCount;
    }

    public String getManageHelpCount() {
        return manageHelpCount;
    }

    public void setManageHelpCount(String manageHelpCount) {
        this.manageHelpCount = manageHelpCount;
    }

    public String getManageNotPayCount() {
        return manageNotPayCount;
    }

    public void setManageNotPayCount(String manageNotPayCount) {
        this.manageNotPayCount = manageNotPayCount;
    }
}
