/*
 * CopyRight: StartTech 2005 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>Jktj_jtbcsDTO</code>.
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: Jktj_jtbcsDTO
 * </p>
 * <p>
 * Description:Jktj_jtbcsDTO
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 *
 * @hibernate.class table="Jktj_jtbcsDTO"
 * @author 劳动力99开发小组
 * @version 1.0
 */

public class Jktj_jtbcsDTO {
	// TODO 自动产生的HSQL语句，根据需要自己修改
	// public final static String HSQL="from Jktj_jtbcsDTO where 1=1 and name
	// like :name and telphone like :telphone and adress_city like :adress_city
	// and adress_county like :adress_county and adress_rural like :adress_rural
	// and adress_village like :adress_village and adrss_hnumber like
	// :adrss_hnumber and adress_pro like :adress_pro and birthday like
	// :birthday and sex = :sex and ref_ci_id like :ref_ci_id and f_id like
	// :f_id and jtbcsid like :jtbcsid and jcrq like :jcrq and ccrq like :ccrq
	// and bjyy like :bjyy and yljgmc like :yljgmc and bcbah like :bcbah and
	// df_id like :df_id and yyid00 like :yyid00 and jktjcs like :jktjcs ";
	/**
	 * 默认构造函数
	 */
	public Jktj_jtbcsDTO() {
	}

	private String name; // 居民姓名
	private String telphone; // 联系电话
	private String adress_city; // 常住地址：市（地区）
	private String adress_county; // 常住地址：县（区）
	private String adress_rural; // 常住地址：乡（镇、街道办事处）
	private String adress_village; // 常住地址：村（街、路、弄）
	private String adrss_hnumber; // 常住地址：门牌号码
	private String adress_pro; // 常住地址：省（自治区、直辖市）
	private String birthday; // 出生日期
	private java.math.BigDecimal sex; // 性别
	private String ref_ci_id; // 社区号
	private String f_id; // 家庭档案号
	private String jtbcsid; // 家庭病床史编号
	private String jcrq; // 建床日期
	private String ccrq; // 撤床日期
	private String bjyy; // 病床原因
	private String yljgmc; // 医院名称
	private String bcbah; // 病床病案号
	private String df_id; // 居民编号
	private String yyid00; // 机构名称
	private String jktjcs; // 健康体检次数

	/**
	 * 居民姓名
	 *
	 * @hibernate.property column="name"
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 联系电话
	 *
	 * @hibernate.property column="telphone"
	 * @return telphone
	 */
	public String getTelphone() {
		return this.telphone;
	}

	/**
	 * 常住地址：市（地区）
	 *
	 * @hibernate.property column="adress_city"
	 * @return adress_city
	 */
	public String getAdress_city() {
		return this.adress_city;
	}

	/**
	 * 常住地址：县（区）
	 *
	 * @hibernate.property column="adress_county"
	 * @return adress_county
	 */
	public String getAdress_county() {
		return this.adress_county;
	}

	/**
	 * 常住地址：乡（镇、街道办事处）
	 *
	 * @hibernate.property column="adress_rural"
	 * @return adress_rural
	 */
	public String getAdress_rural() {
		return this.adress_rural;
	}

	/**
	 * 常住地址：村（街、路、弄）
	 *
	 * @hibernate.property column="adress_village"
	 * @return adress_village
	 */
	public String getAdress_village() {
		return this.adress_village;
	}

	/**
	 * 常住地址：门牌号码
	 *
	 * @hibernate.property column="adrss_hnumber"
	 * @return adrss_hnumber
	 */
	public String getAdrss_hnumber() {
		return this.adrss_hnumber;
	}

	/**
	 * 常住地址：省（自治区、直辖市）
	 *
	 * @hibernate.property column="adress_pro"
	 * @return adress_pro
	 */
	public String getAdress_pro() {
		return this.adress_pro;
	}

	/**
	 * 出生日期
	 *
	 * @hibernate.property column="birthday"
	 * @return birthday
	 */
	public String getBirthday() {
		return this.birthday;
	}

	/**
	 * 性别
	 *
	 * @hibernate.property column="sex"
	 * @return sex
	 */
	public java.math.BigDecimal getSex() {
		return this.sex;
	}

	/**
	 * 社区号
	 *
	 * @hibernate.property column="ref_ci_id"
	 * @return ref_ci_id
	 */
	public String getRef_ci_id() {
		return this.ref_ci_id;
	}

	/**
	 * 家庭档案号
	 *
	 * @hibernate.property column="f_id"
	 * @return f_id
	 */
	public String getF_id() {
		return this.f_id;
	}

	/**
	 * 家庭病床史编号
	 *
	 * @hibernate.property column="jtbcsid"
	 * @return jtbcsid
	 */
	public String getJtbcsid() {
		return this.jtbcsid;
	}

	/**
	 * 建床日期
	 *
	 * @hibernate.property column="jcrq"
	 * @return jcrq
	 */
	public String getJcrq() {
		return this.jcrq;
	}

