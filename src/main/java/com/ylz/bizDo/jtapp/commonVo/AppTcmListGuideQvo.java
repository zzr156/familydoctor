package com.ylz.bizDo.jtapp.commonVo;

import java.util.List;

/**保存中医药保健指导
 * Created by zzl on 2017/8/29.
 */
public class AppTcmListGuideQvo  {
    private String type;//类型 1医生 2医院 3系统
    private String drId;
    private String hospId;
    private List<AppNewTcmGuideQvo> guideList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public List<AppNewTcmGuideQvo> getGuideList() {
        return guideList;
    }

    public void setGuideList(List<AppNewTcmGuideQvo> guideList) {
        this.guideList = guideList;
    }
}
