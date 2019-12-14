package com.ylz.bizDo.web.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**个人用户表
 * AppPatientUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_PATIENT_USER")
public class WebPatientUser extends BasePO {

	// Fields
	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	private String id;
	@Column(name = "PATIENT_NAME", length = 20)
	private String patientName;//名字
	@Column(name = "PATIENT_GENDER", length = 10)
	private String patientGender;//性别
	@Column(name = "PATIENT_BIRTHDAY", length = 10)
	private Calendar patientBirthday;//出生日期
	@Column(name = "PATIENT_IDNO", length = 18)
	private String patientIdno;//身份证号
	@Column(name = "PATIENT_CARD", length = 20)
	private String patientCard;//社保卡
	@Column(name = "PATIENT_AGE", length = 10)
	private String patientAge;//年龄
	@Column(name = "PATIENT_TEL", length = 15)
	private String patientTel;//电话
	@Column(name = "PATIENT_ADDRESS", length = 200)
	private String patientAddress;//地址
	@Column(name = "PATIENT_IMAGEURL", length = 200)
	private String patientImageurl;//头像
	@Column(name = "PATIENT_IMAGE_NAME", length = 200)
	private String patientImageName;//头像文件名
	@Column(name = "PATIENT_PWD", length = 100)
	private String patientPwd;//密码
	@Column(name = "PATIENT_PROVINCE", length = 36)
	private String patientProvince;//省
	@Column(name = "PATIENT_CITY", length = 36)
	private String patientCity;//市
	@Column(name = "PATIENT_AREA", length = 36)
	private String patientArea;//区
	@Column(name = "PATIENT_STREET", length = 36)
	private String patientStreet;//街道
	@Column(name = "PATIENT_NEIGHBORHOOD_COMMITTEE", length = 36)
	private String patientNeighborhoodCommittee;//居委会
	@Column(name = "PATIENT_STATE", length = 10)
	private String patientState;//状态
	@Column(name = "PATIENT_HEALTHY", length = 10)
	private String patientHealthy;//是否授权健康档案
	@Column(name = "PATIENT_ORDINATE", length = 50)
	private String patientOrdinate;//纵坐标(经度)
	@Column(name = "PATIENT_ABSCISSA", length = 50)
	private String patientAbscissa;//横坐标(纬度)
	@Column(name = "PATIENT_EASE_STATE", length = 10)
	private String patientEaseState;//环信状态
	@Column(name = "PATIENT_ID_CARD_TEMP", length = 100)
	private String patientIdCardTemp;//临时字段(多个社保卡才存入该字段)
	@Column(name = "PATIENT_JG_STATE", length = 100)
	private String patientJgState;//极光状态
	@Column(name = "PATIENT_BOPOMOFO", length = 100)
	private String patientBopomofo;//拼音
	@Column(name = "PATIENT_HEIGHT", length = 100)
	private String patientHeight;//身高
	@Column(name = "PATIENT_WEIGHT", length = 100)
	private String patientWeight;//体重
	@Column(name = "PATIENT_BMI", length = 100)
	private String patientBmi;//BMI
	@Column(name = "PATIENT_UP_HPIS", length = 10)
	private String patientUpHpis;//激活状态（0激活 1未激活）
	@Column(name = "PATIENT_PWD_STATE",length = 10)
	private String patientd = "0";//
	// Property accessors

	/**
	 * web家签 新增字段
	 * @return
	 */
	@Column(name = "PATIENT_JTDA", length = 20)
	private String patientjtda;//居民家庭档案
	@Column(name = "PATIENT_JMDA", length = 20)
	private String patientjmda;//居民建康档案

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPatientName() {
		return this.patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}


