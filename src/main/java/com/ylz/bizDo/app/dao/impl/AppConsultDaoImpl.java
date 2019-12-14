package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppConsultDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppCousultEntity;
import com.ylz.bizDo.jtapp.commonVo.AppConsultQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrConsultEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.FileUtils;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/23.
 */
@Service("appConsultDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppConsultDaoImpl implements AppConsultDao {
    @Autowired
    private SysDao sysDao;
    @Override
    public void saveCon(AppConsultQvo qvo) throws Exception {
            AppConsult table = new AppConsult();
            table.setConTitle(qvo.getTitle());
            table.setConContent(qvo.getContent());
            table.setConCreateUserId(qvo.getCreateId());
            table.setConPid(qvo.getPid());
            Calendar cal = Calendar.getInstance();
            table.setConCreateTime(cal);
            table.setConReolierId(qvo.getToUserId());
            if(StringUtils.isNotBlank(qvo.getToUserId())){
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,qvo.getToUserId());
                if(drUser!=null){
                    if(StringUtils.isNotBlank(drUser.getDrHospId())){
                        AppHospDept hospDept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                        if(hospDept!=null){
                            table.setConHospId(hospDept.getId());
                            table.setConAreaCode(hospDept.getHospAreaCode());
                        }
                    }
                }
            }
            table.setConType(qvo.getType());
            if(StringUtils.isBlank(qvo.getPid())){
                table.setConState(CommonConsultState.DHF.getValue());//待回复
            }
            if(StringUtils.isNotBlank(qvo.getFileUrl())){
//                String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureHz"),qvo.getFileName());
//                FileUtils.decoderBase64File(qvo.getFileUrl(),PropertiesUtil.getConfValue("filePicture")+path);
//                table.setConFileUrl(path);

                Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getFileUrl(), CommonShortType.HUANGZHE.getValue());
                table.setConFileUrl(map.get("objectUrl").toString());
            }
            if(StringUtils.isNotBlank(qvo.getCreateId())){
                AppSignForm v= sysDao.getAppSignformDao().getSignFormUserId(qvo.getCreateId());
//            AppSignForm v =(AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,qvo.getCreateId());
                if(v!=null){
                    table.setConTeamId(v.getSignTeamId());
                }
            }
            sysDao.getServiceDo().add(table);
            if(StringUtils.isNotBlank(qvo.getToUserId())){
                sysDao.getAppNoticeDao().addNotices("咨询消息提醒","您好，有人于"+ ExtendDate.getChineseYMD(cal)+"给您发了一条咨询，请注意查看。", NoticesType.ZXXX.getValue(),qvo.getCreateId(),qvo.getToUserId(),table.getId(), DrPatientType.DR.getValue());
            }
            //添加履约
        if(StringUtils.isNotBlank(qvo.getCreateId())){
            AppDrUser appDrUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getCreateId());
            if(appDrUser != null){//创建者是医生
                if(StringUtils.isNotBlank(qvo.getPid())){
                    AppConsult pConsult = (AppConsult)sysDao.getServiceDo().find(AppConsult.class,qvo.getPid());
                    if(pConsult != null){
                        if(StringUtils.isNotBlank(pConsult.getConCreateUserId())){
                            AppPatientUser pUser = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,pConsult.getConCreateUserId());
                            if(pUser != null){//接收者是居民 添加履约数据
                                AppSignForm form= sysDao.getAppSignformDao().getSignFormUserId(pUser.getId());
                                if(form != null){
                                    if(StringUtils.isNotBlank(appDrUser.getDrHospId())){
                                        AppHospDept hosp = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,appDrUser.getDrHospId());
                                        if(hosp != null){
                                            //判断今天是否添加过
                                            List<AppConsult> listConsult = sysDao.getAppConsultDao().findToDayCon(qvo.getCreateId(),qvo.getPid(),table.getId());
                                            if(listConsult == null){//
                                                PerformanceDataQvo qqvo = new PerformanceDataQvo();
                                                //患者信息
                                                qqvo.setPerName(pUser.getPatientName());
                                                qqvo.setPerIdno(pUser.getPatientIdno());
                                                qqvo.setPerForeign(table.getId());
                                                if(StringUtils.isNotBlank(hosp.getHospAreaCode())){
                                                    CdAddress p = sysDao.getCdAddressDao().findByCode(hosp.getHospAreaCode());
                                                    if(p != null){
                                                        String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                                                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                                                        if(value != null) {
                                                            qqvo.setPerArea(value.getCodeTitle());
                                                        }
                                                    }
                                                }
                                                qqvo.setPerSource("2");//app
                                                //医院信息
                                                qqvo.setHospId(hosp.getId());
                                                qqvo.setHospName(hosp.getHospName());
                                                qqvo.setHospAreaCode(hosp.getHospAreaCode());
                                                qqvo.setHospAddress(hosp.getHospAddress());
                                                qqvo.setHospTel(hosp.getHospTel());
                                                //医生信息
                                                qqvo.setDrId(appDrUser.getId());
                                                qqvo.setDrName(appDrUser.getDrName());
                                                qqvo.setDrAccount(appDrUser.getDrAccount());
                                                qqvo.setDrPwd(appDrUser.getDrPwd());
                                                qqvo.setDrGender(appDrUser.getDrGender());
                                                qqvo.setDrTel(appDrUser.getDrTel());
                                                qqvo.setPerType(PerformanceType.JKZX.getValue());
                                                if(StringUtils.isNotBlank(qqvo.getPerArea())){
                                                    if(StringUtils.isNotBlank(qqvo.getPerType())){
                                                        String fwType = "";
                                                        String sermeal = "";//服务包id
                                                        fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                                                        sermeal = form.getSignpackageid();
                                                        sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<AppDrConsultEntity> findByDr(AppConsultQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT a.ID id,a.CON_TITLE title,a.CON_CONTENT content,a.CON_TYPE type,date_format(a.CON_CREATE_TIME,'%Y-%c-%d %H:%i:%s') time," +
                "b.PATIENT_NAME userName,b.PATIENT_GENDER userSex,b.PATIENT_AGE userAge,b.PATIENT_IMAGEURL imgUrl,b.ID userId" +
                " FROM APP_CONSULT a INNER JOIN APP_PATIENT_USER b ON a.CON_CREATE_USER_ID = b.ID  WHERE 1=1";
        if(StringUtils.isNotBlank(qvo.getToUserId())){
            map.put("drId",qvo.getToUserId());
            sql += " AND a.CON_REPLIER_ID =:drId";
        }
        if(StringUtils.isNotBlank(qvo.getType())){
            map.put("type",qvo.getType());
            sql += " AND a.CON_TYPE =:type";
        }
        sql += " AND a.CON_PID IS NULL ORDER BY a.CON_CREATE_TIME DESC";
        List<AppDrConsultEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrConsultEntity.class);
        return ls;
    }

    @Override
    public List<AppCousultEntity> findList(AppConsultQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",qvo.getCreateId());
        String sql = "SELECT a.ID id,a.CON_TITLE title,a.CON_PID pid,a.CON_STATE state,a.CON_CONTENT content,a.CON_TYPE type,date_format(a.CON_CREATE_TIME,'%Y-%c-%d %H:%i:%s') time" +
                ",b.ID userId,a.CON_REPLIER_ID drId,'' drName,'' drType,a.CON_FILE_URL imageUrl,b.PATIENT_NAME userName,b.PATIENT_AGE userAge,b.PATIENT_GENDER userSex " +
                " FROM APP_CONSULT a INNER JOIN APP_PATIENT_USER b ON a.CON_CREATE_USER_ID=b.ID WHERE 1=1";
        sql +=" AND a.CON_PID IS NULL";
        if(StringUtils.isNotBlank(qvo.getCreateId())){
            sql += " AND a.CON_CREATE_USER_ID = :userId";
        }
        if(StringUtils.isNotBlank(qvo.getType())){
            map.put("type",qvo.getType());
            sql += " AND a.CON_TYPE =:type";
        }
        if(StringUtils.isNotBlank(qvo.getState())){
            map.put("state",qvo.getState());
            sql += " AND a.CON_STATE =:state";
        }
        List<AppCousultEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppCousultEntity.class);
        return ls;
    }

    @Override
    public List<AppCousultEntity> findReply(AppConsultQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",qvo.getCreateId());

        String sql = "SELECT a.ID id,a.CON_FILE_URL imageUrl,a.CON_REPLIER_ID drId,'' drName,'' drType,a.CON_TITLE title,a.CON_PID pid,a.CON_STATE state,a.CON_CONTENT content," +
                "c.ID userId,c.PATIENT_NAME userName,c.PATIENT_AGE userAge,c.PATIENT_GENDER userSex," +
                "a.CON_TYPE type,date_format(a.CON_CREATE_TIME,'%Y-%c-%d %H:%i:%s') time" +
                " FROM APP_CONSULT a,APP_CONSULT b,APP_PATIENT_USER c WHERE 1=1 AND a.CON_CREATE_USER_ID=c.ID";
        if(StringUtils.isNotBlank(qvo.getType())){
            map.put("type",qvo.getType());
            sql += " AND b.CON_TYPE = :type ";
        }
        sql +=" AND b.CON_CREATE_USER_ID = :userId AND b .CON_PID = a.ID GROUP BY b.CON_PID";
        List<AppCousultEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppCousultEntity.class);
        return ls;
    }

    @Override
    public List<AppCousultEntity> findDetailed(AppConsultQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pid",qvo.getId());
        String sql = "SELECT a.ID id,a.CON_TITLE title,a.CON_PID pid,a.CON_STATE state,a.CON_CONTENT content,a.CON_TYPE type,date_format(a.CON_CREATE_TIME,'%Y-%c-%d %H:%i:%s') time" +
                ",a.CON_FILE_URL imageUrl,a.CON_REPLIER_ID drId,'' drName,'' drType,b.ID userId,b.PATIENT_NAME userName,b.PATIENT_AGE userAge,b.PATIENT_GENDER userSex FROM APP_CONSULT a INNER JOIN APP_PATIENT_USER b ON a.CON_CREATE_USER_ID = b.ID WHERE 1=1";
        sql +=" AND a.CON_PID = :pid OR a.ID =:pid";
        List<AppCousultEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppCousultEntity.class);
        return ls;
    }
    @Override
    public List<AppDrConsultEntity> findComplete(AppConsultQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getToUserId());
        map.put("state",CommonConsultState.YWC.getValue());
        String sql = "SELECT a.ID id,a.CON_STATE state,a.CON_TITLE title,a.CON_CONTENT content,a.CON_TYPE type,date_format(a.CON_CREATE_TIME,'%Y-%c-%d %H:%i:%s') time" +
                ",b.ID userId,b.PATIENT_NAME userName,b.PATIENT_GENDER userSex,b.PATIENT_AGE userAge FROM APP_CONSULT a INNER JOIN APP_PATIENT_USER b ON a.CON_CREATE_USER_ID = b.ID WHERE 1=1";
        sql +=" AND a.CON_REPLIER_ID = :drId AND a.CON_PID IS NULL AND a.CON_STATE =:state";
        List<AppDrConsultEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrConsultEntity.class);
        return ls;
    }

    @Override
    public List<AppCousultEntity> findConList(AppConsultQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",qvo.getCreateId());
        map.put("state",qvo.getState());
        String sql = "SELECT a.ID id,a.CON_TITLE title,a.CON_PID pid,a.CON_STATE state,a.CON_CONTENT content,a.CON_TYPE type,date_format(a.CON_CREATE_TIME,'%Y-%c-%d %H:%i:%s') time" +
                ",b.ID userId,b.PATIENT_NAME userName,b.PATIENT_AGE userAge,b.PATIENT_GENDER userSex FROM APP_CONSULT a INNER JOIN APP_PATIENT_USER b ON a.CON_CREATE_USER_ID = b.ID WHERE 1=1";
//        String sql = "SELECT a.ID id,a.CON_PID pid,a.CON_STATE state,a.CON_TITLE title,a.CON_CONTENT content,a.CON_TYPE type,date_format(a.CON_CREATE_TIME,'%Y-%c-%d %h:%i:%s') time" +
//                " FROM APP_CONSULT a WHERE 1=1";
        sql +=" AND a.CON_CREATE_USER_ID = :userId AND a.CON_PID IS NULL AND a.CON_STATE =:state";
        List<AppCousultEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppCousultEntity.class);
        return ls;
    }

    @Override
    public List<AppConsult> findToDayCon(String drId, String pid,String conId)throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("drId",drId);
        map.put("pid",pid);
        map.put("id",conId);
        map.put("startTime",ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
        map.put("endTime", ExtendDate.getYMD(Calendar.getInstance())+ " 23:59:59");
        String sql = "SELECT * FROM APP_CONSULT WHERE 1=1 ";
        sql += " AND CON_CREATE_USER_ID =:drId AND  CON_PID =:pid AND HS_CREATE_DATE >=:startTime AND HS_CREATE_DATE <=:endTime and id !=:conId";
        List<AppConsult> list = sysDao.getServiceDo().findSqlMap(sql,map,AppConsult.class);
        if(list != null && list.size()>0){
            return list;
        }
        return null;
    }
}
