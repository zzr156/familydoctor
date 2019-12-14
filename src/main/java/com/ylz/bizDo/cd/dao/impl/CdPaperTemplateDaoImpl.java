package com.ylz.bizDo.cd.dao.impl;

import com.ylz.bizDo.cd.dao.CdPaperTemplateDao;
import com.ylz.bizDo.cd.po.CdPaperManage;
import com.ylz.packaccede.allDo.SysDao;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WangCheng on 2018/11/15.
 */
@Service("cdPaperTemplateDao")
@Transactional(rollbackForClassName={"Exception"})
public class CdPaperTemplateDaoImpl implements CdPaperTemplateDao {

    @Autowired
    public SysDao sysDao;

    /**
     * 通过模板代码获取模板
     * WangCheng
     *
     * @param templateCode
     * @return
     * @throws Exception
     */
    public String findTemplateByCode(String templateCode) throws Exception {
        CdPaperManage cdPaperManage = (CdPaperManage) sysDao.getServiceDo().getSessionFactory().getCurrentSession().createCriteria(CdPaperManage.class).add(Restrictions.eq("documentCode", templateCode)).uniqueResult();
        if (cdPaperManage != null) {
            return cdPaperManage.getId();
        }
        return null;
    }

    /**
     * WangCheng
     *
     * @param templateId
     * @param parameter
     * @return
     * @throws Exception
     */
    public JasperPrint getJasperPrint(String templateId, HashMap<String, Object> parameter) throws Exception {
        CdPaperManage cdPaperManage = sysDao.getCdPaperTemplateDao().getPaperManage(templateId);
        if (cdPaperManage != null) {
            JasperDesign design = JRXmlLoader.load(cdPaperManage.getDocumentPath());
            //编译报表模板
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            //填充报表
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
            return jasperPrint;
        }
        return null;
    }

    /**
     * WangCheng
     *
     * @param templateId
     * @return
     * @throws Exception
     */
    public CdPaperManage getPaperManage(String templateId) throws Exception {
        CdPaperManage cdPaperManage = (CdPaperManage) sysDao.getServiceDo().getSessionFactory().getCurrentSession().createCriteria(CdPaperManage.class).add(Restrictions.eq("id", templateId)).uniqueResult();
        if (cdPaperManage != null) {
            return cdPaperManage;
        }
        return null;
    }

}
