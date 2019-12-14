package com.ylz.bizDo.cd.po;


import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 角色菜单
 * @author admin
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="CD_ROLE_MENU")
public class CdRoleMenu extends BasePO {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	//private String mid;//主菜单
	@ManyToOne
	private CdMenu mid;//对应功能
	@ManyToOne
	private CdRole rid;//对应角色
	@OneToMany(mappedBy="rmid",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<CdRoleMenuSon> rmlist;//一对多个功能

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
	
	
	public CdRole getRid() {
		return rid;
	}
	public void setRid(CdRole rid) {
		this.rid = rid;
	}
	public List<CdRoleMenuSon> getRmlist() {
		return rmlist;
	}
	public void setRmlist(List<CdRoleMenuSon> rmlist) {
		this.rmlist = rmlist;
	}
	public CdMenu getMid() {
		return mid;
	}
	public void setMid(CdMenu mid) {
		this.mid = mid;
	}
	
}
