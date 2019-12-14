package com.ylz.bizDo.jtapp.commonVo;

import java.util.List;

/**体质类型
 * Created by zzl on 2017/8/28.
 */
public class AppTcmTzQvo {
    private String tzType;//体质类型
    private List<AppTcmGuideVo> guideList;

    public String getTzType() {
        return tzType;
    }

    public void setTzType(String tzType) {
        this.tzType = tzType;
    }

    public List<AppTcmGuideVo> getGuideList() {
        return guideList;
    }

    public void setGuideList(List<AppTcmGuideVo> guideList) {
        this.guideList = guideList;
    }
}
