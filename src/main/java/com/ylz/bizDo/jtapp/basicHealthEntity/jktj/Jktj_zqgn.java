/*
 * CopyRight: StartTech 2005 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>Jktj_zqgn</code>.
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;


/**
 * <p>
 * Title: Jktj_zqgn
 * </p>
 * <p>
 * Description:健康体检--脏器功能
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 *
 * @hibernate.class table="Jktj_zqgn"
 * @author 劳动力99开发小组
 * @version 1.0
 */
public class Jktj_zqgn {
	// TODO 自动产生的HSQL语句，根据需要自己修改
	// public final static String HSQL="from Jktj_zqgn where 1=1 and jktj_zqgnid
	// like :jktj_zqgnid and yyid00 like :yyid00 and df_id like :df_id and
	// jktjcs like :jktjcs and jktj_zqgn_kqkc like :jktj_zqgn_kqkc and
	// jktj_zqgn_kqcl like :jktj_zqgn_kqcl and jktj_zqgn_kqyb like
	// :jktj_zqgn_kqyb and jktj_zqgn_slzy like :jktj_zqgn_slzy and
	// jktj_zqgn_slyy like :jktj_zqgn_slyy and jktj_zqgn_jzslzy like
	// :jktj_zqgn_jzslzy and jktj_zqgn_jzslyy like :jktj_zqgn_jzslyy and
	// jktj_zqgn_tl like :jktj_zqgn_tl and jktj_zqgn_ydgn like :jktj_zqgn_ydgn
	// and jktj_zqgn_quechi like :jktj_zqgn_quechi and jktj_zqgn_quchi0 like
	// :jktj_zqgn_quchi0 and jktj_zqgn_yichi0 like :jktj_zqgn_yichi0 ";
	/**
	 * 默认构造函数
	 */
	public Jktj_zqgn() {
	}

	private String jktj_zqgnid; // 健康体检--脏器功能ID
	private String yyid00; // 机构ID
	private String df_id; // 居民健康档案ID
	private String jktjcs; // 健康体检次数
	private String jktj_zqgn_kqkc; // 健康体检--脏器功能--口腔-口唇
	private String jktj_zqgn_kqcl; // 健康体检--脏器功能--口腔-齿列（1--正常，2--缺齿，3--龋齿，4--义齿）
	private String jktj_zqgn_kqyb; // 健康体检--脏器功能--口腔-咽部
	private String jktj_zqgn_slzy; // 健康体检--脏器功能--视力左眼
	private String jktj_zqgn_slyy; // 健康体检--脏器功能--视力右眼
	private String jktj_zqgn_jzslzy; // 健康体检--脏器功能--矫正视力左眼
	private String jktj_zqgn_jzslyy; // 健康体检--脏器功能--矫正视力右眼
	private String jktj_zqgn_tl; // 健康体检--脏器功能--听力
	private String jktj_zqgn_ydgn; // 健康体检--脏器功能--运动功能
	private String jktj_zqgn_quechi; // 健康体检--脏器功能--口腔--齿列（缺齿情况，上下左右分别用分号拼接起来）
	private String jktj_zqgn_quchi0; // 健康体检--脏器功能--口腔--齿列（龋齿情况，上下左右分别用分号拼接起来）
	private String jktj_zqgn_yichi0; // 健康体检--脏器功能--口腔--齿列（义齿情况，上下左右分别用分号拼接起来）
	@Id
    @Column(name = "JKTJ_ZQGNID")
	public String getJktj_zqgnid() {
		return jktj_zqgnid;
	}

