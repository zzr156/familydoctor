package com.ylz.bizDo.plan.Entity;

import java.math.BigInteger;

/**
 * 当天随访提醒
 * Created by zzl on 2017/7/29.
 */
public class AppFollowPlanTxEntity  {
    private String id;//主键id
    private BigInteger zsfs;//总随访人数
    private BigInteger ysfs;//已随访人数
    private BigInteger wsfs;//未随访人数
    private String drId;//医生id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public BigInteger getZsfs() {
        return zsfs;
    }

    public void setZsfs(BigInteger zsfs) {
        this.zsfs = zsfs;
    }

    public BigInteger getYsfs() {
        return ysfs;
    }

    public void setYsfs(BigInteger ysfs) {
        this.ysfs = ysfs;
    }

    public BigInteger getWsfs() {
        return wsfs;
    }

    public void setWsfs(BigInteger wsfs) {

        this.wsfs = wsfs;
    }
}
