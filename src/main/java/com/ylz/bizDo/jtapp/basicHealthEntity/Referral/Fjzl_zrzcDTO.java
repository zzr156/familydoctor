/*
 * CopyRight: StartTech 2010 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>Fjzl_zrzc</code>.
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.Referral;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

/**
 * <p>
 * Title: Fjzl_zrzc
 * </p>
 * <p>
 * Description:分级诊疗转入转出表
 * </p>
 * <p>
 * Copyright:Copyright(c)2010
 * </p>
 * <p>
 * Company:易联众技术股份有限公司
 * </p>
 *
 * @hibernate.class table="Fjzl_zrzc"
 * @author
 * @version 1.0
 */
/*
 * <mapping
 * resource="com/start/si/classification/common/entity/Fjzl_zrzc.hbm.xml" />
 */
public class Fjzl_zrzcDTO implements Serializable {
	/**
	 * 默认构造函数
	 */
	public Fjzl_zrzcDTO() {
	}

	private String zzsqdh; // 转诊申请单号 主键
	@JsonView({IFindGroup.class,IFindOrgGroup.class})
	private String orgid; // 转出医院id
	@JsonView({IFindGroup.class})
	private String community; // 转入医院
	@JsonView({IFindGroup.class})
	private String referraltype; // 转诊类别（1门诊、2检查、3住院、4康复）

	private String referraldoctorid; // 转诊医生
	@JsonView({IFindGroup.class})
	private String referraldoctorphone; // 医生电话
	@JsonView({IFindGroup.class})
	private String patientname; // 患者姓名
	@JsonView({IFindGroup.class})
	private String birthday; // 出生日期
	@JsonView({IFindGroup.class})
	private String mobile; // 联系电话
	@JsonView({IFindGroup.class})
	private String personid; // 身份证号
	@JsonView({IFindGroup.class})
	private String address; // 家庭地址
	@JsonView({IFindGroup.class})
	private String sex; // 性别
	@JsonView({IFindGroup.class})
	private String healthfeetype; // 费用类别（1医保、2公费、3新农合、4自费、5其他）
	@JsonView({IFindGroup.class})
	private String summary; // 病情摘要
	@JsonView({IFindGroup.class})
	private String diagnose; // 转诊前诊断
	@JsonView({IFindGroup.class})
	private String reserveflag; // 转诊状态（1待接收、2编辑中、3拒绝、4待就诊、5待接收超时、6已撤回、7已就诊）
	private String qrz000; // 确认者
	private String qrzxm0; // 确认者姓名
	private String userid; // 创建者id
	@JsonView({IFindGroup.class})
	private String username; // 创建者姓名
	@JsonView({IFindGroup.class,IFindOrgGroup.class})
	private String registerdate; // 创建日期
	private String zhbjz0; // 最后编辑者
	private String zhbjxm; // 最后编辑者姓名
	private String zhbjrq; // 最后编辑日期
	private String zdeptid; // 转出科室
	private String typesn; // 检查项目id
	private String jffs00; // 缴费方式(1社区代收费用2医院现场缴费)
	private String jfje00; // 缴费金额
	@JsonView({IFindGroup.class})
	private String zzyqsm; // 转诊要求说明
	@JsonView({IFindGroup.class})
	private String referraldoctorname; // 转诊医生姓名
	private String deptid; // 转往科室
	private String jzrq00; // 接诊日期
	private String doctorid; // 选择医师
	@JsonView({IFindGroup.class})
	private String doctorname; // 选择医师 姓名
	@JsonView({IFindGroup.class})
	private String doctoraphone; // 选择医师 联系电话
	@JsonView({IFindGroup.class})
	private String age; // 患者年龄
	@JsonView({IFindGroup.class})
	private String referralreason; // 转诊原因
	private String diagnosticresults; // 检查项目名称
	@JsonView({IFindGroup.class})
	private String commname; // 转入医院名称
	@JsonView({IFindGroup.class})
	private String zdeptanme; // 转诊科室名称
	@JsonView({IFindGroup.class})
	private String apn; // 上午：am 下午：pm 晚上：night
	private String expireenddate; // 转诊预约的结束时间
	private String expirestartdate; // 转诊预约的开始时间

