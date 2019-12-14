package com.ylz.bizDo.cd.po;


import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 用户角色
 * @author admin
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="CD_ROLE")
public class CdRole extends BasePO {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//id
	private String rname;//角色名称
	private String remark;//描述
	private Integer state;//状态
	@OneToMany(mappedBy="rid",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<CdRoleMenu> rmlist;//对多个角色主菜单
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinTable(name="CD_DEPT_ROLE",
	joinColumns={@JoinColumn(name="rid")},
	inverseJoinColumns={@JoinColumn(name="id")})
	private List<CdDept> cdDepts;

	@Column(name = "cjrId",length = 36)
	private String cjrId;//创建人ID
	@Column(name = "cjdwId",length = 36)
	private String cjdwId;//创建单位ID
	@Column(name = "cjsj")
	private Date cjsj;//创建时间
	@Column(name = "xgrId",length = 36)
	private String xgrId;//修改人ID
	@Column(name = "xgdwId",length = 36)
	private String xgdwId;//修改单位ID
	@Column(name = "xgsj")
	private Date xgsj;//修改时间
	@Column(name = "rnum")
	private String rnum;//角色编码
	
	public Date getXgsj() {
		return xgsj;
	}
	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}
	public List<CdDept> getCdDepts() {
		return cdDepts;
	}
	public String getCjrId() {
		return cjrId;
	}
	public void setCjrId(String cjrId) {
		this.cjrId = cjrId;
	}
	public String getCjdwId() {
		return cjdwId;
	}
	public void setCjdwId(String cjdwId) {
		this.cjdwId = cjdwId;
	}
	public Date getCjsj() {
		return cjsj;
	}
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	public String getXgrId() {
		return xgrId;
	}
	public void setXgrId(String xgrId) {
		this.xgrId = xgrId;
	}
	public String getXgdwId() {
		return xgdwId;
	}
	public void setXgdwId(String xgdwId) {
		this.xgdwId = xgdwId;
	}
	public void setCdDepts(List<CdDept> cdDepts) {
		this.cdDepts = cdDepts;
	}
	//	多对多
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinTable(name="cd_user_role",inverseJoinColumns={@JoinColumn(name="userId")},joinColumns={@JoinColumn(name="roleId")})
	private List<CdUser> user = new ArrayList<CdUser>();
	
	public List<CdUser> getUser() {
		return user;
	}
	public void setUser(List<CdUser> user) {
		this.user = user;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public List<CdRoleMenu> getRmlist() {
		return rmlist;
	}
	public void setRmlist(List<CdRoleMenu> rmlist) {
		this.rmlist = rmlist;
	}
	
	public Integer getRoleState() {
		return this.getState();
	}

	public void setRoleState(Integer state) {
		this.state = state;
	}
	
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}

	//根据state获取部门状态名称
	public String getStateName() throws Exception{
		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
		CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ENABLE, String.valueOf(this.getState()));
		if(value!=null) {
            return value.getCodeTitle();
        }
		return "";
	}
}
