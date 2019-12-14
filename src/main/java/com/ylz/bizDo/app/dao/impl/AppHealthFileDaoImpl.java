package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppHealthFileDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfile;
import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfileYlkDTO;
import com.ylz.bizDo.jtapp.basicHealthVo.DwellerfileOneQvo;
import com.ylz.bizDo.jtapp.basicHealthVo.DwellerfileQvo;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.commonVo.AppInternetNewsQvo;
import com.ylz.bizDo.jtapp.commonVo.AppInternetNewsSonQvo;
import com.ylz.bizDo.jtapp.drVo.AppFileAuditQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.DrPatientType;
import com.ylz.packcommon.common.comEnum.NoticesType;
import com.ylz.packcommon.common.comEnum.OpenTheInterfaceState;
import com.ylz.packcommon.common.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.opensaml.xml.signature.P;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/2/7.
 */
@Service("appHealthFileDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppHealthFileDaoImpl implements AppHealthFileDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public AppHealthFile saveFile(AppFileAuditQvo qvo) throws Exception {
        List<AppHealthFile> files = sysDao.getServiceDo().loadByPk(AppHealthFile.class,"hfPatientId",qvo.getHfPatientId());
        if(files!=null && files.size()>0){
//            AppHealthFile file = (AppHealthFile)sysDao.getServiceDo().find(AppHealthFile.class,qvo.getId());
            AppHealthFile file = files.get(0);
            if(StringUtils.isNotBlank(qvo.getData())){
                file.setModifyData(qvo.getData());
            }
            if(StringUtils.isNotBlank(qvo.getHfDrId())){
                file.setHfDrId(qvo.getHfDrId());
            }
            if(StringUtils.isNotBlank(qvo.getHfTeamId())){
                file.setHfTeamId(qvo.getHfTeamId());
            }
            if(StringUtils.isNotBlank(qvo.getHfHospId())){
                file.setHfHospId(qvo.getHfHospId());
            }
            if(StringUtils.isNotBlank(qvo.getHfAreaCode())){
                file.setHfAreaCode(qvo.getHfAreaCode());
            }
            file.setHfState("1");
            sysDao.getServiceDo().modify(file);
            AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getHfPatientId());
            sysDao.getAppNoticeDao().addNotices("建档消息提醒：修改审核",user.getPatientName()+"居民修改了居民档案个人信息，请及时核对审批。",
                    NoticesType.JDXX.getValue(),qvo.getHfPatientId(),qvo.getHfDrId(),file.getId(), DrPatientType.DR.getValue());
            return file;
        }else{
            AppHealthFile file = new AppHealthFile();
            file.setData(qvo.getData());
            file.setHfPatientId(qvo.getHfPatientId());
            file.setHfDrId(qvo.getHfDrId());
            file.setHfTeamId(qvo.getHfTeamId());
            file.setHfHospId(qvo.getHfHospId());
            file.setHfAreaCode(qvo.getHfAreaCode());
            sysDao.getServiceDo().add(file);
            AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getHfPatientId());
            sysDao.getAppNoticeDao().addNotices("建档消息提醒：预建档审核",user.getPatientName()+"居民申请预建档，请您处理。",
                    NoticesType.JDXX.getValue(),qvo.getHfPatientId(),qvo.getHfDrId(),file.getId(), DrPatientType.DR.getValue());
            return file;
        }

    }

    /**
     * 查询患者提交的健康档案
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppHealthFile> findFile(AppCommQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT * FROM APP_HEALTH_FILE WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getPatientId())){
            map.put("patientId",qvo.getPatientId());
            sql += " AND HF_PATIENT_ID =:patientId ";
        }
        if(StringUtils.isNotBlank(qvo.getSignAreaCode())){
            map.put("areaCode",qvo.getSignAreaCode()+"%");
            sql += " AND HF_AREA_CODE like :areaCode ";
        }
        if(StringUtils.isNotBlank(qvo.getDrId())){
            map.put("drId",qvo.getDrId());
            sql += " AND HF_DR_ID =:drId ";
        }
        if(StringUtils.isNotBlank(qvo.getSignType())){
            sql += " AND HF_AUDIT_STATE = '0' ";
        }
        sql += " ORDER BY HS_CREATE_DATE DESC ";
        List<AppHealthFile> list = sysDao.getServiceDo().findSqlMap(sql,map,AppHealthFile.class);
        return list;
    }

    /**
     * 审核健康档案
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String fileAudit(AppFileAuditQvo qvo) throws Exception {
        if(StringUtils.isNotBlank(qvo.getId())){
            AppHealthFile file = (AppHealthFile)sysDao.getServiceDo().find(AppHealthFile.class,qvo.getId());
            if(file == null){
                return "查无此档案信息";
            }
            String hospId = "";
            String hospName = "";
            if("1".equals(qvo.getHfAuditState())){//审核通过同步到基卫
//                file.setData(file.getModifyData());
//                sysDao.getServiceDo().modify(file);
                String strr = file.getData();
                T_dwellerfileYlkDTO vo = JsonUtil.fromJson(strr,T_dwellerfileYlkDTO.class);
                T_dwellerfileYlkDTO voo = JsonUtil.fromJson(file.getModifyData(),T_dwellerfileYlkDTO.class);
                if(voo!=null){
                    if(StringUtils.isNotBlank(voo.getDoctor())){
                        vo.setDoctor(voo.getDoctor());
                    }
                    if(StringUtils.isNotBlank(voo.getWorkstatus())){
                        vo.setWorkstatus(voo.getWorkstatus());
                    }
                    if(StringUtils.isNotBlank(voo.getWorkplace())){
                        vo.setWorkplace(voo.getWorkplace());
                    }
                    if(StringUtils.isNotBlank(voo.getBloodtype())){
                        vo.setBloodtype(voo.getBloodtype());
                    }
                    if(StringUtils.isNotBlank(voo.getRhxx())){
                        vo.setRhxx(voo.getRhxx());
                    }
                    if(StringUtils.isNotBlank(voo.getCdegree())){
                        vo.setCdegree(voo.getCdegree());
                    }
                    if(StringUtils.isNotBlank(voo.getMstatus())){
                        vo.setMstatus(voo.getMstatus());
                    }
                    if(StringUtils.isNotBlank(voo.getNamecode())){
                        vo.setNamecode(voo.getNamecode());
                    }
                    if(StringUtils.isNotBlank(voo.getTelphonetype())){
                        vo.setTelphonetype(voo.getTelphonetype());
                    }
                    if(StringUtils.isNotBlank(voo.getCzzgbx())){
                        vo.setCzzgbx(voo.getCzzgbx());
                    }
                    if(StringUtils.isNotBlank(voo.getCzjmbx())){
                        vo.setCzjmbx(voo.getCzjmbx());
                    }
                    if(StringUtils.isNotBlank(voo.getXrhzyl())){
                        vo.setXrhzyl(voo.getXrhzyl());
                    }
                    if(StringUtils.isNotBlank(voo.getPkjz())){
                        vo.setPkjz(voo.getPkjz());
                    }
                    if(StringUtils.isNotBlank(voo.getSyylbx())){
                        vo.setSyylbx(voo.getSyylbx());
                    }
                    if(StringUtils.isNotBlank(voo.getQgf())){
                        vo.setQgf(voo.getQgf());
                    }
                    if(StringUtils.isNotBlank(voo.getQzf())){
                        vo.setQzf(voo.getQzf());
                    }
                    if(StringUtils.isNotBlank(voo.getQtfs00())){
                        vo.setQtfs00(voo.getQtfs00());
                    }
                    if(StringUtils.isNotBlank(voo.getJwbsbh())){
                        vo.setJwbsbh(voo.getJwbsbh());
                    }
                    if(StringUtils.isNotBlank(voo.getJwbsjb())){
                        vo.setJwbsjb(voo.getJwbsjb());
                    }
                    if(StringUtils.isNotBlank(voo.getSensitive())){
                        vo.setSensitive(voo.getSensitive());
                    }
                    if(StringUtils.isNotBlank(voo.getExpose())){
                        vo.setExpose(voo.getExpose());
                    }
                    if(StringUtils.isNotBlank(voo.getCjqk0())){
                        vo.setCjqk0(voo.getCjqk0());
                    }
                    if(StringUtils.isNotBlank(voo.getJwbsss())){
                        vo.setJwbsss(voo.getJwbsss());
                    }
                    if(StringUtils.isNotBlank(voo.getJwbsws())){
                        vo.setJwbsws(voo.getJwbsws());
                    }
                    if(StringUtils.isNotBlank(voo.getJwbssx())){
                        vo.setJwbssx(voo.getJwbssx());
                    }
                    if(StringUtils.isNotBlank(voo.getJzsfq())){
                        vo.setJzsfq(voo.getJzsfq());
                    }
                    if(StringUtils.isNotBlank(voo.getJzsmq())){
                        vo.setJzsmq(voo.getJzsmq());
                    }
                    if(StringUtils.isNotBlank(voo.getJzsxm())){
                        vo.setJzsxm(voo.getJzsxm());
                    }
                    if(StringUtils.isNotBlank(voo.getJzszn())){
                        vo.setJzszn(voo.getJzszn());
                    }
                    if(StringUtils.isNotBlank(voo.getYcbs00())){
                        vo.setYcbs00(voo.getYcbs00());
                    }
                    if(StringUtils.isNotBlank(voo.getCfpfss())){
                        vo.setCfpfss(voo.getCfpfss());
                    }
                    if(StringUtils.isNotBlank(voo.getRllx())){
                        vo.setRllx(voo.getRllx());
                    }
                    if(StringUtils.isNotBlank(voo.getYs())){
                        vo.setYs(voo.getYs());
                    }
                    if(StringUtils.isNotBlank(voo.getCs())){
                        vo.setCs(voo.getCs());
                    }
                    if(StringUtils.isNotBlank(voo.getQcl())){
                        vo.setQcl(voo.getQcl());
                    }
                }

                if(StringUtils.isNotBlank(file.getHfHospId())){
                    AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,file.getHfHospId());
                    if(dept!=null){
                        if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                            String areaCode = AreaUtils.getAreaCode(dept.getHospAreaCode(),"2");
                            if(!"3501".equals(areaCode)){
                                if(StringUtils.isBlank(vo.getRef_ci_id())){
                                    String[] hospIds = file.getHfHospId().split("_");
                                    vo.setRef_ci_id(hospIds[1]);
                                }
                                if(StringUtils.isBlank(vo.getJdrq00())){
                                    vo.setJdrq00(ExtendDate.getYYYYMMD(Calendar.getInstance()));
                                }
                                if(StringUtils.isBlank(vo.getDoctor())){
                                    String[] drIds = file.getHfDrId().split("_");
                                    vo.setDoctor(drIds[1]);
                                }
                                if(StringUtils.isBlank(vo.getInvestigators())){
                                    String[] drIds = file.getHfDrId().split("_");
                                    vo.setInvestigators(drIds[1]);
                                }
                                if(StringUtils.isBlank(vo.getCreator())){
                                    String[] drIds = file.getHfDrId().split("_");
                                    vo.setCreator(drIds[1]);
                                }
                            }else{
                                if(StringUtils.isBlank(vo.getRef_ci_id())){
                                    vo.setRef_ci_id(file.getHfHospId());
                                }
                                if(StringUtils.isBlank(vo.getJdrq00())){
                                    vo.setJdrq00(ExtendDate.getYYYYMMD(Calendar.getInstance()));
                                }
                                if(StringUtils.isBlank(vo.getDoctor())){
                                    vo.setDoctor(file.getHfDrId());
                                }
                                if(StringUtils.isBlank(vo.getInvestigators())){
                                    vo.setInvestigators(file.getHfDrId());
                                }
                                if(StringUtils.isBlank(vo.getCreator())){
                                    vo.setCreator(file.getHfDrId());
                                }
                            }
                        }
                    }
                }
                DwellerfileQvo qqvo = new DwellerfileQvo();
                qqvo.setFileQvo(vo);
                if(StringUtils.isNotBlank(file.getHfHospId())){
                    AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,file.getHfHospId());
                    if(dept!=null){
                        String cityCode = AreaUtils.getAreaCode(dept.getHospAreaCode(),"2");
                        CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                        if(code!=null){
                            qqvo.setUrlType(code.getCodeTitle());
                        }
                    }
                }
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = JSONObject.fromObject(qqvo);
                String state = PropertiesUtil.getConfValue("openTheInterface");
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=uploadHealthCareRecord";
                String str = null;
                String requestUserId = "";
                String requestUserName = "";
                if(StringUtils.isNotBlank(file.getHfDrId())){
                    AppDrUser drUser =(AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,file.getHfDrId());
                    if(drUser != null){
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                if(OpenTheInterfaceState.NOT.getValue().equals(state)){
                    str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                }else{
                    str = sysDao.getSecurityCardAsyncBean().getDateBasic(requestUserId,requestUserName,jsonParam.toString(),file.getHfPatientId(),"uploadHealthCareRecord");
                }
                sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, DrPatientType.DR.getValue(),"uploadHealthCareRecord");
                //{"message":"","success":true,"errorcode":"","entity":{"t_dwellerfile":["35010200300800001"]},"type":"1","errorType":"0"}
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("entity") != null){
                        JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                        if("true".equals(entity.get("success").toString())){
                            file.setHfAuditState(qvo.getHfAuditState());
                            sysDao.getServiceDo().modify(file);
                        }else{
                            return  entity.get("message").toString();
                        }
                    }
                }else{

                }
            }else {
                file.setModifyData("");
                file.setHfRefusedReason(qvo.getHfRefusedReason());
                sysDao.getServiceDo().modify(file);
            }
//            return "true";
            AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,file.getHfDrId());
            String drName = "";//医生姓名
            String teamName = "";//团队名称
            String drId = "";//医生主键

            if(drUser!=null){
                drName = drUser.getDrName();
                AppHospDept hospDept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(hospDept != null){
                    hospId = hospDept.getId();
                    hospName = hospDept.getHospName();
                }

            }
            AppTeam team = (AppTeam) sysDao.getServiceDo().find(AppTeam.class,file.getHfTeamId());
            if(team!=null){
                teamName = team.getTeamName();
            }
            String yhContent = "";
            String yhTitle = "";
            if("1".equals(qvo.getHfAuditState())){
                if("0".equals(file.getHfState())){//预建档
                    yhTitle = "预建档消息";
                    yhContent = "您好，您申请提交的预建档消息，"+teamName+drName+"医生已审核通过了。";
                    sysDao.getAppNoticeDao().addNotices("预建档消息","您好，您申请提交的预建档消息，"+teamName+drName+"医生已审核通过了。",NoticesType.JDXX.getValue(),
                            file.getHfDrId(),file.getHfPatientId(),file.getId(),DrPatientType.PATIENT.getValue());
                }else{
                    yhTitle = "建档消息";
                    yhContent = "您好，"+teamName+drName+"医生已审核通过您提交修改居民个人档案内容，请及时前往“个人资料”-“居民健康档案”获取更新。";
                    sysDao.getAppNoticeDao().addNotices("建档消息","您好，"+teamName+drName+"医生已审核通过您提交修改居民个人档案内容，请及时前往“个人资料”-“居民健康档案”获取更新。",NoticesType.JDXX.getValue(),
                            file.getHfDrId(),file.getHfPatientId(),file.getId(),DrPatientType.PATIENT.getValue());
                }
            }else if("2".equals(qvo.getHfAuditState())){
                if("0".equals(file.getHfState())){//预建档
                    yhTitle = "预建档消息";
                    yhContent = "您好，您申请提交的预建档消息，"+teamName+drName+"医生已拒绝，拒绝原因："+file.getHfRefusedReason()+"。";
                    sysDao.getAppNoticeDao().addNotices("预建档消息","您好，您申请提交的预建档消息，"+teamName+drName+"医生已拒绝，拒绝原因："+file.getHfRefusedReason()+"。",NoticesType.JDXX.getValue(),
                            file.getHfDrId(),file.getHfPatientId(),file.getId(),DrPatientType.PATIENT.getValue());
                }else{
                    yhTitle = "建档消息";
                    yhContent = "您好，"+teamName+drName+"医生已拒绝您提交修改居民个人档案内容。拒绝原因："+file.getHfRefusedReason()+"。";
                    sysDao.getAppNoticeDao().addNotices("建档消息","您好，"+teamName+drName+"医生已拒绝您提交修改居民个人档案内容。拒绝原因："+file.getHfRefusedReason(),NoticesType.JDXX.getValue(),
                            file.getHfDrId(),file.getHfPatientId(),file.getId(),DrPatientType.PATIENT.getValue());
                }
            }
            if(StringUtils.isNotBlank(yhContent)){
                //查询是否开启调用互联网接口
                String openState = PropertiesUtil.getConfValue("openInterNetState");
                if("1".equals(openState)) {//开启
                    AppInternetNewsQvo qqvo = new AppInternetNewsQvo();
                    qqvo.setTitle(yhTitle);
                    qqvo.setContent(yhContent);
                    qqvo.setDoctorName(drName);
                    qqvo.setDoctorId(drId);
                    qqvo.setHospName(hospName);
                    qqvo.setHospId(hospId);
                    qqvo.setMsgType("YW003");
                    qqvo.setMsgShowType("2");
                    AppInternetNewsSonQvo sonQvo = new AppInternetNewsSonQvo();
                    sonQvo.setType("ZXJD");
                    if(StringUtils.isNotBlank(file.getHfPatientId())){
                        AppPatientUser patientUser = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,file.getHfPatientId());
                        if(patientUser != null){
                            qqvo.setType("3");
                            qqvo.setPhone(patientUser.getPatientTel());
                            qqvo.setIdNo(patientUser.getPatientIdno());
                            qqvo.setIdType("01");
                            qqvo.setUserName(patientUser.getPatientName());
                            qqvo.setUserId(patientUser.getPatientEHCId());//电子健康卡主键
                            sonQvo.setPatientIdNo(patientUser.getPatientIdno());
                            sonQvo.setPatientCard(patientUser.getPatientCard());
                            sonQvo.setPatientName(patientUser.getPatientName());
                            sonQvo.setPatientTel(patientUser.getPatientTel());
                            sonQvo.setPatientNeighborhoodCommittee(patientUser.getPatientNeighborhoodCommittee());
                            sonQvo.setEhcId(patientUser.getPatientEHCId());
                            sonQvo.setEhcCardNo(patientUser.getPatientEHCNo());
                            sonQvo.setDeviceType("");
                            qqvo.setUrlParam(sonQvo);
                        }
                    }
                    sysDao.getSecurityCardAsyncBean().sendOutInternetNews(qqvo);
                }
            }
            return "true";
        }else{
            return "主键id不能为空";
        }

    }

    @Override
    public AppHealthFile findFileByPatientId(String patientId) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("patientId",patientId);
        String sql = "SELECT * FROM APP_HEALTH_FILE WHERE HF_PATIENT_ID=:patientId";
        List<AppHealthFile> list = sysDao.getServiceDo().findSqlMap(sql,map,AppHealthFile.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
