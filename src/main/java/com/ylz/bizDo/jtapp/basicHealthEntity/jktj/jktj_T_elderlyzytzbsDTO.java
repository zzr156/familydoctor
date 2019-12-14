package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class jktj_T_elderlyzytzbsDTO {
	public String getQxzqt0() {
		return qxzqt0;
	}
	public void setQxzqt0(String qxzqt0) {
		this.qxzqt0 = qxzqt0;
	}
	public String getYx0qt0() {
		return yx0qt0;
	}
	public void setYx0qt0(String yx0qt0) {
		this.yx0qt0 = yx0qt0;
	}
	public String getYxzqt0() {
		return yxzqt0;
	}
	public void setYxzqt0(String yxzqt0) {
		this.yxzqt0 = yxzqt0;
	}
	public String getTszqt0() {
		return tszqt0;
	}
	public void setTszqt0(String tszqt0) {
		this.tszqt0 = tszqt0;
	}
	public String getSrzqt0() {
		return srzqt0;
	}
	public void setSrzqt0(String srzqt0) {
		this.srzqt0 = srzqt0;
	}
	public String getXyzqt0() {
		return xyzqt0;
	}
	public void setXyzqt0(String xyzqt0) {
		this.xyzqt0 = xyzqt0;
	}
	public String getQyzqt0() {
		return qyzqt0;
	}
	public void setQyzqt0(String qyzqt0) {
		this.qyzqt0 = qyzqt0;
	}
	public String getTbzqt0() {
		return tbzqt0;
	}
	public void setTbzqt0(String tbzqt0) {
		this.tbzqt0 = tbzqt0;
	}
	public String getPhzqt0() {
		return phzqt0;
	}
	public void setPhzqt0(String phzqt0) {
		this.phzqt0 = phzqt0;
	}
		/**
 	 * 默认构造函数
 	 */
 	public jktj_T_elderlyzytzbsDTO(){}

			 	private  String tzbs_id; //中医体质辨识ID
				private  String df_id; //居民档案号
				private  String bsxxjg; //辨识选项结果
				private  String qxzscore; //气虚质分数
				private  String yx0score; //阳虚质
				private  String yxzscore; //阴虚质
				private  String tszscore; //痰湿质
				private  String srzscore; //湿热质
				private  String xyzscore; //血瘀质
				private  String qyzscore; //气郁质
				private  String tbzscore; //特禀质
				private  String phzscore; //平和质
				private  String tzbstj; //体质辨识统计
				private  String bjzdtj; //中医药保健指导统计
				private  String zdid00;
				private java.sql.Timestamp cjrq00; // 创建日期
				private java.sql.Timestamp zhbjrq; // 最后编辑日期
				private  String qxzqt0;
				private  String yx0qt0;
				private  String yxzqt0;
				private  String tszqt0;
				private  String srzqt0;
				private  String xyzqt0;
				private  String qyzqt0;
				private  String tbzqt0;
				private  String phzqt0;
				private  String jktjcs; //健康体检次数
				private  String yyid00; //机构ID
				private  String xgpg00; //效果评估
				private  String myddc0; //满意度调查
					/**
			 * 中医体质辨识ID
			 * @hibernate.property column="tzbs_id"
			 * @return tzbs_id
			 */
			public  String getTzbs_id() {
			return this.tzbs_id;
			}
				/**
			 * 居民档案号
			 * @hibernate.property column="df_id"
			 * @return df_id
			 */
			public  String getDf_id() {
			return this.df_id;
			}
				/**
			 * 辨识选项结果
			 * @hibernate.property column="bsxxjg"
			 * @return bsxxjg
			 */
			public  String getBsxxjg() {
			return this.bsxxjg;
			}
				/**
			 * 气虚质分数
			 * @hibernate.property column="qxzscore"
			 * @return qxzscore
			 */
			public  String getQxzscore() {
			return this.qxzscore;
			}
				/**
			 * 阳虚质
			 * @hibernate.property column="yx0score"
			 * @return yx0score
			 */
			public  String getYx0score() {
			return this.yx0score;
			}
				/**
			 * 阴虚质
			 * @hibernate.property column="yxzscore"
			 * @return yxzscore
			 */
			public  String getYxzscore() {
			return this.yxzscore;
			}
				/**
			 * 痰湿质
			 * @hibernate.property column="tszscore"
			 * @return tszscore
			 */
			public  String getTszscore() {
			return this.tszscore;
			}
				/**
			 * 湿热质
			 * @hibernate.property column="srzscore"
			 * @return srzscore
			 */
			public  String getSrzscore() {
			return this.srzscore;
			}
				/**
			 * 血瘀质
			 * @hibernate.property column="xyzscore"
			 * @return xyzscore
			 */
			public  String getXyzscore() {
			return this.xyzscore;
			}
				/**
			 * 气郁质
			 * @hibernate.property column="qyzscore"
			 * @return qyzscore
			 */
			public  String getQyzscore() {
			return this.qyzscore;
			}
				/**
			 * 特禀质
			 * @hibernate.property column="tbzscore"
			 * @return tbzscore
			 */
			public  String getTbzscore() {
			return this.tbzscore;
			}
				/**
			 * 平和质
			 * @hibernate.property column="phzscore"
			 * @return phzscore
			 */
			public  String getPhzscore() {
			return this.phzscore;
			}
				/**
			 * 体质辨识统计
			 * @hibernate.property column="tzbstj"
			 * @return tzbstj
			 */
			public  String getTzbstj() {
			return this.tzbstj;
			}
				/**
			 * 中医药保健指导统计
			 * @hibernate.property column="bjzdtj"
			 * @return bjzdtj
			 */
			public  String getBjzdtj() {
			return this.bjzdtj;
			}

					/**
		 *	@param tzbs_id 中医体质辨识ID
			 */
			public  void setTzbs_id(String tzbs_id)
			{
				this.tzbs_id=tzbs_id;
			}
				/**
		 *	@param df_id 居民档案号
			 */
			public  void setDf_id(String df_id)
			{
				this.df_id=df_id;
			}
				/**
		 *	@param bsxxjg 辨识选项结果
			 */
			public  void setBsxxjg(String bsxxjg)
			{
				this.bsxxjg=bsxxjg;
			}
				/**
		 *	@param qxzscore 气虚质分数
			 */
			public  void setQxzscore(String qxzscore)
			{
				this.qxzscore=qxzscore;
			}
				/**
		 *	@param yx0score 阳虚质
			 */
			public  void setYx0score(String yx0score)
			{
				this.yx0score=yx0score;
			}
				/**
		 *	@param yxzscore 阴虚质
			 */
			public  void setYxzscore(String yxzscore)
			{
				this.yxzscore=yxzscore;
			}
				/**
		 *	@param tszscore 痰湿质
			 */
			public  void setTszscore(String tszscore)
			{
				this.tszscore=tszscore;
			}
				/**
		 *	@param srzscore 湿热质
			 */
			public  void setSrzscore(String srzscore)
			{
				this.srzscore=srzscore;
			}
				/**
		 *	@param xyzscore 血瘀质
			 */
			public  void setXyzscore(String xyzscore)
			{
				this.xyzscore=xyzscore;
			}
				/**
		 *	@param qyzscore 气郁质
			 */
			public  void setQyzscore(String qyzscore)
			{
				this.qyzscore=qyzscore;
			}
				/**
		 *	@param tbzscore 特禀质
			 */
			public  void setTbzscore(String tbzscore)
			{
				this.tbzscore=tbzscore;
			}
				/**
		 *	@param phzscore 平和质
			 */
			public  void setPhzscore(String phzscore)
			{
				this.phzscore=phzscore;
			}
				/**
		 *	@param tzbstj 体质辨识统计
			 */
			public  void setTzbstj(String tzbstj)
			{
				this.tzbstj=tzbstj;
			}
				/**
		 *	@param bjzdtj 中医药保健指导统计
			 */
			public  void setBjzdtj(String bjzdtj)
			{
				this.bjzdtj=bjzdtj;
			}

		public String toString()
    	{
        	        		return (new ToStringBuilder(this))
        		        		.append("tzbs_id", getTzbs_id())
        		        		.toString();
        	    	}

		public int hashCode()	{
    	    	return (new HashCodeBuilder(17, 37))
	    		    		.append(getTzbs_id())
	    	    	.toHashCode();
    		}

		public boolean equals(Object o) {
		    if ( !(o instanceof jktj_T_elderlyzytzbsDTO) ) {
        		return false;
    		}
		    if(o==this) {
                return true;
            }
		    jktj_T_elderlyzytzbsDTO me=(jktj_T_elderlyzytzbsDTO)o;
	    		        return new EqualsBuilder()
	        	        		.append(getTzbs_id(),me.getTzbs_id())
	                	.isEquals();
    	}
		public String getZdid00() {
			return zdid00;
		}
		public void setZdid00(String zdid00) {
			this.zdid00 = zdid00;
		}


		public java.sql.Timestamp getCjrq00() {
			return cjrq00;
		}
		public void setCjrq00(java.sql.Timestamp cjrq00) {
			this.cjrq00 = cjrq00;
		}
		public java.sql.Timestamp getZhbjrq() {
			return zhbjrq;
		}
		public void setZhbjrq(java.sql.Timestamp zhbjrq) {
			this.zhbjrq = zhbjrq;
		}
		public String getJktjcs() {
			return jktjcs;
		}
		public void setJktjcs(String jktjcs) {
			this.jktjcs = jktjcs;
		}
		public String getYyid00() {
			return yyid00;
		}
		public void setYyid00(String yyid00) {
			this.yyid00 = yyid00;
		}
		public String getXgpg00() {
			return xgpg00;
		}
		public void setXgpg00(String xgpg00) {
			this.xgpg00 = xgpg00;
		}
		public String getMyddc0() {
			return myddc0;
		}
		public void setMyddc0(String myddc0) {
			this.myddc0 = myddc0;
		}

}
