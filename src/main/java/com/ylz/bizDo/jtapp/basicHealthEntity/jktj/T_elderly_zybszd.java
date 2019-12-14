/*
 * CopyRight: StartTech 2010 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>T_elderly_zybszd</code>.
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * <p>
 * Title: T_elderly_zybszd
 * </p>
 * <p>
 * Description:中医体质辨识指导内容
 * </p>
 * <p>
 * Copyright:Copyright(c)2010
 * </p>
 * <p>
 * Company:易联众技术股份有限公司
 * </p>
 *
 * @hibernate.class table="T_elderly_zybszd"
 * @author
 * @version 1.0
 */
/*
 * <mapping resource="com/start/si/classification/test/T_elderly_zybszd.hbm.xml"
 * />
 */
public class T_elderly_zybszd{
	/**
	 * 默认构造函数
	 */
	public T_elderly_zybszd() {
	}

	private String tzlx00; // 体质类型
	private String tzbm00; // 体质名称
	private String qzts00; // 情志调摄
	private String state; // 标志
	private String zybszdid; // 体质类型ID
	private String userid; // 所属人ID
	private String ysty00; // 饮食调养
	private String qjts00; // 起居调摄
	private String ydbj00; // 运动保健
	private String xwbj00; // 穴位保健
	private String yyid00; // 机构ID
	private String jktjcs; // 健康体检次数
	private String df_id; // 居民档案号
	private String xcsfrq; // 下次随访日期
	private String zdrq00; // 指导日期
	private String ysxm00; // 医生姓名

	/**
	 * 体质类型
	 *
	 * @hibernate.property column="tzlx00"
	 * @return tzlx00
	 */
	@Basic
	@Column(name = "TZLX00")
	public String getTzlx00() {
		return this.tzlx00;
	}

	/**
	 * 体质名称
	 *
	 * @hibernate.property column="tzbm00"
	 * @return tzbm00
	 */
	@Basic
	@Column(name = "TZBM00")
	public String getTzbm00() {
		return this.tzbm00;
	}

	/**
	 * 情志调摄
	 *
	 * @hibernate.property column="qzts00"
	 * @return qzts00
	 */
	@Basic
	@Column(name = "QZTS00")
	public String getQzts00() {
		return this.qzts00;
	}

	/**
	 * 标志
	 *
	 * @hibernate.property column="state"
	 * @return state
	 */
	@Basic
	@Column(name = "STATE")
	public String getState() {
		return this.state;
	}

	/**
	 * 体质类型ID
	 *
	 * @hibernate.property column="zybszdid"
	 * @return zybszdid
	 */
	@Id
	@Column(name = "ZYBSZDID")
	public String getZybszdid() {
		return this.zybszdid;
	}

	/**
	 * 所属人ID
	 *
	 * @hibernate.property column="userid"
	 * @return userid
	 */
	@Basic
	@Column(name = "USERID")
	public String getUserid() {
		return this.userid;
	}

	/**
	 * 饮食调养
	 *
	 * @hibernate.property column="ysty00"
	 * @return ysty00
	 */
	@Basic
	@Column(name = "YSTY00")
	public String getYsty00() {
		return this.ysty00;
	}

	/**
	 * 起居调摄
	 *
	 * @hibernate.property column="qjts00"
	 * @return qjts00
	 */
	@Basic
	@Column(name = "QJTS00")
	public String getQjts00() {
		return this.qjts00;
	}

	/**
	 * 运动保健
	 *
	 * @hibernate.property column="ydbj00"
	 * @return ydbj00
	 */
	@Basic
	@Column(name = "YDBJ00")
	public String getYdbj00() {
		return this.ydbj00;
	}

	/**
	 * 穴位保健
	 *
	 * @hibernate.property column="xwbj00"
	 * @return xwbj00
	 */
	@Basic
	@Column(name = "XWBJ00")
	public String getXwbj00() {
		return this.xwbj00;
	}

	/**
	 * 机构ID
	 *
	 * @hibernate.property column="yyid00"
	 * @return yyid00
	 */
	@Basic
	@Column(name = "YYID00")
	public String getYyid00() {
		return this.yyid00;
	}

