package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppSignsWarningSettingDao;
import com.ylz.bizDo.app.po.AppSignsWarningRecord;
import com.ylz.bizDo.app.po.AppSignsWarningSetting;
import com.ylz.bizDo.jtapp.drEntity.AppDrTzEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrTzyjEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrTzyjSetEntity;
import com.ylz.bizDo.jtapp.drEntity.AppSignsWarningRecordEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrTzQvo;
import com.ylz.bizDo.jtapp.drVo.AppDrYjSetQvo;
import com.ylz.bizDo.jtapp.drVo.AppSignsWarQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zzl on 2017/11/2.
 */
@Service("appSignsWarningSettingDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSignsWarningSettingDaoImpl implements AppSignsWarningSettingDao {
    @Autowired
    private SysDao sysDao;

    /**
     * 医生设置所管居民未更新体征数据的预警时间
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String setSigns(AppDrYjSetQvo qvo) throws Exception {
        if(qvo.getGxy()!=null){
            AppSignsWarQvo gxy = qvo.getGxy();
            if("1".equals(gxy.getAllState())){
                AppSignsWarningSetting vv = findAppSignsWarn(gxy.getDrId(),"",gxy.getDisType());
                if(vv!=null){
                    if(StringUtils.isNotBlank(gxy.getGreen())){
                        vv.setSwsGreenSet(gxy.getGreen());
                    }
                    if(StringUtils.isNotBlank(gxy.getRed())){
                        vv.setSwsRedSet(gxy.getRed());
                    }
                    if(StringUtils.isNotBlank(gxy.getYellow())){
                        vv.setSwsYellowSet(gxy.getYellow());
                    }
                    if(StringUtils.isNotBlank(gxy.getRedSwitch())){
                        vv.setSwsRedSwitch(gxy.getRedSwitch());
                    }
                    if(StringUtils.isNotBlank(gxy.getYellowSwitch())){
                        vv.setSwsYellowSwitch(gxy.getYellowSwitch());
                    }
                    if(StringUtils.isNotBlank(gxy.getGreenSwitch())){
                        vv.setSwsGreenSwitch(gxy.getGreenSwitch());
                    }
                    if(StringUtils.isNotBlank(gxy.getGray())){
                        vv.setSwsGraySet(gxy.getGray());
                    }
                    if(StringUtils.isNotBlank(gxy.getGraySwitch())){
                        vv.setSwsGraySwitch(gxy.getGraySwitch());
                    }
                    if(StringUtils.isNotBlank(gxy.getFirstDay())){
                        vv.setSwsFirstSet(gxy.getFirstDay());
                    }
                    if(StringUtils.isNotBlank(gxy.getOpenState())){
                        vv.setSwsOpenState(gxy.getOpenState());
                    }
                    sysDao.getServiceDo().modify(vv);
                }else{
                    //通用设置
                    AppSignsWarningSetting vo = new AppSignsWarningSetting();
                    vo.setSwsCreateId(gxy.getDrId());
                    vo.setSwsCreateTime(Calendar.getInstance());
                    vo.setSwsGreenSet(gxy.getGreen());
                    vo.setSwsOpenState(gxy.getOpenState());
                    vo.setSwsRedSet(gxy.getRed());
                    vo.setSwsYellowSet(gxy.getYellow());
                    vo.setSwsDisType(gxy.getDisType());
                    vo.setSwsRedSwitch(gxy.getRedSwitch());
                    vo.setSwsYellowSwitch(gxy.getYellowSwitch());
                    vo.setSwsGreenSwitch(gxy.getGreenSwitch());
                    vo.setSwsGraySet(gxy.getGray());
                    vo.setSwsGraySwitch(gxy.getGraySwitch());
                    vo.setSwsType("1");//通用
                    vo.setSwsFirstSet(gxy.getFirstDay());
                    sysDao.getServiceDo().add(vo);
                }
            }else{
                if(StringUtils.isNotBlank(gxy.getPatientId())){
                    String[] patientIds = gxy.getPatientId().split(";");
                    for(String patientId:patientIds){
                        AppSignsWarningSetting vv = findAppSignsWarn(gxy.getDrId(),patientId,gxy.getDisType());
                        if(vv!=null){
                            if(StringUtils.isNotBlank(gxy.getGreen())){
                                vv.setSwsGreenSet(gxy.getGreen());
                            }
                            if(StringUtils.isNotBlank(gxy.getRed())){
                                vv.setSwsRedSet(gxy.getRed());
                            }
                            if(StringUtils.isNotBlank(gxy.getYellow())){
                                vv.setSwsYellowSet(gxy.getYellow());
                            }
                            if(StringUtils.isNotBlank(gxy.getRedSwitch())){
                                vv.setSwsRedSwitch(gxy.getRedSwitch());
                            }
                            if(StringUtils.isNotBlank(gxy.getYellowSwitch())){
                                vv.setSwsYellowSwitch(gxy.getYellowSwitch());
                            }
                            if(StringUtils.isNotBlank(gxy.getGreenSwitch())){
                                vv.setSwsGreenSwitch(gxy.getGreenSwitch());
                            }
                            if(StringUtils.isNotBlank(gxy.getGray())){
                                vv.setSwsGraySet(gxy.getGray());
                            }
                            if(StringUtils.isNotBlank(gxy.getGraySwitch())){
                                vv.setSwsGraySwitch(gxy.getGraySwitch());
                            }
                            if(StringUtils.isNotBlank(gxy.getFirstDay())){
                                vv.setSwsFirstSet(gxy.getFirstDay());
                            }
                            if(StringUtils.isNotBlank(gxy.getOpenState())){
                                vv.setSwsOpenState(gxy.getOpenState());
                            }
                            sysDao.getServiceDo().modify(vv);
                        }else{
                            AppSignsWarningSetting vo = new AppSignsWarningSetting();
                            vo.setSwsCreateId(gxy.getDrId());
                            vo.setSwsCreateTime(Calendar.getInstance());
                            vo.setSwsGreenSet(gxy.getGreen());
                            vo.setSwsOpenState(gxy.getOpenState());
                            vo.setSwsRedSet(gxy.getRed());
                            vo.setSwsUserId(patientId);
                            vo.setSwsYellowSet(gxy.getYellow());
                            vo.setSwsFirstSet(gxy.getFirstDay());
                            vo.setSwsDisType(gxy.getDisType());
                            vo.setSwsRedSwitch(gxy.getRedSwitch());
                            vo.setSwsYellowSwitch(gxy.getYellowSwitch());
                            vo.setSwsGreenSwitch(gxy.getGreenSwitch());
                            vo.setSwsGraySet(gxy.getGray());
                            vo.setSwsGraySwitch(gxy.getGraySwitch());
                            sysDao.getServiceDo().add(vo);
                        }
                    }
                }
            }
        }
        if(qvo.getTnb()!=null){
            AppSignsWarQvo gxy = qvo.getTnb();
            if("1".equals(gxy.getAllState())){
                AppSignsWarningSetting vv = findAppSignsWarn(gxy.getDrId(),"",gxy.getDisType());
                if(vv!=null){
                    if(StringUtils.isNotBlank(gxy.getGreen())){
                        vv.setSwsGreenSet(gxy.getGreen());
                    }
                    if(StringUtils.isNotBlank(gxy.getRed())){
                        vv.setSwsRedSet(gxy.getRed());
                    }
                    if(StringUtils.isNotBlank(gxy.getYellow())){
                        vv.setSwsYellowSet(gxy.getYellow());
                    }
                    if(StringUtils.isNotBlank(gxy.getRedSwitch())){
                        vv.setSwsRedSwitch(gxy.getRedSwitch());
                    }
                    if(StringUtils.isNotBlank(gxy.getYellowSwitch())){
                        vv.setSwsYellowSwitch(gxy.getYellowSwitch());
                    }
                    if(StringUtils.isNotBlank(gxy.getGreenSwitch())){
                        vv.setSwsGreenSwitch(gxy.getGreenSwitch());
                    }
                    if(StringUtils.isNotBlank(gxy.getGray())){
                        vv.setSwsGraySet(gxy.getGray());
                    }
                    if(StringUtils.isNotBlank(gxy.getGraySwitch())){
                        vv.setSwsGraySwitch(gxy.getGraySwitch());
                    }
                    if(StringUtils.isNotBlank(gxy.getFirstDay())){
                        vv.setSwsFirstSet(gxy.getFirstDay());
                    }
                    if(StringUtils.isNotBlank(gxy.getOpenState())){
                        vv.setSwsOpenState(gxy.getOpenState());
                    }
                    sysDao.getServiceDo().modify(vv);
                }else{
                    //通用设置
                    AppSignsWarningSetting vo = new AppSignsWarningSetting();
                    vo.setSwsCreateId(gxy.getDrId());
                    vo.setSwsCreateTime(Calendar.getInstance());
                    vo.setSwsGreenSet(gxy.getGreen());
                    vo.setSwsOpenState(gxy.getOpenState());
                    vo.setSwsRedSet(gxy.getRed());
                    vo.setSwsYellowSet(gxy.getYellow());
                    vo.setSwsDisType(gxy.getDisType());
                    vo.setSwsRedSwitch(gxy.getRedSwitch());
                    vo.setSwsYellowSwitch(gxy.getYellowSwitch());
                    vo.setSwsGreenSwitch(gxy.getGreenSwitch());
                    vo.setSwsGraySet(gxy.getGray());
                    vo.setSwsGraySwitch(gxy.getGraySwitch());
                    vo.setSwsFirstSet(gxy.getFirstDay());
                    vo.setSwsType("1");//通用
                    sysDao.getServiceDo().add(vo);
                }
            }else{
                if(StringUtils.isNotBlank(gxy.getPatientId())){
                    String[] patientIds = gxy.getPatientId().split(";");
                    for(String patientId:patientIds){
                        AppSignsWarningSetting vv = findAppSignsWarn(gxy.getDrId(),patientId,gxy.getDisType());
                        if(vv!=null){
                            if(StringUtils.isNotBlank(gxy.getGreen())){
                                vv.setSwsGreenSet(gxy.getGreen());
                            }
                            if(StringUtils.isNotBlank(gxy.getRed())){
                                vv.setSwsRedSet(gxy.getRed());
                            }
                            if(StringUtils.isNotBlank(gxy.getYellow())){
                                vv.setSwsYellowSet(gxy.getYellow());
                            }
                            if(StringUtils.isNotBlank(gxy.getRedSwitch())){
                                vv.setSwsRedSwitch(gxy.getRedSwitch());
                            }
                            if(StringUtils.isNotBlank(gxy.getYellowSwitch())){
                                vv.setSwsYellowSwitch(gxy.getYellowSwitch());
                            }
                            if(StringUtils.isNotBlank(gxy.getGreenSwitch())){
                                vv.setSwsGreenSwitch(gxy.getGreenSwitch());
                            }
                            if(StringUtils.isNotBlank(gxy.getGray())){
                                vv.setSwsGraySet(gxy.getGray());
                            }
                            if(StringUtils.isNotBlank(gxy.getGraySwitch())){
                                vv.setSwsGraySwitch(gxy.getGraySwitch());
                            }
                            if(StringUtils.isNotBlank(gxy.getFirstDay())){
                                vv.setSwsFirstSet(gxy.getFirstDay());
                            }
                            if(StringUtils.isNotBlank(gxy.getOpenState())){
                                vv.setSwsOpenState(gxy.getOpenState());
                            }
                            sysDao.getServiceDo().modify(vv);
                        }else{
                            AppSignsWarningSetting vo = new AppSignsWarningSetting();
                            vo.setSwsCreateId(gxy.getDrId());
                            vo.setSwsCreateTime(Calendar.getInstance());
                            vo.setSwsGreenSet(gxy.getGreen());
                            vo.setSwsOpenState(gxy.getOpenState());
                            vo.setSwsRedSet(gxy.getRed());
                            vo.setSwsUserId(patientId);
                            vo.setSwsYellowSet(gxy.getYellow());
                            vo.setSwsFirstSet(gxy.getFirstDay());
                            vo.setSwsDisType(gxy.getDisType());
                            vo.setSwsRedSwitch(gxy.getRedSwitch());
                            vo.setSwsYellowSwitch(gxy.getYellowSwitch());
                            vo.setSwsGreenSwitch(gxy.getGreenSwitch());
                            vo.setSwsGraySet(gxy.getGray());
                            vo.setSwsGraySwitch(gxy.getGraySwitch());
                            sysDao.getServiceDo().add(vo);
                        }
                    }
                }
            }
        }

        /*if("1".equals(qvo.getAllState())){//设置全部人员
            //查询医生所管的居民
          *//*  Map<String,Object> map = new HashMap<String,Object>();
            map.put("drId",qvo.getDrId());
            map.put("signState", SignFormType.YQY.getValue());
            map.put("xqState","0");
            String sql = "SELECT\n" +
                    "\ta.*\n" +
                    "FROM\n" +
                    "\tAPP_PATIENT_USER a\n" +
                    "INNER JOIN APP_SIGN_FORM b ON a.ID = b.SIGN_PATIENT_ID\n" +
                    "WHERE 1=1\n" +
                    "\tAND b.SIGN_DR_ID =:drId " +
                    "AND b.SIGN_CONTRACT_STATE =:xqState " +
                    "AND b.SIGN_STATE=:signState";
            List<AppPatientUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppPatientUser.class);
            if(ls!=null && ls.size()>0){
                for(AppPatientUser ll:ls){
                    AppSignsWarningSetting vv = findAppSignsWarn(qvo.getDrId(),ll.getId(),qvo.getDisType());
                    if(vv!=null){
                        vv.setSwsGreenSet(qvo.getGreen());
                        vv.setSwsOpenState(qvo.getOpenState());
                        vv.setSwsRedSet(qvo.getRed());
                        vv.setSwsYellowSet(qvo.getYellow());
                        vv.setSwsFirstSet(qvo.getFirstDay());
                        sysDao.getServiceDo().modify(vv);
                    }else{
                        AppSignsWarningSetting vo = new AppSignsWarningSetting();
                        vo.setSwsCreateId(qvo.getDrId());
                        vo.setSwsCreateTime(Calendar.getInstance());
                        vo.setSwsGreenSet(qvo.getGreen());
                        vo.setSwsOpenState(qvo.getOpenState());
                        vo.setSwsRedSet(qvo.getRed());
                        vo.setSwsUserId(ll.getId());
                        vo.setSwsYellowSet(qvo.getYellow());
                        vo.setSwsFirstSet(qvo.getFirstDay());
                        vo.setSwsDisType(qvo.getDisType());
                        vo.setSwsType("1");//通用
                        sysDao.getServiceDo().add(vo);
                    }
                }
            }*//*

        }else{
            if(StringUtils.isNotBlank(qvo.getPatientId())){
                String[] patientIds = qvo.getPatientId().split(";");
                for(String patientId:patientIds){
                    AppSignsWarningSetting vv = findAppSignsWarn(qvo.getDrId(),patientId,qvo.getDisType());
                    if(vv!=null){
                        if(StringUtils.isNotBlank(qvo.getGreen())){
                            vv.setSwsGreenSet(qvo.getGreen());
                        }
                        if(StringUtils.isNotBlank(qvo.getRed())){
                            vv.setSwsRedSet(qvo.getRed());
                        }
                        if(StringUtils.isNotBlank(qvo.getYellow())){
                            vv.setSwsYellowSet(qvo.getYellow());
                        }
                        if(StringUtils.isNotBlank(qvo.getRedSwitch())){
                            vv.setSwsRedSwitch(qvo.getRedSwitch());
                        }
                        if(StringUtils.isNotBlank(qvo.getYellowSwitch())){
                            vv.setSwsYellowSwitch(qvo.getYellowSwitch());
                        }
                        if(StringUtils.isNotBlank(qvo.getGreenSwitch())){
                            vv.setSwsGreenSwitch(qvo.getGreenSwitch());
                        }
                        sysDao.getServiceDo().modify(vv);
                    }else{
                        AppSignsWarningSetting vo = new AppSignsWarningSetting();
                        vo.setSwsCreateId(qvo.getDrId());
                        vo.setSwsCreateTime(Calendar.getInstance());
                        vo.setSwsGreenSet(qvo.getGreen());
                        vo.setSwsOpenState(qvo.getOpenState());
                        vo.setSwsRedSet(qvo.getRed());
                        vo.setSwsUserId(patientId);
                        vo.setSwsYellowSet(qvo.getYellow());
                        vo.setSwsFirstSet(qvo.getFirstDay());
                        vo.setSwsDisType(qvo.getDisType());
                        vo.setSwsRedSwitch(qvo.getRedSwitch());
                        vo.setSwsYellowSwitch(qvo.getYellowSwitch());
                        vo.setSwsGreenSwitch(qvo.getGreenSwitch());
                        sysDao.getServiceDo().add(vo);
                    }
                }
            }
        }*/
        return "true";
    }

    /**
     * 根据医生id 患者id查询预警表记录
     * @param drId
     * @param userId
     * @return
     */
    public AppSignsWarningSetting findAppSignsWarn(String drId,String userId,String disType) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",drId);
        map.put("disType",disType);
        String sql = "SELECT * FROM APP_SIGNS_WARNING_SETTING " +
                "WHERE 1=1 AND SWS_CREATE_ID=:drId" +
                " AND SWS_DIS_TYPE=:disType";
        if(StringUtils.isNotBlank(userId)){
            map.put("userId",userId);
            sql+=" AND SWS_USER_ID=:userId";
        }else{
            sql +=" AND SWS_TYPE ='1'";
        }
        List<AppSignsWarningSetting> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignsWarningSetting.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;

    }

    /**
     * 查询体征未及时更新数据
     * @return
     */
    @Override
    public List<AppDrTzyjEntity> findTzyjList(String drId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",drId);
        String sql = "SELECT\n" +
                "\ta.SIGN_DR_ID drId,\n" +
                "\ta.SIGN_PATIENT_ID patientId,\n" +
                "\tb.LABEL_VALUE disType,\n" +
                "\tb.LABEL_COLOR color,\n" +
                "\t'' dayNum," +
                "a.SIGN_TEAM_ID teamId\n" +
                "\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "INNER JOIN APP_LABEL_DISEASE b ON a.ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\tb.LABEL_TYPE = '2'\n" +
                "AND a.SIGN_DR_ID = :drId\n" +
                "AND b.LABEL_VALUE IN ('201','202')";
        List<AppDrTzyjEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrTzyjEntity.class);
        List<AppDrTzyjEntity> list = new ArrayList<AppDrTzyjEntity>();
        if(ls!=null && ls.size()>0){
            for(AppDrTzyjEntity ll:ls){
                AppSignsWarningSetting vv = findAppSignsWarn(ll.getDrId(),ll.getPatientId(),ll.getDisType());
                if(vv==null){
                    vv = findAppSignsWarn(ll.getDrId(),"",ll.getDisType());
                }
                if(vv!=null){
                    if("red".equals(ll.getColor())){
                        if("1".equals(vv.getSwsRedSwitch())){
                            if(StringUtils.isNotBlank(vv.getSwsRedSet())){
                                if(Integer.parseInt(vv.getSwsRedSet())>0){
                                    if(Integer.parseInt(ll.getDayNum())>Integer.parseInt(vv.getSwsRedSet())){
                                        list.add(ll);
                                    }
                                }
                            }
                        }else if("1".equals(vv.getSwsOpenState())){
                            if(StringUtils.isNotBlank(vv.getSwsFirstSet())){
                                if(Integer.parseInt(vv.getSwsFirstSet())>0){
                                    if(Integer.parseInt(ll.getDayNum())>Integer.parseInt(vv.getSwsFirstSet())){
                                        list.add(ll);
                                    }
                                }
                            }
                        }
                    }else if("yellow".equals(ll.getColor()) ){
                        if("1".equals(vv.getSwsYellowSwitch())){
                            if(StringUtils.isNotBlank(vv.getSwsYellowSet())){
                                if(Integer.parseInt(vv.getSwsYellowSet())>0){
                                    if(Integer.parseInt(ll.getDayNum())>Integer.parseInt(vv.getSwsYellowSet())){
                                        list.add(ll);
                                    }
                                }
                            }
                        }else if("1".equals(vv.getSwsOpenState())){
                            if(StringUtils.isNotBlank(vv.getSwsFirstSet())){
                                if(Integer.parseInt(vv.getSwsFirstSet())>0){
                                    if(Integer.parseInt(ll.getDayNum())>Integer.parseInt(vv.getSwsFirstSet())){
                                        list.add(ll);
                                    }
                                }
                            }
                        }

                    }else if("green".equals(ll.getColor())){
                        if("1".equals(vv.getSwsGreenSwitch())){
                            if(StringUtils.isNotBlank(vv.getSwsGreenSet())){
                                if(Integer.parseInt(vv.getSwsGreenSet())>0){
                                    if(StringUtils.isNotBlank(ll.getDayNum())){
                                        if(Integer.parseInt(ll.getDayNum())>Integer.parseInt(vv.getSwsGreenSet())){
                                            list.add(ll);
                                        }
                                    }
                                }
                            }
                        }else if("1".equals(vv.getSwsOpenState())){
                            if(StringUtils.isNotBlank(vv.getSwsFirstSet())){
                                if(Integer.parseInt(vv.getSwsFirstSet())>0){
                                    if(StringUtils.isNotBlank(ll.getDayNum())){
                                        if(Integer.parseInt(ll.getDayNum())>Integer.parseInt(vv.getSwsFirstSet())){
                                            list.add(ll);
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        if("1".equals(vv.getSwsOpenState())){
                            if(StringUtils.isNotBlank(vv.getSwsFirstSet())){
                                if(Integer.parseInt(vv.getSwsFirstSet())>0){
                                    if(StringUtils.isNotBlank(ll.getDayNum())){
                                        if(Integer.parseInt(ll.getDayNum())>Integer.parseInt(vv.getSwsFirstSet())){
                                            list.add(ll);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<AppSignsWarningRecordEntity> findTzxxList(String drId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",drId);
        String sql = "SELECT ID id,SWR_DR_ID drId,SWR_USER_ID userId,SWR_DIS_TYPE disType," +
                "SWR_COLOR color,SWR_DAY_NUM dayNum,SWR_CREATE_TIME createTime " +
                "FROM APP_SIGNS_WARNING_RECORD WHERE 1=1 " +
                "AND SWR_DR_ID=:drId AND SWR_TX_STATE ='0'";
        List<AppSignsWarningRecordEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppSignsWarningRecordEntity.class);
        return list;
    }

    @Override
    public AppSignsWarningRecord findOne(String drId, String userId, String disType) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",drId);
        map.put("userId",userId);
        map.put("disType",disType);
        map.put("txState","0");
        String sql = "SELECT * FROM APP_SIGNS_WARNING_RECORD WHERE 1=1 " +
                "AND SWR_DR_ID=:drId AND SWR_USER_ID=:userId AND SWR_DIS_TYPE=:disType AND SWR_TX_STATE=:txState";
        List<AppSignsWarningRecord> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignsWarningRecord.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询体征预警设置
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppDrTzyjSetEntity> findTzyjSet(AppDrTzQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
       // map.put("disType",qvo.getDisType());
        String sql = "SELECT ID id," +
                "SWS_RED_SET red," +
                "SWS_YELLOW_SET yellow," +
                "SWS_GREEN_SET green," +
                "SWS_OPEN_STATE openState," +
                "SWS_DIS_TYPE disType," +
                "SWS_RED_SWITCH redSwitch," +
                "SWS_YELLOW_SWITCH yellowSwitch," +
                "SWS_GREEN_SWITCH greenSwitch," +
                "SWS_GRAY_SET gray," +
                "SWS_FIRST_SET firstDay," +
                "SWS_GRAY_SWITCH graySwitch " +
                "FROM APP_SIGNS_WARNING_SETTING WHERE 1=1 AND SWS_CREATE_ID=:drId ";
        if(StringUtils.isNotBlank(qvo.getDisType())){
            map.put("disType",qvo.getDisType());
            sql += " AND SWS_DIS_TYPE=:disType ";
        }else{
            sql +="AND SWS_DIS_TYPE IN ('201','202')";
        }
        /*if("1".equals(qvo.getType())){//查询通用
                map.put("type","1");
                sql+=" AND SWS_TYPE =:type";
        }else*/
        if(StringUtils.isNotBlank(qvo.getPatientId())){//查询个人
                map.put("patientId",qvo.getPatientId());
                sql += " AND SWS_USER_ID=:patientId ";
        }else{
            map.put("type","1");
            sql+=" AND SWS_TYPE =:type";
        }

        List<AppDrTzyjSetEntity> list= sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrTzyjSetEntity.class);
        return list;
    }

    @Override
    public List<AppDrTzEntity> findPeopleList(AppDrTzQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        String sql = "SELECT c.ID userId," +
                "c.PATIENT_NAME userName," +
                "c.PATIENT_IMAGEURL imageUrl," +
                "c.PATIENT_GENDER sex," +
                "a.SIGN_DR_ID drId," +
                "(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID and g.LABEL_VALUE =b.LABEL_VALUE) disColor," +
                "(SELECT GROUP_CONCAT(LABEL_VALUE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID and g.LABEL_VALUE =b.LABEL_VALUE) disType," +
                "(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID and g.LABEL_VALUE =b.LABEL_VALUE) disTypeName, " +
                "'' age," +
                "'' dayNum " +
                "FROM " +
                "APP_SIGN_FORM a " +
                "LEFT JOIN APP_LABEL_DISEASE b ON a.ID = b.LABEL_SIGN_ID " +
                "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID " +
                "where 1=1 and a.SIGN_STATE='2' AND b.LABEL_VALUE IN ('201','202') AND a.SIGN_CONTRACT_STATE='0' AND a.SIGN_DR_ID=:drId " +
                "AND c.ID IN (SELECT d.SWS_USER_ID FROM APP_SIGNS_WARNING_SETTING d WHERE d.SWS_CREATE_ID =:drId AND d.SWS_DIS_TYPE=b.LABEL_VALUE)";
            List<AppDrTzEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrTzEntity.class);
        return list;
    }

    /**
     * 删除体征设置
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String delTzSet(AppDrTzQvo qvo) throws Exception {
        if(StringUtils.isNotBlank(qvo.getPatientId())){
            AppSignsWarningSetting vo = findAppSignsWarn(qvo.getDrId(),qvo.getPatientId(),qvo.getDisType());
            if(vo!=null){
                sysDao.getServiceDo().delete(vo);
                return "true";
            }
        }
        return "false";
    }
}
