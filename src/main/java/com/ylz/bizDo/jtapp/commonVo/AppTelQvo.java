package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by asus on 2017/6/17.
 */
public class AppTelQvo extends CommConditionVo {
    private String newTel;//新手机
    private String userShort;//短信号

    public String getNewTel() {
        return newTel;
    }

    public void setNewTel(String newTel) {
        this.newTel = newTel;
    }

    public String getUserShort() {
        return userShort;
    }

    public void setUserShort(String userShort) {
        this.userShort = userShort;
    }
}
