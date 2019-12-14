package com.ylz.bizDo.web.dao;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.web.po.*;
import com.ylz.bizDo.web.vo.*;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * 签约接口
 */
@Service("webSignUpDao")
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class WebSignUpDaoImpl implements WebSignUpDao {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(WebSignUpDaoImpl.class);

    private static final String agreeId = "9ac4acce-6d5e-4979-9258-10a818af129c";
    private static final String areaCode = "350000000000";

    @Autowired
    private SysDao sysDao;

    /**
     * 签约数据上传
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public AppSignForm webSignUp(WebSignUpVo vo) throws Exception {
        WebHospDept webHospDept = null;
        WebDrUser webDrUser = null;
        AppDrUser drBatchOperator = null;//操作者
        AppDrUser drAssistant = null;//助理
        AppTeamMember teamMember = null;//子团队
        WebTeam webTeam = null;//团队
        WebPatientUser webPatientUser = null;//居民
        String patientId = vo.getPatientId();
        String teamId = vo.getTeamId();
        String drId = vo.getDrId();
        String age = null;//年龄
        if (StringUtils.isBlank(vo.getAreaCodeProvince())) {
            throw new Exception("行政区划(省)不能为空!");
        }
        if (StringUtils.isBlank(vo.getAreaCodeCity())) {
            throw new Exception("行政区划（市）不能为空!");
        }
        if (StringUtils.isBlank(vo.getHospId())) {
            throw new Exception("医院主键不能为空!");
        }
//        if (StringUtils.isBlank(vo.getHospName())) {
//            throw new Exception("医院名称不能为空!");
//        }
//        if (StringUtils.isBlank(vo.getHospAreaCode())) {
//            throw new Exception("区域编码不能为空!");
//        }
//        if (StringUtils.isBlank(vo.getHospAddress())) {
//            throw new Exception("医院地址不能为空!");
//        }
        if (StringUtils.isBlank(vo.getDrId())) {
            throw new Exception("医生主键不能为空!");
        }
//        if (StringUtils.isBlank(vo.getDrName())) {
//            throw new Exception("医生名称不能为空!");
//        }
//        if (StringUtils.isBlank(vo.getDrAccount())) {
//            throw new Exception("医生账号不能为空!");
//        }
//        if (StringUtils.isBlank(vo.getDrGender())) {
//            throw new Exception("医生性别不能为空!");
//        }
//        if (StringUtils.isBlank(vo.getDrTel())) {
//            throw new Exception("医生手机号不能为空!");
//        } else {
//            if (vo.getDrTel().length() != 11) {
//                throw new Exception("医生手机号长度有误!");
//            }
//            if (!AccountValidatorUtil.isMobile(vo.getDrTel())) {
//                throw new Exception("医生手机号格式错误,请传入正确格式!");
//            }
//        }
        if (StringUtils.isBlank(vo.getHospOperatorId())) {
            throw new Exception("操作医生医院主键不能为空!");
        }
//        if (StringUtils.isBlank(vo.getDrOperatorId())) {
//            throw new Exception("操作医生主键不能为空!");
//        }
//        if (StringUtils.isBlank(vo.getDrOperatorName())) {
//            throw new Exception("操作医生名称不能为空!");
//        }
//        if (StringUtils.isBlank(vo.getDrOperatorAccount())) {
//            throw new Exception("操作医生账号不能为空!");
//        }
//        if (StringUtils.isBlank(vo.getDrOperatorGender())) {
//            throw new Exception("操作医生性别不能为空!");
//        }
//        if (StringUtils.isBlank(vo.getDrOperatorTel())) {
//            throw new Exception("操作医生电话不能为空!");
//        } else {
//            if (vo.getDrOperatorTel().length() != 11) {
//                throw new Exception("操作医生手机号长度有误!");
//            }
//            if (!AccountValidatorUtil.isMobile(vo.getDrOperatorTel())) {
//                throw new Exception("操作医生手机号格式错误,请传入正确格式!");
//            }
//        }
//        if (StringUtils.isBlank(vo.getMemState())) {
//            throw new Exception("团队角色不能为空!");
//        }
        if (StringUtils.isBlank(vo.getTeamId())) {
            throw new Exception("团队主键不能为空!");
        }
//        if (StringUtils.isBlank(vo.getTeamName())) {
//            throw new Exception("团队名称不能为空!");
//        }
        if (StringUtils.isBlank(vo.getPatientId())) {
            throw new Exception("居民主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientName())) {
            throw new Exception("居民名字不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientIdno())) {
            throw new Exception("居民身份证号不能为空!");
        }else{
            String resultIdNo = CardUtil.IDCardValidate(vo.getPatientIdno().toLowerCase());
            if(StringUtils.isNotBlank(resultIdNo)){
                throw new Exception(resultIdNo);
            }
        }
        if (StringUtils.isBlank(vo.getPatientCard())) {
            throw new Exception("居民社保卡不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientAddress())) {
            throw new Exception("居民地址不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientTel())) {
            throw new Exception("居民手机号不能为空!");
        } else {
            if (vo.getPatientTel().length() != 11) {
                throw new Exception("居民手机号长度有误!");
            }
            if (!AccountValidatorUtil.isMobile(vo.getPatientTel())) {
                throw new Exception("居民手机号格式错误,请传入正确格式!");
            }
        }
        if (StringUtils.isBlank(vo.getPatientProvince())) {
            throw new Exception("省不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientCity())) {
            throw new Exception("市不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientArea())) {
            throw new Exception("区不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientStreet())) {
            throw new Exception("街道不能为空!");
        }
//        if (StringUtils.isBlank(vo.getPatientNeighborhoodCommittee())) {
//            throw new Exception("居委会不能为空!");
//        }
        if (StringUtils.isBlank(vo.getPatientjmda())) {
            throw new Exception("居民建康档案不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientjtda())) {
            throw new Exception("居民家庭档案不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignDate())) {
            throw new Exception("签约时间不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignFromDate())) {
            throw new Exception("有效开始时间不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignToDate())) {
            throw new Exception("有效结束时间不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignPayState())) {
            throw new Exception("缴费状态不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignState())) {
            throw new Exception("签约状态不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignPersGroup())) {
            throw new Exception("服务人群不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignsJjType())) {
            throw new Exception("经济类型不能为空!");
        }

        AppSignForm signForm = sysDao.getAppSignformDao().findSignFormByUser(vo.getPatientId());
        if (signForm != null) {
            throw new Exception("签约单已存在,请不要重复上传签约单!");
        }
        //医院信息 根据医院id查询是否存在，没有自动创建医院
        AppHospDept appHospDept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, vo.getHospId());
        //创建医院
        if (appHospDept == null) {
            throw new Exception("查无机构数据,请先上传机构数据!");
//            webHospDept = new WebHospDept();
//            webHospDept.setId(vo.getHospId());//医院主键
//            webHospDept.setHospName(vo.getHospName());//医院名称
//            webHospDept.setHospAreaCode(vo.getHospAreaCode());//行政区划
//            webHospDept.setHospAddress(vo.getHospAddress());//医院地址
//            webHospDept.setHospTel(vo.getHospTel());//医院电话
//            sysDao.getServiceDo().add(webHospDept);
//            AppAgreement v = (AppAgreement) sysDao.getServiceDo().find(AppAgreement.class, agreeId);
//            if (v != null) {
//                String agreeAreaCode = AreaUtils.getAreaCode(webHospDept.getHospAreaCode(), "2");
//                AppAgreement p = new AppAgreement();
//                p.setMentUseType("2");
//                p.setMentCityId(agreeAreaCode);
//                p.setMentContent(v.getMentContent());
//                p.setMentState("1");
//                p.setMentType("1");
//                p.setMentHospId(webHospDept.getId());
//                p.setMentTitle(v.getMentTitle());
//                sysDao.getServiceDo().add(p);
//            }
        }
        //医生信息 根据医生id查询是否存在，没有自动创建医生
        AppDrUser dr = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, vo.getDrId());
        if (dr == null) {
            throw new Exception("查无医生数据,请先上传医生数据!");
//            webDrUser = new WebDrUser();
//            webDrUser.setId(vo.getDrId());//医生主键
//            webDrUser.setDrHospId(vo.getHospId());//医院主键
//            webDrUser.setDrName(vo.getDrName());//医生姓名
//            webDrUser.setDrAccount(vo.getDrAccount());//医生账号
//            webDrUser.setDrGender(vo.getDrGender());//医生性别
//            webDrUser.setDrTel(vo.getDrTel());//医生电话
//            if (StringUtils.isNotBlank(vo.getDrIdNo())) {
//                webDrUser.setDrIdno(vo.getDrIdNo());//医生身份证
//            }
//            if (StringUtils.isNotBlank(vo.getDrPwd())) {
//                webDrUser.setDrPwd(vo.getDrPwd());//医生密码
//            } else {
//                webDrUser.setDrPwd(Md5Util.MD5(vo.getDrTel().substring(vo.getDrTel().length() - 6, vo.getDrTel().length())));//医生密码
//            }
//            if (StringUtils.isNotBlank(webDrUser.getDrName())) {
//                webDrUser.setDrBopomofo(PinyinUtil.getPinYinHeadChar(webDrUser.getDrName()));
//            }
//            sysDao.getServiceDo().add(webDrUser);
        }

        if(StringUtils.isNotBlank(vo.getDrOperatorId())){
            drBatchOperator = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, vo.getDrOperatorId());
            if (drBatchOperator == null) {
                throw new Exception("查无操作医生数据,请先上传操作医生数据!");
//            webDrUserBatchOperator = new WebDrUser();
//            webDrUserBatchOperator.setId(vo.getDrOperatorId());//操作医生主键
//            webDrUserBatchOperator.setDrHospId(vo.getHospOperatorId());//操作医生医院主键
//            webDrUserBatchOperator.setDrName(vo.getDrOperatorName());//操作医生姓名
//            webDrUserBatchOperator.setDrAccount(vo.getDrOperatorAccount());//操作医生账号
//            if (StringUtils.isNotBlank(vo.getDrOperatorPwd())) {//操作医生密码
//                webDrUserBatchOperator.setDrPwd(vo.getDrOperatorPwd());
//            } else {
//                webDrUserBatchOperator.setDrPwd(Md5Util.MD5(vo.getDrOperatorTel().substring(vo.getDrOperatorTel().length() - 6, vo.getDrOperatorTel().length())));//医生密码
//            }
//            if (StringUtils.isNotBlank(vo.getDrOperatorIdNo())) {
//                webDrUserBatchOperator.setDrIdno(vo.getDrOperatorIdNo());//操作医生身份证
//            }
//            webDrUserBatchOperator.setDrGender(vo.getDrOperatorGender());//操作医生性别
//            webDrUserBatchOperator.setDrTel(vo.getDrOperatorTel());//操作医生电话
//            if (StringUtils.isNotBlank(webDrUserBatchOperator.getDrName())) {
//                webDrUserBatchOperator.setDrBopomofo(PinyinUtil.getPinYinHeadChar(webDrUserBatchOperator.getDrName()));
//            }
//            sysDao.getServiceDo().add(webDrUserBatchOperator);
            }
        }




        //助理
        if (StringUtils.isNotBlank(vo.getDrAssistantId())) {
//            if (!vo.getDrId().equals(vo.getDrAssistantId()) && !vo.getDrAssistantId().equals(vo.getDrOperatorId())) {
                drAssistant = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, vo.getDrAssistantId());
                if (drAssistant == null) {
                    throw new Exception("查无助理医生数据,请先上传助理医生数据!");
//                    if (StringUtils.isBlank(vo.getDrAssistantAccount())) {
//                        throw new Exception("助理医生账户不能为空!");
//                    }
//                    if (StringUtils.isBlank(vo.getDrAssistantName())) {
//                        throw new Exception("助理医生名称不能为空!");
//                    }
//                    if (StringUtils.isBlank(vo.getDrAssistantAccount())) {
//                        throw new Exception("助理医生账号不能为空!");
//                    }
//                    if (StringUtils.isBlank(vo.getDrAssistantGender())) {
//                        throw new Exception("助理医生性别不能为空!");
//                    }
//                    if (StringUtils.isBlank(vo.getDrAssistantTel())) {
//                        throw new Exception("助理医生电话不能为空!");
//                    } else {
//                        if (vo.getPatientTel().length() != 11) {
//                            throw new Exception("助理医生手机号长度有误!");
//                        }
//                        if (!AccountValidatorUtil.isMobile(vo.getPatientTel())) {
//                            throw new Exception("助理医生手机号格式错误,请传入正确格式!");
//                        }
//                    }
//                    webDrUserAssistant = new WebDrUser();
//                    webDrUserAssistant.setId(vo.getDrAssistantId());//助理医生主键
//                    webDrUserAssistant.setDrHospId(vo.getHospAssistantId());//助理医生医院主键
//                    webDrUserAssistant.setDrName(vo.getDrAssistantName());//助理医生名称
//                    webDrUserAssistant.setDrAccount(vo.getDrAssistantAccount());//助理医生账号
//                    if (StringUtils.isNotBlank(vo.getDrAssistantPwd())) {//助理医生密码
//                        webDrUserAssistant.setDrPwd(vo.getDrAssistantPwd());
//                    } else {
//                        webDrUserAssistant.setDrPwd(Md5Util.MD5(vo.getDrAssistantTel().substring(vo.getDrAssistantTel().length() - 6, vo.getDrAssistantTel().length())));//医生密码
//                    }
//                    if (StringUtils.isNotBlank(vo.getDrOperatorIdNo())) {
//                        webDrUserAssistant.setDrIdno(vo.getDrOperatorIdNo());//助理医生身份证
//                    }
//                    webDrUserAssistant.setDrGender(vo.getDrAssistantGender());//助理医生性别
//                    webDrUserAssistant.setDrTel(vo.getDrAssistantTel());//助理医生电话
//                    if (StringUtils.isNotBlank(webDrUserAssistant.getDrName())) {
//                        webDrUserAssistant.setDrBopomofo(PinyinUtil.getPinYinHeadChar(webDrUserAssistant.getDrName()));
//                    }
//                    sysDao.getServiceDo().add(webDrUserAssistant);
                }
//            }
        }
        //团队 医生id查询是否已有团队 没有就根据上传的团队名称自动创建团队
        AppTeam appTeam = (AppTeam) this.sysDao.getServiceDo().find(AppTeam.class,teamId);
        if(appTeam == null){
            throw new Exception("查无团队数据,请先上团队数据!");
        }
//        List<AppTeamMember> tls = this.findTeam(vo.getHospId(), vo.getTeamId());
//        if (tls != null && !tls.isEmpty()) {
//            for (AppTeamMember a : tls) {
//                String teamName = a.getMemTeamName();
//                if (teamName.equals(vo.getTeamName())) {
//                    teamId = a.getMemTeamid();
//                    break;
//                } else {
//                    teamId = a.getMemTeamid();
//                }
//            }
//        } else {
//            webTeam = (WebTeam) sysDao.getServiceDo().find(WebTeam.class, vo.getTeamId());
//            if (webTeam == null) {
//                webTeam = new WebTeam();
//                webTeam.setTeamState("1");
//                webTeam.setTeamType("0");
//                webTeam.setId(vo.getTeamId());
//                webTeam.setTeamDrId(vo.getDrId());
//                webTeam.setTeamName(vo.getTeamName());
//                webTeam.setTeamHospId(vo.getHospId());
//                webTeam.setTeamHospName(vo.getHospName());
//                sysDao.getServiceDo().add(webTeam);
//            }
//            teamMember = findTeamMember(vo.getDrId(), vo.getTeamId());
//            if (teamMember == null) {
//                teamMember = new AppTeamMember();
//                teamMember.setMemState("1");
//                teamMember.setMemWorkType("3");
//                teamMember.setMemDrId(vo.getDrId());
//                teamMember.setMemDrName(vo.getDrName());
//                teamMember.setMemTeamid(vo.getTeamId());
//                teamMember.setMemTeamName(vo.getTeamName());
//                teamMember.setMemState(vo.getMemState());
//                sysDao.getServiceDo().add(teamMember);
//            }
//            //如果助理医生不为空
//            if (StringUtils.isNotBlank(vo.getDrAssistantId())) {
//                teamMember = findTeamMember(vo.getDrAssistantId(), vo.getTeamId());
//                if (teamMember == null) {
//                    teamMember = new AppTeamMember();
//                    teamMember.setMemState("1");
//                    teamMember.setMemWorkType("3");
//                    teamMember.setMemDrId(vo.getDrOperatorId());
//                    teamMember.setMemDrName(vo.getDrAssistantName());
//                    teamMember.setMemTeamid(vo.getTeamId());
//                    teamMember.setMemTeamName(vo.getTeamName());
//                    teamMember.setMemState("1");
//                    sysDao.getServiceDo().add(teamMember);
//                }
//            }
//        }
        //居民 根据居民身份证查询是否存在，没有自动创建居民
        List<AppPatientUser> puser = sysDao.getServiceDo().loadByPk(AppPatientUser.class, "patientIdno", vo.getPatientIdno());
        if (puser != null && !puser.isEmpty()) {
            age = puser.get(0).getPatientAge();
            vo.setPatientId(puser.get(0).getId());//用户存在 替换成当前用户
            vo.setPatientName(puser.get(0).getPatientName());
            vo.setPatientGender(puser.get(0).getPatientGender());
        } else {
            webPatientUser = new WebPatientUser();
            webPatientUser.setId(vo.getPatientId());//主键
            webPatientUser.setPatientName(vo.getPatientName());//姓名
            webPatientUser.setPatientIdno(vo.getPatientIdno());//身份证
            webPatientUser.setPatientCard(vo.getPatientCard());//社保卡
            webPatientUser.setPatientTel(vo.getPatientTel());//电话
            webPatientUser.setPatientAddress(vo.getPatientAddress());//地址
            if (StringUtils.isNotBlank(webPatientUser.getPatientName())) {
                webPatientUser.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(webPatientUser.getPatientName()));
            }
            //身份证
            Map<String, Object> idNos = new HashMap<String, Object>();
            if (vo.getPatientIdno().trim().length() == 18) {
                idNos = CardUtil.getCarInfo(vo.getPatientIdno().trim());
            } else if (vo.getPatientIdno().trim().length() == 15) {
                idNos = CardUtil.getCarInfo15W(vo.getPatientIdno().trim());
            }
            //出生日期
            if (idNos.get("birthday") != null) {
                webPatientUser.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
            }
            //年龄
            if (idNos.get("age") != null) {
                if (webPatientUser.getPatientBirthday() != null) {
                    webPatientUser.setPatientAge(AgeUtil.getAgeYear(webPatientUser.getPatientBirthday()));
                } else {
                    webPatientUser.setPatientAge(idNos.get("age").toString());
                }

            }
            if (idNos.get("sex") != null) {
                webPatientUser.setPatientGender(idNos.get("sex").toString());//性别
            }
            webPatientUser.setPatientProvince(vo.getAreaCodeProvince());//省
            webPatientUser.setPatientCity(vo.getAreaCodeCity());//市
            webPatientUser.setPatientArea(vo.getPatientArea());//区
            webPatientUser.setPatientStreet(vo.getPatientStreet());//街道
            webPatientUser.setPatientjmda(vo.getPatientjmda());//居民档案
            webPatientUser.setPatientjtda(vo.getPatientjtda());//家庭档案
            webPatientUser.setPatientUpHpis(UserUpHpisType.WEIJIHUO.getValue());//用户未激活
            webPatientUser.setPatientState(CommonEnable.QIYONG.getValue());
            webPatientUser.setPatientHealthy(CommonEnable.JINYONG.getValue());
            webPatientUser.setPatientJgState(UserJgType.WEISHEZHI.getValue());
            webPatientUser.setPatientEaseState(UserJgType.WEISHEZHI.getValue());
            webPatientUser.setPatientPwd(Md5Util.MD5(webPatientUser.getPatientTel().substring(webPatientUser.getPatientTel().length() - 6, webPatientUser.getPatientTel().length())));
            sysDao.getServiceDo().add(webPatientUser);
        }
        //签约----------------------------------------------
        System.out.println("patientId--" + patientId + "--teamid--" + teamId + "--drId--" + drId + "---hospId---" + vo.getHospId());
        AppSignBatch batch = new AppSignBatch();//批次
        AppSignForm form = new AppSignForm();//签约单
        batch.setBatchCreateDate(Calendar.getInstance());
        batch.setBatchTeamId(teamId);
        batch.setBatchTeamName(appTeam.getTeamName());
        batch.setBatchCreatePersid(vo.getPatientId());
        batch.setBatchPatientName(vo.getPatientName());
        batch.setBatchOperatorId(drBatchOperator.getId());
        batch.setBatchOperatorName(drBatchOperator.getDrName());
        //组织批次号
        String cityAreaCode = vo.getAreaCodeCity();
        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, vo.getHospId());
        if (StringUtils.isNotBlank(cityAreaCode)) {
            if (dept != null) {
                batch.setBatchAreaCode(dept.getHospAreaCode());
            } else {
                batch.setBatchAreaCode(webHospDept.getHospAreaCode());
            }
            AppSerial serial = sysDao.getAppSignformDao().getAppSerial(cityAreaCode.substring(0, 4), "batch");
            if (serial != null) {
                String num = serial.getSerialNo();
                Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(num,SignFormType.WEBSTATE.getValue());
                serial.setSerialNo(bcnum.get("old").toString());
                sysDao.getServiceDo().modify(serial);
                batch.setBatchNum(bcnum.get("new").toString());//批次号
            }
        }
        sysDao.getServiceDo().add(batch);
        form.setSignBatchId(batch.getId());
        //组织编码
        if (StringUtils.isNotBlank(cityAreaCode)) {
            if (dept != null) {
                form.setSignAreaCode(dept.getHospAreaCode());
                form.setSignHospId(dept.getId());
            } else {
                form.setSignAreaCode(webHospDept.getHospAreaCode());
                form.setSignHospId(webHospDept.getId());

            }
            AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(vo.getAreaCodeCity().substring(0, 4), "sign");
            if (serialSign != null) {
                String num = serialSign.getSerialNo();
                Map<String,Object> sinum = sysDao.getAppSignformDao().getNum(num,SignFormType.WEBSTATE.getValue());
                serialSign.setSerialNo(sinum.get("old").toString());
                sysDao.getServiceDo().modify(serialSign);
                form.setSignNum(sinum.get("new").toString());//签约编码
            }
        }
        //签约单
        form.setSignPatientId(vo.getPatientId());//居民主键
        form.setSignDate(ExtendDate.getCalendar(vo.getSignDate()));//签约申请时间
        form.setSignType("1");//1家庭签约
        form.setSignTeamId(teamId);//团队主键
        form.setSignTeamName(appTeam.getTeamName());//团队名称
        form.setSignWay("2");//医生代签
        form.setSignPayState(vo.getSignPayState());//缴费状态
        form.setSignState(vo.getSignState());//签约状态
        form.setSignPatientGender(vo.getPatientGender());//性别
        if (StringUtils.isNotBlank(age)) {
            form.setSignPatientAge(Integer.parseInt(age));//年龄
        }
        form.setSignPatientIdNo(vo.getPatientIdno());//身份证
        form.setSignFromDate(ExtendDate.getCalendar(vo.getSignFromDate()));
        form.setSignToDate(ExtendDate.getCalendar(vo.getSignToDate()));
        form.setSignDrId(drId);//医生主键
        form.setSignContractState("0");//1是 0否
        form.setSignGreenState("0");//1是 0否
        form.setSignYellowState("0");//1是 0否
        form.setSignRedState("0");//1是 0否
        form.setUpHpis("2");//数据来源web
        //健康情况
        if (StringUtils.isNotBlank(vo.getSignHealth())) {
            form.setSignHealthGroup(vo.getSignHealth());
        } else {
            form.setSignHealthGroup("199");
        }
        if (StringUtils.isNotBlank(vo.getSignlx())) {
            form.setSignlx(vo.getSignlx());//医保类型
        }
        if (StringUtils.isNotBlank(vo.getSignczpay())) {
            form.setSignczpay(vo.getSignczpay());//财政
        }
        if (StringUtils.isNotBlank(vo.getSignzfpay())) {
            form.setSignzfpay(vo.getSignzfpay());//自费
        }
        form.setSignDrAssistantId(vo.getSignDrAssistantId());//助理
        sysDao.getServiceDo().add(form);
        //服务人群
        if (StringUtils.isNotBlank(vo.getSignPersGroup())) {
            String[] persGroup = vo.getSignPersGroup().split(",");
            if (persGroup != null && persGroup.length > 0) {
                String areaCode = "";
                if (dept != null) {
                    areaCode = dept.getHospAreaCode();
                } else {
                    areaCode = webHospDept.getHospAreaCode();
                }
                form.setSignPersGroup(persGroup[0]);//支持之前版
                sysDao.getAppLabelGroupDao().addLabel(persGroup, form.getId(), form.getSignTeamId(), areaCode, LabelManageType.FWRQ.getValue());
            }
        }
        //经济类型
        if (StringUtils.isNotBlank(vo.getSignsJjType())) {
            String[] jjTypes = vo.getSignsJjType().split(",");
            if (jjTypes != null && jjTypes.length > 0) {
                String areaCode = "";
                if (dept != null) {
                    areaCode = dept.getHospAreaCode();
                } else {
                    areaCode = webHospDept.getHospAreaCode();
                }
                form.setSignsJjType(jjTypes[0]);//支持之前版
                sysDao.getAppLabelGroupDao().addLabel(jjTypes, form.getId(), form.getSignTeamId(), areaCode, LabelManageType.JJLX.getValue());
            }
        }

        //疾病类型
        if (StringUtils.isNotBlank(vo.getSignDiseaseGroup())) {
            String[] disease = vo.getSignDiseaseGroup().split(",");
            if (disease != null && disease.length > 0) {
                String areaCode = "";
                if (dept != null) {
                    areaCode = dept.getHospAreaCode();
                } else {
                    areaCode = webHospDept.getHospAreaCode();
                }
                sysDao.getAppLabelGroupDao().addLabel(disease, form.getId(), form.getSignTeamId(), areaCode, LabelManageType.JBLX.getValue());
            }
        }
        return form;
    }


    //签约数据上传至web版
    public List<AppTeamMember> findTeam(String hospId, String teamId) throws Exception {
        List<AppTeamMember> ls = null;
        String sql = "SELECT\n" +
                "\tb.*\n" +
                "FROM\n" +
                "\tAPP_TEAM a LEFT JOIN APP_TEAM_MEMBER b on a.ID=b.MEM_TEAMID\n" +
                "WHERE\n" +
                "\ta.TEAM_HOSP_ID = :TEAM_HOSP_ID\n" +
                "AND b.MEM_DR_ID = :MEM_DR_ID";
        Map<String, Object> map = new HashedMap();
        map.put("TEAM_HOSP_ID", hospId);
        map.put("MEM_DR_ID", teamId);
        ls = sysDao.getServiceDo().findSqlMap(sql, map, AppTeam.class);
        return ls;
    }


    public AppTeamMember findTeamMember(String drId, String teamId) throws Exception {
        Map<String, Object> map = new HashedMap();
        String sql = "SELECT\n" +
                "\ta.*\n" +
                "FROM\n" +
                "\tAPP_TEAM_MEMBER a\n" +
                "WHERE\n" +
                "\ta.MEM_DR_ID =:MEM_DR_ID \n" +
                "AND a.MEM_TEAMID =:MEM_TEAMID ";
        map.put("MEM_DR_ID", drId);
        map.put("MEM_TEAMID", teamId);
        List<AppTeamMember> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppTeamMember.class);
        if (ls != null && !ls.isEmpty()) {
            return ls.get(0);
        }
        return null;
    }

    /**
     * 保存机构数据
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public WebHospDept webSaveHosp(WebSignUpVo vo) throws Exception {
        if (StringUtils.isBlank(vo.getAreaCodeCity())) {
            throw new Exception("行政区划（市）不能为空!");
        }
        if (StringUtils.isBlank(vo.getHospId())) {
            throw new Exception("医院主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getHospName())) {
            throw new Exception("医院名称不能为空!");
        }
        if (StringUtils.isBlank(vo.getHospAreaCode())) {
            throw new Exception("区域编码不能为空!");
        }
        if (StringUtils.isBlank(vo.getHospAddress())) {
            throw new Exception("医院地址不能为空!");
        }
        //医院信息 根据医院id查询是否存在，没有自动创建医院
        List<WebHospDept> listDept = sysDao.getServiceDo().loadByPk(WebHospDept.class,"id",vo.getHospId());
        if(listDept!=null && listDept.size()>0){
            for(WebHospDept dept:listDept){
                dept.setHospState("0");
                sysDao.getServiceDo().modify(dept);
            }
        }
        WebHospDept webHospDept = new WebHospDept();
        webHospDept.setId(vo.getHospId());//医院主键
        webHospDept.setHospName(vo.getHospName());//医院名称
        webHospDept.setHospAreaCode(vo.getHospAreaCode());//行政区划
        webHospDept.setHospAddress(vo.getHospAddress());//医院地址
        webHospDept.setHospTel(vo.getHospTel());//医院电话
        sysDao.getServiceDo().add(webHospDept);
        return webHospDept;

        /*WebHospDept webHospDept = (WebHospDept) sysDao.getServiceDo().find(WebHospDept.class, vo.getHospId());
        //创建医院
        if (webHospDept == null) {
            webHospDept = new WebHospDept();
            webHospDept.setId(vo.getHospId());//医院主键
            webHospDept.setHospName(vo.getHospName());//医院名称
            webHospDept.setHospAreaCode(vo.getHospAreaCode());//行政区划
            webHospDept.setHospAddress(vo.getHospAddress());//医院地址
            webHospDept.setHospTel(vo.getHospTel());//医院电话
            sysDao.getServiceDo().add(webHospDept);
            AppAgreement v = (AppAgreement) sysDao.getServiceDo().find(AppAgreement.class, agreeId);
            if (v != null) {
                String agreeAreaCode = AreaUtils.getAreaCode(webHospDept.getHospAreaCode(), "2");
                AppAgreement p = new AppAgreement();
                p.setMentUseType("2");
                p.setMentCityId(agreeAreaCode);
                p.setMentContent(v.getMentContent());
                p.setMentState("1");
                p.setMentType("1");
                p.setMentHospId(webHospDept.getId());
                p.setMentTitle(v.getMentTitle());
                sysDao.getServiceDo().add(p);
            }
            return webHospDept;
        } else {//修改操作*/
//            webHospDept.setHospName(vo.getHospName());//医院名称
//            webHospDept.setHospAreaCode(vo.getHospAreaCode());//行政区划
//            webHospDept.setHospAddress(vo.getHospAddress());//医院地址
//            if (StringUtils.isNotBlank(vo.getHospTel())) {
//                webHospDept.setHospTel(vo.getHospTel());//医院电话
//            }
//            sysDao.getServiceDo().modify(webHospDept);

//        }
    }

    @Override
    public WebDrUser webSaveDr(WebSignUpVo vo) throws Exception {
        if (StringUtils.isBlank(vo.getAreaCodeCity())) {
            throw new Exception("行政区划（市）不能为空!");
        }
        if (StringUtils.isBlank(vo.getDrId())) {
            throw new Exception("医生主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getHospId())) {
            throw new Exception("医院主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getDrName())) {
            throw new Exception("医生名称不能为空!");
        }
        if (StringUtils.isBlank(vo.getDrAccount())) {
            throw new Exception("医生账号不能为空!");
        }
        if (StringUtils.isBlank(vo.getDrGender())) {
            throw new Exception("医生性别不能为空!");
        }
        if (StringUtils.isBlank(vo.getDrTel())) {
            throw new Exception("医生手机号不能为空!");
        } else {
            if (vo.getDrTel().length() != 11) {
                throw new Exception("医生手机号长度有误!");
            }
            if (!AccountValidatorUtil.isMobile(vo.getDrTel())) {
                throw new Exception("医生手机号格式错误,请传入正确格式!");
            }
        }
        List<WebDrUser> listDrUser = sysDao.getServiceDo().loadByPk(WebDrUser.class,"id",vo.getDrId());
        if(listDrUser!=null && listDrUser.size()>0){
            for(WebDrUser drUser:listDrUser){

                sysDao.getServiceDo().modify(drUser);
            }
        }
        WebDrUser webDrUser = new WebDrUser();
        webDrUser.setId(vo.getDrId());//医生主键
        webDrUser.setDrHospId(vo.getHospId());//医院主键
        webDrUser.setDrName(vo.getDrName());//医生姓名
        webDrUser.setDrAccount(vo.getDrAccount());//医生账号
        webDrUser.setDrGender(vo.getDrGender());//医生性别
        webDrUser.setDrTel(vo.getDrTel());//医生电话
        if (StringUtils.isNotBlank(vo.getDrIdNo())) {
            webDrUser.setDrIdno(vo.getDrIdNo());//医生身份证
        }
        if (StringUtils.isNotBlank(vo.getDrPwd())) {
            webDrUser.setDrPwd(vo.getDrPwd());//医生密码
        } else {
            webDrUser.setDrPwd(Md5Util.MD5(vo.getDrTel().substring(vo.getDrTel().length() - 6, vo.getDrTel().length())));//医生密码
        }
        if (StringUtils.isNotBlank(webDrUser.getDrName())) {
            webDrUser.setDrBopomofo(PinyinUtil.getPinYinHeadChar(webDrUser.getDrName()));
        }
        sysDao.getServiceDo().add(webDrUser);
        return webDrUser;

       /* WebDrUser webDrUser = (WebDrUser) sysDao.getServiceDo().find(WebDrUser.class, vo.getDrId());
        if (webDrUser == null) {
            webDrUser = new WebDrUser();
            webDrUser.setId(vo.getDrId());//医生主键
            webDrUser.setDrHospId(vo.getHospId());//医院主键
            webDrUser.setDrName(vo.getDrName());//医生姓名
            webDrUser.setDrAccount(vo.getDrAccount());//医生账号
            webDrUser.setDrGender(vo.getDrGender());//医生性别
            webDrUser.setDrTel(vo.getDrTel());//医生电话
            if (StringUtils.isNotBlank(vo.getDrIdNo())) {
                webDrUser.setDrIdno(vo.getDrIdNo());//医生身份证
            }
            if (StringUtils.isNotBlank(vo.getDrPwd())) {
                webDrUser.setDrPwd(vo.getDrPwd());//医生密码
            } else {
                webDrUser.setDrPwd(Md5Util.MD5(vo.getDrTel().substring(vo.getDrTel().length() - 6, vo.getDrTel().length())));//医生密码
            }
            if (StringUtils.isNotBlank(webDrUser.getDrName())) {
                webDrUser.setDrBopomofo(PinyinUtil.getPinYinHeadChar(webDrUser.getDrName()));
            }
            sysDao.getServiceDo().add(webDrUser);
        } else {
//            webDrUser.setDrHospId(vo.getHospId());//医院主键
//            webDrUser.setDrName(vo.getDrName());//医生姓名
//            webDrUser.setDrAccount(vo.getDrAccount());//医生账号
//            webDrUser.setDrGender(vo.getDrGender());//医生性别
//            webDrUser.setDrTel(vo.getDrTel());//医生电话
//            if (StringUtils.isNotBlank(vo.getDrIdNo())) {
//                webDrUser.setDrIdno(vo.getDrIdNo());//医生身份证
//            }
//            if (StringUtils.isNotBlank(vo.getDrPwd())) {
//                webDrUser.setDrPwd(vo.getDrPwd());//医生密码
//            }
//            if (StringUtils.isNotBlank(webDrUser.getDrName())) {
//                webDrUser.setDrBopomofo(PinyinUtil.getPinYinHeadChar(webDrUser.getDrName()));
//            }
//            sysDao.getServiceDo().modify(webDrUser);


        }*/

    }

    /**
     * 保存团队信息
     *
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public WebTeam webSaveTeam(WebSignUpVo vo) throws Exception {
        if (StringUtils.isBlank(vo.getTeamId())) {
            throw new Exception("团队主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getTeamName())) {
            throw new Exception("团队名称不能为空!");
        }
        if (StringUtils.isBlank(vo.getHospId())) {
            throw new Exception("医院主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getDrId())) {
            throw new Exception("医生主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getHospName())) {
            throw new Exception("医院名称不能为空!");
        }
        List<WebTeam> listTeam = sysDao.getServiceDo().loadByPk(WebTeam.class,"id",vo.getTeamId());
        if(listTeam!=null && listTeam.size()>0){
            for(WebTeam team:listTeam){
                team.setTeamState("");
                sysDao.getServiceDo().modify(team);
            }
        }
        WebTeam webTeam = new WebTeam();
        webTeam.setTeamState("1");
        webTeam.setTeamType("0");
        webTeam.setId(vo.getTeamId());
        webTeam.setTeamDrId(vo.getDrId());
        webTeam.setTeamName(vo.getTeamName());
        webTeam.setTeamHospId(vo.getHospId());
        webTeam.setTeamHospName(vo.getHospName());
        sysDao.getServiceDo().add(webTeam);
        return webTeam;
        /*WebTeam webTeam = (WebTeam) sysDao.getServiceDo().find(WebTeam.class, vo.getTeamId());
        if (webTeam == null) {
            webTeam = new WebTeam();
            webTeam.setTeamState("1");
            webTeam.setTeamType("0");
            webTeam.setId(vo.getTeamId());
            webTeam.setTeamDrId(vo.getDrId());
            webTeam.setTeamName(vo.getTeamName());
            webTeam.setTeamHospId(vo.getHospId());
            webTeam.setTeamHospName(vo.getHospName());
            sysDao.getServiceDo().add(webTeam);
        } else {
//            webTeam.setTeamDrId(vo.getDrId());
//            webTeam.setTeamName(vo.getTeamName());
//            webTeam.setTeamHospId(vo.getHospId());
//            webTeam.setTeamHospName(vo.getHospName());
//            sysDao.getServiceDo().modify(webTeam);

        }*/

    }

    /**
     * 保存或删除团队成员信息
     *
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public AppTeamMember webSaveTeamM(WebSignUpVo vo) throws Exception {
        if (StringUtils.isBlank(vo.getDrId())) {
            throw new Exception("医生主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getDrName())) {
            throw new Exception("医生姓名不能为空!");
        }
        if (StringUtils.isBlank(vo.getTeamId())) {
            throw new Exception("团队主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getTeamName())) {
            throw new Exception("团队名称不能为空!");
        }
        if (StringUtils.isBlank(vo.getMemState())) {
            throw new Exception("团队角色不能为空!");
        }
        if("1".equals(vo.getTeamMerType())){
            //团队 医生id查询是否已有团队 没有就根据上传的团队名称自动创建团队
            List<AppTeamMember> list = findListTeamMember(vo.getDrId(),vo.getTeamId());
            if(list!=null && list.size()>0){
                for(AppTeamMember ls:list){

                    sysDao.getServiceDo().modify(ls);
                }
            }
            AppTeamMember teamMember = new AppTeamMember();
            teamMember.setMemState("1");
            teamMember.setMemWorkType("3");
            teamMember.setMemDrId(vo.getDrId());
            teamMember.setMemDrName(vo.getDrName());
            teamMember.setMemTeamid(vo.getTeamId());
            teamMember.setMemTeamName(vo.getTeamName());
            teamMember.setMemState(vo.getMemState());
            sysDao.getServiceDo().add(teamMember);


            /*AppTeamMember teamMember = findTeamMember(vo.getDrId(), vo.getTeamId());
            if (teamMember == null) {
                teamMember = new AppTeamMember();
                teamMember.setMemState("1");
                teamMember.setMemWorkType("3");
                teamMember.setMemDrId(vo.getDrId());
                teamMember.setMemDrName(vo.getDrName());
                teamMember.setMemTeamid(vo.getTeamId());
                teamMember.setMemTeamName(vo.getTeamName());
                teamMember.setMemState(vo.getMemState());
                sysDao.getServiceDo().add(teamMember);
            } else {
                throw new Exception("该成员以存在此团队中，无须再次添加!");
            }*/
            return teamMember;
        }else{//删除团队成员
           /* List<AppTeamMember> list = findListTeamMember(vo.getDrId(),vo.getTeamId());
            if(list!=null && list.size()>0){
                for(AppTeamMember ls:list){
                    sysDao.getServiceDo().delete(list);
                }
            }*/

            /*AppTeamMember teamMember = findTeamMember(vo.getDrId(), vo.getTeamId());
            if (teamMember != null) {
                sysDao.getServiceDo().delete(teamMember);
            }*/
        }
        return null;
    }

    /**
     * 删除当前签约单
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public AppSignForm webDeleteSign(WebSignUpVo vo) throws Exception {
        if(StringUtils.isBlank(vo.getSignId())){
            throw new Exception("签约单主键不能为空!");
        }
       /* if(StringUtils.isBlank(vo.getSignState())){
            throw new Exception("签约状态不能为空!");
        }*/
        if(StringUtils.isBlank(vo.getSignDelReason())){
            throw new Exception("删除理由不能为空!");
        }
        if(StringUtils.isBlank(vo.getSignDelDate())){
            throw new Exception("删除时间不能为空!");
        }
        AppSignForm signForm = (AppSignForm)sysDao.getServiceDo().find(AppSignForm.class,vo.getSignId());
        if(signForm!=null){
            signForm.setSignState(SignFormType.SC.getValue());
            signForm.setSignDelReason(vo.getSignDelReason());
            signForm.setSignDelType(vo.getSignDelType());
            signForm.setSignDelDate(ExtendDate.getCalendar(vo.getSignDelDate()));
            sysDao.getServiceDo().modify(signForm);
        }
        return signForm;
    }

    /**
     * 修改签约单缴费状态
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public AppSignForm webModifySign(WebSignUpVo vo) throws Exception {
        if(StringUtils.isBlank(vo.getSignId())){
            throw new Exception("签约单主键不能为空!");
        }
        if(StringUtils.isBlank(vo.getSignPayState())){
            throw new Exception("缴费状态不能为空!");
        }
        AppSignForm signForm = (AppSignForm)sysDao.getServiceDo().find(AppSignForm.class,vo.getSignId());
        if(signForm!=null){
            signForm.setSignPayState(vo.getSignPayState());
            sysDao.getServiceDo().modify(signForm);
        }
        return signForm;
    }

    /**
     * 签约单团队变更
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public AppSignForm webChangeSign(WebSignUpVo vo) throws Exception {
        if(StringUtils.isBlank(vo.getSignId())){
            throw new Exception("签约单主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getTeamId())) {
            throw new Exception("团队主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getTeamName())) {
            throw new Exception("团队名称不能为空!");
        }
        if (StringUtils.isBlank(vo.getHospId())) {
            throw new Exception("医院主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getDrId())) {
            throw new Exception("医生主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getAreaCodeCity())) {
            throw new Exception("行政区划（市）不能为空!");
        }
        if(StringUtils.isBlank(vo.getDrAssistantId())){
            throw new Exception("助理主键不能为空!");
        }
        AppSignForm signForm = (AppSignForm)sysDao.getServiceDo().find(AppSignForm.class,vo.getSignId());
        AppSignForm ss = new AppSignForm();
        if(signForm!=null){
            String signstate=signForm.getSignState(); //先定义 存签约状态 0 或2
            signForm.setSignState(SignFormType.YJY.getValue());
            signForm.setSignChangeState(ChangeState.TYBG.getValue());
            signForm.setSignState(SignFormType.BG.getValue());
            sysDao.getServiceDo().modify(signForm);
            AppTeam team = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, vo.getTeamId());
            AppSignBatch batch = new AppSignBatch();//批次
            batch.setBatchCreateDate(Calendar.getInstance());
            batch.setBatchTeamId(vo.getTeamId());
            batch.setBatchTeamName(team.getTeamName());
            batch.setBatchCreatePersid(signForm.getSignPatientId());
            batch.setBatchPatientName(signForm.getPatientName());
            //组织批次号
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, team.getTeamHospId());
            if (dept != null) {
                AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
                if (serial != null) {
                    Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.WEBSTATE.getValue());
                    serial.setSerialNo(bcnum.get("old").toString());
                    sysDao.getServiceDo().modify(serial);
                    batch.setBatchNum(bcnum.get("new").toString());//批次号
                }
            }
            batch.setBatchOperatorId(vo.getDrId());
            AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(vo.getDrId());
            if (drUser != null) {
                batch.setBatchOperatorName(drUser.getDrName());
            }
            batch.setBatchAreaCode(signForm.getSignAreaCode());
            sysDao.getServiceDo().add(batch);
            ss.setSignState(signstate);
            ss.setSignFee(signForm.getSignFee());
            ss.setSignPayType(signForm.getSignPayType());
            ss.setSignYellowState(signForm.getSignYellowState());
            ss.setSignGreenState(signForm.getSignGreenState());
            ss.setSignAreaCode(signForm.getSignAreaCode());
            ss.setSignContractState(signForm.getSignContractState());
            ss.setSignDate(signForm.getSignDate());
            ss.setSignDelDate(signForm.getSignDelDate());
            ss.setSignDelReason(signForm.getSignDelReason());
            ss.setSignDelType(signForm.getSignDelType());
            ss.setSignDieDate(signForm.getSignDieDate());
            ss.setSignDrId(vo.getDrId());
            ss.setSignFromDate(signForm.getSignFromDate());
            ss.setSignHealthGroup(signForm.getSignHealthGroup());
            ss.setSignHospId(signForm.getSignHospId());
            if(dept!=null){
                //组织编码
                AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
                if (serialSign != null) {
                    Map<String,Object> sinum = sysDao.getAppSignformDao().getNum(serialSign.getSerialNo(),SignFormType.WEBSTATE.getValue());
                    serialSign.setSerialNo(sinum.get("old").toString());
                    sysDao.getServiceDo().modify(serialSign);
                    ss.setSignNum(sinum.get("new").toString());//签约编码
                }
            }
            ss.setSignOthnerReason(signForm.getSignOthnerReason());
            ss.setSignPatientAge(signForm.getSignPatientAge());
            ss.setSignPatientGender(signForm.getSignPatientGender());
            ss.setSignPatientIdNo(signForm.getSignPatientIdNo());
            ss.setSignPatientId(signForm.getSignPatientId());
            ss.setSignPayState(signForm.getSignPayState());
            ss.setSignPersGroup(signForm.getSignPersGroup());
            ss.setSignRedState(signForm.getSignRedState());
            ss.setSignServiceA(signForm.getSignServiceA());
            ss.setSignServiceADate(signForm.getSignServiceADate());
            ss.setSignServiceAPayState(signForm.getSignServiceAPayState());
            ss.setSignServiceB(signForm.getSignServiceB());
            ss.setSignServiceBColor(signForm.getSignServiceBColor());
            ss.setSignServiceBDate(signForm.getSignServiceBDate());
            ss.setSignServiceBPayState(signForm.getSignServiceBPayState());
            ss.setSignsJjType(signForm.getSignsJjType());
            ss.setSignSurrenderDate(signForm.getSignSurrenderDate());
            ss.setSignTeamId(vo.getTeamId());
            if (team != null) {
                ss.setSignTeamName(team.getTeamName());
            }
            ss.setSignToDate(signForm.getSignToDate());
            ss.setSignType(signForm.getSignType());
            ss.setSignUrrenderReason(signForm.getSignUrrenderReason());
            ss.setSignUrrenderReasonPatient(signForm.getSignUrrenderReasonPatient());
            ss.setSignWay(signForm.getSignWay());
//                        ss.setUpHpis(s.getUpHpis());
            ss.setSignBatchId(batch.getId());
            // 新字段
            ss.setSignDrAssistantId(vo.getDrAssistantId());
            ss.setSignWebState(signForm.getSignWebState());
            ss.setSignlx(signForm.getSignlx());
            ss.setSignczpay(signForm.getSignczpay());
            ss.setSignzfpay(signForm.getSignzfpay());
            ss.setSigntext(signForm.getSigntext());
            ss.setSignpackageid(signForm.getSignpackageid());
            sysDao.getServiceDo().add(ss);
            AppSignChange signChange = new AppSignChange();
            if(dept!=null){
                AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
                Map<String,Object> sinum = new HashMap<>();
                if (serialSign != null) {
                    sinum = sysDao.getAppSignformDao().getNum(serialSign.getSerialNo(),SignFormType.WEBSTATE.getValue());
                    serialSign.setSerialNo(sinum.get("old").toString());
                    signChange.setChangeNum(sinum.get("new").toString());
                    sysDao.getServiceDo().modify(serialSign);
                }
            }
            signChange.setChangeDrId(vo.getDrId());
            signChange.setChangeTeamId(vo.getTeamId());
            signChange.setChangeDr(signForm.getSignDrId());
            signChange.setChangeTeam(signForm.getSignTeamId());
            signChange.setChangeDate(ExtendDate.getCalendar(vo.getChangeDate()));
            signChange.setChangeUserId(signForm.getSignPatientId());
            signChange.setChangeState(ChangeState.TYBG.getValue());
            signChange.setChangeAgreeDate(ExtendDate.getCalendar(vo.getChangeDate()));
            signChange.setChangeSignId(signForm.getId());

            List<AppLabelGroup> list = sysDao.getServiceDo().loadByPk(AppLabelGroup.class, "labelSignId", signForm.getId());
            if (list != null) {
                for (AppLabelGroup ll : list) {
                    AppLabelGroup lg = new AppLabelGroup();
                    lg.setLabelColor(ll.getLabelColor());
                    lg.setLabelId(ll.getLabelId());
                    lg.setLabelSignId(ss.getId());
                    lg.setLabelTeamId(ss.getSignTeamId());
                    lg.setLabelTitle(ll.getLabelTitle());
                    lg.setLabelType(ll.getLabelType());
                    lg.setLabelValue(ll.getLabelValue());
                    lg.setLabelAreaCode(ss.getSignAreaCode());
                    sysDao.getServiceDo().add(lg);
                }
            }
            List<AppLabelDisease> listD = sysDao.getServiceDo().loadByPk(AppLabelDisease.class, "labelSignId", signForm.getId());
            if (listD != null && listD.size()>0) {
                for (AppLabelDisease ll : listD) {
                    AppLabelDisease lg = new AppLabelDisease();
                    lg.setLabelColor(ll.getLabelColor());
                    lg.setLabelId(ll.getLabelId());
                    lg.setLabelSignId(ss.getId());
                    lg.setLabelTeamId(ss.getSignTeamId());
                    lg.setLabelTitle(ll.getLabelTitle());
                    lg.setLabelType(ll.getLabelType());
                    lg.setLabelValue(ll.getLabelValue());
                    lg.setLabelAreaCode(ss.getSignAreaCode());
                    sysDao.getServiceDo().add(lg);
                }
            }
            List<AppLabelEconomics> ls = sysDao.getServiceDo().loadByPk(AppLabelEconomics.class, "labelSignId", signForm.getId());
            if (ls != null) {
                for (AppLabelEconomics ll : ls) {
                    AppLabelEconomics lg = new AppLabelEconomics();
                    lg.setLabelColor(ll.getLabelColor());
                    lg.setLabelId(ll.getLabelId());
                    lg.setLabelSignId(ss.getId());
                    lg.setLabelTeamId(ss.getSignTeamId());
                    lg.setLabelTitle(ll.getLabelTitle());
                    lg.setLabelType(ll.getLabelType());
                    lg.setLabelValue(ll.getLabelValue());
                    lg.setLabelAreaCode(ss.getSignAreaCode());
                    sysDao.getServiceDo().add(lg);
                }
            }
        }


        return signForm;
    }

    @Override
    public AppServeSetmeal webAddServeMeal(WebServeMealVo vo) throws Exception {
        if(StringUtils.isBlank(vo.getAreaCodeCity())){
            throw new Exception("行政区划（市）不能为空!");
        }
        if(StringUtils.isBlank(vo.getDeptId())){
            throw new Exception("机构主键不能为空!");
        }
        if(StringUtils.isBlank(vo.getDrId())){
            throw new Exception("医生主键不能为空!");
        }
        if(StringUtils.isBlank(vo.getSersmValue())){
            throw new Exception("服务包编号不能为空!");
        }
        if(StringUtils.isBlank(vo.getSersmName())){
            throw new Exception("服务包名称不能为空!");
        }
//        vo.setSersmValue(vo.getStrAre()+vo.getSersmValue());
        AppServeSetmeal meal = sysDao.getAppServeSetmealDao().findByValue(vo.getSersmValue());
        if(meal==null){
            meal = new AppServeSetmeal();
            //服务包变量
            String groupId = "";
            String groupValue = "";
            int totalFee = 0;
            String geFee = "";
            int btFee = 0;
            int resultFee = 0;

            String geId = "";
            String geValue="";

            String objectValue = "";
            String objectTitle = "";
            String objectType = "";

            String mpkValue = "";
            String mpkTitle = "";
            String mpkType = "";

            String book = "";

            if(vo.getGroupVos()!=null && vo.getGroupVos().size()>0){
                for(WebServeGroupVo ll:vo.getGroupVos()){//循环组合数据
                    if(StringUtils.isBlank(ll.getSergValue())){
                        throw new Exception("服务组合编号不能为空!");
                    }
                    if(StringUtils.isBlank(ll.getSeroValue())){
                        throw new Exception("服务对象编号不能为空!");
                    }
                    if(StringUtils.isBlank(ll.getSeroName())){
                        throw new Exception("服务对象名称不能为空!");
                    }
                    /*添加服务对象信息开始*/
