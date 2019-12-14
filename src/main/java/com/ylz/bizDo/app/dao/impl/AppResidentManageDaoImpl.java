package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppResidentManageDao;
import com.ylz.bizDo.app.po.AppResidentManage;
import com.ylz.bizDo.app.vo.AppResidentManageQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/14.
 */
@Service("appResidentManageDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppResidentManageDaoImpl implements AppResidentManageDao {

    @Autowired
    private SysDao sysDao;


    @Override
    public List<AppResidentManage> findAll(AppResidentManageQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_RESIDENT_MANAGE WHERE 1=1";
        List<AppResidentManage> ls = this.sysDao.getServiceDo().findSqlMap(sql,map,AppResidentManage.class,qvo);
        if(ls!=null){
            return ls;
        }
        return null;
    }
}
