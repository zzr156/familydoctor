package com.ylz.packaccede.fiter;

import com.ylz.bizDo.app.dao.AppDrPatientKeyDao;
import com.ylz.bizDo.app.dao.AppPatientUserDao;
import com.ylz.bizDo.app.po.AppDrPatientKey;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.cd.dao.SysLogAddDo;
import com.ylz.bizDo.cd.dao.SysLogDeletDo;
import com.ylz.bizDo.cd.dao.SysLogModifyDo;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.common.bizDo.ServiceDo;
import com.ylz.packcommon.common.Constant;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import com.ylz.packcommon.common.util.JacksonUtils;
import com.ylz.packcommon.common.util.JsonUtil;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * zk
 */
public class LogEntityInterceptor extends EmptyInterceptor {
    private static final long serialVersionUID = 1L;
    String slog="";

    private org.slf4j.Logger logger = LoggerFactory.getLogger(LogEntityInterceptor.class);

    /**
     * 删除前
     * @param entity
     * @param id
     * @param state
     * @param propertyNames
     * @param types
     */
    public void onDelete(Object entity,Serializable id, Object[] state,String[] propertyNames, Type[] types){
        try {
            if(entity.getClass().getName().startsWith("com.ylz.bizDo.cd.po")){
                return;
            }
            String ss = getJsonString(state,propertyNames);
            String slog ="";
            if(RequestContextHolder.getRequestAttributes()!=null) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                Map<String, String[]> map = request.getParameterMap();
                CdUser user = ((CdUser) (request.getSession().getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF)));
                if(user != null){
                    if(map != null && map.size() >0) {
                        String[] act = map.get("act");
                        if(act != null && act.length >0){
                            String keyAct = act[0];
                            slog="userid:"+user.getUserId()+";"+user.getUserName() + "删除实体:" + entity.getClass().getName() + "; id:" + id + "；数据："+ss;
                            SysLogDeletDo dao= (SysLogDeletDo) SpringHelper.getBean("sysLogDeletDo");
                            dao.addSysLogDelet(user.getUserId(),user.getUserName(),entity.getClass().getName(), String.valueOf(id),ss,keyAct);
                        }
                    }
                }else{
                    if(map != null && map.size() >0){
                        String[] token = map.get("token");
                        String[] act = map.get("act");
                        if(token != null && token.length >0){
                            String keyAct = act[0];
                            SysDao sysDao = (SysDao) SpringHelper.getBean("sysDao");
                            String keyToken = token[0];
                            if(StringUtils.isNotBlank(keyToken)){
                                String rule = PropertiesUtil.getConfValue("ruleAuthorization");
                                StringBuilder sb = new StringBuilder(keyToken);
                                for(int i = rule.length();i>0;i--){
                                    sb.deleteCharAt(Integer.parseInt(rule.substring(i-1,i)));
                                }
                                keyToken = sb.toString();
                                Map<String,Object> key = sysDao.getAppDrPatientKeyDao().findDrPatientKeyByTokenSql(keyToken);
                                if(key != null){
                                    String drPatientId = key.get("DR_PATIENT_ID").toString();
                                    String drPatientType = key.get("DR_PATIENT_TYPE").toString();
                                    String userName = null;
                                    if(drPatientType.equals(CommonShortType.HUANGZHE.getValue())){
                                        Map<String,Object> appPatientUser = sysDao.getAppPatientUserDao().findPatentUserName(drPatientId);
                                        if(appPatientUser.get("PATIENT_NAME") != null) {
                                            userName = appPatientUser.get("PATIENT_NAME").toString();
                                        }
                                    }else if(drPatientType.equals(CommonShortType.YISHENG.getValue())){
                                        Map<String,Object> drUser = sysDao.getAppDrUserDao().findDrUserName(drPatientId);
                                        if(drUser.get("DR_NAME") != null) {
                                            userName = drUser.get("DR_NAME").toString();
                                        }
                                    }
//                                slog = "userid:"+drPatientId+";"+userName + "修改实体:" + entity.getClass().getName() +"; id:" + id +  "; 数据:" + ss;
                                    SysLogDeletDo dao= (SysLogDeletDo) SpringHelper.getBean("sysLogDeletDo");
                                    dao.addSysLogDelet(drPatientId,userName,entity.getClass().getName(), String.valueOf(id),ss,keyAct);
                                }
                            }else{
                                SysLogModifyDo dao= (SysLogModifyDo) SpringHelper.getBean("sysLogModifyDo");
                                dao.addSysLogModify(null,null,entity.getClass().getName(), String.valueOf(id),ss,keyAct);
                            }
                        }else{
                            String keyAct = act[0];
                            SysLogDeletDo dao= (SysLogDeletDo) SpringHelper.getBean("sysLogDeletDo");
                            dao.addSysLogDelet(null,null,entity.getClass().getName(), String.valueOf(id),ss,keyAct);
                        }
                    }
                }
            }else{
                slog="admin删除实体:" + entity.getClass().getName() +"; id:" + id + "；数据：" + ss;
            }
            if(StringUtils.isNotBlank(slog)) {
                logger.info(slog);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    /**
     * 修改前
     * @param entity
     * @param id
     * @param currentState
     * @param preState
     * @param propertyNames
     * @param types
     * @return
     */
    public boolean onFlushDirty(Object entity,Serializable id, Object[] currentState,Object[] preState,String[] propertyNames, Type[] types){
        try {
            if(entity.getClass().getName().startsWith("com.ylz.bizDo.cd.po") ||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppSerial")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageCount")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppExerciseCount")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageArchivingCount")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageArchivingPeople")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageArchivingAllCount")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageChronicDisease")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageTeamCount")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageTeam")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageOtherCount")){

                return false;
            }
            String ss = getJsonString(currentState,propertyNames);
            String slog ="";
            if(RequestContextHolder.getRequestAttributes()!=null) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                Map<String, String[]> map = request.getParameterMap();
                CdUser user = ((CdUser) (request.getSession().getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF)));
                if (user != null) {
                    if(map != null && map.size() >0) {
                        String[] act = map.get("act");
                        if(act != null && act.length >0){
                            String keyAct = act[0];
                            slog = "userid:"+user.getUserId()+";"+user.getUserName() + "修改实体:" + entity.getClass().getName() +"; id:" + id +  "; 数据:" + ss;
                            SysLogModifyDo dao= (SysLogModifyDo) SpringHelper.getBean("sysLogModifyDo");
                            dao.addSysLogModify(user.getUserId(),user.getUserName(),entity.getClass().getName(), String.valueOf(id),ss,keyAct);
                        }
                    }

                }else{
                    if(map != null && map.size() >0){
                        String[] token = map.get("token");
                        String[] act = map.get("act");
                        if(token != null && token.length >0){
                            String keyToken = token[0];
                            String keyAct = act[0];
                            if(StringUtils.isNotBlank(keyToken)){
                                SysDao sysDao = (SysDao) SpringHelper.getBean("sysDao");
                                String rule = PropertiesUtil.getConfValue("ruleAuthorization");
                                StringBuilder sb = new StringBuilder(keyToken);
                                for(int i = rule.length();i>0;i--){
                                    sb.deleteCharAt(Integer.parseInt(rule.substring(i-1,i)));
                                }
                                keyToken = sb.toString();
                                Map<String,Object> key = sysDao.getAppDrPatientKeyDao().findDrPatientKeyByTokenSql(keyToken);
                                if(key != null){
                                    String drPatientId = key.get("DR_PATIENT_ID").toString();
                                    String drPatientType = key.get("DR_PATIENT_TYPE").toString();
                                    String userName = null;
                                    if(drPatientType.equals(CommonShortType.HUANGZHE.getValue())){
                                        Map<String,Object> appPatientUser = sysDao.getAppPatientUserDao().findPatentUserName(drPatientId);
                                        if(appPatientUser.get("PATIENT_NAME") != null) {
                                            userName = appPatientUser.get("PATIENT_NAME").toString();
                                        }
                                    }else if(drPatientType.equals(CommonShortType.YISHENG.getValue())){
                                        Map<String,Object> drUser = sysDao.getAppDrUserDao().findDrUserName(drPatientId);
                                        if(drUser.get("DR_NAME") != null) {
                                            userName = drUser.get("DR_NAME").toString();
                                        }
                                    }
//                                slog = "userid:"+drPatientId+";"+userName + "修改实体:" + entity.getClass().getName() +"; id:" + id +  "; 数据:" + ss;
                                    SysLogModifyDo dao= (SysLogModifyDo) SpringHelper.getBean("sysLogModifyDo");
                                    dao.addSysLogModify(drPatientId,userName,entity.getClass().getName(), String.valueOf(id),ss,keyAct);
                                }
                            }else{
                                SysLogModifyDo dao= (SysLogModifyDo) SpringHelper.getBean("sysLogModifyDo");
                                dao.addSysLogModify(null,null,entity.getClass().getName(), String.valueOf(id),ss,keyAct);
                            }
                        }else{
                            String keyAct = act[0];
                            SysLogModifyDo dao= (SysLogModifyDo) SpringHelper.getBean("sysLogModifyDo");
                            dao.addSysLogModify(null,null,entity.getClass().getName(), String.valueOf(id),ss,keyAct);
                        }
                    }
                }
            }else{
//                slog = "admin修改实体:" + entity.getClass().getName() +"; id:" + id +  "; 数据:" + ss;
                SysLogModifyDo dao= (SysLogModifyDo) SpringHelper.getBean("sysLogModifyDo");
                dao.addSysLogModify("admin","admin",entity.getClass().getName(), String.valueOf(id),ss,null);
            }
            if(StringUtils.isNotBlank(slog)) {
                logger.info(slog);
            }
        }catch (Exception e){e.printStackTrace();}
        return false;
    }

    /**
     * 保存前
     * @param entity
     * @param id
     * @param state
     * @param propertyNames
     * @param types
     * @return
     */
    public boolean onSave(Object entity,Serializable id, Object[] state,String[] propertyNames,Type[] types){
        try {
            if(entity.getClass().getName().startsWith("com.ylz.bizDo.cd.po") ||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppSerial")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageCount")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppExerciseCount")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageArchivingCount")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageArchivingPeople")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageArchivingAllCount")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageChronicDisease")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageTeamCount")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageTeam")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppNotice")||
                    entity.getClass().getName().equals("com.ylz.bizDo.app.po.AppManageOtherCount")){

                return false;
            }
            String ss = getJsonString(state,propertyNames);
            String slog ="";
            if(RequestContextHolder.getRequestAttributes()!=null) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                Map<String, String[]> map = request.getParameterMap();
                CdUser user = ((CdUser) (request.getSession().getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF)));
                if(user != null){
                    if(map != null && map.size() >0) {
                        String[] act = map.get("act");
                        if(act != null && act.length >0){
                            String keyAct = act[0];
                            slog="userid:"+user.getUserId()+";"+user.getUserName() + "添加实体:" + entity.getClass().getName() + "; id:" + id + "；数据："+ss;
                            SysLogAddDo dao= (SysLogAddDo) SpringHelper.getBean("sysLogAddDo");
                            dao.addSysLogAdd(user.getUserId(),user.getUserName(),entity.getClass().getName(), String.valueOf(id),ss,keyAct);
                        }
                    }


                }else{
                    if(map != null && map.size() >0){
                        String[] token = map.get("token");
                        String[] act = map.get("act");
                        if(token != null && token.length >0){
                            SysDao sysDao = (SysDao) SpringHelper.getBean("sysDao");
                            String keyToken = token[0];
                            String keyAct = act[0];
                            if(StringUtils.isNotBlank(keyToken)){
                                String rule = PropertiesUtil.getConfValue("ruleAuthorization");
                                StringBuilder sb = new StringBuilder(keyToken);
                                for(int i = rule.length();i>0;i--){
                                    sb.deleteCharAt(Integer.parseInt(rule.substring(i-1,i)));
                                }
                                keyToken = sb.toString();
                                Map<String,Object> key = sysDao.getAppDrPatientKeyDao().findDrPatientKeyByTokenSql(keyToken);
                                if(key != null){
                                    String drPatientId = key.get("DR_PATIENT_ID").toString();
                                    String drPatientType = key.get("DR_PATIENT_TYPE").toString();
                                    String userName = null;
                                    if(drPatientType.equals(CommonShortType.HUANGZHE.getValue())){
                                        Map<String,Object> appPatientUser = sysDao.getAppPatientUserDao().findPatentUserName(drPatientId);
                                        userName = appPatientUser.get("PATIENT_NAME").toString();
                                    }else if(drPatientType.equals(CommonShortType.YISHENG.getValue())){
                                        Map<String,Object> appPatientUser = sysDao.getAppDrUserDao().findDrUserName(drPatientId);
                                        userName = appPatientUser.get("DR_NAME").toString();
                                    }
                                    SysLogAddDo dao= (SysLogAddDo) SpringHelper.getBean("sysLogAddDo");
                                    dao.addSysLogAdd(drPatientId,userName,entity.getClass().getName(), String.valueOf(id),ss,keyAct);
                                }
                            }else{
                                SysLogAddDo dao= (SysLogAddDo) SpringHelper.getBean("sysLogAddDo");
                                dao.addSysLogAdd(null,null,entity.getClass().getName(), String.valueOf(id),ss,keyAct);
                            }
                        }else{
                            String keyAct = act[0];
                            SysLogAddDo dao= (SysLogAddDo) SpringHelper.getBean("sysLogAddDo");
                            dao.addSysLogAdd(null,null,entity.getClass().getName(), String.valueOf(id),ss,keyAct);
                        }
                    }
                }
            }else{
                slog="admin添加实体:" + entity.getClass().getName() +"; id:" + id + "；数据：" + ss;
            }
            if(StringUtils.isNotBlank(slog)) {
                logger.info(slog);
            }
        }catch (Exception e){e.printStackTrace();}
        return false;
    }

    public String getJsonString(Object[] State,String[] propertyNames){
        int i = 0;
        Map<String,Object> map=new HashedMap();
        String ss = "";
        for (String p : propertyNames) {
            String s = null;
            if (null != State[i]){
                map.put(p,State[i]);
            }
            i++;
        }
        return JacksonUtils.objToStr(map);
    }



}
