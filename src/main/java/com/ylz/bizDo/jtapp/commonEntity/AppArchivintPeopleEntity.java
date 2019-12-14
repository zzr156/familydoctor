package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppArchivingCardPeople;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

/**
 * Created by zzl on 2018/8/3.
 */
public class AppArchivintPeopleEntity {
    private String name;//姓名
    private String idno;//身份证
    private String teamName;//团队名称
    private String drName;//医生姓名
    private String keyCrowd;//重点人群
    private String keyValue;//重点人群值
    private String signDate;//签约时间
    private String povertyState;//是否脱贫（0否 1是）
    private String agreement;//协议
    private String other;//其他
    private String teamId;//团队主键
    private String drTel;//医生电话
//    private String sourceType;//来源类型 1民政 2家签
//    private String econValue;//

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getKeyCrowd() {
        return keyCrowd;
    }

    public void setKeyCrowd(String keyCrowd) {
        this.keyCrowd = keyCrowd;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(Timestamp signDate) {
        if(signDate != null){
            this.signDate = ExtendDate.getYMD_h_m_s(signDate);
        }else{
            this.signDate = "";
        }
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

//    public String getSourceType() {
//        return sourceType;
//    }
//
//    public void setSourceType(String sourceType) {
//        if(StringUtils.isBlank(sourceType)){
//            if(StringUtils.isNotBlank(this.getIdno())){
//                SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
//                AppArchivingCardPeople vv = dao.getAppArchivingCardPeopeDao().findPeopleByIdno(this.getIdno());
//                if(vv != null){
//                    sourceType = vv.getSourceType();
//                }else{
//                    sourceType = "2";
//                }
//            }else{
//                sourceType = "2";
//            }
//        }
//        this.sourceType = sourceType;
//    }

    public String getPovertyState() {
        return povertyState;
    }

    public void setPovertyState(String povertyState) {
        this.povertyState = povertyState;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDrTel() {
        return drTel;
    }

    public void setDrTel(String drTel) {
        this.drTel = drTel;
    }
}
