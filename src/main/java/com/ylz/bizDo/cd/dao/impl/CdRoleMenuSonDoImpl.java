package com.ylz.bizDo.cd.dao.impl;


import com.ylz.bizDo.cd.dao.CdRoleMenuSonDo;
import com.ylz.bizDo.cd.po.CdRoleMenuSon;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.hql.HQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;
@Service("roleSonDo")
@Transactional(rollbackForClassName={"Exception"})
public class CdRoleMenuSonDoImpl   implements CdRoleMenuSonDo {
	
	@Autowired
	private SysDao sysDao;
	
	public List<CdRoleMenuSon> finRM(String rid) throws Exception{
		  HQuery hq=new  HQuery("from CdRoleMenuSon as rs where 1=1 ");
		  hq.addHqlSplit(" and rs.rmid.id= ? ").addParas(rid, Types.VARCHAR);
		  List<CdRoleMenuSon> li = sysDao.getServiceDo().findHquery(hq);
	        if (li!= null) {
                return li;
            }
	        return null;
	}
	
}
