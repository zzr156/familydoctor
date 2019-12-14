package com.ylz.bizDo.cd.vo;



import com.ylz.packcommon.common.CommConditionVo;

public class UserVo extends CommConditionVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String account;//账号
	private String userName;//用户名
    private String userNum;//编码
	private String deptId;//部门
	private String workState;//在职状态
	private String jg;//机构
	private String userLevel;//查询
	private String userTop;//顶级
	private String userUpId;
	private String userState;
	private String userId;//
    private String startDate;
    private String endDate;

	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getWorkState() {
		return workState;
	}
	public void setWorkState(String workState) {
		this.workState = workState;
	}
	public String getJg() {
		return jg;
	}
	public void setJg(String jg) {
		this.jg = jg;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserTop() {
		return userTop;
	}

	public void setUserTop(String userTop) {
		this.userTop = userTop;
	}

	public String getUserUpId() {
		return userUpId;
	}

	public void setUserUpId(String userUpId) {
		this.userUpId = userUpId;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
