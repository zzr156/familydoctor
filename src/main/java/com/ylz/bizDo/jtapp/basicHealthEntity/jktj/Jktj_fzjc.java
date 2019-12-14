package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;


/**
 * <p>
 * Title: Jktj_fzjc
 * </p>
 * <p>
 * Description:健康体检--辅助检查
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 *
 * @hibernate.class table="Jktj_fzjc"
 * @author 劳动力99开发小组
 * @version 1.0
 */
public class Jktj_fzjc{
	// TODO 自动产生的HSQL语句，根据需要自己修改
	// public final static String HSQL="from Jktj_fzjc where 1=1 and jktj_fzjcid
	// like :jktj_fzjcid and yyid00 like :yyid00 and df_id like :df_id and
	// jktjcs like :jktjcs and fzjc_kfxt like :fzjc_kfxt and fzjc_kfxthk like
	// :fzjc_kfxthk and fzjc_xcghdb like :fzjc_xcghdb and fzjc_xcgbxb like
	// :fzjc_xcgbxb and fzjc_xcgxxb like :fzjc_xcgxxb and fzjc_xcgqt like
	// :fzjc_xcgqt and fzjc_ncgndb like :fzjc_ncgndb and fzjc_ncgnt like
	// :fzjc_ncgnt and fzjc_ncgntt like :fzjc_ncgntt and fzjc_ncgnqx like
	// :fzjc_ncgnqx and fzjc_ncgqt like :fzjc_ncgqt and fzjc_nwlbdb like
	// :fzjc_nwlbdb and fzjc_dbqx like :fzjc_dbqx and fzjc_ggnxqb like
	// :fzjc_ggnxqb and fzjc_ggnxqc like :fzjc_ggnxqc and fzjc_ggnbdb like
	// :fzjc_ggnbdb and fzjc_ggnzdh like :fzjc_ggnzdh and fzjc_ggnjhd like
	// :fzjc_ggnjhd and fzjc_sgnqjg like :fzjc_sgnqjg and fzjc_sgnnsd like
	// :fzjc_sgnnsd and fzjc_sgnjnd like :fzjc_sgnjnd and fzjc_sgnnnd like
	// :fzjc_sgnnnd and fzjc_xzzdgc like :fzjc_xzzdgc and fzjc_xzgysz like
	// :fzjc_xzgysz and fzjc_xzdmdz like :fzjc_xzdmdz and fzjc_xzgmdz like
	// :fzjc_xzgmdz and fzjc_thxhdb like :fzjc_thxhdb and fzjc_yxgyky like
	// :fzjc_yxgyky and fzjc_yd like :fzjc_yd and fzjc_ydqt like :fzjc_ydqt and
	// fzjc_xdt like :fzjc_xdt and fzjc_xdtqt like :fzjc_xdtqt and fzjc_xbxxp
	// like :fzjc_xbxxp and fzjc_xbxxqt like :fzjc_xbxxqt and fzjc_bc like
	// :fzjc_bc and fzjc_bcqt like :fzjc_bcqt and fzjc_gjtp like :fzjc_gjtp and
	// fzjc_gjtpqt like :fzjc_gjtpqt and fzjc_qt0000 like :fzjc_qt0000 ";
	/**
	 * 默认构造函数
	 */
	public Jktj_fzjc() {
	}

