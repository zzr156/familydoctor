package com.ylz.bizDo.jtapp.drVo;

/**
 * Created by zzl on 2017/7/31.
 */
public class AppHealthToQvo  {
    private String teamId;//团队id
    private String drId;//医生id
    private String newsId;//健康教育模板id
    private String labelType;//1健康情况 2疾病类型 3
    private String labelValue;
    private String fwrq;//服务人群

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getFwrq() {
        return fwrq;
    }

    public void setFwrq(String fwrq) {
        this.fwrq = fwrq;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }
}
