package com.ylz.bizDo.cd.po;


import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/*
 * 菜单
 */

@Entity
@Table(name="CD_MENU")
public class CdMenu extends BasePO {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//编号
	private String mname;//名称
	@ManyToOne(fetch = FetchType.LAZY)  
	@JoinColumn(name="pid")
	private CdMenu pid;//父id
	private String address;//链接地址
	private Integer onumber;//顺序号
	private int state;//状态
	private String menuIcon;//菜单图标
	
	@OneToMany( cascade = { CascadeType.ALL },fetch=FetchType.LAZY, mappedBy = "pid")  //自身关联
	@Fetch(FetchMode.SUBSELECT)  
	@OrderBy("onumber") 
	//@OrderBy(value="onumber")
	private List<CdMenu> slist;//子菜单
	
	
	@OneToMany(mappedBy="mid",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<CdMenuSon> sonlist;//一对多个功能
	
	@OneToMany(mappedBy="mid",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<CdRoleMenu> rmlist;//一对多个功能
	

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
	
	/** default constructor */
	public CdMenu() {
	}

	/** minimal constructor */
	public CdMenu(String id) {
		this.id = id;
	}
	
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
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public Integer getOnumber() {
		return onumber;
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public List<CdMenu> getSlist() {
		return slist;
	}
	public void setSlist(List<CdMenu> slist) {
		this.slist = slist;
	}
	public CdMenu getPid() {
		return pid;
	}
	public void setPid(CdMenu pid) {
		this.pid = pid;
	}
	public List<CdMenuSon> getSonlist() {
		return sonlist;
	}
	public void setSonlist(List<CdMenuSon> sonlist) {
		this.sonlist = sonlist;
	}
	public List<CdRoleMenu> getRmlist() {
		return rmlist;
	}
	public void setRmlist(List<CdRoleMenu> rmlist) {
		this.rmlist = rmlist;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setOnumber(Integer onumber) {
		this.onumber = onumber;
	}
	

    public int getMenuState()
    {
        return this.getState();
    }
    public void setMenuState(int state)
    {
        this.state = state;
    }

    public String getSonListName(){
        String names="";
        if(this.getSonlist()!=null && this.getSonlist().size()>0){
            for(CdMenuSon s:this.getSonlist()){
                if(StringUtils.isBlank(names)){
                    names =  s.getSname();
                }else{
                    names = names +","+ s.getSname();
                }
            }
        }
        return names;
    }
    public String getParnentId() {
    	if(getPid() != null){
    		return getPid().getId();
    	}
    	return "";
	}
    public String getParnentMname(){
    	if(getPid() != null){
	        if(StringUtils.isNotBlank(getPid().getId())){
	            SysDao service =  (SysDao) SpringHelper.getBean("sysDao");
	            CdMenu vo =  (CdMenu)service.getServiceDo().find(CdMenu.class,getPid().getId());
	            return vo.getMname();
	        }
    	}
        return null;
    }
	public void setParnentId(String parnentId) {
		SysDao service =  (SysDao) SpringHelper.getBean("sysDao");
		if(StringUtils.isNotBlank(parnentId)){
			this.setPid((CdMenu)service.getServiceDo().find(CdMenu.class,parnentId));
		}
	}
	//根据state获取部门状态名称
	public String getStateName()  throws Exception{
		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
		CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ENABLE, String.valueOf(this.getState()));
		if(value!=null) {
            return value.getCodeTitle();
        }
		return "";
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	
}
