package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.WebHealthReportEntity;
import com.ylz.bizDo.app.po.AppHealthReport;
import com.ylz.bizDo.web.vo.WebHealthReportVo;

/**
 * Created by WangCheng on 2018/11/08.
 */
public interface AppHealthAssessmentDao {

    /**
     * 健康评估报告保存
     * WangCheng
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public WebHealthReportEntity addHealthReport(WebHealthReportVo vo) throws Exception;

    /**
     * 获取健康报告信息
     * WangCheng
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public AppHealthReport findHealthReport(WebHealthReportVo vo) throws Exception;

    /**
     * 修改健康报告信息
     * WangCheng
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public WebHealthReportEntity modifyHealthReport(WebHealthReportVo vo) throws Exception;
}
