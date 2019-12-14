package com.ylz.bizDo.jtapp.commonVo;

/**
 * 科室查询条件
 * Created by zzl on 2017/12/13.
 */
public class AppFindDeptQvo {
    private String hospId;//医院id
    private String deptName;//科室名称

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
