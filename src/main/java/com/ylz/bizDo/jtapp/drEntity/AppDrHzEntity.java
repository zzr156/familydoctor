package com.ylz.bizDo.jtapp.drEntity;

/**
 * 转诊患者信息初始化
 * Created by zzl on 2017/12/11.
 */
public class AppDrHzEntity {
    private String id;//主键
    private String name;//姓名
    private String age;//年龄
    private String sex;//性别
    private String fwrq;//服务类型
    private String fwrqColor;//服务类型颜色
    private String idNo;//身份证
    private String card;//医保卡号
    private String phone;//手机号
    private String address;//居住地

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFwrq() {
        return fwrq;
    }

    public void setFwrq(String fwrq) {
        this.fwrq = fwrq;
    }

    public String getFwrqColor() {
        return fwrqColor;
    }

    public void setFwrqColor(String fwrqColor) {
        this.fwrqColor = fwrqColor;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