//                    ll.setSeroValue(vo.getStrAre()+ll.getSeroValue());
                    AppServeObject sero = sysDao.getAppServeObjectDao().findByValue(ll.getSeroValue());
                    if(sero==null){
                        sero = new AppServeObject();
                        sero.setSeroName(ll.getSeroName());
                        sero.setSeroValue(ll.getSeroValue());
                        sero.setSeroAreaCode(vo.getAreaCodeCity());
                        sero.setSeroFwType(ll.getSeroFwType());
                        sero.setSeroLabelType(ll.getSeroLabelType());
                        sero.setSeroState(ll.getSeroState());
                        sero.setSeroDeptId(vo.getDeptId());
                        sero.setSeroCreateTime(Calendar.getInstance());
                        sero.setSeroCreateId(vo.getDrId());
                        sysDao.getServiceDo().add(sero);
                    }else{
                        sero.setSeroLabelType(ll.getSeroLabelType());
                        sero.setSeroFwType(ll.getSeroFwType());
                        sero.setSeroState(ll.getSeroState());
                        sero.setSeroName(ll.getSeroName());
                        sysDao.getServiceDo().modify(sero);
                    }
                    /*添加服务对象信息结束*/

                    String gpkId = "";
                    String gpkName = "";
                    String gpkValue = "";
                    String gpkType = "";

                    /*添加服务内容信息开始*/
                    if(ll.getPkVos()!=null && ll.getPkVos().size()>0){
                        for(WebServePkVo ls:ll.getPkVos()){
                            if(StringUtils.isBlank(ls.getSerpkName())){
                                throw new Exception("服务内容名称不能为空!");
                            }
                            if(StringUtils.isBlank(ls.getSerpkValue())){
                                throw new Exception("服务编号不能为空!");
                            }
//                            ls.setSerpkValue(vo.getStrAre()+ls.getSerpkValue());
                            AppServePackage pk = sysDao.getAppServePackageDao().findByValue(ls.getSerpkValue());
                            if(pk==null){
                                pk = new AppServePackage();
                                pk.setSerpkValue(ls.getSerpkValue());
                                pk.setSerpkRemark(ls.getSerpkRemark());
                                pk.setSerpkBaseType(ls.getSerpkBaseType());
                                pk.setSerpkOpenState(ls.getSerpkOpenState());
                                if("1".equals(ls.getSerpkOpenState())){
                                    pk.setSerpkTime(ls.getSerpkTime());
                                    pk.setSerpkNum(ls.getSerpkNum());
                                    pk.setSerpkIntervalType(ls.getSerpkIntervalType());
                                }
                                pk.setSerpkName(ls.getSerpkName());
                                pk.setSerpkAreaCode(vo.getAreaCodeCity());
                                pk.setSerpkDeptId(vo.getDeptId());
                                pk.setSerpkCreateTime(Calendar.getInstance());
                                pk.setSerpkCreateId(vo.getDrId());
                                sysDao.getServiceDo().add(pk);
                            }else{
                                pk.setSerpkName(ls.getSerpkName());
                                pk.setSerpkOpenState(ls.getSerpkOpenState());
                                if("1".equals(ls.getSerpkOpenState())){
                                    pk.setSerpkTime(ls.getSerpkTime());
                                    pk.setSerpkNum(ls.getSerpkNum());
                                    pk.setSerpkIntervalType(ls.getSerpkIntervalType());
                                }
                                pk.setSerpkBaseType(ls.getSerpkBaseType());
                                pk.setSerpkRemark(ls.getSerpkRemark());
                                sysDao.getServiceDo().modify(pk);
                            }

                            if(StringUtils.isBlank(gpkId)){
                                gpkId = pk.getId();
                            }else{
                                gpkId += ";"+pk.getId();
                            }

                            if(StringUtils.isBlank(gpkName)){
                                gpkName = pk.getSerpkName();
                            }else{
                                gpkName += ","+pk.getSerpkName();
                            }

                            if(StringUtils.isBlank(gpkValue)){
                                gpkValue = pk.getSerpkValue();
                            }else{
                                gpkValue += ";"+pk.getSerpkValue();
                            }

                            if(StringUtils.isBlank(gpkType)){
                                gpkType = pk.getSerpkBaseType();
                            }else{
                                gpkType += ";"+pk.getSerpkBaseType();
                            }
                        }
                    }
                    /*添加服务内容结束*/

                    /*添加服务组合开始*/
