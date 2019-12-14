package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.*;

/**
 * 莆田参保人员表
 */
@Entity
@Table(name = "APP_HFS_SIGN_SSC")
public class AppHfsSignSsc extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "PT_NUMBER", length = 30)
	private String ptNumber;
	@Column(name = "PT_NATURE", length = 200)
	private String ptNature;
	@Column(name = "PT_NAME", length = 50)
	private String ptName;
	@Column(name = "PT_GENDER", length = 10)
	private String ptGender;
	@Column(name = "PT_BIRTH", length = 10)
	private Calendar ptBirth;
	@Column(name = "PT_USER_PH", length = 200)
	private String ptUserPh;
	@Column(name = "PT_USER_NAME", length = 50)
	private String ptUserName;
	@Column(name = "PT_ID_NO", length = 30)
	private String ptIdNo;
	@Column(name = "PT_SCC_NO", length = 30)
	private String ptSccNo;
	@Column(name = "PT_REGION", length = 200)
	private String ptRegion;
	@Column(name = "PT_TYPE", length = 50)
	private String ptType;
	@Column(name = "PT_LX", length = 5)
	private String ptLx;
	@Column(name = "PT_NATION", length = 30)
	private String ptNation;
	@Column(name = "PT_TELEPHONE", length = 30)
	private String ptTelephone;
	@Column(name = "PT_ORGID", length = 40)
	private String ptOrgid;
	@Column(name = "PT_WORKSPACE")
	private String ptWorkspace;
	@Column(name = "PT_JTBH", length = 50)
	private String ptJtbh;

	// Constructors

	/** default constructor */
	public AppHfsSignSsc() {
	}

	/** minimal constructor */
	public AppHfsSignSsc(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppHfsSignSsc(String id, String ptNumber, String ptNature,
			String ptName, String ptGender, Calendar ptBirth, String ptUserPh,
			String ptUserName, String ptIdNo, String ptSccNo, String ptRegion,
			String ptType, String ptLx, String ptNation, String ptTelephone,
			String ptOrgid, String ptWorkspace, String ptJtbh) {
		this.id = id;
		this.ptNumber = ptNumber;
		this.ptNature = ptNature;
		this.ptName = ptName;
		this.ptGender = ptGender;
		this.ptBirth = ptBirth;
		this.ptUserPh = ptUserPh;
		this.ptUserName = ptUserName;
		this.ptIdNo = ptIdNo;
		this.ptSccNo = ptSccNo;
		this.ptRegion = ptRegion;
		this.ptType = ptType;
		this.ptLx = ptLx;
		this.ptNation = ptNation;
		this.ptTelephone = ptTelephone;
		this.ptOrgid = ptOrgid;
		this.ptWorkspace = ptWorkspace;
		this.ptJtbh = ptJtbh;
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPtNumber() {
		return this.ptNumber;
	}

	public void setPtNumber(String ptNumber) {
		this.ptNumber = ptNumber;
	}

	public String getPtNature() {
		return this.ptNature;
	}

	public void setPtNature(String ptNature) {
		this.ptNature = ptNature;
	}

	public String getPtName() {
		return this.ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtGender() {
		return this.ptGender;
	}

	public void setPtGender(String ptGender) {
		this.ptGender = ptGender;
	}

	public Calendar getPtBirth() {
		return this.ptBirth;
	}

	public void setPtBirth(Calendar ptBirth) {
		this.ptBirth = ptBirth;
	}

	public String getPtUserPh() {
		return this.ptUserPh;
	}

	public void setPtUserPh(String ptUserPh) {
		this.ptUserPh = ptUserPh;
	}

	public String getPtUserName() {
		return this.ptUserName;
	}

	public void setPtUserName(String ptUserName) {
		this.ptUserName = ptUserName;
	}

	public String getPtIdNo() {
		return this.ptIdNo;
	}

	public void setPtIdNo(String ptIdNo) {
		this.ptIdNo = ptIdNo;
	}

	public String getPtSccNo() {
		return this.ptSccNo;
	}

	public void setPtSccNo(String ptSccNo) {
		this.ptSccNo = ptSccNo;
	}

	public String getPtRegion() {
		return this.ptRegion;
	}

	public void setPtRegion(String ptRegion) {
		this.ptRegion = ptRegion;
	}

	public String getPtType() {
		return this.ptType;
	}

	public void setPtType(String ptType) {
		this.ptType = ptType;
	}

	public String getPtLx() {
		return this.ptLx;
	}

	public void setPtLx(String ptLx) {
		this.ptLx = ptLx;
	}

	public String getPtNation() {
		return this.ptNation;
	}

	public void setPtNation(String ptNation) {
		this.ptNation = ptNation;
	}

	public String getPtTelephone() {
		return this.ptTelephone;
	}

	public void setPtTelephone(String ptTelephone) {
		this.ptTelephone = ptTelephone;
	}

	public String getPtOrgid() {
		return this.ptOrgid;
	}

	public void setPtOrgid(String ptOrgid) {
		this.ptOrgid = ptOrgid;
	}

	public String getPtWorkspace() {
		return this.ptWorkspace;
	}

	public void setPtWorkspace(String ptWorkspace) {
		this.ptWorkspace = ptWorkspace;
	}

	public String getPtJtbh() {
		return this.ptJtbh;
	}

	public void setPtJtbh(String ptJtbh) {
		this.ptJtbh = ptJtbh;
	}

}