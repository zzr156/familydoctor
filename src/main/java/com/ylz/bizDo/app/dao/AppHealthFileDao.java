package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppHealthFile;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drVo.AppFileAuditQvo;

import java.util.List;

/**
 * Created by zzl on 2018/2/7.
 */
public interface AppHealthFileDao  {
    /**
     * 保存患者健康档案
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppHealthFile saveFile(AppFileAuditQvo qvo) throws Exception;

    /**
     * 查询患者提交的健康档案
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppHealthFile> findFile(AppCommQvo qvo) throws Exception;

    /**
     * 审核健康档案
     * @param qvo
     * @return
     * @throws Exception
     */
    public String fileAudit(AppFileAuditQvo qvo) throws Exception;

    public AppHealthFile findFileByPatientId(String patientId) throws Exception;

}