	private String arrsn;// 号源id
	private String sequence;//号源序号
	@JsonView({IFindGroup.class})
	private String deptname;// 转往科室名称
	private String receivedoctorid;// 接诊医师
	private String receivedoctorname;// 接诊医师姓名
	private String receivedoctoraphone;// 接诊医师联系电话
	private String receivedeptid;// 接诊科室
	private String receivedeptname;// 接诊科室名称
	private String jzbz00;// 接诊备注
	private String jjly00;// 拒绝理由
	private String upfile;//上传文件
	private String referraldirection;//转诊方向：1上转，2下转，3平转
	private String clinic_date;//就诊时间
	@JsonView({IFindGroup.class,IFindOrgGroup.class})
	private String orgname;//申请机构名称
	@JsonView({IFindGroup.class})
	private String endemic_area;//入院病区
	@JsonView({IFindGroup.class})
	private String sickbed_no;//入院床位号
	private String doctorcommanded;//康复指导

	/**
	 * 转诊申请单号 主键
	 *
	 * @hibernate.property column="zzsqdh"
	 * @return zzsqdh
	 */
	public String getZzsqdh() {
		return this.zzsqdh;
	}

	/**
	 * 转出医院id
	 *
	 * @hibernate.property column="orgid"
	 * @return orgid
	 */
	public String getOrgid() {
		return this.orgid;
	}

	/**
	 * 转入医院
	 *
	 * @hibernate.property column="community"
	 * @return community
	 */
	public String getCommunity() {
		return this.community;
	}

	/**
	 * 转诊类别（1门诊、2检查、3住院、4康复）
	 *
	 * @hibernate.property column="referraltype"
	 * @return referraltype
	 */
	public String getReferraltype() {
		return this.referraltype;
	}

	/**
	 * 转诊医生
	 *
	 * @hibernate.property column="referraldoctorid"
	 * @return referraldoctorid
	 */
	public String getReferraldoctorid() {
		return this.referraldoctorid;
	}

	/**
	 * 医生电话
	 *
	 * @hibernate.property column="referraldoctorphone"
	 * @return referraldoctorphone
	 */
	public String getReferraldoctorphone() {
		return this.referraldoctorphone;
	}

	/**
	 * 患者姓名
	 *
	 * @hibernate.property column="patientname"
	 * @return patientname
	 */
	public String getPatientname() {
		return this.patientname;
	}

	/**
	 * 出生日期
	 *
	 * @hibernate.property column="birthday"
	 * @return birthday
	 */
	public String getBirthday() {
		return this.birthday;
	}

	/**
	 * 联系电话
	 *
	 * @hibernate.property column="mobile"
	 * @return mobile
	 */
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 身份证号
	 *
	 * @hibernate.property column="personid"
	 * @return personid
	 */
	public String getPersonid() {
		return this.personid;
	}

	/**
	 * 家庭地址
	 *
	 * @hibernate.property column="address"
	 * @return address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * 性别
	 *
	 * @hibernate.property column="sex"
	 * @return sex
	 */
	public String getSex() {
		return this.sex;
	}

	/**
	 * 费用类别（1医保、2公费、3新农合、4自费、5其他）
	 *
	 * @hibernate.property column="healthfeetype"
	 * @return healthfeetype
	 */
	public String getHealthfeetype() {
		return this.healthfeetype;
	}

	/**
	 * 病情摘要
	 *
	 * @hibernate.property column="summary"
	 * @return summary
	 */
	public String getSummary() {
		return this.summary;
	}

	/**
	 * 转诊前诊断
	 *
	 * @hibernate.property column="diagnose"
	 * @return diagnose
	 */
	public String getDiagnose() {
		return this.diagnose;
	}

	/**
	 * 转诊状态（1待接收、2编辑中、3拒绝、4待就诊、5待接收超时、6已撤回、7已就诊）
	 *
	 * @hibernate.property column="reserveflag"
	 * @return reserveflag
	 */
	public String getReserveflag() {
		return this.reserveflag;
	}

	/**
	 * 确认者
	 *
	 * @hibernate.property column="qrz000"
	 * @return qrz000
	 */
	public String getQrz000() {
		return this.qrz000;
	}