//                    ll.setSergValue(vo.getStrAre()+ll.getSergValue());
                    AppServeGroup group = sysDao.getAppServeGroupDao().findByValue(ll.getSergValue());
                    if(group == null){
                        group = new AppServeGroup();
                        group.setSergValue(ll.getSergValue());
                        group.setSergGroupFee(ll.getSergGroupFee());
                        group.setSergAreaCode(vo.getAreaCodeCity());
                        group.setSergCreateId(vo.getDrId());
                        group.setSergCreateTime(Calendar.getInstance());

                        group.setSergObjectId(sero.getId());
                        group.setSergObjectType(sero.getSeroState());
                        group.setSergObjectTitle(sero.getSeroName());
                        group.setSergObjectValue(sero.getSeroValue());

                        group.setSergPkType(gpkType);
                        group.setSergPkTitle(gpkName);
                        group.setSergPkId(gpkId);
                        group.setSergPkValue(gpkValue);
                        sysDao.getServiceDo().add(group);
                    }else{
                        group.setSergPkId(gpkId);
                        group.setSergPkValue(gpkValue);
                        group.setSergPkTitle(gpkName);
                        group.setSergPkType(gpkType);
                        group.setSergObjectId(sero.getId());
                        group.setSergObjectValue(sero.getSeroValue());
                        group.setSergObjectTitle(sero.getSeroName());
                        group.setSergObjectType(sero.getSeroState());

                    }
                     /*添加服务组合结束*/

                    book += group.getSergObjectTitle()+":"+group.getSergPkTitle()+"<br/>";

                    if(StringUtils.isNotBlank(group.getSergGroupFee())){//组合费用不为空
                        totalFee += Integer.parseInt(group.getSergGroupFee());
                    }
                    if(StringUtils.isBlank(groupId)){
                        groupId = group.getId();
                    }else{
                        groupId += ";"+group.getId();
                    }

                    if(StringUtils.isBlank(groupValue)){
                        groupValue = group.getSergValue();
                    }else{
                        groupValue += ";"+group.getSergValue();
                    }

                    if(StringUtils.isBlank(objectTitle)){
                        objectTitle = group.getSergObjectTitle();
                    }else{
                        objectTitle += ","+group.getSergObjectTitle();
                    }

                    if(StringUtils.isBlank(objectType)){
                        objectType = group.getSergObjectType();
                    }else{
                        objectType += ";"+group.getSergObjectType();
                    }

                    if(StringUtils.isBlank(objectValue)){
                        objectValue = group.getSergObjectValue();
                    }else{
                        objectValue += ";"+group.getSergObjectValue();
                    }

                    if(StringUtils.isBlank(mpkTitle)){
                        mpkTitle = group.getSergPkTitle();
                    }else{
                        mpkTitle += ";"+group.getSergPkTitle();
                    }

                    if(StringUtils.isBlank(mpkType)){
                        mpkType = group.getSergPkType();
                    }else{
                        mpkType += ";"+group.getSergPkType();
                    }

                    if(StringUtils.isBlank(mpkValue)){
                        mpkValue = group.getSergPkValue();
                    }else{
                        mpkValue += ";"+group.getSergPkValue();
                    }
                }
            }
            //循环经济和补贴
            if(vo.getEconGovVos()!=null && vo.getEconGovVos().size()>0){
                meal.setSersmDownState("1");
                for(WebEconGovVo ll:vo.getEconGovVos()){
//                    ll.setEconGovId(vo.getStrAre()+ll.getEconGovId());
                    WebEconAndGov weag = (WebEconAndGov)sysDao.getServiceDo().find(WebEconAndGov.class,ll.getEconGovId());
                    if(weag==null){
                        weag = new WebEconAndGov();
                        weag.setEagAreaCode(vo.getAreaCodeCity());
                        weag.setEagCreateId(vo.getDrId());
                        weag.setEagCreateTime(Calendar.getInstance());
                        weag.setEagDeptId(vo.getDeptId());
                        weag.setId(ll.getEconGovId());
//                        ll.setEconValue(vo.getStrAre()+ll.getEconValue());
                        AppServeEcon econ = sysDao.getAppServeEconDao().findByValue(ll.getEconValue());
                        /*添加经济类型数据开始*/
                        if(econ==null){
                            econ = new AppServeEcon();
                            econ.setEconTitle(ll.getEconName());
                            econ.setEconValue(ll.getEconValue());
                            econ.setEconDeptId(vo.getDeptId());
                            econ.setEconAreaCode(vo.getAreaCodeCity());
                            econ.setEconCreateTime(Calendar.getInstance());
                            econ.setEconCreateId(vo.getDrId());
                            sysDao.getServiceDo().add(econ);
                        }else{
                            econ.setEconTitle(ll.getEconName());
                            sysDao.getServiceDo().modify(econ);
                        }
                        /*添加经济类型数据结束*/
                        weag.setEagEconId(econ.getId());
                        weag.setEagEconTitle(econ.getEconTitle());
                        weag.setEagEconValue(econ.getEconValue());
                        /*添加政府补贴数据开始*/
                        if(ll.getGovVos()!=null && ll.getGovVos().size()>0){
                            String govTitle = "";
                            String govValue = "";
                            String govId = "";
                            for(WebGovVo ls:ll.getGovVos()){
//                                ls.setGovValue(vo.getStrAre()+ls.getGovValue());
                                AppServeGov gov = sysDao.getAppServeGovDao().findByValue(ls.getGovValue());
                                if(gov==null){
                                    gov = new AppServeGov();
                                    gov.setGovMoney(ls.getGovMoney());
                                    gov.setGovDeptId(vo.getDeptId());
                                    gov.setGovAreaCode(vo.getAreaCodeCity());
                                    gov.setGovValue(ls.getGovValue());
                                    gov.setGovTitle(ls.getGovName());
                                    gov.setGovCreateTime(Calendar.getInstance());
                                    gov.setGovCreateId(vo.getDrId());
                                    sysDao.getServiceDo().add(gov);
                                }else{
                                    gov.setGovTitle(ls.getGovName());
                                    gov.setGovMoney(ls.getGovMoney());
                                    sysDao.getServiceDo().modify(gov);
                                }
                                if(StringUtils.isNotBlank(gov.getGovMoney())){//
                                    btFee += Integer.parseInt(gov.getGovMoney());
                                }
                                if(StringUtils.isBlank(govId)){
                                    govId = gov.getId();
                                }else{
                                    govId += ";"+gov.getId();
                                }
                                if(StringUtils.isBlank(govTitle)){
                                    govTitle = gov.getGovTitle();
                                }else{
                                    govTitle += ","+gov.getGovTitle();
                                }
                                if(StringUtils.isBlank(govValue)){
                                    govValue = gov.getGovValue();
                                }else{
                                    govValue += ";"+gov.getGovValue();
                                }
                            }
                            weag.setEagGovId(govId);
                            weag.setEagGovTitle(govTitle);
                            weag.setEagGovValue(govValue);
                            sysDao.getServiceDo().add(weag);
                        }
                    }
                    if(StringUtils.isBlank(geFee)){
                        int reFee = totalFee-btFee;
                        geFee = weag.getId()+":"+reFee;
                        resultFee+=reFee;
                    }else{
                        int reFee = totalFee-btFee;
                        geFee += ";"+weag.getId()+":"+reFee;
                        resultFee+=reFee;
                    }

                    if(StringUtils.isBlank(geId)){
                        geId = weag.getId();
                    }else{
                        geId+=";"+weag.getId();
                    }

                    if(StringUtils.isBlank(geValue)){
                        geValue = weag.getEagEconValue();
                    }else{
                        geValue += ";"+weag.getEagEconValue();
                    }
                }
            }
            /*添加套餐数据开始*/
            meal.setSersmValue(vo.getSersmValue());
            meal.setSersmName(vo.getSersmName());
            meal.setSersmYxTimeType(vo.getSersmYxTimeType());
            if("4".equals(vo.getSersmYxTimeType())){
                meal.setSersmStartTime(vo.getSersmStartTime());
                meal.setSersmEndTime(vo.getSersmEndTime());
            }
            meal.setSersmBgDr(vo.getSersmBgDr());
            meal.setSersmJcState(vo.getSersmJcState());

            meal.setSersmGroupId(groupId);
            meal.setSersmGroupValue(groupValue);
            meal.setSersmObjectValue(objectValue);
            meal.setSersmObjectType(objectType);
            meal.setSersmObjectTitle(objectTitle);

            meal.setSersmBook(book);
            meal.setSersmPkType(mpkType);
            meal.setSersmPkTitle(mpkTitle);
            meal.setSersmPkValue(mpkValue);

            meal.setSersmJjId(geId);
            meal.setSersmJjType(geValue);

            meal.setSersmAreaCode(vo.getAreaCodeCity());
            meal.setSersmCreateDept(vo.getDeptId());
            meal.setSersmCreateId(vo.getDrId());
            meal.setSersmCreateTime(Calendar.getInstance());
            meal.setSersmOneFee(geFee);
            meal.setSersmTotalFee(String.valueOf(totalFee));
            meal.setSersmTotalOneFee(String.valueOf(resultFee));
            sysDao.getServiceDo().add(meal);
        }
        return meal;
    }

    /**
     * 转签数据
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public AppSignForm webGotoSignUp(WebSignUpVo vo) throws Exception {
        String age = null;//年龄
        if (StringUtils.isBlank(vo.getHospId())) {
            throw new Exception("医院主键不能为空!");
        }

        if(StringUtils.isBlank(vo.getDrId())){
            throw new Exception("医生主键不能为空!");
        }

        if(StringUtils.isBlank(vo.getTeamId())){
            throw new Exception("团队主键不能为空!");
        }

        if (StringUtils.isBlank(vo.getAreaCodeProvince())) {
            throw new Exception("行政区划(省)不能为空!");
        }

        if (StringUtils.isBlank(vo.getAreaCodeCity())) {
            throw new Exception("行政区划（市）不能为空!");
        }

        //判断是否有机构
        AppHospDept dept =(AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,vo.getHospId());
        if(dept==null){
            throw new Exception("查无机构数据,请先上传机构数据!");
        }
        //判断是否有医生信息
        AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,vo.getDrId());
        if(drUser==null){
            throw new Exception("查无医生数据,请先上传医生数据!");
        }

        //判断是否有团队信息
        AppTeam team = (AppTeam) sysDao.getServiceDo().find(AppTeam.class,vo.getTeamId());
        if(team==null){
            throw new Exception("查无团队数据,请先上传团队数据!");
        }

        /*if (StringUtils.isBlank(vo.getPatientCard())) {
            throw new Exception("居民社保卡不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientAddress())) {
            throw new Exception("居民地址不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientTel())) {
            throw new Exception("居民手机号不能为空!");
        } else {
            if (vo.getPatientTel().length() != 11) {
                throw new Exception("居民手机号长度有误!");
            }
            if (!AccountValidatorUtil.isMobile(vo.getPatientTel())) {
                throw new Exception("居民手机号格式错误,请传入正确格式!");
            }
        }
        if (StringUtils.isBlank(vo.getPatientProvince())) {
            throw new Exception("省不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientCity())) {
            throw new Exception("市不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientArea())) {
            throw new Exception("区不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientStreet())) {
            throw new Exception("街道不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientNeighborhoodCommittee())) {
            throw new Exception("居委会不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientjmda())) {
            throw new Exception("居民建康档案不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientjtda())) {
            throw new Exception("居民家庭档案不能为空!");
        }*/
        if (StringUtils.isBlank(vo.getSignDate())) {
            throw new Exception("签约时间不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignFromDate())) {
            throw new Exception("有效开始时间不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignToDate())) {
            throw new Exception("有效结束时间不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignPayState())) {
            throw new Exception("缴费状态不能为空!");
        }
       /* if (StringUtils.isBlank(vo.getSignState())) {
            throw new Exception("签约状态不能为空!");
        }*/
        if (StringUtils.isBlank(vo.getSignPersGroup())) {
            throw new Exception("服务人群不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignsJjType())) {
            throw new Exception("经济类型不能为空!");
        }

//        AppSignForm signForm = sysDao.getAppSignformDao().findSignFormByUser(vo.getPatientId());
//        if (signForm != null) {
//            throw new Exception("签约单已存在,请不要重复上传签约单!");
//        }
        //转签数据
        AppSignBatch batch = new AppSignBatch();//批次
        AppSignForm form = new AppSignForm();//签约单
        AppSignForm oldSignForm = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, vo.getSignFormId());
        AppPatientUser uservo = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, vo.getPatientId());//患者
        if(uservo == null){
            throw new Exception("查无居民数据,请先上传居民数据!");
        }
        age = uservo.getPatientAge();
        batch.setBatchCreateDate(Calendar.getInstance());
        batch.setBatchTeamId(team.getId());
        batch.setBatchTeamName(team.getTeamName());
        batch.setBatchCreatePersid(uservo.getId());
        batch.setBatchPatientName(uservo.getPatientName());
        //组织批次号
        if (dept != null && dept.getHospAreaCode() != null) {
            AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
            if (serial != null) {
                Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.WEBSTATE.getValue());
                serial.setSerialNo(bcnum.get("old").toString());
                sysDao.getServiceDo().modify(serial);
                batch.setBatchNum(bcnum.get("new").toString());//批次号
            }
            AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
            if (serialSign != null) {
                Map<String,Object> sinum = sysDao.getAppSignformDao().getNum(serialSign.getSerialNo(),SignFormType.WEBSTATE.getValue());
                serialSign.setSerialNo(sinum.get("old").toString());
                sysDao.getServiceDo().modify(serialSign);
                form.setSignNum(sinum.get("new").toString());//签约编码
            }
        }
        //有操作人
        if(StringUtils.isNotBlank(vo.getDrOperatorId())){
            //判断操作人是否有数据
            AppDrUser drUserO = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,vo.getDrOperatorId());
            if(drUserO == null){
                throw new Exception("查无医生数据,请先上传医生数据!");
            }else{
                batch.setBatchOperatorId(drUserO.getId());
                batch.setBatchOperatorName(drUserO.getDrName());
            }
        }else{
            batch.setBatchOperatorId(drUser.getId());
            batch.setBatchOperatorName(drUser.getDrName());
        }
        //判断是否需要助理
        if(StringUtils.isNotBlank(vo.getDrAssistantId())){
            AppDrUser drUserA = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,vo.getDrAssistantId());
            if(drUserA == null){
                throw new Exception("查无医生数据,请先上传医生数据!");
            }else{
                form.setSignDrAssistantId(drUserA.getId());
            }
        }

        batch.setBatchAreaCode(dept.getHospAreaCode());
        sysDao.getServiceDo().add(batch);
        form.setSignBatchId(batch.getId());
        form.setSignAreaCode(dept.getHospAreaCode());
        form.setSignAreaCode(dept.getHospAreaCode());
        form.setSignHospId(dept.getId());
        form.setSignPatientId(vo.getPatientId());//居民主键
        form.setSignDate(ExtendDate.getCalendar(vo.getSignDate()));//签约申请时间
        form.setSignType("1");//1家庭签约
        form.setSignTeamId(team.getId());//团队主键
        form.setSignTeamName(team.getTeamName());//团队名称
        form.setSignWay("2");//医生代签
        if(StringUtils.isNotBlank(vo.getSignState())){
            form.setSignState(vo.getSignState());
        }else{
            form.setSignState(SignFormType.ZQ.getValue());//缴费状态
        }
        form.setSignPatientGender(vo.getPatientGender());//性别
        if (StringUtils.isNotBlank(age)) {
            form.setSignPatientAge(Integer.parseInt(age));//年龄
        }
        form.setSignPatientIdNo(vo.getPatientIdno());//身份证
        form.setSignFromDate(ExtendDate.getCalendar(vo.getSignFromDate()));
        form.setSignToDate(ExtendDate.getCalendar(vo.getSignToDate()));
        form.setSignDrId(drUser.getId());//医生主键
        form.setSignContractState("0");//1是 0否
        form.setSignGreenState("0");//1是 0否
        form.setSignYellowState("0");//1是 0否
        form.setSignRedState("0");//1是 0否
        form.setUpHpis("2");//数据来源web
        if(StringUtils.isNotBlank(vo.getSersmValue())){
            String[] strs = vo.getSersmValue().split(";");
            String mealIds = "";
            for(String str:strs){
                AppServeSetmeal meal = sysDao.getAppServeSetmealDao().findByValue(str);
                if(meal==null){
                    throw new Exception("查无服务包数据,请先上传服务包数据!");
                }else{
                    if(StringUtils.isBlank(mealIds)){
                        mealIds = meal.getId();
                    }else{
                        mealIds += ";"+meal.getId();
                    }
                }
            }
            form.setSignpackageid(mealIds);
        }
        //健康情况
        form.setSignHealthGroup(oldSignForm.getSignHealthGroup());
        sysDao.getServiceDo().add(form);

        //服务人群
        if (StringUtils.isNotBlank(vo.getSignPersGroup())) {
            String[] persGroup = vo.getSignPersGroup().split(",");
            if (persGroup != null && persGroup.length > 0) {
                String areaCode = dept.getHospAreaCode();
                form.setSignPersGroup(persGroup[0]);//支持之前版
                sysDao.getAppLabelGroupDao().addLabel(persGroup, form.getId(), form.getSignTeamId(), areaCode, LabelManageType.FWRQ.getValue());
            }
        }
        //经济类型
        if (StringUtils.isNotBlank(vo.getSignsJjType())) {
            String[] jjTypes = vo.getSignsJjType().split(",");
            if (jjTypes != null && jjTypes.length > 0) {
                String areaCode = dept.getHospAreaCode();
                form.setSignsJjType(jjTypes[0]);//支持之前版
                sysDao.getAppLabelGroupDao().addLabel(jjTypes, form.getId(), form.getSignTeamId(), areaCode, LabelManageType.JJLX.getValue());
            }
        }

        //疾病类型
        if (StringUtils.isNotBlank(vo.getSignDiseaseGroup())) {
            String[] disease = vo.getSignDiseaseGroup().split(",");
            if (disease != null && disease.length > 0) {
                String areaCode = dept.getHospAreaCode();
                sysDao.getAppLabelGroupDao().addLabel(disease, form.getId(), form.getSignTeamId(), areaCode, LabelManageType.JBLX.getValue());
            }
        }
        oldSignForm.setSignRenewOrGoToSignDate(Calendar.getInstance());
        oldSignForm.setSignGoToSignState("1");//转签
        if (oldSignForm.getSignTeamId().equals(form.getSignTeamId()) && !oldSignForm.getSignDrId().equals(form.getSignDrId())) {
            oldSignForm.setSignGotoSignType("1");//医生转签
        } else {
            oldSignForm.setSignGotoSignType("2");
        }
        sysDao.getServiceDo().modify(oldSignForm);

        AppGotoSignRecord gotoSign = new AppGotoSignRecord();
        gotoSign.setGtsOldAreaCode(oldSignForm.getSignAreaCode());
        gotoSign.setGtsCreateTime(Calendar.getInstance());
        gotoSign.setGtsOldDrId(oldSignForm.getSignDrId());
        gotoSign.setGtsOldHospId(oldSignForm.getSignHospId());
        gotoSign.setGtsOldSignId(oldSignForm.getId());
        gotoSign.setGtsOldTeamId(oldSignForm.getSignTeamId());
        gotoSign.setGtsPatientId(oldSignForm.getSignPatientId());
        gotoSign.setGtsReasonValue(vo.getReason());
        gotoSign.setGtsTeamId(form.getSignTeamId());
        gotoSign.setGtsSignId(form.getId());
        gotoSign.setGtsHospId(form.getSignHospId());
        gotoSign.setGtsDrId(form.getSignDrId());
        gotoSign.setGtsAreaCode(form.getSignAreaCode());
        gotoSign.setGtsSignState("1");
        sysDao.getServiceDo().add(gotoSign);
        if (StringUtils.isNotBlank(vo.getReason())) {
            String[] ss = vo.getReason().split(";");
            for (String s : ss) {
                AppGotosignFb tt = new AppGotosignFb();
                tt.setGsId(gotoSign.getId());
                tt.setGsReasonType(s);
                sysDao.getServiceDo().add(tt);
            }
        }
        return form;
    }


    public List<AppTeamMember> findListTeamMember(String drId, String teamId) throws Exception {
        Map<String, Object> map = new HashedMap();
        String sql = "SELECT\n" +
                "\ta.*\n" +
                "FROM\n" +
                "\tAPP_TEAM_MEMBER a\n" +
                "WHERE\n" +
                "\ta.MEM_DR_ID =:MEM_DR_ID \n" +
                "AND a.MEM_TEAMID =:MEM_TEAMID ";
        map.put("MEM_DR_ID", drId);
        map.put("MEM_TEAMID", teamId);
        List<AppTeamMember> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppTeamMember.class);
        return ls;
    }

    /**
     * 续签数据上传
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public AppSignForm webContinueSign(WebSignUpVo vo) throws Exception {
        if(StringUtils.isBlank(vo.getHospId())){
            throw new Exception("机构主键不能为空!");
        }
        if(StringUtils.isBlank(vo.getDrId())){
            throw new Exception("医生主键不能为空!");
        }
        if(StringUtils.isBlank(vo.getTeamId())){
            throw new Exception("团队主键不能为空!");
        }
        if(StringUtils.isBlank(vo.getSignFormId())){
            throw new Exception("签约单主键不能为空!");
        }
        if(StringUtils.isBlank(vo.getAreaCodeProvince())){
            throw new Exception("行政区划(省)不能为空!");
        }
        if(StringUtils.isBlank(vo.getAreaCodeCity())){
            throw new Exception("行政区划(市)不能为空!");
        }

        if(StringUtils.isBlank(vo.getSignPayState())){
            throw new Exception("缴费状态不能为空!");
        }
        if(StringUtils.isBlank(vo.getPatientId())){
            throw new Exception("居民主键不能为空!");
        }
       /* if(StringUtils.isBlank(vo.getPatientName())){
            throw new Exception("居民名字不能为空!");
        }
        if(StringUtils.isBlank(vo.getPatientGender())){
            throw new Exception("居民性别不能为空!");
        }
        if(StringUtils.isBlank(vo.getPatientIdno())){
            throw new Exception("居民身份证号不能为空!");
        }
        if(StringUtils.isBlank(vo.getPatientCard())){
            throw new Exception("居民社保卡号不能为空!");
        }
        if(StringUtils.isBlank(vo.getPatientAddress())){
            throw new Exception("居民住址不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientTel())) {
            throw new Exception("居民手机号不能为空!");
        } else {
            if (vo.getPatientTel().length() != 11) {
                throw new Exception("居民手机号长度有误!");
            }
            if (!AccountValidatorUtil.isMobile(vo.getPatientTel())) {
                throw new Exception("居民手机号格式错误,请传入正确格式!");
            }
        }*/
        if (StringUtils.isBlank(vo.getPatientProvince())){
            throw new Exception("省不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientCity())){
            throw new Exception("市不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientArea())){
            throw new Exception("区不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientStreet())){
            throw new Exception("街道不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignDate())){
            throw new Exception("签约时间不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignFromDate())){
            throw new Exception("有效开始时间不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignToDate())){
            throw new Exception("有效结束时间不能为空!");
        }
       /* if (StringUtils.isBlank(vo.getSignState())){
            throw new Exception("签约状态不能为空!");
        }*/
        if (StringUtils.isBlank(vo.getSignPersGroup())){
            throw new Exception("服务人群不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignsJjType())){
            throw new Exception("经济类型不能为空!");
        }



        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,vo.getHospId());
        if(dept == null){
            throw new Exception("查无此机构数据,请先上传机构数据!");
        }
        AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,vo.getDrId());
        if(drUser == null){
            throw new Exception("查无此医生数据,请先上传医生数据");
        }
        AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,vo.getTeamId());
        if(team == null){
            throw new Exception("查无此团队数据,请先上传团队数据");
        }
        AppSignForm form = new AppSignForm();
        AppSignBatch batch = new AppSignBatch();
        //判断是否有操作人数据
        if(StringUtils.isNotBlank(vo.getDrOperatorId())){
            AppDrUser drUserO = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,vo.getDrOperatorId());
            if(drUserO == null){
                throw new Exception("查无操作医生数据,请先上传操作医生数据!");
            }else{
                batch.setBatchOperatorId(drUserO.getId());
                batch.setBatchOperatorName(drUserO.getDrName());
            }
        }
        //判断是否有助理医生数据
        if(StringUtils.isNotBlank(vo.getDrAssistantId())){
            AppDrUser drUserA = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,vo.getDrAssistantId());
            if(drUserA == null){
                throw new Exception("查无助理医生数据,请先上传助理医生数据!");
            }else{
                form.setSignDrAssistantId(drUserA.getId());
            }
        }
        form.setSignPayState(vo.getSignPayState());

        AppSignForm oldForm = (AppSignForm)sysDao.getServiceDo().find(AppSignForm.class,vo.getSignFormId());

        AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, vo.getPatientId());
        batch.setBatchCreateDate(Calendar.getInstance());
        batch.setBatchTeamId(team.getId());
        batch.setBatchTeamName(team.getTeamName());
        batch.setBatchCreatePersid(user.getId());
        batch.setBatchPatientName(user.getPatientName());
        //组织批次号
        if (dept != null && dept.getHospAreaCode() != null) {
            AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
            if (serial != null) {
                Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.WEBSTATE.getValue());
                serial.setSerialNo(bcnum.get("old").toString());
                sysDao.getServiceDo().modify(serial);
                batch.setBatchNum(bcnum.get("new").toString());//批次号
            }
            AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
            if (serialSign != null) {
                Map<String,Object> sinum = sysDao.getAppSignformDao().getNum(serialSign.getSerialNo(),SignFormType.WEBSTATE.getValue());
                serialSign.setSerialNo(sinum.get("old").toString());
                sysDao.getServiceDo().modify(serialSign);
                form.setSignNum(sinum.get("new").toString());//签约编码
            }
        }

        batch.setBatchAreaCode(dept.getHospAreaCode());
        sysDao.getServiceDo().add(batch);
        String formCal = ExtendDate.getYMD_h_m_s(oldForm.getSignToDate());
        Calendar call = ExtendDate.getCalendar(formCal);
        call.add(Calendar.DATE,1);
        form.setSignFromDate(call);
        form.setSignState(SignFormType.XQ.getValue());//续签
        String oldCal = ExtendDate.getYMD_h_m_s(oldForm.getSignToDate());
        Calendar cal = ExtendDate.getCalendar(oldCal);
        cal.add(Calendar.YEAR, 1);
        form.setSignToDate(cal);

        form.setSignPatientId(user.getId());
        form.setSignPatientAge(Integer.parseInt(user.getPatientAge()));
        form.setSignPatientGender(user.getPatientGender());
        form.setSignPatientIdNo(user.getPatientIdno());
        form.setSignPayState(vo.getSignPayState());//0：未缴费 1:已缴费
        form.setSignType(oldForm.getSignType());//1家庭签约
        form.setSignBatchId(batch.getId());
        form.setSignPersGroup(oldForm.getSignPersGroup());
        form.setSignHealthGroup(oldForm.getSignHealthGroup());
        form.setSignTeamId(team.getId());
        form.setSignTeamName(team.getTeamName());
        form.setSignHospId(dept.getId());
        form.setSignAreaCode(dept.getHospAreaCode());
        form.setSignWay("0");//个人发起签约
        form.setSignDate(ExtendDate.getCalendar(vo.getSignDate()));
        form.setSignDrId(oldForm.getSignDrId());//绑定医生
        form.setSignServiceA(oldForm.getSignServiceA());
        form.setSignServiceADate(oldForm.getSignServiceADate());
        form.setSignServiceAPayState(oldForm.getSignServiceAPayState());
        form.setSignServiceB(oldForm.getSignServiceB());
        form.setSignServiceBDate(oldForm.getSignServiceBDate());
        form.setSignServiceBPayState(oldForm.getSignServiceBPayState());
        form.setSignServiceBColor(oldForm.getSignServiceBColor());
        form.setSignContractState("0");//是否已续约 1是 0否
        form.setSignGreenState("0");//1是 0否
        form.setSignYellowState("0");//1是 0否
        form.setSignRedState("0");//1是 0否
        form.setSignsJjType(oldForm.getSignsJjType());
        form.setSignChangeState("0");
        form.setSignRenewState("0");
        form.setSignGoToSignState("0");
        if(StringUtils.isNotBlank(vo.getSersmValue())){
            String[] strs = vo.getSersmValue().split(";");
            String mealIds = "";
            for(String str:strs){
                AppServeSetmeal meal = sysDao.getAppServeSetmealDao().findByValue(str);
                if(meal==null){
                    throw new Exception("查无服务包数据,请先上传服务包数据!");
                }else {
                    if(StringUtils.isBlank(mealIds)){
                        mealIds = meal.getId();
                    }else{
                        mealIds += ";"+meal.getId();
                    }
                }

            }
            form.setSignpackageid(mealIds);
            if(StringUtils.isNotBlank(mealIds)){
                String text = sysDao.getAppSignformDao().findPkRemark(mealIds);
                form.setSigntext(text);
            }
        }
        sysDao.getServiceDo().add(form);
        oldForm.setSignGoToSignState("2");
        oldForm.setSignRenewOrGoToSignDate(Calendar.getInstance());