	private String jktj_fzjcid; // 健康体检--辅助检查ID(SEQ_JKJC_FZJCID)
	private String yyid00; // 机构ID
	private String df_id; // 健康档案ID
	private String jktjcs; // 健康体检次数
	private String fzjc_kfxt; // 辅助检查--空腹血糖
	private String fzjc_kfxthk; // 辅助检查--空腹血糖mg
	private String fzjc_xcghdb; // 辅助检查--血常规--血红蛋白
	private String fzjc_xcgbxb; //
	private String fzjc_xcgxxb; //
	private String fzjc_xcgqt; // 辅助检查--血常规--其他
	private String fzjc_ncgndb; //
	private String fzjc_ncgnt; // 辅助检查--尿常规--尿糖
	private String fzjc_ncgntt; //
	private String fzjc_ncgnqx; //
	private String fzjc_ncgqt; // 辅助检查--尿常规--其他
	private String fzjc_nwlbdb; // 辅助检查--尿微量白蛋白
	private String fzjc_dbqx; // 辅助检查--大便潜血
	private String fzjc_ggnxqb; // 辅助检查--肝功能--血清谷丙转氨酶
	private String fzjc_ggnxqc; // 辅助检查--肝功能--血清谷草转氨酶
	private String fzjc_ggnbdb; //
	private String fzjc_ggnzdh; // 辅助检查--肝功能--总胆红素
	private String fzjc_ggnjhd; // 辅助检查--肝功能--结合胆红素
	private String fzjc_sgnqjg; // 辅助检查--肾功能--血清肌酐
	private String fzjc_sgnnsd; // 辅助检查--肾功能--血尿素氮
	private String fzjc_sgnjnd; // 辅助检查--肾功能--血钾浓度
	private String fzjc_sgnnnd; // 辅助检查--肾功能--血钠浓度
	private String fzjc_xzzdgc; // 辅助检查--血脂--总胆固醇
	private String fzjc_xzgysz; // 辅助检查--血脂--甘油三酯
	private String fzjc_xzdmdz; // 辅助检查--血脂--血清低密度脂蛋白胆固醇
	private String fzjc_xzgmdz; // 辅助检查--血脂--血清高密度脂蛋白胆固醇
	private String fzjc_thxhdb; // 辅助检查--糖化血红蛋白
	private String fzjc_yxgyky; // 辅助检查--乙型肝炎表面抗原
	private String fzjc_yd; // 辅助检查--眼底
	private String fzjc_ydqt; // 辅助检查--眼底异常
	private String fzjc_xdt; // 辅助检查--心电图
	private String fzjc_xdtqt; // 辅助检查--心电图异常
	private String fzjc_xbxxp; // 辅助检查--胸部X线片
	private String fzjc_xbxxqt; // 辅助检查--胸部X线片异常
	private String fzjc_bc; // 辅助检查--B超
	private String fzjc_bcqt; // 辅助检查--B超其他
	private String fzjc_gjtp; // 辅助检查--宫颈涂片
	private String fzjc_gjtpqt; // 辅助检查--宫颈涂片异常
	private String fzjc_qt0000; // 辅助检查--其他

	private String fzjc_ncgbxb; // 尿常规 尿白细胞
	private String fzjc_ncgdhs; // 尿常规 尿胆红素
	private String fzjc_ncgyxsy; // 尿常规 尿亚硝酸盐
	private String fzjc_ggnag; // 肝功能 白蛋白/球蛋白比值(*)
	private String fzjc_ggnudbil; // 肝功能 间接胆红素(*)
	private String fzjc_ncgalp;// 肝功能 碱性磷酸酶(ALP)
	private String fzjc_ggnggt;// 肝功能 r-谷氨酰转移酶
	private String fzjc_sgnns;// 肾功能 尿酸
	private String fzjc_xdtfrist;
	private String fzjc_xdtsecond;
	private String fzjc_xdtthree;

	@Id
	@Column(name = "JKTJ_FZJCID")
	public String getJktj_fzjcid() {
		return jktj_fzjcid;
	}

	public void setJktj_fzjcid(String jktj_fzjcid) {
		this.jktj_fzjcid = jktj_fzjcid;
	}
	@Basic
    @Column(name = "FZJC_NCGBXB")
	public String getFzjc_ncgbxb() {
		return fzjc_ncgbxb;
	}

	public void setFzjc_ncgbxb(String fzjc_ncgbxb) {
		this.fzjc_ncgbxb = fzjc_ncgbxb;
	}
	@Basic
    @Column(name = "FZJC_NCGDHS")
	public String getFzjc_ncgdhs() {
		return fzjc_ncgdhs;
	}

	public void setFzjc_ncgdhs(String fzjc_ncgdhs) {
		this.fzjc_ncgdhs = fzjc_ncgdhs;
	}
	@Basic
    @Column(name = "FZJC_NCGYXSY")
	public String getFzjc_ncgyxsy() {
		return fzjc_ncgyxsy;
	}

	public void setFzjc_ncgyxsy(String fzjc_ncgyxsy) {
		this.fzjc_ncgyxsy = fzjc_ncgyxsy;
	}
	@Basic
    @Column(name = "FZJC_GGNAG")
	public String getFzjc_ggnag() {
		return fzjc_ggnag;
	}

	public void setFzjc_ggnag(String fzjc_ggnag) {
		this.fzjc_ggnag = fzjc_ggnag;
	}
	@Basic
    @Column(name = "FZJC_GGNUDBIL")
	public String getFzjc_ggnudbil() {
		return fzjc_ggnudbil;
	}

