package com.ylz.view.ysapp.action;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ylz.bizDo.cd.po.CdAddressConfiguration;
import com.ylz.bizDo.jtapp.commonEntity.AppSubsidyEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.po.AppArchivingCardPeople;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppGotoSignRecord;
import com.ylz.bizDo.app.po.AppGuideTemplate;
import com.ylz.bizDo.app.po.AppHealthGuide;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppRefuseSign;
import com.ylz.bizDo.app.po.AppServeSetmeal;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.app.po.AppSignSetting;
import com.ylz.bizDo.app.po.AppSignSubtable;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.app.po.AppWorkType;
import com.ylz.bizDo.app.vo.AppArchivingCardPeopleVo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.basicHealthEntity.AppEnterpatientEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.EnterpatientEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfile;
import com.ylz.bizDo.jtapp.basicHealthVo.EnterpatientVo;
import com.ylz.bizDo.jtapp.basicHealthVo.HomeServiceItemsQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppGrantInAidEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppMeddleEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.commonVo.AppHealthEducationQvo;
import com.ylz.bizDo.jtapp.commonVo.AppHealthGuideQvo;
import com.ylz.bizDo.jtapp.commonVo.WebSurrenderSignVo;
import com.ylz.bizDo.jtapp.drEntity.AppDrHealthListEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrPatientFwEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrPatientSignEntity;
import com.ylz.bizDo.jtapp.drEntity.AppGuideModelEntity;
import com.ylz.bizDo.jtapp.drEntity.AppLyPeopleEntity;
import com.ylz.bizDo.jtapp.drEntity.AppRefuseEntity;
import com.ylz.bizDo.jtapp.drEntity.AppServeEntity;
import com.ylz.bizDo.jtapp.drEntity.AppSignFormEntity;
import com.ylz.bizDo.jtapp.drEntity.ToplimitEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrHealthListQvo;
import com.ylz.bizDo.jtapp.drVo.AppDrSignSetQvo;
import com.ylz.bizDo.jtapp.drVo.AppFileAuditQvo;
import com.ylz.bizDo.jtapp.drVo.AppGuideTemplateQvo;
import com.ylz.bizDo.jtapp.drVo.AppHealthMeddleQvo;
import com.ylz.bizDo.jtapp.drVo.AppLyQvo;
import com.ylz.bizDo.jtapp.drVo.AppRefuseSignVo;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientRegisterEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.bizDo.jtapp.patientVo.AppPatientResgisterQvo;
import com.ylz.bizDo.jtapp.signSersetEntity.AppSignSerMealEntity;
import com.ylz.bizDo.jtapp.signSersetEntity.AppSignSettingEntity;
import com.ylz.bizDo.jtapp.signSersetVo.AppSignSerQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.comEnum.CommSF;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.CommonGuideType;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import com.ylz.packcommon.common.comEnum.DrPatientType;
import com.ylz.packcommon.common.comEnum.EconomicType;
import com.ylz.packcommon.common.comEnum.LabelManageType;
import com.ylz.packcommon.common.comEnum.NoticesMType;
import com.ylz.packcommon.common.comEnum.NoticesType;
import com.ylz.packcommon.common.comEnum.ResidentMangeType;
import com.ylz.packcommon.common.comEnum.SignFormDelType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.comEnum.UserJgType;
import com.ylz.packcommon.common.comEnum.UserUpHpisType;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.JsonUtil;
import com.ylz.packcommon.common.util.Md5Util;
import com.ylz.packcommon.common.util.PinyinUtil;
import com.ylz.packcommon.common.util.PropertiesUtil;

import net.sf.json.JSONObject;

/**
 * 签约接口action.
 */
