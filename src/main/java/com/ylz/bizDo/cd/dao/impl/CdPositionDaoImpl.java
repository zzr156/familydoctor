package com.ylz.bizDo.cd.dao.impl;


import com.ylz.bizDo.cd.dao.CdPositionDao;
import com.ylz.bizDo.cd.entity.CdPositionEntity;
import com.ylz.bizDo.cd.po.CdPosition;
import com.ylz.bizDo.cd.vo.PositionQvo;
import com.ylz.bizDo.cd.vo.PositionVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.hql.HQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("positionDao")
@Transactional(rollbackForClassName={"Exception"})
public class CdPositionDaoImpl  implements CdPositionDao {
	
	@Autowired
	private SysDao sysDao;
	
	public List<CdPosition> findAll() throws Exception{
	HQuery hq = new HQuery("from CdPosition order by num");
	List<CdPosition> list = new ArrayList<CdPosition>();
	list = sysDao.getServiceDo().findHquery(hq);
	return list;
	}
	/**
	 * 查询编号是否 存在
	 * @param userName
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public CdPosition findIsPosition(String num, String id) throws Exception{
		String hql = "from CdPosition as s where s.num = ? and s.id != ?";
		HQuery query = new HQuery(hql);
		query.addParas(num, Types.VARCHAR).addParas(id,Types.VARCHAR);
		List list = sysDao.getServiceDo().findHquery(query);
		if (list != null && list.size() > 0) {
			return (CdPosition) list.get(0);
		}
		return null;
	}
	
	public List<CdPosition> findCdPosition(PositionQvo qvo) throws Exception {
		HQuery hq = new HQuery("from CdPosition order by num");
		sysDao.getServiceDo().getNewQvo(qvo, hq);
		List<CdPosition> ls = sysDao.getServiceDo().findHquery(hq);
		if(ls != null && ls.size()>0) {
            return ls;
        }
		return null;
	}
	
	@Override
	public List<CdPositionEntity> findByUserId(String userId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "SELECT p.account Name FROM CD_POSITION p LEFT JOIN CD_USER_POSITION u ON p.id = u.positionId WHERE 1=1";
		if(StringUtils.isNotBlank(userId)){
			map.put("USERID", userId);
			sql +=" AND u.userId =:USERID";
		}
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, CdPositionEntity.class);
	}
	
	@Override
	public List<PositionVo> findPosition() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "SELECT id,account  FROM CD_POSITION ORDER BY NUM";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, PositionVo.class);
	}
	
	
	
	
}
