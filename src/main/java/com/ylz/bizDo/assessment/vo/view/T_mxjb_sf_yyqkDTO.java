/*
 * CopyRight: StartTech 2010 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>T_mxjb_sf_yyqkDTO</code>.
 */
package com.ylz.bizDo.assessment.vo.view;

import java.io.Serializable;

/**
 * <p>
 * Title: T_mxjb_sf_yyqkDTO
 * </p>
 * <p>
 * Description:慢性疾病--用药情况
 * </p>
 * <p>
 * Copyright:Copyright(c)2010
 * </p>
 * <p>
 * Company:易联众技术股份有限公司
 * </p>
 *
 * @author
 * @version 1.0
 */

public class T_mxjb_sf_yyqkDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1948072811348581804L;
	private java.lang.String ywbh_id; // 药物编号
	private java.lang.String ywmc; // 药物名称
	private java.lang.String ywyf; // 用法(1-qod,2-prn,3-qn,4-qd,5-bid,6-tid,7-qid,其他任意值-请选择)
	private java.lang.String ywyl; // 用量
	private java.lang.String yysj; // 用药时间
	private java.lang.String fyycx; // 服药依从性(1-规律,2-间断,其他任意值-不服药)
	private java.lang.String ref_sf_id; // 随访编号
	private java.lang.String yyid00; // 机构ID
	private java.lang.String jktjcs; // 健康体检次数
	private java.lang.String df_id; // 居民编号
	private java.lang.String sfmb; // 是否模板

	public java.lang.String getYwbh_id() {
		return ywbh_id;
	}

	public void setYwbh_id(java.lang.String ywbh_id) {
		this.ywbh_id = ywbh_id;
	}

	public java.lang.String getYwmc() {
		return ywmc;
	}

	public void setYwmc(java.lang.String ywmc) {
		this.ywmc = ywmc;
	}

	public java.lang.String getYwyf() {
		return ywyf;
	}

	public void setYwyf(java.lang.String ywyf) {
		this.ywyf = ywyf;
	}

	public java.lang.String getYwyl() {
		return ywyl;
	}

	public void setYwyl(java.lang.String ywyl) {
		this.ywyl = ywyl;
	}

	public java.lang.String getYysj() {
		return yysj;
	}

	public void setYysj(java.lang.String yysj) {
		this.yysj = yysj;
	}

	public java.lang.String getFyycx() {
		return fyycx;
	}

	public void setFyycx(java.lang.String fyycx) {
		this.fyycx = fyycx;
	}

	public java.lang.String getRef_sf_id() {
		return ref_sf_id;
	}

	public void setRef_sf_id(java.lang.String ref_sf_id) {
		this.ref_sf_id = ref_sf_id;
	}

	public java.lang.String getYyid00() {
		return yyid00;
	}

	public void setYyid00(java.lang.String yyid00) {
		this.yyid00 = yyid00;
	}

	public java.lang.String getJktjcs() {
		return jktjcs;
	}

	public void setJktjcs(java.lang.String jktjcs) {
		this.jktjcs = jktjcs;
	}

	public java.lang.String getDf_id() {
		return df_id;
	}

	public void setDf_id(java.lang.String df_id) {
		this.df_id = df_id;
	}

	public java.lang.String getSfmb() {
		return sfmb;
	}

	public void setSfmb(java.lang.String sfmb) {
		this.sfmb = sfmb;
	}
}
