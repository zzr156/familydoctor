package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 患者健康档案表
 * Created by zzl on 2018/2/6.
 */
@Entity
@Table(name = "APP_HEALTH_FILE")
public class AppHealthFile extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "HF_PATIENT_ID",length = 36)
    private String hfPatientId;//患者id
    @Column(name = "HF_DR_ID",length = 36)
    private String hfDrId;//医生id
    @Column(name = "HF_AREA_CODE",length = 12)
    private String hfAreaCode;//区域编号
    @Column(name = "HF_HOSP_ID",length = 36)
    private String hfHospId;//医院id
    @Column(name = "HF_TEAM_ID",length = 36)
    private String hfTeamId;//团队id
    @Column(name = "DATA")
    private String data;//json数据
    @Column(name = "HF_AUDIT_STATE",length = 10)
    private String hfAuditState="0";//审核状态(0待审核 1审核通过 2拒绝)
    @Column(name = "HF_REFUSED_REASON")
    private String hfRefusedReason;//拒绝原因
    @Column(name = "HF_STATE",length = 10)
    private String hfState = "0";//数据状态 0是预建档数据 1是修改后的建档数据
    @Column(name = "MODIFY_DATA")
    private String modifyData;//修改数据
//    @Column(name = "HF_BASIC_DAH",length = 50)
//    private String hfBasicDah;//基卫档案号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHfPatientId() {
        return hfPatientId;
    }

    public void setHfPatientId(String hfPatientId) {
        this.hfPatientId = hfPatientId;
    }

    public String getHfDrId() {
        return hfDrId;
    }

    public void setHfDrId(String hfDrId) {
        this.hfDrId = hfDrId;
    }

    public String getHfAreaCode() {
        return hfAreaCode;
    }

    public void setHfAreaCode(String hfAreaCode) {
        this.hfAreaCode = hfAreaCode;
    }

    public String getHfHospId() {
        return hfHospId;
    }

    public void setHfHospId(String hfHospId) {
        this.hfHospId = hfHospId;
    }

    public String getHfTeamId() {
        return hfTeamId;
    }

    public void setHfTeamId(String hfTeamId) {
        this.hfTeamId = hfTeamId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHfAuditState() {
        return hfAuditState;
    }

    public void setHfAuditState(String hfAuditState) {
        this.hfAuditState = hfAuditState;
    }

    public String getHfRefusedReason() {
        return hfRefusedReason;
    }

    public void setHfRefusedReason(String hfRefusedReason) {
        this.hfRefusedReason = hfRefusedReason;
    }

    public String getHfState() {
        return hfState;
    }

    public void setHfState(String hfState) {
        this.hfState = hfState;
    }

    public String getModifyData() {
        return modifyData;
    }

    public void setModifyData(String modifyData) {
        this.modifyData = modifyData;
    }

//    public String getHfBasicDah() {
//        return hfBasicDah;
//    }
//
//    public void setHfBasicDah(String hfBasicDah) {
//        this.hfBasicDah = hfBasicDah;
//    }
}
