package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 医生表
 */
@Entity
@Table(name = "APP_DR_USER")
//@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_WRITE)//要使用hibernate的二级缓存Cache的注释
public class AppDrUser extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
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
	private String drPwd;//医生WEB密码
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
	private String drState="1";//医生状态 1在职 2离职
	@Column(name = "DR_EASE_STATE", length = 10)
	private String drEaseState;//环信状态
	@Column(name = "DR_JG_STATE", length = 10)
	private String drJgState;//极光状态
	@Column(name = "DR_BOPOMOFO", length = 100)
	private String drBopomofo;//拼音
	@Column(name = "DR_ROLE",length = 50)
	private String drRole;//用户权限
	@Column(name = "DR_WEB_STATE",length = 10)
	private String drWebState;//是否web登入
	/*@Column(name = "DR_PEOPLE",length = 10)
	private String drPeople;//目标人数*/
	@Column(name = "DR_PWD_STATE",length = 10)
	private String drs="0";//是否有修改过密码（0无 1有）
	@Column(name = "DR_TX_STATE",length = 10)
	private String drTxState;//每月是否提醒（0未提醒 1提醒）
	@Column(name = "DR_REFERRAL_STATE",length = 10)
	private String drReferralState;//是否接收转诊消息
	@Column(name = "DR_DEPARTMENT_ID",length = 36)
	private String drDepartmentId;//科室id
	@Column(name="DR_IDNO",length = 20)
	private String drIdno;//医生身份证信息
	// Constructors
	@Column(name = "DR_ZX_STATE",length = 10)
	private String drZxState="0";
	@Column(name = "DR_TEL_PWD",length = 100)
	private String drTelPwd;//手机登录密码
	@Column(name = "DR_TYPE_ROLE",length = 10)
	private String drTypeRole;// 是否 村所用户 0 是 1 否

	@Column(name = "DR_ABROAB_URL",length = 200)
	private String drAbroabUrl;//外网签名地址 (poss机医生登入返回)
	@Column(name = "DR_WITHIN_URL",length = 200)
	private String drWithinUrl;//内网签名地址
	/** default constructor */
	public AppDrUser() {
	}

	/** minimal constructor */
	public AppDrUser(String id) {
		this.id = id;
	}

	public AppDrUser(String drName, String drHospId, String drCode,
					 String drAccount, String drPwd, String drGender,
					 String drTel, String drImageurl, String drImageName,
					 String drIntro, String drGood, String drType, String drJobTitle,
					 String drState, String drEaseState, String drJgState, String drBopomofo,
					 String drRole, String drWebState, String drs,String drTxState,String drReferralState,String drDepartmentId,
					 String drAbroabUrl,String drWithinUrl) {
		this.drName = drName;
		this.drHospId = drHospId;
		this.drCode = drCode;
		this.drAccount = drAccount;
		this.drPwd = drPwd;
		this.drGender = drGender;
		this.drTel = drTel;
		this.drImageurl = drImageurl;
		this.drImageName = drImageName;
		this.drIntro = drIntro;
		this.drGood = drGood;
		this.drType = drType;
		this.drJobTitle = drJobTitle;
		this.drState = drState;
		this.drEaseState = drEaseState;
		this.drJgState = drJgState;
		this.drBopomofo = drBopomofo;
		this.drRole = drRole;
		this.drWebState = drWebState;
		this.drs = drs;
		this.drTxState = drTxState;
		this.drReferralState = drReferralState;
		this.drDepartmentId = drDepartmentId;
		this.drAbroabUrl = drAbroabUrl;
		this.drWithinUrl = drWithinUrl;

	}

	/** full constructor */


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

	public String getDrWebState() {
		return drWebState;
	}

	public void setDrWebState(String drWebState) {
		this.drWebState = drWebState;
	}

	//医院名称
	public String getDrHospName(){
		SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
		if(StringUtils.isNotBlank(this.getDrHospId())){
			AppHospDept hosp = (AppHospDept) dao.getServiceDo().find(AppHospDept.class,this.getDrHospId());
			if(hosp!=null){
				if(StringUtils.isNotBlank(hosp.getHospName())){
					return hosp.getHospName();
				}
			}
		}
		return  "";
	}

	//医生类别名称
	public String getDrTypeName() throws Exception{
		if(StringUtils.isNotBlank(this.getDrType())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YSTYPE, this.getDrType());
			if(value!=null) {
                return value.getCodeTitle();
            }
		}
		return "";
	}

	//医生状态名称
	public String getDrStateName() throws Exception{
		if(StringUtils.isNotBlank(this.getDrState())){
			SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_WORKSTATE,this.getDrState());
			if(value != null){
				return value.getCodeTitle();
			}
		}
		return "";
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

	public String getDrs() {
		return drs;
	}

	public void setDrs(String drs) {
		this.drs = drs;
	}

	public String getDrTxState() {
		return drTxState;
	}

	public void setDrTxState(String drTxState) {
		this.drTxState = drTxState;
	}

	public String getDrReferralState() {
		return drReferralState;
	}

	public void setDrReferralState(String drReferralState) {
		this.drReferralState = drReferralState;
	}

	public String getDrDepartmentId() {
		return drDepartmentId;
	}

	public void setDrDepartmentId(String drDepartmentId) {
		this.drDepartmentId = drDepartmentId;
	}

	public String getDrIdno() {
		return drIdno;
	}

	public void setDrIdno(String drIdno) {
		this.drIdno = drIdno;
	}

	public String getDrZxState() {
		return drZxState;
	}

	public void setDrZxState(String drZxState) {
		this.drZxState = drZxState;
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

	public String getDrAbroabUrl() {
		return drAbroabUrl;
	}

	public void setDrAbroabUrl(String drAbroabUrl) {
		this.drAbroabUrl = drAbroabUrl;
	}

	public String getDrWithinUrl() {
		return drWithinUrl;
	}

	public void setDrWithinUrl(String drWithinUrl) {
		this.drWithinUrl = drWithinUrl;
	}
}