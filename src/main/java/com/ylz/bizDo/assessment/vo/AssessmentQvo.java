package com.ylz.bizDo.assessment.vo;

import com.ylz.bizDo.assessment.po.AssessmentDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by zms on 2018/6/6.
 */
public class AssessmentQvo {
    private String assessId;
    private String signId;
    private String patientId;
    private String drId;
    private Map<String,AssessmentDetail> detailMap;
    private Integer detailNum;
    private List<Map<String,String[]>> groupList;


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

    public List<Map<String, String[]>> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Map<String, String[]>> groupList) {
        this.groupList = groupList;
    }
}
