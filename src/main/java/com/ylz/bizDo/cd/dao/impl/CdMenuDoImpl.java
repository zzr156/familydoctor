package com.ylz.bizDo.cd.dao.impl;


import com.ylz.bizDo.cd.dao.CdMenuDo;
import com.ylz.bizDo.cd.entity.CdMenuEntity;
import com.ylz.bizDo.cd.po.CdMenu;
import com.ylz.bizDo.cd.vo.MenuOneVo;
import com.ylz.bizDo.cd.vo.MenuSvo;
import com.ylz.bizDo.cd.vo.MenuTwoVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.hql.HQuery;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("menuDo")
@Transactional(rollbackForClassName={"Exception"})
public class CdMenuDoImpl  implements CdMenuDo {
	@Autowired
	private SysDao sysDao;
	
	@Override
	public List<CdMenu> selectAll() throws Exception{
		  HQuery hq=new  HQuery("from CdMenu as m order by m.onumber");
		  return sysDao.getServiceDo().findHquery(hq);
	}
	public List<CdMenu> findStateTrue() throws Exception{
		 HQuery hq=new  HQuery("from CdMenu as m where m.state='1' order by m.onumber");
		 return sysDao.getServiceDo().findHquery(hq);
	}
	public List<CdMenu> finRM(String id) throws Exception{
		  HQuery hq=new  HQuery("from CdMenu as m where 1=1 ");
		  hq.addHqlSplit(" and m.pid= ? ").addParas(id, Types.VARCHAR);
		  return sysDao.getServiceDo().findHquery(hq);
	}

	@Override
	public List<CdMenu> findRoId(String[] rid) throws Exception {
		HashMap map=new HashMap();
		map.put("rid", rid);
		String sql="SELECT * FROM CD_MENU M WHERE M.ID IN (" +
				" SELECT a.MID_ID"+
				" FROM"+
				" 	CD_ROLE_MENU A"+
				" WHERE"+
				" 	1 = 1"+
				" AND A .RID_ID IN (:rid)"+
			" GROUP BY A.MID_ID) ORDER BY M.ONUMBER";
		List<CdMenu> findSqlMap = sysDao.getServiceDo().findSqlMap(sql, map,CdMenu.class);
		return findSqlMap;
	}
	
	public List<CdMenu> top() throws Exception{
        String sql="SELECT * FROM CD_MENU a where a.pid is null ORDER BY a.onumber";
        List<CdMenu> li =sysDao.getServiceDo().findSqlList(sql,CdMenu.class);
        if (li!= null) {
            return li;
        }
        return null;
    }
	@Override
	public List<CdMenuEntity> findMenuList() throws Exception {
		Map map = new HashMap();
		map.put("STATE", "1");
		String sql ="SELECT a.id id,a.pid pid ,a.mname mname FROM CD_MENU a WHERE a.STATE =:STATE ORDER BY a.PID,a.ONUMBER";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, CdMenuEntity.class);
	}
	@Override
	public List<MenuSvo> findSByState() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="SELECT id id,onumber onumber,mname mname,state state,pid pid FROM CD_MENU a where a.state ='1' order by a.onumber";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, MenuSvo.class);
	}
	@Override
	public List<MenuSvo> findAll() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="SELECT id id,address address,mname mname,state state,pid pid,menuIcon menuIcon FROM CD_MENU a where a.state ='1' order by a.onumber";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, MenuSvo.class);
	}

	@Override
	public List<MenuSvo> findByRoId(String[] rid) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rid", rid);
		String sql="SELECT id id,address address,mname mname,state state,pid pid,menuIcon menuIcon FROM CD_MENU M WHERE M.ID IN (" +
				" SELECT a.MID_ID"+
				" FROM"+
				" 	CD_ROLE_MENU A"+
				" WHERE"+
				" 	1 = 1"+
				" AND A .RID_ID IN (:rid)"+
				" GROUP BY A.MID_ID) ORDER BY M.ONUMBER";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, MenuSvo.class);
	}

	@Override
	public List<MenuTwoVo> findTopMenulist(String[] rid,String userId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rid", rid);
		String temp = "'"+userId+"' userId ";
		String sql="SELECT id fid,"+temp+",id as children ,address as href,mname as title,menuIcon as icon FROM CD_MENU M WHERE M.ID IN (" +
				" SELECT a.MID_ID"+
				" FROM"+
				" 	CD_ROLE_MENU A"+
				" WHERE"+
				" 	1 = 1"+
				" AND A .RID_ID IN (:rid)"+
				" GROUP BY A.MID_ID)" +
				" AND pid is null ORDER BY M.ONUMBER";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, MenuTwoVo.class);
	}

	@Override
	public List<MenuOneVo> findTopMenulist(String userId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId",userId);
		String sql="SELECT\n" +
				"\tid id,\n" +
				"\taddress href,\n" +
				"\tmname title,\n" +
				"\tmenuIcon icon\n" +
				"FROM\n" +
				"\tcd_menu c\n" +
				"WHERE\n" +
				"\tpid IS NULL\n" +
				"AND id IN (\n" +
				"\tSELECT\n" +
				"\t\tb.mid_id\n" +
				"\tFROM\n" +
				"\t\tcd_user_role a\n" +
				"\tLEFT JOIN cd_role_menu b ON a.roleId = b.rid_id\n" +
				"\tWHERE\n" +
				"\t\ta.userId = :userId \n" +
				"\tGROUP BY\n" +
				"\t\tb.mid_id\n" +
				")\n" +
				"ORDER BY\n" +
				"\tc.onumber desc";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, MenuOneVo.class);
	}

	@Override
	public List<MenuTwoVo> findTwoList(String fid) throws Exception {
		Map map=new HashedMap();
		String sql ="SELECT\n" +
				"\ta.id AS fid,\n" +
				"\ta.mname AS title,\n" +
				"\ta.menuIcon AS icon,\n" +
				"\ta.address AS href,\n" +
				"\ta.id AS children\n" +
				"FROM cd_menu a \n" +
				"WHERE\n" +
				"\t1 = 1\n";
		if(StringUtils.isNotBlank(fid)){
			sql += "AND a.pid = :pid\n" ;
			map.put("pid",fid);
		}

		sql +=" ORDER BY a.onumber asc";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, MenuTwoVo.class);
	}

	@Override
	public List<MenuTwoVo> findTwoListAc(String id, String userId) throws Exception {
		Map map=new HashedMap();
		String sql ="SELECT\n" +
				"\ta.id AS fid,\n" +
				"\ta.mname AS title,\n" +
				"\ta.menuIcon AS icon,\n" +
				"\ta.address AS href,\n" +
				"\ta.id AS children\n" +
				"FROM\n" +
				" cd_role_menu b\t\n" +
				"LEFT JOIN cd_menu a ON b.mid_id = a.id\n" +
				"LEFT JOIN cd_user_role e ON e.roleId = b.rid_id\n" +
				"WHERE\n" +
				"\t1 = 1\n";
				if(StringUtils.isNotBlank(id)){
					sql += "AND a.pid = :pid\n" ;
					map.put("pid",id);
				}
				if(StringUtils.isNotBlank(userId)){
					sql+=" AND e.userId = :userId";
					map.put("userId",userId);
				}
				sql +=" GROUP BY a.id,a.mname,a.menuIcon,a.address,a.id  ORDER BY a.onumber asc";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, MenuTwoVo.class);
	}
}
