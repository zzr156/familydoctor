package com.ylz.bizDo.sys.service;

import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.sys.po.SysLogBusiness;
import com.ylz.bizDo.sys.po.SysLogBusinessItem;
import com.ylz.bizDo.sys.vo.LogQvo;
import com.ylz.bizDo.sys.vo.SysLogVo;

import java.util.List;

/**
 * Created by hzk on 2017/8/8.
 * 业务日志
 */
public interface SysLogBusinessDo {

    /**
     * 日志记录 异步记录
     * @param businessName
     * @param businessTable
     * @param businessId
     * @param ls
     * @param user
     * @throws Exception
     */
    public void addSysLog(String businessName, String businessTable, String businessId, List<SysLogVo> ls, CdUser user) throws Exception;

    /**
     * 日志列表
     * @return
     */
    public List<SysLogBusiness> findLogList(LogQvo qvo) throws Exception;

    public void addSysLogBusiness(SysLogBusiness po) throws Exception;
    public void addSysLogBusinessItem(SysLogBusinessItem po) throws Exception;
    public void addSysLogBusinessItem(SysLogBusiness vo, SysLogBusinessItem po) throws Exception;

}
