package com.ylz.bizDo.assessment.vo;

/**
 * 考核报表Vo
 *
 * @author mxx
 * @date 2018年11月26日
 */
public class AssessReportVo {

    private String statisticObjCode;// 统计对象编码
    private String statisticObjName;// 统计对象名称
    private String statisticObjType;// 统计对象类型（1-地区，2-机构，3-团队）
    private int signNum;// 签约数量
    private int assessNum;// 已考核数量
    private int excellentNum;// 评级为优数量
    private int goodNum;// 评级为良数量
    private int qualifiedNum;// 评级为合格数量
    private int unQualifiedNum;// 评级为不合格数量
    private double subsidy;// 补贴金额量


    public String getStatisticObjCode() {
        return statisticObjCode;
    }

    public void setStatisticObjCode(String statisticObjCode) {
        this.statisticObjCode = statisticObjCode;
    }

    public String getStatisticObjName() {
        return statisticObjName;
    }

    public void setStatisticObjName(String statisticObjName) {
        this.statisticObjName = statisticObjName;
    }

    public String getStatisticObjType() {
        return statisticObjType;
    }

    public void setStatisticObjType(String statisticObjType) {
        this.statisticObjType = statisticObjType;
    }

    public int getSignNum() {
        return signNum;
    }

    public void setSignNum(int signNum) {
        this.signNum = signNum;
    }

    public int getAssessNum() {
        return assessNum;
    }

    public void setAssessNum(int assessNum) {
        this.assessNum = assessNum;
    }

    public int getExcellentNum() {
        return excellentNum;
    }

    public void setExcellentNum(int excellentNum) {
        this.excellentNum = excellentNum;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public int getQualifiedNum() {
        return qualifiedNum;
    }

    public void setQualifiedNum(int qualifiedNum) {
        this.qualifiedNum = qualifiedNum;
    }

    public int getUnQualifiedNum() {
        return unQualifiedNum;
    }

    public void setUnQualifiedNum(int unQualifiedNum) {
        this.unQualifiedNum = unQualifiedNum;
    }

    public double getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(double subsidy) {
        this.subsidy = subsidy;
    }
}
