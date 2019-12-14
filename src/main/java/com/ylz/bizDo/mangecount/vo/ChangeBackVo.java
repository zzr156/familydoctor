package com.ylz.bizDo.mangecount.vo;

/**
 * Created by asus on 2017/6/27.
 */
public class ChangeBackVo {
    private String changeBackType;//改签类型 1待审核人数,2改签人数,3.退签人数
    private String dateType;///时间类型,1日,2周,3月
    private String areaId;//区域主键
    private String hospId;//社区主键

    public String getChangeBackType() {
        return changeBackType;
    }

    public void setChangeBackType(String changeBackType) {
        this.changeBackType = changeBackType;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }
}
