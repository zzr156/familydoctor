package com.ylz.bizDo.cd.dao.impl;


import com.ylz.bizDo.cd.dao.CdRoleMenuDo;
import com.ylz.bizDo.cd.po.CdRoleMenu;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.hql.HQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
@Service("roleMenuDo")
@Transactional(rollbackForClassName={"Exception"})
public class CdRoleMenuDoImpl   implements CdRoleMenuDo {
	
	@Autowired
	private SysDao sysDao;
	
	//根据角色查
	public List<CdRoleMenu> finRM(String rid) throws Exception{
		  HQuery hq=new  HQuery("from CdRoleMenu as rm where 1=1 ");
		  hq.addHqlSplit(" and rm.rid= ? ").addParas(rid, Types.VARCHAR);
		  hq.setOrderby(" order by rm.mid.onumber asc ");//排序
		  List<CdRoleMenu> li =sysDao.getServiceDo().findHquery(hq);
	        if (li!= null) {
                return li;
            }
	        return null;
	}
	//根据mid查
	public List<CdRoleMenu> fin(String rid) throws Exception{
		  HQuery hq=new  HQuery("from CdRoleMenu as rm where 1=1 ");
		  hq.addHqlSplit(" and rm.rid= ? ").addParas(rid, Types.VARCHAR);
		  List<CdRoleMenu> li =sysDao.getServiceDo().findHquery(hq);
	        if (li!= null) {
                return li;
            }
	        return null;
	}
	
	public List<CdRoleMenu> findRidList(String rid) throws Exception{
		HashMap map=new HashMap();
		StringBuffer sql=new StringBuffer("SELECT a.* from CD_ROLE_MENU a where 1=1 ");
		if(StringUtils.isNotBlank(rid)) {
            sql.append(" and a.rid_id=:rid_id ");
            map.put("rid_id",rid);
        }
		List<CdRoleMenu> ls=sysDao.getServiceDo().findSqlMap(sql.toString(),map,CdRoleMenu.class);
		if(ls!=null) {
            return ls;
        }
        return null;
	}
	

}
