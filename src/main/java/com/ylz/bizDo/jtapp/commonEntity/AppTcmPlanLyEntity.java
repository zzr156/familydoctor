package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by zzl on 2017/11/16.
 */
public class AppTcmPlanLyEntity {
    private String tjDate;//体检日期
    private String state;//体检完成状态（0未完成 1完成）

    public String getTjDate() {
        return tjDate;
    }

    public void setTjDate(String tjDate) {
        this.tjDate = tjDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