	/**
	 * 确认者姓名
	 *
	 * @hibernate.property column="qrzxm0"
	 * @return qrzxm0
	 */
	public String getQrzxm0() {
		return this.qrzxm0;
	}

	/**
	 * 创建者id
	 *
	 * @hibernate.property column="userid"
	 * @return userid
	 */
	public String getUserid() {
		return this.userid;
	}

	/**
	 * 创建者姓名
	 *
	 * @hibernate.property column="username"
	 * @return username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * 创建日期
	 *
	 * @hibernate.property column="registerdate"
	 * @return registerdate
	 */
	public String getRegisterdate() {
		return this.registerdate;
	}

	/**
	 * 最后编辑者
	 *
	 * @hibernate.property column="zhbjz0"
	 * @return zhbjz0
	 */
	public String getZhbjz0() {
		return this.zhbjz0;
	}

	/**
	 * 最后编辑者姓名
	 *
	 * @hibernate.property column="zhbjxm"
	 * @return zhbjxm
	 */
	public String getZhbjxm() {
		return this.zhbjxm;
	}

	/**
	 * 最后编辑日期
	 *
	 * @hibernate.property column="zhbjrq"
	 * @return zhbjrq
	 */
	public String getZhbjrq() {
		return this.zhbjrq;
	}

	/**
	 * 转出科室
	 *
	 * @hibernate.property column="zdeptid"
	 * @return zdeptid
	 */
	public String getZdeptid() {
		return this.zdeptid;
	}

	/**
	 * 检查项目id
	 *
	 * @hibernate.property column="typesn"
	 * @return typesn
	 */
	public String getTypesn() {
		return this.typesn;
	}

	/**
	 * 缴费方式(1社区代收费用2医院现场缴费)
	 *
	 * @hibernate.property column="jffs00"
	 * @return jffs00
	 */
	public String getJffs00() {
		return this.jffs00;
	}

	/**
	 * 缴费金额
	 *
	 * @hibernate.property column="jfje00"
	 * @return jfje00
	 */
	public String getJfje00() {
		return this.jfje00;
	}

	/**
	 * 转诊要求说明
	 *
	 * @hibernate.property column="zzyqsm"
	 * @return zzyqsm
	 */
	public String getZzyqsm() {
		return this.zzyqsm;
	}

	/**
	 * 转诊医生姓名
	 *
	 * @hibernate.property column="referraldoctorname"
	 * @return referraldoctorname
	 */
	public String getReferraldoctorname() {
		return this.referraldoctorname;
	}

	/**
	 * 转往科室
	 *
	 * @hibernate.property column="deptid"
	 * @return deptid
	 */
	public String getDeptid() {
		return this.deptid;
	}

	/**
	 * 接诊日期
	 *
	 * @hibernate.property column="jzrq00"
	 * @return jzrq00
	 */
	public String getJzrq00() {
		return this.jzrq00;
	}

	/**
	 * 选择医师
	 *
	 * @hibernate.property column="doctorid"
	 * @return doctorid
	 */
	public String getDoctorid() {
		return this.doctorid;
	}

	/**
	 * 选择医师 姓名
	 *
	 * @hibernate.property column="doctorname"
	 * @return doctorname
	 */
	public String getDoctorname() {
		return this.doctorname;
	}

	/**
	 * 选择医师 联系电话
	 *
	 * @hibernate.property column="doctoraphone"
	 * @return doctoraphone
	 */
	public String getDoctoraphone() {
		return this.doctoraphone;
	}

	/**
	 * 患者年龄
	 *
	 * @hibernate.property column="age"
	 * @return age
	 */
	public String getAge() {
		return this.age;
	}

	/**
	 * 转诊原因
	 *
	 * @hibernate.property column="referralreason"
	 * @return referralreason
	 */
	public String getReferralreason() {
		return this.referralreason;
	}

	/**
	 * 检查项目名称
	 *
	 * @hibernate.property column="diagnosticresults"
	 * @return diagnosticresults
	 */
	public String getDiagnosticresults() {
		return this.diagnosticresults;
	}

	/**
	 * 转入医院名称
	 *
	 * @hibernate.property column="commname"
	 * @return commname
	 */
	public String getCommname() {
		return this.commname;
	}

