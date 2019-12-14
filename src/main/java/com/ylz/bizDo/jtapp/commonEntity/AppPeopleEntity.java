package com.ylz.bizDo.jtapp.commonEntity;

import java.math.BigInteger;

/**
 * 根据服务人群、健康情况、疾病类型返回数据类
 * Created by zzl on 2017/8/7.
 */
public class AppPeopleEntity  {
    private String typeValue;//类型值
    private String typeName;//类型名称
    private String count;//人数

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
