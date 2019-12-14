package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;


/**
 * <p>
 * Title: Jktj_shfs
 * </p>
 * <p>
 * Description:健康体检--生活方式
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 *
 * @hibernate.class table="Jktj_shfs"
 * @author 劳动力99开发小组
 * @version 1.0
 */

public class Jktj_shfs  {
	/**
	 * 默认构造函数
	 */
	public Jktj_shfs() {
	}

	private String jktj_sfhs_id; // 编号
	private String df_id; // 居民档案号
	private String sfhs_zybl_jtzy; // 职业暴露--具体职业
	private String sfhs_zybl_cysj; // 职业暴露--从业时间（年）
	private String sfhs_zybl_hxp; // 职业暴露--化学品
	private String sfhs_zybl_hxpcs; // 职业暴露--化学品--防护措施（1--无，2--有）
	private String sfhs_zybl_dw; // 职业暴露--毒物
	private String sfhs_zybl_dwcs; // 职业暴露--毒物--防护措施（1--无，2--有）
	private String sfhs_zybl_sx; // 职业暴露--射线
	private String sfhs_zybl_sxcs; // 职业暴露--射线--防护措施（1--无，2--有）
	private String shfs_tydl_jcdlsj; // 体育锻炼--坚持锻炼时间
	private String shsf_ysxg; // 饮食习惯（1--荤素均衡，2--荤食为主，3--素食为主
										// ，4--嗜盐，5--嗜油，6--嗜糖）
	private String shsf_xyqk_xynl; // 吸烟情况--吸烟年龄
	private String shsf_xyqk_jynl; // 吸烟情况--戒烟年龄
	private String shfs_yjqk_jjnl; // 饮酒情况--戒酒年龄
	private String shfs_yjqk_ksyjnl; // 饮酒情况--开始饮酒年龄
	private String shfs_yjqk_sfcjj; // 饮酒情况--近一年内是否曾醉酒
	private String shfs_yjzl_; // 饮酒情况--饮酒种类（1--白酒，2--啤酒，3--红酒，4--黄酒，5--其他）
	private String shfs_yjzl_qt0000; // 饮酒情况--饮酒种类--其他
	private String shfs_zybl_qk; // 职业暴露--情况（是否）
	private String yyid00; // 机构ID
	private String jktjcs; // 健康体检次数
	private String sfhs_zybl_hxpcsnr; // 职业暴露--化学品--防护措施内容
	private String sfhs_zybl_dwcsnr; // 职业暴露--毒物--防护措施内容
	private String sfhs_zybl_sxcsnr; // 职业暴露--射线--防护措施内容
	private String shfs_tydl_dlpl; // 锻炼频率
	private String shfs_tydl_mcdlsj; // 每次锻炼时间（分钟）
	private String shfs_tydl_dlfs; // 锻炼方式
	private String shsf_xyqk_xyzk; // 吸烟情况--吸烟状况
	private String shsf_xyqk_rxyl; // 吸烟情况--日吸烟量
	private String shfs_yjqk_yjpl; // 饮酒情况--饮酒频率
	private String shfs_yjqk_ryjl; // 饮酒情况--日饮酒量
	private String shfs_yjqk_sfjj; // 饮酒情况--是否戒酒
	private String sfhs_zybl_fc;// 职业暴露--粉尘
	private String sfhs_zybl_fccs;// 职业暴露--粉尘--防护措施（1--无，2--有）
	private String sfhs_zybl_qt;// 职业暴露--其他
	private String sfhs_zybl_qtcs;// 职业暴露--其他--防护措施（1--无，2--有）
	private String sfhs_zybl_fccsnr;// 职业暴露--粉尘--防护措施内容
	private String sfhs_zybl_qtcsnr;// 职业暴露--其他--防护措施内容

	@Id
	@Column(name = "JKTJ_SFHS_ID")
	public String getJktj_sfhs_id() {
		return jktj_sfhs_id;
	}

