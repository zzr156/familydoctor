package com.ylz.bizDo.assessment.dao;

import com.ylz.bizDo.assessment.po.ReviewLog;

import java.util.List;

/**
 * Created by zms on 2018/6/13.
 */
public interface ReviewLogDao {
    public List<ReviewLog> findLogList(String assessmentId) throws Exception;
}
