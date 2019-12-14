package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppUserBloodpressureDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppThreeBloodPressureDataVo;
import com.ylz.bizDo.jtapp.commonEntity.AppPressureCountEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppPressureEntity;
import com.ylz.bizDo.jtapp.commonVo.AppInternetNewsQvo;
import com.ylz.bizDo.jtapp.commonVo.AppInternetNewsSonQvo;
import com.ylz.bizDo.jtapp.commonVo.AppUserBloodpressureVo;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamMemberEntity;
import com.ylz.bizDo.smjq.smEntity.*;
import com.ylz.bizDo.smjq.smVo.AppSmPeopleBasicVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.Constrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.ExtendDateUtil;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zzl on 2017/6/14.
 */
@Service("appUserBloodpressureDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppUserBloodpressureDaoImpl implements AppUserBloodpressureDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppPressureEntity> findById(AppUserBloodpressureVo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",qvo.getUserId());
        String sql = "select ID id, " +
                "UP_TIME time, " +
                "UP_SYS sys, " +
                "UP_DIA dia, " +
                "UP_PUL pul," +
                "UP_NOTE note " +
                "from APP_USER_BLOODPRESSURE where UP_USER_ID = :userId";
        sql += " ORDER BY UP_TIME DESC";
        List<AppPressureEntity> list = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPressureEntity.class,qvo);
        return list;
    }

    /**
     * @param vo  userId患者id
     * @param vo ano 心率
     * @param vo sys 收缩压
     * @param vo dia 舒张压
     * @param vo pul 脉搏
     * @param vo symptom 症状
     * @param vo note 备注
     */
    @Override
    public void appPressure(AppUserBloodpressureVo vo) throws Exception{
        AppUserBloodpressure bp = new AppUserBloodpressure();
        bp.setUpUserId(vo.getUserId());
        bp.setUpAno(vo.getAno());
        bp.setUpSys(Integer.parseInt(vo.getSys()));
        bp.setUpDia(Integer.parseInt(vo.getDia()));
        bp.setUpPul(Integer.parseInt(vo.getPul()));
        bp.setUpTime(ExtendDate.getCalendarms(vo.getTime()));
        bp.setUpSymptom(vo.getSymptom());
        bp.setUpNote(vo.getNote());
        if(StringUtils.isNotBlank(vo.getSourceType())){
            bp.setSourceType(vo.getSourceType());
        }else{
            bp.setSourceType(SourceType.APP.getValue());
        }
        sysDao.getServiceDo().add(bp);
        //推送到慢病
        sysDao.getSecurityCardAsyncBean().sendHbpNcd(vo);

        String flag = pressureAlert(vo.getUserId(),vo.getSys(),vo.getDia(),vo.getPul(),vo.getAno());
//        if(flag){
            //判断是否是三明
            AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(vo.getUserId());
            if(form != null){
                if(AddressType.SMS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                        ||AddressType.XMS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                        || AddressType.ZZS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                        || AddressType.GPS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))){
                    AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,vo.getUserId());
                    if(user != null){
                        AppSignForm signForm = sysDao.getAppSignformDao().findSignFormByUser(user.getId());
                        if(signForm != null){
                            List<AppLabelGroup> lsLbael = sysDao.getAppLabelGroupDao().findBySignGroup(signForm.getId(),"3");
                            if(lsLbael != null && lsLbael.size()>0){
                                boolean flagGroup = false;
                                for(AppLabelGroup p : lsLbael){
                                    if(p.getLabelValue().equals(ResidentMangeType.GXY.getValue())){
                                        flagGroup = true;
                                        break;
                                    }
                                }
                                if(flagGroup){
                                    AppSmHbpTwoEntity hbp = new AppSmHbpTwoEntity();
                                    hbp.setDbp(bp.getUpDia());
                                    hbp.setSbp(bp.getUpSys());
                                    hbp.setPulse(bp.getUpPul());
                                    hbp.setTestTime(ExtendDate.getYMD_h_m_s(bp.getUpTime()));
                                    hbp.setSourceType(bp.getSourceType());
                                    if(StringUtils.isNotBlank(flag)){
                                        hbp.setYcState("1");
                                        hbp.setHbpRemark(flag);
                                        bp.setAbnormalState("1");
                                        sysDao.getServiceDo().modify(bp);
                                    }else{
                                        hbp.setYcState("0");
                                    }
                                    AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,form.getSignDrId());
                                    if(drUser != null){
                                        hbp.setDrId(drUser.getId());
                                        hbp.setDrName(drUser.getDrName());
                                        hbp.setDrTel(drUser.getDrTel());
                                    }
                                    sysDao.getSecurityCardAsyncBean().addAbnormal(user,hbp,null,form.getSignHospId(),form.getSignTeamId());
                                }
                            }
                        }
                    }
                }
            }
