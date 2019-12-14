package com.ylz.bizDo.jtapp.drVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * app添加团队成员保持团队id和工作类型
 * Created by zzl on 2017/7/13.
 */
public class AppDrTeamMemQvo extends CommConditionVo {
    private String memId;
    private String workType;

    public String getMemId() {
        return memId;
    }

    public void setMemId(String memId) {
        this.memId = memId;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }
}
