package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by asus on 2017/6/17.
 */
public class AppPwdQvo extends CommConditionVo {
    private String oldPwd;//原密码
    private String newPwd;//新密码
    private String userShort;//短信
    private String userTel;//电话

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }


    public String getUserShort() {
        return userShort;
    }

    public void setUserShort(String userShort) {
        this.userShort = userShort;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }
}
