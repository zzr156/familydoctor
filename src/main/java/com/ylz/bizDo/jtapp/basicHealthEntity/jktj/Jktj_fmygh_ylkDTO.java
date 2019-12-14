/*
 * CopyRight: StartTech 2005 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>Jktj_fmygh</code>.
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import java.io.Serializable;

/**
 * <p>
 * Title: Jktj_fmygh
 * </p>
 * <p>
 * Description:健康体检之非免疫规划预防接种史
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 * @hibernate.class table="Jktj_fmygh"
 * @author 劳动力99开发小组
 * @version 1.0
 */

 public class Jktj_fmygh_ylkDTO implements Serializable
 {


					private  String fmyghid; //非免疫规划预防接种编号
				private  String ymmc; //疫苗名称
				private  String jzrq; //接种日期
				private  String jzyy; //接种医院
				private  String yyid00; //机构ID
				private  String df_id; //居民编号
				private  String jktjcs; //健康体检次数

					/**
			* 非免疫规划预防接种编号
			* @hibernate.property column="fmyghid"
			* @return fmyghid
			*/
			public  String getFmyghid() {
			return this.fmyghid;
			}
				/**
			* 疫苗名称
			* @hibernate.property column="ymmc"
			* @return ymmc
			*/
			public  String getYmmc() {
			return this.ymmc;
			}
				/**
			* 接种日期
			* @hibernate.property column="jzrq"
			* @return jzrq
			*/
			public  String getJzrq() {
			return this.jzrq;
			}
				/**
			* 接种医院
			* @hibernate.property column="jzyy"
			* @return jzyy
			*/
			public  String getJzyy() {
			return this.jzyy;
			}
				/**
			* 机构ID
			* @hibernate.property column="yyid00"
			* @return yyid00
			*/
			public  String getYyid00() {
			return this.yyid00;
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
			* 健康体检次数
			* @hibernate.property column="jktjcs"
			* @return jktjcs
			*/
			public  String getJktjcs() {
			return this.jktjcs;
			}

					/**
			*	@param fmyghid 非免疫规划预防接种编号
			*/
			public  void setFmyghid(String fmyghid)
			{
				this.fmyghid=fmyghid;
			}
				/**
			*	@param ymmc 疫苗名称
			*/
			public  void setYmmc(String ymmc)
			{
				this.ymmc=ymmc;
			}
				/**
			*	@param jzrq 接种日期
			*/
			public  void setJzrq(String jzrq)
			{
				this.jzrq=jzrq;
			}
				/**
			*	@param jzyy 接种医院
			*/
			public  void setJzyy(String jzyy)
			{
				this.jzyy=jzyy;
			}
				/**
			*	@param yyid00 机构ID
			*/
			public  void setYyid00(String yyid00)
			{
				this.yyid00=yyid00;
			}
				/**
			*	@param df_id 居民编号
			*/
			public  void setDf_id(String df_id)
			{
				this.df_id=df_id;
			}
				/**
			*	@param jktjcs 健康体检次数
			*/
			public  void setJktjcs(String jktjcs)
			{
				this.jktjcs=jktjcs;
			}


 }