@SuppressWarnings("all")
@Action(
        value="ysSign",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class YsSignAction extends CommonAction {

        /**
         * 同意签约接口
         * @param signFormId 签约单id
         * @param signPersGroup 居民分组 多分号隔开
         * @param signHealthGroup 健康分布
         * @param labelGruops 疾病类型接口 多分号隔开
         * @param signsJjType 经济类型
         */
        public String agreeSignForm(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                boolean typeBoolean = false;
                                //改服务人群为多选
                                if (StringUtils.isNotBlank(qvo.getSignPersGroup())) {
                                        String[] groups = qvo.getSignPersGroup().split(";");
                                        if (groups != null && groups.length > 0) {
                                                for(String s : groups){
                                                        if(!s.equals(ResidentMangeType.PTRQ.getValue()) && !s.equals(ResidentMangeType.WEIZHI.getValue())){
                                                                typeBoolean = true;
                                                        }
                                                }
                                        }
                                }
                                //经济类型
                                if(StringUtils.isNotBlank(qvo.getSignsJjType())){
                                        String[] jjlx = qvo.getSignsJjType().split(";");
                                        if (jjlx != null && jjlx.length > 0) {
                                                for(String s : jjlx){
                                                        if(!s.equals(EconomicType.YBRK.getValue())){
                                                                typeBoolean = true;
                                                        }
                                                }
                                        }
                                }
                                if(typeBoolean){
                                        AppDrUser drUser = this.getAppDrUser();
                                        if(drUser!=null){
                                                AppSignForm form = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,qvo.getSignFormId());
                                                AppHospDept dept =  (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                                if(dept!=null && form != null ){
                                                        if(StringUtils.isNotBlank(qvo.getSignPersGroup())&&qvo.getSignPersGroup().indexOf(ResidentMangeType.PTRQ.getValue())==-1){
                                                                CdAddressPeople cdAdd = sysDao.getCdAddressPeopleDao().findByCacheCode(dept.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                                                                if(cdAdd!=null){
                                                                        AppCommQvo app = new AppCommQvo();
                                                                        app.setSignType("1");//重点人群查询
                                                                        if("1".equals(cdAdd.getAreaDisSignWay())){//团队上限签约人数
                                                                                app.setTeamId(form.getSignTeamId());
                                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
                                                                                if (result >= Integer.parseInt(cdAdd.getAreaDisSignTop())) {
                                                                                        this.getAjson().setMsg("您团队重点签约人数已达上限!");
                                                                                        this.getAjson().setMsgCode("999");
                                                                                        return "ajson";
                                                                                }
                                                                        }else if("0".equals(cdAdd.getAreaDisSignWay())){
                                                                                app.setTeamId(form.getSignTeamId());
                                                                                //app.setDrId(form.getSignDrId());
//                                                                        Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
//                                                                        if (result >= Integer.parseInt(cdAdd.getAreaDisSignTop())) {
//                                                                                this.getAjson().setMsg("您的重点人群签约已上限!");
//                                                                                this.getAjson().setMsgCode("999");
//                                                                                return "ajson";
//                                                                        }
                                                                                int count = sysDao.getAppTeamMemberDao().findTeamPeopleCount(qvo.getTeamId());//查询整个团队人员数
                                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);//查询整个团队签约数
                                                                                if (result >= Integer.parseInt(cdAdd.getAreaDisSignTop())*count) {
                                                                                        this.getAjson().setMsg("您团队重点签约人数已达上限!");
                                                                                        this.getAjson().setMsgCode("999");
                                                                                        return "ajson";
                                                                                }
                                                                        }
                                                                }else{
                                                                        this.getAjson().setMsg("该机构未设置签约管理指标，请先设置");
                                                                        this.getAjson().setMsgCode("900");
                                                                        return "ajson";
                                                                }
                                                        }
                                                        AppSignForm sign=sysDao.getAppSignformDao().agreeSignForm(qvo.getSignFormId(),qvo.getSignPersGroup(),qvo.getSignHealthGroup(),qvo.getLabelGruops(),qvo.getFee(),qvo.getPayType(),qvo.getSignsJjType(),qvo.getPatientjmda(),qvo.getPatientjtda(),qvo.getSignlx(),qvo.getDrId(),qvo.getSignDrAssistantId());
                                                        this.getAjson().setVo(sign);
                                                        this.getAjson().setMsgCode("800");
                                                }else{
                                                        this.getAjson().setMsgCode("900");
                                                        this.getAjson().setMsg("参数错误!");
                                                }
                                        }
                                }else{
                                        AppSignForm sign=sysDao.getAppSignformDao().agreeSignForm(qvo.getSignFormId(),qvo.getSignPersGroup(),qvo.getSignHealthGroup(),qvo.getLabelGruops(),qvo.getFee(),qvo.getPayType(),qvo.getSignsJjType(),qvo.getPatientjmda(),qvo.getPatientjtda(),qvo.getSignlx(),qvo.getDrId(),qvo.getSignDrAssistantId());
                                        this.getAjson().setVo(sign);
                                        this.getAjson().setMsgCode("800");
                                }


                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

        /**
         * 拒接签约接口
         * @param signFormId 签约单id
         * @param signOthnerReason;其他原因
         */
        public String refuseSignForm(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                AppSignForm sign= (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,qvo.getSignFormId());
                                AppDrUser user = sysDao.getAppDrUserDao().findByUserId(sign.getSignDrId());
                                if(SignFormType.YUZQ.getValue().equals(sign.getSignState())){
                                        List<AppGotoSignRecord> records = sysDao.getServiceDo().loadByPk(AppGotoSignRecord.class,"gtsSignId",sign.getId());
                                        if(records!=null && records.size()>0){
                                                AppGotoSignRecord record = records.get(0);
                                                AppSignForm form = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,record.getGtsOldSignId());
                                                if(form!=null){
                                                        form.setSignGoToSignState("0");
                                                        sysDao.getServiceDo().modify(form);
                                                }
                                        }
                                }
                                sign.setSignState("8");
                                sign.setSignOthnerReason(qvo.getSignOthnerReason());
                                sysDao.getServiceDo().modify(sign);
                                this.getAjson().setVo(sign);
                                this.getAjson().setMsgCode("800");
                                String dcontent = user.getDrName()+ "医生：拒绝您的签约申请,拒绝原因:"+qvo.getSignOthnerReason()+"!";
                                sysDao.getAppNoticeDao().addNotices("解约消息", dcontent, NoticesType.QYXX.getValue(), user.getId(), sign.getSignPatientId(), null, DrPatientType.DR.getValue());
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

        /**
         * 待解约同意接口
         * @param signFormId 签约单id
         */
        public String surrenderOkSignForm(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                AppSignForm sign= (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,qvo.getSignFormId());
                                AppDrUser user = sysDao.getAppDrUserDao().findByUserId(sign.getSignDrId());
                                sign.setSignState("4");
                                sign.setSignSurrenderDate(Calendar.getInstance());
                                sysDao.getServiceDo().modify(sign);
                                //同意解约修改建档立卡居民的签约信息
                                sysDao.getAppArchivingCardPeopeDao().changeArchiSignState(sign.getSignPatientId(),null);
                                this.getAjson().setVo(sign);
                                this.getAjson().setMsgCode("800");
                                String dcontent = "医生:"+user.getDrName()+ "：同意您的申请解约!";
                                sysDao.getAppNoticeDao().addNotices("解约消息", dcontent, NoticesType.QYXX.getValue(), user.getId(), sign.getSignPatientId(), null, DrPatientType.DR.getValue());
                                String result = null;
                                try {
                                        result = PropertiesUtil.getConfValue("messageCode");
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                                if("0".equals(result)){
                                        AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,sign.getSignTeamId());
                                        if(team != null) {
                                                //环信退讨论组
                                                sysDao.getSecurityCardAsyncBean().removeRoomMembers(team.getTeamEaseRoomId(),sign.getSignPatientId(),CommonShortType.HUANGZHE.getValue());
                                        }
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

        /**
         * 待解约拒绝接口
         * @param signFormId 签约单id
         * @param signUrrenderReason 解约原因
         */
        public String surrenderNoSignForm(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                AppSignForm sign= (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,qvo.getSignFormId());
                                AppDrUser user = sysDao.getAppDrUserDao().findByUserId(sign.getSignDrId());
                                sign.setSignState("2");
                                sign.setSignUrrenderReason(qvo.getSignUrrenderReason());
                                sysDao.getServiceDo().modify(sign);
                                this.getAjson().setVo(sign);
                                this.getAjson().setMsgCode("800");
                                String dcontent = "医生:"+user.getDrName()+ "：拒绝您的申请解约!";
                                sysDao.getAppNoticeDao().addNotices("解约消息", dcontent, NoticesType.QYXX.getValue(), user.getId(), sign.getSignPatientId(), null, DrPatientType.DR.getValue());
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

        /**
         * 新增指导/干预模板
         * @return
         */
        public String  guideTemplate(){
             try{
                     AppGuideTemplate gt = new AppGuideTemplate();
                     AppGuideTemplateQvo qvo = (AppGuideTemplateQvo)getAppJson(AppGuideTemplateQvo.class);
                     if(qvo==null){
                             this.getAjson().setMsg("参数格式错误");
                             this.getAjson().setMsgCode("900");
                     }else{
                             gt.setGuideContent(qvo.getContent());
                             gt.setGuideTitle(qvo.getTitle());
                             gt.setGuideType(CommonGuideType.JKZD.getValue());
                             AppDrUser drUser = this.getAppDrUser();
                             if(drUser!=null){
                                     if(drUser.getDrRole().indexOf(AppRoleType.SHEQU.getValue())!=-1){
                                             gt.setGuideHospId(drUser.getDrHospId());
                                     }else{
                                             gt.setGuideCreateId(drUser.getId());
                                     }
                             }
                             gt.setGuideDiseaseType(qvo.getDiseaseType());
                             gt.setGuideMeddleType(qvo.getMeddleType());
                             if(StringUtils.isNotBlank(qvo.getImageUrl())){
                                     if(StringUtils.isBlank(qvo.getImageName())){
                                             qvo.setImageName(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                                     }
//                                     String path = this.getSysDao().getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureYz"),qvo.getImageName());
//                                     FileUtils.decoderBase64File(qvo.getImageUrl(),PropertiesUtil.getConfValue("filePicture")+path);
//                                     gt.setGuideImageUrl(path);
                                     Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getImageUrl(),CommonShortType.YISHENG.getValue());
                                     gt.setGuideImageUrl(map.get("objectUrl").toString());
                             }
                             Calendar cal = Calendar.getInstance();
                             gt.setGuideCreateTime(cal);
                             this.sysDao.getServiceDo().add(gt);
                             this.getAjson().setMsgCode("800");
                             this.getAjson().setMsg("保存成功");
                     }
             }catch (Exception e){
                  e.printStackTrace();
                  this.getAjson().setMsgCode("900");
                  this.getAjson().setMsg(e.getMessage());
             }
             return "ajson";
        }

        /**
         * 查找指导/干预模板
         * @return
         */
        public String findGuide(){
                try{
                        AppGuideTemplateQvo qvo = (AppGuideTemplateQvo)getAppJson(AppGuideTemplateQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else{
                                List<AppGuideModelEntity> ls = this.sysDao.getAppGuideTemplateDao().findByQvo(qvo);
                                this.getAjson().setRows(ls);
                                this.getAjson().setMsgCode("800");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }
        /**
         * 给增患者发指导
         * @return
         */
        public String addHealthGuide(){
                try{
                        AppHealthGuideQvo qvo = (AppHealthGuideQvo)getAppJson(AppHealthGuideQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else{
                                AppHealthGuide hg = new AppHealthGuide();
                                hg.setHgContent(qvo.getContent());
                                hg.setHgModId(qvo.getModId());
                                hg.setHgTitle(qvo.getTitle());
                                hg.setHgPatientId(qvo.getPatientId());
                                hg.setHgType(qvo.getType());
                                hg.setHgModId(qvo.getModId());
                                Calendar cal = Calendar.getInstance();
                                hg.setHgTime(cal);
                                hg.setHgDrId(qvo.getDrId());
                                if(StringUtils.isNotBlank(qvo.getDrId())){
                                        AppDrUser drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
                                        if(drUser!=null){
                                                AppHospDept hospDept = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                                if(hospDept!=null){
                                                        hg.setHgHospId(hospDept.getId());
                                                        hg.setHgAreaCode(hospDept.getHospAreaCode());
                                                }
                                        }

                                }
                                hg.setId("null");
                                this.sysDao.getServiceDo().add(hg);
                                this.getAjson().setMsg("保存成功");
                                this.getAjson().setMsgCode("800");

                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }

                return "ajson";
        }

        /**
         * 医生查询所有健康教育--无条件
         * 医生搜索健康教育 -- content 不为空
         * 医生查找自己添加的健康教育 -- id不为空
         * @return
         */
        public String drFindHealthEducation(){
                try{
                        AppDrHealthListQvo qvo = (AppDrHealthListQvo)getAppJson(AppDrHealthListQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else{
                                List<AppDrHealthListEntity> ls = this.sysDao.getNewsTableDao().findAll(qvo);
                                this.getAjson().setRows(ls);
                                this.getAjson().setMsgCode("800");
                        }
                }catch(Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

        /**
         * 查询医生收藏健康教育列表
         * @return
         */
        public String appDrHealthEnshrine(){
                try{
                        AppDrHealthListQvo qvo = (AppDrHealthListQvo)getAppJson(AppDrHealthListQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else{
                                if(StringUtils.isNotBlank(qvo.getId())&&StringUtils.isNotBlank(qvo.getIsEnshrine())){
                                        List<AppDrHealthListEntity> newList = this.sysDao.getNewsTableDao().findDrByEnshrine(qvo);
                                        this.getAjson().setRows(newList);
                                        this.getAjson().setMsgCode("800");
                                }else{
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("传递的参数错误");
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("系统错误,请联系管理员");
                }
                return "ajson";
        }
        /**
         * 医生新增健康教育模板NEWS_TABLE表
         * @return
         */
        public String saveHealthEducation(){
                try{
                        AppHealthEducationQvo qvo = (AppHealthEducationQvo)getAppJson(AppHealthEducationQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsg("参数格式错误");
                                this.getAjson().setMsgCode("900");
                        }else{
                                AppDrUser drUser = this.getAppDrUser();
                                if(drUser!=null){
                                        qvo.setDrId(drUser.getId());
                                        qvo.setDrName(drUser.getDrName());
                                        qvo.setHospId(drUser.getDrHospId());
                                        if(StringUtils.isNotBlank(qvo.getNewId())){
                                                //只有医生权限的要有患者
                                                if(StringUtils.isBlank(qvo.getPatientId())){
                                                        this.getAjson().setMsg("请选择发送对象");
                                                        this.getAjson().setMsgCode("900");
                                                        return "ajson";
                                                }
                                        }
                                        this.sysDao.getNewsTableDao().saveHealth(qvo);
                                        this.getAjson().setMsgCode("800");
                                        this.getAjson().setMsg("保存健康教育成功");
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";

        }

        /**
         * 医生代理注册
         * @return
         */
        public String appSignRegisterTemp(){
                try{
                        AppPatientResgisterQvo qvo = (AppPatientResgisterQvo)getAppJson(AppPatientResgisterQvo.class);
                        if(qvo!=null){
                                AppPatientUserEntity patient = sysDao.getAppPatientUserDao().findByUserIdNoCardTel(qvo.getUserIdNo(),qvo.getUserCrad(),qvo.getUserTel());
                                if(patient == null){
                                        String userIdNo  = qvo.getUserIdNo().toLowerCase();
                                        String resultIdNo = CardUtil.IDCardValidate(userIdNo);
                                        if(StringUtils.isNotBlank(resultIdNo)){
                                                this.getAjson().setMsgCode("900");
                                                this.getAjson().setMsg(resultIdNo);
                                                return "ajson";
                                        }else{
                                                String requestUserId = null;
                                                String requestUserName = null;
                                                AppDrUser drUser = this.getAppDrUser();
                                                if(drUser!=null){
                                                        requestUserId = drUser.getId();
                                                        requestUserName = drUser.getDrName();
                                                }
                                                String resultCard = this.getSysDao().getSecurityCardAsyncBean().getSecurityCardNotAsync(qvo.getUserIdNo(),qvo.getUserName(),requestUserId,requestUserName,DrPatientType.DR.getValue());
                                                if(StringUtils.isNotBlank(resultCard)){
                                                        if(resultCard.contains(qvo.getUserCrad())){

                                                        }else{
                                                                this.getAjson().setMsgCode("900");
                                                                this.getAjson().setMsg("身份证和社保卡不匹配,不能代理签约!");
                                                                return "ajson";
                                                        }
                                                }

                                                AppPatientUser user = new AppPatientUser();
                                                Map<String,Object> idNos;
                                                if(qvo.getUserIdNo().length() == 18){
                                                        idNos = CardUtil.getCarInfo(qvo.getUserIdNo());
                                                }else{
                                                        idNos = CardUtil.getCarInfo15W(qvo.getUserIdNo());
                                                }
                                                user.setPatientName(qvo.getUserName());
                                                user.setPatientIdno(qvo.getUserIdNo());
                                                user.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
                                                user.setPatientAge(AgeUtil.getAgeYear(user.getPatientBirthday()));
                                                user.setPatientGender(idNos.get("sex").toString());
                                                user.setPatientCard(qvo.getUserCrad());
                                                user.setPatientTel(qvo.getUserTel());
                                                user.setPatientState(CommonEnable.QIYONG.getValue());
                                                user.setPatientHealthy(CommonEnable.JINYONG.getValue());
                                                user.setPatientUpHpis(UserUpHpisType.JIHUO.getValue());//用户激活
                                                if(StringUtils.isNotBlank(qvo.getUserTel())){
                                                        user.setPatientPwd(Md5Util.MD5(qvo.getUserTel().substring(qvo.getUserTel().length()-6,qvo.getUserTel().length())));
                                                }else{
                                                        user.setPatientPwd(Md5Util.MD5(qvo.getUserIdNo().substring(qvo.getUserIdNo().length()-6,qvo.getUserIdNo().length())));
                                                }

                                                this.getSysDao().getServiceDo().add(user);
                                                patient = sysDao.getAppPatientUserDao().findByUserIdNo(qvo.getUserIdNo());
                                        }
                                }
                                AppSignForm signForm = sysDao.getAppSignformDao().findSignOne(patient.getId());
                                if(signForm!=null){
                                        this.getAjson().setVo(signForm);
                                }else{
                                        AppSignForm form=sysDao.getAppSignformDao().signFormUser(patient.getId(),qvo.getTeamId(),qvo.getDrId(),qvo.getSignpackageid(),"1",null,null,null,null);
                                        this.getAjson().setVo(signForm);
                                }
                                this.getAjson().setMsg("签约成功!");
                        }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("系统错误,请联系管理员");
                }
                return "ajson";
        }

        /**
         * 查询签约医保类型
         * @return
         */
        public String appSignmedicalInsuranceType(){
                try{
                        AppDrUser drUser = this.getAppDrUser();
                        if(drUser != null){
                                AppHospDept hospDept = (AppHospDept) this.getSysDao().getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                if(hospDept != null){
                                        List<CdCode> lsCode = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SIGN_LX,CommonEnable.QIYONG.getValue());
                                        String areaCode = AreaUtils.getAreaCode(hospDept.getHospAreaCode(),"2");
                                        if("3501".equals(areaCode)){//福州
                                                lsCode.remove(lsCode.size()-1);
                                        }else{
                                                lsCode.remove(0);
                                        }
                                        this.getAjson().setRows(lsCode);
                                }else{
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("系统错误,请联系管理员");
                                }
                        }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("系统错误,请联系管理员");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("系统错误,请联系管理员");
                }
                return "ajson";
        }

        /**
         * 医生代理注册
         * @return
         */
        public String appSignRegister(){
                try{

                        AppPatientResgisterQvo qvo = (AppPatientResgisterQvo)getAppJson(AppPatientResgisterQvo.class);
                        if(qvo!=null){
                                String requestUserId = null;
                                String requestUserName = null;
                                String userType = null;
                                AppDrUser drUser = this.getAppDrUser();
                                AppHospDept hospDept = (AppHospDept) this.getSysDao().getServiceDo().find(AppHospDept.class, drUser.getDrHospId());
                                if(drUser!=null){
                                        String mealId = "";
                                        String mealName = "";
                                        userType = "2";
                                        requestUserId = drUser.getId();
                                        requestUserName = drUser.getDrName();
                                        if(hospDept!=null){
                                                if(SignFormType.POSSTATE.getValue().equals(qvo.getSignUpHpis())){
                                                        if("1".equals(qvo.getQuickState())){
                                                                if(StringUtils.isBlank(hospDept.getHospMealId())){
                                                                        this.getAjson().setMsgCode("900");
                                                                        this.getAjson().setMsg("请配置快捷签约服务套餐");
                                                                        return "ajson";
                                                                }else{
                                                                        mealId = hospDept.getHospMealId();
                                                                        if(StringUtils.isNotBlank(mealId)){
                                                                                AppServeSetmeal vmeal = (AppServeSetmeal)sysDao.getServiceDo().find(AppServeSetmeal.class,mealId);
                                                                                if(vmeal != null){
                                                                                        mealName = vmeal.getSersmName();
                                                                                }
                                                                        }
                                                                }
                                                        }
                                                }

                                                CdAddressPeople cdAdd = sysDao.getCdAddressPeopleDao().findByCacheCode(hospDept.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                                                if(cdAdd!=null){
                                                        AppCommQvo app = new AppCommQvo();
                                                        app.setSignType("0");//普通人群查询
                                                        if("1".equals(cdAdd.getAreaSignWay())){//团队上限签约人数
                                                                app.setTeamId(qvo.getTeamId());
                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
                                                                if (result >= Integer.parseInt(cdAdd.getAreaSignTop())) {
                                                                        this.getAjson().setMsg("您的团队签约人数已达上限!");
                                                                        this.getAjson().setMsgCode("900");
                                                                        return "ajson";
                                                                }
                                                        }else if("0".equals(cdAdd.getAreaSignWay())){
//                                                                app.setTeamId(qvo.getTeamId());
//                                                                app.setDrId(qvo.getDrId());
//                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
//                                                                if (result >= Integer.parseInt(cdAdd.getAreaSignTop())) {
//                                                                        this.getAjson().setMsg("您的签约已上限!");
//                                                                        this.getAjson().setMsgCode("900");
//                                                                        return "ajson";
//                                                                }
                                                                app.setTeamId(qvo.getTeamId());
                                                                int count = sysDao.getAppTeamMemberDao().findTeamPeopleCount(qvo.getTeamId());//查询整个团队人员数
                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);//查询整个团队签约数
                                                                if (result >= Integer.parseInt(cdAdd.getAreaSignTop())*count) {
                                                                        this.getAjson().setMsg("您的团队签约人数已达上限!");
                                                                        this.getAjson().setMsgCode("900");
                                                                        return "ajson";
                                                                }
                                                        }
                                                }
                                        }
                                        String code = AreaUtils.getAreaCode(hospDept.getHospAreaCode(),"2");
                                        boolean upFile = true;
                                        if(StringUtils.isNotBlank(qvo.getSignUpHpis())){
                                                if(SignFormType.POSSTATE.getValue().equals(qvo.getSignUpHpis())){
                                                        upFile = false;
                                                }
                                        }
                                        if(upFile){
                                                //判断附件上传条件
                                                AppSignSetting settt = sysDao.getAppSignSettingDao().findByAreaCode(code);
                                                if(settt != null){
                                                        if("1".equals(settt.getSerImageState())){//该市必须上传附件
                                                                if("0".equals(qvo.getFlagFileUp())){//还没上传附件
                                                                        this.getAjson().setMsgCode("900");
                                                                        this.getAjson().setMsg("请上传附件图片");
                                                                        return "ajson";
                                                                }
                                                        }
                                                }
                                        }
                                        CdCode codeCity = this.getSysDao().getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                                        AppPatientRegisterEntity patient = sysDao.getAppPatientUserDao().findByUserIdNoCardTelRegister(qvo.getUserIdNo(),null,null);
                                        //获取地址信息
                                        String proAddr = "";//省
                                        String cityAddr = "";//市
                                        String areaAddr = "";//区
                                        String streetAddr = "";//街道
                                        String jwhAddr = "";//居委会
                                        String jwhTitle = "";
                                        if(patient != null){
                                                AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,patient.getId());
                                                if(user != null){
                                                        if(StringUtils.isNotBlank(qvo.getUserTel()) && qvo.getUserTel().length()>=11){
                                                                if(!qvo.getUserTel().equals(user.getPatientTel())){
                                                                        user.setPatientTel(qvo.getUserTel());
                                                                        user.setPatientPwd(Md5Util.MD5(qvo.getUserTel().substring(qvo.getUserTel().length()-6,qvo.getUserTel().length())));
                                                                        sysDao.getServiceDo().modify(user);
                                                                        patient.setPatientTel(user.getPatientTel());
                                                                }
                                                        }
                                                        if(StringUtils.isNotBlank(qvo.getUserCrad())){
                                                                if(!qvo.getUserCrad().equals(user.getPatientCard())){
                                                                       user.setPatientCard(qvo.getUserCrad());
                                                                       sysDao.getServiceDo().modify(user);
                                                                       patient.setPatientCard(user.getPatientCard());
                                                                }
                                                        }
                                                }

                                                this.getAjson().setVo(patient);
                                                Map<String,Object> map  = new HashMap<String,Object>();
                                                //居民服务
                                                List<AppLabelManage> lsjmfw = this.sysDao.getAppLabelManageDao().findByType("3");
                                                //疾病类型
                                                List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("2");
                                                //居民标签
                                                List<AppLabelManage> lss = this.sysDao.getAppLabelManageDao().findByType("1");
                                                if(codeCity != null){
                                                        if(StringUtils.isNotBlank(codeCity.getCodeTitle())){
                                                                //获取居民服务类型
                                                                AppDrPatientFwEntity vv = this.sysDao.getAppSignformDao().findFwType(patient.getId(),codeCity.getCodeTitle(),requestUserId,requestUserName,userType);
                                                                map.put("fwlx",vv);
                                                        }else{
                                                                //获取居民服务类型
                                                                AppDrPatientFwEntity vv = this.sysDao.getAppSignformDao().findFwType(patient.getId(),patient.getCityCode(),requestUserId,requestUserName,userType);
                                                                map.put("fwlx",vv);
                                                        }
                                                }else{
                                                        //获取居民服务类型
                                                        AppDrPatientFwEntity vv = this.sysDao.getAppSignformDao().findFwType(patient.getId(),patient.getCityCode(),requestUserId,requestUserName,userType);
                                                        map.put("fwlx",vv);
                                                }

                                               /* if(StringUtils.isNotBlank(patient.getCityCode())){
                                                        //获取居民服务类型
                                                        AppDrPatientFwEntity vv = this.sysDao.getAppSignformDao().findFwType(patient.getId(),patient.getCityCode(),requestUserId,requestUserName,userType);
                                                        map.put("fwlx",vv);
                                                }else{
                                                        //获取居民服务类型
                                                        AppDrPatientFwEntity vv = this.sysDao.getAppSignformDao().findFwType(patient.getId(),codeCity.getCodeTitle(),requestUserId,requestUserName,userType);
                                                        map.put("fwlx",vv);
                                                }*/
                                                //经济类型
//                                        List<AppMeddleEntity> jjType = this.getSysDao().getCodeDao().findMeddle(CodeGroupConstrats.GROUP_JJLX,CommonEnable.QIYONG.getValue());
                                                List<AppMeddleEntity> jjType = this.getSysDao().getAppLabelManageDao().findByMeddle(LabelManageType.JJLX.getValue());
                                                //状态
//                                      List<CdCode> state = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_VIP,CommonEnable.QIYONG.getValue());
                                                HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                                                qqvo.setIdno(patient.getPatientIdno());
                                                qqvo.setType("2");
                                                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, hospDept.getHospAreaCode().substring(0,4));
                                                if(value!=null){
                                                        qqvo.setUrlType(value.getCodeTitle());
                                                }
                                                String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,requestUserId,requestUserName,userType);
                                                if(StringUtils.isNotBlank(str)){
                                                        T_dwellerfile entitys = sysDao.getSecurityCardAsyncBean().analysisFileOne(str, qqvo.getUrlType());
                                                        if (entitys != null) {
                                                                map.put("jtdnh",entitys.getJtdah());
                                                                map.put("jmdnh",entitys.getJmdah());
                                                                String result = "";
                                                                if (AddressType.FZ.getValue().equals(value.getCodeTitle())) {

                                                                } else if (AddressType.QZ.getValue().equals(value.getCodeTitle())) {
                                                                        result = "qz_";
                                                                } else if (AddressType.ZZ.getValue().equals(value.getCodeTitle())) {
                                                                        result = "zz_";
                                                                } else if (AddressType.PT.getValue().equals(value.getCodeTitle())) {
                                                                        result = "pt_";
                                                                } else if (AddressType.NP.getValue().equals(value.getCodeTitle())) {
                                                                        result = "np_";
                                                                } else if (AddressType.SM.getValue().equals(value.getCodeTitle())) {
                                                                        result = "sm_";
                                                                } else if (AddressType.LY.getValue().equals(value.getCodeTitle())) {
                                                                        result = "ly_";
                                                                } else if (AddressType.ND.getValue().equals(value.getCodeTitle())) {
                                                                        result = "nd_";
                                                                } else if (AddressType.XM.getValue().equals(value.getCodeTitle())) {
                                                                        result = "xm_";
                                                                } else if (AddressType.PINGT.getValue().equals(value.getCodeTitle())) {
                                                                        result = "pg_";
                                                                }
                                                                map.put("orgId",result+entitys.getSqh());
                                                                //根据健康档案获取地址信息
                                                                                                /*if(StringUtils.isNotBlank(entitys.getSheng())){//省
                                                                                                        CdAddress address = sysDao.getCdAddressDao().findByNameOrUpCode(entitys.getSheng(),null,null);
                                                                                                        if(address != null){
                                                                                                                proAddr = address.getCtcode();
                                                                                                                jwhTitle = address.getAreaName();
                                                                                                        }
                                                                                                }
                                                                                                if(StringUtils.isNotBlank(entitys.getShi())){//市
                                                                                                        CdAddress address = sysDao.getCdAddressDao().findByNameOrUpCode(null,entitys.getShi(),proAddr);
                                                                                                        if(address != null){
                                                                                                                cityAddr = address.getCtcode();
                                                                                                                jwhTitle = address.getAreaName();
                                                                                                        }
                                                                                                }
                                                                                                if(StringUtils.isNotBlank(entitys.getXian())){//区
                                                                                                        CdAddress address = sysDao.getCdAddressDao().findByNameOrUpCode(null,entitys.getXian(),cityAddr);
                                                                                                        if(address != null){
                                                                                                                areaAddr = address.getCtcode();
                                                                                                                jwhTitle = address.getAreaName();
                                                                                                        }
                                                                                                }
                                                                                                if(StringUtils.isNotBlank(entitys.getXiang())){//街道
                                                                                                        CdAddress address = sysDao.getCdAddressDao().findByNameOrUpCode(null,entitys.getXiang(),areaAddr);
                                                                                                        if(address != null){
                                                                                                                streetAddr = address.getCtcode();
                                                                                                                jwhTitle = address.getAreaName();
                                                                                                        }
                                                                                                }

                                                                                                if(StringUtils.isNotBlank(entitys.getCun())){//村
                                                                                                        jwhTitle += entitys.getCun();
                                                                                                        CdAddress address = sysDao.getCdAddressDao().findByNameOrUpCode(jwhTitle,null,streetAddr);
                                                                                                        if(address != null){
                                                                                                                jwhAddr = address.getCtcode();
                                                                                                        }
                                                                                                }*/

                                                        }
                                                }

                                                map.put("jmfw",lsjmfw);
                                                map.put("jblx",ls);
                                                map.put("jmbq",lss);
                                                map.put("jjlx",jjType);
                                                Map<String,Object> mealMap = new HashMap<>();
                                                mealMap.put("mealId",mealId);
                                                mealMap.put("mealName",mealName);
                                                map.put("fwb",mealMap);
//                                              map.put("state",state);
                                                /*if(StringUtils.isBlank(user.getPatientProvince())){//如果居民地址信息为空

                                                }*/
                                                this.getAjson().setVo(patient);
                                                this.getAjson().setMap(map);
                                        }else{
//                                                if(StringUtils.isNotBlank(qvo.getUserCrad())){
//                                                        this.getAjson().setMsgCode("900");
//                                                        this.getAjson().setMsg("社保卡不能为空!");
//                                                        return "ajson";
//                                                }
//                                                if(StringUtils.isNotBlank(qvo.getUserTel())){
//                                                        this.getAjson().setMsgCode("900");
//                                                        this.getAjson().setMsg("手机号码不能为空!");
//                                                        return "ajson";
//                                                }
                                                String userIdNo  = qvo.getUserIdNo().toLowerCase();
                                                String resultIdNo = CardUtil.IDCardValidate(userIdNo);
                                                if(StringUtils.isNotBlank(resultIdNo)){
                                                        this.getAjson().setMsgCode("900");
                                                        this.getAjson().setMsg(resultIdNo);
                                                        return "ajson";
                                                }else{
//                                                        if(StringUtils.isNotBlank(qvo.getUserCrad())){
//                                                                String resultCard = this.getSysDao().getSecurityCardAsyncBean().getSecurityCardNotAsync(qvo.getUserIdNo(),qvo.getUserName());
//                                                                if(StringUtils.isNotBlank(resultCard)){
//                                                                        if(resultCard.contains(qvo.getUserCrad())){
//
//                                                                        }else{
//                                                                                this.getAjson().setMsgCode("900");
//                                                                                this.getAjson().setMsg("身份证和社保卡不匹配,不能代理签约!");
//                                                                                return "ajson";
//                                                                        }
//                                                                }
//                                                        }

//                                                        if(StringUtils.isNotBlank(qvo.getUserTel())){
//                                                                boolean tel = this.getSysDao().getAppPatientUserDao().findPatientByTel(qvo.getUserTel(),UserUpHpisType.JIHUO.getValue());
//                                                                if(tel){
//                                                                        this.getAjson().setMsgCode("900");
//                                                                        this.getAjson().setMsg("手机号已存在,不能重复注册!");
//                                                                        return "ajson";
//                                                                }
//                                                        }


                                                        Map<String,Object> map  = new HashMap<String,Object>();
                                                        HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                                                        qqvo.setIdno(qvo.getUserIdNo());
                                                        qqvo.setType("2");
                                                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, hospDept.getHospAreaCode().substring(0,4));
                                                        if(value!=null){
                                                                qqvo.setUrlType(value.getCodeTitle());
                                                        }
                                                        String patientAddress = null;
                                                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,requestUserId,requestUserName,userType);
                                                        JSONObject jsonall = JSONObject.fromObject(str);
                                                        if(jsonall.get("vo")!=null && "800".equals(jsonall.get("msgCode"))) {
                                                                if (jsonall.get("entity") != null) {
                                                                        if(!"null".equals(jsonall.get("entity").toString())){
                                                                                JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                                                                if ("true".equals(entity.get("success").toString())) {
                                                                                        T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
                                                                                        if (entitys != null) {
                                                                                                map.put("jtdnh",entitys.getJtdah());
                                                                                                map.put("jmdnh",entitys.getJmdah());
                                                                                                patientAddress = entitys.getCun()+entitys.getMphm();
                                                                                                String result = "";
                                                                                                if (AddressType.FZ.getValue().equals(value.getCodeTitle())) {

                                                                                                } else if (AddressType.QZ.getValue().equals(value.getCodeTitle())) {
                                                                                                        result = "qz_";
                                                                                                } else if (AddressType.ZZ.getValue().equals(value.getCodeTitle())) {
                                                                                                        result = "zz_";
                                                                                                } else if (AddressType.PT.getValue().equals(value.getCodeTitle())) {
                                                                                                        result = "pt_";
                                                                                                } else if (AddressType.NP.getValue().equals(value.getCodeTitle())) {
                                                                                                        result = "np_";
                                                                                                } else if (AddressType.SM.getValue().equals(value.getCodeTitle())) {
                                                                                                        result = "sm_";
                                                                                                } else if (AddressType.LY.getValue().equals(value.getCodeTitle())) {
                                                                                                        result = "ly_";
                                                                                                } else if (AddressType.ND.getValue().equals(value.getCodeTitle())) {
                                                                                                        result = "nd_";
                                                                                                } else if (AddressType.XM.getValue().equals(value.getCodeTitle())) {
                                                                                                        result = "xm_";
                                                                                                } else if (AddressType.PINGT.getValue().equals(value.getCodeTitle())) {
                                                                                                        result = "pg_";
                                                                                                }
                                                                                                map.put("orgId",result+entitys.getSqh());
                                                                                                if(StringUtils.isNotBlank(entitys.getXzqydm())){//获取健康档案返回的最低区域编码
                                                                                                        CdAddressConfiguration cp = sysDao.getCdAddressDao().findByCodeJw(entitys.getXzqydm());
                                                                                                        if(cp!=null){
                                                                                                                CdAddress cdAddress = sysDao.getCdAddressDao().findByCode(cp.getId());
                                                                                                                if(cdAddress != null){
                                                                                                                        if("5".equals(cdAddress.getLevel())){//居委会
                                                                                                                                proAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                                cityAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"2")+"00000000";
                                                                                                                                areaAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"3")+"000000";
                                                                                                                                streetAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"4")+"000";
                                                                                                                                jwhAddr = cdAddress.getCtcode();
                                                                                                                        }else if("4".equals(cdAddress.getLevel())){//街道
                                                                                                                                proAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                                cityAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"2")+"00000000";
                                                                                                                                areaAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"3")+"000000";
                                                                                                                                streetAddr = cdAddress.getCtcode();
                                                                                                                        }else if("3".equals(cdAddress.getLevel())){//区
                                                                                                                                proAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                                cityAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"2")+"00000000";
                                                                                                                                areaAddr = cdAddress.getCtcode();
                                                                                                                        }else if("2".equals(cdAddress.getLevel())){//市
                                                                                                                                proAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                                cityAddr = cdAddress.getCtcode();
                                                                                                                        }else if("1".equals(cdAddress.getLevel())){
                                                                                                                                proAddr = cdAddress.getCtcode();
                                                                                                                        }
                                                                                                                }
                                                                                                        }else{
                                                                                                                CdAddress cdAddress = sysDao.getCdAddressDao().findByCode(entitys.getXzqydm());
                                                                                                                if(cdAddress != null){
                                                                                                                        if("5".equals(cdAddress.getLevel())){//居委会
                                                                                                                                proAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                                cityAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"2")+"00000000";
                                                                                                                                areaAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"3")+"000000";
                                                                                                                                streetAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"4")+"000";
                                                                                                                                jwhAddr = cdAddress.getCtcode();
                                                                                                                        }else if("4".equals(cdAddress.getLevel())){//街道
                                                                                                                                proAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                                cityAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"2")+"00000000";
                                                                                                                                areaAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"3")+"000000";
                                                                                                                                streetAddr = cdAddress.getCtcode();
                                                                                                                        }else if("3".equals(cdAddress.getLevel())){//区
                                                                                                                                proAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                                cityAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"2")+"00000000";
                                                                                                                                areaAddr = cdAddress.getCtcode();
                                                                                                                        }else if("2".equals(cdAddress.getLevel())){//市
                                                                                                                                proAddr = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                                cityAddr = cdAddress.getCtcode();
                                                                                                                        }else if("1".equals(cdAddress.getLevel())){
                                                                                                                                proAddr = cdAddress.getCtcode();
                                                                                                                        }
                                                                                                                }
                                                                                                        }
                                                                                                }
                                                                                        }
                                                                                }
                                                                        }
                                                                }
                                                        }
                                                        AppPatientUser user = new AppPatientUser();
                                                        Map<String,Object> idNos;
                                                        if(qvo.getUserIdNo().length() == 18){
                                                                idNos = CardUtil.getCarInfo(qvo.getUserIdNo());
                                                        }else{
                                                                idNos = CardUtil.getCarInfo15W(qvo.getUserIdNo());
                                                        }
                                                        user.setPatientName(qvo.getUserName());
                                                        user.setPatientIdno(qvo.getUserIdNo());
                                                        user.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
                                                        user.setPatientAge(AgeUtil.getAgeYear(user.getPatientBirthday()));
                                                        user.setPatientGender(idNos.get("sex").toString());
                                                        user.setPatientCard(qvo.getUserCrad());
                                                        user.setPatientTel(qvo.getUserTel());
                                                        user.setPatientState(CommonEnable.QIYONG.getValue());
                                                        user.setPatientHealthy(CommonEnable.JINYONG.getValue());
                                                        user.setPatientJgState(UserJgType.WEISHEZHI.getValue());
                                                        user.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(user.getPatientName()));
                                                        user.setPatientUpHpis(UserUpHpisType.WEIJIHUO.getValue());//用户未激活
                                                        //先取健康档案的地址信息
                                                        if(StringUtils.isNotBlank(proAddr)){
                                                                user.setPatientProvince(proAddr);
                                                                user.setPatientCity(cityAddr);
                                                                user.setPatientArea(areaAddr);
                                                                user.setPatientStreet(streetAddr);
                                                                user.setPatientNeighborhoodCommittee(jwhAddr);

                                                        }
                                                        if(StringUtils.isNotBlank(patientAddress)){
                                                                user.setPatientAddress(patientAddress);
                                                        }
                                                        if(StringUtils.isBlank(user.getPatientProvince())){//如果健康档案没有地址信息，取机构所在地
                                                                if(StringUtils.isNotBlank(hospDept.getHospAreaCode())){
                                                                        String pros = AreaUtils.getAreaCodeAll(hospDept.getHospAreaCode(),"1");
                                                                        String citys = AreaUtils.getAreaCodeAll(hospDept.getHospAreaCode(),"2");
                                                                        String areas = AreaUtils.getAreaCodeAll(hospDept.getHospAreaCode(),"3");
                                                                        user.setPatientProvince(pros);
                                                                        user.setPatientCity(citys);
                                                                        user.setPatientArea(areas);
                                                                        user.setPatientStreet(hospDept.getHospAreaCode());
                                                                        if(StringUtils.isNotBlank(patientAddress)){
                                                                                user.setPatientAddress(patientAddress);
                                                                        }
                                                                }
                                                        }
                                                        if(StringUtils.isNotBlank(qvo.getUserTel())){
                                                                user.setPatientPwd(Md5Util.MD5(qvo.getUserTel().substring(qvo.getUserTel().length()-6,qvo.getUserTel().length())));
                                                        }else{
                                                                user.setPatientPwd(Md5Util.MD5(qvo.getUserIdNo().substring(qvo.getUserIdNo().length()-6,qvo.getUserIdNo().length())));
                                                        }
                                                        this.getSysDao().getServiceDo().add(user);
                                                        this.getAjson().setMsg("注册成功!");
                                                        patient = sysDao.getAppPatientUserDao().findByUserIdNoCardTelRegister(qvo.getUserIdNo(),qvo.getUserCrad(),qvo.getUserTel());
                                                        this.getAjson().setVo(patient);

                                                        //居民服务
                                                        List<AppLabelManage> lsjmfw = this.sysDao.getAppLabelManageDao().findByType("3");
                                                        //疾病类型
                                                        List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("2");
                                                        //居民标签
                                                        List<AppLabelManage> lss = this.sysDao.getAppLabelManageDao().findByType("1");
                                                        if(codeCity != null){
                                                                if(StringUtils.isNotBlank(codeCity.getCodeTitle())){
//获取居民服务类型
                                                                        AppDrPatientFwEntity vv = this.sysDao.getAppSignformDao().findFwType(patient.getId(),codeCity.getCodeTitle(),requestUserId,requestUserName,userType);
                                                                        map.put("fwlx",vv);
                                                                }else{
                                                                        //获取居民服务类型
                                                                        AppDrPatientFwEntity vv = this.sysDao.getAppSignformDao().findFwType(patient.getId(),patient.getCityCode(),requestUserId,requestUserName,userType);
                                                                        map.put("fwlx",vv);
                                                                }
                                                        }else{
                                                                //获取居民服务类型
                                                                AppDrPatientFwEntity vv = this.sysDao.getAppSignformDao().findFwType(patient.getId(),patient.getCityCode(),requestUserId,requestUserName,userType);
                                                                map.put("fwlx",vv);
                                                        }
                                                        //经济类型
//                                                List<AppMeddleEntity> jjType = this.getSysDao().getCodeDao().findMeddle(CodeGroupConstrats.GROUP_JJLX,CommonEnable.QIYONG.getValue());
                                                        List<AppMeddleEntity> jjType = this.getSysDao().getAppLabelManageDao().findByMeddle(LabelManageType.JJLX.getValue());
                                                        //状态
//                                               List<CdCode> state = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_VIP,CommonEnable.QIYONG.getValue());


                                                        map.put("jmfw",lsjmfw);
                                                        map.put("jblx",ls);
                                                        map.put("jmbq",lss);
                                                        map.put("jjlx",jjType);
                                                        Map<String,Object> mealMap = new HashMap<>();
                                                        mealMap.put("mealId",mealId);
                                                        mealMap.put("mealName",mealName);
                                                        map.put("fwb",mealMap);
//                                              map.put("state",state);
                                                        this.getAjson().setMap(map);
                                                }
                                        }
                                }
                        }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("系统错误,请联系管理员");
                }
                return "ajson";
        }

        /**
         * 医生代签约 医生代签审核直接通过
         * @param patientId 患者id
         * @param teamid 团队
         * @param signPersGroup 居民分组 99 多分号隔开
         * @param signHealthGroup 健康分布 199
         * @param labelGruops 疾病类型接口 多分号隔开
         * @param drId 医生id
         */
        public String drAgreeSignForm(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {

                                boolean flag = true;
                                if(StringUtils.isNotBlank(qvo.getSignUpHpis())){
                                          if(SignFormType.POSSTATE.getValue().equals(qvo.getSignUpHpis())){
                                                flag = false;
                                        }
                                }
                                if(flag){
                                        if(!this.getrandomError()){
                                                this.getAjson().setMsg("系统错误,请联系管理员...");
                                                this.getAjson().setMsgCode("900");
                                                return "ajson";
                                        }
                                }
                                if(StringUtils.isBlank(qvo.getTeamId())){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("团队参数不能为空!");
                                        return "ajson";
                                }
                                if(StringUtils.isBlank(qvo.getSignPersGroup())){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("请选择服务人群!");
                                        return "ajson";
                                }
                                if(StringUtils.isBlank(qvo.getSignsJjType())){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("请选择经济类型!");
                                        return "ajson";
                                }
                                if(StringUtils.isBlank(qvo.getSignHealthGroup())){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("请选择健康情况!");
                                        return "ajson";
                                }
//                                AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(qvo.getPatientId());
//                                String md5Result = Md5Util.MD5(qvo.getPatientId()+qvo.getTeamId()+qvo.getDrId()+ExtendDate.getYYYY(Calendar.getInstance()));
//                                AppSubmissionRepetition submissionRepetition = sysDao.getAppSubmissionRepetitionDao().getDataOne(md5Result);
//                                if(submissionRepetition != null){
//                                        if(StringUtils.isBlank(submissionRepetition.getSignId())){
//                                                this.getAjson().setMsgCode("900");
//                                                this.getAjson().setMsg("该数据上传保存中,请不要重复提交!");
//                                                return "ajson";
//                                        }
//                                }else{
//                                        submissionRepetition = new AppSubmissionRepetition();
//                                        submissionRepetition.setPatientId(qvo.getPatientId());
//                                        submissionRepetition.setSignMd5Result(md5Result);
//                                        sysDao.getServiceDo().add(submissionRepetition);
//                                }
                                AppSignForm form = sysDao.getAppSignformDao().findSignFormByState(qvo.getPatientId());
                                AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getPatientId());
                                if(form == null){
                                        String requestUserId = null;
                                        String requestUserName = null;
                                        boolean typeBoolean = false;
                                        //改服务人群为多选
                                        if (StringUtils.isNotBlank(qvo.getSignPersGroup())) {
                                                String[] groups = qvo.getSignPersGroup().split(";");
                                                if (groups != null && groups.length > 0) {
                                                        for(String s : groups){
                                                                if(!s.equals(ResidentMangeType.PTRQ.getValue()) && !s.equals(ResidentMangeType.WEIZHI.getValue())){
                                                                        typeBoolean = true;
                                                                }
                                                        }
                                                }
                                        }
                                        //经济类型
                                        if(StringUtils.isNotBlank(qvo.getSignsJjType())){
                                                String[] jjlx = qvo.getSignsJjType().split(";");
                                                if (jjlx != null && jjlx.length > 0) {
                                                        for(String s : jjlx){
                                                                if(!s.equals(EconomicType.YBRK.getValue())){
                                                                        typeBoolean = true;
                                                                }
                                                        }
                                                }
                                        }



                                        if(typeBoolean){
                                                AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
                                                if(drUser!=null){
                                                        AppHospDept dept =  (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                                        if(dept!=null){
                                                                CdAddressPeople cdAdd = sysDao.getCdAddressPeopleDao().findByCacheCode(dept.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                                                                if(cdAdd!=null){
                                                                        AppCommQvo app = new AppCommQvo();
                                                                        app.setSignType("1");//重点人群查询
                                                                        if("1".equals(cdAdd.getAreaDisSignWay())){//团队上限签约人数
                                                                                app.setTeamId(qvo.getTeamId());
                                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
                                                                                if (result >= Integer.parseInt(cdAdd.getAreaDisSignTop())) {
                                                                                        this.getAjson().setMsg("您的团队重点签约人数已达上限!");
                                                                                        this.getAjson().setMsgCode("900");
                                                                                        return "ajson";
                                                                                }
                                                                        }else if("0".equals(cdAdd.getAreaDisSignWay())){
//                                                                        app.setTeamId(qvo.getTeamId());
//                                                                        app.setDrId(qvo.getDrId());
//                                                                        Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
//                                                                        if (result >= Integer.parseInt(cdAdd.getAreaDisSignTop())) {
//                                                                                this.getAjson().setMsg("您的重点人群签约已上限!");
//                                                                                this.getAjson().setMsgCode("900");
//                                                                                return "ajson";
//                                                                        }
                                                                                app.setTeamId(qvo.getTeamId());
                                                                                int count = sysDao.getAppTeamMemberDao().findTeamPeopleCount(qvo.getTeamId());//查询整个团队人员数
                                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);//查询整个团队签约数
                                                                                if (result >= Integer.parseInt(cdAdd.getAreaDisSignTop())*count) {
                                                                                        this.getAjson().setMsg("您的团队重点签约人数已达上限!");
                                                                                        this.getAjson().setMsgCode("900");
                                                                                        return "ajson";
                                                                                }
                                                                        }


                                                                        if(user != null){
                                                                                if( StringUtils.isBlank(user.getPatientProvince()) && StringUtils.isBlank(qvo.getPatientProvince()) ){
                                                                                        this.getAjson().setMsgCode("900");
                                                                                        this.getAjson().setMsg("省不能为空!");
                                                                                        return "ajson";
                                                                                }
                                                                                if(StringUtils.isBlank(user.getPatientCity()) && StringUtils.isBlank(qvo.getPatientCity())){
                                                                                        this.getAjson().setMsgCode("900");
                                                                                        this.getAjson().setMsg("市不能为空!");
                                                                                        return "ajson";
                                                                                }
                                                                                if(StringUtils.isBlank(user.getPatientArea()) && StringUtils.isBlank(qvo.getPatientArea())){
                                                                                        this.getAjson().setMsgCode("900");
                                                                                        this.getAjson().setMsg("区不能为空!");
                                                                                        return "ajson";
                                                                                }
                                                                                if(StringUtils.isBlank(user.getPatientStreet()) && StringUtils.isBlank(qvo.getPatientStreet())){
                                                                                        this.getAjson().setMsgCode("900");
                                                                                        this.getAjson().setMsg("街道不能为空!");
                                                                                        return "ajson";
                                                                                }
                                                                                if(StringUtils.isBlank(user.getPatientNeighborhoodCommittee()) && StringUtils.isBlank(qvo.getPatientNeighborhoodCommittee())){
                                                                                        EnterpatientVo qvos = new EnterpatientVo();
                                                                                        qvos.setCurrentPage("1");
                                                                                        qvos.setIdcardno(user.getPatientIdno());
                                                                                        String code = AreaUtils.getAreaCode(dept.getHospAreaCode(),"2");
                                                                                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                                                                                        if(value != null) {
                                                                                                qvos.setUrlType(value.getCodeTitle());
                                                                                        }
                                                                                        List<EnterpatientEntity> lsEnter = this.getSysDao().getSecurityCardAsyncBean().getEnterpatientList(qvos,requestUserId,requestUserName,DrPatientType.DR.getValue());
                                                                                        String committee = null;
                                                                                        if(lsEnter != null && lsEnter.size()>0){
                                                                                                EnterpatientEntity entt = lsEnter.get(0);
                                                                                                if(StringUtils.isNotBlank(entt.getJname())){
                                                                                                        CdAddress address = sysDao.getCdAddressDao().findBySname(entt.getJname(),entt.getAdress_village());
                                                                                                        if(address != null){
                                                                                                                committee = address.getCtcode();
                                                                                                        }
                                                                                                }
                                                                                        }
                                                                                        if(StringUtils.isNotBlank(committee)){
                                                                                                if(AreaUtils.getAreaCodeAll(committee,"2").equals(user.getPatientCity()) || AreaUtils.getAreaCodeAll(committee,"2").equals(qvo.getPatientCity())){
                                                                                                        qvo.setPatientNeighborhoodCommittee(committee);
                                                                                                }
                                                                                        }else{
                                                                                                this.getAjson().setMsgCode("900");
                                                                                                this.getAjson().setMsg("居委会不能为空!");
                                                                                                return "ajson";
                                                                                        }
                                                                                }
                                                                                if(StringUtils.isBlank(user.getPatientAddress()) && StringUtils.isBlank(qvo.getPatientAddress())){
                                                                                        this.getAjson().setMsgCode("900");
                                                                                        this.getAjson().setMsg("地址不能为空!");
                                                                                        return "ajson";
                                                                                }
                                                                        }

//                                                                        if(StringUtils.isBlank(qvo.getPatientjmda())){
//                                                                                this.getAjson().setMsg("居民档案号不能为空!");
//                                                                                this.getAjson().setMsgCode("900");
//                                                                                return "ajson";
//                                                                        }
                                                                        AppSignForm sign=sysDao.getAppSignformDao().drAgreeSignForm(qvo.getPatientId(),qvo.getTeamId(),qvo.getSignPersGroup(),
                                                                                qvo.getSignHealthGroup(),qvo.getLabelGruops(),qvo.getDrId(),qvo.getPatientAddress(),qvo.getPatientProvince(),
                                                                                qvo.getPatientCity(),qvo.getPatientArea(),qvo.getPatientStreet(),qvo.getPatientNeighborhoodCommittee(),qvo.getSignsJjType(),
                                                                                qvo.getSignpackageid(),qvo.getSignlx(),qvo.getPatientjtda(),qvo.getPatientjmda(),qvo.getSignUpHpis(),qvo.getSignatureImageUrl(),
                                                                                qvo.getSignPhotoImageUrl(),qvo.getSignImageUrl(),qvo.getSignDrAssistantId(),qvo.getSignMobileType());
//                                                                        sysDao.getServiceDo().removePoFormSession(submissionRepetition);
//                                                                        submissionRepetition = (AppSubmissionRepetition) sysDao.getServiceDo().find(AppSubmissionRepetition.class,submissionRepetition);
//                                                                        submissionRepetition.setSignId(sign.getId());
//                                                                        sysDao.getServiceDo().modify(submissionRepetition);
                                                                        this.getAjson().setVo(sign);
                                                                        this.getAjson().setMsgCode("800");
                                                                }else{
                                                                        this.getAjson().setMsgCode("900");
                                                                        this.getAjson().setMsg("该机构未设置签约管理指标，请先设置");
                                                                }
                                                        }
                                                }else{
                                                        this.getAjson().setMsg("系统错误,请联系管理员!");
                                                        this.getAjson().setMsgCode("900");
                                                }
                                        }else{
                                                String code = "";
                                                AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
                                                if(drUser!=null) {
                                                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, drUser.getDrHospId());
                                                        if (dept != null) {
                                                                code = AreaUtils.getAreaCode(dept.getHospAreaCode(),"2");
                                                        }
                                                }

                                                if(user != null){
                                                        if( StringUtils.isBlank(user.getPatientProvince()) && StringUtils.isBlank(qvo.getPatientProvince()) ){
                                                                this.getAjson().setMsgCode("900");
                                                                this.getAjson().setMsg("省不能为空!");
                                                                return "ajson";
                                                        }
                                                        if(StringUtils.isBlank(user.getPatientCity()) && StringUtils.isBlank(qvo.getPatientCity())){
                                                                this.getAjson().setMsgCode("900");
                                                                this.getAjson().setMsg("市不能为空!");
                                                                return "ajson";
                                                        }
                                                        if(StringUtils.isBlank(user.getPatientArea()) && StringUtils.isBlank(qvo.getPatientArea())){
                                                                this.getAjson().setMsgCode("900");
                                                                this.getAjson().setMsg("区不能为空!");
                                                                return "ajson";
                                                        }
                                                        if(StringUtils.isBlank(user.getPatientStreet()) && StringUtils.isBlank(qvo.getPatientStreet())){
                                                                this.getAjson().setMsgCode("900");
                                                                this.getAjson().setMsg("街道不能为空!");
                                                                return "ajson";
                                                        }
                                                        if(StringUtils.isBlank(user.getPatientNeighborhoodCommittee()) && StringUtils.isBlank(qvo.getPatientNeighborhoodCommittee())){

                                                                EnterpatientVo qvos = new EnterpatientVo();
                                                                qvos.setCurrentPage("1");
                                                                qvos.setIdcardno(user.getPatientIdno());
                                                                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                                                                if(value != null) {
                                                                        qvos.setUrlType(value.getCodeTitle());
                                                                }
                                                                List<EnterpatientEntity> lsEnter = this.getSysDao().getSecurityCardAsyncBean().getEnterpatientList(qvos,requestUserId,requestUserName,DrPatientType.DR.getValue());
                                                                String committee = null;
                                                                if(lsEnter != null && lsEnter.size()>0){
                                                                        EnterpatientEntity entt = lsEnter.get(0);
                                                                        if(StringUtils.isNotBlank(entt.getJname())){
                                                                                CdAddress address = sysDao.getCdAddressDao().findBySname(entt.getJname(),entt.getAdress_village());
                                                                                if(address != null){
                                                                                        committee = address.getCtcode();
                                                                                }
                                                                        }
                                                                }
                                                                if(StringUtils.isNotBlank(committee)){
                                                                        if(AreaUtils.getAreaCodeAll(committee,"2").equals(user.getPatientCity()) || AreaUtils.getAreaCodeAll(committee,"2").equals(qvo.getPatientCity())){
                                                                                qvo.setPatientNeighborhoodCommittee(committee);
                                                                        }
                                                                }else{
                                                                        this.getAjson().setMsgCode("900");
                                                                        this.getAjson().setMsg("居委会不能为空!");
                                                                        return "ajson";
                                                                }
                                                        }
                                                        if(StringUtils.isBlank(user.getPatientAddress()) && StringUtils.isBlank(qvo.getPatientAddress())){
                                                                this.getAjson().setMsgCode("900");
                                                                this.getAjson().setMsg("地址不能为空!");
                                                                return "ajson";
                                                        }
                                                }
                                                AppSignForm sign=sysDao.getAppSignformDao().drAgreeSignForm(qvo.getPatientId(),qvo.getTeamId(),qvo.getSignPersGroup(),
                                                        qvo.getSignHealthGroup(),qvo.getLabelGruops(),qvo.getDrId(),qvo.getPatientAddress(),qvo.getPatientProvince(),
                                                        qvo.getPatientCity(),qvo.getPatientArea(),qvo.getPatientStreet(),qvo.getPatientNeighborhoodCommittee(),qvo.getSignsJjType(),
                                                        qvo.getSignpackageid(),qvo.getSignlx(),qvo.getPatientjtda(),qvo.getPatientjmda(),qvo.getSignUpHpis(),qvo.getSignatureImageUrl(),
                                                        qvo.getSignPhotoImageUrl(),qvo.getSignImageUrl(),qvo.getSignDrAssistantId(),qvo.getSignMobileType());
//                                                sysDao.getServiceDo().removePoFormSession(submissionRepetition);
//                                                submissionRepetition = (AppSubmissionRepetition) sysDao.getServiceDo().find(AppSubmissionRepetition.class,submissionRepetition);
//                                                submissionRepetition.setSignId(sign.getId());
//                                                sysDao.getServiceDo().modify(submissionRepetition);
                                                this.getAjson().setVo(sign);
                                                this.getAjson().setMsgCode("800");
                                        }
                                }else{
                                        if(SignFormType.DQY.getValue().equals(form.getSignState())){
                                                this.getAjson().setMsgCode("900");
                                                this.getAjson().setMsg("该居民已有代签约,请勿重复申请签约!");
                                        }else{
                                                this.getAjson().setMsgCode("900");
                                                this.getAjson().setMsg("该居民已签约,请勿重复签约!");
                                        }
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }



        /**
         * 修改居民分组
         */
        public String appModifyGroup(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                List<AppSignForm> sign=sysDao.getAppSignformDao().changeGroup(qvo);
                                //this.getAjson().setRows(sign);
                                this.getAjson().setMsg("修改成功");
                                this.getAjson().setMsgCode("800");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";

        }

        /**
         * 添加拒管居民
         * @param idno 患者身份证号
         * @param name 患者姓名
         * @param card 患者社保卡号
         * @param teamId
         * @param drId
         */
        public String appRefuseSign(){
                AppRefuseSignVo vo=(AppRefuseSignVo)getAppJson(AppRefuseSignVo.class);
                AppDrUser user = getAppDrUser();
                try {
                        if(vo == null||user==null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                AppRefuseSign refuseSign = new AppRefuseSign();
                                refuseSign.setRsPatientIdno(vo.getIdno());
                                refuseSign.setRsPatientName(vo.getName());
                                refuseSign.setRsPatientCard(vo.getCard());
                                refuseSign.setRsRefuseDrId(user.getId());
                                refuseSign.setRsSignTeamId(vo.getTeamId());
                                refuseSign.setRsRefuseTime(Calendar.getInstance());
                                refuseSign.setRsRefuseHospId(user.getDrHospId());
                                AppHospDept hosp = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,user.getDrHospId());
                                refuseSign.setRsRefuseHospAreaCode(hosp.getHospAreaCode());
                                sysDao.getServiceDo().add(refuseSign);
                                this.getAjson().setMsg("保存成功");
                                this.getAjson().setMsgCode("800");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";

        }

        /**
         * 修改拒管居民
         * @param idno 患者身份证号
         * @param name 患者姓名
         * @param card 患者社保卡号
         * @param teamId
         * @param drId
         * @param id
         */
        public String appModifyRefuseSign(){
                AppRefuseSignVo vo=(AppRefuseSignVo)getAppJson(AppRefuseSignVo.class);
                try {
                        if(vo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                AppRefuseSign refuseSign = (AppRefuseSign) sysDao.getServiceDo().find(AppRefuseSign.class,vo.getId());
                                if(StringUtils.isNotBlank(vo.getIdno())) {
                                    refuseSign.setRsPatientIdno(vo.getIdno());
                                }
                                if(StringUtils.isNotBlank(vo.getName())) {
                                    refuseSign.setRsPatientName(vo.getName());
                                }
                                if(StringUtils.isNotBlank(vo.getCard())) {
                                    refuseSign.setRsPatientCard(vo.getCard());
                                }
                                if(StringUtils.isNotBlank(vo.getTeamId())) {
                                    refuseSign.setRsSignTeamId(vo.getTeamId());
                                }
                                sysDao.getServiceDo().modify(refuseSign);
                                AppRefuseEntity returnVo = new AppRefuseEntity();
                                returnVo.setId(refuseSign.getId());
                                returnVo.setDrId(refuseSign.getRsRefuseDrId());
                                returnVo.setTeamId(refuseSign.getRsRefuseTeamId());
                                returnVo.setName(refuseSign.getRsPatientName());
                                returnVo.setIdno(refuseSign.getRsPatientIdno());
                                returnVo.setCard(refuseSign.getRsPatientCard());
                                returnVo.setTime(ExtendDate.getYMD_h_m_s(refuseSign.getRsRefuseTime()));
                                this.getAjson().setVo(returnVo);
                                this.getAjson().setMsg("更改成功");
                                this.getAjson().setMsgCode("800");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";

        }

    /**
     * 删除拒管居民
     * @param idno 患者身份证号
     * @param name 患者姓名
     * @param card 患者社保卡号
     * @param teamId
     * @param drId
     * @param id
     */
    public String appDeleteRefuseSign(){
        AppRefuseSignVo vo=(AppRefuseSignVo)getAppJson(AppRefuseSignVo.class);
        try {
            if(vo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                sysDao.getServiceDo().delete(AppRefuseSign.class,vo.getId());
                this.getAjson().setMsg("删除成功");
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";

    }

        /**
         * 查询拒管居民列表
         * @return
         */
        public String appFindRefuseList(){
                AppDrUser user = getAppDrUser();
                AppRefuseSignVo qvo=(AppRefuseSignVo)getAppJson(AppRefuseSignVo.class);
                try {
                        if(user == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                HashMap<String,Object> map = new HashMap<String,Object>();
                                map.put("rsRefuseDrId",user.getId());
                                String sql = "SELECT ID id,RS_PATIENT_NAME name,RS_PATIENT_IDNO idno,RS_PATIENT_CARD card,date_format(RS_REFUSE_TIME,'%Y-%c-%d %H:%i:%s') time,RS_REFUSE_DR_ID drId,RS_REFUSE_TEAM_ID teamId " +
                                        "FROM APP_REFUSE_SIGN " +
                                        "WHERE 1=1 ";
                                       sql += " AND RS_REFUSE_DR_ID = :rsRefuseDrId ";
                                       sql +=" ORDER BY RS_REFUSE_TIME DESC";
                                if(qvo==null){
                                        qvo = new AppRefuseSignVo();
                                }
                                qvo.setDrId(user.getId());
                                List<AppRefuseEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppRefuseEntity.class,qvo);
                                this.getAjson().setQvo(qvo);
                                this.getAjson().setRows(list);
                                this.getAjson().setMsgCode("800");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";

        }

        /**
         * 发送健康指导给患者
         * @return
         */
        public String saveMeddle(){
                try{
                        AppHealthMeddleQvo qvo = (AppHealthMeddleQvo)getAppJson(AppHealthMeddleQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else{
                                AppDrUser drUser = this.getAppDrUser();
                                if(drUser!=null){
                                        if(drUser!=null){
                                                qvo.setDrId(drUser.getId());
                                        }
                                        this.sysDao.getAppHealthMeddleDao().saveToPatient(qvo);
                                        this.getAjson().setMsgCode("800");
                                        this.getAjson().setMsg("发送指导成功");
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

        /**
         * 查询健康教育分类
         * @return
         */
        public String healthEduType(){
                try{
                        List<AppMeddleEntity> ls = this.getSysDao().getCodeDao().findMeddlel(CodeGroupConstrats.GROUP_RESIDENTMANGE, CommonEnable.QIYONG.getValue());
                        this.getAjson().setRows(ls);
                        this.getAjson().setMsgCode("800");
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

        /**
         *  修改居家养老颜色
         *  patientId 患者id
         *  signServiceBColor 居家养老颜色
         *
         */
        public String modifySignServiceBColor(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                List<AppSignForm> ls=sysDao.getServiceDo().loadByPk(AppSignForm.class,"signPatientId",qvo.getPatientId());
                                if(ls!=null && !ls.isEmpty()){
                                        ls.get(0).setSignServiceBColor(qvo.getSignServiceBColor());
                                        sysDao.getServiceDo().modify(ls.get(0));
                                }
                                this.getAjson().setMsgCode("800");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

        /**
         * 市权限签约管理设置
         * @return
         */
        public String saveSignSet(){
                try{
                        AppSignSetting qvo = (AppSignSetting)getAppJson(AppSignSetting.class);
                        if(qvo==null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else{
                                AppDrUser drUser = this.getAppDrUser();
                                if(drUser!=null){
                                        qvo.setSignsCreateId(drUser.getId());
                                        AppSignSetting vo = sysDao.getAppSignSettingDao().saveSet(qvo);
                                        this.getAjson().setVo(vo);
                                        this.getAjson().setMsg("修改成功");
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

        /**
         * 签约处理初始信息
         * @return
         */
        public String signManage(){
                try{
                        AppDrSignSetQvo qvo = (AppDrSignSetQvo)getAppJson(AppDrSignSetQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else{
                                String requestUserId = null;
                                String requestUserName = null;
                                String userType = null;
                                AppPatientUser user = this.getAppPatientUser();
                                if(user != null){
                                        userType = "1";
                                        requestUserId = user.getId();
                                        requestUserName = user.getPatientName();
                                }else{
                                        AppDrUser drUser = this.getAppDrUser();
                                        if(drUser != null ){
                                                userType = "2";
                                                requestUserId = drUser.getId();
                                                requestUserName = drUser.getDrName();
                                        }
                                }
                                String jtdnh = null;
                                String jmdnh = null;
                                String pro = "";
                                String city = "";
                                String area = "";
                                String street = "";
                                String cun = "";
                                AppDrPatientSignEntity vo = sysDao.getAppSignformDao().findPatient(qvo.getUserId(),qvo.getSignState());
                                Map<String,Object> map  = new HashMap<String,Object>();
                                //疾病类型
                                List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("2");
                                //居民标签
                                List<AppLabelManage> lss = this.sysDao.getAppLabelManageDao().findByType("1");
                                //服务标签
                                List<AppLabelManage> lsss = this.sysDao.getAppLabelManageDao().findByType("3");
                                //获取居民服务类型
                                AppDrPatientFwEntity vv = this.sysDao.getAppSignformDao().findFwType(qvo.getUserId(),qvo.getCityCode(),requestUserId,requestUserName,userType);
                                if(vo != null){
                                        String addresss = "";
                                        //获取居民家庭档案号和居民档案号
                                        HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                                        qqvo.setIdno(vo.getSignIdNo());
                                        qqvo.setType("2");
                                        qqvo.setUrlType(vo.getCityCode());
                                        String str = sysDao.getSecurityCardAsyncBean().getHealthCareRecords(qqvo,requestUserId,requestUserName,userType);
                                        if(StringUtils.isNotBlank(str)){
                                                JSONObject jsonall = JSONObject.fromObject(str);
                                                if(jsonall.get("vo")!=null && "800".equals(jsonall.get("msgCode"))) {
                                                        if (jsonall.get("entity") != null && jsonall.get("entity") != "" ) {
                                                                if(!"null".equals(jsonall.get("entity").toString())){
                                                                        JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                                                        if(AddressType.ND.getValue().equals(qqvo.getUrlType()) || AddressType.PINGT.getValue().equals(qqvo.getUrlType())) {//宁德居民档案
                                                                                if(entity != null && "800".equals(entity.get("msgCode").toString())){
//                                                                                        System.out.println("宁德居民档案1:"+str);
                                                                                        List<AppEnterpatientEntity> list = JsonUtil.fromJson(entity.get("rows").toString(),new TypeToken<List<AppEnterpatientEntity>>() {}.getType());
                                                                                        if(list != null && list.size()>0){
                                                                                                jtdnh = "";
                                                                                                jmdnh = list.get(0).getDf_id();
                                                                                                //如果居民健康档案返回的地址信息不为空
                                                                                                //村不为空
                                                                                                if(StringUtils.isNotBlank(list.get(0).getAdress_village())){
                                                                                                        pro = list.get(0).getAdress_pro();
                                                                                                        city = list.get(0).getAdress_city();
                                                                                                        area = list.get(0).getAdress_county();
                                                                                                        street = list.get(0).getAdress_rural();
                                                                                                        cun = list.get(0).getAdress_village();
                                                                                                        CdAddress address = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_village());
                                                                                                        if(address != null){
                                                                                                                addresss = address.getAreaSname()+list.get(0).getAdrss_hnumber();
                                                                                                        }
                                                                                                }else if(StringUtils.isNotBlank(list.get(0).getAdress_rural())){
                                                                                                        pro = list.get(0).getAdress_pro();
                                                                                                        city = list.get(0).getAdress_city();
                                                                                                        area = list.get(0).getAdress_county();
                                                                                                        addresss = list.get(0).getAdrss_hnumber();
                                                                                                }else if(StringUtils.isNotBlank(list.get(0).getAdress_county())){
                                                                                                        pro = list.get(0).getAdress_pro();
                                                                                                        city = list.get(0).getAdress_city();
                                                                                                        addresss = list.get(0).getAdrss_hnumber();
                                                                                                }else if(StringUtils.isNotBlank(list.get(0).getAdress_city())){
                                                                                                        pro = list.get(0).getAdress_pro();
                                                                                                        addresss = list.get(0).getAdrss_hnumber();
                                                                                                }else if(StringUtils.isNotBlank(list.get(0).getAdress_pro())){
                                                                                                        pro = list.get(0).getAdress_pro();
                                                                                                        addresss = list.get(0).getAdrss_hnumber();
                                                                                                }
                                                                                        }
                                                                                }

                                                                        }else{
                                                                                if(entity != null && "true".equals(entity.get("success").toString())){
                                                                                        T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
                                                                                        if (entitys != null) {
                                                                                                jtdnh = entitys.getJtdah();
                                                                                                jmdnh = entitys.getJmdah();
                                                                                                addresss = entitys.getCun()+entitys.getMphm();
                                                                                        }
                                                                                        //如果居民健康档案返回的地址信息不为空
                                                                                        //村不为空
                                                                                        if(StringUtils.isNotBlank(entitys.getXzqydm())){//获取健康档案返回的最低区域编码
                                                                                                CdAddressConfiguration cp = sysDao.getCdAddressDao().findByCodeJw(entitys.getXzqydm());
                                                                                                if(cp!=null){
                                                                                                        CdAddress cdAddress = sysDao.getCdAddressDao().findByCode(cp.getId());
                                                                                                        if(cdAddress != null){
                                                                                                                if("5".equals(cdAddress.getLevel())){//居委会
                                                                                                                        pro = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                        city = AreaUtils.getAreaCode(cdAddress.getCtcode(),"2")+"00000000";
                                                                                                                        area = AreaUtils.getAreaCode(cdAddress.getCtcode(),"3")+"000000";
                                                                                                                        street = AreaUtils.getAreaCode(cdAddress.getCtcode(),"4")+"000";
                                                                                                                        cun = cdAddress.getCtcode();
                                                                                                                }else if("4".equals(cdAddress.getLevel())){//街道
                                                                                                                        pro = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                        city = AreaUtils.getAreaCode(cdAddress.getCtcode(),"2")+"00000000";
                                                                                                                        area = AreaUtils.getAreaCode(cdAddress.getCtcode(),"3")+"000000";
                                                                                                                        street = cdAddress.getCtcode();
                                                                                                                }else if("3".equals(cdAddress.getLevel())){//区
                                                                                                                        pro = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                        city = AreaUtils.getAreaCode(cdAddress.getCtcode(),"2")+"00000000";
                                                                                                                        area = cdAddress.getCtcode();
                                                                                                                }else if("2".equals(cdAddress.getLevel())){//市
                                                                                                                        pro = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                        city = cdAddress.getCtcode();
                                                                                                                }else if("1".equals(cdAddress.getLevel())){
                                                                                                                        pro = cdAddress.getCtcode();
                                                                                                                }
                                                                                                        }
                                                                                                }else{
                                                                                                        CdAddress cdAddress = sysDao.getCdAddressDao().findByCode(entitys.getXzqydm());
                                                                                                        if(cdAddress != null){
                                                                                                                if("5".equals(cdAddress.getLevel())){//居委会
                                                                                                                        pro = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                        city = AreaUtils.getAreaCode(cdAddress.getCtcode(),"2")+"00000000";
                                                                                                                        area = AreaUtils.getAreaCode(cdAddress.getCtcode(),"3")+"000000";
                                                                                                                        street = AreaUtils.getAreaCode(cdAddress.getCtcode(),"4")+"000";
                                                                                                                        cun = cdAddress.getCtcode();
                                                                                                                }else if("4".equals(cdAddress.getLevel())){//街道
                                                                                                                        pro = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                        city = AreaUtils.getAreaCode(cdAddress.getCtcode(),"2")+"00000000";
                                                                                                                        area = AreaUtils.getAreaCode(cdAddress.getCtcode(),"3")+"000000";
                                                                                                                        street = cdAddress.getCtcode();
                                                                                                                }else if("3".equals(cdAddress.getLevel())){//区
                                                                                                                        pro = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                        city = AreaUtils.getAreaCode(cdAddress.getCtcode(),"2")+"00000000";
                                                                                                                        area = cdAddress.getCtcode();
                                                                                                                }else if("2".equals(cdAddress.getLevel())){//市
                                                                                                                        pro = AreaUtils.getAreaCode(cdAddress.getCtcode(),"1")+"0000000000";
                                                                                                                        city = cdAddress.getCtcode();
                                                                                                                }else if("1".equals(cdAddress.getLevel())){
                                                                                                                        pro = cdAddress.getCtcode();
                                                                                                                }
                                                                                                        }
                                                                                                }
                                                                                        }
//                                                                                if(StringUtils.isBlank(vo.getSignAddr())){//居民地址为空
//                                                                                      String jdAddr = entitys.getSheng()+entitys.getShi()+entitys.getXian()+entitys.getXiang()+entitys.getCun()+entitys.getMphm();
//                                                                                      if(StringUtils.isNotBlank(jdAddr)){//健康档案中有地址修改居民地址
//                                                                                              vo.setSignAddr(jdAddr);
//                                                                                              AppPatientUser patient = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,vo.getId());
//                                                                                              if(StringUtils.isBlank(patient.getPatientAddress()) && StringUtils.isNotBlank(entitys.getMphm())){
//                                                                                                      patient.setPatientAddress(entitys.getMphm());
//                                                                                              }
//                                                                                              if(StringUtils.isNotBlank(entitys.getSheng())){//省
//                                                                                                        CdAddress pro = sysDao.getCdAddressDao().findBySname(entitys.getSheng(),null);
//                                                                                                        if(pro != null){
//                                                                                                                patient.setPatientProvince(pro.getCtcode());
//                                                                                                                if(StringUtils.isNotBlank(entitys.getShi())){//市
//                                                                                                                        CdAddress city = sysDao.getCdAddressDao().findByName(entitys.getShi(),pro.getCtcode());
//                                                                                                                        if(city != null){
//                                                                                                                                patient.setPatientCity(city.getCtcode());
//                                                                                                                                if(StringUtils.isNotBlank(entitys.getXian())){//区
//                                                                                                                                        CdAddress area = sysDao.getCdAddressDao().findByName(entitys.getXian(),city.getCtcode());
//                                                                                                                                        if(area != null){
//                                                                                                                                                patient.setPatientArea(area.getCtcode());
//                                                                                                                                                if(StringUtils.isNotBlank(entitys.getXiang())){//街道
//                                                                                                                                                        CdAddress street = sysDao.getCdAddressDao().findByName(entitys.getXiang(),area.getCtcode());
//                                                                                                                                                        if(street != null){
//                                                                                                                                                                patient.setPatientStreet(street.getCtcode());
//                                                                                                                                                                if(StringUtils.isNotBlank(entitys.getCun())){//居委会
//                                                                                                                                                                        CdAddress cun = sysDao.getCdAddressDao().findByName(entitys.getCun(),street.getCtcode());
//                                                                                                                                                                        if(cun != null){
//                                                                                                                                                                                patient.setPatientNeighborhoodCommittee(cun.getCtcode());
//                                                                                                                                                                        }
//                                                                                                                                                                }
//                                                                                                                                                        }
//                                                                                                                                                }
//                                                                                                                                        }
//                                                                                                                                }
//                                                                                                                        }
//                                                                                                                }
//                                                                                                        }
//                                                                                              }
//                                                                                              sysDao.getServiceDo().modify(patient);
//                                                                                      }
//                                                                                }
                                                                                }
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                        if(StringUtils.isBlank(vo.getSignAddr())){//如果居民地址信息为空，默认代入签约医生所在医院的地址
                                             AppDrUser drUser = this.getAppDrUser();
                                             if(drUser != null){
                                                     if(StringUtils.isNotBlank(drUser.getDrHospId())){
                                                             AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                                             if(dept != null){
                                                                     if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                                                                             AppPatientUser patient = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,vo.getId());
                                                                             if(StringUtils.isBlank(pro)){
                                                                                     pro = AreaUtils.getAreaCodeAll(dept.getHospAreaCode(),"1");
                                                                                     city = AreaUtils.getAreaCodeAll(dept.getHospAreaCode(),"2");
                                                                                     area = AreaUtils.getAreaCodeAll(dept.getHospAreaCode(),"3");
                                                                                     street = dept.getHospAreaCode();
                                                                             }
                                                                             patient.setPatientProvince(pro);
                                                                             patient.setPatientCity(city);
                                                                             patient.setPatientArea(area);
                                                                             patient.setPatientStreet(street);
                                                                             patient.setPatientNeighborhoodCommittee(cun);
                                                                             if(StringUtils.isNotBlank(addresss)){
                                                                                     patient.setPatientAddress(addresss);
                                                                             }
                                                                             sysDao.getServiceDo().modify(patient);
                                                                             String address = "";
                                                                             String addr = "";
                                                                             if(StringUtils.isNotBlank(patient.getPatientStreet())){
                                                                                     address = patient.getPatientStreet();
                                                                             }else if(StringUtils.isNotBlank(patient.getPatientArea())){
                                                                                     address = patient.getPatientArea();
                                                                             }else if(StringUtils.isNotBlank(patient.getPatientCity())){
                                                                                     address = patient.getPatientCity();
                                                                             }else if(StringUtils.isNotBlank(patient.getPatientProvince())){
                                                                                     address = patient.getPatientProvince();
                                                                             }
                                                                             if(StringUtils.isNotBlank(address)){
                                                                                     CdAddress addres = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,address);
                                                                                     if(addres!=null&&StringUtils.isNotBlank(patient.getPatientAddress())){
                                                                                             addr = addres.getAreaName()+patient.getPatientAddress();
                                                                                     }else{
                                                                                             addr = addres.getAreaName();
                                                                                     }
                                                                             }else{
                                                                                     if(StringUtils.isNotBlank(patient.getPatientAddress())){
                                                                                             addr = patient.getPatientAddress();
                                                                                     }
                                                                             }
                                                                             vo.setSignAddr(addr);
//                                                                             CdAddress address = sysDao.getCdAddressDao().findByCacheCode(dept.getHospAreaCode());
//                                                                             if(address != null){
//                                                                                     AppPatientUser patient = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,vo.getId());
//                                                                                     vo.setSignAddr(address.getAreaName());
//                                                                                     if("1".equals(address.getLevel())){//省
//                                                                                             patient.setPatientProvince(address.getCtcode());
//                                                                                     }else if("2".equals(address.getLevel())){//市
//                                                                                             patient.setPatientProvince(AreaUtils.getAreaCode(address.getCtcode(),"1")+"0000000000");
//                                                                                             patient.setPatientCity(address.getCtcode());
//                                                                                     }else if("3".equals(address.getLevel())){//区
//                                                                                             patient.setPatientProvince(AreaUtils.getAreaCode(address.getCtcode(),"1")+"0000000000");
//                                                                                             patient.setPatientCity(AreaUtils.getAreaCode(address.getCtcode(),"2")+"00000000");
//                                                                                             patient.setPatientArea(address.getCtcode());
//                                                                                     }else if("4".equals(address.getLevel())){//街道
//                                                                                             patient.setPatientProvince(AreaUtils.getAreaCode(address.getCtcode(),"1")+"0000000000");
//                                                                                             patient.setPatientCity(AreaUtils.getAreaCode(address.getCtcode(),"2")+"00000000");
//                                                                                             patient.setPatientArea(AreaUtils.getAreaCode(address.getCtcode(),"3")+"000000");
//                                                                                             patient.setPatientStreet(address.getCtcode());
//                                                                                     }else {
//                                                                                             patient.setPatientProvince(AreaUtils.getAreaCode(address.getCtcode(),"1")+"0000000000");
//                                                                                             patient.setPatientCity(AreaUtils.getAreaCode(address.getCtcode(),"2")+"00000000");
//                                                                                             patient.setPatientArea(AreaUtils.getAreaCode(address.getCtcode(),"3")+"000000");
//                                                                                             patient.setPatientStreet(AreaUtils.getAreaCode(address.getCtcode(),"4")+"000");
//                                                                                             patient.setPatientNeighborhoodCommittee(address.getCtcode());
//                                                                                     }
//                                                                                     sysDao.getServiceDo().modify(patient);
//                                                                             }
                                                                     }
                                                             }
                                                     }
                                             }
                                        }
                                        map.put("jtdnh",jtdnh);
                                        map.put("jmdnh",jmdnh);
                                }
                                map.put("jblx",ls);
                                map.put("jmbq",lss);
                                map.put("fwrq",lsss);
                                map.put("fwlx",vv);
                                this.getAjson().setMap(map);
                                this.getAjson().setVo(vo);
                                this.getAjson().setMsgCode("800");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsg(e.getMessage());
                        this.getAjson().setMsgCode("900");
                }
                return "ajson";
        }

        /**
         * 删除居民签约单
         * @return
         */
        public String signDelPatient(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                if(StringUtils.isBlank(qvo.getPatientId())){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("患者住键不能空!");
                                        return "ajson";
                                }
                                if(StringUtils.isBlank(qvo.getSignDelType())){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("删除类型不能为空!");
                                        return "ajson";
                                }else{
                                        if(qvo.getSignDelType().equals(SignFormDelType.SW.getValue())){
                                                if(StringUtils.isBlank(qvo.getSignDieDate())){
                                                        this.getAjson().setMsgCode("900");
                                                        this.getAjson().setMsg("死亡时间不能为空!");
                                                        return "ajson";
                                                }
                                        }
                                }
                                if(StringUtils.isBlank(qvo.getSignDelReason())){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("删除原因不能为空!");
                                        return "ajson";
                                }
                                AppDrUser user = this.getAppDrUser();
                                AppSignForm signForm = this.getSysDao().getAppSignformDao().findSignFormByUserState(qvo.getPatientId());
                                sysDao.getAppSignformDao().signDelPatient(qvo.getPatientId(),qvo.getSignDelType(),qvo.getSignDelReason(),qvo.getSignDieDate());
                                String dcontent = "医生:"+user.getDrName()+ "：删除您的签约单,删除原因:"+qvo.getSignDelReason()+"!";
                                sysDao.getAppNoticeDao().addNotices("删除消息", dcontent, NoticesType.QYXX.getValue(), user.getId(), qvo.getPatientId(), signForm.getId(), DrPatientType.PATIENT.getValue());
                                this.getAjson().setMsg("删除成功!");
                                this.getAjson().setMsgCode("800");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";

        }

        /**
         * 新签约设置页面初始数据接口
         * @return
         */
        public String findSerMeal(){
                try{
                        AppSignSerQvo qvo = new AppSignSerQvo();
                        Map<String,Object> map = new HashMap<String,Object>();
                        //工作类型
                        List<AppWorkType> workList = sysDao.getAppWorkTypeDao().findAllList();
                        map.put("workList",workList);
                        AppDrUser drUser = this.getAppDrUser();
                        if(drUser!=null){//查询套餐:市的查市和系统开放
                                //医院的查自己医院的+市+系统开放的
                                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                String areaCode= AreaUtils.getAreaCode(dept.getHospAreaCode());
                                if(areaCode.length()!=4){//不是市医院
                                        if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                                                qvo.setAreaCode(dept.getHospAreaCode().substring(0,4)+"00000000");
                                        }
                                }
                                qvo.setHospId(dept.getId());
                                List<AppSignSerMealEntity> ls = sysDao.getAppServeSetmealDao().findSerAllMeal(qvo);
                                map.put("meal",ls);
                                //查询当前医院已选择的套餐
                                AppSignSettingEntity asse = sysDao.getAppSignSettingDao().findByMeal(qvo.getHospId());
                                map.put("nowMeal",asse);
                                this.getAjson().setMap(map);

                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

        /**
         * 保存签约管理设置
         * @return
         */
        public String saveSignSerSet(){
                try{
                        AppSignSetting qvo = (AppSignSetting)getAppJson(AppSignSetting.class);
                        if(qvo==null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else{
                                AppDrUser drUser = this.getAppDrUser();
                                if(drUser!=null){
                                        qvo.setSignsCreateId(drUser.getId());
                                        qvo.setSignsDeptId(drUser.getDrHospId());
                                        if(StringUtils.isNotBlank(qvo.getId())){
                                                AppSignSetting vo = sysDao.getAppSignSettingDao().modifySign(qvo);
                                                this.getAjson().setVo(vo);
                                                this.getAjson().setMsgCode("800");
                                                this.getAjson().setMsg("修改成功");
                                        }else{
                                                AppSignSetting vo = sysDao.getAppSignSettingDao().saveSign(qvo);
                                                this.getAjson().setVo(vo);
                                                this.getAjson().setMsgCode("800");
                                                this.getAjson().setMsg("保存成功");
                                        }
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

        /**
         * 医生发送续签
         * @return
         */
        public String renewalReminder(){
                try{
                        AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else{
                               AppDrUser drUser = this.getAppDrUser();
                               if(drUser!=null){
                                       List<AppSignForm> signForms = sysDao.getAppSignformDao().renewalReminder(qvo.getSignFormId());
                                       if(signForms!=null&&signForms.size()>0){
                                               for(AppSignForm signForm:signForms){
                                                       sysDao.getAppNoticeDao().addNotices("续签提醒",  "您好，您的家庭医生签约服务将于"+ExtendDate.getChineseYMD(signForm.getSignToDate())+"到期，建议续签。", NoticesType.QYXX.getValue()+","+NoticesMType.XQXX.getValue(), drUser.getId(),
                                                               signForm.getSignPatientId(), signForm.getId(), DrPatientType.PATIENT.getValue());
                                                       signForm.setSignRenewState(CommSF.YES.getValue());
                                                       sysDao.getServiceDo().modify(signForm);
                                               }
                                               this.getAjson().setMsg("发送成功");
                                               this.getAjson().setMsgCode("800");
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

        /**
         * 查询续签列表
         * @return
         */
        public String findRenewalList(){
                try{
                        AppCommQvo qvo = new AppCommQvo();
                        AppDrUser drUser = this.getAppDrUser();
                        if(drUser!=null){
                                qvo.setDrId(drUser.getId());
                                List<AppSignFormEntity> ls = sysDao.getAppSignformDao().findRenewalList(qvo);
                                this.getAjson().setRows(ls);
                                this.getAjson().setMsgCode("800");
                        }
//                        }

                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

        /**
         * 查询医生是否签约上限
         * @return
         */
        public String findDrCount(){
                try{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser!=null){
                            ToplimitEntity vo = sysDao.getAppSignformDao().findDrCount(drUser);
                            this.getAjson().setMsgCode("800");
                            this.getAjson().setVo(vo);
                    }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }


        /**
         * 医生履约统计
         * @return
         */
        public String appSignPerformance(){
                try{
						AppCommQvo qvo = (AppCommQvo) getAppJson(AppCommQvo.class);
						if (qvo != null) {
							if (StringUtils.isNotBlank(qvo.getDrId())) {
								Map<String, Object> mapPerformance = this.getSysDao().getAppPerformanceStatisticsDao().findNewAppSingPerformanceDr(qvo);
								this.getAjson().setMap(mapPerformance);
							} else {
								this.getAjson().setMsgCode("900");
								this.getAjson().setMsg("医生主键不能为空!");
							}
						} else {
							this.getAjson().setMsgCode("900");
							this.getAjson().setMsg("参数格式错误");
						}
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("系统错误,请联系管理员");
                }
                return "ajson";
        }

        /**
         * 查询履约人员列表
         * @return
         */
    public String findLyPeopleList(){
        try {
                AppLyQvo qvo = (AppLyQvo)this.getAppJson(AppLyQvo.class);
                if(qvo == null){
                        this.getAjson().setMsg("参数格式错误");
                        this.getAjson().setMsgCode("900");
                }else {
                        AppDrUser drUser = this.getAppDrUser();
                        if (drUser != null) {
                                qvo.setDrId(drUser.getId());
                                List<AppLyPeopleEntity> list = sysDao.getAppPatientUserDao().findLyPeopleList(qvo);
                                this.getAjson().setMsgCode("800");
                                this.getAjson().setRows(list);
                        }
                }
        }catch (Exception e){
                e.printStackTrace();
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询套餐
     * @return
     */
    public String findSeverMeal(){
            try{
                    AppCommQvo qvo = (AppCommQvo)this.getAppJson(AppCommQvo.class);
                    AppPatientUser user = this.getAppPatientUser();
                    if(user != null){//个人端
                            if(qvo == null){
                                    this.getAjson().setMsgCode("900");
                                    this.getAjson().setMsg("参数格式错误");
                            }else{
                                    if(StringUtils.isBlank(qvo.getDrId())){
                                            this.getAjson().setMsg("该居民签约医生主键不能为空");
                                            this.getAjson().setMsgCode("900");
                                    }else{
                                            AppDrUser drUser = this.getSysDao().getAppDrUserDao().findByUserId(qvo.getDrId());
                                            if(drUser!=null){
                                                    if(StringUtils.isNotBlank(drUser.getDrHospId())){
                                                            List<AppServeEntity> list = sysDao.getAppServeSetmealDao().findSeverMeal(drUser.getDrHospId());
                                                            this.getAjson().setRows(list);
                                                            this.getAjson().setMsgCode("800");
                                                    }else{
                                                            this.getAjson().setMsg("查无该医生机构信息");
                                                            this.getAjson().setMsgCode("900");
                                                    }
                                            }else{
                                                    this.getAjson().setMsgCode("900");
                                                    this.getAjson().setMsg("查无医生信息");
                                            }
                                    }
                            }
                    }else{//医生端
                            AppDrUser drUser = this.getAppDrUser();
                            if(drUser != null){
                                    if(StringUtils.isNotBlank(drUser.getDrHospId())){
                                            List<AppServeEntity> list = sysDao.getAppServeSetmealDao().findSeverMeal(drUser.getDrHospId());
                                            this.getAjson().setRows(list);
                                            this.getAjson().setMsgCode("800");
                                    }else{
                                            this.getAjson().setMsg("查无该医生机构信息");
                                            this.getAjson().setMsgCode("900");
                                    }
                            }else{
                                    this.getAjson().setMsgCode("900");
                                    this.getAjson().setMsg("查无医生信息");
                            }
                    }

//                    if(qvo != null && StringUtils.isNotBlank(qvo.getDrId())){
//                         AppDrUser drUser = this.getSysDao().getAppDrUserDao().findByUserId(qvo.getDrId());
//                            if(drUser!=null){
//                                    List<AppServeEntity> list = sysDao.getAppServeSetmealDao().findSeverMeal(drUser.getDrHospId());
//                                    this.getAjson().setRows(list);
//                                    this.getAjson().setMsgCode("800");
//                            }else{
//                                    this.getAjson().setMsg("查无医生信息");
//                                    this.getAjson().setMsgCode("900");
//                            }
//                    }else{
//                            AppDrUser drUser = this.getAppDrUser();
//                            if(drUser!=null){
//                                    List<AppServeEntity> list = sysDao.getAppServeSetmealDao().findSeverMeal(drUser.getDrHospId());
//                                    this.getAjson().setRows(list);
//                                    this.getAjson().setMsgCode("800");
//                            }else{
//                                    this.getAjson().setMsg("查无医生信息数据");
//                                    this.getAjson().setMsgCode("900");
//                            }
//                    }

            }catch (Exception e){
                    e.printStackTrace();
                    this.getAjson().setMsg(e.getMessage());
                    this.getAjson().setMsgCode("900");
            }
            return "ajson";
    }

        /**
         * 医生审核健康档案
         * @return
         */
    public String fileAudit(){
            try{
                    AppFileAuditQvo qvo = (AppFileAuditQvo)this.getAppJson(AppFileAuditQvo.class);
                    if(qvo==null){
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("参数格式错误");
                    }else{
                            String str = sysDao.getAppHealthFileDao().fileAudit(qvo);
                            if("true".equals(str)){
                                    this.getAjson().setMsg("审核通过");
                                    this.getAjson().setMsgCode("800");
                            }else{
                                    this.getAjson().setMsgCode("900");
                                    this.getAjson().setMsg(str);
                            }
                    }
            }catch (Exception e){
                    e.printStackTrace();
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg(e.getMessage());
            }
            return "ajson";
    }

	/**
	 * 医生新履约统计
	 * 
	 * @return
	 */
	public String appNewSignPerformance() {
		try {
			AppCommQvo qvo = (AppCommQvo) getAppJson(AppCommQvo.class);
			if (qvo != null) {
				if (StringUtils.isNotBlank(qvo.getDrId())) {
					// 旧履约统计
					Map<String, Object> mapPerformance = this.getSysDao().getAppPerformanceStatisticsDao().findAppSingPerformanceDr(qvo);
					// 新履约统计
					// Map<String, Object> mapPerformance = this.getSysDao().getAppPerformanceStatisticsDao().findNewAppSingPerformanceDr(qvo);
					this.getAjson().setMap(mapPerformance);
				} else {
					this.getAjson().setMsgCode("900");
					this.getAjson().setMsg("医生主键不能为空!");
				}
			} else {
				this.getAjson().setMsgCode("900");
				this.getAjson().setMsg("参数格式错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getAjson().setMsgCode("900");
			this.getAjson().setMsg("系统错误,请联系管理员");
		}
		return "ajson";
	}

    /**
    * 死亡类型解约接口
    *  @return
    */
    public String surrenderSign(){
        try{
            WebSurrenderSignVo qvo = (WebSurrenderSignVo)this.getAppJson(WebSurrenderSignVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppSignForm form = sysDao.getAppSignformDao().surrenderSign(qvo);
                if(form != null){
                    this.getAjson().setMsg("解约成功!");
                    this.getAjson().setMsgCode("800");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("查无相关数据");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
    * 根据居民档案号查询此人是否死亡
    * @return
    */
    public String findDieState(){
        try{
            AppCommQvo qvo = (AppCommQvo)getAppJson(AppCommQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String state = sysDao.getAppSignformDao().findDieState(qvo.getPatientjmda());
                Map<String,Object> map = new HashMap<>();
                map.put("dieState",state);
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("900");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    public String findGrantInAidByMeal(){
        try{
            AppCommQvo qvo = (AppCommQvo)this.getAppJson(AppCommQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isBlank(qvo.getSignpackageid())){
                    this.getAjson().setMsg("套餐主键不能为空");
                    this.getAjson().setMsgCode("900");
                }else if(StringUtils.isBlank(qvo.getSignPersGroup())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("服务人群不能为空");
                }else if(StringUtils.isBlank(qvo.getSignsJjType())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("经济类型不能为空");
                }else{
                    List<AppGrantInAidEntity> list = sysDao.getAppServeSetmealDao().findGrantInAidByMeal(qvo);
                    this.getAjson().setRows(list);
                    this.getAjson().setMsgCode("800");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }
        //补签名
    public String saveAutograph(){
        try{
            AppCommQvo qvo = (AppCommQvo)this.getAppJson(AppCommQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppSignSubtable> list = sysDao.getAppSignSubtableDao().findBySign(qvo.getSignFormId(),SignFormType.POSSTATE.getValue(),CommSF.YES.getValue());
                if(list!=null && list.size()>0){
//                    this.getAjson().setMsg("已签名,不可更改");
//                    this.getAjson().setMsgCode("900");
                        for(AppSignSubtable table : list){
                                table.setIsAutograph(CommSF.NOT.getValue());
                                sysDao.getServiceDo().modify(table);
                        }
                }
//                else{
                    AppSignForm form = (AppSignForm)sysDao.getServiceDo().find(AppSignForm.class,qvo.getSignFormId());
                    sysDao.getAppSignformDao().saveImage(qvo.getSignImageUrl(),qvo.getSignFormId(),SignFormType.POSSTATE.getValue(),CommSF.YES.getValue(),form.getSignAreaCode());
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


        /**
         * 随机判断错误
         * @return
         */
    private boolean getrandomError() throws Exception {
            int i = (int)(Math.random()*8888)+4000;
            int q = (int)(Math.random()*7777)+5000;
            String first = String.valueOf(i).substring(0,2);
            String last = String.valueOf(i).substring(2,4);
            int result = Integer.parseInt(last) % Integer.parseInt(first) / 7;
            if(result>0){
               Thread.sleep(i+q);
                return  true;
            }else{
                return  false;
            }
    }

        /**
         * 查询是否是建档立卡居民
         * @return
         */
    public String findIsArchiving(){
            try {
                    AppArchivingCardPeopleVo vo = (AppArchivingCardPeopleVo)getAppJson(AppArchivingCardPeopleVo.class);
                    if(vo != null){
                            String jdSourceType = "1";
                            AppDrUser drUser = this.getAppDrUser();
                            if(drUser != null){
                                    if(StringUtils.isNotBlank(drUser.getDrHospId())){
                                            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                            if(dept != null){
                                                    if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                                                            CdCode cdCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(dept.getHospAreaCode(),"2"));
                                                            if(cdCode != null){
                                                                    if("0".equals(cdCode.getCodeTitle())){
                                                                            jdSourceType = "3";
                                                                    }
                                                            }
                                                    }
                                            }
                                    }
                            }
                            AppArchivingCardPeople archivingCardPeople = sysDao.getAppArchivingCardPeopeDao().findPeopleByIdno(vo.getPtidno(),jdSourceType);
                            Map<String,Object> map = new HashMap<String,Object>();
                            if(archivingCardPeople != null){
                                    map.put("result","1");
                            }else {
                                    map.put("result","0");
                            }
                            this.getAjson().setMsgCode("800");
                            this.getAjson().setMap(map);
                    }else {
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("参数格式错误！");
                    }
            }catch (Exception e){
                    e.printStackTrace();
            }
            return "ajson";
    }

        /**
         * 根据选择的经济类型返回补贴情况（传入经济类型多个，服务包主键 暂未使用）
         * @return
         */
    public String findByJjType(){
            try{
                AppCommQvo qvo = (AppCommQvo)this.getAppJson(AppCommQvo.class);
                if(qvo == null){
                        this.getAjson().setMsg("参数格式错误");
                        this.getAjson().setMsgCode("900");
                }else{
                        if(StringUtils.isBlank(qvo.getSignsJjType())){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("经济类型参数不能为空");
                                return "ajson";
                        }
                        if(StringUtils.isBlank(qvo.getSignpackageid())){
                                this.getAjson().setMsg("服务包选择不能为空");
                                this.getAjson().setMsgCode("900");
                                return "ajson";
                        }
                        List<AppSubsidyEntity> list = sysDao.getAppServeEconDao().findByJjType(qvo.getSignsJjType(),qvo.getSignpackageid());
                        this.getAjson().setRows(list);
                        this.getAjson().setMsgCode("800");

                }

            }catch (Exception e){
                    e.printStackTrace();
            }
            return "ajson";
    }
}
