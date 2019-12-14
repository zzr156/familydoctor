package com.ylz.bizDo.plan.vo;

/**
 * Created by zzl on 2017/11/28.
 */
public class AppGeneralDiseaseQvo {
    private String id;
    private String gdtGenId;//通用随访外键
    private String gdtDiseaseValue;//疾病值 1无 2高血压 3糖尿病 4冠心病 5慢性阻塞性肺疾病 6恶性肿瘤 7脑卒中 8严重精神障碍 9结核病 10肝炎 11其他法定传染病 12职业病 13其他
    private String gdtDiseaseTitle;//输入的疾病名称
    private String gdtConfirmedDate;//确诊时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGdtGenId() {
        return gdtGenId;
    }

    public void setGdtGenId(String gdtGenId) {
        this.gdtGenId = gdtGenId;
    }

    public String getGdtDiseaseValue() {
        return gdtDiseaseValue;
    }

    public void setGdtDiseaseValue(String gdtDiseaseValue) {
        this.gdtDiseaseValue = gdtDiseaseValue;
    }

    public String getGdtDiseaseTitle() {
        return gdtDiseaseTitle;
    }

    public void setGdtDiseaseTitle(String gdtDiseaseTitle) {
        this.gdtDiseaseTitle = gdtDiseaseTitle;
    }

    public String getGdtConfirmedDate() {
        return gdtConfirmedDate;
    }

    public void setGdtConfirmedDate(String gdtConfirmedDate) {
        this.gdtConfirmedDate = gdtConfirmedDate;
    }
}
