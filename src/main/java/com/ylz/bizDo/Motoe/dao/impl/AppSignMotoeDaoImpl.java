package com.ylz.bizDo.Motoe.dao.impl;

import com.ylz.bizDo.Motoe.dao.AppSignMotoeDao;
import com.ylz.bizDo.Motoe.dao.vo.MotoeVo;
import com.ylz.bizDo.Motoe.dao.vo.SignsurrenderVo;
import com.ylz.bizDo.app.entity.AppWebSignFormListEntity;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppGoToEntity;
import com.ylz.bizDo.mangecount.dao.AppSignAnalysisDao;
import com.ylz.bizDo.mangecount.entity.ManageCountEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 签约分析
 */
@Service("appSignMotoeDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSignMotoeDaoImpl implements AppSignMotoeDao {

    @Autowired
    private SysDao sysDao;





    /**
     * 签约量统计（省，市,社区）
     * @return
     */
    public Map<String,Object> getSianCount(CdAddress address,AppHospDept hosp,String startDate,String endDate) throws Exception{
        Calendar calendar = Calendar.getInstance();
            int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
        String[] year = endDate.split("-");
        int endYear = Integer.parseInt(year[0]);
        int endMonth = Integer.parseInt(year[1]);
        int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));
        int areaReg = 0;//户籍人数
        int areaRegTarget = 0;//户籍目标数
        int areaFoucs = 0;//重点人群数
        int areaFoucsTarget = 0;//重点人群目标数
        int areaEconomic = 0;//经济人口性质人口数
        int areaEconomicTarget = 0;//经济人口性质目标数


        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        String addressCtCode = address.getCtcode();
        //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
        if("350698000000".equals(address.getCtcode())){//台商投资区
            addressCtCode = "350681102000";
        }else if("350699000000".equals(address.getCtcode())){//招商局漳州开发区
            addressCtCode = "350681501000";
        }else if("350697000000".equals(address.getCtcode())){//常山农场经济开发区
            addressCtCode = "350622450000";
        }
        List<CdAddressPeople> lsPeople = this.sysDao.getCdAddressPeopleDao().findByCacheList(addressCtCode,year[0]);
        if(lsPeople != null && lsPeople.size() >0){
            areaReg = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaPopulation()) ? lsPeople.get(0).getAreaPopulation():"0");
            areaRegTarget = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaTarget()) ? lsPeople.get(0).getAreaTarget():"0");
            areaFoucs = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaFocus()) ? lsPeople.get(0).getAreaFocus():"0");
            areaFoucsTarget = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaFocusTarget()) ? lsPeople.get(0).getAreaFocusTarget():"0");
            areaEconomic = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaEconomic()) ? lsPeople.get(0).getAreaEconomic():"0");
            areaEconomicTarget = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaEconomicTarget()) ? lsPeople.get(0).getAreaEconomicTarget():"0");
        }
        int yqy = 0;
        int zdrqyqy = 0;
        int renew = 0;
        int totalRenew = 0;
        int economic = 0;//人口性质签约人数

