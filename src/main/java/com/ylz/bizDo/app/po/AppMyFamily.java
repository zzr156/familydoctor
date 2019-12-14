package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 我的家庭
 */
@Entity
@Table(name = "APP_MY_FAMILY")
public class AppMyFamily extends BasePO {

	// Fields

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//z主键
	@Column(name = "MY_PATIENT_ID", length = 36)
	private String myPatientId;//患者主键
	@Column(name = "MF_FM_PATIENT_ID", length = 36)
	private String mfFmPatientId;//成员主键
	@Column(name = "MF_FM_ID_NO", length = 50)
	private String mfFmIdno;//成员身份证
	@Column(name = "MF_FM_CARD", length = 50)
	private String mfFmCard;//社保卡
	@Column(name = "MF_FM_AGE", length = 50)
	private String mfFmAge;//成员年龄
	@Column(name = "MF_FM_MYKH", length = 50)
	private String mfFmMykh;//成员免疫卡号
	@Column(name = "MF_FM_GENDER", length = 50)
	private String mfFMGender;//成员性别
	@Column(name = "MF_FM_BIRTHDAY", length = 50)
	private Calendar mfFmBirthday;//成员出生日期
	@Column(name = "MF_FM_NAME", length = 20)
	private String mfFmName;//成员姓名
	@Column(name = "MF_FM_NICK_NAME", length = 20)
	private String mfFmNickName;//成员昵称（关系code）
	@Column(name = "MF_FM_STATE", length = 20)
	private String mfFmState;//是否开启监测异常提醒


	// Constructors

	/** default constructor */
	public AppMyFamily() {
	}

	/** minimal constructor */
	public AppMyFamily(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppMyFamily(String id, String myPatientId, String mfFmPatientId,
					   String mfFmIdno,String mfFmName,String mfFmNickName,String mfFmState ) {
		this.id = id;
		this.myPatientId = myPatientId;
		this.mfFmPatientId = mfFmPatientId;
		this.mfFmIdno = mfFmIdno;
		this.mfFmName = mfFmName;
		this.mfFmNickName = mfFmNickName;
		this.mfFmState = mfFmState;

	}

	// Property accessors


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMyPatientId() {
		return myPatientId;
	}

	public void setMyPatientId(String myPatientId) {
		this.myPatientId = myPatientId;
	}

	public String getMfFmPatientId() {
		return mfFmPatientId;
	}

	public void setMfFmPatientId(String mfFmPatientId) {
		this.mfFmPatientId = mfFmPatientId;
	}

	public String getMfFmIdno() {
		return mfFmIdno;
	}

	public void setMfFmIdno(String mfFmIdno) {
		this.mfFmIdno = mfFmIdno;
	}

	public String getMfFmName() {
		return mfFmName;
	}

	public void setMfFmName(String mfFmName) {
		this.mfFmName = mfFmName;
	}

	public String getMfFmNickName() {
		return mfFmNickName;
	}

	public void setMfFmNickName(String mfFmNickName) {
		this.mfFmNickName = mfFmNickName;
	}

	public String getMfFmAge() {
		return mfFmAge;
	}

	public void setMfFmAge(String mfFmAge) {
		this.mfFmAge = mfFmAge;
	}

	public String getMfFmMykh() {
		return mfFmMykh;
	}

	public void setMfFmMykh(String mfFmMykh) {
		this.mfFmMykh = mfFmMykh;
	}

	public String getMfFMGender() {
		return mfFMGender;
	}

	public void setMfFMGender(String mfFMGender) {
		this.mfFMGender = mfFMGender;
	}

	public Calendar getMfFmBirthday() {
		return mfFmBirthday;
	}

	public void setMfFmBirthday(Calendar mfFmBirthday) {
		this.mfFmBirthday = mfFmBirthday;
	}

	public String getStrMfFmBirthday(){
		return ExtendDate.getYMD(this.getMfFmBirthday());
	}

	public String getStrMfFmNickName()throws Exception{
		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
		CdCode p = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_FAMILYRELATION,this.getMfFmNickName());
		if(p != null){
			String[] titles = p.getCodeTitle().split(";");
			return titles[0];
		}
		return  "";
	}

	public String getMfFmCard() {
		return mfFmCard;
	}

	public void setMfFmCard(String mfFmCard) {
		this.mfFmCard = mfFmCard;
	}

	public String getMfFmState() {
		return mfFmState;
	}

	public void setMfFmState(String mfFmState) {
		this.mfFmState = mfFmState;
	}
}