	public void setJktj_zqgnid(String jktj_zqgnid) {
		this.jktj_zqgnid = jktj_zqgnid;
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
	@Basic
    @Column(name = "JKTJ_ZQGN_KQKC")
	public String getJktj_zqgn_kqkc() {
		return jktj_zqgn_kqkc;
	}

	public void setJktj_zqgn_kqkc(String jktj_zqgn_kqkc) {
		this.jktj_zqgn_kqkc = jktj_zqgn_kqkc;
	}
	@Basic
    @Column(name = "JKTJ_ZQGN_KQCL")
	public String getJktj_zqgn_kqcl() {
		return jktj_zqgn_kqcl;
	}

	public void setJktj_zqgn_kqcl(String jktj_zqgn_kqcl) {
		this.jktj_zqgn_kqcl = jktj_zqgn_kqcl;
	}
	@Basic
    @Column(name = "JKTJ_ZQGN_KQYB")
	public String getJktj_zqgn_kqyb() {
		return jktj_zqgn_kqyb;
	}

	public void setJktj_zqgn_kqyb(String jktj_zqgn_kqyb) {
		this.jktj_zqgn_kqyb = jktj_zqgn_kqyb;
	}
	@Basic
    @Column(name = "JKTJ_ZQGN_SLZY")
	public String getJktj_zqgn_slzy() {
		return jktj_zqgn_slzy;
	}

	public void setJktj_zqgn_slzy(String jktj_zqgn_slzy) {
		this.jktj_zqgn_slzy = jktj_zqgn_slzy;
	}
	@Basic
    @Column(name = "JKTJ_ZQGN_SLYY")
	public String getJktj_zqgn_slyy() {
		return jktj_zqgn_slyy;
	}

	public void setJktj_zqgn_slyy(String jktj_zqgn_slyy) {
		this.jktj_zqgn_slyy = jktj_zqgn_slyy;
	}
	@Basic
    @Column(name = "JKTJ_ZQGN_JZSLZY")
	public String getJktj_zqgn_jzslzy() {
		return jktj_zqgn_jzslzy;
	}

	public void setJktj_zqgn_jzslzy(String jktj_zqgn_jzslzy) {
		this.jktj_zqgn_jzslzy = jktj_zqgn_jzslzy;
	}
	@Basic
    @Column(name = "JKTJ_ZQGN_JZSLYY")
	public String getJktj_zqgn_jzslyy() {
		return jktj_zqgn_jzslyy;
	}

	public void setJktj_zqgn_jzslyy(String jktj_zqgn_jzslyy) {
		this.jktj_zqgn_jzslyy = jktj_zqgn_jzslyy;
	}
	@Basic
    @Column(name = "JKTJ_ZQGN_TL")
	public String getJktj_zqgn_tl() {
		return jktj_zqgn_tl;
	}

	public void setJktj_zqgn_tl(String jktj_zqgn_tl) {
		this.jktj_zqgn_tl = jktj_zqgn_tl;
	}
	@Basic
    @Column(name = "JKTJ_ZQGN_YDGN")
	public String getJktj_zqgn_ydgn() {
		return jktj_zqgn_ydgn;
	}

	public void setJktj_zqgn_ydgn(String jktj_zqgn_ydgn) {
		this.jktj_zqgn_ydgn = jktj_zqgn_ydgn;
	}
	@Basic
    @Column(name = "JKTJ_ZQGN_QUECHI")
	public String getJktj_zqgn_quechi() {
		return jktj_zqgn_quechi;
	}

	public void setJktj_zqgn_quechi(String jktj_zqgn_quechi) {
		this.jktj_zqgn_quechi = jktj_zqgn_quechi;
	}
	@Basic
    @Column(name = "JKTJ_ZQGN_QUCHI0")
	public String getJktj_zqgn_quchi0() {
		return jktj_zqgn_quchi0;
	}

	public void setJktj_zqgn_quchi0(String jktj_zqgn_quchi0) {
		this.jktj_zqgn_quchi0 = jktj_zqgn_quchi0;
	}
	@Basic
    @Column(name = "JKTJ_ZQGN_YICHI0")
	public String getJktj_zqgn_yichi0() {
		return jktj_zqgn_yichi0;
	}

	public void setJktj_zqgn_yichi0(String jktj_zqgn_yichi0) {
		this.jktj_zqgn_yichi0 = jktj_zqgn_yichi0;
	}

}