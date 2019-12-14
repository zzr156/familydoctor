package com.ylz.bizDo.cd.dao.impl;

import com.ylz.bizDo.cd.dao.SysLogAddDo;
import com.ylz.bizDo.cd.po.SysLogAdd;
import com.ylz.bizDo.cd.vo.LogAddVo;
import com.ylz.bizDo.cd.vo.LogModifyQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/6/13.
 */
@Service("sysLogAddDo")
@Transactional(rollbackForClassName={"Exception"})
public class SysLogAddDoImpl implements SysLogAddDo {
    @Autowired
    public SysDao sysDao;

    @Async
    public void addSysLogAdd(String userid, String userName, String className, String businessId, String businessJson,String act) throws Exception {
        SysLogAdd loga=new SysLogAdd();
        loga.setUserId(userid);
        loga.setUserName(userName);
        loga.setClassName(className);
        loga.setBusinessId(businessId);
        loga.setBusinessJson(businessJson);
        loga.setBusinessJson(businessJson);
        loga.setCreateDate(Calendar.getInstance());
        loga.setClassAct(act);
        sysDao.getServiceDo().add(loga);
    }

    @Override
    public List<LogAddVo> findLogAddList(LogModifyQvo qvo) throws Exception {
        List<LogAddVo> ls=null;
        Map<String,Object> map=new HashedMap();
        String hql= "select a.id as id,a.userId as userId,a.userName as userName,a.className as className,businessId as businessId,a.createDate as createDate from SysLogAdd a where 1=1";
        if(StringUtils.isNotBlank(qvo.getStartTime())){
            String startTime = qvo.getStartTime() +" 00:00:00";
            map.put("startTime", ExtendDate.getCalendar(startTime));
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
        ls = sysDao.getServiceDo().findHqlMapRVo(hql,map,LogAddVo.class,qvo);
        return ls;
    }
}
