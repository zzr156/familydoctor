package com.ylz.bizDo.jtapp.commonVo;

import java.util.List;

/**发送中医药保健指导请求参数
 * Created by zzl on 2017/8/30.
 */
public class AppNewTcmVo {
    private String jlId;//答题记录Id
    private String drId;//医生id
    private String userId;//患者id
    private List<AppNewTcmGuideQvo> guideList;//指导集合

    public String getJlId() {
        return jlId;
    }

    public void setJlId(String jlId) {
        this.jlId = jlId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<AppNewTcmGuideQvo> getGuideList() {
        return guideList;
    }

    public void setGuideList(List<AppNewTcmGuideQvo> guideList) {
        this.guideList = guideList;
    }
}
