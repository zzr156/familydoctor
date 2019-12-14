package com.ylz.bizDo.assessment.dao.impl;

import com.ylz.bizDo.assessment.dao.AssessmentTeamShareDao;
import com.ylz.bizDo.assessment.po.AssessmentTeamShare;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zms on 2018/6/13.
 */
@Service("assessmentTeamShareDao")
@Transactional(rollbackFor = {Exception.class})
public class AssessmentTeamShareDaoImpl implements AssessmentTeamShareDao {

    @Autowired
    private SysDao sysDao;


    @Override
    public List<AssessmentTeamShare> listShare(String teamId, Integer year, Integer month) throws Exception {
        Map<String, Object> map = new HashMap<>();
        StringBuilder hql = new StringBuilder();
        hql.append(" from AssessmentTeamShare where 1 = 1 ");
        // 团队ID
        if (StringUtils.isNotBlank(teamId)) {
            hql.append(" and teamId = :teamId ");
            map.put("teamId", teamId);
        }
        // 年份和月份
        if (year != null && month != null) {
            hql.append(" and ((year = :year and month >= :month)  ");
            hql.append(" or (year = :nextYear and month <= :month)) ");
            map.put("year", year);
            map.put("nextYear", year + 1);
            map.put("month", month);
        }
        hql.append(" order by HsCreateDate ");
        List<AssessmentTeamShare> list = sysDao.getServiceDo().findHqlMap(hql.toString(), map);
        return list;
    }

    @Override
    public AssessmentTeamShare getShare(String teamId, Integer year, Integer month) throws Exception {
        String hql = " from AssessmentTeamShare where teamId = :teamId and year = :year and month = :month ";
        Map<String, Object> map = new HashMap<>();
        map.put("teamId", teamId);
        map.put("year", year);
        map.put("month", month);
        List<AssessmentTeamShare> list = sysDao.getServiceDo().findHqlMap(hql, map);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
