package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2018/1/15.
 */
public class AppSignSetQvo extends CommConditionVo {
    private String areaCode;//市区域编码（4位数）

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
