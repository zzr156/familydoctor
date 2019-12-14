package com.ylz.bizDo.web.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 医生表
 */
@Entity
@Table(name = "APP_DR_USER")
public class WebDrUser extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	private String id;//主键
	@Column(name = "DR_NAME", length = 20)
	private String drName;//医生名称
	@Column(name = "DR_HOSP_ID", length = 36)
	private String drHospId;//医院主键
	@Column(name = "DR_CODE", length = 50)
	private String drCode;//医生编号
	@Column(name = "DR_ACCOUNT", length = 15)
	private String drAccount;//医生账号
	@Column(name = "DR_PWD", length = 100)
	private String drPwd;//医生密码
	@Column(name = "DR_GENDER", length = 6)
	private String drGender;//医生性别
	@Column(name = "DR_TEL", length = 20)
	private String drTel;//医生电话
	@Column(name = "DR_IMAGEURL", length = 100)
	private String drImageurl;//医生头像
	@Column(name = "DR_IMAGE_NAME", length = 100)
	private String drImageName;//医生头像名字
	@Column(name = "DR_INTRO", length = 65535)
	private String drIntro;//医生简介
	@Column(name = "DR_GOOD", length = 65535)
	private String drGood;//医生擅长
	@Column(name = "DR_TYPE", length = 10)
	private String drType;//医生类别 1,全科医生,2护士,3公卫人员,4家庭医生
	@Column(name = "DR_JOB_TITLE", length = 40)
	private String drJobTitle;//医生工作职称
	@Column(name = "DR_STATE", length = 10)
	private String drState="1";//医生状态(1在职 2离职)
	@Column(name = "DR_EASE_STATE", length = 10)
	private String drEaseState;//环信状态
	@Column(name = "DR_JG_STATE", length = 10)
	private String drJgState;//极光状态
	@Column(name = "DR_BOPOMOFO", length = 100)
	private String drBopomofo;//拼音
	@Column(name = "DR_ROLE",length = 50)
	private String drRole;//用户权限
	@Column(name="DR_IDNO",length = 20)
	private String drIdno;//医生身份证信息
	/*@Column(name = "DR_PEOPLE",length = 10)
	private String drPeople;//目标人数*/
	@Column(name = "DR_PWD_STATE",length = 10)
	private String drs="0";//是否有修改过密码（0无 1有）
	@Column(name = "DR_TEL_PWD",length = 10)
	private String drTelPwd;//手机登录密码
	@Column(name = "DR_TYPE_ROLE",length = 10)
	private String drTypeRole;// 是否 村所用户 0 是 1 否

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDrName() {
		return this.drName;
	}

	public void setDrName(String drName) {
		this.drName = drName;
	}

	public String getDrHospId() {
		return this.drHospId;
	}

	public void setDrHospId(String drHospId) {
		this.drHospId = drHospId;
	}

	public String getDrCode() {
		return this.drCode;
	}

	public void setDrCode(String drCode) {
		this.drCode = drCode;
	}

	public String getDrAccount() {
		return this.drAccount;
	}

	public void setDrAccount(String drAccount) {
		this.drAccount = drAccount;
	}

	public String getDrPwd() {
		return this.drPwd;
	}

	public void setDrPwd(String drPwd) {
		this.drPwd = drPwd;
	}

	public String getDrGender() {
		return this.drGender;
	}

	public void setDrGender(String drGender) {
		this.drGender = drGender;
	}

	public String getDrTel() {
		return this.drTel;
	}

	public void setDrTel(String drTel) {
		this.drTel = drTel;
	}

	public String getDrImageurl() {
		return this.drImageurl;
	}

	public void setDrImageurl(String drImageurl) {
		this.drImageurl = drImageurl;
	}

	public String getDrImageName() {
		return drImageName;
	}

	public void setDrImageName(String drImageName) {
		this.drImageName = drImageName;
	}

	public String getDrIntro() {
		return this.drIntro;
	}

	public void setDrIntro(String drIntro) {
		this.drIntro = drIntro;
	}

	public String getDrGood() {
		return this.drGood;
	}

	public void setDrGood(String drGood) {
		this.drGood = drGood;
	}

	public String getDrType() {
		return this.drType;
	}

	public void setDrType(String drType) {
		this.drType = drType;
	}

	public String getDrJobTitle() {
		return this.drJobTitle;
	}

	public void setDrJobTitle(String drJobTitle) {
		this.drJobTitle = drJobTitle;
	}

	public String getDrState() {
		return this.drState;
	}

	public void setDrState(String drState) {
		this.drState = drState;
	}


	public String getDrEaseState() {
		return drEaseState;
	}

	public void setDrEaseState(String drEaseState) {
		this.drEaseState = drEaseState;
	}

	public String getDrRole() {
		return drRole;
	}

	public void setDrRole(String drRole) {
		this.drRole = drRole;
	}

	public String getDrJgState() {
		return drJgState;
	}

	public void setDrJgState(String drJgState) {
		this.drJgState = drJgState;
	}

	public String getDrBopomofo() {
		return drBopomofo;
	}

	public void setDrBopomofo(String drBopomofo) {
		this.drBopomofo = drBopomofo;
	}

	public String getDrIdno() {
		return drIdno;
	}

	public void setDrIdno(String drIdno) {
		this.drIdno = drIdno;
	}

	public String getDrs() {
		return drs;
	}

	public void setDrs(String drs) {
		this.drs = drs;
	}

	public String getDrTelPwd() {
		return drTelPwd;
	}

	public void setDrTelPwd(String drTelPwd) {
		this.drTelPwd = drTelPwd;
	}

	public String getDrTypeRole() {
		return drTypeRole;
	}

	public void setDrTypeRole(String drTypeRole) {
		this.drTypeRole = drTypeRole;
	}
}