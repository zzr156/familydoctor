package com.ylz.view.dwr.admin.po;

import com.ylz.packcommon.common.bean.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: hzk
 * Date: 12-11-12
 * Time: 上午1:46
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class Login extends BasePO{
    private String userName;
    private String userPassword;
    private String yzm;

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

	public String getYzm() {
		return yzm;
	}

	public void setYzm(String yzm) {
        this.yzm = yzm;
    }
}
