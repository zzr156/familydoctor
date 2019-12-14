package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;


/**
 * <p>
 * Title: Jktj_jtbcs
 * </p>
 * <p>
 * Description:健康体检之家庭病床史
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 *
 * @hibernate.class table="Jktj_jtbcs"
 * @author 劳动力99开发小组
 * @version 1.0
 */
public class Jktj_jtbcs {
	// TODO 自动产生的HSQL语句，根据需要自己修改
	// public final static String HSQL="from Jktj_jtbcs where 1=1 and jtbcsid
	// like :jtbcsid and jcrq like :jcrq and ccrq like :ccrq and bjyy like :bjyy
	// and yljgmc like :yljgmc and bcbah like :bcbah and df_id like :df_id and
	// f_id like :f_id and yyid00 like :yyid00 and jktjcs like :jktjcs ";
	/**
	 * 默认构造函数
	 */
	public Jktj_jtbcs() {
	}

	private String jtbcsid; // 家庭病床史编号
	private String jcrq; // 建床日期
	private String ccrq; // 撤床日期
	private String bjyy; // 病床原因
	private String yljgmc; // 医院名称
	private String bcbah; // 病床病案号
	private String df_id; // 居民编号
	private String f_id; // 家庭编号
	private String yyid00; // 机构名称
	private String jktjcs; // 健康体检次数

	@Id
	@Column(name = "JTBCSID")
	public String getJtbcsid() {
		return jtbcsid;
	}

	public void setJtbcsid(String jtbcsid) {
		this.jtbcsid = jtbcsid;
	}

	@Basic
	@Column(name = "JCRQ")
	public String getJcrq() {
		return jcrq;
	}

	public void setJcrq(String jcrq) {
		this.jcrq = jcrq;
	}

	@Basic
	@Column(name = "CCRQ")
	public String getCcrq() {
		return ccrq;
	}

	public void setCcrq(String ccrq) {
		this.ccrq = ccrq;
	}

	@Basic
	@Column(name = "BJYY")
	public String getBjyy() {
		return bjyy;
	}

	public void setBjyy(String bjyy) {
		this.bjyy = bjyy;
	}

	@Basic
	@Column(name = "YLJGMC")
	public String getYljgmc() {
		return yljgmc;
	}

	public void setYljgmc(String yljgmc) {
		this.yljgmc = yljgmc;
	}

	@Basic
	@Column(name = "BCBAH")
	public String getBcbah() {
		return bcbah;
	}

	public void setBcbah(String bcbah) {
		this.bcbah = bcbah;
	}

	@Basic
	@Column(name = "DF_ID")
	public String getDf_id() {
		return df_id;
	}

	public void setDf_id(String df_id) {
		this.df_id = df_id;
	}

	@Basic
	@Column(name = "F_ID")
	public String getF_id() {
		return f_id;
	}

	public void setF_id(String f_id) {
		this.f_id = f_id;
	}

	@Basic
	@Column(name = "YYID00")
	public String getYyid00() {
		return yyid00;
	}

	public void setYyid00(String yyid00) {
		this.yyid00 = yyid00;
	}

	@Basic
	@Column(name = "JKTJCS")
	public String getJktjcs() {
		return jktjcs;
	}

	public void setJktjcs(String jktjcs) {
		this.jktjcs = jktjcs;
	}

}