	public void setJktj_sfhs_id(String jktj_sfhs_id) {
		this.jktj_sfhs_id = jktj_sfhs_id;
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
	@Column(name = "SFHS_ZYBL_JTZY")
	public String getSfhs_zybl_jtzy() {
		return sfhs_zybl_jtzy;
	}

	public void setSfhs_zybl_jtzy(String sfhs_zybl_jtzy) {
		this.sfhs_zybl_jtzy = sfhs_zybl_jtzy;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_CYSJ")
	public String getSfhs_zybl_cysj() {
		return sfhs_zybl_cysj;
	}

	public void setSfhs_zybl_cysj(String sfhs_zybl_cysj) {
		this.sfhs_zybl_cysj = sfhs_zybl_cysj;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_HXP")
	public String getSfhs_zybl_hxp() {
		return sfhs_zybl_hxp;
	}

	public void setSfhs_zybl_hxp(String sfhs_zybl_hxp) {
		this.sfhs_zybl_hxp = sfhs_zybl_hxp;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_HXPCS")
	public String getSfhs_zybl_hxpcs() {
		return sfhs_zybl_hxpcs;
	}

	public void setSfhs_zybl_hxpcs(String sfhs_zybl_hxpcs) {
		this.sfhs_zybl_hxpcs = sfhs_zybl_hxpcs;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_DW")
	public String getSfhs_zybl_dw() {
		return sfhs_zybl_dw;
	}

	public void setSfhs_zybl_dw(String sfhs_zybl_dw) {
		this.sfhs_zybl_dw = sfhs_zybl_dw;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_DWCS")
	public String getSfhs_zybl_dwcs() {
		return sfhs_zybl_dwcs;
	}

	public void setSfhs_zybl_dwcs(String sfhs_zybl_dwcs) {
		this.sfhs_zybl_dwcs = sfhs_zybl_dwcs;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_SX")
	public String getSfhs_zybl_sx() {
		return sfhs_zybl_sx;
	}

	public void setSfhs_zybl_sx(String sfhs_zybl_sx) {
		this.sfhs_zybl_sx = sfhs_zybl_sx;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_SXCS")
	public String getSfhs_zybl_sxcs() {
		return sfhs_zybl_sxcs;
	}

	public void setSfhs_zybl_sxcs(String sfhs_zybl_sxcs) {
		this.sfhs_zybl_sxcs = sfhs_zybl_sxcs;
	}

	@Basic
	@Column(name = "SHFS_TYDL_JCDLSJ")
	public String getShfs_tydl_jcdlsj() {
		return shfs_tydl_jcdlsj;
	}

	public void setShfs_tydl_jcdlsj(String shfs_tydl_jcdlsj) {
		this.shfs_tydl_jcdlsj = shfs_tydl_jcdlsj;
	}

	@Basic
	@Column(name = "SHSF_YSXG")
	public String getShsf_ysxg() {
		return shsf_ysxg;
	}

	public void setShsf_ysxg(String shsf_ysxg) {
		this.shsf_ysxg = shsf_ysxg;
	}

	@Basic
	@Column(name = "SHSF_XYQK_XYNL")
	public String getShsf_xyqk_xynl() {
		return shsf_xyqk_xynl;
	}

	public void setShsf_xyqk_xynl(String shsf_xyqk_xynl) {
		this.shsf_xyqk_xynl = shsf_xyqk_xynl;
	}

	@Basic
	@Column(name = "SHSF_XYQK_JYNL")
	public String getShsf_xyqk_jynl() {
		return shsf_xyqk_jynl;
	}

	public void setShsf_xyqk_jynl(String shsf_xyqk_jynl) {
		this.shsf_xyqk_jynl = shsf_xyqk_jynl;
	}

	@Basic
	@Column(name = "SHFS_YJQK_JJNL")
	public String getShfs_yjqk_jjnl() {
		return shfs_yjqk_jjnl;
	}

	public void setShfs_yjqk_jjnl(String shfs_yjqk_jjnl) {
		this.shfs_yjqk_jjnl = shfs_yjqk_jjnl;
	}

	@Basic
	@Column(name = "SHFS_YJQK_KSYJNL")
	public String getShfs_yjqk_ksyjnl() {
		return shfs_yjqk_ksyjnl;
	}

	public void setShfs_yjqk_ksyjnl(String shfs_yjqk_ksyjnl) {
		this.shfs_yjqk_ksyjnl = shfs_yjqk_ksyjnl;
	}

	@Basic
	@Column(name = "SHFS_YJQK_SFCJJ")
	public String getShfs_yjqk_sfcjj() {
		return shfs_yjqk_sfcjj;
	}

	public void setShfs_yjqk_sfcjj(String shfs_yjqk_sfcjj) {
		this.shfs_yjqk_sfcjj = shfs_yjqk_sfcjj;
	}

	@Basic
	@Column(name = "SHFS_YJZL_")
	public String getShfs_yjzl_() {
		return shfs_yjzl_;
	}

	public void setShfs_yjzl_(String shfs_yjzl_) {
		this.shfs_yjzl_ = shfs_yjzl_;
	}

	@Basic
	@Column(name = "SHFS_YJZL_QT0000")
	public String getShfs_yjzl_qt0000() {
		return shfs_yjzl_qt0000;
	}

	public void setShfs_yjzl_qt0000(String shfs_yjzl_qt0000) {
		this.shfs_yjzl_qt0000 = shfs_yjzl_qt0000;
	}

	@Basic
	@Column(name = "SHFS_ZYBL_QK")
	public String getShfs_zybl_qk() {
		return shfs_zybl_qk;
	}

	public void setShfs_zybl_qk(String shfs_zybl_qk) {
		this.shfs_zybl_qk = shfs_zybl_qk;
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

	@Basic
	@Column(name = "SFHS_ZYBL_HXPCSNR")
	public String getSfhs_zybl_hxpcsnr() {
		return sfhs_zybl_hxpcsnr;
	}

	public void setSfhs_zybl_hxpcsnr(String sfhs_zybl_hxpcsnr) {
		this.sfhs_zybl_hxpcsnr = sfhs_zybl_hxpcsnr;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_DWCSNR")
	public String getSfhs_zybl_dwcsnr() {
		return sfhs_zybl_dwcsnr;
	}

	public void setSfhs_zybl_dwcsnr(String sfhs_zybl_dwcsnr) {
		this.sfhs_zybl_dwcsnr = sfhs_zybl_dwcsnr;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_SXCSNR")
	public String getSfhs_zybl_sxcsnr() {
		return sfhs_zybl_sxcsnr;
	}

	public void setSfhs_zybl_sxcsnr(String sfhs_zybl_sxcsnr) {
		this.sfhs_zybl_sxcsnr = sfhs_zybl_sxcsnr;
	}

	@Basic
	@Column(name = "SHFS_TYDL_DLPL")
	public String getShfs_tydl_dlpl() {
		return shfs_tydl_dlpl;
	}

	public void setShfs_tydl_dlpl(String shfs_tydl_dlpl) {
		this.shfs_tydl_dlpl = shfs_tydl_dlpl;
	}

	@Basic
	@Column(name = "SHFS_TYDL_MCDLSJ")
	public String getShfs_tydl_mcdlsj() {
		return shfs_tydl_mcdlsj;
	}

	public void setShfs_tydl_mcdlsj(String shfs_tydl_mcdlsj) {
		this.shfs_tydl_mcdlsj = shfs_tydl_mcdlsj;
	}

	@Basic
	@Column(name = "SHFS_TYDL_DLFS")
	public String getShfs_tydl_dlfs() {
		return shfs_tydl_dlfs;
	}

	public void setShfs_tydl_dlfs(String shfs_tydl_dlfs) {
		this.shfs_tydl_dlfs = shfs_tydl_dlfs;
	}

	@Basic
	@Column(name = "SHSF_XYQK_XYZK")
	public String getShsf_xyqk_xyzk() {
		return shsf_xyqk_xyzk;
	}

	public void setShsf_xyqk_xyzk(String shsf_xyqk_xyzk) {
		this.shsf_xyqk_xyzk = shsf_xyqk_xyzk;
	}

	@Basic
	@Column(name = "SHSF_XYQK_RXYL")
	public String getShsf_xyqk_rxyl() {
		return shsf_xyqk_rxyl;
	}

	public void setShsf_xyqk_rxyl(String shsf_xyqk_rxyl) {
		this.shsf_xyqk_rxyl = shsf_xyqk_rxyl;
	}

	@Basic
	@Column(name = "SHFS_YJQK_YJPL")
	public String getShfs_yjqk_yjpl() {
		return shfs_yjqk_yjpl;
	}

	public void setShfs_yjqk_yjpl(String shfs_yjqk_yjpl) {
		this.shfs_yjqk_yjpl = shfs_yjqk_yjpl;
	}

	@Basic
	@Column(name = "SHFS_YJQK_RYJL")
	public String getShfs_yjqk_ryjl() {
		return shfs_yjqk_ryjl;
	}

	public void setShfs_yjqk_ryjl(String shfs_yjqk_ryjl) {
		this.shfs_yjqk_ryjl = shfs_yjqk_ryjl;
	}

	@Basic
	@Column(name = "SHFS_YJQK_SFJJ")
	public String getShfs_yjqk_sfjj() {
		return shfs_yjqk_sfjj;
	}

	public void setShfs_yjqk_sfjj(String shfs_yjqk_sfjj) {
		this.shfs_yjqk_sfjj = shfs_yjqk_sfjj;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_FC")
	public String getSfhs_zybl_fc() {
		return sfhs_zybl_fc;
	}

	public void setSfhs_zybl_fc(String sfhs_zybl_fc) {
		this.sfhs_zybl_fc = sfhs_zybl_fc;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_FCCS")
	public String getSfhs_zybl_fccs() {
		return sfhs_zybl_fccs;
	}

	public void setSfhs_zybl_fccs(String sfhs_zybl_fccs) {
		this.sfhs_zybl_fccs = sfhs_zybl_fccs;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_QT")
	public String getSfhs_zybl_qt() {
		return sfhs_zybl_qt;
	}

	public void setSfhs_zybl_qt(String sfhs_zybl_qt) {
		this.sfhs_zybl_qt = sfhs_zybl_qt;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_QTCS")
	public String getSfhs_zybl_qtcs() {
		return sfhs_zybl_qtcs;
	}

	public void setSfhs_zybl_qtcs(String sfhs_zybl_qtcs) {
		this.sfhs_zybl_qtcs = sfhs_zybl_qtcs;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_FCCSNR")
	public String getSfhs_zybl_fccsnr() {
		return sfhs_zybl_fccsnr;
	}

	public void setSfhs_zybl_fccsnr(String sfhs_zybl_fccsnr) {
		this.sfhs_zybl_fccsnr = sfhs_zybl_fccsnr;
	}

	@Basic
	@Column(name = "SFHS_ZYBL_QTCSNR")
	public String getSfhs_zybl_qtcsnr() {
		return sfhs_zybl_qtcsnr;
	}

	public void setSfhs_zybl_qtcsnr(String sfhs_zybl_qtcsnr) {
		this.sfhs_zybl_qtcsnr = sfhs_zybl_qtcsnr;
	}

}