package com.ylz.view.interfact.action;

import com.ylz.bizDo.app.entity.AppSignServiceRecordEntity;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppMsgQvo;
import com.ylz.bizDo.app.vo.AppTeamMemberQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.basicHealthEntity.jktj.Jktj_maternal;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.jtapp.basicHealthVo.InoculationVo;
import com.ylz.bizDo.jtapp.commonEntity.AlgorithEpiVoList;
import com.ylz.bizDo.jtapp.commonEntity.AppAllreservedWzxx;
import com.ylz.bizDo.jtapp.commonEntity.AppHealthEntiry;
import com.ylz.bizDo.jtapp.commonEntity.AppPossEntity;
import com.ylz.bizDo.jtapp.commonVo.AppAgreeMentQvo;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.commonVo.AppPossQvo;
import com.ylz.bizDo.jtapp.drEntity.*;
import com.ylz.bizDo.jtapp.patientEntity.AppOldPlanEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamMemberEntity;
import com.ylz.bizDo.jtapp.patientVo.AppTeamVo;
import com.ylz.bizDo.plan.po.AppFollowPlan;
import com.ylz.bizDo.jtapp.drVo.AppDrQvo;
import com.ylz.bizDo.web.vo.WebUpVo;
import com.ylz.bizDo.jtapp.patientVo.AppChildPlanQvo;
import com.ylz.bizDo.jtapp.patientVo.AppMaternalPlanQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.ResidentMangeType;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.lang.StringUtils;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.*;
import java.util.List;

/**
 * 公共对外接口
 */
@Action(
        value="interface",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"}),
                @Result(name = "upjson", type = "json",params={"root","upjson","contentType", "application/json"})
        }
)
public class AppInterfaceAction extends CommonAction {

//        private static final String ylzTestKey = "ylzdocortest@)!*)!";
        private static final String ylzTestKey = "familyDoctor@)!*ylz";
        private static final String ylzCsKey = "ylzdocortest@)!*)!";

