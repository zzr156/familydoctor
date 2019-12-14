package com.ylz.bizDo.cd.dao.impl;


import com.ylz.bizDo.cd.dao.CdDeptDao;
import com.ylz.bizDo.cd.po.CdDept;
import com.ylz.bizDo.cd.po.CdRole;
import com.ylz.bizDo.cd.vo.DeptQvo;
import com.ylz.bizDo.cd.vo.DeptSelect;
import com.ylz.bizDo.cd.vo.DeptSvo;
import com.ylz.bizDo.cd.vo.DeptVo;
import com.ylz.packaccede.allDo.SysDao;
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
import java.util.Map;
@Service("cdDeptDao")
@Transactional(rollbackForClassName={"Exception"})
public class CdDeptDaoImpl  implements CdDeptDao {
	@Autowired
	public SysDao sysDao;
	
	public List<CdDept> findAll() throws Exception {
		HQuery hq = new HQuery("from CdDept as a order by a.sort");
		List<CdDept> list = new ArrayList<CdDept>();
		list = sysDao.getServiceDo().findHquery(hq);
		return list;
	}
	
	public List<CdDept> findByState() throws Exception {
		HQuery hq = new HQuery("from CdDept as a where a.sid is null and a.state='1' order by a.sort");
		List<CdDept> list = new ArrayList<CdDept>();
		list = sysDao.getServiceDo().findHquery(hq);
		return list;
	}
	
	
	@Override
	public List<CdRole> findRole() throws Exception {
		HQuery hq = new HQuery("from CdRole");
		List<CdRole> list = new ArrayList<CdRole>();
		list = sysDao.getServiceDo().findHquery(hq);
		return list;
	}
	
	public List<CdDept> findmohu(DeptQvo qvo) throws Exception {
		HQuery hq = new HQuery("from CdDept as a where 1=1");
		if(StringUtils.isNotBlank(qvo.getDeptName())){
		   hq.addHqlSplit("and a.sname like ?").addParas("%"+qvo.getDeptName()+"%", Types.VARCHAR);
		}
		if(StringUtils.isNotBlank(qvo.getCdInstitution())){
			hq.addHqlSplit("and a.cdInstitution.id = ?").addParas(qvo.getCdInstitution(), Types.VARCHAR);
		}
		hq.addHqlSplit(" order by a.sort");
		List<CdDept> ls=sysDao.getServiceDo().findHquery(hq);
		return ls;
	}
	
