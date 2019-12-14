/*
 * CopyRight: StartTech 2005 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>Jktj_ybzk</code>.
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import java.io.Serializable;

/**
 * <p>
 * Title: Jktj_ybzk
 * </p>
 * <p>
 * Description:健康体检--一般状况
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 *
 * @hibernate.class table="Jktj_ybzk"
 * @author 劳动力99开发小组
 * @version 1.0
 */

public class Jktj_ybzk_ylkDTO implements Serializable {

	private String jktj_ybzkid; // 健康体检--一般状况ID(SEQ_JKTJ_YBZKID)
	private String ybzk_tiwen; // 健康体检--一般状况--体温
	private String ybzk_ml; // 健康体检--一般状况--脉率
	private String ybzk_hxpl; // 健康体检--一般状况-呼吸频率
	private String ybzk_zszy; // 健康体检--一般状况--左舒张压
	private String ybzk_zssy; // 健康体检--一般状况--左收缩压
	private String ybzk_yszy; // 健康体检--一般状况--右舒张压
	private String ybzk_yssy; // 健康体检--一般状况--右收缩压
	private String ybzk_sg; // 健康体检--一般状况--身高
	private String ybzk_tz; // 健康体检--一般状况--体重
	private String ybzk_yw; // 健康体检--一般状况--腰围
	private String ybzk_tzzs; // 健康体检--一般状况--体质指数
	private String ybzk_tunwei; // 健康体检--一般状况--臀围
	private String ybzk_ytwbz; // 健康体检--一般状况--腰臀围比值
	private String ybzk_lnrzgn; // 健康体检--一般状况--老年人认知功能(0-请选择,1-粗筛阴性,2-粗筛阳性)
	private String ybzk_lnzljc; // 健康体检--一般状况--老年人智力状态检查
	private String ybzk_lnqgzt; // 健康体检--一般状况--老年人情感状态(0-请选择,1-粗筛阴性,2-粗筛阳性)
	private String ybzk_lnyypf; // 健康体检--一般状况--老年人抑郁评分检查
	private String yyid00; // 机构ID
	private String df_id; // 居民健康档案ID
	private String jktjcs; // 健康体检--体检次数
	private String edate; // 检查日期
	private String doctor; // 责任医生
	private String tjzzqk; // 健康体检症状情况
	private String zz_qt0000;// 症状其他
	private String lnrjkpj;// 老年人健康状态自我评估(1-满意,2-基本满意,3-说不清楚,4-不太满意,5-不满意)
	private String lnrshpj; // 老年人生活自理能力自我评估(1-可自理（0～3分,2-轻度依赖（4～8分）,3-中度依赖（9～18分),4-4 不能自理（≥19分）)
	private String gxrq00; // 更新日期
	private String gxsj00; // 更新时间
	private String gxygbh; // 更新员工编号
	private String gxygxm; // 更新员工姓名
	private String doctorname;//医生姓名

	public String getGxrq00() {
		return gxrq00;
	}

	public void setGxrq00(String gxrq00) {
		this.gxrq00 = gxrq00;
	}

	public String getGxsj00() {
		return gxsj00;
	}

	public void setGxsj00(String gxsj00) {
		this.gxsj00 = gxsj00;
	}

	public String getGxygbh() {
		return gxygbh;
	}

	public void setGxygbh(String gxygbh) {
		this.gxygbh = gxygbh;
	}

	public String getGxygxm() {
		return gxygxm;
	}

	public void setGxygxm(String gxygxm) {
		this.gxygxm = gxygxm;
	}

	public String getZz_qt0000() {
		return zz_qt0000;
	}

	public void setZz_qt0000(String zz_qt0000) {
		this.zz_qt0000 = zz_qt0000;
	}

	/**
	 * 健康体检--一般状况ID(SEQ_JKTJ_YBZKID)
	 *
	 * @hibernate.property column="jktj_ybzkid"
	 * @return jktj_ybzkid
	 */
	public String getJktj_ybzkid() {
		return this.jktj_ybzkid;
	}

	/**
	 * 健康体检--一般状况--体温
	 *
	 * @hibernate.property column="ybzk_tiwen"
	 * @return ybzk_tiwen
	 */
	public String getYbzk_tiwen() {
		return this.ybzk_tiwen;
	}

	/**
	 * 健康体检--一般状况--脉率
	 *
	 * @hibernate.property column="ybzk_ml"
	 * @return ybzk_ml
	 */
	public String getYbzk_ml() {
		return this.ybzk_ml;
	}

	/**
	 * 健康体检--一般状况-呼吸频率
	 *
	 * @hibernate.property column="ybzk_hxpl"
	 * @return ybzk_hxpl
	 */
	public String getYbzk_hxpl() {
		return this.ybzk_hxpl;
	}

