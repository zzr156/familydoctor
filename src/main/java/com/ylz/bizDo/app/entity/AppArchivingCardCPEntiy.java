package com.ylz.bizDo.app.entity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2018/11/19.
 */
public class AppArchivingCardCPEntiy {
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
    private String delState;//状态
    private String sourceType;//来源
    private String provincialInsurance;//省定扶贫标准下的低保对象 (1 建档立卡贫困,2低保对象)
    private String signFromDate;//签约时间
    private String signId;//签约单主键
    private String addState;//是否添加建档立卡核查表
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
        if("1".equals(isNotPoverty)){
            isNotPoverty = "是";
        }else{
            isNotPoverty = "否";
        }
        this.isNotPoverty = isNotPoverty;
    }

    public String getNotSignReason() {
        return notSignReason;
    }

    public void setNotSignReason(String notSignReason) throws Exception {
        String noSignReason = "";
        if("未签约".equals(this.getSignState())){
            if(org.apache.commons.lang3.StringUtils.isNotBlank(notSignReason)){
                SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
                if("医保".equals(this.getSourceType())){
                    CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_NOTSIGNREASONYB,notSignReason);
                    if(value!=null) {
                        noSignReason = value.getCodeTitle();
                    }
                }else {
                    CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_NOTSIGNREASON,notSignReason);
                    if(value!=null) {
                        noSignReason = value.getCodeTitle();
                    }
                }
            }
        }
        this.notSignReason = noSignReason;
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
        if("1".equals(lowInsured)){
            lowInsured = "是";
        }else{
            lowInsured = "否";
        }
        this.lowInsured = lowInsured;
    }

    public String getPoorHouseholds() {
        return poorHouseholds;
    }

    public void setPoorHouseholds(String poorHouseholds) {
        if("1".equals(poorHouseholds)){
            poorHouseholds = "是";
        }else{
            poorHouseholds = "否";
        }
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

    public String getDelState() {
        return delState;
    }

    public void setDelState(String delState) {
        if("0".equals(delState) || StringUtils.isBlank(delState)){
            this.delState = "正常";
        }else if("1".equals(delState)){
            this.delState = "删除";
        }else if("3".equals(delState)){
            this.delState = "失访";
        }else if("4".equals(delState)){
            this.delState = "死亡";
        }else{
            this.delState = "";
        }
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) throws Exception {
        String name = "";
        if(StringUtils.isNotBlank(sourceType)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SOURCETYPE,sourceType);
            if(value!=null) {
                name = value.getCodeTitle();
            }
        }
        this.sourceType = name;
    }

    public String getProvincialInsurance() {
        return provincialInsurance;
    }

    public void setProvincialInsurance(String provincialInsurance) throws Exception {
        if(org.apache.commons.lang3.StringUtils.isNotBlank(provincialInsurance)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_PROVINCIALINSURANCE,provincialInsurance);
            if(value!=null) {
                provincialInsurance = value.getCodeTitle();
            }
        }
        this.provincialInsurance = provincialInsurance;
    }

    public String getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(Timestamp signFromDate) {
        if(signFromDate != null){
            this.signFromDate = ExtendDate.getYMD_h_m_s(signFromDate);
        }else{
            this.signFromDate = "";
        }
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getAddState() {
        return addState;
    }

    public void setAddState(String addState) {
        if(StringUtils.isBlank(addState)){
            String state = "0";
            if(StringUtils.isNotBlank(this.getId())){
                Map<String,Object> map = new HashMap<>();
                map.put("archivingId",this.getId());
//                map.put("signId",this.getSignId());
                SysDao dao = (SysDao)SpringHelper.getBean("sysDao");

                String sql = " SELECT count(1) FROM APP_ARCHIVING_CARD_CHECK WHERE 1=1 ";
                sql += " AND ACC_ARCHIVING_ID = :archivingId ";
//                if(StringUtils.isNotBlank(this.getSignId())){
//                    sql += " AND ACC_SIGN_ID =:signId ";
//                }else{
//                    sql += " AND ACC_SIGN_ID IS NULL ";
//                }
                int count = dao.getServiceDo().findCount(sql,map);
                if(count>0){
                    state = "1";
                }
            }
            this.addState = state;
        }else{
            this.addState = addState;
        }

    }
}
