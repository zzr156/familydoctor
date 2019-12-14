package com.ylz.bizDo.plan.Entity;

import java.math.BigInteger;

/**
 * Created by lintingjie on 2017/8/7.
 */
public class AppFollowListEntity {

    private String followDate;
    private BigInteger followCount;

    public String getFollowDate() {
        return followDate;
    }

    public void setFollowDate(String followDate) {
        this.followDate = followDate;
    }

    public BigInteger getFollowCount() {
        return followCount;
    }

    public void setFollowCount(BigInteger followCount) {
        this.followCount = followCount;
    }
}
