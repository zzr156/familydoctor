package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by zzl on 2017/6/19.
 */
public class AppNewsTableEntity {
    private String id;//健康教育主键
    private String title;//标题
    private String imageUrl;//图片
    private String type;//类型
    private String browse;//浏览次数
    private String transmit;//发布次数
    private String enshrine;//收藏次数
   // private String time;//
   // private String userId;//

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTransmit() {
        return transmit;
    }

    public void setTransmit(String transmit) {
        this.transmit = transmit;
    }

    public String getEnshrine() {
        return enshrine;
    }

    public void setEnshrine(String enshrine) {
        this.enshrine = enshrine;
    }
}