//        map.put("STARTDATE", ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
//        map.put("ENDDATE",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
//        String sqlDate = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE ";
//        String sqlYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE = :SIGN_STATE "+sqlDate;
//        String sqlZdrqqy = "SELECT count(1) FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID where 1=1 and b.LABEL_TYPE='3' AND t.SIGN_STATE = :SIGN_STATE AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) "+sqlDate;
//        String state = SignFormType.YQY.getValue();//状态
//        String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
//        map.put("SIGN_STATE",state);
//        map.put("SIGN_PERS_GROUP",fwState);
//        if(hosp != null){
//            map.put("HOSP_ID",hosp.getId());
//            sqlYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
//            yqy = this.sysDao.getServiceDo().findCount(sqlYqy,map);//已签约
//            sqlZdrqqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
//            zdrqyqy = this.sysDao.getServiceDo().findCount(sqlZdrqqy,map);//重點人群已簽約
//        }else {
//            map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode())+"%");
//            sqlYqy += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
//            yqy = this.sysDao.getServiceDo().findCount(sqlYqy,map);//已签约
//            sqlZdrqqy += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
//            zdrqyqy = this.sysDao.getServiceDo().findCount(sqlZdrqqy,map);//重點人群已簽約
//        }
        map.put("STARTDATE",startDate);
        map.put("ENDDATE",endDate);
        String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
        String sql = "SELECT SUM(t.MANAGE_SIGN_COUNT) signCount,SUM(t.MANAGE_KEY_SIGN_COUNT) keySingCount,SUM(t.MANAGE_RENEW) renew," +
                "SUM(t.MANAGE_LOW_FAMILY_COUNT) manageLowFamilyCount," +
                "SUM(t.MANAGE_DESTITUTE_FAMILY_COUNT) manageDestituteFamilyCount," +
                "SUM(t.MANAGE_SPECIAL_PLAN_FAMILY_COUNT) manageSpecialPlanFamilyCount," +
                "SUM(t.MANAGE_POOR_FAMILY_COUNT) managePoorFamilyCount, " +
                "SUM(t.MANAGE_TOTAL_RENEW) totalRenew "+
                " FROM APP_MANAGE_COUNT t where 1=1 "+sqlDate;

        if(hosp != null){
            map.put("HOSP_ID",hosp.getId());
            sql += " AND t.MANAGE_HOSP_ID = :HOSP_ID ";
        }else {
            map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode())+"%");
            //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
            if("350698000000".equals(address.getCtcode())){//台商投资区
                map.put("AREA_CODE","350681102%");
            }else if("350699000000".equals(address.getCtcode())){//招商局漳州开发区
                map.put("AREA_CODE","350681501%");
            }else if("350697000000".equals(address.getCtcode())){//常山农场经济开发区
                map.put("AREA_CODE","350622450%");
            }else if("350681000000".equals(address.getCtcode())){
                map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                sql += " AND t.MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
            }else if("350622000000".equals(address.getCtcode())){
                map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                sql += " AND t.MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
            }
            sql += " AND t.MANAGE_AREA_CODE LIKE :AREA_CODE ";

        }
        List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
        if(maps != null && maps.size() >0){
            if(maps.get(0).get("signCount") != null) {
                yqy = (int)Double.parseDouble(maps.get(0).get("signCount").toString());//已签约
            }
            if(maps.get(0).get("keySingCount") != null) {
                zdrqyqy = (int)Double.parseDouble(maps.get(0).get("keySingCount").toString());//重點人群已簽約
            }
            if(maps.get(0).get("renew") !=null){
                renew = (int)Double.parseDouble(maps.get(0).get("renew").toString());//续签数
            }
            if(maps.get(0).get("manageLowFamilyCount")!=null){
                economic+=(int)Double.parseDouble(maps.get(0).get("manageLowFamilyCount").toString());//低保户
            }
            if(maps.get(0).get("manageDestituteFamilyCount")!=null){
                economic+=(int)Double.parseDouble(maps.get(0).get("manageDestituteFamilyCount").toString());//特困户（五保户）
            }
            if(maps.get(0).get("manageSpecialPlanFamilyCount")!=null){
                economic+=(int)Double.parseDouble(maps.get(0).get("manageSpecialPlanFamilyCount").toString());//计生特殊家庭
            }
            if(maps.get(0).get("managePoorFamilyCount")!=null){
                economic+=(int)Double.parseDouble(maps.get(0).get("managePoorFamilyCount").toString());//建档立卡贫困人口
            }
            if(maps.get(0).get("totalRenew")!=null){
                totalRenew = (int)Double.parseDouble(maps.get(0).get("totalRenew").toString());//总签约数
            }
        }

        //如果结束时间大于等于当前时间
        if(end >= now){
//            List<CdCode> ls = this.sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_JJLX, CommonEnable.QIYONG.getValue());
            List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType(LabelManageType.JJLX.getValue());
            map.put("STARTDATE", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            sqlDate = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE ";
            //签约数
            String sqlYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) "+sqlDate;
            //重点人群数
            String sqlZdrqqy = "SELECT t.* FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) AND b.LABEL_TYPE='3'  "+sqlDate;
            //经济类型数
            String sqlJjType = "SELECT COUNT(1) FROM APP_SIGN_FORM t  LEFT JOIN APP_LABEL_ECONOMICS b ON t.ID = b.LABEL_SIGN_ID WHERE 1=1 AND  t.SIGN_STATE IN (:SIGN_STATE) "+sqlDate;
            //续签数
            String sqlRenew = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_GOTO_SIGN_STATE = :SIGN_GOTO_SIGN_STATE "+sqlDate;
            String gotoSignState = SignFormType.YQY.getValue();//状态
            String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
            String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
            String[] jjState = {EconomicType.YBRK.getValue(),EconomicType.JDLKPKRK.getValue(),EconomicType.DBH.getValue(),EconomicType.TKH.getValue(),EconomicType.JSTSJT.getValue()};
            map.put("SIGN_GOTO_SIGN_STATE","2");
            map.put("SIGN_STATE",state);
            map.put("SIGN_PERS_GROUP",fwState);
            map.put("SIGN_JJ_TYPE",jjState);
            if(hosp != null){
                map.put("HOSP_ID",hosp.getId());
                sqlYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
                yqy += this.sysDao.getServiceDo().findCount(sqlYqy,map);//已签约
                sqlZdrqqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
                String sqlZdrq = "SELECT COUNT(1) FROM ( "+sqlZdrqqy +" GROUP BY t.SIGN_PATIENT_ID) c";
                zdrqyqy += this.sysDao.getServiceDo().findCount(sqlZdrq,map);//重點人群已簽約
                sqlJjType += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
                sqlRenew += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
                renew += this.sysDao.getServiceDo().findCount(sqlRenew,map);//续签
            }else {
                map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode())+"%");
                sqlYqy += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
                yqy += this.sysDao.getServiceDo().findCount(sqlYqy,map);//已签约
                sqlZdrqqy += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
                String sqlZdrq = "SELECT COUNT(1) FROM ( "+sqlZdrqqy +" GROUP BY t.SIGN_PATIENT_ID) c";
                zdrqyqy += this.sysDao.getServiceDo().findCount(sqlZdrq,map);//重點人群已簽約
                sqlJjType += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
                sqlRenew += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
                renew += this.sysDao.getServiceDo().findCount(sqlRenew,map);//续签
            }


            if(ls != null && !ls.isEmpty()){
                sqlJjType += " AND b.LABEL_VALUE =:LABEL_VALUE  AND b.LABEL_TYPE='4' ";
                for(AppLabelManage l:ls) {
                    if(l.getLabelValue().equals("2")){//建档立卡贫困人口
                        map.put("LABEL_VALUE",l.getLabelValue());
                        economic += sysDao.getServiceDo().findCount(sqlJjType, map);
                    }else if(l.getLabelValue().equals("3")){//低保户
                        map.put("LABEL_VALUE",l.getLabelValue());
                        economic += sysDao.getServiceDo().findCount(sqlJjType, map);
                    }else if(l.getLabelValue().equals("4")){//特困户（五保户）
                        map.put("LABEL_VALUE",l.getLabelValue());
                        economic += sysDao.getServiceDo().findCount(sqlJjType, map);
                    }else if(l.getLabelValue().equals("5")){//计生特殊家庭
                        map.put("LABEL_VALUE",l.getLabelValue());
                        economic += sysDao.getServiceDo().findCount(sqlJjType, map);
                    }
                }
            }
        }

        //续签率
        double xql = 0;
        if(totalRenew>0){
            xql = MyMathUtil.div(Double.valueOf(renew),Double.valueOf(totalRenew),4)*100;//续签率
        }
        //签约率
        double qywcl = 0;
        if(areaReg >0){
            qywcl = MyMathUtil.div(Double.valueOf(yqy),Double.valueOf(areaReg),4)*100;//签约率
        }
        //目标率
        double mbwcl = 0;
        if(areaRegTarget >0){
            mbwcl = MyMathUtil.div(Double.valueOf(yqy),Double.valueOf(areaRegTarget),4)*100;//目标率
        }
        //重点人群签约率
        double zdrqwcl = 0;
        if(areaFoucs >0){
            zdrqwcl = MyMathUtil.div(Double.valueOf(zdrqyqy),Double.valueOf(areaFoucs),4)*100;//重点人群签约率
        }
        double zdrqmbwcl = 0;
        if(areaFoucsTarget >0){
            zdrqmbwcl = MyMathUtil.div(Double.valueOf(zdrqyqy),Double.valueOf(areaFoucsTarget),4)*100;//重点人群目标率
        }
        //人口经济性质签约率
        double economicRate =0;
        if(areaEconomic>0){
            economicRate = MyMathUtil.div(Double.valueOf(economic),Double.valueOf(areaEconomic),4)*100;//人口性质签约率
        }
        //人口性质目标率
        double economicTargetRate = 0;
        if(areaEconomicTarget>0){
            economicTargetRate = MyMathUtil.div(Double.valueOf(economic),Double.valueOf(areaEconomicTarget),4)*100;//人口性质目标率
        }
        //未签约数
        int wqys = (int) MyMathUtil.sub(Double.valueOf(areaReg),Double.valueOf(yqy));
        if(hosp != null){
            returnMap.put("hospId",hosp.getId());//区域编码
            returnMap.put("hospName",hosp.getHospName());//名称
            CdAddress cdAddress = this.sysDao.getCdAddressDao().findByCacheCode(hosp.getHospAreaCode());
            if(cdAddress != null ) {
                returnMap.put("upWebAreaCode", cdAddress.getPid());
            }
            returnMap.put("areaCode",null);//区域编码
            returnMap.put("areaName",null);//区域名称
        }else{
            returnMap.put("areaCode",address.getCtcode());//区域编码
            returnMap.put("areaName",address.getAreaSname());//区域名称
            returnMap.put("upWebAreaCode",address.getPid());
            returnMap.put("hospId",null);//区域编码
            returnMap.put("hospName",null);//名称
        }
        returnMap.put("areaReg",areaReg);//户籍人数
        returnMap.put("areaRegTarget",areaRegTarget);//户籍目标数
        returnMap.put("yqy",yqy);//签约数
        returnMap.put("wqys",wqys);//签约数
        returnMap.put("qywcl",String.valueOf(MyMathUtil.round(qywcl,2)));//签约率
        returnMap.put("mbwcl",String.valueOf(MyMathUtil.round(mbwcl,2)));//目标率
        returnMap.put("areaFoucs",areaFoucs);//重点人群数
        returnMap.put("zdrqyqy",zdrqyqy);//重点人群签约数
        returnMap.put("areaFoucsTarget",areaFoucsTarget);//重点人群目标数
        returnMap.put("zdrqwcl",String.valueOf(MyMathUtil.round(zdrqwcl,2)));//重点人群签约率
        returnMap.put("zdrqmbwcl",String.valueOf(MyMathUtil.round(zdrqmbwcl,2)));//重点人群目标率
        returnMap.put("xql",String.valueOf(MyMathUtil.round(xql,2)));//续签率
        returnMap.put("xqs",renew);//续签数
        returnMap.put("synqys",totalRenew);//上一年签约数

        returnMap.put("economic",economic);//人口性质签约数
        returnMap.put("economicRate",economicRate);//人口性质签约率
        returnMap.put("economicTargetRate",economicTargetRate);//人口性质目标率
        returnMap.put("areaEconomic",areaEconomic);//经济人口性质人口数
        returnMap.put("areaEconomicTarget",areaEconomicTarget);//经济人口性质目标数
        return returnMap;
    }


    /**
     * 对基卫签约首页统计接口
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map<String,Object> findSignAnalysisIndexMotoe(ResidentVo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(qvo.getAreaId());
            map = getSianCount(address, null,qvo.getYearStart(),qvo.getYearEnd());
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            /*AppHospDept hospDept = (AppHospDept)this.sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
            if(hospDept != null){
                CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(hospDept.getHospAreaCode());
                map = getSianCount(address, hospDept,qvo.getYearStart(),qvo.getYearEnd());
            }*/
            List<MotoeVo>ls = getSianHosp(qvo);
            map.put("lsTotal",ls);
        }
        return map;
    }

    public List<MotoeVo> getSianHosp(ResidentVo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        String type = "";
        if (StringUtils.isNotBlank(qvo.getPtType())) {
            type = "'" + qvo.getPtType() + "' type ,";
        }
        if(StringUtils.isNotBlank(qvo.getHospId())) {
            if (StringUtils.isNotBlank(qvo.getPtType())) {
                String sql = " SELECT\n" +
                         type +
                        "\tu.PATIENT_NAME ptName,\n" +
                        "\tu.PATIENT_GENDER ptGender,\n" +
                        "\tu.PATIENT_BIRTHDAY ptBirthday,\n" +
                        "\tu.PATIENT_IDNO ptIdNo,\n" +
                        "\tf.SIGN_DATE ptsignDate,\n" +
                        " GROUP_CONCAT(g.LABEL_TITLE)  ptzdrq, " +
                        "  (SELECT GROUP_CONCAT(LABEL_TITLE) FROM APP_LABEL_ECONOMICS g WHERE g.LABEL_TYPE = 4 and g.LABEL_SIGN_ID = f.ID ) ptjjrk,  \n" +
                        "\tu.ID ptId,\n" +
                        "\tf.SIGN_TEAM_ID ptTeam ,\n" +
                        "\tu.PATIENT_JMDA  patientjmda\n" +
                        "FROM\n" +
                        "\t\tAPP_SIGN_FORM f LEFT JOIN APP_PATIENT_USER u on f.SIGN_PATIENT_ID = u.ID \n" +
                        "LEFT JOIN APP_LABEL_GROUP g on f.ID = g.LABEL_SIGN_ID \n" +
                        "WHERE\n" +
                        "\tf.SIGN_PATIENT_ID = u.ID  " +
                        "  and f.SIGN_STATE in ('0','2')  \n" +
                        "AND f.SIGN_TYPE = '1'  \n" +
                        "AND f.SIGN_HOSP_ID =:hospId ";
                map.put("hospId", qvo.getHospId());
                if (StringUtils.isNotBlank(qvo.getYearStart())) {
                    sql += "  AND f.SIGN_DATE >=:fromDate ";
                    map.put("fromDate", qvo.getYearStart() + " 00:00:00");
                }
                if (StringUtils.isNotBlank(qvo.getYearEnd())) {
                    sql += "  AND f.SIGN_DATE <=:toDtate ";
                    map.put("toDtate", qvo.getYearEnd() + " 23:59:59");
                }
                if (StringUtils.isNotBlank(qvo.getPtIdNo())) {
                    sql += " and u.PATIENT_IDNO=:ptIdNo ";
                    map.put("ptIdNo", qvo.getPtIdNo());
                }
                if (StringUtils.isNotBlank(qvo.getPtName())) {
                    sql += " and u.PATIENT_NAME like:ptName ";
                    map.put("ptName", "%" + qvo.getPtName() + "%");
                }
                if (StringUtils.isNotBlank(qvo.getPtType())) {
                    map.put("ptTypes",qvo.getPtType().split(";"));
                    sql += " and g.LABEL_TYPE='3'  and g.LABEL_VALUE IN (:ptTypes) ";
//                    if (qvo.getPtType().equals("1")) {
//                        sql += " and g.LABEL_TYPE='3'  and g.LABEL_VALUE not in('1','99') ";
//                    } else {
//                        sql += " and g.LABEL_TYPE='3'  and g.LABEL_VALUE  in('1') ";
//                    }
                }
                sql += " GROUP BY f.ID HAVING COUNT(*) >= 0  ORDER BY f.SIGN_DATE DESC ";

                List<MotoeVo> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, MotoeVo.class, qvo);
                if (ls != null && ls.size() > 0) {
                    return ls;
                }
            } else {

                String sql = " SELECT\n" +
                        "\tu.PATIENT_NAME ptName,\n" +
                        "\tu.PATIENT_GENDER ptGender,\n" +
                        "\tu.PATIENT_BIRTHDAY ptBirthday,\n" +
                        "\tu.PATIENT_IDNO ptIdNo,\n" +
                        "\tf.SIGN_DATE ptsignDate,\n" +
                        "\tf.ID signId,\n" +
                        type +
                        " '' ptzdrq, " +
                        " '' ptjjrk,  \n" +
                        "\tu.ID ptId,\n" +
                        "\tf.SIGN_TEAM_ID ptTeam ,\n" +
                        "\tu.PATIENT_JMDA  patientjmda\n" +
                        "FROM\n" +
                        "\t\tAPP_SIGN_FORM f LEFT JOIN APP_PATIENT_USER u on f.SIGN_PATIENT_ID = u.ID \n" +
                        "WHERE\n" +
                        "\t1=1 " +
                        "AND f.SIGN_STATE in ('0','2')  \n" +
                        "AND f.SIGN_TYPE = '1'  \n" +
                        "AND f.SIGN_HOSP_ID =:hospId ";
                map.put("hospId", qvo.getHospId());
                if (StringUtils.isNotBlank(qvo.getYearStart())) {
                    sql += "  AND f.SIGN_FROM_DATE >=:fromDate ";
                    map.put("fromDate", qvo.getYearStart() + " 00:00:00");
                }
                if (StringUtils.isNotBlank(qvo.getYearEnd())) {
                    sql += "  AND f.SIGN_FROM_DATE <=:toDtate ";
                    map.put("toDtate", qvo.getYearEnd() + " 23:59:59");
                }
                if (StringUtils.isNotBlank(qvo.getPtIdNo())) {
                    sql += " and u.PATIENT_IDNO=:ptIdNo ";
                    map.put("ptIdNo", qvo.getPtIdNo());
                }
                if (StringUtils.isNotBlank(qvo.getPtName())) {
                    sql += " and u.PATIENT_NAME like:ptName ";
                    map.put("ptName", "%" + qvo.getPtName() + "%");
                }

                sql += " ORDER BY f.SIGN_DATE DESC ";
                List<MotoeVo> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, MotoeVo.class, qvo);
                if (ls != null && ls.size() > 0) {
                    return ls;
                }
            }
        }
        return null;
    }



    /**
     * 签约转取统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findSignAnalysisListMotoe(ResidentVo qvo) throws Exception {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//签约
        String result = null;
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6 || AreaUtils.getAreaCode(qvo.getAreaId()).length() == 5){
            //    if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6) {//区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts != null && hospDepts.size() >0){
                    for (AppHospDept hosp : hospDepts) {
                        CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(hosp.getHospAreaCode());
                        Map<String, Object> map = getSianCount(address, hosp,qvo.getYearStart(),qvo.getYearEnd());
                        list.add(map);
                    }
                }
            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
                if(lsCdAddress != null && lsCdAddress.size()>0){
                    for (CdAddress address : lsCdAddress) {
                        Map<String, Object> map = getSianCount(address, null,qvo.getYearStart(),qvo.getYearEnd());
                        list.add(map);
                    }
                }
            }
            result = "1";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            List<AppTeam> lsTeam = sysDao.getAppTeamDao().findAll(qvo.getHospId());
            if(lsTeam != null && lsTeam.size()>0){
                for(AppTeam team : lsTeam){
                    Map<String, Object> map = getSianCountTeam(team,qvo.getYearStart(),qvo.getYearEnd());
                    list.add(map);
                }
            }
            result = "2";
        }
        Collections.sort(list, ComparatorUtils.getComparatorMotoe());
        HashMap<String,Object> returnMap = new HashMap<>();
        returnMap.put("total",list);
        returnMap.put("state",result);
        return returnMap;
    }
    /**
     * 签约量统计（团队）
     * @param team
     * @return
     */
    public Map<String,Object> getSianCountTeam(AppTeam team,String startDate,String endDate) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
        String[] year = endDate.split("-");
        int endYear = Integer.parseInt(year[0]);
        int endMonth = Integer.parseInt(year[1]);
        int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));
        int yqy = 0;
        int zdrqyqy = 0;
        int renew = 0;
        int totalRenew = 0;
