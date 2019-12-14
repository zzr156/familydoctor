package com.ylz.bizDo.cd.dao.impl;


import com.ylz.bizDo.cd.dao.CdRoleDo;
import com.ylz.bizDo.cd.entity.CdRoleMenuEntity;
import com.ylz.bizDo.cd.entity.CdRoleMenuSonEntity;
import com.ylz.bizDo.cd.po.CdRole;
import com.ylz.bizDo.cd.vo.RoleVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.hql.HQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("roleDo")
@Transactional(rollbackForClassName={"Exception"})
public class CdRoleDoImpl   implements CdRoleDo {

	@Autowired
	private SysDao sysDao;
	
	@SuppressWarnings("unchecked")
	public List<CdRole> selectAll(String userLevel) throws Exception{
		  HQuery hq=new  HQuery("from CdRole a where 1=1 ");
		  hq.addHqlSplit(" and a.state = 1  ");
		  List<CdRole> li = sysDao.getServiceDo().findHquery(hq);
	        if (li!= null){
	        	return li;
	        }
	           
	        return null;
	}
	
	public List<CdRole> findRole(RoleVo qvo) throws Exception {
		 HQuery hq=new  HQuery("from CdRole where 1=1");
		 // 分页查询
		 sysDao.getServiceDo().getNewQvo(qvo, hq);
		  List<CdRole> li =sysDao.getServiceDo().findHquery(hq);
	        if (li!= null){
	        	return li;
	        }
	       return null;
	}

	@Override
	public List<RoleVo> findRoleAll() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "SELECT id id,rname rname from CD_ROLE where state =1";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, RoleVo.class);
	}

	@Override
	public List<CdRoleMenuEntity> findRoleMenu(String id) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rid", id);
		String sql = "SELECT a.mid_id id FROM CD_ROLE_MENU a WHERE a.rid_id =:rid";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, CdRoleMenuEntity.class);
	}

	@Override
	public List<CdRoleMenuSonEntity> findRoleMenuSon(String id) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rid", id);
		String sql = "SELECT a.sid id,b.mid_id mid FROM CD_ROLE_MENU_SON a LEFT JOIN CD_ROLE_MENU b ON a.rmid_id = b.id WHERE b.rid_id =:rid";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, CdRoleMenuSonEntity.class);
	}
	
	public List<RoleVo> findRoleByDeptId(String deptId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deptId", deptId);
		String sql = "SELECT a.id id,a.rname rname FROM CD_ROLE a LEFT JOIN CD_DEPT_ROLE b ON a.id = b.rid WHERE b.id =:deptId";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, RoleVo.class);
	}
	public List<CdRole> findroleid(String id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="";
		if(id!=null&&id!=""){
			map.put("id",id);
			 sql="	select * from CD_ROLE a where a.id=:id";
		}
		List<CdRole> ls = sysDao.getServiceDo().findSqlMap(sql, map, CdRole.class);
		return ls;
	}
}
