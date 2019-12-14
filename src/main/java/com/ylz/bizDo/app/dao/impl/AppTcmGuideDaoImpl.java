package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppTcmGuideDao;
import com.ylz.bizDo.app.po.AppTcmGuide;
import com.ylz.bizDo.app.po.AppTcmResult;
import com.ylz.bizDo.app.po.AppUserTcmGuide;
import com.ylz.bizDo.jtapp.commonEntity.AppNewUserTcmGuideEntity;
import com.ylz.bizDo.jtapp.commonVo.*;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonTcmType;
import com.ylz.packcommon.common.comEnum.DrPatientType;
import com.ylz.packcommon.common.comEnum.NoticesType;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zzl on 2017/8/29.
 */
@Service("appTcmGuideDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppTcmGuideDaoImpl implements AppTcmGuideDao {
    @Autowired
    private SysDao sysDao;

    /**
     * 查询中医药保健指导
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppNewTcmGuideQvo> findGuideMod(AppNewTcmQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT ID id," +
                "TCMG_CREATE_ID createId," +
                "DATE_FORMAT(TCMG_CREATE_TIME, '%Y-%c-%d %H:%i:%s') createTime," +
                "TCMG_CORPOREITY_TYPE tzlx," +
                "TCMG_MODERN_CULTIVATE qzts," +
                "TCMG_DIET_AFTERCARE ysty," +
                "TCMG_DAILY_LIFE_CULTIVATE qjts," +
                "TCMG_SPORTS_HEALTH ydbj," +
                "TCMG_MERIDIAN_HEALTH xwbj," +
                "TCMG_OTHER qt," +
                "TCMG_TYPE type " +
                " FROM APP_TCM_GUIDE WHERE 1=1";
        if(CommonTcmType.YS.getValue().equals(qvo.getType())){
            map.put("drId",qvo.getDrId());
            sql += " AND TCMG_CREATE_ID =:drId";
        }
        if(CommonTcmType.YY.getValue().equals(qvo.getType())){
            map.put("hospId",qvo.getHospId());
            sql += " AND TCMG_CREATE_ID =:hospId";
        }
        if(CommonTcmType.XT.getValue().equals(qvo.getType())){
            map.put("type","0");
            sql += " AND TCMG_CREATE_ID =:type";
        }
        if(StringUtils.isNotBlank(qvo.getTzlx())){
            map.put("tzlx",qvo.getTzlx().split(";"));
            sql += " AND TCMG_CORPOREITY_TYPE IN :tzlx";
        }
        List<AppNewTcmGuideQvo> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppNewTcmGuideQvo.class);
        return ls;
    }

    /**
     * 保存或修改中医药保健指导
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppNewTcmGuideQvo> saveTcmGuide(AppTcmListGuideQvo qvo) throws Exception {
        List<AppNewTcmGuideQvo> listTcm = new ArrayList<AppNewTcmGuideQvo>();
        if(qvo.getGuideList()!=null&&qvo.getGuideList().size()>0){
            List<AppTcmGuide> lss = new ArrayList<AppTcmGuide>();
            String createId = "0";
            if(CommonTcmType.YS.getValue().equals(qvo.getType())){
                lss = sysDao.getServiceDo().loadByPk(AppTcmGuide.class,"tcmgCreateId",qvo.getDrId());
                createId = qvo.getDrId();
            }else if(CommonTcmType.YY.getValue().equals(qvo.getType())){
                lss = sysDao.getServiceDo().loadByPk(AppTcmGuide.class,"tcmgCreateId",qvo.getHospId());
                createId = qvo.getHospId();
            }else{
                lss = sysDao.getServiceDo().loadByPk(AppTcmGuide.class,"tcmgCreateId","0");
            }
            if(lss!=null&&lss.size()>0){
                for(AppNewTcmGuideQvo ls:qvo.getGuideList()){
                    boolean flag = true;
                    for(AppTcmGuide ll:lss){
                        if(ls.getTzlx().equals(ll.getTcmgCorporeityType())){//体质相同
                            ll.setTcmgModernCultivate(ls.getQzts());
                            ll.setTcmgDietAftercare(ls.getYsty());
                            ll.setTcmgDailyLifeCultivate(ls.getQjts());
                            ll.setTcmgSportsHealth(ls.getYdbj());
                            ll.setTcmgMeridianHealth(ls.getXwbj());
                            ll.setTcmgOther(ls.getQt());
                            sysDao.getServiceDo().modify(ll);
                            flag = false;
                        }
                    }
                    if(flag){
                        AppTcmGuide gg = new AppTcmGuide();
                        gg.setTcmgType(qvo.getType());
                        gg.setTcmgCorporeityType(ls.getTzlx());
                        gg.setTcmgCreateTime(Calendar.getInstance());
                        gg.setTcmgCreateId(createId);
                        gg.setTcmgModernCultivate(ls.getQzts());
                        gg.setTcmgDietAftercare(ls.getYsty());
                        gg.setTcmgDailyLifeCultivate(ls.getQjts());
                        gg.setTcmgSportsHealth(ls.getYdbj());
                        gg.setTcmgMeridianHealth(ls.getXwbj());
                        gg.setTcmgOther(ls.getQt());
                        sysDao.getServiceDo().add(gg);
                        AppNewTcmGuideQvo tt = new AppNewTcmGuideQvo();
                        tt.setId(gg.getId());
                        tt.setCreateId(gg.getTcmgCreateId());
                        tt.setCreateTime(ExtendDate.getYMD_h_m_s(gg.getTcmgCreateTime()));
                        tt.setTzlx(gg.getTcmgCorporeityType());
                        tt.setQzts(gg.getTcmgModernCultivate());
                        tt.setYsty(gg.getTcmgDietAftercare());
                        tt.setQjts(gg.getTcmgDailyLifeCultivate());
                        tt.setYdbj(gg.getTcmgSportsHealth());
                        tt.setXwbj(gg.getTcmgMeridianHealth());
                        tt.setQt(gg.getTcmgOther());
                        listTcm.add(tt);
                    }
                }
                for(AppTcmGuide ll:lss){
                    AppNewTcmGuideQvo tt = new AppNewTcmGuideQvo();
                    tt.setId(ll.getId());
                    tt.setCreateId(ll.getTcmgCreateId());
                    tt.setCreateTime(ExtendDate.getYMD_h_m_s(ll.getTcmgCreateTime()));
                    tt.setTzlx(ll.getTcmgCorporeityType());
                    tt.setQzts(ll.getTcmgModernCultivate());
                    tt.setYsty(ll.getTcmgDietAftercare());
                    tt.setQjts(ll.getTcmgDailyLifeCultivate());
                    tt.setYdbj(ll.getTcmgSportsHealth());
                    tt.setXwbj(ll.getTcmgMeridianHealth());
                    tt.setQt(ll.getTcmgOther());
                    listTcm.add(tt);
                }
            }else{
                for(AppNewTcmGuideQvo ls:qvo.getGuideList()){
                    if(StringUtils.isBlank(ls.getId())){
                        AppTcmGuide tg = new AppTcmGuide();
                        tg.setTcmgCorporeityType(ls.getTzlx());
                        tg.setTcmgCreateTime(Calendar.getInstance());
                        tg.setTcmgModernCultivate(ls.getQzts());
                        tg.setTcmgDietAftercare(ls.getYsty());
                        tg.setTcmgDailyLifeCultivate(ls.getQjts());
                        tg.setTcmgSportsHealth(ls.getYdbj());
                        tg.setTcmgMeridianHealth(ls.getXwbj());
                        tg.setTcmgOther(ls.getQt());
                        tg.setTcmgType(qvo.getType());
                        if("1".equals(qvo.getType())){//医生
                            tg.setTcmgCreateId(qvo.getDrId());
                        }else if("2".equals(qvo.getType())){
                            tg.setTcmgCreateId(qvo.getHospId());
                        }else{
                            tg.setTcmgCreateId("0");
                        }
                        sysDao.getServiceDo().add(tg);
                        AppNewTcmGuideQvo tt = new AppNewTcmGuideQvo();
                        tt.setId(tg.getId());
                        tt.setCreateId(tg.getTcmgCreateId());
                        tt.setCreateTime(ExtendDate.getYMD_h_m_s(tg.getTcmgCreateTime()));
                        tt.setTzlx(tg.getTcmgCorporeityType());
                        tt.setQzts(tg.getTcmgModernCultivate());
                        tt.setYsty(tg.getTcmgDietAftercare());
                        tt.setQjts(tg.getTcmgDailyLifeCultivate());
                        tt.setYdbj(tg.getTcmgSportsHealth());
                        tt.setXwbj(tg.getTcmgMeridianHealth());
                        tt.setQt(tg.getTcmgOther());
                        listTcm.add(tt);
                    }
                }
            }
        }
        return listTcm;
    }

    /**
     * 发送中医药保健指导
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String fsGuide(AppNewTcmVo qvo) throws Exception {
        if(qvo.getGuideList()!=null&&qvo.getGuideList().size()>0){
            List<AppUserTcmGuide> list = sysDao.getServiceDo().loadByPk(AppUserTcmGuide.class,"utgTcmId",qvo.getJlId());
            if(list!=null&&list.size()>0){
                return "此次指导已发送";
            }else{
                for(AppNewTcmGuideQvo ls:qvo.getGuideList()){
                    AppTcmGuide tg = (AppTcmGuide) sysDao.getServiceDo().find(AppTcmGuide.class,ls.getId());
                    if(tg!=null){
                        AppUserTcmGuide utg = new AppUserTcmGuide();
                        utg.setUtgCorporeityType(tg.getTcmgCorporeityType());
                        utg.setUtgCreateId(qvo.getDrId());
                        utg.setUtgTcmId(qvo.getJlId());
                        utg.setUtgCreateTime(Calendar.getInstance());
                        utg.setUtgDailyLifeCultivate(tg.getTcmgDailyLifeCultivate());
                        utg.setUtgDietAftercare(tg.getTcmgDietAftercare());
                        utg.setUtgMeridianHealth(tg.getTcmgMeridianHealth());
                        utg.setUtgModernCultivate(tg.getTcmgModernCultivate());
                        utg.setUtgSportsHealth(tg.getTcmgSportsHealth());
                        if(StringUtils.isNotBlank(ls.getQt())){
                            utg.setUtgOther(ls.getQt());
                        }else{
                            utg.setUtgOther("");
                        }
                        utg.setUtgUserId(qvo.getUserId());
                        sysDao.getServiceDo().add(utg);
                        AppTcmResult result = sysDao.getAppTcmSyndromeDao().findResult(utg.getUtgTcmId(),utg.getUtgCorporeityType());
                        if(result != null){
                            result.setTcmrFsState("1");
                            sysDao.getServiceDo().modify(result);
                        }

                        sysDao.getAppNoticeDao().addNotices("中医药保健指导",
                                "您好，"+utg.getDrName()+"医生在"+ ExtendDate.getChineseYMD(Calendar.getInstance())+"给您发了一条"+utg.getStrUtgCorporeityType()+"类型的中医药保健指导，请注意查看。",
                                NoticesType.JKZD.getValue()+","+"1",utg.getUtgCreateId(),utg.getUtgUserId(),utg.getId(), DrPatientType.PATIENT.getValue());
                    }
                }
            }
        }else{
            return "指导内容为空";
        }
        return "true";
    }

    /**
     * 查询中医药保健指导记录
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppNewUserTcmGuideEntity> findGuideList(AppUserTcmGuideQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT ID id," +
                "UTG_CREATE_ID createId," +
                "UTG_CORPOREITY_TYPE tzlx," +
                "UTG_MODERN_CULTIVATE qzts," +
                "UTG_DIET_AFTERCARE ysty," +
                "UTG_DAILY_LIFE_CULTIVATE qjts," +
                "UTG_SPORTS_HEALTH ydbj," +
                "UTG_MERIDIAN_HEALTH xwbj," +
                "UTG_OTHER qt," +
                "UTG_USER_ID userId," +
                "DATE_FORMAT(UTG_CREATE_TIME, '%Y-%c-%d %H:%i:%s') createTime," +
                "UTG_TCM_ID jlId " +
                "FROM APP_USER_TCM_GUIDE WHERE 1=1";
        map.put("userId",qvo.getUserId());
        if(DrPatientType.PATIENT.getValue().equals(qvo.getType())){//患者查询指导记录
           sql += " AND UTG_USER_ID=:userId";
        }else if(DrPatientType.DR.getValue().equals(qvo.getType())){//医生查询指导记录
            sql += " AND UTG_CREATE_ID=:userId";
        }
        List<AppNewUserTcmGuideEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppNewUserTcmGuideEntity.class);
        return ls;
    }

}
