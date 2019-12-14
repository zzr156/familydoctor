package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppHealthAssessmentDao;
import com.ylz.bizDo.app.entity.WebHealthReportEntity;
import com.ylz.bizDo.app.po.AppHealthReport;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.web.vo.WebHealthReportVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.CopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WangCheng on 2018/11/08.
 */
@Service("appHealthAssessmentDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppHealthAssessmentDaoImpl implements AppHealthAssessmentDao {

    @Autowired
    private SysDao sysDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 健康评估报告保存
     * WangCheng
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public WebHealthReportEntity addHealthReport(WebHealthReportVo vo) throws Exception {
        WebHealthReportEntity webHealthReportEntity = new WebHealthReportEntity();
        AppSignForm appSignForm = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, vo.getSignLableId());
        if (appSignForm != null) {
            logger.info("ID为 " + vo.getSignLableId() + " 的签约单存在！");
            appSignForm.setSignHealthReportState("1");
            sysDao.getServiceDo().modify(appSignForm);
        } else {
            logger.error("ID为 " + vo.getSignLableId() + " 的签约单不存在！");
            throw new Exception("ID为 " + vo.getSignLableId() + " 的签约单不存在！");
        }
        AppHealthReport appHealthReport = new AppHealthReport();
        CopyUtils.Copy(vo, appHealthReport);
        sysDao.getServiceDo().add(appHealthReport);

        CopyUtils.Copy(appHealthReport, webHealthReportEntity);
        return webHealthReportEntity;
    }

    /**
     * 获取健康报告信息
     * WangCheng
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public AppHealthReport findHealthReport(WebHealthReportVo vo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "select * from app_health_report a where 1=1";
        if (StringUtils.isNotEmpty(vo.getPatientIdNo())) {
            map.put("patientIdNo", vo.getPatientIdNo());
            sql += " and a.PATIENT_ID_NO = :patientIdNo";
        }
        if (StringUtils.isNotEmpty(vo.getHealthReportState())) {
            map.put("healthReportState", vo.getHealthReportState());
            sql += " and a.REPORT_STATE = :healthReportState";
        }
        if (StringUtils.isNotEmpty(vo.getSignLableId())) {
            map.put("signLableId", vo.getSignLableId());
            sql += " and a.SIGN_LABLE_ID = :signLableId";
        }
        if (StringUtils.isNotEmpty(vo.getOrgId())) {
            map.put("orgId", vo.getOrgId());
            sql += " and a.ORG_ID = :orgId";
        }
        List<AppHealthReport> list = sysDao.getServiceDo().findSqlMap(sql, map, AppHealthReport.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 修改健康报告信息
     * WangCheng
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public WebHealthReportEntity modifyHealthReport(WebHealthReportVo vo) throws Exception {
        WebHealthReportEntity webHealthReportEntity = new WebHealthReportEntity();
        AppHealthReport appHealthReport = sysDao.getAppHealthAssessmentDao().findHealthReport(vo);
        if(appHealthReport != null){
            CopyUtils.Copy(vo, appHealthReport);
            sysDao.getServiceDo().modify(appHealthReport);

            CopyUtils.Copy(appHealthReport, webHealthReportEntity);
            return webHealthReportEntity;
        }else {
            return null;
        }
    }
}