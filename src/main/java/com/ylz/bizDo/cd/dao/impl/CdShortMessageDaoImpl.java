package com.ylz.bizDo.cd.dao.impl;

import com.ylz.bizDo.cd.dao.CdShortMessageDao;
import com.ylz.bizDo.cd.po.CdShortMessage;
import com.ylz.bizDo.cd.vo.CdShortMessageQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service("cdShortMessageDao")
@Transactional(rollbackForClassName={"Exception"})
public class CdShortMessageDaoImpl implements CdShortMessageDao {

	@Autowired
	private SysDao sysDao;

	@Override
	public List<CdShortMessage> findListQvo(CdShortMessageQvo qvo) throws Exception {
		HashMap map=new HashMap();
        String sql="SELECT * FROM CD_SHORT_MESSAGE a WHERE 1=1  ";
        sql += " ORDER BY a.CJSJ desc ";
        List<CdShortMessage> ls=sysDao.getServiceDo().findSqlMap(sql, map, CdShortMessage.class,qvo);
        return ls;
	}
	
}
