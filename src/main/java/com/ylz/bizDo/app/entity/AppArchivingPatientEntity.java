package com.ylz.bizDo.app.entity;

import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

/**
 * Created by zzl on 2018/7/23.
 */
public class AppArchivingPatientEntity {
    private String id;//主键
    private String rhfId;//居民档案号
    private String addrCountyCode;//县区划
    private String addrCountyName;//县名称
    private String addrRuralCode;//街道(乡镇)区划
    private String addrRuralName;//街道(乡镇)名称
    private String addrVillageCode;//社区(村)区划
    private String addrVillageName;//社区(村)名称
    private String patientName;//姓名
    private String patientIdno;//身份证
    private String signState;//签约状态(0未签约 1已签约)
    private String isNotPoverty;//是否脱贫（0否 1是）
    private String notSignReason;//未签约原因
    private String patientTel;//联系电话
    private String lowInsured;//低保户
    private String poorHouseholds;//贫困户
    private String teamId;//团队id
    private String drId;//医生id
    private String delReason;//删除原因
    private String delState;//删除状态
    private String otherReason;//未签其他原因
    private String revokeReason;//注销原因(死亡原因)
    private String revokeDate;//注销时间(失访时间/死亡时间)
    private String sex;//性别
    private String remarks;//备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRhfId() {
        return rhfId;
    }

    public void setRhfId(String rhfId) {
        if(StringUtils.isBlank(rhfId)){
            rhfId = "未建档";
        }
        this.rhfId = rhfId;
    }

    public String getAddrCountyCode() {
        return addrCountyCode;
    }

    public void setAddrCountyCode(String addrCountyCode) {
        this.addrCountyCode = addrCountyCode;
    }

    public String getAddrCountyName() {
        return addrCountyName;
    }

    public void setAddrCountyName(String addrCountyName) {
        this.addrCountyName = addrCountyName;
    }

    public String getAddrRuralCode() {
        return addrRuralCode;
    }

    public void setAddrRuralCode(String addrRuralCode) {
        this.addrRuralCode = addrRuralCode;
    }

    public String getAddrRuralName() {
        return addrRuralName;
    }

    public void setAddrRuralName(String addrRuralName) {
        this.addrRuralName = addrRuralName;
    }

    public String getAddrVillageCode() {
        return addrVillageCode;
    }

    public void setAddrVillageCode(String addrVillageCode) {
        this.addrVillageCode = addrVillageCode;
    }

    public String getAddrVillageName() {
        return addrVillageName;
    }

    public void setAddrVillageName(String addrVillageName) {
        this.addrVillageName = addrVillageName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        if(StringUtils.isNotBlank(signState)){
            signState = "已签约";
        }else{
            signState = "未签约";
        }
        this.signState = signState;
    }

    public String getIsNotPoverty() {
        return isNotPoverty;
    }

    public void setIsNotPoverty(String isNotPoverty) {
        this.isNotPoverty = isNotPoverty;
    }

    public String getNotSignReason() {
        return notSignReason;
    }

    public void setNotSignReason(String notSignReason) {
        this.notSignReason = notSignReason;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getLowInsured() {
        return lowInsured;
    }

    public void setLowInsured(String lowInsured) {
        this.lowInsured = lowInsured;
    }

    public String getPoorHouseholds() {
        return poorHouseholds;
    }

    public void setPoorHouseholds(String poorHouseholds) {
        this.poorHouseholds = poorHouseholds;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDelReason() {
        return delReason;
    }

    public void setDelReason(String delReason) {
        this.delReason = delReason;
    }

    public String getDelState() {
        return delState;
    }

    public void setDelState(String delState) {
        this.delState = delState;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    public String getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(String revokeReason) {
        this.revokeReason = revokeReason;
    }

    public String getRevokeDate() {
        return revokeDate;
    }

    public void setRevokeDate(Timestamp revokeDate) {
        if(revokeDate != null){
            this.revokeDate = ExtendDate.getYMD(revokeDate);
        }else{
            this.revokeDate = "";
        }
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
