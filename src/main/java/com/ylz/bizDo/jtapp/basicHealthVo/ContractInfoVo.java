package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * 签约数据
 *
 * Created by lintingjie on 2017/7/20.
 */
public class ContractInfoVo {

    private String address;
    private String df_id;//居民健康档案号 df_id
    private String doctor;//签约医生编号 乙方
    private String doctorId;//相关社区
    private String dqsj;//到期时间 dqsj
    private String f_id;//家庭健康档案号 甲方 f_id
    private String hname;//户主 甲方 hname
    private String linkman;//签约联系人 甲方
    private String mobile;//1表示移动端签约数据
    private String qyfs;//签约方式 1团队 2医生-乙方
    private String qysj;//签约时间
    private String qyzt;//签约状态
    private String service;//服务
    private String tel;//联系电话-乙方
    private String yyid;//机构id
    private String urlType;//1.福州,2泉州,3漳州,4.莆田,5.南平,6.三明,7.测试


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDf_id() {
        return df_id;
    }

    public void setDf_id(String df_id) {
        this.df_id = df_id;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDqsj() {
        return dqsj;
    }

    public void setDqsj(String dqsj) {
        this.dqsj = dqsj;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQyfs() {
        return qyfs;
    }

    public void setQyfs(String qyfs) {
        this.qyfs = qyfs;
    }

    public String getQysj() {
        return qysj;
    }

    public void setQysj(String qysj) {
        this.qysj = qysj;
    }

    public String getQyzt() {
        return qyzt;
    }

    public void setQyzt(String qyzt) {
        this.qyzt = qyzt;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getYyid() {
        return yyid;
    }

    public void setYyid(String yyid) {
        this.yyid = yyid;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }
}