	/**
	 * 健康体检--一般状况--左舒张压
	 *
	 * @hibernate.property column="ybzk_zszy"
	 * @return ybzk_zszy
	 */
	public String getYbzk_zszy() {
		return this.ybzk_zszy;
	}

	/**
	 * 健康体检--一般状况--左收缩压
	 *
	 * @hibernate.property column="ybzk_zssy"
	 * @return ybzk_zssy
	 */
	public String getYbzk_zssy() {
		return this.ybzk_zssy;
	}

	/**
	 * 健康体检--一般状况--右舒张压
	 *
	 * @hibernate.property column="ybzk_yszy"
	 * @return ybzk_yszy
	 */
	public String getYbzk_yszy() {
		return this.ybzk_yszy;
	}

	/**
	 * 健康体检--一般状况--右收缩压
	 *
	 * @hibernate.property column="ybzk_yssy"
	 * @return ybzk_yssy
	 */
	public String getYbzk_yssy() {
		return this.ybzk_yssy;
	}

	/**
	 * 健康体检--一般状况--身高
	 *
	 * @hibernate.property column="ybzk_sg"
	 * @return ybzk_sg
	 */
	public String getYbzk_sg() {
		return this.ybzk_sg;
	}

	/**
	 * 健康体检--一般状况--体重
	 *
	 * @hibernate.property column="ybzk_tz"
	 * @return ybzk_tz
	 */
	public String getYbzk_tz() {
		return this.ybzk_tz;
	}

	/**
	 * 健康体检--一般状况--腰围
	 *
	 * @hibernate.property column="ybzk_yw"
	 * @return ybzk_yw
	 */
	public String getYbzk_yw() {
		return this.ybzk_yw;
	}

	/**
	 * 健康体检--一般状况--体质指数
	 *
	 * @hibernate.property column="ybzk_tzzs"
	 * @return ybzk_tzzs
	 */
	public String getYbzk_tzzs() {
		return this.ybzk_tzzs;
	}

	/**
	 * 健康体检--一般状况--臀围
	 *
	 * @hibernate.property column="ybzk_tunwei"
	 * @return ybzk_tunwei
	 */
	public String getYbzk_tunwei() {
		return this.ybzk_tunwei;
	}

	/**
	 * 健康体检--一般状况--腰臀围比值
	 *
	 * @hibernate.property column="ybzk_ytwbz"
	 * @return ybzk_ytwbz
	 */
	public String getYbzk_ytwbz() {
		return this.ybzk_ytwbz;
	}

	/**
	 * 健康体检--一般状况--老年人认知功能
	 *
	 * @hibernate.property column="ybzk_lnrzgn"
	 * @return ybzk_lnrzgn
	 */
	public String getYbzk_lnrzgn() {
		return this.ybzk_lnrzgn;
	}

	/**
	 * 健康体检--一般状况--老年人智力状态检查
	 *
	 * @hibernate.property column="ybzk_lnzljc"
	 * @return ybzk_lnzljc
	 */
	public String getYbzk_lnzljc() {
		return this.ybzk_lnzljc;
	}

	/**
	 * 健康体检--一般状况--老年人情感状态
	 *
	 * @hibernate.property column="ybzk_lnqgzt"
	 * @return ybzk_lnqgzt
	 */
	public String getYbzk_lnqgzt() {
		return this.ybzk_lnqgzt;
	}

