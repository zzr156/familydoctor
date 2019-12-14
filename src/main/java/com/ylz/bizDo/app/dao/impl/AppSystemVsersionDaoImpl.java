package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppSystemVsersionDao;
import com.ylz.bizDo.app.entity.AppSystemVersionEntity;
import com.ylz.bizDo.app.po.AppSystemVsersion;
import com.ylz.bizDo.app.vo.AppSystemVesionQvo;
import com.ylz.bizDo.app.vo.AppSystemVsersionQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统版本更新
 */
@Service("appSystemVsersionDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSystemVsersionDaoImpl implements AppSystemVsersionDao {

    @Autowired
    private SysDao sysDao;


    @Override
    public List<AppSystemVsersion> findListQvo(AppSystemVesionQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SYSTEM_VSERSION  as a WHERE 1=1 ORDER BY a.CREATE_DATE DESC ";
        return sysDao.getServiceDo().findSqlMap(sql, map, AppSystemVsersion.class, qvo);
    }

    @Override
    public AppSystemVersionEntity findSystemVersion(AppSystemVsersionQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tt.SYSTEM_FORCE systemForce,\n" +
                "\t'' systemUpdate,\n" +
                "\tt.DOWN_LOAD_URL downLoadUrl,\n" +
                "\tt.UPDATE_TIME updateTime,\n" +
                "\tt.CHANGE_LOG changeLog,\n" +
                "\tt.VSERSION_CODE vsersionCode,\n" +
                "\tt.VSERSION_NAME vsersionName\n" +
                "FROM\n" +
                "\tAPP_SYSTEM_VSERSION t\n" +
                "WHERE 1=1\n";
        if(StringUtils.isNotBlank(qvo.getSystem())){
            map.put("SYSTEM",qvo.getSystem());
            sql += " AND t.SYSTEM = :SYSTEM";
        }
        if(StringUtils.isNotBlank(qvo.getType())){
            map.put("TYPE",qvo.getType());
            sql += " AND t.TYPE = :TYPE";
        }

//        if(StringUtils.isNotBlank(qvo.getVersion())){
//            map.put("VERSION",qvo.getVersion());
//            sql += " AND t.VSERSION_CODE > :VERSION";
//        }
        sql += " ORDER BY t.UPDATE_TIME DESC limit 1";
        List<AppSystemVersionEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppSystemVersionEntity.class);
        if(ls != null && ls.size()>0){
            return ls.get(0);
        }
        return null;
    }
}
