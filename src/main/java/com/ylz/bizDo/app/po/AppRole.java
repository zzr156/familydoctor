package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**手机权限表
 * AppRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_ROLE")
public class AppRole extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "ROLE_NAME", length = 20)
	private String roleName;//权限名称
	@Column(name = "ROLE_VALUE", length = 10)
	private String roleValue;//权限值
	@Column(name = "ROLE_STATE", length = 10)
	private String roleState;//状态

	// Constructors

	/** default constructor */
	public AppRole() {
	}

	/** minimal constructor */
	public AppRole(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppRole(String id, String roleName, String roleValue,String roleState) {
		this.id = id;
		this.roleName = roleName;
		this.roleValue = roleValue;
		this.roleState = roleState;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public String getRoleValue() {
		return this.roleValue;
	}

	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}

	public String getRoleState() {
		return roleState;
	}

	public void setRoleState(String roleState) {
		this.roleState = roleState;
	}

	public String getAppRoleStateName() throws Exception{
		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
		CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ENABLE, this.getRoleState());
		if(value!=null) {
            return value.getCodeTitle();
        }
		return "";
	}
}