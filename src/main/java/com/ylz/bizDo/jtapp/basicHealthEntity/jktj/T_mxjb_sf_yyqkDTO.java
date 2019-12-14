/*
 * CopyRight: StartTech 2010 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>T_mxjb_sf_yyqkDTO</code>.
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

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
	 * 默认构造函数
	 */
	public T_mxjb_sf_yyqkDTO() {
	}
	private String ywbh_id; // 药物编号
	private String ywmc; // 药物名称
	private String ywyf; // 用法(1-qod,2-prn,3-qn,4-qd,5-bid,6-tid,7-qid,其他任意值-请选择)
	private String ywyl; // 用量
	private String yysj; // 用药时间
	private String fyycx; // 服药依从性(1-规律,2-间断,其他任意值-不服药)
	private String ref_sf_id; // 随访编号
	private String yyid00; // 机构ID
	private String jktjcs; // 健康体检次数
	private String df_id; // 居民编号
	private String sfmb; // 是否模板

	/**
	 * 药物编号
	 *
	 * @return ywbh_id
	 */
	public String getYwbh_id() {
		return this.ywbh_id;
	}

	/**
	 * 药物名称
	 *
	 * @return ywmc
	 */
	public String getYwmc() {
		return this.ywmc;
	}

	/**
	 * 用法
	 *
	 * @return ywyf
	 */
	public String getYwyf() {
		return this.ywyf;
	}

	/**
	 * 用量
	 *
	 * @return ywyl
	 */
	public String getYwyl() {
		return this.ywyl;
	}

	/**
	 * 用药时间
	 *
	 * @return yysj
	 */
	public String getYysj() {
		return this.yysj;
	}

	/**
	 * 服药依从性
	 *
	 * @return fyycx
	 */
	public String getFyycx() {
		return this.fyycx;
	}

	/**
	 * 随访编号
	 *
	 * @return ref_sf_id
	 */
	public String getRef_sf_id() {
		return this.ref_sf_id;
	}

	/**
	 * 机构ID
	 *
	 * @return yyid00
	 */
	public String getYyid00() {
		return this.yyid00;
	}

	/**
	 * 健康体检次数
	 *
	 * @return jktjcs
	 */
	public String getJktjcs() {
		return this.jktjcs;
	}

	/**
	 * 居民编号
	 *
	 * @return df_id
	 */
	public String getDf_id() {
		return this.df_id;
	}

	/**
	 * 是否模板
	 *
	 * @return sfmb
	 */
	public String getSfmb() {
		return this.sfmb;
	}

	/**
	 * @param ywbh_id
	 *            药物编号
	 */
	public void setYwbh_id(String ywbh_id) {
		this.ywbh_id = ywbh_id;
	}

	/**
	 * @param ywmc
	 *            药物名称
	 */
	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}

	/**
	 * @param ywyf
	 *            用法
	 */
	public void setYwyf(String ywyf) {
		this.ywyf = ywyf;
	}

	/**
	 * @param ywyl
	 *            用量
	 */
	public void setYwyl(String ywyl) {
		this.ywyl = ywyl;
	}

	/**
	 * @param yysj
	 *            用药时间
	 */
	public void setYysj(String yysj) {
		this.yysj = yysj;
	}

	/**
	 * @param fyycx
	 *            服药依从性
	 */
	public void setFyycx(String fyycx) {
		this.fyycx = fyycx;
	}

	/**
	 * @param ref_sf_id
	 *            随访编号
	 */
	public void setRef_sf_id(String ref_sf_id) {
		this.ref_sf_id = ref_sf_id;
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
	 * @param df_id
	 *            居民编号
	 */
	public void setDf_id(String df_id) {
		this.df_id = df_id;
	}

	/**
	 * @param sfmb
	 *            是否模板
	 */
	public void setSfmb(String sfmb) {
		this.sfmb = sfmb;
	}

	public String toString() {
		return (new ToStringBuilder(this)).append("ywbh_id", getYwbh_id()).toString();
	}

	public int hashCode() {
		return (new HashCodeBuilder(17, 37)).append(getYwbh_id()).toHashCode();
	}

	public boolean equals(Object o) {
		if (!(o instanceof T_mxjb_sf_yyqkDTO)) {
			return false;
		}
		if (o == this) {
            return true;
        }
		T_mxjb_sf_yyqkDTO me = (T_mxjb_sf_yyqkDTO) o;
		return new EqualsBuilder().append(getYwbh_id(), me.getYwbh_id()).isEquals();
	}
}
