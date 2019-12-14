package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <p>
 * Title: Jktj_fmygh
 * </p>
 * <p>
 * Description:健康体检之非免疫规划预防接种史
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 *
 * @hibernate.class table="Jktj_fmygh"
 * @author 劳动力99开发小组
 * @version 1.0
 */
public class Jktj_fmygh  {
	// TODO 自动产生的HSQL语句，根据需要自己修改
	// public final static String HSQL="from Jktj_fmygh where 1=1 and fmyghid
	// like :fmyghid and ymmc like :ymmc and jzrq like :jzrq and jzyy like :jzyy
	// and yyid00 like :yyid00 and df_id like :df_id and jktjcs like :jktjcs ";
	/**
	 * 默认构造函数
	 */
	public Jktj_fmygh() {
	}

	private String fmyghid; // 非免疫规划预防接种编号
	private String ymmc; // 疫苗名称
	private String jzrq; // 接种日期
	private String jzyy; // 接种医院
	private String yyid00; // 机构ID
	private String df_id; // 居民编号
	private String jktjcs; // 健康体检次数
	@Id
    @Column(name = "FMYGHID")
	public String getFmyghid() {
		return fmyghid;
	}

	public void setFmyghid(String fmyghid) {
		this.fmyghid = fmyghid;
	}
	@Basic
    @Column(name = "YMMC")
	public String getYmmc() {
		return ymmc;
	}

	public void setYmmc(String ymmc) {
		this.ymmc = ymmc;
	}
	@Basic
    @Column(name = "JZRQ")
	public String getJzrq() {
		return jzrq;
	}

	public void setJzrq(String jzrq) {
		this.jzrq = jzrq;
	}
	@Basic
    @Column(name = "JZYY")
	public String getJzyy() {
		return jzyy;
	}

	public void setJzyy(String jzyy) {
		this.jzyy = jzyy;
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
    @Column(name = "DF_ID")
	public String getDf_id() {
		return df_id;
	}

	public void setDf_id(String df_id) {
		this.df_id = df_id;
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