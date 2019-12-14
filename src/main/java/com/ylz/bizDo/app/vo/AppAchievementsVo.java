package com.ylz.bizDo.app.vo;

/**绩效考核Vo
 *
 * Created by WangCheng on 2018/10/31.
 */
public class AppAchievementsVo {

    private String orgId;//机构Id
    private String patientIdNo;//身份证
    private String startTime;//查询起始时间
    private String endTime;//查询结束时间
    private String urlType;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPatientIdNo() {
        return patientIdNo;
    }

    public void setPatientIdNo(String patientIdNo) {
        this.patientIdNo = patientIdNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }
}
