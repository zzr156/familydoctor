/*
 * CopyRight: StartTech 2005 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>Jktj_zyjkwt</code>.
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;


/**
 * <p>
 * Title: Jktj_zyjkwt
 * </p>
 * <p>
 * Description:健康体检--主要健康问题
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 * @hibernate.class table="Jktj_zyjkwt"
 * @author 劳动力99开发小组
 * @version 1.0
 */
 public class Jktj_zyjkwt
 {


					private  String jktj_zyjkwtid; //健康体检--主要健康问题ID
				private  String yyid00; //机构ID
				private  String df_id; //居民健康档案ID
				private  String jktjcs; //健康体检次数
				private  String zyjkwt_nxg; //主要健康问题--脑血管疾病（1--未发现，2--缺血性卒中，3--脑出血，4--蛛网膜下腔出血，5--短暂性脑缺血发作，6--其他）
				private  String zyjkwt_nxgqt; //主要健康问题--脑血管疾病--其他
				private  String zyjkwt_sz; //主要健康问题--肾脏疾病（1--未发现，2--糖尿病肾病，3--肾功能衰竭，4--急性肾炎，5--慢性肾炎，6--其他）
				private  String zyjkwt_szqt; //主要健康问题--肾脏疾病--其他
				private  String zyjkwt_xzwfx; //主要健康问题--心脏疾病（1--未发现，2--心肌梗死，3--心绞痛，4--冠状动脉血运重建，5--心力衰竭，6--心前区疼痛，7--其他）
				private  String zyjkwt_xzqt; //主要健康问题--心脏疾病--其他
				private  String zyjkwt_xgwfx; //主要健康问题--血管疾病（1--未发现，2--夹层动脉瘤，3--动脉闭塞性疾病，4--其他）
				private  String zyjkwt_xgqt; //主要健康问题--血管疾病--其他
				private  String zyjkwt_ybwfx; //主要健康问题--眼部疾病（1--未发现，2--视网膜出血或渗出，3--视乳头水肿，4--白内障，5--其他）
				private  String zyjkwt_ybqt; //主要健康问题--眼部疾病--其他
				private  String zyjkwt_sjxtjb; //主要健康问题--神经系统疾病（1--未发现，2--有）
				private  String zyjkwt_sjxtqt; //主要健康问题--神经系统疾病其他
				private  String zyjkwt_qtxtjb; //主要健康问题--其他系统疾病（1--未发现，2--有）
				private  String zyjkwt_qtxtqt; //主要健康问题--其他系统疾病其他
				private  String zytzbs_phz; //健康体检--中医体质辨识--平和质
				private  String zytzbs_qxz; //健康体检--中医体质辨识--气虚质
				private  String zytzbs_yangxz; //健康体检--中医体质辨识--阳虚质
				private  String zytzbs_yinxz; //健康体检--中医体质辨识--阴虚质
				private  String zytzbs_tsz; //健康体检--中医体质辨识--痰湿质
				private  String zytzbs_srz; //健康体检--中医体质辨识--湿热质
				private  String zytzbs_xyz; //健康体检--中医体质辨识--血瘀质
				private  String zytzbs_qyz; //健康体检--中医体质辨识--气郁质
				private  String zytzbs_tbz; //健康体检--中医体质辨识--特秉质
				@Id
				@Column(name = "JKTJ_ZYJKWTID")
				public String getJktj_zyjkwtid() {
					return jktj_zyjkwtid;
				}
				public void setJktj_zyjkwtid(String jktj_zyjkwtid) {
					this.jktj_zyjkwtid = jktj_zyjkwtid;
				}
				@Basic
				@Column(name = "yyid00")
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
				@Column(name = "jktjcs")
				public String getJktjcs() {
					return jktjcs;
				}
				public void setJktjcs(String jktjcs) {
					this.jktjcs = jktjcs;
				}
				@Basic
				@Column(name = "zyjkwt_nxg")
				public String getZyjkwt_nxg() {
					return zyjkwt_nxg;
				}
				public void setZyjkwt_nxg(String zyjkwt_nxg) {
					this.zyjkwt_nxg = zyjkwt_nxg;
				}
				@Basic
				@Column(name = "zyjkwt_nxgqt")
				public String getZyjkwt_nxgqt() {
					return zyjkwt_nxgqt;
				}
				public void setZyjkwt_nxgqt(String zyjkwt_nxgqt) {
					this.zyjkwt_nxgqt = zyjkwt_nxgqt;
				}
				@Basic
				@Column(name = "zyjkwt_sz")
				public String getZyjkwt_sz() {
					return zyjkwt_sz;
				}
				public void setZyjkwt_sz(String zyjkwt_sz) {
					this.zyjkwt_sz = zyjkwt_sz;
				}
				@Basic
				@Column(name = "zyjkwt_szqt")
				public String getZyjkwt_szqt() {
					return zyjkwt_szqt;
				}
				public void setZyjkwt_szqt(String zyjkwt_szqt) {
					this.zyjkwt_szqt = zyjkwt_szqt;
				}
				@Basic
				@Column(name = "zyjkwt_xzwfx")
				public String getZyjkwt_xzwfx() {
					return zyjkwt_xzwfx;
				}
				public void setZyjkwt_xzwfx(String zyjkwt_xzwfx) {
					this.zyjkwt_xzwfx = zyjkwt_xzwfx;
				}
				@Basic
				@Column(name = "zyjkwt_xzqt")
				public String getZyjkwt_xzqt() {
					return zyjkwt_xzqt;
				}
				public void setZyjkwt_xzqt(String zyjkwt_xzqt) {
					this.zyjkwt_xzqt = zyjkwt_xzqt;
				}
				@Basic
				@Column(name = "zyjkwt_xgwfx")
				public String getZyjkwt_xgwfx() {
					return zyjkwt_xgwfx;
				}
				public void setZyjkwt_xgwfx(String zyjkwt_xgwfx) {
					this.zyjkwt_xgwfx = zyjkwt_xgwfx;
				}
				@Basic
				@Column(name = "zyjkwt_xgqt")
				public String getZyjkwt_xgqt() {
					return zyjkwt_xgqt;
				}
				public void setZyjkwt_xgqt(String zyjkwt_xgqt) {
					this.zyjkwt_xgqt = zyjkwt_xgqt;
				}
				@Basic
				@Column(name = "zyjkwt_ybwfx")
				public String getZyjkwt_ybwfx() {
					return zyjkwt_ybwfx;
				}
				public void setZyjkwt_ybwfx(String zyjkwt_ybwfx) {
					this.zyjkwt_ybwfx = zyjkwt_ybwfx;
				}
				@Basic
				@Column(name = "zyjkwt_ybqt")
				public String getZyjkwt_ybqt() {
					return zyjkwt_ybqt;
				}
				public void setZyjkwt_ybqt(String zyjkwt_ybqt) {
					this.zyjkwt_ybqt = zyjkwt_ybqt;
				}
				@Basic
				@Column(name = "zyjkwt_sjxtjb")
				public String getZyjkwt_sjxtjb() {
					return zyjkwt_sjxtjb;
				}
				public void setZyjkwt_sjxtjb(String zyjkwt_sjxtjb) {
					this.zyjkwt_sjxtjb = zyjkwt_sjxtjb;
				}
				@Basic
				@Column(name = "zyjkwt_sjxtqt")
				public String getZyjkwt_sjxtqt() {
					return zyjkwt_sjxtqt;
				}
				public void setZyjkwt_sjxtqt(String zyjkwt_sjxtqt) {
					this.zyjkwt_sjxtqt = zyjkwt_sjxtqt;
				}
				@Basic
				@Column(name = "zyjkwt_qtxtjb")
				public String getZyjkwt_qtxtjb() {
					return zyjkwt_qtxtjb;
				}
				public void setZyjkwt_qtxtjb(String zyjkwt_qtxtjb) {
					this.zyjkwt_qtxtjb = zyjkwt_qtxtjb;
				}
				@Basic
				@Column(name = "zyjkwt_qtxtqt")
				public String getZyjkwt_qtxtqt() {
					return zyjkwt_qtxtqt;
				}
				public void setZyjkwt_qtxtqt(String zyjkwt_qtxtqt) {
					this.zyjkwt_qtxtqt = zyjkwt_qtxtqt;
				}
				@Basic
				@Column(name = "zytzbs_phz")
				public String getZytzbs_phz() {
					return zytzbs_phz;
				}

				public void setZytzbs_phz(String zytzbs_phz) {
					this.zytzbs_phz = zytzbs_phz;
				}
				@Basic
				@Column(name = "zytzbs_qxz")
				public String getZytzbs_qxz() {
					return zytzbs_qxz;
				}
				public void setZytzbs_qxz(String zytzbs_qxz) {
					this.zytzbs_qxz = zytzbs_qxz;
				}
				@Basic
				@Column(name = "zytzbs_yangxz")
				public String getZytzbs_yangxz() {
					return zytzbs_yangxz;
				}
				public void setZytzbs_yangxz(String zytzbs_yangxz) {
					this.zytzbs_yangxz = zytzbs_yangxz;
				}
				@Basic
				@Column(name = "zytzbs_yinxz")
				public String getZytzbs_yinxz() {
					return zytzbs_yinxz;
				}
				public void setZytzbs_yinxz(String zytzbs_yinxz) {
					this.zytzbs_yinxz = zytzbs_yinxz;
				}
				@Basic
				@Column(name = "zytzbs_tsz")
				public String getZytzbs_tsz() {
					return zytzbs_tsz;
				}
				public void setZytzbs_tsz(String zytzbs_tsz) {
					this.zytzbs_tsz = zytzbs_tsz;
				}
				@Basic
				@Column(name = "zytzbs_srz")
				public String getZytzbs_srz() {
					return zytzbs_srz;
				}
				public void setZytzbs_srz(String zytzbs_srz) {
					this.zytzbs_srz = zytzbs_srz;
				}
				@Basic
				@Column(name = "zytzbs_xyz")
				public String getZytzbs_xyz() {
					return zytzbs_xyz;
				}
				public void setZytzbs_xyz(String zytzbs_xyz) {
					this.zytzbs_xyz = zytzbs_xyz;
				}
				@Basic
				@Column(name = "zytzbs_qyz")
				public String getZytzbs_qyz() {
					return zytzbs_qyz;
				}
				public void setZytzbs_qyz(String zytzbs_qyz) {
					this.zytzbs_qyz = zytzbs_qyz;
				}
				@Basic
				@Column(name = "zytzbs_tbz")
				public String getZytzbs_tbz() {
					return zytzbs_tbz;
				}
				public void setZytzbs_tbz(String zytzbs_tbz) {
					this.zytzbs_tbz = zytzbs_tbz;
				}

 }