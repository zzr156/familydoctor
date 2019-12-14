package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;


/**
 * <p>
 * Title: Jktj_ct
 * </p>
 * <p>
 * Description:健康体检--查体
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 *
 * @hibernate.class table="Jktj_ct"
 * @author 劳动力99开发小组
 * @version 1.0
 */
public class Jktj_ct {
	// TODO 自动产生的HSQL语句，根据需要自己修改
	// public final static String HSQL="from Jktj_ct where 1=1 and jktj_ctid
	// like :jktj_ctid and yyid00 like :yyid00 and df_id like :df_id and jktjcs
	// like :jktjcs and ct_pf like :ct_pf and ct_pfqt like :ct_pfqt and ct_gm
	// like :ct_gm and ct_gmqt like :ct_gmqt and ct_lbj like :ct_lbj and
	// ct_lbjqt like :ct_lbjqt and ct_ftzx like :ct_ftzx and ct_fhxy like
	// :ct_fhxy and ct_fhxyyc like :ct_fhxyyc and ct_fly like :ct_fly and
	// ct_flyqt like :ct_flyqt and ct_xzxl like :ct_xzxl and ct_xzxinl like
	// :ct_xzxinl and ct_xzzy like :ct_xzzy and ct_xzzyqt like :ct_xzzyqt and
	// ct_fbyt like :ct_fbyt and ct_fbytqt like :ct_fbytqt and ct_fbbk like
	// :ct_fbbk and ct_fbbkqt like :ct_fbbkqt and ct_fbgd like :ct_fbgd and
	// ct_fbgdqt like :ct_fbgdqt and ct_fbpd like :ct_fbpd and ct_fbpdqt like
	// :ct_fbpdqt and ct_fbydzy like :ct_fbydzy and ct_fbydzyqt like
	// :ct_fbydzyqt and ct_xzsz like :ct_xzsz and ct_zbdmbd like :ct_zbdmbd and
	// ct_gmzz like :ct_gmzz and ct_gmzzqt like :ct_gmzzqt and ct_rxqk like
	// :ct_rxqk and ct_rxqt like :ct_rxqt and ct_fkwy like :ct_fkwy and
	// ct_fkwyqt like :ct_fkwyqt and ct_fkyd like :ct_fkyd and ct_fkydqt like
	// :ct_fkydqt and ct_fkgj like :ct_fkgj and ct_fkgjqt like :ct_fkgjqt and
	// ct_fkgt like :ct_fkgt and ct_fkgtqt like :ct_fkgtqt and ct_fkfj like
	// :ct_fkfj and ct_fkfjqt like :ct_fkfjqt and ct_qt like :ct_qt ";
	/**
	 * 默认构造函数
	 */
	public Jktj_ct() {
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
	private String ct_fbbk; // 查体--腹部--包块
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
	@Id
    @Column(name = "JKTJ_CTID")
	public String getJktj_ctid() {
		return jktj_ctid;
	}

	public void setJktj_ctid(String jktj_ctid) {
		this.jktj_ctid = jktj_ctid;
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
    @Column(name = "CT_PF")
	public String getCt_pf() {
		return ct_pf;
	}

	public void setCt_pf(String ct_pf) {
		this.ct_pf = ct_pf;
	}
	@Basic
    @Column(name = "CT_PFQT")
	public String getCt_pfqt() {
		return ct_pfqt;
	}

	public void setCt_pfqt(String ct_pfqt) {
		this.ct_pfqt = ct_pfqt;
	}
	@Basic
    @Column(name = "CT_GM")
	public String getCt_gm() {
		return ct_gm;
	}

	public void setCt_gm(String ct_gm) {
		this.ct_gm = ct_gm;
	}
	@Basic
    @Column(name = "CT_GMQT")
	public String getCt_gmqt() {
		return ct_gmqt;
	}

	public void setCt_gmqt(String ct_gmqt) {
		this.ct_gmqt = ct_gmqt;
	}
	@Basic
    @Column(name = "CT_LBJ")
	public String getCt_lbj() {
		return ct_lbj;
	}

	public void setCt_lbj(String ct_lbj) {
		this.ct_lbj = ct_lbj;
	}
	@Basic
    @Column(name = "CT_LBJQT")
	public String getCt_lbjqt() {
		return ct_lbjqt;
	}

	public void setCt_lbjqt(String ct_lbjqt) {
		this.ct_lbjqt = ct_lbjqt;
	}
	@Basic
    @Column(name = "CT_FTZX")
	public String getCt_ftzx() {
		return ct_ftzx;
	}

	public void setCt_ftzx(String ct_ftzx) {
		this.ct_ftzx = ct_ftzx;
	}
	@Basic
    @Column(name = "CT_FHXY")
	public String getCt_fhxy() {
		return ct_fhxy;
	}

	public void setCt_fhxy(String ct_fhxy) {
		this.ct_fhxy = ct_fhxy;
	}
	@Basic
    @Column(name = "CT_FHXYYC")
	public String getCt_fhxyyc() {
		return ct_fhxyyc;
	}

	public void setCt_fhxyyc(String ct_fhxyyc) {
		this.ct_fhxyyc = ct_fhxyyc;
	}
	@Basic
    @Column(name = "CT_FLY")
	public String getCt_fly() {
		return ct_fly;
	}

	public void setCt_fly(String ct_fly) {
		this.ct_fly = ct_fly;
	}
	@Basic
    @Column(name = "CT_FLYQT")
	public String getCt_flyqt() {
		return ct_flyqt;
	}

	public void setCt_flyqt(String ct_flyqt) {
		this.ct_flyqt = ct_flyqt;
	}
	@Basic
    @Column(name = "CT_XZXL")
	public String getCt_xzxl() {
		return ct_xzxl;
	}

	public void setCt_xzxl(String ct_xzxl) {
		this.ct_xzxl = ct_xzxl;
	}
	@Basic
    @Column(name = "CT_XZXINL")
	public String getCt_xzxinl() {
		return ct_xzxinl;
	}

	public void setCt_xzxinl(String ct_xzxinl) {
		this.ct_xzxinl = ct_xzxinl;
	}
	@Basic
    @Column(name = "CT_XZZY")
	public String getCt_xzzy() {
		return ct_xzzy;
	}

	public void setCt_xzzy(String ct_xzzy) {
		this.ct_xzzy = ct_xzzy;
	}
	@Basic
    @Column(name = "CT_XZZYQT")
	public String getCt_xzzyqt() {
		return ct_xzzyqt;
	}

	public void setCt_xzzyqt(String ct_xzzyqt) {
		this.ct_xzzyqt = ct_xzzyqt;
	}
	@Basic
    @Column(name = "CT_FBYT")
	public String getCt_fbyt() {
		return ct_fbyt;
	}

	public void setCt_fbyt(String ct_fbyt) {
		this.ct_fbyt = ct_fbyt;
	}
	@Basic
    @Column(name = "CT_FBYTQT")
	public String getCt_fbytqt() {
		return ct_fbytqt;
	}

	public void setCt_fbytqt(String ct_fbytqt) {
		this.ct_fbytqt = ct_fbytqt;
	}
	@Basic
    @Column(name = "CT_FBBK")
	public String getCt_fbbk() {
		return ct_fbbk;
	}

	public void setCt_fbbk(String ct_fbbk) {
		this.ct_fbbk = ct_fbbk;
	}
	@Basic
    @Column(name = "CT_FBBKQT")
	public String getCt_fbbkqt() {
		return ct_fbbkqt;
	}

	public void setCt_fbbkqt(String ct_fbbkqt) {
		this.ct_fbbkqt = ct_fbbkqt;
	}
	@Basic
    @Column(name = "CT_FBGD")
	public String getCt_fbgd() {
		return ct_fbgd;
	}

	public void setCt_fbgd(String ct_fbgd) {
		this.ct_fbgd = ct_fbgd;
	}
	@Basic
    @Column(name = "CT_FBGDQT")
	public String getCt_fbgdqt() {
		return ct_fbgdqt;
	}

	public void setCt_fbgdqt(String ct_fbgdqt) {
		this.ct_fbgdqt = ct_fbgdqt;
	}
	@Basic
    @Column(name = "CT_FBPD")
	public String getCt_fbpd() {
		return ct_fbpd;
	}

	public void setCt_fbpd(String ct_fbpd) {
		this.ct_fbpd = ct_fbpd;
	}
	@Basic
    @Column(name = "CT_FBPDQT")
	public String getCt_fbpdqt() {
		return ct_fbpdqt;
	}

	public void setCt_fbpdqt(String ct_fbpdqt) {
		this.ct_fbpdqt = ct_fbpdqt;
	}
	@Basic
    @Column(name = "CT_FBYDZY")
	public String getCt_fbydzy() {
		return ct_fbydzy;
	}

	public void setCt_fbydzy(String ct_fbydzy) {
		this.ct_fbydzy = ct_fbydzy;
	}
	@Basic
    @Column(name = "CT_FBYDZYQT")
	public String getCt_fbydzyqt() {
		return ct_fbydzyqt;
	}

	public void setCt_fbydzyqt(String ct_fbydzyqt) {
		this.ct_fbydzyqt = ct_fbydzyqt;
	}
	@Basic
    @Column(name = "CT_XZSZ")
	public String getCt_xzsz() {
		return ct_xzsz;
	}

	public void setCt_xzsz(String ct_xzsz) {
		this.ct_xzsz = ct_xzsz;
	}
	@Basic
    @Column(name = "CT_ZBDMBD")
	public String getCt_zbdmbd() {
		return ct_zbdmbd;
	}

	public void setCt_zbdmbd(String ct_zbdmbd) {
		this.ct_zbdmbd = ct_zbdmbd;
	}
	@Basic
    @Column(name = "CT_GMZZ")
	public String getCt_gmzz() {
		return ct_gmzz;
	}

	public void setCt_gmzz(String ct_gmzz) {
		this.ct_gmzz = ct_gmzz;
	}
	@Basic
    @Column(name = "CT_GMZZQT")
	public String getCt_gmzzqt() {
		return ct_gmzzqt;
	}

	public void setCt_gmzzqt(String ct_gmzzqt) {
		this.ct_gmzzqt = ct_gmzzqt;
	}
	@Basic
    @Column(name = "CT_RXQK")
	public String getCt_rxqk() {
		return ct_rxqk;
	}

	public void setCt_rxqk(String ct_rxqk) {
		this.ct_rxqk = ct_rxqk;
	}
	@Basic
    @Column(name = "CT_RXQT")
	public String getCt_rxqt() {
		return ct_rxqt;
	}

	public void setCt_rxqt(String ct_rxqt) {
		this.ct_rxqt = ct_rxqt;
	}
	@Basic
    @Column(name = "CT_FKWY")
	public String getCt_fkwy() {
		return ct_fkwy;
	}

	public void setCt_fkwy(String ct_fkwy) {
		this.ct_fkwy = ct_fkwy;
	}
	@Basic
    @Column(name = "CT_FKWYQT")
	public String getCt_fkwyqt() {
		return ct_fkwyqt;
	}

	public void setCt_fkwyqt(String ct_fkwyqt) {
		this.ct_fkwyqt = ct_fkwyqt;
	}
	@Basic
    @Column(name = "CT_FKYD")
	public String getCt_fkyd() {
		return ct_fkyd;
	}

	public void setCt_fkyd(String ct_fkyd) {
		this.ct_fkyd = ct_fkyd;
	}
	@Basic
    @Column(name = "CT_FKYDQT")
	public String getCt_fkydqt() {
		return ct_fkydqt;
	}

	public void setCt_fkydqt(String ct_fkydqt) {
		this.ct_fkydqt = ct_fkydqt;
	}
	@Basic
    @Column(name = "CT_FKGJ")
	public String getCt_fkgj() {
		return ct_fkgj;
	}

	public void setCt_fkgj(String ct_fkgj) {
		this.ct_fkgj = ct_fkgj;
	}
	@Basic
    @Column(name = "CT_FKGJQT")
	public String getCt_fkgjqt() {
		return ct_fkgjqt;
	}

	public void setCt_fkgjqt(String ct_fkgjqt) {
		this.ct_fkgjqt = ct_fkgjqt;
	}
	@Basic
    @Column(name = "CT_FKGT")
	public String getCt_fkgt() {
		return ct_fkgt;
	}

	public void setCt_fkgt(String ct_fkgt) {
		this.ct_fkgt = ct_fkgt;
	}
	@Basic
    @Column(name = "CT_FKGTQT")
	public String getCt_fkgtqt() {
		return ct_fkgtqt;
	}

	public void setCt_fkgtqt(String ct_fkgtqt) {
		this.ct_fkgtqt = ct_fkgtqt;
	}
	@Basic
    @Column(name = "CT_FKFJ")
	public String getCt_fkfj() {
		return ct_fkfj;
	}

	public void setCt_fkfj(String ct_fkfj) {
		this.ct_fkfj = ct_fkfj;
	}
	@Basic
    @Column(name = "CT_FKFJQT")
	public String getCt_fkfjqt() {
		return ct_fkfjqt;
	}

	public void setCt_fkfjqt(String ct_fkfjqt) {
		this.ct_fkfjqt = ct_fkfjqt;
	}
	@Basic
    @Column(name = "CT_QT")
	public String getCt_qt() {
		return ct_qt;
	}

	public void setCt_qt(String ct_qt) {
		this.ct_qt = ct_qt;
	}

}