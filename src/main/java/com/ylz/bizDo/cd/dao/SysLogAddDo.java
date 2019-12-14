package com.ylz.bizDo.cd.dao;

import com.ylz.bizDo.cd.vo.LogAddVo;
import com.ylz.bizDo.cd.vo.LogModifyQvo;
import com.ylz.bizDo.cd.vo.LogModifyVo;

import java.util.List;

/**
 * Created by zzl on 2018/6/13.
 */
public interface SysLogAddDo {
    /**
     * 异步添加日志
     * @param userid
     * @param userName
     * @param className
     * @param businessId
     * @param businessJson
     * @throws Exception
     */
    public void addSysLogAdd(String userid, String userName, String className, String businessId, String businessJson,String act) throws Exception;

    /**
     * 删除日志查询
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<LogAddVo> findLogAddList(LogModifyQvo qvo) throws Exception;
}
