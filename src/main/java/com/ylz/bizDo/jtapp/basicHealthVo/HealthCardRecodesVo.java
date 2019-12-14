package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * Created by zzl on 2017/7/21.
 */
public class HealthCardRecodesVo {
    private String idNo;//身份证
    private String orgId;//机构主键
    private String type;//类型默认 1:判断档案是否本机构管理；2:获取档案信息；3:判断是何种慢病；0:获取1、2、3全部信息
    private String urlType;//1.福州,2泉州,3漳州,4.莆田,5.南平,6.三明,7.测试
    private String df_id;

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getDf_id() {
        return df_id;
    }

    public void setDf_id(String df_id) {
        this.df_id = df_id;
    }
}
