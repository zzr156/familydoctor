package com.ylz.bizDo.jtapp.drVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by dws on 2017-05-09.
 */
public class AppDrUserQvo extends CommConditionVo {
    private String drId;
    private String userAccount;
    private String userPass;
    private String selectType;
    private String userShort;//短信号

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    public String getUserShort() {
        return userShort;
    }

    public void setUserShort(String userShort) {
        this.userShort = userShort;
    }
}
