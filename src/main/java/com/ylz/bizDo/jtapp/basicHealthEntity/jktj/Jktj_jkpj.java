package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;


/**
 * <p>
 * Title: Jktj_jkpj
 * </p>
 * <p>
 * Description:健康体检--健康评价
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 *
 * @hibernate.class table="Jktj_jkpj"
 * @author 劳动力99开发小组
 * @version 1.0
 */
public class Jktj_jkpj {
	// TODO 自动产生的HSQL语句，根据需要自己修改
	// public final static String HSQL="from Jktj_jkpj where 1=1 and jktj_jkpjid
	// like :jktj_jkpjid and yyid00 like :yyid00 and df_id like :df_id and
	// jktjcs like :jktjcs and jkpj_tjsfyc like :jkpj_tjsfyc and jkpj_tjyc1 like
	// :jkpj_tjyc1 and jkpj_tjyc2 like :jkpj_tjyc2 and jkpj_tjyc3 like
	// :jkpj_tjyc3 and jkpj_tjyc4 like :jkpj_tjyc4 and jkzd like :jkzd and
	// wxyskz like :wxyskz and wxyskz_jtzmb like :wxyskz_jtzmb and wxyskz_ymjz
	// like :wxyskz_ymjz and wxyskz_qt like :wxyskz_qt ";
	/**
	 * 默认构造函数
	 */
	public Jktj_jkpj() {
	}

	private String jktj_jkpjid; // 健康体检--健康评价ID
	private String yyid00; // 机构ID
	private String df_id; // 居民健康档案ID
	private String jktjcs; // 健康体检次数
	private String jkpj_tjsfyc; // 健康评价--体检是否异常（1--体检无异常，2--有异常）
	private String jkpj_tjyc1; // 健康评价--体检异常1
	private String jkpj_tjyc2; // 健康评价--体检异常2
	private String jkpj_tjyc3; // 健康评价--体检异常3
	private String jkpj_tjyc4; // 健康评价--体检异常4
	private String jkzd; // 健康指导（1--定期随访，2--纳入慢性病患者健康管理，3--建议复诊，4---建议转诊）
	private String wxyskz; // 危险因素控制（1--戒烟，2--健康饮酒，3--饮食，4--锻炼，5--减体重，6--建议疫苗接种，7--其他）
	private String wxyskz_jtzmb; // 危险因素控制--减体重目标
	private String wxyskz_ymjz; // 危险因素控制--疫苗接种
	private String wxyskz_qt; // 危险因素控制--其他
	private String jkzd00; // 健康指导

	private String jjcf00; // 健教处方
	@Id
    @Column(name = "JKTJ_JKPJID")
	public String getJktj_jkpjid() {
		return jktj_jkpjid;
	}

	public void setJktj_jkpjid(String jktj_jkpjid) {
		this.jktj_jkpjid = jktj_jkpjid;
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
	@Column(name = "JKPJ_TJSFYC")
	public String getJkpj_tjsfyc() {
		return jkpj_tjsfyc;
	}

	public void setJkpj_tjsfyc(String jkpj_tjsfyc) {
		this.jkpj_tjsfyc = jkpj_tjsfyc;
	}
	@Basic
	@Column(name = "JKPJ_TJYC1")
	public String getJkpj_tjyc1() {
		return jkpj_tjyc1;
	}

	public void setJkpj_tjyc1(String jkpj_tjyc1) {
		this.jkpj_tjyc1 = jkpj_tjyc1;
	}
	@Basic
	@Column(name = "JKPJ_TJYC2")
	public String getJkpj_tjyc2() {
		return jkpj_tjyc2;
	}

	public void setJkpj_tjyc2(String jkpj_tjyc2) {
		this.jkpj_tjyc2 = jkpj_tjyc2;
	}
	@Basic
	@Column(name = "JKPJ_TJYC3")
	public String getJkpj_tjyc3() {
		return jkpj_tjyc3;
	}

	public void setJkpj_tjyc3(String jkpj_tjyc3) {
		this.jkpj_tjyc3 = jkpj_tjyc3;
	}
	@Basic
	@Column(name = "JKPJ_TJYC4")
	public String getJkpj_tjyc4() {
		return jkpj_tjyc4;
	}

	public void setJkpj_tjyc4(String jkpj_tjyc4) {
		this.jkpj_tjyc4 = jkpj_tjyc4;
	}
	@Basic
	@Column(name = "JKZD")
	public String getJkzd() {
		return jkzd;
	}

	public void setJkzd(String jkzd) {
		this.jkzd = jkzd;
	}
	@Basic
	@Column(name = "WXYSKZ")
	public String getWxyskz() {
		return wxyskz;
	}

	public void setWxyskz(String wxyskz) {
		this.wxyskz = wxyskz;
	}
	@Basic
	@Column(name = "WXYSKZ_JTZMB")
	public String getWxyskz_jtzmb() {
		return wxyskz_jtzmb;
	}

	public void setWxyskz_jtzmb(String wxyskz_jtzmb) {
		this.wxyskz_jtzmb = wxyskz_jtzmb;
	}
	@Basic
	@Column(name = "WXYSKZ_YMJZ")
	public String getWxyskz_ymjz() {
		return wxyskz_ymjz;
	}

	public void setWxyskz_ymjz(String wxyskz_ymjz) {
		this.wxyskz_ymjz = wxyskz_ymjz;
	}
	@Basic
	@Column(name = "WXYSKZ_QT")
	public String getWxyskz_qt() {
		return wxyskz_qt;
	}

	public void setWxyskz_qt(String wxyskz_qt) {
		this.wxyskz_qt = wxyskz_qt;
	}
	@Basic
	@Column(name = "JKZD00")
	public String getJkzd00() {
		return jkzd00;
	}

	public void setJkzd00(String jkzd00) {
		this.jkzd00 = jkzd00;
	}
	@Basic
	@Column(name = "JJCF00")
	public String getJjcf00() {
		return jjcf00;
	}

	public void setJjcf00(String jjcf00) {
		this.jjcf00 = jjcf00;
	}

}