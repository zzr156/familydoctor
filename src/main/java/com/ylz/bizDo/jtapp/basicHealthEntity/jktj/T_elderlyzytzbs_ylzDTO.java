/*
 * CopyRight: StartTech 2010 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>T_elderlyzytzbs</code>.
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import com.fasterxml.jackson.annotation.JsonView;


/**
 * <p>
 * Title: T_elderlyzytzbs
 * </p>
 * <p>
 * Description:中医体质标识结果表
 * </p>
 * <p>
 * Copyright:Copyright(c)2010
 * </p>
 * <p>
 * Company:易联众技术股份有限公司
 * </p>
 * @hibernate.class table="T_elderlyzytzbs"
 * @author
 * @version 1.0
 */
 /*
 	<mapping resource="com/start/si/elderlyhealth/commons/entity/T_elderlyzytzbs.hbm.xml" />
 */

 public class T_elderlyzytzbs_ylzDTO
 {
	 /**
		 * 默认构造函数
		 */
		public T_elderlyzytzbs_ylzDTO() {
		}


		private String tzbs_id; // 中医体质辨识ID
		private String df_id; // 居民档案号
		@JsonView(IFindGroup.class)
		private String bsxxjg; // 辨识选项结果(共有33项，每项以";"拼接。且每项值为：1-没有(根本不/从来没有)，2-很少(有一点/偶尔)，3-有时(有些/少数时间)，4-经常(相当/多数时间)，5-总是(非常/每天))
		private String qxzscore; // 气虚质分数
		private String yx0score; // 阳虚质分数
		private String yxzscore; // 阴虚质分数
		private String tszscore; // 痰湿质分数
		private String srzscore; // 湿热质分数
		private String xyzscore; // 血瘀质分数
		private String qyzscore; // 气郁质分数
		private String tbzscore; // 特禀质分数
		private String phzscore; // 平和质分数
		private String tzbstj; // 体质辨识统计（有以下9项，通过";"拼接。气虚质：1-是，2-倾向是；阳虚质：3-是，4-倾向是；阴虚质：5-是，6-倾向是；痰湿质7-是，8-倾向是；湿热质：9-是，10-倾向是；血瘀质：11-是，12-倾向是；气郁质：13-是，14-倾向是；特禀质：15-是，16-倾向是；平和质：17-是，18-基本是；）

		private String bjzdtj; // 中医药保健指导统计
		private String zdid00; // 测试医生
		private java.sql.Date cjrq00; // 创建日期
		private java.sql.Date zhbjrq; // 最后编辑日期
		@JsonView(IFindGroup.class)
		private String cjrq001; // 创建日期

		private String zhbjrq1; // 最后编辑日期
		private String qxzqt0; // 气虚质其他
		private String yx0qt0; // 阳虚质其他
		private String yxzqt0; // 阴虚质其他
		private String tszqt0; // 痰湿质其他
		private String srzqt0; // 湿热质其他
		private String xyzqt0; // 血瘀质其他
		private String qyzqt0; // 气郁质其他
		private String tbzqt0; // 特禀质其他
		private String phzqt0; // 平和质其他
		@JsonView(IFindGroup.class)
		private String jktjcs; // 健康体检次数
		@JsonView(IFindGroup.class)
		private String yyid00; // 机构ID
		private String xgpg00; // 效果评估(1-完全掌握,2-熟练掌握,3-基本掌握,4-困难掌握)
		private String myddc0; // 满意度调查(1-很满意 ,2-比较满意,3-一般清楚,4-不满意)
		private String name1;
		/*private String score;*/
		//@JsonView({IFindJktj_MxGroup.class})
		private String tzbsmc;//体质辨识名称

		private String score; // 分数
		private String qt0000; // 其他


		public String getCjrq001() {
			return cjrq001;
		}

		public void setCjrq001(String cjrq001) {
			this.cjrq001 = cjrq001;
		}

		public String getZhbjrq1() {
			return zhbjrq1;
		}

		public void setZhbjrq1(String zhbjrq1) {
			this.zhbjrq1 = zhbjrq1;
		}

		/**
		 * 中医体质辨识ID
		 *
		 * @return tzbs_id
		 */
		@JsonView(IFindGroup.class)
		public String getTzbs_id() {
			return this.tzbs_id;
		}

		/**
		 * 居民档案号
		 *
		 * @return df_id
		 */
		public String getDf_id() {
			return this.df_id;
		}

		/**
		 * 辨识选项结果
		 *
		 * @return bsxxjg
		 */
		public String getBsxxjg() {
			return this.bsxxjg;
		}

		/**
		 * 气虚质分数
		 *
		 * @return qxzscore
		 */
		public String getQxzscore() {
			return this.qxzscore;
		}

		/**
		 * 阳虚质
		 *
		 * @return yx0score
		 */
		public String getYx0score() {
			return this.yx0score;
		}

		/**
		 * 阴虚质
		 *
		 * @return yxzscore
		 */
		public String getYxzscore() {
			return this.yxzscore;
		}

		/**
		 * 痰湿质
		 *
		 * @return tszscore
		 */
		public String getTszscore() {
			return this.tszscore;
		}

		/**
		 * 湿热质
		 *
		 * @return srzscore
		 */
		public String getSrzscore() {
			return this.srzscore;
		}

		/**
		 * 血瘀质
		 *
		 * @return xyzscore
		 */
		public String getXyzscore() {
			return this.xyzscore;
		}

		/**
		 * 气郁质
		 *
		 * @return qyzscore
		 */
		public String getQyzscore() {
			return this.qyzscore;
		}

		/**
		 * 特禀质
		 *
		 * @return tbzscore
		 */
		public String getTbzscore() {
			return this.tbzscore;
		}

		/**
		 * 平和质
		 *
		 * @return phzscore
		 */
		public String getPhzscore() {
			return this.phzscore;
		}

		/**
		 * 体质辨识统计
		 *
		 * @return tzbstj
		 */
		public String getTzbstj() {
			return this.tzbstj;
		}

		/**
		 * 中医药保健指导统计
		 *
		 * @return bjzdtj
		 */
		public String getBjzdtj() {
			return this.bjzdtj;
		}

		/**
		 * 测试医生
		 *
		 * @return zdid00
		 */
		public String getZdid00() {
			return this.zdid00;
		}



		/**
		 * 气虚质其他
		 *
		 * @return qxzqt0
		 */
		public String getQxzqt0() {
			return this.qxzqt0;
		}

		/**
		 * 阳虚质其他
		 *
		 * @return yx0qt0
		 */
		public String getYx0qt0() {
			return this.yx0qt0;
		}

		/**
		 * 阴虚质其他
		 *
		 * @return yxzqt0
		 */
		public String getYxzqt0() {
			return this.yxzqt0;
		}

		/**
		 * 痰湿质其他
		 *
		 * @return tszqt0
		 */
		public String getTszqt0() {
			return this.tszqt0;
		}

		/**
		 * 湿热质其他
		 *
		 * @return srzqt0
		 */
		public String getSrzqt0() {
			return this.srzqt0;
		}

		/**
		 * 血瘀质其他
		 *
		 * @return xyzqt0
		 */
		public String getXyzqt0() {
			return this.xyzqt0;
		}

		/**
		 * 气郁质其他
		 *
		 * @return qyzqt0
		 */
		public String getQyzqt0() {
			return this.qyzqt0;
		}

		/**
		 * 特禀质其他
		 *
		 * @return tbzqt0
		 */
		public String getTbzqt0() {
			return this.tbzqt0;
		}

		/**
		 * 平和质其他
		 *
		 * @return phzqt0
		 */
		public String getPhzqt0() {
			return this.phzqt0;
		}

		/**
		 * 健康体检次数
		 *
		 * @return jktjcs
		 */
		public String getJktjcs() {
			return this.jktjcs;
		}

		/**
		 * 机构ID
		 *
		 * @return yyid00
		 */
		public String getYyid00() {
			return this.yyid00;
		}

		/**
		 * 效果评估
		 *
		 * @return xgpg00
		 */
		public String getXgpg00() {
			return this.xgpg00;
		}

		/**
		 * 满意度调查
		 *
		 * @return myddc0
		 */
		public String getMyddc0() {
			return this.myddc0;
		}

		/**
		 * @param tzbs_id
		 *            中医体质辨识ID
		 */
		public void setTzbs_id(String tzbs_id) {
			this.tzbs_id = tzbs_id;
		}

		/**
		 * @param df_id
		 *            居民档案号
		 */
		public void setDf_id(String df_id) {
			this.df_id = df_id;
		}

		/**
		 * @param bsxxjg
		 *            辨识选项结果
		 */
		public void setBsxxjg(String bsxxjg) {
			this.bsxxjg = bsxxjg;
		}

		/**
		 * @param qxzscore
		 *            气虚质分数
		 */
		public void setQxzscore(String qxzscore) {
			this.qxzscore = qxzscore;
		}

		/**
		 * @param yx0score
		 *            阳虚质
		 */
		public void setYx0score(String yx0score) {
			this.yx0score = yx0score;
		}

		/**
		 * @param yxzscore
		 *            阴虚质
		 */
		public void setYxzscore(String yxzscore) {
			this.yxzscore = yxzscore;
		}

		/**
		 * @param tszscore
		 *            痰湿质
		 */
		public void setTszscore(String tszscore) {
			this.tszscore = tszscore;
		}

		/**
		 * @param srzscore
		 *            湿热质
		 */
		public void setSrzscore(String srzscore) {
			this.srzscore = srzscore;
		}

		/**
		 * @param xyzscore
		 *            血瘀质
		 */
		public void setXyzscore(String xyzscore) {
			this.xyzscore = xyzscore;
		}

		/**
		 * @param qyzscore
		 *            气郁质
		 */
		public void setQyzscore(String qyzscore) {
			this.qyzscore = qyzscore;
		}

		/**
		 * @param tbzscore
		 *            特禀质
		 */
		public void setTbzscore(String tbzscore) {
			this.tbzscore = tbzscore;
		}

		/**
		 * @param phzscore
		 *            平和质
		 */
		public void setPhzscore(String phzscore) {
			this.phzscore = phzscore;
		}

		/**
		 * @param tzbstj
		 *            体质辨识统计
		 */
		public void setTzbstj(String tzbstj) {
			this.tzbstj = tzbstj;
		}

		/**
		 * @param bjzdtj
		 *            中医药保健指导统计
		 */
		public void setBjzdtj(String bjzdtj) {
			this.bjzdtj = bjzdtj;
		}

		/**
		 * @param zdid00
		 *            测试医生
		 */
		public void setZdid00(String zdid00) {
			this.zdid00 = zdid00;
		}

		/**
		 * @param qxzqt0
		 *            气虚质其他
		 */
		public void setQxzqt0(String qxzqt0) {
			this.qxzqt0 = qxzqt0;
		}

		public java.sql.Date getCjrq00() {
			return cjrq00;
		}

		public void setCjrq00(java.sql.Date cjrq00) {
			this.cjrq00 = cjrq00;
		}

		public java.sql.Date getZhbjrq() {
			return zhbjrq;
		}

		public void setZhbjrq(java.sql.Date zhbjrq) {
			this.zhbjrq = zhbjrq;
		}

		/**
		 * @param yx0qt0
		 *            阳虚质其他
		 */
		public void setYx0qt0(String yx0qt0) {
			this.yx0qt0 = yx0qt0;
		}

		/**
		 * @param yxzqt0
		 *            阴虚质其他
		 */
		public void setYxzqt0(String yxzqt0) {
			this.yxzqt0 = yxzqt0;
		}

		/**
		 * @param tszqt0
		 *            痰湿质其他
		 */
		public void setTszqt0(String tszqt0) {
			this.tszqt0 = tszqt0;
		}

		/**
		 * @param srzqt0
		 *            湿热质其他
		 */
		public void setSrzqt0(String srzqt0) {
			this.srzqt0 = srzqt0;
		}

		/**
		 * @param xyzqt0
		 *            血瘀质其他
		 */
		public void setXyzqt0(String xyzqt0) {
			this.xyzqt0 = xyzqt0;
		}

		/**
		 * @param qyzqt0
		 *            气郁质其他
		 */
		public void setQyzqt0(String qyzqt0) {
			this.qyzqt0 = qyzqt0;
		}

		/**
		 * @param tbzqt0
		 *            特禀质其他
		 */
		public void setTbzqt0(String tbzqt0) {
			this.tbzqt0 = tbzqt0;
		}

		/**
		 * @param phzqt0
		 *            平和质其他
		 */
		public void setPhzqt0(String phzqt0) {
			this.phzqt0 = phzqt0;
		}

		/**
		 * @param jktjcs
		 *            健康体检次数
		 */
		public void setJktjcs(String jktjcs) {
			this.jktjcs = jktjcs;
		}

		/**
		 * @param yyid00
		 *            机构ID
		 */
		public void setYyid00(String yyid00) {
			this.yyid00 = yyid00;
		}

		/**
		 * @param xgpg00
		 *            效果评估
		 */
		public void setXgpg00(String xgpg00) {
			this.xgpg00 = xgpg00;
		}

		/**
		 * @param myddc0
		 *            满意度调查
		 */
		public void setMyddc0(String myddc0) {
			this.myddc0 = myddc0;
		}

		/**
		 * 分数
		 * @return
		 */
		public String getScore() {
			return score;
		}

		/**
		 * 分数
		 * @param score
		 */
		public void setScore(String score) {
			this.score = score;
		}

		/**
		 * 其他
		 * @return
		 */
		public String getQt0000() {
			return qt0000;
		}

		/**
		 * 其他
		 * @param qt0000
		 */
		public void setQt0000(String qt0000) {
			this.qt0000 = qt0000;
		}

		/**
		 * 体质辨识名称
		 * @return
		 */
		public String getName1() {
			return name1;
		}

		/**
		 *  体质辨识名称
		 * @param name1
		 */
		public void setName1(String name1) {
			this.name1 = name1;
		}

		/**
		 * 体质辨识名称串
		 * @return
		 */
		public String getTzbsmc() {
			return tzbsmc;
		}

		/**
		 * 体质辨识名称串
		 * @param tzbsmc
		 */
		public void setTzbsmc(String tzbsmc) {
			this.tzbsmc = tzbsmc;
		}

		public static interface IFindGroup {}
 }
