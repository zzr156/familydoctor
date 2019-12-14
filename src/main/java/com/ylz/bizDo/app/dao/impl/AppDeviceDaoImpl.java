package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppDeviceDao;
import com.ylz.bizDo.app.po.AppDevice;
import com.ylz.bizDo.app.vo.AppDeviceQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppDeviceEntity;
import com.ylz.bizDo.jtapp.commonEntity.DeviceEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.commonVo.AppDeviceVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonDeviceType;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.util.HtmlUtils;
import com.ylz.packcommon.common.util.PropertiesUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/17.
 */
@Service("appDeviceDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppDeviceDaoImpl implements AppDeviceDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppDevice> findListQvo(AppDeviceQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_DEVICE  as a WHERE 1=1 ";
        return sysDao.getServiceDo().findSqlMap(sql, map, AppDevice.class, qvo);
    }

    @Override
    public List<AppDeviceEntity> findByType(AppDeviceVo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
       map.put("type",qvo.getType());
       String sql = "SELECT a.ID id,a.DEV_NAME devName,a.DEV_IMAGE_URL devImageUrl,a.DEV_TYPE devType,a.DEV_FACTORY_MODEL devFactoryModel FROM APP_DEVICE AS a WHERE 1=1";
       sql +=" AND a.DEV_TYPE =:type";
       return sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDeviceEntity.class);
    }

    @Override
    public List<DeviceEntity> findLike(AppCommQvo vo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();

        String sql1 = "SELECT a.ID id,a.BP_DEV_ID sim,a.BP_TYPE type,b.PATIENT_NAME user,DATE_FORMAT(a.BP_CREATE_TIME,'%Y-%c-%d %h:%i:%s') createTime,DATE_FORMAT(a.BP_DEV_TIME_ONE,'%Y-%c-%d %h:%i:%s') bindTime " +
                " FROM APP_BLOODPRESSURE a,APP_PATIENT_USER b  WHERE a.BP_USER_ONE IS NOT NULL AND  a.BP_USER_ONE = b.ID  ";

        String sql2 = "SELECT a.ID id,a.BP_DEV_ID sim,a.BP_TYPE type, '' user,DATE_FORMAT(a.BP_CREATE_TIME,'%Y-%c-%d %h:%i:%s') createTime,'' bindTime " +
                " FROM APP_BLOODPRESSURE a WHERE a.BP_USER_ONE IS NULL AND a.BP_USER_TWO IS NULL ";

        String sql3 = "SELECT a.ID id,a.BP_DEV_ID sim,a.BP_TYPE type, b.PATIENT_NAME user,DATE_FORMAT(a.BP_CREATE_TIME,'%Y-%c-%d %h:%i:%s') createTime,DATE_FORMAT(a.BP_DEV_TIME_TWO,'%Y-%c-%d %h:%i:%s') bindTime " +
                " FROM APP_BLOODPRESSURE a,APP_PATIENT_USER b  WHERE a.BP_USER_TWO = b.ID  ";

        String sql4 = "SELECT a.ID id,a.BG_DEV_ID sim,a.BG_TYPE type, b.PATIENT_NAME user,DATE_FORMAT(a.BG_CREATE_TIME,'%Y-%c-%d %h:%i:%s') createTime,DATE_FORMAT(a.BG_DEV_TIME,'%Y-%c-%d %h:%i:%s') bindTime " +
                " FROM APP_BLOODGLU a,APP_PATIENT_USER b  WHERE a.BG_PAIENT_ID IS NOT NULL AND a.BG_PAIENT_ID = b.ID  ";

        String sql5 = "SELECT a.ID id,a.BG_DEV_ID sim,a.BG_TYPE type, '' user,DATE_FORMAT(a.BG_CREATE_TIME,'%Y-%c-%d %h:%i:%s') createTime,'' bindTime " +
                " FROM APP_BLOODGLU a WHERE a.BG_PAIENT_ID IS NULL  ";

        if(StringUtils.isNotBlank(vo.getSerachValue())){
            map.put("seachValue","%"+vo.getSerachValue()+"%");
            sql1+= " AND (b.PATIENT_NAME like :seachValue OR a.BP_DEV_ID like :seachValue) ";
            sql2+=" AND a.BP_DEV_ID like :seachValue ";
            sql3 +=" AND (b.PATIENT_NAME like :seachValue OR a.BP_DEV_ID like :seachValue)";
            sql4 +=" AND (b.PATIENT_NAME like :seachValue OR a.BG_DEV_ID like :seachValue) ";
            sql5 +=" AND a.BG_DEV_ID like :seachValue ";
        }
        if(StringUtils.isNotBlank(vo.getTeamId())){
            map.put("teamId",vo.getTeamId());
            sql1+= " AND a.BP_TEAM_ID = :teamId ";
            sql2+= " AND a.BP_TEAM_ID = :teamId ";
            sql3 += " AND a.BP_TEAM_ID = :teamId ";
            sql4 += " AND a.BG_TEAM_ID = :teamId ";
            sql5 +=" AND a.BG_TEAM_ID = :teamId ";
        }
        List<DeviceEntity> list1 = sysDao.getServiceDo().findSqlMapRVo(sql1,map,DeviceEntity.class,vo);
        List<DeviceEntity> list2 = sysDao.getServiceDo().findSqlMapRVo(sql2,map,DeviceEntity.class,vo);
        List<DeviceEntity> list3 = sysDao.getServiceDo().findSqlMapRVo(sql3,map,DeviceEntity.class,vo);
        List<DeviceEntity> list4 = sysDao.getServiceDo().findSqlMapRVo(sql4,map,DeviceEntity.class,vo);
        List<DeviceEntity> list5 = sysDao.getServiceDo().findSqlMapRVo(sql5,map,DeviceEntity.class,vo);
        List<DeviceEntity> returnList = new ArrayList<>();
        if(list1!=null){
            returnList.addAll(list1);
        }
        if(list2!=null){
            returnList.addAll(list2);
        }
        if(list3!=null){
            returnList.addAll(list3);
        }
        if(list4!=null){
            returnList.addAll(list4);
        }
        if(list5!=null){
            returnList.addAll(list5);
        }
        return returnList;
    }

    @Override
    public boolean verification(String devCode, String type) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("icSerial",devCode);
        if(StringUtils.isNotBlank(type)){
            //血压
            if(CommonDeviceType.XY.getValue().equals(type)){
                jsonParam.put("icSerialType","98");
            }
            //血糖
            if(CommonDeviceType.XT.getValue().equals(type)){
                jsonParam.put("icSerialType","99");
            }
        }
        String url = PropertiesUtil.getConfValue("verificationIp")+"/iccard/controllerAction.action?act=CardVerificationAll&strJson="+jsonParam;
        String str = HtmlUtils.getInstance().loadURL( url);
        if(StringUtils.isNotBlank(str)){
            JSONObject jsonall = JSONObject.fromObject(str);
            if(jsonall.get("code") != null) {
                if (jsonall.get("code").toString().equals("800")) {
                    return true;
                }
            }
        }
        return false;
    }
}
