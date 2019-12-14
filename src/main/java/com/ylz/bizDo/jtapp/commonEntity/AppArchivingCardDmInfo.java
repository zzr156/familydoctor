package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by zzl on 2018/11/13.
 */
public class AppArchivingCardDmInfo {
    private String flupNum;//签约后随访次数
    private String lastflupDate;//最后一次随访时间
    private String gluNum;//签约后测血糖次数
    private String lastGluDate;//最后一次测血糖时间

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

    public String getGluNum() {
        return gluNum;
    }

    public void setGluNum(String gluNum) {
        this.gluNum = gluNum;
    }

    public String getLastGluDate() {
        return lastGluDate;
    }

    public void setLastGluDate(String lastGluDate) {
        this.lastGluDate = lastGluDate;
    }
}
