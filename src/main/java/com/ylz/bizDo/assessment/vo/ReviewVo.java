package com.ylz.bizDo.assessment.vo;

import com.ylz.bizDo.assessment.po.AssessmentDetail;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zms on 2018/6/11.
 */
public class ReviewVo {

    private String assessmentId;
    private List<AssessmentDetail> details;
    private BigDecimal totalScoreAft;
    private String state;
    private String hospLevel;
    private String updateUserId;
    private String updateUserName;
    private String signAreaCode;

    public List<AssessmentDetail> getDetails() {
        return details;
    }

    public void setDetails(List<AssessmentDetail> details) {
        this.details = details;
    }

    public BigDecimal getTotalScoreAft() {
        return totalScoreAft;
    }

    public void setTotalScoreAft(BigDecimal totalScoreAft) {
        this.totalScoreAft = totalScoreAft;
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHospLevel() {
        return hospLevel;
    }

    public void setHospLevel(String hospLevel) {
        this.hospLevel = hospLevel;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getSignAreaCode() {
        return signAreaCode;
    }

    public void setSignAreaCode(String signAreaCode) {
        this.signAreaCode = signAreaCode;
    }
}
