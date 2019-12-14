package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppTcmSyndromeDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.basicHealthVo.AppBasicTcmAllQvo;
import com.ylz.bizDo.jtapp.basicHealthVo.AppBasicTcmGuideQvo;
import com.ylz.bizDo.jtapp.basicHealthVo.AppBasicTcmResultQvo;
import com.ylz.bizDo.jtapp.commonEntity.*;
import com.ylz.bizDo.jtapp.commonVo.*;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.FileUtils;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zzl on 2017/8/4.
 */
@Service("appTcmSyndromeDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppTcmSyndromeDaoImpl implements AppTcmSyndromeDao {
    @Autowired
    private SysDao sysDao;

    /**
     * 保存体质辨识
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppTcmEntity> saveTcm(AppTcmQvo qvo) throws Exception {
        if(StringUtils.isNotBlank(qvo.getJlId())){
            List<AppTcmResult> lss = sysDao.getServiceDo().loadByPk(AppTcmResult.class,"tcmrId",qvo.getJlId());
            if(lss!=null&&lss.size()>0){
                for(AppTcmResult ls:lss){
                    sysDao.getServiceDo().delete(ls);
                }
            }
            AppTcmSyndrome tsd = (AppTcmSyndrome) sysDao.getServiceDo().find(AppTcmSyndrome.class,qvo.getJlId());
            if(tsd!=null){
                sysDao.getServiceDo().delete(tsd);
            }
        }
        AppTcmSyndrome table = new AppTcmSyndrome();
        table.setTcmCreateTime(Calendar.getInstance());
        table.setTcmQuestionValue(qvo.getQuestion());
        table.setTcmChooseNum(qvo.getResult());
        table.setTcmUserId(qvo.getUserId());
        table.setTcmType(qvo.getType());
        String drUserId = null;
        String hospId = null;
        if("1".equals(qvo.getType())){//患者自测
            table.setTcmDrId(qvo.getUserId());
            table.setTcmDrName(qvo.getUserName());
        }else{
            table.setTcmDrId(qvo.getDrId());
            table.setTcmDrName(qvo.getDrName());
            drUserId = qvo.getDrId();
        }
        AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getUserId());
        if(user!=null){
            table.setTcmUserName(user.getPatientName());
        }
        if(StringUtils.isNotBlank(qvo.getResult())){
            String[] strs = qvo.getResult().split(";");
            String strr = "";
            for(String str:strs){
                String stt = "";
                if("A".equals(str)){
                    stt = "1";
                }else if("B".equals(str)){
                    stt = "2";
                }else if("C".equals(str)){
                    stt = "3";
                }else if("D".equals(str)){
                    stt = "4";
                }else if("E".equals(str)){
                    stt = "5";
                }
                if(StringUtils.isBlank(strr)){
                    strr = stt;
                }else {
                    strr +=";"+stt;
                }
            }
            table.setTcmScode(strr);
        }
        sysDao.getServiceDo().add(table);
        if(CommonDrPartientType.yisheng.getValue().equals(qvo.getType())){
            //履约数据
            PerformanceDataQvo qqvo = new PerformanceDataQvo();
            AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
            if(user!=null){
                AppSignForm form= sysDao.getAppSignformDao().getSignFormUserId(user.getId());
                if(form != null){
                    qqvo.setPerName(user.getPatientName());
                    qqvo.setPerIdno(user.getPatientIdno());
                    qqvo.setPerType(PerformanceType.ZYTZBS.getValue());
                    qqvo.setPerForeign(table.getId());
                    qqvo.setPerSource("2");
                    if(StringUtils.isNotBlank(user.getPatientCity())){
                        CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                        if(p != null){
                            String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                            if(value != null) {
                                qqvo.setPerArea(value.getCodeTitle());
                            }
                        }
                    }
                    if(drUser != null){
                        qqvo.setDrName(drUser.getDrName());
                        qqvo.setDrAccount(drUser.getDrAccount());
                        qqvo.setDrPwd(drUser.getDrPwd());
                        qqvo.setDrGender(drUser.getDrGender());
                        qqvo.setDrTel(drUser.getDrTel());
                        qqvo.setDrId(drUser.getId());
                        AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                        if(dept!=null){
                            hospId = dept.getId();
                            qqvo.setHospId(dept.getId());
                            qqvo.setHospName(dept.getHospName());
                            qqvo.setHospAreaCode(dept.getHospAreaCode());
                            qqvo.setHospAddress(dept.getHospAddress());
                            qqvo.setHospTel(dept.getHospTel());
                        }
                    }
                    if(StringUtils.isNotBlank(qqvo.getPerArea())){
                        if(StringUtils.isNotBlank(qqvo.getPerType())){
//                    String result = null;
//                    if (qqvo.getPerArea().equals(AddressType.FZ.getValue())) {
//
//                    } else if (qqvo.getPerArea().equals(AddressType.QZ.getValue())) {
//                        result = "qz_";
//                    } else if (qqvo.getPerArea().equals(AddressType.ZZ.getValue())) {
//                        result = "zz_";
//                    } else if (qqvo.getPerArea().equals(AddressType.PT.getValue())) {
//                        result = "pt_";
//                    } else if (qqvo.getPerArea().equals(AddressType.NP.getValue())) {
//                        result = "np_";
//                    } else if (qqvo.getPerArea().equals(AddressType.SM.getValue())) {
//                        result = "sm_";
//                    }
//                    if(StringUtils.isNotBlank(result)){
//                        qqvo.setDrId(result+qqvo.getDrId());
//                        qqvo.setHospId(result+qqvo.getHospId());
//                    }
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

        List<AppTcmEntity> list = new ArrayList<AppTcmEntity>();
        if(StringUtils.isNotBlank(table.getTcmScode())){
            String[] strs =  table.getTcmScode().split(";");
            Integer qxzDf = Integer.parseInt(strs[1])+Integer.parseInt(strs[2])+Integer.parseInt(strs[3])+Integer.parseInt(strs[13]);
            Integer yangxzDf = Integer.parseInt(strs[10])+Integer.parseInt(strs[11])+Integer.parseInt(strs[12])+Integer.parseInt(strs[28]);
            Integer yinxzDf = Integer.parseInt(strs[9])+Integer.parseInt(strs[20])+Integer.parseInt(strs[25])+Integer.parseInt(strs[30]);
            Integer tszDf = Integer.parseInt(strs[8])+Integer.parseInt(strs[15])+Integer.parseInt(strs[27])+Integer.parseInt(strs[31]);
            Integer srzDf = Integer.parseInt(strs[22])+Integer.parseInt(strs[24])+Integer.parseInt(strs[26])+Integer.parseInt(strs[29]);
            Integer xyzDf = Integer.parseInt(strs[18])+Integer.parseInt(strs[21])+Integer.parseInt(strs[23])+Integer.parseInt(strs[32]);
            Integer qyzDf = Integer.parseInt(strs[4])+Integer.parseInt(strs[5])+Integer.parseInt(strs[6])+Integer.parseInt(strs[7]);
            Integer tbzDf = Integer.parseInt(strs[14])+Integer.parseInt(strs[16])+Integer.parseInt(strs[17])+Integer.parseInt(strs[19]);
            Integer phzDf = Integer.parseInt(strs[0])+jsResult(strs[1])+jsResult(strs[3])+jsResult(strs[4])+jsResult(strs[12]);

            AppTcmEntity t1 = tcmResult(qxzDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.QXZ.getValue(),table.getTcmType());
            list.add(t1);
            AppTcmEntity t2 = tcmResult(yangxzDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.YANGXZ.getValue(),table.getTcmType());
            list.add(t2);
            AppTcmEntity t3 = tcmResult(yinxzDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.YINXZ.getValue(),table.getTcmType());
            list.add(t3);
            AppTcmEntity t4 = tcmResult(tszDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.TSZ.getValue(),table.getTcmType());
            list.add(t4);
            AppTcmEntity t5 = tcmResult(srzDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.SRZ.getValue(),table.getTcmType());
            list.add(t5);
            AppTcmEntity t6 = tcmResult(xyzDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.XYZ.getValue(),table.getTcmType());
            list.add(t6);
            AppTcmEntity t7 = tcmResult(qyzDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.QYZ.getValue(),table.getTcmType());
            list.add(t7);
            AppTcmEntity t8 = tcmResult(tbzDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.TBZ.getValue(),table.getTcmType());
            list.add(t8);
            //平和质
            AppTcmResult tt = new AppTcmResult();
            tt.setTcmrScore(String.valueOf(phzDf));
            tt.setTcmrUserId(table.getTcmUserId());
            tt.setTcmrUserName(table.getTcmUserName());
            tt.setTcmrDrId(table.getTcmDrId());
            tt.setTcmrDrName(table.getTcmDrName());
            tt.setTcmrResultType(CommTcmTz.PHZ.getValue());
            tt.setTcmrId(table.getId());
            if(phzDf>=17&&qxzDf<=8&&yangxzDf<=8&&yinxzDf<=8&&tszDf<=8&&srzDf<=8&&xyzDf<=8&&qyzDf<=8&&tbzDf<=8){
                tt.setTcmrResultValue(CommTcmBs.SHI.getValue());
            }else if(phzDf>=17&&qxzDf<=10&&yangxzDf<=10&&yinxzDf<=10&&tszDf<=10&&srzDf<=10&&xyzDf<=10&&qyzDf<=10&&tbzDf<=10){
                tt.setTcmrResultValue(CommTcmBs.QXS.getValue());
            }else{
                tt.setTcmrResultValue(CommTcmBs.FOU.getValue());
            }
            tt.setTcmrModernCultivate(CommTcmGuide.QZTS.getValue());//情志调摄指导
            tt.setTcmrFoodNursing(CommTcmGuide.YSTY.getValue());//饮食调养指导
            tt.setTcmrDailyLifeCultivate(CommTcmGuide.QJTS.getValue());//起居调摄指导
            tt.setTcmrSportsHealthCare(CommTcmGuide.YDBJ.getValue());//运动保健指导
            tt.setTcmrMeridianHealth(CommTcmGuide.XWBJ.getValue());//穴位保健指导
            tt.setTcmrCreateTime(Calendar.getInstance());
            sysDao.getServiceDo().add(tt);
            AppTcmEntity entity = new AppTcmEntity();
            entity.setId(tt.getId());
            entity.setDf(tt.getTcmrScore());
            entity.setTzbs(tt.getTcmrResultValue());
            entity.setTzType(tt.getTcmrResultType());
            entity.setQzts(tt.getTcmrModernCultivate());
            entity.setYsty(tt.getTcmrFoodNursing());
            entity.setQjts(tt.getTcmrDailyLifeCultivate());
            entity.setYdbj(tt.getTcmrSportsHealthCare());
            entity.setXwbj(tt.getTcmrMeridianHealth());
            entity.setJlId(tt.getTcmrId());
            if("1".equals(table.getTcmType())){
                if(CommTcmBs.SHI.getValue().equals(tt.getTcmrResultValue())||
                        CommTcmBs.QXS.getValue().equals(tt.getTcmrResultValue())){
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("tcmType",tt.getTcmrResultType());
                    map.put("guideType","3");//系统
                    String sql = "select a.* from APP_TCM_GUIDE a where 1=1 ";
                    sql += "and a.TCMG_CORPOREITY_TYPE =:tcmType";
                    sql += " and a.TCMG_TYPE=:guideType";
                    List<AppTcmGuide> guides = sysDao.getServiceDo().findSqlMap(sql,map,AppTcmGuide.class);
                    if(guides!=null&&guides.size()>0){
                        AppTcmGuide guide = guides.get(0);
                        AppUserTcmGuide userTcmGuide = new AppUserTcmGuide();
                        userTcmGuide.setUtgCorporeityType(tt.getTcmrResultType());
                        userTcmGuide.setUtgTcmId(tt.getTcmrId());
                        userTcmGuide.setUtgUserId(tt.getTcmrUserId());
                        userTcmGuide.setUtgCreateId(tt.getTcmrUserId());
                        userTcmGuide.setUtgCreateTime(Calendar.getInstance());
                        userTcmGuide.setUtgMeridianHealth(guide.getTcmgMeridianHealth());
                        userTcmGuide.setUtgSportsHealth(guide.getTcmgSportsHealth());
                        userTcmGuide.setUtgDailyLifeCultivate(guide.getTcmgDailyLifeCultivate());
                        userTcmGuide.setUtgDietAftercare(guide.getTcmgDietAftercare());
                        userTcmGuide.setUtgModernCultivate(guide.getTcmgModernCultivate());
//                userTcmGuide.setUtgType("3");
                        sysDao.getServiceDo().add(userTcmGuide);
                    }
                }

            }
            list.add(entity);
//            if(StringUtils.isNotBlank(drUserId)){
//                AppTcmJwQvo qqvvo = new AppTcmJwQvo();
//                qqvvo.setDf_id(user.getPatientjmda());
//                qqvvo.setDrId(drUserId);
//                qqvvo.setUrlType(urlType);
//                qqvvo.setListTcm(list);
//                if(StringUtils.isNotBlank(ccode)){
//                    if(AddressType.FZS.getValue().equals(ccode)){
//                        qqvvo.setDrId(drUserId);
//                        qqvvo.setOrgId(hospId);
//                    }else if(AddressType.XMS.getValue().equals(ccode)){
//                        qqvvo.setDrId(drUserId.replaceAll("xm_",""));
//                        qqvvo.setOrgId(hospId.replaceAll("xm_",""));
//                    }else if(AddressType.PTS.getValue().equals(ccode)){
//                        qqvvo.setDrId(drUserId.replaceAll("pt_",""));
//                        qqvvo.setOrgId(hospId.replaceAll("pt_",""));
//                    }else if(AddressType.SMS.getValue().equals(ccode)){
//                        qqvvo.setDrId(drUserId.replaceAll("sm_",""));
//                        qqvvo.setOrgId(hospId.replaceAll("sm_",""));
//                    }else if(AddressType.QZS.getValue().equals(ccode)){
//                        qqvvo.setDrId(drUserId.replaceAll("qz_",""));
//                        qqvvo.setOrgId(hospId.replaceAll("qz_",""));
//                    }else if(AddressType.ZZS.getValue().equals(ccode)){
//                        qqvvo.setDrId(drUserId.replaceAll("zz_",""));
//                        qqvvo.setOrgId(hospId.replaceAll("zz_",""));
//                    }else if(AddressType.NPS.getValue().equals(ccode)){
//                        qqvvo.setDrId(drUserId.replaceAll("np_",""));
//                        qqvvo.setOrgId(hospId.replaceAll("np_",""));
//                    }else if(AddressType.LYS.getValue().equals(ccode)){
//                        qqvvo.setDrId(drUserId.replaceAll("ly_",""));
//                        qqvvo.setOrgId(hospId.replaceAll("ly_",""));
//                    }else if(AddressType.NDS.getValue().equals(ccode)){
//                        qqvvo.setDrId(drUserId.replaceAll("nd_",""));
//                        qqvvo.setOrgId(hospId.replaceAll("nd_",""));
//                    }else if(AddressType.PTZHSYQ.equals(ccode)){
//                        qqvvo.setDrId(drUserId.replaceAll("pg_",""));
//                        qqvvo.setOrgId(hospId.replaceAll("pg_",""));
//                    }
//                }
//                boolean flag = sysDao.getSecurityCardAsyncBean().uploadTcmToBasic(qqvvo);
//                if(flag){
//                    table.setUploadState("1");
//                }else{
//                    table.setUploadState("2");
//                }
//                sysDao.getServiceDo().modify(table);
//            }

            return list;
        }
        return null;
    }

    /**
     * 查询中医体质辨识记录列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppTcmListEntity findList(AppTcmQvo qvo) throws Exception {
        AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getUserId());
        AppTcmListEntity table = new AppTcmListEntity();
        if(user!=null){
            table.setUserId(user.getId());
            table.setUserName(user.getPatientName());
            table.setIdNo(user.getPatientIdno());
            table.setTel(user.getPatientTel());
            String patientAddress = "";
            if (user != null) {
                if(StringUtils.isNotBlank(user.getPatientProvince())){
                    CdAddress provience = (CdAddress) sysDao.getServiceDo().find(CdAddress.class, user.getPatientProvince());
                    if(provience != null) {
                        patientAddress += provience.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientCity())){
                    CdAddress city = (CdAddress) sysDao.getServiceDo().find(CdAddress.class, user.getPatientCity());
                    if(city != null) {
                        patientAddress += city.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientArea())){
                    CdAddress area = (CdAddress) sysDao.getServiceDo().find(CdAddress.class, user.getPatientArea());
                    if(area != null) {
                        patientAddress += area.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientStreet())){
                    CdAddress street = (CdAddress) sysDao.getServiceDo().find(CdAddress.class, user.getPatientStreet());
                    if(street != null) {
                        patientAddress += street.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientNeighborhoodCommittee())){
                    CdAddress committee = (CdAddress) sysDao.getServiceDo().find(CdAddress.class, user.getPatientNeighborhoodCommittee());
                    if(committee != null) {
                        patientAddress += committee.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientAddress())){
                    patientAddress += user.getPatientAddress();
                }
            }
            table.setAddr(patientAddress);
            //分页查询
            Map<String,Object> map = new HashMap<>();
            map.put("userId",user.getId());
            String sql = "SELECT * FROM APP_TCM_SYNDROME WHERE 1=1 AND TCM_USER_ID=:userId ";
            List<AppTcmSyndrome> lisS = sysDao.getServiceDo().findSqlMap(sql,map,AppTcmSyndrome.class,qvo);
//            List<AppTcmSyndrome> lisS = sysDao.getServiceDo().loadByPk(AppTcmSyndrome.class,"tcmUserId",user.getId());
            if(lisS!=null&&lisS.size()>0){
                List<AppTcmLsEntity> list = new ArrayList<AppTcmLsEntity>();
                for(AppTcmSyndrome lis:lisS){
                    AppTcmLsEntity vo = new AppTcmLsEntity();
                    vo.setId(lis.getId());
                    vo.setCreateTime(ExtendDate.getYMD_h_m_s(lis.getTcmCreateTime()));
                    vo.setDrId(lis.getTcmDrId());
                    vo.setDrName(lis.getTcmDrName());
                    List<AppTcmResult> results = sysDao.getServiceDo().loadByPk(AppTcmResult.class,"tcmrId",lis.getId());
                    if(results!=null&&results.size()>0){
                        List<AppTcmEntity> lss = new ArrayList<AppTcmEntity>();
                        for(AppTcmResult result:results){
                            if("1".equals(result.getTcmrFsState())){
                                AppTcmEntity tt = new AppTcmEntity();
                                tt.setId(result.getId());
                                tt.setDf(result.getTcmrScore());
                                tt.setTzbs(result.getTcmrResultValue());
                                tt.setTzType(result.getTcmrResultType());
                                tt.setQzts(result.getTcmrModernCultivate());
                                tt.setYsty(result.getTcmrFoodNursing());
                                tt.setQjts(result.getTcmrDailyLifeCultivate());
                                tt.setYdbj(result.getTcmrSportsHealthCare());
                                tt.setXwbj(result.getTcmrMeridianHealth());
                                lss.add(tt);
                            }
                        }
                        vo.setResulList(lss);
                    }
                    list.add(vo);
                }
                table.setReasult(list);
            }
            return table;
        }
        return null;
    }

    /**
     * 发送中医药保健指导
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public void fsGuide(AppTcmGuideQvo qvo) throws Exception {
        if(StringUtils.isNotBlank(qvo.getId())){
            String[] ids = qvo.getId().split(";");
            for(String id:ids){
                AppTcmGuide tg = (AppTcmGuide)sysDao.getServiceDo().find(AppTcmGuide.class,id);
                if(tg!=null){
                    AppUserTcmGuide utg = new AppUserTcmGuide();
                    utg.setUtgCorporeityType(tg.getTcmgCorporeityType());//体质类型
                    utg.setUtgModernCultivate(tg.getTcmgModernCultivate());//情志调摄
                    utg.setUtgDietAftercare(tg.getTcmgDietAftercare());//饮食调养
                    utg.setUtgDailyLifeCultivate(tg.getTcmgDailyLifeCultivate());//起居调摄
                    utg.setUtgSportsHealth(tg.getTcmgSportsHealth());//运动保健
                    utg.setUtgMeridianHealth(tg.getTcmgMeridianHealth());//穴位保健
                    if(qvo.getOthreGuide()!=null){
                        for( AppTcmGuideOtherQvo s:qvo.getOthreGuide()){
                            if(tg.getTcmgCorporeityType().equals(s.getType())){
                                utg.setUtgOther(s.getContent());
                            }
                        }
                    }
                    utg.setUtgCreateTime(Calendar.getInstance());
                    utg.setUtgCreateId(qvo.getDrId());
                    utg.setUtgUserId(qvo.getUserId());
                    utg.setUtgTcmId(qvo.getJlId());//中医体质辨识答题记录id
                    sysDao.getServiceDo().add(utg);
                    AppTcmResult result = sysDao.getAppTcmSyndromeDao().findResult(utg.getUtgTcmId(),utg.getUtgCorporeityType());
                    if(result != null){
                        result.setTcmrFsState("1");
                        sysDao.getServiceDo().modify(result);
                    }
                    AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
                    sysDao.getAppNoticeDao().addNotices("中医药保健指导",
                            "您好，"+drUser.getDrName()+"医生在"+ ExtendDate.getChineseYMD(Calendar.getInstance())+"给您发了一条中医药"+utg.getStrUtgCorporeityType()+"保健指导，请注意查看。",
                            NoticesType.JKZD.getValue()+","+"1",utg.getUtgCreateId(),utg.getUtgUserId(),utg.getId(), DrPatientType.PATIENT.getValue());
                    //履约数据
                    /*PerformanceDataQvo qqvo = new PerformanceDataQvo();
                    if(StringUtils.isNotBlank(qvo.getUserId())){
                        AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getUserId());
                        if(user!=null){
                            qqvo.setPerName(user.getPatientName());
                            qqvo.setPerIdno(user.getPatientIdno());
                            qqvo.setPerType(PerformanceType.ZYYBJZD.getValue());
                            qqvo.setPerForeign(utg.getId());
                            qqvo.setPerSource("2");
                        }
                        if(drUser != null){
                            qqvo.setDrName(drUser.getDrName());
                            qqvo.setDrAccount(drUser.getDrAccount());
                            qqvo.setDrPwd(drUser.getDrPwd());
                            qqvo.setDrGender(drUser.getDrGender());
                            qqvo.setDrTel(drUser.getDrTel());
                            qqvo.setDrId(drUser.getId());
                            AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                            if(dept!=null){
                                if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                                    CdAddress p = sysDao.getCdAddressDao().findByCode(dept.getHospAreaCode());
                                    if(p != null){
                                        String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                                        if(value != null) {
                                            qqvo.setPerArea(value.getCodeTitle());
                                        }
                                    }
                                }
                                qqvo.setHospId(dept.getId());
                                qqvo.setHospName(dept.getHospName());
                                qqvo.setHospAreaCode(dept.getHospAreaCode());
                                qqvo.setHospAddress(dept.getHospAddress());
                                qqvo.setHospTel(dept.getHospTel());
                            }
                        }
                        if(StringUtils.isNotBlank(qqvo.getPerArea())){
                            if(StringUtils.isNotBlank(qqvo.getPerType())){
                                sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo);
                            }
                        }
                    }*/

                }
            }
        }
    }

    /**
     * 查询中医体质辨识居民列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppTcmPeopleEntity> findPeople(AppTcmQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("teamId",qvo.getTeamId());
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("state",signStates);
        map.put("type",ResidentMangeType.LNR.getValue());
        String sql = "SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.PATIENT_NAME name,\n" +
                "\ta.PATIENT_GENDER gender,\n" +
                "\t'' age,\n" +
                "\ta.PATIENT_IMAGEURL imageUrl,\n" +
                "\tb.ID signId," +
                "'' fwrq\n" +
                "FROM\n" +
                "\tAPP_PATIENT_USER a\n" +
                "INNER JOIN APP_SIGN_FORM b ON a.ID = b.SIGN_PATIENT_ID\n" +
                "WHERE\n" +
                "\tb.SIGN_STATE IN (:state)\n" +
                "AND b.SIGN_TEAM_ID =:teamId\n" +
                " AND b.ID IN (SELECT LABEL_SIGN_ID FROM APP_LABEL_GROUP WHERE LABEL_VALUE =:type AND LABEL_TYPE='3' )";

        if(StringUtils.isNotBlank(qvo.getUserName())){
            map.put("userName","%"+qvo.getUserName()+"%");
            sql += " AND a.PATIENT_NAME LIKE:userName";
        }
        List<AppTcmPeopleEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTcmPeopleEntity.class,qvo);
        return ls;
    }

    /**
     * 保存中医药体质辨识指导模板
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String saveTcmGuide(AppTcmGuideListQvo qvo) throws Exception {
        if(qvo.getGuideLs()!=null&&qvo.getGuideLs().size()>0){
            for(AppTcmTzQvo tzls:qvo.getGuideLs()){
                if(tzls.getGuideList()!=null&&tzls.getGuideList().size()>0){
                    for(AppTcmGuideVo guidels:tzls.getGuideList()){
                        if(StringUtils.isBlank(guidels.getId())){//添加操作
                            AppGuideTemplate gt =new AppGuideTemplate();
                            gt.setGuideTitle(guidels.getTitle());
                            gt.setGuideContent(guidels.getContent());
                            gt.setGuideCreateTime(Calendar.getInstance());
                            if("1".equals(qvo.getRoleType())){
                                gt.setGuideCreateId(qvo.getDrId());
                            }else if("2".equals(qvo.getRoleType())){
                                gt.setGuideCreateId(qvo.getHospId());
                            }
                            if(StringUtils.isNotBlank(guidels.getImageUrl())){
//                                String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureYz"),guidels.getImageName());
//                                FileUtils.decoderBase64File(guidels.getImageUrl(),PropertiesUtil.getConfValue("filePicture")+path);
//                                gt.setGuideImageUrl(path);

                                Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(guidels.getImageUrl(),CommonShortType.YISHENG.getValue());
                                gt.setGuideImageUrl(map.get("objectUrl").toString());
//                    user.setPatientImageName(map.get("objectName").toString());
                            }
                            gt.setGuideType(CommonGuideType.ZYYZD.getValue());
                            gt.setGuideMeddleType(guidels.getGuideType());
                            gt.setGuideDiseaseType(tzls.getTzType());
                            sysDao.getServiceDo().add(gt);
                        }else if(StringUtils.isNotBlank(guidels.getId())){
                            AppGuideTemplate gt = (AppGuideTemplate)sysDao.getServiceDo().find(AppGuideTemplate.class,guidels.getId());
                            gt.setGuideTitle(guidels.getTitle());
                            gt.setGuideContent(guidels.getContent());
                            if(StringUtils.isNotBlank(guidels.getImageUrl())){
//                                String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureYz"),guidels.getImageName());
//                                FileUtils.decoderBase64File(guidels.getImageUrl(),PropertiesUtil.getConfValue("filePicture")+path);
//                                gt.setGuideImageUrl(path);

                                Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(guidels.getImageUrl(),CommonShortType.YISHENG.getValue());
                                gt.setGuideImageUrl(map.get("objectUrl").toString());
//                    user.setPatientImageName(map.get("objectName").toString());
                            }
                            gt.setGuideMeddleType(guidels.getGuideType());
                            gt.setGuideDiseaseType(tzls.getTzType());
                            sysDao.getServiceDo().modify(gt);
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     *查询个人中医药体质辨识记录
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppTcmListEntity findByPersonal(AppTcmQvo qvo) throws Exception {
        AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getUserId());
        AppTcmListEntity table = new AppTcmListEntity();
        if(user!=null){
            table.setUserId(user.getId());
            table.setUserName(user.getPatientName());
            table.setIdNo(user.getPatientIdno());
            table.setTel(user.getPatientTel());
            table.setAddr(user.getPatientAddress());
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = "select a.* from APP_TCM_SYNDROME a where 1=1 ";
            map.put("tcmUserId",user.getId());
            sql += " and a.TCM_USER_ID =:tcmUserId ";
            sql += " order by a.TCM_CREATE_TIME desc";
            List<AppTcmSyndrome> lisS = sysDao.getServiceDo().findSqlMap(sql,map,AppTcmSyndrome.class);
//            List<AppTcmSyndrome> lisS = sysDao.getServiceDo().loadByPk(AppTcmSyndrome.class,"tcmUserId",user.getId());
            if(lisS!=null&&lisS.size()>0){
                List<AppTcmLsEntity> list = new ArrayList<AppTcmLsEntity>();
                for(AppTcmSyndrome lis:lisS){
//                    if("1".equals(lis.getTcmType())){
                        AppTcmLsEntity vo = new AppTcmLsEntity();
                        vo.setId(lis.getId());
                        vo.setCreateTime(ExtendDate.getYMD_h_m_s(lis.getTcmCreateTime()));
                        vo.setDrId(lis.getTcmDrId());
                        vo.setDrName(lis.getTcmDrName());
                        List<AppTcmResult> results = sysDao.getServiceDo().loadByPk(AppTcmResult.class,"tcmrId",lis.getId());
                        if(results!=null&&results.size()>0){
                            List<AppTcmEntity> lss = new ArrayList<AppTcmEntity>();
                            for(AppTcmResult result:results){
                                AppTcmEntity tt = new AppTcmEntity();
                                tt.setId(result.getId());
                                tt.setDf(result.getTcmrScore());
                                tt.setTzbs(result.getTcmrResultValue());
                                tt.setTzType(result.getTcmrResultType());
                                tt.setQzts(result.getTcmrModernCultivate());
                                tt.setYsty(result.getTcmrFoodNursing());
                                tt.setQjts(result.getTcmrDailyLifeCultivate());
                                tt.setYdbj(result.getTcmrSportsHealthCare());
                                tt.setXwbj(result.getTcmrMeridianHealth());
                                lss.add(tt);
                            }
                            vo.setResulList(lss);
//                        }
                        list.add(vo);
                    }
                }
                table.setReasult(list);
            }
            return table;
        }
        return null;
    }

    /**
     * 平和质得分计算
     * @param str
     * @return
     */
    public Integer jsResult(String str){
        if("1".equals(str)){
            return 5;
        }else if("2".equals(str)){
            return 4;
        }else if("3".equals(str)){
            return 3;
        }else if("4".equals(str)){
            return 2;
        }else if("5".equals(str)){
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * 保存中医体质辨识结果
     * @param num
     * @param drId
     * @param id
     * @param userId
     * @param type
     * @return
     */
    public AppTcmEntity tcmResult(Integer num,String drId,String id,String userId,String type,String tcmType){
        AppTcmResult tcm = new AppTcmResult();
        tcm.setTcmrId(id);
        tcm.setTcmrDrId(drId);
        AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,userId);
        if(user!=null){
            tcm.setTcmrUserId(user.getId());
            tcm.setTcmrUserName(user.getPatientName());
        }
        if("1".equals(tcmType)){
            tcm.setTcmrDrName(user.getPatientName());
        }else{
            AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,drId);
            if(drUser!=null){
                tcm.setTcmrDrName(drUser.getDrName());
            }
        }
        tcm.setTcmrScore(String.valueOf(num));
        if(!CommTcmTz.PHZ.getValue().equals(type)){
            if(num>=11){
                tcm.setTcmrResultValue(CommTcmBs.SHI.getValue());
            }else if (num>=9 && num <=10){
                tcm.setTcmrResultValue(CommTcmBs.QXS.getValue());
            }else {
                tcm.setTcmrResultValue(CommTcmBs.FOU.getValue());
            }
        }
        if(CommTcmTz.QXZ.getValue().equals(type)){
            tcm.setTcmrResultType(CommTcmTz.QXZ.getValue());
        }else if(CommTcmTz.YANGXZ.getValue().equals(type)){
            tcm.setTcmrResultType(CommTcmTz.YANGXZ.getValue());
        }else if(CommTcmTz.YINXZ.getValue().equals(type)){
            tcm.setTcmrResultType(CommTcmTz.YINXZ.getValue());
        }else if(CommTcmTz.TSZ.getValue().equals(type)){
            tcm.setTcmrResultType(CommTcmTz.TSZ.getValue());
        }else if(CommTcmTz.SRZ.getValue().equals(type)){
            tcm.setTcmrResultType(CommTcmTz.SRZ.getValue());
        }else if(CommTcmTz.XYZ.getValue().equals(type)){
            tcm.setTcmrResultType(CommTcmTz.XYZ.getValue());
        }else if(CommTcmTz.QYZ.getValue().equals(type)){
            tcm.setTcmrResultType(CommTcmTz.QYZ.getValue());
        }else if(CommTcmTz.TBZ.getValue().equals(type)){
            tcm.setTcmrResultType(CommTcmTz.TBZ.getValue());
        }
        tcm.setTcmrModernCultivate(CommTcmGuide.QZTS.getValue());//情志调摄指导
        tcm.setTcmrFoodNursing(CommTcmGuide.YSTY.getValue());//饮食调养指导
        tcm.setTcmrDailyLifeCultivate(CommTcmGuide.QJTS.getValue());//起居调摄指导
        tcm.setTcmrSportsHealthCare(CommTcmGuide.YDBJ.getValue());//运动保健指导
        tcm.setTcmrMeridianHealth(CommTcmGuide.XWBJ.getValue());//穴位保健指导
        tcm.setTcmrCreateTime(Calendar.getInstance());
        sysDao.getServiceDo().add(tcm);
        AppTcmEntity table = new AppTcmEntity();
        table.setId(tcm.getId());
        table.setJlId(id);
        table.setTzType(type);
        table.setDf(String.valueOf(num));
        table.setTzbs(tcm.getTcmrResultValue());
        table.setQzts(tcm.getTcmrModernCultivate());
        table.setYsty(tcm.getTcmrFoodNursing());
        table.setQjts(tcm.getTcmrDailyLifeCultivate());
        table.setYdbj(tcm.getTcmrSportsHealthCare());
        table.setXwbj(tcm.getTcmrMeridianHealth());

        //个人端自测添加系统指导
        if("1".equals(tcmType)){
            if(CommTcmBs.SHI.getValue().equals(tcm.getTcmrResultValue())||
            CommTcmBs.QXS.getValue().equals(tcm.getTcmrResultValue())){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("tcmType",tcm.getTcmrResultType());
                map.put("guideType","3");//系统
                String sql = "select a.* from APP_TCM_GUIDE a where 1=1 ";
                sql += "and a.TCMG_CORPOREITY_TYPE =:tcmType";
                sql += " and a.TCMG_TYPE=:guideType";
                List<AppTcmGuide> guides = sysDao.getServiceDo().findSqlMap(sql,map,AppTcmGuide.class);
                if(guides!=null&&guides.size()>0){
                    AppTcmGuide guide = guides.get(0);
                    AppUserTcmGuide userTcmGuide = new AppUserTcmGuide();
                    userTcmGuide.setUtgCorporeityType(tcm.getTcmrResultType());
                    userTcmGuide.setUtgTcmId(tcm.getTcmrId());
                    userTcmGuide.setUtgUserId(tcm.getTcmrUserId());
                    userTcmGuide.setUtgCreateId(tcm.getTcmrUserId());
                    userTcmGuide.setUtgCreateTime(Calendar.getInstance());
                    userTcmGuide.setUtgMeridianHealth(guide.getTcmgMeridianHealth());
                    userTcmGuide.setUtgSportsHealth(guide.getTcmgSportsHealth());
                    userTcmGuide.setUtgDailyLifeCultivate(guide.getTcmgDailyLifeCultivate());
                    userTcmGuide.setUtgDietAftercare(guide.getTcmgDietAftercare());
                    userTcmGuide.setUtgModernCultivate(guide.getTcmgModernCultivate());
//                userTcmGuide.setUtgType("3");
                    sysDao.getServiceDo().add(userTcmGuide);
                }
            }

        }

        return table;
    }

    @Override
    public List<AppTcmRecordEntity> findListByState() throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("upState","0");
        String sql = "SELECT ID jlId," +
                "TCM_USER_ID patientId," +
                "TCM_DR_ID doctor," +
                "TCM_CREATE_TIME edate," +
                "TCM_CHOOSE_NUM dtjg," +
                "'' df_id," +
                "'' tzjl " +
                " FROM APP_TCM_SYNDROME  WHERE UPLOAD_STATE =:upState AND TCM_TYPE = '2'";
        List<AppTcmRecordEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTcmRecordEntity.class);
        return list;
    }

    @Override
    public AppTcmResult findResult(String jlId, String type) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("jlId",jlId);
        map.put("type",type);
        String sql = "SELECT * FROM  APP_TCM_RESULT WHERE TCMR_ID =:jlId AND TCMR_RESULT_TYPE =:type";
        List<AppTcmResult> list = sysDao.getServiceDo().findSqlMap(sql,map,AppTcmResult.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 保存基卫中医体质辨识
     * @param qvo
     * @return
     */
    @Override
    public String savaBasicTcm(AppBasicTcmAllQvo qvo) throws Exception {
        if(StringUtils.isBlank(qvo.getCityCode())){
            throw new Exception("城市编码不能为空");
        }
        if(StringUtils.isBlank(qvo.getDrId())){
            throw new Exception("医生主键不能为空!");
        }

        if(StringUtils.isBlank(qvo.getUserIdno())){
            throw new Exception("居民身份证号不能为空!");
        }
        AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(qvo.getUserIdno());
        if(user == null){
            throw new Exception("该居民未注册");
        }
        AppSignForm form = sysDao.getAppSignformDao().findSignFormByUser(user.getId());
        if(form == null){
            throw new Exception("该居民未签约");
        }
        if(qvo.getTcmJl() == null){
            throw new Exception("中医体质辨识记录数据不能为空");
        }
        if(qvo.getGuideVo() == null){
            throw new Exception("中医体质辨识指导数据不能为空");
        }
        if(qvo.getTcmResult() == null){
            throw new Exception("中医体质辨识接口数据不能为空");
        }
        String result = "";
        if(AddressType.FZS.getValue().equals(qvo.getCityCode())){

        }else if(AddressType.XMS.getValue().equals(qvo.getCityCode())){
            result = "xm_";
        }else if(AddressType.PTS.getValue().equals(qvo.getCityCode())){
            result = "pt_";
        }else if(AddressType.SMS.getValue().equals(qvo.getCityCode())){
            result = "sm_";
        }else if(AddressType.QZS.getValue().equals(qvo.getCityCode())){
            result = "qz_";
        }else if(AddressType.ZZS.getValue().equals(qvo.getCityCode())){
            result = "zz_";
        }else if(AddressType.NPS.getValue().equals(qvo.getCityCode())){
            result = "np_";
        }else if(AddressType.LYS.getValue().equals(qvo.getCityCode())){
            result = "ly_";
        }else if(AddressType.NDS.getValue().equals(qvo.getCityCode())){
            result = "nd_";
        }else if(AddressType.PTZHSYQ.getValue().equals(qvo.getCityCode())){
            result = "pg_";
        }

        if(qvo.getDrId().length()<=33){
            qvo.setDrId(result+qvo.getDrId());
        }
        AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
        if(drUser == null){
            throw new Exception("查无医生信息");
        }
        AppTcmSyndrome table = new AppTcmSyndrome();
        table.setTcmCreateTime(ExtendDate.getCalendar(qvo.getCreateTime()));
        table.setTcmUserId(user.getId());
        table.setTcmUserName(user.getPatientName());
        table.setTcmDrId(drUser.getId());
        table.setTcmDrName(drUser.getDrName());
        table.setTcmType("2");
        table.setUploadState("2");
        if(qvo.getTcmJl() != null){
            if(StringUtils.isBlank(qvo.getTcmJl().getTcmQuestionValue())){
                table.setTcmQuestionValue("1;2;3;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;23;24;25;26;27;28;29;30;31;32;33");
            }else{
                table.setTcmQuestionValue(qvo.getTcmJl().getTcmQuestionValue());
            }
            String chooseNum = "";
            if(StringUtils.isNotBlank(qvo.getTcmJl().getTcmChooseNum())){
                String[] nums = qvo.getTcmJl().getTcmChooseNum().split(";");
                for(String num:nums){
                    String stt = "";
                    if("1".equals(num)){
                        stt = "A";
                    }else if("2".equals(num)){
                        stt = "B";
                    }else if("3".equals(num)){
                        stt = "C";
                    }else if("4".equals(num)){
                        stt = "D";
                    }else if("5".equals(num)){
                        stt = "E";
                    }
                    if(StringUtils.isBlank(chooseNum)){
                        chooseNum = stt;
                    }else {
                        chooseNum +=";"+stt;
                    }
                }
            }
            table.setTcmChooseNum(chooseNum);
            table.setTcmScode(qvo.getTcmJl().getTcmChooseNum());
        }
        sysDao.getServiceDo().add(table);//保存中医体质辨识主表数据
        if(qvo.getTcmResult()!=null){
            AppBasicTcmResultQvo resultQvo = new AppBasicTcmResultQvo();
            Integer qxzDf = Integer.parseInt(resultQvo.getQxzscore()) ;
            Integer yangxzDf = Integer.parseInt(resultQvo.getYx0score());
            Integer yinxzDf = Integer.parseInt(resultQvo.getYxzscore());
            Integer tszDf = Integer.parseInt(resultQvo.getTszscore());
            Integer srzDf = Integer.parseInt(resultQvo.getSrzscore());
            Integer xyzDf = Integer.parseInt(resultQvo.getXyzscore());
            Integer qyzDf = Integer.parseInt(resultQvo.getQyzscore());
            Integer tbzDf = Integer.parseInt(resultQvo.getTbzscore());
            Integer phzDf = Integer.parseInt(resultQvo.getPhzscore());

            tcmResult(qxzDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.QXZ.getValue(),table.getTcmType());
            tcmResult(yangxzDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.YANGXZ.getValue(),table.getTcmType());
            tcmResult(yinxzDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.YINXZ.getValue(),table.getTcmType());
            tcmResult(tszDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.TSZ.getValue(),table.getTcmType());
            tcmResult(srzDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.SRZ.getValue(),table.getTcmType());
            tcmResult(xyzDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.XYZ.getValue(),table.getTcmType());
            tcmResult(qyzDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.QYZ.getValue(),table.getTcmType());
            tcmResult(tbzDf,table.getTcmDrId(),table.getId(),table.getTcmUserId(),CommTcmTz.TBZ.getValue(),table.getTcmType());
            //平和质
            AppTcmResult tt = new AppTcmResult();
            tt.setTcmrScore(String.valueOf(phzDf));
            tt.setTcmrUserId(table.getTcmUserId());
            tt.setTcmrUserName(table.getTcmUserName());
            tt.setTcmrDrId(table.getTcmDrId());
            tt.setTcmrDrName(table.getTcmDrName());
            tt.setTcmrResultType(CommTcmTz.PHZ.getValue());
            tt.setTcmrId(table.getId());
            if(phzDf>=17&&qxzDf<=8&&yangxzDf<=8&&yinxzDf<=8&&tszDf<=8&&srzDf<=8&&xyzDf<=8&&qyzDf<=8&&tbzDf<=8){
                tt.setTcmrResultValue(CommTcmBs.SHI.getValue());
            }else if(phzDf>=17&&qxzDf<=10&&yangxzDf<=10&&yinxzDf<=10&&tszDf<=10&&srzDf<=10&&xyzDf<=10&&qyzDf<=10&&tbzDf<=10){
                tt.setTcmrResultValue(CommTcmBs.QXS.getValue());
            }else{
                tt.setTcmrResultValue(CommTcmBs.FOU.getValue());
            }
            tt.setTcmrModernCultivate(CommTcmGuide.QZTS.getValue());//情志调摄指导
            tt.setTcmrFoodNursing(CommTcmGuide.YSTY.getValue());//饮食调养指导
            tt.setTcmrDailyLifeCultivate(CommTcmGuide.QJTS.getValue());//起居调摄指导
            tt.setTcmrSportsHealthCare(CommTcmGuide.YDBJ.getValue());//运动保健指导
            tt.setTcmrMeridianHealth(CommTcmGuide.XWBJ.getValue());//穴位保健指导
            tt.setTcmrCreateTime(Calendar.getInstance());
            sysDao.getServiceDo().add(tt);
        }
        if(qvo.getGuideVo() != null){
            AppBasicTcmGuideQvo guideQvo = qvo.getGuideVo();
            AppUserTcmGuide guide = new AppUserTcmGuide();
            guide.setUtgCreateId(drUser.getId());
            guide.setUtgCorporeityType(guideQvo.getTzlx00());
            guide.setUtgModernCultivate(guideQvo.getQzts00());
            guide.setUtgDietAftercare(guideQvo.getYsty00());
            guide.setUtgDailyLifeCultivate(guideQvo.getQjts00());
            guide.setUtgSportsHealth(guideQvo.getYdbj00());
            guide.setUtgMeridianHealth(guideQvo.getXwbj00());
            guide.setUtgTcmId(table.getId());
            guide.setUtgUserId(user.getId());
            guide.setUtgCreateTime(ExtendDate.getCalendar(guideQvo.getCreateTime()));
            guide.setUtgOther(guideQvo.getOther());
            sysDao.getServiceDo().add(guide);
        }
        //履约数据
        PerformanceDataQvo qqvo = new PerformanceDataQvo();
        qqvo.setPerName(user.getPatientName());
        qqvo.setPerIdno(user.getPatientIdno());
        qqvo.setPerType(PerformanceType.ZYTZBS.getValue());
        qqvo.setPerForeign(table.getId());
        qqvo.setPerSource("2");
        if(StringUtils.isNotBlank(user.getPatientCity())){
            CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
            if(p != null){
                String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                if(value != null) {
                    qqvo.setPerArea(value.getCodeTitle());
                }
            }
        }
        qqvo.setDrName(drUser.getDrName());
        qqvo.setDrAccount(drUser.getDrAccount());
        qqvo.setDrPwd(drUser.getDrPwd());
        qqvo.setDrGender(drUser.getDrGender());
        qqvo.setDrTel(drUser.getDrTel());
        qqvo.setDrId(drUser.getId());
        AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
        if(dept!=null){
            qqvo.setHospId(dept.getId());
            qqvo.setHospName(dept.getHospName());
            qqvo.setHospAreaCode(dept.getHospAreaCode());
            qqvo.setHospAddress(dept.getHospAddress());
            qqvo.setHospTel(dept.getHospTel());
        }
        if(StringUtils.isNotBlank(qqvo.getPerArea())){
            if(StringUtils.isNotBlank(qqvo.getPerType())){
                String fwType = "";
                String sermeal = "";//服务包id
                fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                sermeal = form.getSignpackageid();
                sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
            }
        }
        return "true";
    }

}
