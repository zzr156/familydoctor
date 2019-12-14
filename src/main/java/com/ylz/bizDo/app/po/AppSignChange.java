package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/** 变更记录表
 * Created by zzl on 2017/9/6.
 */
@Entity
@Table(name = "APP_SIGN_CHANGE")
public class AppSignChange extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "CHANGE_NUM",length = 50)
    private String changeNum;//变更编号
    @Column(name = "CHANGE_DR_ID",length = 36)
    private String changeDrId;//变更医生id
    @Column(name = "CHANGE_TEAM_ID",length = 36)
    private String changeTeamId;//变更医生团队id
    @Column(name = "CHANGE_DR",length = 36)
    private String changeDr;//申请医生id
    @Column(name = "CHANGE_TEAM",length = 36)
    private String changeTeam;//申请医生团队id
    @Column(name = "CHANGE_DATE")
    private Calendar changeDate;//申请变更时间
    @Column(name = "CHANGE_USER_ID",length = 36)
    private String changeUserId;//患者id
    @Column(name = "CHANGE_STATE",length = 10)
    private String changeState;//变更状态
    @Column(name = "CHANGE_AGREE_DATE")
    private Calendar changeAgreeDate;//变更同意时间或拒绝时间
    @Column(name = "CHANGE_SIGN_ID",length = 36)
    private String changeSignId;//签约单id
    @Column(name = "CHANGE_REASON")
    private String changeReason;//拒绝理由

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(String changeNum) {
        this.changeNum = changeNum;
    }

    public String getChangeDrId() {
        return changeDrId;
    }

    public void setChangeDrId(String changeDrId) {
        this.changeDrId = changeDrId;
    }

    public String getChangeTeamId() {
        return changeTeamId;
    }

    public void setChangeTeamId(String changeTeamId) {
        this.changeTeamId = changeTeamId;
    }

    public String getChangeDr() {
        return changeDr;
    }

    public void setChangeDr(String changeDr) {
        this.changeDr = changeDr;
    }

    public String getChangeTeam() {
        return changeTeam;
    }

    public void setChangeTeam(String changeTeam) {
        this.changeTeam = changeTeam;
    }

    public Calendar getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Calendar changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangeUserId() {
        return changeUserId;
    }

    public void setChangeUserId(String changeUserId) {
        this.changeUserId = changeUserId;
    }

    public String getChangeState() {
        return changeState;
    }

    public void setChangeState(String changeState) {
        this.changeState = changeState;
    }

    public Calendar getChangeAgreeDate() {
        return changeAgreeDate;
    }

    public void setChangeAgreeDate(Calendar changeAgreeDate) {
        this.changeAgreeDate = changeAgreeDate;
    }

    public String getChangeSignId() {
        return changeSignId;
    }

    public void setChangeSignId(String changeSignId) {
        this.changeSignId = changeSignId;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }
}
