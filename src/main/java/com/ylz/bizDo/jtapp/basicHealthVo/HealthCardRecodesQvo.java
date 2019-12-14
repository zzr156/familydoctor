package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * Created by asus on 2017/7/3.
 */
public class HealthCardRecodesQvo {
    private String jgid;//机构id
    private String jwhid;//居委会id
    private String xzbh;//乡镇编号
    private String name;//居民姓名
    private String sex;//居民性别
    private String birthday;//出生日期
    private String doctor;//责任医生
    private String investigators;//调查员
    private String creator;//建档人
    private String jdrq;//建档日期
    private String idcardno;//居民身份证
    private String urlType;//1.福州,2泉州,3漳州,4.莆田,5.南平,6.三明,7.测试

    public String getJgid() {
        return jgid;
    }

    public void setJgid(String jgid) {
        this.jgid = jgid;
    }

    public String getJwhid() {
        return jwhid;
    }

    public void setJwhid(String jwhid) {
        this.jwhid = jwhid;
    }

    public String getXzbh() {
        return xzbh;
    }

    public void setXzbh(String xzbh) {
        this.xzbh = xzbh;
    }

    public String getJdrq() {
        return jdrq;
    }

    public void setJdrq(String jdrq) {
        this.jdrq = jdrq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getInvestigators() {
        return investigators;
    }

    public void setInvestigators(String investigators) {
        this.investigators = investigators;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }
}
