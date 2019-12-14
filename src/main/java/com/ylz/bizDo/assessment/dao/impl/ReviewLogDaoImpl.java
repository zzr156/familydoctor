package com.ylz.bizDo.assessment.dao.impl;

import com.ylz.bizDo.assessment.dao.ReviewLogDao;
import com.ylz.bizDo.assessment.po.ReviewLog;
import com.ylz.packaccede.allDo.SysDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zms on 2018/6/13.
 */
@Service("reviewLogDao")
public class ReviewLogDaoImpl implements ReviewLogDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<ReviewLog> findLogList(String assessmentId) throws Exception {
        String hql = "from ReviewLog where assessmentId=:assessmentId order by HsCreateDate desc";
        HashMap map = new HashMap();
        map.put("assessmentId", assessmentId);
        List<ReviewLog> list = sysDao.getServiceDo().findHqlMap(hql, map);
        if (list.size() > 0) {
            return list;
        }
        return null;
    }
}
