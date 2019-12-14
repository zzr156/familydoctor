package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppUserBloodgluDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppGluCountEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppGluEntity;
import com.ylz.bizDo.jtapp.commonVo.AppInternetNewsQvo;
import com.ylz.bizDo.jtapp.commonVo.AppInternetNewsSonQvo;
import com.ylz.bizDo.jtapp.commonVo.AppUserBloodgluVo;
import com.ylz.bizDo.jtapp.commonVo.DevUserBloodgluVo;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamMemberEntity;
import com.ylz.bizDo.smjq.smEntity.AppBloodSugarEntity;
import com.ylz.bizDo.smjq.smEntity.AppBloodSugarTwoEntity;
import com.ylz.bizDo.smjq.smEntity.AppSmPeopleBasicEntity;
import com.ylz.bizDo.smjq.smEntity.AppSmSignEntity;
import com.ylz.bizDo.smjq.smVo.AppSmPeopleBasicVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.Constrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
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
 * Created by zzl on 2017/6/14.
 */
@Service("appUserBloodgluDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppUserBloodgluDaoImpl implements AppUserBloodgluDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppGluEntity> findById(AppUserBloodgluVo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",qvo.getUserId());
        String sql = "select ID id," +
                " UG_TEST_TIME time," +
                " UG_BGSTATE bgState," +
                " UG_BLOODGLU glu," +
                " UG_NOTE note " +
                " from APP_USER_BLOODGLU where UG_USER_ID = :userId";
        sql += " ORDER BY UG_TEST_TIME DESC";
        List<AppGluEntity> list = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppGluEntity.class,qvo);
        return list;
    }

    @Override
    public String findNewGxt(String userId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",userId);
        String sql = "select UG_BGSTATE from APP_USER_BLOODGLU where UG_TEST_TIME = " +
                "(select max(UG_TEST_TIME) from APP_USER_BLOODGLU where UG_USER_ID = :id) and UG_USER_ID = :id";
        return (String) this.sysDao.getServiceDo().findSqlObject(sql,map);

    }

    @Override
    public void appBloodglu(AppUserBloodgluVo vo) throws Exception{
        AppUserBloodglu xt = new AppUserBloodglu();
        xt.setUgUserId(vo.getUserId());
        xt.setUgBloodglu(Double.parseDouble(vo.getBloodglu()));//血糖值
        xt.setUgBgstate(vo.getBgstate());//时间段
        xt.setUgTestTime(ExtendDate.getCalendarms(vo.getTesttime()));
        xt.setUgSymptom(vo.getSymptom());
        xt.setUgNote(vo.getNote());
        xt.setUgState(CommonUgState.MR.getValue());
        if(StringUtils.isNotBlank(vo.getSourceType())){
            xt.setSourceType(vo.getSourceType());
        }else{
            xt.setSourceType(SourceType.APP.getValue());
        }
        sysDao.getServiceDo().add(xt);
        //推送到慢病
        sysDao.getSecurityCardAsyncBean().sendDmNcd(vo);

       String flag = alertMsg(vo.getUserId(),vo.getBgstate(),vo.getBloodglu());
//        if(flag){//返回全部数据
            //判断是否是三明尤溪
            AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(vo.getUserId());
            if(form != null){
                if(AddressType.SMS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                        || AddressType.XMS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                        || AddressType.ZZS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                        || AddressType.GPS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))){
                    AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,vo.getUserId());
                    if(user != null) {
                        AppSignForm signForm = sysDao.getAppSignformDao().findSignFormByUser(user.getId());
                        if (signForm != null) {
                            List<AppLabelGroup> lsLbael = sysDao.getAppLabelGroupDao().findBySignGroup(signForm.getId(), "3");
                            if (lsLbael != null && lsLbael.size() > 0) {
                                boolean flagGroup = false;
                                for (AppLabelGroup p : lsLbael) {
                                    if (p.getLabelValue().equals(ResidentMangeType.TNB.getValue())) {
                                        flagGroup = true;
                                        break;
                                    }
                                }
                                if (flagGroup) {
                                    AppBloodSugarTwoEntity ss = new AppBloodSugarTwoEntity();
                                    ss.setBloodSugarValue(xt.getUgBloodglu());
                                    ss.setMeasureTime(ExtendDate.getYMD_h_m_s(xt.getUgTestTime()));
                                    ss.setSourceType(xt.getSourceType());
                                    if(StringUtils.isNotBlank(flag)){
                                        ss.setYcState("1");
                                        ss.setBloodRemark(flag);
                                        xt.setAbnormalState("1");
                                        sysDao.getServiceDo().modify(xt);
                                    }else{
                                        ss.setYcState("0");
                                    }
                                    AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,form.getSignDrId());
                                    if(drUser != null){
                                        ss.setDrId(drUser.getId());
                                        ss.setDrName(drUser.getDrName());
                                        ss.setDrTel(drUser.getDrTel());
                                    }
                                    sysDao.getSecurityCardAsyncBean().addAbnormal(user,null,ss,form.getSignHospId(),form.getSignTeamId());
                                }
                            }
                        }
                    }
                }
            }
