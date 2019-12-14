package com.ylz.bizDo.cd.dao.impl;


import com.ylz.bizDo.cd.dao.CdMenuSonDo;
import com.ylz.bizDo.cd.entity.CdMenuSonEntity;
import com.ylz.bizDo.cd.po.CdMenuSon;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.hql.HQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
@Service("sonDo")
@Transactional(rollbackForClassName={"Exception"})
public class CdMenuSonDoImpl  implements CdMenuSonDo {
	@Autowired
	private SysDao sysDao;
	
	public List<CdMenuSon> selectAll() throws Exception{
		  HQuery hq=new  HQuery("from CdMenuSon");
		  List<CdMenuSon> li =sysDao.getServiceDo().findHquery(hq);
	        if (li!= null) {
                return li;
            }
	        return null;
	}

	@Override
	public List<CdMenuSon> findSonRoId(String[] rid) throws Exception {
		HashMap map=new HashMap();
		map.put("rid", rid);
		String sql="SELECT * FROM CD_ROLE_MENU_SON rs INNER JOIN CD_MENU_SON s ON rs.SID=s.ID WHERE rs.RMID_ID IN (" +
				" SELECT"+ 
				" A.ID "+
				" FROM"+
				" CD_ROLE_MENU A"+
				" WHERE 1 = 1"+
				" AND A .RID_ID IN (:rid)"+
				" GROUP BY A.ID)";
		return sysDao.getServiceDo().findSqlMap(sql, map,CdMenuSon.class);
	}
	
	public List<CdMenuSon> findSonRoId(String[] rid,String mid) throws Exception {
		HashMap map=new HashMap();
		String sql="SELECT * FROM CD_ROLE_MENU_SON rs INNER JOIN CD_MENU_SON s ON rs.SID=s.ID WHERE rs.RMID_ID IN (" +
				" SELECT"+ 
				" A.ID "+
				" FROM"+
				" CD_ROLE_MENU A"+
				" WHERE 1 = 1";
		if(rid != null){
			map.put("RID", rid);
			sql += " AND A .RID_ID IN (:RID)";
		}
		if(StringUtils.isNotBlank(mid)){
			map.put("MID", mid);
			sql += " AND A .MID_ID = :MID";
		}
		sql += "  GROUP BY A.ID)";
		return sysDao.getServiceDo().findSqlMap(sql, map,CdMenuSon.class);
	}

	@Override
	public List<CdMenuSonEntity> findMenuSonId(String id) throws Exception {
		HashMap map=new HashMap();
		map.put("mid", id);
		String sql = "SELECT a.id id,a.sname sname,nature nature,remark remark FROM CD_MENU_SON a WHERE a.mid_id =:mid ";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, CdMenuSonEntity.class);
	}
}
