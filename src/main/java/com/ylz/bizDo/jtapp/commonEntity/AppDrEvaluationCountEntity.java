package com.ylz.bizDo.jtapp.commonEntity;

import java.math.BigInteger;

/**
 * Created by asus on 2017/07/23.
 */
public class AppDrEvaluationCountEntity {
    private String type;//类型
    private BigInteger total;//总计

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
    }
}
