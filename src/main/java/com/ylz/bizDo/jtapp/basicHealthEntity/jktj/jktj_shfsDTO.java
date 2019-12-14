package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class jktj_shfsDTO {
	public jktj_shfsDTO() {
	}

	private String jktj_sfhs_id; // 编号
	private String df_id; // 居民档案号
	private String sfhs_zybl_jtzy; // 职业暴露--具体职业
	private String sfhs_zybl_cysj; // 职业暴露--从业时间（年）
	private String sfhs_zybl_hxp; // 职业暴露--化学品
	private String sfhs_zybl_hxpcs; // 职业暴露--化学品--防护措施（1--有，0--无）
	private String sfhs_zybl_dw; // 职业暴露--毒物
	private String sfhs_zybl_dwcs; // 职业暴露--毒物--防护措施（1--有，0--无）
	private String sfhs_zybl_sx; // 职业暴露--射线
	private String sfhs_zybl_sxcs; // 职业暴露--射线--防护措施（1--有，0--无）
	private String shfs_tydl_jcdlsj; // 体育锻炼--坚持锻炼时间(年)
	private String shsf_ysxg; // 饮食习惯（1--荤素均衡，2--荤食为主，3--素食为主
										// ，4--嗜盐，5--嗜油，6--嗜糖）
	private String shsf_xyqk_xynl; // 吸烟情况-- 吸烟年龄(岁)
	private String shsf_xyqk_jynl; // 吸烟情况--戒烟年龄(岁)
	private String shfs_yjqk_jjnl; // 饮酒情况--戒酒年龄
	private String shfs_yjqk_ksyjnl; // 饮酒情况--开始饮酒年龄
	private String shfs_yjqk_sfcjj; // 饮酒情况--近一年内是否曾醉酒(1-是，0-否)
	private String shfs_yjzl_; // 饮酒情况--饮酒种类（1--白酒，2--啤酒，3--红酒，4--黄酒，5--其他）
	private String shfs_yjzl_qt0000; // 饮酒情况--饮酒种类--其他
	private String shfs_zybl_qk; // 职业暴露--情况（是否）
	@JsonView({ IFindGroup.class })
	private String yyid00; // 机构ID
	@JsonView({ IFindGroup.class })
	private String jktjcs; // 健康体检次数
	private String sfhs_zybl_hxpcsnr; // 职业暴露--化学品--防护措施内容
	private String sfhs_zybl_dwcsnr; // 职业暴露--毒物--防护措施内容
	private String sfhs_zybl_sxcsnr; // 职业暴露--射线--防护措施内容
	private String shfs_tydl_dlpl; // 锻炼频率(0-请选择..,1-每天,2-每周一次以上,3-偶尔,4-不锻炼)
	private String shfs_tydl_mcdlsj; // 每次锻炼时间（分钟）
	private String shfs_tydl_dlfs; // 锻炼方式
	private String shsf_xyqk_xyzk; // 吸烟情况--吸烟状况(1-从不吸烟,2-已戒烟,3-吸烟,其他任意值-请选择..)
	private String shsf_xyqk_rxyl; // 吸烟情况--日吸烟量(平均/支)
	private String shfs_yjqk_yjpl; // 饮酒情况--
												// 饮酒频率(0-请选择..,1-从不,2-偶尔,3-经常,4-每天)
	private String shfs_yjqk_ryjl; // 饮酒情况--日饮酒量(平均/两)
	private String shfs_yjqk_sfjj; // 饮酒情况--是否戒酒(1-未戒酒,2-已戒酒)
	private String sfhs_zybl_fc;// 职业暴露--粉尘
	private String sfhs_zybl_fccs;// 职业暴露--粉尘--防护措施（1--无，2--有）
	private String sfhs_zybl_qt;// 职业暴露--其他
	private String sfhs_zybl_qtcs;// 职业暴露--其他--防护措施（1--无，2--有）
	private String sfhs_zybl_fccsnr;// 职业暴露--粉尘--防护措施内容
	private String sfhs_zybl_qtcsnr;// 职业暴露--其他--防护措施内容

	public String getSfhs_zybl_fc() {
		return sfhs_zybl_fc;
	}

	public void setSfhs_zybl_fc(String sfhs_zybl_fc) {
		this.sfhs_zybl_fc = sfhs_zybl_fc;
	}

	public String getSfhs_zybl_fccs() {
		return sfhs_zybl_fccs;
	}

	public void setSfhs_zybl_fccs(String sfhs_zybl_fccs) {
		this.sfhs_zybl_fccs = sfhs_zybl_fccs;
	}

	public String getSfhs_zybl_qt() {
		return sfhs_zybl_qt;
	}

	public void setSfhs_zybl_qt(String sfhs_zybl_qt) {
		this.sfhs_zybl_qt = sfhs_zybl_qt;
	}

	public String getSfhs_zybl_qtcs() {
		return sfhs_zybl_qtcs;
	}

	public void setSfhs_zybl_qtcs(String sfhs_zybl_qtcs) {
		this.sfhs_zybl_qtcs = sfhs_zybl_qtcs;
	}

	public String getSfhs_zybl_fccsnr() {
		return sfhs_zybl_fccsnr;
	}

	public void setSfhs_zybl_fccsnr(String sfhs_zybl_fccsnr) {
		this.sfhs_zybl_fccsnr = sfhs_zybl_fccsnr;
	}

	public String getSfhs_zybl_qtcsnr() {
		return sfhs_zybl_qtcsnr;
	}

	public void setSfhs_zybl_qtcsnr(String sfhs_zybl_qtcsnr) {
		this.sfhs_zybl_qtcsnr = sfhs_zybl_qtcsnr;
	}

	/**
	 * 编号
	 *
	 * @hibernate.property column="jktj_sfhs_id"
	 * @return jktj_sfhs_id
	 */
	public String getJktj_sfhs_id() {
		return this.jktj_sfhs_id;
	}

	/**
	 * 居民档案号
	 *
	 * @hibernate.property column="df_id"
	 * @return df_id
	 */
	public String getDf_id() {
		return this.df_id;
	}

	/**
	 * 职业暴露--具体职业
	 *
	 * @hibernate.property column="sfhs_zybl_jtzy"
	 * @return sfhs_zybl_jtzy
	 */
	public String getSfhs_zybl_jtzy() {
		return this.sfhs_zybl_jtzy;
	}

	/**
	 * 职业暴露--从业时间（年）
	 *
	 * @hibernate.property column="sfhs_zybl_cysj"
	 * @return sfhs_zybl_cysj
	 */
	public String getSfhs_zybl_cysj() {
		return this.sfhs_zybl_cysj;
	}

	/**
	 * 职业暴露--化学品
	 *
	 * @hibernate.property column="sfhs_zybl_hxp"
	 * @return sfhs_zybl_hxp
	 */
	public String getSfhs_zybl_hxp() {
		return this.sfhs_zybl_hxp;
	}

	/**
	 * 职业暴露--化学品--防护措施（1--无，2--有）
	 *
	 * @hibernate.property column="sfhs_zybl_hxpcs"
	 * @return sfhs_zybl_hxpcs
	 */
	public String getSfhs_zybl_hxpcs() {
		return this.sfhs_zybl_hxpcs;
	}

	/**
	 * 职业暴露--毒物
	 *
	 * @hibernate.property column="sfhs_zybl_dw"
	 * @return sfhs_zybl_dw
	 */
	public String getSfhs_zybl_dw() {
		return this.sfhs_zybl_dw;
	}

	/**
	 * 职业暴露--毒物--防护措施（1--无，2--有）
	 *
	 * @hibernate.property column="sfhs_zybl_dwcs"
	 * @return sfhs_zybl_dwcs
	 */
	public String getSfhs_zybl_dwcs() {
		return this.sfhs_zybl_dwcs;
	}

	/**
	 * 职业暴露--射线
	 *
	 * @hibernate.property column="sfhs_zybl_sx"
	 * @return sfhs_zybl_sx
	 */
	public String getSfhs_zybl_sx() {
		return this.sfhs_zybl_sx;
	}

	/**
	 * 职业暴露--射线--防护措施（1--无，2--有）
	 *
	 * @hibernate.property column="sfhs_zybl_sxcs"
	 * @return sfhs_zybl_sxcs
	 */
	public String getSfhs_zybl_sxcs() {
		return this.sfhs_zybl_sxcs;
	}

	/**
	 * 体育锻炼--坚持锻炼时间
	 *
	 * @hibernate.property column="shfs_tydl_jcdlsj"
	 * @return shfs_tydl_jcdlsj
	 */
	public String getShfs_tydl_jcdlsj() {
		return this.shfs_tydl_jcdlsj;
	}

	/**
	 * 饮食习惯（1--荤素均衡，2--荤食为主，3--素食为主 ，4--嗜盐，5--嗜油，6--嗜糖）
	 *
	 * @hibernate.property column="shsf_ysxg"
	 * @return shsf_ysxg
	 */
	public String getShsf_ysxg() {
		return this.shsf_ysxg;
	}

	/**
	 * 吸烟情况--吸烟年龄
	 *
	 * @hibernate.property column="shsf_xyqk_xynl"
	 * @return shsf_xyqk_xynl
	 */
	public String getShsf_xyqk_xynl() {
		return this.shsf_xyqk_xynl;
	}

	/**
	 * 吸烟情况--戒烟年龄
	 *
	 * @hibernate.property column="shsf_xyqk_jynl"
	 * @return shsf_xyqk_jynl
	 */
	public String getShsf_xyqk_jynl() {
		return this.shsf_xyqk_jynl;
	}

	/**
	 * 饮酒情况--戒酒年龄
	 *
	 * @hibernate.property column="shfs_yjqk_jjnl"
	 * @return shfs_yjqk_jjnl
	 */
	public String getShfs_yjqk_jjnl() {
		return this.shfs_yjqk_jjnl;
	}

	/**
	 * 饮酒情况--开始饮酒年龄
	 *
	 * @hibernate.property column="shfs_yjqk_ksyjnl"
	 * @return shfs_yjqk_ksyjnl
	 */
	public String getShfs_yjqk_ksyjnl() {
		return this.shfs_yjqk_ksyjnl;
	}

	/**
	 * 饮酒情况--近一年内是否曾醉酒
	 *
	 * @hibernate.property column="shfs_yjqk_sfcjj"
	 * @return shfs_yjqk_sfcjj
	 */
	public String getShfs_yjqk_sfcjj() {
		return this.shfs_yjqk_sfcjj;
	}

	/**
	 * 饮酒情况--饮酒种类（1--白酒，2--啤酒，3--红酒，4--黄酒，5--其他）
	 *
	 * @hibernate.property column="shfs_yjzl_"
	 * @return shfs_yjzl_
	 */
	public String getShfs_yjzl_() {
		return this.shfs_yjzl_;
	}

	/**
	 * 饮酒情况--饮酒种类--其他
	 *
	 * @hibernate.property column="shfs_yjzl_qt0000"
	 * @return shfs_yjzl_qt0000
	 */
	public String getShfs_yjzl_qt0000() {
		return this.shfs_yjzl_qt0000;
	}

	/**
	 * 职业暴露--情况（是否）
	 *
	 * @hibernate.property column="shfs_zybl_qk"
	 * @return shfs_zybl_qk
	 */
	public String getShfs_zybl_qk() {
		return this.shfs_zybl_qk;
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
	 * 健康体检次数
	 *
	 * @hibernate.property column="jktjcs"
	 * @return jktjcs
	 */
	public String getJktjcs() {
		return this.jktjcs;
	}

	/**
	 * 职业暴露--化学品--防护措施内容
	 *
	 * @hibernate.property column="sfhs_zybl_hxpcsnr"
	 * @return sfhs_zybl_hxpcsnr
	 */
	public String getSfhs_zybl_hxpcsnr() {
		return this.sfhs_zybl_hxpcsnr;
	}

	/**
	 * 职业暴露--毒物--防护措施内容
	 *
	 * @hibernate.property column="sfhs_zybl_dwcsnr"
	 * @return sfhs_zybl_dwcsnr
	 */
	public String getSfhs_zybl_dwcsnr() {
		return this.sfhs_zybl_dwcsnr;
	}

	/**
	 * 职业暴露--射线--防护措施内容
	 *
	 * @hibernate.property column="sfhs_zybl_sxcsnr"
	 * @return sfhs_zybl_sxcsnr
	 */
	public String getSfhs_zybl_sxcsnr() {
		return this.sfhs_zybl_sxcsnr;
	}

	/**
	 * 锻炼频率
	 *
	 * @hibernate.property column="shfs_tydl_dlpl"
	 * @return shfs_tydl_dlpl
	 */
	public String getShfs_tydl_dlpl() {
		return this.shfs_tydl_dlpl;
	}

	/**
	 * 每次锻炼时间（分钟）
	 *
	 * @hibernate.property column="shfs_tydl_mcdlsj"
	 * @return shfs_tydl_mcdlsj
	 */
	public String getShfs_tydl_mcdlsj() {
		return this.shfs_tydl_mcdlsj;
	}

	/**
	 * 锻炼方式
	 *
	 * @hibernate.property column="shfs_tydl_dlfs"
	 * @return shfs_tydl_dlfs
	 */
	public String getShfs_tydl_dlfs() {
		return this.shfs_tydl_dlfs;
	}

	/**
	 * 吸烟情况--吸烟状况
	 *
	 * @hibernate.property column="shsf_xyqk_xyzk"
	 * @return shsf_xyqk_xyzk
	 */
	public String getShsf_xyqk_xyzk() {
		return this.shsf_xyqk_xyzk;
	}

	/**
	 * 吸烟情况--日吸烟量
	 *
	 * @hibernate.property column="shsf_xyqk_rxyl"
	 * @return shsf_xyqk_rxyl
	 */
	public String getShsf_xyqk_rxyl() {
		return this.shsf_xyqk_rxyl;
	}

	/**
	 * 饮酒情况--饮酒频率
	 *
	 * @hibernate.property column="shfs_yjqk_yjpl"
	 * @return shfs_yjqk_yjpl
	 */
	public String getShfs_yjqk_yjpl() {
		return this.shfs_yjqk_yjpl;
	}

	/**
	 * 饮酒情况--日饮酒量
	 *
	 * @hibernate.property column="shfs_yjqk_ryjl"
	 * @return shfs_yjqk_ryjl
	 */
	public String getShfs_yjqk_ryjl() {
		return this.shfs_yjqk_ryjl;
	}

	/**
	 * 饮酒情况--是否戒酒
	 *
	 * @hibernate.property column="shfs_yjqk_sfjj"
	 * @return shfs_yjqk_sfjj
	 */
	public String getShfs_yjqk_sfjj() {
		return this.shfs_yjqk_sfjj;
	}

	/**
	 * @param jktj_sfhs_id
	 *            编号
	 */
	public void setJktj_sfhs_id(String jktj_sfhs_id) {
		this.jktj_sfhs_id = jktj_sfhs_id;
	}

	/**
	 * @param df_id
	 *            居民档案号
	 */
	public void setDf_id(String df_id) {
		this.df_id = df_id;
	}

	/**
	 * @param sfhs_zybl_jtzy
	 *            职业暴露--具体职业
	 */
	public void setSfhs_zybl_jtzy(String sfhs_zybl_jtzy) {
		this.sfhs_zybl_jtzy = sfhs_zybl_jtzy;
	}

	/**
	 * @param sfhs_zybl_cysj
	 *            职业暴露--从业时间（年）
	 */
	public void setSfhs_zybl_cysj(String sfhs_zybl_cysj) {
		this.sfhs_zybl_cysj = sfhs_zybl_cysj;
	}

	/**
	 * @param sfhs_zybl_hxp
	 *            职业暴露--化学品
	 */
	public void setSfhs_zybl_hxp(String sfhs_zybl_hxp) {
		this.sfhs_zybl_hxp = sfhs_zybl_hxp;
	}

	/**
	 * @param sfhs_zybl_hxpcs
	 *            职业暴露--化学品--防护措施（1--无，2--有）
	 */
	public void setSfhs_zybl_hxpcs(String sfhs_zybl_hxpcs) {
		this.sfhs_zybl_hxpcs = sfhs_zybl_hxpcs;
	}

	/**
	 * @param sfhs_zybl_dw
	 *            职业暴露--毒物
	 */
	public void setSfhs_zybl_dw(String sfhs_zybl_dw) {
		this.sfhs_zybl_dw = sfhs_zybl_dw;
	}

	/**
	 * @param sfhs_zybl_dwcs
	 *            职业暴露--毒物--防护措施（1--无，2--有）
	 */
	public void setSfhs_zybl_dwcs(String sfhs_zybl_dwcs) {
		this.sfhs_zybl_dwcs = sfhs_zybl_dwcs;
	}

	/**
	 * @param sfhs_zybl_sx
	 *            职业暴露--射线
	 */
	public void setSfhs_zybl_sx(String sfhs_zybl_sx) {
		this.sfhs_zybl_sx = sfhs_zybl_sx;
	}

	/**
	 * @param sfhs_zybl_sxcs
	 *            职业暴露--射线--防护措施（1--无，2--有）
	 */
	public void setSfhs_zybl_sxcs(String sfhs_zybl_sxcs) {
		this.sfhs_zybl_sxcs = sfhs_zybl_sxcs;
	}

	/**
	 * @param shfs_tydl_jcdlsj
	 *            体育锻炼--坚持锻炼时间
	 */
	public void setShfs_tydl_jcdlsj(String shfs_tydl_jcdlsj) {
		this.shfs_tydl_jcdlsj = shfs_tydl_jcdlsj;
	}

	/**
	 * @param shsf_ysxg
	 *            饮食习惯（1--荤素均衡，2--荤食为主，3--素食为主 ，4--嗜盐，5--嗜油，6--嗜糖）
	 */
	public void setShsf_ysxg(String shsf_ysxg) {
		this.shsf_ysxg = shsf_ysxg;
	}

	/**
	 * @param shsf_xyqk_xynl
	 *            吸烟情况--吸烟年龄
	 */
	public void setShsf_xyqk_xynl(String shsf_xyqk_xynl) {
		this.shsf_xyqk_xynl = shsf_xyqk_xynl;
	}

	/**
	 * @param shsf_xyqk_jynl
	 *            吸烟情况--戒烟年龄
	 */
	public void setShsf_xyqk_jynl(String shsf_xyqk_jynl) {
		this.shsf_xyqk_jynl = shsf_xyqk_jynl;
	}

	/**
	 * @param shfs_yjqk_jjnl
	 *            饮酒情况--戒酒年龄
	 */
	public void setShfs_yjqk_jjnl(String shfs_yjqk_jjnl) {
		this.shfs_yjqk_jjnl = shfs_yjqk_jjnl;
	}

	/**
	 * @param shfs_yjqk_ksyjnl
	 *            饮酒情况--开始饮酒年龄
	 */
	public void setShfs_yjqk_ksyjnl(String shfs_yjqk_ksyjnl) {
		this.shfs_yjqk_ksyjnl = shfs_yjqk_ksyjnl;
	}

	/**
	 * @param shfs_yjqk_sfcjj
	 *            饮酒情况--近一年内是否曾醉酒
	 */
	public void setShfs_yjqk_sfcjj(String shfs_yjqk_sfcjj) {
		this.shfs_yjqk_sfcjj = shfs_yjqk_sfcjj;
	}

	/**
	 * @param shfs_yjzl_
	 *            饮酒情况--饮酒种类（1--白酒，2--啤酒，3--红酒，4--黄酒，5--其他）
	 */
	public void setShfs_yjzl_(String shfs_yjzl_) {
		this.shfs_yjzl_ = shfs_yjzl_;
	}

	/**
	 * @param shfs_yjzl_qt0000
	 *            饮酒情况--饮酒种类--其他
	 */
	public void setShfs_yjzl_qt0000(String shfs_yjzl_qt0000) {
		this.shfs_yjzl_qt0000 = shfs_yjzl_qt0000;
	}

	/**
	 * @param shfs_zybl_qk
	 *            职业暴露--情况（是否）
	 */
	public void setShfs_zybl_qk(String shfs_zybl_qk) {
		this.shfs_zybl_qk = shfs_zybl_qk;
	}

	/**
	 * @param yyid00
	 *            机构ID
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

	/**
	 * @param sfhs_zybl_hxpcsnr
	 *            职业暴露--化学品--防护措施内容
	 */
	public void setSfhs_zybl_hxpcsnr(String sfhs_zybl_hxpcsnr) {
		this.sfhs_zybl_hxpcsnr = sfhs_zybl_hxpcsnr;
	}

	/**
	 * @param sfhs_zybl_dwcsnr
	 *            职业暴露--毒物--防护措施内容
	 */
	public void setSfhs_zybl_dwcsnr(String sfhs_zybl_dwcsnr) {
		this.sfhs_zybl_dwcsnr = sfhs_zybl_dwcsnr;
	}

	/**
	 * @param sfhs_zybl_sxcsnr
	 *            职业暴露--射线--防护措施内容
	 */
	public void setSfhs_zybl_sxcsnr(String sfhs_zybl_sxcsnr) {
		this.sfhs_zybl_sxcsnr = sfhs_zybl_sxcsnr;
	}

	/**
	 * @param shfs_tydl_dlpl
	 *            锻炼频率
	 */
	public void setShfs_tydl_dlpl(String shfs_tydl_dlpl) {
		this.shfs_tydl_dlpl = shfs_tydl_dlpl;
	}

	/**
	 * @param shfs_tydl_mcdlsj
	 *            每次锻炼时间（分钟）
	 */
	public void setShfs_tydl_mcdlsj(String shfs_tydl_mcdlsj) {
		this.shfs_tydl_mcdlsj = shfs_tydl_mcdlsj;
	}

	/**
	 * @param shfs_tydl_dlfs
	 *            锻炼方式
	 */
	public void setShfs_tydl_dlfs(String shfs_tydl_dlfs) {
		this.shfs_tydl_dlfs = shfs_tydl_dlfs;
	}

	/**
	 * @param shsf_xyqk_xyzk
	 *            吸烟情况--吸烟状况
	 */
	public void setShsf_xyqk_xyzk(String shsf_xyqk_xyzk) {
		this.shsf_xyqk_xyzk = shsf_xyqk_xyzk;
	}

	/**
	 * @param shsf_xyqk_rxyl
	 *            吸烟情况--日吸烟量
	 */
	public void setShsf_xyqk_rxyl(String shsf_xyqk_rxyl) {
		this.shsf_xyqk_rxyl = shsf_xyqk_rxyl;
	}

	/**
	 * @param shfs_yjqk_yjpl
	 *            饮酒情况--饮酒频率
	 */
	public void setShfs_yjqk_yjpl(String shfs_yjqk_yjpl) {
		this.shfs_yjqk_yjpl = shfs_yjqk_yjpl;
	}

	/**
	 * @param shfs_yjqk_ryjl
	 *            饮酒情况--日饮酒量
	 */
	public void setShfs_yjqk_ryjl(String shfs_yjqk_ryjl) {
		this.shfs_yjqk_ryjl = shfs_yjqk_ryjl;
	}

	/**
	 * @param shfs_yjqk_sfjj
	 *            饮酒情况--是否戒酒
	 */
	public void setShfs_yjqk_sfjj(String shfs_yjqk_sfjj) {
		this.shfs_yjqk_sfjj = shfs_yjqk_sfjj;
	}

	public String toString() {
		return (new ToStringBuilder(this)).append("jktj_sfhs_id", getJktj_sfhs_id()).toString();
	}

	public int hashCode() {
		return (new HashCodeBuilder(17, 37)).append(getJktj_sfhs_id()).toHashCode();
	}

	public boolean equals(Object o) {
		if (!(o instanceof jktj_shfsDTO)) {
			return false;
		}
		if (o == this) {
            return true;
        }
		jktj_shfsDTO me = (jktj_shfsDTO) o;
		return new EqualsBuilder().append(getJktj_sfhs_id(), me.getJktj_sfhs_id()).isEquals();
	}

	public static interface IFindGroup {
	}
}
