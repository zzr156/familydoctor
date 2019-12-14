package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class jktj_jkpjDTO {

	/**
	 * 默认构造函数
	 */
	public jktj_jkpjDTO() {
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

	/**
	 * 健康体检--健康评价ID
	 *
	 * @hibernate.property column="jktj_jkpjid"
	 * @return jktj_jkpjid
	 */
	public String getJktj_jkpjid() {
		return this.jktj_jkpjid;
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
	 * 居民健康档案ID
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
	 * 健康评价--体检是否异常（1--体检无异常，2--有异常）
	 *
	 * @hibernate.property column="jkpj_tjsfyc"
	 * @return jkpj_tjsfyc
	 */
	public String getJkpj_tjsfyc() {
		return this.jkpj_tjsfyc;
	}

	/**
	 * 健康评价--体检异常1
	 *
	 * @hibernate.property column="jkpj_tjyc1"
	 * @return jkpj_tjyc1
	 */
	public String getJkpj_tjyc1() {
		return this.jkpj_tjyc1;
	}

	/**
	 * 健康评价--体检异常2
	 *
	 * @hibernate.property column="jkpj_tjyc2"
	 * @return jkpj_tjyc2
	 */
	public String getJkpj_tjyc2() {
		return this.jkpj_tjyc2;
	}

	/**
	 * 健康评价--体检异常3
	 *
	 * @hibernate.property column="jkpj_tjyc3"
	 * @return jkpj_tjyc3
	 */
	public String getJkpj_tjyc3() {
		return this.jkpj_tjyc3;
	}

	/**
	 * 健康评价--体检异常4
	 *
	 * @hibernate.property column="jkpj_tjyc4"
	 * @return jkpj_tjyc4
	 */
	public String getJkpj_tjyc4() {
		return this.jkpj_tjyc4;
	}

	/**
	 * 健康指导（1--定期随访，2--纳入慢性病患者健康管理，3--建议复诊，4---建议转诊）
	 *
	 * @hibernate.property column="jkzd"
	 * @return jkzd
	 */
	public String getJkzd() {
		return this.jkzd;
	}

	/**
	 * 危险因素控制（1--戒烟，2--健康饮酒，3--饮食，4--锻炼，5--减体重，6--建议疫苗接种，7--其他）
	 *
	 * @hibernate.property column="wxyskz"
	 * @return wxyskz
	 */
	public String getWxyskz() {
		return this.wxyskz;
	}

	/**
	 * 危险因素控制--减体重目标
	 *
	 * @hibernate.property column="wxyskz_jtzmb"
	 * @return wxyskz_jtzmb
	 */
	public String getWxyskz_jtzmb() {
		return this.wxyskz_jtzmb;
	}

	/**
	 * 危险因素控制--疫苗接种
	 *
	 * @hibernate.property column="wxyskz_ymjz"
	 * @return wxyskz_ymjz
	 */
	public String getWxyskz_ymjz() {
		return this.wxyskz_ymjz;
	}

	/**
	 * 危险因素控制--其他
	 *
	 * @hibernate.property column="wxyskz_qt"
	 * @return wxyskz_qt
	 */
	public String getWxyskz_qt() {
		return this.wxyskz_qt;
	}

	/**
	 * @param jktj_jkpjid
	 *            健康体检--健康评价ID
	 */
	public void setJktj_jkpjid(String jktj_jkpjid) {
		this.jktj_jkpjid = jktj_jkpjid;
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
	 *            居民健康档案ID
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
	 * @param jkpj_tjsfyc
	 *            健康评价--体检是否异常（1--体检无异常，2--有异常）
	 */
	public void setJkpj_tjsfyc(String jkpj_tjsfyc) {
		this.jkpj_tjsfyc = jkpj_tjsfyc;
	}

	/**
	 * @param jkpj_tjyc1
	 *            健康评价--体检异常1
	 */
	public void setJkpj_tjyc1(String jkpj_tjyc1) {
		this.jkpj_tjyc1 = jkpj_tjyc1;
	}

	/**
	 * @param jkpj_tjyc2
	 *            健康评价--体检异常2
	 */
	public void setJkpj_tjyc2(String jkpj_tjyc2) {
		this.jkpj_tjyc2 = jkpj_tjyc2;
	}

	/**
	 * @param jkpj_tjyc3
	 *            健康评价--体检异常3
	 */
	public void setJkpj_tjyc3(String jkpj_tjyc3) {
		this.jkpj_tjyc3 = jkpj_tjyc3;
	}

	/**
	 * @param jkpj_tjyc4
	 *            健康评价--体检异常4
	 */
	public void setJkpj_tjyc4(String jkpj_tjyc4) {
		this.jkpj_tjyc4 = jkpj_tjyc4;
	}

	/**
	 * @param jkzd
	 *            健康指导（1--定期随访，2--纳入慢性病患者健康管理，3--建议复诊，4---建议转诊）
	 */
	public void setJkzd(String jkzd) {
		this.jkzd = jkzd;
	}

	/**
	 * @param wxyskz
	 *            危险因素控制（1--戒烟，2--健康饮酒，3--饮食，4--锻炼，5--减体重，6--建议疫苗接种，7--其他）
	 */
	public void setWxyskz(String wxyskz) {
		this.wxyskz = wxyskz;
	}

	/**
	 * @param wxyskz_jtzmb
	 *            危险因素控制--减体重目标
	 */
	public void setWxyskz_jtzmb(String wxyskz_jtzmb) {
		this.wxyskz_jtzmb = wxyskz_jtzmb;
	}

	/**
	 * @param wxyskz_ymjz
	 *            危险因素控制--疫苗接种
	 */
	public void setWxyskz_ymjz(String wxyskz_ymjz) {
		this.wxyskz_ymjz = wxyskz_ymjz;
	}

	/**
	 * @param wxyskz_qt
	 *            危险因素控制--其他
	 */
	public void setWxyskz_qt(String wxyskz_qt) {
		this.wxyskz_qt = wxyskz_qt;
	}

	public String toString() {
		return (new ToStringBuilder(this)).append("jktj_jkpjid", getJktj_jkpjid()).toString();
	}

	public int hashCode() {
		return (new HashCodeBuilder(17, 37)).append(getJktj_jkpjid()).toHashCode();
	}

	public boolean equals(Object o) {
		if (!(o instanceof jktj_jkpjDTO)) {
			return false;
		}
		if (o == this) {
            return true;
        }
		jktj_jkpjDTO me = (jktj_jkpjDTO) o;
		return new EqualsBuilder().append(getJktj_jkpjid(), me.getJktj_jkpjid()).isEquals();
	}

	public String getJkzd00() {
		return jkzd00;
	}

	public void setJkzd00(String jkzd00) {
		this.jkzd00 = jkzd00;
	}

	public String getJjcf00() {
		return jjcf00;
	}

	public void setJjcf00(String jjcf00) {
		this.jjcf00 = jjcf00;
	}

}
