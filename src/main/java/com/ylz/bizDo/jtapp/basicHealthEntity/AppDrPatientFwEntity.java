package com.ylz.bizDo.jtapp.basicHealthEntity;

/**
 *  获取居民服务类型
 * Created by zzl on 2017/7/28.
 */
public class AppDrPatientFwEntity {
    private String is06child;//是否0-6岁儿童（0-否，1-是）
    private String islnr;//是否老年人（0-否，1-是）
    private String isycf;//是否孕产妇（0-否，1-是）
    private String isgxy;//是否高血压（0-否，1-是）
    private String istnb;//是否糖尿病（0-否，1-是）
    private String isjhb;//是否结核病（0-否，1-是）
    private String iscjr;//是否残疾人（0-否，1-是）
    private String iszxjsb;//是否重性精神病（0-否，1-是）
    private String comservice;//是否普通服务（0-否，1-是）

    public String getIs06child() {
        return is06child;
    }

    public void setIs06child(String is06child) {
        this.is06child = is06child;
    }

    public String getIslnr() {
        return islnr;
    }

    public void setIslnr(String islnr) {
        this.islnr = islnr;
    }

    public String getIsycf() {
        return isycf;
    }

    public void setIsycf(String isycf) {
        this.isycf = isycf;
    }

    public String getIsgxy() {
        return isgxy;
    }

    public void setIsgxy(String isgxy) {
        this.isgxy = isgxy;
    }

    public String getIstnb() {
        return istnb;
    }

    public void setIstnb(String istnb) {
        this.istnb = istnb;
    }

    public String getIsjhb() {
        return isjhb;
    }

    public void setIsjhb(String isjhb) {
        this.isjhb = isjhb;
    }

    public String getIscjr() {
        return iscjr;
    }

    public void setIscjr(String iscjr) {
        this.iscjr = iscjr;
    }

    public String getIszxjsb() {
        return iszxjsb;
    }

    public void setIszxjsb(String iszxjsb) {
        this.iszxjsb = iszxjsb;
    }

    public String getComservice() {
        return comservice;
    }

    public void setComservice(String comservice) {
        this.comservice = comservice;
    }
}
