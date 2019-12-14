package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppSignsWarningRecord;
import com.ylz.bizDo.jtapp.drEntity.AppDrTzEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrTzyjEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrTzyjSetEntity;
import com.ylz.bizDo.jtapp.drEntity.AppSignsWarningRecordEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrTzQvo;
import com.ylz.bizDo.jtapp.drVo.AppDrYjSetQvo;
import com.ylz.bizDo.jtapp.drVo.AppSignsWarQvo;

import java.util.List;

/**
 * Created by zzl on 2017/11/2.
 */
public interface AppSignsWarningSettingDao {
    /**
     * 医生设置所管居民未更新体征数据的预警时间
     * @param qvo
     * @return
     * @throws Exception
     */
    public String setSigns(AppDrYjSetQvo qvo) throws Exception;

    /**
     * 查询体征预警未及时更新数据
     * @return
     */
    public List<AppDrTzyjEntity> findTzyjList(String drId) throws Exception;

    public List<AppSignsWarningRecordEntity> findTzxxList(String drId) throws Exception;

    public AppSignsWarningRecord findOne(String drId, String userId, String disType) throws Exception;

    /**
     * 查询体征预警设置
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppDrTzyjSetEntity> findTzyjSet(AppDrTzQvo qvo) throws Exception;

    public  List<AppDrTzEntity> findPeopleList(AppDrTzQvo qvo) throws Exception;

    /**
     * 删除体征设置
     * @param qvo
     * @return
     * @throws Exception
     */
    public String delTzSet(AppDrTzQvo qvo) throws Exception;

}
