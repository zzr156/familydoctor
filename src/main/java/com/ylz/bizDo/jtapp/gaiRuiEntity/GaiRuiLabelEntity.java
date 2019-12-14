package com.ylz.bizDo.jtapp.gaiRuiEntity;

/**
 * Created by zzl on 2019/3/25.
 */
public class GaiRuiLabelEntity {
    private String id;//主键
    private String labelValue;//值
    private String labelTitle;//名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }
}
