package com.ylz.bizDo.plan.Entity;

import com.ylz.packcommon.common.util.ExtendDate;

import java.math.BigInteger;
import java.sql.Date;

/**
 * Created by dws on 2017-05-15.
 */
public class AppFollowPlanTjEntjty {
    private Date planDate;
    private BigInteger planCount;

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public BigInteger getPlanCount() {
        return planCount;
    }

    public void setPlanCount(BigInteger planCount) {
        this.planCount = planCount;
    }

    public String getStrPlanDate(){
        if(this.getPlanDate()!=null){
            return ExtendDate.getYMD(this.getPlanDate());
        }else{
            return "";
        }

    }
}
