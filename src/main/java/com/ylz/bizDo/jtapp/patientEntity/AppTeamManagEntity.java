package com.ylz.bizDo.jtapp.patientEntity;

/**
 * Created by zzl on 2017/6/17.
 */
public class AppTeamManagEntity {
    private String teamName;//团队名称
    private String teamId;//团队id
    private String hospId;//医院主键
    private String areaCode;//行政区划
    private String hospLevelType;//上级医院：0综合医院 1专科医院 2中医医院 3中西医结合医院 4民族医医院 5康复医院 6妇幼保健院  基层医院：7社区卫生服务中心（站） 8中心卫生院 9乡镇卫生院
    private String jdAreaCode;
    private String addrHospId;//归管单位

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getJdAreaCode() {
        return jdAreaCode;
    }

    public void setJdAreaCode(String jdAreaCode) {
        this.jdAreaCode = jdAreaCode;
    }

    public String getHospLevelType() {
        return hospLevelType;
    }

    public void setHospLevelType(String hospLevelType) {
        this.hospLevelType = hospLevelType;
    }

    public String getAddrHospId() {
        return addrHospId;
    }

    public void setAddrHospId(String addrHospId) {
        this.addrHospId = addrHospId;
    }
}
