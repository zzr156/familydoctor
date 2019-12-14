package com.ylz.bizDo.jtapp.signSersetEntity;

/**
 * Created by zzl on 2018/3/20.
 */
public class AppSignServePkEntity {
    private String id;//服务内容主键
    private String title;//服务内容名称
    private String value;//服务内容值
    private String openState;//是否开启频次
    private String num;//频次（次/年）

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

    public String getOpenState() {
        return openState;
    }

    public void setOpenState(String openState) {
        this.openState = openState;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
