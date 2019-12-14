package com.ylz.bizDo.app.vo;

/**
 * Created by asus on 2017/11/08.
 */
public class AppServeManageQvo {
    private String areaCode;//行政区划
    private String hospId;//医院主键
    private String result;//结果
    private String serValue;
    private String serHospId;//医院主键
    private String serDrId;//医生主键
    private String serAreaCode;//行政区划
    private String serYear;//年

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSerValue() {
        return serValue;
    }

    public void setSerValue(String serValue) {
        this.serValue = serValue;
    }

    public String getSerHospId() {
        return serHospId;
    }

    public void setSerHospId(String serHospId) {
        this.serHospId = serHospId;
    }

    public String getSerDrId() {
        return serDrId;
    }

    public void setSerDrId(String serDrId) {
        this.serDrId = serDrId;
    }

    public String getSerAreaCode() {
        return serAreaCode;
    }

    public void setSerAreaCode(String serAreaCode) {
        this.serAreaCode = serAreaCode;
    }

    public String getSerYear() {
        return serYear;
    }

    public void setSerYear(String serYear) {
        this.serYear = serYear;
    }
}
