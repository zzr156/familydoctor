package com.ylz.bizDo.assessment.dao.impl;

import com.ylz.bizDo.assessment.dao.AssessLogDao;
import com.ylz.bizDo.assessment.po.AssessLog;
import com.ylz.packaccede.allDo.SysDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zms on 2018/6/13.
 */
@Service("assessLogDao")
@Transactional(rollbackForClassName={"Exception"})
public class AssessLogDaoImpl implements AssessLogDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AssessLog> findLogList(String assessmentId) throws Exception {
        String hql = "from AssessLog where assessmentId=:assessmentId order by HsCreateDate desc";
        HashMap map = new HashMap();
        map.put("assessmentId", assessmentId);
        List<AssessLog> list = sysDao.getServiceDo().findHqlMap(hql, map);
        if (list.size() > 0) {
            return list;
        }
        return null;
    }
}
