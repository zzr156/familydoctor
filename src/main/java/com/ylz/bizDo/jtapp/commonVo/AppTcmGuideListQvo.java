package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

import java.util.List;

/**中医体质辨识添加指导qvo
 * Created by zzl on 2017/8/28.
 */
public class AppTcmGuideListQvo extends CommConditionVo {
    private String id;
    private String drId;//医生id
    private String hospId;//医院id
    private String roleType;//添加模板类型（1医生端 添加医生模板 2管理端 添加医院模板
    private List<AppTcmTzQvo> guideLs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public List<AppTcmTzQvo> getGuideLs() {
        return guideLs;
    }

    public void setGuideLs(List<AppTcmTzQvo> guideLs) {
        this.guideLs = guideLs;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }
}
