package com.ylz.bizDo.jtapp.commonEntity;

/**
 * 我的设备
 */
public class AppDevEntity {

    private String id;//设备id
    private String sim;//设备码
    private String type;//设备类型 1血压计 2血糖仪
    private String time;//绑定时间
    private String drName;//绑定医生姓名
    private String drTitle;//医生职称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrTitle() {
        return drTitle;
    }

    public void setDrTitle(String drTitle) {
        this.drTitle = drTitle;
    }
}
