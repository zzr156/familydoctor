/*
 * CopyRight: StartTech 2005 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>Jktj_ybzk</code>.
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;


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
 * @hibernate.class table="Jktj_ybzk"
 * @author 劳动力99开发小组
 * @version 1.0
 */

 public class Jktj_ybzk
 {


					private  String jktj_ybzkid; //健康体检--一般状况ID(SEQ_JKTJ_YBZKID)
				private  String ybzk_tiwen; //健康体检--一般状况--体温
				private  String ybzk_ml; //健康体检--一般状况--脉率
				private  String ybzk_hxpl; //健康体检--一般状况-呼吸频率
				private  String ybzk_zszy; //健康体检--一般状况--左舒张压
				private  String ybzk_zssy; //健康体检--一般状况--左收缩压
				private  String ybzk_yszy; //健康体检--一般状况--右舒张压
				private  String ybzk_yssy; //健康体检--一般状况--右收缩压
				private  String ybzk_sg; //健康体检--一般状况--身高
				private  String ybzk_tz; //健康体检--一般状况--体重
				private  String ybzk_yw; //健康体检--一般状况--腰围
				private  String ybzk_tzzs; //健康体检--一般状况--体质指数
				private  String ybzk_tunwei; //健康体检--一般状况--臀围
				private  String ybzk_ytwbz; //健康体检--一般状况--腰臀围比值
				private  String ybzk_lnrzgn; //健康体检--一般状况--老年人认知功能
				private  String ybzk_lnzljc; //健康体检--一般状况--老年人智力状态检查
				private  String ybzk_lnqgzt; //健康体检--一般状况--老年人情感状态
				private  String ybzk_lnyypf; //健康体检--一般状况--老年人抑郁评分检查
				private  String yyid00; //机构ID
				private  String df_id; //居民健康档案ID
				private  String jktjcs; //健康体检--体检次数
				private  String edate; //检查日期
				private  String doctor; //责任医生
				private  String tjzzqk; //健康体检症状情况
				private  String zz_qt0000;//症状其他
				private String lnrjkpj;//老年人健康状态自我评估
				private String lnrshpj; //老年人生活自理能力自我评估
				private String gxrq00; //更新日期
				private String gxsj00; //更新时间
				private String gxygbh; //更新员工编号
				private String gxygxm; //更新员工姓名

				@Id
				@Column(name = "JKTJ_YBZKID")
				public String getJktj_ybzkid() {
					return jktj_ybzkid;
				}
				public void setJktj_ybzkid(String jktj_ybzkid) {
					this.jktj_ybzkid = jktj_ybzkid;
				}
				@Basic
				@Column(name = "YBZK_TIWEN")
				public String getYbzk_tiwen() {
					return ybzk_tiwen;
				}
				public void setYbzk_tiwen(String ybzk_tiwen) {
					this.ybzk_tiwen = ybzk_tiwen;
				}
				@Basic
				@Column(name = "YBZK_ML")
				public String getYbzk_ml() {
					return ybzk_ml;
				}
				public void setYbzk_ml(String ybzk_ml) {
					this.ybzk_ml = ybzk_ml;
				}
				@Basic
				@Column(name = "YBZK_HXPL")
				public String getYbzk_hxpl() {
					return ybzk_hxpl;
				}
				public void setYbzk_hxpl(String ybzk_hxpl) {
					this.ybzk_hxpl = ybzk_hxpl;
				}
				@Basic
				@Column(name = "YBZK_ZSZY")
				public String getYbzk_zszy() {
					return ybzk_zszy;
				}
				public void setYbzk_zszy(String ybzk_zszy) {
					this.ybzk_zszy = ybzk_zszy;
				}
				@Basic
				@Column(name = "YBZK_ZSSY")
				public String getYbzk_zssy() {
					return ybzk_zssy;
				}
				public void setYbzk_zssy(String ybzk_zssy) {
					this.ybzk_zssy = ybzk_zssy;
				}
				@Basic
				@Column(name = "YBZK_YSZY")
				public String getYbzk_yszy() {
					return ybzk_yszy;
				}
				public void setYbzk_yszy(String ybzk_yszy) {
					this.ybzk_yszy = ybzk_yszy;
				}
				@Basic
				@Column(name = "YBZK_YSSY")
				public String getYbzk_yssy() {
					return ybzk_yssy;
				}
				public void setYbzk_yssy(String ybzk_yssy) {
					this.ybzk_yssy = ybzk_yssy;
				}
				@Basic
				@Column(name = "YBZK_SG")
				public String getYbzk_sg() {
					return ybzk_sg;
				}
				public void setYbzk_sg(String ybzk_sg) {
					this.ybzk_sg = ybzk_sg;
				}
				@Basic
				@Column(name = "YBZK_TZ")
				public String getYbzk_tz() {
					return ybzk_tz;
				}
				public void setYbzk_tz(String ybzk_tz) {
					this.ybzk_tz = ybzk_tz;
				}
				@Basic
				@Column(name = "YBZK_YW")
				public String getYbzk_yw() {
					return ybzk_yw;
				}
				public void setYbzk_yw(String ybzk_yw) {
					this.ybzk_yw = ybzk_yw;
				}
				@Basic
				@Column(name = "YBZK_TZZS")
				public String getYbzk_tzzs() {
					return ybzk_tzzs;
				}
				public void setYbzk_tzzs(String ybzk_tzzs) {
					this.ybzk_tzzs = ybzk_tzzs;
				}
				@Basic
				@Column(name = "YBZK_TUNWEI")
				public String getYbzk_tunwei() {
					return ybzk_tunwei;
				}
				public void setYbzk_tunwei(String ybzk_tunwei) {
					this.ybzk_tunwei = ybzk_tunwei;
				}
				@Basic
				@Column(name = "YBZK_YTWBZ")
				public String getYbzk_ytwbz() {
					return ybzk_ytwbz;
				}
				public void setYbzk_ytwbz(String ybzk_ytwbz) {
					this.ybzk_ytwbz = ybzk_ytwbz;
				}
				@Basic
				@Column(name = "YBZK_LNRZGN")
				public String getYbzk_lnrzgn() {
					return ybzk_lnrzgn;
				}
				public void setYbzk_lnrzgn(String ybzk_lnrzgn) {
					this.ybzk_lnrzgn = ybzk_lnrzgn;
				}
				@Basic
				@Column(name = "YBZK_LNZLJC")
				public String getYbzk_lnzljc() {
					return ybzk_lnzljc;
				}
				public void setYbzk_lnzljc(String ybzk_lnzljc) {
					this.ybzk_lnzljc = ybzk_lnzljc;
				}
				@Basic
				@Column(name = "YBZK_LNQGZT")
				public String getYbzk_lnqgzt() {
					return ybzk_lnqgzt;
				}
				public void setYbzk_lnqgzt(String ybzk_lnqgzt) {
					this.ybzk_lnqgzt = ybzk_lnqgzt;
				}
				@Basic
				@Column(name = "YBZK_LNYYPF")
				public String getYbzk_lnyypf() {
					return ybzk_lnyypf;
				}
				public void setYbzk_lnyypf(String ybzk_lnyypf) {
					this.ybzk_lnyypf = ybzk_lnyypf;
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
				@Column(name = "EDATE")
				public String getEdate() {
					return edate;
				}
				public void setEdate(String edate) {
					this.edate = edate;
				}
				@Basic
				@Column(name = "DOCTOR")
				public String getDoctor() {
					return doctor;
				}
				public void setDoctor(String doctor) {
					this.doctor = doctor;
				}
				@Basic
				@Column(name = "TJZZQK")
				public String getTjzzqk() {
					return tjzzqk;
				}
				public void setTjzzqk(String tjzzqk) {
					this.tjzzqk = tjzzqk;
				}
				@Basic
				@Column(name = "ZZ_QT0000")
				public String getZz_qt0000() {
					return zz_qt0000;
				}
				public void setZz_qt0000(String zz_qt0000) {
					this.zz_qt0000 = zz_qt0000;
				}
				@Basic
				@Column(name = "LNRJKPJ")
				public String getLnrjkpj() {
					return lnrjkpj;
				}
				public void setLnrjkpj(String lnrjkpj) {
					this.lnrjkpj = lnrjkpj;
				}
				@Basic
				@Column(name = "LNRSHPJ")
				public String getLnrshpj() {
					return lnrshpj;
				}
				public void setLnrshpj(String lnrshpj) {
					this.lnrshpj = lnrshpj;
				}
				@Basic
				@Column(name = "GXRQ00")
				public String getGxrq00() {
					return gxrq00;
				}
				public void setGxrq00(String gxrq00) {
					this.gxrq00 = gxrq00;
				}
				@Basic
				@Column(name = "GXSJ00")
				public String getGxsj00() {
					return gxsj00;
				}
				public void setGxsj00(String gxsj00) {
					this.gxsj00 = gxsj00;
				}
				@Basic
				@Column(name = "GXYGBH")
				public String getGxygbh() {
					return gxygbh;
				}
				public void setGxygbh(String gxygbh) {
					this.gxygbh = gxygbh;
				}
				@Basic
				@Column(name = "GXYGXM")
				public String getGxygxm() {
					return gxygxm;
				}
				public void setGxygxm(String gxygxm) {
					this.gxygxm = gxygxm;
				}


 }