package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppMsg;
import com.ylz.bizDo.app.vo.AppMsgQvo;

import java.util.List;

/**
 * Created by zzl on 2019/1/28.
 */
public interface AppMsgDao {
    /**
     * 分页查询
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppMsg> findMsg(AppMsgQvo qvo) throws Exception;

    /**
     * 新增
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppMsg addMsg(AppMsgQvo qvo) throws Exception;

    /**
     * 修改
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppMsg modifyMsg(AppMsgQvo qvo) throws Exception;

    public AppMsg findMsgByQvo(AppMsgQvo qvo) throws Exception;

    /**
     * 根据appId查询下应用
     * @param appId
     * @return
     * @throws Exception
     */
    public AppMsg findMsgByAppId(String appId) throws Exception;


}
