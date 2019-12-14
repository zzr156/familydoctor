package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class jktj_T_elderly_zybszdDTO {
 	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
		/**
 	 * 默认构造函数
 	 */
 	public jktj_T_elderly_zybszdDTO(){}

				private  String tzlx00; //
				private  String tzbm00; //
				private  String qzts00; //
				private  String state; //
				private String zybszdid;
				private String userid;
				private  String ysty00; //
				private  String qjts00; //
				private  String ydbj00; //
				private  String xwbj00; //
				private  String yyid00; //
				private  String jktjcs; //
				private  String df_id; //
				private String xcsfrq; // 下次随访日期
				private String zdrq00; // 指导日期
				private String ysname; // 指导医生
				private String ysxm00;// 指导医生姓名
					public String getZdrq00() {
					return zdrq00;
				}
				public void setZdrq00(String zdrq00) {
					this.zdrq00 = zdrq00;
				}
					public String getXcsfrq() {
					return xcsfrq;
				}
				public void setXcsfrq(String xcsfrq) {
					this.xcsfrq = xcsfrq;
				}
					/**
			*
			* @return tzlx00
			*/
			public  String getTzlx00() {
			return this.tzlx00;
			}
				/**
			*
			* @return tzbm00
			*/
			public  String getTzbm00() {
			return this.tzbm00;
			}


					/**
			*	@param tzlx00
			*/
			public  void setTzlx00(String tzlx00)
			{
				this.tzlx00=tzlx00;
			}
				/**
			*	@param tzbm00
			*/
			public  void setTzbm00(String tzbm00)
			{
				this.tzbm00=tzbm00;
			}


		public String toString()
    	{
        	        		return ToStringBuilder.reflectionToString(this);
        	    	}

		public int hashCode()	{
    	    		return HashCodeBuilder.reflectionHashCode(this);
    		}

		public boolean equals(Object o) {
		    if ( !(o instanceof jktj_T_elderly_zybszdDTO) ) {
        		return false;
    		}
		    if(o==this) {
                return true;
            }
		    jktj_T_elderly_zybszdDTO me=(jktj_T_elderly_zybszdDTO)o;
	            		return EqualsBuilder.reflectionEquals(this, o);
    	}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getZybszdid() {
			return zybszdid;
		}
		public void setZybszdid(String zybszdid) {
			this.zybszdid = zybszdid;
		}
		public String getQzts00() {
			return qzts00;
		}
		public void setQzts00(String qzts00) {
			this.qzts00 = qzts00;
		}
		public String getYsty00() {
			return ysty00;
		}
		public void setYsty00(String ysty00) {
			this.ysty00 = ysty00;
		}
		public String getQjts00() {
			return qjts00;
		}
		public void setQjts00(String qjts00) {
			this.qjts00 = qjts00;
		}
		public String getYdbj00() {
			return ydbj00;
		}
		public void setYdbj00(String ydbj00) {
			this.ydbj00 = ydbj00;
		}
		public String getXwbj00() {
			return xwbj00;
		}
		public void setXwbj00(String xwbj00) {
			this.xwbj00 = xwbj00;
		}
		public String getYyid00() {
			return yyid00;
		}
		public void setYyid00(String yyid00) {
			this.yyid00 = yyid00;
		}
		public String getJktjcs() {
			return jktjcs;
		}
		public void setJktjcs(String jktjcs) {
			this.jktjcs = jktjcs;
		}
		public String getDf_id() {
			return df_id;
		}
		public void setDf_id(String df_id) {
			this.df_id = df_id;
		}
		public String getYsname() {
			return ysname;
		}
		public void setYsname(String ysname) {
			this.ysname = ysname;
		}
		public String getYsxm00() {
			return ysxm00;
		}
		public void setYsxm00(String ysxm00) {
			this.ysxm00 = ysxm00;
		}

}
