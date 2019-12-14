package com.ylz.bizDo.jtapp.commonEntity;

/**
 * 科室查询返回列表
 * Created by zzl on 2017/12/13.
 */
public class AppFindDeptEntity {
    private String id;//主键
    private String hospId;//医院id
    private String hospName;//医院名称
    private String deptName;//科室名称
    private String deptCode;//科室编码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        this.hospName = hospName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
}
