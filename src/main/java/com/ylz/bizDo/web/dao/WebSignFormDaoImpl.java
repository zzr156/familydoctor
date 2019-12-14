package com.ylz.bizDo.web.dao;

import com.ylz.bizDo.app.entity.AppSignFormEntity;
import com.ylz.bizDo.app.entity.AppWebSignFormListEntity;
import com.ylz.bizDo.app.entity.ResultVo;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppSignFormVo;
import com.ylz.bizDo.app.vo.AppSignInfoAllVo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.web.po.WebDrUser;
import com.ylz.bizDo.web.po.WebHospDept;
import com.ylz.bizDo.web.po.WebPatientUser;
import com.ylz.bizDo.web.po.WebTeam;
import com.ylz.bizDo.web.vo.WebFamilySignVo;
import com.ylz.bizDo.web.vo.WebSignVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**w
 * 签约接口
 */
@Service("webSignFormDao")
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class WebSignFormDaoImpl implements WebSignFormDao {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(WebSignFormDaoImpl.class);

    private static final String agreeId = "9ac4acce-6d5e-4979-9258-10a818af129c";
    private static final String areaCode = "350000000000";

    @Autowired
    private SysDao sysDao;

    //福州签约web版接口 对外
    public AppSignForm webSignUp(WebSignVo vo) throws Exception{
        System.out.println("传输:===="+JsonUtil.toJson(vo));
        System.out.print(vo.getDrId()+"======++==="+vo.getDrOperatorId()+"======++===="+vo.getDrAssistantId());
        WebHospDept whosp = null;
        WebDrUser wdruser = null;
        WebDrUser webDrUserBatchOperator = null;//操作者
        WebDrUser webDrUserAssistant = null;//助理
        AppTeamMember teamMember = null;
        WebTeam wteam = null;
        WebPatientUser wpuser = null;
        String patientId=vo.getPatientId();
        String teamid=vo.getTeamId();
        String drId=vo.getDrId();
        String age = null;
        //医院信息 根据医院id查询是否存在，没有自动创建医院
        AppHospDept adept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,vo.getHospId());
        if(adept==null){  //创建医院
            whosp=new WebHospDept();
            whosp.setId(vo.getHospId());
            whosp.setHospName(vo.getHospName());
            whosp.setHospAreaCode(vo.getHospAreaCode());
            whosp.setHospAddress(vo.getHospAddress());
            whosp.setHospTel(vo.getHospTel());
            whosp.setHospState("");
//            whosp.setHospLevelType();
            CdAddress address = sysDao.getCdAddressDao().findByCacheCode(vo.getHospAreaCode());
            if(address != null){
                whosp.setHospUpcityareaId(address.getPid());
            }

            sysDao.getServiceDo().add(whosp);
            AppAgreement v = (AppAgreement) sysDao.getServiceDo().find(AppAgreement.class,agreeId);
            if(v != null){
                AppAgreement agreement = new AppAgreement();
                agreement.setMentUseType("2");
                agreement.setMentCityId(AreaUtils.getAreaCode(whosp.getHospAreaCode(),"2"));
                agreement.setMentContent(v.getMentContent());
                agreement.setMentState("1");
                agreement.setMentType("1");
                agreement.setMentHospId(whosp.getId());
                agreement.setMentTitle(v.getMentTitle());
                sysDao.getServiceDo().add(agreement);
            }
        }
        //医生信息 根据医生id查询是否存在，没有自动创建医生

        AppDrUser dr= (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,vo.getDrId());
        if(dr==null){
            wdruser=new WebDrUser();
            wdruser.setId(vo.getDrId());
            wdruser.setDrHospId(vo.getHospId());
            wdruser.setDrName(vo.getDrName());
            wdruser.setDrAccount(vo.getDrAccount());
            wdruser.setDrPwd(vo.getDrPwd());
            wdruser.setDrGender(vo.getDrGender());
            wdruser.setDrTel(vo.getDrTel());
            wdruser.setDrRole("4");
            if(vo.getDrTel().length()>=11){
                wdruser.setDrTelPwd(Md5Util.MD5(vo.getDrTel().substring(vo.getDrTel().length()-6,vo.getDrTel().length())));

            }

            if(StringUtils.isNotBlank(wdruser.getDrName())) {
                wdruser.setDrBopomofo(PinyinUtil.getPinYinHeadChar(wdruser.getDrName()));
            }
            sysDao.getServiceDo().add(wdruser);
//            sysDao.getServiceDo().removePoFormSession(wdruser);

//            this.sysDao.getSecurityCardAsyncBean().registeredEasemob(wdruser.getId());
        }
       if(!vo.getDrId().equals(vo.getDrOperatorId())){
           //操作人
           if(vo.getDrOperatorId()!=null && vo.getDrOperatorId()!=""){
               AppDrUser drBatchOperator = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,vo.getDrOperatorId());
               if(drBatchOperator==null){
                   webDrUserBatchOperator=new WebDrUser();
                   webDrUserBatchOperator.setId(vo.getDrOperatorId());
                   webDrUserBatchOperator.setDrHospId(vo.getHospOperatorId());
                   webDrUserBatchOperator.setDrName(vo.getDrOperatorName());
                   webDrUserBatchOperator.setDrAccount(vo.getDrOperatorAccount());
                   webDrUserBatchOperator.setDrPwd(vo.getDrOperatorPwd());
                   webDrUserBatchOperator.setDrGender(vo.getDrOperatorGender());
                   webDrUserBatchOperator.setDrTel(vo.getDrOperatorTel());
                   if(StringUtils.isNotBlank(webDrUserBatchOperator.getDrName())) {
                       webDrUserBatchOperator.setDrBopomofo(PinyinUtil.getPinYinHeadChar(webDrUserBatchOperator.getDrName()));
                   }
                   sysDao.getServiceDo().add(webDrUserBatchOperator);
//                sysDao.getServiceDo().removePoFormSession(webDrUserBatchOperator);
//            this.sysDao.getSecurityCardAsyncBean().registeredEasemob(wdruser.getId());
               }
           }
       }


    //助理
        if(vo.getDrAssistantId()!=null && vo.getDrAssistantId()!=""){
            if(!vo.getDrId().equals(vo.getDrAssistantId()) && !vo.getDrAssistantId().equals(vo.getDrOperatorId())){
            AppDrUser drAssistant = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,vo.getDrAssistantId());
            if(drAssistant==null){
                webDrUserAssistant=new WebDrUser();
                webDrUserAssistant.setId(vo.getDrAssistantId());
                webDrUserAssistant.setDrHospId(vo.getHospAssistantId());
                webDrUserAssistant.setDrName(vo.getDrAssistantName());
                webDrUserAssistant.setDrAccount(vo.getDrAccount());
                webDrUserAssistant.setDrPwd(vo.getDrAssistantPwd());
                webDrUserAssistant.setDrGender(vo.getDrAssistantGender());
                webDrUserAssistant.setDrTel(vo.getDrAssistantTel());
                if(StringUtils.isNotBlank(webDrUserAssistant.getDrName())) {
                    webDrUserAssistant.setDrBopomofo(PinyinUtil.getPinYinHeadChar(webDrUserAssistant.getDrName()));
                }
                sysDao.getServiceDo().add(webDrUserAssistant);
//            this.sysDao.getSecurityCardAsyncBean().registeredEasemob(wdruser.getId());
            }
        }
    }
        //团队 医生id查询是否已有团队 没有就根据上传的团队名称自动创建团队
//        List<AppTeamMember> tls=sysDao.getServiceDo().loadByPk(AppTeamMember.class,"memDrId",vo.getDrId());
        List<AppTeamMember> tls=this.findTeam(vo.getHospId(),vo.getTeamId());
        if(tls!=null && !tls.isEmpty()){
            for (AppTeamMember a:tls){
                if(a.getMemTeamName().equals(vo.getTeamName())){
                    teamid=a.getMemTeamid();
                    break;
                }else {
                    teamid=a.getMemTeamid();
                }
            }
        }else {
            wteam= (WebTeam) sysDao.getServiceDo().find(WebTeam.class,vo.getTeamId());
            String groupId = "";
            if(wteam==null) {
                wteam = new WebTeam();
                wteam.setId(vo.getTeamId());
                wteam.setTeamName(vo.getTeamName());
                wteam.setTeamHospId(vo.getHospId());
                wteam.setTeamHospName(vo.getHospName());
                wteam.setTeamDrId(vo.getDrId());
                wteam.setTeamState("1");
                wteam.setTeamType("0");
                sysDao.getServiceDo().add(wteam);
//                AppTeam team = (AppTeam)this.sysDao.getServiceDo().find(AppTeam.class,wteam.getId());
//                groupId = this.sysDao.getSecurityCardAsyncBean().addGroup(team);
            }
            teamMember=findTeamMember(vo.getDrId(),vo.getTeamId());
            if(teamMember==null) {
                teamMember = new AppTeamMember();
                teamMember.setMemDrId(vo.getDrId());
                teamMember.setMemDrName(vo.getDrName());
                teamMember.setMemState("1");
                teamMember.setMemWorkType("3");
                teamMember.setMemTeamid(vo.getTeamId());
                teamMember.setMemTeamName(vo.getTeamName());
                teamMember.setMemState(vo.getMemState());
                sysDao.getServiceDo().add(teamMember);
//                if(!teamMember.getMemDrId().equals(wteam.getTeamDrId())){
//                    this.sysDao.getSecurityCardAsyncBean().addGroupMembers(groupId,teamMember.getMemDrId());
//                }

            }
        }
        //患者 根据患者身份证查询是否存在，没有自动创建患者
        List<AppPatientUser> puser= sysDao.getServiceDo().loadByPk(AppPatientUser.class,"patientIdno",vo.getPatientIdno());
        if(puser!=null && !puser.isEmpty()){
            vo.setPatientId(puser.get(0).getId());//用户存在 替换成当前用户
            vo.setPatientName(puser.get(0).getPatientName());
            vo.setPatientGender(puser.get(0).getPatientGender());
            age=puser.get(0).getPatientAge();
        }
        else{
            wpuser=new WebPatientUser();
            wpuser.setId(vo.getPatientId());
            wpuser.setPatientName(vo.getPatientName());
            wpuser.setPatientIdno(vo.getPatientIdno());
            wpuser.setPatientCard(vo.getPatientCard());
//            wpuser.setPatientTel(vo.getPatientTel());
            wpuser.setPatientAddress(vo.getPatientAddress());
            wpuser.setPatientPwd(vo.getPatientPwd());
            if(StringUtils.isNotBlank(wpuser.getPatientName())) {
                wpuser.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(wpuser.getPatientName()));
            }
            Map<String,Object> idNos = new HashMap<String,Object>();
            System.out.println("身份证:===="+vo.getPatientId()+"========");
            if(vo.getPatientIdno().trim().length() == 18){
                idNos = CardUtil.getCarInfo(vo.getPatientIdno().trim());
            }else if(vo.getPatientIdno().trim().length() == 15){
                idNos = CardUtil.getCarInfo15W(vo.getPatientIdno().trim());
            }
            if(idNos.get("birthday") != null) {
                wpuser.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
            }
            if(idNos.get("age") != null){
                if(wpuser.getPatientBirthday() != null) {
                    wpuser.setPatientAge( AgeUtil.getAgeYear(wpuser.getPatientBirthday()));
                }else{
                    wpuser.setPatientAge(idNos.get("age").toString());
                }

            }
            if(idNos.get("sex") != null) {
                wpuser.setPatientGender(idNos.get("sex").toString());
            }

            wpuser.setPatientProvince(vo.getAreaCodeProvince());
            wpuser.setPatientCity(vo.getAreaCodeCity());
            wpuser.setPatientCity(vo.getPatientCity());
            wpuser.setPatientStreet(vo.getPatientStreet());
            wpuser.setPatientTel(vo.getPatientTel());
            wpuser.setPatientjmda(vo.getPatientjmda());
            wpuser.setPatientjtda(vo.getPatientjtda());
            wpuser.setPatientState(CommonEnable.QIYONG.getValue());
            wpuser.setPatientHealthy(CommonEnable.JINYONG.getValue());
            wpuser.setPatientJgState(UserJgType.WEISHEZHI.getValue());
            wpuser.setPatientEaseState(UserJgType.WEISHEZHI.getValue());
            wpuser.setPatientUpHpis(UserUpHpisType.WEIJIHUO.getValue());//用户未激活
            sysDao.getServiceDo().add(wpuser);
