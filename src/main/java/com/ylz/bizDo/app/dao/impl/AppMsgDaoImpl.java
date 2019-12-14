package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppMsgDao;
import com.ylz.bizDo.app.po.AppMsg;
import com.ylz.bizDo.app.vo.AppMsgQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.Md5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zzl on 2019/1/28.
 */
@Service("appMsgDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppMsgDaoImpl implements AppMsgDao {
    @Autowired
    private SysDao sysDao;

    /**
     * 分页查询
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppMsg> findMsg(AppMsgQvo qvo) throws Exception {

        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT * FROM APP_MSG WHERE 1=1 ";

        List<AppMsg> list = sysDao.getServiceDo().findSqlMap(sql,map,AppMsg.class,qvo);
        return list;
    }

    /**
     * 新增
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppMsg addMsg(AppMsgQvo qvo) throws Exception {
        AppMsg msg = new AppMsg();
        msg.setAppName(qvo.getAppName());
        msg.setAppPgName(qvo.getAppPgName());
        msg.setAppCreateTime(ExtendDate.getCalendar(qvo.getAppCreateTime()));
        String appId = UUID.randomUUID().toString().replaceAll("-","");
        msg.setAppId(Md5Util.MD5(appId).toUpperCase());
        String appMasterSecret = UUID.randomUUID().toString().replaceAll("-","")+ExtendDate.getTime(Calendar.getInstance());
        msg.setAppMasterSecret(Md5Util.MD5(appMasterSecret).toUpperCase());
        msg.setAppKey(Md5Util.MD5(msg.getAppId()+msg.getAppMasterSecret()+msg.getAppPgName()).toUpperCase());
        msg.setAppState("0");
        msg.setAppAreaCode(qvo.getAppAreaCode());
        sysDao.getServiceDo().add(msg);
        return msg;
    }

    /**
     * 修改
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppMsg modifyMsg(AppMsgQvo qvo) throws Exception {
        if(StringUtils.isNotBlank(qvo.getId())){
            AppMsg msg = (AppMsg)sysDao.getServiceDo().find(AppMsg.class,qvo.getId());
            if(msg != null){
                msg.setAppName(qvo.getAppName());
                if(!qvo.getAppPgName().equals(msg.getAppPgName())){
                    msg.setAppKey(Md5Util.MD5(msg.getAppId()+msg.getAppMasterSecret()+qvo.getAppPgName()).toUpperCase());
                }
                msg.setAppPgName(qvo.getAppPgName());
                msg.setAppAreaCode(qvo.getAppAreaCode());
                sysDao.getServiceDo().modify(msg);
                return msg;
            }
        }
        return null;
    }

    @Override
    public AppMsg findMsgByQvo(AppMsgQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("appId",qvo.getAppId());
        String sql = "select * from app_msg where 1=1 ";
        sql += " AND APP_ID =:appId ";
        List<AppMsg> list = sysDao.getServiceDo().findSqlMap(sql,map,AppMsg.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public AppMsg findMsgByAppId(String appId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("appId",appId);
        String sql = "select * from app_msg where 1=1 ";
        sql += " AND APP_ID =:appId ";
        List<AppMsg> list = sysDao.getServiceDo().findSqlMap(sql,map,AppMsg.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
