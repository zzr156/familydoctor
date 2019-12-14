package com.ylz.bizDo.app.vo;

/**
 * 三诺接口返回
 * Created by zzl on 2018/5/11.
 */
public class SNJson {
    private String statusCode;//00成功、01重复数据、02数据错误、03服务器异常、04没有权限、05数据不存在
    private String desc;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
