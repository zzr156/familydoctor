package com.ylz.bizDo.jtapp.basicHealthEntity;

/**
 * Created by lintingjie on 2017/7/22.
 */
public class ResidentHealthFiles {
    private String df_id;//居民档案号
    private String name;//居民姓名
    private String workplace;//工作单位
    private String idcardno;//身份证号
    private String telphone;//联系电话
    private String adress_city;// 常住地址：市（地区）
    private String adress_county;//常住地址：县（区）
    private String adress_rural;//常住地址：乡（镇、街道办事处）
    private String adress_village;//常住地址：村（街、路、弄
    private String adrss_hnumber;//常住地址：门牌号码
    private String adress_pro;//常住地址：省（自治区、直辖市）
    private String sex;//性别:1男 2女 3未知性别
    private String folk;//民族
    private String mstatus;//婚姻状况:1未婚 2已婚 3丧偶 4离婚 5其他
    private String cdegree;//文化程度:1文盲或半文盲 2小学 3初中 4高中/技校/中专 5大学专科及以上 6不详
    private String workstatus;//职业状况(1农、林、牧、渔、水利业生产人员;2商业、服务业人员;3专业技术人员;4生产、运输设备操作人员及有关人员;5办事人员和有关人员;6国家机关、党群组织、企业、事业单位负责人;7军人;8不便分类的其他从业人员)
    private String sensitive;//过敏史
    private String bloodtype;//血型(1 O型;2 A型;3 B型;4 AB型;5 不详;)
    private String rprtype;//常住类型(1户籍 2非户籍)
    private String ishcpact;//是否签保健合同(0否  1是)
    private String ismove;//是否迁出(0否  1是)
    private String movedate;//迁出时间yyyymmdd
    private String isdead;//是否死亡(0否  1是)
    private String ddate;//死亡日期yyyymmdd
    private String creator;//建档人
    private String investigators;//调查员
    private String idate;//调查日期yyyymmdd
    private String ref_ci_id;//社区号
    private String ref_cjid;//居委会ID
    private String f_id;//家庭档案号
    private String r_id;//居民与户主关系见附录居民与户主关系）
    private String namecode;//姓名-标识对象代码
    private String telphonetype;//联系电话-类别(联系电话所属者类别的名称)
    private String rhxx;//RH血型(1阳性;2阴性;3不详)
    private String czzgbx;//医疗费用支付方式--城镇职工基本医疗保险(0否  1是)
    private String czjmbx;//医疗费用支付方式--城镇居民基本医疗保险(0否  1是)
    private String xrhzyl;//医疗费用支付方式--新型农村合作医疗(0否  1是)
    private String pkjz;//医疗费用支付方式-贫困救助(0否  1是)
    private String syylbx;//医疗费用支付方式--商业医疗保险(0否  1是)
    private String qgf;//医疗费用支付方式--全公费
    private String qzf;//医疗费用支付方式--全自费(0否  1是)
    private String qtfs00;//医疗费用支付方式-其他方式
    private String ycbs00;//遗传病史
    private String jwbsjb;//既往病史疾病
    private String jwbsss;//既往病史手术
    private String jwbsws;//既往病史外伤
    private String jwbssx;//既往病史输血
    private String jzsfq;//家族史父亲
    private String jzsmq;//家族史母亲
    private String jzsxm;//家族史兄妹
    private String jzszn;//家族史子女
    private String cjqk0;//残疾编号,以；号隔开（0：无残疾；7：视力残疾；8：听力残疾；9：言语残疾；10：智力残疾；11：肢体残疾；12：精神残疾）
    private String jwbsbh;//既往病史，疾病编号，以；号分隔()
    private String doctor;//责任医生
    private String jdrq00;//建档日期
    private String expose;//暴露史(取值有 化学品 毒物  射线 无)例如无，例如化学品、毒物、
    private String cfpfss;//厨房排风设施(1无;2油烟机;3换气扇;4烟囱)
    private String rllx;//燃料类型(1液化气;2煤;3天然气;4沼气;5柴火;6其他)
    private String ys;//饮水(1自来水;2经净化过滤的水;3井水;4河湖水;5塘水;6其他)
    private String cs;//厕所（1卫生厕所;2一格或二格粪池式;3马桶;4露天粪坑;5简易棚厕）
    private String qcl;//禽畜栏(1单设;2室内;3室外;4无)
    private String dasfwz;//档案是否完整
    private String yyidqc;//迁出医院yyid
    private String isdel0;//是否删除(0、删除1、未删除)
    private String sfyxda;//是否有效档案（0、无效1、有效）
    private String jzsj00;//是否有就诊记录(0、没有1、有)
    private String beizhu;//备注（原先的为纸质档案号）
    private String fh_id;//标识ID（不为空的时候填的是居民档案号，fh_id为空不对居民档案进行操作）
    private String yyid;//机构ID
    private String birthday;//出生日期yyyymmdd
    private String zrysxm;
    private String orgname;
    private String creatorname;//建档人姓名
    private String doctorname;//医生姓名
    private String investigatorsname;//调查员姓名
    private String jname;//居委会名称


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

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
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

