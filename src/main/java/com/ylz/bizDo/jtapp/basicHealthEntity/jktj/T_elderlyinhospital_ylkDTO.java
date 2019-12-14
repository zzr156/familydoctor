/*
 * CopyRight: StartTech 2005 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>T_elderlyinhospital</code>.
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import java.io.Serializable;

/**
 * <p>
 * Title: T_elderlyinhospital
 * </p>
 * <p>
 * Description:老年人住院记录
 * </p>
 * <p>
 * Copyright:Copyright(c)2004
 * </p>
 * <p>
 * Company:实达科技
 * </p>
 *
 * @hibernate.class table="T_elderlyinhospital"
 * @author 劳动力99开发小组
 * @version 1.0
 */

public class T_elderlyinhospital_ylkDTO implements Serializable {

	private String ih_id; // 住院记录ID
	private String eld_id; // 老年人保健ID
	private String indate; // 入院日期
	private String outdate; // 出院日期
	private String hospital; // 医院
	private String departments; // 科别
	private String diagnosis; // 诊断
	private String surgery; // 手术
	private String result; // 结果
	private String inhreason; // 入院原因
	private String yyid00; // 机构ID
	private String jktjcs; // 健康体检次数
	private String df_id; // 居民编号
	private String zybah; // 住院病案号

	/**
	 * 住院记录ID
	 *
	 * @hibernate.property column="ih_id"
	 * @return ih_id
	 */
	public String getIh_id() {
		return this.ih_id;
	}

	/**
	 * 老年人保健ID
	 *
	 * @hibernate.property column="eld_id"
	 * @return eld_id
	 */
	public String getEld_id() {
		return this.eld_id;
	}

	/**
	 * 入院日期
	 *
	 * @hibernate.property column="indate"
	 * @return indate
	 */
	public String getIndate() {
		return this.indate;
	}

	/**
	 * 出院日期
	 *
	 * @hibernate.property column="outdate"
	 * @return outdate
	 */
	public String getOutdate() {
		return this.outdate;
	}

	/**
	 * 医院
	 *
	 * @hibernate.property column="hospital"
	 * @return hospital
	 */
	public String getHospital() {
		return this.hospital;
	}

	/**
	 * 科别
	 *
	 * @hibernate.property column="departments"
	 * @return departments
	 */
	public String getDepartments() {
		return this.departments;
	}

	/**
	 * 诊断
	 *
	 * @hibernate.property column="diagnosis"
	 * @return diagnosis
	 */
	public String getDiagnosis() {
		return this.diagnosis;
	}

	/**
	 * 手术
	 *
	 * @hibernate.property column="surgery"
	 * @return surgery
	 */
	public String getSurgery() {
		return this.surgery;
	}

	/**
	 * 结果
	 *
	 * @hibernate.property column="result"
	 * @return result
	 */
	public String getResult() {
		return this.result;
	}

	/**
	 * 入院原因
	 *
	 * @hibernate.property column="inhreason"
	 * @return inhreason
	 */
	public String getInhreason() {
		return this.inhreason;
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
	 * 健康体检次数
	 *
	 * @hibernate.property column="jktjcs"
	 * @return jktjcs
	 */
	public String getJktjcs() {
		return this.jktjcs;
	}

	/**
	 * 居民编号
	 *
	 * @hibernate.property column="df_id"
	 * @return df_id
	 */
	public String getDf_id() {
		return this.df_id;
	}

	/**
	 * 住院病案号
	 *
	 * @hibernate.property column="zybah"
	 * @return zybah
	 */
	public String getZybah() {
		return this.zybah;
	}

	/**
	 * @param ih_id
	 *            住院记录ID
	 */
	public void setIh_id(String ih_id) {
		this.ih_id = ih_id;
	}

	/**
	 * @param eld_id
	 *            老年人保健ID
	 */
	public void setEld_id(String eld_id) {
		this.eld_id = eld_id;
	}

	/**
	 * @param indate
	 *            入院日期
	 */
	public void setIndate(String indate) {
		this.indate = indate;
	}

	/**
	 * @param outdate
	 *            出院日期
	 */
	public void setOutdate(String outdate) {
		this.outdate = outdate;
	}

	/**
	 * @param hospital
	 *            医院
	 */
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	/**
	 * @param departments
	 *            科别
	 */
	public void setDepartments(String departments) {
		this.departments = departments;
	}

	/**
	 * @param diagnosis
	 *            诊断
	 */
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	/**
	 * @param surgery
	 *            手术
	 */
	public void setSurgery(String surgery) {
		this.surgery = surgery;
	}

	/**
	 * @param result
	 *            结果
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @param inhreason
	 *            入院原因
	 */
	public void setInhreason(String inhreason) {
		this.inhreason = inhreason;
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
	 *            居民编号
	 */
	public void setDf_id(String df_id) {
		this.df_id = df_id;
	}

	/**
	 * @param zybah
	 *            住院病案号
	 */
	public void setZybah(String zybah) {
		this.zybah = zybah;
	}

}