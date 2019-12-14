package com.ylz.bizDo.cd.dao.impl;


import com.ylz.bizDo.cd.dao.CdUserDo;
import com.ylz.bizDo.cd.po.CdDept;
import com.ylz.bizDo.cd.po.CdPosition;
import com.ylz.bizDo.cd.po.CdRole;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.cd.vo.CdUserJsonVo;
import com.ylz.bizDo.cd.vo.UserVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.hql.HQuery;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("userDo")
@Transactional(rollbackForClassName={"Exception"})
public class CdUserDoImpl   implements CdUserDo {

	@Autowired
	private SysDao sysDao;

	/**
	 * 按账号,用户名,部门,在职状态分别查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CdUser> findH(UserVo qvo) throws Exception {

		HQuery hql = new HQuery("from CdUser a where 1=1");
		if (StringUtils.isNotBlank(qvo.getAccount())) {
			hql.addHqlSplit(" and a.account like ? ").addParas(
					"%" + qvo.getAccount() + "%", Types.VARCHAR);
		}

		if (StringUtils.isNotBlank(qvo.getUserName())) {
			hql.addHqlSplit(" and a.userName like ? ").addParas(
					"%" + qvo.getUserName() + "%", Types.VARCHAR);
		}

		if (StringUtils.isNotBlank(qvo.getDeptId())) {
			hql.addHqlSplit(" and (a.cdDept.id = ? or a.cdDept.sid.id =?)").addParas(
					qvo.getDeptId(), Types.VARCHAR).addParas(qvo.getDeptId(), Types.VARCHAR);
		}

		if (StringUtils.isNotBlank(qvo.getWorkState())) {
			hql.addHqlSplit(" and a.workState = ? ").addParas(qvo.getWorkState(), Types.VARCHAR);
		}
		
		hql.addHqlSplit(" order by a.userSort");
		// 分页查询
		sysDao.getServiceDo().getNewQvo(qvo, hql);
		//
		List<CdUser> ls = sysDao.getServiceDo().findHquery(hql);
		return ls;
	}

	/**
	 * 根据部门查询用户
	 * @param deptId
	 * @return
	 * @throws DaoException
	 */
	public List<CdUser> findNotH(String deptId) throws Exception {

		HQuery hql = new HQuery("from CdUser a where 1=1");

		if (StringUtils.isNotBlank(deptId)) {
			hql.addHqlSplit(" and (a.cdDept.id = ? or a.cdDept.sid.id =?)").addParas(
					deptId, Types.VARCHAR).addParas(deptId, Types.VARCHAR);
		}

		hql.addHqlSplit(" order by a.userSort");
		// 分页查询
		List<CdUser> ls = sysDao.getServiceDo().findHquery(hql);
		return ls;
	}
	// 用户登录
	@Override
	public CdUser findUser(String userName, String userPassword)
			throws Exception {
		String hql = "from CdUser as u where u.account = ? and u.userPassword = ?";

		HQuery query = new HQuery(hql);
		query.addParas(userName, Types.VARCHAR).addParas(userPassword,
				Types.VARCHAR);
		List list = sysDao.getServiceDo().findHquery(query);
		if (list != null && list.size() > 0) {
			return (CdUser) list.get(0);
		}
		return null;
	}
	public CdUser findUserOrgid(String userName, String orgid) throws Exception{
		String hql = "from CdUser as u where u.account = ? and u.hospId = ?";
		HQuery query = new HQuery(hql);
		query.addParas(userName, Types.VARCHAR).addParas(orgid, Types.VARCHAR);
		List list = sysDao.getServiceDo().findHquery(query);
		if (list != null && list.size() > 0) {
			return (CdUser) list.get(0);
		}
		return null;
	}


	// 删除用户
	public void delete(String uid) throws Exception {
		sysDao.getServiceDo().delete(CdUser.class,uid);
	}

	// 查询指定用户
	public CdUser findUser(String userId) throws Exception {
		// Session
		// session=this.getBaseDao().getSessionFactory().getCurrentSession();
		String hql = "from CdUser as u where u.userId = ? ";
		// Query query=(Query)
		// this.getBaseDao().getSessionFactory().getCurrentSession().
		// createQuery(hql).setParameter(0, account);

		// List list=session.createQuery(hql).setParameter(0, pk).list();

		HQuery query = new HQuery(hql);
		query.addParas(userId, Types.VARCHAR);
		List list = sysDao.getServiceDo().findHquery(query);

		if (list != null && list.size() > 0) {
			return (CdUser) list.get(0);
		}
		return null;
	}

	// 查询所有部门名称
	@SuppressWarnings("unchecked")
	public List<CdDept> findAllCdDept() throws Exception {
		String hql = "from CdDept";
		HQuery query = new HQuery(hql);
		return (List<CdDept>) sysDao.getServiceDo().findHquery(query);
	}

	// 查询所有职位名称
	@SuppressWarnings("unchecked")
	public List<CdPosition> findAllPosition() throws Exception {
		String hql = "from CdPosition";
		HQuery query = new HQuery(hql);
		return (List<CdPosition>) sysDao.getServiceDo().findHquery(query);
	}

	// 查询所有角色名称
	@SuppressWarnings("unchecked")
	public List<CdRole> findAllRole() throws Exception {
		String hql = "from CdRole";
		HQuery query = new HQuery(hql);
		return (List<CdRole>) sysDao.getServiceDo().findHquery(query);
	}
	
