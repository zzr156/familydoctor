package com.ylz.bizDo.assessment.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by zms on 2018/6/13.
 * 考核操作记录表
 */
@Entity
@Table(name = "ASSESS_LOG")
@DynamicInsert
public class AssessLog extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "ASSESSMENT_ID")
    private String assessmentId;
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;
    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "SIGN_AREA_CODE", length = 200)
    private String signAreaCode;// 行政编码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getSignAreaCode() {
        return signAreaCode;
    }

    public void setSignAreaCode(String signAreaCode) {
        this.signAreaCode = signAreaCode;
    }
}