//            this.sysDao.getSecurityCardAsyncBean().registeredEasemob(wpuser.getId());
        }
        //签约----------------------------------------------
        System.out.println("patientId--"+patientId+"--teamid--"+teamid+"--drId--"+drId+"---hospId---"+vo.getHospId());
//        AppSignForm from = this.sysDao.getAppSignformDao().findBySignweb(patientId,teamid,drId);
        AppSignBatch batch=new AppSignBatch();//批次
        AppSignForm form=new AppSignForm();//签约单
//        AppTeam teamvo= (AppTeam) sysDao.getServiceDo().find(AppTeam.class,teamid);//团队
//        AppPatientUser uservo= (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,patientId);//患者
        batch.setBatchCreateDate(Calendar.getInstance());
        batch.setBatchTeamId(teamid);
        batch.setBatchTeamName(vo.getTeamName());
        batch.setBatchCreatePersid(vo.getPatientId());
        batch.setBatchPatientName(vo.getPatientName());
        batch.setBatchOperatorId(vo.getDrOperatorId());
        batch.setBatchOperatorName(vo.getDrOperatorName());
        //组织批次号
        AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,vo.getHospId());
        if(StringUtils.isNotBlank(vo.getAreaCodeCity())) {
            if(dept != null){
                batch.setBatchAreaCode(dept.getHospAreaCode());
            }else{
                batch.setBatchAreaCode(whosp.getHospAreaCode());
            }
            AppSerial serial = sysDao.getAppSignformDao().getAppSerial(vo.getAreaCodeCity().substring(0, 4), "batch");
            //莆田
           // AppSerial serial = sysDao.getAppSignformDao().getAppSerial("3503", "batch");
            if(serial!=null) {
                Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.WEBSTATE.getValue());
                serial.setSerialNo(bcnum.get("old").toString());
                sysDao.getServiceDo().modify(serial);
                batch.setBatchNum(bcnum.get("new").toString());//批次号
            }
        }
        //
        sysDao.getServiceDo().add(batch);
        form.setSignBatchId(batch.getId());
        //组织编码
        if(StringUtils.isNotBlank(vo.getAreaCodeCity())) {
            if(dept != null){
                form.setSignHospId(dept.getId());
                form.setSignAreaCode(dept.getHospAreaCode());
            }else{
                form.setSignHospId(whosp.getId());
                form.setSignAreaCode(whosp.getHospAreaCode());

            }
            System.out.print("========="+vo.getAreaCodeCity()+"=========");
           AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(vo.getAreaCodeCity().substring(0, 4), "sign");
           //莆田
           // AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial("3503", "sign");
            if (serialSign != null) {
                Map<String,Object> sinum = sysDao.getAppSignformDao().getNum(serialSign.getSerialNo(),SignFormType.WEBSTATE.getValue());
                serialSign.setSerialNo(sinum.get("old").toString());
                sysDao.getServiceDo().modify(serialSign);
                form.setSignNum(sinum.get("new").toString());//签约编码
            }
        }
        //
        form.setSignPatientId(vo.getPatientId());
        form.setSignDate(ExtendDate.getCalendar(vo.getSignDate()));
        form.setSignPayState(vo.getSignPayState());//0：未缴费
        form.setSignType(vo.getSignType());//1家庭签约
        form.setSignTeamId(teamid);
        form.setSignTeamName(vo.getTeamName());
        form.setSignWay("2");//医生代签
        form.setSignState("2");//已签约
        form.setSignFromDate(vo.getSignFromDate());
        form.setSignPatientGender(vo.getPatientGender());
        if(StringUtils.isNoneBlank(age)) {
            form.setSignPatientAge(Integer.parseInt(age));
        }
        form.setSignPatientIdNo(vo.getPatientIdno());
        form.setSignToDate(vo.getSignToDate());
        form.setSignDrId(drId);
        form.setSignContractState("0");//1是 0否
        form.setSignGreenState("0");//1是 0否
        form.setSignYellowState("0");//1是 0否
        form.setSignRedState("0");//1是 0否
        form.setSignsJjType(vo.getSignsJjType());
        form.setUpHpis("2");//数据来源web
        form.setSignHealthGroup("199");
        form.setSignlx(vo.getSignlx());//医保类型
        form.setSignczpay(vo.getSignczpay());//财政
        form.setSignzfpay(vo.getSignzfpay());//自费
        form.setSignDrAssistantId(vo.getSignDrAssistantId());//助理
        sysDao.getServiceDo().add(form);
//        List<AppTeamMember> lsTeam = this.sysDao.getAppTeamMemberDao().findTeamId(form.getSignTeamId());
//        if(lsTeam != null && lsTeam.size() >0){
//            for(AppTeamMember v : lsTeam){
//                this.sysDao.getSecurityCardAsyncBean().addFridenSignl(form.getSignPatientId(),v.getMemDrId());
//            }
//        }
        //改服务人群为多选
        if(StringUtils.isNotBlank(vo.getSignPersGroup())){
            String[] groups = vo.getSignPersGroup().split(",");
            if (groups != null && groups.length > 0) {
                form.setSignPersGroup(groups[0]);//支持之前版
                String areaCode = "";
                if(dept!=null){
                    areaCode = dept.getHospAreaCode();
                }else{
                    areaCode = whosp.getHospAreaCode();
                }
                sysDao.getAppLabelGroupDao().addLabel(groups,form.getId(),form.getSignTeamId(),areaCode, LabelManageType.FWRQ.getValue());
            }
        }

        //经济类型
        if(StringUtils.isNotBlank(vo.getSignsJjType())){
            String[] jjTypes = vo.getSignsJjType().split(",");
            if (jjTypes != null && jjTypes.length > 0) {
                form.setSignsJjType(jjTypes[0]);//支持之前版
                String areaCode = "";
                if(dept!=null){
                    areaCode = dept.getHospAreaCode();
                }else{
                    areaCode = whosp.getHospAreaCode();
                }
                sysDao.getAppLabelGroupDao().addLabel(jjTypes,form.getId(),form.getSignTeamId(),areaCode, LabelManageType.JJLX.getValue());
            }
        }
        return form;
    }

    //福州签约web版接口 对外
    public WebSignVo webSignUpTempPt(WebSignVo vo) throws Exception{
        System.out.println("传输:===="+JsonUtil.toJson(vo));
        WebHospDept whosp = null;
        WebDrUser wdruser = null;
        AppTeamMember teamMember = null;
        WebTeam wteam = null;
        //医院信息 根据医院id查询是否存在，没有自动创建医院
        AppHospDept adept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,vo.getHospId());
        if(adept==null){  //创建医院
            whosp=new WebHospDept();
            whosp.setId(vo.getHospId());
            whosp.setHospName(vo.getHospName());
            whosp.setHospAreaCode(vo.getHospAreaCode());
            whosp.setHospAddress(vo.getHospAddress());
            whosp.setHospTel(vo.getHospTel());
            sysDao.getServiceDo().add(whosp);
            AppAgreement v = (AppAgreement) sysDao.getServiceDo().find(AppAgreement.class,agreeId);
            if(v != null){
                AppAgreement agreement = new AppAgreement();
                agreement.setMentUseType("2");
                agreement.setMentCityId(AreaUtils.getAreaCode(whosp.getHospAreaCode(),"2"));
                agreement.setMentContent(v.getMentContent());
                agreement.setMentState("1");
                agreement.setMentType("1");
                agreement.setMentHospId(whosp.getId());
                agreement.setMentTitle(v.getMentTitle());
                sysDao.getServiceDo().add(agreement);
            }
        }else{
            whosp=new WebHospDept();
            whosp.setId(vo.getHospId());
        }        //医生信息 根据医生id查询是否存在，没有自动创建医生
        AppDrUser dr= (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,vo.getDrId());
        if(dr==null){
            wdruser=new WebDrUser();
            wdruser.setId(vo.getDrId());
            wdruser.setDrHospId(vo.getHospId());
            wdruser.setDrName(vo.getDrName());
            wdruser.setDrAccount(vo.getDrAccount());
            wdruser.setDrPwd(vo.getDrPwd());
            wdruser.setDrGender(vo.getDrGender());
            wdruser.setDrTel(vo.getDrTel());
            if(StringUtils.isNotBlank(wdruser.getDrName())) {
                wdruser.setDrBopomofo(PinyinUtil.getPinYinHeadChar(wdruser.getDrName()));
            }
            sysDao.getServiceDo().add(wdruser);
        }else{
            wdruser=new WebDrUser();
            wdruser.setId(vo.getDrId());
        }
        //团队 医生id查询是否已有团队 没有就根据上传的团队名称自动创建团队
        List<AppTeamMember> tls=this.findTeam(vo.getHospId(),vo.getTeamId());
        if(tls!=null && !tls.isEmpty()){

        }else {
            wteam= (WebTeam) sysDao.getServiceDo().find(WebTeam.class,vo.getTeamId());
            if(wteam==null) {
                wteam = new WebTeam();
                wteam.setId(vo.getTeamId());
                wteam.setTeamName(vo.getTeamName());
                wteam.setTeamHospId(vo.getHospId());
                wteam.setTeamHospName(vo.getHospName());
                wteam.setTeamDrId(vo.getDrId());
                wteam.setTeamState("1");
                wteam.setTeamType("0");
                sysDao.getServiceDo().add(wteam);
            }else {
                wteam = new WebTeam();
                wteam.setId(vo.getTeamId());
            }
            teamMember=findTeamMember(vo.getDrId(),vo.getTeamId());
            if(teamMember==null) {
                teamMember = new AppTeamMember();
                teamMember.setMemDrId(vo.getDrId());
                teamMember.setMemDrName(vo.getDrName());
                teamMember.setMemState("1");
                teamMember.setMemWorkType("3");
                teamMember.setMemTeamid(vo.getTeamId());
                teamMember.setMemTeamName(vo.getTeamName());
                teamMember.setMemState(vo.getMemState());
                teamMember.setDrRole(vo.getDrRole());
                sysDao.getServiceDo().add(teamMember);
            }
        }
        WebSignVo p = new WebSignVo();
        p.setDrId(wdruser.getId());
        p.setTeamId(wteam.getId());
        p.setHospId(whosp.getId());
        return p;
    }

    //签约数据上传至web版
    public List<AppTeamMember> findTeam(String hospId, String teamId) throws Exception{
        List<AppTeamMember> ls=null;
        String sql="SELECT\n" +
                "\tb.*\n" +
                "FROM\n" +
                "\tAPP_TEAM a LEFT JOIN APP_TEAM_MEMBER b on a.ID=b.MEM_TEAMID\n" +
                "WHERE\n" +
                "\ta.TEAM_HOSP_ID = :TEAM_HOSP_ID\n" +
                "AND b.MEM_DR_ID = :MEM_DR_ID AND a.TEAM_DEL_STATE = '0' ";
        Map<String ,Object> map=new HashedMap();
        map.put("TEAM_HOSP_ID",hospId);
        map.put("MEM_DR_ID",teamId);
        ls=sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
        return ls;
    }




    /**
     * 接收web数据接口
     * @throws Exception
     */
    public String signUpWeb(AppSignForm l,String url) throws Exception{
//        String url= PropertiesUtil.getConfValue("weburl");
//        Map<String ,Object> map=new HashedMap();
////        String sql="SELECT * from APP_SIGN_FORM a where a.UP_HPIS is null";//查询未上传数据
//        url= "http://192.168.30.90:8082/test/phis/hfs/cmmdo.action?dist=SIGN&act=appSignUp_NL";//测试
//        String sql2="SELECT * from APP_SIGN_FORM a where a.UP_HPIS is null and a.ID='0048b263-54be-4117-830d-5ad0a7abeadd'";//查询未上传数据 测试
//        List<AppSignForm> ls=sysDao.getServiceDo().findSqlMap(sql2,map,AppSignForm.class);
//        if(ls!=null && !ls.isEmpty()) {
//            for (AppSignForm l : ls) {

//------------------------------------------------------
        AppHospDept hosp= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,l.getSignHospId());
        AppDrUser dr= (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,l.getSignDrId());
        AppTeam team= (AppTeam) sysDao.getServiceDo().find(AppTeam.class,l.getSignTeamId());
        AppPatientUser user= (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,l.getSignPatientId());
        if(hosp!=null && dr!=null && team!=null && user!=null){
            WebSignVo vo = new WebSignVo();//组织数据上传
            //医院信息
            vo.setHospId(hosp.getId());
            vo.setHospName(hosp.getHospName());
            vo.setHospAreaCode(hosp.getHospAreaCode());
            vo.setHospTel(hosp.getHospTel());
            vo.setHospAddress(hosp.getHospAddress());
            //医生信息
            vo.setDrId(dr.getId());
            vo.setDrName(dr.getDrName());
            vo.setDrAccount(dr.getDrAccount());
            vo.setDrPwd(dr.getDrPwd());
            vo.setDrGender(dr.getDrGender());
            vo.setDrTel(dr.getDrTel());
            AppTeamMember teamm=findTeamMember(dr.getId(),team.getId());
            vo.setMemState(teamm.getMemState());
            //团队
            vo.setTeamId(team.getId());
            vo.setTeamName(team.getTeamName());
            //患者
            vo.setPatientId(user.getId());
            vo.setPatientName(user.getPatientName());
            vo.setPatientGender(user.getPatientGender());
            vo.setPatientIdno(user.getPatientIdno());
            vo.setPatientCard(user.getPatientCard());
            vo.setPatientTel(user.getPatientTel());
            vo.setPatientAddress(user.getPatientAddress());
            vo.setPatientPwd(user.getPatientPwd());
            //签约
            vo.setSignDate(ExtendDate.getYMD_h_m(l.getSignDate()));
            vo.setSignFromDate(l.getSignFromDate());
            vo.setSignToDate(l.getSignToDate());
            vo.setSignPayState(l.getSignPayState());
            vo.setSignType(l.getSignType());
            vo.setSignPersGroup(l.getSignPersGroup());
            vo.setSignsJjType(l.getSignsJjType());

            //开始上传
            String rt=HttpPostUtils.doPostJson(JacksonUtils.objToStr(vo),url);
            return rt;

        }else{
            System.out.println(l.getId()+":数据不全无法上传");
            logger.error(l.getId()+":数据不全无法上传");
            l.setUpHpis("99");
            sysDao.getServiceDo().modify(l);
        }

        //------------------------------------------------------
