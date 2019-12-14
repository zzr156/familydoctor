package com.ylz.bizDo.plan.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**随访
 * Created by zzl on 2017/6/29.
 */
public class AppFollowWarnQvo extends CommConditionVo {

    private String morningTime;//早上提醒时间
    private String noonTime;//中午提醒时间
    private String nightTime;//晚上提醒时间
    private String warnState;//随访当天预警状态


    public String getMorningTime() {
        return morningTime;
    }

    public void setMorningTime(String morningTime) {
        this.morningTime = morningTime;
    }

    public String getNoonTime() {
        return noonTime;
    }

    public void setNoonTime(String noonTime) {
        this.noonTime = noonTime;
    }

    public String getNightTime() {
        return nightTime;
    }

    public void setNightTime(String nightTime) {
        this.nightTime = nightTime;
    }

    public String getWarnState() {
        return warnState;
    }

    public void setWarnState(String warnState) {
        this.warnState = warnState;
    }

}
