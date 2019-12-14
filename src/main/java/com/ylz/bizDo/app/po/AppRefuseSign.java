package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 拒绝签约表
 */
@Entity
@Table(name = "APP_REFUSE_SIGN")
public class AppRefuseSign extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "RS_PATIENT_IDNO", length = 36)
    private String rsPatientIdno;//拒签患者身份证
    @Column(name = "RS_PATIENT_NAME", length = 50)
    private String rsPatientName;//拒签患者名字
    @Column(name = "RS_PATIENT_CARD", length = 50)
    private String rsPatientCard;//拒签患者社保卡号
    @Column(name = "RS_REFUSE_TIME")
    private Calendar rsRefuseTime;//拒绝签约时间
    @Column(name = "RS_REFUSE_DR_ID", length = 36)
    private String rsRefuseDrId;//拒签医生id
    @Column(name = "RS_REFUSE_TEAM_ID", length = 36)
    private String rsRefuseTeamId;//拒签团队id
    @Column(name = "RS_REFUSE_HOSP_ID", length = 36)
    private String rsRefuseHospId;//拒签医院id
    @Column(name = "RS_REFUSE_HOSP_AREA_CODE", length = 50)
    private String rsRefuseHospAreaCode;//拒签医院区域code
    @Column(name = "RS_SIGN_TIME")
    private Calendar rsSignTime;//签约时间（如果未签约则为空）
    @Column(name = "RS_SIGN_DR_ID", length = 36)
    private String rsSignDrId;//签约医生id
    @Column(name = "RS_SIGN_TEAM_ID", length = 36)
    private String rsSignTeamId;//签约团队id
    @Column(name = "RS_SIGN_HOSP_ID", length = 36)
    private String rsSignHospId;//签约医院id
    @Column(name = "RS_SIGN_HOSP_AREA_CODE", length = 50)
    private String rsSignHospAreaCode;//签约医院区域code

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getRsPatientIdno() {
        return rsPatientIdno;
    }

    public void setRsPatientIdno(String rsPatientIdno) {
        this.rsPatientIdno = rsPatientIdno;
    }

    public Calendar getRsRefuseTime() {
        return rsRefuseTime;
    }

    public void setRsRefuseTime(Calendar rsRefuseTime) {
        this.rsRefuseTime = rsRefuseTime;
    }

    public Calendar getRsSignTime() {
        return rsSignTime;
    }

    public void setRsSignTime(Calendar rsSignTime) {
        this.rsSignTime = rsSignTime;
    }

    public String getRsSignDrId() {
        return rsSignDrId;
    }

    public void setRsSignDrId(String rsSignDrId) {
        this.rsSignDrId = rsSignDrId;
    }

    public String getRsSignTeamId() {
        return rsSignTeamId;
    }

    public void setRsSignTeamId(String rsSignTeamId) {
        this.rsSignTeamId = rsSignTeamId;
    }

    public String getRsSignHospId() {
        return rsSignHospId;
    }

    public void setRsSignHospId(String rsSignHospId) {
        this.rsSignHospId = rsSignHospId;
    }

    public String getRsSignHospAreaCode() {
        return rsSignHospAreaCode;
    }

    public void setRsSignHospAreaCode(String rsSignHospAreaCode) {
        this.rsSignHospAreaCode = rsSignHospAreaCode;
    }

    public String getRsRefuseDrId() {
        return rsRefuseDrId;
    }

    public void setRsRefuseDrId(String rsRefuseDrId) {
        this.rsRefuseDrId = rsRefuseDrId;
    }

    public String getRsRefuseHospId() {
        return rsRefuseHospId;
    }

    public void setRsRefuseHospId(String rsRefuseHospId) {
        this.rsRefuseHospId = rsRefuseHospId;
    }

    public String getRsRefuseHospAreaCode() {
        return rsRefuseHospAreaCode;
    }

    public void setRsRefuseHospAreaCode(String rsRefuseHospAreaCode) {
        this.rsRefuseHospAreaCode = rsRefuseHospAreaCode;
    }

    public String getRsPatientName() {
        return rsPatientName;
    }

    public void setRsPatientName(String rsPatientName) {
        this.rsPatientName = rsPatientName;
    }

    public String getRsPatientCard() {
        return rsPatientCard;
    }

    public void setRsPatientCard(String rsPatientCard) {
        this.rsPatientCard = rsPatientCard;
    }

    public String getRsRefuseTeamId() {
        return rsRefuseTeamId;
    }

    public void setRsRefuseTeamId(String rsRefuseTeamId) {
        this.rsRefuseTeamId = rsRefuseTeamId;
    }
}