//            }
//        }
        return "{\"errCode\":900}";

    }

    public AppTeamMember findTeamMember(String drId,String teamId) throws Exception{
        Map<String ,Object> map=new HashedMap();
        String sql="SELECT\n" +
                "\ta.*\n" +
                "FROM\n" +
                "\tAPP_TEAM_MEMBER a\n" +
                "WHERE\n" +
                "\ta.MEM_DR_ID =:MEM_DR_ID \n" +
                "AND a.MEM_TEAMID =:MEM_TEAMID ";
        map.put("MEM_DR_ID",drId);
        map.put("MEM_TEAMID",teamId);
        List<AppTeamMember> ls=sysDao.getServiceDo().findSqlMap(sql,map,AppTeamMember.class);
        if(ls!=null && !ls.isEmpty()){
            return ls.get(0);
        }
        return null;
    }

    /**
     * Dmao
     * web签约保存（新）
     * @param vo
     * @return
     * @throws Exception
     */
    public AppSignFormEntity websignAdd(WebSignVo vo)throws Exception
    {
        AppSignFormEntity wvo = new AppSignFormEntity();
        String bytype="";
        String drid = vo.getDrId().split(";;;")[0];
        String assid = null;
        if(StringUtils.isNoneBlank(vo.getSignDrAssistantId())){
             assid = vo.getSignDrAssistantId().split(";;;")[0];
        }
        //居民用户表
        AppPatientUser uservo = null;
        List<AppPatientUser> puser= sysDao.getServiceDo().loadByPk(AppPatientUser.class,"patientIdno",vo.getPatientIdno());
        if(puser!=null && !puser.isEmpty()){
            uservo = puser.get(0);
//            uservo.setPatientProvince(areaCode);
            uservo.setPatientProvince(AreaUtils.getAreaCode(vo.getPatientCity(),"1")+"0000000000");
            uservo.setPatientCity(vo.getPatientCity());
            uservo.setPatientArea(vo.getPatientArea());
            uservo.setPatientStreet(vo.getPatientStreet());
            uservo.setPatientNeighborhoodCommittee(vo.getPatientNeighborhoodCommittee());
            uservo.setPatientAddress(vo.getPatientAddress());
            if(StringUtils.isNotBlank(uservo.getPatientName())) {
                uservo.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(uservo.getPatientName()));
            }
            Map<String,Object> idNos = new HashMap<String,Object>();
            if(vo.getPatientIdno().trim().length() == 18){
                idNos = CardUtil.getCarInfo(uservo.getPatientIdno().trim());
            }else if(vo.getPatientIdno().trim().length() == 15){
                idNos = CardUtil.getCarInfo15W(uservo.getPatientIdno().trim());
            }
            if(idNos.get("birthday") != null) {
                uservo.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
            }
//            if(idNos.get("age") != null){
//                if(uservo.getPatientBirthday() != null) {
//                    uservo.setPatientAge( AgeUtil.getAgeYear(uservo.getPatientBirthday()));
//                }else{
//                    uservo.setPatientAge(idNos.get("age").toString());
//                }
//            }
            uservo.setPatientAge(vo.getPatientAge());
            uservo.setPatientGender(vo.getPatientGender());
            uservo.setPatientName(vo.getPatientName());
            uservo.setPatientjmda(vo.getPatientjmda());
            uservo.setPatientjtda(vo.getPatientjtda());
            uservo.setPatientTel(vo.getPatientTel());
            uservo.setPatientCard(vo.getPatientCard());
            uservo.setPatientIdno(vo.getPatientIdno());

//            if(idNos.get("sex") != null) {
//                uservo.setPatientGender(idNos.get("sex").toString());
//            }
            this.sysDao.getServiceDo().modify(uservo);
        }else{
            uservo = new AppPatientUser();
            // -新增用户-
            uservo.setPatientName(vo.getPatientName());
            uservo.setPatientIdno(vo.getPatientIdno());
            uservo.setPatientCard(vo.getPatientCard());
            uservo.setPatientjmda(vo.getPatientjmda());
            uservo.setPatientjtda(vo.getPatientjtda());
            uservo.setPatientAge(vo.getPatientAge());
            uservo.setPatientTel(vo.getPatientTel());
            uservo.setPatientGender(vo.getPatientGender());
            //shi
            CardUtil Util= new CardUtil();
            boolean flag= Util.isDigit(vo.getPatientCity());
            if(flag!=true){
                uservo.setPatientProvince(areaCode);
                if(StringUtils.isNotBlank(vo.getPatientCity()) && "14".equals(AreaUtils.getAreaCode(vo.getPatientCity(),"1"))){
                    uservo.setPatientProvince(AreaUtils.getAreaCode(vo.getPatientCity(),"1")+"0000000000");
                }
                String city=findCity(vo.getPatientCity(),"");
                if(city!=null){
                    //qu xian
                    uservo.setPatientCity(city);
                    String area=findCity(vo.getPatientArea(),city);
                    if(area!=null){
                        uservo.setPatientArea(area);
                        //jie dao
                        String Street=findCity(vo.getPatientStreet(),area);
                        if(Street!=null){
                            uservo.setPatientStreet(Street);
                        }
                    }
                }
            }else{
                uservo.setPatientProvince(areaCode);
                //如果是山西
                if(StringUtils.isNotBlank(vo.getPatientCity()) &&"14".equals(AreaUtils.getAreaCode(vo.getPatientCity(),"1"))){
                    uservo.setPatientProvince(AreaUtils.getAreaCode(vo.getPatientCity(),"1")+"0000000000");
                }
//                uservo.setPatientProvince(areaCode);
                uservo.setPatientCity(vo.getPatientCity());
                uservo.setPatientArea(vo.getPatientArea());
                uservo.setPatientStreet(vo.getPatientStreet());
                uservo.setPatientNeighborhoodCommittee(vo.getPatientNeighborhoodCommittee());
            }
            if(StringUtils.isNotBlank(uservo.getPatientName())) {
                uservo.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(uservo.getPatientName()));
            }
            Map<String,Object> idNos = new HashMap<String,Object>();
            if(vo.getPatientIdno().trim().length() == 18){
                idNos = CardUtil.getCarInfo(uservo.getPatientIdno().trim());
            }else if(vo.getPatientIdno().trim().length() == 15){
                idNos = CardUtil.getCarInfo15W(uservo.getPatientIdno().trim());
            }
            if(idNos.get("birthday") != null) {
                uservo.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
            }
            if(idNos.get("age") != null){
                if(uservo.getPatientBirthday() != null) {
                    uservo.setPatientAge( AgeUtil.getAgeYear(uservo.getPatientBirthday()));
                }else{
                    uservo.setPatientAge(idNos.get("age").toString());
                }

            }
            if(idNos.get("sex") != null) {
                uservo.setPatientGender(idNos.get("sex").toString());
            }

            uservo.setPatientAddress(vo.getPatientAddress());
            uservo.setPatientUpHpis(UserUpHpisType.WEIJIHUO.getValue());//用户未激活
            uservo.setPatientState(CommonEnable.QIYONG.getValue());
            sysDao.getServiceDo().add(uservo);
        }
        AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,vo.getHospId());
        //签约单
        AppSignForm signvo = new AppSignForm();
        //批次
        AppSignBatch batch=new AppSignBatch();


        //-批次表-

        if(StringUtils.isNotBlank(uservo.getPatientCity())) {
            AppSerial serial = sysDao.getAppSignformDao().getAppSerial(uservo.getPatientCity().substring(0, 4), "batch");
            if(serial!=null) {
                Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.WEBSTATE.getValue());
                serial.setSerialNo(bcnum.get("old").toString());
                sysDao.getServiceDo().modify(serial);
                batch.setBatchNum(bcnum.get("new").toString());//批次号
            }
        }
        batch.setBatchPatientName(vo.getPatientName());
        batch.setBatchCreatePersid(uservo.getId());
        batch.setBatchTeamId(vo.getTeamId());
        batch.setBatchTeamName(vo.getTeamName());
        batch.setBatchCreateDate(Calendar.getInstance());
        batch.setBatchOperatorId(vo.getBatchOperatorId());
        batch.setBatchOperatorName(vo.getBatchOperatorName());
        batch.setBatchAreaCode(dept.getHospAreaCode());
        sysDao.getServiceDo().add(batch);

        // - 新增签约单-

        signvo.setSignState(vo.getSignWebState());
        signvo.setSignDrId(drid);
        signvo.setSignBatchId(batch.getId());
        signvo.setSignHospId(vo.getHospId());
        signvo.setSignTeamId(vo.getTeamId());
        AppTeam team= (AppTeam)sysDao.getServiceDo().find(AppTeam.class, vo.getTeamId());
        signvo.setSignTeamName(team.getTeamName());
        signvo.setSignDrAssistantId(assid);
        signvo.setSignDate(Calendar.getInstance());
        signvo.setSignPatientId(uservo.getId());
        signvo.setSignPatientAge(Integer.parseInt(vo.getPatientAge()));
        signvo.setSignPatientGender(uservo.getPatientGender());
        signvo.setSignPatientIdNo(uservo.getPatientIdno());

        Calendar fromC = Calendar.getInstance();
        fromC.set(Calendar.YEAR, vo.getSignFromDate().get(Calendar.YEAR));
        fromC.set(Calendar.MONTH, vo.getSignFromDate().get(Calendar.MONTH));
        fromC.set(Calendar.DAY_OF_MONTH, vo.getSignFromDate().get(Calendar.DAY_OF_MONTH));
        signvo.setSignFromDate(fromC);
        Calendar toC = Calendar.getInstance();
        toC.set(Calendar.YEAR, vo.getSignToDate().get(Calendar.YEAR));
        toC.set(Calendar.MONTH, vo.getSignToDate().get(Calendar.MONTH));
        toC.set(Calendar.DAY_OF_MONTH, vo.getSignToDate().get(Calendar.DAY_OF_MONTH));
        signvo.setSignToDate(toC);

        signvo.setSignPayState("0");//支付状态
        signvo.setSignType("1");//签约类型
        signvo.setUpHpis("2");//签约来源
        signvo.setSignWay("2");//医生代签
        signvo.setSignAreaCode(dept.getHospAreaCode());
        // 原经济类型
        // signvo.setSignsJjType(vo.getSignsJjType());
        signvo.setSignczpay(vo.getSignczpay());
        signvo.setSignzfpay(vo.getSignzfpay());
        signvo.setSignlx(vo.getSignlx());
        signvo.setSignContractState("0");//1是 0否
        signvo.setSignGreenState("0");//1是 0否
        signvo.setSignYellowState("0");//1是 0否
        signvo.setSignRedState("0");//1是 0否
        signvo.setSignHealthGroup("199");//健康分布
        AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(uservo.getPatientCity().substring(0, 4), "sign");
        if (serialSign != null) {
            Map<String,Object> sinum = sysDao.getAppSignformDao().getNum(serialSign.getSerialNo(),SignFormType.WEBSTATE.getValue());
            serialSign.setSerialNo(sinum.get("old").toString());
            sysDao.getServiceDo().modify(serialSign);
            signvo.setSignNum(sinum.get("new").toString());//签约编码
        }
        if(vo.getSignpackageid()!=null) {
            System.out.println("");
            if (vo.getSignpackageid().length > 0) {
                String Signpackageid = "";
                for (int i = 0; i < vo.getSignpackageid().length; i++) {
                    if (i == 0) {
                        Signpackageid = vo.getSignpackageid()[i];
                    } else {
                        Signpackageid = Signpackageid + ";" + vo.getSignpackageid()[i];
                    }
                }
                signvo.setSignpackageid(Signpackageid);
            }
        }
        signvo.setSigntext(vo.getSigntext());
        sysDao.getServiceDo().add(signvo);

        if(StringUtils.isNotBlank(dept.getHospAreaCode())){
            /**
             * 莆田增加签约信息是建档立卡用户是、往app_sign_jdlk_log插数据
             * WangCheng
             */
            String hostCode = dept.getHospAreaCode().substring(0,4);
            //Arrays.asList(vo.getsJjType()).contains("2")作用是检查数组中是否有包含2
            if("3503".equals(hostCode) && Arrays.asList(vo.getsJjType()).contains("2")){
                AppSignJdlkLog appSignJdlkLog = new AppSignJdlkLog();
                appSignJdlkLog.setSignFormId(signvo.getId());
                appSignJdlkLog.setSignCreater(vo.getBatchOperatorName());
                sysDao.getServiceDo().add(appSignJdlkLog);

            }
            if(Arrays.asList(vo.getsJjType()).contains("2")){
                /**
                 * 修改建档立卡人群签约状态
                 * WangCheng
                 */
                sysDao.getAppSignformDao().addOrModifyArchivingSign(uservo.getPatientIdno(),signvo.getId(),signvo.getSignDrId(),signvo.getSignTeamId(),signvo.getSignState(),vo.getPersGroup(),signvo.getSignAreaCode(),signvo.getSignHospId(),signvo.getSignDate(),uservo);
            }

             /*
             *cjw
             * 福州 调用医保接口 重视一下
             * 查询 cd_code
             * CodeState  1 为 与医保对接状态 0 为不对接
             */
            String hospcode = dept.getHospAreaCode().substring(0, 4);
            CdCode code = sysDao.getCodeDao().findSign(hospcode);
            if(code != null && code.getCodeState().equals("1")){
                if(code.getCodeValue().equals("3501")){
                    if(!Arrays.asList(vo.getSignpackageid()).contains("fb4c94c1-9342-4aae-a5db-8646ad061f07")){//如果有惠普服務包的話就不跟醫保交互
                        bytype = SignYiBao(vo,signvo);
                        if(StringUtils.isNotBlank(bytype)){
                            if("800" != bytype){
                                throw  new Exception(bytype);
                        /*wvo.setCode("900");
                        wvo.setMsg(bytype);
                        return wvo;*/
                            }
                        }else{
                            throw  new Exception("缴费登记失败");
                        }
                    }
                }
            }
        }

        CopyUtils.Copy(signvo, wvo);
        wvo.setCode("800");
        //-新增服务类型 -
        //改服务人群为多选
      //  if(vo.getPersGroup().length>0){
           // String[] groups = vo.getSignPersGroup().split(",");
            if (vo.getPersGroup() != null && vo.getPersGroup().length > 0) {
               // signvo.setSignPersGroup(groups[0]);//支持之前版
                sysDao.getAppLabelGroupDao().addLabel(vo.getPersGroup(),signvo.getId(),signvo.getSignTeamId(),signvo.getSignAreaCode(),LabelManageType.FWRQ.getValue());
            }
       // }
        //经济类型
            if (vo.getsJjType() != null && vo.getsJjType().length > 0) {
                sysDao.getAppLabelGroupDao().addLabel(vo.getsJjType(),signvo.getId(),signvo.getSignTeamId(),signvo.getSignAreaCode(), LabelManageType.JJLX.getValue());
            }

        //web添加疾病标签（现只添加高血压和糖尿病默认灰标）
        if(vo.getPersGroup() != null && vo.getPersGroup().length>0){//数组
            String disLabel = "";
           String select="";
           String Tselect="";
           String Jselect="";
           String Bselect="";
            if(vo.getLableState()!=""&&vo.getLableState()!=null){
                if(vo.getLableState().equals("1")&&vo.getPersonLable()!=null){ //判断是否拥有添加疾病标签权限（1为有权限）
                      String[] strGroup=vo.getPersonLable().split(";");
                    if(strGroup!=null&&strGroup.length>0){//判断是否同时选中高血压及糖尿病的疾病标签和疾病类型
                        for(String vv:vo.getPersGroup()){
                            if(ResidentMangeType.GXY.getValue().equals(vv)){ //高血压
                                for(String vv1:strGroup){
                                    if(DiseaseType.GXY.getValue().equals(vv1.substring(0,3))){
                                        select="GXY";
                                        break;
                                    }else{
                                        select="NGXY";
                                    }
                                }
                            }
                            if(ResidentMangeType.TNB.getValue().equals(vv)){//糖尿病
                                for(String vv1:strGroup){
                                    if(DiseaseType.TNB.getValue().equals(vv1.substring(0,3))){
                                        Tselect="TNB";
                                        break;
                                    }else{
                                        Tselect="NTNB";
                                    }
                                }
                            }

                            if(ResidentMangeType.JHB.getValue().equals(vv)){//结核病
                                for(String vv1:strGroup){
                                    if(DiseaseType.JHB.getValue().equals(vv1.substring(0,3))){
                                        Jselect="JHB";
                                        break;
                                    }else{
                                        Jselect="NJHB";
                                    }
                                }
                            }

                            if(ResidentMangeType.YZJSZY.getValue().equals(vv)){//严重精神障碍
                                for(String vv1:strGroup){
                                    if(DiseaseType.YZJSZA.getValue().equals(vv1.substring(0,3))){
                                        Bselect="JSB";
                                        break;
                                    }else{
                                        Bselect="NJSB";
                                    }
                                }
                            }
                        }
                        disLabel=vo.getPersonLable();
                        if(select.equals("NGXY")){    //选中高血压服务人群，没有选中高血压疾病标签，默认灰标
                            disLabel=disLabel+ ";"+DiseaseType.GXY.getValue()+"|gray";
                        }
                        if(Tselect.equals("NTNB")){  //选中糖尿病服务人群，没有选中糖尿病疾病标签，默认灰标
                            disLabel=disLabel+ ";"+DiseaseType.TNB.getValue()+"|gray";
                        }
                        if(Jselect.equals("NJHB")){  //选中结核病服务人群，没有选中结核病疾病标签，默认灰标
                            disLabel=disLabel+ ";"+DiseaseType.JHB.getValue()+"|gray";
                        }
                        if(Bselect.equals("NJSB")){  //选中精神病服务人群，没有选中精神病疾病标签，默认灰标
                            disLabel=disLabel+ ";"+DiseaseType.YZJSZA.getValue()+"|gray";
                        }

                    }
                }else{
                    for(String vv:vo.getPersGroup()){
                        if(ResidentMangeType.GXY.getValue().equals(vv)){//高血压
                            if(StringUtils.isBlank(disLabel)){
                                disLabel = DiseaseType.GXY.getValue()+"|gray";
                            }else{
                                disLabel += ";"+DiseaseType.GXY.getValue()+"|gray";
                            }
                        }else if(ResidentMangeType.TNB.getValue().equals(vv)){//糖尿病

                            if(StringUtils.isBlank(disLabel)){
                                disLabel = DiseaseType.TNB.getValue()+"|gray";
                            }else{
                                disLabel += ";"+DiseaseType.TNB.getValue()+"|gray";
                            }
                        }
                    }
                }
            }

            if(StringUtils.isNotBlank(disLabel)){
                String[] disLabels = disLabel.split(";");
                sysDao.getAppLabelGroupDao().addLabel(disLabels,signvo.getId(),signvo.getSignTeamId(),signvo.getSignAreaCode(), LabelManageType.JBLX.getValue());
            }
        }

        //初始化医生,患者,环信账号,添加群聊