//        oldForm.setSignContractState("1");//是否已续约 1是 0否
        sysDao.getServiceDo().modify(oldForm);
        //服务人群signPersGroup
        if(StringUtils.isNotBlank(vo.getSignPersGroup())){
            String[] strs = vo.getSignPersGroup().split(";");
        }
        List<AppLabelGroup> labelList = sysDao.getServiceDo().loadByPk(AppLabelGroup.class, "labelSignId", oldForm.getId());
        if(labelList!=null && labelList.size()>0){
            for (AppLabelGroup g : labelList) {
                AppLabelGroup alg = new AppLabelGroup();
                alg.setLabelId(g.getLabelId());
                alg.setLabelSignId(form.getId());
                alg.setLabelTeamId(team.getId());
                alg.setLabelTitle(g.getLabelTitle());
                alg.setLabelValue(g.getLabelValue());
                alg.setLabelType(g.getLabelType());
                alg.setLabelColor(g.getLabelColor());
                alg.setLabelAreaCode(form.getSignAreaCode());
                sysDao.getServiceDo().add(alg);
            }
        }
        //疾病类型
        List<AppLabelDisease> labelDiseases = sysDao.getServiceDo().loadByPk(AppLabelDisease.class,"labelSignId",oldForm.getId());
        if(labelDiseases!=null && labelDiseases.size()>0){
            for(AppLabelDisease g:labelDiseases){
                AppLabelDisease alg = new AppLabelDisease();
                alg.setLabelId(g.getLabelId());
                alg.setLabelSignId(form.getId());
                alg.setLabelTeamId(team.getId());
                alg.setLabelTitle(g.getLabelTitle());
                alg.setLabelValue(g.getLabelValue());
                alg.setLabelType(g.getLabelType());
                alg.setLabelColor(g.getLabelColor());
                alg.setLabelAreaCode(form.getSignAreaCode());
                sysDao.getServiceDo().add(alg);
            }
        }
        //经济类型
        List<AppLabelEconomics> listE = sysDao.getServiceDo().loadByPk(AppLabelEconomics.class,"labelSignId",oldForm.getId());
        if(listE!=null && listE.size()>0){
            for(AppLabelEconomics g:listE){
                AppLabelEconomics alg = new AppLabelEconomics();
                alg.setLabelId(g.getLabelId());
                alg.setLabelSignId(form.getId());
                alg.setLabelTeamId(team.getId());
                alg.setLabelTitle(g.getLabelTitle());
                alg.setLabelValue(g.getLabelValue());
                alg.setLabelType(g.getLabelType());
                alg.setLabelColor(g.getLabelColor());
                alg.setLabelAreaCode(form.getSignAreaCode());
                sysDao.getServiceDo().add(alg);
            }
        }
        return form;
    }

    @Override
    public AppSignForm gairuiSignUp(WebSignUpVo vo) throws Exception {
        WebHospDept webHospDept = null;
        WebDrUser webDrUser = null;
        AppDrUser drBatchOperator = null;//操作者
        AppDrUser drAssistant = null;//助理
        AppTeamMember teamMember = null;//子团队
        WebTeam webTeam = null;//团队
        AppPatientUser webPatientUser = null;//居民
        String patientId = vo.getPatientId();
        String teamId = vo.getTeamId();
        String drId = vo.getDrId();
        String age = null;//年龄
//        if (StringUtils.isBlank(vo.getAreaCodeProvince())) {
//            throw new Exception("行政区划(省)不能为空!");
//        }
//        if (StringUtils.isBlank(vo.getAreaCodeCity())) {
//            throw new Exception("行政区划（市）不能为空!");
//        }
        if (StringUtils.isBlank(vo.getHospId())) {
            throw new Exception("医院主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getDrId())) {
            throw new Exception("医生主键不能为空!");
        }
//        if (StringUtils.isBlank(vo.getHospOperatorId())) {
//            throw new Exception("操作医生医院主键不能为空!");
//        }
        if (StringUtils.isBlank(vo.getTeamId())) {
            throw new Exception("团队主键不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientName())) {
            throw new Exception("居民名字不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientIdno())) {
            throw new Exception("居民身份证号不能为空!");
        }else{
            String resultIdNo = CardUtil.IDCardValidate(vo.getPatientIdno().toLowerCase());
            if(StringUtils.isNotBlank(resultIdNo)){
                throw new Exception(resultIdNo);
            }
        }
        if (StringUtils.isBlank(vo.getPatientCard())) {
            throw new Exception("居民社保卡不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientAddress())) {
            throw new Exception("居民地址不能为空!");
        }
        if (StringUtils.isBlank(vo.getPatientTel())) {
            throw new Exception("居民手机号不能为空!");
        } else {
            if (vo.getPatientTel().length() < 6) {
                throw new Exception("居民手机号长度有误!");
            }
            if (!AccountValidatorUtil.isMobile(vo.getPatientTel())) {
                throw new Exception("居民手机号格式错误,请传入正确格式!");
            }
        }
        if (StringUtils.isBlank(vo.getPatientNeighborhoodCommittee())) {
            throw new Exception("居委会不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignDate())) {
            throw new Exception("签约时间不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignFromDate())) {
            throw new Exception("有效开始时间不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignToDate())) {
            throw new Exception("有效结束时间不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignPayState())) {
            throw new Exception("缴费状态不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignPersGroup())) {
            throw new Exception("服务人群不能为空!");
        }
        if (StringUtils.isBlank(vo.getSignsJjType())) {
            throw new Exception("经济类型不能为空!");
        }
        if(StringUtils.isBlank(vo.getSersmValue())){
            throw new Exception("服务包编码不能为空!");
        }
        String cityCode = AreaUtils.getAreaCode(vo.getPatientNeighborhoodCommittee(),"2");
        if(AddressType.PTS.getValue().equals(cityCode) && vo.getHospId().indexOf("_")==-1){
            vo.setHospId("pt_"+vo.getHospId());
//            vo.setDrId("pt_"+vo.getDrId());
        }else if(AddressType.SMS.getValue().equals(cityCode) && vo.getHospId().indexOf("_")==-1){
            vo.setHospId("sm_"+vo.getHospId());
//            vo.setDrId("sm_"+vo.getDrId());
        }else if(AddressType.QZS.getValue().equals(cityCode) && vo.getHospId().indexOf("_")==-1){
            vo.setHospId("qz_"+vo.getHospId());
//            vo.setDrId("qz_"+vo.getDrId());
        }else if(AddressType.ZZS.getValue().equals(cityCode) && vo.getHospId().indexOf("_")==-1){
            vo.setHospId("zz_"+vo.getHospId());
//            vo.setDrId("zz_"+vo.getDrId());
        }else if(AddressType.NPS.getValue().equals(cityCode) && vo.getHospId().indexOf("_")==-1){
            vo.setHospId("np_"+vo.getHospId());
//            vo.setDrId("np_"+vo.getDrId());
        }
        //医院信息 根据医院id查询是否存在，没有自动创建医院
        AppHospDept appHospDept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, vo.getHospId());
        if (appHospDept == null) {
            throw new Exception("查无机构数据!");
        }
        //医生信息 根据医生id查询是否存在，没有自动创建医生
        AppDrUser dr = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, vo.getDrId());
        if (dr == null) {
            throw new Exception("查无医生数据!");
        }

        if(StringUtils.isNotBlank(vo.getDrOperatorId())){
            drBatchOperator = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, vo.getDrOperatorId());
            if (drBatchOperator == null) {
                throw new Exception("查无操作医生数据!");
            }
        }

        //助理
        if (StringUtils.isNotBlank(vo.getDrAssistantId())) {
            drAssistant = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, vo.getDrAssistantId());
            if (drAssistant == null) {
                throw new Exception("查无助理医生数据!");
            }
        }
        //团队 医生id查询是否已有团队
        AppTeam appTeam = (AppTeam) this.sysDao.getServiceDo().find(AppTeam.class,teamId);
        if(appTeam == null){
            throw new Exception("查无团队数据!");
        }
        //居民 根据居民身份证查询是否存在，没有自动创建居民
        List<AppPatientUser> puser = sysDao.getServiceDo().loadByPk(AppPatientUser.class, "patientIdno", vo.getPatientIdno());
        if (puser != null && !puser.isEmpty()) {
            age = puser.get(0).getPatientAge();
            vo.setPatientId(puser.get(0).getId());//用户存在 替换成当前用户
            vo.setPatientName(puser.get(0).getPatientName());
            vo.setPatientGender(puser.get(0).getPatientGender());

            AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(puser.get(0).getId());
            if(form != null){
                throw new Exception("签约单已存在，不可重复提交！");
            }
        } else {
            webPatientUser = new AppPatientUser();
            webPatientUser.setPatientName(vo.getPatientName());//姓名
            webPatientUser.setPatientIdno(vo.getPatientIdno());//身份证
            webPatientUser.setPatientCard(vo.getPatientCard());//社保卡
            webPatientUser.setPatientTel(vo.getPatientTel());//电话
            webPatientUser.setPatientAddress(vo.getPatientAddress());//地址
            if (StringUtils.isNotBlank(webPatientUser.getPatientName())) {
                webPatientUser.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(webPatientUser.getPatientName()));
            }
            //身份证
            Map<String, Object> idNos = new HashMap<String, Object>();
            if (vo.getPatientIdno().trim().length() == 18) {
                idNos = CardUtil.getCarInfo(vo.getPatientIdno().trim());
            } else if (vo.getPatientIdno().trim().length() == 15) {
                idNos = CardUtil.getCarInfo15W(vo.getPatientIdno().trim());
            }
            //出生日期
            if (idNos.get("birthday") != null) {
                webPatientUser.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
            }
            //年龄
            if (idNos.get("age") != null) {
                if (webPatientUser.getPatientBirthday() != null) {
                    webPatientUser.setPatientAge(AgeUtil.getAgeYear(webPatientUser.getPatientBirthday()));
                } else {
                    webPatientUser.setPatientAge(idNos.get("age").toString());
                }

            }
            if (idNos.get("sex") != null) {
                webPatientUser.setPatientGender(idNos.get("sex").toString());//性别
            }
            webPatientUser.setPatientProvince(AreaUtils.getAreaCode(vo.getPatientNeighborhoodCommittee(),"1")+"0000000000");//省
            webPatientUser.setPatientCity(AreaUtils.getAreaCode(vo.getPatientNeighborhoodCommittee(),"2")+"00000000");//市
            webPatientUser.setPatientArea(AreaUtils.getAreaCode(vo.getPatientNeighborhoodCommittee(),"3")+"000000");//区
            webPatientUser.setPatientStreet(AreaUtils.getAreaCode(vo.getPatientNeighborhoodCommittee(),"4")+"000");//街道
            webPatientUser.setPatientNeighborhoodCommittee(vo.getPatientNeighborhoodCommittee());
//            webPatientUser.setPatientjmda(vo.getPatientjmda());//居民档案
//            webPatientUser.setPatientjtda(vo.getPatientjtda());//家庭档案
            webPatientUser.setPatientUpHpis(UserUpHpisType.WEIJIHUO.getValue());//用户未激活
            webPatientUser.setPatientState(CommonEnable.QIYONG.getValue());
            webPatientUser.setPatientHealthy(CommonEnable.JINYONG.getValue());
            webPatientUser.setPatientJgState(UserJgType.WEISHEZHI.getValue());
            webPatientUser.setPatientEaseState(UserJgType.WEISHEZHI.getValue());
            webPatientUser.setPatientPwd(Md5Util.MD5(webPatientUser.getPatientTel().substring(webPatientUser.getPatientTel().length() - 6, webPatientUser.getPatientTel().length())));
            sysDao.getServiceDo().add(webPatientUser);
        }

        //签约----------------------------------------------
        System.out.println("patientId--" + patientId + "--teamid--" + teamId + "--drId--" + drId + "---hospId---" + vo.getHospId());
        AppSignBatch batch = new AppSignBatch();//批次
        AppSignForm form = new AppSignForm();//签约单
        batch.setBatchCreateDate(Calendar.getInstance());
        batch.setBatchTeamId(teamId);
        batch.setBatchTeamName(appTeam.getTeamName());
        batch.setBatchCreatePersid(vo.getPatientId());
        batch.setBatchPatientName(vo.getPatientName());
        batch.setBatchOperatorId(drBatchOperator.getId());
        batch.setBatchOperatorName(drBatchOperator.getDrName());
        //组织批次号
        String cityAreaCode = webPatientUser.getPatientCity();
        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, vo.getHospId());
        if (StringUtils.isNotBlank(cityAreaCode)) {
            if (dept != null) {
                batch.setBatchAreaCode(dept.getHospAreaCode());
            }
            AppSerial serial = sysDao.getAppSignformDao().getAppSerial(cityAreaCode.substring(0, 4), "batch");
            if (serial != null) {
                String num = serial.getSerialNo();
                Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(num,SignFormType.WEBSTATE.getValue());
                serial.setSerialNo(bcnum.get("old").toString());
                sysDao.getServiceDo().modify(serial);
                batch.setBatchNum(bcnum.get("new").toString());//批次号
            }
        }
        sysDao.getServiceDo().add(batch);
        form.setSignBatchId(batch.getId());
        //组织编码
        if (StringUtils.isNotBlank(cityAreaCode)) {
            if (dept != null) {
                form.setSignAreaCode(dept.getHospAreaCode());
                form.setSignHospId(dept.getId());
            } else {
                form.setSignAreaCode(webHospDept.getHospAreaCode());
                form.setSignHospId(webHospDept.getId());

            }
            AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(vo.getPatientNeighborhoodCommittee().substring(0, 4), "sign");
            if (serialSign != null) {
                String num = serialSign.getSerialNo();
                Map<String,Object> sinum = sysDao.getAppSignformDao().getNum(num,SignFormType.WEBSTATE.getValue());
                serialSign.setSerialNo(sinum.get("old").toString());
                sysDao.getServiceDo().modify(serialSign);
                form.setSignNum(sinum.get("new").toString());//签约编码
            }
        }
        //签约单
        form.setSignPatientId(webPatientUser.getId());//居民主键
        form.setSignDate(ExtendDate.getCalendar(vo.getSignDate()));//签约申请时间
        form.setSignType("1");//1家庭签约
        form.setSignTeamId(teamId);//团队主键
        form.setSignTeamName(appTeam.getTeamName());//团队名称
        form.setSignWay("2");//医生代签
        form.setSignPayState(vo.getSignPayState());//缴费状态
        if("1".equals(vo.getSignPayState())){
            form.setSignState(SignFormType.YQY.getValue());//签约状态
        }else{
            form.setSignState(SignFormType.YUQY.getValue());//签约状态
        }
        form.setSignPatientGender(vo.getPatientGender());//性别
        if (StringUtils.isNotBlank(age)) {
            form.setSignPatientAge(Integer.parseInt(age));//年龄
        }
        form.setSignPatientIdNo(vo.getPatientIdno());//身份证
        form.setSignFromDate(ExtendDate.getCalendar(vo.getSignFromDate()));
        form.setSignToDate(ExtendDate.getCalendar(vo.getSignToDate()));
        form.setSignDrId(drId);//医生主键
        form.setSignContractState("0");//1是 0否
        form.setSignGreenState("0");//1是 0否
        form.setSignYellowState("0");//1是 0否
        form.setSignRedState("0");//1是 0否
        form.setUpHpis("2");//数据来源web
        //健康情况
        if (StringUtils.isNotBlank(vo.getSignHealth())) {
            form.setSignHealthGroup(vo.getSignHealth());
        } else {
            form.setSignHealthGroup("199");
        }
        if (StringUtils.isNotBlank(vo.getSignlx())) {
            form.setSignlx(vo.getSignlx());//医保类型
        }
        if (StringUtils.isNotBlank(vo.getSignczpay())) {
            form.setSignczpay(vo.getSignczpay());//财政
        }
        if (StringUtils.isNotBlank(vo.getSignzfpay())) {
            form.setSignzfpay(vo.getSignzfpay());//自费
        }
        form.setSignDrAssistantId(vo.getSignDrAssistantId());//助理

        //添加服务套餐
        String meals = sysDao.getAppServeSetmealDao().findMealIdsByValue(vo.getSersmValue());
        if(StringUtils.isNotBlank(meals)){
            meals = meals.replaceAll(",",";");
            form.setSignpackageid(meals);
        }else{
            form.setSignpackageid(vo.getSersmValue());
        }

        sysDao.getServiceDo().add(form);
        //服务人群
        if (StringUtils.isNotBlank(vo.getSignPersGroup())) {
            String[] persGroup = vo.getSignPersGroup().split(",");
            if (persGroup != null && persGroup.length > 0) {
                String areaCode = "";
                if (dept != null) {
                    areaCode = dept.getHospAreaCode();
                } else {
                    areaCode = webHospDept.getHospAreaCode();
                }
                form.setSignPersGroup(persGroup[0]);//支持之前版
                sysDao.getAppLabelGroupDao().addLabel(persGroup, form.getId(), form.getSignTeamId(), areaCode, LabelManageType.FWRQ.getValue());
            }
        }
        //经济类型
        if (StringUtils.isNotBlank(vo.getSignsJjType())) {
            String[] jjTypes = vo.getSignsJjType().split(",");
            if (jjTypes != null && jjTypes.length > 0) {
                String areaCode = "";
                if (dept != null) {
                    areaCode = dept.getHospAreaCode();
                } else {
                    areaCode = webHospDept.getHospAreaCode();
                }
                form.setSignsJjType(jjTypes[0]);//支持之前版
                sysDao.getAppLabelGroupDao().addLabel(jjTypes, form.getId(), form.getSignTeamId(), areaCode, LabelManageType.JJLX.getValue());
            }
        }

        //疾病类型
        if (StringUtils.isNotBlank(vo.getSignDiseaseGroup())) {
            String[] disease = vo.getSignDiseaseGroup().split(",");
            if (disease != null && disease.length > 0) {
                String areaCode = "";
                if (dept != null) {
                    areaCode = dept.getHospAreaCode();
                } else {
                    areaCode = webHospDept.getHospAreaCode();
                }
                sysDao.getAppLabelGroupDao().addLabel(disease, form.getId(), form.getSignTeamId(), areaCode, LabelManageType.JBLX.getValue());
            }
        }
        return form;
    }
}

