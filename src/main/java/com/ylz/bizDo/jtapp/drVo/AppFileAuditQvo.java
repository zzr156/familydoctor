package com.ylz.bizDo.jtapp.drVo;

/**
 * Created by zzl on 2018/2/7.
 */
public class AppFileAuditQvo {
    private String id;//主键
    private String hfPatientId;//患者id
    private String hfDrId;//医生id
    private String hfAreaCode;//区域编号
    private String hfHospId;//医院id
    private String hfTeamId;//团队id
    private String data;//json数据
    private String hfAuditState;//审核状态
    private String hfRefusedReason;//拒绝原因

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
}
