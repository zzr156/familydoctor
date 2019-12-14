package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 排名统计表
 * Created by zzl on 2019/1/25.
 */
@Entity
@Table(name = "APP_MANAGE_RANK_COUNT")
public class AppManageRankCount extends BasePO{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    private String id;
    @Column(name = "MANAGE_TOTAL_FOLLOW",length = 20)
    private String manageTotalFollow;//总随访量
    @Column(name = "MANAGE_COMPLETE_FOLLOW",length = 20)
    private String manageCompleteFollow;//完成随访量
    @Column(name = "MANAGE_TOTAL_WORK",length = 20)
    private String manageTotalWork;//总工作量
    @Column(name = "MANAGE_COMPLETE_WORK",length = 20)
    private String manageCompleteWork;//完成工作量
    @Column(name = "MANAGE_TOTAL_HEALTH",length = 20)
    private String manageTotalHealth;//总健康干预
    @Column(name = "MANAGE_COMPLETE_HEALTH",length = 20)
    private String manageCompleteHealth;//完成健康干预
    @Column(name = "MANAGE_REFUSE_SIGN",length = 20)
    private String manageRefuseSign;//拒签数
    @Column(name = "MANAGE_GOTO_SIGN",length = 20)
    private String manageGoToSign;//转签
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManageTotalFollow() {
        return manageTotalFollow;
    }

    public void setManageTotalFollow(String manageTotalFollow) {
        this.manageTotalFollow = manageTotalFollow;
    }

    public String getManageCompleteFollow() {
        return manageCompleteFollow;
    }

    public void setManageCompleteFollow(String manageCompleteFollow) {
        this.manageCompleteFollow = manageCompleteFollow;
    }

    public String getManageTotalWork() {
        return manageTotalWork;
    }

    public void setManageTotalWork(String manageTotalWork) {
        this.manageTotalWork = manageTotalWork;
    }

    public String getManageCompleteWork() {
        return manageCompleteWork;
    }

    public void setManageCompleteWork(String manageCompleteWork) {
        this.manageCompleteWork = manageCompleteWork;
    }

    public String getManageTotalHealth() {
        return manageTotalHealth;
    }

    public void setManageTotalHealth(String manageTotalHealth) {
        this.manageTotalHealth = manageTotalHealth;
    }

    public String getManageCompleteHealth() {
        return manageCompleteHealth;
    }

    public void setManageCompleteHealth(String manageCompleteHealth) {
        this.manageCompleteHealth = manageCompleteHealth;
    }

    public String getManageRefuseSign() {
        return manageRefuseSign;
    }

    public void setManageRefuseSign(String manageRefuseSign) {
        this.manageRefuseSign = manageRefuseSign;
    }

    public String getManageGoToSign() {
        return manageGoToSign;
    }

    public void setManageGoToSign(String manageGoToSign) {
        this.manageGoToSign = manageGoToSign;
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
}
