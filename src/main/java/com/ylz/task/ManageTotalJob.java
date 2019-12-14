package com.ylz.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppExerciseCountNew;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppTcmSyndrome;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppTcmDtEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppTcmRecordEntity;
import com.ylz.bizDo.jtapp.commonVo.AppTcmBsQvo;
import com.ylz.bizDo.jtapp.commonVo.AppTcmRecordQvo;
import com.ylz.bizDo.jtapp.commonVo.AppTcmYbQvo;
import com.ylz.bizDo.jtapp.commonVo.AppTcmZdQvo;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamExerciseEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamManagEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.CommTcmTz;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.ExtendDateUtil;
import com.ylz.packcommon.common.util.PropertiesUtil;
/**
 * 管理端统计调度
 */
public class ManageTotalJob {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(ManageTotalJob.class);
    @Autowired
    public SysDao sysDao=(SysDao) SpringHelper.getBean("sysDao");


    public void appManageTotalCount(){
        try {
            String state = PropertiesUtil.getConfValue("manageState");
            List<String> ls = new ArrayList<String>();
            if(state.equals(CommonEnable.QIYONG.getValue()) ){
                Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
                //ca.setTime(new Date()); // 设置时间为当前时间
                ca.add(Calendar.YEAR, -1); // 年份减1
//                String startDate = ExtendDate.getYMD(ca);
                String startDate = "2018-01-01";
                String endDate = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            }else{
                String date = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(date,date);
            }
//            String delSql = "DELETE FROM app_manage_count";
//            sysDao.getServiceDo().deleteSql(delSql);
//            String startDate = "2017-01-01";
//            String endDate = ExtendDate.getYMD(Calendar.getInstance());
//            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
//            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findByManageCont();
            //获取配置的城市地区，判断是否是省内还是其他省 调度工程要加配置cityJdCode(城市编码前四位 例:cityJdCode=3501(代表福州市))
            String cityCode = PropertiesUtil.getConfValue("cityJdCode");
            boolean flag = false;
            if(StringUtils.isNotBlank(cityCode)){//目前只有判断福建省和山西省就行
                if("14".equals(cityCode.substring(0,2))){//山西省
                    flag = true;
                }
            }
            String[] areaCode = null;
            if(flag){
                areaCode = new String[]{AddressType.GPS.getValue()};
            }else{
                areaCode = new String[]{AddressType.FZS.getValue(),AddressType.SMS.getValue(),AddressType.NPS.getValue(),
                        AddressType.PTS.getValue(),AddressType.ZZS.getValue(),AddressType.QZS.getValue()};
            }
            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findManageContByAreaCode(areaCode);
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().totalManageCount(s,lsTeam);
                }
            }
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }


    public void appExerciseTotalCount(){
        try {
            String state = PropertiesUtil.getConfValue("manageState");
            List<String> ls = new ArrayList<String>();
            if(state.equals(CommonEnable.QIYONG.getValue())){
                Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
                //ca.setTime(new Date()); // 设置时间为当前时间
                ca.add(Calendar.YEAR, -1); // 年份减1
                String startDate = ExtendDate.getYMD(ca);
                String endDate = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            }else{
                String date = ExtendDate.getYYYY(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenYear(date,date);
            }
            List<AppTeamExerciseEntity> lsTeam = sysDao.getAppTeamDao().findByExerciseCount();
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().totalExerciseCount(s,lsTeam);
                }
            }
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    /**
     * 排行履约统计
     */
    public void performanceRankingsCount(){
        try {
            String state = PropertiesUtil.getConfValue("manageState");
            List<String> ls = new ArrayList<String>();
//            if(state.equals(CommonEnable.QIYONG.getValue())){
//                String startDate = "2016-01-01";
//                String endDate = ExtendDate.getYMD(Calendar.getInstance());
//                ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
//            }else{
//                String date = ExtendDate.getYMD(Calendar.getInstance());
//                ls = ExtendDateUtil.getListBetweenMonth(date,date);
//            }
            String startDate = "2017-01-01";
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findByManageCont();
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().totalPerformanceCount(s,lsTeam);
                }
            }
        }catch (Exception e){
            new ActionException(this.getClass(), e.getMessage());
        }

    }

    /**
     * 根据区域调度
     */
    public void appNewManageTotalCount(){
        try {
            String state = PropertiesUtil.getConfValue("manageStates");
            List<String> ls = new ArrayList<String>();
          /*  if(state.equals(CommonEnable.QIYONG.getValue())){
                String startDate = "2016-01-01";
                String endDate = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            }else{
                String date = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(date,date);
            }*/
            String startDate = "2017-01-01";
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            String[] areaCode = new String[]{AddressType.XMS.getValue(),AddressType.NDS.getValue(),
                    AddressType.LYS.getValue(),AddressType.PTZHSYQ.getValue()};
            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findManageContByAreaCode(areaCode);
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().totalManageCount(s,lsTeam);
                }
            }
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    /**
     * 健康立卡统计
     */
    public void appManageArchivingTotalCount(){
        try {
            String state = PropertiesUtil.getConfValue("manageState");
            List<String> ls = new ArrayList<String>();
           /* if(state.equals(CommonEnable.QIYONG.getValue()) ){
                String startDate = "2017-01-01";
                String endDate = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            }else{
                String date = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(date,date);
            }*/
            String delSql = " DELETE FROM app_manage_archiving_count ";
            sysDao.getServiceDo().deleteSql(delSql);

            String startDate = "2017-01-01";
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            Map<String,Object> map = new HashMap<>();
            String areaCode = PropertiesUtil.getConfValue("cityJdCode");
            CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, areaCode);
            map.put("SOURCE_TYPE","1");
            String sourceType = "1";
            if(cityCode != null){
                if("0".equals(cityCode.getCodeTitle())){//查询医保数据
                    map.put("SOURCE_TYPE","3");
                    sourceType = "3";
                }
            }
            String sql = " SELECT " +
                    "a.ADDR_RURAL_CODE jdAreaCode," +
                    "a.TEAM_ID teamId," +
                    "(SELECT TEAM_NAME FROM APP_TEAM WHERE ID = a.TEAM_ID) teamName," +
                    "a.HOSP_ID hospId," +
                    "a.ADDR_HOSP_ID addrHospId," +
                    "(SELECT HOSP_AREA_CODE FROM APP_HOSP_DEPT WHERE ID = a.HOSP_ID) areaCode " +
                    " FROM app_archivingcard_people a " +
                    "where a.ADDR_RURAL_CODE IS NOT NULL AND a.TEAM_ID IS NOT NULL AND SOURCE_TYPE =:SOURCE_TYPE  GROUP BY a.ADDR_RURAL_CODE,a.TEAM_ID,a.ADDR_HOSP_ID ";

            List<AppTeamManagEntity> lsTeam = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamManagEntity.class);
            if(ls != null && ls.size()>0){
                for(String ss:ls){
                    sysDao.getSecurityCardAsyncBean().totalManageArchivingCount(ss,lsTeam,sourceType);
                }
            }
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    /**
     * 建档立卡各未签原因调度统计
     */
    public void appManageArchivingPeopelTotalCount(){
        try {
            String state = PropertiesUtil.getConfValue("manageState");
            List<String> ls = new ArrayList<String>();
            /*if(state.equals(CommonEnable.QIYONG.getValue()) ){
                String startDate = "2017-01-01";
                String endDate = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            }else{
                String date = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(date,date);
            }*/
            String delSql = " DELETE FROM app_manage_archiving_people ";
            sysDao.getServiceDo().deleteSql(delSql);
            String areaCode = PropertiesUtil.getConfValue("cityJdCode");
            CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, areaCode);
            String sourceType = "1";
            if(cityCode != null){
                if("0".equals(cityCode.getCodeTitle())){//查询医保数据
                    sourceType = "3";
                }
            }
            String startDate = "2017-01-01";
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            Map<String,Object> map = new HashMap<>();
            map.put("SOURCE_TYPE",sourceType);
            String sql = " SELECT ADDR_RURAL_CODE areaCode," +
                    " ADDR_HOSP_ID hospId" +
                    " FROM app_archivingcard_people where ADDR_RURAL_CODE IS NOT NULL AND SOURCE_TYPE =:SOURCE_TYPE GROUP BY ADDR_RURAL_CODE,ADDR_HOSP_ID";
            List<AppTeamManagEntity> lsTeam = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamManagEntity.class);
           /* List<Map> lsMap = sysDao.getServiceDo().findSql(sql);
            List<String> lsString = new ArrayList<String>();
            if(lsMap != null && lsMap.size() >0){
                for(Map maps : lsMap){
                    if(maps.get("ADDR_RURAL_CODE") != null){
                        lsString.add(maps.get("ADDR_RURAL_CODE").toString());
                    }
                }
            }*/

            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().totalManageArchivingCountPeople(s,lsTeam,sourceType);
                }
            }
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    /**
     * 建档立卡（全部）
     */
    public void appManageArchivingAllTotalCount(){
        try {
            String state = PropertiesUtil.getConfValue("manageState");
            List<String> ls = new ArrayList<String>();
            /*if(state.equals(CommonEnable.QIYONG.getValue()) ){
                String startDate = "2017-01-01";
                String endDate = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            }else{
                String date = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(date,date);
            }*/
            String delSql = " DELETE FROM app_manage_archiving_all_count ";
            sysDao.getServiceDo().deleteSql(delSql);

            String startDate = "2017-01-01";
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
//            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findByManageCont();
//            String[] areaCode = new String[]{AddressType.FZS.getValue(),AddressType.SMS.getValue(),AddressType.NPS.getValue(),
//                    AddressType.PTS.getValue(),AddressType.ZZS.getValue(),AddressType.QZS.getValue()};
            String areaCode = PropertiesUtil.getConfValue("cityJdCode");
            CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, areaCode);
            String sourceType = "1";
            if(cityCode != null){
                if("0".equals(cityCode.getCodeTitle())){//查询医保数据
                    sourceType = "3";
                }
            }
            Map<String,Object> map = new HashMap<>();
            map.put("SOURCE_TYPE",sourceType);
            String sql = " SELECT ADDR_RURAL_CODE areaCode,ADDR_HOSP_ID hospId FROM app_archivingcard_people " +
                    "where ADDR_RURAL_CODE IS NOT NULL AND SOURCE_TYPE =:SOURCE_TYPE GROUP BY ADDR_RURAL_CODE,ADDR_HOSP_ID ";
            List<AppTeamManagEntity> lsTeam = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamManagEntity.class);

           /* List<Map> lsMap = sysDao.getServiceDo().findSql(sql);
            List<String> lsString = new ArrayList<String>();
            if(lsMap != null && lsMap.size() >0){
                for(Map maps : lsMap){
                    if(maps.get("ADDR_RURAL_CODE") != null){
                        lsString.add(maps.get("ADDR_RURAL_CODE").toString());
                    }
                }
            }*/

            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().totalManageArchivingAllCount(s,lsTeam,sourceType);
                }
            }
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    /**
     * 上传中医体质辨识记录到基卫
     */
    public void uploadTcmToBasic(){
        try {
            System.out.println("开始");
            List<AppTcmRecordEntity> list = sysDao.getAppTcmSyndromeDao().findListByState();
            if(list != null && list.size()>0){
                for(AppTcmRecordEntity ll:list){
                    AppTcmSyndrome table = (AppTcmSyndrome)sysDao.getServiceDo().find(AppTcmSyndrome.class,ll.getJlId());
                    if(table != null){
                        AppTcmRecordQvo qvo = new AppTcmRecordQvo();
                        AppTcmYbQvo ybqk = new AppTcmYbQvo();
                        AppTcmBsQvo bsjl = new AppTcmBsQvo();
                        List<AppTcmZdQvo> listZd = new ArrayList<>();
                        String code= "";
                        String drId = "";
                        String hospId = "";
                        if(StringUtils.isNotBlank(ll.getDoctor())){
                            AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,ll.getDoctor());
                            if(drUser != null){
                                drId = drUser.getId();
                                if(StringUtils.isNotBlank(drUser.getDrHospId())){
                                    AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                    if(dept != null){
                                        hospId = dept.getId();
                                        String codee = AreaUtils.getAreaCode(dept.getHospAreaCode(),"2");
                                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,codee);
                                        if(value != null){
                                            code = value.getCodeTitle();
                                        }
                                    }
                                }
                            }
                            //判断医生和机构
                            if(StringUtils.isNotBlank(code)){
                                String result = "";
                                if (code.equals(AddressType.FZ.getValue())) {

                                } else if (code.equals(AddressType.QZ.getValue())) {
                                    result = "qz_";
                                } else if (code.equals(AddressType.ZZ.getValue())) {
                                    result = "zz_";
                                } else if (code.equals(AddressType.PT.getValue())) {
                                    result = "pt_";
                                } else if (code.equals(AddressType.NP.getValue())) {
                                    result = "np_";
                                } else if (code.equals(AddressType.SM.getValue())) {
                                    result = "sm_";
                                }
                                drId = drId.replace(result,"");
                                hospId = hospId.replace(result,"");
                            }
                        }
                        String jgj = "";
                        if(StringUtils.isNotBlank(ll.getDtjg())){
                            String[] jgs = ll.getDtjg().split(";");
                            for(String num:jgs){
                                String numm = "";
                                if("A".equals(num)){
                                    numm = "1";
                                }else if("B".equals(num)){
                                    numm = "2";
                                }else if("C".equals(num)){
                                    numm = "3";
                                }else{
                                    numm = "4";
                                }
                                if(StringUtils.isBlank(jgj)){
                                    jgj = numm;
                                }else {
                                    jgj += ";"+numm;
                                }
                            }
                        }
                        //一般情况
//                        ll.setDf_id("35012310045400030");
//                        String dfId = "35012310045400030";
//                        hospId = "6932";
//                        drId = "8512";
                        ybqk.setDf_id(ll.getDf_id());
                        ybqk.setDoctor(drId);
                        ybqk.setEdate(ll.getEdate());
                        ybqk.setYyid00(hospId);
                        //体质得分
                        bsjl.setBsxxjg(jgj);
                        bsjl.setDf_id(ll.getDf_id());
                        bsjl.setYyid00(hospId);
                        bsjl.setZdid00(drId);
                        bsjl.setZhbjrq(ll.getEdate());
//                    bsjl.setTzbs_id(ll.getJlId());
                        if(ll.getTzjl()!=null){
                            AppTcmZdQvo zdtable = null;
                            for (AppTcmDtEntity ls:ll.getTzjl()){
                                if("1".equals(ls.getTcmrResultValue()) && zdtable == null){
                                    zdtable = new AppTcmZdQvo();
                                    zdtable.setQzts00(ls.getTcmrModernCultivate());
                                    zdtable.setYsty00(ls.getTcmrFoodNursing());
                                    zdtable.setQjts00(ls.getTcmrDailyLifeCultivate());
                                    zdtable.setYdbj00(ls.getTcmrSportsHealthCare());
                                    zdtable.setXwbj00(ls.getTcmrMeridianHealth());
                                    zdtable.setTzlx00(ls.getTcmrResultType());
                                    zdtable.setDf_id(ll.getDf_id());
                                    zdtable.setYyid00(hospId);
                                    CdCode cc = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TZLX,ls.getTcmrResultType());
                                    if(cc != null){
                                        zdtable.setTzbm00(cc.getCodeTitle());
                                    }
                                    if(CommTcmTz.QXZ.getValue().equals(ls.getTcmrResultType())){//气虚质
                                        bsjl.setQxzscore(ls.getTcmrScore());
                                        bsjl.setQxzqt0(ls.getTcmrOther());
                                    }else if(CommTcmTz.YANGXZ.getValue().equals(ls.getTcmrResultType())){//阳虚质
                                        bsjl.setYxzscore(ls.getTcmrScore());
                                        bsjl.setYxzqt0(ls.getTcmrOther());
                                    }else if(CommTcmTz.YINXZ.getValue().equals(ls.getTcmrResultType())){//阴虚质
                                        bsjl.setYxzscore(ls.getTcmrScore());
                                        bsjl.setYxzqt0(ls.getTcmrOther());
                                    }else if(CommTcmTz.TSZ.getValue().equals(ls.getTcmrResultType())){//痰湿质
                                        bsjl.setTszscore(ls.getTcmrScore());
                                        bsjl.setTszqt0(ls.getTcmrOther());
                                    }else if(CommTcmTz.SRZ.getValue().equals(ls.getTcmrResultType())){//湿热质
                                        bsjl.setSrzscore(ls.getTcmrScore());
                                        bsjl.setSrzqt0(ls.getTcmrOther());
                                    }else if(CommTcmTz.XYZ.getValue().equals(ls.getTcmrResultType())){//血瘀质
                                        bsjl.setXyzscore(ls.getTcmrScore());
                                        bsjl.setXyzqt0(ls.getTcmrOther());
                                    }else if(CommTcmTz.QYZ.getValue().equals(ls.getTcmrResultType())){//气郁质
                                        bsjl.setQyzscore(ls.getTcmrScore());
                                        bsjl.setQyzqt0(ls.getTcmrOther());
                                    }else if(CommTcmTz.TBZ.getValue().equals(ls.getTcmrResultType())){//特禀质
                                        bsjl.setTbzscore(ls.getTcmrScore());
                                        bsjl.setTbzqt0(ls.getTcmrOther());
                                    }else if(CommTcmTz.PHZ.getValue().equals(ls.getTcmrResultType())){//平和质
                                        bsjl.setPhzscore(ls.getTcmrScore());
                                        bsjl.setPhzqt0(ls.getTcmrOther());
                                    }
                                    listZd.add(zdtable);
                                }
                            }
                        }
                        qvo.setUrlType(code);
                        qvo.setBsjl(bsjl);
                        qvo.setYbqk(ybqk);
                        qvo.setJlvo(listZd);
                        if(StringUtils.isNotBlank(ll.getDf_id())){//档案号不为空
                            boolean flag = sysDao.getSecurityCardAsyncBean().uploadTcmToBasic(qvo);
                            if(flag){
                                table.setUploadState("1");
                                sysDao.getServiceDo().modify(table);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    /**
     * 尤溪慢病统计
     */
    public void appManageChronicDiseaseCount(){
        try {
            String state = PropertiesUtil.getConfValue("manageState");
            List<String> ls = new ArrayList<String>();
//            if(state.equals(CommonEnable.QIYONG.getValue()) ){
//                String startDate = "2016-01-01";
//                String endDate = ExtendDate.getYMD(Calendar.getInstance());
//                ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
//            }else{
//                String date = ExtendDate.getYMD(Calendar.getInstance());
//                ls = ExtendDateUtil.getListBetweenMonth(date,date);
//            }
            String delSql = " DELETE FROM APP_MANAGE_CHRONIC_DISEASE ";
            sysDao.getServiceDo().deleteSql(delSql);
            String startDate = "2017-01-01";
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            String areaCode = "350426000000";
            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findManageNCD(null);
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().totalManageChronicCount(s,lsTeam);
                }
            }
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }
    
    /**
	 * 新履约统计调度
	 * 
	 * @author lyy
	 */
	public void appExerciseNewTotalCount() {
		try {
			logger.info("====== 开始新履约统计调度 " + ExtendDate.getYMD_h_m_s(Calendar.getInstance()) + " ======");
			List<String> yearList = new ArrayList<String>();
			String startDate = "2016";
			String endDate = ExtendDate.getYYYY(Calendar.getInstance());
			yearList = ExtendDateUtil.getListBetweenYear(startDate, endDate);
			// 查询最新数据
			for (String year : yearList) {

				// 清除表数据
				logger.info("开始删除" + year + "年数据");
				sysDao.getServiceDo().deleteSql("delete from app_exercise_count_new where SIGN_YEAR = '" + year + "'");
				logger.info("结束删除" + year + "年数据");

				int yearInt = Integer.valueOf(year);

				// 循环查询1个月的数据
				for (int i = 1; i <= 12; i++) {
					logger.info(year + "年第" + i + "月查询开始");
					List<Map> newDataMapList = sysDao.getAppExerciseCountNewDao().dispatchExerciseCount(yearInt, i);
					if (newDataMapList != null && newDataMapList.size() > 0) {
						logger.info(year + "年第" + i + "月查询数据结束，共有数据" + newDataMapList.size() + "条");
						logger.info(year + "年第" + i + "月插入开始");
						for (Map newDataMap : newDataMapList) {
							if (StringUtils.isNotBlank((String) newDataMap.get("SIGN_PACKAGEID"))) {
								AppExerciseCountNew entity = new AppExerciseCountNew();
								entity.setSignPackageId((String) newDataMap.get("SIGN_PACKAGEID"));
								entity.setLabelValue((String) newDataMap.get("LABEL_VALUE"));
								entity.setSignNumber(newDataMap.get("SIGN_NUMBER").toString());
								entity.setSignAreaCode((String) newDataMap.get("SIGN_AREA_CODE"));
								entity.setSignYear(year);
								entity.setSignDrId((String) newDataMap.get("SIGN_DR_ID"));
								sysDao.getServiceDo().add(entity);
							}
						}
						logger.info(year + "年第" + i + "月插入结束");
					} else {
						logger.info(year + "年第" + i + "月查询数据结束，共有数据0条");
					}
				}
			}
			logger.info("====== 结束新履约统计调度 " + ExtendDate.getYMD_h_m_s(Calendar.getInstance()) + " ======");
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
		}
	}

	/**
	 * 新履约统计调度（今年）
	 * 
	 * @author lyy
	 */
	public void appExerciseNewTotalCountThisYear() {
		try {
			//			logger.info("====== 开始新履约统计调度 " + ExtendDate.getYMD_h_m_s(Calendar.getInstance()) + " （今年）======");
			//			String year = ExtendDate.getYYYY(Calendar.getInstance());
			//			int yearInt = Integer.valueOf(year);
			//
			//			// 清除表数据
			//			logger.info("开始删除" + year + "年数据");
			//			sysDao.getServiceDo().deleteSql("delete from app_exercise_count_new where SIGN_YEAR = '" + year + "'");
			//			logger.info("结束删除" + year + "年数据");
			//
			//
			//			// 循环查询1个月的数据
			//			for (int i = 1; i <= 12; i++) {
			//				logger.info(year + "年第" + i + "月查询开始");
			//				List<Map> newDataMapList = sysDao.getAppExerciseCountNewDao().dispatchExerciseCount(yearInt, i);
			//				if (newDataMapList != null && newDataMapList.size() > 0) {
			//					logger.info(year + "年第" + i + "月查询数据结束，共有数据" + newDataMapList.size() + "条");
			//					logger.info(year + "年第" + i + "月插入开始");
			//					for (Map newDataMap : newDataMapList) {
			//						if (StringUtils.isNotBlank((String) newDataMap.get("SIGN_PACKAGEID"))) {
			//							AppExerciseCountNew entity = new AppExerciseCountNew();
			//							entity.setSignPackageId((String) newDataMap.get("SIGN_PACKAGEID"));
			//							entity.setLabelValue((String) newDataMap.get("LABEL_VALUE"));
			//							entity.setSignNumber(newDataMap.get("SIGN_NUMBER").toString());
			//							entity.setSignAreaCode((String) newDataMap.get("SIGN_AREA_CODE"));
			//							entity.setSignYear(year);
			//							entity.setSignDrId((String) newDataMap.get("SIGN_DR_ID"));
			//							sysDao.getServiceDo().add(entity);
			//						}
			//					}
			//					logger.info(year + "年第" + i + "月插入结束");
			//				} else {
			//					logger.info(year + "年第" + i + "月查询数据结束，共有数据0条");
			//				}
			//			}
			//
			//			logger.info("====== 结束新履约统计调度 " + ExtendDate.getYMD_h_m_s(Calendar.getInstance()) + " （今年）======");
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
		}
	}

    /**
     * 统计签约团队、团队成员、签约医生数据
     */
	public void manageTeam(){
        try {
//            String state = PropertiesUtil.getConfValue("manageState");
            List<String> ls = new ArrayList<String>();
            String startDate = "2017-01-01";
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    String[] areaCode = new String[]{AddressType.FZS.getValue(),AddressType.SMS.getValue(),AddressType.NPS.getValue(),
                            AddressType.PTS.getValue(),AddressType.ZZS.getValue(),AddressType.QZS.getValue()};
                    List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findManageContByAreaCodeAndTime(areaCode,s);
                    sysDao.getSecurityCardAsyncBean().totalManageTeamCount(s,lsTeam);
                }
            }
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    /**
     * 统计随访、健康指导、健康教育、求助、未缴费数量
     */
    public void appManageOtherCount(){
        try {
            List<String> ls = new ArrayList<String>();
            String startDate = ExtendDate.getYMD(Calendar.getInstance());
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            String delSql = "DELETE FROM APP_MANAGE_OTHER_COUNT";
            sysDao.getServiceDo().deleteSql(delSql);
            //获取配置的城市地区，判断是否是省内还是其他省 调度工程要加配置cityJdCode(城市编码前四位 例:cityJdCode=3501(代表福州市))
            String cityCode = PropertiesUtil.getConfValue("cityJdCode");
            boolean flag = false;
            if(StringUtils.isNotBlank(cityCode)){//目前只有判断福建省和山西省就行
                if("14".equals(cityCode.substring(0,2))){//山西省
                    flag = true;
                }
            }
            String[] areaCode = null;
            if(flag){
                areaCode = new String[]{AddressType.GPS.getValue()};
            }else{
                areaCode = new String[]{AddressType.FZS.getValue(),AddressType.SMS.getValue(),AddressType.NPS.getValue(),
                        AddressType.PTS.getValue(),AddressType.ZZS.getValue(),AddressType.QZS.getValue()};
            }
            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findManageContByAreaCode(areaCode);
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().totalManageOtherCount(s,lsTeam);
                }
            }
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    /**
     * 排名统计
     */
    public void manageRanking(){
        try{
            List<String> ls = new ArrayList<String>();
//            String startDate = ExtendDate.getYMD(Calendar.getInstance());
            String startDate = "2017-01-01";
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
//            String delSql = "DELETE FROM APP_MANAGE_OTHER_COUNT";
//            sysDao.getServiceDo().deleteSql(delSql);
            //获取配置的城市地区，判断是否是省内还是其他省 调度工程要加配置cityJdCode(城市编码前四位 例:cityJdCode=3501(代表福州市))
            String cityCode = PropertiesUtil.getConfValue("cityJdCode");
            boolean flag = false;
            if(StringUtils.isNotBlank(cityCode)){//目前只有判断福建省和山西省就行
                if("14".equals(cityCode.substring(0,2))){//山西省
                    flag = true;
                }
            }
            String[] areaCode = null;
            if(flag){
                areaCode = new String[]{AddressType.GPS.getValue()};
            }else{
                areaCode = new String[]{AddressType.FZS.getValue(),AddressType.SMS.getValue(),AddressType.NPS.getValue(),
                        AddressType.PTS.getValue(),AddressType.ZZS.getValue(),AddressType.QZS.getValue()};
            }
            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findManageContByAreaCode(areaCode);
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().totalManageRankCount(s,lsTeam);
                }
            }
        }catch (Exception e){
            new ActionException(this.getClass(),e.getMessage());
        }
    }
}
