package com.ylz.bizDo.assessment.po;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zms on 2018/6/8.
 */
@Entity
@Table(name = "ASSESSMENT_CONTENT")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)//要使用hibernate的二级缓存Cache的注释一定要添加上
public class AssessmentContent {

    @Id
    private String id;
    @Column(name = "PROJECT_ID")
    private String projectId;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "PROOF")
    private String proof;
    @Column(name = "SCORE")
    private String score;
    @Column(name = "METHOD")
    private String method;
    @Column(name = "CODE")
    private String code;
    @Column(name = "SHORT_CONTENT")
    private String shortContent;
    @Column(name = "NEED_UPLOAD")
    private String needUpload;
    @Column(name = "ORDER_NO")
    private Integer orderNo;
    @Column(name = "SIGN_AREA_CODE", length = 200)
    private String signAreaCode;// 行政编码


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public String getNeedUpload() {
        return needUpload;
    }

    public void setNeedUpload(String needUpload) {
        this.needUpload = needUpload;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getSignAreaCode() {
        return signAreaCode;
    }

    public void setSignAreaCode(String signAreaCode) {
        this.signAreaCode = signAreaCode;
    }
}
