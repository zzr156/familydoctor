package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * Created by asus on 2017/10/25.
 */
public class HomeServiceItemsQvo {
    private String idno;
    private String urlType;//1.福州,2泉州,3漳州,4.莆田,5.南平,6.三明,7.测试
    private String xm0000;//项目（1为产后，2为产后42天）
    private String orgId;//机构id
    private String type;//类型默认 1:判断档案是否本机构管理；2:获取档案信息；3:判断是何种慢病；0:获取1、2、3全部信息
    private String patientId;//居民主键

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getXm0000() {
        return xm0000;
    }

    public void setXm0000(String xm0000) {
        this.xm0000 = xm0000;
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

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
