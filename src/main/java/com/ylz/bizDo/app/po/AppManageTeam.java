package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 签约团队统计表
 * Created by zzl on 2018/9/19.
 */
@Entity
@Table(name = "APP_MANAGE_TEAM")
public class AppManageTeam extends BasePO {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    private String id;
    @Column(name = "MANAGE_HOSP_ID",length = 36)
    private String manageHospId;//医院id
    @Column(name = "MANAGE_HOSP_LEVE",length = 10)
    private String manageHospLeve;// 医院级别（7社区机构 9乡镇机构）
    @Column(name = "MANAGE_TEAM_ID",length = 36)
    private String manageTeamId;//团队id
    @Column(name = "MANAGE_TEAM_CREATE_TIME")
    private Calendar manageTeamCreateTime;//团队创建时间
    @Column(name = "MANAGE_MEMBER_COUNT",length = 20)
    private String manageMemberCount;//成员数量
    @Column(name = "MANAGE_SIGN_MEMBER_COUNT",length = 20)
    private String manageSignMemberCount;//签约医生数量
    @Column(name = "MANAGE_AREA_CODE",length = 12)
    private String manageAreaCode;//区域编码
    @Column(name = "MANAGE_YEAR",length = 50)
    private String manageYear;//年
    @Column(name = "MANAGE_MONTH",length = 50)
    private String manageMonth;//月
    @Column(name = "MANAGE_YEAR_MONTH",length = 50)
    private String manageYearMonth;//年月
    @Column(name = "MANAGE_SIGN_STATE",length = 10)
    private String manageSignState;//是否是签约团队（0否 1是）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManageHospId() {
        return manageHospId;
    }

    public void setManageHospId(String manageHospId) {
        this.manageHospId = manageHospId;
    }

    public String getManageHospLeve() {
        return manageHospLeve;
    }

    public void setManageHospLeve(String manageHospLeve) {
        this.manageHospLeve = manageHospLeve;
    }

    public String getManageTeamId() {
        return manageTeamId;
    }

    public void setManageTeamId(String manageTeamId) {
        this.manageTeamId = manageTeamId;
    }

    public Calendar getManageTeamCreateTime() {
        return manageTeamCreateTime;
    }

    public void setManageTeamCreateTime(Calendar manageTeamCreateTime) {
        this.manageTeamCreateTime = manageTeamCreateTime;
    }

    public String getManageMemberCount() {
        return manageMemberCount;
    }

    public void setManageMemberCount(String manageMemberCount) {
        this.manageMemberCount = manageMemberCount;
    }

    public String getManageSignMemberCount() {
        return manageSignMemberCount;
    }

    public void setManageSignMemberCount(String manageSignMemberCount) {
        this.manageSignMemberCount = manageSignMemberCount;
    }

    public String getManageAreaCode() {
        return manageAreaCode;
    }

    public void setManageAreaCode(String manageAreaCode) {
        this.manageAreaCode = manageAreaCode;
    }

    public String getManageYear() {
        return manageYear;
    }

    public void setManageYear(String manageYear) {
        this.manageYear = manageYear;
    }

    public String getManageYearMonth() {
        return manageYearMonth;
    }

    public void setManageYearMonth(String manageYearMonth) {
        this.manageYearMonth = manageYearMonth;
    }

    public String getManageSignState() {
        return manageSignState;
    }

    public void setManageSignState(String manageSignState) {
        this.manageSignState = manageSignState;
    }

    public String getManageMonth() {
        return manageMonth;
    }

    public void setManageMonth(String manageMonth) {
        this.manageMonth = manageMonth;
    }
}
