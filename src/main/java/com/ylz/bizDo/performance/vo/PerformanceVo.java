package com.ylz.bizDo.performance.vo;

/**
 * 居民分析
 */
public class    PerformanceVo {
    private String areaId;//区域主键
    private String hospId;//社区主键
    private String teamId;//团队主键
    private String drId;//医生主键

    private String labelGruops;//疾病类型 多使用分号隔开
    private String labelGruopsColor;//疾病颜色 多使用分号隔开

    private String startDate;//开始时间
    private String endDate;//结束时间

    private String startYyyyMm;//开始月份
    private String endYyyyMm;//结束月份

    private String roleId;//角色权限(3,医生,4,医院,5区,6市,7省)

    public String getStartYyyyMm() {
        return startYyyyMm;
    }

    public void setStartYyyyMm(String startYyyyMm) {
        this.startYyyyMm = startYyyyMm;
    }

    public String getEndYyyyMm() {
        return endYyyyMm;
    }

    public void setEndYyyyMm(String endYyyyMm) {
        this.endYyyyMm = endYyyyMm;
    }

    public String getLabelGruops() {
        return labelGruops;
    }

    public void setLabelGruops(String labelGruops) {
        this.labelGruops = labelGruops;
    }

    public String getLabelGruopsColor() {
        return labelGruopsColor;
    }

    public void setLabelGruopsColor(String labelGruopsColor) {
        this.labelGruopsColor = labelGruopsColor;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
