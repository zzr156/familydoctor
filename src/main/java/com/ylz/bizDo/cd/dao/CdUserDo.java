package com.ylz.bizDo.cd.dao;


import com.ylz.bizDo.cd.po.CdDept;
import com.ylz.bizDo.cd.po.CdPosition;
import com.ylz.bizDo.cd.po.CdRole;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.cd.vo.CdUserJsonVo;
import com.ylz.bizDo.cd.vo.UserVo;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.List;

public interface CdUserDo  {
	public List<CdUser> findH(UserVo qvo) throws Exception;
	public List<CdUser> findNotH(String deptId) throws Exception;
	/**
	 * 用户登录,根据用户名和密码查询用户对象
	 * @param UserLoginVo
	 *        qvo
	 * @return
	 */
	public CdUser findUser(String userName, String userPassword) throws Exception;
	public CdUser findUserOrgid(String userName, String orgid) throws Exception;
	/**
	 * 查询指定用户
	 * @param account
	 * @return
	 * @throws DaoException
	 */
	public CdUser findUser(String userId) throws Exception;
	public CdUser findByUserName(String account) throws Exception;
	
	public List<CdDept> findAllCdDept() throws Exception;
	
	public List<CdPosition> findAllPosition() throws Exception;
	
	public List<CdRole> findAllRole() throws Exception;
	public CdUser findIsUser(String userName, String id)throws Exception;
	/**
	 * 根据部门查询用户
	 * @param
	 * @return
	 */
	public List<CdUser> findUserAll() throws Exception;
	//根据部门查询用户
	public List<CdUserJsonVo> findCdUserJson(String deptId) throws Exception;

	public String[] getRoleIdd(String areaCode,String drRole,String roleStr) throws Exception;

	public List<CdRole> getRoleList(String areaCode,String drRole) throws Exception;

	public CdUser findUserByAccount(String userName) throws Exception;

	/**
	 * 根据账号和app_dr_user表中的医生主键查询cd_user表中账号姓名
	 * @param account
	 * @param drId
	 * @return
	 * @throws Exception
	 */
	public List<CdUser> findUserByAccountDrId(String account,String drId,String hospId) throws Exception;
}
