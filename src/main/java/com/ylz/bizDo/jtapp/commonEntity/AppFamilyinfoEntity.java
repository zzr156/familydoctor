package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by zzl on 2018/6/20.
 */
public class AppFamilyinfoEntity {
    private String code;//身份证
    private String name;//名字
    private String tel;//电话
    private String relation;//昵称
    private String patientId;//患者主键
    private String signState;//签约状态
    private String jdState;//建档状态

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }

    public String getJdState() {
        return jdState;
    }

    public void setJdState(String jdState) {
        this.jdState = jdState;
    }
}