	/**
	 * 健康体检次数
	 *
	 * @hibernate.property column="jktjcs"
	 * @return jktjcs
	 */
	@Basic
	@Column(name = "JKTJCS")
	public String getJktjcs() {
		return this.jktjcs;
	}

	/**
	 * 居民档案号
	 *
	 * @hibernate.property column="df_id"
	 * @return df_id
	 */
	@Basic
	@Column(name = "DF_ID")
	public String getDf_id() {
		return this.df_id;
	}

	/**
	 * 下次随访日期
	 *
	 * @hibernate.property column="xcsfrq"
	 * @return xcsfrq
	 */
	@Basic
	@Column(name = "XCSFRQ")
	public String getXcsfrq() {
		return this.xcsfrq;
	}

	/**
	 * 指导日期
	 *
	 * @hibernate.property column="zdrq00"
	 * @return zdrq00
	 */
	@Basic
	@Column(name = "ZDRQ00")
	public String getZdrq00() {
		return this.zdrq00;
	}

	/**
	 * 医生姓名
	 *
	 * @hibernate.property column="ysxm00"
	 * @return ysxm00
	 */
	@Basic
	@Column(name = "YSXM00")
	public String getYsxm00() {
		return this.ysxm00;
	}

	/**
	 * @param tzlx00
	 *            体质类型
	 */
	public void setTzlx00(String tzlx00) {
		this.tzlx00 = tzlx00;
	}

	/**
	 * @param tzbm00
	 *            体质名称
	 */
	public void setTzbm00(String tzbm00) {
		this.tzbm00 = tzbm00;
	}

	/**
	 * @param qzts00
	 *            情志调摄
	 */
	public void setQzts00(String qzts00) {
		this.qzts00 = qzts00;
	}

	/**
	 * @param state
	 *            标志
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param zybszdid
	 *            体质类型ID
	 */
	public void setZybszdid(String zybszdid) {
		this.zybszdid = zybszdid;
	}

	/**
	 * @param userid
	 *            所属人ID
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @param ysty00
	 *            饮食调养
	 */
	public void setYsty00(String ysty00) {
		this.ysty00 = ysty00;
	}

	/**
	 * @param qjts00
	 *            起居调摄
	 */
	public void setQjts00(String qjts00) {
		this.qjts00 = qjts00;
	}

	/**
	 * @param ydbj00
	 *            运动保健
	 */
	public void setYdbj00(String ydbj00) {
		this.ydbj00 = ydbj00;
	}

	/**
	 * @param xwbj00
	 *            穴位保健
	 */
	public void setXwbj00(String xwbj00) {
		this.xwbj00 = xwbj00;
	}

	/**
	 * @param yyid00
	 *            机构ID
	 */
	public void setYyid00(String yyid00) {
		this.yyid00 = yyid00;
	}

	/**
	 * @param jktjcs
	 *            健康体检次数
	 */
	public void setJktjcs(String jktjcs) {
		this.jktjcs = jktjcs;
	}

	/**
	 * @param df_id
	 *            居民档案号
	 */
	public void setDf_id(String df_id) {
		this.df_id = df_id;
	}

	/**
	 * @param xcsfrq
	 *            下次随访日期
	 */
	public void setXcsfrq(String xcsfrq) {
		this.xcsfrq = xcsfrq;
	}

	/**
	 * @param zdrq00
	 *            指导日期
	 */
	public void setZdrq00(String zdrq00) {
		this.zdrq00 = zdrq00;
	}

	/**
	 * @param ysxm00
	 *            医生姓名
	 */
	public void setYsxm00(String ysxm00) {
		this.ysxm00 = ysxm00;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public boolean equals(Object o) {
		if (!(o instanceof T_elderly_zybszd)) {
			return false;
		}
		if (o == this) {
            return true;
        }
		T_elderly_zybszd me = (T_elderly_zybszd) o;
		return EqualsBuilder.reflectionEquals(this, o);
	}
}
