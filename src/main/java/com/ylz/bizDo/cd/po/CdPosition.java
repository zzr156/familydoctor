package com.ylz.bizDo.cd.po;


import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 职位管理
 * @author admin
 *
 */
@Entity
@Table(name="CD_POSITION")
public class CdPosition extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
	private String num;//编号
	private String account;//描述
//	多对多
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinTable(name="CD_USER_POSITION",inverseJoinColumns={@JoinColumn(name="userId")},joinColumns={@JoinColumn(name="positionId")})
	private List<CdUser> user = new ArrayList<CdUser>();
	

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
	public List<CdUser> getUser() {
		return user;
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
	public void setUser(List<CdUser> user) {
		this.user = user;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
}
