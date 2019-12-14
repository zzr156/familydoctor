package com.ylz.bizDo.web.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by zzl on 2018/12/11.
 */
@Entity
@Table(name = "APP_LABEL_GROUP")
public class WebLabelGroup extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
    private String id;//主键
    @Column(name = "LABEL_SIGN_ID", length = 36)
    private String labelSignId;//签约表主键
    @Column(name = "LABEL_TEAM_ID", length = 36)
    private String labelTeamId;//团队主键
    @Column(name = "LABEL_VALUE", length = 10)
    private String labelValue;//标签值
    @Column(name = "LABEL_ID", length = 36)
    private String labelId;//标签主键
    @Column(name = "LABEL_TITLE", length = 30)
    private String labelTitle;//标签名称
    @Column(name = "LABEL_TYPE", length = 10)
    private String labelType;//标签类型
    @Column(name = "LABEL_COLOR", length = 10)
    private String labelColor;//标签颜色
    @Column(name = "LABEL_AREA_CODE", length = 100)
    private String labelAreaCode;//行政区划

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelSignId() {
        return labelSignId;
    }

    public void setLabelSignId(String labelSignId) {
        this.labelSignId = labelSignId;
    }

    public String getLabelTeamId() {
        return labelTeamId;
    }

    public void setLabelTeamId(String labelTeamId) {
        this.labelTeamId = labelTeamId;
    }

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public String getLabelAreaCode() {
        return labelAreaCode;
    }

    public void setLabelAreaCode(String labelAreaCode) {
        this.labelAreaCode = labelAreaCode;
    }
}
