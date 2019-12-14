package com.ylz.view.ysapp.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppPressureEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientEntity;
import com.ylz.bizDo.jtapp.patientVo.AppPatientQvo;
import com.ylz.bizDo.plan.Entity.*;
import com.ylz.bizDo.plan.po.*;
import com.ylz.bizDo.plan.vo.AppFollowPlanQvo;
import com.ylz.bizDo.plan.vo.AppPlanQvo;
import com.ylz.bizDo.plan.vo.AppPlanTypeQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Action(
        value="ysPlan",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"}),
                @Result(name = "sfjson", type = "json",params={"root","ajson", "excludeProperties","planDate","contentType", "application/json"})
        }
)
public class YsPlanAction extends CommonAction{

    //根据id查询随访信息
    public String findById() {
        try {
            AppPlanQvo qvo = (AppPlanQvo)getAppJson(AppPlanQvo.class);
            Map<String, Object> map = new HashMap<String, Object>();
            if (qvo != null) {
                AppFollowPlan plan = this.getSysDao().getAppFollowPlanDao().findById(qvo.getId());
                if (plan != null) {
//                    map.put("public", plan.getSfFollowData());
                    this.getAjson().setMap(map);
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }


    /**
     * 随访计划查询
     *
     * @return
     */
    public String findFollowPlan() {
        try {
            AppFollowPlanQvo qvo = (AppFollowPlanQvo) getAppJson(AppFollowPlanQvo.class);
            if(qvo != null){
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setPlanDoctorId(drUser.getId());
                    List<AppFollowPlanTjEntjty> lsCount = this.getSysDao().getAppFollowPlanDao().findTjPlan(qvo);
                    List<AppFollowPlan> lsPlan = this.getSysDao().getAppFollowPlanDao().findPlan(qvo);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("lsCount", lsCount);
                    this.getAjson().setMap(map);
                    this.getAjson().setRows(lsPlan);
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 新增随访初始化
     *
     * @return
     */
    public String findFollowPlanInit() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            //随访类型
            List<CdCode> lsSfType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFTYPE, CommonEnable.QIYONG.getValue());
            //随访表
            List<CdCode> lsSfTable = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFTABLE, CommonEnable.QIYONG.getValue());
            //居民类型
            List<CdCode> lsJmType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_RESIDENTMANGE, CommonEnable.QIYONG.getValue());
            map.put("lsSfType", lsSfType);
            map.put("lsSfTable", lsSfTable);
            map.put("lsJmType", lsJmType);
            this.getAjson().setMap(map);
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 数据初始化
     * @return
     *
     */
    public String findFollowPlanDataInit(){
        try {
            AppPlanTypeQvo qvo = (AppPlanTypeQvo)this.getAppJson(AppPlanTypeQvo.class);
            Map<String, Object> map = new HashMap<String, Object>();
            if(qvo != null){
                AppFollowPlan plan = this.getSysDao().getAppFollowPlanDao().findByType(qvo.getType(),qvo.getPatientId());
                if(plan != null){
//                    if(StringUtils.isNotBlank(plan.getSfFollowData()))
//                        map.put("public",plan.getSfFollowData());
                    List<AppPressureEntity> ls = sysDao.getAppUserBloodpressureDao().findLook(plan.getSfFollowPatientid(),"1");
                    if(ls != null && ls.size()>0){
                        Map<String,Object> mapXy = new HashMap<String,Object>();
                        int sys = 0;//高压
                        int dia = 0;//低压
                        int pul = 0;//心率
                        for(AppPressureEntity p : ls){
                            sys = sys+p.getSys();
                            dia = sys+p.getDia();
                            pul = sys+p.getPul();
                        }
                        if(sys != 0){
                            int sysResult = sys/ls.size();
                            mapXy.put("sys",sysResult);
                        }
                        if(dia != 0){
                            int diaResult = dia/ls.size();
                            mapXy.put("dia",diaResult);
                        }
                        if(pul != 0){
                            int pulResult = pul/ls.size();
                            mapXy.put("pul",pulResult);
                        }
                        map.put("xy",mapXy);
                    }
                    this.getAjson().setMap(map);
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }

//                                if (type.equals("0")) {//高血压
//                                        //高血压症状
//                                        List<CdCode> lsGxyZzType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_GXYZZ, CommonEnable.QIYONG.getValue());
//                                        //良好一般差
//                                        List<CdCode> lsLhybc = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_LHYBC, CommonEnable.QIYONG.getValue());
//                                        //服药依从性
//                                        List<CdCode> lsFyycx = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FYYCX, CommonEnable.QIYONG.getValue());
//                                        //是否有无
//                                        List<CdCode> lsSfyw = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYW, CommonEnable.QIYONG.getValue());
//                                        //此次随访分类
//                                        List<CdCode> lsCcsffl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_CCFFFL, CommonEnable.QIYONG.getValue());
//                                        map.put("lsGxyZzType",lsGxyZzType);
//                                        map.put("lsLhybc",lsLhybc);
//                                        map.put("lsFyycx",lsFyycx);
//                                        map.put("lsSfyw",lsSfyw);
//                                        map.put("lsCcsffl",lsCcsffl);
//                                } else if (type.equals("1")) {//血糖
//                                        //血糖症状
//                                        List<CdCode> lsXtZzType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_XTZZ, CommonEnable.QIYONG.getValue());
//                                        //足被动脉搏动
//                                        List<CdCode> lsZbdbmb = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ZBDMBD, CommonEnable.QIYONG.getValue());
//                                        //良好一般差
//                                        List<CdCode> lsLhybc = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_LHYBC, CommonEnable.QIYONG.getValue());
//                                        //服药依从性
//                                        List<CdCode> lsFyycx = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FYYCX, CommonEnable.QIYONG.getValue());
//                                        //是否有无
//                                        List<CdCode> lsSfyw = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYW, CommonEnable.QIYONG.getValue());
//                                        //低血糖反应
//                                        List<CdCode> lsDxtfy = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_DXTYY, CommonEnable.QIYONG.getValue());
//                                        //此次随访分类
//                                        List<CdCode> lsCcsffl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_CCFFFL, CommonEnable.QIYONG.getValue());
//                                        map.put("lsXtZzType",lsXtZzType);
//                                        map.put("lsZbdbmb",lsZbdbmb);
//                                        map.put("lsFyycx",lsFyycx);
//                                        map.put("lsSfyw",lsSfyw);
//                                        map.put("lsDxtfy",lsDxtfy);
//                                        map.put("lsCcsffl",lsCcsffl);
//                                } else if (type.equals("2")) {//新生儿
//                                        //性别
//                                        List<CdCode> lsSex = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_CODESEX, CommonEnable.QIYONG.getValue());
//                                        //妊娠期患病情况
//                                        List<CdCode> lsEtrcqhb = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETRCQHB, CommonEnable.QIYONG.getValue());
//                                        //出生情况
//                                        List<CdCode> lsEtcsqk = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETCSQK, CommonEnable.QIYONG.getValue());
//                                        //是否有无
//                                        List<CdCode> lsSfyw = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYW, CommonEnable.QIYONG.getValue());
//                                        //新生儿听力筛查
//                                        List<CdCode> lsEttlsc = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETTLSC, CommonEnable.QIYONG.getValue());
//                                        //新生儿疾病筛查
//                                        List<CdCode> lsEtjbsc = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETJBSC, CommonEnable.QIYONG.getValue());
//                                        //喂养方式
//                                        List<CdCode> lsWyfs = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_WYFS, CommonEnable.QIYONG.getValue());
//                                        //儿童大便情况
//                                        List<CdCode> lsEtdbqk = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETDBQK, CommonEnable.QIYONG.getValue());
//                                        //儿童面色
//                                        List<CdCode> lsEtmsqk = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETMSQK, CommonEnable.QIYONG.getValue());
//                                        //黄痘部位
//                                        List<CdCode> lsEthdbwqk = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETHDBWQK, CommonEnable.QIYONG.getValue());
//                                        //儿童皮肤
//                                        List<CdCode> lsEtpfqk = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETPFQK, CommonEnable.QIYONG.getValue());
//                                        //是否异常
//                                        List<CdCode> lsSfyc = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYC, CommonEnable.QIYONG.getValue());
//                                        //儿童脐带情况
//                                        List<CdCode> lsDxtfy = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETQDQK, CommonEnable.QIYONG.getValue());
//                                        //新生儿指导
//                                        List<CdCode> lsXsrzd = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETXSEZD, CommonEnable.QIYONG.getValue());
//                                        map.put("lsSex",lsSex);
//                                        map.put("lsEtrcqhb",lsEtrcqhb);
//                                        map.put("lsEtcsqk",lsEtcsqk);
//                                        map.put("lsSfyw",lsSfyw);
//                                        map.put("lsEttlsc",lsEttlsc);
//                                        map.put("lsEtjbsc",lsEtjbsc);
//                                        map.put("lsWyfs",lsWyfs);
//                                        map.put("lsEtdbqk",lsEtdbqk);
//                                        map.put("lsEtmsqk",lsEtmsqk);
//                                        map.put("lsEthdbwqk",lsEthdbwqk);
//                                        map.put("lsEtpfqk",lsEtpfqk);
//                                        map.put("lsSfyc",lsSfyc);
//                                        map.put("lsDxtfy",lsDxtfy);
//                                        map.put("lsXsrzd",lsXsrzd);
//                                } else if (type.equals("3")) {//1~8月龄
//                                        //儿童面色
//                                        List<CdCode> lsEtmsqk = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETMSQK, CommonEnable.QIYONG.getValue());
//                                        //前卤情况
//                                        List<CdCode> lsQlqk = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_QLQK, CommonEnable.QIYONG.getValue());
//                                        //可疑佝偻病症状
//                                        List<CdCode> lsKyglbzz = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_KYGLBZZ3ZHI8YL, CommonEnable.QIYONG.getValue());
//                                        //新生儿听力筛查
//                                        List<CdCode> lsEttlsc = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFTG, CommonEnable.QIYONG.getValue());
//                                        //是否异常
//                                        List<CdCode> lsSfyc = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYC, CommonEnable.QIYONG.getValue());
//                                        //儿童脐带情况
//                                        List<CdCode> lsDxtfy = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETQDQK, CommonEnable.QIYONG.getValue());
//                                        //是否有无
//                                        List<CdCode> lsSyyw = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYW, CommonEnable.QIYONG.getValue());
//                                        //新生儿指导
//                                        List<CdCode> lsXsrzd = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETXSEZD, CommonEnable.QIYONG.getValue());
//                                        //指导
//                                        List<CdCode> lsEtPgqk018yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETPGQK018YL, CommonEnable.QIYONG.getValue());
//                                        //发育评估3月龄
//                                        List<CdCode> lsFypg3yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FYPG3YL, CommonEnable.QIYONG.getValue());
//                                        //发育评估6月龄
//                                        List<CdCode> lsFypg6yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FYPG6YL, CommonEnable.QIYONG.getValue());
//                                        //发育评估8月龄
//                                        List<CdCode> lsFypg8yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FYPG8YL, CommonEnable.QIYONG.getValue());
//                                        map.put("lsEttlsc",lsEttlsc);
//                                        map.put("lsEtmsqk",lsEtmsqk);
//                                        map.put("lsQlqk",lsQlqk);
//                                        map.put("lsKyglbzz",lsKyglbzz);
//                                        map.put("lsSfyc",lsSfyc);
//                                        map.put("lsDxtfy",lsDxtfy);
//                                        map.put("lsSyyw",lsSyyw);
//                                        map.put("lsXsrzd",lsXsrzd);
//                                        map.put("lsEtPgqk018yl",lsEtPgqk018yl);
//                                        map.put("lsEtmsqk",lsEtmsqk);
//                                        map.put("lsFypg3yl",lsFypg3yl);
//                                        map.put("lsFypg6yl",lsFypg6yl);
//                                        map.put("lsFypg8yl",lsFypg8yl);
//                                } else if (type.equals("4")) {//12~30月龄
//                                        //儿童面色
//                                        List<CdCode> lsEtmsqk = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETMSQK, CommonEnable.QIYONG.getValue());
//                                        //前卤情况
//                                        List<CdCode> lsQlqk = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_QLQK, CommonEnable.QIYONG.getValue());
//                                        //新生儿听力筛查
//                                        List<CdCode> lsEttlsc = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFTG, CommonEnable.QIYONG.getValue());
//                                        //是否异常
//                                        List<CdCode> lsSfyc = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYC, CommonEnable.QIYONG.getValue());
//                                        //可疑佝偻病体征
//                                        List<CdCode> lsKyglbtz1230yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_KYGLBTZ1230YL, CommonEnable.QIYONG.getValue());
//                                        //指导1218
//                                        List<CdCode> lsEtPgqk018yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETPGQK018YL, CommonEnable.QIYONG.getValue());
//                                        //指导2472
//                                        List<CdCode> lsEtpgqk2472yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETPGQK2472YL, CommonEnable.QIYONG.getValue());
//                                        //是否有无
//                                        List<CdCode> lsSyyw = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYW, CommonEnable.QIYONG.getValue());
//                                        //发育评估12月龄
                                        List<CdCode> lsFypg12yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FYPG12YL, CommonEnable.QIYONG.getValue());
//                                        //发育评估18月龄
//                                        List<CdCode> lsFypg18yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FYPG18YL, CommonEnable.QIYONG.getValue());
//                                        //发育评估24月龄
//                                        List<CdCode> lsFypg24yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FYPG24YL, CommonEnable.QIYONG.getValue());
//                                        //发育评估30月龄
//                                        List<CdCode> lsFypg30yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FYPG30YL, CommonEnable.QIYONG.getValue());
//                                        map.put("lsEtmsqk",lsEtmsqk);
//                                        map.put("lsQlqk",lsQlqk);
//                                        map.put("lsEttlsc",lsEttlsc);
//                                        map.put("lsKyglbtz1230yl",lsKyglbtz1230yl);
//                                        map.put("lsEtpgqk2472yl",lsEtpgqk2472yl);
//                                        map.put("lsSyyw",lsSyyw);
//                                        map.put("lsFypg12yl",lsFypg12yl);
//                                        map.put("lsFypg18yl",lsFypg18yl);
//                                        map.put("lsFypg24yl",lsFypg24yl);
//                                        map.put("lsFypg30yl",lsFypg30yl);
//                                } else if (type.equals("5")) {//3~6岁
//                                        //体格发育评价
//                                        List<CdCode> lsTgfypj = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_TGFYPJ, CommonEnable.QIYONG.getValue());
//                                        //新生儿听力筛查
//                                        List<CdCode> lsEttlsc = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFTG, CommonEnable.QIYONG.getValue());
//                                        //是否异常
//                                        List<CdCode> lsSfyc = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYC, CommonEnable.QIYONG.getValue());
//                                        //指导2472
//                                        List<CdCode> lsEtpgqk2472yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ETPGQK2472YL, CommonEnable.QIYONG.getValue());
//                                        //是否有无
//                                        List<CdCode> lsSyyw = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYW, CommonEnable.QIYONG.getValue());
//                                        //发育评估36月龄
//                                        List<CdCode> lsFypg36yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FYPG36YL, CommonEnable.QIYONG.getValue());
//                                        //发育评估48月龄
//                                        List<CdCode> lsFypg48yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FYPG48YL, CommonEnable.QIYONG.getValue());
//                                        //发育评估60月龄
//                                        List<CdCode> lsFypg60yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FYPG60YL, CommonEnable.QIYONG.getValue());
//                                        //发育评估72月龄
//                                        List<CdCode> lsFypg72yl = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FYPG72YL, CommonEnable.QIYONG.getValue());
//                                        map.put("lsTgfypj",lsTgfypj);
//                                        map.put("lsEttlsc",lsEttlsc);
//                                        map.put("lsSfyc",lsSfyc);
//                                        map.put("lsEtpgqk2472yl",lsEtpgqk2472yl);
//                                        map.put("lsSyyw",lsSyyw);
//                                        map.put("lsFypg36yl",lsFypg36yl);
//                                        map.put("lsFypg48yl",lsFypg48yl);
//                                        map.put("lsFypg60yl",lsFypg60yl);
//                                        map.put("lsFypg72yl",lsFypg72yl);
//                                } else if (type.equals("6")) {//第一次产前
//                                        //产前既往史
//                                        List<CdCode> lsCqjws = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_CQJWS, CommonEnable.QIYONG.getValue());
//                                        //产前家族史
//                                        List<CdCode> lsCqjzs = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_CQJTS, CommonEnable.QIYONG.getValue());
//                                        //是否异常
//                                        List<CdCode> lsSfyc = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYC, CommonEnable.QIYONG.getValue());
//                                        //产前个人史
//                                        List<CdCode> lsCqgrs = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_CQGRS, CommonEnable.QIYONG.getValue());
//                                        //是否有无
//                                        List<CdCode> lsSyyw = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYW, CommonEnable.QIYONG.getValue());
//                                        //产前孕产史
//                                        List<CdCode> lsCqycs = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_CQYCS, CommonEnable.QIYONG.getValue());
//                                        //阴道分泌物
//                                        List<CdCode> lsCqydfmw = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_CQYDFMW, CommonEnable.QIYONG.getValue());
//                                        //阴道清洁度
//                                        List<CdCode> lsCqydqjcd = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_CQYDQJCD, CommonEnable.QIYONG.getValue());
//                                        //阴性阳性
//                                        List<CdCode> lsSfyxyx = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYXYX, CommonEnable.QIYONG.getValue());
//                                        //保健指导
//                                        List<CdCode> lsCqbjzd = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_CQBJZD, CommonEnable.QIYONG.getValue());
//                                        map.put("lsCqjws",lsCqjws);
//                                        map.put("lsCqjzs",lsCqjzs);
//                                        map.put("lsSfyc",lsSfyc);
//                                        map.put("lsCqgrs",lsCqgrs);
//                                        map.put("lsSyyw",lsSyyw);
//                                        map.put("lsCqydfmw",lsCqydfmw);
//                                        map.put("lsCqydqjcd",lsCqydqjcd);
//                                        map.put("lsSfyxyx",lsSfyxyx);
//                                        map.put("lsCqbjzd",lsCqbjzd);
//                                } else if (type.equals("7")) {//2~5产前
//                                        //是否异常
//                                        List<CdCode> lsSfyc = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYC, CommonEnable.QIYONG.getValue());
//                                        //产前生活指导
//                                        List<CdCode> lsCqshzd = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_CQSHZDS, CommonEnable.QIYONG.getValue());
//                                        //是否有无
//                                        List<CdCode> lsSyyw = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYW, CommonEnable.QIYONG.getValue());
//                                        map.put("lsSfyc",lsSfyc);
//                                        map.put("lsCqshzd",lsCqshzd);
//                                        map.put("lsSyyw",lsSyyw);
//                                } else if (type.equals("8")) {//肺结核
//                                        //督导人员
//                                        List<CdCode> lsDdryfjh = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_DDRYFJH, CommonEnable.QIYONG.getValue());
//                                        //症状及体征
//                                        List<CdCode> lsZzjtzfjh = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FJHZZJTZ, CommonEnable.QIYONG.getValue());
//                                        //用药用法
//                                        List<CdCode> lsYyyf = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_YYYFFJH, CommonEnable.QIYONG.getValue());
//                                        //药品剂型
//                                        List<CdCode> lsYyypjx = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_YYYPJXFJH, CommonEnable.QIYONG.getValue());
//                                        //停止资料原因
//                                        List<CdCode> lsTzzlyy = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_TZZLYYFJH, CommonEnable.QIYONG.getValue());
//                                        map.put("lsDdryfjh",lsDdryfjh);
//                                        map.put("lsZzjtzfjh",lsZzjtzfjh);
//                                        map.put("lsYyyf",lsYyyf);
//                                        map.put("lsYyypjx",lsYyypjx);
//                                        map.put("lsTzzlyy",lsTzzlyy);
//                                } else if (type.equals("9")) {//精神
//                                        //失访原因
//                                        List<CdCode> lsSfyyjsb = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYYJSB, CommonEnable.QIYONG.getValue());
//                                        //死亡原因
//                                        List<CdCode> lsSyyysjb = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SYYYSJB, CommonEnable.QIYONG.getValue());
//                                        //危险性评估
//                                        List<CdCode> lsWxxpg = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_WXXPGJSB, CommonEnable.QIYONG.getValue());
//                                        //目前症状
//                                        List<CdCode> lsMqzzjsb = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_MQZZJSB, CommonEnable.QIYONG.getValue());
//                                        //自知力
//                                        List<CdCode> lsZzljsb = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ZZLJSB, CommonEnable.QIYONG.getValue());
//                                        //良好 一般差
//                                        List<CdCode> lsLhybc = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_LHYBC, CommonEnable.QIYONG.getValue());
//                                        //关锁情况
//                                        List<CdCode> lsGsqk = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_GSQKJSB, CommonEnable.QIYONG.getValue());
//                                        //住院情况
//                                        List<CdCode> lsZyqkjsb = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ZYQKJSB, CommonEnable.QIYONG.getValue());
//                                        //是否有无
//                                        List<CdCode> lsSyyw = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFYW, CommonEnable.QIYONG.getValue());
//                                        //是否
//                                        List<CdCode> lsSycommon = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFCOMMON, CommonEnable.QIYONG.getValue());
//                                        //用药医嘱性
//                                        List<CdCode> lsYyycxjsb = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_YYYCXJSB, CommonEnable.QIYONG.getValue());
//                                        //治疗效果
//                                        List<CdCode> lsZlxg = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ZLXGJSB, CommonEnable.QIYONG.getValue());
//                                        //康复措施
//                                        List<CdCode> lsKfcs = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_KFCSJSB, CommonEnable.QIYONG.getValue());
//                                        map.put("lsSfyyjsb",lsSfyyjsb);
//                                        map.put("lsSyyysjb",lsSyyysjb);
//                                        map.put("lsWxxpg",lsWxxpg);
//                                        map.put("lsMqzzjsb",lsMqzzjsb);
//                                        map.put("lsZzljsb",lsZzljsb);
//                                        map.put("lsLhybc",lsLhybc);
//                                        map.put("lsGsqk",lsGsqk);
//                                        map.put("lsZyqkjsb",lsZyqkjsb);
//                                        map.put("lsSyyw",lsSyyw);
//                                        map.put("lsSycommon",lsSycommon);
//                                        map.put("lsYyycxjsb",lsYyycxjsb);
//                                        map.put("lsZlxg",lsZlxg);
//                                        map.put("lsKfcs",lsKfcs);
//                                }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 添加随访计划
     *
     * @return
     */
    public String addFollowPlan() {
        try {
            HttpClient client = HttpClients.createDefault();
//            AppFollowPlanEntity vo = (AppFollowPlanEntity) getVoJson(AppFollowPlanEntity.class);
            AppFollowPlanEntity vo = (AppFollowPlanEntity)getAppJson(AppFollowPlanEntity.class);
            AppDrUser user = this.getAppDrUser();
            if(vo != null && StringUtils.isNotBlank(vo.getSfFollowPatientid())){
                if(StringUtils.isNotBlank(vo.getSfFollowPatientid())){
                    AppHospDept dept = (AppHospDept)this.getSysDao().getServiceDo().find(AppHospDept.class,user.getDrHospId());
                    String[] patientIds = vo.getSfFollowPatientid().split(";");
                    // String[] patientNames = vo.getSfFollowPatientName().split(";");
                    boolean fl = false;
                    String result = null;
                    AppOpenRemind gor = null;
                    vo.setSfFollowDoctorid(user.getId());
                    List<AppOpenRemind> gors=this.sysDao.getServiceDo().loadByPk(AppOpenRemind.class,"doctorId",vo.getSfFollowDoctorid());
                    if(gors!=null&&gors.size()>0){
                        gor = gors.get(0);
                    }else{
                        gor = new AppOpenRemind();
                        gor.setDoctorId(vo.getSfFollowDoctorid());
                        gor.setDayNum("0");
                        gor.setState("0");
                        this.getSysDao().getServiceDo().add(gor);
                    }
                    for(int i=0;i<patientIds.length;i++){
                        //根据身份证号和当前时间查询记录
                        boolean flag = this.getSysDao().getAppFollowPlanDao().findByIdAndTime(patientIds[i],vo.getSfFollowDate(),vo.getSfFollowType());
                        AppPatientUser patientUser = (AppPatientUser)this.sysDao.getServiceDo().find(AppPatientUser.class,patientIds[i]);
                        if(flag){
                            fl = true;
                            if(StringUtils.isBlank(result)){
                                result = patientUser.getPatientName()+"已随访，不能重复随访！";
                            }else{
                                result += patientUser.getPatientName()+"已随访，不能重复随访！";
                            }
                            continue;
                        }

                        AppFollowPlan p = new AppFollowPlan();
                        p.setSfFollowPatientid(patientIds[i]);
//                        p.setSfYaxis(vo.getSfY());//纵坐标
//                        p.setSfXaxis(vo.getSfX());//横坐标
                        p.setSfFollowPatientName(patientUser.getPatientName());
                        p.setSfFollowDate(ExtendDate.getCalendar(vo.getSfFollowDate()));
                        p.setSfFollowMode(vo.getSfFollowMode());
                        p.setSfFollowType(vo.getSfFollowType());
                        p.setSfOrgId(user.getDrHospId());
                        p.setSfHosAreaCode(dept.getHospAreaCode());
                        p.setSfTeamId(vo.getSfTeamId());
                        if(gor!=null){
//                            p.setSfFollowDay(gor.getDayNum());
//                            p.setSfFollowDayTx(gor.getState());
                        }
                        p.setSfFollowDoctorid(vo.getSfFollowDoctorid());
                        if(StringUtils.isNotBlank(vo.getSfFollowPatientid())){
                            List<AppSignForm> signForms = this.sysDao.getServiceDo().loadByPk(AppSignForm.class,"signPatientId",vo.getSfFollowPatientid());
                            if(signForms!=null && signForms.size()>0){
                                AppSignForm signForm = signForms.get(0);
                                if(signForm!=null){
                                    p.setSfHosId(signForm.getSignHospId());
                                    p.setSfHosAreaCode(signForm.getSignAreaCode());
                                    p.setSfTeamId(signForm.getSignTeamId());
                                }
                            }

                        }
                        String num = this.getSysDao().getManageMentCodePolicy().createAlipayPch("01", ExtendDate.getYMD(Calendar.getInstance()));
                        p.setSfFollowNum(num);
                        p.setSfFollowState("0");
//                        p.setSfFollowSftx("0");
                        CdCode codeResult = this.getSysDao().getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFDKJWSJ);
                        if(codeResult != null){
                            if(codeResult.getCodeValue().equals("0")){
                                JSONObject jsonParam = new JSONObject();
                                jsonParam.put("name",vo.getSfFollowPatientName());
                                jsonParam.put("idno",vo.getSfFollowPatientid());
                                jsonParam.put("city","FZ");
                                String urlLogin =  PropertiesUtil.getConfValue("appYlkUrl") + "/thirdAPI/patient/archive";
                                String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                                JSONObject jsonAll = JSONObject.fromObject(str1);
                                String jsonString = jsonAll.get("entity").toString();
                                if(!("400").equals(jsonString)){
                                    JSONObject entity = JSONObject.fromObject(jsonString);
                                    String jmdah = entity.get("jmdah").toString();
                                    p.setSfHealthNum(jmdah);//健康档案号
                                }

                            }
                        }
                        this.getSysDao().getServiceDo().add(p);
                        AppWorkingPlan plan = new AppWorkingPlan();//工作计划
                        plan.setPlanDrId(p.getSfFollowDoctorid());
                        plan.setPlanDate(Calendar.getInstance());
                        plan.setPlanType(CommonWorkPlanType.SFJH.getValue());
                        plan.setPlanState(CommonWorkPlanState.WWC.getValue());
                        plan.setPlanForeignId(p.getId());
                        AppDrUser drUser = this.getAppDrUser();
                        if(drUser!=null){
                            plan.setPlanHospId(drUser.getDrHospId());
                            AppHospDept hosp = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                            if(hosp!=null){
                                plan.setPlanAreaCode(hosp.getHospAreaCode());
                            }
                        }
                        List<AppSignForm> vv = this.sysDao.getServiceDo().loadByPk(AppSignForm.class,"signPatientId",p.getSfFollowPatientid());
                        if(vv!=null && vv.size()>0){
                            plan.setPlanTeamId(vv.get(0).getSignTeamId());
                        }
                        this.sysDao.getServiceDo().add(plan);

                    }
                    if(fl){
                        this.getAjson().setMsgCode("801");
                        this.getAjson().setMsg(result);
                    }
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 延期随访计划
     *
     * @return
     */
    public String callFollowPlan() {
        try {
            AppPlanTypeQvo qvo = (AppPlanTypeQvo)this.getAppJson(AppPlanTypeQvo.class);
            if (qvo!=null) {
                    AppFollowPlan v = (AppFollowPlan) this.getSysDao().getServiceDo().find(AppFollowPlan.class, qvo.getPatientId());
                    if (v != null) {
                        v.setSfFollowState("2");//取消随访
                        v.setSfFollowDate(ExtendDate.getCalendar(qvo.getDelayDate()));
                        this.getSysDao().getServiceDo().modify(v);
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setVo(v);
                        this.getAjson().setMsg("延期随访成功");
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("未找到随访计划");
                    }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 开始随访计划
     *
     * @return
     */
    public String startFollowPlan() {
        try {
            AppDrUser doc = this.getAppDrUser();
            String json = this.getAppStrJson();
            JSONObject jsonAll = JSONObject.fromObject(json);
                if (jsonAll.get("id") != null) {
                    AppFollowPlan v = (AppFollowPlan) this.getSysDao().getServiceDo().find(AppFollowPlan.class, jsonAll.get("id").toString());
                    if(doc!=null){
                        AppHospDept hosp = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,doc.getDrHospId());
                        v.setSfHosId(hosp.getId());
                        v.setSfHosAreaCode(hosp.getHospAreaCode());
                    }
                    v.setSfEndDate(Calendar.getInstance());
                    v.setSfIsOrNot("0");
                    AppMainFamilyDoctor family = new AppMainFamilyDoctor();
                    if (v != null) {
                        if (jsonAll.get("entity") != null) {
                            if (v.getSfFollowType().equals("0")) {//高血压
                                AppHdBlooPressureTableEntity table = JsonUtil.fromJson(jsonAll.get("entity").toString(), AppHdBlooPressureTableEntity.class);
                                AppHdBlooPressureTable po = new AppHdBlooPressureTable();
                                if(table!=null){
                                    CopyUtils.Copy(table, po);
                                    po.setVisitId(v.getId());
                                    this.getSysDao().getServiceDo().add(po);
                                    if(table.getUserMedicine() != null && table.getUserMedicine().size() >0){
                                        for(int i=0;i<table.getUserMedicine().size();i++){
                                            AppMedicationTable vv = new AppMedicationTable();
                                            CopyUtils.Copy(table.getUserMedicine().get(i),vv);
                                            vv.setVisitId(po.getId());
                                            this.getSysDao().getServiceDo().add(vv);
                                        }
                                    }
                                }

                                CdCode codeResult = this.getSysDao().getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFDKJWSJ);
                                if(codeResult != null){
                                    if(codeResult.getCodeValue().equals("0")){
                                        AppToJwEntity jwe = new AppToJwEntity();
                                        jwe.setYgbh00(v.getSfFollowDoctorid());
                                        jwe.setDf_id(v.getSfHealthNum());//居民档案号
                                        jwe.setMxjbbz("1");
                                        jwe.setSfrq00(String.valueOf(v.getSfFollowDate()).replace("-",""));
                                        jwe.setSfysqm(v.getSfFollowDoctorid());
                                        jwe.setSffs00(v.getSfFollowMode());
                                        jwe.setXcsfrq(table.getNextVisiTime().replace("-",""));
                                        jwe.setMark("1");
                                        jwe.setSf_id0000(v.getSfFollowNum());
                                        if(table.getSymptoms().indexOf("无症状")>=0){
                                            jwe.setWuzz00("1");//无症状（1-是，0-否）
                                        }else{
                                            jwe.setWuzz00("0");//无症状（1-是，0-否）
                                        }
                                        if(table.getSymptoms().indexOf("头痛头晕")>=0){
                                            jwe.setTtty("1");//头痛头晕（高血压：1-是，0-否）
                                        }else{
                                            jwe.setTtty("0");//头痛头晕（高血压：1-是，0-否）
                                        }
                                        if(table.getSymptoms().indexOf("恶心呕吐")>=0){
                                            jwe.setExot("1");//恶心呕吐（高血压：1-是，0-否）
                                        }else{
                                            jwe.setExot("0");//恶心呕吐（高血压：1-是，0-否）
                                        }
                                        if(table.getSymptoms().indexOf("眼花耳鸣")>=0){
                                            jwe.setYhem("1");//眼花耳鸣（高血压：1-是，0-否）
                                        }else{
                                            jwe.setYhem("0");//眼花耳鸣（高血压：1-是，0-否）
                                        }
                                        if(table.getSymptoms().indexOf("心悸胸闷")>=0){
                                            jwe.setXjxm("1");//心悸胸闷（高血压：1-是，0-否）
                                        }else{
                                            jwe.setXjxm("0");//心悸胸闷（高血压：1-是，0-否）
                                        }
                                        if(table.getSymptoms().indexOf("呼吸困难")>=0){
                                            jwe.setHxkn("1");//呼吸困难（高血压：1-是，0-否）
                                        }else{
                                            jwe.setHxkn("0");//呼吸困难（高血压：1-是，0-否）
                                        }
                                        if(table.getSymptoms().indexOf("鼻子出血不止")>=0){
                                            jwe.setBccxbz("1");//鼻子出血不止（高血压：1-是，0-否）
                                        }else{
                                            jwe.setBccxbz("0");//鼻子出血不止（高血压：1-是，0-否）
                                        }
                                        if(table.getSymptoms().indexOf("四肢麻木")>=0){
                                            jwe.setSzfm("1");//(高血压-四肢麻木，糖尿病-手脚麻木：1-是，0-否)
                                        }else{
                                            jwe.setSzfm("0");//(高血压-四肢麻木，糖尿病-手脚麻木：1-是，0-否)
                                        }
                                        if(table.getSymptoms().indexOf("下肢水肿")>=0){
                                            jwe.setXzsz("1");//下肢水肿（高血压：1-是，0-否）
                                        }else{
                                            jwe.setXzsz("0");//下肢水肿（高血压：1-是，0-否）
                                        }
                                        jwe.setTqzz("");//其他症状

                                        jwe.setSzy(table.getBloodPressureTwo());//舒张压（血压mmHg）
                                        jwe.setSsy(table.getBloodPressureOne());//收缩压（血压mmHg）
                                        jwe.setTzone(table.getWeight());//体重（目前kg）
                                        jwe.setTztwo("");//体重（计划kg）
                                        jwe.setSg0000("");//身高(cm)
                                        jwe.setTzzs00(table.getBmi());//体质指数
                                        jwe.setXlone(table.getHeartRate());//心率（高血压：目前）
                                        jwe.setXltwo("");//心率（高血压：计划）
                                        jwe.setQttz00(table.getSaltSituation());//其他体征
                                        jwe.setRxylone(table.getSmokingToDay());//日吸烟量（目前 支）
                                        jwe.setRxyltwo("");//日吸烟量（计划 支）
                                        jwe.setRyjlone(table.getDrinkingToDay());//日饮酒量（目前 两）
                                        jwe.setRyjltwo("");//日饮酒量（计划 两）
                                        jwe.setYdzcone(table.getActivityToWeek());//运动周次（目前 次/周）
                                        jwe.setYdzctwo("");//运动周次（计划 次/周）
                                        jwe.setYdrcone(table.getActivityToTime());//运动分次（目前 分钟/次）
                                        jwe.setYdrctwo("");//运动分次（计划 分钟/次）
                                        jwe.setSyqkone(table.getSaltSituation());//摄盐情况（高血压目前：轻-轻，中-中，重-重）
                                        jwe.setSyqktwo("");//摄盐情况（高血压计划：轻-轻，中-中，重-重）
                                        if(table.getMentalityAdjust().equals("良好")){
                                            jwe.setXltzqk("1");//心理调整（1--良好，2--一般，3--差）
                                        }else if(table.getMentalityAdjust().equals("一般")){
                                            jwe.setXltzqk("2");//心理调整（1--良好，2--一般，3--差）
                                        }else if(table.getMentalityAdjust().equals("差")){
                                            jwe.setXltzqk("3");//心理调整（1--良好，2--一般，3--差）
                                        }
                                        if(table.getFollowingBehavior().equals("良好")){
                                            jwe.setZyxwqk("1");//遵医情况（1--良好，2---一般，3--差）
                                        }else if(table.getFollowingBehavior().equals("一般")){
                                            jwe.setZyxwqk("2");//遵医情况（1--良好，2---一般，3--差）
                                        }else if(table.getFollowingBehavior().equals("差")){
                                            jwe.setZyxwqk("3");//遵医情况（1--良好，2---一般，3--差）
                                        }

                                        jwe.setQtfzjc(table.getFzCheck());//其他辅助检查
                                        if(table.getMedicationAdherence().equals("规律")){
                                            jwe.setFyycx0("1");//服药依从性（1--规律，2--间断，3--不服药）
                                        }else if(table.getMedicationAdherence().equals("间断")){
                                            jwe.setFyycx0("2");//服药依从性（1--规律，2--间断，3--不服药）
                                        }else if(table.getMedicationAdherence().equals("不服药")){
                                            jwe.setFyycx0("3");//服药依从性（1--规律，2--间断，3--不服药）
                                        }

                                        if(table.getDrugReaction().equals("无")){
                                            jwe.setYwblfy("0");//药物不良反应（0--无，1--有）
                                        }else if(table.getDrugReaction().equals("有")){
                                            jwe.setYwblfy("1");//药物不良反应（0--无，1--有）
                                        }

                                        jwe.setBlfyms(table.getDrugReactionContent());//不良反应描述
//                                                            jwe.setDxtfy("");//低血糖反应（糖尿病：1--无，2--偶尔，3--频繁）
                                        if(table.getVisitThisType().equals("控制满意")){
                                            jwe.setCcsffl("1");//此次随访分类（1--控制满意，2--控制不满意，3--不良反应，4--并发症）
                                        }else if(table.getVisitThisType().equals("控制不满意")){
                                            jwe.setCcsffl("2");//此次随访分类（1--控制满意，2--控制不满意，3--不良反应，4--并发症）
                                        }else if(table.getVisitThisType().equals("不良反应")){
                                            jwe.setCcsffl("3");//此次随访分类（1--控制满意，2--控制不满意，3--不良反应，4--并发症）
                                        }else if(table.getVisitThisType().equals("并发症")){
                                            jwe.setCcsffl("4");//此次随访分类（1--控制满意，2--控制不满意，3--不良反应，4--并发症）
                                        }

                                        jwe.setYyqkList(table.getUserMedicine());//用药情况列表
//                                                            jwe.setYds000("");//胰岛素种类（糖尿病）
//                                                            jwe.setYdsyf0("");//胰岛素用法和用量（糖尿病）
                                        jwe.setZzqk00("");//转诊情况（0--无，1--有）
                                        jwe.setZzyy00(table.getReferralReason());//转诊原因
//                                        jwe.setZzjgks(table.getOrgAndDept());//转诊机构科室
                                        jwe.setZzbz00("");//转诊备注
                                        jwe.setYwysxm(table.getVisitDoctorName());//院外医生姓名


                                        HttpClient client = HttpClients.createDefault();
                                        JSONObject jsonParam = new JSONObject();
                                        jsonParam.put("jwDoctorId", v.getSfFollowDoctorid());//医生id contractState
                                        jsonParam.put("detail",JsonUtil.toJson(jwe));
                                        jsonParam.put("city", "FZ");
                                        String urlLogin = PropertiesUtil.getConfValue("appYlkUrl") + "/dc/fu/addSugar/followUpInfo";
                                        String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam, "utf-8");
                                        JSONObject jsonAll1 = JSONObject.fromObject(str1);
                                        if(jsonAll1.get("success").equals("false")){
                                            //数据未传到基卫，添加系统消息
//                                            GwMsg m = new GwMsg();
//                                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                                            m.setMsgType("2");//系统消息
//                                            m.setMsgText("对患者"+v.getSfFollowPatientName()+"在"+ExtendDate.getYMD(v.getSfFollowDate())+"的高血压随访，由于"+jsonAll1.get("message")+"所以该患者的随访的信息无法上传到基卫。");
//                                            m.setMsgTitle("随访提醒");
//                                            m.setMsgUserName(v.getDoctorName());
//                                            m.setMsgUserid(v.getSfFollowDoctorid());
//                                            m.setMsgCreaterDate(Calendar.getInstance());
//                                            m.setMsgReadState("0");
//                                            sysDao.getServiceDo().add(m);
                                        }else{
                                            v.setSfIsOrNot("1");
                                        }

                                    }
                                }
                                CopyUtils.Copy(table, family);
//                                v.setSfFollowData(jsonAll.get("entity").toString());
//                                                        v.setSfFollowPublicData(JsonUtil.toJson(family));
                            } else if (v.getSfFollowType().equals("1")) {//血糖
                                AppDiabetesTableEntity table = JsonUtil.fromJson(jsonAll.get("entity").toString(), AppDiabetesTableEntity.class);
                                AppDiabetesTable po = new AppDiabetesTable();
                                if(table!=null){
                                    CopyUtils.Copy(table, po);
                                    po.setVisitId(v.getId());
                                    this.getSysDao().getServiceDo().add(po);
                                    if(table.getUserMedicine() != null && table.getUserMedicine().size() >0){
                                        for(int i=0;i<table.getUserMedicine().size();i++){
                                            AppMedicationTable vv = new AppMedicationTable();
                                            CopyUtils.Copy(table.getUserMedicine().get(i),vv);
                                            vv.setVisitId(po.getId());
                                            this.getSysDao().getServiceDo().add(vv);
                                        }
                                    }
                                }

                                CdCode codeResult = this.getSysDao().getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFDKJWSJ);
                                if(codeResult != null){
                                    if(codeResult.getCodeValue().equals("0")){
                                        AppToJwEntity jwe = new AppToJwEntity();
                                        jwe.setYgbh00(v.getSfFollowDoctorid());
                                        jwe.setDf_id(v.getSfHealthNum());//居民档案号
                                        jwe.setMxjbbz("1");
                                        jwe.setSfrq00(String.valueOf(v.getSfFollowDate()).replace("-",""));
                                        jwe.setSfysqm(v.getSfFollowDoctorid());
                                        jwe.setSffs00(v.getSfFollowMode());
                                        jwe.setXcsfrq(table.getNextVisiTime().replace("-",""));
                                        jwe.setMark("1");
                                        jwe.setSf_id0000(v.getSfFollowNum());
                                        if(table.getSymptoms().indexOf("无症状")>=0){
                                            jwe.setWuzz00("1");//无症状（1-是，0-否）
                                        }else{
                                            jwe.setWuzz00("0");//无症状（1-是，0-否）
                                        }
//                                                            jwe.setTtty("");//头痛头晕（高血压：1-是，0-否）
//                                                            jwe.setExot("");//恶心呕吐（高血压：1-是，0-否）
//                                                            jwe.setYhem("");//眼花耳鸣（高血压：1-是，0-否）
//                                                            jwe.setXjxm("");//心悸胸闷（高血压：1-是，0-否）
//                                                            jwe.setHxkn("");//呼吸困难（高血压：1-是，0-否）
//                                                            jwe.setBccxbz("");//鼻子出血不止（高血压：1-是，0-否）
                                        if(table.getSymptoms().indexOf("四肢麻木")>=0){
                                            jwe.setSzfm("1");//(高血压-四肢麻木，糖尿病-手脚麻木：1-是，0-否)
                                        }else{
                                            jwe.setSzfm("0");//(高血压-四肢麻木，糖尿病-手脚麻木：1-是，0-否)
                                        }
//                                                            jwe.setXzsz("");//下肢水肿（高血压：1-是，0-否）
                                        if(table.getSymptoms().indexOf("多饮")>=0){
                                            jwe.setDy("1");//多饮（糖尿病：1-是，0-否）
                                        }else{
                                            jwe.setDy("0");//多饮（糖尿病：1-是，0-否）
                                        }
                                        if(table.getSymptoms().indexOf("多食")>=0){
                                            jwe.setDs("1");//多食（糖尿病：1-是，0-否）
                                        }else{
                                            jwe.setDs("0");//多食（糖尿病：1-是，0-否）
                                        }
                                        if(table.getSymptoms().indexOf("多尿")>=0){
                                            jwe.setDn("1");//多尿 （糖尿病：1-是，0-否）
                                        }else{
                                            jwe.setDn("0");//多尿 （糖尿病：1-是，0-否）
                                        }
                                        if(table.getSymptoms().indexOf("视力模糊")>=0){
                                            jwe.setSlmh("1");//视力模糊（糖尿病：1-是，0-否）
                                        }else{
                                            jwe.setSlmh("0");//视力模糊（糖尿病：1-是，0-否）
                                        }
                                        if(table.getSymptoms().indexOf("感染")>=0){
                                            jwe.setGl("1");//感染（糖尿病：1-是，0-否）
                                        }else{
                                            jwe.setGl("0");//感染（糖尿病：1-是，0-否）
                                        }
                                        if(table.getSymptoms().indexOf("四肢浮肿")>=0){
                                            jwe.setXzfz("1");//四肢浮肿（糖尿病：1-是，0-否）
                                        }else{
                                            jwe.setXzfz("0");//四肢浮肿（糖尿病：1-是，0-否）
                                        }

                                        jwe.setTqzz("");//其他症状
                                        jwe.setSzy(table.getBloodPressureTwo());//舒张压（血压mmHg）
                                        jwe.setSsy(table.getBloodPressureOne());//收缩压（血压mmHg）
                                        jwe.setTzone(table.getWeight());//体重（目前kg）
                                        jwe.setTztwo("");//体重（计划kg）
                                        jwe.setSg0000("");//身高(cm)
//                                        jwe.setTzzs00(table.getBMI());//体质指数
//
                                        jwe.setQttz00(table.getSignsOther());//其他体征
                                        jwe.setRxylone(table.getSmokingToDay());//日吸烟量（目前 支）
                                        jwe.setRxyltwo("");//日吸烟量（计划 支）
                                        jwe.setRyjlone(table.getDrinkingToDay());//日饮酒量（目前 两）
                                        jwe.setRyjltwo("");//日饮酒量（计划 两）
                                        jwe.setYdzcone(table.getActivityToWeek());//运动周次（目前 次/周）
                                        jwe.setYdzctwo("");//运动周次（计划 次/周）
                                        jwe.setYdrcone(table.getActivityToTime());//运动分次（目前 分钟/次）
                                        jwe.setYdrctwo("");//运动分次（计划 分钟/次）
//                                                            jwe.setXlone("");//心率（高血压：目前）
//                                                            jwe.setXltwo("");//心率（高血压：计划）
                                        jwe.setZbdmpd("");//足背动脉搏动（糖尿病：1--未触及，2--触及）
//                                                            jwe.setSyqkone("");//摄盐情况（高血压目前：轻-轻，中-中，重-重）
//                                                            jwe.setSyqktwo("");//摄盐情况（高血压计划：轻-轻，中-中，重-重）
                                        jwe.setZsqkone(table.getFood());//主食情况（糖尿病：目前  克/每天）
                                        jwe.setZsqktwo("");//主食情况（糖尿病：计划 克/每天）
                                        if(table.getMentalityAdjust().equals("良好")){
                                            jwe.setXltzqk("1");//心理调整（1--良好，2--一般，3--差）
                                        }else if(table.getMentalityAdjust().equals("一般")){
                                            jwe.setXltzqk("2");//心理调整（1--良好，2--一般，3--差）
                                        }else if(table.getMentalityAdjust().equals("差")){
                                            jwe.setXltzqk("3");//心理调整（1--良好，2--一般，3--差）
                                        }
                                        if(table.getFollowingBehavior().equals("良好")){
                                            jwe.setZyxwqk("1");//遵医情况（1--良好，2---一般，3--差）
                                        }else if(table.getFollowingBehavior().equals("一般")){
                                            jwe.setZyxwqk("2");//遵医情况（1--良好，2---一般，3--差）
                                        }else if(table.getFollowingBehavior().equals("差")){
                                            jwe.setZyxwqk("3");//遵医情况（1--良好，2---一般，3--差）
                                        }

                                        jwe.setKfxtz0(table.getFastingBloodSugar());//空腹血糖值(糖尿病mmol/L)
                                        jwe.setChxtz0("");//餐后2小时血糖值(糖尿病 mmol/L)
                                        jwe.setThxhdb(table.getThHemoglobin());//糖化血红蛋白 (糖尿病)
                                        if(StringUtils.isNoneBlank(table.getFzCheckDate())) {
                                            jwe.setJcrq00(table.getFzCheckDate().replace("/",""));//检查日期（糖尿病 格式：yyyymmdd)
                                        }
                                        jwe.setQtfzjc(table.getOtherCheck());//其他辅助检查
                                        if(table.getMedicationAdherence().equals("规律")){
                                            jwe.setFyycx0("1");//服药依从性（1--规律，2--间断，3--不服药）
                                        }else if(table.getMedicationAdherence().equals("间断")){
                                            jwe.setFyycx0("2");//服药依从性（1--规律，2--间断，3--不服药）
                                        }else if(table.getMedicationAdherence().equals("不服药")){
                                            jwe.setFyycx0("3");//服药依从性（1--规律，2--间断，3--不服药）
                                        }

                                        if(table.getDrugReaction().equals("无")){
                                            jwe.setYwblfy("0");//药物不良反应（0--无，1--有）
                                        }else if(table.getDrugReaction().equals("有")){
                                            jwe.setYwblfy("1");//药物不良反应（0--无，1--有）
                                        }
                                        jwe.setBlfyms(table.getDrugReaction());//不良反应描述
                                        if(table.getVisitThisType().equals("控制满意")){
                                            jwe.setCcsffl("");//此次随访分类（1--控制满意，2--控制不满意，3--不良反应，4--并发症）
                                        }else if(table.getVisitThisType().equals("控制不满意")){
                                            jwe.setCcsffl("");//此次随访分类（1--控制满意，2--控制不满意，3--不良反应，4--并发症）
                                        }else if(table.getVisitThisType().equals("不良反应")){
                                            jwe.setCcsffl("");//此次随访分类（1--控制满意，2--控制不满意，3--不良反应，4--并发症）
                                        }else if(table.getVisitThisType().equals("并发症")){
                                            jwe.setCcsffl("");//此次随访分类（1--控制满意，2--控制不满意，3--不良反应，4--并发症）
                                        }

                                        jwe.setDxtfy(table.getLowBloodGlucose());//低血糖反应（糖尿病：1--无，2--偶尔，3--频繁）
                                        jwe.setYyqkList(table.getUserMedicine());//用药情况列表
                                        jwe.setYds000(table.getInsulin());//胰岛素种类（糖尿病）
                                        jwe.setYdsyf0(table.getUserInsulin());//胰岛素用法和用量（糖尿病）
                                        jwe.setZzqk00("");//转诊情况（0--无，1--有）
                                        jwe.setZzyy00(table.getReferralReason());//转诊原因
//                                        jwe.setZzjgks(table.getOrgAndDept());//转诊机构科室
                                        jwe.setZzbz00("");//转诊备注
                                        jwe.setYwysxm(table.getVisitDoctorName());//院外医生姓名
                                        HttpClient client = HttpClients.createDefault();
                                        JSONObject jsonParam = new JSONObject();
                                        jsonParam.put("jwDoctorId", v.getSfFollowDoctorid());//医生id contractState
                                        jsonParam.put("detail",JsonUtil.toJson(jwe));
                                        jsonParam.put("city", "FZ");
                                        String urlLogin = PropertiesUtil.getConfValue("appYlkUrl") + "/dc/fu/addSugar/followUpInfo";
                                        String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam, "utf-8");
                                        JSONObject jsonAll1 = JSONObject.fromObject(str1);
                                        if(jsonAll1.get("success").equals("false")){
                                            //数据未传到基卫，添加系统消息
//                                            GwMsg m = new GwMsg();
//                                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                                            m.setMsgType("2");//系统消息
//                                            m.setMsgText("对患者"+v.getSfFollowPatientName()+"在"+ExtendDate.getYMD(v.getSfFollowDate())+"的糖尿病随访，由于"+jsonAll1.get("message")+"所以该患者的随访的信息无法上传到基卫。");
//                                            m.setMsgTitle("随访提醒");
//                                            m.setMsgUserName(v.getDoctorName());
//                                            m.setMsgUserid(v.getSfFollowDoctorid());
//                                            m.setMsgCreaterDate(Calendar.getInstance());
//                                            m.setMsgReadState("0");
//                                            sysDao.getServiceDo().add(m);
                                        }else{
                                            v.setSfIsOrNot("1");
                                        }
                                    }
                                }


                                CopyUtils.Copy(table, family);
//                                v.setSfFollowData(jsonAll.get("entity").toString());
//                                                        v.setSfFollowPublicData(JsonUtil.toJson(family));
                            } else if (v.getSfFollowType().equals("2")) {//新生儿
                                AppNewChildrenTableEntity table = JsonUtil.fromJson(jsonAll.get("entity").toString(), AppNewChildrenTableEntity.class);
                                AppNewChildrenTable po = new AppNewChildrenTable();
                                if(table!=null){
                                    CopyUtils.Copy(table, po);
                                    po.setVisitId(v.getId());
                                    this.getSysDao().getServiceDo().add(po);
                                }
//                                                        CopyUtils.Copy(table, family);
//                                v.setSfFollowData(jsonAll.get("entity").toString());
//                                                        v.setSfFollowPublicData(JsonUtil.toJson(family));
                            } else if (v.getSfFollowType().equals("3")) {//1~8月龄
                                AppOTEMonthChildrenTableEntity table = JsonUtil.fromJson(jsonAll.get("entity").toString(), AppOTEMonthChildrenTableEntity.class);
                                AppOTEMonthChildrenTable po = JsonUtil.fromJson(jsonAll.get("entity").toString(), AppOTEMonthChildrenTable.class);
                                po.setVisitId(v.getId());
                                this.getSysDao().getServiceDo().add(po);

//                                                        CopyUtils.Copy(table, family);
//                                v.setSfFollowData(jsonAll.get("entity").toString());
//                                                        v.setSfFollowPublicData(JsonUtil.toJson(family));
                            } else if (v.getSfFollowType().equals("4")) {//12~30月龄
                                AppTTTMonthChildrenTableEntity table = JsonUtil.fromJson(jsonAll.get("entity").toString(), AppTTTMonthChildrenTableEntity.class);
                                AppTTTMonthChildrenTable po = new AppTTTMonthChildrenTable();
                                if(table!=null){
                                    CopyUtils.Copy(table, po);
                                    po.setVisitId(v.getId());
                                    this.getSysDao().getServiceDo().add(po);
                                }
//                                                        CopyUtils.Copy(table, family);
//                                v.setSfFollowData(jsonAll.get("entity").toString());
//                                                        v.setSfFollowPublicData(JsonUtil.toJson(family));
                            } else if (v.getSfFollowType().equals("5")) {//3~6岁
                                //GwTTSYearChildrenTableEntity table = JsonUtil.fromJson(jsonAll.get("entity").toString(), GwTTSYearChildrenTableEntity.class);
                                AppTTSYearChildrenTable po =JsonUtil.fromJson(jsonAll.get("entity").toString(), AppTTSYearChildrenTable.class);
                                //   if(table!=null){
                                // CopyUtils.Copy(table, po);
                                po.setVisitId(v.getId());
                                this.getSysDao().getServiceDo().add(po);
                                //   }

//                                v.setSfFollowData(jsonAll.get("entity").toString());
//                                                        v.setSfFollowPublicData(JsonUtil.toJson(family));
                            } else if (v.getSfFollowType().equals("6")) {//第一次产前

                                AppFirstPrenatalTable po = JsonUtil.fromJson(jsonAll.get("entity").toString(), AppFirstPrenatalTable.class);
                                po.setVisitId(v.getId());
                                this.getSysDao().getServiceDo().add(po);

//                                v.setSfFollowData(jsonAll.get("entity").toString());

                            } else if (v.getSfFollowType().equals("7")) {//2~5产前
                                //GwTTFPrenatalTableEntity table = JsonUtil.fromJson(jsonAll.get("entity").toString(), GwTTFPrenatalTableEntity.class);
                                AppTTFPrenatalTable po = JsonUtil.fromJson(jsonAll.get("entity").toString(), AppTTFPrenatalTable.class);
                                po.setVisitId(v.getId());
                                this.getSysDao().getServiceDo().add(po);
//                                v.setSfFollowData(jsonAll.get("entity").toString());
//                                                        v.setSfFollowPublicData(JsonUtil.toJson(family));
                            } else if (v.getSfFollowType().equals("8")) {//肺结核
                                //GwTBFollowVisitTableEntity table = JsonUtil.fromJson(jsonAll.get("entity").toString(), GwTBFollowVisitTableEntity.class);
                                AppTBFollowVisitTable po = JsonUtil.fromJson(jsonAll.get("entity").toString(), AppTBFollowVisitTable.class);
//                                                        if(table!=null){
//                                                            CopyUtils.Copy(table, po);
                                po.setVisitId(v.getId());
                                this.getSysDao().getServiceDo().add(po);
//                                                        }
                                //CopyUtils.Copy(table, family);
//                                v.setSfFollowData(jsonAll.get("entity").toString());
//                                                        v.setSfFollowPublicData(JsonUtil.toJson(family));
                            } else if (v.getSfFollowType().equals("9")) {//精神
                                //GwMentalVisitTableEntity table = JsonUtil.fromJson(jsonAll.get("entity").toString(), GwMentalVisitTableEntity.class);
                                AppMentalVisitTable po = JsonUtil.fromJson(jsonAll.get("entity").toString(), AppMentalVisitTable.class);
                                po.setVisitId(v.getId());
                                this.getSysDao().getServiceDo().add(po);
//                                v.setSfFollowData(jsonAll.get("entity").toString());
                            }

                            JSONObject jsonConclusion = JSONObject.fromObject(  jsonAll.get("entity").toString());
//                            if(jsonConclusion.get("conclusion") != null){
//                                v.setSfFollowResult(jsonConclusion.get("conclusion").toString());
//                            }
                            v.setSfFollowState("1");
                            JSONObject jsonAllEntity = JSONObject.fromObject(jsonAll.get("entity").toString());
                            if(jsonAllEntity.get("sfY") != null){
                                v.setSfYaxis(jsonAllEntity.get("sfY").toString());//纵坐标
                            }
                            if(jsonAllEntity.get("sfX") != null){
                                v.setSfXaxis(jsonAllEntity.get("sfX").toString());//横坐标
                            }


                            this.getSysDao().getServiceDo().modify(v);
                            List<AppWorkingPlan> plans = this.sysDao.getServiceDo().loadByPk(AppWorkingPlan.class,"planForeignId",v.getId());
                            if(plans!=null && plans.size()>0){
                                AppWorkingPlan plan=plans.get(0);
                                plan.setPlanFinishDate(Calendar.getInstance());
                                plan.setPlanState(CommonWorkPlanState.YWC.getValue());
                                this.sysDao.getServiceDo().modify(plan);
                                AppWorkingPlan pl= new AppWorkingPlan();
                                pl.setPlanDate(Calendar.getInstance());
                                pl.setPlanFinishDate(Calendar.getInstance());
                                pl.setPlanHospId(plan.getPlanHospId());
                                pl.setPlanAreaCode(plan.getPlanAreaCode());
                                pl.setPlanTeamId(plan.getPlanTeamId());
                                pl.setPlanState(CommonWorkPlanState.YWC.getValue());
                                pl.setPlanType(CommonWorkPlanType.GY.getValue());
                                pl.setPlanDrId(v.getSfFollowDoctorid());
                                pl.setPlanForeignId(v.getSfFollowPatientid());
                                this.sysDao.getServiceDo().add(pl);
                                sysDao.getAppNoticeDao().addNotices("健康指导消息提醒",
                                        "您好，"+pl.getDrName()+"医生在"+ ExtendDate.getChineseYMD(Calendar.getInstance())+"给您发了一条健康指导，请注意查看。", NoticesType.JKZD.getValue()+","+"2",pl.getPlanDrId(),pl.getPlanForeignId(),pl.getId(), DrPatientType.PATIENT.getValue());

                            }
                            this.getAjson().setMsg("随访成功");
                            //随访成功保存消息(系统消息)

                        } else {
                            this.getAjson().setMsg("entity数据不能为空!");
                            this.getAjson().setMsgCode("900");
                        }
                    } else {
                        this.getAjson().setMsg("查无信息!");
                        this.getAjson().setMsgCode("900");
                    }
                } else {
                    this.getAjson().setMsg("id不能为空!");
                    this.getAjson().setMsgCode("900");
                }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 随访人员列表
     * @param teamId 团队id
     * @param value 6 高血压
     * @param patientName 患者姓名
     * @return
     */
    public String sfpeopleList(){
        try{
            AppPatientQvo qvo = (AppPatientQvo)getAppJson(AppPatientQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppPatientEntity> ls = this.sysDao.getAppFollowPlanDao().findList(qvo);
                this.getAjson().setQvo(qvo);
                this.getAjson().setRows(ls);
                this.getAjson().setMsgCode("800");
            }
        }catch(Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 随访计划初始化接口
     * @return
     */
    public String followWay(){
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            //随访方式
            List<CdCode> way = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FOLLOWWAY, CommonEnable.QIYONG.getValue());
            //服务人群
            List<CdCode> serveGroups = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_GXYTNB, CommonEnable.QIYONG.getValue());
            map.put("followWay",way);
            map.put("serveGroups",serveGroups);
            this.getAjson().setMap(map);
            this.getAjson().setMsgCode("800");
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 随访计划查询
     *
     * @return
     */
    public String findFollowPlanList() {
        try {
            AppFollowPlanQvo qvo = (AppFollowPlanQvo)this.getAppJson(AppFollowPlanQvo.class);
            if(qvo != null){
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setPlanDoctorId(drUser.getId());
                    List<AppFollowPlan> lsPlan = this.getSysDao().getAppFollowPlanDao().findPlanYearList(qvo);
                    this.getAjson().setRows(lsPlan);
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

}
