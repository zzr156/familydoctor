/*
 * CopyRight: StartTech 2005 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>Sf_brxxb0</code>.
 */
package com.ylz.bizDo.assessment.vo.view;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: Sf_brxxb0
 * </p>
 * <p>
 * Description:门诊病人挂号信息表 SF_BRXXB0
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 * 
 * @hibernate.class table="Sf_brxxb0"
 * @author 劳动力99开发小组
 * @version 1.0
 */
public class Sf_brxxb0DTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4834517979072525776L;

	private java.lang.String yyid00; // 医院内部号(社区卫生院ID)XT_YYXX00.YYID00
	private java.lang.String brblh0; // 门诊病历号
	private java.lang.String ghh000; // 挂号号(SQ_SF_BRXXB0_GHH000),暂时挂号号由序列来生成
	private java.lang.String ybghh0; // 医保挂号流水号,由医保程序生成的挂号流水号
	private java.math.BigDecimal ghlb00; // 挂号类别,F.K=BM_GHLBB0.LBBH00
	private java.math.BigDecimal brid00; // 病人ID号 F.K=BM_BRXXB0.BRID00
	private java.lang.String xm0000; // 姓名
	private java.lang.String xb0000; // 性别 AA10.AAA100="T_SEX" 性别ID
	private java.lang.String csrq00; // 出生日期
	private java.math.BigDecimal fbbh00; // 病人费别 F.K=BM_BRFBB0.FBBH00
	private java.lang.String ybzxlb; // 医保中心类别 BM_YBJGLB.YBZXLB 转费别时，
										// 修改本次挂号的YBZXLB
	private java.lang.String yblb00; // 医保分中心 BM_YBJGLB.YBLB00
	private java.lang.String ghrq00; // 病人挂号日期
	private java.lang.String ghsj00; // 病人挂号时间
	private java.math.BigDecimal ghy000; // 挂号员编号 F.K=BM_YGBM00.YGBH00
	private java.math.BigDecimal ghks00; // 挂号科室编号 F.K=BM_BMBM00.BMBH00
	private java.lang.String jzrq00; // 病人就诊日期(可能和挂号日期不同)
	private java.lang.String jzsj00; // 病人就诊时间
	private java.math.BigDecimal jzks00; // 就诊科室编号 F.K=BM_BMBM00.BMBH00
	private java.math.BigDecimal jzys00; // 就诊医生代码 F.K=BM_YGBM00.YGBH00
	private java.lang.String thrq00; // 退号日期
	private java.lang.String thsj00; // 退号时间
	private java.math.BigDecimal thy000; // 退号员编号 F.K=BM_YGBM00.YGBH00
	private java.lang.String thyxm0; // 退号员姓名
	private java.lang.String thbz00; // 退号标志,"0":已退号,"1":可退号,"2":不可退号

	private java.math.BigDecimal ghyxts; // 挂号有效天数
	private java.lang.String sfnlyd; // 是否老年优待 "Y"是,"N"否
	private java.math.BigDecimal djh000; // 挂号费对应的费用单据号
	private java.lang.String czrq00; // 挂号操作日期
	private java.lang.String czsj00; // 挂号操作时间
	private java.math.BigDecimal ghfzje; // 挂号费总金额 --发票项目是挂号费的费用的金额
	private java.math.BigDecimal zcfzje; // 诊查费总金额 --发票项目是诊查费的费用的金额
	private java.lang.String brmz00; //
	private java.lang.String sfcz00;

	private java.lang.String gdszl0;// 诊疗序号GDS
	private java.lang.String zdscbz;// 医保诊断上传标志
	private java.lang.String tjbz00;// 是否体检标志

	public java.lang.String getTjbz00() {
		return tjbz00;
	}

	public void setTjbz00(java.lang.String tjbz00) {
		this.tjbz00 = tjbz00;
	}

	public java.lang.String getZdscbz() {
		return zdscbz;
	}

	public void setZdscbz(java.lang.String zdscbz) {
		this.zdscbz = zdscbz;
	}

	public String getGdszl0() {
		return gdszl0;
	}

	public void setGdszl0(String gdszl0) {
		this.gdszl0 = gdszl0;
	}

	public java.lang.String getSfcz00() {
		return sfcz00;
	}

	public void setSfcz00(java.lang.String sfcz00) {
		this.sfcz00 = sfcz00;
	}

	public java.lang.String getBrmz00() {
		return brmz00;
	}

	public void setBrmz00(java.lang.String brmz00) {
		this.brmz00 = brmz00;
	}

	/**
	 * 医院内部号(社区卫生院ID)XT_YYXX00.YYID00
	 * 
	 * @hibernate.property column="yyid00"
	 * @return yyid00
	 */
	public java.lang.String getYyid00() {
		return this.yyid00;
	}

	/**
	 * 门诊病历号
	 * 
	 * @hibernate.property column="brblh0"
	 * @return brblh0
	 */
	public java.lang.String getBrblh0() {
		return this.brblh0;
	}

	/**
	 * 挂号号(SQ_SF_BRXXB0_GHH000),暂时挂号号由序列来生成
	 * 
	 * @hibernate.property column="ghh000"
	 * @return ghh000
	 */
	public java.lang.String getGhh000() {
		return this.ghh000;
	}

	/**
	 * 医保挂号流水号,由医保程序生成的挂号流水号
	 * 
	 * @hibernate.property column="ybghh0"
	 * @return ybghh0
	 */
	public java.lang.String getYbghh0() {
		return this.ybghh0;
	}

	/**
	 * 挂号类别,F.K=BM_GHLBB0.LBBH00
	 * 
	 * @hibernate.property column="ghlb00"
	 * @return ghlb00
	 */
	public java.math.BigDecimal getGhlb00() {
		return this.ghlb00;
	}

	/**
	 * 病人ID号 F.K=BM_BRXXB0.BRID00
	 * 
	 * @hibernate.property column="brid00"
	 * @return brid00
	 */
	public java.math.BigDecimal getBrid00() {
		return this.brid00;
	}

	/**
	 * 姓名
	 * 
	 * @hibernate.property column="xm0000"
	 * @return xm0000
	 */
	public java.lang.String getXm0000() {
		return this.xm0000;
	}

	/**
	 * 性别 AA10.AAA100="T_SEX" 性别ID
	 * 
	 * @hibernate.property column="xb0000"
	 * @return xb0000
	 */
	public java.lang.String getXb0000() {
		return this.xb0000;
	}

	/**
	 * 出生日期
	 * 
	 * @hibernate.property column="csrq00"
	 * @return csrq00
	 */
	public java.lang.String getCsrq00() {
		return this.csrq00;
	}

	/**
	 * 病人费别 F.K=BM_BRFBB0.FBBH00
	 * 
	 * @hibernate.property column="fbbh00"
	 * @return fbbh00
	 */
	public java.math.BigDecimal getFbbh00() {
		return this.fbbh00;
	}

	/**
	 * 医保中心类别 BM_YBJGLB.YBZXLB 转费别时， 修改本次挂号的YBZXLB
	 * 
	 * @hibernate.property column="ybzxlb"
	 * @return ybzxlb
	 */
	public java.lang.String getYbzxlb() {
		return this.ybzxlb;
	}

	/**
	 * 医保分中心 BM_YBJGLB.YBLB00
	 * 
	 * @hibernate.property column="yblb00"
	 * @return yblb00
	 */
	public java.lang.String getYblb00() {
		return this.yblb00;
	}

	/**
	 * 病人挂号日期
	 * 
	 * @hibernate.property column="ghrq00"
	 * @return ghrq00
	 */
	public java.lang.String getGhrq00() {
		return this.ghrq00;
	}

	/**
	 * 病人挂号时间
	 * 
	 * @hibernate.property column="ghsj00"
	 * @return ghsj00
	 */
	public java.lang.String getGhsj00() {
		return this.ghsj00;
	}

	/**
	 * 挂号员编号 F.K=BM_YGBM00.YGBH00
	 * 
	 * @hibernate.property column="ghy000"
	 * @return ghy000
	 */
	public java.math.BigDecimal getGhy000() {
		return this.ghy000;
	}

	/**
	 * 挂号科室编号 F.K=BM_BMBM00.BMBH00
	 * 
	 * @hibernate.property column="ghks00"
	 * @return ghks00
	 */
	public java.math.BigDecimal getGhks00() {
		return this.ghks00;
	}

	/**
	 * 病人就诊日期(可能和挂号日期不同)
	 * 
	 * @hibernate.property column="jzrq00"
	 * @return jzrq00
	 */
	public java.lang.String getJzrq00() {
		return this.jzrq00;
	}

	/**
	 * 病人就诊时间
	 * 
	 * @hibernate.property column="jzsj00"
	 * @return jzsj00
	 */
	public java.lang.String getJzsj00() {
		return this.jzsj00;
	}

	/**
	 * 就诊科室编号 F.K=BM_BMBM00.BMBH00
	 * 
	 * @hibernate.property column="jzks00"
	 * @return jzks00
	 */
	public java.math.BigDecimal getJzks00() {
		return this.jzks00;
	}

	/**
	 * 就诊医生代码 F.K=BM_YGBM00.YGBH00
	 * 
	 * @hibernate.property column="jzys00"
	 * @return jzys00
	 */
	public java.math.BigDecimal getJzys00() {
		return this.jzys00;
	}

	/**
	 * 退号日期
	 * 
	 * @hibernate.property column="thrq00"
	 * @return thrq00
	 */
	public java.lang.String getThrq00() {
		return this.thrq00;
	}

	/**
	 * 退号时间
	 * 
	 * @hibernate.property column="thsj00"
	 * @return thsj00
	 */
	public java.lang.String getThsj00() {
		return this.thsj00;
	}

	/**
	 * 退号员编号 F.K=BM_YGBM00.YGBH00
	 * 
	 * @hibernate.property column="thy000"
	 * @return thy000
	 */
	public java.math.BigDecimal getThy000() {
		return this.thy000;
	}

	/**
	 * 退号员姓名
	 * 
	 * @hibernate.property column="thyxm0"
	 * @return thyxm0
	 */
	public java.lang.String getThyxm0() {
		return this.thyxm0;
	}

	/**
	 * 退号标志,"0":已退号,"1":可退号,"2":不可退号
	 * 
	 * @hibernate.property column="thbz00"
	 * @return thbz00
	 */
	public java.lang.String getThbz00() {
		return this.thbz00;
	}

	/**
	 * 挂号有效天数
	 * 
	 * @hibernate.property column="ghyxts"
	 * @return ghyxts
	 */
	public java.math.BigDecimal getGhyxts() {
		return this.ghyxts;
	}

	/**
	 * 是否老年优待 "Y"是,"N"否
	 * 
	 * @hibernate.property column="sfnlyd"
	 * @return sfnlyd
	 */
	public java.lang.String getSfnlyd() {
		return this.sfnlyd;
	}

	/**
	 * 挂号费对应的费用单据号
	 * 
	 * @hibernate.property column="djh000"
	 * @return djh000
	 */
	public java.math.BigDecimal getDjh000() {
		return this.djh000;
	}

	/**
	 * 挂号操作日期
	 * 
	 * @hibernate.property column="czrq00"
	 * @return czrq00
	 */
	public java.lang.String getCzrq00() {
		return this.czrq00;
	}

	/**
	 * 挂号操作时间
	 * 
	 * @hibernate.property column="czsj00"
	 * @return czsj00
	 */
	public java.lang.String getCzsj00() {
		return this.czsj00;
	}

	/**
	 * 挂号费总金额 --发票项目是挂号费的费用的金额
	 * 
	 * @hibernate.property column="ghfzje"
	 * @return ghfzje
	 */
	public java.math.BigDecimal getGhfzje() {
		return this.ghfzje;
	}

	/**
	 * 诊查费总金额 --发票项目是诊查费的费用的金额
	 * 
	 * @hibernate.property column="zcfzje"
	 * @return zcfzje
	 */
	public java.math.BigDecimal getZcfzje() {
		return this.zcfzje;
	}

	/**
	 * @param yyid00
	 *            医院内部号(社区卫生院ID)XT_YYXX00.YYID00
	 */
	public void setYyid00(java.lang.String yyid00) {
		this.yyid00 = yyid00;
	}

	/**
	 * @param brblh0
	 *            门诊病历号
	 */
	public void setBrblh0(java.lang.String brblh0) {
		this.brblh0 = brblh0;
	}

	/**
	 * @param ghh000
	 *            挂号号(SQ_SF_BRXXB0_GHH000),暂时挂号号由序列来生成
	 */
	public void setGhh000(java.lang.String ghh000) {
		this.ghh000 = ghh000;
	}

	/**
	 * @param ybghh0
	 *            医保挂号流水号,由医保程序生成的挂号流水号
	 */
	public void setYbghh0(java.lang.String ybghh0) {
		this.ybghh0 = ybghh0;
	}

	/**
	 * @param ghlb00
	 *            挂号类别,F.K=BM_GHLBB0.LBBH00
	 */
	public void setGhlb00(java.math.BigDecimal ghlb00) {
		this.ghlb00 = ghlb00;
	}

	/**
	 * @param brid00
	 *            病人ID号 F.K=BM_BRXXB0.BRID00
	 */
	public void setBrid00(java.math.BigDecimal brid00) {
		this.brid00 = brid00;
	}

	/**
	 * @param xm0000
	 *            姓名
	 */
	public void setXm0000(java.lang.String xm0000) {
		this.xm0000 = xm0000;
	}

	/**
	 * @param xb0000
	 *            性别 AA10.AAA100="T_SEX" 性别ID
	 */
	public void setXb0000(java.lang.String xb0000) {
		this.xb0000 = xb0000;
	}

	/**
	 * @param csrq00
	 *            出生日期
	 */
	public void setCsrq00(java.lang.String csrq00) {
		this.csrq00 = csrq00;
	}

	/**
	 * @param fbbh00
	 *            病人费别 F.K=BM_BRFBB0.FBBH00
	 */
	public void setFbbh00(java.math.BigDecimal fbbh00) {
		this.fbbh00 = fbbh00;
	}

	/**
	 * @param ybzxlb
	 *            医保中心类别 BM_YBJGLB.YBZXLB 转费别时， 修改本次挂号的YBZXLB
	 */
	public void setYbzxlb(java.lang.String ybzxlb) {
		this.ybzxlb = ybzxlb;
	}

	/**
	 * @param yblb00
	 *            医保分中心 BM_YBJGLB.YBLB00
	 */
	public void setYblb00(java.lang.String yblb00) {
		this.yblb00 = yblb00;
	}

	/**
	 * @param ghrq00
	 *            病人挂号日期
	 */
	public void setGhrq00(java.lang.String ghrq00) {
		this.ghrq00 = ghrq00;
	}

	/**
	 * @param ghsj00
	 *            病人挂号时间
	 */
	public void setGhsj00(java.lang.String ghsj00) {
		this.ghsj00 = ghsj00;
	}

	/**
	 * @param ghy000
	 *            挂号员编号 F.K=BM_YGBM00.YGBH00
	 */
	public void setGhy000(java.math.BigDecimal ghy000) {
		this.ghy000 = ghy000;
	}

	/**
	 * @param ghks00
	 *            挂号科室编号 F.K=BM_BMBM00.BMBH00
	 */
	public void setGhks00(java.math.BigDecimal ghks00) {
		this.ghks00 = ghks00;
	}

	/**
	 * @param jzrq00
	 *            病人就诊日期(可能和挂号日期不同)
	 */
	public void setJzrq00(java.lang.String jzrq00) {
		this.jzrq00 = jzrq00;
	}

	/**
	 * @param jzsj00
	 *            病人就诊时间
	 */
	public void setJzsj00(java.lang.String jzsj00) {
		this.jzsj00 = jzsj00;
	}

	/**
	 * @param jzks00
	 *            就诊科室编号 F.K=BM_BMBM00.BMBH00
	 */
	public void setJzks00(java.math.BigDecimal jzks00) {
		this.jzks00 = jzks00;
	}

	/**
	 * @param jzys00
	 *            就诊医生代码 F.K=BM_YGBM00.YGBH00
	 */
	public void setJzys00(java.math.BigDecimal jzys00) {
		this.jzys00 = jzys00;
	}

	/**
	 * @param thrq00
	 *            退号日期
	 */
	public void setThrq00(java.lang.String thrq00) {
		this.thrq00 = thrq00;
	}

	/**
	 * @param thsj00
	 *            退号时间
	 */
	public void setThsj00(java.lang.String thsj00) {
		this.thsj00 = thsj00;
	}

	/**
	 * @param thy000
	 *            退号员编号 F.K=BM_YGBM00.YGBH00
	 */
	public void setThy000(java.math.BigDecimal thy000) {
		this.thy000 = thy000;
	}

	/**
	 * @param thyxm0
	 *            退号员姓名
	 */
	public void setThyxm0(java.lang.String thyxm0) {
		this.thyxm0 = thyxm0;
	}

	/**
	 * @param thbz00
	 *            退号标志,"0":已退号,"1":可退号,"2":不可退号
	 */
	public void setThbz00(java.lang.String thbz00) {
		this.thbz00 = thbz00;
	}

	/**
	 * @param ghyxts
	 *            挂号有效天数
	 */
	public void setGhyxts(java.math.BigDecimal ghyxts) {
		this.ghyxts = ghyxts;
	}

	/**
	 * @param sfnlyd
	 *            是否老年优待 "Y"是,"N"否
	 */
	public void setSfnlyd(java.lang.String sfnlyd) {
		this.sfnlyd = sfnlyd;
	}

	/**
	 * @param djh000
	 *            挂号费对应的费用单据号
	 */
	public void setDjh000(java.math.BigDecimal djh000) {
		this.djh000 = djh000;
	}

	/**
	 * @param czrq00
	 *            挂号操作日期
	 */
	public void setCzrq00(java.lang.String czrq00) {
		this.czrq00 = czrq00;
	}

	/**
	 * @param czsj00
	 *            挂号操作时间
	 */
	public void setCzsj00(java.lang.String czsj00) {
		this.czsj00 = czsj00;
	}

	/**
	 * @param ghfzje
	 *            挂号费总金额 --发票项目是挂号费的费用的金额
	 */
	public void setGhfzje(java.math.BigDecimal ghfzje) {
		this.ghfzje = ghfzje;
	}

	/**
	 * @param zcfzje
	 *            诊查费总金额 --发票项目是诊查费的费用的金额
	 */
	public void setZcfzje(java.math.BigDecimal zcfzje) {
		this.zcfzje = zcfzje;
	}

	public String toString() {
		return (new ToStringBuilder(this)).append("ghh000", getGhh000()).toString();
	}

	public int hashCode() {
		return (new HashCodeBuilder(17, 37)).append(getGhh000()).toHashCode();
	}
}