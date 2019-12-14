package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by zzl on 2018/8/22.
 */
public class AppArchivintPeopleTTEntity {
    private String rhfId;//居民档案号
    private String villageName;//行政村名
    private String name;//姓名
    private String idno;//身份证号
    private String tel;//电话
    private String isSign;//是否签约
    private String isNotPoverty;//是否脱贫
    private String notSignReason;//未签约原因
    private String sourceType;//来源类型 1民政 2家签

    public String getRhfId() {
        return rhfId;
    }

    public void setRhfId(String rhfId) {
        this.rhfId = rhfId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIsSign() {
        return isSign;
    }

    public void setIsSign(String isSign) {
        this.isSign = isSign;
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

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}
