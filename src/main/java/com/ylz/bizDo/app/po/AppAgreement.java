package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 协议表
 */
@Entity
@Table(name = "APP_AGREEMENT")
public class AppAgreement extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name ="MENT_CITY_ID")
	private String mentCityId;//所属城市
	@Column(name = "MENT_HOSP_ID", length = 36)
	private String mentHospId;// 所属医院
	@Column(name = "MENT_TITLE", length = 20)
	private String mentTitle;//协议标题
	@Column(name = "MENT_CONTENT")
	private String mentContent;//协议内容
	@Column(name = "MENT_TYPE", length = 20)
	private String mentType;//协议类型
	@Column(name = "MENT_USE_TYPE", length = 20)
	private String mentUseType;//使用类型
	@Column(name = "MENT_STATE", length = 6)
	private String mentState;//状态

	// Constructors

	/** default constructor */
	public AppAgreement() {
	}

	/** minimal constructor */
	public AppAgreement(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppAgreement(String id,String mentCityId, String mentHospId,String mentTitle,
			String mentContent, String mentType,String mentUseType,String mentState) {
		this.id = id;
		this.mentCityId = mentCityId;
		this.mentHospId = mentHospId;
		this.mentTitle = mentTitle;
		this.mentContent = mentContent;
		this.mentType = mentType;
		this.mentUseType = mentUseType;
		this.mentState = mentState;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMentHospId() {
		return mentHospId;
	}

	public void setMentHospId(String mentHospId) {
		this.mentHospId = mentHospId;
	}

	public String getMentContent() {
		return this.mentContent;
	}

	public void setMentContent(String mentContent) {
		this.mentContent = mentContent;
	}

	public String getMentType() {
		return this.mentType;
	}

	public void setMentType(String mentType) {
		this.mentType = mentType;
	}

	public String getMentState() {
		return mentState;
	}

	public void setMentState(String mentState) {
		this.mentState = mentState;
	}

	public String getMentTitle() {
		return mentTitle;
	}

	public void setMentTitle(String mentTitle) {
		this.mentTitle = mentTitle;
	}

	public String getMentCityId() {
		return mentCityId;
	}

	public void setMentCityId(String mentCityId) {
		this.mentCityId = mentCityId;
	}

	public String getMentUseType() {
		return mentUseType;
	}

	public void setMentUseType(String mentUseType) {
		this.mentUseType = mentUseType;
	}

	public String getMentHospName(){
		if(StringUtils.isNotBlank(this.getMentHospId())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			AppHospDept value = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,this.getMentHospId());
			if(value != null){
				return value.getHospName();
			}
		}
		return "";
	}

	public String getMentCityName(){
		if(StringUtils.isNotBlank(this.getMentCityId())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			CdAddress value = (CdAddress)dao.getServiceDo().find(CdAddress.class,this.getMentCityId());
			if(value != null){
				return value.getAreaSname();
			}
		}
		return "";
	}

	public String getMentTypeName() throws Exception{
		if(StringUtils.isNotBlank(this.getMentType())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_MENTTYPE, this.getMentType());
			if(value!=null) {
				return value.getCodeTitle();
			}
		}
		return "";
	}

	public String getMentStateName() throws Exception{
		if(StringUtils.isNotBlank(this.getMentState())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ENABLE, this.getMentState());
			if(value!=null) {
				return value.getCodeTitle();
			}
		}
		return "";
	}
	public String getMentUseTypeName() throws Exception{
		if(StringUtils.isNotBlank(this.getMentUseType())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_MENTUSETYPE, this.getMentUseType());
			if(value!=null) {
				return value.getCodeTitle();
			}
		}
		return "";
	}
}