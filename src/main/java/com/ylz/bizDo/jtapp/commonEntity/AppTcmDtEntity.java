package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by zzl on 2018/8/11.
 */
public class AppTcmDtEntity {
    private String id;//主键
    private String tcmrId;//外键
    private String tcmrUserId;//用户id
    private String tcmrUserName;//用户姓名
    private String tcmrDrId;//医生id
    private String tcmrDrName;//医生姓名
    private String tcmrResultType;//体质类型
    private String tcmrResultValue;//体质辨识
    private String tcmrScore;//得分
    private String tcmrModernCultivate;//情志调摄指导
    private String tcmrFoodNursing;// 饮食调养
    private String tcmrDailyLifeCultivate;//起居调摄
    private String tcmrSportsHealthCare;//运动保健
    private String tcmrMeridianHealth;//穴位保健
    private String tcmrOther;//其他

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
}
