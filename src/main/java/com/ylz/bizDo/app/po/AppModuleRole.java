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
 * AppModuleRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_MODULE_ROLE")
public class AppModuleRole extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "MODULE_NAME", length = 20)
	private String moduleName;//权限名称
	@Column(name = "MODULE_REMARK", length = 20)
	private String moduleRemark;//描述
	@Column(name = "MODULE_STATE", length = 10)
	private String moduleState;// 权限模块状态
	@Column(name = "MODULE_MENU_TYPE", length = 10)
	private String moduleMenuType;//权限菜单类型
	@Column(name = "MODULE_ROLE_AREA_CODE", length = 20)
	private String moduleRoleAreaCode;//所属市
	@Column(name = "MODULE_ROLE_HOSP_ID", length = 36)
	private String moduleRoleHospId;//所属医院
	@Column(name = "MODULE_ROLE_MANY_SIGN", length = 36)
	private String moduleRoleManySign;//是否开启多人签约 0单人,1多人

	// Constructors

	/** default constructor */
	public AppModuleRole() {
	}

	/** minimal constructor */
	public AppModuleRole(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppModuleRole(String id, String moduleName, String moduleRemark,
			String moduleState, String moduleMenuType ,String moduleRoleAreaCode) {
		this.id = id;
		this.moduleName = moduleName;
		this.moduleRemark = moduleRemark;
		this.moduleState = moduleState;
		this.moduleMenuType = moduleMenuType;
		this.moduleRoleAreaCode = moduleRoleAreaCode;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleRemark() {
		return this.moduleRemark;
	}

	public void setModuleRemark(String moduleRemark) {
		this.moduleRemark = moduleRemark;
	}

	public String getModuleState() {
		return this.moduleState;
	}

	public void setModuleState(String moduleState) {
		this.moduleState = moduleState;
	}

	public String getModuleMenuType() {
		return moduleMenuType;
	}

	public void setModuleMenuType(String moduleMenuType) {
		this.moduleMenuType = moduleMenuType;
	}

	public String getModuleRoleAreaCode() {
		return moduleRoleAreaCode;
	}

	public void setModuleRoleAreaCode(String moduleRoleAreaCode) {
		this.moduleRoleAreaCode = moduleRoleAreaCode;
	}

	public String getStrModuleState() throws Exception{
		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
		CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ENABLE, this.getModuleState());
		if(value!=null) {
            return value.getCodeTitle();
        }
		return "";
	}

	public String getStrModuleType() throws Exception{
		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
		CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_APP_MENU_TYPE, this.getModuleMenuType());
		if(value!=null) {
            return value.getCodeTitle();
        }
		return "";
	}

	public String getStrModuleAreaCode(){
			if(StringUtils.isNotBlank(this.getModuleRoleAreaCode())){
				SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
				CdAddress p = (CdAddress)dao.getServiceDo().find(CdAddress.class, this.getModuleRoleAreaCode());
				if(p != null) {
                    return p.getAreaSname();
                }
			}
			if(StringUtils.isNotBlank(this.getModuleRoleHospId())){
				SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
				AppHospDept p = (AppHospDept)dao.getServiceDo().find(AppHospDept.class, this.getModuleRoleHospId());
				if(p != null) {
                    return p.getHospName();
                }
			}


			return "";
	}

	public String getModuleType(){
		if(StringUtils.isNotBlank(this.getModuleRoleAreaCode())){
				return "1";
		}
		if(StringUtils.isNotBlank(this.getModuleRoleHospId())){

				return"2";
		}
		return  "";
	}
	public String getModuleRoleHospId() {
		return moduleRoleHospId;
	}

	public void setModuleRoleHospId(String moduleRoleHospId) {
		this.moduleRoleHospId = moduleRoleHospId;
	}

	public String getDrHospName(){
		if(StringUtils.isNotBlank(this.getModuleRoleHospId())) {
			SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
			AppHospDept p = (AppHospDept) dao.getServiceDo().find(AppHospDept.class, this.getModuleRoleHospId());
			if (p != null) {
                return p.getHospName();
            }
		}
		return  "";
	}

	public String getModuleRoleManySign() {
		return moduleRoleManySign;
	}

	public void setModuleRoleManySign(String moduleRoleManySign) {
		this.moduleRoleManySign = moduleRoleManySign;
	}
}