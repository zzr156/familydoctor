package com.ylz.bizDo.jtapp.basicHealthEntity.findFy;

/**
 * Created by zzl on 2017/11/2.
 */
public class Yyqk {
    private String ywbh_id; // 药物编号
    private String ywmc; // 药物名称
    private String ywyf; // 用法(1-qod,2-prn,3-qn,4-qd,5-bid,6-tid,7-qid,其他任意值-请选择)
    private String ywyl; // 用量
    private String yysj; // 用药时间
    private String fyycx; // 服药依从性(1-规律,2-间断,其他任意值-不服药)
    private String ref_sf_id; // 随访编号
    private String yyid00; // 机构ID
    private String jktjcs; // 健康体检次数
    private String df_id; // 居民编号
    private String sfmb; // 是否模板

    public String getYwbh_id() {
        return ywbh_id;
    }

    public void setYwbh_id(String ywbh_id) {
        this.ywbh_id = ywbh_id;
    }

    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc;
    }

    public String getYwyf() {
        return ywyf;
    }

    public void setYwyf(String ywyf) {
        this.ywyf = ywyf;
    }

    public String getYwyl() {
        return ywyl;
    }

    public void setYwyl(String ywyl) {
        this.ywyl = ywyl;
    }

    public String getYysj() {
        return yysj;
    }

    public void setYysj(String yysj) {
        this.yysj = yysj;
    }

    public String getFyycx() {
        return fyycx;
    }

    public void setFyycx(String fyycx) {
        this.fyycx = fyycx;
    }

    public String getRef_sf_id() {
        return ref_sf_id;
    }

    public void setRef_sf_id(String ref_sf_id) {
        this.ref_sf_id = ref_sf_id;
    }

    public String getYyid00() {
        return yyid00;
    }

    public void setYyid00(String yyid00) {
        this.yyid00 = yyid00;
    }

    public String getJktjcs() {
        return jktjcs;
    }

    public void setJktjcs(String jktjcs) {
        this.jktjcs = jktjcs;
    }

    public String getDf_id() {
        return df_id;
    }

    public void setDf_id(String df_id) {
        this.df_id = df_id;
    }

    public String getSfmb() {
        return sfmb;
    }

    public void setSfmb(String sfmb) {
        this.sfmb = sfmb;
    }
}