//        }
    }


    @Override
    public void addlfPressure(AppUserBloodpressureVo vo) throws Exception{
        AppUserBloodpressure pressure = new AppUserBloodpressure();
        pressure.setSourceType(vo.getSourceType());
        List<AppBloodpressure> list =sysDao.getServiceDo().loadByPk(AppBloodpressure.class,"bpDevId",vo.getImei());
        if(list!=null&&list.size()>0){
            if(vo.getUser().equals("1")){
                if(StringUtils.isNotBlank(list.get(0).getBpUserOne())){
                    pressure.setUpUserId(list.get(0).getBpUserOne());
                }else{
                    return;
                    //throw new DaoException(this.getClass(),"找不到绑定用户");
                }
            }else if(vo.getUser().equals("2")){
                if(StringUtils.isNotBlank(list.get(0).getBpUserTwo())){
                    pressure.setUpUserId(list.get(0).getBpUserTwo());
                }else{
                    return;
                    //throw new DaoException(this.getClass(),"找不到绑定用户");
                }
            }
        }else{
            return;
            //throw new DaoException(this.getClass(),"找不到绑定用户");
        }
        pressure.setUpUser(vo.getUser());
        pressure.setUpAno(vo.getAno());
        pressure.setUpSys(Integer.parseInt(vo.getSys()));
        pressure.setUpDia(Integer.parseInt(vo.getDia()));
        pressure.setUpPul(Integer.parseInt(vo.getPul()));
        pressure.setUpImei(vo.getImei());
        pressure.setUpTel(vo.getTel());
        pressure.setUpIccid(vo.getIccid());
        pressure.setUpImsi(vo.getImsi());
        String receiveTime = vo.getTime().replace("/"," ");
        pressure.setUpTime(ExtendDate.getCalendar(receiveTime));
        sysDao.getServiceDo().add(pressure);
        String flag = pressureAlert(pressure.getUpUserId(),vo.getSys(),vo.getDia(),vo.getPul(),vo.getAno());
        AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(vo.getUserId());
        if(form != null){
            if(AddressType.SMS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                    ||AddressType.XMS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                    || AddressType.ZZS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                    || AddressType.GPS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))){
                AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,vo.getUserId());
                if(user != null){
                    AppSignForm signForm = sysDao.getAppSignformDao().findSignFormByUser(user.getId());
                    if(signForm != null){
                        List<AppLabelGroup> lsLbael = sysDao.getAppLabelGroupDao().findBySignGroup(signForm.getId(),"3");
                        if(lsLbael != null && lsLbael.size()>0){
                            boolean flagGroup = false;
                            for(AppLabelGroup p : lsLbael){
                                if(p.getLabelValue().equals(ResidentMangeType.GXY.getValue())){
                                    flagGroup = true;
                                    break;
                                }
                            }
                            if(flagGroup){
                                AppSmHbpTwoEntity hbp = new AppSmHbpTwoEntity();
                                hbp.setDbp(pressure.getUpDia());
                                hbp.setSbp(pressure.getUpSys());
                                hbp.setPulse(pressure.getUpPul());
                                hbp.setTestTime(ExtendDate.getYMD_h_m_s(pressure.getUpTime()));
                                hbp.setSourceType(pressure.getSourceType());
                                if(StringUtils.isNotBlank(flag)){
                                    hbp.setYcState("1");
                                    hbp.setHbpRemark(flag);
                                    pressure.setAbnormalState("1");
                                    sysDao.getServiceDo().modify(pressure);
                                }else{
                                    hbp.setYcState("0");
                                }
                                AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,form.getSignDrId());
                                if(drUser != null){
                                    hbp.setDrId(drUser.getId());
                                    hbp.setDrName(drUser.getDrName());
                                    hbp.setDrTel(drUser.getDrTel());
                                }
                                sysDao.getSecurityCardAsyncBean().addAbnormal(user,hbp,null,form.getSignHospId(),form.getSignTeamId());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 血压预警
     */
    public String pressureAlert(String patientId,String sys,String dia,String pul,String ano) throws Exception{
        String sss = "";
        String title = "血压预警";
        String diaContent = "舒张压"+dia+"mmHg，正常";
        String sysContent = "收缩压"+sys+"mmHg，正常";
        boolean flag = false;
        if(dia!=null && Double.parseDouble(dia)> Constrats.TZ_SZYSX){
            diaContent = "舒张压"+dia+"mmHg，偏高";
            flag = true;
        }else if(dia!=null && Double.parseDouble(dia)< Constrats.TZ_SZYXX){
            diaContent = "舒张压"+dia+"mmHg，偏低";
            flag = true;
        }
        if(sys!=null && Double.parseDouble(sys)> Constrats.TZ_SSYSX){
            sysContent = "收缩压"+sys+"mmHg，偏高";
            flag = true;

        }else if(sys!=null && Double.parseDouble(sys)< Constrats.TZ_SSYXX){
            sysContent = "收缩压"+sys+"mmHg，偏低";
            flag = true;
        }
        String anoContent = "";
        if("0".equals(ano)){
            anoContent = "齐";
        }else if("1".equals(ano)){
            anoContent = "不齐";
        }
        if(flag){
            boolean isInternet = false;
            AppInternetNewsQvo qqvo = new AppInternetNewsQvo();
            AppInternetNewsSonQvo sonQvo = new AppInternetNewsSonQvo();
            qqvo.setMsgType("YW002");
            qqvo.setMsgShowType("2");
            AppPatientUser patientUser = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,patientId);
            List<AppWarningSetting> setList = sysDao.getAppWarningSettingDao().findSetting(patientId, CommonWarnSet.JKJC.getValue());
            //预警发给患者
            if(!setList.isEmpty()&&CommonEnable.QIYONG.getValue().equals(setList.get(0).getWsState())||setList.isEmpty()) {
                String content = "您好，您本次测量的"+diaContent+"；"+sysContent+"；心率"+pul+"次/分钟"+anoContent+"。";
                sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.JKJCYCTX.getValue(), patientId, patientId, patientId,DrPatientType.PATIENT.getValue());

                isInternet = true;
                if(patientUser != null){
                    qqvo.setContent(content);
                    qqvo.setTitle(title);
                    qqvo.setUserId(patientUser.getPatientEHCId());
                    qqvo.setUserName(patientUser.getPatientName());
                    qqvo.setIdType("01");
                    qqvo.setIdNo(patientUser.getPatientIdno());
                    qqvo.setPhone(patientUser.getPatientTel());
                    qqvo.setType("3");
                    sonQvo.setType("JKJC");

                    sonQvo.setPatientIdNo(patientUser.getPatientIdno());
                    sonQvo.setPatientCard(patientUser.getPatientCard());
                    sonQvo.setPatientName(patientUser.getPatientName());
                    sonQvo.setPatientTel(patientUser.getPatientTel());
                    sonQvo.setPatientNeighborhoodCommittee(patientUser.getPatientNeighborhoodCommittee());
                    sonQvo.setEhcId(patientUser.getPatientEHCId());
                    sonQvo.setEhcCardNo(patientUser.getPatientEHCNo());
                    sonQvo.setDeviceType("1");
                    qqvo.setUrlParam(sonQvo);
                }

            }
            //发送医生
            AppSignForm sign = sysDao.getAppSignformDao().findSignFormByUser(patientId);
            if(sign!=null){
                if(sign.getSignState().equals(SignFormType.YQY.getValue())||SignFormType.YUQY.getValue().equals(sign.getSignState())) {
                    List<AppTeamMemberEntity> ls = sysDao.getAppTeamMemberDao().findMemByTeamId(sign.getSignTeamId());
                    for (AppTeamMemberEntity entity : ls) {
                        String dcontent = patientUser.getPatientName() + "：" + diaContent + "，" + sysContent + "，心率" + pul + "次/分钟" + anoContent + "。" + "(" + entity.getStrMemTeamName() + ")";
                        if(StringUtils.isBlank(sss)){
                            sss = diaContent + "，" + sysContent + "，心率" + pul + "次/分钟" + anoContent + "。";
                        }
                        sysDao.getAppNoticeDao().addNotices(title, dcontent, NoticesType.TZZSYJ.getValue(), patientId, entity.getMemDrId(), patientId, DrPatientType.DR.getValue());
                    }

                    qqvo.setHospId(sign.getSignHospId());
                    qqvo.setHospName(sign.getHosptName());
                    qqvo.setDoctorId(sign.getSignDrId());
                    AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,sign.getSignDrId());
                    if(drUser != null){
                        qqvo.setDoctorName(drUser.getDrName());
                    }
                }
            }
            //发给家人
            List<AppMyFamily> lsFamily = sysDao.getAppMyFamilyDao().findBdMyFamily(patientId,CommonEnable.QIYONG.getValue());
            if(lsFamily != null && lsFamily.size() > 0){
                for(AppMyFamily entity : lsFamily){
                    String dcontent = entity.getStrMfFmNickName()+":"+patientUser.getPatientName()+ diaContent+"，"+sysContent+"，心率"+pul+"次/分钟"+anoContent+"。";
                    sysDao.getAppNoticeDao().addNotices(title, dcontent, NoticesType.JKJCYCTX.getValue(), patientId, entity.getMyPatientId(), patientId, DrPatientType.PATIENT.getValue());
                }
            }

            if(isInternet){//异常发送互联网消息接口
                //查询是否开启调用互联网接口
                String openState = PropertiesUtil.getConfValue("openInterNetState");
                if("1".equals(openState)) {//开启
                    sysDao.getSecurityCardAsyncBean().sendOutInternetNews(qqvo);
                }
            }
        }
        return sss;
    }

    @Override
    public AppPressureCountEntity findCount(AppUserBloodpressureVo vo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",vo.getUserId());
        if("1".equals(vo.getPeriod())){
            map.put("days",7);
        }else if("2".equals(vo.getPeriod())){
            map.put("days",30);
        }else if("3".equals(vo.getPeriod())){
            map.put("days",90);
        }
        map.put("upSys",140);
        map.put("lowSys",90);
        map.put("upDia",90);
        map.put("lowDia",60);
        String sum = "SELECT COUNT(*) FROM APP_USER_BLOODPRESSURE WHERE  DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UP_TIME  AND UP_USER_ID = :userId ";
        String high = "SELECT count(*) from APP_USER_BLOODPRESSURE WHERE DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UP_TIME AND (UP_SYS > :upSys OR UP_DIA > :upDia) AND UP_USER_ID = :userId ";
        String low = "SELECT count(*) from APP_USER_BLOODPRESSURE WHERE DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UP_TIME AND (UP_SYS < :lowSys OR UP_DIA < :lowDia) AND UP_USER_ID = :userId ";
        String maxsys = "SELECT MAX(UP_SYS) FROM APP_USER_BLOODPRESSURE WHERE DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UP_TIME AND UP_USER_ID = :userId ";
        String minsys = "SELECT MIN(UP_SYS) FROM APP_USER_BLOODPRESSURE WHERE DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UP_TIME AND UP_USER_ID = :userId ";
        String maxdia = "SELECT MAX(UP_DIA) FROM APP_USER_BLOODPRESSURE WHERE DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UP_TIME AND UP_USER_ID = :userId ";
        String mindia = "SELECT MIN(UP_DIA) FROM APP_USER_BLOODPRESSURE WHERE DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UP_TIME AND UP_USER_ID = :userId ";
        String maxpul = "SELECT MAX(UP_PUL) FROM APP_USER_BLOODPRESSURE WHERE DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UP_TIME AND UP_USER_ID = :userId ";
        String minpul = "SELECT MIN(UP_PUL) FROM APP_USER_BLOODPRESSURE WHERE DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UP_TIME AND UP_USER_ID = :userId ";
        String avgsys = "SELECT AVG(UP_SYS) FROM APP_USER_BLOODPRESSURE  WHERE  DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UP_TIME AND UP_USER_ID = :userId";
        String avgdia = "SELECT AVG(UP_DIA) FROM APP_USER_BLOODPRESSURE  WHERE  DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UP_TIME AND UP_USER_ID = :userId";
        String avgpul = "SELECT AVG(UP_PUL) FROM APP_USER_BLOODPRESSURE  WHERE  DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UP_TIME AND UP_USER_ID = :userId";
        String sql = "SELECT ("+sum+") count,("+high+") high,("+low+") low, ("+maxsys+") maxsys, ("+minsys+") minsys, ("+maxdia+") maxdia, ("+mindia+") mindia, ("+maxpul+") maxpul, " +
                "("+minpul+") minpul, ("+avgsys+") avgsys, ("+avgdia+") avgdia, ("+avgpul+") avgpul FROM DUAL";
        List<AppPressureCountEntity> list = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPressureCountEntity.class);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * @param userId
     * @param period
     * @return
     */
    @Override
    public List<AppPressureEntity> findLook(String userId,String period) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT UP_SYS sys, UP_DIA dia, UP_PUL pul, UP_TIME time FROM APP_USER_BLOODPRESSURE WHERE 1=1 ";
        if(StringUtils.isNotBlank(userId)){
            map.put("userId",userId);
            sql += " AND UP_USER_ID = :userId";
        }
        if(StringUtils.isNotBlank(period)){
            if("1".equals(period)){
                String dateStart = ExtendDate.getYMD(Calendar.getInstance());
                String dateEnd = dateStart;
                map.put("dateStart",dateStart + " 00:00:00");
                map.put("dateEnd",dateEnd + " 23:59:59");
            }else if("2".equals(period)){
                String dateEnd = ExtendDate.getYMD(Calendar.getInstance());
                String dateStart = ExtendDateUtil.minusDate(dateEnd,ExtendDateType.DAYS.getValue(),7);
                map.put("dateStart",dateStart + " 00:00:00");
                map.put("dateEnd",dateEnd + " 23:59:59");
            }else if("3".equals(period)){
                String dateEnd = ExtendDate.getYMD(Calendar.getInstance());
                String dateStart = ExtendDateUtil.minusDate(dateEnd,ExtendDateType.DAYS.getValue(),30);
                map.put("dateStart",dateStart + " 00:00:00");
                map.put("dateEnd",dateEnd + " 23:59:59");
            }
            sql +=" AND UP_TIME >= :dateStart AND UP_TIME <= :dateEnd ";
        }
        sql += " ORDER BY UP_TIME ";
        return  this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPressureEntity.class);
    }

    @Override
    public AppPressureEntity findLatest(String patientId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",patientId);
        map.put("beginTime",ExtendDate.getYMD(new Date())+" 00:00:00");
        map.put("endTime",ExtendDate.getYMD(new Date())+" 23:59:59");
        String sql = "SELECT cast(AVG(UP_SYS) as SIGNED) sys, cast(AVG(UP_DIA) as SIGNED) dia, cast(AVG(UP_PUL) as SIGNED) pul " +
                "FROM APP_USER_BLOODPRESSURE  WHERE  UP_TIME between :beginTime and :endTime AND UP_USER_ID = :userId";
        List<AppPressureEntity> list = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPressureEntity.class);
        if(list!=null && list.size()>0 && list.get(0).getSys()!=null && list.get(0).getDia()!=null){
            return list.get(0);
        }else{
            String sql2 = "select ID id, UP_TIME time, UP_SYS sys, UP_DIA dia, UP_PUL pul from APP_USER_BLOODPRESSURE " +
                    " where UP_USER_ID = :userId order by UP_TIME desc ";
            List<AppPressureEntity> list2 = this.sysDao.getServiceDo().findSqlMapRVo(sql2,map,AppPressureEntity.class);
            if(list2!=null && list2.size()>0) {
                return list2.get(0);
            }
        }
        return null;
    }

    @Override
    public void addThreeBloodPressureData(AppThreeBloodPressureDataVo qvo) throws Exception {
        AppUserBloodpressure pressure = new AppUserBloodpressure();
        if(StringUtils.isNotBlank(qvo.getSourceType())){
            pressure.setSourceType(qvo.getSourceType());
        }else{
            pressure.setSourceType("2");
        }
        if("3".equals(qvo.getSourceType())||"4".equals(qvo.getSourceType())||"5".equals(qvo.getSourceType())){//随访\门诊类型的通过姓名和身份证验证
            AppPatientUser user = sysDao.getAppPatientUserDao().findByIdnoAndName(qvo.getIdno(),qvo.getName());
            if(user != null){
                pressure.setUpUserId(user.getId());
            }else{
                throw new Exception("找不到用户");
            }
        }else{//其他的通过设备验证
            List<AppBloodpressure> list =sysDao.getServiceDo().loadByPk(AppBloodpressure.class,"bpDevId",qvo.getDeviceSim());
            if(list!=null&&list.size()>0){
                if(qvo.getaOrB().equals("A")){
                    if(StringUtils.isNotBlank(list.get(0).getBpUserOne())){
                        pressure.setUpUserId(list.get(0).getBpUserOne());
                    }else{
                        throw new Exception("找不到绑定用户");
                    }
                }else if(qvo.getaOrB().equals("B")){
                    if(StringUtils.isNotBlank(list.get(0).getBpUserTwo())){
                        pressure.setUpUserId(list.get(0).getBpUserTwo());
                    }else{
                        throw new Exception("找不到绑定用户");
                    }
                }
            }else{
                throw new Exception("找不到设备");
            }
        }
        pressure.setUpImei(qvo.getDeviceSim());
        pressure.setUpSys(Integer.parseInt(qvo.getHighPressure()));
        pressure.setUpDia(Integer.parseInt(qvo.getLowVoltage()));
        pressure.setUpPul(Integer.parseInt(qvo.getPulse()));
        pressure.setUpTime(ExtendDate.getCalendarYmshms(qvo.getUploadTime()));
//        pressure.setUpNote("第三方远程无线血压数据");
        if(Integer.parseInt(qvo.getPulse())>160 || Integer.parseInt(qvo.getPulse())<40){
            pressure.setUpAno("1");
        }else{
            pressure.setUpAno("0");
        }
        sysDao.getServiceDo().add(pressure);
        String flag = pressureAlert(pressure.getUpUserId(),qvo.getHighPressure(),qvo.getLowVoltage(),qvo.getPulse(),pressure.getUpAno());
//        if(flag){
            //判断是否是三明尤溪
            AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(pressure.getUpUserId());
            if(form != null){
                if(AddressType.SMS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                        || AddressType.XMS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                        || AddressType.ZZS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                        || AddressType.GPS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))){
                    AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,pressure.getUpUserId());
                    if(user != null){
                        AppSignForm signForm = sysDao.getAppSignformDao().findSignFormByUser(user.getId());
                        if(signForm != null){
                            List<AppLabelGroup> lsLbael = sysDao.getAppLabelGroupDao().findBySignGroup(signForm.getId(),"3");
                            if(lsLbael != null && lsLbael.size() >0){
                                boolean flagGroup = false;
                                for(AppLabelGroup p : lsLbael){
                                    if(p.getLabelValue().equals(ResidentMangeType.GXY.getValue())){
                                        flagGroup = true;
                                        break;
                                    }
                                }
                                if(flagGroup){
                                    AppSmHbpTwoEntity hbp = new AppSmHbpTwoEntity();
                                    hbp.setDbp(pressure.getUpDia());
                                    hbp.setSbp(pressure.getUpSys());
                                    hbp.setPulse(pressure.getUpPul());
                                    hbp.setTestTime(ExtendDate.getYMD_h_m_s(pressure.getUpTime()));
                                    hbp.setSourceType(pressure.getSourceType());
                                    if(StringUtils.isNotBlank(flag)){
                                        hbp.setYcState("1");
                                        hbp.setHbpRemark(flag);
                                        pressure.setAbnormalState("1");
                                        sysDao.getServiceDo().modify(pressure);
                                    }else {
                                        hbp.setYcState("0");
                                    }
                                    AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,form.getSignDrId());
                                    if(drUser != null){
                                        hbp.setDrId(drUser.getId());
                                        hbp.setDrName(drUser.getDrName());
                                        hbp.setDrTel(drUser.getDrTel());
                                    }
                                    sysDao.getSecurityCardAsyncBean().addAbnormal(user,hbp,null,form.getSignHospId(),form.getSignTeamId());
                                }
                            }
                        }
                    }
                }
            }
