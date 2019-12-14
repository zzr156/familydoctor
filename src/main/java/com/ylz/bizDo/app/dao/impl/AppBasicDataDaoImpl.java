package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppBasicDataDao;
import com.ylz.bizDo.app.po.AppBasicData;
import com.ylz.packaccede.allDo.SysDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/4/13.
 */
@Service("appBasicDataDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppBasicDataDaoImpl implements AppBasicDataDao {
    @Autowired
    private SysDao sysDao;
    /**
     * 保存调取基卫相关数据
     * @param userId 请求人主键
     * @param userName 请求人姓名
     * @param requestData 请求参数
     * @param patientId 居民主键
     * @param data 请求返回数据
     * @param methodsType 方法类型
     */
    @Override
    public String saveBasicData(String userId, String userName, String requestData, String patientId, String data, String methodsType) throws Exception{
            AppBasicData ss = new AppBasicData();
            ss.setUserId(userId);
            ss.setUserName(userName);
            ss.setData(data);
            ss.setMethodsType(methodsType);
            ss.setPatientId(patientId);
            ss.setRequestData(requestData);
            sysDao.getServiceDo().add(ss);
            return ss.getId();
    }
    /**
     * 查询数据
     * @param patientId 居民主键
     * @param methodsType 方法类型
     * @return
     */
    @Override
    public AppBasicData findByPatientIdAndMethodsType(String patientId, String methodsType) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("patientId",patientId);
        map.put("methodsType",methodsType);
        String sql = "SELECT * FROM APP_BASIC_DATA WHERE 1=1 AND PATIENT_ID = :patientId AND METHODS_TYPE = :methodsType";
        List<AppBasicData> list = sysDao.getServiceDo().findSqlMap(sql,map,AppBasicData.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public AppBasicData findById(String id) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        String sql = "SELECT * FROM APP_BASIC_DATA WHERE 1=1 AND ID =:id";
        List<AppBasicData> list = sysDao.getServiceDo().findSqlMap(sql,map,AppBasicData.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
