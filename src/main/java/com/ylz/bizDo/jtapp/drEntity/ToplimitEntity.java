package com.ylz.bizDo.jtapp.drEntity;

/** 签约上限返回参数
 * Created by zzl on 2017/10/24.
 */
public class ToplimitEntity {
    private String toplimit;//是否签约上限（0否 1是）
    private Integer lastName;//还剩签约人数

    public String getToplimit() {
        return toplimit;
    }

    public void setToplimit(String toplimit) {
        this.toplimit = toplimit;
    }

    public Integer getLastName() {
        return lastName;
    }

    public void setLastName(Integer lastName) {
        this.lastName = lastName;
    }
}
