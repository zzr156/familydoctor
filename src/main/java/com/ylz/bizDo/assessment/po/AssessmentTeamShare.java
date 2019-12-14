package com.ylz.bizDo.assessment.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 团队共享表
 */
@Entity
@Table(name = "assessment_team_share")
public class AssessmentTeamShare extends BasePO {

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;// 主键
    @Column(name = "TEAM_ID")
    private String teamId;// 团队ID
    @Column(name = "YEAR")
    private Integer year;// 活动年份
    @Column(name = "MONTH")
    private Integer month;// 活动月份
    @Column(name = "OPTION_WEB")
    private String optionWeb;// 图片路径WEB
    @Column(name = "OPTION_APP")
    private String optionApp;// 图片路径APP
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;// 修改人ID
    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;// 修改人名称
    @Column(name = "SIGN_AREA_CODE")
    private String signAreaCode;// 区域编码


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getOptionWeb() {
        return optionWeb;
    }

    public void setOptionWeb(String optionWeb) {
        this.optionWeb = optionWeb;
    }

    public String getOptionApp() {
        return optionApp;
    }

    public void setOptionApp(String optionApp) {
        this.optionApp = optionApp;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getSignAreaCode() {
        return signAreaCode;
    }

    public void setSignAreaCode(String signAreaCode) {
        this.signAreaCode = signAreaCode;
    }
}
