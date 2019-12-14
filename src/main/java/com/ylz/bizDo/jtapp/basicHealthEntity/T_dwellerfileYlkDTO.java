package com.ylz.bizDo.jtapp.basicHealthEntity;

/**
 * 居民--居民档案
 * @author hp
 *
 */
public class T_dwellerfileYlkDTO {
	private String df_id; // 居民档案号
	private String name; // 居民姓名
	private String idcardno; // 身份证号
	private String sex; // 性别
	private String birthday; // 出生日期
	private String rprtype; // 户口性质  常住类型
	private String adress_city; // 常住地址：市（地区）
	private String adress_county; // 常住地址：县（区）
	private String adress_rural; // 常住地址：乡（镇、街道办事处）
	private String adress_village; // 常住地址：村（街、路、弄）
	private String adrss_hnumber; // 常住地址：门牌号码
	private String adress_pro; // 常住地址：省（自治区、直辖市）
	private String ref_cjid; // 居委会ID（必填）
	private String folk; // 民族
	private String telphone; // 本人电话
	private String workstatus; // 职业状况
	private String workplace; // 工作单位
	private String bloodtype; // 血型
	private String rhxx; // RH血型
	private String cdegree; // 文化程度
	private String mstatus; // 婚姻状况
	private String namecode; // 联系人姓名 姓名-标识对象代码(姓名的标识对象代码)
	private String telphonetype; //联系人电话  联系电话-类别(联系电话所属者类别的名称)
	private String czzgbx; // 医疗费用支付方式--城镇职工基本医疗保险
	private String czjmbx; // 医疗费用支付方式--城镇居民基本医疗保险
	private String xrhzyl; // 医疗费用支付方式--新型农村合作医疗
	private String pkjz; // 医疗费用支付方式-贫困救助
	private String syylbx; // 医疗费用支付方式--商业医疗保险
	private String qgf; // 医疗费用支付方式--全公费
	private String qzf; // 医疗费用支付方式--全自费
	private String qtfs00; // 医疗费用支付方式-其他方式
	private String jwbsbh; // 既往病史编号
	private String jwbsjb; // 既往病史疾病
	private String sensitive; // 过敏史
	private String expose; // 暴露史
	private String cjqk0; // 残疾编号（0：无残疾；7：视力残疾；8：听力残疾；9：言语残疾；10：智力残疾；11：肢体残疾；12：精神残疾）
	private String jwbsss; // 既往病史手术
	private String jwbsws; // 既往病史外伤
	private String jwbssx; // 既往病史输血
	private String jzsfq; // 家族史父亲
	private String jzsmq; // 家族史母亲
	private String jzsxm; // 家族史兄妹
	private String jzszn; // 家族史子女
	private String ycbs00; // 遗传病史
	private String cfpfss; // 厨房排风设施
	private String rllx; // 燃料类型
	private String ys; // 饮水
	private String cs; // 厕所
	private String qcl; // 禽畜栏
	private String investigators; // 调查员
	private String idate; // 调查日期
	private String creator; // 建档人
	private String jdrq00; // 建档日期
	private String ismove; // 是否迁出
	private String movedate; // 迁出时间
	private String isdead; // 是否死亡
	private String ddate; // 死亡日期
	private String doctor; // 责任医生
	private String ishcpact; // 是否签保健合同
	private String r_id; // 居民与户主关系
	private String beizhu;//备注
	private String xzbh00; //乡镇编号
	private String ref_ci_id; // 社区号
	private String lrybh0; // 录入员编号
	private String lryxm0; // 录入员姓名
	private String cjqt0; // 残疾情况其他

	private String cdate; // 建档日期
	private String dabh00; // 档案编号
	private String plxh00; // 排列序号
	private String f_id; // 家庭档案号

	private String hadress_pro; // 家庭地址：省（自治区、直辖市）

	private String hadress_city; // 家庭地址：市（地区）

	private String hadress_county; // 家庭地址：县（区）

	private String hadress_rural; // 家庭地址：乡（镇、街道办事处）

	private String hadress_village; // 家庭地址：村（街、路、弄）

	private String hadress_hnumber; // 家庭地址：门牌号码

	private  String tel; //家庭联系电话

	private String isPerson;// 是否居民地址


	private  String dataSource; //数据来源（1新基卫）


