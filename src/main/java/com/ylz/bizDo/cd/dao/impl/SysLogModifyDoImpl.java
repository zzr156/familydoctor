package com.ylz.bizDo.cd.dao.impl;

import com.ylz.bizDo.cd.dao.SysLogDeletDo;
import com.ylz.bizDo.cd.dao.SysLogModifyDo;
import com.ylz.bizDo.cd.po.SysLogDelet;
import com.ylz.bizDo.cd.po.SysLogModify;
import com.ylz.bizDo.cd.vo.LogDeletQvo;
import com.ylz.bizDo.cd.vo.LogDeletVo;
import com.ylz.bizDo.cd.vo.LogModifyQvo;
import com.ylz.bizDo.cd.vo.LogModifyVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by hzk on 2017/8/24.
 * 数据修改日志表
 */
@Service("sysLogModifyDo")
@Transactional(rollbackForClassName={"Exception"})
public class SysLogModifyDoImpl implements SysLogModifyDo {
    @Autowired
    public SysDao sysDao;

    @Async
    public void addSysLogModify(String userid,String userName,String className,String businessId,String businessJson,String act) throws Exception{
       try{
           SysLogModify logm=new SysLogModify();
           logm.setUserId(userid);
           logm.setUserName(userName);
           logm.setClassName(className);
           logm.setBusinessId(businessId);
           logm.setBusinessJson(businessJson);
           logm.setClassAct(act);
           logm.setCreateDate(Calendar.getInstance());
           sysDao.getServiceDo().add(logm);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    @Override
    public List<LogModifyVo> findLogModifyList(LogModifyQvo qvo) throws Exception{
        List<LogModifyVo> ls=null;
        Map<String,Object> map=new HashedMap();
        String hql= "select a.id as id,a.userId as userId,a.userName as userName,a.className as className,businessId as businessId,a.createDate as createDate from SysLogModfiy a where 1=1";
        if(StringUtils.isNotBlank(qvo.getStartTime())){
            String startTime = qvo.getStartTime() +" 00:00:00";
            map.put("startTime",ExtendDate.getCalendar(startTime));
            hql += " AND a.createDate >= :startTime";
        }
        if(StringUtils.isNotBlank(qvo.getEndTime())){
            String endTime = qvo.getEndTime() + " 23:59:59";
            map.put("endTime", ExtendDate.getCalendar(endTime));
            hql += " AND a.createDate <= :endTime";
        }
        if(StringUtils.isNotBlank(qvo.getUserName())){
            map.put("userName","%"+qvo.getUserName()+"%");
            hql += " AND a.userName like :userName ";
        }
        if(StringUtils.isNotBlank(qvo.getClassName())){
            map.put("className","%"+qvo.getClassName()+"%");
            hql += " AND a.className like :className ";
        }

        hql += "  ORDER BY a.createDate DESC";
        ls = sysDao.getServiceDo().findHqlMapRVo(hql,map,LogModifyVo.class,qvo);
        return ls;
    }
}