	/**
	 * 转诊科室名称
	 *
	 * @hibernate.property column="zdeptanme"
	 * @return zdeptanme
	 */
	public String getZdeptanme() {
		return this.zdeptanme;
	}

	/**
	 * 上午：am 下午：pm 晚上：night
	 *
	 * @hibernate.property column="apn"
	 * @return apn
	 */
	public String getApn() {
		return this.apn;
	}

	/**
	 * 转诊预约的结束时间
	 *
	 * @hibernate.property column="expireenddate"
	 * @return expireenddate
	 */
	public String getExpireenddate() {
		return this.expireenddate;
	}

	/**
	 * 转诊预约的开始时间
	 *
	 * @hibernate.property column="expirestartdate"
	 * @return expirestartdate
	 */
	public String getExpirestartdate() {
		return this.expirestartdate;
	}

	/**
	 * 号源id
	 *
	 * @hibernate.property column="arrsn"
	 * @return arrsn
	 */
	public String getArrsn() {
		return arrsn;
	}

	/**
	 * 转往科室名称
	 *
	 * @hibernate.property column="deptname"
	 * @return deptname
	 */
	public String getDeptname() {
		return deptname;
	}

	/**
	 * 接诊医师
	 *
	 * @hibernate.property column="receivedoctorid"
	 * @return deptname
	 */
	public String getReceivedoctorid() {
		return receivedoctorid;
	}

	/**
	 * 接诊医师姓名
	 *
	 * @hibernate.property column="receivedoctorname"
	 * @return deptname
	 */
	public String getReceivedoctorname() {
		return receivedoctorname;
	}

	/**
	 * 接诊医师联系电话
	 *
	 * @hibernate.property column="receivedoctoraphone"
	 * @return deptname
	 */
	public String getReceivedoctoraphone() {
		return receivedoctoraphone;
	}

	/**
	 * 接诊科室
	 *
	 * @hibernate.property column="receivedeptid"
	 * @return deptname
	 */
	public String getReceivedeptid() {
		return receivedeptid;
	}

	/**
	 * 接诊科室名称
	 *
	 * @hibernate.property column="receivedeptname"
	 * @return deptname
	 */
	public String getReceivedeptname() {
		return receivedeptname;
	}

	/**
	 * 接诊备注
	 *
	 * @hibernate.property column="jzbz00"
	 * @return jzbz00
	 */
	public String getJzbz00() {
		return jzbz00;
	}

	/**
	 * 拒绝理由
	 *
	 * @hibernate.property column="jjly00"
	 * @return jjly00
	 */
	public String getJjly00() {
		return jjly00;
	}

	/**
	 * 上传文件
	 *
	 * @hibernate.property column="upfile"
	 * @return upfile
	 */
	public String getUpfile() {
		return upfile;
	}

	/**
	 * 转诊方向：1上转，2下转，3平转
	 *
	 * @hibernate.property column="referraldirection"
	 * @return
	 */
	public String getReferraldirection() {
		return referraldirection;
	}

	/**
	 * 就诊时间
	 *
	 * @hibernate.property column="clinic_date"
	 * @return
	 */
	public String getClinic_date() {
		return clinic_date;
	}

	/**
	 *
	 * 申请机构名称
	 *
	 *@hibernate.property column="orgname"
	 * @return
	 */
	public String getOrgname() {
		return orgname;
	}

	/**
	 * 号源序号
	 * @hibernate.property column="sequence"
	 * @return
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * 入院病区
	 * @hibernate.property column="endemic_area"
	 * @return
	 */
	public String getEndemic_area() {
		return endemic_area;
	}

	/**
	 * 入院床位号
	 * @hibernate.property column="sickbed_no"
	 * @return
	 */
	public String getSickbed_no() {
		return sickbed_no;
	}

	/**
	 * 康复指导
	 * @hibernate.property column="doctorcommanded"
	 * @return
	 */
	public String getDoctorcommanded() {
		return doctorcommanded;
	}

	/**
	 *  号源序号
	 * @param sequence
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	/**
	 * 申请机构名称
	 * @param orgname
	 */
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	/**
	 * 就诊时间
	 * @param clinic_date
	 */
	public void setClinic_date(String clinic_date) {
		this.clinic_date = clinic_date;
	}

