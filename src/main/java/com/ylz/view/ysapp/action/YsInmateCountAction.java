package com.ylz.view.ysapp.action;


import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.app.po.AppTeamMember;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrCountEntity;
import com.ylz.bizDo.jtapp.drEntity.JmInfo;
import com.ylz.bizDo.jtapp.drEntity.ReferralInfo;
import com.ylz.bizDo.jtapp.drVo.AppDrCount;
import com.ylz.bizDo.jtapp.drVo.AppJmList;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.SignFormType;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 医生统计,居民列表action.
 */
@SuppressWarnings("all")
@Action(
        value="yzInmate",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class YsInmateCountAction extends CommonAction {

        /**
         * 居民查询1 得到类型集合和总条数
         * @param expire 1已到期
         * @param vip vip 1勾选
         * @param serviceA 三师共管 1勾选
         * @param serviceAred 三师共管 红色 1勾选
         * @param serviceAyellow 三师共管 黄色 1勾选
         * @param serviceAgreen 三师共管 绿 1勾选
         * @param serviceB 居家养老 1勾选
         * @param serviceBred 居家养老 红色 1勾选
         * @param serviceByellow 居家养老 黄色 1勾选
         * @param serviceBgreen 居家养老 绿色 1勾选
         * @param signPersGroup 服务人群 多分号隔开
         * @param signHealthGroup 健康情况 多分号隔开
         * @param labelGruops 疾病类型 多分号隔开
         * @param teamId 团队id
         */
        public String jmFindCount(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                this.getAjson().setMsgCode("800");
                                //三师 或 居家 或 vip 不为空 直接统计人员列表
                                if((StringUtils.isNotBlank(qvo.getServiceA()) && qvo.getServiceA().equals("1")) || (StringUtils.isNotBlank(qvo.getServiceB()) && qvo.getServiceB().equals("1")) || (StringUtils.isNotBlank(qvo.getVip()) && qvo.getVip().equals("1"))||(StringUtils.isNotBlank(qvo.getExpire())&&"1".equals(qvo.getExpire()))){
                                        return this.jmFindList();
                                }
                                //疾病类型不为空 //统计各疾病人员数列表
                                if(StringUtils.isNotBlank(qvo.getLabelGruops())){
                                        String[] ps=qvo.getLabelGruops().split(";");
                                        if(ps!=null && ps.length>0) {
                                                List<AppJmList> ls = new ArrayList<AppJmList>();
                                                for (String p : ps) {
                                                        AppJmList a=new AppJmList();
                                                        AppLabelManage c=this.getSysDao().getAppLabelManageDao().findLabelByValue("2",p);
                                                        a.setName(c.getLabelTitle());
                                                        //保存查询条件
                                                        a.setSignHealthGroup(p);
                                                        a.setSignPersGroup(qvo.getSignPersGroup());
                                                        a.setLabelGruops(p);
                                                        //
                                                        HashMap map=new HashMap();
                                                        String sql="SELECT\n" +
                                                                "\tCOUNT(1)\n" +
                                                                "FROM\n" +
                                                                "\tAPP_SIGN_FORM a,\n" +
                                                                "\tAPP_LABEL_DISEASE b\n" +
                                                                "WHERE\n" +
                                                                "\ta.ID = b.LABEL_SIGN_ID AND a.SIGN_TYPE='1'\n" +
                                                                "and b.LABEL_VALUE =:LABEL_VALUE";
                                                        map.put("LABEL_VALUE",p);
                                                        if(StringUtils.isNotBlank(qvo.getTeamId())) {
                                                                sql = sql + " AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID";
                                                                map.put("SIGN_TEAM_ID", qvo.getTeamId());
                                                        }
                                                        String[] pg=qvo.getSignPersGroup().split(";");
                                                        if(pg!=null && pg.length>0){
                                                                sql=sql+" and a.SIGN_PERS_GROUP  in (:SIGN_PERS_GROUP)";
                                                                map.put("SIGN_PERS_GROUP",pg);
                                                        }
                                                        String[] hg=qvo.getSignHealthGroup().split(";");
                                                        if(hg!=null && hg.length>0){
                                                                sql=sql+" and a.SIGN_HEALTH_GROUP  in (:SIGN_HEALTH_GROUP)";
                                                                map.put("SIGN_HEALTH_GROUP",hg);
                                                        }
                                                        int count=sysDao.getServiceDo().findCount(sql,map);
                                                        a.setCount(String.valueOf(count));
                                                        ls.add(a);
                                                }
                                                this.getAjson().setRows(ls);
                                        }
                                        return "ajson";
                                }
                                //健康情况不为空 //统计各健康情况人员数列表
                                if(StringUtils.isNotBlank(qvo.getSignHealthGroup())){
                                        String[] ps=qvo.getSignHealthGroup().split(";");
                                        if(ps!=null && ps.length>0) {
                                                List<AppJmList> ls = new ArrayList<AppJmList>();
                                                for (String p : ps) {
                                                        AppJmList a=new AppJmList();
                                                        AppLabelManage c=this.getSysDao().getAppLabelManageDao().findLabelByValue("1",p);
                                                        a.setName(c.getLabelTitle());
                                                        //保存查询条件
                                                        a.setSignHealthGroup(p);
                                                        a.setSignPersGroup(qvo.getSignPersGroup());
                                                        //
                                                        HashMap map=new HashMap();
                                                        String sql="SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_TYPE='1' AND a.SIGN_HEALTH_GROUP=:SIGN_HEALTH_GROUP ";
                                                        map.put("SIGN_HEALTH_GROUP",p);
                                                        if(StringUtils.isNotBlank(qvo.getTeamId())) {
                                                                sql = sql + " AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID";
                                                                map.put("SIGN_TEAM_ID", qvo.getTeamId());
                                                        }
                                                        String[] pg=qvo.getSignPersGroup().split(";");
                                                        if(pg!=null && pg.length>0){
                                                                sql=sql+" and a.SIGN_PERS_GROUP  in (:SIGN_PERS_GROUP)";
                                                                map.put("SIGN_PERS_GROUP",pg);
                                                        }
                                                        int count=sysDao.getServiceDo().findCount(sql,map);
                                                        a.setCount(String.valueOf(count));
                                                        ls.add(a);

                                                }
                                                this.getAjson().setRows(ls);
                                        }
                                        return "ajson";
                                }
                                //服务人群不为空 //统计各服务人群人群列表
                                if(StringUtils.isNotBlank(qvo.getSignPersGroup())){
                                        String[] ps=qvo.getSignPersGroup().split(";");
                                        if(ps!=null && ps.length>0){
                                                List<AppJmList> ls=new ArrayList<AppJmList>();
                                                for (String p:ps){
                                                        AppJmList a=new AppJmList();
                                                        CdCode c=this.getSysDao().getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_RESIDENTMANGE,p);
                                                        //保存查询条件
                                                        a.setSignPersGroup(p);
                                                        //
                                                        a.setName(c.getCodeTitle());
                                                        String sql="SELECT\n" +
                                                                "\tCOUNT(1)\n" +
                                                                "FROM\n" +
                                                                "\tAPP_SIGN_FORM a,\n" +
                                                                "\tAPP_LABEL_GROUP b\n" +
                                                                "WHERE\n" +
                                                                "\ta.ID = b.LABEL_SIGN_ID AND a.SIGN_TYPE='1'\n" +
                                                                "and b.LABEL_VALUE =:LABEL_VALUE";
                                                        HashMap map=new HashMap();
                                                        map.put("LABEL_VALUE",p);
                                                        if(StringUtils.isNotBlank(qvo.getTeamId())) {
                                                                sql = sql + " AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID";
                                                                map.put("SIGN_TEAM_ID", qvo.getTeamId());
                                                        }
                                                        int count=sysDao.getServiceDo().findCount(sql,map);
                                                        a.setCount(String.valueOf(count));
                                                        ls.add(a);
                                                }
                                                this.getAjson().setRows(ls);
                                        }
                                        return "ajson";
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
         * 居民查询 2 查询详细人员列表
         * @param expire 1已到期
         * @param vip vip 1勾选
         * @param serviceA 三师共管 1勾选
         * @param serviceAred 三师共管 红色 1勾选
         * @param serviceAyellow 三师共管 黄色 1勾选
         * @param serviceAgreen 三师共管 绿 1勾选
         * @param serviceB 居家养老 1勾选
         * @param serviceBred 居家养老 红色 1勾选
         * @param serviceByellow 居家养老 黄色 1勾选
         * @param serviceBgreen 居家养老 绿色 1勾选
         * @param signPersGroup 服务人群 多分号隔开.
         * @param signHealthGroup 健康情况 多分号隔开
         * @param labelGruops 疾病类型 多分号隔开
         * @param teamId 团队id
         * @param patientName 患者姓名
         */
        public String  jmFindList(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        AppDrUser drUser = this.getAppDrUser();
                        if(qvo == null || drUser == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                HashMap map = new HashMap();
                                String sql = "SELECT\n" +
                                        "\tc.ID id,\n" +
                                        "\tc.PATIENT_NAME name,\n" +
                                        "\tc.PATIENT_GENDER sex,\n" +
                                        //"\tc.PATIENT_AGE age,\n" +
                                        "\tc.PATIENT_TEL tel,\n" +
                                        "\tc.PATIENT_IDNO patientIdno,\n" +
                                        "null age," +
                                        "\tc.PATIENT_CARD patientCard,\n" +
                                        "\tc.PATIENT_IMAGEURL imgurl,\n" +
                                        "\ta.SIGN_SERVICE_A ssgg,\n" +
                                        "\ta.SIGN_SERVICE_B jjyl,\n" +
                                        "\ta.SIGN_TYPE signType,\n" +
                                        "\tc.PATIENT_ADDRESS address,\n" +
                                        "\ta.SIGN_URRENDER_REASON signUrrenderReason,\n" +
                                        "\ta.SIGN_URRENDER_REASON_PATIENT signUrrenderReasonPatient,\n" +
                                        "\ta.SIGN_OTHNER_REASON signOthnerReason,\n" +
                                        "\t(SELECT GROUP_CONCAT(LABEL_TITLE ORDER BY LABEL_TITLE) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID) signPersGroup,\n" +
                                        "\t(SELECT COUNT(*) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID) signPersGroupCount,\n" +
                                        "\t(SELECT GROUP_CONCAT(LABEL_VALUE ORDER BY LABEL_VALUE) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID) signPersGroupValue,\n" +
                                        "\ta.SIGN_SERVICE_B_COLOR jjylcolor,\n" +
                                        "\t(SELECT GROUP_CONCAT(LABEL_TITLE ORDER BY LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID) labtitle,\n" +
                                        "\t(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID) labcolor,\n" +
                                        "\t(SELECT COUNT(*) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID) signDiseaseCount,\n" +
                                        "\t(SELECT GROUP_CONCAT(LABEL_VALUE ORDER BY LABEL_VALUE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID) signDiseaseValue,\n" +
                                        "\t(SELECT COUNT(*) from APP_LABEL_ECONOMICS g where g.LABEL_TYPE=4 and g.LABEL_SIGN_ID=a.ID) signEconomicsCount,\n" +
                                        "\t(SELECT GROUP_CONCAT(LABEL_VALUE ORDER BY LABEL_VALUE) from APP_LABEL_ECONOMICS g where g.LABEL_TYPE=4 and g.LABEL_SIGN_ID=a.ID) signEconomicsValue,\n" +
                                        "\tDATE_FORMAT(a.SIGN_DATE,'%Y-%m-%d') signDate,\n" +
                                        "\ta.SIGN_PAY_STATE signPayState,\n" +
                                        "\ta.ID signFormId,\n" +
                                        "\ta.SIGN_STATE signState,\n" +
                                        "\ta.SIGN_HEALTH_GROUP signHealthGroup,\n" +
                                        "\tnull referralState\n" +
                                        "FROM\n" +
                                        "\tAPP_SIGN_FORM a\n" +
//                                        "LEFT JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                                        "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID\n" ;
                                sql+= "where 1=1 ";
                                //模糊搜索
                                if(StringUtils.isNotBlank(qvo.getPatientName())) {
                                        sql = sql + " AND ( c.PATIENT_NAME LIKE :searchValue OR c.PATIENT_IDNO LIKE :searchValue OR c.PATIENT_BOPOMOFO LIKE :searchValue ) ";
                                        map.put("searchValue", "%"+qvo.getPatientName()+"%");
                                }
                                if(StringUtils.isBlank(qvo.getTeamId()) && StringUtils.isBlank(qvo.getTeamId())){
//                                        this.getAjson().setMsg("请求参数异常!");
//                                        this.getAjson().setMsgCode("900");
//                                        return "ajson";
                                        throw new Exception("请求参数异常!");
                                }else{
                                        if(StringUtils.isNotBlank(qvo.getTeamId())) {
                                                sql = sql + " AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID "/*AND a.SIGN_DR_ID=:SIGN_DR_ID*/;
                                                map.put("SIGN_TEAM_ID", qvo.getTeamId());
//                                         map.put("SIGN_DR_ID",drUser.getId());
                                        }
//                                if(!"1".equals(qvo.getOpenState())){//没有打开开关，查询带医生主键
//                                        sql = sql + " AND a.SIGN_DR_ID=:SIGN_DR_ID ";
//                                        map.put("SIGN_DR_ID",drUser.getId());
//                                }
                                        if(StringUtils.isNotBlank(qvo.getDrId())){
                                                sql = sql + " AND a.SIGN_DR_ID=:SIGN_DR_ID ";
                                                map.put("SIGN_DR_ID",qvo.getDrId());
                                        }
                                }

                                //vip
                                if (StringUtils.isNotBlank(qvo.getVip()) && qvo.getVip().equals("1")){
                                        sql=sql+" AND a.SIGN_TYPE='2'";
                                }else {
                                        sql=sql+" AND a.SIGN_TYPE='1'";
                                }
                                //到期
                                if (StringUtils.isNotBlank(qvo.getExpire()) && "1".equals(qvo.getExpire())){
                                        map.put("SIGN_STATE", SignFormType.YJY.getValue());
                                        sql = sql + " AND a.SIGN_STATE = :SIGN_STATE ";
                                }
                                if(StringUtils.isBlank(qvo.getExpire())&&StringUtils.isBlank(qvo.getSignDelType())){
                                        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
                                        map.put("SIGN_STATE",signStates);
                                        sql = sql + " AND a.SIGN_STATE IN (:SIGN_STATE) ";
                                }
                                //删除
                                if(StringUtils.isNotBlank(qvo.getSignDelType()) && "1".equals(qvo.getSignDelType())){
                                        map.put("SIGN_STATE", SignFormType.SC.getValue());
                                        sql = sql + " AND a.SIGN_STATE = :SIGN_STATE ";
                                }


                                //三师
                                if(StringUtils.isNotBlank(qvo.getServiceA()) && qvo.getServiceA().equals("1")){
                                        sql=sql+" AND a.SIGN_SERVICE_A='2'";
                                        String bacolor[]=new String[3];
                                        int bia=0;
                                        if(StringUtils.isNotBlank(qvo.getServiceAred()) && qvo.getServiceAred().equals("1")) {
                                                bacolor[bia]="red";bia++;
                                        }
                                        if(StringUtils.isNotBlank(qvo.getServiceAyellow()) && qvo.getServiceAyellow().equals("1")) {
                                                bacolor[bia]="yellow";bia++;
                                        }
                                        if(StringUtils.isNotBlank(qvo.getServiceAgreen()) && qvo.getServiceAgreen().equals("1")) {
                                                bacolor[bia]="green";bia++;
                                        }
                                        if((StringUtils.isNotBlank(qvo.getServiceAred()) && qvo.getServiceAred().equals("1")) || (StringUtils.isNotBlank(qvo.getServiceAyellow()) && qvo.getServiceAyellow().equals("1")) || (StringUtils.isNotBlank(qvo.getServiceAgreen()) && qvo.getServiceAgreen().equals("1"))){
                                                sql=sql+" AND b.LABEL_COLOR in (:LABEL_COLOR)";
                                                map.put("LABEL_COLOR",bacolor);
                                        }
                                }
                                //居家
                                if(StringUtils.isNotBlank(qvo.getServiceB()) && qvo.getServiceB().equals("1")){
                                        sql=sql+" AND a.SIGN_SERVICE_B='2'";
                                        String bcolor[]=new String[3];
                                        int bi=0;
                                        if(StringUtils.isNotBlank(qvo.getServiceBred()) && qvo.getServiceBred().equals("1")) {
                                                bcolor[bi]="red";bi++;
                                        }
                                        if(StringUtils.isNotBlank(qvo.getServiceByellow()) && qvo.getServiceByellow().equals("1")){
                                                bcolor[bi]="yellow";bi++;
                                        }
                                        if(StringUtils.isNotBlank(qvo.getServiceBgreen()) && qvo.getServiceBgreen().equals("1")){
                                                bcolor[bi]="green";bi++;
                                        }
                                        if((StringUtils.isNotBlank(qvo.getServiceBred()) && qvo.getServiceBred().equals("1")) || (StringUtils.isNotBlank(qvo.getServiceByellow()) && qvo.getServiceByellow().equals("1")) || (StringUtils.isNotBlank(qvo.getServiceBgreen()) && qvo.getServiceBgreen().equals("1"))){
                                                sql=sql+" AND a.SIGN_SERVICE_B_COLOR in (:SIGN_SERVICE_B_COLOR)";
                                                map.put("SIGN_SERVICE_B_COLOR",bcolor);
                                        }
                                }
                                //转诊
                                if(StringUtils.isNotBlank(qvo.getIsReferral())&&"1".equals(qvo.getIsReferral())){
                                        sql += " AND a.ID IN (SELECT ccc.REF_SIGN_ID FROM APP_REFERRAL_TABLE ccc WHERE ccc.REF_STATE IN ('0','1')) ";
                                }
                                sql += " GROUP BY c.ID";
                                String sqlAll = null;
                                String sqlSignPersGroup = null;//服务人群
                                String sqlLabelGruops = null;//疾病类型
                                String sqlHealth = null;//健康情况
                                String sqlLabelEconomics = null;//经济类型
                                //服务人群
                                if(StringUtils.isNotBlank(qvo.getSignPersGroup())) {
                                        String[] result = qvo.getSignPersGroup().split(";");
                                        if(result != null && result.length >0){
                                                String signPersGroup = null;
                                                String perV = "";
                                                for(String s : result){
                                                       if(StringUtils.isNotBlank(signPersGroup)){
                                                               signPersGroup += "," +s;
                                                       }else{
                                                               signPersGroup = s;
                                                       }
                                                       perV += " AND find_in_set("+s+",c.signPersGroupValue) ";
                                                }
                                                sqlSignPersGroup = "SELECT c.* FROM (" + sql + ") c LEFT JOIN APP_LABEL_GROUP b ON c.signFormId = b.LABEL_SIGN_ID ";
                                                if(result.length == 1){
                                                        sqlSignPersGroup += " WHERE 1=1 AND b.LABEL_TYPE='3' AND b.LABEL_VALUE = :signPersGroup";
                                                }else{
//                                                        sqlSignPersGroup += " WHERE 1=1 AND b.LABEL_TYPE='3' AND c.signPersGroupValue = :signPersGroup";
                                                        sqlSignPersGroup += " WHERE 1=1 AND b.LABEL_TYPE='3' "+perV;
                                                }

                                                sqlSignPersGroup += " GROUP BY c.id ";
//                                        sqlSignPersGroup += " GROUP BY c.id HAVING c.signPersGroupCount >= "+ num;
                                                map.put("signPersGroup",signPersGroup);
                                        }

                                }
                                //疾病类型
                                if(StringUtils.isNotBlank(qvo.getLabelGruops())) {
                                        String[] result = qvo.getLabelGruops().split(";");
                                        if(result != null && result.length >0){
                                                String labelGroups = null;
                                                for(String s : result){
                                                        if(StringUtils.isNotBlank(labelGroups)){
                                                                labelGroups += "," +s;
                                                        }else{
                                                                labelGroups = s;
                                                        }
                                                }
                                                sqlLabelGruops = "SELECT c.* FROM (" + sql + ") c LEFT JOIN APP_LABEL_DISEASE b ON c.signFormId = b.LABEL_SIGN_ID ";
                                                if(result.length == 1){
                                                        sqlLabelGruops += " WHERE 1=1 AND b.LABEL_TYPE='2' AND b.LABEL_VALUE = :labelGruops";
                                                }else{
                                                        sqlLabelGruops += " WHERE 1=1 AND b.LABEL_TYPE='2' AND c.signDiseaseValue = :labelGruops";
                                                }
                                                sqlLabelGruops += " GROUP BY c.id  ";
//                                              sqlSignPersGroup += " GROUP BY c.id HAVING c.signDiseaseCount >= "+ num;
                                                map.put("labelGruops",labelGroups);
                                        }
                                }
                                //健康类型
                                if(StringUtils.isNotBlank(qvo.getSignHealthGroup())) {
                                        sqlHealth = "SELECT c.* FROM (" + sql + ") c WHERE 1=1 ";
                                        sqlHealth += "  AND c.signHealthGroup IN (:signHealthGroup)";
                                        map.put("signHealthGroup",qvo.getSignHealthGroup().split(";"));
                                }
                                //经济类型
                                if(StringUtils.isNotBlank(qvo.getSignsJjType())){
                                        String[] result = qvo.getSignsJjType().split(";");
                                        if(result != null && result.length >0){
                                                String economicsGruops = null;
                                                for(String s : result){
                                                        if(StringUtils.isNotBlank(economicsGruops)){
                                                                economicsGruops += "," +s;
                                                        }else{
                                                                economicsGruops = s;
                                                        }
                                                }
                                                sqlLabelEconomics = "SELECT c.* FROM (" + sql + ") c LEFT JOIN APP_LABEL_ECONOMICS b ON c.signFormId = b.LABEL_SIGN_ID ";
                                                if(result.length == 1){
                                                        sqlLabelEconomics += " WHERE 1=1 AND b.LABEL_TYPE='4' AND b.LABEL_VALUE = :economicsGruops";
                                                }else{
                                                        sqlLabelEconomics += " WHERE 1=1 AND b.LABEL_TYPE='4' AND c.signDiseaseValue = :economicsGruops";
                                                }
                                                sqlLabelEconomics += " GROUP BY c.id  ";
                                                map.put("economicsGruops",economicsGruops);
                                        }
                                }
                                if(StringUtils.isBlank(sqlSignPersGroup) && StringUtils.isBlank(sqlLabelGruops) && StringUtils.isBlank(sqlHealth) && StringUtils.isBlank(sqlLabelEconomics)){
                                        sqlAll = "SELECT * FROM ("+sql +") f";
                                }else if(StringUtils.isNotBlank(sqlSignPersGroup) && StringUtils.isNotBlank(sqlLabelGruops) && StringUtils.isNotBlank(sqlHealth)){
                                        sqlAll = sqlSignPersGroup + " UNION " + sqlLabelGruops + " UNION " + sqlHealth;
                                }else if(StringUtils.isNotBlank(sqlSignPersGroup) && StringUtils.isNotBlank(sqlLabelGruops) && StringUtils.isBlank(sqlHealth)){
                                        sqlAll = sqlSignPersGroup + " UNION " + sqlLabelGruops ;
                                }else if(StringUtils.isNotBlank(sqlSignPersGroup) && StringUtils.isBlank(sqlLabelGruops) && StringUtils.isNotBlank(sqlHealth)){
                                        sqlAll = sqlSignPersGroup + " UNION " + sqlHealth ;
                                }else if(StringUtils.isBlank(sqlSignPersGroup) && StringUtils.isNotBlank(sqlLabelGruops) && StringUtils.isNotBlank(sqlHealth)){
                                        sqlAll = sqlLabelGruops + " UNION " + sqlHealth ;
                                }else if(StringUtils.isNotBlank(sqlSignPersGroup) && StringUtils.isBlank(sqlLabelGruops) && StringUtils.isBlank(sqlHealth) && StringUtils.isBlank(sqlLabelEconomics)){
                                        sqlAll =  sqlSignPersGroup ;
                                }else if(StringUtils.isBlank(sqlSignPersGroup) && StringUtils.isNotBlank(sqlLabelGruops) && StringUtils.isBlank(sqlHealth)){
                                        sqlAll = sqlLabelGruops ;
                                }else if(StringUtils.isBlank(sqlSignPersGroup) && StringUtils.isBlank(sqlLabelGruops) && StringUtils.isNotBlank(sqlHealth)){
                                        sqlAll = sqlHealth ;
                                }else if(StringUtils.isBlank(sqlSignPersGroup) && StringUtils.isBlank(sqlLabelGruops) && StringUtils.isBlank(sqlHealth) && StringUtils.isNotBlank(sqlLabelEconomics)){
                                        sqlAll =  sqlLabelEconomics ;
                                }else if(StringUtils.isNotBlank(sqlSignPersGroup) && StringUtils.isBlank(sqlLabelGruops) && StringUtils.isBlank(sqlHealth) && StringUtils.isNotBlank(sqlLabelEconomics)){
                                        sqlAll = sqlSignPersGroup + " UNION " + sqlLabelEconomics ;
                                }
                                List<JmInfo> ls=sysDao.getServiceDo().findSqlMapRVo(sqlAll,map, JmInfo.class,qvo);
                                Map<String,Object> mapp = new HashMap<>();
                                mapp.put("count",qvo.getItemCount());
                                this.getAjson().setQvo(qvo);
                                this.getAjson().setRows(ls);
                                this.getAjson().setMap(mapp);
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
     * 首页统计结果
     * @return
     */
    public String appIndexCount(){
        try{
            AppDrCount qvo = (AppDrCount)this.getAppJson(AppDrCount.class);
            if(qvo != null){
                    if(StringUtils.isNotBlank(qvo.getTeamId())){
                            AppDrUser user = this.getAppDrUser();
                            AppDrCountEntity count = this.getSysDao().getAppSignformDao().findSignCount(qvo.getTeamId(),user.getId());
                            AppTeamMember team = this.getSysDao().getAppTeamMemberDao().findMemByDrId(user.getId(),qvo.getTeamId());
                            if(team != null) {
                                    count.setSftz(team.getMemState());
                            }
                            this.getAjson().setVo(count);
                    }else{
                            AppDrCountEntity count = new AppDrCountEntity();
                            count.setSftz("2");
                            count.setDshrs(new BigInteger("0"));
                            count.setLnrq(new BigInteger("0"));
                            count.setMbrq(new BigInteger("0"));
                            count.setPtrq(new BigInteger("0"));
                            count.setZqyrs(new BigInteger("0"));
                            count.setJdlkrcpkrq(new BigInteger("0"));
                            count.setJstsjtrq(new BigInteger("0"));
                            count.setTkh(new BigInteger("0"));
                            count.setDbh(new BigInteger("0"));
                            this.getAjson().setVo(count);
                    }
            }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }
    /**
     * 根据经济类型查询人员列表
     * @return
     */
    public String findByEconomicType(){
        try{
            AppCommQvo qvo = (AppCommQvo)this.getAppJson(AppCommQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<JmInfo> list = sysDao.getAppPatientUserDao().findByEconomicType(qvo);
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
         * 上级医院查询转诊居民
         * @return
         */
    public String findUpHospReferral(){
        try{
            AppCommQvo qvo = (AppCommQvo)this.getAppJson(AppCommQvo.class);
            if(qvo==null){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
            }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser!=null){
                            qvo.setSignHospId(drUser.getDrHospId());
                            List<ReferralInfo> list = sysDao.getAppRefarralTableDao().findUpHospt(qvo);
                            if(list!=null && list.size()>0){
                                    this.getAjson().setRows(list);
                                    this.getAjson().setQvo(qvo);
                                    this.getAjson().setMsgCode("800");
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
}
