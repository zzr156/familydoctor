package com.ylz.bizDo.jtapp.commonEntity;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 血压统计返回实体
 * Created by lintingjie on 2017/6/27.
 */
public class AppPressureCountEntity {

    private BigInteger count;//总测量次数
    private BigInteger high;//过高次数
    private BigInteger low;//过低次数
    private BigInteger normal;//正常次数
    private Integer maxsys;//收缩压最大值
    private Integer minsys;//收缩压最小值
    private Integer maxdia;//舒张压最大值
    private Integer mindia;//舒张压最小值
    private Integer maxpul;//心率最大值
    private Integer minpul;//心率最小值
    private BigDecimal avgsys;//收缩压平均值
    private BigDecimal avgdia;//舒张压平均值
    private BigDecimal avgpul;//心率平均值

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
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
        return count.subtract(high).subtract(low);
    }

    public void setNormal(BigInteger normal) {
        this.normal = normal;
    }

    public Integer getMaxsys() {
        return maxsys;
    }

    public void setMaxsys(Integer maxsys) {
        this.maxsys = maxsys;
    }

    public Integer getMinsys() {
        return minsys;
    }

    public void setMinsys(Integer minsys) {
        this.minsys = minsys;
    }

    public Integer getMaxdia() {
        return maxdia;
    }

    public void setMaxdia(Integer maxdia) {
        this.maxdia = maxdia;
    }

    public Integer getMindia() {
        return mindia;
    }

    public void setMindia(Integer mindia) {
        this.mindia = mindia;
    }

    public Integer getMaxpul() {
        return maxpul;
    }

    public void setMaxpul(Integer maxpul) {
        this.maxpul = maxpul;
    }

    public Integer getMinpul() {
        return minpul;
    }

    public void setMinpul(Integer minpul) {
        this.minpul = minpul;
    }

    public BigDecimal getAvgsys() {
        return avgsys;
    }

    public void setAvgsys(BigDecimal avgsys) {
        this.avgsys = avgsys;
    }

    public BigDecimal getAvgdia() {
        return avgdia;
    }

    public void setAvgdia(BigDecimal avgdia) {
        this.avgdia = avgdia;
    }

    public BigDecimal getAvgpul() {
        return avgpul;
    }

    public void setAvgpul(BigDecimal avgpul) {
        this.avgpul = avgpul;
    }
}
