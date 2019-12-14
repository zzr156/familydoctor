package com.ylz.bizDo.jtapp.drEntity;

import java.math.BigInteger;

/**
 * Created by asus on 2017/6/24.
 */
public class AppDrCountEntity {
    private BigInteger ptrq;// 普通人群
    private BigInteger mbrq;//慢病人群
    private BigInteger lnrq;//老年人群
    private BigInteger zqyrs;//总签约人数
    private BigInteger dshrs;//待审核人数
    private BigInteger jstsjtrq;//计生特殊家庭人群
    private BigInteger jdlkrcpkrq;//建档立卡农村贫困人群
    private String sftz;//是否团长
    private BigInteger tkh;//特困户人群
    private BigInteger dbh;//低保户人群
    public BigInteger getPtrq() {
        return ptrq;
    }

    public void setPtrq(BigInteger ptrq) {
        this.ptrq = ptrq;
    }

    public BigInteger getMbrq() {
        return mbrq;
    }

    public void setMbrq(BigInteger mbrq) {
        this.mbrq = mbrq;
    }

    public BigInteger getLnrq() {
        return lnrq;
    }

    public void setLnrq(BigInteger lnrq) {
        this.lnrq = lnrq;
    }

    public BigInteger getZqyrs() {
        return zqyrs;
    }

    public void setZqyrs(BigInteger zqyrs) {
        this.zqyrs = zqyrs;
    }

    public BigInteger getDshrs() {
        return dshrs;
    }

    public void setDshrs(BigInteger dshrs) {
        this.dshrs = dshrs;
    }

    public String getSftz() {
        return sftz;
    }

    public void setSftz(String sftz) {
        this.sftz = sftz;
    }

    public BigInteger getJstsjtrq() {
        return jstsjtrq;
    }

    public void setJstsjtrq(BigInteger jstsjtrq) {
        this.jstsjtrq = jstsjtrq;
    }

    public BigInteger getJdlkrcpkrq() {
        return jdlkrcpkrq;
    }

    public void setJdlkrcpkrq(BigInteger jdlkrcpkrq) {
        this.jdlkrcpkrq = jdlkrcpkrq;
    }

    public BigInteger getTkh() {
        return tkh;
    }

    public void setTkh(BigInteger tkh) {
        this.tkh = tkh;
    }

    public BigInteger getDbh() {
        return dbh;
    }

    public void setDbh(BigInteger dbh) {
        this.dbh = dbh;
    }
}
