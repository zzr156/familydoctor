package com.ylz.bizDo.jtapp.commonEntity;

import java.util.List;

/**
 * 中医体质辨识列表返回数据
 * Created by zzl on 2017/8/7.
 */
public class AppTcmListEntity  {
    private String userId;//患者id
    private String userName;//患者姓名
    private String idNo;//身份证
    private String tel;//电话
    private String addr;//地址
    private List<AppTcmLsEntity> reasult;//体质辨识记录

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public List<AppTcmLsEntity> getReasult() {
        return reasult;
    }

    public void setReasult(List<AppTcmLsEntity> reasult) {
        this.reasult = reasult;
    }
}
