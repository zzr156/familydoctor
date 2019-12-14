package com.ylz.bizDo.jtapp.basicHealthEntity.findFy;

import java.util.List;

/**
 * 重性精神疾病人员信息
 * Created by zzl on 2017/10/27.
 */
public class Mb_zxjsjbDTO {
    private String id0000;//编号
    private String df_id;//居民编号
    private String jhrname;//监护人姓名
    private String rel;//与患者关系
    private String jhradd;//监护人住址
    private String jhrtel;// 监护人电话
    private String jwhname;//辖区村（居）委会联系人
    private String jwhtel;//辖区村（居）委会联系人电话
    private String ccfbtime;//初次发病时间
    private String jwmzzlqk;//既往门诊治疗情况（1---未治，2----间断门诊治疗，3----连续门诊治疗）
    private String jwzyzlqk;//既往住院治疗情况
    private String zjzd;//最近诊断
    private String qzyy;//确诊医院
    private String qztime;//确诊日期
    private String zjzlxg;//最近一次治疗效果（1-痊愈，2--好转，3--无变化，4--加重）
    private String gsqk;//关锁情况（1---无关锁，2---关锁，3---关锁已解除）
    private String tbtime;//填表日期
    private String doctor;//医生
    private String wyysxm;//外院医生姓名
    private String jwszz0;//既往主要症状
    private String qtzz00;//其他症状（既往主要症状）
    private String qdzscs;//轻度滋事（患者对家庭社会的影响）
    private String zhscs0;//肇事（患者对家庭社会的影响）
    private String zhhcs0;//肇祸（患者对家庭社会的影响）
    private String zscs00;//自伤（患者对家庭社会的影响）
    private String zswscs;//自杀未遂（患者对家庭社会的影响）
    private String ssid00;//社会保障卡号(智业上传标志)
    private String zqty00;// 知情同意（1同意参加管理 0不同意参加管理）
    private String zqtysj;//知情同意时间
    private String sczlsj;//首次抗精神病药治疗时间
    private String jjzk00;//经济状况（1贫困，在当地贫困线标准以下 2非贫困 3不详）
    private String zkysyj;//专科医生的意见
    private String isdel0;// 是否删除1未删除2已删除
    private List<Zxjsjb> t_zxjsjb_sfDTO;//随访信息
    private String ysxm00;
    private String xm0000;

    public String getId0000() {
        return id0000;
    }

    public void setId0000(String id0000) {
        this.id0000 = id0000;
    }

    public String getDf_id() {
        return df_id;
    }

    public void setDf_id(String df_id) {
        this.df_id = df_id;
    }

    public String getJhrname() {
        return jhrname;
    }

    public void setJhrname(String jhrname) {
        this.jhrname = jhrname;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getJhradd() {
        return jhradd;
    }

    public void setJhradd(String jhradd) {
        this.jhradd = jhradd;
    }

    public String getJhrtel() {
        return jhrtel;
    }

    public void setJhrtel(String jhrtel) {
        this.jhrtel = jhrtel;
    }

    public String getJwhname() {
        return jwhname;
    }

    public void setJwhname(String jwhname) {
        this.jwhname = jwhname;
    }

    public String getJwhtel() {
        return jwhtel;
    }

    public void setJwhtel(String jwhtel) {
        this.jwhtel = jwhtel;
    }

    public String getCcfbtime() {
        return ccfbtime;
    }

    public void setCcfbtime(String ccfbtime) {
        this.ccfbtime = ccfbtime;
    }

    public String getJwmzzlqk() {
        return jwmzzlqk;
    }

    public void setJwmzzlqk(String jwmzzlqk) {
        this.jwmzzlqk = jwmzzlqk;
    }

    public String getJwzyzlqk() {
        return jwzyzlqk;
    }

    public void setJwzyzlqk(String jwzyzlqk) {
        this.jwzyzlqk = jwzyzlqk;
    }

    public String getZjzd() {
        return zjzd;
    }

    public void setZjzd(String zjzd) {
        this.zjzd = zjzd;
    }

    public String getQzyy() {
        return qzyy;
    }

    public void setQzyy(String qzyy) {
        this.qzyy = qzyy;
    }

    public String getQztime() {
        return qztime;
    }

    public void setQztime(String qztime) {
        this.qztime = qztime;
    }

    public String getZjzlxg() {
        return zjzlxg;
    }

    public void setZjzlxg(String zjzlxg) {
        this.zjzlxg = zjzlxg;
    }

    public String getGsqk() {
        return gsqk;
    }

    public void setGsqk(String gsqk) {
        this.gsqk = gsqk;
    }

    public String getTbtime() {
        return tbtime;
    }

    public void setTbtime(String tbtime) {
        this.tbtime = tbtime;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getWyysxm() {
        return wyysxm;
    }

    public void setWyysxm(String wyysxm) {
        this.wyysxm = wyysxm;
    }

    public String getJwszz0() {
        return jwszz0;
    }

    public void setJwszz0(String jwszz0) {
        this.jwszz0 = jwszz0;
    }

    public String getQtzz00() {
        return qtzz00;
    }

    public void setQtzz00(String qtzz00) {
        this.qtzz00 = qtzz00;
    }

    public String getQdzscs() {
        return qdzscs;
    }

    public void setQdzscs(String qdzscs) {
        this.qdzscs = qdzscs;
    }

    public String getZhscs0() {
        return zhscs0;
    }

    public void setZhscs0(String zhscs0) {
        this.zhscs0 = zhscs0;
    }

    public String getZhhcs0() {
        return zhhcs0;
    }

    public void setZhhcs0(String zhhcs0) {
        this.zhhcs0 = zhhcs0;
    }

    public String getZscs00() {
        return zscs00;
    }

    public void setZscs00(String zscs00) {
        this.zscs00 = zscs00;
    }

    public String getZswscs() {
        return zswscs;
    }

    public void setZswscs(String zswscs) {
        this.zswscs = zswscs;
    }

    public String getSsid00() {
        return ssid00;
    }

    public void setSsid00(String ssid00) {
        this.ssid00 = ssid00;
    }

    public String getZqty00() {
        return zqty00;
    }

    public void setZqty00(String zqty00) {
        this.zqty00 = zqty00;
    }

    public String getZqtysj() {
        return zqtysj;
    }

    public void setZqtysj(String zqtysj) {
        this.zqtysj = zqtysj;
    }

    public String getSczlsj() {
        return sczlsj;
    }

    public void setSczlsj(String sczlsj) {
        this.sczlsj = sczlsj;
    }

    public String getJjzk00() {
        return jjzk00;
    }

    public void setJjzk00(String jjzk00) {
        this.jjzk00 = jjzk00;
    }

    public String getZkysyj() {
        return zkysyj;
    }

    public void setZkysyj(String zkysyj) {
        this.zkysyj = zkysyj;
    }

    public String getIsdel0() {
        return isdel0;
    }

    public void setIsdel0(String isdel0) {
        this.isdel0 = isdel0;
    }

    public List<Zxjsjb> getT_zxjsjb_sfDTO() {
        return t_zxjsjb_sfDTO;
    }

    public void setT_zxjsjb_sfDTO(List<Zxjsjb> t_zxjsjb_sfDTO) {
        this.t_zxjsjb_sfDTO = t_zxjsjb_sfDTO;
    }

    public String getYsxm00() {
        return ysxm00;
    }

    public void setYsxm00(String ysxm00) {
        this.ysxm00 = ysxm00;
    }

    public String getXm0000() {
        return xm0000;
    }

    public void setXm0000(String xm0000) {
        this.xm0000 = xm0000;
    }
}
