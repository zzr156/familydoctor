package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

import java.util.List;

/**发送中医药保健指导
 * Created by zzl on 2017/8/7.
 */
public class AppTcmGuideQvo  extends CommConditionVo {
    private String userId;//患者id
    private String tzType;//体质类型
    private String guideType;//指导类型
    private String drId;//医生id
    private String title;//指导标题
    private String content;//指导内容
    private String imageUrl;//图片
    private String imageName;//图片名称
    private String id;//指导模板id
    private String type;//查询类型（1查医生 2查医院 3查系统）
    private List<AppTcmGuideOtherQvo> othreGuide;//其他指导
    private String jlId;//答题记录Id
    private String newId;//系统或医院模板id
    private String roleType;//权限状态（1管理端加医院模板 2医生端加医生模板）
    private String hospId;//医院id
    private List<AppTcmGuideQQvo> guideList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTzType() {
        return tzType;
    }

    public void setTzType(String tzType) {
        this.tzType = tzType;
    }

    public String getGuideType() {
        return guideType;
    }

    public void setGuideType(String guideType) {
        this.guideType = guideType;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AppTcmGuideOtherQvo> getOthreGuide() {
        return othreGuide;
    }

    public void setOthreGuide(List<AppTcmGuideOtherQvo> othreGuide) {
        this.othreGuide = othreGuide;
    }

    public String getJlId() {
        return jlId;
    }

    public void setJlId(String jlId) {
        this.jlId = jlId;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public List<AppTcmGuideQQvo> getGuideList() {
        return guideList;
    }

    public void setGuideList(List<AppTcmGuideQQvo> guideList) {
        this.guideList = guideList;
    }
}
