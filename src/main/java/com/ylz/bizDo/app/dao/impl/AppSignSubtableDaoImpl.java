package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppSignSubtableDao;
import com.ylz.bizDo.app.po.AppSignSubtable;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommSF;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/7/17.
 */
@Service("appSignSubtableDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSignSubtableDaoImpl implements AppSignSubtableDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppSignSubtable> findBySign(String signId, String type,String cType) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("signId",signId);
        String sql = "SELECT * FROM APP_SIGN_SUBTABLE WHERE 1=1 AND SIGN_ID=:signId ";
        if(StringUtils.isNotBlank(type)){
            map.put("type",type);
            sql += " AND TYPE =:type ";
        }
        if(CommSF.YES.getValue().equals(cType)){
            map.put("ctype",cType);
            sql += " AND IS_AUTOGRAPH =:ctype ";
        }
        List<AppSignSubtable> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignSubtable.class);
        return list;
    }
}
