package com.ylz.view.sign.action;

import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.cd.entity.AddressHospEntity;
import com.ylz.bizDo.cd.entity.CdAddressEntity;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.vo.CdAddressQvo;
import com.ylz.bizDo.jtapp.commonEntity.*;
import com.ylz.bizDo.jtapp.commonVo.AppAddressQvo;
import com.ylz.bizDo.jtapp.drEntity.JmInfo;
import com.ylz.bizDo.jtapp.patientEntity.AppHealthListEntity;
import com.ylz.bizDo.mangecount.entity.ManageCountEntity;
import com.ylz.bizDo.mangecount.vo.PerformanceVo;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理端统计查询
 */
@SuppressWarnings("all")
@Action(
        value="manageCounAction",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class ManageCountAction extends CommonAction {

    /**
     * 首页统计
     * @return
     */
    public String appManageIndexCount(){
        try{
            Map<String,Object> returnMap = new HashMap<String,Object>();
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                //签约首页统计
                Map<String,Object> map = sysDao.getAppSignAnalysisDao().findSignAnalysisIndex(qvo);
                returnMap.put("signTotal",map);
                //统计数（随访量、健康指导、健康教育、求助量、未缴费人数）
                Map<String,Object> mapCount= this.sysDao.getAppStatisticalAnalysisDao().findCount(qvo);
                returnMap.put("mapCount",mapCount);
                //排名
                Map<String,Object> map2 = sysDao.getAppRankAnalysisDao().findRanking(qvo);
                returnMap.put("rank",map2);
                this.getAjson().setMap(returnMap);
                this.getAjson().setMsgCode("800");
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
     * 首页统计
     * @return
     */
    public String appManageIndexCountNew(){
        try{
            Map<String,Object> returnMap = new HashMap<String,Object>();
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                //签约首页统计
                Map<String,Object> mapIndex = sysDao.getAppSignAnalysisDao().findSignAnalysisIndex(qvo);
                returnMap.put("signTotal",mapIndex);
                //签约首页统计
                Map<String,Object> map = sysDao.getAppSignAnalysisDao().findSignAnalysisList(qvo);
                returnMap.put("signTotalState",map);
                //重点人群统计
                String mapPers = this.getSysDao().getAppResidentAnalysisDao().findPersGroupCountFocusGroups(qvo);//服务分布
                List<ManageCountEntity> ls = JsonUtil.fromJson(mapPers,new TypeToken<List<ManageCountEntity>>() {}.getType());
                returnMap.put("mapPers",ls);
                //经济类型
                String economic = this.getSysDao().getAppSignAnalysisDao().findSignEconomicTypeList(qvo);//经济类型统计
                List<ManageCountEntity> lsEconomic = JsonUtil.fromJson(economic,new TypeToken<List<ManageCountEntity>>() {}.getType());
                returnMap.put("mapEconomic",lsEconomic);
                //统计数（随访量、健康指导、健康教育、求助量、未缴费人数）
                Map<String,Object> mapCount= this.sysDao.getAppStatisticalAnalysisDao().findCount(qvo);
                returnMap.put("mapCount",mapCount);
                //排名
                Map<String,Object> map2 = sysDao.getAppRankAnalysisDao().findRanking(qvo);
                returnMap.put("rank",map2);
                this.getAjson().setMap(returnMap);
                this.getAjson().setMsgCode("800");
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
     * 签约统计
     * @return
     */
    public String appSignCount(){
        try{
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                Map<String,Object> map = sysDao.getAppSignAnalysisDao().findSignAnalysisList(qvo);
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
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
     *  扇形统计
     * @return
     */
    public String appSectorStatisticsCount(){
        try{
            Map<String,Object> returnMap = new HashMap<String,Object>();
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                //重点人群统计
                String mapPers = this.getSysDao().getAppResidentAnalysisDao().findPersGroupCountFocusGroups(qvo);//服务分布
                List<ManageCountEntity> ls = JsonUtil.fromJson(mapPers,new TypeToken<List<ManageCountEntity>>() {}.getType());
                returnMap.put("mapPers",ls);
                //经济类型
                String economic = this.getSysDao().getAppSignAnalysisDao().findSignEconomicTypeList(qvo);//经济类型统计
                List<ManageCountEntity> lsEconomic = JsonUtil.fromJson(economic,new TypeToken<List<ManageCountEntity>>() {}.getType());
                returnMap.put("mapEconomic",lsEconomic);
                this.getAjson().setMap(returnMap);
                this.getAjson().setMsgCode("800");
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
     * 居民分析
     * @return
     */
    public String appResidentAnalysis(){
        try{
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                if(StringUtils.isNotBlank(qvo.getYearStart()) && StringUtils.isNotBlank(qvo.getYearEnd())){
                    Map<String,Object> map = new HashMap<String,Object>();
                    Map<String,Object> mapGender = this.getSysDao().getAppResidentAnalysisDao().findCountGender(qvo);//性别
                    Map<String,Object> mapAge = this.getSysDao().getAppResidentAnalysisDao().findCountAge(qvo);//年龄分布
                    Map<String,Object> mapPay = this.getSysDao().getAppResidentAnalysisDao().findPayStateCount(qvo);//是否支付
                    Map<String,Object> mapHealth = this.getSysDao().getAppResidentAnalysisDao().findHealthGroupCount(qvo);//健康分布
                    Map<String,Object> mapPers = this.getSysDao().getAppResidentAnalysisDao().findPersGroupCount(qvo);//服务分布
                    Map<String,Object> mapEconomic = this.getSysDao().getAppSignAnalysisDao().findSignEconomicTypeCount(qvo);//经济类型统计
                    map.put("mapGender",mapGender);
                    map.put("mapAge",mapAge);
                    map.put("mapPay",mapPay);
                    map.put("mapHealth",mapHealth);
                    map.put("mapPers",mapPers);
                    map.put("mapEconomic",mapEconomic);
                    this.getAjson().setMap(map);
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
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
     * 综合排名
     * @param areaId 区域编码
     * @param hospId 医院id
     * @param yearStart 开始时间（yyyy-MM)
     * @param yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public String appRanking(){
        try{
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                Map<String,Object> map = sysDao.getAppRankAnalysisDao().findRanking(qvo);
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
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
     * 遵从医嘱情况
     * @param areaId 区域编码
     * @param hospId 医院id
     * @param teamId 团队id
     * @param yearStart 开始时间（yyyy-MM)
     * @param yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public String appFollowDoctor(){
        try{
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                Map<String,Object> map = sysDao.getAppStatisticalAnalysisDao().findFollowDoctor(qvo);
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
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
     * 拒转签情况
     * @param areaId 区域编码
     * @param hospId 医院id
     * @param teamId 团队id
     * @param yearStart 开始时间（yyyy-MM)
     * @param yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public String appRefuseToSign(){
        try{
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                Map<String,Object> map = sysDao.getAppStatisticalAnalysisDao().findRefuseToSign(qvo);
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
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
     * 随访工作量
     * @return
     */
    public String appSfWorkPlan(){
        try{
            ResidentVo qvo = (ResidentVo)getAppJson(ResidentVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                Map<String, Object> map = this.sysDao.getAppStatisticalAnalysisDao().findSfWorkPlan(qvo);
                this.getAjson().setMsgCode("800");
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
     * 健康干预统计
     * @return
     */
    public String appGuideSum(){
        try{
            ResidentVo qvo = (ResidentVo)getAppJson(ResidentVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                Map<String,Object> map = this.sysDao.getAppStatisticalAnalysisDao().findGuideWork(qvo);
                this.getAjson().setMap(map);
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
     * 查询下级行政区划
     */
    public String findSubAreas() {
        try {
            CdAddressQvo qvo = (CdAddressQvo) this.getVoJson(CdAddressQvo.class);
            if (qvo != null) {
                if (StringUtils.isNotBlank(qvo.getAreaCode())) {
                    CdAddress entity = this.getSysDao().getCdAddressDao().findByCacheCode(qvo.getAreaCode());
                    if (entity != null) {
                        List<CdAddress> ls = this.getSysDao().getCdAddressDao().findSubAreas(entity.getCtcode());
                        this.getAjson().setRows(ls);
                    }
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数不合法");
                }
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数不合法");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误，请联系管理员！");
        }
        return "ajson";
    }

    /**
     * 查询省市区街道医院团队
     *
     * @return
     */
    public String appAddressResult() {
        CdAddress entity = new CdAddress();
        try {
            AppAddressQvo qvo = (AppAddressQvo) this.getVoJson(AppAddressQvo.class);
              if (qvo != null) {
                if(StringUtils.isNotBlank(qvo.getUpId())){
                    if("2".equals(qvo.getSource())){
                        entity.setId(qvo.getUpId());
                        entity.setLevel(qvo.getLevel());
                    }else{
                        entity = this.getSysDao().getCdAddressDao().findByCacheCode(qvo.getUpId());
                    }
                    if(entity != null){
                        if(entity.getLevel().equals("1")){
                            AddressHospEntity vo = new AddressHospEntity();
                            vo.setId(entity.getId());
                            vo.setLevel("1");
                            vo.setName("全省");
                            vo.setState("0");
                            List<AddressHospEntity> ls = this.getSysDao().getCdAddressDao().findAssociateJw2(qvo.getUpId(),qvo.getSource());
                            ls.add(0,vo);
                            this.getAjson().setRows(ls);
                        }else if(entity.getLevel().equals("2")){
                            AddressHospEntity vo = new AddressHospEntity();
                            vo.setId(entity.getId());
                            vo.setLevel("1");
                            vo.setName("全市");
                            vo.setState("0");
                            List<AddressHospEntity> ls = this.getSysDao().getCdAddressDao().findAssociateJw2(qvo.getUpId(),qvo.getSource());
                            ls.add(0,vo);
                            this.getAjson().setVo(entity);
                            this.getAjson().setRows(ls);
                        }else if (entity.getLevel().equals("3")){
                            AddressHospEntity vo = new AddressHospEntity();
                            vo.setId(entity.getId());
                            vo.setLevel("1");
                            vo.setName("全区");
                            vo.setState("0");
                            List<AddressHospEntity> ls = this.getSysDao().getCdAddressDao().findAssociateJw2(qvo.getUpId(),qvo.getSource());
                            ls.add(0,vo);
                            this.getAjson().setRows(ls);
                        }else if (entity.getLevel().equals("4")){
                            AddressHospEntity vo = new AddressHospEntity();
                            List<AddressHospEntity> ls = this.getSysDao().getCdAddressDao().findAssociateJw2(qvo.getUpId(),qvo.getSource());
                            this.getAjson().setRows(ls);
                        }
                    }else{
                        AppHospDept dept = (AppHospDept)this.getSysDao().getServiceDo().find(AppHospDept.class,qvo.getUpId());
                        if(dept != null){
                            AddressHospEntity vo = new AddressHospEntity();
                            vo.setId(dept.getId());
                            vo.setName("全社区");
                            vo.setLevel("2");
                            vo.setState("0");
                            List<AddressHospEntity> ls =  this.getSysDao().getAppTeamDao().findTeamManageTs(dept.getId());
                            ls.add(0,vo);
                            this.getAjson().setRows(ls);
                        }
                    }
                }else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
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
     * 查询省市区街道
     *
     * @return
     */
    public String appAddressTeamResult() {
        try {
            AppAddressQvo qvo = (AppAddressQvo) this.getAppJson(AppAddressQvo.class);
            if (qvo != null) {
                if(StringUtils.isNotBlank(qvo.getUpId())){
                    CdAddress entity = this.getSysDao().getCdAddressDao().findByCacheCode(qvo.getUpId());
                    if(entity != null){
                        if(entity.getLevel().equals("1")){
                            List<CdAddressEntity> ls = this.getSysDao().getCdAddressDao().findByUpIdOrNot(qvo.getUpId());
                            this.getAjson().setRows(ls);
                        }else if(entity.getLevel().equals("2")){
                            List<CdAddressEntity> ls = this.getSysDao().getCdAddressDao().findByUpIdOrNot(qvo.getUpId());
                            this.getAjson().setRows(ls);
                        }else if (entity.getLevel().equals("3")){
                            List<AppHospEntity> ls = this.getSysDao().getAppHospDeptDao().findByAreaIdNotZero(qvo.getUpId());
                            this.getAjson().setRows(ls);
                        }
                    }
                }else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
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
     * 工作计划
     * @param areaId 区域编码
     * @param hospId 医院id
     * @param teamId 团队id
     * @param yearStart 开始时间（yyyy-MM)
     * @param yearEnd 结束时间(yyyy-MM)
     * @return
     */
    public String appWorkPlan(){
        try{
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                Map<String,Object> map = sysDao.getAppWorkPlanDao().findWorkPlan(qvo);
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
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
     * 随访量
     * @return
     */
    public String appIndexFollowPlan(){
        try{
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                 Map<String,Object> map = sysDao.getAppStatisticalAnalysisDao().findIndexFollowPlan(qvo);
                String result = map.get("ls").toString();
                 if(StringUtils.isNotBlank(qvo.getAreaId())){
                     List<AppManagePublicEntity> ls = JsonUtil.fromJson(result,new TypeToken<List<AppManagePublicEntity>>() {}.getType());
                     this.getAjson().setRows(ls);
                 }
                 if(StringUtils.isNotBlank(qvo.getHospId())){
                     this.getAjson().setQvo(qvo);
                     this.getAjson().setVo(JsonUtil.fromJson(map.get("count").toString(),AppManagePublicEntity.class));
                     List<AppFollowPlanManageEntity> ls = JsonUtil.fromJson(result,new TypeToken<List<AppFollowPlanManageEntity>>() {}.getType());
                     this.getAjson().setRows(ls);
                 }
                this.getAjson().setMsgCode("800");
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
     * 健康指导
     * @return
     */
    public String appIndexGuide(){
        try{
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                Map<String,Object> map = sysDao.getAppStatisticalAnalysisDao().findIndexGuide(qvo);
                String result = map.get("ls").toString();
                if(StringUtils.isNotBlank(qvo.getAreaId())){
                    List<AppManagePublicEntity> ls = JsonUtil.fromJson(result,new TypeToken<List<AppManagePublicEntity>>() {}.getType());
                    this.getAjson().setRows(ls);
                }
                if(StringUtils.isNotBlank(qvo.getHospId())){
                    this.getAjson().setQvo(qvo);
                    this.getAjson().setVo(JsonUtil.fromJson(map.get("count").toString(),AppManagePublicEntity.class));
                    List<AppHealthGuideEntity> ls = JsonUtil.fromJson(result,new TypeToken<List<AppHealthGuideEntity>>() {}.getType());
                    this.getAjson().setRows(ls);
                }
                this.getAjson().setMsgCode("800");
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
     * 健康教育
     * @return
     */
    public String appIndexHealth(){
        try{
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                Map<String,Object> map = sysDao.getAppStatisticalAnalysisDao().findIndexHealth(qvo);
                String result = map.get("ls").toString();
                if(StringUtils.isNotBlank(qvo.getAreaId())){
                    List<AppManagePublicEntity> ls = JsonUtil.fromJson(result,new TypeToken<List<AppManagePublicEntity>>() {}.getType());
                    this.getAjson().setRows(ls);
                }
                if(StringUtils.isNotBlank(qvo.getHospId())){
                    this.getAjson().setQvo(qvo);
                    this.getAjson().setVo(JsonUtil.fromJson(map.get("count").toString(),AppManagePublicEntity.class));
                    List<AppHealthListEntity> ls = JsonUtil.fromJson(result,new TypeToken<List<AppHealthListEntity>>() {}.getType());
                    this.getAjson().setRows(ls);
                }
                this.getAjson().setMsgCode("800");
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
     * 求助量
     * @return
     */
    public String appIndexHelp(){
        try{
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                Map<String,Object> map = sysDao.getAppStatisticalAnalysisDao().findIndexHelp(qvo);
                String result = map.get("ls").toString();
                if(StringUtils.isNotBlank(qvo.getAreaId())){
                    List<AppManagePublicEntity> ls = JsonUtil.fromJson(result,new TypeToken<List<AppManagePublicEntity>>() {}.getType());
                    this.getAjson().setRows(ls);
                }
                if(StringUtils.isNotBlank(qvo.getHospId())){
                    this.getAjson().setQvo(qvo);
                    this.getAjson().setVo(JsonUtil.fromJson(map.get("count").toString(),AppManagePublicEntity.class));
                    List<AppSeekHelpEntity> ls = JsonUtil.fromJson(result,new TypeToken<List<AppSeekHelpEntity>>() {}.getType());
                    this.getAjson().setRows(ls);
                }
                this.getAjson().setMsgCode("800");
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
     * 未缴费人数
     * @return
     */
    public String appIndexUnpaid(){
        try{
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                Map<String,Object> map = sysDao.getAppStatisticalAnalysisDao().findIndexUnpaid(qvo);
                String result = map.get("ls").toString();
                if(StringUtils.isNotBlank(qvo.getAreaId())){
                    List<AppManagePublicEntity> ls = JsonUtil.fromJson(result,new TypeToken<List<AppManagePublicEntity>>() {}.getType());
                    this.getAjson().setRows(ls);
                }
                if(StringUtils.isNotBlank(qvo.getHospId())){
                    this.getAjson().setQvo(qvo);
                    this.getAjson().setVo(JsonUtil.fromJson(map.get("count").toString(),AppManagePublicEntity.class));
                    List<JmInfo> ls = JsonUtil.fromJson(result,new TypeToken<List<JmInfo>>() {}.getType());
                    this.getAjson().setRows(ls);
                }
                this.getAjson().setMsgCode("800");
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
     * 评价
     * @return
     */
    public String  assess(){
        try{
            ResidentVo qvo = (ResidentVo)this.getAppJson(ResidentVo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
//                AssessEntity vo = sysDao.getAppDrEvaluationDao().findAssess(qvo);
                Map<String, Object> vo = sysDao.getAppSignAnalysisDao().findAssess(qvo);
                this.getAjson().setMap(vo);
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
     * 履约统计
     * @return
     */
    public String findPerformance(){
        try{
            PerformanceVo qvo = (PerformanceVo) getAppJson(PerformanceVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                /*AppPatientUser patientUser = this.getAppPatientUser();
                if(patientUser!=null){
                    qvo.setPatientId(patientUser.getId());
                }
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                }*/
                AppSignForm signForm = sysDao.getAppSignformDao().findByPatientDr(qvo.getPatientId(),qvo.getDrId());
                if(signForm !=null){
                    qvo.setSignFormDate(ExtendDate.getYMD_h_m_s(signForm.getSignFromDate()));
                    qvo.setSignToDate(ExtendDate.getYMD_h_m(signForm.getSignToDate()));
                }
                Map<String,Object> map = new HashMap<String,Object>();
                //咨询统计
                Map<String,Object> consultMap = sysDao.getAppStatisticalAnalysisDao().findConsult(qvo);
                map.put("consultMap",consultMap);
                //随访统计（完成量/计划量）
                Map<String,Object> visitMap = sysDao.getAppStatisticalAnalysisDao().findVisit(qvo);
                map.put("visitMap",visitMap);
                //代预约
                map.put("dyyMap",null);
                //健康指导统计
                Map<String,Object> guideMap = sysDao.getAppStatisticalAnalysisDao().findGuide(qvo);
                map.put("guideMap",guideMap);
                //健康教育统计
                Map<String,Object> healthMap = sysDao.getAppStatisticalAnalysisDao().findHealth(qvo);
                map.put("healthMap",healthMap);
                this.getAjson().setMap(map);
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
     * 初始化医院信息
     * WangCheng
     * @return
     */
    public String initHospital() {
        CdAddress cdAddress = new CdAddress();
        try {
            AppAddressQvo qvo = (AppAddressQvo) this.getVoJson(AppAddressQvo.class);
            if (qvo != null) {
                if(StringUtils.isNotBlank(qvo.getUpId())){
                    cdAddress = this.sysDao.getCdAddressDao().findByCacheCode(qvo.getUpId());
                    if(cdAddress != null){
                        if(cdAddress.getLevel().equals("3")){
                            AddressHospEntity vo = new AddressHospEntity();
                            vo.setId(cdAddress.getId());
                            vo.setLevel("1");
                            vo.setName("全社区");
                            vo.setState("0");
                            List<AddressHospEntity> ls = this.getSysDao().getAppHospDeptDao().listAllCommunity(qvo.getUpId(),qvo.getSource());
                            if(ls != null){
                                ls.add(0,vo);
                            }
                            Map<String,Object> resultMap = new HashMap<String,Object>();
                            resultMap.put("hospNameId",cdAddress.getId());
                            resultMap.put("hospName",cdAddress.getAreaSname());
                            CdAddress upCdAddress = new CdAddress();
                            //获取市级名称
                            cdAddress = this.sysDao.getCdAddressDao().findByCacheCode(cdAddress.getPid());
                            resultMap.put("upHospNameId",cdAddress.getId());
                            resultMap.put("upHospName",cdAddress.getAreaSname());
                            this.getAjson().setMap(resultMap);
                            this.getAjson().setRows(ls);
                        }
                    }
                }else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
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
     * 初始化市、区县、社区
     * WangCheng
     * @return
     */
    public String initAllLevel(){
        try {
            AppAddressQvo qvo = (AppAddressQvo) this.getVoJson(AppAddressQvo.class);
            if(qvo != null){
                if(StringUtils.isNotEmpty(qvo.getAreaCode())){
                    Map<String,Object> resultMap = new HashMap<String,Object>();
                    //社区
                    AppHospDept appHospDept = this.sysDao.getAppHospDeptDao().findByAreaCodeByOne(qvo.getAreaCode());
                    if(appHospDept != null){
                        resultMap.put("communityNameId",appHospDept.getHospCityareaId());
                        resultMap.put("communityName",appHospDept.getHospName());
                        //区县级
                        CdAddress areaAddress = this.sysDao.getCdAddressDao().findByCacheCode(appHospDept.getHospUpcityareaId());
                        if(areaAddress != null){
                            resultMap.put("areaNameId",areaAddress.getId());
                            resultMap.put("areaName",areaAddress.getAreaSname());
                            //市级
                            CdAddress cityAddress = this.sysDao.getCdAddressDao().findByCacheCode(areaAddress.getPid());
                            if(cityAddress != null){
                                resultMap.put("cityNameId",cityAddress.getId());
                                resultMap.put("cityName",cityAddress.getAreaSname());
                            }
                        }
                    }
                    this.getAjson().setMap(resultMap);
                }else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
                }
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ajson";
    }
}
