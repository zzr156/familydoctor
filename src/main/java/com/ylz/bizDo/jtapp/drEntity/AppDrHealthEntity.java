package com.ylz.bizDo.jtapp.drEntity;

/**
 * 健康教育list列表返回数据(查看发给患者的列表)
 * Created by zzl on 2017/6/30.
 */
public class AppDrHealthEntity {
    private String id;//数据主键
    private String title;//健康教育标题
    private String imageUrl;//图片路径
    private String time;//发布时间
    private String patientId;//患者id
    private String patientName;//患者姓名
    private String diseaseType;//患者类型
    private String getDiseaseTypeName;//患者类型名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getGetDiseaseTypeName() {
        return getDiseaseTypeName;
    }

    public void setGetDiseaseTypeName(String getDiseaseTypeName) {
        this.getDiseaseTypeName = getDiseaseTypeName;
    }
}
