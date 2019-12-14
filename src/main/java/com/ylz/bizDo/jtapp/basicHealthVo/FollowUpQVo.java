package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * 随访查询条件
 * @author WTJ
 * @date 2017年07月23日
 */
public class FollowUpQVo {

    private String ygbh00;  //员工编号
    private String df_id;   //居民健康档案号
    private String mxjbbz;  //慢性疾病编号（1-高血压3-糖尿病）（在4.7接口中，2代表糖尿病）
    private String sfid00;  //随访随访编号
    private String zzbhid;  //症状编号
    private String urlType;//请求地市  1.福州,2泉州,3漳州,4.莆田,5.南平,6.三明,7.测试

    public String getYgbh00() {
        return ygbh00;
    }

    public void setYgbh00(String ygbh00) {
        this.ygbh00 = ygbh00;
    }

    public String getMxjbbz() {
        return mxjbbz;
    }

    public String getSfid00() {
        return sfid00;
    }

    public void setSfid00(String sfid00) {
        this.sfid00 = sfid00;
    }

    public String getZzbhid() {
        return zzbhid;
    }

    public void setZzbhid(String zzbhid) {
        this.zzbhid = zzbhid;
    }

    public void setMxjbbz(String mxjbbz) {
        this.mxjbbz = mxjbbz;
    }

    public String getDf_id() {
        return df_id;
    }

    public void setDf_id(String df_id) {
        this.df_id = df_id;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }
}
