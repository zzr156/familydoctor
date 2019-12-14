package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by zzl on 2017/6/26.
 */
public class AppMeddleEntity {
    private String id;
    private String value;
    private String title;
    private String state;//启用状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
