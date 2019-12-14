package com.ylz.bizDo.jtapp.commonEntity;

/**
 * 血糖血压设备
 */
public class DeviceEntity {

    private String id;//设备id
    private String sim;//设备码
    private String type;//设备类型 1血压计 2血糖仪
    private String user;//用户id
    private String bindTime;//绑定日期
    private String createTime;//添加时间

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