//        map.put("STARTDATE", ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
//        map.put("ENDDATE",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
//        String sqlDate = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE ";
//        String sqlYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE = :SIGN_STATE "+sqlDate;
//        String sqlZdrqqy = "SELECT count(1) FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID where 1=1 and b.LABEL_TYPE=3 AND t.SIGN_STATE = :SIGN_STATE AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) "+sqlDate;
//        String state = SignFormType.YQY.getValue();//状态
//        String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
//        map.put("SIGN_STATE",state);
//        map.put("SIGN_PERS_GROUP",fwState);
//        map.put("TEAM_ID",team.getId());
//        sqlYqy += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
//        yqy = this.sysDao.getServiceDo().findCount(sqlYqy,map);//已签约
//        sqlZdrqqy += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
//        zdrqyqy = this.sysDao.getServiceDo().findCount(sqlZdrqqy,map);//重點人群已簽約
        map.put("STARTDATE",startDate);
        map.put("ENDDATE",endDate);
        String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
        String sql = "SELECT SUM(t.MANAGE_SIGN_COUNT) signCount," +
                "SUM(t.MANAGE_KEY_SIGN_COUNT) keySingCount," +
                "SUM(t.MANAGE_RENEW) renew, " +
                "SUM(t.MANAGE_TOTAL_RENEW) totalRenew "+
                " FROM APP_MANAGE_COUNT t where 1=1 "+sqlDate;
        map.put("TEAM_ID",team.getId());
        sql += " AND t.MANAGE_TEAM_ID = :TEAM_ID ";

        List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
        if(maps != null && maps.size() >0){
            if(maps.get(0).get("signCount") != null) {
                yqy = (int)Double.parseDouble(maps.get(0).get("signCount").toString());//已签约
            }
            if(maps.get(0).get("keySingCount") != null) {
                zdrqyqy = (int)Double.parseDouble(maps.get(0).get("keySingCount").toString());//重點人群已簽約
            }
            if(maps.get(0).get("renew") !=null){
                renew = (int)Double.parseDouble(maps.get(0).get("renew").toString());//续签数
            }
            if(maps.get(0).get("totalRenew")!=null){
                totalRenew = (int)Double.parseDouble(maps.get(0).get("totalRenew").toString());//总签约数
            }
        }

        //如果结束时间大于等于当前时间
        if(end >= now){
            List<CdCode> ls = this.sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_JJLX, CommonEnable.QIYONG.getValue());
            map.put("STARTDATE", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            sqlDate = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE ";
            //签约数
            String sqlYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) "+sqlDate;
            //重点人群数
            String sqlZdrqqy = "SELECT count(1) FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID where 1=1 and b.LABEL_TYPE='3' AND t.SIGN_STATE IN (:SIGN_STATE) AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) "+sqlDate;
