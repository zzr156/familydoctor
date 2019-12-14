package com.ylz.bizDo.assessment.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by zms on 2018/6/5.
 */
@Entity
@Table(name = "ASSESSMENT_DETAIL")
@DynamicInsert//动态插入，为null不插入,默认值生效
public class AssessmentDetail extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "ASSESSMENT_ID")
    private String assessmentId;
    @Column(name = "CONTENT_CODE")
    private String contentCode;
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "OPTION_WEB")
    private String optionWeb;
    @Column(name = "OPTION_APP")
    private String optionApp;
    @Column(name = "STATE")
    private String state;
    @Column(name = "REASON")
    private String reason;
    @Column(name = "SCORE_PRE", precision = 16, scale = 2)
    private BigDecimal scorePre;
    @Column(name = "SCORE_AFT", precision = 16, scale = 2)
    private BigDecimal scoreAft;
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;
    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;
    @Column(name = "SIGN_AREA_CODE", length = 20)
    private String signAreaCode;// 行政编码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getOptionWeb() {
        return optionWeb;
    }

    public void setOptionWeb(String optionWeb) {
        this.optionWeb = optionWeb;
    }

    public String getOptionApp() {
        return optionApp;
    }

    public void setOptionApp(String optionApp) {
        this.optionApp = optionApp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getScorePre() {
        return scorePre;
    }

    public void setScorePre(BigDecimal scorePre) {
        this.scorePre = scorePre;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public BigDecimal getScoreAft() {
        return scoreAft;
    }

    public void setScoreAft(BigDecimal scoreAft) {
        this.scoreAft = scoreAft;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSignAreaCode() {
        return signAreaCode;
    }

    public void setSignAreaCode(String signAreaCode) {
        this.signAreaCode = signAreaCode;
    }
}
