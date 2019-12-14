package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class jktj_fzjcDTO {
	/**
	 * 默认构造函数
	 */
	public jktj_fzjcDTO() {
	}

	private String jktj_fzjcid; // 健康体检--辅助检查ID(SEQ_JKJC_FZJCID)
	private String yyid00; // 机构ID
	private String df_id; // 健康档案ID
	private String jktjcs; // 健康体检次数
	private String fzjc_kfxt; // 辅助检查--空腹血糖
	private String fzjc_kfxthk; // 辅助检查--空腹血糖mg
	private String fzjc_xcghdb; // 辅助检查--血常规--血红蛋白
	private String fzjc_xcgbxb; //辅助检查--血常规--白细胞
	private String fzjc_xcgxxb; //辅助检查--血常规--血小板
	private String fzjc_xcgqt; // 辅助检查--血常规--其他
	private String fzjc_ncgndb; //辅助检查--尿常规-- 尿蛋白(1.-,2.+,3.++,4.+++,5.++++,6.+-)
	private String fzjc_ncgnt; // 辅助检查--尿常规--尿糖(1.-,2.+,3.++,4.+++,5.++++,6.+-)
	private String fzjc_ncgnqt; //
	private String fzjc_ncgnqx; //辅助检查--尿常规--尿潜血(1.-,2.+,3.++,4.+++,5.++++,6.+-)
	private String fzjc_ncgqt; // 辅助检查--尿常规--其他
	private String fzjc_nwlbdb; // 辅助检查--尿微量白蛋白
	private String fzjc_dbqx; // 辅助检查--大便潜血(1-阴性,2-阳性)
	private String fzjc_ggnxqb; // 辅助检查--肝功能--血清谷丙转氨酶
	private String fzjc_ggnxqc; // 辅助检查--肝功能--血清谷草转氨酶
	private String fzjc_ggnbdb; //辅助检查--肝功能--白蛋白
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
	private String fzjc_yxgyky; // 辅助检查--乙型肝炎表面抗原(1-阴性,2-阳性)
	private String fzjc_yd; // 辅助检查--眼底(1-正常,2-异常)
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
	private String fzjc_ncgntt; // 尿酮体(1.-,2.+,3.++,4.+++,5.++++,6.+-)

	public String getFzjc_ncgbxb() {
		return fzjc_ncgbxb;
	}

	public void setFzjc_ncgbxb(String fzjc_ncgbxb) {
		this.fzjc_ncgbxb = fzjc_ncgbxb;
	}

	public String getFzjc_ncgdhs() {
		return fzjc_ncgdhs;
	}

	public void setFzjc_ncgdhs(String fzjc_ncgdhs) {
		this.fzjc_ncgdhs = fzjc_ncgdhs;
	}

	public String getFzjc_ncgyxsy() {
		return fzjc_ncgyxsy;
	}

	public void setFzjc_ncgyxsy(String fzjc_ncgyxsy) {
		this.fzjc_ncgyxsy = fzjc_ncgyxsy;
	}

	public String getFzjc_ggnag() {
		return fzjc_ggnag;
	}

	public void setFzjc_ggnag(String fzjc_ggnag) {
		this.fzjc_ggnag = fzjc_ggnag;
	}

	public String getFzjc_ggnudbil() {
		return fzjc_ggnudbil;
	}

	public void setFzjc_ggnudbil(String fzjc_ggnudbil) {
		this.fzjc_ggnudbil = fzjc_ggnudbil;
	}

	public String getFzjc_ncgalp() {
		return fzjc_ncgalp;
	}

	public void setFzjc_ncgalp(String fzjc_ncgalp) {
		this.fzjc_ncgalp = fzjc_ncgalp;
	}

	public String getFzjc_ggnggt() {
		return fzjc_ggnggt;
	}

	public void setFzjc_ggnggt(String fzjc_ggnggt) {
		this.fzjc_ggnggt = fzjc_ggnggt;
	}

	public String getFzjc_sgnns() {
		return fzjc_sgnns;
	}

	public void setFzjc_sgnns(String fzjc_sgnns) {
		this.fzjc_sgnns = fzjc_sgnns;
	}

	/**
	 * 健康体检--辅助检查ID(SEQ_JKJC_FZJCID)
	 *
	 * @hibernate.property column="jktj_fzjcid"
	 * @return jktj_fzjcid
	 */
	public String getJktj_fzjcid() {
		return this.jktj_fzjcid;
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
	 * 健康档案ID
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
	 * 辅助检查--空腹血糖
	 *
	 * @hibernate.property column="fzjc_kfxt"
	 * @return fzjc_kfxt
	 */
	public String getFzjc_kfxt() {
		return this.fzjc_kfxt;
	}

	/**
	 * 辅助检查--空腹血糖mg
	 *
	 * @hibernate.property column="fzjc_kfxthk"
	 * @return fzjc_kfxthk
	 */
	public String getFzjc_kfxthk() {
		return this.fzjc_kfxthk;
	}

	/**
	 * 辅助检查--血常规--血红蛋白
	 *
	 * @hibernate.property column="fzjc_xcghdb"
	 * @return fzjc_xcghdb
	 */
	public String getFzjc_xcghdb() {
		return this.fzjc_xcghdb;
	}

	/**
	 *
	 * @hibernate.property column="fzjc_xcgbxb"
	 * @return fzjc_xcgbxb
	 */
	public String getFzjc_xcgbxb() {
		return this.fzjc_xcgbxb;
	}

	/**
	 *
	 * @hibernate.property column="fzjc_xcgxxb"
	 * @return fzjc_xcgxxb
	 */
	public String getFzjc_xcgxxb() {
		return this.fzjc_xcgxxb;
	}

	/**
	 * 辅助检查--血常规--其他
	 *
	 * @hibernate.property column="fzjc_xcgqt"
	 * @return fzjc_xcgqt
	 */
	public String getFzjc_xcgqt() {
		return this.fzjc_xcgqt;
	}

	/**
	 *
	 * @hibernate.property column="fzjc_ncgndb"
	 * @return fzjc_ncgndb
	 */
	public String getFzjc_ncgndb() {
		return this.fzjc_ncgndb;
	}

	/**
	 * 辅助检查--尿常规--尿糖
	 *
	 * @hibernate.property column="fzjc_ncgnt"
	 * @return fzjc_ncgnt
	 */
	public String getFzjc_ncgnt() {
		return this.fzjc_ncgnt;
	}

	/**
	 *
	 * /**
	 *
	 * @hibernate.property column="fzjc_ncgnqx"
	 * @return fzjc_ncgnqx
	 */
	public String getFzjc_ncgnqx() {
		return this.fzjc_ncgnqx;
	}

	/**
	 * 辅助检查--尿常规--其他
	 *
	 * @hibernate.property column="fzjc_ncgqt"
	 * @return fzjc_ncgqt
	 */
	public String getFzjc_ncgqt() {
		return this.fzjc_ncgqt;
	}

	/**
	 * 辅助检查--尿微量白蛋白
	 *
	 * @hibernate.property column="fzjc_nwlbdb"
	 * @return fzjc_nwlbdb
	 */
	public String getFzjc_nwlbdb() {
		return this.fzjc_nwlbdb;
	}

	/**
	 * 辅助检查--大便潜血
	 *
	 * @hibernate.property column="fzjc_dbqx"
	 * @return fzjc_dbqx
	 */
	public String getFzjc_dbqx() {
		return this.fzjc_dbqx;
	}

	/**
	 * 辅助检查--肝功能--血清谷丙转氨酶
	 *
	 * @hibernate.property column="fzjc_ggnxqb"
	 * @return fzjc_ggnxqb
	 */
	public String getFzjc_ggnxqb() {
		return this.fzjc_ggnxqb;
	}

	/**
	 * 辅助检查--肝功能--血清谷草转氨酶
	 *
	 * @hibernate.property column="fzjc_ggnxqc"
	 * @return fzjc_ggnxqc
	 */
	public String getFzjc_ggnxqc() {
		return this.fzjc_ggnxqc;
	}

	/**
	 *
	 * @hibernate.property column="fzjc_ggnbdb"
	 * @return fzjc_ggnbdb
	 */
	public String getFzjc_ggnbdb() {
		return this.fzjc_ggnbdb;
	}

	/**
	 * 辅助检查--肝功能--总胆红素
	 *
	 * @hibernate.property column="fzjc_ggnzdh"
	 * @return fzjc_ggnzdh
	 */
	public String getFzjc_ggnzdh() {
		return this.fzjc_ggnzdh;
	}

	/**
	 * 辅助检查--肝功能--结合胆红素
	 *
	 * @hibernate.property column="fzjc_ggnjhd"
	 * @return fzjc_ggnjhd
	 */
	public String getFzjc_ggnjhd() {
		return this.fzjc_ggnjhd;
	}

	/**
	 * 辅助检查--肾功能--血清肌酐
	 *
	 * @hibernate.property column="fzjc_sgnqjg"
	 * @return fzjc_sgnqjg
	 */
	public String getFzjc_sgnqjg() {
		return this.fzjc_sgnqjg;
	}

	/**
	 * 辅助检查--肾功能--血尿素氮
	 *
	 * @hibernate.property column="fzjc_sgnnsd"
	 * @return fzjc_sgnnsd
	 */
	public String getFzjc_sgnnsd() {
		return this.fzjc_sgnnsd;
	}

	/**
	 * 辅助检查--肾功能--血钾浓度
	 *
	 * @hibernate.property column="fzjc_sgnjnd"
	 * @return fzjc_sgnjnd
	 */
	public String getFzjc_sgnjnd() {
		return this.fzjc_sgnjnd;
	}

	/**
	 * 辅助检查--肾功能--血钠浓度
	 *
	 * @hibernate.property column="fzjc_sgnnnd"
	 * @return fzjc_sgnnnd
	 */
	public String getFzjc_sgnnnd() {
		return this.fzjc_sgnnnd;
	}

	/**
	 * 辅助检查--血脂--总胆固醇
	 *
	 * @hibernate.property column="fzjc_xzzdgc"
	 * @return fzjc_xzzdgc
	 */
	public String getFzjc_xzzdgc() {
		return this.fzjc_xzzdgc;
	}

	/**
	 * 辅助检查--血脂--甘油三酯
	 *
	 * @hibernate.property column="fzjc_xzgysz"
	 * @return fzjc_xzgysz
	 */
	public String getFzjc_xzgysz() {
		return this.fzjc_xzgysz;
	}

	/**
	 * 辅助检查--血脂--血清低密度脂蛋白胆固醇
	 *
	 * @hibernate.property column="fzjc_xzdmdz"
	 * @return fzjc_xzdmdz
	 */
	public String getFzjc_xzdmdz() {
		return this.fzjc_xzdmdz;
	}

	/**
	 * 辅助检查--血脂--血清高密度脂蛋白胆固醇
	 *
	 * @hibernate.property column="fzjc_xzgmdz"
	 * @return fzjc_xzgmdz
	 */
	public String getFzjc_xzgmdz() {
		return this.fzjc_xzgmdz;
	}

	/**
	 * 辅助检查--糖化血红蛋白
	 *
	 * @hibernate.property column="fzjc_thxhdb"
	 * @return fzjc_thxhdb
	 */
	public String getFzjc_thxhdb() {
		return this.fzjc_thxhdb;
	}

	/**
	 * 辅助检查--乙型肝炎表面抗原
	 *
	 * @hibernate.property column="fzjc_yxgyky"
	 * @return fzjc_yxgyky
	 */
	public String getFzjc_yxgyky() {
		return this.fzjc_yxgyky;
	}

	/**
	 * 辅助检查--眼底
	 *
	 * @hibernate.property column="fzjc_yd"
	 * @return fzjc_yd
	 */
	public String getFzjc_yd() {
		return this.fzjc_yd;
	}

	/**
	 * 辅助检查--眼底异常
	 *
	 * @hibernate.property column="fzjc_ydqt"
	 * @return fzjc_ydqt
	 */
	public String getFzjc_ydqt() {
		return this.fzjc_ydqt;
	}

	/**
	 * 辅助检查--心电图
	 *
	 * @hibernate.property column="fzjc_xdt"
	 * @return fzjc_xdt
	 */
	public String getFzjc_xdt() {
		return this.fzjc_xdt;
	}

	/**
	 * 辅助检查--心电图异常
	 *
	 * @hibernate.property column="fzjc_xdtqt"
	 * @return fzjc_xdtqt
	 */
	public String getFzjc_xdtqt() {
		return this.fzjc_xdtqt;
	}

	/**
	 * 辅助检查--胸部X线片
	 *
	 * @hibernate.property column="fzjc_xbxxp"
	 * @return fzjc_xbxxp
	 */
	public String getFzjc_xbxxp() {
		return this.fzjc_xbxxp;
	}

	/**
	 * 辅助检查--胸部X线片异常
	 *
	 * @hibernate.property column="fzjc_xbxxqt"
	 * @return fzjc_xbxxqt
	 */
	public String getFzjc_xbxxqt() {
		return this.fzjc_xbxxqt;
	}

	/**
	 * 辅助检查--B超
	 *
	 * @hibernate.property column="fzjc_bc"
	 * @return fzjc_bc
	 */
	public String getFzjc_bc() {
		return this.fzjc_bc;
	}

	/**
	 * 辅助检查--B超其他
	 *
	 * @hibernate.property column="fzjc_bcqt"
	 * @return fzjc_bcqt
	 */
	public String getFzjc_bcqt() {
		return this.fzjc_bcqt;
	}

	/**
	 * 辅助检查--宫颈涂片
	 *
	 * @hibernate.property column="fzjc_gjtp"
	 * @return fzjc_gjtp
	 */
	public String getFzjc_gjtp() {
		return this.fzjc_gjtp;
	}

	/**
	 * 辅助检查--宫颈涂片异常
	 *
	 * @hibernate.property column="fzjc_gjtpqt"
	 * @return fzjc_gjtpqt
	 */
	public String getFzjc_gjtpqt() {
		return this.fzjc_gjtpqt;
	}

	/**
	 * 辅助检查--其他
	 *
	 * @hibernate.property column="fzjc_qt0000"
	 * @return fzjc_qt0000
	 */
	public String getFzjc_qt0000() {
		return this.fzjc_qt0000;
	}

	/**
	 * @param jktj_fzjcid
	 *            健康体检--辅助检查ID(SEQ_JKJC_FZJCID)
	 */
	public void setJktj_fzjcid(String jktj_fzjcid) {
		this.jktj_fzjcid = jktj_fzjcid;
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
	 *            健康档案ID
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
	 * @param fzjc_kfxt
	 *            辅助检查--空腹血糖
	 */
	public void setFzjc_kfxt(String fzjc_kfxt) {
		this.fzjc_kfxt = fzjc_kfxt;
	}

	/**
	 * @param fzjc_kfxthk
	 *            辅助检查--空腹血糖mg
	 */
	public void setFzjc_kfxthk(String fzjc_kfxthk) {
		this.fzjc_kfxthk = fzjc_kfxthk;
	}

	/**
	 * @param fzjc_xcghdb
	 *            辅助检查--血常规--血红蛋白
	 */
	public void setFzjc_xcghdb(String fzjc_xcghdb) {
		this.fzjc_xcghdb = fzjc_xcghdb;
	}

	/**
	 * @param fzjc_xcgbxb
	 */
	public void setFzjc_xcgbxb(String fzjc_xcgbxb) {
		this.fzjc_xcgbxb = fzjc_xcgbxb;
	}

	/**
	 * @param fzjc_xcgxxb
	 */
	public void setFzjc_xcgxxb(String fzjc_xcgxxb) {
		this.fzjc_xcgxxb = fzjc_xcgxxb;
	}

	/**
	 * @param fzjc_xcgqt
	 *            辅助检查--血常规--其他
	 */
	public void setFzjc_xcgqt(String fzjc_xcgqt) {
		this.fzjc_xcgqt = fzjc_xcgqt;
	}

	/**
	 * @param fzjc_ncgndb
	 */
	public void setFzjc_ncgndb(String fzjc_ncgndb) {
		this.fzjc_ncgndb = fzjc_ncgndb;
	}

	/**
	 * @param fzjc_ncgnt
	 *            辅助检查--尿常规--尿糖
	 */
	public void setFzjc_ncgnt(String fzjc_ncgnt) {
		this.fzjc_ncgnt = fzjc_ncgnt;
	}

	/**
	 * @param fzjc_ncgnqx
	 */
	public void setFzjc_ncgnqx(String fzjc_ncgnqx) {
		this.fzjc_ncgnqx = fzjc_ncgnqx;
	}

	/**
	 * @param fzjc_ncgqt
	 *            辅助检查--尿常规--其他
	 */
	public void setFzjc_ncgqt(String fzjc_ncgqt) {
		this.fzjc_ncgqt = fzjc_ncgqt;
	}

	/**
	 * @param fzjc_nwlbdb
	 *            辅助检查--尿微量白蛋白
	 */
	public void setFzjc_nwlbdb(String fzjc_nwlbdb) {
		this.fzjc_nwlbdb = fzjc_nwlbdb;
	}

	/**
	 * @param fzjc_dbqx
	 *            辅助检查--大便潜血
	 */
	public void setFzjc_dbqx(String fzjc_dbqx) {
		this.fzjc_dbqx = fzjc_dbqx;
	}

	/**
	 * @param fzjc_ggnxqb
	 *            辅助检查--肝功能--血清谷丙转氨酶
	 */
	public void setFzjc_ggnxqb(String fzjc_ggnxqb) {
		this.fzjc_ggnxqb = fzjc_ggnxqb;
	}

	/**
	 * @param fzjc_ggnxqc
	 *            辅助检查--肝功能--血清谷草转氨酶
	 */
	public void setFzjc_ggnxqc(String fzjc_ggnxqc) {
		this.fzjc_ggnxqc = fzjc_ggnxqc;
	}

	/**
	 * @param fzjc_ggnbdb
	 */
	public void setFzjc_ggnbdb(String fzjc_ggnbdb) {
		this.fzjc_ggnbdb = fzjc_ggnbdb;
	}

	/**
	 * @param fzjc_ggnzdh
	 *            辅助检查--肝功能--总胆红素
	 */
	public void setFzjc_ggnzdh(String fzjc_ggnzdh) {
		this.fzjc_ggnzdh = fzjc_ggnzdh;
	}

	/**
	 * @param fzjc_ggnjhd
	 *            辅助检查--肝功能--结合胆红素
	 */
	public void setFzjc_ggnjhd(String fzjc_ggnjhd) {
		this.fzjc_ggnjhd = fzjc_ggnjhd;
	}

	/**
	 * @param fzjc_sgnqjg
	 *            辅助检查--肾功能--血清肌酐
	 */
	public void setFzjc_sgnqjg(String fzjc_sgnqjg) {
		this.fzjc_sgnqjg = fzjc_sgnqjg;
	}

	/**
	 * @param fzjc_sgnnsd
	 *            辅助检查--肾功能--血尿素氮
	 */
	public void setFzjc_sgnnsd(String fzjc_sgnnsd) {
		this.fzjc_sgnnsd = fzjc_sgnnsd;
	}

	/**
	 * @param fzjc_sgnjnd
	 *            辅助检查--肾功能--血钾浓度
	 */
	public void setFzjc_sgnjnd(String fzjc_sgnjnd) {
		this.fzjc_sgnjnd = fzjc_sgnjnd;
	}

	/**
	 * @param fzjc_sgnnnd
	 *            辅助检查--肾功能--血钠浓度
	 */
	public void setFzjc_sgnnnd(String fzjc_sgnnnd) {
		this.fzjc_sgnnnd = fzjc_sgnnnd;
	}

	/**
	 * @param fzjc_xzzdgc
	 *            辅助检查--血脂--总胆固醇
	 */
	public void setFzjc_xzzdgc(String fzjc_xzzdgc) {
		this.fzjc_xzzdgc = fzjc_xzzdgc;
	}

	/**
	 * @param fzjc_xzgysz
	 *            辅助检查--血脂--甘油三酯
	 */
	public void setFzjc_xzgysz(String fzjc_xzgysz) {
		this.fzjc_xzgysz = fzjc_xzgysz;
	}

	/**
	 * @param fzjc_xzdmdz
	 *            辅助检查--血脂--血清低密度脂蛋白胆固醇
	 */
	public void setFzjc_xzdmdz(String fzjc_xzdmdz) {
		this.fzjc_xzdmdz = fzjc_xzdmdz;
	}

	/**
	 * @param fzjc_xzgmdz
	 *            辅助检查--血脂--血清高密度脂蛋白胆固醇
	 */
	public void setFzjc_xzgmdz(String fzjc_xzgmdz) {
		this.fzjc_xzgmdz = fzjc_xzgmdz;
	}

	/**
	 * @param fzjc_thxhdb
	 *            辅助检查--糖化血红蛋白
	 */
	public void setFzjc_thxhdb(String fzjc_thxhdb) {
		this.fzjc_thxhdb = fzjc_thxhdb;
	}

	/**
	 * @param fzjc_yxgyky
	 *            辅助检查--乙型肝炎表面抗原
	 */
	public void setFzjc_yxgyky(String fzjc_yxgyky) {
		this.fzjc_yxgyky = fzjc_yxgyky;
	}

	/**
	 * @param fzjc_yd
	 *            辅助检查--眼底
	 */
	public void setFzjc_yd(String fzjc_yd) {
		this.fzjc_yd = fzjc_yd;
	}

	/**
	 * @param fzjc_ydqt
	 *            辅助检查--眼底异常
	 */
	public void setFzjc_ydqt(String fzjc_ydqt) {
		this.fzjc_ydqt = fzjc_ydqt;
	}

	/**
	 * @param fzjc_xdt
	 *            辅助检查--心电图
	 */
	public void setFzjc_xdt(String fzjc_xdt) {
		this.fzjc_xdt = fzjc_xdt;
	}

	/**
	 * @param fzjc_xdtqt
	 *            辅助检查--心电图异常
	 */
	public void setFzjc_xdtqt(String fzjc_xdtqt) {
		this.fzjc_xdtqt = fzjc_xdtqt;
	}

	/**
	 * @param fzjc_xbxxp
	 *            辅助检查--胸部X线片
	 */
	public void setFzjc_xbxxp(String fzjc_xbxxp) {
		this.fzjc_xbxxp = fzjc_xbxxp;
	}

	/**
	 * @param fzjc_xbxxqt
	 *            辅助检查--胸部X线片异常
	 */
	public void setFzjc_xbxxqt(String fzjc_xbxxqt) {
		this.fzjc_xbxxqt = fzjc_xbxxqt;
	}

	/**
	 * @param fzjc_bc
	 *            辅助检查--B超
	 */
	public void setFzjc_bc(String fzjc_bc) {
		this.fzjc_bc = fzjc_bc;
	}

	/**
	 * @param fzjc_bcqt
	 *            辅助检查--B超其他
	 */
	public void setFzjc_bcqt(String fzjc_bcqt) {
		this.fzjc_bcqt = fzjc_bcqt;
	}

	/**
	 * @param fzjc_gjtp
	 *            辅助检查--宫颈涂片
	 */
	public void setFzjc_gjtp(String fzjc_gjtp) {
		this.fzjc_gjtp = fzjc_gjtp;
	}

	/**
	 * @param fzjc_gjtpqt
	 *            辅助检查--宫颈涂片异常
	 */
	public void setFzjc_gjtpqt(String fzjc_gjtpqt) {
		this.fzjc_gjtpqt = fzjc_gjtpqt;
	}

	/**
	 * @param fzjc_qt0000
	 *            辅助检查--其他
	 */
	public void setFzjc_qt0000(String fzjc_qt0000) {
		this.fzjc_qt0000 = fzjc_qt0000;
	}

	public String toString() {
		return (new ToStringBuilder(this)).append("jktj_fzjcid", getJktj_fzjcid()).toString();
	}

	public int hashCode() {
		return (new HashCodeBuilder(17, 37)).append(getJktj_fzjcid()).toHashCode();
	}

	public boolean equals(Object o) {
		if (!(o instanceof jktj_fzjcDTO)) {
			return false;
		}
		if (o == this) {
            return true;
        }
		jktj_fzjcDTO me = (jktj_fzjcDTO) o;
		return new EqualsBuilder().append(getJktj_fzjcid(), me.getJktj_fzjcid()).isEquals();
	}

	public String getFzjc_xdtfrist() {
		return fzjc_xdtfrist;
	}

	public void setFzjc_xdtfrist(String fzjc_xdtfrist) {
		this.fzjc_xdtfrist = fzjc_xdtfrist;
	}

	public String getFzjc_xdtsecond() {
		return fzjc_xdtsecond;
	}

	public void setFzjc_xdtsecond(String fzjc_xdtsecond) {
		this.fzjc_xdtsecond = fzjc_xdtsecond;
	}

	public String getFzjc_xdtthree() {
		return fzjc_xdtthree;
	}

	public void setFzjc_xdtthree(String fzjc_xdtthree) {
		this.fzjc_xdtthree = fzjc_xdtthree;
	}

	/**
	 * 尿酮体
	 *
	 * @return
	 */
	public String getFzjc_ncgntt() {
		return fzjc_ncgntt;
	}

	/**
	 * 尿酮体
	 *
	 * @param fzjc_ncgntt
	 */
	public void setFzjc_ncgntt(String fzjc_ncgntt) {
		this.fzjc_ncgntt = fzjc_ncgntt;
	}

}
