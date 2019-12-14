package com.ylz.bizDo.assessment.dao;

import com.ylz.bizDo.assessment.po.AssessLog;

import java.util.List;

/**
 * Created by zms on 2018/6/13.
 */
public interface AssessLogDao {

    public List<AssessLog> findLogList(String assessmentId) throws Exception;
}
