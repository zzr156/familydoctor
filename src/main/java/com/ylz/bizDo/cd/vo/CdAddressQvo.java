package com.ylz.bizDo.cd.vo;

/**
 * 行政区划编码Qvo
 *
 * @date 2018年09月18日
 */
public class CdAddressQvo {

    private String areaCode;// 区域编码
    private String upAreaCode;// 上级区域编码


    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getUpAreaCode() {
        return upAreaCode;
    }

    public void setUpAreaCode(String upAreaCode) {
        this.upAreaCode = upAreaCode;
    }
}
