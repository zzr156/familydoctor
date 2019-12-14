/*
 * CopyRight: StartTech 2005 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>Jktj_fmyghDTO</code>.
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * <p>
 * Title: Jktj_fmyghDTO
 * </p>
 * <p>
 * Description:Jktj_fmyghDTO
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 *
 * @hibernate.class table="Jktj_fmyghDTO"
 * @author 劳动力99开发小组
 * @version 1.0
 */

public class Jktj_fmyghDTO implements Serializable {
	// TODO 自动产生的HSQL语句，根据需要自己修改
	// public final static String HSQL="from Jktj_fmyghDTO where 1=1 and fmyghid
	// like :fmyghid and ymmc like :ymmc and jzrq like :jzrq and jzyy like :jzyy
	// and yyid00 like :yyid00 and df_id like :df_id and jktjcs like :jktjcs and
	// name like :name and telphone like :telphone and adress_city like
	// :adress_city and adress_county like :adress_county and adress_rural like
	// :adress_rural and adress_village like :adress_village and adrss_hnumber
	// like :adrss_hnumber and adress_pro like :adress_pro and birthday like
	// :birthday and sex = :sex and ref_ci_id like :ref_ci_id and f_id like
	// :f_id ";
	/**
	 * 默认构造函数
	 */
	public Jktj_fmyghDTO() {
	}

	private String fmyghid; // 非免疫规划预防接种编号
	private String ymmc; // 疫苗名称
	private String jzrq; // 接种日期
	private String jzyy; // 接种医院
	private String yyid00; // 机构ID
	private String df_id; // 居民编号
	private String jktjcs; // 健康体检次数
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

	/**
	 * 非免疫规划预防接种编号
	 *
	 * @hibernate.property column="fmyghid"
	 * @return fmyghid
	 */
	public String getFmyghid() {
		return this.fmyghid;
	}

	/**
	 * 疫苗名称
	 *
	 * @hibernate.property column="ymmc"
	 * @return ymmc
	 */
	public String getYmmc() {
		return this.ymmc;
	}

	/**
	 * 接种日期
	 *
	 * @hibernate.property column="jzrq"
	 * @return jzrq
	 */
	public String getJzrq() {
		return this.jzrq;
	}

	/**
	 * 接种医院
	 *
	 * @hibernate.property column="jzyy"
	 * @return jzyy
	 */
	public String getJzyy() {
		return this.jzyy;
	}

	/**
	 * 机构ID
	 *
	 * @hibernate.property column="yyid00"
	 * @return yyid00
	 */
	public String getYyid00() {
		return this.yyid00;
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
	 * 健康体检次数
	 *
	 * @hibernate.property column="jktjcs"
	 * @return jktjcs
	 */
	public String getJktjcs() {
		return this.jktjcs;
	}

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
	 * @param fmyghid
	 *            非免疫规划预防接种编号
	 */
	public void setFmyghid(String fmyghid) {
		this.fmyghid = fmyghid;
	}

	/**
	 * @param ymmc
	 *            疫苗名称
	 */
	public void setYmmc(String ymmc) {
		this.ymmc = ymmc;
	}

	/**
	 * @param jzrq
	 *            接种日期
	 */
	public void setJzrq(String jzrq) {
		this.jzrq = jzrq;
	}

	/**
	 * @param jzyy
	 *            接种医院
	 */
	public void setJzyy(String jzyy) {
		this.jzyy = jzyy;
	}

	/**
	 * @param yyid00
	 *            机构ID
	 */
	public void setYyid00(String yyid00) {
		this.yyid00 = yyid00;
	}

	/**
	 * @param df_id
	 *            居民编号
	 */
	public void setDf_id(String df_id) {
		this.df_id = df_id;
	}

	/**
	 * @param jktjcs
	 *            健康体检次数
	 */
	public void setJktjcs(String jktjcs) {
		this.jktjcs = jktjcs;
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public boolean equals(Object o) {
		if (!(o instanceof Jktj_fmyghDTO)) {
			return false;
		}
		if (o == this) {
            return true;
        }
		Jktj_fmyghDTO me = (Jktj_fmyghDTO) o;
		return EqualsBuilder.reflectionEquals(this, o);
	}
}