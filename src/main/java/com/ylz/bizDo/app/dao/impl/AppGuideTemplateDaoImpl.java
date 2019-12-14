package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppGuideTemplateDao;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppGuideTemplate;
import com.ylz.bizDo.jtapp.commonEntity.AppTcmGuideEntity;
import com.ylz.bizDo.jtapp.commonVo.AppTcmGuideQQvo;
import com.ylz.bizDo.jtapp.commonVo.AppTcmGuideQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrMeddleEntity;
import com.ylz.bizDo.jtapp.drEntity.AppGuideModelEntity;
import com.ylz.bizDo.jtapp.drVo.AppGuideTemplateQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonGuideType;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import com.ylz.packcommon.common.comEnum.CommonTcmType;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zzl on 2017/6/20.
 */
@Service("appGuideTemplateDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppGuideTemplateDaoImpl implements AppGuideTemplateDao {
    @Autowired
    private SysDao sysDao;
    @Override
    public List<AppGuideModelEntity> findByQvo(AppGuideTemplateQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " select a.ID id,a.GUIDE_TITLE title,a.GUIDE_CONTENT content,date_format(GUIDE_CREATE_TIME, '%Y-%c-%d %H:%i:%s'" +
                ") time,a.GUIDE_TYPE guideType,a.GUIDE_DISEASE_TYPE diseaseType,a.GUIDE_MEDDLE_TYPE meddleType,a.GUIDE_IMAGE_URL imageUrl FROM APP_GUIDE_TEMPLATE a WHERE 1=1";
        if(StringUtils.isNotBlank(qvo.getType())){
            map.put("type",qvo.getType());
            sql += " AND a.GUIDE_TYPE =:type";
        }
        if(StringUtils.isNotBlank(qvo.getDiseaseType())){
            map.put("disType",qvo.getDiseaseType());
            sql += " AND a.GUIDE_DISEASE_TYPE =:disType";
        }
        if(StringUtils.isNotBlank(qvo.getMeddleType())){
            map.put("meddleType",qvo.getMeddleType());
            sql += " AND a.GUIDE_MEDDLE_TYPE = :meddleType";
        }
        if(StringUtils.isNotBlank(qvo.getUserId())){
            map.put("userId",qvo.getUserId());
            sql += " AND a.GUIDE_CREATE_ID =:userId";
        }
        sql += " ORDER BY a.GUIDE_CREATE_TIME DESC ";
        List<AppGuideModelEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppGuideModelEntity.class,qvo);
        return ls;
    }

    /**
     * 公共添加健康指导模板
     * @param qvo
     * @throws Exception
     */
    @Override
    public void addHealthGuide(AppGuideTemplateQvo qvo) throws Exception {
            AppGuideTemplate gt = new AppGuideTemplate();
            gt.setGuideContent(qvo.getContent());
            gt.setGuideTitle(qvo.getTitle());
            gt.setGuideType(CommonGuideType.JKZD.getValue());
            gt.setGuideDiseaseType(qvo.getDiseaseType());
            gt.setGuideMeddleType(qvo.getMeddleType());
//            gt.setGuideHospId(qvo.getHospId());
//            if(StringUtils.isNotBlank(qvo.getUserId())){
//                AppDrUser user = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getUserId());
//                if(user!=null){//公共健康指导模板保存医生医院id
//                    gt.setGuideCreateId(user.getDrHospId());
//                }
//            }
            gt.setGuideHospId(qvo.getHospId());
            gt.setGuideCreateId(qvo.getUserId());
            if(StringUtils.isNotBlank(qvo.getImageUrl())){
                if(StringUtils.isBlank(qvo.getImageName())){
                    qvo.setImageName(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                }
//                String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureYz"),qvo.getImageName());
//                FileUtils.decoderBase64File(qvo.getImageUrl(),PropertiesUtil.getConfValue("filePicture")+path);
//                gt.setGuideImageUrl(path);

                Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getImageUrl(), CommonShortType.YISHENG.getValue());
                gt.setGuideImageUrl(map.get("objectUrl").toString());
//                    user.setPatientImageName(map.get("objectName").toString());
            }
            Calendar cal = Calendar.getInstance();
            gt.setGuideCreateTime(cal);
            this.sysDao.getServiceDo().add(gt);
    }

    /**
     * 修改健康指导模板
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppDrMeddleEntity modifyGuide(AppGuideTemplateQvo qvo) throws Exception {
        AppGuideTemplate gt =(AppGuideTemplate) sysDao.getServiceDo().find(AppGuideTemplate.class,qvo.getId());
        if(gt!=null){
            AppDrUser user = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getUserId());
            if(user!=null){
                //修改健康指导模板时如果是自己或本医院的模板做修改操作否则做新增操作
                if(gt.getGuideCreateId().equals(user.getId())||gt.getGuideCreateId().equals(user.getDrHospId())){
                    if(StringUtils.isNotBlank(qvo.getMeddleType())){
                        gt.setGuideMeddleType(qvo.getMeddleType());
                    }
                    if(StringUtils.isNotBlank(qvo.getDiseaseType())){
                        gt.setGuideDiseaseType(qvo.getDiseaseType());
                    }
                    if(StringUtils.isNotBlank(qvo.getImageUrl())){
                        if(StringUtils.isBlank(qvo.getImageName())){
                            qvo.setImageName(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                        }
//                        String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureYz"),qvo.getImageName());
//                        FileUtils.decoderBase64File(qvo.getImageUrl(),PropertiesUtil.getConfValue("filePicture")+path);
//                        gt.setGuideImageUrl(path);

                        Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getImageUrl(), CommonShortType.YISHENG.getValue());
                        gt.setGuideImageUrl(map.get("objectUrl").toString());
//                    user.setPatientImageName(map.get("objectName").toString());
                    }
                    if(StringUtils.isNotBlank(qvo.getTitle())){
                        gt.setGuideTitle(qvo.getTitle());
                    }
                    if(StringUtils.isNotBlank(qvo.getContent())){
                        gt.setGuideContent(qvo.getContent());
                    }
                    sysDao.getServiceDo().modify(gt);
                    AppDrMeddleEntity table = new AppDrMeddleEntity();
                    table.setContent(gt.getGuideContent());
                    table.setDisType(gt.getGuideDiseaseType());
                    table.setDrId(gt.getGuideCreateId());
                    table.setDrName(gt.getDrName());
                    table.setId(gt.getId());
                    table.setIllType(gt.getGuideDiseaseType());
                    table.setImageUrl(gt.getGuideImageUrl());
                    table.setMeddleType(gt.getGuideMeddleType());
                    table.setMedType(gt.getGuideMeddleType());
                    table.setTime(ExtendDate.getSqlTimestamp(ExtendDate.getYMD_h_m_s(gt.getGuideCreateTime())));
                    table.setTitle(gt.getGuideTitle());
                    return table;
                }else{
                    AppGuideTemplate tt = new AppGuideTemplate();
                    if(StringUtils.isNotBlank(qvo.getMeddleType())){
                        tt.setGuideMeddleType(qvo.getMeddleType());
                    }else{
                        tt.setGuideMeddleType(gt.getGuideMeddleType());
                    }
                    if(StringUtils.isNotBlank(qvo.getDiseaseType())){
                        tt.setGuideDiseaseType(qvo.getDiseaseType());
                    }else{
                        tt.setGuideDiseaseType(gt.getGuideDiseaseType());
                    }
                    if(StringUtils.isNotBlank(qvo.getImageUrl())){
                        if(StringUtils.isBlank(qvo.getImageName())){
                            qvo.setImageName(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                        }
//                        String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureYz"),qvo.getImageName());
//                        FileUtils.decoderBase64File(qvo.getImageUrl(),PropertiesUtil.getConfValue("filePicture")+path);
//                        tt.setGuideImageUrl(path);

                        Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getImageUrl(), CommonShortType.YISHENG.getValue());
                        gt.setGuideImageUrl(map.get("objectUrl").toString());
//                    user.setPatientImageName(map.get("objectName").toString());
                    }else{
                        tt.setGuideImageUrl(gt.getGuideImageUrl());
                    }
                    if(StringUtils.isNotBlank(qvo.getTitle())){
                        tt.setGuideTitle(qvo.getTitle());
                    }else{
                        tt.setGuideTitle(gt.getGuideTitle());
                    }
                    if(StringUtils.isNotBlank(qvo.getContent())){
                        tt.setGuideContent(qvo.getContent());
                    }else{
                        tt.setGuideContent(gt.getGuideContent());
                    }
                    tt.setGuideType(CommonGuideType.JKZD.getValue());
                    tt.setGuideCreateId(qvo.getUserId());
                    tt.setGuideCreateTime(Calendar.getInstance());
                    sysDao.getServiceDo().add(tt);
                    AppDrMeddleEntity table = new AppDrMeddleEntity();
                    table.setContent(tt.getGuideContent());
                    table.setDisType(tt.getGuideDiseaseType());
                    table.setDrId(tt.getGuideCreateId());
                    table.setDrName(tt.getDrName());
                    table.setId(tt.getId());
                    table.setIllType(tt.getGuideDiseaseType());
                    table.setImageUrl(tt.getGuideImageUrl());
                    table.setMeddleType(tt.getGuideMeddleType());
                    table.setMedType(tt.getGuideMeddleType());
                    table.setTime(ExtendDate.getSqlTimestamp(ExtendDate.getYMD_h_m_s(gt.getGuideCreateTime())));
                    table.setTitle(tt.getGuideTitle());
                    return table;

                }
            }
        }
        return null;
    }

    /**
     * 添加中医药体质辨识保健指导
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppTcmGuideEntity addTcmGuide(AppTcmGuideQvo qvo) throws Exception {
        AppGuideTemplate table = new AppGuideTemplate();
        table.setGuideContent(qvo.getContent());
        table.setGuideTitle(qvo.getTitle());
        if("1".equals(qvo.getRoleType())){//添加医院模板
            table.setGuideCreateId(qvo.getHospId());
        }else if ("2".equals(qvo.getRoleType())){//添加医生模板
            table.setGuideCreateId(qvo.getDrId());
        }
        if(StringUtils.isNotBlank(qvo.getImageUrl())){
//            String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureYz"),qvo.getImageName());
//            FileUtils.decoderBase64File(qvo.getImageUrl(),PropertiesUtil.getConfValue("filePicture")+path);
//            table.setGuideImageUrl(path);

            Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getImageUrl(), CommonShortType.YISHENG.getValue());
            table.setGuideImageUrl(map.get("objectUrl").toString());
//                    user.setPatientImageName(map.get("objectName").toString());
        }
        table.setGuideDiseaseType(qvo.getTzType());
        table.setGuideCreateTime(Calendar.getInstance());
        table.setGuideType(CommonGuideType.ZYYZD.getValue());
        table.setGuideMeddleType(qvo.getGuideType());
        table.setGuideCreateTime(Calendar.getInstance());
        sysDao.getServiceDo().add(table);
        AppTcmGuideEntity tt = new AppTcmGuideEntity();
        tt.setBjzdlx(table.getGuideMeddleType());
        tt.setContent(table.getGuideContent());
        tt.setCreateId(table.getGuideCreateId());
        tt.setId(table.getId());
        tt.setImageUrl(table.getGuideImageUrl());
        tt.setCreateTime(ExtendDate.getYMD_h_m(table.getGuideCreateTime()));
        tt.setTitle(table.getGuideTitle());
        tt.setTzlx(table.getGuideDiseaseType());
        return tt;
    }

    /**
     * 查询中医药体质辨识保健指导
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppTcmGuideEntity> findZyyGuide(AppTcmGuideQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("type",CommonGuideType.ZYYZD.getValue());
        String sql = "SELECT " +
                "ID id," +
                "GUIDE_TITLE title," +
                "GUIDE_CONTENT content," +
                "GUIDE_IMAGE_URL imageUrl," +
                "GUIDE_CREATE_ID createId," +
                "date_format(GUIDE_CREATE_TIME, '%Y-%c-%d %H:%i:%s') createTime," +
                "GUIDE_MEDDLE_TYPE bjzdlx," +
                "GUIDE_DISEASE_TYPE tzlx " +
                " FROM APP_GUIDE_TEMPLATE WHERE GUIDE_TYPE = :type";
        if("1".equals(qvo.getType())){//查医生
            map.put("createId",qvo.getDrId());
            sql += " AND GUIDE_CREATE_ID =:createId";
        }else if("2".equals(qvo.getType())){//查医院
            AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
            map.put("createId",drUser.getDrHospId());
            sql += " AND GUIDE_CREATE_ID =:createId";
        }else if("3".equals(qvo.getType())){//查系统
            map.put("createId","0");
            sql += " AND GUIDE_CREATE_ID =:createId";
        }

        String strr = "0";
        if(StringUtils.isNotBlank(qvo.getDrId())){
            //医生、医院、系统
            AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
            strr += ";"+drUser.getId();
            if(drUser!=null){
                strr += ";"+drUser.getDrHospId();
            }
//            map.put("drId",qvo.getDrId());
//            sql += " AND GUIDE_CREATE_ID =:drId";
        }
        if(StringUtils.isNotBlank(strr)){
            map.put("createId",strr.split(";"));
            sql += " AND GUIDE_CREATE_ID IN :createId";
        }

        if(StringUtils.isNotBlank(qvo.getTzType())){
            map.put("tzType",qvo.getTzType().split(";"));
            sql += " AND GUIDE_DISEASE_TYPE IN :tzType";
        }
        if(StringUtils.isNotBlank(qvo.getGuideType())){
            map.put("guideType",qvo.getGuideType().split(";"));
            sql += " AND GUIDE_MEDDLE_TYPE IN :guideType";
        }
        List<AppTcmGuideEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTcmGuideEntity.class,qvo);
        return ls;
    }

    /**
     * 修改中医药保健指导模板
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppTcmGuideEntity modifyTcmGuide(AppTcmGuideQvo qvo) throws Exception {
        AppGuideTemplate gt = (AppGuideTemplate)sysDao.getServiceDo().find(AppGuideTemplate.class,qvo.getId());
        if(gt!=null){
            AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
            if(drUser!=null){
                if(drUser.getId().equals(gt.getGuideCreateId())){
                    if(StringUtils.isNotBlank(qvo.getImageUrl())){
//                        String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureYz"),qvo.getImageName());
//                        FileUtils.decoderBase64File(qvo.getImageUrl(),PropertiesUtil.getConfValue("filePicture")+path);
//                        gt.setGuideImageUrl(path);
                        Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getImageUrl(), CommonShortType.YISHENG.getValue());
                        gt.setGuideImageUrl(map.get("objectUrl").toString());
//                    user.setPatientImageName(map.get("objectName").toString());
                    }
                    if(StringUtils.isNotBlank(qvo.getTzType())){
                        gt.setGuideDiseaseType(qvo.getTzType());
                    }
                    if(StringUtils.isNotBlank(qvo.getGuideType())){
                        gt.setGuideMeddleType(qvo.getGuideType());
                    }
                    if(StringUtils.isNotBlank(qvo.getTitle())){
                        gt.setGuideTitle(qvo.getTitle());
                    }
                    if(StringUtils.isNotBlank(qvo.getContent())){
                        gt.setGuideContent(qvo.getContent());
                    }
                    sysDao.getServiceDo().modify(gt);
                    AppTcmGuideEntity table = new AppTcmGuideEntity();
                    table.setTzlx(gt.getGuideDiseaseType());
                    table.setTitle(gt.getGuideTitle());
                    table.setCreateTime(ExtendDate.getYMD_h_m_s(gt.getGuideCreateTime()));
                    table.setImageUrl(gt.getGuideImageUrl());
                    table.setId(gt.getId());
                    table.setCreateId(gt.getGuideCreateId());
                    table.setContent(gt.getGuideContent());
                    table.setBjzdlx(gt.getGuideMeddleType());
                    return table;
                }else{
                    AppGuideTemplate tt = new AppGuideTemplate();
                    if(StringUtils.isNotBlank(qvo.getImageUrl())){
//                        String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureYz"),qvo.getImageName());
//                        FileUtils.decoderBase64File(qvo.getImageUrl(),PropertiesUtil.getConfValue("filePicture")+path);
//                        tt.setGuideImageUrl(path);

                        Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getImageUrl(), CommonShortType.YISHENG.getValue());
                        gt.setGuideImageUrl(map.get("objectUrl").toString());
//                    user.setPatientImageName(map.get("objectName").toString());
                    }else{
                        tt.setGuideImageUrl(gt.getGuideImageUrl());
                    }
                    if(StringUtils.isNotBlank(qvo.getTzType())){
                        tt.setGuideDiseaseType(qvo.getTzType());
                    }else{
                        tt.setGuideDiseaseType(gt.getGuideDiseaseType());
                    }
                    if(StringUtils.isNotBlank(qvo.getGuideType())){
                        tt.setGuideMeddleType(qvo.getGuideType());
                    }else{
                        tt.setGuideMeddleType(gt.getGuideMeddleType());
                    }
                    if(StringUtils.isNotBlank(qvo.getTitle())){
                        tt.setGuideTitle(qvo.getTitle());
                    }else{
                        tt.setGuideTitle(gt.getGuideTitle());
                    }
                    if(StringUtils.isNotBlank(qvo.getContent())){
                        tt.setGuideContent(qvo.getContent());
                    }else{
                        tt.setGuideContent(gt.getGuideContent());
                    }
                    tt.setGuideType(CommonGuideType.ZYYZD.getValue());
                    tt.setGuideCreateId(drUser.getId());
                    tt.setGuideCreateTime(Calendar.getInstance());
                    sysDao.getServiceDo().add(tt);
                    AppTcmGuideEntity table = new AppTcmGuideEntity();
                    table.setTzlx(tt.getGuideDiseaseType());
                    table.setTitle(tt.getGuideTitle());
                    table.setCreateTime(ExtendDate.getYMD_h_m_s(tt.getGuideCreateTime()));
                    table.setImageUrl(tt.getGuideImageUrl());
                    table.setId(tt.getId());
                    table.setCreateId(tt.getGuideCreateId());
                    table.setContent(tt.getGuideContent());
                    table.setBjzdlx(tt.getGuideMeddleType());
                    return table;
                }
            }
        }
        return null;
    }

    /**
     * 添加或修改中医药保健指导
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppTcmGuideEntity> addTcmGuide1(AppTcmGuideQvo qvo) throws Exception {
        List<AppTcmGuideEntity> list = new ArrayList<AppTcmGuideEntity>();
        if(qvo.getGuideList()!=null&&qvo.getGuideList().size()>0){
            List<AppGuideTemplate> lis = new ArrayList<AppGuideTemplate>();
            String userId = "";
            if(CommonTcmType.YS.getValue().equals(qvo.getRoleType())){//查询此医生是否有中医药保健指导模板
                userId = qvo.getDrId();
            }else if(CommonTcmType.YY.getValue().equals(qvo.getRoleType())){//查询此医院是否有中医药保健指导模板
                userId = qvo.getHospId();
            }else{//系统模板暂不提供App添加
                return null;
            }
            lis = sysDao.getServiceDo().loadByPk(AppGuideTemplate.class,"guideCreateId",userId);
            if(lis!=null&&lis.size()>0){//有 --》做修改操作
                for(AppTcmGuideQQvo q:qvo.getGuideList()){
                    boolean flag = true;
                    for(AppGuideTemplate s:lis){
                        if(s.getGuideDiseaseType().equals(q.getTzType())&&s.getGuideMeddleType().equals(q.getGuideType())){
                            if(StringUtils.isNotBlank(q.getContent())){
                                s.setGuideContent(q.getContent());
                            }
                            if(StringUtils.isNotBlank(q.getTitle())){
                                s.setGuideTitle(q.getTitle());
                            }
                            sysDao.getServiceDo().modify(s);
                            flag = false;
                            AppTcmGuideEntity tg = new AppTcmGuideEntity();
                            tg.setBjzdlx(s.getGuideMeddleType());
                            tg.setContent(s.getGuideContent());
                            tg.setCreateId(s.getGuideCreateId());
                            tg.setId(s.getId());
                            tg.setCreateTime(ExtendDate.getYMD(s.getGuideCreateTime()));
                            tg.setTitle(s.getGuideTitle());
                            tg.setTzlx(s.getGuideDiseaseType());
                            list.add(tg);
                        }
                    }
                    if(flag){
                        AppGuideTemplate gt = new AppGuideTemplate();
                        gt.setGuideTitle(q.getTitle());
                        gt.setGuideContent(q.getContent());
                        gt.setGuideDiseaseType(q.getTzType());
                        gt.setGuideMeddleType(q.getGuideType());
                        gt.setGuideType(CommonGuideType.ZYYZD.getValue());
                        gt.setGuideCreateId(userId);
                        gt.setGuideCreateTime(Calendar.getInstance());
                        sysDao.getServiceDo().add(gt);
                        AppTcmGuideEntity tg = new AppTcmGuideEntity();
                        tg.setBjzdlx(gt.getGuideMeddleType());
                        tg.setContent(gt.getGuideContent());
                        tg.setCreateId(gt.getGuideCreateId());
                        tg.setId(gt.getId());
                        tg.setCreateTime(ExtendDate.getYMD(gt.getGuideCreateTime()));
                        tg.setTitle(gt.getGuideTitle());
                        tg.setTzlx(gt.getGuideDiseaseType());
                        list.add(tg);
                    }
                }
            }else{//无--》做保存操作
                for(AppTcmGuideQQvo q:qvo.getGuideList()){
                    AppGuideTemplate gt = new AppGuideTemplate();
                    gt.setGuideTitle(q.getTitle());
                    gt.setGuideContent(q.getContent());
                    gt.setGuideDiseaseType(q.getTzType());
                    gt.setGuideMeddleType(q.getGuideType());
                    gt.setGuideType(CommonGuideType.ZYYZD.getValue());
                    gt.setGuideCreateId(userId);
                    gt.setGuideCreateTime(Calendar.getInstance());
                    sysDao.getServiceDo().add(gt);
                    AppTcmGuideEntity tg = new AppTcmGuideEntity();
                    tg.setBjzdlx(gt.getGuideMeddleType());
                    tg.setContent(gt.getGuideContent());
                    tg.setCreateId(gt.getGuideCreateId());
                    tg.setId(gt.getId());
                    tg.setCreateTime(ExtendDate.getYMD(gt.getGuideCreateTime()));
                    tg.setTitle(gt.getGuideTitle());
                    tg.setTzlx(gt.getGuideDiseaseType());
                    list.add(tg);
                }
            }
        }
        return list;
    }
}
