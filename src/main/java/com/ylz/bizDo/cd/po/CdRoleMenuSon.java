package com.ylz.bizDo.cd.po;


import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
/**
 * 角色菜单按钮
 * @author admin
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="CD_ROLE_MENU_SON")
public class CdRoleMenuSon extends BasePO {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	private String sid;//权限的id
	@ManyToOne
	private CdRoleMenu rmid;

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
	
	
	
	public Date getXgsj() {
		return xgsj;
	}
	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}
	public String getId() {
		return id;
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
	public void setId(String id) {
		this.id = id;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public CdRoleMenu getRmid() {
		return rmid;
	}
	public void setRmid(CdRoleMenu rmid) {
		this.rmid = rmid;
	}

	
}
