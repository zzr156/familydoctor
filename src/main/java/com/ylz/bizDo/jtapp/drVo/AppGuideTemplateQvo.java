package com.ylz.bizDo.jtapp.drVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/6/20.
 */
public class AppGuideTemplateQvo extends CommConditionVo {
    private String title;//标题
    private String content;//内容
    private String type;//指导/干预类型类型
    private String diseaseType;//疾病类别
    private String meddleType;//干预类别
    private String imageUrl;//图片
    private String imageName;//文件名
    private String userId;//用户id
    private String id;//健康指导id
    private String hospId;//医院id

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getMeddleType() {
        return meddleType;
    }

    public void setMeddleType(String meddleType) {
        this.meddleType = meddleType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }
}
