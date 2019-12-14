package com.ylz.bizDo.assessment.vo;

public class AssessmentContentVo {

    private String id;// 主键ID
    private String projectId;// 项目ID
    private String content;// 考核内容
    private String proof;// 佐证
    private String score;// 分值
    private String method;// 考核方式
    private String code;// 考核项编码
    private String shortContent;// 考核内容简述
    private String needUpload;// 是否需要上传附件
    private Integer orderNo;// 序号

    private String detailId;// 考核明细ID
    private Double scorePre;// 审核前得分
    private String optionWeb;// 附件路径


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

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public Double getScorePre() {
        return scorePre;
    }

    public void setScorePre(Double scorePre) {
        this.scorePre = scorePre;
    }

    public String getOptionWeb() {
        return optionWeb;
    }

    public void setOptionWeb(String optionWeb) {
        this.optionWeb = optionWeb;
    }
}
