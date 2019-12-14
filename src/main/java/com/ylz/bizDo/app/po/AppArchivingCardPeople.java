package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 建档立卡居民信息
 * Created by zzl on 2018/7/17.
 */
@Entity
@Table(name = "APP_ARCHIVINGCARD_PEOPLE")
public class AppArchivingCardPeople extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
//    @Column(name = "PATIENT_ID",length = 36)
//    private String patientId;//居民主键
    @Column(name = "RHF_ID",length = 20)
    private String rhfId;//居民档案号
    @Column(name = "ADDR_COUNTY_CODE",length = 12)
    private String addrCountyCode;//县区划
    @Column(name = "ADDR_COUNTY_NAME",length = 50)
    private String addrCountyName;//县名称
    @Column(name = "ADDR_RURAL_CODE",length = 12)
    private String addrRuralCode;//街道(乡镇)区划
    @Column(name = "ADDR_RURAL_NAME",length = 50)
    private String addrRuralName;//街道(乡镇)名称
    @Column(name = "ADDR_VILLAGE_CODE",length = 12)
    private String addrVillageCode;//社区(村)区划
    @Column(name = "ADDR_VILLAGE_NAME",length = 100)
    private String addrVillageName;//社区(村)名称
    @Column(name = "ADDR_HOSP_ID",length = 36)
    private String addrHospId;//归管单位主键
    @Column(name = "ARCHIVING_PATIENT_NAME",length = 50)
    private String archivingPatientName;//姓名
    @Column(name = "ARCHIVING_PATIENT_IDNO",length = 18)
    private String archivingPatientIdno;//身份证
    @Column(name = "SIGN_STATE",length = 2)
    private String signState;//签约状态(null未签约 0,2已签约)
    @Column(name = "IS_NOT_POVERTY",length = 2)
    private String isNotPoverty;//是否脱贫（0否 1是）
    @Column(name = "NOT_SIGN_REASON",length = 10)
    private String notSignReason;//未签约原因
    @Column(name = "PATIENT_TEL",length = 11)
    private String patientTel;//联系电话
    @Column(name = "LOW_INSURED",length = 2)
    private String lowInsured;//低保户
    @Column(name = "POOR_HOUSEHOLDS",length = 2)
    private String poorHouseholds;//贫困户
    @Column(name = "TEAM_ID",length = 36)
    private String teamId;//团队id
    @Column(name = "DR_ID",length = 36)
    private String drId;//医生id
    @Column(name = "DEL_STATE",length = 10)
    private String delState="0";//删除状态 默认0  1删除 2不是建档立卡居民 3失访 4死亡
    @Column(name = "DEL_REASON",length = 4000)
    private String delReason;//删除原因
    @Column(name = "REVOKE_REASON",length = 4000)
    private String revokeReason;//注销原因(死亡原因)
    @Column(name = "REVOKE_DATE")
    private Calendar revokeDate;//注销时间(失访时间/死亡时间)
    @Column(name = "SOURCE_TYPE",length = 10)
    private String sourceType;//来源类型(1民政 2家签签约 3医保)

    @Column(name = "SIGN_ID",length = 36)
    private String signId;//签约单id
    @Column(name = "SEX",length = 36)
    private String sex;//性别
    @Column(name = "AGE",length = 36)
    private String age;//年龄
    @Column(name = "BIRTHDAY",length = 36)
    private Calendar birthday;//出生日期
    @Column(name = "OTHER_REASON",length = 255)
    private String otherReason;//其他原因
    @Column(name = "HOSP_ID",length = 36)
    private String hospId;//机构id
    @Column(name = "SIGN_FROM_DATE")
    private Calendar signFromDate;//签约时间
    @Column(name = "PROVINCIAL_INSURANCE")
    private String provincialInsurance;//省定扶贫标准下的低保对象 (1 建档立卡贫困人口,2低保对象)
    @Column(name = "REMARKS",length = 10)
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

    public String getArchivingPatientName() {
        return archivingPatientName;
    }

    public void setArchivingPatientName(String archivingPatientName) {
        this.archivingPatientName = archivingPatientName;
    }

    public String getArchivingPatientIdno() {
        return archivingPatientIdno;
    }

    public void setArchivingPatientIdno(String archivingPatientIdno) {
        this.archivingPatientIdno = archivingPatientIdno;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
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

    public String getDelState() {
        return delState;
    }

    public void setDelState(String delState) {
        this.delState = delState;
    }

    public String getDelReason() {
        return delReason;
    }

    public void setDelReason(String delReason) {
        this.delReason = delReason;
    }

    public String getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(String revokeReason) {
        this.revokeReason = revokeReason;
    }

    public Calendar getRevokeDate() {
        return revokeDate;
    }

    public void setRevokeDate(Calendar revokeDate) {
        this.revokeDate = revokeDate;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public Calendar getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(Calendar signFromDate) {
        this.signFromDate = signFromDate;
    }

    public String getProvincialInsurance() {
        return provincialInsurance;
    }

    public void setProvincialInsurance(String provincialInsurance) {
        this.provincialInsurance = provincialInsurance;
    }

    public String getAddrHospId() {
        return addrHospId;
    }

    public void setAddrHospId(String addrHospId) {
        this.addrHospId = addrHospId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
