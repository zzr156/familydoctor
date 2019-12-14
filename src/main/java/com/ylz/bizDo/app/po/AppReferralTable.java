package com.ylz.bizDo.app.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 转诊记录表
 * Created by zzl on 2017/12/11.
 */
@Entity
@Table(name = "APP_REFERRAL_TABLE")
public class AppReferralTable extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "REF_PATIENT_ID",length = 36)
    private String refPatientId;//患者id
    @Column(name = "REF_PATIENT_NAME",length = 50)
    private String refPatientName;//患者姓名
    @Column(name = "REF_PATIENT_GENDER",length = 10)
    private String refPatientGender;//患者性别
    @Column(name = "REF_PATIENT_AGE",length = 10)
    private String refPatientAge;//患者年龄
    @Column(name = "REF_PATIENT_IDNO",length = 18)
    private String refPatientIdNo;//患者身份证号
    @Column(name = "REF_PATIENT_CARD",length = 10)
    private String refPatientCard;//患者医保卡号
    @Column(name = "REF_PATIENT_ADDRESS",length = 255)
    private String refPatientAddress;//患者居住地
    @Column(name = "REF_PATIENT_TEL",length = 11)
    private String refPatientTel;//患者联系电话
    @Column(name = "REF_FIRST_IMPRESSION")
    private String refFirstImpression;//初步印象
    @Column(name = "REF_MAIN_HISTORY")
    private String refMainHistory;//主要现病史（转出原因）
    @Column(name = "REF_MAIN_PAST_HISTORY")
    private String refMainPastHistory;//主要既往史
    @Column(name = "REF_TREATMENT_OF")
    private String refTreatmentOf;//治疗经过
    @Column(name = "REF_OUT_ORG_ID",length = 36)
    private String refOutOrgId;//转诊发起机构id
    @Column(name = "REF_OUT_DR_ID",length = 36)
    private String refOutDrId;//转诊发起医生id
    @Column(name = "REF_CONTACT",length = 100)
    private String refContact;//联系方式
    @Column(name = "REF_IN_ORG_ID",length = 36)
    private String refInOrgId;//转诊接收机构id
    @Column(name = "REF_IN_DEPT_ID",length = 36)
    private String refInDeptId;//转诊接收科室id
    @Column(name = "REF_YY_DATE")
    private Date refYyDate;//预约转诊日期
    @Column(name = "REF_APPLY_TIME")
    private Calendar refApplyTime;//申请转诊日期
    @Column(name = "REF_TYPE",length = 10)
    private String refType;//转诊类型（1转出 2转入）
    @Column(name = "REF_ACCEPTS_TIME")
    private Calendar refAcceptsTime;//接诊时间
    @Column(name = "REF_REJECT_TIME")
    private Calendar refRejectTime;//拒诊时间
    @Column(name = "REF_REJECT_REASON",length = 255)
    private String refRejectReason;//拒接诊原因
    @Column(name = "REF_TERMINATION_TIME")
    private Calendar refTerminationTime;//终止日期
    @Column(name = "REF_TERMINATION_REASON",length = 255)
    private String refTerminationReason;//终止原因
    @Column(name = "REF_REHABILITATION_TIME")
    private Calendar refRehabilitationTime;//康复转回日期
    @Column(name = "REF_RESULTS",length = 255)
    private String refResults;//诊断结果
    @Column(name = "REF_MAIN_RESULTS",length = 255)
    private String refMainResults;//主要检查结果
    @Column(name = "REF_NEXT_ADVICE",length = 255)
    private String refNextAdvice;//下一步建议
    @Column(name = "REF_STATE",length = 10)
    private String refState;//转诊状态（0转诊待通过 1转诊已通过待康复回转或终止 2转诊已过期 3转诊已拒绝 4转诊终止 5康复回转）
   /* @Column(name = "REF_FID",length = 36)
    private String refFid;//父id*/
    @Column(name = "REF_TEAM_ID",length = 36)
    private String refTeamId;//团队id
    @Column(name = "REF_AREA_CODE",length = 18)
    private String refAreaCode;//区域编码
    @Column(name = "REF_SIGN_ID",length = 36)
    private String refSignId;//签约单id
    @Column(name = "REF_IN_DR_ID",length = 36)
    private String refInDrId;//转诊接收医生id
    @Column(name = "REF_CODE",length = 40)
    private String refCode;//转诊编号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefPatientId() {
        return refPatientId;
    }

    public void setRefPatientId(String refPatientId) {
        this.refPatientId = refPatientId;
    }

    public String getRefFirstImpression() {
        return refFirstImpression;
    }

    public void setRefFirstImpression(String refFirstImpression) {
        this.refFirstImpression = refFirstImpression;
    }

    public String getRefMainHistory() {
        return refMainHistory;
    }

    public void setRefMainHistory(String refMainHistory) {
        this.refMainHistory = refMainHistory;
    }

    public String getRefMainPastHistory() {
        return refMainPastHistory;
    }

    public void setRefMainPastHistory(String refMainPastHistory) {
        this.refMainPastHistory = refMainPastHistory;
    }

    public String getRefTreatmentOf() {
        return refTreatmentOf;
    }

    public void setRefTreatmentOf(String refTreatmentOf) {
        this.refTreatmentOf = refTreatmentOf;
    }

    public String getRefOutOrgId() {
        return refOutOrgId;
    }

    public void setRefOutOrgId(String refOutOrgId) {
        this.refOutOrgId = refOutOrgId;
    }

    public String getRefOutDrId() {
        return refOutDrId;
    }

    public void setRefOutDrId(String refOutDrId) {
        this.refOutDrId = refOutDrId;
    }

    public String getRefContact() {
        return refContact;
    }

    public void setRefContact(String refContact) {
        this.refContact = refContact;
    }

    public String getRefInOrgId() {
        return refInOrgId;
    }

    public void setRefInOrgId(String refInOrgId) {
        this.refInOrgId = refInOrgId;
    }

    public String getRefInDeptId() {
        return refInDeptId;
    }

    public void setRefInDeptId(String refInDeptId) {
        this.refInDeptId = refInDeptId;
    }

    public Date getRefYyDate() {
        return refYyDate;
    }

    public void setRefYyDate(Date refYyDate) {
        this.refYyDate = refYyDate;
    }

    public Calendar getRefApplyTime() {
        return refApplyTime;
    }

    public void setRefApplyTime(Calendar refApplyTime) {
        this.refApplyTime = refApplyTime;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public Calendar getRefAcceptsTime() {
        return refAcceptsTime;
    }

    public void setRefAcceptsTime(Calendar refAcceptsTime) {
        this.refAcceptsTime = refAcceptsTime;
    }

    public Calendar getRefRejectTime() {
        return refRejectTime;
    }

    public void setRefRejectTime(Calendar refRejectTime) {
        this.refRejectTime = refRejectTime;
    }

    public String getRefRejectReason() {
        return refRejectReason;
    }

    public void setRefRejectReason(String refRejectReason) {
        this.refRejectReason = refRejectReason;
    }

    public Calendar getRefTerminationTime() {
        return refTerminationTime;
    }

    public void setRefTerminationTime(Calendar refTerminationTime) {
        this.refTerminationTime = refTerminationTime;
    }

    public String getRefTerminationReason() {
        return refTerminationReason;
    }

    public void setRefTerminationReason(String refTerminationReason) {
        this.refTerminationReason = refTerminationReason;
    }

    public Calendar getRefRehabilitationTime() {
        return refRehabilitationTime;
    }

    public void setRefRehabilitationTime(Calendar refRehabilitationTime) {
        this.refRehabilitationTime = refRehabilitationTime;
    }

    public String getRefResults() {
        return refResults;
    }

    public void setRefResults(String refResults) {
        this.refResults = refResults;
    }

    public String getRefMainResults() {
        return refMainResults;
    }

    public void setRefMainResults(String refMainResults) {
        this.refMainResults = refMainResults;
    }

    public String getRefNextAdvice() {
        return refNextAdvice;
    }

    public void setRefNextAdvice(String refNextAdvice) {
        this.refNextAdvice = refNextAdvice;
    }

    public String getRefState() {
        return refState;
    }

    public void setRefState(String refState) {
        this.refState = refState;
    }

   /* public String getRefFid() {
        return refFid;
    }

    public void setRefFid(String refFid) {
        this.refFid = refFid;
    }*/

    public String getRefTeamId() {
        return refTeamId;
    }

    public void setRefTeamId(String refTeamId) {
        this.refTeamId = refTeamId;
    }

    public String getRefAreaCode() {
        return refAreaCode;
    }

    public void setRefAreaCode(String refAreaCode) {
        this.refAreaCode = refAreaCode;
    }

    public String getRefSignId() {
        return refSignId;
    }

    public void setRefSignId(String refSignId) {
        this.refSignId = refSignId;
    }

    public String getRefPatientName() {
        return refPatientName;
    }

    public void setRefPatientName(String refPatientName) {
        this.refPatientName = refPatientName;
    }

    public String getRefPatientGender() {
        return refPatientGender;
    }

    public void setRefPatientGender(String refPatientGender) {
        this.refPatientGender = refPatientGender;
    }

    public String getRefPatientAge() {
        return refPatientAge;
    }

    public void setRefPatientAge(String refPatientAge) {
        this.refPatientAge = refPatientAge;
    }

    public String getRefPatientIdNo() {
        return refPatientIdNo;
    }

    public void setRefPatientIdNo(String refPatientIdNo) {
        this.refPatientIdNo = refPatientIdNo;
    }

    public String getRefPatientCard() {
        return refPatientCard;
    }

    public void setRefPatientCard(String refPatientCard) {
        this.refPatientCard = refPatientCard;
    }

    public String getRefPatientAddress() {
        return refPatientAddress;
    }

    public void setRefPatientAddress(String refPatientAddress) {
        this.refPatientAddress = refPatientAddress;
    }

    public String getRefInDrId() {
        return refInDrId;
    }

    public void setRefInDrId(String refInDrId) {
        this.refInDrId = refInDrId;
    }

    public String getRefPatientTel() {
        return refPatientTel;
    }

    public void setRefPatientTel(String refPatientTel) {
        this.refPatientTel = refPatientTel;
    }

    /**
     * 预约转诊日期
     * @return
     */
    public String getStrRefYyDate(){
        if(this.getRefYyDate()!=null){
            return ExtendDate.getYMD(this.getRefYyDate());
        }else{
            return "";
        }
    }

    /**
     * 申请转诊日期
     * @return
     */
    public String getStrRefApplyTime(){
        if(this.getRefApplyTime()!=null){
            return ExtendDate.getYMD_h_m(this.getRefApplyTime());
        }else{
            return "";
        }
    }

    /**
     * 拒诊时间
     * @return
     */
    public String getStrRefRejectTime(){
        if(this.getRefRejectTime()!=null){
            return ExtendDate.getYMD_h_m(this.getRefRejectTime());
        }
        return "";
    }

    /**
     * 终止日期
     * @return
     */
    public String getStrRefTerminationTime(){
        if(this.getRefTerminationTime()!=null){
            return ExtendDate.getYMD_h_m(this.getRefTerminationTime());
        }
        return "";
    }

    /**
     * 康复转回日期
     * @return
     */
    public String getStrRefRehabilitationTime(){
        if(this.getRefRehabilitationTime()!=null){
            return ExtendDate.getYMD_h_m(this.getRefRehabilitationTime());
        }
        return "";
    }

    /**
     * 查询转诊接收医院名称
     * @return
     */
    public String getInOrgName(){
        if(StringUtils.isNotBlank(this.getRefInOrgId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,this.getRefInOrgId());
            if(dept!=null){
                return dept.getHospName();
            }
        }
        return "";
    }

    /**
     * 转诊发起机构名称
     * @return
     */
    public String getOutOrgName(){
        if(StringUtils.isNotBlank(this.getRefOutOrgId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,this.getRefOutOrgId());
            if(dept !=null){
                return dept.getHospName();
            }
        }
        return "";
    }

    /**
     * 获取科室名称
     * @return
     */
    public String getInDeptName(){
        if(StringUtils.isNotBlank(this.getRefInDeptId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppHospitalDepartments dept = (AppHospitalDepartments)dao.getServiceDo().find(AppHospitalDepartments.class,this.getRefInDeptId());
            if(dept != null ){
                return dept.getHdSectionName();
            }
        }
        return "";
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }
}
