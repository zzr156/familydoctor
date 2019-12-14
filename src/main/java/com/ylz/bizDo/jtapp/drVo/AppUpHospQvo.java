package com.ylz.bizDo.jtapp.drVo;

/**
 * Created by zzl on 2017/12/20.
 */
public class AppUpHospQvo {
    private String hospName;//医院名称
    private String baseId;//当前医院id
    private String areaCode;//区域编号

    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        this.hospName = hospName;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
