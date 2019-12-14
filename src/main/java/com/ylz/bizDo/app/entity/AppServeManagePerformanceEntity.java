package com.ylz.bizDo.app.entity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.PerformanceType;
import com.ylz.packcommon.common.comEnum.ResidentMangeType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/11/07.
 */
public class AppServeManagePerformanceEntity {
    private String setNum;//频次
    private String serTitle;//标题
    private String serValue;//结果
    private String obectTitle;
    private String objectValue;
    private String hospId;
    private String areaCode;
    private String drId;
    private String signCount;
    private String year;//年份

    public String getSetNum() {
        return setNum;
    }

    public void setSetNum(String setNum) {
        this.setNum = setNum;
    }

    public String getSerTitle() {
        return serTitle;
    }

    public void setSerTitle(String serTitle) {
        this.serTitle = serTitle;
    }

    public String getSerValue() {
        return serValue;
    }

    public void setSerValue(String serValue) {
        this.serValue = serValue;
    }

    public String getObectTitle() {
        return obectTitle;
    }

    public void setObectTitle(String obectTitle) {
        this.obectTitle = obectTitle;
    }

    public String getObjectValue() {
        return objectValue;
    }

    public void setObjectValue(String objectValue) {
        this.objectValue = objectValue;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

	public String getSignCount() {
		return signCount;
	}

	public void setSignCount(String signCount) {
		this.signCount = signCount;
	}

}
