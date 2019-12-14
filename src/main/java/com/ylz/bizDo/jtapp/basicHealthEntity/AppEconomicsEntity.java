package com.ylz.bizDo.jtapp.basicHealthEntity;

/**
 * Created by zzl on 2018/6/29.
 */
public class AppEconomicsEntity {
    private String isybrk;//是否一般人口（0-否，1-是）
    private String isjdlkpkrk;//是否建档立卡贫困人口（0-否，1-是）
    private String isdbh;//是否低保户（0-否，1-是）
    private String istkh;//是否特困户（五保户）（0-否，1-是）
    private String isjstsjt;//是否计生特殊家庭（0-否，1-是）

    public String getIsybrk() {
        return isybrk;
    }

    public void setIsybrk(String isybrk) {
        this.isybrk = isybrk;
    }

    public String getIsjdlkpkrk() {
        return isjdlkpkrk;
    }

    public void setIsjdlkpkrk(String isjdlkpkrk) {
        this.isjdlkpkrk = isjdlkpkrk;
    }

    public String getIsdbh() {
        return isdbh;
    }

    public void setIsdbh(String isdbh) {
        this.isdbh = isdbh;
    }

    public String getIstkh() {
        return istkh;
    }

    public void setIstkh(String istkh) {
        this.istkh = istkh;
    }

    public String getIsjstsjt() {
        return isjstsjt;
    }

    public void setIsjstsjt(String isjstsjt) {
        this.isjstsjt = isjstsjt;
    }
}
