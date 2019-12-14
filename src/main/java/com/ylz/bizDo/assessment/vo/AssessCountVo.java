package com.ylz.bizDo.assessment.vo;

/**
 * 考核统计Vo
 *
 * @author mxx
 * @date 2018年9月29日
 */
public class AssessCountVo {

    private int signNum;// 签约人数
    private int assessNum;// 已考核的人数
    private int notAssessNum;// 未考核的人数
    private int finishNum;// 已考核人数（兼容旧版，不要删除）
    private int notFinishNum;// 未考核人数（兼容旧版，不要删除）

    private int excellentNum;// 考核评级为优的人数
    private double excellentRate;// 考核评级为优的百分比
    private int goodNum;// 考核评级为良的人数
    private double goodRate;// 考核评级为良的百分比
    private int qualifiedNum;// 考核评级为合格的人数
    private double qualifiedRate;// 考核评级为合格的百分比
    private int notQualifiedNum;// 考核评级为不合格的人数
    private double notQualifiedRate;// 考核评级为不合格的百分比


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

    public int getNotAssessNum() {
        return notAssessNum;
    }

    public void setNotAssessNum(int notAssessNum) {
        this.notAssessNum = notAssessNum;
    }

    public int getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(int finishNum) {
        this.finishNum = finishNum;
    }

    public int getNotFinishNum() {
        return notFinishNum;
    }

    public void setNotFinishNum(int notFinishNum) {
        this.notFinishNum = notFinishNum;
    }

    public int getExcellentNum() {
        return excellentNum;
    }

    public void setExcellentNum(int excellentNum) {
        this.excellentNum = excellentNum;
    }

    public double getExcellentRate() {
        return excellentRate;
    }

    public void setExcellentRate(double excellentRate) {
        this.excellentRate = excellentRate;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public double getGoodRate() {
        return goodRate;
    }

    public void setGoodRate(double goodRate) {
        this.goodRate = goodRate;
    }

    public int getQualifiedNum() {
        return qualifiedNum;
    }

    public void setQualifiedNum(int qualifiedNum) {
        this.qualifiedNum = qualifiedNum;
    }

    public double getQualifiedRate() {
        return qualifiedRate;
    }

    public void setQualifiedRate(double qualifiedRate) {
        this.qualifiedRate = qualifiedRate;
    }

    public int getNotQualifiedNum() {
        return notQualifiedNum;
    }

    public void setNotQualifiedNum(int notQualifiedNum) {
        this.notQualifiedNum = notQualifiedNum;
    }

    public double getNotQualifiedRate() {
        return notQualifiedRate;
    }

    public void setNotQualifiedRate(double notQualifiedRate) {
        this.notQualifiedRate = notQualifiedRate;
    }
}
