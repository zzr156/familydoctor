package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class jktj_ctDTO {
	/**
	 * 默认构造函数
	 */
	public jktj_ctDTO() {
	}

	private String jktj_ctid; // 健康体检--查体ID
	private String yyid00; // 机构ID
	private String df_id; // 居民健康档案ID
	private String jktjcs; // 健康体检次数
	private String ct_pf; // 查体--皮肤
	private String ct_pfqt; // 查体--皮肤其他
	private String ct_gm; // 查体--巩膜
	private String ct_gmqt; // 查体--巩膜其他
	private String ct_lbj; // 查体--淋巴结
	private String ct_lbjqt; // 查体--淋巴结其他
	private String ct_ftzx; // 查体--肺--桶状胸
	private String ct_fhxy; // 查体--肺--呼吸音
	private String ct_fhxyyc; // 查体--肺--呼吸音异常
	private String ct_fly; // 查体--肺--罗音
	private String ct_flyqt; // 查体--肺--罗音其他
	private String ct_xzxl; // 查体--心脏--心率
	private String ct_xzxinl; // 查体--心脏--心律
	private String ct_xzzy; // 查体--心脏--杂音
	private String ct_xzzyqt; // 查体--心脏--杂音其他
	private String ct_fbyt; // 查体--腹部--压痛
	private String ct_fbytqt; // 查体--腹部--压痛其他
	private String ct_fbbk; // 查体--腹部--包块(1-有,0-无)
	private String ct_fbbkqt; // 查体--腹部--包块其他
	private String ct_fbgd; // 查体--腹部--肝大
	private String ct_fbgdqt; // 查体--腹部--肝大其他
	private String ct_fbpd; // 查体--腹部--脾大
	private String ct_fbpdqt; // 查体--腹部--脾大其他
	private String ct_fbydzy; // 查体--腹部--移动性浊音
	private String ct_fbydzyqt; // 查体--腹部--移动性浊音其他
	private String ct_xzsz; // 查体--下肢水肿
	private String ct_zbdmbd; // 查体--足背动脉搏动
	private String ct_gmzz; // 查体--肛门指诊
	private String ct_gmzzqt; // 查体--肛门指诊其他
	private String ct_rxqk; // 查体--乳腺情况
	private String ct_rxqt; // 查体--乳腺其他
	private String ct_fkwy; // 查体--妇科--外阴
	private String ct_fkwyqt; // 查体--妇科--外阴其他
	private String ct_fkyd; // 查体--妇科--阴道
	private String ct_fkydqt; // 查体--妇科--阴道其他
	private String ct_fkgj; // 查体--妇科--宫颈
	private String ct_fkgjqt; // 查体--妇科--宫颈其他
	private String ct_fkgt; // 查体--妇科--宫体
	private String ct_fkgtqt; // 查体--妇科--宫体其他
	private String ct_fkfj; // 查体--妇科--附件
	private String ct_fkfjqt; // 查体--妇科--附件其他
	private String ct_qt; // 查体--其他

	/**
	 * 健康体检--查体ID
	 *
	 * @hibernate.property column="jktj_ctid"
	 * @return jktj_ctid
	 */
	public String getJktj_ctid() {
		return this.jktj_ctid;
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
	 * 查体--皮肤
	 *
	 * @hibernate.property column="ct_pf"
	 * @return ct_pf
	 */
	public String getCt_pf() {
		return this.ct_pf;
	}

	/**
	 * 查体--皮肤其他
	 *
	 * @hibernate.property column="ct_pfqt"
	 * @return ct_pfqt
	 */
	public String getCt_pfqt() {
		return this.ct_pfqt;
	}

	/**
	 * 查体--巩膜
	 *
	 * @hibernate.property column="ct_gm"
	 * @return ct_gm
	 */
	public String getCt_gm() {
		return this.ct_gm;
	}

	/**
	 * 查体--巩膜其他
	 *
	 * @hibernate.property column="ct_gmqt"
	 * @return ct_gmqt
	 */
	public String getCt_gmqt() {
		return this.ct_gmqt;
	}

	/**
	 * 查体--淋巴结
	 *
	 * @hibernate.property column="ct_lbj"
	 * @return ct_lbj
	 */
	public String getCt_lbj() {
		return this.ct_lbj;
	}

	/**
	 * 查体--淋巴结其他
	 *
	 * @hibernate.property column="ct_lbjqt"
	 * @return ct_lbjqt
	 */
	public String getCt_lbjqt() {
		return this.ct_lbjqt;
	}

	/**
	 * 查体--肺--桶状胸
	 *
	 * @hibernate.property column="ct_ftzx"
	 * @return ct_ftzx
	 */
	public String getCt_ftzx() {
		return this.ct_ftzx;
	}

	/**
	 * 查体--肺--呼吸音
	 *
	 * @hibernate.property column="ct_fhxy"
	 * @return ct_fhxy
	 */
	public String getCt_fhxy() {
		return this.ct_fhxy;
	}

	/**
	 * 查体--肺--呼吸音异常
	 *
	 * @hibernate.property column="ct_fhxyyc"
	 * @return ct_fhxyyc
	 */
	public String getCt_fhxyyc() {
		return this.ct_fhxyyc;
	}

	/**
	 * 查体--肺--罗音
	 *
	 * @hibernate.property column="ct_fly"
	 * @return ct_fly
	 */
	public String getCt_fly() {
		return this.ct_fly;
	}

	/**
	 * 查体--肺--罗音其他
	 *
	 * @hibernate.property column="ct_flyqt"
	 * @return ct_flyqt
	 */
	public String getCt_flyqt() {
		return this.ct_flyqt;
	}

	/**
	 * 查体--心脏--心率
	 *
	 * @hibernate.property column="ct_xzxl"
	 * @return ct_xzxl
	 */
	public String getCt_xzxl() {
		return this.ct_xzxl;
	}

	/**
	 * 查体--心脏--心律
	 *
	 * @hibernate.property column="ct_xzxinl"
	 * @return ct_xzxinl
	 */
	public String getCt_xzxinl() {
		return this.ct_xzxinl;
	}

	/**
	 * 查体--心脏--杂音
	 *
	 * @hibernate.property column="ct_xzzy"
	 * @return ct_xzzy
	 */
	public String getCt_xzzy() {
		return this.ct_xzzy;
	}

	/**
	 * 查体--心脏--杂音其他
	 *
	 * @hibernate.property column="ct_xzzyqt"
	 * @return ct_xzzyqt
	 */
	public String getCt_xzzyqt() {
		return this.ct_xzzyqt;
	}

	/**
	 * 查体--腹部--压痛
	 *
	 * @hibernate.property column="ct_fbyt"
	 * @return ct_fbyt
	 */
	public String getCt_fbyt() {
		return this.ct_fbyt;
	}

	/**
	 * 查体--腹部--压痛其他
	 *
	 * @hibernate.property column="ct_fbytqt"
	 * @return ct_fbytqt
	 */
	public String getCt_fbytqt() {
		return this.ct_fbytqt;
	}

	/**
	 * 查体--腹部--包块
	 *
	 * @hibernate.property column="ct_fbbk"
	 * @return ct_fbbk
	 */
	public String getCt_fbbk() {
		return this.ct_fbbk;
	}

	/**
	 * 查体--腹部--包块其他
	 *
	 * @hibernate.property column="ct_fbbkqt"
	 * @return ct_fbbkqt
	 */
	public String getCt_fbbkqt() {
		return this.ct_fbbkqt;
	}

	/**
	 * 查体--腹部--肝大
	 *
	 * @hibernate.property column="ct_fbgd"
	 * @return ct_fbgd
	 */
	public String getCt_fbgd() {
		return this.ct_fbgd;
	}

	/**
	 * 查体--腹部--肝大其他
	 *
	 * @hibernate.property column="ct_fbgdqt"
	 * @return ct_fbgdqt
	 */
	public String getCt_fbgdqt() {
		return this.ct_fbgdqt;
	}

	/**
	 * 查体--腹部--脾大
	 *
	 * @hibernate.property column="ct_fbpd"
	 * @return ct_fbpd
	 */
	public String getCt_fbpd() {
		return this.ct_fbpd;
	}

	/**
	 * 查体--腹部--脾大其他
	 *
	 * @hibernate.property column="ct_fbpdqt"
	 * @return ct_fbpdqt
	 */
	public String getCt_fbpdqt() {
		return this.ct_fbpdqt;
	}

	/**
	 * 查体--腹部--移动性浊音
	 *
	 * @hibernate.property column="ct_fbydzy"
	 * @return ct_fbydzy
	 */
	public String getCt_fbydzy() {
		return this.ct_fbydzy;
	}

	/**
	 * 查体--腹部--移动性浊音其他
	 *
	 * @hibernate.property column="ct_fbydzyqt"
	 * @return ct_fbydzyqt
	 */
	public String getCt_fbydzyqt() {
		return this.ct_fbydzyqt;
	}

	/**
	 * 查体--下肢水肿
	 *
	 * @hibernate.property column="ct_xzsz"
	 * @return ct_xzsz
	 */
	public String getCt_xzsz() {
		return this.ct_xzsz;
	}

	/**
	 * 查体--足背动脉搏动
	 *
	 * @hibernate.property column="ct_zbdmbd"
	 * @return ct_zbdmbd
	 */
	public String getCt_zbdmbd() {
		return this.ct_zbdmbd;
	}

	/**
	 * 查体--肛门指诊
	 *
	 * @hibernate.property column="ct_gmzz"
	 * @return ct_gmzz
	 */
	public String getCt_gmzz() {
		return this.ct_gmzz;
	}

	/**
	 * 查体--肛门指诊其他
	 *
	 * @hibernate.property column="ct_gmzzqt"
	 * @return ct_gmzzqt
	 */
	public String getCt_gmzzqt() {
		return this.ct_gmzzqt;
	}

	/**
	 * 查体--乳腺情况
	 *
	 * @hibernate.property column="ct_rxqk"
	 * @return ct_rxqk
	 */
	public String getCt_rxqk() {
		return this.ct_rxqk;
	}

	/**
	 * 查体--乳腺其他
	 *
	 * @hibernate.property column="ct_rxqt"
	 * @return ct_rxqt
	 */
	public String getCt_rxqt() {
		return this.ct_rxqt;
	}

	/**
	 * 查体--妇科--外阴
	 *
	 * @hibernate.property column="ct_fkwy"
	 * @return ct_fkwy
	 */
	public String getCt_fkwy() {
		return this.ct_fkwy;
	}

	/**
	 * 查体--妇科--外阴其他
	 *
	 * @hibernate.property column="ct_fkwyqt"
	 * @return ct_fkwyqt
	 */
	public String getCt_fkwyqt() {
		return this.ct_fkwyqt;
	}

	/**
	 * 查体--妇科--阴道
	 *
	 * @hibernate.property column="ct_fkyd"
	 * @return ct_fkyd
	 */
	public String getCt_fkyd() {
		return this.ct_fkyd;
	}

	/**
	 * 查体--妇科--阴道其他
	 *
	 * @hibernate.property column="ct_fkydqt"
	 * @return ct_fkydqt
	 */
	public String getCt_fkydqt() {
		return this.ct_fkydqt;
	}

	/**
	 * 查体--妇科--宫颈
	 *
	 * @hibernate.property column="ct_fkgj"
	 * @return ct_fkgj
	 */
	public String getCt_fkgj() {
		return this.ct_fkgj;
	}

	/**
	 * 查体--妇科--宫颈其他
	 *
	 * @hibernate.property column="ct_fkgjqt"
	 * @return ct_fkgjqt
	 */
	public String getCt_fkgjqt() {
		return this.ct_fkgjqt;
	}

	/**
	 * 查体--妇科--宫体
	 *
	 * @hibernate.property column="ct_fkgt"
	 * @return ct_fkgt
	 */
	public String getCt_fkgt() {
		return this.ct_fkgt;
	}

	/**
	 * 查体--妇科--宫体其他
	 *
	 * @hibernate.property column="ct_fkgtqt"
	 * @return ct_fkgtqt
	 */
	public String getCt_fkgtqt() {
		return this.ct_fkgtqt;
	}

	/**
	 * 查体--妇科--附件
	 *
	 * @hibernate.property column="ct_fkfj"
	 * @return ct_fkfj
	 */
	public String getCt_fkfj() {
		return this.ct_fkfj;
	}

	/**
	 * 查体--妇科--附件其他
	 *
	 * @hibernate.property column="ct_fkfjqt"
	 * @return ct_fkfjqt
	 */
	public String getCt_fkfjqt() {
		return this.ct_fkfjqt;
	}

	/**
	 * 查体--其他
	 *
	 * @hibernate.property column="ct_qt"
	 * @return ct_qt
	 */
	public String getCt_qt() {
		return this.ct_qt;
	}

	/**
	 * @param jktj_ctid
	 *            健康体检--查体ID
	 */
	public void setJktj_ctid(String jktj_ctid) {
		this.jktj_ctid = jktj_ctid;
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
	 * @param ct_pf
	 *            查体--皮肤
	 */
	public void setCt_pf(String ct_pf) {
		this.ct_pf = ct_pf;
	}

	/**
	 * @param ct_pfqt
	 *            查体--皮肤其他
	 */
	public void setCt_pfqt(String ct_pfqt) {
		this.ct_pfqt = ct_pfqt;
	}

	/**
	 * @param ct_gm
	 *            查体--巩膜
	 */
	public void setCt_gm(String ct_gm) {
		this.ct_gm = ct_gm;
	}

	/**
	 * @param ct_gmqt
	 *            查体--巩膜其他
	 */
	public void setCt_gmqt(String ct_gmqt) {
		this.ct_gmqt = ct_gmqt;
	}

	/**
	 * @param ct_lbj
	 *            查体--淋巴结
	 */
	public void setCt_lbj(String ct_lbj) {
		this.ct_lbj = ct_lbj;
	}

	/**
	 * @param ct_lbjqt
	 *            查体--淋巴结其他
	 */
	public void setCt_lbjqt(String ct_lbjqt) {
		this.ct_lbjqt = ct_lbjqt;
	}

	/**
	 * @param ct_ftzx
	 *            查体--肺--桶状胸
	 */
	public void setCt_ftzx(String ct_ftzx) {
		this.ct_ftzx = ct_ftzx;
	}

	/**
	 * @param ct_fhxy
	 *            查体--肺--呼吸音
	 */
	public void setCt_fhxy(String ct_fhxy) {
		this.ct_fhxy = ct_fhxy;
	}

	/**
	 * @param ct_fhxyyc
	 *            查体--肺--呼吸音异常
	 */
	public void setCt_fhxyyc(String ct_fhxyyc) {
		this.ct_fhxyyc = ct_fhxyyc;
	}

	/**
	 * @param ct_fly
	 *            查体--肺--罗音
	 */
	public void setCt_fly(String ct_fly) {
		this.ct_fly = ct_fly;
	}

	/**
	 * @param ct_flyqt
	 *            查体--肺--罗音其他
	 */
	public void setCt_flyqt(String ct_flyqt) {
		this.ct_flyqt = ct_flyqt;
	}

	/**
	 * @param ct_xzxl
	 *            查体--心脏--心率
	 */
	public void setCt_xzxl(String ct_xzxl) {
		this.ct_xzxl = ct_xzxl;
	}

	/**
	 * @param ct_xzxinl
	 *            查体--心脏--心律
	 */
	public void setCt_xzxinl(String ct_xzxinl) {
		this.ct_xzxinl = ct_xzxinl;
	}

	/**
	 * @param ct_xzzy
	 *            查体--心脏--杂音
	 */
	public void setCt_xzzy(String ct_xzzy) {
		this.ct_xzzy = ct_xzzy;
	}

	/**
	 * @param ct_xzzyqt
	 *            查体--心脏--杂音其他
	 */
	public void setCt_xzzyqt(String ct_xzzyqt) {
		this.ct_xzzyqt = ct_xzzyqt;
	}

	/**
	 * @param ct_fbyt
	 *            查体--腹部--压痛
	 */
	public void setCt_fbyt(String ct_fbyt) {
		this.ct_fbyt = ct_fbyt;
	}

	/**
	 * @param ct_fbytqt
	 *            查体--腹部--压痛其他
	 */
	public void setCt_fbytqt(String ct_fbytqt) {
		this.ct_fbytqt = ct_fbytqt;
	}

	/**
	 * @param ct_fbbk
	 *            查体--腹部--包块
	 */
	public void setCt_fbbk(String ct_fbbk) {
		this.ct_fbbk = ct_fbbk;
	}

	/**
	 * @param ct_fbbkqt
	 *            查体--腹部--包块其他
	 */
	public void setCt_fbbkqt(String ct_fbbkqt) {
		this.ct_fbbkqt = ct_fbbkqt;
	}

	/**
	 * @param ct_fbgd
	 *            查体--腹部--肝大
	 */
	public void setCt_fbgd(String ct_fbgd) {
		this.ct_fbgd = ct_fbgd;
	}

	/**
	 * @param ct_fbgdqt
	 *            查体--腹部--肝大其他
	 */
	public void setCt_fbgdqt(String ct_fbgdqt) {
		this.ct_fbgdqt = ct_fbgdqt;
	}

	/**
	 * @param ct_fbpd
	 *            查体--腹部--脾大
	 */
	public void setCt_fbpd(String ct_fbpd) {
		this.ct_fbpd = ct_fbpd;
	}

	/**
	 * @param ct_fbpdqt
	 *            查体--腹部--脾大其他
	 */
	public void setCt_fbpdqt(String ct_fbpdqt) {
		this.ct_fbpdqt = ct_fbpdqt;
	}

	/**
	 * @param ct_fbydzy
	 *            查体--腹部--移动性浊音
	 */
	public void setCt_fbydzy(String ct_fbydzy) {
		this.ct_fbydzy = ct_fbydzy;
	}

	/**
	 * @param ct_fbydzyqt
	 *            查体--腹部--移动性浊音其他
	 */
	public void setCt_fbydzyqt(String ct_fbydzyqt) {
		this.ct_fbydzyqt = ct_fbydzyqt;
	}

	/**
	 * @param ct_xzsz
	 *            查体--下肢水肿
	 */
	public void setCt_xzsz(String ct_xzsz) {
		this.ct_xzsz = ct_xzsz;
	}

	/**
	 * @param ct_zbdmbd
	 *            查体--足背动脉搏动
	 */
	public void setCt_zbdmbd(String ct_zbdmbd) {
		this.ct_zbdmbd = ct_zbdmbd;
	}

	/**
	 * @param ct_gmzz
	 *            查体--肛门指诊
	 */
	public void setCt_gmzz(String ct_gmzz) {
		this.ct_gmzz = ct_gmzz;
	}

	/**
	 * @param ct_gmzzqt
	 *            查体--肛门指诊其他
	 */
	public void setCt_gmzzqt(String ct_gmzzqt) {
		this.ct_gmzzqt = ct_gmzzqt;
	}

	/**
	 * @param ct_rxqk
	 *            查体--乳腺情况
	 */
	public void setCt_rxqk(String ct_rxqk) {
		this.ct_rxqk = ct_rxqk;
	}

	/**
	 * @param ct_rxqt
	 *            查体--乳腺其他
	 */
	public void setCt_rxqt(String ct_rxqt) {
		this.ct_rxqt = ct_rxqt;
	}

	/**
	 * @param ct_fkwy
	 *            查体--妇科--外阴
	 */
	public void setCt_fkwy(String ct_fkwy) {
		this.ct_fkwy = ct_fkwy;
	}

	/**
	 * @param ct_fkwyqt
	 *            查体--妇科--外阴其他
	 */
	public void setCt_fkwyqt(String ct_fkwyqt) {
		this.ct_fkwyqt = ct_fkwyqt;
	}

	/**
	 * @param ct_fkyd
	 *            查体--妇科--阴道
	 */
	public void setCt_fkyd(String ct_fkyd) {
		this.ct_fkyd = ct_fkyd;
	}

	/**
	 * @param ct_fkydqt
	 *            查体--妇科--阴道其他
	 */
	public void setCt_fkydqt(String ct_fkydqt) {
		this.ct_fkydqt = ct_fkydqt;
	}

	/**
	 * @param ct_fkgj
	 *            查体--妇科--宫颈
	 */
	public void setCt_fkgj(String ct_fkgj) {
		this.ct_fkgj = ct_fkgj;
	}

	/**
	 * @param ct_fkgjqt
	 *            查体--妇科--宫颈其他
	 */
	public void setCt_fkgjqt(String ct_fkgjqt) {
		this.ct_fkgjqt = ct_fkgjqt;
	}

	/**
	 * @param ct_fkgt
	 *            查体--妇科--宫体
	 */
	public void setCt_fkgt(String ct_fkgt) {
		this.ct_fkgt = ct_fkgt;
	}

	/**
	 * @param ct_fkgtqt
	 *            查体--妇科--宫体其他
	 */
	public void setCt_fkgtqt(String ct_fkgtqt) {
		this.ct_fkgtqt = ct_fkgtqt;
	}

	/**
	 * @param ct_fkfj
	 *            查体--妇科--附件
	 */
	public void setCt_fkfj(String ct_fkfj) {
		this.ct_fkfj = ct_fkfj;
	}

	/**
	 * @param ct_fkfjqt
	 *            查体--妇科--附件其他
	 */
	public void setCt_fkfjqt(String ct_fkfjqt) {
		this.ct_fkfjqt = ct_fkfjqt;
	}

	/**
	 * @param ct_qt
	 *            查体--其他
	 */
	public void setCt_qt(String ct_qt) {
		this.ct_qt = ct_qt;
	}

	public String toString() {
		return (new ToStringBuilder(this)).append("jktj_ctid", getJktj_ctid()).toString();
	}

	public int hashCode() {
		return (new HashCodeBuilder(17, 37)).append(getJktj_ctid()).toHashCode();
	}

	public boolean equals(Object o) {
		if (!(o instanceof jktj_ctDTO)) {
			return false;
		}
		if (o == this) {
            return true;
        }
		jktj_ctDTO me = (jktj_ctDTO) o;
		return new EqualsBuilder().append(getJktj_ctid(), me.getJktj_ctid()).isEquals();
	}

}
