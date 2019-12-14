package com.ylz.bizDo.cd.dao;

import com.ylz.bizDo.cd.po.CdPaperManage;
import net.sf.jasperreports.engine.JasperPrint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WangCheng on 2018/11/15.
 */
public interface CdPaperTemplateDao {

    /**
     * 通过模板代码获取模板
     * WangCheng
     *
     * @param templateCode
     * @return
     * @throws Exception
     */
    public String findTemplateByCode(String templateCode) throws Exception;

    /**
     * WangCheng
     * @param templateId
     * @param parameter
     * @return
     * @throws Exception
     */
    public JasperPrint getJasperPrint(String templateId, HashMap<String, Object> parameter) throws Exception;

    /**
     * WangCHeng
     * @param templateId
     * @return
     * @throws Exception
     */
    public CdPaperManage getPaperManage(String templateId) throws Exception;
}
