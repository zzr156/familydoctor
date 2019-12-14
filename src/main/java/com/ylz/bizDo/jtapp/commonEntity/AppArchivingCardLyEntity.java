package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by zzl on 2018/11/13.
 */
public class AppArchivingCardLyEntity {
    private String filiingDate;//居民健康档案建档时间
    private String lastUpdateDate;//居民健康档案最后更新时间
    private String healthCheckNum;//签约后健康体检次数
    private String lastHealthCheckDate;//最后一次健康体检时间
    private AppArchivingCardInfo kidInfo;//0-6岁儿童
    private AppArchivingCardInfo maternalInfo;//孕产妇
    private AppArchivingCardHbpInfo hbpInfo;//高血压患者
    private AppArchivingCardDmInfo dmInfo;//糖尿病患者
    private AppArchivingCardInfo mentalInfo;//严重精神障碍患者

    private String totalCost;//总费用
    private String zfFee;//自付金额
    private String ncmsFee;//新农合报销
    private String civilAssistance;//民政补助
    private String sgcpa;//扶贫叠加保障补偿
    private String otherFund;//其他



    public String getFiliingDate() {
        return filiingDate;
    }

    public void setFiliingDate(String filiingDate) {
        this.filiingDate = filiingDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getHealthCheckNum() {
        return healthCheckNum;
    }

    public void setHealthCheckNum(String healthCheckNum) {
        this.healthCheckNum = healthCheckNum;
    }

    public String getLastHealthCheckDate() {
        return lastHealthCheckDate;
    }

    public void setLastHealthCheckDate(String lastHealthCheckDate) {
        this.lastHealthCheckDate = lastHealthCheckDate;
    }

    public AppArchivingCardInfo getKidInfo() {
        return kidInfo;
    }

    public void setKidInfo(AppArchivingCardInfo kidInfo) {
        this.kidInfo = kidInfo;
    }

    public AppArchivingCardInfo getMaternalInfo() {
        return maternalInfo;
    }

    public void setMaternalInfo(AppArchivingCardInfo maternalInfo) {
        this.maternalInfo = maternalInfo;
    }

    public AppArchivingCardHbpInfo getHbpInfo() {
        return hbpInfo;
    }

    public void setHbpInfo(AppArchivingCardHbpInfo hbpInfo) {
        this.hbpInfo = hbpInfo;
    }

    public AppArchivingCardDmInfo getDmInfo() {
        return dmInfo;
    }

    public void setDmInfo(AppArchivingCardDmInfo dmInfo) {
        this.dmInfo = dmInfo;
    }

    public AppArchivingCardInfo getMentalInfo() {
        return mentalInfo;
    }

    public void setMentalInfo(AppArchivingCardInfo mentalInfo) {
        this.mentalInfo = mentalInfo;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getZfFee() {
        return zfFee;
    }

    public void setZfFee(String zfFee) {
        this.zfFee = zfFee;
    }

    public String getNcmsFee() {
        return ncmsFee;
    }

    public void setNcmsFee(String ncmsFee) {
        this.ncmsFee = ncmsFee;
    }

    public String getCivilAssistance() {
        return civilAssistance;
    }

    public void setCivilAssistance(String civilAssistance) {
        this.civilAssistance = civilAssistance;
    }

    public String getSgcpa() {
        return sgcpa;
    }

    public void setSgcpa(String sgcpa) {
        this.sgcpa = sgcpa;
    }

    public String getOtherFund() {
        return otherFund;
    }

    public void setOtherFund(String otherFund) {
        this.otherFund = otherFund;
    }
}
