package com.ylz.bizDo.smjq.smVo;

/**
 * 三明登入请求参数
 * Created by zzl on 2018/7/24.
 */
public class AppSmLoginVo {
    private String account;//医生账号
    private String passWord;//密码

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
