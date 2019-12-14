package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppSeekHelpDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.SeekHelpVo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamMemberEntity;
import com.ylz.bizDo.smjq.smEntity.AppBloodSugarTwoEntity;
import com.ylz.bizDo.smjq.smEntity.AppSmHbpTwoEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.DrPatientType;
import com.ylz.packcommon.common.comEnum.NoticesType;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 *求助
 */
@Service("appSeekHelpDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSeekHelpDaoImpl implements AppSeekHelpDao{
    @Autowired
    private SysDao sysDao;

    /**
     * 根据imei给医生发送求助信息
     * @param imei
     */
    @Override
    public void sendHelpMsg(String imei) throws Exception{
        List<AppBloodpressure> list = sysDao.getServiceDo().loadByPk(AppBloodpressure.class,"bpDevId",imei);
        if(!list.isEmpty()){
            if(list.get(0).getBpUserOne()!=null){
                String patientId = list.get(0).getBpUserOne();
                if(StringUtils.isNotBlank(patientId)){
                    sendMsg(patientId,imei);
                }
            }else if(list.get(0).getBpUserTwo() !=null){
                String patientId = list.get(0).getBpUserTwo();
                if(StringUtils.isNotBlank(patientId)){
                    sendMsg(patientId,imei);
                }
            }

        }
    }

    public void sendMsg(String patientId,String imei) throws Exception{
        AppPatientUser patient = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,patientId);
        AppSignForm sign = sysDao.getAppSignformDao().findSignFormByUser(patientId);

        if(sign!=null){
            AppSeekHelp sos = new AppSeekHelp();
            sos.setSeekPatientId(patientId);
            sos.setSeekDevId(imei);
            sos.setSeekDrId(sign.getSignDrId());
            sos.setSeekAreaCode(sign.getSignAreaCode());
            sos.setSeekHospId(sign.getSignHospId());
            sos.setSeekTeamId(sign.getSignTeamId());
            sos.setSeekHelpDate(Calendar.getInstance());
            sysDao.getServiceDo().add(sos);

            List<AppTeamMemberEntity> ls = sysDao.getAppTeamMemberDao().findMemByTeamId(sign.getSignTeamId());
            String sosRemark = "";
            for(AppTeamMemberEntity entity : ls){
                String dtitle = "设备呼叫";
                String patientName = "无名氏";
                if(StringUtils.isNotBlank(patient.getPatientName())){
                    patientName = patient.getPatientName();
                }
                String tel = "未知";
                if(StringUtils.isNotBlank(patient.getPatientTel())){
                    tel = patient.getPatientTel();
                }
                String address = "未知";
                if(StringUtils.isNotBlank(patient.getPatientAddress())){
                    address = patient.getPatientAddress();
                }
                String dcontent= "居民"+patientName+",电话:"+tel+",地址:"+address+"在"+ ExtendDate.getYMD_h_m(Calendar.getInstance())+"，请求帮助!"+"(" +entity.getStrMemTeamName()+ ")";
                if(StringUtils.isBlank(sosRemark)){
                    sosRemark = "电话:"+tel+",地址:"+address+"在"+ ExtendDate.getYMD_h_m(Calendar.getInstance())+"，请求帮助!";
                }
                sysDao.getAppNoticeDao().addNotices(dtitle,dcontent, NoticesType.HJXX.getValue(),patientId ,entity.getMemDrId(),tel, DrPatientType.DR.getValue());
            }
            if(AddressType.SMS.getValue().equals(AreaUtils.getAreaCode(sign.getSignAreaCode(),"2")) || AddressType.XMS.getValue().equals(AreaUtils.getAreaCode(sign.getSignAreaCode(),"2"))){//三明尤溪
                SeekHelpVo vv = new SeekHelpVo();
                vv.setSeekDevId(imei);
                if(StringUtils.isNotBlank(sign.getSignAreaCode())){
                    CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,sign.getSignAreaCode());
                    if(address != null){
                        vv.setSeekAreaCode(address.getCtcode());
                        vv.setSeekAreaName(address.getAreaSname());
                    }
                }
                if(StringUtils.isNotBlank(sign.getSignDrId())){
                    AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,sign.getSignDrId());
                    if(drUser != null){
                        vv.setSeekDrId(drUser.getId());
                        vv.setSeekDrName(drUser.getDrName());
                        vv.setSeekDrTel(drUser.getDrTel());
                    }
                }
                vv.setSeekHelpDate(ExtendDate.getYMD_h_m_s(sos.getSeekHelpDate()));
                if(StringUtils.isNotBlank(sign.getSignHospId())){
                    AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,sign.getSignHospId());
                    if(dept != null){
                        vv.setSeekHospId(dept.getId());
                        vv.setSeekHospName(dept.getHospName());
                    }
                }
                if(StringUtils.isNotBlank(sign.getSignTeamId())){
                    AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,sign.getSignTeamId());
                    if(team != null){
                        vv.setSeekTeamId(team.getId());
                        vv.setSeekTeamName(team.getTeamName());
                    }
                }
                vv.setSeekPatientId(sign.getSignPatientId());
                vv.setSeekRemark(sosRemark);

                //user, AppSmHbpTwoEntity hbp, AppBloodSugarTwoEntity dm, String orgId, String teamId
                sysDao.getSecurityCardAsyncBean().fsSos(patient,null,null,sign.getSignHospId(),sign.getSignTeamId(),vv);
            }
        }
    }
}
