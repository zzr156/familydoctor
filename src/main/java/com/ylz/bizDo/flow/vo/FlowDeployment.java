package com.ylz.bizDo.flow.vo;

import com.ylz.packcommon.common.util.ExtendDate;

import java.sql.Timestamp;

/**
 * Created by PC on 2015/9/2.
 */
public class FlowDeployment {
    private String id;
    private String name;//名称
    private String category;//类型
    private String dtime;//时间
    private String ves;//版本
    private String actkey;//key值

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDtime() {
        return dtime;
    }

    public void setDtime(Timestamp dtime) {
        if(dtime!=null) {
            this.dtime = ExtendDate.getYMD_h_m_s(dtime);
        } else {
            this.dtime ="";
        }
    }

    public String getVes() {
        return ves;
    }

    public void setVes(Integer ves) {
        this.ves = String.valueOf(ves);
    }

    public String getActkey() {
        return actkey;
    }

    public void setActkey(String actkey) {
        this.actkey = actkey;
    }
}
