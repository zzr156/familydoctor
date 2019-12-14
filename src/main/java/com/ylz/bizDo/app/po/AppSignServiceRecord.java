package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/** 家庭医生签约服务记录表
 * Created by zzl on 2018/10/30.
 */
@Entity
@Table(name="APP_SIGN_SERVICE_RECORD")
public class AppSignServiceRecord extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "SSR_PATIENT_ID",length = 36)
    private String ssrPatientId;//签约居民主键
    @Column(name = "SSR_PATIENT_NAME",length = 50)
    private String ssrPatientName;//签约居民姓名
    @Column(name = "SSR_SERVE_TIME")
    private Calendar ssrServeTime;//服务时间
    @Column(name = "SSR_SERVE_ADDR",length = 10)
    private String ssrServeAddr;//服务地点
    @Column(name = "SSR_HP",length = 10)
    private Integer ssrHP;//血压（高压）
    @Column(name = "SSR_LP",length = 10)
    private Integer ssrLP;//血压（低压）
    @Column(name = "SSR_BLOOD_SUGAR",length = 10)
    private String ssrBloodSugar;//血糖值
    @Column(name = "SSR_CONTENT_VALUE",length = 20)
    private String ssrContentValue;//服务内容
    @Column(name = "SSR_MAIN_SITUATION",length = 255)
    private String ssrMainSituation;//主要情况告知
    @Column(name = "SSR_HEALTH_GUIDANCE",length = 255)
    private String ssrHealthGuidance;//健康指导
    @Column(name = "SSR_PATIENT_AUTOGRAPH",length = 255)
    private String ssrPatientAutograph;//接受服务对象签名(外网地址)
    @Column(name = "SSR_PATIENT_WITHIN",length = 255)
    private String ssrPatientWithin;//接受服务对象签名（内网地址）
    @Column(name = "SSR_DR_AUTOGRAPH",length = 255 )
    private String ssrDrAutograph;//医生签名
    @Column(name = "SSR_SIGN_ID",length = 36)
    private String ssrSignId;//签约单主键
    @Column(name = "SSR_EMERGENCY_CONTACT_NAME ",length = 50)
    private String ssrEmergencyContactName;//紧急联系人
    @Column(name = "SSR_EMERGENCY_CONTACT_TEL",length = 20)
    private String ssrEmergencyContactTel;//紧急联系人电话
    @Column(name = "SSR_DR_ID",length = 36)
    private String ssrDrId;//医生主键
    @Column(name = "SSR_AREA_CODE",length = 12)
    private String ssrAreaCode;//行政区划

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSsrPatientId() {
        return ssrPatientId;
    }

    public void setSsrPatientId(String ssrPatientId) {
        this.ssrPatientId = ssrPatientId;
    }

    public String getSsrPatientName() {
        return ssrPatientName;
    }

    public void setSsrPatientName(String ssrPatientName) {
        this.ssrPatientName = ssrPatientName;
    }

    public Calendar getSsrServeTime() {
        return ssrServeTime;
    }

    public void setSsrServeTime(Calendar ssrServeTime) {
        this.ssrServeTime = ssrServeTime;
    }

    public String getSsrServeAddr() {
        return ssrServeAddr;
    }

    public void setSsrServeAddr(String ssrServeAddr) {
        this.ssrServeAddr = ssrServeAddr;
    }

    public Integer getSsrHP() {
        return ssrHP;
    }

    public void setSsrHP(Integer ssrHP) {
        this.ssrHP = ssrHP;
    }

    public Integer getSsrLP() {
        return ssrLP;
    }

    public void setSsrLP(Integer ssrLP) {
        this.ssrLP = ssrLP;
    }

    public String getSsrBloodSugar() {
        return ssrBloodSugar;
    }

    public void setSsrBloodSugar(String ssrBloodSugar) {
        this.ssrBloodSugar = ssrBloodSugar;
    }

    public String getSsrContentValue() {
        return ssrContentValue;
    }

    public void setSsrContentValue(String ssrContentValue) {
        this.ssrContentValue = ssrContentValue;
    }

    public String getSsrMainSituation() {
        return ssrMainSituation;
    }

    public void setSsrMainSituation(String ssrMainSituation) {
        this.ssrMainSituation = ssrMainSituation;
    }

    public String getSsrHealthGuidance() {
        return ssrHealthGuidance;
    }

    public void setSsrHealthGuidance(String ssrHealthGuidance) {
        this.ssrHealthGuidance = ssrHealthGuidance;
    }

    public String getSsrPatientAutograph() {
        return ssrPatientAutograph;
    }

    public void setSsrPatientAutograph(String ssrPatientAutograph) {
        this.ssrPatientAutograph = ssrPatientAutograph;
    }

    public String getSsrDrAutograph() {
        return ssrDrAutograph;
    }

    public void setSsrDrAutograph(String ssrDrAutograph) {
        this.ssrDrAutograph = ssrDrAutograph;
    }

    public String getSsrSignId() {
        return ssrSignId;
    }

    public void setSsrSignId(String ssrSignId) {
        this.ssrSignId = ssrSignId;
    }

    public String getSsrEmergencyContactName() {
        return ssrEmergencyContactName;
    }

    public void setSsrEmergencyContactName(String ssrEmergencyContactName) {
        this.ssrEmergencyContactName = ssrEmergencyContactName;
    }

    public String getSsrEmergencyContactTel() {
        return ssrEmergencyContactTel;
    }

    public void setSsrEmergencyContactTel(String ssrEmergencyContactTel) {
        this.ssrEmergencyContactTel = ssrEmergencyContactTel;
    }

    public String getSsrDrId() {
        return ssrDrId;
    }

    public void setSsrDrId(String ssrDrId) {
        this.ssrDrId = ssrDrId;
    }

    public String getSsrPatientWithin() {
        return ssrPatientWithin;
    }

    public void setSsrPatientWithin(String ssrPatientWithin) {
        this.ssrPatientWithin = ssrPatientWithin;
    }

    public String getSsrAreaCode() {
        return ssrAreaCode;
    }

    public void setSsrAreaCode(String ssrAreaCode) {
        this.ssrAreaCode = ssrAreaCode;
    }
}
