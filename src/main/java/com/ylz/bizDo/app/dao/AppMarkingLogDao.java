package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppMarkingLogItem;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.smjq.smEntity.AppModifyLogEntity;
import com.ylz.bizDo.smjq.smEntity.AppModifyPeopleListEntity;
import com.ylz.bizDo.smjq.smVo.AppSmPeopleBasicVo;
import com.ylz.bizDo.sys.vo.SysLogVo;

import java.util.List;

/**
 * Created by zzl on 2018/8/15.
 */
public interface AppMarkingLogDao {
    /**
     * 保存分标管理日志
     * @param userId 居民id
     * @param businessName 业务名称
     * @throws Exception
     */
    public void saveMarkingLog(String userId, String businessName, List<AppMarkingLogItem> ls, String hospId,String drName,String drId ) throws Exception;

    /**
     * 根据条件查询分标修改日志
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppModifyLogEntity> findMarkingLogList(AppSmPeopleBasicVo qvo) throws Exception;

    /**
     * 根据条件查询有修改居民信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppModifyPeopleListEntity> findModifyPeopleList(AppSmPeopleBasicVo qvo) throws Exception;
}
