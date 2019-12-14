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
@Table(name = "ASSESSMENT")
@DynamicInsert
public class Assessment extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;

    @Column(name = "SIGN_ID")
    private String signId;
    @Column(name = "DOCTOR_ID")
    private String doctorId;
    @Column(name = "PATIENT_ID")
    private String patientId;
    @Column(name = "HOSP_ID")
    private String hospId;
    @Column(name = "TEAM_ID")
    private String teamId;
    @Column(name = "TOTAL_SCORE_PRE", precision = 16, scale = 2)
    private BigDecimal totalScorePre;
    @Column(name = "TOTAL_SCORE_AFT", precision = 16, scale = 2)
    private BigDecimal totalScoreAft;
    @Column(name = "IS_REVIEW")
    private String isReview;
    @Column(name = "DETAIL_NUM")
    private Integer detailNum;
    @Column(name = "FINISH_NUM")
    private Integer finishNum;
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;
    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;
    @Column(name = "STATE")
    private String state;
    @Column(name = "SAVE_NUM")
    private Integer saveNum;//考核次数
    @Column(name = "REVIEW_NUM")
    private Integer reviewNum;//审核次数
    @Column(name = "IS_EXTRACT")
    private String isExtract;//是否抽取
    @Column(name = "SIGN_AREA_CODE", length = 200)
    private String signAreaCode;// 行政编码
    @Column(name = "IS_FINISH")
    private String isFinish;// 是否考核完成（1-已完成，0或null-未完成）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public BigDecimal getTotalScorePre() {
        return totalScorePre;
    }

    public void setTotalScorePre(BigDecimal totalScorePre) {
        this.totalScorePre = totalScorePre;
    }

    public BigDecimal getTotalScoreAft() {
        return totalScoreAft;
    }

    public void setTotalScoreAft(BigDecimal totalScoreAft) {
        this.totalScoreAft = totalScoreAft;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getIsReview() {
        return isReview;
    }

    public void setIsReview(String isReview) {
        this.isReview = isReview;
    }

    public Integer getDetailNum() {
        return detailNum;
    }

    public void setDetailNum(Integer detailNum) {
        this.detailNum = detailNum;
    }

    public Integer getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getSaveNum() {
        return saveNum;
    }

    public void setSaveNum(Integer saveNum) {
        this.saveNum = saveNum;
    }

    public Integer getReviewNum() {
        return reviewNum;
    }

    public void setReviewNum(Integer reviewNum) {
        this.reviewNum = reviewNum;
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

    public String getSignAreaCode() {
        return signAreaCode;
    }

    public void setSignAreaCode(String signAreaCode) {
        this.signAreaCode = signAreaCode;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }
}
