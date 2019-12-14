package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppSubmissionRepetition;

/**
 * app医生代签约重复提交接口
 */
public interface AppSubmissionRepetitionDao {
    /**
     * 根据数据查询
     * @param md5Result
     * @return
     */
    public AppSubmissionRepetition getDataOne(String md5Result) throws Exception;
}
