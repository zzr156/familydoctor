package com.ylz.bizDo.jtapp.commonEntity;

import java.math.BigInteger;

/**
 * 根据服务人群、健康情况、疾病类型返回数据类
 * Created by zzl on 2017/8/7.
 */
public class AppPeopleHealthEntity {
    private String labelType;//类型值
    private String count;//人数

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public String getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        if(count == null){
            this.count = "0";
        }else{
            this.count = String.valueOf(count);
        }

    }

}