//        }
    }

    /**
     * 三明查询居民血压数据
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppSmPeopleBasicEntity findPeopleBasic(AppSmPeopleBasicVo qvo) throws Exception {
        AppSmPeopleBasicEntity vo = null;
        Map<String,Object> map = new HashMap<>();
        map.put("patientId",qvo.getPatientId());
        String sql = "SELECT  \n" +
                "a.ID patientId,\n" +
                "a.PATIENT_NAME patientName,\n" +
                "a.PATIENT_IDNO patientIdno,\n" +
                "a.PATIENT_TEL paitentTel,\n" +
                "a.PATIENT_PROVINCE province,\n" +
                "a.PATIENT_CITY city,\n" +
                "a.PATIENT_AREA area,\n" +
                "a.PATIENT_STREET street,\n" +
                "a.PATIENT_NEIGHBORHOOD_COMMITTEE village,\n" +
                "a.PATIENT_ADDRESS addr," +
                "a.PATIENT_X xaxis,\n" +
                "a.PATIENT_Y yaxis\n" +
                "FROM app_patient_user a  \n" +
                "WHERE a.ID =:patientId ";
        List<AppSmPeopleBasicEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppSmPeopleBasicEntity.class);
        if(list != null && list.size()>0){
            vo = list.get(0);
            //查询签约信息
            AppSmSignEntity ss = sysDao.getAppSignformDao().findSignBypatientId(qvo.getPatientId());
            if(ss != null){
                vo.setTeamId(ss.getTeamId());
                vo.setTeamName(ss.getTeamName());
                vo.setOrgId(ss.getOrgId());
                vo.setOrgName(ss.getOrgName());
                vo.setDrId(ss.getDrId());
                vo.setDrName(ss.getDrName());
                vo.setDrTel(ss.getDrTel());
            }
            List<AppSmHbpEntity> listHbp = findPeopleHbp(qvo);
            if(listHbp != null && listHbp.size()>0){
                vo.setListHbp(listHbp);
            }
            return vo;
        }
        return null;
    }
    public List<AppSmHbpEntity> findPeopleHbp(AppSmPeopleBasicVo qvo) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("patientId",qvo.getPatientId());
        String sql = "SELECT\n" +
                "\tUP_DIA dbp,\n" +
                "\tUP_SYS sbp,\n" +
                "\tUP_PUL pulse,\n" +
                "\tUP_TIME testTime,\n" +
                "\tSOURCE_TYPE sourceType,\n" +
                "\tABNORMAL_STATE abnormalState\n" +
                "FROM\n" +
                "\tAPP_USER_BLOODPRESSURE\n" +
                "WHERE\n" +
                "\tUP_USER_ID =:patientId ORDER BY UP_TIME DESC";
        List<AppSmHbpEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppSmHbpEntity.class,qvo);
        if(list != null && list.size()>0){
            return list;
        }
        return null;
    }
}
