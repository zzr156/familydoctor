package com.ylz.bizDo.jtapp.commonEntity;

import org.apache.commons.lang.StringUtils;

/** 健康教育收藏信息
 * Created by zzl on 2017/8/4.
 */
public class AppEnshrineHealthEntity  {
    private String id;//主键id
    private String newsId;//健康模板id
    private String title;//标题
    private String content;//内容
    private String imageUrl;//图片
    private String timeStart;//收藏时间
    private String timeCreate;//创建教育模板时间
    private String drId;//医生id
    private String drName;//医生姓名
    private String scl;//收藏量
    private String lll;//浏览量
    private String tsl;//推送量

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
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

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(String timeCreate) {
        this.timeCreate = timeCreate;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        if(StringUtils.isBlank(drName)){
            drName = "系统";
        }
        this.drName = drName;
    }

    public String getScl() {
        return scl;
    }

    public void setScl(String scl) {
        this.scl = scl;
    }

    public String getLll() {
        return lll;
    }

    public void setLll(String lll) {
        this.lll = lll;
    }

    public String getTsl() {
        return tsl;
    }

    public void setTsl(String tsl) {
        this.tsl = tsl;
    }
}