//            String state = SignFormType.YQY.getValue();//状态
            String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
            String[] signStates = new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()};
            map.put("SIGN_STATE",signStates);
            map.put("SIGN_PERS_GROUP",fwState);
            map.put("TEAM_ID",team.getId());
            sqlYqy += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
            yqy += this.sysDao.getServiceDo().findCount(sqlYqy,map);//已签约
            sqlZdrqqy += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
            zdrqyqy += this.sysDao.getServiceDo().findCount(sqlZdrqqy,map);//重點人群已簽約

        }


        //续签率
        double xql = 0;
        if(totalRenew>0){
            xql = MyMathUtil.div(Double.valueOf(renew),Double.valueOf(totalRenew),4)*100;//续签率
        }
        returnMap.put("xql",xql);//续签率
        returnMap.put("yqy",yqy);//签约数
        returnMap.put("zdrqyqy",zdrqyqy);//重点人群签约数
        returnMap.put("teamName",team.getTeamName());//团队名称
        returnMap.put("teamDrName",team.getTeamDrName());//团队队长名称
        returnMap.put("xqs",renew);//续签数
        returnMap.put("synqys",totalRenew);//上一年签约数
        return returnMap;
    }

    /**
     * 根据居民档案号获取居民签约信息
     * WangCheng
     * @param vo
     * @return
     * @throws Exception
     */
    public AppSignForm findSignformJMDA(SignsurrenderVo vo)throws Exception
    {
        if(StringUtils.isNotBlank(vo.getPtJmdah())){
            Map<String,Object> map = new HashMap<String,Object>();
            String sql =" SELECT f.* from app_patient_user u INNER JOIN app_sign_form  f on u.ID = f.SIGN_PATIENT_ID where f.SIGN_STATE in ('2','0') and u.PATIENT_JMDA =:jmda ";
            map.put("jmda",vo.getPtJmdah());
            List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
            if(ls!=null  && ls.size()>0){
                return ls.get(0);
            }
        }
        return null ;
    }


    public AppSignForm findSignPatient(String patientIdno)throws Exception
    {
        if(StringUtils.isNotBlank(patientIdno)){
            Map<String,Object> map = new HashMap<String,Object>();
            String sql ="";
            sql =" SELECT f.* from app_sign_form f INNER JOIN app_patient_user u on f.SIGN_PATIENT_ID = u.ID  where f.SIGN_DEL_TYPE ='1'  and u.PATIENT_IDNO =:patientidno " ;
            map.put("patientidno",patientIdno);
            List<AppSignForm> ls =sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
            if(ls!=null && ls.size()>0){
                return ls.get(0);
            }
        }
        return null ;
    }

}
