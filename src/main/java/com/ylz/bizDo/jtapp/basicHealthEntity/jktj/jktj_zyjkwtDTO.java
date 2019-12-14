package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class jktj_zyjkwtDTO {
	/**
	 * 默认构造函数
	 */
	public jktj_zyjkwtDTO() {
	}

	private String jktj_zyjkwtid; // 健康体检--主要健康问题ID
	private String yyid00; // 机构ID
	private String df_id; // 居民健康档案ID
	private String jktjcs; // 健康体检次数
	private String zyjkwt_nxg; // 主要健康问题--脑血管疾病（1--未发现，2--缺血性卒中，3--脑出血，4--蛛网膜下腔出血，5--短暂性脑缺血发作，6--其他）
	private String zyjkwt_nxgqt; // 主要健康问题--脑血管疾病--其他
	private String zyjkwt_sz; // 主要健康问题--肾脏疾病（1--未发现，2--糖尿病肾病，3--肾功能衰竭，4--急性肾炎，5--慢性肾炎，6--其他）
	private String zyjkwt_szqt; // 主要健康问题--肾脏疾病--其他
	private String zyjkwt_xzwfx; // 主要健康问题--心脏疾病（1--未发现，2--心肌梗死，3--心绞痛，4--冠状动脉血运重建，5--心力衰竭，6--心前区疼痛，7--其他）
	private String zyjkwt_xzqt; // 主要健康问题--心脏疾病--其他
	private String zyjkwt_xgwfx; // 主要健康问题--血管疾病（1--未发现，2--夹层动脉瘤，3--动脉闭塞性疾病，4--其他）
	private String zyjkwt_xgqt; // 主要健康问题--血管疾病--其他
	private String zyjkwt_ybwfx; // 主要健康问题--眼部疾病（1--未发现，2--视网膜出血或渗出，3--视乳头水肿，4--白内障，5--其他）
	private String zyjkwt_ybqt; // 主要健康问题--眼部疾病--其他
	private String zyjkwt_sjxtjb; // 主要健康问题--神经系统疾病(1-未发现,2-是)
	private String zyjkwt_sjxtqt; // 主要健康问题--神经系统疾病其他
	private String zyjkwt_qtxtjb; // 主要健康问题--其他系统疾病(1-未发现,2-是)
	private String zyjkwt_qtxtqt; // 主要健康问题--其他系统疾病其他
	private String zytzbs_phz; // 健康体检--中医体质辨识--平和质(1-是,2-基本是)
	private String zytzbs_qxz; // 健康体检--中医体质辨识--气虚质(1-是,2-倾向是)
	private String zytzbs_yangxz; // 健康体检--中医体质辨识--阳虚质(1-是,2-倾向是)
	private String zytzbs_yinxz; // 健康体检--中医体质辨识--阴虚质(1-是,2-倾向是)
	private String zytzbs_tsz; // 健康体检--中医体质辨识--痰湿质(1-是,2-倾向是)
	private String zytzbs_srz; // 健康体检--中医体质辨识--湿热质(1-是,2-倾向是)
	private String zytzbs_xyz; // 健康体检--中医体质辨识--血瘀质(1-是,2-倾向是)
	private String zytzbs_qyz; // 健康体检--中医体质辨识--气郁质(1-是,2-倾向是)
	private String zytzbs_tbz; // 健康体检--中医体质辨识--特秉质(1-是,2-倾向是)

	/**
	 * 健康体检--主要健康问题ID
	 *
	 * @hibernate.property column="jktj_zyjkwtid"
	 * @return jktj_zyjkwtid
	 */
	public String getJktj_zyjkwtid() {
		return this.jktj_zyjkwtid;
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
	 * 主要健康问题--脑血管疾病（1--未发现，2--缺血性卒中，3--脑出血，4--蛛网膜下腔出血，5--短暂性脑缺血发作，6--其他）
	 *
	 * @hibernate.property column="zyjkwt_nxg"
	 * @return zyjkwt_nxg
	 */
	public String getZyjkwt_nxg() {
		return this.zyjkwt_nxg;
	}

	/**
	 * 主要健康问题--脑血管疾病--其他
	 *
	 * @hibernate.property column="zyjkwt_nxgqt"
	 * @return zyjkwt_nxgqt
	 */
	public String getZyjkwt_nxgqt() {
		return this.zyjkwt_nxgqt;
	}

	/**
	 * 主要健康问题--肾脏疾病（1--未发现，2--糖尿病肾病，3--肾功能衰竭，4--急性肾炎，5--慢性肾炎，6--其他）
	 *
	 * @hibernate.property column="zyjkwt_sz"
	 * @return zyjkwt_sz
	 */
	public String getZyjkwt_sz() {
		return this.zyjkwt_sz;
	}

	/**
	 * 主要健康问题--肾脏疾病--其他
	 *
	 * @hibernate.property column="zyjkwt_szqt"
	 * @return zyjkwt_szqt
	 */
	public String getZyjkwt_szqt() {
		return this.zyjkwt_szqt;
	}

	/**
	 * 主要健康问题--心脏疾病（1--未发现，2--心肌梗死，3--心绞痛，4--冠状动脉血运重建，5--心力衰竭，6--心前区疼痛，7--其他）
	 *
	 * @hibernate.property column="zyjkwt_xzwfx"
	 * @return zyjkwt_xzwfx
	 */
	public String getZyjkwt_xzwfx() {
		return this.zyjkwt_xzwfx;
	}

	/**
	 * 主要健康问题--心脏疾病--其他
	 *
	 * @hibernate.property column="zyjkwt_xzqt"
	 * @return zyjkwt_xzqt
	 */
	public String getZyjkwt_xzqt() {
		return this.zyjkwt_xzqt;
	}

	/**
	 * 主要健康问题--血管疾病（1--未发现，2--夹层动脉瘤，3--动脉闭塞性疾病，4--其他）
	 *
	 * @hibernate.property column="zyjkwt_xgwfx"
	 * @return zyjkwt_xgwfx
	 */
	public String getZyjkwt_xgwfx() {
		return this.zyjkwt_xgwfx;
	}

	/**
	 * 主要健康问题--血管疾病--其他
	 *
	 * @hibernate.property column="zyjkwt_xgqt"
	 * @return zyjkwt_xgqt
	 */
	public String getZyjkwt_xgqt() {
		return this.zyjkwt_xgqt;
	}

	/**
	 * 主要健康问题--眼部疾病（1--未发现，2--视网膜出血或渗出，3--视乳头水肿，4--白内障，5--其他）
	 *
	 * @hibernate.property column="zyjkwt_ybwfx"
	 * @return zyjkwt_ybwfx
	 */
	public String getZyjkwt_ybwfx() {
		return this.zyjkwt_ybwfx;
	}

	/**
	 * 主要健康问题--眼部疾病--其他
	 *
	 * @hibernate.property column="zyjkwt_ybqt"
	 * @return zyjkwt_ybqt
	 */
	public String getZyjkwt_ybqt() {
		return this.zyjkwt_ybqt;
	}

	/**
	 * 主要健康问题--神经系统疾病（1--未发现，2--有）
	 *
	 * @hibernate.property column="zyjkwt_sjxtjb"
	 * @return zyjkwt_sjxtjb
	 */
	public String getZyjkwt_sjxtjb() {
		return this.zyjkwt_sjxtjb;
	}

	/**
	 * 主要健康问题--神经系统疾病其他
	 *
	 * @hibernate.property column="zyjkwt_sjxtqt"
	 * @return zyjkwt_sjxtqt
	 */
	public String getZyjkwt_sjxtqt() {
		return this.zyjkwt_sjxtqt;
	}

	/**
	 * 主要健康问题--其他系统疾病（1--未发现，2--有）
	 *
	 * @hibernate.property column="zyjkwt_qtxtjb"
	 * @return zyjkwt_qtxtjb
	 */
	public String getZyjkwt_qtxtjb() {
		return this.zyjkwt_qtxtjb;
	}

	/**
	 * 主要健康问题--其他系统疾病其他
	 *
	 * @hibernate.property column="zyjkwt_qtxtqt"
	 * @return zyjkwt_qtxtqt
	 */
	public String getZyjkwt_qtxtqt() {
		return this.zyjkwt_qtxtqt;
	}

	/**
	 * 健康体检--中医体质辨识--平和质
	 *
	 * @hibernate.property column="zytzbs_phz"
	 * @return zytzbs_phz
	 */
	public String getZytzbs_phz() {
		return this.zytzbs_phz;
	}

	/**
	 * 健康体检--中医体质辨识--气虚质
	 *
	 * @hibernate.property column="zytzbs_qxz"
	 * @return zytzbs_qxz
	 */
	public String getZytzbs_qxz() {
		return this.zytzbs_qxz;
	}

	/**
	 * 健康体检--中医体质辨识--阳虚质
	 *
	 * @hibernate.property column="zytzbs_yangxz"
	 * @return zytzbs_yangxz
	 */
	public String getZytzbs_yangxz() {
		return this.zytzbs_yangxz;
	}

	/**
	 * 健康体检--中医体质辨识--阴虚质
	 *
	 * @hibernate.property column="zytzbs_yinxz"
	 * @return zytzbs_yinxz
	 */
	public String getZytzbs_yinxz() {
		return this.zytzbs_yinxz;
	}

	/**
	 * 健康体检--中医体质辨识--痰湿质
	 *
	 * @hibernate.property column="zytzbs_tsz"
	 * @return zytzbs_tsz
	 */
	public String getZytzbs_tsz() {
		return this.zytzbs_tsz;
	}

	/**
	 * 健康体检--中医体质辨识--湿热质
	 *
	 * @hibernate.property column="zytzbs_srz"
	 * @return zytzbs_srz
	 */
	public String getZytzbs_srz() {
		return this.zytzbs_srz;
	}

	/**
	 * 健康体检--中医体质辨识--血瘀质
	 *
	 * @hibernate.property column="zytzbs_xyz"
	 * @return zytzbs_xyz
	 */
	public String getZytzbs_xyz() {
		return this.zytzbs_xyz;
	}

	/**
	 * 健康体检--中医体质辨识--气郁质
	 *
	 * @hibernate.property column="zytzbs_qyz"
	 * @return zytzbs_qyz
	 */
	public String getZytzbs_qyz() {
		return this.zytzbs_qyz;
	}

	/**
	 * 健康体检--中医体质辨识--特秉质
	 *
	 * @hibernate.property column="zytzbs_tbz"
	 * @return zytzbs_tbz
	 */
	public String getZytzbs_tbz() {
		return this.zytzbs_tbz;
	}

	/**
	 * @param jktj_zyjkwtid
	 *            健康体检--主要健康问题ID
	 */
	public void setJktj_zyjkwtid(String jktj_zyjkwtid) {
		this.jktj_zyjkwtid = jktj_zyjkwtid;
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
	 * @param zyjkwt_nxg
	 *            主要健康问题--脑血管疾病（1--未发现，2--缺血性卒中，3--脑出血，4--蛛网膜下腔出血，5--短暂性脑缺血发作，6-
	 *            -其他）
	 */
	public void setZyjkwt_nxg(String zyjkwt_nxg) {
		this.zyjkwt_nxg = zyjkwt_nxg;
	}

	/**
	 * @param zyjkwt_nxgqt
	 *            主要健康问题--脑血管疾病--其他
	 */
	public void setZyjkwt_nxgqt(String zyjkwt_nxgqt) {
		this.zyjkwt_nxgqt = zyjkwt_nxgqt;
	}

	/**
	 * @param zyjkwt_sz
	 *            主要健康问题--肾脏疾病（1--未发现，2--糖尿病肾病，3--肾功能衰竭，4--急性肾炎，5--慢性肾炎，6--其他）
	 */
	public void setZyjkwt_sz(String zyjkwt_sz) {
		this.zyjkwt_sz = zyjkwt_sz;
	}

	/**
	 * @param zyjkwt_szqt
	 *            主要健康问题--肾脏疾病--其他
	 */
	public void setZyjkwt_szqt(String zyjkwt_szqt) {
		this.zyjkwt_szqt = zyjkwt_szqt;
	}

	/**
	 * @param zyjkwt_xzwfx
	 *            主要健康问题--心脏疾病（1--未发现，2--心肌梗死，3--心绞痛，4--冠状动脉血运重建，5--心力衰竭，6--
	 *            心前区疼痛，7--其他）
	 */
	public void setZyjkwt_xzwfx(String zyjkwt_xzwfx) {
		this.zyjkwt_xzwfx = zyjkwt_xzwfx;
	}

	/**
	 * @param zyjkwt_xzqt
	 *            主要健康问题--心脏疾病--其他
	 */
	public void setZyjkwt_xzqt(String zyjkwt_xzqt) {
		this.zyjkwt_xzqt = zyjkwt_xzqt;
	}

	/**
	 * @param zyjkwt_xgwfx
	 *            主要健康问题--血管疾病（1--未发现，2--夹层动脉瘤，3--动脉闭塞性疾病，4--其他）
	 */
	public void setZyjkwt_xgwfx(String zyjkwt_xgwfx) {
		this.zyjkwt_xgwfx = zyjkwt_xgwfx;
	}

	/**
	 * @param zyjkwt_xgqt
	 *            主要健康问题--血管疾病--其他
	 */
	public void setZyjkwt_xgqt(String zyjkwt_xgqt) {
		this.zyjkwt_xgqt = zyjkwt_xgqt;
	}

	/**
	 * @param zyjkwt_ybwfx
	 *            主要健康问题--眼部疾病（1--未发现，2--视网膜出血或渗出，3--视乳头水肿，4--白内障，5--其他）
	 */
	public void setZyjkwt_ybwfx(String zyjkwt_ybwfx) {
		this.zyjkwt_ybwfx = zyjkwt_ybwfx;
	}

	/**
	 * @param zyjkwt_ybqt
	 *            主要健康问题--眼部疾病--其他
	 */
	public void setZyjkwt_ybqt(String zyjkwt_ybqt) {
		this.zyjkwt_ybqt = zyjkwt_ybqt;
	}

	/**
	 * @param zyjkwt_sjxtjb
	 *            主要健康问题--神经系统疾病（1--未发现，2--有）
	 */
	public void setZyjkwt_sjxtjb(String zyjkwt_sjxtjb) {
		this.zyjkwt_sjxtjb = zyjkwt_sjxtjb;
	}

	/**
	 * @param zyjkwt_sjxtqt
	 *            主要健康问题--神经系统疾病其他
	 */
	public void setZyjkwt_sjxtqt(String zyjkwt_sjxtqt) {
		this.zyjkwt_sjxtqt = zyjkwt_sjxtqt;
	}

	/**
	 * @param zyjkwt_qtxtjb
	 *            主要健康问题--其他系统疾病（1--未发现，2--有）
	 */
	public void setZyjkwt_qtxtjb(String zyjkwt_qtxtjb) {
		this.zyjkwt_qtxtjb = zyjkwt_qtxtjb;
	}

	/**
	 * @param zyjkwt_qtxtqt
	 *            主要健康问题--其他系统疾病其他
	 */
	public void setZyjkwt_qtxtqt(String zyjkwt_qtxtqt) {
		this.zyjkwt_qtxtqt = zyjkwt_qtxtqt;
	}

	/**
	 * @param zytzbs_phz
	 *            健康体检--中医体质辨识--平和质
	 */
	public void setZytzbs_phz(String zytzbs_phz) {
		this.zytzbs_phz = zytzbs_phz;
	}

	/**
	 * @param zytzbs_qxz
	 *            健康体检--中医体质辨识--气虚质
	 */
	public void setZytzbs_qxz(String zytzbs_qxz) {
		this.zytzbs_qxz = zytzbs_qxz;
	}

	/**
	 * @param zytzbs_yangxz
	 *            健康体检--中医体质辨识--阳虚质
	 */
	public void setZytzbs_yangxz(String zytzbs_yangxz) {
		this.zytzbs_yangxz = zytzbs_yangxz;
	}

	/**
	 * @param zytzbs_yinxz
	 *            健康体检--中医体质辨识--阴虚质
	 */
	public void setZytzbs_yinxz(String zytzbs_yinxz) {
		this.zytzbs_yinxz = zytzbs_yinxz;
	}

	/**
	 * @param zytzbs_tsz
	 *            健康体检--中医体质辨识--痰湿质
	 */
	public void setZytzbs_tsz(String zytzbs_tsz) {
		this.zytzbs_tsz = zytzbs_tsz;
	}

	/**
	 * @param zytzbs_srz
	 *            健康体检--中医体质辨识--湿热质
	 */
	public void setZytzbs_srz(String zytzbs_srz) {
		this.zytzbs_srz = zytzbs_srz;
	}

	/**
	 * @param zytzbs_xyz
	 *            健康体检--中医体质辨识--血瘀质
	 */
	public void setZytzbs_xyz(String zytzbs_xyz) {
		this.zytzbs_xyz = zytzbs_xyz;
	}

	/**
	 * @param zytzbs_qyz
	 *            健康体检--中医体质辨识--气郁质
	 */
	public void setZytzbs_qyz(String zytzbs_qyz) {
		this.zytzbs_qyz = zytzbs_qyz;
	}

	/**
	 * @param zytzbs_tbz
	 *            健康体检--中医体质辨识--特秉质
	 */
	public void setZytzbs_tbz(String zytzbs_tbz) {
		this.zytzbs_tbz = zytzbs_tbz;
	}

	public String toString() {
		return (new ToStringBuilder(this)).append("jktj_zyjkwtid", getJktj_zyjkwtid()).toString();
	}

	public int hashCode() {
		return (new HashCodeBuilder(17, 37)).append(getJktj_zyjkwtid()).toHashCode();
	}

	public boolean equals(Object o) {
		if (!(o instanceof jktj_zyjkwtDTO)) {
			return false;
		}
		if (o == this) {
            return true;
        }
		jktj_zyjkwtDTO me = (jktj_zyjkwtDTO) o;
		return new EqualsBuilder().append(getJktj_zyjkwtid(), me.getJktj_zyjkwtid()).isEquals();
	}

}
