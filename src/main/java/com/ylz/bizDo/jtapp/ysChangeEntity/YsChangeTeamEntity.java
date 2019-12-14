package com.ylz.bizDo.jtapp.ysChangeEntity;

import java.math.BigInteger;

/**
 * Created by zzl on 2017/9/4.
 */
public class YsChangeTeamEntity {
    private String id;//团队id
    private String teamName;//团队名称
    private String signCount;//签约数
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getSignCount() {
        return signCount;
    }

    public void setSignCount(BigInteger signCount) {
        if(signCount==null){
            this.signCount = "0";
        }else{
            this.signCount = signCount.toString();
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
