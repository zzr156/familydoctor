package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/** 中医体质辨识结论表
 * Created by zzl on 2017/8/4.
 */
@Entity
@Table(name = "APP_TCM_RESULT")
public class AppTcmResult extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "TCMR_ID",length =36 )
    private String tcmrId;//外键
    @Column(name = "TCMR_USER_ID",length = 36)
    private String tcmrUserId;//用户id
    @Column(name = "TCMR_USER_NAME",length = 50)
    private String tcmrUserName;//用户姓名
    @Column(name = "TCMR_DR_ID",length = 36 )
    private String tcmrDrId;//医生id
    @Column(name = "TCMR_DR_NAME",length =50 )
    private String tcmrDrName;//医生姓名
    @Column(name = "TCMR_RESULT_TYPE",length = 10)
    private String tcmrResultType;//体质类型
    @Column(name = "TCMR_RESULT_VALUE",length = 10)
    private String tcmrResultValue;//体质辨识
    @Column(name = "TCMR_SCORE",length = 50)
    private String tcmrScore;//得分
    @Column(name = "TCMR_MODERN_CULTIVATE",length = 50)
    private String tcmrModernCultivate;//情志调摄指导
    @Column(name = "TCMR_FOOD_NURSING",length = 50)
    private String tcmrFoodNursing;// 饮食调养
    @Column(name = "TCMR_DAILY_LIFE_CULTIVATE",length = 50)
    private String tcmrDailyLifeCultivate;//起居调摄
    @Column(name = "TCMR_SPORTS_HEALTH_CARE",length = 50)
    private String tcmrSportsHealthCare;//运动保健
    @Column(name = "TCMR_MERIDIAN_HEALTH",length = 50)
    private String tcmrMeridianHealth;//穴位保健
    @Column(name = "TCMR_OTHER")
    private String tcmrOther;//其他
    @Column(name = "TCMR_CREATE_TIME")
    private Calendar tcmrCreateTime;//创建时间
    @Column(name = "TCMR_FS_STATE",length = 10)
    private String tcmrFsState="0";//是否发送过指导 0没有发送 1发送

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTcmrId() {
        return tcmrId;
    }

    public void setTcmrId(String tcmrId) {
        this.tcmrId = tcmrId;
    }

    public String getTcmrUserId() {
        return tcmrUserId;
    }

    public void setTcmrUserId(String tcmrUserId) {
        this.tcmrUserId = tcmrUserId;
    }

    public String getTcmrUserName() {
        return tcmrUserName;
    }

    public void setTcmrUserName(String tcmrUserName) {
        this.tcmrUserName = tcmrUserName;
    }

    public String getTcmrDrId() {
        return tcmrDrId;
    }

    public void setTcmrDrId(String tcmrDrId) {
        this.tcmrDrId = tcmrDrId;
    }

    public String getTcmrDrName() {
        return tcmrDrName;
    }

    public void setTcmrDrName(String tcmrDrName) {
        this.tcmrDrName = tcmrDrName;
    }

    public String getTcmrResultType() {
        return tcmrResultType;
    }

    public void setTcmrResultType(String tcmrResultType) {
        this.tcmrResultType = tcmrResultType;
    }

    public String getTcmrResultValue() {
        return tcmrResultValue;
    }

    public void setTcmrResultValue(String tcmrResultValue) {
        this.tcmrResultValue = tcmrResultValue;
    }

    public String getTcmrScore() {
        return tcmrScore;
    }

    public void setTcmrScore(String tcmrScore) {
        this.tcmrScore = tcmrScore;
    }

    public String getTcmrModernCultivate() {
        return tcmrModernCultivate;
    }

    public void setTcmrModernCultivate(String tcmrModernCultivate) {
        this.tcmrModernCultivate = tcmrModernCultivate;
    }

    public String getTcmrFoodNursing() {
        return tcmrFoodNursing;
    }

    public void setTcmrFoodNursing(String tcmrFoodNursing) {
        this.tcmrFoodNursing = tcmrFoodNursing;
    }

    public String getTcmrDailyLifeCultivate() {
        return tcmrDailyLifeCultivate;
    }

    public void setTcmrDailyLifeCultivate(String tcmrDailyLifeCultivate) {
        this.tcmrDailyLifeCultivate = tcmrDailyLifeCultivate;
    }

    public String getTcmrSportsHealthCare() {
        return tcmrSportsHealthCare;
    }

    public void setTcmrSportsHealthCare(String tcmrSportsHealthCare) {
        this.tcmrSportsHealthCare = tcmrSportsHealthCare;
    }

    public String getTcmrMeridianHealth() {
        return tcmrMeridianHealth;
    }

    public void setTcmrMeridianHealth(String tcmrMeridianHealth) {
        this.tcmrMeridianHealth = tcmrMeridianHealth;
    }

    public String getTcmrOther() {
        return tcmrOther;
    }

    public void setTcmrOther(String tcmrOther) {
        this.tcmrOther = tcmrOther;
    }

    public Calendar getTcmrCreateTime() {
        return tcmrCreateTime;
    }

    public void setTcmrCreateTime(Calendar tcmrCreateTime) {
        this.tcmrCreateTime = tcmrCreateTime;
    }

    public String getTcmrFsState() {
        return tcmrFsState;
    }

    public void setTcmrFsState(String tcmrFsState) {
        this.tcmrFsState = tcmrFsState;
    }
}