	/**
	 * 撤床日期
	 *
	 * @hibernate.property column="ccrq"
	 * @return ccrq
	 */
	public String getCcrq() {
		return this.ccrq;
	}

	/**
	 * 病床原因
	 *
	 * @hibernate.property column="bjyy"
	 * @return bjyy
	 */
	public String getBjyy() {
		return this.bjyy;
	}

	/**
	 * 医院名称
	 *
	 * @hibernate.property column="yljgmc"
	 * @return yljgmc
	 */
	public String getYljgmc() {
		return this.yljgmc;
	}

	/**
	 * 病床病案号
	 *
	 * @hibernate.property column="bcbah"
	 * @return bcbah
	 */
	public String getBcbah() {
		return this.bcbah;
	}

	/**
	 * 居民编号
	 *
	 * @hibernate.property column="df_id"
	 * @return df_id
	 */
	public String getDf_id() {
		return this.df_id;
	}

	/**
	 * 机构名称
	 *
	 * @hibernate.property column="yyid00"
	 * @return yyid00
	 */
	public String getYyid00() {
		return this.yyid00;
	}

	/**
	 * 健康体检次数
	 *
	 * @hibernate.property column="jktjcs"
	 * @return jktjcs
	 */
	public String getJktjcs() {
		return this.jktjcs;
	}

	/**
	 * @param name
	 *            居民姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param telphone
	 *            联系电话
	 */
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	/**
	 * @param adress_city
	 *            常住地址：市（地区）
	 */
	public void setAdress_city(String adress_city) {
		this.adress_city = adress_city;
	}

	/**
	 * @param adress_county
	 *            常住地址：县（区）
	 */
	public void setAdress_county(String adress_county) {
		this.adress_county = adress_county;
	}

	/**
	 * @param adress_rural
	 *            常住地址：乡（镇、街道办事处）
	 */
	public void setAdress_rural(String adress_rural) {
		this.adress_rural = adress_rural;
	}

	/**
	 * @param adress_village
	 *            常住地址：村（街、路、弄）
	 */
	public void setAdress_village(String adress_village) {
		this.adress_village = adress_village;
	}

	/**
	 * @param adrss_hnumber
	 *            常住地址：门牌号码
	 */
	public void setAdrss_hnumber(String adrss_hnumber) {
		this.adrss_hnumber = adrss_hnumber;
	}

	/**
	 * @param adress_pro
	 *            常住地址：省（自治区、直辖市）
	 */
	public void setAdress_pro(String adress_pro) {
		this.adress_pro = adress_pro;
	}

	/**
	 * @param birthday
	 *            出生日期
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * @param sex
	 *            性别
	 */
	public void setSex(java.math.BigDecimal sex) {
		this.sex = sex;
	}

	/**
	 * @param ref_ci_id
	 *            社区号
	 */
	public void setRef_ci_id(String ref_ci_id) {
		this.ref_ci_id = ref_ci_id;
	}

	/**
	 * @param f_id
	 *            家庭档案号
	 */
	public void setF_id(String f_id) {
		this.f_id = f_id;
	}

	/**
	 * @param jtbcsid
	 *            家庭病床史编号
	 */
	public void setJtbcsid(String jtbcsid) {
		this.jtbcsid = jtbcsid;
	}

	/**
	 * @param jcrq
	 *            建床日期
	 */
	public void setJcrq(String jcrq) {
		this.jcrq = jcrq;
	}

	/**
	 * @param ccrq
	 *            撤床日期
	 */
	public void setCcrq(String ccrq) {
		this.ccrq = ccrq;
	}

	/**
	 * @param bjyy
	 *            病床原因
	 */
	public void setBjyy(String bjyy) {
		this.bjyy = bjyy;
	}

	/**
	 * @param yljgmc
	 *            医院名称
	 */
	public void setYljgmc(String yljgmc) {
		this.yljgmc = yljgmc;
	}

	/**
	 * @param bcbah
	 *            病床病案号
	 */
	public void setBcbah(String bcbah) {
		this.bcbah = bcbah;
	}

	/**
	 * @param df_id
	 *            居民编号
	 */
	public void setDf_id(String df_id) {
		this.df_id = df_id;
	}

	/**
	 * @param yyid00
	 *            机构名称
	 */
	public void setYyid00(String yyid00) {
		this.yyid00 = yyid00;
	}

	/**
	 * @param jktjcs
	 *            健康体检次数
	 */
	public void setJktjcs(String jktjcs) {
		this.jktjcs = jktjcs;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public boolean equals(Object o) {
		if (!(o instanceof Jktj_jtbcsDTO)) {
			return false;
		}
		if (o == this) {
            return true;
        }
		Jktj_jtbcsDTO me = (Jktj_jtbcsDTO) o;
		return EqualsBuilder.reflectionEquals(this, o);
	}
}