//        }
    }

    /**
     *
     * @param vo devid
     * @param vo testtime
     * @param vo bgstate 1饭后 2饭前
     * @param vo temptur
     * @param vo bloodglu
     */
    @Override
    public void addlfBlu(DevUserBloodgluVo vo) throws Exception{
        AppUserBloodglu xt = new AppUserBloodglu();
        if(StringUtils.isNotBlank(vo.getSourceType())){
            xt.setSourceType(vo.getSourceType());
        }else{
            xt.setSourceType("2");
        }
        xt.setUgCode(vo.getDevid());
        String receivetime = vo.getTestTime();
        String reg = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
        receivetime = receivetime.replaceAll(reg, "$1-$2-$3 $4:$5:$6");
        //System.out.println(receivetime);
        xt.setUgTestTime(ExtendDate.getCalendar(receivetime));
        //0-11早餐后  11-16午餐后  16-24晚餐后
        //0-5凌晨  5-10空腹  10- 14午餐前  14-20晚餐前  20-24睡前
        if("1".equals(vo.getBgState())){//餐后
            if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=0&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<11){
                xt.setUgBgstate("2");
            }else if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=11&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<16){
                xt.setUgBgstate("4");
            }else if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=16&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<24){
                xt.setUgBgstate("6");
            }
        }else if("2".equals(vo.getBgState())){//餐前
            if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=0&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<5){
                xt.setUgBgstate("8");
            }else if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=5&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<10){
                xt.setUgBgstate("1");
            }else if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=10&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<14){
                xt.setUgBgstate("3");
            }
            else if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=14&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<20){
                xt.setUgBgstate("5");
            }
            else if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=20&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<24){
                xt.setUgBgstate("7");
            }
        }else{
            xt.setUgBgstate("9");
        }
        xt.setUgTemptur(vo.getTempTur());
        xt.setUgCodeNum(vo.getCodeNum());
        xt.setUgBloodglu(Double.parseDouble(vo.getBloodGlu()));
        if("3".equals(vo.getSourceType())||"4".equals(vo.getSourceType())||"5".equals(vo.getSourceType())) {//是随访\门诊\poss的不用设备验证，通过身份证和姓名查询验证
            AppPatientUser user = sysDao.getAppPatientUserDao().findByIdnoAndName(vo.getIdno(),vo.getName());
            if(user != null){
                xt.setUgUserId(user.getId());
            }else{
                return;
            }
        }else{
            List<AppBloodglu> list =sysDao.getServiceDo().loadByPk(AppBloodglu.class,"bgDevId",vo.getDevid());
            if(list!=null&&list.size()>0){
                xt.setUgUserId(list.get(0).getBgPaientId());
            }else{
                //throw new DaoException(this.getClass(),"找不到绑定用户");
                return;
            }
        }
        sysDao.getServiceDo().add(xt);
        vo.setBgState(xt.getUgBgstate());
        vo.setTestTime(ExtendDate.getYMD_h_m(xt.getUgTestTime()));
        String flag = alertMsg(xt.getUgUserId(),vo.getBgState(),vo.getBloodGlu());
