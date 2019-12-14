package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * Created by asus on 2017/10/25.
 */
public class JmjktjQvo {
    private String df_id;//居民档案号
    private String edate;//检查日期
    private String jktjcs;//体检次数
    private String jktj_ybzkid;//健康体检--一般状况ID
    private String urlType;//-请求地市[1.福州,2泉州,3漳州,4.莆田,5.南平,6.三明,7.测试](必填)
    private String sf_bhid;//随访编号
    private String ksrj00;//开始日期
    public String getDf_id() {
        return df_id;
    }

    public void setDf_id(String df_id) {
        this.df_id = df_id;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getJktjcs() {
        return jktjcs;
    }

    public void setJktjcs(String jktjcs) {
        this.jktjcs = jktjcs;
    }

    public String getJktj_ybzkid() {
        return jktj_ybzkid;
    }

    public void setJktj_ybzkid(String jktj_ybzkid) {
        this.jktj_ybzkid = jktj_ybzkid;
    }

    public String getSf_bhid() {
        return sf_bhid;
    }

    public void setSf_bhid(String sf_bhid) {
        this.sf_bhid = sf_bhid;
    }

    public String getKsrj00() {
        return ksrj00;
    }

    public void setKsrj00(String ksrj00) {
        this.ksrj00 = ksrj00;
    }
}
