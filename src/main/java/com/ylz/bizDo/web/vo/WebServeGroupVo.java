package com.ylz.bizDo.web.vo;

import java.util.List;

/**
 * 服务组合vo
 * Created by zzl on 2018/3/13.
 */
public class WebServeGroupVo {
    private String sergId;//组合id
    private String sergValue;//组合编号
    private String sergGroupFee;//组合费用
    //服务对象
    private String seroId;//服务对象id
    private String seroLabelType;//所属服务类型 3服务人群 4经济类型
    private String seroFwType;//所属服务类型 3:1普通人群 2儿童(0-6岁) 3孕产妇 4老年人 5高血压 6糖尿病 7严重精神障碍
                                // 8结核病 9残疾人 99未知 4:1一般人口 2建档立卡贫困人口 3低保户 4特困户（五保户）5计生特殊家庭
    private String seroName;//服务对象名称
    private String seroValue;//服务对象编号
    private String seroState;//是否设为基本公共卫生服务对象 0否 1是

    //服务内容
    private List<WebServePkVo> pkVos;

    public String getSergValue() {
        return sergValue;
    }

    public void setSergValue(String sergValue) {
        this.sergValue = sergValue;
    }

    public String getSergGroupFee() {
        return sergGroupFee;
    }

    public void setSergGroupFee(String sergGroupFee) {
        this.sergGroupFee = sergGroupFee;
    }

    public String getSeroLabelType() {
        return seroLabelType;
    }

    public void setSeroLabelType(String seroLabelType) {
        this.seroLabelType = seroLabelType;
    }

    public String getSeroFwType() {
        return seroFwType;
    }

    public void setSeroFwType(String seroFwType) {
        this.seroFwType = seroFwType;
    }

    public String getSeroName() {
        return seroName;
    }

    public void setSeroName(String seroName) {
        this.seroName = seroName;
    }

    public String getSeroValue() {
        return seroValue;
    }

    public void setSeroValue(String seroValue) {
        this.seroValue = seroValue;
    }

    public String getSeroState() {
        return seroState;
    }

    public void setSeroState(String seroState) {
        this.seroState = seroState;
    }

    public List<WebServePkVo> getPkVos() {
        return pkVos;
    }

    public void setPkVos(List<WebServePkVo> pkVos) {
        this.pkVos = pkVos;
    }
}