	/**
	 * 转诊方向：1上转，2下转，3平转
	 * @param referraldirection
	 */
	public void setReferraldirection(String referraldirection) {
		this.referraldirection = referraldirection;
	}

	/**
	 * @param zzsqdh
	 *            转诊申请单号 主键
	 */
	public void setZzsqdh(String zzsqdh) {
		this.zzsqdh = zzsqdh;
	}

	/**
	 * @param orgid
	 *            转出医院id
	 */
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	/**
	 * @param community
	 *            转入医院
	 */
	public void setCommunity(String community) {
		this.community = community;
	}

	/**
	 * @param referraltype
	 *            转诊类别（1门诊、2检查、3住院、4康复）
	 */
	public void setReferraltype(String referraltype) {
		this.referraltype = referraltype;
	}

	/**
	 * @param referraldoctorid
	 *            转诊医生
	 */
	public void setReferraldoctorid(String referraldoctorid) {
		this.referraldoctorid = referraldoctorid;
	}

	/**
	 * @param referraldoctorphone
	 *            医生电话
	 */
	public void setReferraldoctorphone(String referraldoctorphone) {
		this.referraldoctorphone = referraldoctorphone;
	}

	/**
	 * @param patientname
	 *            患者姓名
	 */
	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}

	/**
	 * @param birthday
	 *            出生日期
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * @param mobile
	 *            联系电话
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @param personid
	 *            身份证号
	 */
	public void setPersonid(String personid) {
		this.personid = personid;
	}

	/**
	 * @param address
	 *            家庭地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param sex
	 *            性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @param healthfeetype
	 *            费用类别（1医保、2公费、3新农合、4自费、5其他）
	 */
	public void setHealthfeetype(String healthfeetype) {
		this.healthfeetype = healthfeetype;
	}

	/**
	 * @param summary
	 *            病情摘要
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @param diagnose
	 *            转诊前诊断
	 */
	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	/**
	 * @param reserveflag
	 *            转诊状态（1待接收、2编辑中、3拒绝、4待就诊、5待接收超时、6已撤回、7已就诊）
	 */
	public void setReserveflag(String reserveflag) {
		this.reserveflag = reserveflag;
	}

	/**
	 * @param qrz000
	 *            确认者
	 */
	public void setQrz000(String qrz000) {
		this.qrz000 = qrz000;
	}

	/**
	 * @param qrzxm0
	 *            确认者姓名
	 */
	public void setQrzxm0(String qrzxm0) {
		this.qrzxm0 = qrzxm0;
	}

	/**
	 * @param userid
	 *            创建者id
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @param username
	 *            创建者姓名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param registerdate
	 *            创建日期
	 */
	public void setRegisterdate(String registerdate) {
		this.registerdate = registerdate;
	}

	/**
	 * @param zhbjz0
	 *            最后编辑者
	 */
	public void setZhbjz0(String zhbjz0) {
		this.zhbjz0 = zhbjz0;
	}

	/**
	 * @param zhbjxm
	 *            最后编辑者姓名
	 */
	public void setZhbjxm(String zhbjxm) {
		this.zhbjxm = zhbjxm;
	}

	/**
	 * @param zhbjrq
	 *            最后编辑日期
	 */
	public void setZhbjrq(String zhbjrq) {
		this.zhbjrq = zhbjrq;
	}

	/**
	 * @param zdeptid
	 *            转出科室
	 */
	public void setZdeptid(String zdeptid) {
		this.zdeptid = zdeptid;
	}

	/**
	 * @param typesn
	 *            检查项目id
	 */
	public void setTypesn(String typesn) {
		this.typesn = typesn;
	}

	/**
	 * @param jffs00
	 *            缴费方式(1社区代收费用2医院现场缴费)
	 */
	public void setJffs00(String jffs00) {
		this.jffs00 = jffs00;
	}

	/**
	 * @param jfje00
	 *            缴费金额
	 */
	public void setJfje00(String jfje00) {
		this.jfje00 = jfje00;
	}

	/**
	 * @param zzyqsm
	 *            转诊要求说明
	 */
	public void setZzyqsm(String zzyqsm) {
		this.zzyqsm = zzyqsm;
	}

	/**
	 * @param referraldoctorname
	 *            转诊医生姓名
	 */
	public void setReferraldoctorname(String referraldoctorname) {
		this.referraldoctorname = referraldoctorname;
	}

	/**
	 * @param deptid
	 *            转往科室
	 */
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	/**
	 * @param jzrq00
	 *            接诊日期
	 */
	public void setJzrq00(String jzrq00) {
		this.jzrq00 = jzrq00;
	}

	/**
	 * @param doctorid
	 *            选择医师
	 */
	public void setDoctorid(String doctorid) {
		this.doctorid = doctorid;
	}

	/**
	 * @param doctorname
	 *            选择医师 姓名
	 */
	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}

	/**
	 * @param doctoraphone
	 *            选择医师 联系电话
	 */
	public void setDoctoraphone(String doctoraphone) {
		this.doctoraphone = doctoraphone;
	}

	/**
	 * @param age
	 *            患者年龄
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * @param referralreason
	 *            转诊原因
	 */
	public void setReferralreason(String referralreason) {
		this.referralreason = referralreason;
	}

	/**
	 * @param diagnosticresults
	 *            检查项目名称
	 */
	public void setDiagnosticresults(String diagnosticresults) {
		this.diagnosticresults = diagnosticresults;
	}

	/**
	 * @param commname
	 *            转入医院名称
	 */
	public void setCommname(String commname) {
		this.commname = commname;
	}

	/**
	 * @param zdeptanme
	 *            转诊科室名称
	 */
	public void setZdeptanme(String zdeptanme) {
		this.zdeptanme = zdeptanme;
	}

	/**
	 * @param apn
	 *            上午：am 下午：pm 晚上：night
	 */
	public void setApn(String apn) {
		this.apn = apn;
	}

	/**
	 * @param expireenddate
	 *            转诊预约的结束时间
	 */
	public void setExpireenddate(String expireenddate) {
		this.expireenddate = expireenddate;
	}

	/**
	 * @param expirestartdate
	 *            转诊预约的开始时间
	 */
	public void setExpirestartdate(String expirestartdate) {
		this.expirestartdate = expirestartdate;
	}

	/**
	 * @param arrsn
	 *            号源id
	 */
	public void setArrsn(String arrsn) {
		this.arrsn = arrsn;
	}

	/**
	 * @param deptname
	 *            转往科室名称
	 */
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	/**
	 * @param jzbz00
	 *            接诊备注
	 */
	public void setJzbz00(String jzbz00) {
		this.jzbz00 = jzbz00;
	}

	/**
	 * @param jjly00
	 *            拒绝理由
	 */
	public void setJjly00(String jjly00) {
		this.jjly00 = jjly00;
	}

	/**
	 *
	 * @param receivedoctorid
	 */
	public void setReceivedoctorid(String receivedoctorid) {
		this.receivedoctorid = receivedoctorid;
	}

	/**
	 *
	 * @param receivedoctorname
	 */
	public void setReceivedoctorname(String receivedoctorname) {
		this.receivedoctorname = receivedoctorname;
	}

	/**
	 *
	 * @param receivedoctoraphone
	 */
	public void setReceivedoctoraphone(String receivedoctoraphone) {
		this.receivedoctoraphone = receivedoctoraphone;
	}

	/**
	 *
	 * @param receivedeptid
	 */
	public void setReceivedeptid(String receivedeptid) {
		this.receivedeptid = receivedeptid;
	}

	/**
	 *
	 * @param receivedeptname
	 */
	public void setReceivedeptname(String receivedeptname) {
		this.receivedeptname = receivedeptname;
	}

	/**
	 *
	 * @param upfile
	 */
	public void setUpfile(String upfile) {
		this.upfile = upfile;
	}

	/**
	 * 入院病区
	 * @param endemic_area
	 */
	public void setEndemic_area(String endemic_area) {
		this.endemic_area = endemic_area;
	}

	/**
	 * 入院床位号
	 * @param sickbed_no
	 */
	public void setSickbed_no(String sickbed_no) {
		this.sickbed_no = sickbed_no;
	}

	/**
	 * 康复指导
	 * @param doctorcommanded
	 */
	public void setDoctorcommanded(String doctorcommanded) {
		this.doctorcommanded = doctorcommanded;
	}
	public static interface IFindGroup{}

	public static interface IFindOrgGroup{}
}