	/**
	 * 查询用户名是否 存在
	 * @param userName
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public CdUser findIsUser(String userName, String id)throws Exception {
		String hql = "from CdUser as u where u.userName = ? and u.userId != ?";

		HQuery query = new HQuery(hql);
		query.addParas(userName, Types.VARCHAR).addParas(id,Types.VARCHAR);
		List list = sysDao.getServiceDo().findHquery(query);
		if (list != null && list.size() > 0) {
			return (CdUser) list.get(0);
		}
		return null;
	}
	
	public List<CdUser> findUserAll() throws Exception {
		HQuery hql = new HQuery("from CdUser a where 1=1");
		hql.addHqlSplit(" order by a.cdDept.deptType,a.cdDept.snumber, a.userNum");
		List<CdUser> ls = sysDao.getServiceDo().findHquery(hql);
		return ls;
	}

	public List<CdUserJsonVo> findCdUserJson(String deptId) throws Exception {
		HashMap map=new HashMap();
		map.put("cdDept_id",deptId);
		StringBuffer sql =new StringBuffer("SELECT a.userId id,a.userName text from CD_USER a where a.cdDept_id=:cdDept_id ");
		List<CdUserJsonVo> ls = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map,CdUserJsonVo.class);
		if(ls!=null) {
            return ls;
        }
		return null;
	}
	
	public CdUser findByUserName(String account) throws Exception{
		return (CdUser)sysDao.getServiceDo().getSessionFactory()
				.getCurrentSession().createCriteria(CdUser.class)
				.add(Restrictions.eq("account", account))
				.uniqueResult();
	}

	@Override
	public String[] getRoleIdd(String areaCode, String drRole, String roleStr) throws Exception {
		String[] roleId = new String[]{};
		if(org.apache.commons.lang3.StringUtils.isBlank(roleStr)){
			if (areaCode.length() == 4) {
				roleId = new String[]{AppRoleType.SHI.getValue()};
			} else {
				if(org.apache.commons.lang3.StringUtils.isNotBlank(drRole)){
					if(drRole.indexOf(AppRoleType.QU.getValue())!=-1 || drRole.indexOf(AppRoleType.SHEQU.getValue())!=-1){
						roleId = new String[]{AppRoleType.SHEQU.getValue(),AppRoleType.JDLK.getValue()};
					}else{
						roleId = new String[]{AppRoleType.SHEQU.getValue()};
					}
				}else{
					roleId = new String[]{AppRoleType.SHEQU.getValue()};
				}
			}
		}else{
			if (areaCode.length() == 4) {
				if(roleStr.indexOf(AppRoleType.SHI.getValue())==-1){
					roleId = new String[]{AppRoleType.SHI.getValue()};
				}
			} else {
				if(org.apache.commons.lang3.StringUtils.isNotBlank(drRole)){
					if(drRole.indexOf(AppRoleType.QU.getValue())!=-1 || drRole.indexOf(AppRoleType.SHEQU.getValue())!=-1){
						if(roleStr.indexOf(AppRoleType.SHEQU.getValue())==-1 && roleStr.indexOf(AppRoleType.JDLK.getValue())==-1 ){
							roleId = new String[]{AppRoleType.SHEQU.getValue(),AppRoleType.JDLK.getValue()};
						}else if(roleStr.indexOf(AppRoleType.SHEQU.getValue())==-1){
							roleId = new String[]{AppRoleType.SHEQU.getValue()};
						}else if(roleStr.indexOf(AppRoleType.JDLK.getValue())==-1){
							roleId = new String[]{AppRoleType.JDLK.getValue()};
						}
					}
				}else{
					if(roleStr.indexOf(AppRoleType.SHEQU.getValue())==-1){
						roleId = new String[]{AppRoleType.SHEQU.getValue()};
					}
				}
			}
		}
		return roleId;
	}

	@Override
	public List<CdRole> getRoleList(String areaCode, String drRole) throws Exception {
		List<CdRole> list = new ArrayList<>();
		if (areaCode.length() == 4) {
			CdRole role = (CdRole) sysDao.getServiceDo().find(CdRole.class,AppRoleType.SHI.getValue());
			list.add(role);
		} else {
			if(org.apache.commons.lang3.StringUtils.isNotBlank(drRole)){
				if(drRole.indexOf(AppRoleType.QU.getValue())!=-1 || drRole.indexOf(AppRoleType.SHEQU.getValue())!=-1){
					CdRole roleO = (CdRole) sysDao.getServiceDo().find(CdRole.class,AppRoleType.SHEQU.getValue());
					list.add(roleO);
					CdRole roleT = (CdRole) sysDao.getServiceDo().find(CdRole.class,AppRoleType.JDLK.getValue());
					list.add(roleT);
				}else{
					CdRole roleO = (CdRole) sysDao.getServiceDo().find(CdRole.class,AppRoleType.SHEQU.getValue());
					list.add(roleO);
				}
			}else{
				CdRole roleO = (CdRole) sysDao.getServiceDo().find(CdRole.class,AppRoleType.SHEQU.getValue());
				list.add(roleO);
			}
		}
		return list;
	}

	@Override
	public CdUser findUserByAccount(String userName) throws Exception {
		String hql = "from CdUser as u where u.account = ? ";

		HQuery query = new HQuery(hql);
		query.addParas(userName, Types.VARCHAR);
		List list = sysDao.getServiceDo().findHquery(query);
		if (list != null && list.size() > 0) {
			return (CdUser) list.get(0);
		}
		return null;
	}

	@Override
	public List<CdUser> findUserByAccountDrId(String account, String drId,String hospId) throws Exception {
		HQuery hql = new HQuery("from CdUser a where 1=1  ");
		hql.addHqlSplit(" and a.account = ? AND a.drId =? AND a.hospId =?").addParas(
				account, Types.VARCHAR).addParas(drId, Types.VARCHAR).addParas(hospId,Types.VARCHAR);
		List<CdUser> ls = sysDao.getServiceDo().findHquery(hql);
		return ls;
	}
}
