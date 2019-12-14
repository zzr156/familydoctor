package com.ylz.bizDo.jtapp.commonEntity;

import java.math.BigInteger;

/**
 * 绩效消息调度查询非托管实体
 * Created by zzl on 2018/10/11.
 */
public class AssessNewsEntity {
    private String drId;
    private BigInteger notAssessNum;

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public BigInteger getNotAssessNum() {
        return notAssessNum;
    }

    public void setNotAssessNum(BigInteger notAssessNum) {
        this.notAssessNum = notAssessNum;
    }
}