	public String getPatientGender() {
		return this.patientGender;
	}

	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}


	public Calendar getPatientBirthday() {
		return this.patientBirthday;
	}

	public void setPatientBirthday(Calendar patientBirthday) {
		this.patientBirthday = patientBirthday;
	}


	public String getPatientIdno() {
		return this.patientIdno;
	}

	public void setPatientIdno(String patientIdno) {
		this.patientIdno = patientIdno;
	}


	public String getPatientCard() {
		return this.patientCard;
	}

	public void setPatientCard(String patientCard) {
		this.patientCard = patientCard;
	}


	public String getPatientAge() {
		return this.patientAge;
	}

	public void setPatientAge(String patientAge) {
		this.patientAge = patientAge;
	}


	public String getPatientTel() {
		return this.patientTel;
	}

	public void setPatientTel(String patientTel) {
		this.patientTel = patientTel;
	}


	public String getPatientAddress() {
		return this.patientAddress;
	}

	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}


	public String getPatientImageurl() {
		return this.patientImageurl;
	}

	public void setPatientImageurl(String patientImageurl) {
		this.patientImageurl = patientImageurl;
	}


	public String getPatientPwd() {
		return this.patientPwd;
	}

	public void setPatientPwd(String patientPwd) {
		this.patientPwd = patientPwd;
	}


	public String getPatientProvince() {
		return this.patientProvince;
	}

	public void setPatientProvince(String patientProvince) {
		this.patientProvince = patientProvince;
	}


	public String getPatientCity() {
		return this.patientCity;
	}

	public void setPatientCity(String patientCity) {
		this.patientCity = patientCity;
	}


	public String getPatientArea() {
		return this.patientArea;
	}

	public void setPatientArea(String patientArea) {
		this.patientArea = patientArea;
	}


	public String getPatientStreet() {
		return this.patientStreet;
	}

	public void setPatientStreet(String patientStreet) {
		this.patientStreet = patientStreet;
	}


	public String getPatientNeighborhoodCommittee() {
		return patientNeighborhoodCommittee;
	}

	public void setPatientNeighborhoodCommittee(String patientNeighborhoodCommittee) {
		this.patientNeighborhoodCommittee = patientNeighborhoodCommittee;
	}

	public String getPatientState() {
		return this.patientState;
	}

	public void setPatientState(String patientState) {
		this.patientState = patientState;
	}


	public String getPatientHealthy() {
		return this.patientHealthy;
	}

	public void setPatientHealthy(String patientHealthy) {
		this.patientHealthy = patientHealthy;
	}

	public String getPatientImageName() {
		return patientImageName;
	}

	public void setPatientImageName(String patientImageName) {
		this.patientImageName = patientImageName;
	}

	public String getPatientOrdinate() {
		return patientOrdinate;
	}

	public void setPatientOrdinate(String patientOrdinate) {
		this.patientOrdinate = patientOrdinate;
	}

	public String getPatientAbscissa() {
		return patientAbscissa;
	}

	public void setPatientAbscissa(String patientAbscissa) {
		this.patientAbscissa = patientAbscissa;
	}

	public String getPatientEaseState() {
		return patientEaseState;
	}

	public void setPatientEaseState(String patientEaseState) {
		this.patientEaseState = patientEaseState;
	}

	public String getPatientIdCardTemp() {
		return patientIdCardTemp;
	}

	public void setPatientIdCardTemp(String patientIdCardTemp) {
		this.patientIdCardTemp = patientIdCardTemp;
	}

	public String getPatientJgState() {
		return patientJgState;
	}

	public void setPatientJgState(String patientJgState) {
		this.patientJgState = patientJgState;
	}

	public String getPatientBopomofo() {
		return patientBopomofo;
	}

	public void setPatientBopomofo(String patientBopomofo) {
		this.patientBopomofo = patientBopomofo;
	}

	public String getPatientUpHpis() {
		return patientUpHpis;
	}

	public void setPatientUpHpis(String patientUpHpis) {
		this.patientUpHpis = patientUpHpis;
	}

	public String getPatientHeight() {
		return patientHeight;
	}

	public void setPatientHeight(String patientHeight) {
		this.patientHeight = patientHeight;
	}

	public String getPatientWeight() {
		return patientWeight;
	}

	public void setPatientWeight(String patientWeight) {
		this.patientWeight = patientWeight;
	}

	public String getPatientBmi() {
		return patientBmi;
	}

	public void setPatientBmi(String patientBmi) {
		this.patientBmi = patientBmi;
	}

	public String getPatientd() {
		return patientd;
	}

	public void setPatientd(String patientd) {
		this.patientd = patientd;
	}

	public String getPatientjtda() {
		return patientjtda;
	}

	public void setPatientjtda(String patientjtda) {
		this.patientjtda = patientjtda;
	}

	public String getPatientjmda() {
		return patientjmda;
	}

	public void setPatientjmda(String patientjmda) {
		this.patientjmda = patientjmda;
	}
}