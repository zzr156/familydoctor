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
 *  服务设置表权限表
 */
@Entity
@Table(name = "APP_SERVE_ROLE")
public class AppServeRole extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "SERVE_NAME", length = 20)
	private String serveName;//权限名称
	@Column(name = "SERVE_STATE", length = 10)
	private String serveState;// 权限模块状态
	@Column(name = "SERVE_ROLE_AREA_CODE", length = 20)
	private String serveRoleAreaCode;//所属市
	@Column(name = "SERVE_ROLE_HOSP_ID", length = 36)
	private String serveRoleHospId;//所属医院

	// Constructors

	/** default constructor */
	public AppServeRole() {
	}

	/** minimal constructor */
	public AppServeRole(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppServeRole(String id, String serveName, String serveState,
						String serveRoleAreaCode, String serveRoleHospId) {
		this.id = id;
		this.serveName = serveName;
		this.serveState = serveState;
		this.serveRoleAreaCode = serveRoleAreaCode;
		this.serveRoleHospId = serveRoleHospId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServeName() {
		return serveName;
	}

	public void setServeName(String serveName) {
		this.serveName = serveName;
	}

	public String getServeState() {
		return serveState;
	}

	public void setServeState(String serveState) {
		this.serveState = serveState;
	}

	public String getServeRoleAreaCode() {
		return serveRoleAreaCode;
	}

	public void setServeRoleAreaCode(String serveRoleAreaCode) {
		this.serveRoleAreaCode = serveRoleAreaCode;
	}

	public String getServeRoleHospId() {
		return serveRoleHospId;
	}

	public void setServeRoleHospId(String serveRoleHospId) {
		this.serveRoleHospId = serveRoleHospId;
	}

	public String getStrServeState() throws Exception{
		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
		CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ENABLE, this.getServeState());
		if(value!=null) {
            return value.getCodeTitle();
        }
		return "";
	}


	public String getStrServeAreaCode(){
			if(StringUtils.isNotBlank(this.getServeRoleAreaCode())){
				SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
				CdAddress p = (CdAddress)dao.getServiceDo().find(CdAddress.class, this.getServeRoleAreaCode());
				if(p != null) {
                    return p.getAreaSname();
                }
			}
			if(StringUtils.isNotBlank(this.getServeRoleHospId())){
				SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
				AppHospDept p = (AppHospDept)dao.getServiceDo().find(AppHospDept.class, this.getServeRoleHospId());
				if(p != null) {
                    return p.getHospName();
                }
			}


			return "";
	}

	public String getserveType(){
		if(StringUtils.isNotBlank(this.getServeRoleAreaCode())){
				return "1";
		}
		if(StringUtils.isNotBlank(this.getServeRoleHospId())){

				return"2";
		}
		return  "";
	}

	public String getDrHospName(){
		if(StringUtils.isNotBlank(this.getServeRoleHospId())) {
			SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
			AppHospDept p = (AppHospDept) dao.getServiceDo().find(AppHospDept.class, this.getServeRoleHospId());
			if (p != null) {
                return p.getHospName();
            }
		}
		return  "";
	}

}