//        if(flag){//返回所有数据
            //判断是否是三明尤溪
            AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(xt.getUgUserId());
            if(form != null){
                if(AddressType.SMS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                        || AddressType.XMS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                        || AddressType.ZZS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))
                        || AddressType.GPS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))){
                    AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,xt.getUgUserId());
                    if(user != null) {
                        AppSignForm signForm = sysDao.getAppSignformDao().findSignFormByUser(user.getId());
                        if (signForm != null) {
                            List<AppLabelGroup> lsLbael = sysDao.getAppLabelGroupDao().findBySignGroup(signForm.getId(), "3");
                            if (lsLbael != null && lsLbael.size() > 0) {
                                boolean flagGroup = false;
                                for (AppLabelGroup p : lsLbael) {
                                    if (p.getLabelValue().equals(ResidentMangeType.TNB.getValue())) {
                                        flagGroup = true;
                                        break;
                                    }
                                }
                                if (flagGroup) {
                                    AppBloodSugarTwoEntity ss = new AppBloodSugarTwoEntity();
                                    ss.setBloodSugarValue(xt.getUgBloodglu());
                                    ss.setMeasureTime(ExtendDate.getYMD_h_m_s(xt.getUgTestTime()));
                                    ss.setSourceType(xt.getSourceType());
                                    if(StringUtils.isNotBlank(flag)){
                                        ss.setYcState("1");
                                        ss.setBloodRemark(flag);
                                        xt.setAbnormalState("1");
                                        sysDao.getServiceDo().modify(xt);
                                    }else{
                                        ss.setYcState("0");
                                    }
                                    AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,form.getSignDrId());
                                    if(drUser != null){
                                        ss.setDrId(drUser.getId());
                                        ss.setDrName(drUser.getDrName());
                                        ss.setDrTel(drUser.getDrTel());
                                    }
                                    sysDao.getSecurityCardAsyncBean().addAbnormal(user,null,ss,form.getSignHospId(),form.getSignTeamId());
                                }
                            }
                        }
                    }
                }
            }
