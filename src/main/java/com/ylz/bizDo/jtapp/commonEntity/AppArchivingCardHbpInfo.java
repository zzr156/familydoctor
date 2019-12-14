package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by zzl on 2018/11/13.
 */
public class AppArchivingCardHbpInfo {
    private String flupNum;//签约后随访次数
    private String lastflupDate;//最后一次随访时间
    private String bpNum;//签约后测血压次数
    private String lastBpDate;//最后一次测血压时间

    public String getFlupNum() {
        return flupNum;
    }

    public void setFlupNum(String flupNum) {
        this.flupNum = flupNum;
    }

    public String getLastflupDate() {
        return lastflupDate;
    }

    public void setLastflupDate(String lastflupDate) {
        this.lastflupDate = lastflupDate;
    }

    public String getBpNum() {
        return bpNum;
    }

    public void setBpNum(String bpNum) {
        this.bpNum = bpNum;
    }

    public String getLastBpDate() {
        return lastBpDate;
    }

    public void setLastBpDate(String lastBpDate) {
        this.lastBpDate = lastBpDate;
    }
}
