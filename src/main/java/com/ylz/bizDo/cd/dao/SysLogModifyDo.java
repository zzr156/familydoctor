package com.ylz.bizDo.cd.dao;

import com.ylz.bizDo.cd.vo.LogDeletQvo;
import com.ylz.bizDo.cd.vo.LogDeletVo;
import com.ylz.bizDo.cd.vo.LogModifyQvo;
import com.ylz.bizDo.cd.vo.LogModifyVo;

import java.util.List;

/**
 * Created by hzk on 2017/8/24.
 * 数据修改日志表
 */
public interface SysLogModifyDo {
    /**
     * 异步添加日志
     * @param userid
     * @param userName
     * @param className
     * @param businessId
     * @param businessJson
     * @throws Exception
     */
    public void addSysLogModify(String userid, String userName, String className, String businessId, String businessJson,String act) throws Exception;

    /**
     * 删除日志查询
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<LogModifyVo> findLogModifyList(LogModifyQvo qvo) throws Exception;
}
