/*
 * CopyRight: StartTech 2005 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>Jktj_jtbcs</code>.
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import java.io.Serializable;

/**
 * <p>
 * Title: Jktj_jtbcs
 * </p>
 * <p>
 * Description:健康体检之家庭病床史
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 * @hibernate.class table="Jktj_jtbcs"
 * @author 劳动力99开发小组
 * @version 1.0
 */

 public class Jktj_jtbcs_ylkDTO implements Serializable
 {


	private String jtbcsid; //家庭病床史编号
				private  String jcrq; //建床日期
				private  String ccrq; //撤床日期
				private  String bjyy; //病床原因
				private  String yljgmc; //医院名称
				private  String bcbah; //病床病案号
				private  String df_id; //居民编号
				private  String f_id; //家庭编号
				private  String yyid00; //机构名称
				private  String jktjcs; //健康体检次数

					/**
			* 家庭病床史编号
			* @hibernate.property column="jtbcsid"
			* @return jtbcsid
			*/
			public  String getJtbcsid() {
			return this.jtbcsid;
			}
				/**
			* 建床日期
			* @hibernate.property column="jcrq"
			* @return jcrq
			*/
			public  String getJcrq() {
			return this.jcrq;
			}
				/**
			* 撤床日期
			* @hibernate.property column="ccrq"
			* @return ccrq
			*/
			public  String getCcrq() {
			return this.ccrq;
			}
				/**
			* 病床原因
			* @hibernate.property column="bjyy"
			* @return bjyy
			*/
			public  String getBjyy() {
			return this.bjyy;
			}
				/**
			* 医院名称
			* @hibernate.property column="yljgmc"
			* @return yljgmc
			*/
			public  String getYljgmc() {
			return this.yljgmc;
			}
				/**
			* 病床病案号
			* @hibernate.property column="bcbah"
			* @return bcbah
			*/
			public  String getBcbah() {
			return this.bcbah;
			}
				/**
			* 居民编号
			* @hibernate.property column="df_id"
			* @return df_id
			*/
			public  String getDf_id() {
			return this.df_id;
			}
				/**
			* 家庭编号
			* @hibernate.property column="f_id"
			* @return f_id
			*/
			public  String getF_id() {
			return this.f_id;
			}
				/**
			* 机构名称
			* @hibernate.property column="yyid00"
			* @return yyid00
			*/
			public  String getYyid00() {
			return this.yyid00;
			}
				/**
			* 健康体检次数
			* @hibernate.property column="jktjcs"
			* @return jktjcs
			*/
			public  String getJktjcs() {
			return this.jktjcs;
			}

					/**
			*	@param jtbcsid 家庭病床史编号
			*/
			public  void setJtbcsid(String jtbcsid)
			{
				this.jtbcsid=jtbcsid;
			}
				/**
			*	@param jcrq 建床日期
			*/
			public  void setJcrq(String jcrq)
			{
				this.jcrq=jcrq;
			}
				/**
			*	@param ccrq 撤床日期
			*/
			public  void setCcrq(String ccrq)
			{
				this.ccrq=ccrq;
			}
				/**
			*	@param bjyy 病床原因
			*/
			public  void setBjyy(String bjyy)
			{
				this.bjyy=bjyy;
			}
				/**
			*	@param yljgmc 医院名称
			*/
			public  void setYljgmc(String yljgmc)
			{
				this.yljgmc=yljgmc;
			}
				/**
			*	@param bcbah 病床病案号
			*/
			public  void setBcbah(String bcbah)
			{
				this.bcbah=bcbah;
			}
				/**
			*	@param df_id 居民编号
			*/
			public  void setDf_id(String df_id)
			{
				this.df_id=df_id;
			}
				/**
			*	@param f_id 家庭编号
			*/
			public  void setF_id(String f_id)
			{
				this.f_id=f_id;
			}
				/**
			*	@param yyid00 机构名称
			*/
			public  void setYyid00(String yyid00)
			{
				this.yyid00=yyid00;
			}
				/**
			*	@param jktjcs 健康体检次数
			*/
			public  void setJktjcs(String jktjcs)
			{
				this.jktjcs=jktjcs;
			}

 }