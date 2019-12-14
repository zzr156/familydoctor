package com.ylz.bizDo.sys.service.impl;

import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.sys.po.SysLogBusiness;
import com.ylz.bizDo.sys.po.SysLogBusinessItem;
import com.ylz.bizDo.sys.service.SysLogBusinessDo;
import com.ylz.bizDo.sys.vo.LogQvo;
import com.ylz.bizDo.sys.vo.SysLogVo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzk on 2017/8/8.
 * 业务日志
 */
@Service("sysLogBusinessDo")
@Transactional(rollbackForClassName={"Exception"})
public class SysLogBusinessDoImpl implements SysLogBusinessDo {
    @Autowired
    public SysDao sysDao;
    @Async
    public void addSysLog(String businessName, String businessTable, String businessId, List<SysLogVo> ls, CdUser user) throws Exception {
        if(ls!=null && !ls.isEmpty()) {
            SysLogBusiness po = new SysLogBusiness();
            po.setBusinessName(businessName);
            po.setBusinessId(businessId);
            po.setUserId(user.getDrId());
            po.setUserName(user.getUserName());
            po.setOrgId(user.getHospId());
            po.setBusinessTable(businessTable);
            String desc="";
            for(SysLogVo l:ls){
                desc+=l.getKey()+";";
            }
            if(desc.length()>240) {
                po.setBusinessDesc(desc.substring(0,240));
            }else {
                po.setBusinessDesc(desc);
            }
            sysDao.getServiceDo().add(po);
            for(SysLogVo l:ls){
                SysLogBusinessItem item=new SysLogBusinessItem();
                item.setKey(l.getKey());
                item.setOldValue(l.getOldValue());
                item.setNewValue(l.getNewValue());
                item.setLogId(po.getId());
                sysDao.getServiceDo().add(item);
            }
        }

    }

    @Override
    public List<SysLogBusiness> findLogList(LogQvo qvo) throws Exception {
        Map<String,Object> map=new HashMap<String,Object>();
        String sql="from SysLogBusiness a where 1=1";
        if(StringUtils.isNotBlank(qvo.getStartTime())){
            String startTime = qvo.getStartTime() +" 00:00:00";
            map.put("startTime",startTime);
            sql += " AND a.HsCreateDate >= TO_DATE(:startTime, 'yy-mm-dd hh24:mi:ss')";
            //sql += " AND CREATE_DATE >= :startTime";
        }
        if(StringUtils.isNotBlank(qvo.getEndTime())){
            String endTime = qvo.getEndTime() + " 23:59:59";
            map.put("endTime",endTime);
            sql += " AND a.HsCreateDate <= TO_DATE(:endTime, 'yy-mm-dd hh24:mi:ss')";
            //sql += " AND CREATE_DATE <= :endTime";
        }
        if(StringUtils.isNotBlank(qvo.getBusinessTable())){
            map.put("businessTable","%"+qvo.getBusinessTable()+"%");
            sql += " AND a.businessTable like :businessTable";
        }
        if(StringUtils.isNotBlank(qvo.getUserName())){
            map.put("userName","%"+qvo.getUserName()+"%");
            sql += " AND a.userName like :userName";
        }
        if(StringUtils.isNotBlank(qvo.getBusinessName())){
            map.put("businessName","%"+qvo.getBusinessName()+"%");
            sql += " AND a.businessName like :businessName";
        }


        sql += "  ORDER BY a.HsCreateDate DESC";
        List<SysLogBusiness> list = sysDao.getServiceDo().findHqlMap(sql,map,qvo);

        return list;
    }


    public void addSysLogBusiness(SysLogBusiness po) throws Exception {

        sysDao.getServiceDo().add(po);
    }

    public void addSysLogBusinessItem(SysLogBusinessItem po) throws Exception {
        if(1==1)
            throw new Exception("11");
        sysDao.getServiceDo().add(po);
    }

    public void addSysLogBusinessItem(SysLogBusiness vo, SysLogBusinessItem po) throws Exception {

        addSysLogBusiness(vo);
        addSysLogBusinessItem(po);
    }

}
