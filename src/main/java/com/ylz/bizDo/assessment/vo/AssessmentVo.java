package com.ylz.bizDo.assessment.vo;

import com.ylz.bizDo.assessment.po.AssessmentDetail;

import java.math.BigInteger;
import java.util.Map;

/**
 * Created by zms on 2018/6/6.
 */
public class AssessmentVo {
    private String assessId;
    private String signId;
    private String patientId;
    private String hospId;
    private String drId;
    private String teamId;
    private String patientName;
    private Map<String, AssessmentDetail> detailMap;
    private Integer detailNum;
    private Map<String, String[]> groupMap;
    private String[] groups;// 所属人群
    private String isExtract;
    private String isFinish;
    private String activityTime;// 32考核项开展活动的时间
    private BigInteger notAssessNum;// 签约协议到期前一个月还未考核的居民数量

    /**
     * 各考核项对应的附件属性
     */
    private String contentCode;// 考核项编码
    private String attachmentFiles;// 附件文件（多个文件用逗号分隔）
    private String attachmentFileNames;// 附件文件名称（多个文件名称用逗号分隔）


    public String getAssessId() {
        return assessId;
    }

    public void setAssessId(String assessId) {
        this.assessId = assessId;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public Map<String, AssessmentDetail> getDetailMap() {
        return detailMap;
    }

    public void setDetailMap(Map<String, AssessmentDetail> detailMap) {
        this.detailMap = detailMap;
    }

    public Integer getDetailNum() {
        return detailNum;
    }

    public void setDetailNum(Integer detailNum) {
        this.detailNum = detailNum;
    }

    public Map<String, String[]> getGroupMap() {
        return groupMap;
    }

    public void setGroupMap(Map<String, String[]> groupMap) {
        this.groupMap = groupMap;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getIsExtract() {
        return isExtract;
    }

    public void setIsExtract(String isExtract) {
        this.isExtract = isExtract;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public BigInteger getNotAssessNum() {
        return notAssessNum;
    }

    public void setNotAssessNum(BigInteger notAssessNum) {
        this.notAssessNum = notAssessNum;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getAttachmentFiles() {
        return attachmentFiles;
    }

    public void setAttachmentFiles(String attachmentFiles) {
        this.attachmentFiles = attachmentFiles;
    }

    public String getAttachmentFileNames() {
        return attachmentFileNames;
    }

    public void setAttachmentFileNames(String attachmentFileNames) {
        this.attachmentFileNames = attachmentFileNames;
    }
}
