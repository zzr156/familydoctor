package com.ylz.bizDo.cd.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 用户拖建表
 * @author dws
 *
 */
@Entity
@Table(name = "CD_USER_XXX")
public class CdUserXxx extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2495523922350192026L;
	// Fields

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "USER_ID", length = 36)
	private String userId;
	@Column(name = "USER_ACCOUNT", length = 20)
	private String userAccount;
	@Column(name = "USER_PASS_RANDOM", length = 50)
	private String userPassRandom;
	@Column(name = "USER_KEY_PATH", length = 100)
	private String userKeyPath;
	@Column(name = "CJSJ")
	private Calendar cjsj;
	@Column(name = "USER_KEY_VAILDITYDATE")
	private Calendar userKeyVailditydate;

	// Constructors

	/** default constructor */
	public CdUserXxx() {
	}

	/** minimal constructor */
	public CdUserXxx(String id) {
		this.id = id;
	}

	/** full constructor */
	public CdUserXxx(String id, String userId, String userAccount,
			String userPassRandom, String userKeyPath, Calendar cjsj,
			Calendar userKeyVailditydate) {
		this.id = id;
		this.userId = userId;
		this.userAccount = userAccount;
		this.userPassRandom = userPassRandom;
		this.userKeyPath = userKeyPath;
		this.cjsj = cjsj;
		this.userKeyVailditydate = userKeyVailditydate;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getUserPassRandom() {
		return this.userPassRandom;
	}

	public void setUserPassRandom(String userPassRandom) {
		this.userPassRandom = userPassRandom;
	}
	
	public String getUserKeyPath() {
		return this.userKeyPath;
	}

	public void setUserKeyPath(String userKeyPath) {
		this.userKeyPath = userKeyPath;
	}

	public Calendar getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(Calendar cjsj) {
		this.cjsj = cjsj;
	}

	public Calendar getUserKeyVailditydate() {
		return this.userKeyVailditydate;
	}

	public void setUserKeyVailditydate(Calendar userKeyVailditydate) {
		this.userKeyVailditydate = userKeyVailditydate;
	}

}