	public List<CdDept> finRM(String id) throws Exception{
		  HQuery hq=new  HQuery("from CdDept as m where 1=1 ");
		  hq.addHqlSplit(" and m.sid= ? ").addParas(id, Types.VARCHAR);
		  List<CdDept> li = sysDao.getServiceDo().findHquery(hq);
	        if (li!= null) {
                return li;
            }
	        return null;
	}
	/**
	 * 查询编号是否 存在
	 * @param
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public CdDept findIsCdDept(String snumber, String id,String deptType) throws Exception{
		HQuery hql = new HQuery("from CdDept as s where 1=1");
		if (StringUtils.isNotBlank(snumber)) {
            hql.addHqlSplit(" and s.snumber = ? ").addParas(snumber, Types.VARCHAR);
        }
		if (StringUtils.isNotBlank(id)) {
            hql.addHqlSplit(" and s.id != ? ").addParas(id, Types.VARCHAR);
        }
		if (StringUtils.isNotBlank(deptType)) {
            hql.addHqlSplit(" and s.deptType = ? ").addParas(deptType, Types.VARCHAR);
        }
		List list = sysDao.getServiceDo().findHquery(hql);
		if (list != null && list.size() > 0) {
			return (CdDept) list.get(0);
		}
		return null;
	}
	
	
	public CdDept findBySnumber(String ddKhbm) throws Exception {
		return (CdDept) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
				.createCriteria(CdDept.class)
				.add(Restrictions.eq("snumber", ddKhbm))
				.uniqueResult(); 
	}
	
	/**
	 * 根据主键查询下一级部门集合(托管实体)
	 * @param deptId
	 * @return
	 */
	public List<DeptVo> findParentId(String deptId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("SID", deptId);
		String sql ="SELECT DT.SNUMBER id,DT.ID deptId,DT.SNAME text,'0' children FROM CD_DEPT DT WHERE DT.SID =:SID";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, DeptVo.class);
	}
	/**
	 * 根据主键查询下一级顶级部门集合(托管实体)
	 * @param
	 * @return
	 */
	public List<DeptVo> findParentIdTop() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="SELECT DT.SNUMBER id,DT.ID deptId,DT.SNAME text,'0' children FROM CD_DEPT DT WHERE DT.SID IS NULL";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, DeptVo.class);
	}

	@Override
	public String findMaxNum() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql=" SELECT MAX(snumber) snumber FROM CD_DEPT a WHERE 1=1";
		sql +=" AND a.sid is null";
		return (String) sysDao.getServiceDo().findSqlObject(sql,map);
	}

	public CdDept findBySId(String id) throws Exception {
		return (CdDept) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
				.createCriteria(CdDept.class)
				.add(Restrictions.eq("sid.id", id))
				.uniqueResult(); 
	}
	@Override
	public  String findsort(String sid) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String sql=" SELECT MAX(sort) sort FROM CD_DEPT a WHERE 1=1";
		if(StringUtils.isNotBlank(sid)){
			map.put("sid", sid);
			sql +=" AND a.sid =:sid";
		}
		return (String) sysDao.getServiceDo().findSqlObject(sql,map);
	}
	@Override
	public List<DeptSvo> findSByState() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="SELECT id id,rid rid,snumber snumber,deptType deptType,state state,sid sid,sname sname FROM CD_DEPT a where a.state ='1' order by a.sort";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, DeptSvo.class);
	}

	@Override
	public List<DeptSvo> findByType(String deptType) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="SELECT id id,sname sname FROM CD_DEPT a where a.state ='1'";
		if(StringUtils.isNotBlank(deptType)){
			map.put("type", deptType);
			sql+=" and a.deptType=:type";
		}
		sql+=" and a.unitType='1' order by a.sort";
		List<DeptSvo> ls= sysDao.getServiceDo().findSqlMapRVo(sql, map, DeptSvo.class);
		return ls;
	}

	@Override
	public List<DeptSvo> findById(String id) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="SELECT a.id id,a.sid sid,a.sname sname,a.deptType deptType FROM CD_DEPT a where a.state ='1'";
		if(StringUtils.isNotBlank(id)){
			map.put("id", id);
			sql+=" and a.id=:id";
		}
		sql+=" order by a.sort";
		List<DeptSvo> ls= sysDao.getServiceDo().findSqlMapRVo(sql, map, DeptSvo.class);
		return ls;
	}
	@Override
	public String findByIdTypeNum(String id) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String sql=" SELECT MAX(snumber) snumber FROM CD_DEPT a WHERE 1=1";
		if(StringUtils.isNotBlank(id)){
			map.put("sid", id);
			sql +=" AND a.id =:sid";
		}
		return (String) sysDao.getServiceDo().findSqlObject(sql,map);
	}
	public String findMaxNum(String typePid) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String sql=" SELECT MAX(snumber) snumber  FROM CD_DEPT a WHERE 1=1";
		if(StringUtils.isNotBlank(typePid)){
			map.put("sid", typePid);
			sql +=" AND a.sid =:sid";
		}else{
			sql +=" AND a.sid IS NULL";
		}
		return (String) sysDao.getServiceDo().findSqlObject(sql,map);
	}

	@Override
	public List<DeptSvo> findByCyDw() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="SELECT a.id id,a.sid sid,a.sname sname,a.deptType deptType FROM CD_DEPT a where 1=1 ";
		map.put("state","1");
		sql += " and a.state ='1' ";
		map.put("deptType", "1");
		sql +=" and a.deptType=:deptType ";
		sql+=" order by a.sort";
		List<DeptSvo> ls= sysDao.getServiceDo().findSqlMapRVo(sql, map, DeptSvo.class);
		return ls;
	}

	@Override
	public List<DeptSelect> findDeptByName(String name) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="SELECT a.id value,a.HOSP_NAME label FROM APP_HOSP_DEPT a where 1=1 and a.HOSP_NAME like:HOSP_NAME ";
		map.put("HOSP_NAME","%"+name+"%");
		List<DeptSelect> ls= sysDao.getServiceDo().findSqlMapRVo(sql, map, DeptSelect.class);
		return ls;
	}
}
