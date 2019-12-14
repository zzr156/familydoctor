package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class jktj_zqgnDTO {
	public jktj_zqgnDTO(){}

		private  String jktj_zqgnid; //健康体检--脏器功能ID
		private  String yyid00; //机构ID
		private  String df_id; //居民健康档案ID
		private  String jktjcs; //健康体检次数
		private  String jktj_zqgn_kqkc; //健康体检--脏器功能--口腔-口唇(0-请选择..,1-红润,2-苍白,3-发干,4-皲裂,5-疱疹)
		private  String jktj_zqgn_kqcl; //健康体检--脏器功能--口腔-齿列(1-正常,2-缺齿,3-龋齿,4-义齿(假牙))
		private  String jktj_zqgn_kqyb; //健康体检--脏器功能--口腔-咽部(0-请选择..,1-无充血,2-充血,3-淋巴滤泡增生)
		private  String jktj_zqgn_slzy; //健康体检--脏器功能--视力左眼
		private  String jktj_zqgn_slyy; //健康体检--脏器功能--视力右眼
		private  String jktj_zqgn_jzslzy; //健康体检--脏器功能--矫正视力左眼
		private  String jktj_zqgn_jzslyy; //健康体检--脏器功能--矫正视力右眼
		private  String jktj_zqgn_tl; //健康体检--脏器功能--听力(0-请选择..,1-听见,2-听不清或无法听见)
		private  String jktj_zqgn_ydgn; //健康体检--脏器功能--运动功能(0-请选择..,1-可顺利完成,2-无法独立完成其中任何一个动作)
		private  String jktj_zqgn_quechi; //健康体检--脏器功能--口腔--齿列（缺齿情况，上下左右分别用分号拼接起来）
		private  String jktj_zqgn_quchi0; //健康体检--脏器功能--口腔--齿列（龋齿情况，上下左右分别用分号拼接起来）
		private  String jktj_zqgn_yichi0; //健康体检--脏器功能--口腔--齿列（义齿情况，上下左右分别用分号拼接起来）

			/**
		* 健康体检--脏器功能ID
		* @hibernate.property column="jktj_zqgnid"
		* @return jktj_zqgnid
		*/
		public  String getJktj_zqgnid() {
		return this.jktj_zqgnid;
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
		* 居民健康档案ID
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
		* 健康体检--脏器功能--口腔-口唇
		* @hibernate.property column="jktj_zqgn_kqkc"
		* @return jktj_zqgn_kqkc
		*/
		public  String getJktj_zqgn_kqkc() {
		return this.jktj_zqgn_kqkc;
		}
		/**
		* 健康体检--脏器功能--口腔-齿列（1--正常，2--缺齿，3--龋齿，4--义齿）
		* @hibernate.property column="jktj_zqgn_kqcl"
		* @return jktj_zqgn_kqcl
		*/
		public  String getJktj_zqgn_kqcl() {
		return this.jktj_zqgn_kqcl;
		}
		/**
		* 健康体检--脏器功能--口腔-咽部
		* @hibernate.property column="jktj_zqgn_kqyb"
		* @return jktj_zqgn_kqyb
		*/
		public  String getJktj_zqgn_kqyb() {
		return this.jktj_zqgn_kqyb;
		}
		/**
		* 健康体检--脏器功能--视力左眼
		* @hibernate.property column="jktj_zqgn_slzy"
		* @return jktj_zqgn_slzy
		*/
		public  String getJktj_zqgn_slzy() {
		return this.jktj_zqgn_slzy;
		}
		/**
		* 健康体检--脏器功能--视力右眼
		* @hibernate.property column="jktj_zqgn_slyy"
		* @return jktj_zqgn_slyy
		*/
		public  String getJktj_zqgn_slyy() {
		return this.jktj_zqgn_slyy;
		}
		/**
		* 健康体检--脏器功能--矫正视力左眼
		* @hibernate.property column="jktj_zqgn_jzslzy"
		* @return jktj_zqgn_jzslzy
		*/
		public  String getJktj_zqgn_jzslzy() {
		return this.jktj_zqgn_jzslzy;
		}
		/**
		* 健康体检--脏器功能--矫正视力右眼
		* @hibernate.property column="jktj_zqgn_jzslyy"
		* @return jktj_zqgn_jzslyy
		*/
		public  String getJktj_zqgn_jzslyy() {
		return this.jktj_zqgn_jzslyy;
		}
		/**
		* 健康体检--脏器功能--听力
		* @hibernate.property column="jktj_zqgn_tl"
		* @return jktj_zqgn_tl
		*/
		public  String getJktj_zqgn_tl() {
		return this.jktj_zqgn_tl;
		}
		/**
		* 健康体检--脏器功能--运动功能
		* @hibernate.property column="jktj_zqgn_ydgn"
		* @return jktj_zqgn_ydgn
		*/
		public  String getJktj_zqgn_ydgn() {
		return this.jktj_zqgn_ydgn;
		}
		/**
		* 健康体检--脏器功能--口腔--齿列（缺齿情况，上下左右分别用分号拼接起来）
		* @hibernate.property column="jktj_zqgn_quechi"
		* @return jktj_zqgn_quechi
		*/
		public  String getJktj_zqgn_quechi() {
		return this.jktj_zqgn_quechi;
		}
		/**
		* 健康体检--脏器功能--口腔--齿列（龋齿情况，上下左右分别用分号拼接起来）
		* @hibernate.property column="jktj_zqgn_quchi0"
		* @return jktj_zqgn_quchi0
		*/
		public  String getJktj_zqgn_quchi0() {
		return this.jktj_zqgn_quchi0;
		}
		/**
		* 健康体检--脏器功能--口腔--齿列（义齿情况，上下左右分别用分号拼接起来）
		* @hibernate.property column="jktj_zqgn_yichi0"
		* @return jktj_zqgn_yichi0
		*/
		public  String getJktj_zqgn_yichi0() {
		return this.jktj_zqgn_yichi0;
		}

			/**
		*	@param jktj_zqgnid 健康体检--脏器功能ID
		*/
		public  void setJktj_zqgnid(String jktj_zqgnid)
		{
		this.jktj_zqgnid=jktj_zqgnid;
		}
		/**
		*	@param yyid00 机构ID
		*/
		public  void setYyid00(String yyid00)
		{
		this.yyid00=yyid00;
		}
		/**
		*	@param df_id 居民健康档案ID
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
		/**
		*	@param jktj_zqgn_kqkc 健康体检--脏器功能--口腔-口唇
		*/
		public  void setJktj_zqgn_kqkc(String jktj_zqgn_kqkc)
		{
		this.jktj_zqgn_kqkc=jktj_zqgn_kqkc;
		}
		/**
		*	@param jktj_zqgn_kqcl 健康体检--脏器功能--口腔-齿列（1--正常，2--缺齿，3--龋齿，4--义齿）
		*/
		public  void setJktj_zqgn_kqcl(String jktj_zqgn_kqcl)
		{
		this.jktj_zqgn_kqcl=jktj_zqgn_kqcl;
		}
		/**
		*	@param jktj_zqgn_kqyb 健康体检--脏器功能--口腔-咽部
		*/
		public  void setJktj_zqgn_kqyb(String jktj_zqgn_kqyb)
		{
		this.jktj_zqgn_kqyb=jktj_zqgn_kqyb;
		}
		/**
		*	@param jktj_zqgn_slzy 健康体检--脏器功能--视力左眼
		*/
		public  void setJktj_zqgn_slzy(String jktj_zqgn_slzy)
		{
		this.jktj_zqgn_slzy=jktj_zqgn_slzy;
		}
		/**
		*	@param jktj_zqgn_slyy 健康体检--脏器功能--视力右眼
		*/
		public  void setJktj_zqgn_slyy(String jktj_zqgn_slyy)
		{
		this.jktj_zqgn_slyy=jktj_zqgn_slyy;
		}
		/**
		*	@param jktj_zqgn_jzslzy 健康体检--脏器功能--矫正视力左眼
		*/
		public  void setJktj_zqgn_jzslzy(String jktj_zqgn_jzslzy)
		{
		this.jktj_zqgn_jzslzy=jktj_zqgn_jzslzy;
		}
		/**
		*	@param jktj_zqgn_jzslyy 健康体检--脏器功能--矫正视力右眼
		*/
		public  void setJktj_zqgn_jzslyy(String jktj_zqgn_jzslyy)
		{
		this.jktj_zqgn_jzslyy=jktj_zqgn_jzslyy;
		}
		/**
		*	@param jktj_zqgn_tl 健康体检--脏器功能--听力
		*/
		public  void setJktj_zqgn_tl(String jktj_zqgn_tl)
		{
		this.jktj_zqgn_tl=jktj_zqgn_tl;
		}
		/**
		*	@param jktj_zqgn_ydgn 健康体检--脏器功能--运动功能
		*/
		public  void setJktj_zqgn_ydgn(String jktj_zqgn_ydgn)
		{
		this.jktj_zqgn_ydgn=jktj_zqgn_ydgn;
		}
		/**
		*	@param jktj_zqgn_quechi 健康体检--脏器功能--口腔--齿列（缺齿情况，上下左右分别用分号拼接起来）
		*/
		public  void setJktj_zqgn_quechi(String jktj_zqgn_quechi)
		{
		this.jktj_zqgn_quechi=jktj_zqgn_quechi;
		}
		/**
		*	@param jktj_zqgn_quchi0 健康体检--脏器功能--口腔--齿列（龋齿情况，上下左右分别用分号拼接起来）
		*/
		public  void setJktj_zqgn_quchi0(String jktj_zqgn_quchi0)
		{
		this.jktj_zqgn_quchi0=jktj_zqgn_quchi0;
		}
		/**
		*	@param jktj_zqgn_yichi0 健康体检--脏器功能--口腔--齿列（义齿情况，上下左右分别用分号拼接起来）
		*/
		public  void setJktj_zqgn_yichi0(String jktj_zqgn_yichi0)
		{
		this.jktj_zqgn_yichi0=jktj_zqgn_yichi0;
		}

		public String toString()
		{
		    		return (new ToStringBuilder(this))
		        		.append("jktj_zqgnid", getJktj_zqgnid())
		        		.toString();
			}

		public int hashCode()
		{
		    	return (new HashCodeBuilder(17, 37))
			        		.append(getJktj_zqgnid())
		        	.toHashCode();
			}

		public boolean equals(Object o) {
		if ( !(o instanceof jktj_zqgnDTO) ) {
		return false;
		}
		if(o==this) {
            return true;
        }
		jktj_zqgnDTO me=(jktj_zqgnDTO)o;
		return new EqualsBuilder()
		        		.append(getJktj_zqgnid(),me.getJktj_zqgnid())
		    	.isEquals();
		}

}