//        AppSignControl control = new AppSignControl();
//        control.setSignDrId(signvo.getSignDrId());
//        control.setSignId(signvo.getId());
//        control.setSignPatientId(signvo.getSignPatientId());
//        control.setSignState(UserUpHpisType.WEIJIHUO.getValue());
//        control.setSignTeamId(signvo.getSignTeamId());
//        sysDao.getServiceDo().add(control);

       /* for(int i=0;i<vo.getPersGroup().length;i++){
            //服务类型
            AppLabelGroup groupvo = new AppLabelGroup();
            groupvo.setLabelSignId(signvo.getId());
            groupvo.setLabelTeamId(vo.getTeamId());
            groupvo.setLabelValue(vo.getPersGroup()[i]);
            sysDao.getServiceDo().add(groupvo);
        }*/
        return wvo;
    }

    public AppSignForm websignmodify(WebSignVo vo)throws Exception
    {
        AppSignForm signvo = (AppSignForm)sysDao.getServiceDo().find(AppSignForm.class,vo.getSignId());
        //System.out.println("pid-----------------------:"+vo.getPatientId());
        AppPatientUser uservo =(AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,vo.getPatientId());

        uservo.setPatientjtda(vo.getPatientjtda());
        uservo.setPatientjmda(vo.getPatientjmda());
        uservo.setPatientAge(vo.getPatientAge());
        uservo.setPatientTel(vo.getPatientTel());
        uservo.setPatientName(vo.getPatientName());
        //社保卡
        uservo.setPatientCard(vo.getPatientCard());
        CardUtil Util= new CardUtil();
        boolean flag= Util.isDigit(vo.getPatientCity());
        if(flag!=true){
            String city=findCity(vo.getPatientCity(),"");
            if(city!=null){
                //qu xian
                uservo.setPatientCity(city);
                String area=findCity(vo.getPatientArea(),city);
                if(area!=null){
                    uservo.setPatientArea(area);
                    //jie dao
                    String Street=findCity(vo.getPatientStreet(),area);
                    if(Street!=null){
                        uservo.setPatientStreet(Street);
                    }
                }
            }
        }else{
            uservo.setPatientCity(vo.getPatientCity());
            uservo.setPatientArea(vo.getPatientArea());
            uservo.setPatientStreet(vo.getPatientStreet());
            uservo.setPatientNeighborhoodCommittee(vo.getPatientNeighborhoodCommittee());
        }
        uservo.setPatientAddress(vo.getPatientAddress());
        sysDao.getServiceDo().modify(uservo);
        String signAreaCode = signvo.getSignAreaCode().substring(0,4);
        //获取配置表查询是否有修改协议权限
        AppSignSetting setting = sysDao.getAppSignformDao().findsignSetting(signAreaCode);
        if (setting != null) {
           if(setting.getSerSignModifyState().equals("1")){
               signvo.setSignDate(vo.getSignFromDate());
               signvo.setSignFromDate(vo.getSignFromDate());
               signvo.setSignToDate(vo.getSignToDate());
           }
        }

        signvo.setSignsJjType(vo.getSignsJjType());
        signvo.setSignlx(vo.getSignlx());
        signvo.setSigntext(vo.getSigntext());
        //如果带进来的财政补贴是空的就不对财政补贴字段做处理、add by WangCheng
        if(StringUtils.isNotEmpty(vo.getSignczpay())){
            signvo.setSignczpay(vo.getSignczpay());
        }
        signvo.setSignzfpay(vo.getSignzfpay());
        if(vo.getSignpackageid()!=null) {
            if(vo.getSignpackageid().length > 0) {
                String Signpackageid = "";
                for (int i = 0; i < vo.getSignpackageid().length; i++) {
                    if (i == 0) {
                        Signpackageid = vo.getSignpackageid()[i];
                    } else {
                        Signpackageid = Signpackageid + ";" + vo.getSignpackageid()[i];
                    }
                }
                signvo.setSignpackageid(Signpackageid);
            }else {
                signvo.setSignpackageid(null);
            }

        }else {
            signvo.setSignpackageid(null);
        }
        sysDao.getServiceDo().modify(signvo);

        /**
         * 莆田增加签约信息是建档立卡用户是、往app_sign_jdlk_log插数据
         * WangCheng
         */

        //Arrays.asList(vo.getsJjType()).contains("2")作用是检查数组中是否有包含2
        if("3503".equals(signAreaCode) && Arrays.asList(vo.getsJjType()).contains("2")){
            AppSignJdlkLog appSignJdlkLog = new AppSignJdlkLog();
            appSignJdlkLog.setSignFormId(vo.getSignId());
            appSignJdlkLog.setSignModifier(vo.getBatchOperatorName());
            sysDao.getServiceDo().add(appSignJdlkLog);


        }





//        //删除原先服务类型
//        List<AppLabelGroup> gvo =sysDao.getAppLabelGroupDao().findBySignGroup(vo.getSignId(),"3");
//        if(gvo != null && gvo.size() >0){
//            for(int i=0;i<gvo.size();i++){
//                sysDao.getServiceDo().delete(gvo.get(i));
//            }
//        }
        // 服务人群
        if(vo.getPersGroup()!=null && vo.getPersGroup().length>0){
            sysDao.getAppLabelGroupDao().addLabel(vo.getPersGroup(),signvo.getId(),signvo.getSignTeamId(),signvo.getSignAreaCode(),LabelManageType.FWRQ.getValue());
        }
        // 经济人口
        if(vo.getsJjType()!=null && vo.getsJjType().length>0){
            sysDao.getAppLabelGroupDao().addLabel(vo.getsJjType(),signvo.getId(),signvo.getSignTeamId(),signvo.getSignAreaCode(),LabelManageType.JJLX.getValue());
        }
        if(Arrays.asList(vo.getsJjType()).contains("2")){

            sysDao.getAppSignformDao().addOrModifyArchivingSign(uservo.getPatientIdno(),signvo.getId(),signvo.getSignDrId(),signvo.getSignTeamId(),signvo.getSignState(),vo.getPersGroup(),signvo.getSignAreaCode(),signvo.getSignHospId(),signvo.getSignDate(),uservo);
        }
        return signvo;
    }


    public  String findCity(String name , String code) throws Exception {
        Map<String ,Object> map=new HashedMap();
        if(StringUtils.isNoneBlank(name)){
            String sql=" SELECT c.AREA_CODE from cp_city_area_people c where c.AREA_SNAME like '%"+name+"%'  ";
            if(StringUtils.isNoneBlank(code)){
                sql+=" and c.UP_AREA_ID='"+code+"'";
            }
           return (String) this.sysDao.getServiceDo().findSqlObject(sql,map);
        }
        return null;
    }

    public List<AppSignInfoAllVo> findAllSign() throws Exception{
        Map<String ,Object> map=new HashedMap();
        String sql="SELECT\n" +
                "\tu.PATIENT_NAME name,\n" +
                "\tu.PATIENT_IDNO patientIdno,\n" +
                "\tu.PATIENT_CARD patientCard,\n" +
                "\thf.PT_NUMBER signNum,\n" +
                "\thf.PT_SCC_NO ptsscno,\n" +
                "\ta.SIGN_DATE signDate,\n" +
                "\ta.SIGN_FROM_DATE signFromDate,\n" +
                "\ta.SIGN_TO_DATE signToDate,\n" +
                "\tb.BATCH_OPERATOR_NAME operatorName \n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a \n" +
                "LEFT JOIN APP_HFS_SIGN_SSC hf ON a.SIGN_PATIENT_IDNO = hf.PT_ID_NO \n" +
                "LEFT JOIN APP_PATIENT_USER u ON a.SIGN_PATIENT_ID = u.ID \n" +
                "LEFT JOIN APP_SIGN_BATCH b ON a.SIGN_BATCH_ID = b.ID \n" +
                "WHERE\n" +
                "\ta.SIGN_STATE = '0' \n" +
                "AND a.SIGN_LX = '2' \n" +
                "AND hf.PT_NUMBER IS NOT NULL\n" +
                "AND hf.PT_SCC_NO IS NOT NULL\n" +
                "AND u.PATIENT_IDNO IS NOT NULL ";
        List<AppSignInfoAllVo> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppSignInfoAllVo.class);
        return list;
    }

    public AppHfsSignSsc findHfsSignSscByPtIdNo(String ptIdNo) throws Exception{
        Map<String ,Object> map=new HashedMap();
        if(StringUtils.isNotBlank(ptIdNo)){
            String sql = "SELECT * FROM APP_HFS_SIGN_SSC WHERE PT_ID_NO =:ptIdNo AND PT_LX = '3' ";
                    map.put("ptIdNo",ptIdNo);
            List<AppHfsSignSsc> ssc = sysDao.getServiceDo().findSqlMap(sql,map,AppHfsSignSsc.class);
            if(ssc!=null && ssc.size()>0){
                return ssc.get(0);
            }
        }

        return null ;
    }

    /**
     * 签约编码初始化（莆田 适用于各地市）
     */

    /*
    public void testNo(){
        List<AppSignForm> ls=sysDao.getServiceDo().findSqlMap("select * from APP_SIGN_FORM a order by SIGN_DATE asc",null,AppSignForm.class);
        for(AppSignForm l:ls){
            if(StringUtils.isNotBlank(l.getSignAreaCode())){
                AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(l.getSignAreaCode().substring(0, 4), "sign");
                if (serialSign != null) {
                    String sinum =getNum(serialSign.getSerialNo(),l.getSignDate());
                    serialSign.setSerialNo(sinum);
                    sysDao.getServiceDo().modify(serialSign);
                    l.setSignNum(sinum);//签约编码
                    l.setHsUpdateTime(Calendar.getInstance());
                    sysDao.getServiceDo().modify(l);
                }
            }
        }
        *//*List<AppSignBatch> gg=sysDao.getServiceDo().findSqlMap("select * from APP_SIGN_BATCH a order by BATCH_CREATE_DATE asc",null,AppSignForm.class);
        for(AppSignBatch l:gg){
            AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial("3503", "sign");
            if (serialSign != null) {
                String sinum =getNum(serialSign.getSerialNo(),l.getBatchCreateDate());
                serialSign.setSerialNo(sinum);
                sysDao.getServiceDo().modify(serialSign);
                l.setBatchNum(sinum);//签约编码
                sysDao.getServiceDo().modify(l);
            }
        }*//*
    }
    public String getNum(String s,Calendar cal) {
        if (ExtendDate.getYYYYMMD(cal).equals(s.substring(1, 9))) {
            int i = Integer.valueOf(s.substring(9)) + 1;
            String y = String.valueOf(i);
            while (y.length() < 6) {
                y = "0" + y;
            }
            return s.substring(0, 1) + ExtendDate.getYYYYMMD(cal) + y;
        } else {
            return s.substring(0, 1) + ExtendDate.getYYYYMMD(cal) + "000001";
        }
    }
   */


    /**
     *  cjw 家庭版 签约
     */
    /**
     * Dmao
     * web签约保存（新）
     * @param vo
     * @return
     * @throws Exception
     */
    public AppSignForm webFamilysignAdd(WebFamilySignVo vo)throws  Exception
    {
        String drid = vo.getDrId().split(";;;")[0];
        String assid = null;
        if(StringUtils.isNoneBlank(vo.getSignDrAssistantId())){
            assid = vo.getSignDrAssistantId().split(";;;")[0];
        }
        List<AppMyFamily> fvo = new ArrayList<AppMyFamily>();
        String householderId =""; // 户主id
        String householderName = ""; // 户主名称
        //居民用户表
        for(int i=0;i<vo.getFamilyVo().size();i++){

            AppPatientUser uservo = null;
            AppMyFamily familyvo = null;
            List<AppPatientUser> puser= sysDao.getServiceDo().loadByPk(AppPatientUser.class,"patientIdno",vo.getFamilyVo().get(i).getPatientIdno());
            if(puser!=null && !puser.isEmpty()){
                uservo = puser.get(0);
                uservo.setPatientProvince(areaCode);
                uservo.setPatientCity(vo.getPatientCity());
                uservo.setPatientArea(vo.getPatientArea());
                uservo.setPatientStreet(vo.getPatientStreet());
                uservo.setPatientNeighborhoodCommittee(vo.getPatientNeighborhoodCommittee());
                uservo.setPatientAddress(vo.getPatientAddress());
                if(StringUtils.isNotBlank(uservo.getPatientName())) {
                    uservo.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(uservo.getPatientName()));
                }
                Map<String,Object> idNos = new HashMap<String,Object>();
                if(vo.getFamilyVo().get(i).getPatientIdno().trim().length() == 18){
                    idNos = CardUtil.getCarInfo(uservo.getPatientIdno().trim());
                }else if(vo.getFamilyVo().get(i).getPatientIdno().trim().length() == 15){
                    idNos = CardUtil.getCarInfo15W(uservo.getPatientIdno().trim());
                }
                if(idNos.get("birthday") != null) {
                    uservo.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
                }
//            if(idNos.get("age") != null){
//                if(uservo.getPatientBirthday() != null) {
//                    uservo.setPatientAge( AgeUtil.getAgeYear(uservo.getPatientBirthday()));
//                }else{
//                    uservo.setPatientAge(idNos.get("age").toString());
//                }
//            }
                uservo.setPatientAge(vo.getFamilyVo().get(i).getPatientAge());
                uservo.setPatientGender(vo.getFamilyVo().get(i).getPatientGender());
                uservo.setPatientName(vo.getFamilyVo().get(i).getPatientName());
                uservo.setPatientjmda(vo.getFamilyVo().get(i).getPatientjmda());
                uservo.setPatientjtda(vo.getFamilyVo().get(i).getPatientjtda());
                uservo.setPatientTel(vo.getFamilyVo().get(i).getPatientTel());
                uservo.setPatientCard(vo.getFamilyVo().get(i).getPatientCard());
                uservo.setPatientIdno(vo.getFamilyVo().get(i).getPatientIdno());

//            if(idNos.get("sex") != null) {
//                uservo.setPatientGender(idNos.get("sex").toString());
//            }
                this.sysDao.getServiceDo().modify(uservo);
            }else{
                uservo = new AppPatientUser();
                // -新增用户-
                uservo.setPatientName(vo.getFamilyVo().get(i).getPatientName());
                uservo.setPatientIdno(vo.getFamilyVo().get(i).getPatientIdno());
                uservo.setPatientCard(vo.getFamilyVo().get(i).getPatientCard());
                uservo.setPatientjmda(vo.getFamilyVo().get(i).getPatientjmda());
                uservo.setPatientjtda(vo.getFamilyVo().get(i).getPatientjtda());
                uservo.setPatientAge(vo.getFamilyVo().get(i).getPatientAge());
                uservo.setPatientTel(vo.getFamilyVo().get(i).getPatientTel());
                uservo.setPatientGender(vo.getFamilyVo().get(i).getPatientGender());
                //shi
                CardUtil Util= new CardUtil();
                boolean flag= Util.isDigit(vo.getPatientCity());
                if(flag!=true){
                    uservo.setPatientProvince(areaCode);
                    String city=findCity(vo.getPatientCity(),"");
                    if(city!=null){
                        //qu xian
                        uservo.setPatientCity(city);
                        String area=findCity(vo.getPatientArea(),city);
                        if(area!=null){
                            uservo.setPatientArea(area);
                            //jie dao
                            String Street=findCity(vo.getPatientStreet(),area);
                            if(Street!=null){
                                uservo.setPatientStreet(Street);
                            }
                        }
                    }
                }else{
                    uservo.setPatientProvince(areaCode);
                    uservo.setPatientCity(vo.getPatientCity());
                    uservo.setPatientArea(vo.getPatientArea());
                    uservo.setPatientStreet(vo.getPatientStreet());
                    uservo.setPatientNeighborhoodCommittee(vo.getPatientNeighborhoodCommittee());
                }
                if(StringUtils.isNotBlank(uservo.getPatientName())) {
                    uservo.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(uservo.getPatientName()));
                }
                Map<String,Object> idNos = new HashMap<String,Object>();
                if(vo.getFamilyVo().get(i).getPatientIdno().trim().length() == 18){
                    idNos = CardUtil.getCarInfo(uservo.getPatientIdno().trim());
                }else if(vo.getFamilyVo().get(i).getPatientIdno().trim().length() == 15){
                    idNos = CardUtil.getCarInfo15W(uservo.getPatientIdno().trim());
                }
                if(idNos.get("birthday") != null) {
                    uservo.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
                }
                if(idNos.get("age") != null){
                    if(uservo.getPatientBirthday() != null) {
                        uservo.setPatientAge( AgeUtil.getAgeYear(uservo.getPatientBirthday()));
                    }else{
                        uservo.setPatientAge(idNos.get("age").toString());
                    }

                }
                if(idNos.get("sex") != null) {
                    uservo.setPatientGender(idNos.get("sex").toString());
                }
                uservo.setPatientAddress(vo.getPatientAddress());
                uservo.setPatientUpHpis(UserUpHpisType.WEIJIHUO.getValue());//用户未激活
                sysDao.getServiceDo().add(uservo);
            }
            vo.getFamilyVo().get(i).setPatientId(uservo.getId());
            // 户主
            if(vo.getFamilyVo().get(i).getMfFmNickName().equals("49")){
                householderId = uservo.getId();
                householderName = uservo.getPatientName();
            }
            // 家庭成员 表
            familyvo  = new AppMyFamily();
            familyvo.setMfFmPatientId(uservo.getId());
            familyvo.setMfFmIdno(uservo.getPatientIdno());
            familyvo.setMfFmCard(uservo.getPatientCard());
            familyvo.setMfFmAge(uservo.getPatientAge());
            familyvo.setMfFMGender(uservo.getPatientGender());
            familyvo.setMfFmName(uservo.getPatientName());
            familyvo.setMfFmBirthday(uservo.getPatientBirthday());
            familyvo.setMfFmState(CommonEnable.JINYONG.getValue());
            familyvo.setMfFmNickName(vo.getFamilyVo().get(i).getMfFmNickName());
            fvo.add(familyvo);
        }
        // 家庭表 循环
        if(fvo != null && fvo.size()>0){
            for(int j = 0;j<fvo.size();j++){
                fvo.get(j).setMyPatientId(householderId);
                sysDao.getServiceDo().add(fvo.get(j));
            }
        }




        //签约单
        AppSignForm signvo = null;
        //批次
        AppSignBatch batch=new AppSignBatch();
        //-批次表-
        AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,vo.getHospId());
        if(StringUtils.isNotBlank(dept.getHospAreaCode())) {
            AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
            if(serial!=null) {
                Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.WEBSTATE.getValue());
                serial.setSerialNo(bcnum.get("old").toString());
                sysDao.getServiceDo().modify(serial);
                batch.setBatchNum(bcnum.get("new").toString());//批次号
            }
        }
        batch.setBatchPatientName(householderName); //户主
        batch.setBatchCreatePersid(householderId);
        batch.setBatchTeamId(vo.getTeamId());
        batch.setBatchTeamName(vo.getTeamName());
        batch.setBatchCreateDate(Calendar.getInstance());
        batch.setBatchOperatorId(vo.getBatchOperatorId());
        batch.setBatchOperatorName(vo.getBatchOperatorName());
        batch.setBatchAreaCode(dept.getHospAreaCode());
        sysDao.getServiceDo().add(batch);

        // - 新增签约单-
        for(int f = 0; f<vo.getFamilyVo().size();f++){
             signvo = new AppSignForm();
            signvo.setSignState(vo.getSignWebState());
            signvo.setSignDrId(drid);
            signvo.setSignBatchId(batch.getId());
            signvo.setSignHospId(vo.getHospId());
            signvo.setSignTeamId(vo.getTeamId());
            AppTeam team= (AppTeam)sysDao.getServiceDo().find(AppTeam.class, vo.getTeamId());
            signvo.setSignTeamName(team.getTeamName());
            signvo.setSignDrAssistantId(assid);
            signvo.setSignDate(Calendar.getInstance());
            signvo.setSignPatientId(vo.getFamilyVo().get(f).getPatientId());
            signvo.setSignPatientAge(Integer.parseInt(vo.getFamilyVo().get(f).getPatientAge()));
            signvo.setSignPatientGender(vo.getFamilyVo().get(f).getPatientGender());
            signvo.setSignPatientIdNo(vo.getFamilyVo().get(f).getPatientIdno());
       /* SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date dateform =sdf.parse(vo.getFormDate());
        c.setTime(dateform);*/
            signvo.setSignFromDate(vo.getSignFromDate());
       /* Date dateto =sdf.parse(vo.getToDate());
        c.setTime(dateto);*/
            signvo.setSignToDate(vo.getSignToDate());
            signvo.setSignPayState("0");//支付状态
            signvo.setSignType("1");//签约类型
            signvo.setUpHpis("2");//签约来源
            signvo.setSignWay("2");//医生代签
            signvo.setSignAreaCode(dept.getHospAreaCode());
            // 原经济类型
            // signvo.setSignsJjType(vo.getSignsJjType());
            signvo.setSignczpay(vo.getFamilyVo().get(f).getSignczpay());
            signvo.setSignzfpay(vo.getFamilyVo().get(f).getSignzfpay());
           // signvo.setSignlx(vo.getSignlx()); 无
            signvo.setSignContractState("0");//1是 0否
            signvo.setSignGreenState("0");//1是 0否
            signvo.setSignYellowState("0");//1是 0否
            signvo.setSignRedState("0");//1是 0否
            signvo.setSignHealthGroup("199");//健康分布
            AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(vo.getPatientCity().substring(0, 4), "sign");
            if (serialSign != null) {
                Map<String,Object> sinum = sysDao.getAppSignformDao().getNum(serialSign.getSerialNo(),SignFormType.WEBSTATE.getValue());
                serialSign.setSerialNo(sinum.get("old").toString());
                sysDao.getServiceDo().modify(serialSign);
                signvo.setSignNum(sinum.get("new").toString());//签约编码
            }
            if(vo.getFamilyVo().get(f).getSignpackageid()!=null) {
                System.out.println("");
                if (vo.getFamilyVo().get(f).getSignpackageid().length > 0) {
                    String Signpackageid = "";
                    for (int i = 0; i < vo.getFamilyVo().get(f).getSignpackageid().length; i++) {
                        if (i == 0) {
                            Signpackageid = vo.getFamilyVo().get(f).getSignpackageid()[i];
                        } else {
                            Signpackageid = Signpackageid + ";" + vo.getFamilyVo().get(f).getSignpackageid()[i];
                        }
                    }
                    signvo.setSignpackageid(Signpackageid);
                }
            }
            signvo.setSigntext(vo.getFamilyVo().get(f).getSigntext());
            sysDao.getServiceDo().add(signvo);

            //-新增服务类型 -
            //改服务人群为多选
            //  if(vo.getPersGroup().length>0){
            // String[] groups = vo.getSignPersGroup().split(",");
            if (vo.getFamilyVo().get(f).getPersGroup() != null && vo.getFamilyVo().get(f).getPersGroup().length > 0) {
                // signvo.setSignPersGroup(groups[0]);//支持之前版
                sysDao.getAppLabelGroupDao().addLabel(vo.getFamilyVo().get(f).getPersGroup(),signvo.getId(),signvo.getSignTeamId(),signvo.getSignAreaCode(),LabelManageType.FWRQ.getValue());

                //添加疾病标签（高血压和糖尿病）
                String[] disLabels = vo.getFamilyVo().get(f).getPersGroup();
                for(String disLabel:disLabels){
                    if(ResidentMangeType.GXY.getValue().equals(disLabel)){//高血压添加高血压疾病标签
                        AppLabelManage labelManage = sysDao.getAppLabelManageDao().findLabelByValue("2","201");
                        AppLabelDisease disease = new AppLabelDisease();
                        disease.setLabelAreaCode(signvo.getSignAreaCode());
                        disease.setLabelValue(labelManage.getLabelValue());
                        disease.setLabelType(labelManage.getLabelType());
                        disease.setLabelTitle(labelManage.getLabelTitle());
                        disease.setLabelId(labelManage.getId());
                        disease.setLabelSignId(signvo.getId());
                        disease.setLabelTeamId(signvo.getSignTeamId());
                        disease.setLabelColor("gray");
                        sysDao.getServiceDo().add(disease);
                    }else if(ResidentMangeType.TNB.getValue().equals(disLabel)){//糖尿病添加糖尿病标签
                        AppLabelManage labelManage = sysDao.getAppLabelManageDao().findLabelByValue("2","202");
                        AppLabelDisease disease = new AppLabelDisease();
                        disease.setLabelAreaCode(signvo.getSignAreaCode());
                        disease.setLabelValue(labelManage.getLabelValue());
                        disease.setLabelType(labelManage.getLabelType());
                        disease.setLabelTitle(labelManage.getLabelTitle());
                        disease.setLabelId(labelManage.getId());
                        disease.setLabelSignId(signvo.getId());
                        disease.setLabelTeamId(signvo.getSignTeamId());
                        disease.setLabelColor("gray");
                        sysDao.getServiceDo().add(disease);
                    }else if(ResidentMangeType.YZJSZY.getValue().equals(disLabel)){//精神病207
                        AppLabelManage labelManage = sysDao.getAppLabelManageDao().findLabelByValue("2","207");
                        AppLabelDisease disease = new AppLabelDisease();
                        disease.setLabelAreaCode(signvo.getSignAreaCode());
                        disease.setLabelValue(labelManage.getLabelValue());
                        disease.setLabelType(labelManage.getLabelType());
                        disease.setLabelTitle(labelManage.getLabelTitle());
                        disease.setLabelId(labelManage.getId());
                        disease.setLabelSignId(signvo.getId());
                        disease.setLabelTeamId(signvo.getSignTeamId());
                        disease.setLabelColor("gray");
                        sysDao.getServiceDo().add(disease);
                    }else if(ResidentMangeType.JHB.getValue().equals(disLabel)){//结核病
                        AppLabelManage labelManage = sysDao.getAppLabelManageDao().findLabelByValue("2","208");
                        AppLabelDisease disease = new AppLabelDisease();
                        disease.setLabelAreaCode(signvo.getSignAreaCode());
                        disease.setLabelValue(labelManage.getLabelValue());
                        disease.setLabelType(labelManage.getLabelType());
                        disease.setLabelTitle(labelManage.getLabelTitle());
                        disease.setLabelId(labelManage.getId());
                        disease.setLabelSignId(signvo.getId());
                        disease.setLabelTeamId(signvo.getSignTeamId());
                        disease.setLabelColor("gray");
                        sysDao.getServiceDo().add(disease);
                    }
                }
            }
            // }
            //经济类型
            if (vo.getsJjType() != null && vo.getsJjType().length > 0) {
                sysDao.getAppLabelGroupDao().addLabel(vo.getsJjType(),signvo.getId(),signvo.getSignTeamId(),signvo.getSignAreaCode(), LabelManageType.JJLX.getValue());
            }


            //初始化医生,患者,环信账号,添加群聊
//            AppSignControl control = new AppSignControl();
//            control.setSignDrId(signvo.getSignDrId());
//            control.setSignId(signvo.getId());
//            control.setSignPatientId(signvo.getSignPatientId());
//            control.setSignState(UserUpHpisType.WEIJIHUO.getValue());
//            control.setSignTeamId(signvo.getSignTeamId());
//            sysDao.getServiceDo().add(control);


        }



        return signvo;
    }

    /**
     *  虎纠
     * 签约--调医保接口
     * @return
     */
    public String  SignYiBao(WebSignVo svo,AppSignForm signvo) throws Exception{
            AppSignRegister regvo = new AppSignRegister();
            String bytype="";
            Map jsonParam = new HashMap();
            // 暂做福州
            String  address = PropertiesUtil.getConfValue("FZYBURL");
            if(StringUtils.isNotBlank(address)){
                SimpleDateFormat s=new SimpleDateFormat("yyyyMMddHHmmss");
                SimpleDateFormat from=new SimpleDateFormat("yyyyMMdd");
                Calendar c = Calendar.getInstance();
                Calendar strat = svo.getSignFromDate();
                Calendar end = svo.getSignToDate();
                String stratDate = from.format(strat.getTime());
                String endDate = from.format(end.getTime());
                String curDate = s.format(c.getTime());  //当前日期
                String lsh00=svo.getHospId()+curDate;//当前日期加机构ID
                jsonParam.put("userFlowNo",lsh00);//流水号
                jsonParam.put("userName",svo.getPatientName());
                jsonParam.put("userIdno",svo.getPatientIdno());
                jsonParam.put("userCard",svo.getPatientCard());
                jsonParam.put("stratDate",stratDate);
                jsonParam.put("endDate",endDate);
                //未用套餐 沿用旧版 暂写死  福州费用
                jsonParam.put("userSignMoney","120.00");
                if(svo.getSignlx().equals("1")){ //医保
                    jsonParam.put("signtype","1");
                    jsonParam.put("uuser",svo.getYuser());
                    jsonParam.put("paw",svo.getYpaw());
                }else if(svo.getSignlx().equals("2")){ //新农合
                    jsonParam.put("signtype","2");
                    jsonParam.put("uuser",svo.getXuser());
                    jsonParam.put("paw",svo.getXpaw());
                }else if(svo.getSignlx().equals("0")){ // 福州省保
                    jsonParam.put("signtype","0");
                    jsonParam.put("uuser",svo.getXuser());
                    jsonParam.put("paw",svo.getXpaw());

                }
                String  str=  HttpPostUtils.doPostJson(JacksonUtils.objToStr(jsonParam), address);
                if(str!=null){
                    JSONObject jsonAll = JSONObject.fromObject(str);
                    if("800".equals(jsonAll.get("msgCode"))) {
//                        AppSignForm fvo = sysDao.getAppSignformDao().findpatientIdNo(svo.getPatientIdno());
                        if(signvo!=null ){ // update 签约状态
                            signvo.setSignState("2");
                            signvo.setSignPayState("1");
                            sysDao.getServiceDo().modify(signvo);
                            ResultVo signPay= JsonUtil.fromJson(jsonAll.get("vo").toString(), ResultVo.class);
                            regvo.setRegisterNo(signPay.getFlowNo());
                            regvo.setRegisterAddress(signPay.getCbdw());
                            regvo.setRegisterFundCost(signPay.getSignMoney());
                            regvo.setRegisterIdno(signPay.getSfzh());
                            regvo.setRegisterPatientCard(signPay.getId00());
                            regvo.setRegisterPatientName(signPay.getName());
                            regvo.setRegisterPpublichealthCost(signPay.getGwzfe());
                            regvo.setRegisterFundCost(signPay.getJjzfe());
                            regvo.setRegisterPersonalCost(signPay.getGrzfe());
                            regvo.setRegisterCreatedate(Calendar.getInstance());
                            regvo.setRegisterPatientId(signvo.getSignPatientId());
                            sysDao.getServiceDo().add(regvo);
                            bytype="800";
                        }
                    }else{
                           bytype= jsonAll.get("msg").toString();
                    }
                }
            }

        return bytype;
    }

    public AppOutpatientNumber OutpatientNumberUpdate(AppOutpatientNumber vo)throws Exception
    {
        if(StringUtils.isNotBlank(vo.getOutpatientIdno())){
            AppOutpatientNumber nvo = OutpatientNumberfind(vo.getOutpatientIdno());
            nvo.setOutpatientHospLevelOne(vo.getOutpatientHospLevelOne());
            nvo.setOutpatientHospLevel_Tow(vo.getOutpatientHospLevel_Tow());
            nvo.setOutpatientHospLevel_Three(vo.getOutpatientHospLevel_Three());
            nvo.setOutpatientDoctorNumber(vo.getOutpatientDoctorNumber());
            nvo.setOutpatientFundCost(vo.getOutpatientFundCost());
            nvo.setOutpatientDoctorDate(vo.getOutpatientDoctorDate());
            sysDao.getServiceDo().modify(nvo);
        }
        return vo;
    }

    public AppOutpatientNumber OutpatientNumberfind (String outpatientIdno)throws Exception
    {
        Map<String ,Object> map=new HashedMap();
        String sql ="";
        if(StringUtils.isNotBlank(outpatientIdno)){
            sql = " SELECT * from APP_OUTPATIENT_NUMBER n where n.OUTPATIENT_IDNO =:outpatientidno ";
            map.put("outpatientidno",outpatientIdno);
            List<AppOutpatientNumber> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppOutpatientNumber.class);
            if(ls != null && ls.size()>0){
                return ls.get(0);
            }
        }
        return null;
    }

    public AppSignForm websignrefuse(AppSignForm vo)throws Exception
    {
        sysDao.getServiceDo().modify(vo);
        return vo;
    }

    public AppSignForm websignRenew(WebSignVo vo)throws Exception
    {
        //居民用户表
        AppPatientUser uservo = null;
        List<AppPatientUser> puser= sysDao.getServiceDo().loadByPk(AppPatientUser.class,"patientIdno",vo.getPatientIdno());
            uservo = puser.get(0);
            uservo.setPatientProvince(areaCode);
            uservo.setPatientCity(vo.getPatientCity());
            uservo.setPatientArea(vo.getPatientArea());
            uservo.setPatientStreet(vo.getPatientStreet());
            uservo.setPatientNeighborhoodCommittee(vo.getPatientNeighborhoodCommittee());
            uservo.setPatientAddress(vo.getPatientAddress());
            if(StringUtils.isNotBlank(uservo.getPatientName())) {
                uservo.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(uservo.getPatientName()));
            }
            Map<String,Object> idNos = new HashMap<String,Object>();
            if(vo.getPatientIdno().trim().length() == 18){
                idNos = CardUtil.getCarInfo(uservo.getPatientIdno().trim());
            }else if(vo.getPatientIdno().trim().length() == 15){
                idNos = CardUtil.getCarInfo15W(uservo.getPatientIdno().trim());
            }
            if(idNos.get("birthday") != null) {
                uservo.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
            }
            uservo.setPatientAge(vo.getPatientAge());
            uservo.setPatientGender(vo.getPatientGender());
            uservo.setPatientName(vo.getPatientName());
            uservo.setPatientjmda(vo.getPatientjmda());
            uservo.setPatientjtda(vo.getPatientjtda());
            uservo.setPatientTel(vo.getPatientTel());
            uservo.setPatientCard(vo.getPatientCard());
            uservo.setPatientIdno(vo.getPatientIdno());

            this.sysDao.getServiceDo().modify(uservo);
        //签约单
        AppSignForm signvo = new AppSignForm();

        AppSignFormEntity signform = new AppSignFormEntity();
        //批次
        AppSignBatch batch=new AppSignBatch();
        AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,vo.getHospId());
        if(StringUtils.isNotBlank(uservo.getPatientCity())) {
            AppSerial serial = sysDao.getAppSignformDao().getAppSerial(uservo.getPatientCity().substring(0, 4), "batch");
            if(serial!=null) {
                Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.WEBSTATE.getValue());
                serial.setSerialNo(bcnum.get("old").toString());
                sysDao.getServiceDo().modify(serial);
                batch.setBatchNum(bcnum.get("new").toString());//批次号
            }
        }
        batch.setBatchPatientName(vo.getPatientName());
        batch.setBatchCreatePersid(uservo.getId());
        batch.setBatchTeamId(vo.getTeamId());
        batch.setBatchTeamName(vo.getTeamName());
        batch.setBatchCreateDate(Calendar.getInstance());
        batch.setBatchOperatorId(vo.getBatchOperatorId());
        batch.setBatchOperatorName(vo.getBatchOperatorName());
        batch.setBatchAreaCode(dept.getHospAreaCode());
        //sysDao.getServiceDo().add(batch);

        AppSignForm form= (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,vo.getSignId());
        AppSignBatch batch1=(AppSignBatch)sysDao.getServiceDo().find(AppSignBatch.class,form.getSignBatchId());
        if(batch1!=null){
            if(vo.getTeamId()==null&&vo.getTeamName()==null){
                batch.setBatchTeamId(batch1.getBatchTeamId());
                batch.setBatchTeamName(batch1.getBatchTeamName());
            }
        }
        sysDao.getServiceDo().add(batch);
        form.setSignBatchId(batch.getId());
        CopyUtils.Copy(form,signform);
        CopyUtils.Copy(signform,signvo);
        signvo.setUpHpis("2");
        form.setSignGoToSignState("2");
        AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(uservo.getPatientCity().substring(0, 4), "sign");
        if (serialSign != null) {
            Map<String,Object> sinum = sysDao.getAppSignformDao().getNum(serialSign.getSerialNo(),SignFormType.WEBSTATE.getValue());
            serialSign.setSerialNo(sinum.get("old").toString());
            sysDao.getServiceDo().modify(serialSign);
            signvo.setSignNum(sinum.get("new").toString());//签约编码
        }
        signvo.setSignPayState("0");//支付状态
        signvo.setSignType("1");//签约类型
        signvo.setUpHpis("2");//签约来源
        signvo.setSignWay("2");//医生代签
        signvo.setSignSurrenderDate(null);//解约时间
        signvo.setSignUrrenderReason(null);//解约原因
        // 续签操作
        Calendar ccc = Calendar.getInstance();
        String nowTime = ExtendDate.getYMD(ccc);
        boolean ff = false;//判断是否在此直接修改续签数据为已签约数据
        boolean jj = false;//判断是否是建档立卡经济类型
        //如果指定的数与参数相等返回0。 如果指定的数小于参数返回 -1。如果指定的数大于参数返回 1。
        int days = ExtendDateUtil.differDate(ExtendDate.getYMD(vo.getSignFromDate()), nowTime, "1");// 相差天数
        //if(vo.getSignFromDate().compareTo(ExtendDate.getCalendar(nowTime))==1){
        if (days > 0) {
            signvo.setSignState(SignFormType.XQ.getValue());// 续签
        }else{
            ff = true;
            signvo.setSignState(SignFormType.YUQY.getValue());
            form.setSignState(SignFormType.YJY.getValue());
        }
        sysDao.getServiceDo().modify(form);

       // signvo.setSignState("99"); // 续签状态
        signvo.setSignToDate(vo.getSignToDate());
        signvo.setSignFromDate(vo.getSignFromDate());
        signvo.setSignDate(Calendar.getInstance());
        /*//判断是否开启本年度签约协议
        AppSignSetting setting = sysDao.getAppSignSettingDao().findByAreaCode(AreaUtils.getAreaCode(signvo.getSignAreaCode(),"2"));
        if(setting != null){
            if("1".equals(setting.getSerOpenYear())){
                Calendar startDate = form.getSignToDate();
                startDate.add(Calendar.DATE,1);
                String endDate = ExtendDate.getYYYY(startDate)+"-12-31";
                signvo.setSignFromDate(startDate);
                signvo.setSignToDate(ExtendDate.getCalendar(endDate));
            }
        }*/
        sysDao.getServiceDo().add(signvo);


        if(dept != null){
              /*
             *cjw
             * 福州 调用医保接口 重视一下
             * 查询 cd_code
             * CodeState  1 为 与医保对接状态 0 为不对接
             */
            String bytype = "";
            String hospcode = dept.getHospAreaCode().substring(0, 4);
            CdCode code = sysDao.getCodeDao().findSign(hospcode);
            if(code != null && code.getCodeState().equals("1")){
                if(code.getCodeValue().equals("3501")){

                    bytype = SignYiBao(vo,signvo);
                    if(StringUtils.isNotBlank(bytype)){
                        if("800" != bytype){
                            throw  new Exception(bytype);
                        }
                    }else{
                        throw  new Exception("缴费登记失败");
                    }
                }
            }
        }
        if (vo.getPersGroup() != null && vo.getPersGroup().length > 0) {
            // signvo.setSignPersGroup(groups[0]);//支持之前版
            sysDao.getAppLabelGroupDao().addLabel(vo.getPersGroup(),signvo.getId(),signvo.getSignTeamId(),signvo.getSignAreaCode(),LabelManageType.FWRQ.getValue());
        }

        //web添加疾病标签（现只添加高血压和糖尿病默认灰标）
         if(vo.getPersGroup() != null && vo.getPersGroup().length>0){//数组
            String disLabel= "";
            String select="";
            String Tselect="";
            String Jselect="";
            String Bselect="";
            if(vo.getLableState()!=""&&vo.getLableState()!=null){
                if(vo.getLableState().equals("1")&&vo.getPersonLable()!=null){ //判断是否拥有添加疾病标签权限（1为有权限）
                    String[] strGroup=vo.getPersonLable().split(";");
                    if(strGroup!=null&&strGroup.length>0){//判断是否同时选中高血压及糖尿病的疾病标签和疾病类型
                        for(String vv:vo.getPersGroup()){
                            if(ResidentMangeType.GXY.getValue().equals(vv)){ //高血压
                                for(String vv1:strGroup){
                                    if(DiseaseType.GXY.getValue().equals(vv1.substring(0,3))){
                                        select="GXY";
                                        break;
                                    }else{
                                        select="NGXY";
                                    }
                                }
                            }
                            if(ResidentMangeType.TNB.getValue().equals(vv)){//糖尿病
                                for(String vv1:strGroup){
                                    if(DiseaseType.TNB.getValue().equals(vv1.substring(0,3))){
                                        Tselect="TNB";
                                        break;
                                    }else{
                                        Tselect="NTNB";
                                    }
                                }
                            }

                            if(ResidentMangeType.JHB.getValue().equals(vv)){//结核病
                                for(String vv1:strGroup){
                                    if(DiseaseType.JHB.getValue().equals(vv1.substring(0,3))){
                                        Jselect="JHB";
                                        break;
                                    }else{
                                        Jselect="NJHB";
                                    }
                                }
                            }

                            if(ResidentMangeType.YZJSZY.getValue().equals(vv)){//严重精神障碍
                                for(String vv1:strGroup){
                                    if(DiseaseType.YZJSZA.getValue().equals(vv1.substring(0,3))){
                                        Bselect="JSB";
                                        break;
                                    }else{
                                        Bselect="NJSB";
                                    }
                                }
                            }
                        }
                        disLabel=vo.getPersonLable();
                        if(select.equals("NGXY")){    //选中高血压服务人群，没有选中高血压疾病标签，默认灰标
                            disLabel=disLabel+ ";"+DiseaseType.GXY.getValue()+"|gray";
                        }
                        if(Tselect.equals("NTNB")){  //选中糖尿病服务人群，没有选中糖尿病疾病标签，默认灰标
                            disLabel=disLabel+ ";"+DiseaseType.TNB.getValue()+"|gray";
                        }
                        if(Jselect.equals("NJHB")){  //选中结核病服务人群，没有选中结核病疾病标签，默认灰标
                            disLabel=disLabel+ ";"+DiseaseType.JHB.getValue()+"|gray";
                        }
                        if(Bselect.equals("NJSB")){  //选中精神病服务人群，没有选中精神病疾病标签，默认灰标
                            disLabel=disLabel+ ";"+DiseaseType.YZJSZA.getValue()+"|gray";
                        }

                    }
                }else{
                    for(String vv:vo.getPersGroup()){
                        if(ResidentMangeType.GXY.getValue().equals(vv)){//高血压

                            if(StringUtils.isBlank(disLabel)){
                                disLabel = DiseaseType.GXY.getValue()+"|gray";
                            }else{
                                disLabel += ";"+DiseaseType.GXY.getValue()+"|gray";
                            }
                        }else if(ResidentMangeType.TNB.getValue().equals(vv)){//糖尿病

                            if(StringUtils.isBlank(disLabel)){
                                disLabel = DiseaseType.TNB.getValue()+"|gray";
                            }else{
                                disLabel += ";"+DiseaseType.TNB.getValue()+"|gray";
                            }
                        }
                    }
                }
            }

            if(StringUtils.isNotBlank(disLabel)){
                String[] disLabels = disLabel.split(";");
                sysDao.getAppLabelGroupDao().addLabel(disLabels,signvo.getId(),signvo.getSignTeamId(),signvo.getSignAreaCode(), LabelManageType.JBLX.getValue());
            }
        }
        // }
        //经济类型
        if (vo.getsJjType() != null && vo.getsJjType().length > 0) {
            for(String jjtype:vo.getsJjType()){
                if(EconomicType.JDLKPKRK.getValue().equals(jjtype)){
                    jj = true;
                }
            }
            sysDao.getAppLabelGroupDao().addLabel(vo.getsJjType(),signvo.getId(),signvo.getSignTeamId(),signvo.getSignAreaCode(), LabelManageType.JJLX.getValue());
        }
        if(ff&&jj){//判断是否是建档立卡经济类型
            sysDao.getAppSignformDao().addOrModifyArchivingSign(signvo.getSignPatientIdNo(),signvo.getId(),signvo.getSignDrId(),signvo.getSignTeamId(),signvo.getSignState(),vo.getPersGroup(),signvo.getSignAreaCode(),signvo.getSignHospId(),signvo.getSignFromDate(),uservo);
        }
        return signvo;
    }

    /**
     * 通过excel表格导入续签（批量续签)
     * WangCheng
     * @param map
     * @param user
     * @param orgId
     * @return
     * @throws Exception
     */
    public String addExcelRenew(Map<Integer,String> map, CdUser user, String orgId) throws Exception{
        String result = "成功导入"+ map.size() + "条续签信息";
        for (int i = 0;i < map.size();i++){
            String[] resultMap = map.get(i).split("\\|");
            if(StringUtils.isEmpty(resultMap[7])){
                result = "第"+ (i+1) +"行的第8列[续签时间]不能为空!";
                return result;
            }
        }
        for(int j = 0;j < map.size();j++){
            String[] renewMap = map.get(j).split("\\|");
            AppSignForm appSignForm = (AppSignForm)sysDao.getServiceDo().find(AppSignForm.class,renewMap[0]);
            if(appSignForm != null){
                AppSignForm formEntity = sysDao.getAppSignformDao().findSignFrom(appSignForm.getSignPatientIdNo());
                if(formEntity == null){
                    //签约单
                    AppSignForm newSignForm = new AppSignForm();
                    AppSignFormEntity signform = new AppSignFormEntity();

                    //服务人群分类
                    List<AppLabelGroup> labelGroupList = sysDao.getAppLabelGroupDao().listLabelGroup(appSignForm.getId());
                    //人口经济类型
                    List<AppLabelEconomics> economicsList = sysDao.getAppLabelGroupDao().listLabelEconomics(appSignForm.getId());

                    CopyUtils.Copy(appSignForm,signform);
                    CopyUtils.Copy(signform,newSignForm);

                    //通过身份证找到该居民信息
                    AppPatientUser patientUser = sysDao.getAppPatientUserDao().findByIdnoAndName(renewMap[3],null);
                    //批次
                    AppSignBatch batch=new AppSignBatch();
                    if(StringUtils.isNotBlank(patientUser.getPatientCity())) {
                        AppSerial serial = sysDao.getAppSignformDao().getAppSerial(patientUser.getPatientCity().substring(0, 4), "batch");
                        if(serial!=null) {
                            Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.WEBSTATE.getValue());
                            serial.setSerialNo(bcnum.get("old").toString());
                            sysDao.getServiceDo().modify(serial);
                            batch.setBatchNum(bcnum.get("new").toString());//批次号
                        }
                    }
                    batch.setBatchPatientName(patientUser.getPatientName());
                    batch.setBatchCreatePersid(patientUser.getId());
                    batch.setBatchTeamId(newSignForm.getSignTeamId());
                    batch.setBatchTeamName(newSignForm.getSignTeamName());
                    batch.setBatchCreateDate(Calendar.getInstance());
                    batch.setBatchOperatorId(user.getUserId());
                    batch.setBatchOperatorName(user.getUserName());
                    batch.setBatchAreaCode(newSignForm.getSignAreaCode());
                    sysDao.getServiceDo().add(batch);

                    AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(patientUser.getPatientCity().substring(0, 4), "sign");
                    if (serialSign != null) {
                        Map<String,Object> sinum = sysDao.getAppSignformDao().getNum(serialSign.getSerialNo(),SignFormType.WEBSTATE.getValue());
                        serialSign.setSerialNo(sinum.get("old").toString());
                        sysDao.getServiceDo().modify(serialSign);
                        newSignForm.setSignNum(sinum.get("new").toString());//签约编码
                    }
                    newSignForm.setSignGoToSignState("2");
                    newSignForm.setSignState("0");//签约状态
                    newSignForm.setSignPayState("0");//支付状态
                    newSignForm.setSignType("1");//签约类型
                    newSignForm.setUpHpis("2");//签约来源
                    newSignForm.setSignWay("2");//医生代签
                    newSignForm.setSignSurrenderDate(null);//解约时间
                    newSignForm.setSignUrrenderReason(null);//解约原因
                    try {
                        Date date = ExtendDate.getCalendar(renewMap[7]).getTime();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        newSignForm.setSignDate(calendar);
                        newSignForm.setSignFromDate(calendar);
                        Calendar calendarOther = Calendar.getInstance();
                        calendarOther.setTime(date);
                        calendarOther.add(calendarOther.YEAR,+1);
                        calendarOther.add(calendarOther.DAY_OF_MONTH,-1);
                        newSignForm.setSignToDate(calendarOther);
                        sysDao.getServiceDo().add(newSignForm);

                        //修改原有的人群类型
                        for(AppLabelGroup groupResult : labelGroupList){
                            groupResult.setLabelSignId(newSignForm.getId());
                            sysDao.getServiceDo().modify(groupResult);
                        }

                        //修改原有的经济类型
                        for(AppLabelEconomics economicsResult : economicsList){
                            economicsResult.setLabelSignId(newSignForm.getId());
                            sysDao.getServiceDo().modify(economicsResult);
                        }

                    }catch (Exception e){
                        result = "第"+ (j+1) +"行的续签时间格式不正确，正确格式如（2018-01-01）!";
                        return result;
                    }
                }else {
                    result = "第"+ (j+1) +"行的居民已经续签过了，不能重复续签!";
                    return result;
                }
            }else {
                result = "找不到第"+ (j+1) +"行的签约单信息!";
                return result;
            }
        }
        return result;
    }
}
