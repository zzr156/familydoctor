package com.ylz.bizDo.jtapp.commonEntity;

import java.math.BigInteger;

/**
 * 血糖统计返回实体
 * Created by lintingjie on 2017/6/26.
 */
public class AppGluCountEntity {

    private Double max;//最大值
    private Double min;//最小值
    private Double avg1;//空腹平均值
    private Double avg2;//早餐后平均值
    private Double avg3;//午餐前平均值
    private Double avg4;//午餐后平均值
    private Double avg5;//晚餐前平均值
    private Double avg6;//晚餐后平均值
    private Double avg7;//睡前平均值
    private Double avg8;//凌晨平均值
    private Double avg9;//随机平均值
    private BigInteger sum;//总测量次数
    private BigInteger high;//过高次数
    private BigInteger low;//过低次数
    private BigInteger normal;//正常次数


    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getAvg1() {
        return avg1;
    }

    public void setAvg1(Double avg1) {
        this.avg1 = avg1;
    }

    public Double getAvg2() {
        return avg2;
    }

    public void setAvg2(Double avg2) {
        this.avg2 = avg2;
    }

    public Double getAvg3() {
        return avg3;
    }

    public void setAvg3(Double avg3) {
        this.avg3 = avg3;
    }

    public Double getAvg4() {
        return avg4;
    }

    public void setAvg4(Double avg4) {
        this.avg4 = avg4;
    }

    public Double getAvg5() {
        return avg5;
    }

    public void setAvg5(Double avg5) {
        this.avg5 = avg5;
    }

    public Double getAvg6() {
        return avg6;
    }

    public void setAvg6(Double avg6) {
        this.avg6 = avg6;
    }

    public Double getAvg7() {
        return avg7;
    }

    public void setAvg7(Double avg7) {
        this.avg7 = avg7;
    }

    public Double getAvg8() {
        return avg8;
    }

    public void setAvg8(Double avg8) {
        this.avg8 = avg8;
    }

    public Double getAvg9() {
        return avg9;
    }

    public void setAvg9(Double avg9) {
        this.avg9 = avg9;
    }

    public BigInteger getSum() {
        return sum;
    }

    public void setSum(BigInteger sum) {
        this.sum = sum;
    }

    public BigInteger getHigh() {
        return high;
    }

    public void setHigh(BigInteger high) {
        this.high = high;
    }

    public BigInteger getLow() {
        return low;
    }

    public void setLow(BigInteger low) {
        this.low = low;
    }

    public BigInteger getNormal() {
        return sum.subtract(low).subtract(high);
    }

    public void setNormal(BigInteger normal) {
        this.normal = normal;
    }


}
