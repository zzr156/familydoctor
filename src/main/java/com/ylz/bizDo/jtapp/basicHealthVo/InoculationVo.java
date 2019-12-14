package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * Created by asus on 2017/07/21.
 */
public class InoculationVo {
    private String mykh;//免疫卡号
    private String name;//姓名
    private String ymbm;//疫苗编码
    private String jzzc;//疫苗针次
    private String jzrq;//接种日期
    private String jzMzId;//接种门诊主键
    private String jzYyRq;//接种预约日期
    private String jzYyxx;//接种预约信息 :1:全天,2:上午,3:下午
    private String yySjd;//预约时间段ID
    private String sfMf;//是否免费
    private String yyId;//预约主键
    private String ymlx;//疫苗类型
    private String childBirth;//儿童出生日期


    public String getMykh() {
        return mykh;
    }

    public void setMykh(String mykh) {
        this.mykh = mykh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYmbm() {
        return ymbm;
    }

    public void setYmbm(String ymbm) {
        this.ymbm = ymbm;
    }

    public String getJzrq() {
        return jzrq;
    }

    public void setJzrq(String jzrq) {
        this.jzrq = jzrq;
    }

    public String getJzzc() {
        return jzzc;
    }

    public void setJzzc(String jzzc) {
        this.jzzc = jzzc;
    }

    public String getJzMzId() {
        return jzMzId;
    }

    public void setJzMzId(String jzMzId) {
        this.jzMzId = jzMzId;
    }

    public String getJzYyRq() {
        return jzYyRq;
    }

    public void setJzYyRq(String jzYyRq) {
        this.jzYyRq = jzYyRq;
    }

    public String getJzYyxx() {
        return jzYyxx;
    }

    public void setJzYyxx(String jzYyxx) {
        this.jzYyxx = jzYyxx;
    }

    public String getYySjd() {
        return yySjd;
    }

    public void setYySjd(String yySjd) {
        this.yySjd = yySjd;
    }

    public String getSfMf() {
        return sfMf;
    }

    public void setSfMf(String sfMf) {
        this.sfMf = sfMf;
    }

    public String getYyId() {
        return yyId;
    }

    public void setYyId(String yyId) {
        this.yyId = yyId;
    }

    public String getYmlx() {
        return ymlx;
    }

    public void setYmlx(String ymlx) {
        this.ymlx = ymlx;
    }

    public String getChildBirth() {
        return childBirth;
    }

    public void setChildBirth(String childBirth) {
        this.childBirth = childBirth;
    }
}