//        }
    }

    //血糖预警消息
    public String alertMsg(String userId,String bgstate,String bloodglu) throws Exception{
        String sss = "";
        String title = "血糖预警";
        boolean flag = false;
        String index = "";
        String eatText = "";
        //餐后
        if((CommonUgState.ZCH.getValue()).equals(bgstate)||(CommonUgState.WCH.getValue()).equals(bgstate)||(CommonUgState.DCH.getValue()).equals(bgstate)){
            eatText = "餐后";
           if(Double.parseDouble(bloodglu)> Constrats.TZ_CHXTSX){//餐后血糖高
                index = "高";
                flag = true;
            }else if(Double.parseDouble(bloodglu)>Constrats.TZ_CHXTZX){//餐后血糖偏高
               index = "偏高";
               flag = true;
            }else if(Double.parseDouble(bloodglu)< Constrats.TZ_CHXTXX){//餐后血糖低
               index = "偏低";
               flag = true;
           }
        }else{//其他时段
            eatText = "空腹";
            if(Double.parseDouble(bloodglu)> Constrats.TZ_KFXTSX){//空腹血糖高
                index = "高";
                flag = true;
            }else if(Double.parseDouble(bloodglu)>Constrats.TZ_KFXTZX){//空腹血糖偏高
                index = "偏高";
                flag = true;
            }else if(Double.parseDouble(bloodglu)< Constrats.TZ_KFXTXX){//空腹血糖低
                index = "偏低";
                flag = true;
            }

        }
        if(flag) {
            AppPatientUserEntity patientUser = sysDao.getAppPatientUserDao().findUserId(userId);
            List<AppWarningSetting> setList = sysDao.getAppWarningSettingDao().findSetting(userId, CommonWarnSet.JKJC.getValue());
            CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_BGSTATE, bgstate);
            String stateTitle ="";
            if(code!=null){
                stateTitle = code.getCodeTitle();
            }
            boolean isInternet = false;
            AppInternetNewsQvo qqvo = new AppInternetNewsQvo();
            AppInternetNewsSonQvo sonQvo = new AppInternetNewsSonQvo();
            qqvo.setMsgType("YW002");
            qqvo.setMsgShowType("2");
            //预警发给患者
            if (setList != null && !setList.isEmpty() && CommonEnable.QIYONG.getValue().equals(setList.get(0).getWsState()) || setList.isEmpty()) {
                String content = "您好，您本次测量的" + stateTitle + "血糖值" + bloodglu + "mmol/L，"+index+"。";
                sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.JKJCYCTX.getValue(), userId, userId, userId, DrPatientType.PATIENT.getValue());
                isInternet = true;
                AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,userId);
                if(user != null){
                    qqvo.setContent(content);
                    qqvo.setTitle(title);
                    qqvo.setUserId(user.getPatientEHCId());
                    qqvo.setUserName(user.getPatientName());
                    qqvo.setIdType("01");
                    qqvo.setIdNo(user.getPatientIdno());
                    qqvo.setPhone(user.getPatientTel());
                    qqvo.setType("3");
                    sonQvo.setType("JKJC");

                    sonQvo.setPatientIdNo(user.getPatientIdno());
                    sonQvo.setPatientCard(user.getPatientCard());
                    sonQvo.setPatientName(user.getPatientName());
                    sonQvo.setPatientTel(user.getPatientTel());
                    sonQvo.setPatientNeighborhoodCommittee(user.getPatientNeighborhoodCommittee());
                    sonQvo.setEhcId(user.getPatientEHCId());
                    sonQvo.setEhcCardNo(user.getPatientEHCNo());
                    sonQvo.setDeviceType("2");
                    qqvo.setUrlParam(sonQvo);
                }

            }
            //发给医生
            AppSignForm sign = sysDao.getAppSignformDao().findSignFormByUser(userId);
            if (sign != null) {
                if(sign.getSignState().equals(SignFormType.YQY.getValue())||sign.getSignState().equals(SignFormType.YUQY.getValue())) {
                    List<AppTeamMemberEntity> ls = sysDao.getAppTeamMemberDao().findMemByTeamId(sign.getSignTeamId());
                    for (AppTeamMemberEntity entity : ls) {
                        String dcontent = patientUser.getPatientName()+ "：" + stateTitle + "血糖值" + bloodglu + "mmol/L，"+index+"。"+"(" +entity.getStrMemTeamName()+")";
                        if(StringUtils.isBlank(sss)){
                            sss = stateTitle +"血糖值" + bloodglu + "mmol/L，"+index+"。";
                        }
                        sysDao.getAppNoticeDao().addNotices(title, dcontent, NoticesType.TZZSYJ.getValue(), userId, entity.getMemDrId(), userId, DrPatientType.DR.getValue());
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
            List<AppMyFamily> lsFamily = sysDao.getAppMyFamilyDao().findBdMyFamily(userId,CommonEnable.QIYONG.getValue());
            if(lsFamily != null && lsFamily.size() > 0){
                for(AppMyFamily entity : lsFamily){
                    String dcontent = entity.getStrMfFmNickName()+":"+patientUser.getPatientName() + stateTitle + "血糖值" + bloodglu + "mmol/L，"+index+"。";
                    sysDao.getAppNoticeDao().addNotices(title, dcontent, NoticesType.JKJCYCTX.getValue(), userId, entity.getMyPatientId(), userId, DrPatientType.PATIENT.getValue());
                }
            }

            if(isInternet){//异常发送互联网消息接口
                //查询是否开启调用互联网接口
                String openState = PropertiesUtil.getConfValue("openInterNetState");
                if("1".equals(openState)){//开启
                    sysDao.getSecurityCardAsyncBean().sendOutInternetNews(qqvo);
                }
            }
        }
        return sss;
    }

    @Override
    public AppGluCountEntity findCount(AppUserBloodgluVo vo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",vo.getUserId());
        if("1".equals(vo.getPeriod())){
            map.put("days",7);
        }else if("2".equals(vo.getPeriod())){
            map.put("days",30);
        }else if("3".equals(vo.getPeriod())){
            map.put("days",90);
        }
        map.put("upKF",Constrats.TZ_KFXTZX);
        map.put("upCH",Constrats.TZ_CHXTZX);
        map.put("lowKF", Constrats.TZ_KFXTXX);
        map.put("lowCH", Constrats.TZ_CHXTXX);

        String sumsql = "SELECT COUNT(*) FROM APP_USER_BLOODGLU WHERE  DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME  AND UG_USER_ID = :userId";
        String highsql = "SELECT COUNT(*) FROM APP_USER_BLOODGLU WHERE ((UG_BLOODGLU > :upKF AND UG_BGSTATE IN ('1','3','5','7','8','9')) or (UG_BLOODGLU > :upCH AND UG_BGSTATE IN ('2','4','6'))) " +
                " AND DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME AND UG_USER_ID = :userId";
        String lowsql = "SELECT COUNT(*) FROM APP_USER_BLOODGLU WHERE ((UG_BLOODGLU < :lowKF AND UG_BGSTATE IN ('1','3','5','7','8','9')) or (UG_BLOODGLU < :lowCH AND UG_BGSTATE IN ('2','4','6')))  " +
                " AND DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME AND UG_USER_ID = :userId";
        String maxsql = "SELECT MAX(UG_BLOODGLU) FROM APP_USER_BLOODGLU WHERE DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME AND UG_USER_ID = :userId";
        String minsql = "SELECT MIN(UG_BLOODGLU) FROM APP_USER_BLOODGLU WHERE DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME AND UG_USER_ID = :userId";
        String avg0sql = "SELECT AVG(UG_BLOODGLU) FROM APP_USER_BLOODGLU  WHERE UG_BGSTATE = '1' AND DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME AND UG_USER_ID = :userId";
        String avg1sql = "SELECT AVG(UG_BLOODGLU) FROM APP_USER_BLOODGLU WHERE UG_BGSTATE = '2' AND DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME AND UG_USER_ID = :userId";
        String avg2sql = "SELECT AVG(UG_BLOODGLU) FROM APP_USER_BLOODGLU WHERE UG_BGSTATE = '3' AND DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME AND UG_USER_ID = :userId";
        String avg3sql = "SELECT AVG(UG_BLOODGLU) FROM APP_USER_BLOODGLU WHERE UG_BGSTATE = '4' AND DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME AND UG_USER_ID = :userId";
        String avg4sql = "SELECT AVG(UG_BLOODGLU) FROM APP_USER_BLOODGLU WHERE UG_BGSTATE = '5' AND DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME AND UG_USER_ID = :userId";
        String avg5sql = "SELECT AVG(UG_BLOODGLU) FROM APP_USER_BLOODGLU WHERE UG_BGSTATE = '6' AND DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME AND UG_USER_ID = :userId";
        String avg6sql = "SELECT AVG(UG_BLOODGLU) FROM APP_USER_BLOODGLU WHERE UG_BGSTATE = '7' AND DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME AND UG_USER_ID = :userId";
        String avg7sql = "SELECT AVG(UG_BLOODGLU) FROM APP_USER_BLOODGLU WHERE UG_BGSTATE = '8' AND DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME AND UG_USER_ID = :userId";
        String avg8sql = "SELECT AVG(UG_BLOODGLU) FROM APP_USER_BLOODGLU WHERE UG_BGSTATE = '9' AND DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME AND UG_USER_ID = :userId";
        String sql = "SELECT ("+sumsql+") sum,("+highsql+") high,("+lowsql+") low, ("+maxsql+") max, ("+minsql+") min, ("+avg0sql+") avg1, ("+avg1sql+") avg2, ("+avg2sql+") avg3, ("+avg3sql+") avg4, " +
                "("+avg4sql+") avg5, ("+avg5sql+") avg6, ("+avg6sql+") avg7, ("+avg7sql+") avg8, ("+avg8sql+") avg9 FROM DUAL";
        List<AppGluCountEntity> list = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppGluCountEntity.class);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 查看血糖数据 分时段
     * @param userId
     * @param bgstate
     * @param period
     * @return
     */
    @Override
    public List<AppGluEntity> findLook(String userId, String bgstate, String period) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT UG_BLOODGLU glu, UG_BGSTATE bgState, UG_TEST_TIME time FROM APP_USER_BLOODGLU WHERE 1=1 ";
        if(StringUtils.isNotBlank(userId)){
            map.put("userId",userId);
            sql += " AND UG_USER_ID = :userId";
        }
        if(StringUtils.isNotBlank(period)){
            if("1".equals(period)){
                map.put("days",0);
            }else if("2".equals(period)){
                map.put("days",7);
            }else if("3".equals(period)){
                map.put("days",30);
            }
            sql +=" AND DATE_SUB(CURDATE(), INTERVAL :days DAY) <= UG_TEST_TIME";
        }
        if(StringUtils.isNotBlank(bgstate)){
            if("1".equals(bgstate)){//空腹
                sql += " AND UG_BGSTATE IN (1)";
            }else if("2".equals(bgstate)){//餐前
                sql += " AND UG_BGSTATE IN (3,5)";
            }else if("3".equals(bgstate)){//餐后
                sql += " AND UG_BGSTATE IN (2,4,6)";
            }else if("4".equals(bgstate)){//全天
            }
        }
        sql += " ORDER BY UG_TEST_TIME";
        return  this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppGluEntity.class);
    }

    @Override
    public List<AppGluEntity> findAll(String userId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",userId);
        String sql = "select UG_TEST_TIME time, UG_BGSTATE bgState, AVG(UG_BLOODGLU) glu from APP_USER_BLOODGLU " +
                " where UG_USER_ID = :userId " +
                " GROUP BY date_format(UG_TEST_TIME,'%Y-%m-%d'),bgState";
        sql += " ORDER BY UG_TEST_TIME DESC";
        List<AppGluEntity> list = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppGluEntity.class);
        return list;
    }

    @Override
    public AppGluEntity findLatest(String patientId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",patientId);
        String sql = "select UG_TEST_TIME time, UG_BGSTATE bgState, UG_BLOODGLU glu from APP_USER_BLOODGLU " +
                " where UG_USER_ID = :userId  order by UG_TEST_TIME DESC ";
        List<AppGluEntity> list = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppGluEntity.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
    @Override
    public AppGluEntity findLatestT(String patientId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",patientId);
        String time = ExtendDate.getYMD(Calendar.getInstance());
        map.put("startTime",time+" 00:00:00");
        map.put("endTime",time+" 23:59:59");
        map.put("state",CommonUgState.KF.getValue());
        String sql = "select UG_TEST_TIME time, UG_BGSTATE bgState, UG_BLOODGLU glu from APP_USER_BLOODGLU " +
                " where UG_TEST_TIME >=:startTime AND UG_TEST_TIME <=:endTime AND UG_BGSTATE =:state AND  UG_USER_ID = :userId  order by UG_TEST_TIME DESC ";
        List<AppGluEntity> list = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppGluEntity.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

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
                "a.PATIENT_ADDRESS addr,\n" +
                "a.PATIENT_X xaxis,\n" +
                "a.PATIENT_Y yaxis\n" +
                "FROM app_patient_user a  \n" +
                "WHERE a.ID =:patientId ";
        List<AppSmPeopleBasicEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppSmPeopleBasicEntity.class);
        if(list != null && list.size()>0) {
            vo = list.get(0);
            //查询签约信息
            AppSmSignEntity ss = sysDao.getAppSignformDao().findSignBypatientId(qvo.getPatientId());
            if (ss != null) {
                vo.setTeamId(ss.getTeamId());
                vo.setTeamName(ss.getTeamName());
                vo.setOrgId(ss.getOrgId());
                vo.setOrgName(ss.getOrgName());
                vo.setDrId(ss.getDrId());
                vo.setDrName(ss.getDrName());
                vo.setDrTel(ss.getDrTel());
            }
            List<AppBloodSugarEntity> listBs = findPeopleBs(qvo);
            if(listBs != null && listBs.size()>0){
                vo.setListBS(listBs);
            }
            return vo;
        }
        return null;
    }
    public List<AppBloodSugarEntity> findPeopleBs(AppSmPeopleBasicVo qvo) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("patientId",qvo.getPatientId());
        String sql = "SELECT\n" +
                "\tUG_BLOODGLU bloodSugarValue,\n" +
                "\tUG_TEST_TIME measureTime,\n" +
                "\tUG_BGSTATE ugBgstate, " +
                "\tSOURCE_TYPE sourceType,\n" +
                "\tABNORMAL_STATE abnormalState\n" +
                "FROM\n" +
                "\tAPP_USER_BLOODGLU\n" +
                "WHERE\n" +
                "\tUG_USER_ID =:patientId ORDER BY UG_TEST_TIME DESC";
        List<AppBloodSugarEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppBloodSugarEntity.class,qvo);
        if(list != null && list.size()>0){
            return list;
        }
        return null;
    }
}
