package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppWorkTypeDao;
import com.ylz.bizDo.app.po.AppWorkType;
import com.ylz.bizDo.app.vo.AppWorkTypeQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/8/17.
 */
@Service("appWorkTypeDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppWorkTypeDaoImpl implements AppWorkTypeDao {
    @Autowired
    private SysDao sysDao;
    /**
     * 初始查询
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppWorkType> findList(AppWorkTypeQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_WORK_TYPE WHERE 1=1";
        if(StringUtils.isNotBlank(qvo.getContent())){
            map.put("content","%"+qvo.getContent()+"%");
            sql += " AND WORK_TITLE LIKE :content";
        }
        sql += " ORDER BY WORK_VALUE";
        List<AppWorkType> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppWorkType.class,qvo);
        return ls;
    }

    @Override
    public List<AppWorkType> findAllList() throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_WORK_TYPE WHERE 1=1";
        sql += " ORDER BY WORK_VALUE";
        List<AppWorkType> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppWorkType.class);
        return ls;
    }
}
