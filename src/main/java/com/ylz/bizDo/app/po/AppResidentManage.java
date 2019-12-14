package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/** 居民管理
 * AppResidentManage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_RESIDENT_MANAGE")
public class AppResidentManage extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "RM_GROUP", length = 10)
	private String rmGroup;//居民分组值
	@Column(name = "RM_GROUP_NAME", length = 50)
	private String rmGroupName;//居民分组名称
	@Column(name = "RM_SIGN_TYPE", length = 10)
	private String rmSignType;//签约类别值
	@Column(name = "RM_CREATE_ID", length = 36)
	private String rmCreateId;//创建者id

	// Constructors
	public AppResidentManage(){};


	/** minimal constructor */
	public AppResidentManage(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppResidentManage(String id, String rmGroup, String rmGroupName,
			String rmSignType,String rmCreateId) {
		this.id = id;
		this.rmGroup = rmGroup;
		this.rmGroupName = rmGroupName;
		this.rmSignType = rmSignType;
		this.rmCreateId = rmCreateId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getRmGroup() {
		return this.rmGroup;
	}

	public void setRmGroup(String rmGroup) {
		this.rmGroup = rmGroup;
	}


	public String getRmGroupName() {
		return this.rmGroupName;
	}

	public void setRmGroupName(String rmGroupName) {
		this.rmGroupName = rmGroupName;
	}


	public String getRmSignType() {
		return this.rmSignType;
	}

	public void setRmSignType(String rmSignType) {
		this.rmSignType = rmSignType;
	}


	public String getRmCreateId() {
		return this.rmCreateId;
	}

	public void setRmCreateId(String rmCreateId) {
		this.rmCreateId = rmCreateId;
	}

	public String getRmSignTypeName() throws Exception {

		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
		CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_HOUSESIGNING, this.getRmSignType());
		if(value!=null) {
            return value.getCodeTitle();
        }
		return "";

	}


	/**
	 * 创建者姓名
	 * @return
	 */
	public String getRmCreateName(){
		if(StringUtils.isNotBlank(this.getRmCreateId())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			CdUser user = (CdUser)dao.getServiceDo().find(CdUser.class,this.getRmCreateId());
			if(user!=null){
				return user.getUserName();
			}

		}
		return "";
	}

}