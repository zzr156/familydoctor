package com.ylz.bizDo.jtapp.commonVo;

/**
 * 解约请求参数类
 * Created by zzl on 2018/5/21.
 */
public class WebSurrenderSignVo {
    private String patientIdNo;//身份证
    private String dissolutionTime;//解约时间
    private String dissolutionType;//解约类型 (1死亡)
    private String dissolutionCausa;//解约原因
    private String drId;//医生主键
    private String deptId;//医院主键
    private String areaCodeCity;//行政区划（市）

    public String getPatientIdNo() {
        return patientIdNo;
    }

    public void setPatientIdNo(String patientIdNo) {
        this.patientIdNo = patientIdNo;
    }

    public String getDissolutionTime() {
        return dissolutionTime;
    }

    public void setDissolutionTime(String dissolutionTime) {
        this.dissolutionTime = dissolutionTime;
    }

    public String getDissolutionType() {
        return dissolutionType;
    }

    public void setDissolutionType(String dissolutionType) {
        this.dissolutionType = dissolutionType;
    }

    public String getDissolutionCausa() {
        return dissolutionCausa;
    }

    public void setDissolutionCausa(String dissolutionCausa) {
        this.dissolutionCausa = dissolutionCausa;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getAreaCodeCity() {
        return areaCodeCity;
    }

    public void setAreaCodeCity(String areaCodeCity) {
        this.areaCodeCity = areaCodeCity;
    }
}