    public String getAdress_pro() {
        return adress_pro;
    }

    public void setAdress_pro(String adress_pro) {
        this.adress_pro = adress_pro;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFolk() {
        return folk;
    }

    public void setFolk(String folk) {
        this.folk = folk;
    }

    public String getMstatus() {
        return mstatus;
    }

    public void setMstatus(String mstatus) {
        this.mstatus = mstatus;
    }

    public String getCdegree() {
        return cdegree;
    }

    public void setCdegree(String cdegree) {
        this.cdegree = cdegree;
    }

    public String getWorkstatus() {
        return workstatus;
    }

    public void setWorkstatus(String workstatus) {
        this.workstatus = workstatus;
    }

    public String getSensitive() {
        return sensitive;
    }

    public void setSensitive(String sensitive) {
        this.sensitive = sensitive;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }

    public String getRprtype() {
        return rprtype;
    }

    public void setRprtype(String rprtype) {
        this.rprtype = rprtype;
    }

    public String getIshcpact() {
        return ishcpact;
    }

    public void setIshcpact(String ishcpact) {
        this.ishcpact = ishcpact;
    }

    public String getIsmove() {
        return ismove;
    }

    public void setIsmove(String ismove) {
        this.ismove = ismove;
    }

    public String getMovedate() {
        return movedate;
    }

    public void setMovedate(String movedate) {
        this.movedate = movedate;
    }

    public String getIsdead() {
        return isdead;
    }

    public void setIsdead(String isdead) {
        this.isdead = isdead;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getInvestigators() {
        return investigators;
    }

    public void setInvestigators(String investigators) {
        this.investigators = investigators;
    }

    public String getIdate() {
        return idate;
    }

    public void setIdate(String idate) {
        this.idate = idate;
    }

    public String getRef_ci_id() {
        return ref_ci_id;
    }

    public void setRef_ci_id(String ref_ci_id) {
        this.ref_ci_id = ref_ci_id;
    }

    public String getRef_cjid() {
        return ref_cjid;
    }

    public void setRef_cjid(String ref_cjid) {
        this.ref_cjid = ref_cjid;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getR_id() {
        return r_id;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }

    public String getNamecode() {
        return namecode;
    }

    public void setNamecode(String namecode) {
        this.namecode = namecode;
    }

    public String getTelphonetype() {
        return telphonetype;
    }

    public void setTelphonetype(String telphonetype) {
        this.telphonetype = telphonetype;
    }

    public String getRhxx() {
        return rhxx;
    }

    public void setRhxx(String rhxx) {
        this.rhxx = rhxx;
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

    public String getXrhzyl() {
        return xrhzyl;
    }

    public void setXrhzyl(String xrhzyl) {
        this.xrhzyl = xrhzyl;
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

    public String getYcbs00() {
        return ycbs00;
    }

    public void setYcbs00(String ycbs00) {
        this.ycbs00 = ycbs00;
    }

    public String getJwbsjb() {
        return jwbsjb;
    }

    public void setJwbsjb(String jwbsjb) {
        this.jwbsjb = jwbsjb;
    }

    public String getJwbsss() {
        return jwbsss;
    }

    public void setJwbsss(String jwbsss) {
        this.jwbsss = jwbsss;
    }

    public String getJwbsws() {
        return jwbsws;
    }

    public void setJwbsws(String jwbsws) {
        this.jwbsws = jwbsws;
    }

    public String getJwbssx() {
        return jwbssx;
    }

    public void setJwbssx(String jwbssx) {
        this.jwbssx = jwbssx;
    }

    public String getJzsfq() {
        return jzsfq;
    }

    public void setJzsfq(String jzsfq) {
        this.jzsfq = jzsfq;
    }

    public String getJzsmq() {
        return jzsmq;
    }

    public void setJzsmq(String jzsmq) {
        this.jzsmq = jzsmq;
    }

    public String getJzsxm() {
        return jzsxm;
    }

    public void setJzsxm(String jzsxm) {
        this.jzsxm = jzsxm;
    }

    public String getJzszn() {
        return jzszn;
    }

    public void setJzszn(String jzszn) {
        this.jzszn = jzszn;
    }

    public String getCjqk0() {
        return cjqk0;
    }

    public void setCjqk0(String cjqk0) {
        this.cjqk0 = cjqk0;
    }

    public String getJwbsbh() {
        return jwbsbh;
    }

    public void setJwbsbh(String jwbsbh) {
        this.jwbsbh = jwbsbh;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getJdrq00() {
        return jdrq00;
    }

    public void setJdrq00(String jdrq00) {
        this.jdrq00 = jdrq00;
    }

    public String getExpose() {
        return expose;
    }

    public void setExpose(String expose) {
        this.expose = expose;
    }

    public String getCfpfss() {
        return cfpfss;
    }

    public void setCfpfss(String cfpfss) {
        this.cfpfss = cfpfss;
    }

    public String getRllx() {
        return rllx;
    }

    public void setRllx(String rllx) {
        this.rllx = rllx;
    }

    public String getYs() {
        return ys;
    }

    public void setYs(String ys) {
        this.ys = ys;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getQcl() {
        return qcl;
    }

    public void setQcl(String qcl) {
        this.qcl = qcl;
    }

    public String getDasfwz() {
        return dasfwz;
    }

    public void setDasfwz(String dasfwz) {
        this.dasfwz = dasfwz;
    }

    public String getYyidqc() {
        return yyidqc;
    }

    public void setYyidqc(String yyidqc) {
        this.yyidqc = yyidqc;
    }

    public String getIsdel0() {
        return isdel0;
    }

    public void setIsdel0(String isdel0) {
        this.isdel0 = isdel0;
    }

    public String getSfyxda() {
        return sfyxda;
    }

    public void setSfyxda(String sfyxda) {
        this.sfyxda = sfyxda;
    }

    public String getJzsj00() {
        return jzsj00;
    }

    public void setJzsj00(String jzsj00) {
        this.jzsj00 = jzsj00;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getFh_id() {
        return fh_id;
    }

    public void setFh_id(String fh_id) {
        this.fh_id = fh_id;
    }

    public String getYyid() {
        return yyid;
    }

    public void setYyid(String yyid) {
        this.yyid = yyid;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getZrysxm() {
        return zrysxm;
    }

    public void setZrysxm(String zrysxm) {
        this.zrysxm = zrysxm;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getCreatorname() {
        return creatorname;
    }

    public void setCreatorname(String creatorname) {
        this.creatorname = creatorname;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getInvestigatorsname() {
        return investigatorsname;
    }

    public void setInvestigatorsname(String investigatorsname) {
        this.investigatorsname = investigatorsname;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }
}
