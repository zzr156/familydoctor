package com.ylz.bizDo.assessment.vo;

import java.math.BigInteger;

/**
 * 考核统计Vo
 *
 * @author mxx
 * @date 2018年9月29日
 */
public class AssessmentCountVo {

    private String signAreaCode;// 签约区域
    private BigInteger signNum;// 签约人数
    private BigInteger finishNum;// 考核完成的人数
    private String rating;// 评级名称
    private BigInteger ratingNum;// 评级数量


    public String getSignAreaCode() {
        return signAreaCode;
    }

    public void setSignAreaCode(String signAreaCode) {
        this.signAreaCode = signAreaCode;
    }

    public BigInteger getSignNum() {
        return signNum;
    }

    public void setSignNum(BigInteger signNum) {
        this.signNum = signNum;
    }

    public BigInteger getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(BigInteger finishNum) {
        this.finishNum = finishNum;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public BigInteger getRatingNum() {
        return ratingNum;
    }

    public void setRatingNum(BigInteger ratingNum) {
        this.ratingNum = ratingNum;
    }
}
