package com.ylz.bizDo.cd.po;


import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户信息
 * @author 513-02
 *
 */

@Entity
@Table(name="CD_USER")
public class CdUser extends BasePO implements Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2495523922350192026L;

    @Id
    @Column(name = "userId", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String userId;//ID
	@Column(nullable=true)
	private String account;//
	@Column(nullable=true)
	private String userName;//
	@Column(nullable=true)
	private String userPassword;//密码
	@Column(nullable=true)
	private String userSignaturePwd;//签名密码
	@Column(nullable=true)
	private String userSex;//性别
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="CD_USER_POSITION",inverseJoinColumns={@JoinColumn(name="positionId")},joinColumns={@JoinColumn(name="userId")})
	private List<CdPosition> userPost = new ArrayList<CdPosition>();//职位
	

	@ManyToOne
	private CdDept cdDept;//部门
	
	@Column(nullable=true)
	private String workState;//在职状态
	@Column
	private String cardNum;//卡号
	@Column
	private String mobilePhone;//手机
	@Column
	private String homeTelephone;//家庭电话
	@Column
	private String officePhone;//办公电话
	@Column
	private String email;//电子邮件
	@Column
	private String userSort;//排序
	@Column    
    private String userFileName;//附件名称    
	@Column
    private String userFilePath;//附件路径
    
	private Calendar modifyDate;//操作时间
		
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinTable(name="CD_USER_ROLE",inverseJoinColumns={@JoinColumn(name="roleId")},joinColumns={@JoinColumn(name="userId")})
	private List<CdRole> role = new ArrayList<CdRole>();//角色
	

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
	@Column(name = "userNum")
	private String userNum;//编码
    @Column(name = "userIdCard")
    private String userIdCard;//身份证
	@Column(name = "hospId",length = 36)
	private String hospId;
	@Column(name = "drId",length = 36)
	private String drId;//医生id
    
    public Date getXgsj() {
		return xgsj;
	}

	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}

	public List<CdPosition> getUserPost() {
		return userPost;
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

	public void setUserPost(List<CdPosition> userPost) {
		this.userPost = userPost;
	}

	public List<CdRole> getRole() {
		return role;
	}

	public void setRole(List<CdRole> role) {
		this.role = role;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserSignaturePwd() {
		return userSignaturePwd;
	}

	public void setUserSignaturePwd(String userSignaturePwd) {
		this.userSignaturePwd = userSignaturePwd;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public CdDept getCdDept() {
		return cdDept;
	}

	public void setCdDept(CdDept cdDept) {
		this.cdDept = cdDept;
	}

	public String getWorkState() {
		return workState;
	}

	public void setWorkState(String workState) {
		this.workState = workState;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getHomeTelephone() {
		return homeTelephone;
	}

	public void setHomeTelephone(String homeTelephone) {
		this.homeTelephone = homeTelephone;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserSort() {
		return userSort;
	}

	public void setUserSort(String userSort) {
		this.userSort = userSort;
	}
		
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Calendar getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Calendar modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public String getUserFileName() {
		return userFileName;
	}

	public void setUserFileName(String userFileName) {
		this.userFileName = userFileName;
	}

	public String getUserFilePath() {
		return userFilePath;
	}

	public void setUserFilePath(String userFilePath) {
		this.userFilePath = userFilePath;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getHospId() {
		return hospId;
	}

	public void setHospId(String hospId) {
		this.hospId = hospId;
	}

	public String getDrId() {
		return drId;
	}

	public void setDrId(String drId) {
		this.drId = drId;
	}

	public String getUserPostname() {
		String names = "";
		if (this.getUserPost() != null && this.getUserPost().size() > 0) {
			for (CdPosition p : this.getUserPost()) {
				if (StringUtils.isBlank(names)) {
					names = p.getAccount();
				} else {
					names = names + "," + p.getAccount();
				}

			}
		}
		return names;
	}
	 
	public String getCdDeptUnit(){
//		if(this.getCdDept() != null){
//			if(this.getCdDept().getSid() != null){
//				if(StringUtils.isNotBlank(this.getCdDept().getSid().getId())){
//					SysDao service = (SysDao) SpringHelper.getBean("sysDao");
//					CdDept dept = service.getCdDeptDao().find(this.getCdDept().getSid().getId());
//					return dept.getSname();
//				}
//			}
//		}
		SysDao service = (SysDao) SpringHelper.getBean("sysDao");
		if(this.getBaseOrgid("0")!=null) {
			CdDept dept = (CdDept)service.getServiceDo().find(CdDept.class,this.getBaseOrgid("0"));
			return dept.getSname();
		}
		return "";
	}
	public String getCdDeptUnitId(){
		SysDao service = (SysDao) SpringHelper.getBean("sysDao");
		if(this.getBaseOrgid("0")!=null) {
			CdDept dept = (CdDept)service.getServiceDo().find(CdDept.class,this.getBaseOrgid("0"));
			return dept.getId();
		}
		return "";
	}
	public String getDeptName(){
//		SysDao service = (SysDao) SpringHelper.getBean("sysDao");
//		if(this.getBaseOrgid("0")!=null) {
//			CdDept dept = (CdDept)service.getServiceDo().find(CdDept.class,this.getBaseOrgid("0"));
//			return dept.getSname();
//		}else if(this.getBaseOrgid("1")!=null) {
//			CdDept dept = (CdDept)service.getServiceDo().find(CdDept.class,this.getBaseOrgid("1"));
//			return dept.getSname();
//		}
		return this.getCdDept().getSname();
	}
	
	public String getCdDeptId() {
		if (this.getCdDept() != null) {
            return this.getCdDept().getId();
        }
		return "";
	}

	// 多对一
	public void setCdDpetId(String id) {
		SysDao service = (SysDao) SpringHelper.getBean("sysDao");
		if (id != null) {
            this.setCdDept((CdDept)service.getServiceDo().find(CdDept.class,id));
        }
	}

	public String getCdDeptName() {
		SysDao service = (SysDao) SpringHelper.getBean("sysDao");
		if(this.getBaseOrgid("1")!=null) {
			CdDept dept = (CdDept)service.getServiceDo().find(CdDept.class,this.getBaseOrgid("1"));
			return dept.getSname();
		}
		return "";
	}

	public String[] getRoleid() {
		if (this.getRole() != null && this.getRole().size() > 0) {
			String[] ss = new String[this.getRole().size()];
			int i = 0;
			for (CdRole r : this.getRole()) {
				ss[i] = r.getId();
				i++;
			}
			return ss;
		}
		return null;
	}

	// 一对多
	public void setRoleid(String[] roleid) {
		SysDao service = (SysDao) SpringHelper.getBean("sysDao");
		if (roleid != null) {
			for (String i : roleid) {
				this.getRole().add((CdRole)service.getServiceDo().find(CdRole.class,i));
			}
		}
	}

	public String[] getPostid() {
		if (this.getUserPost() != null && this.getUserPost().size() > 0) {
			String[] ss = new String[this.getUserPost().size()];
			int i = 0;
			for (CdPosition r : this.getUserPost()) {
				ss[i] = r.getId();
				i++;
			}
			return ss;
		}
		return null;
	}

	// 一对多
	public void setPostid(String[] postid) {
		SysDao service = (SysDao) SpringHelper.getBean("sysDao");
		if (postid != null) {
			for (String i : postid) {
				this.getUserPost().add((CdPosition)service.getServiceDo().find(CdPosition.class,i));
			}
		}
	}
	//根据workState在职状态名称
	public String getWorkStateName() throws Exception{
		if(this.getWorkState() != null){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_WORKSTATE, this.getWorkState());
			if(value!=null) {
                return value.getCodeTitle();
            }
		}
		return "";
	}   
	
	//根据workState在职状态名称
		public String getUserSexName() throws Exception{
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			if(this.getUserSex() != null){
				CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CODESEX, this.getUserSex());
				if(value!=null) {
                    return value.getCodeTitle();
                }
			}
			
			return "";
		}

	//取单位id
	public String getBaseOrgid(String type){
		if(this.getCdDept() != null) {
			if (this.getCdDept().getDeptType().equals(type)){
				return this.getCdDept().getId();
			}else{
				return orgid(this.getCdDept(),type);
			}
		}
		return "";
	}

	//死循环取单位id
	private String orgid(CdDept dept,String type)
	{
		if(dept.getSid()!=null) {
			if (dept.getSid().getDeptType().equals(type)) {
				return dept.getSid().getId();
			} else {
				return orgid(dept.getSid(),type);
			}
		}
		return null;
	}


	/**克隆*/
	public CdUser clone(){
		CdUser o = null;
		try {
			o = (CdUser) super.clone();
			return o;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}


	public String getUserIdCard() {
		return userIdCard;
	}

	public void setUserIdCard(String userIdCard) {
		this.userIdCard = userIdCard;
	}
    
    
}