	public void setFzjc_ggnudbil(String fzjc_ggnudbil) {
		this.fzjc_ggnudbil = fzjc_ggnudbil;
	}
	@Basic
    @Column(name = "FZJC_NCGALP")
	public String getFzjc_ncgalp() {
		return fzjc_ncgalp;
	}

	public void setFzjc_ncgalp(String fzjc_ncgalp) {
		this.fzjc_ncgalp = fzjc_ncgalp;
	}
	@Basic
    @Column(name = "FZJC_GGNGGT")
	public String getFzjc_ggnggt() {
		return fzjc_ggnggt;
	}

	public void setFzjc_ggnggt(String fzjc_ggnggt) {
		this.fzjc_ggnggt = fzjc_ggnggt;
	}
	@Basic
    @Column(name = "FZJC_SGNNS")
	public String getFzjc_sgnns() {
		return fzjc_sgnns;
	}

	public void setFzjc_sgnns(String fzjc_sgnns) {
		this.fzjc_sgnns = fzjc_sgnns;
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
    @Column(name = "FZJC_KFXT")
	public String getFzjc_kfxt() {
		return fzjc_kfxt;
	}

	public void setFzjc_kfxt(String fzjc_kfxt) {
		this.fzjc_kfxt = fzjc_kfxt;
	}
	@Basic
    @Column(name = "FZJC_KFXTHK")
	public String getFzjc_kfxthk() {
		return fzjc_kfxthk;
	}

	public void setFzjc_kfxthk(String fzjc_kfxthk) {
		this.fzjc_kfxthk = fzjc_kfxthk;
	}
	@Basic
    @Column(name = "FZJC_XCGHDB")
	public String getFzjc_xcghdb() {
		return fzjc_xcghdb;
	}

	public void setFzjc_xcghdb(String fzjc_xcghdb) {
		this.fzjc_xcghdb = fzjc_xcghdb;
	}
	@Basic
    @Column(name = "FZJC_XCGBXB")
	public String getFzjc_xcgbxb() {
		return fzjc_xcgbxb;
	}

	public void setFzjc_xcgbxb(String fzjc_xcgbxb) {
		this.fzjc_xcgbxb = fzjc_xcgbxb;
	}
	@Basic
    @Column(name = "FZJC_XCGXXB")
	public String getFzjc_xcgxxb() {
		return fzjc_xcgxxb;
	}

	public void setFzjc_xcgxxb(String fzjc_xcgxxb) {
		this.fzjc_xcgxxb = fzjc_xcgxxb;
	}
	@Basic
    @Column(name = "FZJC_XCGQT")
	public String getFzjc_xcgqt() {
		return fzjc_xcgqt;
	}

	public void setFzjc_xcgqt(String fzjc_xcgqt) {
		this.fzjc_xcgqt = fzjc_xcgqt;
	}
	@Basic
    @Column(name = "FZJC_NCGNDB")
	public String getFzjc_ncgndb() {
		return fzjc_ncgndb;
	}

	public void setFzjc_ncgndb(String fzjc_ncgndb) {
		this.fzjc_ncgndb = fzjc_ncgndb;
	}
	@Basic
    @Column(name = "FZJC_NCGNT")
	public String getFzjc_ncgnt() {
		return fzjc_ncgnt;
	}

	public void setFzjc_ncgnt(String fzjc_ncgnt) {
		this.fzjc_ncgnt = fzjc_ncgnt;
	}
	@Basic
    @Column(name = "FZJC_NCGNTT")
	public String getFzjc_ncgntt() {
		return fzjc_ncgntt;
	}

	public void setFzjc_ncgntt(String fzjc_ncgntt) {
		this.fzjc_ncgntt = fzjc_ncgntt;
	}
	@Basic
    @Column(name = "FZJC_NCGNQX")
	public String getFzjc_ncgnqx() {
		return fzjc_ncgnqx;
	}

	public void setFzjc_ncgnqx(String fzjc_ncgnqx) {
		this.fzjc_ncgnqx = fzjc_ncgnqx;
	}
	@Basic
    @Column(name = "FZJC_NCGQT")
	public String getFzjc_ncgqt() {
		return fzjc_ncgqt;
	}

	public void setFzjc_ncgqt(String fzjc_ncgqt) {
		this.fzjc_ncgqt = fzjc_ncgqt;
	}
	@Basic
    @Column(name = "FZJC_NWLBDB")
	public String getFzjc_nwlbdb() {
		return fzjc_nwlbdb;
	}

	public void setFzjc_nwlbdb(String fzjc_nwlbdb) {
		this.fzjc_nwlbdb = fzjc_nwlbdb;
	}
	@Basic
    @Column(name = "FZJC_DBQX")
	public String getFzjc_dbqx() {
		return fzjc_dbqx;
	}

	public void setFzjc_dbqx(String fzjc_dbqx) {
		this.fzjc_dbqx = fzjc_dbqx;
	}
	@Basic
    @Column(name = "FZJC_GGNXQB")
	public String getFzjc_ggnxqb() {
		return fzjc_ggnxqb;
	}

	public void setFzjc_ggnxqb(String fzjc_ggnxqb) {
		this.fzjc_ggnxqb = fzjc_ggnxqb;
	}
	@Basic
    @Column(name = "FZJC_GGNXQC")
	public String getFzjc_ggnxqc() {
		return fzjc_ggnxqc;
	}

	public void setFzjc_ggnxqc(String fzjc_ggnxqc) {
		this.fzjc_ggnxqc = fzjc_ggnxqc;
	}
	@Basic
    @Column(name = "FZJC_GGNBDB")
	public String getFzjc_ggnbdb() {
		return fzjc_ggnbdb;
	}

	public void setFzjc_ggnbdb(String fzjc_ggnbdb) {
		this.fzjc_ggnbdb = fzjc_ggnbdb;
	}
	@Basic
    @Column(name = "FZJC_GGNZDH")
	public String getFzjc_ggnzdh() {
		return fzjc_ggnzdh;
	}

	public void setFzjc_ggnzdh(String fzjc_ggnzdh) {
		this.fzjc_ggnzdh = fzjc_ggnzdh;
	}
	@Basic
    @Column(name = "FZJC_GGNJHD")
	public String getFzjc_ggnjhd() {
		return fzjc_ggnjhd;
	}

	public void setFzjc_ggnjhd(String fzjc_ggnjhd) {
		this.fzjc_ggnjhd = fzjc_ggnjhd;
	}
	@Basic
    @Column(name = "FZJC_SGNQJG")
	public String getFzjc_sgnqjg() {
		return fzjc_sgnqjg;
	}

	public void setFzjc_sgnqjg(String fzjc_sgnqjg) {
		this.fzjc_sgnqjg = fzjc_sgnqjg;
	}
	@Basic
    @Column(name = "FZJC_SGNNSD")
	public String getFzjc_sgnnsd() {
		return fzjc_sgnnsd;
	}

	public void setFzjc_sgnnsd(String fzjc_sgnnsd) {
		this.fzjc_sgnnsd = fzjc_sgnnsd;
	}
	@Basic
    @Column(name = "FZJC_SGNJND")
	public String getFzjc_sgnjnd() {
		return fzjc_sgnjnd;
	}

	public void setFzjc_sgnjnd(String fzjc_sgnjnd) {
		this.fzjc_sgnjnd = fzjc_sgnjnd;
	}
	@Basic
    @Column(name = "FZJC_SGNNND")
	public String getFzjc_sgnnnd() {
		return fzjc_sgnnnd;
	}

	public void setFzjc_sgnnnd(String fzjc_sgnnnd) {
		this.fzjc_sgnnnd = fzjc_sgnnnd;
	}
	@Basic
    @Column(name = "FZJC_XZZDGC")
	public String getFzjc_xzzdgc() {
		return fzjc_xzzdgc;
	}

	public void setFzjc_xzzdgc(String fzjc_xzzdgc) {
		this.fzjc_xzzdgc = fzjc_xzzdgc;
	}
	@Basic
    @Column(name = "FZJC_XZGYSZ")
	public String getFzjc_xzgysz() {
		return fzjc_xzgysz;
	}

	public void setFzjc_xzgysz(String fzjc_xzgysz) {
		this.fzjc_xzgysz = fzjc_xzgysz;
	}
	@Basic
    @Column(name = "FZJC_XZDMDZ")
	public String getFzjc_xzdmdz() {
		return fzjc_xzdmdz;
	}

	public void setFzjc_xzdmdz(String fzjc_xzdmdz) {
		this.fzjc_xzdmdz = fzjc_xzdmdz;
	}
	@Basic
    @Column(name = "FZJC_XZGMDZ")
	public String getFzjc_xzgmdz() {
		return fzjc_xzgmdz;
	}

	public void setFzjc_xzgmdz(String fzjc_xzgmdz) {
		this.fzjc_xzgmdz = fzjc_xzgmdz;
	}
	@Basic
    @Column(name = "FZJC_THXHDB")
	public String getFzjc_thxhdb() {
		return fzjc_thxhdb;
	}

	public void setFzjc_thxhdb(String fzjc_thxhdb) {
		this.fzjc_thxhdb = fzjc_thxhdb;
	}
	@Basic
    @Column(name = "FZJC_YXGYKY")
	public String getFzjc_yxgyky() {
		return fzjc_yxgyky;
	}

	public void setFzjc_yxgyky(String fzjc_yxgyky) {
		this.fzjc_yxgyky = fzjc_yxgyky;
	}
	@Basic
    @Column(name = "fzjc_yd")
	public String getFzjc_yd() {
		return fzjc_yd;
	}

	public void setFzjc_yd(String fzjc_yd) {
		this.fzjc_yd = fzjc_yd;
	}
	@Basic
    @Column(name = "FZJC_YDQT")
	public String getFzjc_ydqt() {
		return fzjc_ydqt;
	}

	public void setFzjc_ydqt(String fzjc_ydqt) {
		this.fzjc_ydqt = fzjc_ydqt;
	}
	@Basic
    @Column(name = "FZJC_XDT")
	public String getFzjc_xdt() {
		return fzjc_xdt;
	}

	public void setFzjc_xdt(String fzjc_xdt) {
		this.fzjc_xdt = fzjc_xdt;
	}
	@Basic
    @Column(name = "FZJC_XDTQT")
	public String getFzjc_xdtqt() {
		return fzjc_xdtqt;
	}

	public void setFzjc_xdtqt(String fzjc_xdtqt) {
		this.fzjc_xdtqt = fzjc_xdtqt;
	}
	@Basic
    @Column(name = "FZJC_XBXXP")
	public String getFzjc_xbxxp() {
		return fzjc_xbxxp;
	}

	public void setFzjc_xbxxp(String fzjc_xbxxp) {
		this.fzjc_xbxxp = fzjc_xbxxp;
	}
	@Basic
    @Column(name = "FZJC_XBXXQT")
	public String getFzjc_xbxxqt() {
		return fzjc_xbxxqt;
	}

	public void setFzjc_xbxxqt(String fzjc_xbxxqt) {
		this.fzjc_xbxxqt = fzjc_xbxxqt;
	}
	@Basic
    @Column(name = "FZJC_BC")
	public String getFzjc_bc() {
		return fzjc_bc;
	}

	public void setFzjc_bc(String fzjc_bc) {
		this.fzjc_bc = fzjc_bc;
	}
	@Basic
    @Column(name = "FZJC_BCQT")
	public String getFzjc_bcqt() {
		return fzjc_bcqt;
	}

	public void setFzjc_bcqt(String fzjc_bcqt) {
		this.fzjc_bcqt = fzjc_bcqt;
	}
	@Basic
    @Column(name = "FZJC_GJTP")
	public String getFzjc_gjtp() {
		return fzjc_gjtp;
	}

	public void setFzjc_gjtp(String fzjc_gjtp) {
		this.fzjc_gjtp = fzjc_gjtp;
	}
	@Basic
    @Column(name = "FZJC_GJTPQT")
	public String getFzjc_gjtpqt() {
		return fzjc_gjtpqt;
	}

	public void setFzjc_gjtpqt(String fzjc_gjtpqt) {
		this.fzjc_gjtpqt = fzjc_gjtpqt;
	}
	@Basic
    @Column(name = "FZJC_QT0000")
	public String getFzjc_qt0000() {
		return fzjc_qt0000;
	}

	public void setFzjc_qt0000(String fzjc_qt0000) {
		this.fzjc_qt0000 = fzjc_qt0000;
	}
	@Basic
    @Column(name = "FZJC_XDTFRIST")
	public String getFzjc_xdtfrist() {
		return fzjc_xdtfrist;
	}

	public void setFzjc_xdtfrist(String fzjc_xdtfrist) {
		this.fzjc_xdtfrist = fzjc_xdtfrist;
	}
	@Basic
    @Column(name = "FZJC_XDTSECOND")
	public String getFzjc_xdtsecond() {
		return fzjc_xdtsecond;
	}

	public void setFzjc_xdtsecond(String fzjc_xdtsecond) {
		this.fzjc_xdtsecond = fzjc_xdtsecond;
	}
	@Basic
    @Column(name = "fzjc_xdtthree")
	public String getFzjc_xdtthree() {
		return fzjc_xdtthree;
	}

	public void setFzjc_xdtthree(String fzjc_xdtthree) {
		this.fzjc_xdtthree = fzjc_xdtthree;
	}

}