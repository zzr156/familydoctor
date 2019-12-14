package com.ylz.bizDo.jtapp.signSersetEntity;

/**
 * Created by zzl on 2018/3/20.
 */
public class AppSignServeObjectEntity {
    private String id;//服务对象主键
    private String title;//服务对象名称
    private String value;//服务对象值
    private String fwType;//服务人群类型
    private String labelType;//所属服务类型 3服务人群 4经济类型

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFwType() {
        return fwType;
    }

    public void setFwType(String fwType) {
        this.fwType = fwType;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }
}