	public String getDf_id() {
		return df_id;
	}
	public void setDf_id(String dfId) {
		df_id = dfId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdcardno() {
		return idcardno;
	}
	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
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
	public String getRprtype() {
		return rprtype;
	}
	public void setRprtype(String rprtype) {
		this.rprtype = rprtype;
	}
	public String getAdress_city() {
		return adress_city;
	}
	public void setAdress_city(String adressCity) {
		adress_city = adressCity;
	}
	public String getAdress_county() {
		return adress_county;
	}
	public void setAdress_county(String adressCounty) {
		adress_county = adressCounty;
	}
	public String getAdress_rural() {
		return adress_rural;
	}
	public void setAdress_rural(String adressRural) {
		adress_rural = adressRural;
	}
	public String getAdress_village() {
		return adress_village;
	}
	public void setAdress_village(String adressVillage) {
		adress_village = adressVillage;
	}
	public String getAdrss_hnumber() {
		return adrss_hnumber;
	}
	public void setAdrss_hnumber(String adrssHnumber) {
		adrss_hnumber = adrssHnumber;
	}
	public String getAdress_pro() {
		return adress_pro;
	}
	public void setAdress_pro(String adressPro) {
		adress_pro = adressPro;
	}
	public String getRef_cjid() {
		return ref_cjid;
	}
	public void setRef_cjid(String refCjid) {
		ref_cjid = refCjid;
	}
	public String getFolk() {
		return folk;
	}
	public void setFolk(String folk) {
		this.folk = folk;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getWorkstatus() {
		return workstatus;
	}
	public void setWorkstatus(String workstatus) {
		this.workstatus = workstatus;
	}
	public String getWorkplace() {
		return workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}
	public String getBloodtype() {
		return bloodtype;
	}
	public void setBloodtype(String bloodtype) {
		this.bloodtype = bloodtype;
	}
	public String getRhxx() {
		return rhxx;
	}
	public void setRhxx(String rhxx) {
		this.rhxx = rhxx;
	}
	public String getCdegree() {
		return cdegree;
	}
	public void setCdegree(String cdegree) {
		this.cdegree = cdegree;
	}
	public String getMstatus() {
		return mstatus;
	}
	public void setMstatus(String mstatus) {
		this.mstatus = mstatus;
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
	public String getJwbsbh() {
		return jwbsbh;
	}
	public void setJwbsbh(String jwbsbh) {
		this.jwbsbh = jwbsbh;
	}
	public String getJwbsjb() {
		return jwbsjb;
	}
	public void setJwbsjb(String jwbsjb) {
		this.jwbsjb = jwbsjb;
	}
	public String getSensitive() {
		return sensitive;
	}
	public void setSensitive(String sensitive) {
		this.sensitive = sensitive;
	}
	public String getExpose() {
		return expose;
	}
	public void setExpose(String expose) {
		this.expose = expose;
	}
	public String getCjqk0() {
		return cjqk0;
	}
	public void setCjqk0(String cjqk0) {
		this.cjqk0 = cjqk0;
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
	public String getYcbs00() {
		return ycbs00;
	}
	public void setYcbs00(String ycbs00) {
		this.ycbs00 = ycbs00;
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
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getJdrq00() {
		return jdrq00;
	}
	public void setJdrq00(String jdrq00) {
		this.jdrq00 = jdrq00;
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
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getIshcpact() {
		return ishcpact;
	}
	public void setIshcpact(String ishcpact) {
		this.ishcpact = ishcpact;
	}
	public String getR_id() {
		return r_id;
	}
	public void setR_id(String rId) {
		r_id = rId;
	}
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	public String getXzbh00() {
		return xzbh00;
	}
	public void setXzbh00(String xzbh00) {
		this.xzbh00 = xzbh00;
	}
	public String getRef_ci_id() {
		return ref_ci_id;
	}
	public void setRef_ci_id(String refCiId) {
		ref_ci_id = refCiId;
	}
	public String getLrybh0() {
		return lrybh0;
	}
	public void setLrybh0(String lrybh0) {
		this.lrybh0 = lrybh0;
	}
	public String getLryxm0() {
		return lryxm0;
	}
	public void setLryxm0(String lryxm0) {
		this.lryxm0 = lryxm0;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getDabh00() {
		return dabh00;
	}
	public void setDabh00(String dabh00) {
		this.dabh00 = dabh00;
	}
	public String getPlxh00() {
		return plxh00;
	}
	public void setPlxh00(String plxh00) {
		this.plxh00 = plxh00;
	}
	public String getF_id() {
		return f_id;
	}
	public void setF_id(String fId) {
		f_id = fId;
	}
	public String getCjqt0() {
		return cjqt0;
	}
	public void setCjqt0(String cjqt0) {
		this.cjqt0 = cjqt0;
	}
	public String getHadress_pro() {
		return hadress_pro;
	}
	public void setHadress_pro(String hadress_pro) {
		this.hadress_pro = hadress_pro;
	}
	public String getHadress_city() {
		return hadress_city;
	}
	public void setHadress_city(String hadress_city) {
		this.hadress_city = hadress_city;
	}
	public String getHadress_county() {
		return hadress_county;
	}
	public void setHadress_county(String hadress_county) {
		this.hadress_county = hadress_county;
	}
	public String getHadress_rural() {
		return hadress_rural;
	}
	public void setHadress_rural(String hadress_rural) {
		this.hadress_rural = hadress_rural;
	}
	public String getHadress_village() {
		return hadress_village;
	}
	public void setHadress_village(String hadress_village) {
		this.hadress_village = hadress_village;
	}
	public String getHadress_hnumber() {
		return hadress_hnumber;
	}
	public void setHadress_hnumber(String hadress_hnumber) {
		this.hadress_hnumber = hadress_hnumber;
	}

	/**
	 * 家庭联系电话
	 * @return
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 家庭联系电话
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 是否居民地址
	 * @return
	 */
	public String getIsPerson() {
		return isPerson;
	}

	/**
	 * 是否居民地址
	 * @param isPerson
	 */
	public void setIsPerson(String isPerson) {
		this.isPerson = isPerson;
	}

	/**
	 * 数据来源（1新基卫）
	 * @return
	 */
	public String getDataSource() {
		return dataSource;
	}

	/**
	 * 数据来源（1新基卫）
	 * @param dataSource
	 */
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

}