        /**
         * 签约单查询列表
         * @return
         */
        public String findSignFormList(){
                //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
                AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
                try {
                        WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
                        if(p != null){
                                String json = aesEncrypt.decrypt(p.getStrJson());
                                AppCommQvo qvo = JsonUtil.fromJson(json,AppCommQvo.class);
                                if(qvo == null){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("参数格式错误");
                                }else {
                                        if(StringUtils.isNotBlank(qvo.getPatientIdno())){
                                                AppPatientUserEntity user = this.getSysDao().getAppPatientUserDao().findByUserIdNo(qvo.getPatientIdno());
                                                if(user != null){
                                                        qvo.setPatientId(user.getId());
                                                        List<AppSignFormListEntity> ls = sysDao.getAppSignformDao().findSignFormByUserOrTeam(qvo);
                                                        if(ls!=null && StringUtils.isNotBlank(qvo.getPatientId())){
                                                                Collections.sort(ls, ComparatorUtils.getComparatorSign());
                                                        }
                                                        this.getAjson().setQvo(qvo);
                                                        this.getAjson().setRows(ls);
                                                        this.getAjson().setMsgCode("800");
                                                }else{
                                                        this.getAjson().setMsgCode("900");
                                                        this.getAjson().setMsg("该居民暂无数据!");
                                                }
                                        }else{
                                                this.getAjson().setMsgCode("900");
                                                this.getAjson().setMsg("身份证参数不能为空");
                                        }

                                }
                        }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数错误");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        if(StringUtils.isNotBlank(e.getMessage())) {
                                this.getAjson().setMsg(e.getMessage());
                        }else {
                                this.getAjson().setMsg(e.toString());
                        }
                }finally {
                        String result = JsonUtil.toJson(this.getAjson());
                        this.getUpjson().setEntity(aesEncrypt.encrypt(result));
                }
                return "upjson";
                }


        /**
         * 查看协议内容         *
         * @return
         */
        public String findAgreeMent() {
                //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
                AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
                try {
                        WebUpVo webVo = (WebUpVo) this.getAppJson(WebUpVo.class);
                        if(webVo != null){
                                String json = aesEncrypt.decrypt(webVo.getStrJson());
                                AppAgreeMentQvo qvo = JsonUtil.fromJson(json,AppAgreeMentQvo.class);
                                if (qvo != null) {
                                        String addrxx = "";
                                        String xyGroup = "";
                                        String drName = "";
                                                if(StringUtils.isNotBlank(qvo.getPatientId()) && StringUtils.isNotBlank(qvo.getTeamId())){
                                                        qvo.setQyType("1");//1家庭签约，2三师共管签约,3,居家养老签约
                                                        qvo.setState("2");//签约状态 1申请签约时查看协议 2患者已签约查看协议
                                                        AppTeam team = (AppTeam) this.getSysDao().getServiceDo().find(AppTeam.class, qvo.getTeamId());//团队信息
                                                        AppPatientUser patientUser = (AppPatientUser)this.getSysDao().getServiceDo().find(AppPatientUser.class,qvo.getPatientId());//患者信息
                                                        if (team != null && patientUser != null) {
                                                                AppAgreement v = this.getSysDao().getAppAgreementDao().findByHospId(team.getTeamHospId(), qvo.getQyType());
                                                                AppHospDept dept = (AppHospDept) this.getSysDao().getServiceDo().find(AppHospDept.class, team.getTeamHospId());
                                                                if (v != null && dept != null) {
                                                                        AppSignForm form = new AppSignForm();
                                                                        if("1".equals(qvo.getState())){
                                                                                form = null;
                                                                        }else{
                                                                                form = this.getSysDao().getAppSignformDao().getSignFormUserId(patientUser.getId());
                                                                        }
                                                                        CdAddress address = (CdAddress) this.getSysDao().getServiceDo().find(CdAddress.class, dept.getHospUpcityareaId());
                                                                        if("350702".equals(AreaUtils.getAreaCode(dept.getHospAreaCode(),"3"))){
                                                                                xyGroup = CodeGroupConstrats.GROUP_YPXY;
                                                                        }else{
                                                                                xyGroup = CodeGroupConstrats.GROUP_NPXY;
                                                                        }
                                                                        if("4".equals(dept.getHospLevel())){//街道级查机构上一级区级地址
                                                                                CdAddress adrs = (CdAddress)this.getSysDao().getServiceDo().find(CdAddress.class,dept.getHospUpcityareaId());
                                                                                if(adrs != null){
                                                                                        addrxx = adrs.getAreaSname();
                                                                                }
                                                                        }else if("3".equals(dept.getHospLevel())){//区级直接查取本区级地址
                                                                                CdAddress adrs = (CdAddress)this.getSysDao().getServiceDo().find(CdAddress.class,dept.getHospAreaCode());
                                                                                if(adrs != null){
                                                                                        addrxx = adrs.getAreaSname();
                                                                                }
                                                                        }else if("5".equals(dept.getHospLevel())){
                                                                                CdAddress adrs = (CdAddress)this.getSysDao().getServiceDo().find(CdAddress.class,AreaUtils.getAreaCode(dept.getHospAreaCode(),"3")+"000000");
                                                                                if(adrs != null){
                                                                                        addrxx = adrs.getAreaSname();
                                                                                }
                                                                        }

                                                                        AppDrUser user = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, team.getTeamDrId());
                                                                        Map<String, String> map = ExtendDate.getDateVaild(Calendar.getInstance());
                                                                        String content = v.getMentContent();
                                                                        String patientName = "";
                                                                        if (StringUtils.isNotBlank(patientUser.getPatientName())) {
                                                                                patientName = patientUser.getPatientName();
                                                                        }
                                                                        String patientIdno = "";
                                                                        if (StringUtils.isNotBlank(patientUser.getPatientIdno())) {
                                                                                patientIdno = patientUser.getPatientIdno();
                                                                        }
                                                                        String patientAddress = "";
                                                                        if (StringUtils.isNotBlank(patientUser.getPatientAddress())) {
                                                                                patientAddress = patientUser.getPatientAddress();
                                                                        }
                                                                        String patientTel = "";
                                                                        if (StringUtils.isNotBlank(patientUser.getPatientTel())) {
                                                                                patientTel = patientUser.getPatientTel();
                                                                        }
                                                                        String areaSname = "";
                                                                        if (StringUtils.isNotBlank(address.getAreaSname())) {
                                                                                areaSname = address.getAreaSname();
                                                                        }
                                                                        String hospName = "";
                                                                        if (StringUtils.isNotBlank(dept.getHospName())) {
                                                                                hospName = dept.getHospName();
                                                                        }
                                                                        String teamName = "";
                                                                        if (StringUtils.isNotBlank(team.getTeamName())) {
                                                                                teamName = team.getTeamName();
                                                                        }
                                                                        String drTel = "";
                                                                        if(user != null){
                                                                                if (StringUtils.isNotBlank(user.getDrTel())) {
                                                                                        drTel = user.getDrTel();
                                                                                }
                                                                                drName = user.getDrName();
                                                                        }

                                                                        String hospTel = "";
                                                                        if (StringUtils.isNotBlank(dept.getHospTel())) {
                                                                                hospTel = dept.getHospTel();
                                                                        }
                                                                        String patientjmda="";
                                                                        if(StringUtils.isNotBlank(patientUser.getPatientjmda())){
                                                                                patientjmda= patientUser.getPatientjmda();
                                                                        }
                                                                        String drAssistantName="";
                                                                        String drAssistantTel="";
                                                                        if(user != null ){
                                                                                if(StringUtils.isNotBlank(user.getDrName())){
                                                                                        drAssistantName = user.getDrName();
                                                                                }
                                                                                if(StringUtils.isNotBlank(user.getDrTel())){
                                                                                        drAssistantTel = user.getDrTel();
                                                                                }
                                                                        }
                                                                        content = content.replace("{01}", patientjmda);
                                                                        content = content.replace("{7}", drAssistantName);
                                                                        content = content.replace("{8}", drAssistantTel);
                                                                        content = content.replace("{1}", patientName);
                                                                        content = content.replace("{2}", patientIdno);
                                                                        content = content.replace("{3}", patientAddress);
                                                                        content = content.replace("{4}", patientTel);
                                                                        content = content.replace("{5}", areaSname);
                                                                        content = content.replace("{6}", hospName);
                                                                        content = content.replace("{9}", hospTel);
                                                                        if(form != null){
                                                                                String ypxy = "";
                                                                                AppDrUser drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class,form.getSignDrId());
                                                                                if(drUser != null){
                                                                                        if(StringUtils.isNotBlank(drUser.getDrTel())){
                                                                                                drTel = drUser.getDrTel();
                                                                                        }
                                                                                        drName = drUser.getDrName();
                                                                                        content = content.replace("{02}",drUser.getDrName());
                                                                                        content = content.replace("{03}", drTel);
                                                                                }else {
                                                                                        content = content.replace("{02}", "");
                                                                                        content = content.replace("{03}", "");
                                                                                }
                                                                                if(form.getSignFromDate() != null){
                                                                                        content = content.replace("{10}", ExtendDate.getYMD(form.getSignFromDate()));
                                                                                        content = content.replace("{11}", ExtendDate.getYMD(form.getSignToDate()));
                                                                                }else{
                                                                                        content = content.replace("{10}", map.get("start"));
                                                                                        content = content.replace("{11}", map.get("end"));
                                                                                }
                                                                                if(StringUtils.isNotBlank(form.getSignNum())){
                                                                                        content = content.replace("{30}", form.getSignNum());
                                                                                }else{
                                                                                        content = content.replace("{30}", "");
                                                                                }
                                                                                content = content.replace("{23}", teamName);
                                                                                List<AppLabelGroup> ls = this.getSysDao().getAppLabelGroupDao().findBySignGroup(form.getId(),"3");
                                                                                if(ls != null && ls.size() >0){
                                                                                        for(AppLabelGroup p : ls){
                                                                                                if(ResidentMangeType.PTRQ.getValue().equals(p.getLabelValue())){//普通人群
                                                                                                        content = content.replace("{12}", "☑");//建立健康档案
                                                                                                        content = content.replace("{13}", "☑");//健康教育
                                                                                                        content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                                                                        CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                                                                        if(code!=null){
                                                                                                                ypxy += code.getCodeTitle();
                                                                                                        }
                                                                                                }
                                                                                                if(ResidentMangeType.ETLZLS.getValue().equals(p.getLabelValue())){//儿童(0~6岁)
                                                                                                        content = content.replace("{12}", "☑");//建立健康档案
                                                                                                        content = content.replace("{13}", "☑");//健康教育
                                                                                                        content = content.replace("{14}", "☑");//为0-6岁儿童进行一类疫苗接种
                                                                                                        content = content.replace("{15}", "☑");//为0-6岁儿童进行常规的体格检查
                                                                                                        content = content.replace("{20}", "☑");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                                                                                        content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                                                                        CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                                                                        if(code!=null){
                                                                                                                ypxy += code.getCodeTitle();
                                                                                                        }
                                                                                                }
                                                                                                if(ResidentMangeType.YCF.getValue().equals(p.getLabelValue())){//孕产妇
                                                                                                        content = content.replace("{12}", "☑");//建立健康档案
                                                                                                        content = content.replace("{13}", "☑");//健康教育
                                                                                                        content = content.replace("{16}", "☑");//为孕产妇孕期进行基本保健指导，开展至少5次孕期保健服务和2次产后访视
                                                                                                        content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                                                                        CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                                                                        if(code!=null){
                                                                                                                ypxy += code.getCodeTitle();
                                                                                                        }
                                                                                                }
                                                                                                if(ResidentMangeType.LNR.getValue().equals(p.getLabelValue())){//老年人
                                                                                                        content = content.replace("{12}", "☑");//建立健康档案
                                                                                                        content = content.replace("{13}", "☑");//健康教育
                                                                                                        content = content.replace("{17}", "☑");//为65岁以上老年人每年1次健康体检，包括血生化、血尿常规、心电图、B超等辅助检查
                                                                                                        content = content.replace("{20}", "☑");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                                                                                        content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                                                                        CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                                                                        if(code!=null){
                                                                                                                ypxy += code.getCodeTitle();
                                                                                                        }
                                                                                                }
                                                                                                if(ResidentMangeType.GXY.getValue().equals(p.getLabelValue())){//高血压
                                                                                                        content = content.replace("{12}", "☑");//建立健康档案
                                                                                                        content = content.replace("{13}", "☑");//健康教育
                                                                                                        content = content.replace("{18}", "☑");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                                                                                        content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                                                                        CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                                                                        if(code!=null){
                                                                                                                ypxy += code.getCodeTitle();
                                                                                                        }
                                                                                                }
                                                                                                if(ResidentMangeType.TNB.getValue().equals(p.getLabelValue())){//糖尿病
                                                                                                        content = content.replace("{12}", "☑");//建立健康档案
                                                                                                        content = content.replace("{13}", "☑");//健康教育
                                                                                                        content = content.replace("{18}", "☑");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                                                                                        content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                                                                        CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                                                                        if(code!=null){
                                                                                                                ypxy += code.getCodeTitle();
                                                                                                        }
                                                                                                }
                                                                                                if(ResidentMangeType.YZJSZY.getValue().equals(p.getLabelValue())){//严重精神障碍
                                                                                                        content = content.replace("{12}", "☑");//建立健康档案
                                                                                                        content = content.replace("{13}", "☑");//健康教育
                                                                                                        content = content.replace("{19}", "☑");//对居家重性精神疾病患者提供随访服务和每年健康体检1次
                                                                                                        content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                                                                        CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                                                                        if(code!=null){
                                                                                                                ypxy += code.getCodeTitle();
                                                                                                        }
                                                                                                }
                                                                                                if(ResidentMangeType.JHB.getValue().equals(p.getLabelValue())){//结核病
                                                                                                        content = content.replace("{12}", "☑");//建立健康档案
                                                                                                        content = content.replace("{13}", "☑");//健康教育
                                                                                                        content = content.replace("{21}", "☑");//为结核病患者提供用药、饮食、运动、心理等健康指导
                                                                                                        content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                                                                        CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                                                                        if(code!=null){
                                                                                                                ypxy += code.getCodeTitle();
                                                                                                        }
                                                                                                }
                                                                                                if(ResidentMangeType.CJR.getValue().equals(p.getLabelValue())){//残疾人
                                                                                                        content = content.replace("{12}", "☑");//建立健康档案
                                                                                                        content = content.replace("{13}", "☑");//健康教育
                                                                                                        content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                                                                        CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                                                                        if(code!=null){
                                                                                                                ypxy += code.getCodeTitle();
                                                                                                        }
                                                                                                }
                                                                                                if(ResidentMangeType.WEIZHI.getValue().equals(p.getLabelValue())){//未知
                                                                                                        content = content.replace("{12}", "");//建立健康档案
                                                                                                        content = content.replace("{13}", "");//健康教育
                                                                                                        content = content.replace("{14}", "");//为0-6岁儿童进行一类疫苗接种
                                                                                                        content = content.replace("{15}", "");//为0-6岁儿童进行常规的体格检查
                                                                                                        content = content.replace("{16}", "");//为孕产妇孕期进行基本保健指导，开展至少5次孕期保健服务和2次产后访视
                                                                                                        content = content.replace("{17}", "");//为65岁以上老年人每年1次健康体检，包括血生化、血尿常规、心电图、B超等辅助检查
                                                                                                        content = content.replace("{18}", "");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                                                                                        content = content.replace("{19}", "");//对居家重性精神疾病患者提供随访服务和每年健康体检1次
                                                                                                        content = content.replace("{20}", "");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                                                                                        content = content.replace("{21}", "");//为结核病患者提供用药、饮食、运动、心理等健康指导
                                                                                                        content = content.replace("{22}", "");//其它国家规定免费提供的基本公共卫生服务项目
                                                                                                }
                                                                                        }
                                                                                        content = content.replace("{12}", "");//建立健康档案
                                                                                        content = content.replace("{13}", "");//健康教育
                                                                                        content = content.replace("{14}", "");//为0-6岁儿童进行一类疫苗接种
                                                                                        content = content.replace("{15}", "");//为0-6岁儿童进行常规的体格检查
                                                                                        content = content.replace("{16}", "");//为孕产妇孕期进行基本保健指导，开展至少5次孕期保健服务和2次产后访视
                                                                                        content = content.replace("{17}", "");//为65岁以上老年人每年1次健康体检，包括血生化、血尿常规、心电图、B超等辅助检查
                                                                                        content = content.replace("{18}", "");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                                                                        content = content.replace("{19}", "");//对居家重性精神疾病患者提供随访服务和每年健康体检1次
                                                                                        content = content.replace("{20}", "");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                                                                        content = content.replace("{21}", "");//为结核病患者提供用药、饮食、运动、心理等健康指导
                                                                                        content = content.replace("{22}", "");//其它国家规定免费提供的基本公共卫生服务项目

                                                                                        content = content.replace("{04}","<div id='headerId'>");//页头
                                                                                        content = content.replace("{06}","<div id='tailId'>");//页未
                                                                                        content = content.replace("{05}","</div>");//页未
                                                                                        if(StringUtils.isNotBlank(form.getSigntext())){ //不知道为什么这样写总觉得不太合理
                                                                                                String text="";
                                                                                                String[] a =form.getSigntext().split("\n");
                                                                                                for(int i=0;i<a.length;i++){
                                                                                                        if(i==0){
                                                                                                                text="<p style=\"text-indent:32px;line-height:27px\">"+a[i]+"</p>";
                                                                                                        }else{
                                                                                                                text=text+"<p style=\"text-indent:32px;line-height:27px\">"+a[i]+"</p>";
                                                                                                        }
                                                                                                }
                                                                                                content=content.replace("{0}",text);//特色补充协议包
                                                                                        }else{
                                                                                                content=content.replace("{0}","");//特色补充协议包
                                                                                        }
                                                                                        content = content.replace("97",addrxx);
                                                                                        content = content.replace("{98}",ypxy);
                                                                                }
                                                                        }else{
                                                                                content = content.replace("{04}","<div id='headerId'>");//页头
                                                                                content = content.replace("{06}","<div id='tailId'>");//页未
                                                                                content = content.replace("{05}","</div>");//页未
                                                                                if(StringUtils.isNotBlank(qvo.getDrId())){
                                                                                        AppDrUser drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
                                                                                        if(drUser != null){
                                                                                                if(StringUtils.isNotBlank(drUser.getDrTel())){
                                                                                                        drTel = drUser.getDrTel();
                                                                                                }else{
                                                                                                        drTel = "";
                                                                                                }
                                                                                                drName = drUser.getDrName();
                                                                                                content = content.replace("{02}",drUser.getDrName());
                                                                                                content = content.replace("{03}", drTel);
                                                                                        }else {
                                                                                                content = content.replace("{02}", "");
                                                                                                content = content.replace("{02}", "");
                                                                                        }
                                                                                }else{
                                                                                        content = content.replace("{02}", "");
                                                                                        content = content.replace("{02}", "");
                                                                                }
                                                                                content = content.replace("{23}", teamName);
                                                                                content = content.replace("{0}", "");
                                                                                content = content.replace("{30}", "");
                                                                                content = content.replace("{10}", map.get("start"));
                                                                                content = content.replace("{11}", "");
                                                                                content = content.replace("{12}", "");//建立健康档案
                                                                                content = content.replace("{13}", "");//健康教育
                                                                                content = content.replace("{14}", "");//为0-6岁儿童进行一类疫苗接种
                                                                                content = content.replace("{15}", "");//为0-6岁儿童进行常规的体格检查
                                                                                content = content.replace("{16}", "");//为孕产妇孕期进行基本保健指导，开展至少5次孕期保健服务和2次产后访视
                                                                                content = content.replace("{17}", "");//为65岁以上老年人每年1次健康体检，包括血生化、血尿常规、心电图、B超等辅助检查
                                                                                content = content.replace("{18}", "");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                                                                content = content.replace("{19}", "");//对居家重性精神疾病患者提供随访服务和每年健康体检1次
                                                                                content = content.replace("{20}", "");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                                                                content = content.replace("{21}", "");//为结核病患者提供用药、饮食、运动、心理等健康指导
                                                                                content = content.replace("{22}", "");//其它国家规定免费提供的基本公共卫生服务项目
                                                                                content = content.replace("97","");
                                                                                content = content.replace("{98}","");
                                                                        }
                                                                        content = content.replace("{40}",ExtendDate.getChineseYMD(Calendar.getInstance()));
                                                                        this.getAjson().setMsg(content);
                                                                } else {
                                                                        this.getAjson().setMsgCode("900");
                                                                        this.getAjson().setMsg("查无协议书,请联系管理员");
                                                                }
                                                        } else {
                                                                this.getAjson().setMsgCode("900");
                                                                this.getAjson().setMsg("参数格式错误");
                                                        }
                                                }else{
                                                        this.getAjson().setMsgCode("900");
                                                        this.getAjson().setMsg("身份证参数不能为空!");
                                                }
                                } else {
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("参数格式错误");
                                }
                        }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数错误");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        if(StringUtils.isNotBlank(e.getMessage())) {
                                this.getAjson().setMsg(e.getMessage());
                        }else {
                                this.getAjson().setMsg(e.toString());
                        }
                }finally {
                        String result = JsonUtil.toJson(this.getAjson());
                        this.getUpjson().setEntity(aesEncrypt.encrypt(result));
                }
                return "upjson";
        }

        /**
         * 查询团队成员
         * @return
         */
        public String findTeamMembers(){
                //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
                AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
                try{
                        WebUpVo webVo = (WebUpVo) this.getAppJson(WebUpVo.class);
                        if(webVo != null) {
                                String json = aesEncrypt.decrypt(webVo.getStrJson());
                                AppTeamMemberQvo qvo = JsonUtil.fromJson(json, AppTeamMemberQvo.class);
                                if(qvo!=null){
                                        if(StringUtils.isNotBlank(qvo.getAppMemTeamId())){
                                                List<AppDrTeamMemEntiry> ls = this.sysDao.getAppTeamMemberDao().findTeamMem(qvo.getAppMemTeamId());
                                                this.getAjson().setRows(ls);
                                        }else{
                                                this.getAjson().setMsg("团队主键不能为空!");
                                                this.getAjson().setMsgCode("900");
                                        }
                                }else{
                                        this.getAjson().setMsg("参数格式错误");
                                        this.getAjson().setMsgCode("900");
                                }
                        }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数错误");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        if(StringUtils.isNotBlank(e.getMessage())) {
                                this.getAjson().setMsg(e.getMessage());
                        }else {
                                this.getAjson().setMsg(e.toString());
                        }
                }finally {
                        String result = JsonUtil.toJson(this.getAjson());
                        this.getUpjson().setEntity(aesEncrypt.encrypt(result));
                }
                return "upjson";
        }

        /**
         * 根据医生主键查询医生详情信息及服务包
         * @return
         */
        public String findDrInfo(){
                //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
                AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
                try{
                        WebUpVo webVo = (WebUpVo) this.getAppJson(WebUpVo.class);
                        if(webVo != null) {
                                String json = aesEncrypt.decrypt(webVo.getStrJson());
                                AppCommQvo qvo = JsonUtil.fromJson(json, AppCommQvo.class);
                                if(qvo!=null){
                                        if(StringUtils.isNotBlank(qvo.getDrId())){
                                                if(StringUtils.isNotBlank(qvo.getTeamId())){
                                                        DrInfoEntity info = sysDao.getAppDrUserDao().findDrInfo(qvo.getDrId(),qvo.getTeamId());
                                                        this.getAjson().setVo(info);
                                                }else{
                                                        this.getAjson().setMsg("团队主键不能为空!");
                                                        this.getAjson().setMsgCode("900");
                                                }
                                        }else{
                                                this.getAjson().setMsg("医生主键不能为空!");
                                                this.getAjson().setMsgCode("900");
                                        }
                                }else{
                                        this.getAjson().setMsg("参数格式错误");
                                        this.getAjson().setMsgCode("900");
                                }
                        }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数错误");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        if(StringUtils.isNotBlank(e.getMessage())) {
                                this.getAjson().setMsg(e.getMessage());
                        }else {
                                this.getAjson().setMsg(e.toString());
                        }
                }finally {
                        String result = JsonUtil.toJson(this.getAjson());
                        this.getUpjson().setEntity(aesEncrypt.encrypt(result));
                }
                return "upjson";
        }



        /**
         * 查询计免儿童全程标准方案
         * @return
         */
        public String findMySchByBth(){
                //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
                AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
                try{
                        WebUpVo webVo = (WebUpVo) this.getAppJson(WebUpVo.class);
                        if(webVo != null) {
                                String json = aesEncrypt.decrypt(webVo.getStrJson());
                                InoculationVo qvo = JsonUtil.fromJson(json, InoculationVo.class);
                                if(qvo!=null){
                                        if(StringUtils.isNotBlank(qvo.getChildBirth())){
                                                List<AlgorithEpiVoList> ls = this.getSysDao().getSecurityCardAsyncBean().getInoculateMySchByBth(qvo.getChildBirth());
                                                this.getAjson().setRows(ls);
                                        }else{
                                                this.getAjson().setMsg("出生日期参数格式错误");
                                                this.getAjson().setMsgCode("900");
                                        }
                                }else{
                                        this.getAjson().setMsg("参数格式错误");
                                        this.getAjson().setMsgCode("900");
                                }
                        }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数错误");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        if(StringUtils.isNotBlank(e.getMessage())) {
                                this.getAjson().setMsg(e.getMessage());
                        }else {
                                this.getAjson().setMsg(e.toString());
                        }
                }finally {
                        String result = JsonUtil.toJson(this.getAjson());
                        this.getUpjson().setEntity(aesEncrypt.encrypt(result));
                }
                return "upjson";
        }

        /**
         * 查询模板健康教育列表
         * @return
         */
        public String findListMod(){
                //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
                AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
                try{
                        WebUpVo webVo = (WebUpVo) this.getAppJson(WebUpVo.class);
                        if(webVo != null) {
                                String json = aesEncrypt.decrypt(webVo.getStrJson());
                                AppDrQvo qvo = JsonUtil.fromJson(json, AppDrQvo.class);
                                if(qvo!=null){
                                        if(StringUtils.isNotBlank(qvo.getXtmb()) && qvo.getXtmb().equals("100")){
                                                List<AppHealthEntiry> ls = this.sysDao.getNewsTableDao().findList(qvo);
                                                this.getAjson().setRows(ls);
                                        }else{
                                                this.getAjson().setMsg("系统模板参数格式错误");
                                                this.getAjson().setMsgCode("900");
                                        }
                                }else{
                                        this.getAjson().setMsg("参数格式错误");
                                        this.getAjson().setMsgCode("900");
                                }
                        }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数错误");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        if(StringUtils.isNotBlank(e.getMessage())) {
                                this.getAjson().setMsg(e.getMessage());
                        }else {
                                this.getAjson().setMsg(e.toString());
                        }
                }finally {
                        String result = JsonUtil.toJson(this.getAjson());
                        this.getUpjson().setEntity(aesEncrypt.encrypt(result));
                }
                return "upjson";
        }



        /**
         * 查询基卫儿童计划
         * @return
         */
        public String childPlanByBasic(){
                AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
                try {
                        WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
                        if(p != null){
                                String json = aesEncrypt.decrypt(p.getStrJson());
                                AppChildPlanQvo qvo = JsonUtil.fromJson(json,AppChildPlanQvo.class);
                                if(qvo==null){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("参数格式错误!");
                                }else{
                                        AppPatientUserEntity user = this.getSysDao().getAppPatientUserDao().findByUserIdNo(qvo.getUserIdno());
                                        if(user==null){
                                                this.getAjson().setMsgCode("900");
                                                this.getAjson().setMsg("该居民暂无数据!");
                                        }else{

                                                //没有健康体检，根据出生日期计算
                                                Integer[] num= new Integer[]{1,3,6,8,12,18,24,30,36,48,60,72};
                                                String[] title = new String[]{
                                                        "满月龄","3月龄","6月龄","8月龄","12月龄","18月龄","24月龄",
                                                        "30月龄","3岁龄","4岁龄","5岁龄","6岁龄"
                                                };
//                                                List<AppChildHealthPlan> list = sysDao.getAppChildHealthPlanDao().findByChildUserId()
                                                Map<String,Object> returnMap = new HashMap<>();
                                                boolean flag = true;
                                                for(int i=0;i<12;i++){
                                                        AppChildHealthPlan plan = new AppChildHealthPlan();
                                                        plan.setChpBirthDay(ExtendDate.getCalendar(qvo.getBirthDay()));
                                                        plan.setChpChildUserId(qvo.getChilderIdno());
                                                        plan.setChpUserId(user.getId());
                                                        plan.setChpPlanDate(new ChildHealthPlanUtil().planTime(qvo.getBirthDay(),num[i]));//体检日期
                                                        plan.setChpTitle(title[i]);
                                                        plan.setChpChildName(qvo.getChildName());
                                                        if(plan.getChpPlanDate().getTimeInMillis()< Calendar.getInstance().getTimeInMillis()){
                                                                plan.setChpState("1");
                                                        }else{
                                                                plan.setChpState("0");
                                                        }
                                                        sysDao.getServiceDo().add(plan);
                                                        if(Calendar.getInstance().compareTo(plan.getChpPlanDate())<0){
                                                                if(flag){
                                                                        returnMap.put("title",title[i]);
                                                                        returnMap.put("time",ExtendDate.getYMD(plan.getChpPlanDate()));
                                                                }
                                                                flag = false;
                                                        }
                                                }
                                                this.getAjson().setMap(returnMap);
                                        }
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        if(StringUtils.isNotBlank(e.getMessage())) {
                                this.getAjson().setMsg(e.getMessage());
                        }else {
                                this.getAjson().setMsg(e.toString());
                        }
                }finally {
                        String result = JsonUtil.toJson(this.getAjson());
                        this.getUpjson().setEntity(aesEncrypt.encrypt(result));
                }
                return "upjson";
        }

        /**
         * 孕产妇产检计划
         * @return
         */
        public String maternalPlan(){
                AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
                try {
                        WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
                        if(p != null){
                                String json = aesEncrypt.decrypt(p.getStrJson());
                                AppMaternalPlanQvo qvo = JsonUtil.fromJson(json,AppMaternalPlanQvo.class);
                                if(qvo==null){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("参数格式错误!");
                                }else{
                                        AppPatientUserEntity user = this.getSysDao().getAppPatientUserDao().findByUserIdNo(qvo.getUserIdno());
                                        if(user==null){
                                                this.getAjson().setMsg("该居民暂无数据!");
                                                this.getAjson().setMsgCode("900");
                                        }else{
                                                String[] num = {"第一次产检 怀孕12周","第二次产检  怀孕16周","第三次产检 怀孕20周",
                                                        "第四次产检 怀孕24周","第五次产检 怀孕28周","第六次产检 怀孕30周",
                                                        "第七次产检 怀孕32周","第八次产检 怀孕34周","第九次产检 怀孕36周",
                                                        "第十次产检 怀孕37周","第十一次产检 怀孕38周","第十二次产检 怀孕39周","第十三次产检 怀孕40周"};
                                                String str = this.getSysDao().getSecurityCardAsyncBean().findMaternalPlan(qvo);
                                                JSONObject jsonall = JSONObject.fromObject(str);
                                                Map<String,Object> returnMap = new HashMap<>();
                                                if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){//基卫有相应的产检计划
                                                        Jktj_maternal vo = JsonUtil.fromJson(jsonall.get("vo").toString(),Jktj_maternal.class);
                                                        if(vo!=null){
                                                                if("3".equals(vo.getXm0000())){//产后随访
                                                                        if("2".equals(vo.getCs0000())){//产后42天随访
                                                                                //有产后42天数据代表做过产后42天健康检查
                                                                        }else{//没有做产后42天随访
                                                                                String time = vo.getXcsfrq();
                                                                                if(StringUtils.isNotBlank(time)){
                                                                                        Calendar planTime = ExtendDate.getCalendar(time);
                                                                                        if(Calendar.getInstance().compareTo(planTime)<0){
                                                                                                returnMap.put("title","产后42天计划");
                                                                                                returnMap.put("time",time);
                                                                                        }
                                                                                }
                                                                        }
                                                                }else{
                                                                        int yz = 0;
                                                                        if(StringUtils.isNotBlank(vo.getYz0000())){
                                                                                yz = Integer.parseInt(vo.getYz0000());//孕周
                                                                        }
                                                                        int[] weeks = {12,16,20,24,28,30,32,34,36,37,38,39,40};
                                                                        for(int i=0;i<weeks.length;i++){
                                                                                if(yz<weeks[i]){
                                                                                        if(StringUtils.isNotBlank(vo.getXcsfrq())){
                                                                                                Calendar planTime = ExtendDate.getCalendar(vo.getXcsfrq());
                                                                                                if(Calendar.getInstance().compareTo(planTime)<0){
                                                                                                        returnMap.put("title",num[i]);
                                                                                                        returnMap.put("time",vo.getXcsfrq());
                                                                                                        break;
                                                                                                }
                                                                                        }
                                                                                }
                                                                        }
                                                                }
                                                        }
                                                }else {//没有产检计划，根据输入的时间计算
                                                        List<AppHealthCareSetting> setList = sysDao.getServiceDo().loadByPk(AppHealthCareSetting.class,"hcPatientId",user.getId());
//                                                        if("2".equals(qvo.getType())){//末次月经日期
                                                        int[] weeks = {12,16,20,24,28,30,32,34,36,37,38,39,40};
                                                        boolean flag = true;
                                                        for (int i = 0; i <weeks.length ; i++) {
                                                                AppPregnantPlan plan = new AppPregnantPlan();
                                                                plan.setPpUserId(user.getId());
                                                                plan.setPpDateType(qvo.getType());
                                                                plan.setPpUserDate(ExtendDate.getCalendar(qvo.getUserDate()));
                                                                plan.setPpPlanTitle(num[i]);
                                                                Calendar cal = ExtendDate.getCalendar(qvo.getUserDate());
                                                                cal.add(Calendar.DATE, weeks[i] * 7);
                                                                plan.setPpPlanDate(cal);
                                                                plan.setPpState(CommonEnable.JINYONG.getValue());
                                                                if(!setList.isEmpty()){
                                                                        plan.setPpRemindDay(setList.get(0).getHcRemindDay());
                                                                        plan.setPpEnableAlert(setList.get(0).getHcEnable());
                                                                }
                                                                sysDao.getServiceDo().add(plan);
                                                                if(Calendar.getInstance().compareTo(cal)<0){//系统时间小于计划时间
                                                                        if(flag){
                                                                                String title = num[i];
                                                                                flag = false;
                                                                                returnMap.put("title",title);
                                                                                returnMap.put("time",plan.getPpPlanDate());
                                                                        }
                                                                }

                                                        }
                                                }
                                                this.getAjson().setMap(returnMap);
                                        }
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        if(StringUtils.isNotBlank(e.getMessage())) {
                                this.getAjson().setMsg(e.getMessage());
                        }else {
                                this.getAjson().setMsg(e.toString());
                        }
                }finally {
                        String result = JsonUtil.toJson(this.getAjson());
                        this.getUpjson().setEntity(aesEncrypt.encrypt(result));
                }
                return "upjson";
        }

        /**
         * 老年人健康体检计划
         * @return
         */
        public String oldHealthPlan(){
                AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
                try{
                        WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
                        if(p != null) {
                                String json = aesEncrypt.decrypt(p.getStrJson());
                                AppMaternalPlanQvo qvo = JsonUtil.fromJson(json, AppMaternalPlanQvo.class);
                                if(qvo==null){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("参数格式错误!");
                                }else{
                                        AppPatientUserEntity user = this.getSysDao().getAppPatientUserDao().findByUserIdNo(qvo.getUserIdno());
                                        if(user==null){
                                                this.getAjson().setMsg("该居民暂无数据!");
                                                this.getAjson().setMsgCode("900");
                                        }else{
                                                List<AppOldPlanEntity> lsEty = new ArrayList<>();
                                                String str = this.getSysDao().getSecurityCardAsyncBean().findOldHealthPlan(qvo);
                                                JSONObject jsonall = JSONObject.fromObject(str);
                                                if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){//基卫有相应老年人健康计划
                                                        Jktj_maternal vo = JsonUtil.fromJson(jsonall.get("vo").toString(),Jktj_maternal.class);
                                                        if(vo!=null){
                                                                AppOldPlanEntity vv = new AppOldPlanEntity();
                                                                if(StringUtils.isNotBlank(vo.getNjsj00())){
                                                                        String time = vo.getNjsj00().substring(0,4)+"-"+vo.getNjsj00().substring(4,6)+"-"+vo.getNjsj00().substring(6,8);
                                                                        Calendar cal = ExtendDate.getCalendar(time);
                                                                        cal.add(Calendar.YEAR,1);
                                                                        vv.setTitle("100");
                                                                        vv.setTime(ExtendDate.getYMD(cal));
                                                                        lsEty.add(vv);
                                                                }
                                                                List<AppFollowPlan> lsPlan = sysDao.getAppFollowPlanDao().findPlanById(user.getId());
                                                                if(lsPlan!=null && lsPlan.size()>0){
                                                                        for(AppFollowPlan ll:lsPlan){
                                                                                AppOldPlanEntity vp = new AppOldPlanEntity();
                                                                                vp.setTitle(ll.getSfFollowType());
                                                                                vp.setTime(ExtendDate.getYMD(ll.getSfFollowDate()));
                                                                                lsEty.add(vp);
                                                                        }
                                                                }
                                                        }
                                                }else {//基卫没有老年人健康计划
                                                        List<AppFollowPlan> lsPlan = sysDao.getAppFollowPlanDao().findPlanById(user.getId());
                                                        if(lsPlan!=null && lsPlan.size()>0){
                                                                for(AppFollowPlan ll:lsPlan){
                                                                        AppOldPlanEntity vp = new AppOldPlanEntity();
                                                                        vp.setTitle(ll.getSfFollowType());
                                                                        vp.setTime(ExtendDate.getYMD(ll.getSfFollowDate()));
                                                                        lsEty.add(vp);
                                                                }
                                                        }
                                                }
                                                this.getAjson().setRows(lsEty);
                                        }
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        if(StringUtils.isNotBlank(e.getMessage())) {
                                this.getAjson().setMsg(e.getMessage());
                        }else {
                                this.getAjson().setMsg(e.toString());
                        }
                }finally {
                        String result = JsonUtil.toJson(this.getAjson());
                        this.getUpjson().setEntity(aesEncrypt.encrypt(result));
                }
                return "upjson";
        }

    /**
     * 根据机构id查询机构下的团队
     * @return
     */
    public String findTeamByHospId(){
            AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
            try{
                WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
                if(p != null) {
                    String json = aesEncrypt.decrypt(p.getStrJson());
                    AppDrQvo qvo = JsonUtil.fromJson(json, AppDrQvo.class);
                    if(qvo==null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("参数格式错误!");
                    }else{
                        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
                        if(dept==null){
                            this.getAjson().setMsg("查无该机构数据!");
                            this.getAjson().setMsgCode("900");
                        }else{
                            List<AppTeamEntity> list = sysDao.getAppTeamDao().findTeam(qvo.getHospId());
                            this.getAjson().setRows(list);
                            this.getAjson().setMsgCode("800");
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                this.getAjson().setMsgCode("900");
                if(StringUtils.isNotBlank(e.getMessage())) {
                    this.getAjson().setMsg(e.getMessage());
                }else {
                    this.getAjson().setMsg(e.toString());
                }
            }finally {
                String result = JsonUtil.toJson(this.getAjson());
                this.getUpjson().setEntity(aesEncrypt.encrypt(result));
            }
            return "upjson";
    }

    /**
     * 根据医院主键查询医院套餐餐
     * @return
     */
    public String findMealByHospId(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            if(p != null) {
                String json = aesEncrypt.decrypt(p.getStrJson());
                AppDrQvo qvo = JsonUtil.fromJson(json, AppDrQvo.class);
                if(qvo==null){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误!");
                }else{
                    AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
                    if(dept==null){
                        this.getAjson().setMsg("查无该机构数据!");
                        this.getAjson().setMsgCode("900");
                    }else{
                        List<AppServeEntity> list = sysDao.getAppServeSetmealDao().findSeverMeal(qvo.getHospId());
                        this.getAjson().setRows(list);
                        this.getAjson().setMsgCode("800");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(this.getAjson());
            this.getUpjson().setEntity(aesEncrypt.encrypt(result));
        }
        return "upjson";
    }

        /**
         * 查询服务记录列表（对外）
         * @return
         */
        public String findSignServeRecordList(){
                AesEncrypt aesEncrypt = new AesEncrypt(ylzCsKey);
                try{
                        WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
                        if(p != null){
                                byte[] decryptResult = AESR.decrypt(Base64Utils.decode(p.getStrJson()),ylzCsKey,16);
                                String json = new String(decryptResult,"UTF-8");
//                                String json = aesEncrypt.decrypt(p.getStrJson());
                                AppPossQvo qvo = JsonUtil.fromJson(json, AppPossQvo.class);
                                if(qvo == null){
                                        this.getAjson().setMsg("参数格式错误");
                                        this.getAjson().setMsgCode("900");
                                }else{
                                        if(StringUtils.isBlank(qvo.getPatientId()) && StringUtils.isBlank(qvo.getPatientIdno())){//患者主键和身份证都为空
                                                this.getAjson().setMsg("患者主键和身份证不能同时为空");
                                                this.getAjson().setMsgCode("900");
                                        }else{
                                                if(StringUtils.isBlank(qvo.getPatientId())){
                                                        AppPatientUser user = sysDao.getAppPatientUserDao().findByIdnoAndName(qvo.getPatientIdno(),null);
                                                        if(user != null){
                                                                qvo.setPatientId(user.getId());
                                                                List<AppPossEntity> list = sysDao.getAppPossBindingDao().findSignServeRecordList(qvo);
                                                                this.getAjson().setRows(list);
                                                                qvo.setPageCount(qvo.getPageCount());
                                                                this.getAjson().setQvo(qvo);
                                                                this.getAjson().setMsgCode("800");
                                                        }else{
                                                                this.getAjson().setMsgCode("900");
                                                                this.getAjson().setMsg("查无该患者信息");
                                                        }
                                                }else{
                                                        List<AppPossEntity> list = sysDao.getAppPossBindingDao().findSignServeRecordList(qvo);
                                                        this.getAjson().setRows(list);
                                                        qvo.setPageCount(qvo.getPageCount());
                                                        this.getAjson().setQvo(qvo);
                                                        this.getAjson().setMsgCode("800");
                                                }
                                        }
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        if(StringUtils.isNotBlank(e.getMessage())) {
                                this.getAjson().setMsg(e.getMessage());
                        }else {
                                this.getAjson().setMsg(e.toString());
                        }
                }finally {
                        try{
                                String result = JsonUtil.toJson(this.getAjson());
                                byte[] encryptResult = AESR.encrypt(result, ylzCsKey,16);
                                this.getUpjson().setEntity(Base64Utils.encode(encryptResult));
                        }catch (Exception e){
                                e.printStackTrace();
                        }
                }
                return "upjson";
        }

        /**
         * 查询服务记录详细信息（对外）
         * @return
         */
        public String findSignServeRecord(){
                AesEncrypt aesEncrypt = new AesEncrypt(ylzCsKey);
                try{
                        WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
                        if(p != null){
                                byte[] decryptResult = AESR.decrypt(Base64Utils.decode(p.getStrJson()),ylzCsKey,16);
                                String json = new String(decryptResult,"UTF-8");
//                                String json = aesEncrypt.decrypt(p.getStrJson());
                                AppPossQvo qvo = JsonUtil.fromJson(json, AppPossQvo.class);
                                if(qvo == null){
                                        this.getAjson().setMsg("参数格式错误");
                                        this.getAjson().setMsgCode("900");
                                }else{
                                        if(StringUtils.isBlank(qvo.getId())){
                                                this.getAjson().setMsgCode("900");
                                                this.getAjson().setMsg("查询条件不能为空");
                                        }else{
                                                AppSignServiceRecordEntity vo = sysDao.getAppPossBindingDao().findSignServeRecord(qvo.getId());
                                                if(vo != null){
                                                        this.getAjson().setMsgCode("800");
                                                        this.getAjson().setVo(vo);
                                                }else{
                                                        this.getAjson().setMsgCode("900");
                                                        this.getAjson().setMsg("查无数据");
                                                }
                                        }
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        if(StringUtils.isNotBlank(e.getMessage())) {
                                this.getAjson().setMsg(e.getMessage());
                        }else {
                                this.getAjson().setMsg(e.toString());
                        }
                }finally {
                        try{
                                String result = JsonUtil.toJson(this.getAjson());
                                byte[] encryptResult = AESR.encrypt(result, ylzCsKey,16);
                                this.getUpjson().setEntity(Base64Utils.encode(encryptResult));
                        }catch (Exception e){
                                e.printStackTrace();
                        }
                }
                return "upjson";
        }

        /**
         * 应用授权判断
         * @return
         */
        public String appAccredit(){
                try{
                        AppMsgQvo qvo = (AppMsgQvo)this.getAppJson(AppMsgQvo.class);
                        if(qvo == null){
                                this.getAjson().setMsg("参数格式错误");
                                this.getAjson().setMsgCode("900");
                        }else {
                                if (StringUtils.isBlank(qvo.getAppId())) {
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("appId参数不能为空");
                                } else if (StringUtils.isBlank(qvo.getAppMasterSecret())) {
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("appMasterSecret参数不能为空");
                                } else if (StringUtils.isBlank(qvo.getAppPgName())){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("appPgName参数不能为空");
                                }else{
                                        AppMsg msg = sysDao.getAppMsgDao().findMsgByQvo(qvo);
                                        if(msg != null){
                                                String qvoKey = Md5Util.MD5(qvo.getAppId()+qvo.getAppMasterSecret()+qvo.getAppPgName()).toUpperCase();
//                                                String key = msg.getAppId()+msg.getAppMasterSecret();//取查询的数据的appId和appMasterSecret作为动态解密密钥
//                                                byte[] decryptResult = AESR.decrypt(Base64Utils.decode(msg.getAppKey()),key,16);
//                                                String json = new String(decryptResult,"UTF-8");
                                                if(msg.getAppKey().equals(qvoKey)){
                                                        msg.setAppState("1");
                                                        sysDao.getServiceDo().modify(msg);
                                                        this.getAjson().setMsgCode("800");
                                                        this.getAjson().setMsg("授权通过");
                                                }else {
                                                        this.getAjson().setMsgCode("900");
                                                        this.getAjson().setMsg("授权不通过");
                                                }
                                        }else{
                                                this.getAjson().setMsg("查无该应用信息");
                                                this.getAjson().setMsgCode("900");
                                        }
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsg(e.getMessage());
                        this.getAjson().setMsgCode("900");
                }
                return "ajson";
        }

}