	/**
	 * 健康体检--一般状况--老年人抑郁评分检查
	 *
	 * @hibernate.property column="ybzk_lnyypf"
	 * @return ybzk_lnyypf
	 */
	public String getYbzk_lnyypf() {
		return this.ybzk_lnyypf;
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
	 * 健康体检--体检次数
	 *
	 * @hibernate.property column="jktjcs"
	 * @return jktjcs
	 */
	public String getJktjcs() {
		return this.jktjcs;
	}

	/**
	 * 检查日期
	 *
	 * @hibernate.property column="edate"
	 * @return edate
	 */
	public String getEdate() {
		return this.edate;
	}

	/**
	 * 责任医生
	 *
	 * @hibernate.property column="doctor"
	 * @return doctor
	 */
	public String getDoctor() {
		return this.doctor;
	}

	/**
	 * 健康体检症状情况
	 *
	 * @hibernate.property column="tjzzqk"
	 * @return tjzzqk
	 */
	public String getTjzzqk() {
		return this.tjzzqk;
	}

	/**
	 * @param jktj_ybzkid
	 *            健康体检--一般状况ID(SEQ_JKTJ_YBZKID)
	 */
	public void setJktj_ybzkid(String jktj_ybzkid) {
		this.jktj_ybzkid = jktj_ybzkid;
	}

	/**
	 * @param ybzk_tiwen
	 *            健康体检--一般状况--体温
	 */
	public void setYbzk_tiwen(String ybzk_tiwen) {
		this.ybzk_tiwen = ybzk_tiwen;
	}

	/**
	 * @param ybzk_ml
	 *            健康体检--一般状况--脉率
	 */
	public void setYbzk_ml(String ybzk_ml) {
		this.ybzk_ml = ybzk_ml;
	}

	/**
	 * @param ybzk_hxpl
	 *            健康体检--一般状况-呼吸频率
	 */
	public void setYbzk_hxpl(String ybzk_hxpl) {
		this.ybzk_hxpl = ybzk_hxpl;
	}

	/**
	 * @param ybzk_zszy
	 *            健康体检--一般状况--左舒张压
	 */
	public void setYbzk_zszy(String ybzk_zszy) {
		this.ybzk_zszy = ybzk_zszy;
	}

	/**
	 * @param ybzk_zssy
	 *            健康体检--一般状况--左收缩压
	 */
	public void setYbzk_zssy(String ybzk_zssy) {
		this.ybzk_zssy = ybzk_zssy;
	}

	/**
	 * @param ybzk_yszy
	 *            健康体检--一般状况--右舒张压
	 */
	public void setYbzk_yszy(String ybzk_yszy) {
		this.ybzk_yszy = ybzk_yszy;
	}

	/**
	 * @param ybzk_yssy
	 *            健康体检--一般状况--右收缩压
	 */
	public void setYbzk_yssy(String ybzk_yssy) {
		this.ybzk_yssy = ybzk_yssy;
	}

	/**
	 * @param ybzk_sg
	 *            健康体检--一般状况--身高
	 */
	public void setYbzk_sg(String ybzk_sg) {
		this.ybzk_sg = ybzk_sg;
	}

	/**
	 * @param ybzk_tz
	 *            健康体检--一般状况--体重
	 */
	public void setYbzk_tz(String ybzk_tz) {
		this.ybzk_tz = ybzk_tz;
	}

	/**
	 * @param ybzk_yw
	 *            健康体检--一般状况--腰围
	 */
	public void setYbzk_yw(String ybzk_yw) {
		this.ybzk_yw = ybzk_yw;
	}

	/**
	 * @param ybzk_tzzs
	 *            健康体检--一般状况--体质指数
	 */
	public void setYbzk_tzzs(String ybzk_tzzs) {
		this.ybzk_tzzs = ybzk_tzzs;
	}

	/**
	 * @param ybzk_tunwei
	 *            健康体检--一般状况--臀围
	 */
	public void setYbzk_tunwei(String ybzk_tunwei) {
		this.ybzk_tunwei = ybzk_tunwei;
	}

	/**
	 * @param ybzk_ytwbz
	 *            健康体检--一般状况--腰臀围比值
	 */
	public void setYbzk_ytwbz(String ybzk_ytwbz) {
		this.ybzk_ytwbz = ybzk_ytwbz;
	}

	/**
	 * @param ybzk_lnrzgn
	 *            健康体检--一般状况--老年人认知功能
	 */
	public void setYbzk_lnrzgn(String ybzk_lnrzgn) {
		this.ybzk_lnrzgn = ybzk_lnrzgn;
	}

	/**
	 * @param ybzk_lnzljc
	 *            健康体检--一般状况--老年人智力状态检查
	 */
	public void setYbzk_lnzljc(String ybzk_lnzljc) {
		this.ybzk_lnzljc = ybzk_lnzljc;
	}

	/**
	 * @param ybzk_lnqgzt
	 *            健康体检--一般状况--老年人情感状态
	 */
	public void setYbzk_lnqgzt(String ybzk_lnqgzt) {
		this.ybzk_lnqgzt = ybzk_lnqgzt;
	}

	/**
	 * @param ybzk_lnyypf
	 *            健康体检--一般状况--老年人抑郁评分检查
	 */
	public void setYbzk_lnyypf(String ybzk_lnyypf) {
		this.ybzk_lnyypf = ybzk_lnyypf;
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
	 *            健康体检--体检次数
	 */
	public void setJktjcs(String jktjcs) {
		this.jktjcs = jktjcs;
	}

	/**
	 * @param edate
	 *            检查日期
	 */
	public void setEdate(String edate) {
		this.edate = edate;
	}

	/**
	 * @param doctor
	 *            责任医生
	 */
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	/**
	 * @param tjzzqk
	 *            健康体检症状情况
	 */
	public void setTjzzqk(String tjzzqk) {
		this.tjzzqk = tjzzqk;
	}

	public String getLnrjkpj() {
		return lnrjkpj;
	}

	public void setLnrjkpj(String lnrjkpj) {
		this.lnrjkpj = lnrjkpj;
	}

	public String getLnrshpj() {
		return lnrshpj;
	}

	public void setLnrshpj(String lnrshpj) {
		this.lnrshpj = lnrshpj;
	}

	public String getDoctorname() {
		return doctorname;
	}

	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}
}