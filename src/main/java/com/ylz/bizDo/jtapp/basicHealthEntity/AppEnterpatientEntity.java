package com.ylz.bizDo.jtapp.basicHealthEntity;


/**
 * Created by zzl on 2018/6/29.
 */
public class AppEnterpatientEntity {
    private String df_id;//居民档案号
    private String name;//居民姓名
    private String idCard;//社保卡
    private String cdate;//建档日
    private String idcardno;//身份证号
    private String telphone;//联系电话
    private String birthday;//出生日期(格式：yyyymmdd)
    private String sex;//性别
    private String ref_ci_id;//社区号
    private String age;//年龄
    private String ci_id;//慢性疾病患者个人编号
    private String isgxy;//是否高血压
    private String istnb;//是否糖尿病
    private String islnr;//是老年人
    private String sfyxda;//是否有效档案
    private String ref_cjid;//居委会id
    private String jname;//居委会名称
    private String address;//常住地址(合地址)
    private String adress_pro;//常住地址：省（自治区、直辖市）
    private String adress_city;//常住地：市（地区）
    private String adress_county;//常住地址：县（区）
    private String adress_rural;//常住地址：乡（镇、街道办事处）
    private String adress_village;//常住地址：村（街、路、弄）
    private String adrss_hnumber;//常住地址：门牌号码
    private String czzgbx;//医疗费用支付方式--城镇职工基本医疗保险		0否  1是
    private String czjmbx;//医疗费用支付方式--城镇居民基本医疗保险		0否  1是
    private String rhzyl;//医疗费用支付方式--新型农村合作医疗		0否  1是
    private String pkjz;//医疗费用支付方式-贫困救助		0否  1是
    private String syylbx;//医疗费用支付方式--商业医疗保险		0否  1是
    private String qgf;//医疗费用支付方式--全公费
    private String qzf;//医疗费用支付方式--全自费		0否  1是
    private String qtfs00;//医疗费用支付方式-其他方式

    private AppEconomicsEntity JjType;//经济类型
    private AppDrPatientFwEntity persGroup;//服务人群
    private String dreason;//死亡原因

    public String getDf_id() {
        return df_id;
    }

    public void setDf_id(String df_id) {
        this.df_id = df_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRef_ci_id() {
        return ref_ci_id;
    }

    public void setRef_ci_id(String ref_ci_id) {
        this.ref_ci_id = ref_ci_id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCi_id() {
        return ci_id;
    }

    public void setCi_id(String ci_id) {
        this.ci_id = ci_id;
    }

    public String getIsgxy() {
        return isgxy;
    }

    public void setIsgxy(String isgxy) {
        this.isgxy = isgxy;
    }

    public String getIstnb() {
        return istnb;
    }

    public void setIstnb(String istnb) {
        this.istnb = istnb;
    }

    public String getIslnr() {
        return islnr;
    }

    public void setIslnr(String islnr) {
        this.islnr = islnr;
    }

    public String getSfyxda() {
        return sfyxda;
    }

    public void setSfyxda(String sfyxda) {
        this.sfyxda = sfyxda;
    }

    public String getRef_cjid() {
        return ref_cjid;
    }

    public void setRef_cjid(String ref_cjid) {
        this.ref_cjid = ref_cjid;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdress_pro() {
        return adress_pro;
    }

    public void setAdress_pro(String adress_pro) {
        this.adress_pro = adress_pro;
    }

    public String getAdress_city() {
        return adress_city;
    }

    public void setAdress_city(String adress_city) {
        this.adress_city = adress_city;
    }

    public String getAdress_county() {
        return adress_county;
    }

    public void setAdress_county(String adress_county) {
        this.adress_county = adress_county;
    }

    public String getAdress_rural() {
        return adress_rural;
    }

    public void setAdress_rural(String adress_rural) {
        this.adress_rural = adress_rural;
    }

    public String getAdress_village() {
        return adress_village;
    }

    public void setAdress_village(String adress_village) {
        this.adress_village = adress_village;
    }

    public String getAdrss_hnumber() {
        return adrss_hnumber;
    }

    public void setAdrss_hnumber(String adrss_hnumber) {
        this.adrss_hnumber = adrss_hnumber;
    }

    public String getCzzgbx() {
        return czzgbx;
    }

    public void setCzzgbx(String czzgbx) {
        this.czzgbx = czzgbx;
    }

    public String getCzjmbx() {
        return czjmbx;
    }

    public void setCzjmbx(String czjmbx) {
        this.czjmbx = czjmbx;
    }

    public String getRhzyl() {
        return rhzyl;
    }

    public void setRhzyl(String rhzyl) {
        this.rhzyl = rhzyl;
    }

    public String getPkjz() {
        return pkjz;
    }

    public void setPkjz(String pkjz) {
        this.pkjz = pkjz;
    }

    public String getSyylbx() {
        return syylbx;
    }

    public void setSyylbx(String syylbx) {
        this.syylbx = syylbx;
    }

    public String getQgf() {
        return qgf;
    }

    public void setQgf(String qgf) {
        this.qgf = qgf;
    }

    public String getQzf() {
        return qzf;
    }

    public void setQzf(String qzf) {
        this.qzf = qzf;
    }

    public String getQtfs00() {
        return qtfs00;
    }

    public void setQtfs00(String qtfs00) {
        this.qtfs00 = qtfs00;
    }

    public AppEconomicsEntity getJjType() {
        return JjType;
    }

    public void setJjType(AppEconomicsEntity jjType) {
        JjType = jjType;
    }

    public AppDrPatientFwEntity getPersGroup() {
        return persGroup;
    }

    public void setPersGroup(AppDrPatientFwEntity persGroup) {
        this.persGroup = persGroup;
    }

    public String getDreason() {
        return dreason;
    }

    public void setDreason(String dreason) {
        this.dreason = dreason;
    }
}
