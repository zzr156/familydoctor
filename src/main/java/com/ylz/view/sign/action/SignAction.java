package com.ylz.view.sign.action;

import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.Motoe.dao.vo.SignsurrenderVo;
import com.ylz.bizDo.app.entity.*;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.*;
import com.ylz.bizDo.cd.po.*;
import com.ylz.bizDo.cd.vo.CdAddressPeopleVo;
import com.ylz.bizDo.cd.vo.CdDissolutionWarningVo;
import com.ylz.bizDo.jtapp.commonEntity.AppFamilyInfo;
import com.ylz.bizDo.jtapp.commonVo.AppAgreeMentQvo;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.commonVo.AppYbCommQvo;
import com.ylz.bizDo.jtapp.commonVo.AppYbResultVo;
import com.ylz.bizDo.jtapp.drEntity.AppDrTeamEntity;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeCountQvo;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeSureQvo;
import com.ylz.bizDo.mangecount.entity.ManageCountEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.bizDo.web.vo.WebFamilySignVo;
import com.ylz.bizDo.web.vo.WebHealthReportVo;
import com.ylz.bizDo.web.vo.WebSignSaveVo;
import com.ylz.bizDo.web.vo.WebSignVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.Constant;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.*;
import com.ylz.task.async.SecurityCardAsyncBean;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("all")
@Action(
        value = "signAction",
        results = {
                @Result(name = "json", type = "json", params = {"root", "jsons", "contentType", "application/json"}),
                @Result(name = "jList", type = "json", params = {"root", "jList", "contentType", "application/json"}),
                @Result(name = "ajson", type = "json", params = {"root", "ajson", "contentType", "application/json"}),
                @Result(name = "jsonLayui", type = "json", params = {"root", "jsonLayui", "excludeNullProperties", "true", "contentType", "application/json"}),
                @Result(name = "jsonUpload", type = "json", params = {"root", "jsons", "contentType", "text/html"})
        }
)
public class SignAction extends CommonAction {
    private List jList;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private File upExcel; //上传的文件
    private String upExcelFileName; //文件名称
    private String upExcelContentType; //文件类型

    public File getUpExcel() {
        return upExcel;
    }

    public void setUpExcel(File upExcel) {
        this.upExcel = upExcel;
    }

    public String getUpExcelFileName() {
        return upExcelFileName;
    }

    public void setUpExcelFileName(String upExcelFileName) {
        this.upExcelFileName = upExcelFileName;
    }

    public String getUpExcelContentType() {
        return upExcelContentType;
    }

    public void setUpExcelContentType(String upExcelContentType) {
        this.upExcelContentType = upExcelContentType;
    }

    /**
     * 初始数据
     *
     * @return
     */
    public String commList() {
        try {

        } catch (Exception e) {
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }

    /**
     * 查询
     *
     * @return
     */
    public String list() {
        try {
            AppTeamQvo qvo = (AppTeamQvo) getJsonLay(AppTeamQvo.class);
            if (qvo == null) {
                qvo = new AppTeamQvo();
            }
            List<AppTeam> ls = sysDao.getAppTeamDao().findListQvo(qvo);
            this.getJsonLayui().setData(ls);
            this.getJsonLayui().setCount(qvo.getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLayui";
    }

    /**
     * 查询是否签约
     */
    public String signfind() {
        try {
            AppWebSignQvo qvo = (AppWebSignQvo) getJson(AppWebSignQvo.class);
            if (qvo != null) {
                AppWebSignFormListEntity vo = sysDao.getAppSignformDao().signfind(qvo);
                //调接口查询省库是否有签约数据
//                AppWebSignFormListEntity vo = sysDao.getSecurityCardAsyncBean().signfind(qvo);
                this.jsons.setVo(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }


    /**
     * 调用家签接口获取市级平台url
     * WangCheng
     *
     * @return
     */
    public String findMunicipalPlatformUrl() {
        try {
            String address = null;
            String urlType = "";
            JSONObject jsonParam = new JSONObject();
            AppHealthCardRecodesVo vo = (AppHealthCardRecodesVo) getVoJson(AppHealthCardRecodesVo.class);
            if (vo != null) {
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, vo.getOrgId());
                CloseableHttpClient client = HttpClients.createDefault();
                vo.setUrlType(dept.getHospAreaCode().substring(0, 4));
                if (StringUtils.isNoneBlank(vo.getUrlType())) {
                    if (vo.getUrlType().equals(AddressType.FZS.getValue())) {
                        urlType = AddressType.FZ.getValue();
                        address = PropertiesUtil.getConfValue("FZurl");
                    } else if (vo.getUrlType().equals(AddressType.QZS.getValue())) {
                        urlType = AddressType.QZ.getValue();
                        address = PropertiesUtil.getConfValue("QZurl");
                    } else if (vo.getUrlType().equals(AddressType.ZZS.getValue())) {
                        urlType = AddressType.ZZ.getValue();
                        address = PropertiesUtil.getConfValue("ZZurl");
                    } else if (vo.getUrlType().equals(AddressType.PTS.getValue())) {
                        urlType = AddressType.PT.getValue();
                        address = PropertiesUtil.getConfValue("PTurl");
                    } else if (vo.getUrlType().equals(AddressType.NPS.getValue())) {
                        urlType = AddressType.NP.getValue();
                        address = PropertiesUtil.getConfValue("NPurl");
                    } else if (vo.getUrlType().equals(AddressType.SMS.getValue())) {
                        urlType = AddressType.SM.getValue();
                        address = PropertiesUtil.getConfValue("SMurl");
                    } else if (vo.getUrlType().equals(AddressType.CS.getValue())) {
                        urlType = AddressType.CS.getValue();
                        address = PropertiesUtil.getConfValue("CSurl");
                    }else{
                        urlType = AddressType.SXS.getValue();
                        address = PropertiesUtil.getConfValue("SXurl");
                    }
                    jsonParam.put("idNo", vo.getIdNo());
                    jsonParam.put("urlType", urlType);
                    if (address != null) {
                        String reslut = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                        if (reslut != null && reslut != "") {
                            JSONObject jsonAll = JSONObject.fromObject(reslut);
                            this.jsons.setResult(jsonAll.get("vo").toString());
                        }
                    }
                }
            } else {
                this.jsons.setMsg("参数为空！");
                this.jsons.setCode("1001");
            }
        } catch (Exception e) {
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }


    /**
     * 调取基卫居民档案接口
     */
    public String findjmda() {
        try {
            String address = null;
            String urlType = "";
            JSONObject jsonParam = new JSONObject();
            AppHealthCardRecodesVo vo = (AppHealthCardRecodesVo) getVoJson(AppHealthCardRecodesVo.class);
            if (vo != null) {
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, vo.getOrgId());
                CloseableHttpClient client = HttpClients.createDefault();
                vo.setUrlType(dept.getHospAreaCode().substring(0, 4));
                //System.out.println(vo.getUrlType());
                // vo.setUrlType("3507");
                if (StringUtils.isNoneBlank(vo.getUrlType())) {
                    if (vo.getUrlType().equals(AddressType.FZS.getValue())) {
                        urlType = AddressType.FZ.getValue();
                        address = PropertiesUtil.getConfValue("FZ");
                    } else if (vo.getUrlType().equals(AddressType.QZS.getValue())) {
                        urlType = AddressType.QZ.getValue();
                        address = PropertiesUtil.getConfValue("QZ");
                    } else if (vo.getUrlType().equals(AddressType.ZZS.getValue())) {
                        urlType = AddressType.ZZ.getValue();
                        address = PropertiesUtil.getConfValue("ZZ");
                    } else if (vo.getUrlType().equals(AddressType.PTS.getValue())) {
                        urlType = AddressType.PT.getValue();
                        address = PropertiesUtil.getConfValue("PT");
                    } else if (vo.getUrlType().equals(AddressType.NPS.getValue())) {
                        urlType = AddressType.NP.getValue();
                        address = PropertiesUtil.getConfValue("NP");
                    } else if (vo.getUrlType().equals(AddressType.SMS.getValue())) {
                        urlType = AddressType.SM.getValue();
                        address = PropertiesUtil.getConfValue("SM");
                    } else if (vo.getUrlType().equals(AddressType.CS.getValue())) {
                        urlType = AddressType.CS.getValue();
                        address = PropertiesUtil.getConfValue("CS");
                    }else{
                        urlType = AddressType.SXS.getValue();
                        address = PropertiesUtil.getConfValue("SX");
                    }
                    jsonParam.put("idNo", vo.getIdNo());
                    if (vo.getUrlType().equals("3501") || "14".equals(vo.getUrlType().substring(0,2))) {
                        jsonParam.put("orgId", vo.getOrgId());
                    } else {
                        jsonParam.put("orgId", vo.getOrgId().substring(3, vo.getOrgId().length()));
                    }
                    jsonParam.put("type", "2");
                    jsonParam.put("urlType", urlType);
                    if (address != null) {
                        String reslut = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                        if (StringUtils.isNotBlank(reslut)) {
                            // 将基卫中获取的区域编码转换为通用编码
                            boolean isTransformed = false;
                            JSONObject jsonObject = JSONObject.fromObject(reslut);
                            if (jsonObject != null) {
                                if (StringUtils.isNotBlank(jsonObject.get("entity").toString())) {
                                    String entityOne = jsonObject.get("entity").toString();
                                    JSONObject jsonObjectOne = JSONObject.fromObject(entityOne);
                                    if (StringUtils.isNotBlank(jsonObjectOne.get("success").toString())) {
                                        boolean success = Boolean.valueOf(jsonObjectOne.get("success").toString());
                                        if (success) {
                                            JkdaInfo jkdaInfo = JsonUtil.fromJson(jsonObjectOne.get("entity").toString(), JkdaInfo.class);
                                            if (jkdaInfo != null) {
                                                if (StringUtils.isNotBlank(jkdaInfo.getXzqydm())) {
                                                    CdAddressConfiguration addressConfiguration = this.sysDao.getCdAddressDao().findUniqueAdressByConfig(jkdaInfo.getXzqydm());
                                                    if (addressConfiguration != null) {
                                                        jkdaInfo.setXzqydm(addressConfiguration.getId());// 取通用编码
                                                    }
                                                    reslut = JsonUtil.toJson(jkdaInfo);
                                                    isTransformed = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (isTransformed) {
                                String finalReslut = "{\"message\":\"\",\"success\":true,\"errorcode\":\"\",\"type\":\"1\",\"errorType\":\"0\",\"entity\":" + reslut + "}";
                                this.jsons.setVo(finalReslut);
                            } else {
                                JSONObject jsonAll = JSONObject.fromObject(reslut);
                                if (StringUtils.isNotBlank(jsonAll.get("entity").toString())) {
                                    String entvo = jsonAll.get("entity").toString();
                                    this.jsons.setVo(entvo);
                                }
                            }
                        }
                    }
                }
            } else {
                this.jsons.setMsg("参数为空！");
                this.jsons.setCode("1001");
            }
        } catch (Exception e) {
            //e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }


    /**
     * 获取服务器当前时间
     *
     * @return
     */
    public String findDate() {
        try{
            WebSignVo vo = new WebSignVo();
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            String staDate = s.format(c.getTime());  //当前日期
            c.add(Calendar.YEAR, +1);//当前时间减去一年，即一年前的时间
            c.add(Calendar.DAY_OF_MONTH, -1);
            String endDate = s.format(c.getTime());  //后一年日期
            //先查询是否开启本年度签约协议
            CdUser user = this.getSessionUser();
            if(user != null){
                if(StringUtils.isNotBlank(user.getHospId())){
                    AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                    if(dept != null){
                        AppSignSetting setting = sysDao.getAppSignSettingDao().findByAreaCode(AreaUtils.getAreaCode(dept.getHospAreaCode(),"2"));
                        if(setting != null){
                            if("1".equals(setting.getSerOpenYear())){
                                // staDate = ExtendDate.getYYYY(Calendar.getInstance())+"-01-01";
                                endDate = ExtendDate.getYYYY(Calendar.getInstance())+"-12-31";
                            }
                        }
                    }
                }

            }
            vo.setFormDate(staDate);
            vo.setToDate(endDate);
            this.jsons.setVo(vo);
        }catch (Exception e){
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }

    /**
     * 服务套餐
     * 停止使用 服务包内容
     */

    public String findPk() {
        try {
//            AppServePackageQvo qvo = (AppServePackageQvo)getQvoJson(AppServePackageQvo.class);
//            qvo.setType("2");//1 系统 2 机构
//            List<AppServePackage> ls = sysDao.getAppServePackageDao().findList(qvo);
//            this.setjList(ls);
            AppServeSetmealQvo qvo = (AppServeSetmealQvo) getQvoJson(AppServeSetmealQvo.class);
            qvo.setRoleType("2");//非管理员admin
            qvo.setHospId(getSessionUser().getHospId());
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, getSessionUser().getHospId());
            if (dept != null) {
                qvo.setAreaCode(dept.getHospAreaCode());
            }
            qvo.setPageSize(999);
            List<AppServeSetmeal> ls = sysDao.getAppServeSetmealDao().findList(qvo);
            //南平地市特殊处理
            if ("3507".equals(qvo.getAreaCode().substring(0, 4))) {
                int i = 0;
                boolean flag = false;
                for (; i < ls.size(); i++) {
                    if ("np_default".equals(ls.get(i).getId())) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    AppServeSetmeal appServeSetmeal = ls.get(i);
                    ls.remove(i);
                    ls.add(0, appServeSetmeal);
                }
            }
            //莆田地市特殊处理
            if ("3503".equals(qvo.getAreaCode().substring(0, 4))) {
                int i = 0;
                boolean flag = false;
                for (; i < ls.size(); i++) {
                    if ("pt_20171218".equals(ls.get(i).getId())) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    AppServeSetmeal appServeSetmeal = ls.get(i);
                    ls.remove(i);
                    ls.add(0, appServeSetmeal);
                }
            }
            this.setjList(ls);
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), e);
        }
        return "jList";
    }

    /**
     * 服务套餐单例
     * 停止使用 服务包单例
     * cjw
     */
    public String findPkone() {
        try {
            String pkid = getRequest().getParameter("pkid");
            String sid = getRequest().getParameter("sid");
            Map<String, Object> map = new HashMap<String, Object>();
            String nr = "";
            AppServeSetmeal pkvo = null;
            if (StringUtils.isNotBlank(pkid)) {
                pkvo = (AppServeSetmeal) sysDao.getServiceDo().find(AppServeSetmeal.class, pkid);
                if (pkvo != null) {//修改用主键查询
                    //协议内容
                    //查询组合数据
                    if(StringUtils.isNotBlank(pkvo.getSersmGroupId())){
                        List<AppServeGroup> groups = sysDao.getAppServeGroupDao().findGroups(pkvo.getSersmGroupId());
                        if(groups != null && groups.size()>0){
                            for(AppServeGroup group:groups){
                                if(StringUtils.isNotBlank(group.getSergPkId())){//查询服务内容数据获取服务内容介绍
                                    List<AppServePackage> packages = sysDao.getAppServePackageDao().findPakeges(group.getSergPkId());
                                    if(packages != null){
                                        for(AppServePackage pac:packages){
                                            if(StringUtils.isNotBlank(pac.getSerpkRemark())){
                                                if(StringUtils.isBlank(nr)){
                                                    nr = pac.getSerpkRemark();
                                                }else{
                                                    //去除重复的服务内容介绍
                                                    if(!nr.contains(pac.getSerpkRemark())){
                                                        nr += pac.getSerpkRemark();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    /*if (StringUtils.isNotBlank(pkvo.getSersmPkValue())) {
                        String[] ss = pkvo.getSersmPkValue().split(";");
                        if (ss != null && ss.length > 0) {
                            for (String s : ss) {
                                String sql = "SELECT * from APP_SERVE_PACKAGE a where a.SERPK_VALUE=:pk ORDER BY a.SERPK_CREATE_TIME desc";
                                Map p = new HashMap();
                                p.put("pk", s);
                                List<AppServePackage> ls = sysDao.getServiceDo().findSqlMap(sql, p, AppServePackage.class);
                                if (ls != null && ls.size() > 0) {
                                    AppServePackage l = ls.get(0);
                                    nr = l.getSerpkRemark();
                                    break;
                                }
                            }
                        }
                    }*/
                }
            }
            //自费金额
            String zje = "0";
            //补贴金额
            if (StringUtils.isNotBlank(sid)) {
                String[] smid = sid.split(";");
                String sql = "SELECT a.* from APP_SERVE_SETMEAL a where a.ID in (:sid)";
                Map p = new HashMap();
                p.put("sid", smid);
                List<AppServeSetmeal> ls = sysDao.getServiceDo().findSqlMap(sql, p, AppServeSetmeal.class);

                String[] eids = null;
                if (ls != null && ls.size() > 0) {
                    for (AppServeSetmeal l : ls) {
                        if (StringUtils.isNotBlank(l.getSersmTotalOneFee())) {
                            zje = String.valueOf(Float.valueOf(zje) + Float.valueOf(l.getSersmTotalOneFee()));
                        }
                        String[] gid = l.getSersmJjId().split(";");
                        if (eids == null) {
                            eids = gid;
                        } else {
                            eids = Arrays.copyOf(eids, eids.length + gid.length);//数组扩容
                            System.arraycopy(gid, 0, eids, eids.length - gid.length, gid.length);
                        }
                    }
                    //
                    if (eids != null) {
                        String[] gids = null;
                        String sql2 = "SELECT a.* from APP_ECON_AND_GOV a where a.id in (:eid)";
                        Map p2 = new HashMap();
                        p2.put("eid", eids);
                        List<AppEconAndGov> ls2 = sysDao.getServiceDo().findSqlMap(sql2, p2, AppEconAndGov.class);
                        if (ls2 != null && ls2.size() > 0) {
                            for (AppEconAndGov l2 : ls2) {
                                String[] ts = l2.getEagGovValue().split(";");
                                if (gids == null) {
                                    gids = ts;
                                } else {
                                    gids = Arrays.copyOf(gids, gids.length + ts.length);//数组扩容
                                    System.arraycopy(ts, 0, gids, gids.length - ts.length, ts.length);
                                }
                            }
                        }
                        //补贴金额
                        if (gids != null) {
                            String sql3 = "SELECT * from APP_SERVE_GOV a where a.GOV_VALUE in (:gids);";
                            Map p3 = new HashMap();
                            p3.put("gids", gids);
                            List<AppServeGov> ls3 = sysDao.getServiceDo().findSqlMap(sql3, p3, AppServeGov.class);
                            map.put("bt", ls3);
                        }

                    }

                }
            }

            map.put("nr", nr);
            map.put("zje", zje);
            this.jsons.setMap(map);
            this.jsons.setVo(pkvo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "json";
    }

    /**
     * 初始签约配置
     */

    public String signcode() throws Exception {
        String hospid = getRequest().getParameter("hospid");
        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, hospid);
        if (dept != null) {
            String hospcode = dept.getHospAreaCode().substring(0, 4);
            CdCode code = sysDao.getCodeDao().findSign(hospcode);
            this.jsons.setVo(code);
        }

        return "json";
    }

    /**
     * 签约保存
     * 签约保存增加入参判断服务人群分类和人口经济性质不能为空  add by WangCHeng
     *
     * @return
     */

    public String signAdd() throws Exception {
        WebSignVo vo = (WebSignVo) getVoJson(WebSignVo.class);
        try {
            Map jsonParam = new HashMap();
            if (vo != null) {
                if (vo.getPersGroup() == null) {
                    this.newMsgJson("服务人群分类不能为空");
                    this.jsons.setCode("900");
                    return "json";
                }
                if (vo.getsJjType() == null) {
                    this.newMsgJson("人口经济性质不能为空");
                    this.jsons.setCode("900");
                    return "json";
                }
                if (StringUtils.trim(vo.getPatientTel()).length() != 11 && !StringUtils.trim(vo.getPatientTel()).matches("[0-9]+")) {
                    this.newMsgJson("联系方式必须是以0或1开头的11位固话或手机号!");
                    this.jsons.setCode("900");
                    return "json";
                }
                vo.setPatientTel(StringUtils.trim(vo.getPatientTel()));
                // 先判断 该居民是否已签约
                AppWebSignQvo qvo = new AppWebSignQvo();
                if (qvo != null) {
                    qvo.setPtidno(vo.getPatientIdno());
                    //qvo.setPtsscno(vo.getPatientCard());
                    AppWebSignFormListEntity fvo = sysDao.getAppSignformDao().signfind(qvo);
                    if (fvo == null) { // 为空 未签约
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, vo.getHospId());
                        // 判断 团队签约上限
                        if (StringUtils.isNoneBlank(vo.getTeamId())) {
                            if (dept != null) {
                                CdAddressPeople cdAdd = sysDao.getCdAddressPeopleDao().findByCacheCode(dept.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                                if (cdAdd != null) {
                                    AppCommQvo app = new AppCommQvo();
                                    app.setSignType("0");//普通人群查询
                                    if ("1".equals(cdAdd.getAreaSignWay())) { //团队上限签约人数
                                        app.setTeamId(vo.getTeamId());
                                        Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
                                        if (result >= Integer.parseInt(cdAdd.getAreaSignTop())) {
                                            this.newMsgJson("您的团队签约人数已达上限!,请重新选择签约团队!");
                                            this.jsons.setCode("900");
                                            return "json";
                                        }
                                        String[] pers = vo.getPersGroup();
                                        if (!"1".equals(pers[0])) {
                                            app.setSignType("1");//重点人群签约查询
                                            result = sysDao.getAppSignformDao().findSignXxCount(app);
                                            if (result >= Integer.parseInt(cdAdd.getAreaDisSignTop())) {
                                                this.newMsgJson("您的团队重点签约人数已达上限,请重新选择签约团队!");
                                                this.jsons.setCode("900");
                                                return "json";
                                            }
                                        }
                                    } else if ("0".equals(cdAdd.getAreaDisSignWay())) {
                                        app.setTeamId(vo.getTeamId());
                                        app.setDrId(vo.getDrId());
                                        Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
                                        if (result >= Integer.parseInt(cdAdd.getAreaSignTop())) {
                                            this.newMsgJson("该医生的签约人数已达上限,请重新选择签约医生!");
                                            this.jsons.setCode("900");
                                            return "json";
                                        }
                                        String[] pers = vo.getPersGroup();
                                        if (!"1".equals(pers[0])) {
                                            app.setSignType("1");//重点人群签约查询
                                            result = sysDao.getAppSignformDao().findSignXxCount(app);
                                            if (result >= Integer.parseInt(cdAdd.getAreaDisSignTop())) {
                                                this.newMsgJson("您的重点人群签约已上限,请重新选择签约医生!");
                                                this.jsons.setCode("900");
                                                return "json";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (dept != null) {
                            // 配置表
                            String hospcode = dept.getHospAreaCode().substring(0, 4);
                            CdCode code = sysDao.getCodeDao().findSign(hospcode);
                            if (code != null) {
                                long start = Calendar.getInstance().getTimeInMillis();
                                System.out.println("身份证:" + vo.getPatientIdno() + ",签约开始时间:" + ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                                //判断 是否与医保交互
                                vo.setSignWebState("0");
                                AppSignFormEntity avo = sysDao.getWebSignFormDao().websignAdd(vo);
                                if (avo != null) {
                                    if (avo.getCode().equals("900")) {
                                        this.jsons.setMsg("保存失败： " + avo.getMsg());
                                        this.jsons.setCode("900");
                                        return "json";
                                    }
                                    System.out.println("身份证:" + vo.getPatientIdno() + ",签约结束时间:" + ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                                    Long s = (Calendar.getInstance().getTimeInMillis() - start) / (1000 * 60);
                                    System.out.println("身份证:" + vo.getPatientIdno() + ",签约总耗时:" + s);
                                    if ("3501".equals(hospcode)) {  // 福州 调医保接口 1，门诊次数,2，医保年度费用
//                                        if ("1".equals(avo.getSignlx()) || "0".equals(avo.getSignlx())) {
//                                            start = Calendar.getInstance().getTimeInMillis();
//                                            System.out.println("身份证:"+vo.getPatientIdno()+",上年度支出费用开始时间:"+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
//                                            // sysDao.getWebSignFormDao().websignAdd(vo);
//                                            java.util.Random r = new java.util.Random(10);
//                                            int n = r.nextInt();
//                                            String lsh = avo.getSignHospId() + n;
//                                            String lshnum = lsh.replace("-", "");
//                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//                                            Calendar calendar = avo.getSignFromDate();
//                                            calendar.add(Calendar.YEAR, -1);
//                                            String formdate = sdf.format(calendar.getTime());
//                                            String todate = sdf.format(avo.getSignFromDate().getTime());
//                                            if ("1".equals(avo.getSignlx())) {
//                                                jsonParam.put("uuser", vo.getYuser());
//                                                jsonParam.put("paw", vo.getYpaw());
//                                            } else if ("0".equals(avo.getSignlx())) {
//                                                jsonParam.put("uuser", vo.getXuser());
//                                                jsonParam.put("paw", vo.getXpaw());
//                                            }
//                                            jsonParam.put("userIdno", avo.getSignPatientIdNo());
//                                            jsonParam.put("stratDate", formdate);
//                                            jsonParam.put("endDate", todate);
//                                            jsonParam.put("userFlowNo", lshnum);
//                                            // 上年度支出费用 接口
//                                            String urlLogins = PropertiesUtil.getConfValue("FZYSNDURL");
//                                            String strjson = HttpPostUtils.doPostJson(JacksonUtils.objToStr(jsonParam), urlLogins);
//                                            JSONObject jsons = JSONObject.fromObject(strjson);
//                                            System.out.println("身份证:"+vo.getPatientIdno()+",上年度支出费用结束时间:"+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
//                                            s = (Calendar.getInstance().getTimeInMillis() - start) / (1000 * 60);
//                                            System.out.println("身份证:"+vo.getPatientIdno()+",上年度支出费用总耗时:"+s);
//                                            if ("800".equals(jsons.get("msgCode"))) {
//                                                // 上年度po
//                                                AppOutpatientCost costvo = new AppOutpatientCost();
//                                                String strt = jsons.get("vo").toString();
//                                                ResultVo sign = JsonUtil.fromJson(strt, ResultVo.class);
//                                                costvo.setOutpatientIdno(avo.getSignPatientIdNo());
//                                                costvo.setOutpatientPatientId(avo.getSignPatientId());
//                                                if (StringUtils.isNotBlank(sign.getSmoney())) {
//                                                    costvo.setOutpatientFundCost(sign.getSmoney());
//                                                } else {
//                                                    costvo.setOutpatientFundCost("0");
//                                                }
//                                                costvo.setOutpatientFormDate(calendar);
//                                                costvo.setOutpatientToDate(avo.getSignFromDate());
//                                                costvo.setOutpatient_createdate(Calendar.getInstance());
//                                                sysDao.getServiceDo().add(costvo);
//                                                start = Calendar.getInstance().getTimeInMillis();
//                                                System.out.println("身份证:"+vo.getPatientIdno()+",本年度支出费用及门诊次数接口开始时间:"+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
//                                                // 本年度支出费用及门诊次数接口
//                                                String newdate = ExtendDate.getYMD_h_m_s(Calendar.getInstance());
//                                                jsonParam.put("jzsj", newdate);
//                                                String urlLogin = PropertiesUtil.getConfValue("FZYBNDURL");
//                                                String strjsont = HttpPostUtils.doPostJson(JacksonUtils.objToStr(jsonParam), urlLogin);
//                                                JSONObject jsont = JSONObject.fromObject(strjsont);
//                                                if ("800".equals(jsont.get("msgCode"))) {
//                                                    // 本年度po
//                                                    AppOutpatientNumber numbervo = new AppOutpatientNumber();
//                                                    String strs = jsont.get("vo").toString();
//                                                    ResultVo signvo = JsonUtil.fromJson(strs, ResultVo.class);
//                                                    numbervo.setOutpatientCreateDate(Calendar.getInstance());
//                                                    if (signvo.getJzsj() != null && signvo.getJzsj() != "") {
//                                                        SimpleDateFormat sdfrom = new SimpleDateFormat("yyyy-MM-dd");
//                                                        Date date = sdfrom.parse(signvo.getJzsj());
//                                                        Calendar endar = Calendar.getInstance();
//                                                        endar.setTime(date);
//                                                        numbervo.setOutpatientDoctorDate(endar);
//                                                    }
//                                                    numbervo.setOutpatientDoctorNumber(signvo.getJzcs());
//                                                    numbervo.setOutpatientFundCost(signvo.getBmoney());
//                                                    if (StringUtils.isNotBlank(signvo.getOne())) {
//                                                        numbervo.setOutpatientHospLevelOne(signvo.getOne());
//                                                    } else if (StringUtils.isNotBlank(signvo.getTwo())) {
//                                                        numbervo.setOutpatientHospLevel_Tow(signvo.getTwo());
//                                                    } else if (StringUtils.isNotBlank(signvo.getThree())) {
//                                                        numbervo.setOutpatientHospLevel_Three(signvo.getThree());
//                                                    }
//                                                    numbervo.setOutpatientPatientId(avo.getSignPatientId());
//                                                    numbervo.setOutpatientIdno(avo.getSignPatientIdNo());
//                                                    sysDao.getServiceDo().add(numbervo);
//                                                    System.out.println("身份证:"+vo.getPatientIdno()+",本年度支出费用及门诊次数接口结束时间:"+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
//                                                    s = (Calendar.getInstance().getTimeInMillis() - start) / (1000 * 60);
//                                                    System.out.println("身份证:"+vo.getPatientIdno()+",本年度支出费用及门诊次数接口总耗时:"+s);
//
//                                                }
//                                            }
//                                        }
                                    } else if ("3503".equals(hospcode)) {  //莆田 规则 修改 hfs_sign_ssc 表orgId
                                        AppHfsSignSsc ssc = sysDao.getWebSignFormDao().findHfsSignSscByPtIdNo(avo.getSignPatientIdNo());
                                        if (ssc != null) {
                                            if (StringUtils.isNotBlank(avo.getSignHospId())) {
                                                ssc.setPtOrgid(avo.getSignHospId().substring(3, avo.getSignHospId().length()));
                                                sysDao.getServiceDo().modify(ssc);
                                            }
                                        }
                                    }
                                    // 调接口开关 1 为打开 0 为关闭
                                    if (code.getCodeSort() != null && code.getCodeSort().equals("1")) {
                                        childrenImpl(vo, hospcode);
                                    }
                                    this.jsons.setVo(avo);
                                    this.jsons.setCode("800");
                                } else {
                                    this.newMsgJson("保存失败，没有return!");
                                    this.jsons.setCode("900");
                                }
                            } else {
                                this.newMsgJson("请先维护该地区签约配置!");
                                this.jsons.setCode("900");
                            }
                        }
                    } else {
                        this.newMsgJson("该居民已经在" + fvo.getSignDate() + "日于" + fvo.getHospname() + "签约。勿重复签约！");
                        this.jsons.setCode("900");
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            this.jsons.setMsg("保存失败： " + e.getMessage());
            this.jsons.setCode("900");
        }
        return "json";
    }

    /**
     * 查询特色服务包
     *
     * @return
     */
    public String pklist() {
        try {
            AppServePackageQvo qvo = (AppServePackageQvo) getVoJson(AppServePackageQvo.class);
            System.out.print("==========");
            System.out.print(qvo.getPkName());
            System.out.print("==========");
            CdUser user = this.getSessionUser();
            if (user != null) {
                if (!"admin".equals(user.getAccount()) && !"smadmin".equals(user.getAccount()) &&
                        !"zzadmin".equals(user.getAccount()) &&
                        !"fzadmin".equals(user.getAccount()) &&
                        !"ptadmin".equals(user.getAccount()) &&
                        !"npadmin".equals(user.getAccount()) &&
                        !"qzadmin".equals(user.getAccount())) {
                    qvo.setType("2");//非管理员admin
                    qvo.setHospId(user.getHospId());
                    if (AppRoleType.SHEQU.getValue().equals(user.getRole().get(0).getId())) {
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getHospId());
                        if (dept != null) {
                            if (StringUtils.isNotBlank(dept.getHospAreaCode())) {
                                qvo.setAreaCode(dept.getHospAreaCode().substring(0, 4) + "00000000");
                            }
                        }
                    }
                } else {
                    qvo.setType("1");
                }
                List<AppServePackage> ls = sysDao.getAppServePackageDao().findList(qvo);
                for (AppServePackage ll : ls) {
                    AppServeTab tab = sysDao.getAppServeTabDao().findByDept(user.getCdDeptId(), "4");
                    if (tab != null) {
                        if (ll.getId().equals(tab.getSerTabSerId())) {
                            ll.setSerpkTabState("1");
                        } else {
                            ll.setSerpkTabState("0");
                        }
                    }
                }
                this.getJsonLayui().setData(ls);
                this.getJsonLayui().setCount(qvo.getItemCount());
                //  jsons.setRowsQvo(ls,qvo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            // return "json";
        }
        return "jsonLayui";
    }


    /**
     * 签约修改
     */

    public String signModify() throws Exception {
        WebSignVo vo = (WebSignVo) getVoJson(WebSignVo.class);
        if (vo != null) {
            if (vo.getPersGroup() == null) {
                this.newMsgJson("服务人群分类不能为空");
                this.jsons.setCode("900");
                return "json";
            }
            if (vo.getsJjType() == null) {
                this.newMsgJson("人口经济性质不能为空");
                this.jsons.setCode("900");
                return "json";
            }
            AppSignForm avo = sysDao.getWebSignFormDao().websignmodify(vo);
            if (avo != null) {
                this.jsons.setVo(avo);
                this.jsons.setCode("800");
            }
        }
        return "json";
    }

    /**
     * 续签
     * 续签增加入参判断服务人群分类和人口经济性质不能为空  add by WangCHeng
     */
    public String signRenew() {
        try {
            WebSignVo vo = (WebSignVo) getVoJson(WebSignVo.class);
            if (vo != null) {
                if (vo.getPersGroup() == null) {
                    this.newMsgJson("服务人群分类不能为空");
                    this.jsons.setCode("900");
                    return "json";
                }
                if (vo.getsJjType() == null) {
                    this.newMsgJson("人口经济性质不能为空");
                    this.jsons.setCode("900");
                    return "json";
                }
                AppSignForm appSignForm = sysDao.getAppSignformDao().findSignFromByIdNo(vo.getPatientIdno(), null);
                if (appSignForm == null) {
                    AppSignForm avo = sysDao.getWebSignFormDao().websignRenew(vo);
                    if (avo != null) {
                        this.jsons.setVo(avo);
                        this.jsons.setCode("800");
                    }
                } else {
                    this.jsons.setMsg("true");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.jsons.setVo(e.getMessage());
            this.jsons.setCode("900");
        }

        return "json";
    }


    /**
     * 签约筛选
     *
     * @return
     */
    public String findsignsx() {

        try {
            AppHfsSignSscQvo avo = (AppHfsSignSscQvo) getJsonLay(AppHfsSignSscQvo.class);
            List<AppHfsSignSscEntity> hfsvo = sysDao.getAppSignformDao().findsignsx(avo);
            this.getJsonLayui().setData(hfsvo);
            this.getJsonLayui().setCount(avo.getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), e);
        }
        return "jsonLayui";
    }

    /**
     * Dmao
     * 签约查看
     *
     * @return
     */
    public String signLook() {
        try {

            AppWebSignQvo qvo = (AppWebSignQvo) getVoJson(AppWebSignQvo.class);
            WebSignSaveVo vo = sysDao.getAppSignformDao().signLook(qvo);
            if (vo != null) {
                List<AppLabelGroup> gvo = sysDao.getAppLabelGroupDao().findBySignGroup(qvo.getId(), "3");
                if (gvo != null && gvo.size() > 0) {
                    vo.setPersGroup(new String[gvo.size()]);
                    for (int i = 0; i < gvo.size(); i++) {
                        vo.getPersGroup()[i] = gvo.get(i).getLabelValue();
                    }
                }
                List<AppLabelEconomics> evo = sysDao.getAppLabelGroupDao().findBySignEconomics(qvo.getId(), "4");
                if (evo != null && evo.size() > 0) {
                    vo.setsJjType(new String[evo.size()]);
                    for (int i = 0; i < evo.size(); i++) {
                        vo.getsJjType()[i] = evo.get(i).getLabelValue();
                    }
                }

                this.jsons.setVo(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), e);
        }

        return "json";
    }

    /**
     * 建档立卡贫困人口签约信息
     * wangcheng
     *
     * @return
     */
    public String findPoorSign() {
        try {
            AppCommQvo qvo = (AppCommQvo) getJsonLay(AppCommQvo.class);
            if (qvo == null) {
                qvo = new AppCommQvo();
            }
            CdUser user = this.getSessionUser();
            if (user != null && (
                    user.getAccount().equals("admin") || user.getAccount().equals("qzadmin") ||
                            user.getAccount().equals("ptadmin") || user.getAccount().equals("smadmin") ||
                            user.getAccount().equals("npadmin") || user.getAccount().equals("fzadmin")
            )) {
                List<AppSignVo> ls = sysDao.getAppSignformDao().findPoorSign(qvo);
                this.getJsonLayui().setData(ls);
                this.getJsonLayui().setCount(qvo.getItemCount());
            } else {
                if (StringUtils.isNotEmpty(qvo.getTeamId())) {
                    AppTeam appTeam = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, qvo.getTeamId());
                    AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, appTeam.getTeamHospId());
                    String hospcode = dept.getHospAreaCode().substring(0, 4);
                    CdCode code = sysDao.getCodeDao().findSign(hospcode);
                    if (code != null) {
                        String[] pay = code.getCodeTitle().split(";");
                        qvo.setSignybpay(pay[0]);
                        qvo.setSigngwpay(pay[1]);
                        List<AppSignVo> ls = sysDao.getAppSignformDao().findPoorSign(qvo);
                        this.getJsonLayui().setData(ls);
                        this.getJsonLayui().setCount(qvo.getItemCount());
                    } else {
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("请初始配置基础数据表！");
                    }
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("缺失必要的参数！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLayui";
    }

    //居民签约信息查询
    public String findSignXxWeb() {
        try {
            AppCommQvo qvo = (AppCommQvo) getJsonLay(AppCommQvo.class);
            if (qvo == null) {
                qvo = new AppCommQvo();
            }
            CdUser user = this.getSessionUser();
            if (user != null && (
                    user.getAccount().equals("admin") || user.getAccount().equals("qzadmin") ||
                            user.getAccount().equals("ptadmin") || user.getAccount().equals("smadmin") ||
                            user.getAccount().equals("npadmin") || user.getAccount().equals("fzadmin")
            )) {
                List<AppSignVo> ls = sysDao.getAppSignformDao().findSignXxWeb(qvo);
                this.getJsonLayui().setData(ls);
                this.getJsonLayui().setCount(qvo.getItemCount());
            } else {
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, qvo.getSignHospId());
                String hospcode = dept.getHospAreaCode().substring(0, 4);
                CdCode code = sysDao.getCodeDao().findSign(hospcode);
                if (code != null) {
                    String[] pay = code.getCodeTitle().split(";");
                    qvo.setSignybpay(pay[0]);
                    qvo.setSigngwpay(pay[1]);
                    List<AppSignVo> ls = sysDao.getAppSignformDao().findSignXxWeb(qvo);
                    this.getJsonLayui().setData(ls);
                    this.getJsonLayui().setCount(qvo.getItemCount());
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("请初始配置基础数据表！");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLayui";
    }

    public String findMem() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            String id = getRequest().getParameter("id");
            List<AppTeamMember> ls = this.getSysDao().getServiceDo().loadByPk(AppTeamMember.class, "memTeamid", id);
            this.getJsons().setRows(ls);
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }

    /**
     * 查看协议内容
     *
     * @return
     */
    public String appAgreeMent() {
        try {
            AppAgreeMentQvo qvo = (AppAgreeMentQvo) this.getVoJson(AppAgreeMentQvo.class);
            if (qvo != null) {
                if (StringUtils.isNotBlank(qvo.getType())) {
                    if (qvo.getType().equals(CommonUseType.XITONG.getValue())) {
                        AppAgreement v = this.getSysDao().getAppAgreementDao().findByCityId(this.getAppPatientUser().getPatientCity());
                        logger.info("协议表Id" + v.getId());
                        if (v != null) {
                            Map<String, String> map = ExtendDate.getDateVaild(Calendar.getInstance());
                            String content = v.getMentContent();
                            content = content.replace("{1}", map.get("start"));
                            content = content.replace("{2}", map.get("end"));
                            this.getAjson().setMsg(content);
                        } else {
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无协议书,请联系管理员");
                        }
                    } else {
                        String addrxx = "";
                        String xyGroup = "";
                        String drName = "";
                        AppTeam team = (AppTeam) this.getSysDao().getServiceDo().find(AppTeam.class, qvo.getTeamId());
                        AppPatientUser patientUser = (AppPatientUser) this.getSysDao().getServiceDo().find(AppPatientUser.class, qvo.getPatientId());
                        if (team != null && patientUser != null) {
                            AppAgreement v = this.getSysDao().getAppAgreementDao().findByHospId(team.getTeamHospId(), qvo.getQyType());
                            AppHospDept dept = (AppHospDept) this.getSysDao().getServiceDo().find(AppHospDept.class, team.getTeamHospId());
                            if (v != null && dept != null) {
                                AppSignForm form = null;
                                if (StringUtils.isNotBlank(qvo.getSignId())) {// 优先使用主键查询
                                    form = (AppSignForm) this.getSysDao().getServiceDo().find(AppSignForm.class, qvo.getSignId());
                                } else {
                                    form = this.getSysDao().getAppSignformDao().getSignFormUserId(patientUser.getId());
                                }
                                AppDrUser druser = null;
                                if (form != null && form.getSignDrAssistantId() != null) {
                                    druser = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, form.getSignDrAssistantId());
                                }
                                if("350702".equals(AreaUtils.getAreaCode(dept.getHospAreaCode(),"3"))){
                                    xyGroup = CodeGroupConstrats.GROUP_YPXY;
                                }else{
                                    xyGroup = CodeGroupConstrats.GROUP_NPXY;
                                }
                                CdAddress address = (CdAddress) this.getSysDao().getServiceDo().find(CdAddress.class, dept.getHospUpcityareaId());
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
                                //团队医生
                                AppDrUser user = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, team.getTeamDrId());
                                AppDrUser doctor = null;
                                if (form != null && form.getSignDrId() != null) {
                                    //签约医生
                                    doctor = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, form.getSignDrId());
                                }
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
                                // 家庭住址
                                String patientAddress = "";
                                if (StringUtils.isNotBlank(patientUser.getPatientNeighborhoodCommittee())) {
                                    CdAddress streetAddress = this.sysDao.getCdAddressDao().findByCacheCode(patientUser.getPatientNeighborhoodCommittee());
                                    if (streetAddress != null) {
                                        patientAddress += streetAddress.getAreaName();
                                    }
                                } else if (StringUtils.isNotBlank(patientUser.getPatientStreet())) { // 街道
                                    CdAddress streetAddress = this.sysDao.getCdAddressDao().findByCacheCode(patientUser.getPatientStreet());
                                    if (streetAddress != null) {
                                        patientAddress += streetAddress.getAreaName();
                                    }
                                } else if (StringUtils.isNotBlank(patientUser.getPatientArea())) {// 区
                                    CdAddress areaAddress = this.sysDao.getCdAddressDao().findByCacheCode(patientUser.getPatientArea());
                                    if (areaAddress != null) {
                                        patientAddress += areaAddress.getAreaName();
                                    }
                                } else if (StringUtils.isNotBlank(patientUser.getPatientCity())) {// 市
                                    CdAddress cityAddress = this.sysDao.getCdAddressDao().findByCacheCode(patientUser.getPatientCity());
                                    if (cityAddress != null) {
                                        patientAddress += cityAddress.getAreaName();
                                    }
                                } else if (StringUtils.isNotBlank(patientUser.getPatientProvince())) {// 省
                                    CdAddress provinceAddress = this.sysDao.getCdAddressDao().findByCacheCode(patientUser.getPatientProvince());
                                    if (provinceAddress != null) {
                                        patientAddress += provinceAddress.getAreaName();
                                    }
                                }
                                if (StringUtils.isNotBlank(patientUser.getPatientAddress())) {
                                    //如果详细地址里是手写的就直接取该详细地址，否则就CdAddress,南平直接取详细地址
                                    if (StringUtils.isNotBlank(patientUser.getPatientCity()) && "3507".equals(patientUser.getPatientCity().substring(0, 4))) {
                                        patientAddress = patientUser.getPatientAddress();
                                    } else if (StringUtils.isNotBlank(patientUser.getPatientAddress()) && patientUser.getPatientAddress().indexOf("福建省") != -1) {
                                        patientAddress = patientUser.getPatientAddress();
                                    } else {
                                        patientAddress += patientUser.getPatientAddress();
                                    }
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
                                //签约医生电话
                                String drTel = "";
                                if (doctor != null) {
                                    if (StringUtils.isNotBlank(doctor.getDrTel())) {
                                        drTel = doctor.getDrTel();
                                    }
                                    drName = doctor.getDrName();
                                }
                                String hospTel = "";
                                if (StringUtils.isNotBlank(dept.getHospTel())) {
                                    hospTel = dept.getHospTel();
                                }
                                String patientCard = "";
                                if (StringUtils.isNotBlank(patientUser.getPatientCard())) {
                                    patientCard = patientUser.getPatientCard();
                                }
                                String patientjmda = "";
                                if (StringUtils.isNotBlank(patientUser.getPatientjmda())) {
                                    patientjmda = patientUser.getPatientjmda();
                                }
                                String drAssistantName = "";
                                String drAssistantTel = "";
                                if (druser != null) {
                                    if (StringUtils.isNotBlank(druser.getDrName())) {
                                        drAssistantName = druser.getDrName();
                                    }
                                    if (StringUtils.isNotBlank(druser.getDrTel())) {
                                        drAssistantTel = druser.getDrTel();
                                    }
                                }
                                content = content.replace("{01}", patientjmda);
                                content = content.replace("{02}", drAssistantName);
                                content = content.replace("{03}", drAssistantTel);
                                content = content.replace("{1}", patientName);
                                content = content.replace("{2}", patientIdno);
                                content = content.replace("{3}", patientAddress);
                                content = content.replace("{4}", patientTel);
                                content = content.replace("{5}", areaSname);
                                content = content.replace("{6}", hospName);
                                content = content.replace("{8}", drTel);
                                content = content.replace("{9}", hospTel);
                                content = content.replace("{31}", patientCard);
                                if (form != null) {
                                    content = content.replace("{97}",addrxx);
                                    content = content.replace("{40}", ExtendDate.getChineseYMD(form.getSignDate()));// 签约日期
                                    String ypxy = "";
//                                    boolean flag = false;
//                                    boolean flags = false;
                                    AppDrUser drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class, form.getSignDrId());
                                    if (drUser != null) {
                                        content = content.replace("{7}", drUser.getDrName());
                                    } else {
                                        content = content.replace("{7}", "");
                                    }
                                    if (StringUtils.isNotBlank(form.getSignNum())) {
                                        content = content.replace("{30}", form.getSignNum());
                                    } else {
                                        content = content.replace("{30}", "");
                                    }
                                    content = content.replace("{23}", teamName);
                                    if (form.getSignFromDate() != null) {
                                        content = content.replace("{10}", ExtendDate.getYMD(form.getSignFromDate()));
                                        content = content.replace("{11}", ExtendDate.getYMD(form.getSignToDate()));
                                    } else {
                                        content = content.replace("{10}", map.get("start"));
                                        content = content.replace("{11}", map.get("end"));
                                    }
                                    List<AppLabelEconomics> lsE = this.getSysDao().getAppLabelGroupDao().findBySignEconomics(form.getId(), "4");
                                    if (lsE != null && lsE.size() > 0) {
                                        for (AppLabelEconomics p : lsE) {
                                            if (EconomicType.JDLKPKRK.getValue().equals(p.getLabelValue())) {//建档立卡人口
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, "10");
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                         /*   if (EconomicType.JSTSJT.getValue().equals(p.getLabelValue())) {//计划生育特殊家庭人员
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YPXY, "11");
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if(EconomicType.DBH.getValue().equals(p.getLabelValue())){//低保五保人员
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YPXY,"12");
                                                if(code!=null){
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }*/
                                        }
                                    }
                                    List<AppLabelGroup> ls = this.getSysDao().getAppLabelGroupDao().findBySignGroup(form.getId(), "3");
                                    if (ls != null && ls.size() > 0) {
                                        for (AppLabelGroup p : ls) {
                                            if (ResidentMangeType.PTRQ.getValue().equals(p.getLabelValue())) {//普通人群
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.ETLZLS.getValue().equals(p.getLabelValue())) {//儿童(0~6岁)
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{14}", "☑");//为0-6岁儿童进行一类疫苗接种
                                                content = content.replace("{15}", "☑");//为0-6岁儿童进行常规的体格检查
                                                content = content.replace("{20}", "☑");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.YCF.getValue().equals(p.getLabelValue())) {//孕产妇
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{16}", "☑");//为孕产妇孕期进行基本保健指导，开展至少5次孕期保健服务和2次产后访视
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.LNR.getValue().equals(p.getLabelValue())) {//老年人
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{17}", "☑");//为65岁以上老年人每年1次健康体检，包括血生化、血尿常规、心电图、B超等辅助检查
                                                content = content.replace("{20}", "☑");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.GXY.getValue().equals(p.getLabelValue())) {//高血压
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{18}", "☑");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.TNB.getValue().equals(p.getLabelValue())) {//糖尿病
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{18}", "☑");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.YZJSZY.getValue().equals(p.getLabelValue())) {//严重精神障碍
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{19}", "☑");//对居家重性精神疾病患者提供随访服务和每年健康体检1次
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.JHB.getValue().equals(p.getLabelValue())) {//结核病
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{21}", "☑");//为结核病患者提供用药、饮食、运动、心理等健康指导
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.CJR.getValue().equals(p.getLabelValue())) {//残疾人
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.WEIZHI.getValue().equals(p.getLabelValue())) {//未知
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
                                        /*if (flag) {
                                            CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YPXY, "5");
                                            if (code != null) {
                                                ypxy += code.getCodeTitle();
                                            }
                                        }
                                        if (flags) {
                                            CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YPXY, "9");
                                            if (code != null) {
                                                ypxy += code.getCodeTitle();
                                            }
                                        }*/
                                        content = content.replace("{98}", ypxy);
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
                                        //  福州协议 特殊化多功能套打
                                        content = content.replace("{04}", "<div id='headerId'>");//页头
                                        content = content.replace("{06}", "<div id='tailId'>");//页未
                                        content = content.replace("{05}", "</div>");//页未

                                        if (StringUtils.isNotBlank(form.getUpHpis())) {
                                            if (SignFormType.POSSTATE.getValue().equals(form.getUpHpis()) || SignFormType.YITIJISTATE.getValue().equals(form.getUpHpis())) {
                                                List<AppSignSubtable> list = sysDao.getAppSignSubtableDao().findBySign(form.getId(), form.getUpHpis(), CommSF.YES.getValue());
                                                if (list != null && list.size() > 0) {
                                                    if (form.getSignHospId().equals("zz_6883")) {
                                                        String result = "<image style='display:block; position: absolute;bottom: 20px;left:450px;z-index: -1;' width='100' height='100' src='" + list.get(0).getImgBase64() + "'></image>";
                                                        content = content.replace("{999}", result);//甲方签名
                                                    } else {
                                                        String result = "<image  width='100' height='100' src='" + list.get(0).getImgBase64() + "'></image>";
                                                        content = content.replace("{999}", result);//甲方签名
                                                    }

                                                } else {
                                                    content = content.replace("{999}", "");//甲方签名
                                                }
                                            } else {
                                                content = content.replace("{999}", "");//甲方签名
                                            }
                                        } else {
                                            content = content.replace("{999}", "");//甲方签名
                                        }

                                        content = content.replace("{888}","");

                                        if (form != null && StringUtils.isNoneBlank(form.getSigntext())) { //不知道为什么这样写总觉得不太合理
                                            String text = "";
                                            String[] a = form.getSigntext().split("\n");
                                            for (int i = 0; i < a.length; i++) {
                                                if (i == 0) {
                                                    text = "<p style=\"text-indent:32px;line-height:27px\">" + a[i] + "</p>";
                                                } else {
                                                    text = text + "<p style=\"text-indent:32px;line-height:27px\">" + a[i] + "</p>";
                                                }
                                            }
                                            content = content.replace("{0}", text);//特色补充协议包
                                        } else {
                                            content = content.replace("{0}", "");//特色补充协议包
                                        }

                                    } else {
                                        content = content.replace("{999}", "");//甲方签名
                                        content = content.replace("{888}","");
                                    }
                                } else {
                                    content = content.replace("{40}", ExtendDate.getChineseYMD(Calendar.getInstance()));
                                    content = content.replace("{999}", "");//甲方签名
                                    content = content.replace("{888}","");
//                                    content = content.replace("{7}", teamName);
                                    content = content.replace("{23}", teamName);
                                    content = content.replace("{10}", map.get("start"));
                                    content = content.replace("{11}", map.get("end"));
                                    content = content.replace("{98}", "");
                                    content = content.replace("{97}","");
                                }
                                this.getAjson().setMsg(content);
                                this.getAjson().setUkey(form != null ? form.getId() : "");
                                if (StringUtils.isNotBlank(dept.getHospCachetWithinUrl())) {
                                    this.getAjson().setEntity(dept.getHospCachetWithinUrl());
                                }
                            } else {
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("查无协议书,请联系管理员");
                            }
                        } else {
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("参数格式错误");
                        }
                    }
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("类型不能为空");
                }
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }


    public String findTeam() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            String hospId = getRequest().getParameter("id");
//            List<AppTeam> ls = this.getSysDao().getServiceDo().loadByPk(AppTeam.class, "teamHospId", hospId);
            List<AppTeam> ls = this.getSysDao().getAppTeamDao().findAll(hospId);
            map.put("teamList", ls);
            this.getJsons().setMap(map);
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }

    public String findCommitteeByAreaCode() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            String hospAreaCode = getRequest().getParameter("hospAreaCode");
            List<CdAddress> list = this.getSysDao().getCdAddressDao().findCommitteeByAreaCode(hospAreaCode);
            map.put("committeeList", list);
            this.getJsons().setMap(map);
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }

    /**
     * 首页统计
     *
     * @return
     */
    public String appManageIndexCountNew() {
        try {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            ResidentVo qvo = (ResidentVo) getVoJson(ResidentVo.class);
            if (qvo != null) {
                //签约首页统计
                Map<String, Object> mapIndex = sysDao.getAppSignAnalysisDao().findSignAnalysisIndex(qvo);
                returnMap.put("signTotal", mapIndex);
                //签约首页统计
                Map<String, Object> map = sysDao.getAppSignAnalysisDao().findSignAnalysisList(qvo);
                returnMap.put("signTotalState", map);

                //重点人群统计
                String mapPers = this.getSysDao().getAppResidentAnalysisDao().findPersGroupCountFocusGroups(qvo);//服务分布
                List<ManageCountEntity> ls = JsonUtil.fromJson(mapPers, new TypeToken<List<ManageCountEntity>>() {
                }.getType());
                returnMap.put("mapPers", ls);
                //经济类型
                String economic = this.getSysDao().getAppSignAnalysisDao().findSignEconomicTypeList(qvo);//经济类型统计
                List<ManageCountEntity> lsEconomic = JsonUtil.fromJson(economic, new TypeToken<List<ManageCountEntity>>() {
                }.getType());
                returnMap.put("mapEconomic", lsEconomic);
                // 福州医保饼图
                String code = "";
                if (StringUtils.isNotBlank(qvo.getAreaId())) {
                    code = qvo.getAreaId().substring(0, 4);
                    if (code.equals("3501")) {
                        Map<String, Object> outpatient = this.getSysDao().getAppOutpatientDao().findSignOutpatientTypeList(qvo);//统计
                        returnMap.put("mapOutpatient", outpatient);
                    }
                } else {
                    AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, qvo.getHospId());
                    if (dept != null) {
                        code = dept.getHospAreaCode().substring(0, 4);
                        if (code.equals("3501")) {
                            Map<String, Object> outpatient = this.getSysDao().getAppOutpatientDao().findSignOutpatientTypeList(qvo);//统计
                            returnMap.put("mapOutpatient", outpatient);
                        }
                    }
                }

//                //统计数（随访量、健康指导、健康教育、求助量、未缴费人数）
//                Map<String,Object> mapCount= this.sysDao.getAppStatisticalAnalysisDao().findCount(qvo);
//                returnMap.put("mapCount",mapCount);
//                //排名
//                Map<String,Object> map2 = sysDao.getAppRankAnalysisDao().findRanking(qvo);
//                returnMap.put("rank",map2);
                //团队数据
               /* if(qvo.getHospLevel().equals("4")){
                    List<AppTeam> teams = this.sysDao.getAppTeamDao().findAll(qvo.getHospId());
                    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
                    for(int i=0;i<teams.size();i++){
                        Map<String,Object>  teamsum=sysDao.getAppSignAnalysisDao().getSianCountTeam(teams.get(i),qvo.getYearStart(),qvo.getYearEnd());
                       list.add(teamsum);
                    }
                    returnMap.put("teamMap",list);
                }*/
                if (qvo.getAreaId() != null && qvo.getAreaId() != "") {
                    CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(qvo.getAreaId(), "2"));
                    String sourType = "1";
                    if (cityCode != null) {
                        if ("0".equals(cityCode.getCodeTitle())) {//查询医保数据
                            sourType = "3";
                        }
                    }
                    String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
                    int count = sysDao.getAppArchivingCardPeopeDao().findCountBySourceType(sourType, null, areaCode);
                    if (areaCode.length() == 6 || areaCode.length() == 5) {
                        CdAddressPeopleVo cdVo = sysDao.getCdAddressPeopleDao().findByLevelCode(qvo.getAreaId(), ExtendDate.getYYYY(Calendar.getInstance()), "3");
                        returnMap.put("mapjklmsum", count);
                        returnMap.put("mapdbhsum", cdVo.getAreaEconomicDbh());
                        returnMap.put("maptkhsum", cdVo.getAreaEconomicTkh());
                        returnMap.put("mapjsjtsum", cdVo.getAreaEconomicJsjt());
                    } else {
                        CdAddressPeopleVo cdVo = sysDao.getCdAddressPeopleDao().findByLevelCode(qvo.getAreaId(), ExtendDate.getYYYY(Calendar.getInstance()), "2");
                        returnMap.put("mapjklmsum", count);
                        returnMap.put("mapdbhsum", cdVo.getAreaEconomicDbh());
                        returnMap.put("maptkhsum", cdVo.getAreaEconomicTkh());
                        returnMap.put("mapjsjtsum", cdVo.getAreaEconomicJsjt());
                    }
                    CdAddressPeople vo = sysDao.getCdAddressPeopleDao().findByCacheCode(qvo.getAreaId(), ExtendDate.getYYYY(Calendar.getInstance()));
                    if (vo != null) {
                        returnMap.put("maprksum", vo.getAreaPopulation() == null ? "0" : vo.getAreaPopulation());
                        returnMap.put("mapzdrqsum", vo.getAreaFocus() == null ? "0" : vo.getAreaFocus());

                    }
                } else {
                    AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, qvo.getHospId());
                    CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(dept.getHospAreaCode(), "2"));
                    String sourType = "1";
                    if (cityCode != null) {
                        if ("0".equals(cityCode.getCodeTitle())) {//查询医保数据
                            sourType = "3";
                        }
                    }
                    int count = sysDao.getAppArchivingCardPeopeDao().findCountBySourceType(sourType, dept.getId(), null);
                    if (dept != null) {
                        CdAddressPeople vo = sysDao.getCdAddressPeopleDao().findByCacheCode(dept.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                        if (vo != null) {
                            returnMap.put("maprksum", vo.getAreaPopulation() == null ? "0" : vo.getAreaPopulation());
                            returnMap.put("mapzdrqsum", vo.getAreaFocus() == null ? "0" : vo.getAreaFocus());
                            returnMap.put("mapjklmsum", count);
                            returnMap.put("mapdbhsum", vo.getAreaEconomicDbh() == null ? "0" : vo.getAreaEconomicDbh());
                            returnMap.put("maptkhsum", vo.getAreaEconomicTkh() == null ? "0" : vo.getAreaEconomicTkh());
                            returnMap.put("mapjsjtsum", vo.getAreaEconomicJsjt() == null ? "0" : vo.getAreaEconomicJsjt());
                        }
                    }
                }

                this.getAjson().setMap(returnMap);
                this.getAjson().setMsgCode("800");
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

    //履约统计
    public String findAppSingPerformanceDr() {
        try {
            AppCommQvo qvo = (AppCommQvo) getQvoJson(AppCommQvo.class);
            Map<String, Object> map = sysDao.getAppPerformanceStatisticsDao().findNewAppSingPerformanceDr(qvo);
            getJsons().setMap(map);
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }

        return "json";
    }


    /**
     * 提交变更
     *
     * @return
     */
    public String submitChange() {
        try {
            YsChangeCountQvo qvo = (YsChangeCountQvo) getVoJson(YsChangeCountQvo.class);
            if (qvo == null) {
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            } else {
                // AppDrUser drUser = this.getAppDrUser();
                // if(drUser!=null){
                // qvo.setDrId(drUser.getId());//当前申请医生id
                // qvo.setHospId(drUser.getDrHospId());//当前申请医生医院id changeStates
                String drid = qvo.getChangeDr().split(";;;")[0];
                qvo.setChangeDr(drid);
                String assid = null;
                if (StringUtils.isNoneBlank(qvo.getChangeDrAssistantId())) {
                    assid = qvo.getChangeDrAssistantId().split(";;;")[0];
                }
                qvo.setChangeDrAssistantId(assid);
                qvo.setChangeType("1");
                String result = sysDao.getAppSignformDao().changeStates(qvo);
                if ("false".equals(result)) {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("查无人员信息");
                } else {
                    this.getAjson().setMsg("提交申请成功");
                    this.getAjson().setMsgCode("800");
                    YsChangeSureQvo vo = new YsChangeSureQvo();
                    vo.setNum(result);
                    vo.setChangeType("1");
                    vo.setChangeDrAssistantId(qvo.getChangeDrAssistantId());
                    vo.setBatchOperatorId(qvo.getBatchOperatorId());
                    vo.setBatchOperatorName(qvo.getBatchOperatorName());
                    CdUser user = this.getSessionUser();
                    if(user != null){
                        vo.setChangeOperatorId(user.getDrId());
                        vo.setChangeOperatorName(user.getUserName());
                    }
                    sureChange(vo);
                }
                //  }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 同意或拒绝变更
     *
     * @return
     */
    public String sureChange(YsChangeSureQvo qvo) {
        try {
            //YsChangeSureQvo qvo = (YsChangeSureQvo)getAppJson(YsChangeSureQvo.class);
            if (qvo == null) {
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            } else {
               /* AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                    qvo.setDrName(drUser.getDrName());
                }*/
                qvo.setUpHpis("2");
                String msg = sysDao.getAppSignformDao().sureChange(qvo);
                if ("true".equals(msg)) {
                    this.getAjson().setMsg(qvo.getChangeType());
                    this.getAjson().setMsgCode("800");
                } else {
                    this.getAjson().setMsgCode("900");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


    //居民签约信息导出
    public String findSignXxWebEXCEL() {
        try {
            AppCommQvo qvo = (AppCommQvo) getJson(AppCommQvo.class);
            if (qvo == null) {
                qvo = new AppCommQvo();
            }
            CdUser user = this.getSessionUser();
            if (user.getAccount().equals("admin") || user.getAccount().equals("qzadmin") ||
                    user.getAccount().equals("ptadmin") || user.getAccount().equals("smadmin") ||
                    user.getAccount().equals("npadmin") || user.getAccount().equals("fzadmin")) {
                List<AppSignVo> ls = sysDao.getAppSignformDao().findSignXxWeb(qvo);
                this.getJsonLayui().setData(ls);
                this.getJsonLayui().setCount(qvo.getItemCount());
            } else {
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, qvo.getSignHospId());
                String hospcode = dept.getHospAreaCode().substring(0, 4);
                CdCode code = sysDao.getCodeDao().findSign(hospcode);
                if (code != null) {
                    String[] pay = code.getCodeTitle().split(";");
                    qvo.setSignybpay(pay[0]);
                    qvo.setSigngwpay(pay[1]);
                    qvo.setPageStartNo(1);
                    qvo.setPageSize(50000);
                    List<AppSignVo> ls = sysDao.getAppSignformDao().findSignXxWeb(qvo);
                    this.getJsonLayui().setData(ls);
                    this.getJsonLayui().setCount(qvo.getItemCount());
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("请初始配置基础数据表！");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLayui";
    }

    //居民签约信息查询导出
    public String findSignXxWebExcel() {
        try {
            AppCommQvo qvo = (AppCommQvo) getJsonLay(AppCommQvo.class);
            if (qvo == null) {
                qvo = new AppCommQvo();
            } else {
                if (StringUtils.isNotBlank(qvo.getPatientName())) {
                    qvo.setPatientName(java.net.URLDecoder.decode(qvo.getPatientName(), "UTF-8"));
                }
                if (StringUtils.isNotBlank(qvo.getPatientAddress())) {
                    qvo.setPatientAddress(java.net.URLDecoder.decode(qvo.getPatientAddress(), "UTF-8"));
                }
                if (StringUtils.isNotBlank(qvo.getBatchOperatorName())) {
                    qvo.setBatchOperatorName(java.net.URLDecoder.decode(qvo.getBatchOperatorName(), "UTF-8"));
                }
            }
            String type = getRequest().getParameter("typeNum");
            String numberCount = getRequest().getParameter("numberCount");
            if (type.equals("2")) {
                qvo.setPageSize(99999999);
            } else {
                qvo.setPageSize(Integer.valueOf(numberCount));
            }
            CdUser user = this.getSessionUser();
            if (user.getAccount().equals("admin") || user.getAccount().equals("qzadmin") ||
                    user.getAccount().equals("ptadmin") || user.getAccount().equals("smadmin") ||
                    user.getAccount().equals("npadmin") || user.getAccount().equals("fzadmin")) {
                List<AppSignVo> ls = sysDao.getAppSignformDao().findSignXxWeb(qvo);
                this.getJsonLayui().setData(ls);
                this.getJsonLayui().setCount(qvo.getItemCount());
            } else {
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, qvo.getSignHospId());
                String hospcode = dept.getHospAreaCode().substring(0, 4);
                CdCode code = sysDao.getCodeDao().findSign(hospcode);
                if (code != null) {
                    String[] pay = code.getCodeTitle().split(";");
                    qvo.setSignybpay(pay[0]);
                    qvo.setSigngwpay(pay[1]);
                    List<AppSignVo> ls = sysDao.getAppSignformDao().findSignXxWeb(qvo);
                    ExcelUtil<AppSignVo> ex = new ExcelUtil<AppSignVo>();
                    String[] headers = {"签约编号", "姓名", "年龄", "性别", "身份证", "联系电话","村/居委会", "详细地址", "人口经济性质", "服务类型", "医保类型", "签约时间", "协议时间", "签约状态", "签约团队", "签约操作人", "公卫补助", "医保预支付", "财政补贴", "自费", "签约医生"};
                    String[] datasetName = {"signNum", "name", "age", "sex", "patientIdno", "tel","patientNeighborhoodCommittee", "patientAddress", "signsJjType", "signPersGroup", "signlx", "signDate", "signToDate", "signState", "signTeamName", "batchOperatorName", "signgwpay", "signybpay", "signczpay", "signzfpay", "signDrName"};
                    getResponse().reset();
                    getResponse().setContentType("application/vnd..ms-excel");
                    getResponse().setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode("居民签约信息.xls", "utf-8"));
                    ex.exportExcel("居民签约信息", headers, datasetName, ls, getResponse().getOutputStream(), "");
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("请初始配置基础数据表！");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return null;
    }


    public String findSignRole() {
        try {
            String role = getRequest().getParameter("signrole");
            if (StringUtils.isNotBlank(role)) {
                AppHospDept ls = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, role);
                if (ls != null) {
                    if (StringUtils.isNotBlank(ls.getHospSerState()) && ls.getHospSerState().equals("1")) {
                        jsons.setResult("1000"); //开启签约权限
                    } else {
                        jsons.setResult("1001");
                    }
                    jsons.setCode("800");
                } else {
                    jsons.setCode("900");
                    jsons.setMsg("未找到该社区ID：" + ls.getId());
                }
            } else {
                jsons.setCode("900");
            }
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }

        return "json";
    }

    /**
     * cjw 山西 高平家版家签
     *
     * @return
     */

    public String familySignAdd() {
        try {
            boolean bool = true;
            WebFamilySignVo vo = (WebFamilySignVo) getVoJson(WebFamilySignVo.class);
            if (vo != null) {
                // 先判断 该居民是否已签约
                AppWebSignQvo qvo = new AppWebSignQvo();
                if (qvo != null) {
                    // 循环 查询 居民是否已签约
                    for (int i = 0; i < vo.getFamilyVo().size(); i++) {
                        qvo.setPtidno(vo.getFamilyVo().get(i).getPatientIdno());
                        //qvo.setPtsscno(vo.getPatientCard());
                        AppWebSignFormListEntity fvo = sysDao.getAppSignformDao().signfind(qvo);
                        if (fvo != null) {
                            bool = false;
                            this.newMsgJson("该家庭成员中" + fvo.getPatientName() + "已经于" + fvo.getSignDate() + "日在" + fvo.getHospname() + "签约。勿重复签约！");
                            this.jsons.setCode("900");
                        }
                    }

                    if (bool) {
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, vo.getHospId());
                        // 判断 团队签约上限
                        if (StringUtils.isNoneBlank(vo.getTeamId())) {
                            if (dept != null) {
                                CdAddressPeople cdAdd = sysDao.getCdAddressPeopleDao().findByCacheCode(dept.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                                if (cdAdd != null) {
                                    String areaSignTop = cdAdd.getAreaSignTop();//签约上限人数
                                    String areaSignWay = cdAdd.getAreaSignWay();//签约上限人数方式 0医生 1团队
                                    if (StringUtils.isNotBlank(areaSignTop) && StringUtils.isNotBlank(areaSignWay)) {
                                        AppCommQvo app = new AppCommQvo();
                                        app.setSignHospId(vo.getHospId());
                                        if ("0".equals(areaSignWay)) { //查询医生签约人数
                                            app.setTeamId(vo.getTeamId());
                                            app.setDrId(vo.getDrId());
                                            List<AppSignVo> ls = sysDao.getAppSignformDao().findSignXxWeb(app);
                                            if (ls != null && ls.size() > 0) {
                                                if (ls.size() >= Integer.parseInt(areaSignTop)) {
                                                    this.newMsgJson("该医生的签约人数已达上限,请更换医生!");
                                                    this.jsons.setCode("900");
                                                    return "json";
                                                }
                                            }
                                        } else if ("1".equals(areaSignWay)) { //查询团队签约人数
                                            app.setTeamId(vo.getTeamId());
                                            List<AppSignVo> ls = sysDao.getAppSignformDao().findSignXxWeb(app);
                                            if (ls != null && ls.size() > 0) {
                                                if (ls.size() >= Integer.parseInt(areaSignTop)) {
                                                    this.newMsgJson("该团队签约人数已达上限，请更换团队!");
                                                    this.jsons.setCode("900");
                                                    return "json";
                                                }
                                            }
                                        }
                                    } else {
                                        this.newMsgJson("指标维护不完整!");
                                        this.jsons.setCode("900");
                                        return "json";
                                    }
                                } else {
                                    this.newMsgJson("请先维护指标!");
                                    this.jsons.setCode("900");
                                    return "json";
                                }
                            }
                        }
                        // 判断完后进行 签约数据保存
                        vo.setSignWebState("0");
                        AppSignForm avo = sysDao.getWebSignFormDao().webFamilysignAdd(vo);
                        this.jsons.setVo(avo);
                        this.jsons.setCode("800");
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";

    }

    /**
     * 儿童或孕产妇出院信息 作为以后履约和履约统计
     *
     * @param signId  签约单id
     * @param userId  用户id
     * @param idNo    身份证号
     * @param type    类型（1为新生儿，需要传入母亲身份证号，2为孕产妇）
     * @param urlType 地市編碼 0591
     * @return
     */
    public void childrenImpl(WebSignVo vo, String hospcode) {
        SecurityCardAsyncBean bean = new SecurityCardAsyncBean();
        String urlType = "";
        if ("3501".equals(hospcode)) {
            urlType = AddressType.FZ.getValue();
        } else if ("3503".equals(hospcode)) {
            urlType = AddressType.PT.getValue();
        } else if ("3504".equals(hospcode)) {
            urlType = AddressType.SM.getValue();
        } else if ("3505".equals(hospcode)) {
            urlType = AddressType.QZ.getValue();
        } else if ("3506".equals(hospcode)) {
            urlType = AddressType.ZZ.getValue();
        } else if ("3507".equals(hospcode)) {
            urlType = AddressType.NP.getValue();
        }
        if (vo.getPersGroup() != null && vo.getPersGroup().length > 0) {
            String idno = "";
            String type = "";
            for (int i = 0; i < vo.getPersGroup().length; i++) {
                if (ResidentMangeType.ETLZLS.getValue().equals(vo.getPersGroup()[i].toString())) { //儿童
                    //计生接口（查询母亲身份证）

                    List<AppFamilyInfo> lsInfo = sysDao.getSecurityCardAsyncBean().getFetchFamily(vo.getPatientIdno(), vo.getPatientName(), vo.getBatchOperatorId(), vo.getBatchOperatorName(), DrPatientType.DR.getValue());
                    if (lsInfo != null && lsInfo.size() > 0) {
                        for (AppFamilyInfo ls : lsInfo) {
                            if (ls.getRelation().contains("妻")) {
                                idno = ls.getCode();
                            }
                        }
                    }
                    type = "1";
                } else if (ResidentMangeType.ETLZLS.getValue().equals(vo.getPersGroup()[i].toString())) { //孕产妇
                    idno = vo.getPatientIdno();
                    type = "2";
                }
            }
            // 基卫调接口  返回后数据保存 APP_SIGN_CHILD_OR_WOMEN
            bean.findChildOrWoman(vo.getSignId(), vo.getPatientId(), idno, type, urlType, vo.getDrOperatorId(), vo.getDrOperatorName(), DrPatientType.DR.getValue());
        }
    }

    /**
     * 死亡或解约
     * cxw
     *
     * @return
     */
    public String signDelPatient() {
        AppCommQvo qvo = (AppCommQvo) getJson(AppCommQvo.class);
        try {
            if (qvo == null) {
                this.jsons.setCode("900");
                this.newMsgJson("参数格式错误!");
            } else {
                if (StringUtils.isBlank(qvo.getPatientId())) {
                    this.jsons.setCode("900");
                    this.newMsgJson("患者主键不能空!");
                    return "json";
                }
                if (StringUtils.isBlank(qvo.getSignDelType())) {
                    this.jsons.setCode("900");
                    this.newMsgJson("解约类型不能为空!");
                    return "json";
                } else {
                    if (qvo.getSignDelType().equals(SignFormDelType.SW.getValue())) {
                        if (StringUtils.isBlank(qvo.getSignDieDate())) {
                            this.jsons.setCode("900");
                            this.newMsgJson("死亡时间不能为空!");
                            return "json";
                        }
                    }
                }
                if (StringUtils.isBlank(qvo.getSignDelReason())) {
                    this.jsons.setCode("900");
                    this.newMsgJson("解约原因不能为空!");
                    return "json";
                }
                if (qvo.getSignDelType().equals(SignFormDelType.SW.getValue())) {
                    AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserId(qvo.getPatientId(), "02");
                    if (form != null) {
                        form.setSignState(SignFormType.SC.getValue());
                        boolean flag = false;
                        if (qvo.getSignDelType().equals(SignFormDelType.SW.getValue())) {
                            form.setSignDieDate(ExtendDate.getCalendar(qvo.getSignDieDate()));
                            flag = true;
                        }
                        form.setSignDelType(qvo.getSignDelType());
                        form.setSignDelDate(Calendar.getInstance());
                        form.setSignDelReason(qvo.getSignDelReason());
                        this.sysDao.getServiceDo().modify(form);
                        if (flag) {
                            sysDao.getAppArchivingCardPeopeDao().changeArchiSignState(form.getSignPatientId(), null);
                        }
                        //查询是否有续签数据
                        if(!"0".equals(form.getSignGoToSignState())){//续签
                            AppSignForm signForm = sysDao.getAppSignformDao().findSignBySignState(new String[]{SignFormType.XQ.getValue(),SignFormType.YUZQ.getValue()},form.getSignPatientId());
                            if(signForm != null){
                                signForm.setSignState(SignFormType.YJY.getValue());
                                signForm.setSignSurrenderDate(Calendar.getInstance());
                                signForm.setSignUrrenderReason("解约已签约的续签或转签数据");
                                sysDao.getServiceDo().modify(signForm);
                            }
                        }
                        this.jsons.setMsg("解约成功!");
                        this.jsons.setCode("800");
                    } else {
                        this.jsons.setMsg("解约失败!");
                        this.jsons.setCode("900");
                    }

                } else {
                    AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserId(qvo.getPatientId(), "0");
                    if (form != null) {
                        if (form.getSignPayState().equals("0")) {
                            form.setSignState(SignFormType.YJY.getValue());
                            form.setSignSurrenderDate(Calendar.getInstance());
                            form.setSignUrrenderReason(qvo.getSignDelReason());
                            this.sysDao.getServiceDo().modify(form);
                            //非正常情况下解约去建档立卡花名册查询并解约
                            sysDao.getAppArchivingCardPeopeDao().changeArchiSignState(form.getSignPatientId(), null);
                            //查询是否有续签数据
                            if(!"0".equals(form.getSignGoToSignState())){//续签
                                AppSignForm signForm = sysDao.getAppSignformDao().findSignBySignState(new String[]{SignFormType.XQ.getValue(),SignFormType.YUZQ.getValue()},form.getSignPatientId());
                                if(signForm != null){
                                    signForm.setSignState(SignFormType.YJY.getValue());
                                    signForm.setSignSurrenderDate(Calendar.getInstance());
                                    signForm.setSignUrrenderReason("解约已签约的续签或转签数据");
                                    sysDao.getServiceDo().modify(signForm);
                                }
                            }
                            this.jsons.setMsg("解约成功!");
                            this.jsons.setCode("800");
                        } else {
                            if (form.getSignAreaCode().substring(0, 4).equals("3503")) {
                                this.jsons.setMsg("解约失败，已医保完成交互的暂未开通解约功能!");
                            } else {
                                this.jsons.setMsg("解约失败，该居民已签约已登记或已签约已缴费!");
                            }
                            this.jsons.setCode("900");
                        }
                    } else {
                        Object d = this.getRequest().getSession().getAttribute(Constant.ORG_ID);
                        if (d.toString().substring(0, 2).equals("pt")) {
                            this.jsons.setMsg("解约失败，已医保完成交互的暂未开通解约功能!");
                        } else {
                            this.jsons.setMsg("解约失败，该居民已签约已登记或已签约已缴费!");
                        }
                        this.jsons.setCode("900");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.jsons.setCode("900");
            this.newMsgJson(e.getMessage());
        }
        return "json";
    }

    public List getjList() {
        return jList;
    }

    public void setjList(List jList) {
        this.jList = jList;
    }

    /**
     * 批量打印协议
     * cxw
     *
     * @return
     */
    public String appAgreeMentAll() {
        try {
            AppAgreeMentQvo qvo = (AppAgreeMentQvo) this.getVoJson(AppAgreeMentQvo.class);
            if (qvo != null) {
                if (StringUtils.isNotBlank(qvo.getType())) {
                    if (qvo.getType().equals(CommonUseType.XITONG.getValue())) {
                        AppAgreement v = this.getSysDao().getAppAgreementDao().findByCityId(this.getAppPatientUser().getPatientCity());
                        if (v != null) {
                            Map<String, String> map = ExtendDate.getDateVaild(Calendar.getInstance());
                            String content = v.getMentContent();
                            content = content.replace("{1}", map.get("start"));
                            content = content.replace("{2}", map.get("end"));
                            this.getAjson().setMsg(content);
                        } else {
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无协议书,请联系管理员");
                        }
                    } else {
                        //批量分解
                        if (StringUtils.isNotBlank(qvo.getPatientId())) {
                            String[] signIds = qvo.getPatientId().split(",");
                            String contents = "";
                            String ids = "";
                            for (String signId : signIds) {
                                AppSignForm form = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, signId);
                                if (form != null) {
                                    AppTeam team = (AppTeam) this.getSysDao().getServiceDo().find(AppTeam.class, form.getSignTeamId());
                                    AppPatientUser patientUser = (AppPatientUser) this.getSysDao().getServiceDo().find(AppPatientUser.class, form.getSignPatientId());
                                    if (team != null && patientUser != null) {
                                        AppAgreement v = this.getSysDao().getAppAgreementDao().findByHospId(team.getTeamHospId(), qvo.getQyType());
                                        AppHospDept dept = (AppHospDept) this.getSysDao().getServiceDo().find(AppHospDept.class, team.getTeamHospId());
                                        if (v != null && dept != null) {
                                            AppDrUser druser = null;
                                            if (form.getSignDrAssistantId() != null) {
                                                druser = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, form.getSignDrAssistantId());
                                            }
                                            CdAddress address = (CdAddress) this.getSysDao().getServiceDo().find(CdAddress.class, dept.getHospUpcityareaId());
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
                                            if (StringUtils.isNotBlank(team.getTeamDrName())) {
                                                teamName = team.getTeamDrName();
                                            }
                                            String drTel = "";
                                            if (StringUtils.isNotBlank(user.getDrTel())) {
                                                drTel = user.getDrTel();
                                            }
                                            String hospTel = "";
                                            if (StringUtils.isNotBlank(dept.getHospTel())) {
                                                hospTel = dept.getHospTel();
                                            }
                                            String patientCard = "";
                                            if (StringUtils.isNotBlank(patientUser.getPatientCard())) {
                                                patientCard = patientUser.getPatientCard();
                                            }
                                            String patientjmda = "";
                                            if (StringUtils.isNotBlank(patientUser.getPatientjmda())) {
                                                patientjmda = patientUser.getPatientjmda();
                                            }
                                            String drAssistantName = "";
                                            String drAssistantTel = "";
                                            if (druser != null) {
                                                if (StringUtils.isNotBlank(druser.getDrName())) {
                                                    drAssistantName = druser.getDrName();
                                                }
                                                if (StringUtils.isNotBlank(druser.getDrTel())) {
                                                    drAssistantTel = druser.getDrTel();
                                                }
                                            }
                                            content = content.replace("{01}", patientjmda);
                                            content = content.replace("{02}", drAssistantName);
                                            content = content.replace("{03}", drAssistantTel);
                                            content = content.replace("{1}", patientName);
                                            content = content.replace("{2}", patientIdno);
                                            content = content.replace("{3}", patientAddress);
                                            content = content.replace("{4}", patientTel);
                                            content = content.replace("{5}", areaSname);
                                            content = content.replace("{6}", hospName);
                                            content = content.replace("{8}", drTel);
                                            content = content.replace("{9}", hospTel);
                                            content = content.replace("{31}", patientCard);
                                            content = content.replace("{40}", ExtendDate.getChineseYMD(form.getSignDate()));//签约日期
                                            if (form != null) {
                                                AppDrUser drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class, form.getSignDrId());
                                                if (drUser != null) {
                                                    content = content.replace("{7}", drUser.getDrName());
                                                } else {
                                                    content = content.replace("{7}", "");
                                                }
                                                if (StringUtils.isNotBlank(form.getSignNum())) {
                                                    content = content.replace("{30}", form.getSignNum());
                                                } else {
                                                    content = content.replace("{30}", "");
                                                }
                                                content = content.replace("{23}", teamName);
                                                if (form.getSignFromDate() != null) {
                                                    content = content.replace("{10}", ExtendDate.getYMD(form.getSignFromDate()));
                                                    content = content.replace("{11}", ExtendDate.getYMD(form.getSignToDate()));
                                                } else {
                                                    content = content.replace("{10}", map.get("start"));
                                                    content = content.replace("{11}", map.get("end"));
                                                }
                                                List<AppLabelGroup> ls = this.getSysDao().getAppLabelGroupDao().findBySignGroup(form.getId(), "3");
                                                if (ls != null && ls.size() > 0) {
                                                    for (AppLabelGroup p : ls) {
                                                        if (ResidentMangeType.PTRQ.getValue().equals(p.getLabelValue())) {//普通人群
                                                            content = content.replace("{12}", "☑");//建立健康档案
                                                            content = content.replace("{13}", "☑");//健康教育
                                                            content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                        }
                                                        if (ResidentMangeType.ETLZLS.getValue().equals(p.getLabelValue())) {//儿童(0~6岁)
                                                            content = content.replace("{12}", "☑");//建立健康档案
                                                            content = content.replace("{13}", "☑");//健康教育
                                                            content = content.replace("{14}", "☑");//为0-6岁儿童进行一类疫苗接种
                                                            content = content.replace("{15}", "☑");//为0-6岁儿童进行常规的体格检查
                                                            content = content.replace("{20}", "☑");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                                            content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                        }
                                                        if (ResidentMangeType.YCF.getValue().equals(p.getLabelValue())) {//孕产妇
                                                            content = content.replace("{12}", "☑");//建立健康档案
                                                            content = content.replace("{13}", "☑");//健康教育
                                                            content = content.replace("{16}", "☑");//为孕产妇孕期进行基本保健指导，开展至少5次孕期保健服务和2次产后访视
                                                            content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                        }
                                                        if (ResidentMangeType.LNR.getValue().equals(p.getLabelValue())) {//老年人
                                                            content = content.replace("{12}", "☑");//建立健康档案
                                                            content = content.replace("{13}", "☑");//健康教育
                                                            content = content.replace("{17}", "☑");//为65岁以上老年人每年1次健康体检，包括血生化、血尿常规、心电图、B超等辅助检查
                                                            content = content.replace("{20}", "☑");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                                            content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                        }
                                                        if (ResidentMangeType.GXY.getValue().equals(p.getLabelValue())) {//高血压
                                                            content = content.replace("{12}", "☑");//建立健康档案
                                                            content = content.replace("{13}", "☑");//健康教育
                                                            content = content.replace("{18}", "☑");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                                            content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                        }
                                                        if (ResidentMangeType.TNB.getValue().equals(p.getLabelValue())) {//糖尿病
                                                            content = content.replace("{12}", "☑");//建立健康档案
                                                            content = content.replace("{13}", "☑");//健康教育
                                                            content = content.replace("{18}", "☑");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                                            content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                        }
                                                        if (ResidentMangeType.YZJSZY.getValue().equals(p.getLabelValue())) {//严重精神障碍
                                                            content = content.replace("{12}", "☑");//建立健康档案
                                                            content = content.replace("{13}", "☑");//健康教育
                                                            content = content.replace("{19}", "☑");//对居家重性精神疾病患者提供随访服务和每年健康体检1次
                                                            content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                        }
                                                        if (ResidentMangeType.JHB.getValue().equals(p.getLabelValue())) {//结核病
                                                            content = content.replace("{12}", "☑");//建立健康档案
                                                            content = content.replace("{13}", "☑");//健康教育
                                                            content = content.replace("{21}", "☑");//为结核病患者提供用药、饮食、运动、心理等健康指导
                                                            content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                        }
                                                        if (ResidentMangeType.CJR.getValue().equals(p.getLabelValue())) {//残疾人
                                                            content = content.replace("{12}", "☑");//建立健康档案
                                                            content = content.replace("{13}", "☑");//健康教育
                                                            content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                        }
                                                        if (ResidentMangeType.WEIZHI.getValue().equals(p.getLabelValue())) {//未知
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
                                                    //  福州协议 特殊化多功能套打
                                                    content = content.replace("{04}", "<div id='headerId'>");//页头
                                                    content = content.replace("{06}", "<div id='tailId'>");//页未
                                                    content = content.replace("{05}", "</div>");//页未

                                                    content = content.replace("{999}", "");// 甲方签名（批量打印功能只有Web端才有）

                                                    if (StringUtils.isNoneBlank(form.getSigntext())) { //不知道为什么这样写总觉得不太合理
                                                        String text = "";
                                                        String[] a = form.getSigntext().split("\n");
                                                        for (int i = 0; i < a.length; i++) {
                                                            if (i == 0) {
                                                                text = "<p style=\"text-indent:32px;line-height:27px\">" + a[i] + "</p>";
                                                            } else {
                                                                text = text + "<p style=\"text-indent:32px;line-height:27px\">" + a[i] + "</p>";
                                                            }
                                                        }
                                                        content = content.replace("{0}", text);//特色补充协议包
                                                    } else {
                                                        content = content.replace("{0}", "");//特色补充协议包
                                                    }

                                                }
                                            } else {
                                                content = content.replace("{23}", teamName);
                                                content = content.replace("{10}", map.get("start"));
                                                content = content.replace("{11}", map.get("end"));
                                            }
                                            contents += content + "<div style=\"page-break-after:always;\"></div>";
                                            ids += signId + ",";//预览签约单id;
                                        }
                                    }
                                }
                            }
                            if (StringUtils.isNotBlank(ids)) {//用于判断是否打印签约协议
                                ids = ids.substring(0, ids.length() - 1);
                            }
                            this.getAjson().setMsg(contents);
                            this.getAjson().setUkey(ids);
                        }
                    }
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("类型不能为空");
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
     * 打印协议改变SignPrintFlag状态
     * cxw
     */
    public void changeSignPrintFlag() throws Exception {
        AppAgreeMentQvo qvo = (AppAgreeMentQvo) this.getVoJson(AppAgreeMentQvo.class);
        if (qvo != null) {
            if (StringUtils.isNotBlank(qvo.getPatientId())) {
                String[] signIds = qvo.getPatientId().split(",");
                List<AppSignForm> forms = sysDao.getAppSignformDao().findSignFormListByIds(signIds);
                if (forms.size() > 0) {
                    for (AppSignForm form : forms) {
                        if (form != null) {
                            if ("2".equals(form.getSignPrintFlag())) {

                            } else if ("1".equals(form.getSignPrintFlag())) {
                                form.setSignPrintFlag("2");
                            } else {
                                form.setSignPrintFlag("1");
                            }
                            sysDao.getServiceDo().modify(form);
                        }
                    }
                }
            }
        }
    }

    /**
     * 首页表格统计
     * cxw
     *
     * @return
     */
    public String appManageCountByBiaoGe() {
        try {
            ResidentVo qvo = (ResidentVo) getVoJson(ResidentVo.class);
            String level = qvo.getHospLevel();
            List<AppCountNewVo> countls = new ArrayList<AppCountNewVo>();
            if (qvo != null) {
                if (qvo.getHospLevel() != "4") {
                    List<AppHospDept> hospDepts = sysDao.getAppHospDeptDao().findByHospUpCityAreaId(qvo.getAreaId());
                    if (hospDepts != null && hospDepts.size() > 0) {
                        for (AppHospDept hosplist : hospDepts) {
                            AppCountNewVo countNewVo = new AppCountNewVo();
                            if (level.equals("3")) {
                                qvo.setHospId(hosplist.getId());
                                qvo.setAreaId(null);
                                qvo.setTeamId(null);
                            }
                            if (level.equals("2")) {
                                qvo.setAreaId(hosplist.getHospAreaCode());
                                qvo.setHospId(null);
                                qvo.setTeamId(null);
                            }
                            qvo.setHospLevel(hosplist.getHospLevel());
                            qvo.setArerCode(hosplist.getHospAreaCode());
                            countNewVo.setOrgId(hosplist.getId());
                            countNewVo.setOrgName(hosplist.getHospName());
                            countNewVo.setHospAreaCode(hosplist.getHospAreaCode());
                            countNewVo.setHospLevel(hosplist.getHospLevel());
                            //签约首页统计
                            Map<String, Object> mapIndex = sysDao.getAppSignAnalysisDao().findSignAnalysisIndex(qvo);
                            int rksum = (int) mapIndex.get("areaReg");
                            int zdrqsum = (int) mapIndex.get("areaFoucs");
                            int qysum = (int) mapIndex.get("yqy");
                            int qyzdrq = (int) mapIndex.get("zdrqyqy");
                            countNewVo.setRksum(rksum);
                            countNewVo.setZdrqsum(zdrqsum);
                            countNewVo.setQysum(qysum);
                            countNewVo.setQyzdrq(qyzdrq);
                            double qyrkl = 0;
                            if (rksum > 0) {
                                qyrkl = MyMathUtil.round(MyMathUtil.div(Double.valueOf(qysum), Double.valueOf(rksum), 4) * 100, 4);//签约率
                                countNewVo.setQyrkl(qyrkl + "");
                            } else {
                                countNewVo.setQyrkl("0.0");
                            }
                            double qyzdrql = 0;
                            if (zdrqsum > 0) {
                                qyzdrql = MyMathUtil.round(MyMathUtil.div(Double.valueOf(qyzdrq), Double.valueOf(zdrqsum), 4) * 100, 4);//重点人群签约率
                                countNewVo.setQyzdrql(qyzdrql + "");
                            } else {
                                countNewVo.setQyzdrql("0.0");
                            }
                            int jdlksum = 0;
                            if (hosplist.getHospAreaCode() != null && hosplist.getHospAreaCode() != "") {
                                if (level.equals("3")) {
                                    CdAddressPeople vo = sysDao.getCdAddressPeopleDao().findByCacheCode(hosplist.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                                    if (vo != null) {
                                        if (StringUtils.isNotBlank(vo.getAreaEconomicJklm())) {
                                            jdlksum = Integer.valueOf(vo.getAreaEconomicJklm());
                                        }
                                        countNewVo.setJdlksum(jdlksum);
                                    }
                                }
                                if (level.equals("2")) {
                                    CdAddressPeopleVo cdVo = sysDao.getCdAddressPeopleDao().findByLevelCode(hosplist.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()), "3");
                                    if (cdVo != null) {
                                        if (StringUtils.isNotBlank(cdVo.getAreaEconomicJklm())) {
                                            jdlksum = Integer.valueOf(cdVo.getAreaEconomicJklm());
                                        }
                                        countNewVo.setJdlksum(jdlksum);
                                    }
                                }


                            }
                            String economic = this.getSysDao().getAppSignAnalysisDao().findSignEconomicTypeList(qvo);//经济类型统计
                            List<ManageCountEntity> lsEconomic = JsonUtil.fromJson(economic, new TypeToken<List<ManageCountEntity>>() {
                            }.getType());
                            int qyjdlk = 0;
                            if (lsEconomic != null && lsEconomic.size() > 0) {
                                qyjdlk = Integer.valueOf(lsEconomic.get(1).getValue());
                                countNewVo.setQyjdlk(qyjdlk);
                            }
                            double qyjdlkl = 0;
                            if (jdlksum > 0) {
                                qyjdlkl = MyMathUtil.round(MyMathUtil.div(Double.valueOf(qyjdlk), Double.valueOf(jdlksum), 4) * 100, 4);//建档立卡签约率
                                countNewVo.setQyjdlkl(qyjdlkl + "");
                            } else {
                                countNewVo.setQyjdlkl("0.0");
                            }
                            countls.add(countNewVo);
                        }
                    }
                }
                this.getJsonLayui().setData(countls);
            } else {
                this.getJsonLayui().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getJsonLayui().setMsg("系统错误,请联系管理员");
        }
        return "jsonLayui";
    }

    public String familymember() {
        try {
            String address = null;
            String urlType = "";
            FamilyMemberQvo vo = (FamilyMemberQvo) getJsonLay(FamilyMemberQvo.class);
            if (vo != null) {
                JSONObject jsonParam = new JSONObject();
                CloseableHttpClient client = HttpClients.createDefault();
                vo.setUrlType(vo.getHospAreaCode().substring(0, 4));
                //System.out.println(vo.getUrlType());
                // vo.setUrlType("3507");
                if (StringUtils.isNoneBlank(vo.getUrlType())) {
                    if (vo.getUrlType().equals(AddressType.FZS.getValue())) {
                        urlType = AddressType.FZ.getValue();
                        address = PropertiesUtil.getConfValue("FZFAMILY");
                    } else if (vo.getUrlType().equals(AddressType.QZS.getValue())) {
                        urlType = AddressType.QZ.getValue();
                        address = PropertiesUtil.getConfValue("QZFAMILY");
                    } else if (vo.getUrlType().equals(AddressType.ZZS.getValue())) {
                        urlType = AddressType.ZZ.getValue();
                        address = PropertiesUtil.getConfValue("ZZFAMILY");
                    } else if (vo.getUrlType().equals(AddressType.PTS.getValue())) {
                        urlType = AddressType.PT.getValue();
                        address = PropertiesUtil.getConfValue("PTFAMILY");
                    } else if (vo.getUrlType().equals(AddressType.NPS.getValue())) {
                        urlType = AddressType.NP.getValue();
                        address = PropertiesUtil.getConfValue("NPFAMILY");
                    } else if (vo.getUrlType().equals(AddressType.SMS.getValue())) {
                        urlType = AddressType.SM.getValue();
                        address = PropertiesUtil.getConfValue("SMFAMILY");
                    } else if (vo.getUrlType().equals(AddressType.CS.getValue())) {
                        urlType = AddressType.CS.getValue();
                        address = PropertiesUtil.getConfValue("CSFAMILY");
                    }else{
                        urlType = AddressType.SXS.getValue();
                        address = PropertiesUtil.getConfValue("SXFAMILY");
                    }
                    if (StringUtils.isNotBlank(address)) {
                        jsonParam.put("patientidno", vo.getPatientidno());
                        jsonParam.put("patientjmda", vo.getPatientjmda());
                        jsonParam.put("urlType", urlType);
                        String reslut = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                        if (reslut != null) {
                            JSONObject json = JSONObject.fromObject(reslut);

                            if (json.get("msgCode").toString().equals("800")) {
                                // JSONArray array = JSONArray.fromObject(json.get("rows").toString());
                                List<FamilyMemberVo> ls = JsonUtil.fromJson(json.get("rows").toString(), new TypeToken<List<FamilyMemberVo>>() {
                                }.getType());
                                for (int i = 0; i < ls.size(); i++) {
                                    AppWebSignQvo qvo = new AppWebSignQvo();
                                    qvo.setPtidno(ls.get(i).getPatientidno());
                                    AppWebSignFormListEntity fvo = sysDao.getAppSignformDao().signfind(qvo);
                                    if (fvo == null) {
                                        ls.get(i).setPatientstate("否");
                                    } else {
                                        ls.get(i).setPatientstate("是");
                                    }
                                }
                                this.getJsonLayui().setData(ls);
                            } else {
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("接口返回错误！");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "jsonLayui";
    }

    /**
     * 医保本年度 接口
     *
     * @param avo
     * @return
     */

    public String OutpatientNumber(AppSignFormEntity avo) {
        try {
            Map jsonParam = new HashMap();
            java.util.Random r = new java.util.Random(10);
            int n = r.nextInt();
            String lsh = avo.getSignHospId() + n;
            String lshnum = lsh.replace("-", "");
            String newdate = ExtendDate.getYMD_h_m_s(Calendar.getInstance());
            jsonParam.put("jzsj", newdate);
            jsonParam.put("uuser", avo.getYuser());
            jsonParam.put("paw", avo.getYpaw());
            jsonParam.put("userIdno", avo.getSignPatientIdNo());
            jsonParam.put("userFlowNo", lshnum);

            String urlLogin = PropertiesUtil.getConfValue("FZYBNDURL");
            String strjsont = HttpPostUtils.doPostJson(JacksonUtils.objToStr(jsonParam), urlLogin);
            JSONObject jsont = JSONObject.fromObject(strjsont);
            if ("800".equals(jsont.get("msgCode"))) {
                // 本年度po
                AppOutpatientNumber numbervo = new AppOutpatientNumber();
                String strs = jsont.get("vo").toString();
                AppSignResidentVo signvo = JsonUtil.fromJson(strs, AppSignResidentVo.class);
                numbervo.setOutpatientCreateDate(Calendar.getInstance());
                SimpleDateFormat sdfrom = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdfrom.parse(signvo.getJzsj());
                Calendar endar = Calendar.getInstance();
                endar.setTime(date);
                numbervo.setOutpatientDoctorDate(endar);
                numbervo.setOutpatientDoctorNumber(signvo.getJzcs());
                numbervo.setOutpatientFundCost(signvo.getBmoney());
                if (StringUtils.isNotBlank(signvo.getOne())) {
                    numbervo.setOutpatientHospLevelOne(signvo.getOne());
                } else if (StringUtils.isNotBlank(signvo.getTwo())) {
                    numbervo.setOutpatientHospLevel_Tow(signvo.getTwo());
                } else if (StringUtils.isNotBlank(signvo.getThree())) {
                    numbervo.setOutpatientHospLevel_Three(signvo.getThree());
                }
                numbervo.setOutpatientPatientId(avo.getSignPatientId());
                numbervo.setOutpatientIdno(avo.getSignPatientIdNo());

                sysDao.getWebSignFormDao().OutpatientNumberUpdate(numbervo);
                //  sysDao.getServiceDo().add(numbervo);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ajson";
    }


    //签约审核信息查询
    public String findSignToexamine() {
        try {
            AppCommQvo qvo = (AppCommQvo) getJsonLay(AppCommQvo.class);
            if (qvo == null) {
                qvo = new AppCommQvo();
            }
            CdUser user = this.getSessionUser();
            if (user.getAccount().equals("admin") || user.getAccount().equals("qzadmin") ||
                    user.getAccount().equals("ptadmin") || user.getAccount().equals("smadmin") ||
                    user.getAccount().equals("npadmin") || user.getAccount().equals("fzadmin")) {
                List<AppSignVo> ls = sysDao.getAppSignformDao().findSignToexamine(qvo);
                this.getJsonLayui().setData(ls);
                this.getJsonLayui().setCount(qvo.getItemCount());
            } else {
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, qvo.getSignHospId());
                String hospcode = dept.getHospAreaCode().substring(0, 4);
                CdCode code = sysDao.getCodeDao().findSign(hospcode);
                if (code != null) {
                    String[] pay = code.getCodeTitle().split(";");
                    qvo.setSignybpay(pay[0]);
                    qvo.setSigngwpay(pay[1]);
                    List<AppSignVo> ls = sysDao.getAppSignformDao().findSignToexamine(qvo);
                    this.getJsonLayui().setData(ls);
                    this.getJsonLayui().setCount(qvo.getItemCount());
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("请初始配置基础数据表！");
                }
            }


        } catch (Exception e) {
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLayui";
    }

    // 拒签
    public String refuseadd() throws Exception {
        String id = getRequest().getParameter("id");
        String reason = getRequest().getParameter("reason");

        if (StringUtils.isNotBlank(id)) {
            AppSignForm from = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, id);
            from.setSignState("8");
            from.setSignOthnerReason(reason);
            AppSignForm avo = sysDao.getWebSignFormDao().websignrefuse(from);
            this.jsons.setCode("800");
            this.jsons.setVo(avo);
        }
        return "json";
    }

    /**
     * 调取基卫查询该居民死亡状态
     * WangCheng
     *
     * @return
     * @throws Exception
     */
    public String findDeathState() throws Exception {
        try {
            String address = null;
            String urlType = "";
            JSONObject jsonParam = new JSONObject();
            AppHealthCardRecodesVo vo = (AppHealthCardRecodesVo) getVoJson(AppHealthCardRecodesVo.class);
            if (vo != null) {
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, vo.getOrgId());
                CloseableHttpClient client = HttpClients.createDefault();
                vo.setUrlType(dept.getHospAreaCode().substring(0, 4));
                if (StringUtils.isNoneBlank(vo.getUrlType())) {
                    if (vo.getUrlType().equals(AddressType.FZS.getValue())) {
                        urlType = AddressType.FZ.getValue();
                        address = PropertiesUtil.getConfValue("FZurlDeath");
                    } else if (vo.getUrlType().equals(AddressType.QZS.getValue())) {
                        urlType = AddressType.QZ.getValue();
                        address = PropertiesUtil.getConfValue("QZurlDeath");
                    } else if (vo.getUrlType().equals(AddressType.ZZS.getValue())) {
                        urlType = AddressType.ZZ.getValue();
                        address = PropertiesUtil.getConfValue("ZZurlDeath");
                    } else if (vo.getUrlType().equals(AddressType.PTS.getValue())) {
                        urlType = AddressType.PT.getValue();
                        address = PropertiesUtil.getConfValue("PTurlDeath");
                    } else if (vo.getUrlType().equals(AddressType.NPS.getValue())) {
                        urlType = AddressType.NP.getValue();
                        address = PropertiesUtil.getConfValue("NPurlDeath");
                    } else if (vo.getUrlType().equals(AddressType.SMS.getValue())) {
                        urlType = AddressType.SM.getValue();
                        address = PropertiesUtil.getConfValue("SMurlDeath");
                    } else if (vo.getUrlType().equals(AddressType.CS.getValue())) {
                        urlType = AddressType.CS.getValue();
                        address = PropertiesUtil.getConfValue("CSurlDeath");
                    } else {
                        urlType = AddressType.SXS.getValue();
                        address = PropertiesUtil.getConfValue("SXurlDeath");
                    }
                    jsonParam.put("urlType", urlType);
                    jsonParam.put("df_id", vo.getDf_id());
                    if (address != null) {
                        String result = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                        if (StringUtils.isNotBlank(result)) {
                            JSONObject resultJson = JSONObject.fromObject(result);
                            this.jsons.setCode("800");
                            this.jsons.setResult(resultJson.get("vo").toString());
                        } else {
                            this.jsons.setCode("900");
                            this.jsons.setMsg("接口不通！");
                        }
                    }
                }
            } else {
                this.jsons.setCode("1001");
                this.jsons.setMsg("参数为空！");
            }
        } catch (Exception e) {
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }

    /**
     * 基卫已经死亡时家签自行死亡解约
     * WangCheng
     *
     * @return
     */
    public String signSurrender() {
        try {
            SignsurrenderVo signsurrenderVo = (SignsurrenderVo) getJson(SignsurrenderVo.class);
            if (signsurrenderVo != null) {
                AppSignForm vo = sysDao.getAppSignMotoeDao().findSignformJMDA(signsurrenderVo);
                if (vo != null) {
                    if (StringUtils.isNotEmpty(signsurrenderVo.getSignDieDate())) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                        Date date = formatter.parse(signsurrenderVo.getSignDieDate());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        vo.setSignDieDate(calendar);
                    }
                    vo.setSignDelReason("该居民已在基卫登记死亡");
                    vo.setSignDelType("1");
                    vo.setSignState("9");
                    vo.setSignOperatorName(signsurrenderVo.getSignOperatorName());
                    sysDao.getServiceDo().modify(vo);
                    //修改此人对应建档立卡表中的签约状态
                    sysDao.getAppArchivingCardPeopeDao().changeArchiSignState(vo.getSignPatientId(), null);
                    this.getAjson().setMsg("成功！");
                    this.getAjson().setMsgCode("800");
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("查无该居民！");
                }
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数为空！");
            }
        } catch (Exception e) {
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
            e.printStackTrace();
        }
        return "ajson";
    }

    /**
     * 调用家签接口工程获取社保信息
     * WangCHeng
     *
     * @return
     */
    public String getSecurityCard() {
        try {
            String address = null;
            String urlType = "";
            JSONObject jsonParam = new JSONObject();
            AppCommQvo qvo = (AppCommQvo) getJson(AppCommQvo.class);
            if (qvo != null) {
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, qvo.getOrgId());
                CloseableHttpClient client = HttpClients.createDefault();
                qvo.setUrlType(dept.getHospAreaCode().substring(0, 4));
                if (StringUtils.isNoneBlank(qvo.getUrlType())) {
                    if (qvo.getUrlType().equals(AddressType.FZS.getValue())) {
                        urlType = AddressType.FZ.getValue();
                        address = PropertiesUtil.getConfValue("FZurlCard");
                    } else if (qvo.getUrlType().equals(AddressType.QZS.getValue())) {
                        urlType = AddressType.QZ.getValue();
                        address = PropertiesUtil.getConfValue("QZurlCard");
                    } else if (qvo.getUrlType().equals(AddressType.ZZS.getValue())) {
                        urlType = AddressType.ZZ.getValue();
                        address = PropertiesUtil.getConfValue("ZZurlCard");
                    } else if (qvo.getUrlType().equals(AddressType.PTS.getValue())) {
                        urlType = AddressType.PT.getValue();
                        address = PropertiesUtil.getConfValue("PTurlCard");
                    } else if (qvo.getUrlType().equals(AddressType.NPS.getValue())) {
                        urlType = AddressType.NP.getValue();
                        address = PropertiesUtil.getConfValue("NPurlCard");
                    } else if (qvo.getUrlType().equals(AddressType.SMS.getValue())) {
                        urlType = AddressType.SM.getValue();
                        address = PropertiesUtil.getConfValue("SMurlCard");
                    } else if (qvo.getUrlType().equals(AddressType.CS.getValue())) {
                        urlType = AddressType.CS.getValue();
                        address = PropertiesUtil.getConfValue("CSurlCard");
                    }else{
                        urlType = AddressType.SXS.getValue();
                        address = PropertiesUtil.getConfValue("SXurlCard");
                    }
                    jsonParam.put("idCard", qvo.getPatientIdno());
                    jsonParam.put("urlType", urlType);
                    if (address != null) {
                        String reslut = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                        if (reslut != null && reslut != "") {
                            JSONObject jsonAll = JSONObject.fromObject(reslut);
                            this.getAjson().setVo(jsonAll.get("rows").toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ajson";
    }

    /**
     * 查询是否是建档立卡的人群
     * WangCheng
     *
     * @return
     */
    public String findArchivingCardPeople() {
        try {
            AppArchivingCardPeopleVo vo = (AppArchivingCardPeopleVo) getJson(AppArchivingCardPeopleVo.class);
            if (vo != null) {
                String jdSourceType = "1";
                CdUser user = this.getSessionUser();
                if (user != null) {
                    if (StringUtils.isNotBlank(user.getHospId())) {
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getHospId());
                        if (dept != null) {
                            if (StringUtils.isNotBlank(dept.getHospAreaCode())) {
                                CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(dept.getHospAreaCode(), "2"));
                                if (cityCode != null) {
                                    if ("0".equals(cityCode.getCodeTitle())) {
                                        jdSourceType = "3";
                                    }
                                }
                            }
                        }
                    }
                }
                AppArchivingCardPeople archivingCardPeople = sysDao.getAppArchivingCardPeopeDao().findPeopleByIdno(vo.getPtidno(), jdSourceType);
                Map<String, Object> map = new HashMap<String, Object>();
                if (archivingCardPeople != null) {
                    map.put("result", "1");
                } else {
                    map.put("result", "0");
                }
                this.getAjson().setMsgCode("800");
                this.getAjson().setMap(map);
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ajson";
    }

    public String findCommittee() throws Exception {
        String streetAreaCode = getRequest().getParameter("hospAreaCode");
        /*List<AddressHospEntity> list;
        if ("3507".equals(streetAreaCode.substring(0, 4))) {//南平直接取对照表
            list = sysDao.getCdAddressDao().findConfiguration(streetAreaCode);
        } else {
            list = sysDao.getCdAddressDao().findAssociateJw(streetAreaCode, null);
        }*/
        if (StringUtils.isNotBlank(streetAreaCode)) {
            List<CdAddress> list = this.getSysDao().getCdAddressDao().findSubAreas(streetAreaCode);
            this.getJsons().setRows(list);
            this.getAjson().setMsgCode("800");
        } else {
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("区域编码不能为空！");
        }
        return "json";
    }

    /**
     * 解约查询
     * WangCheng 新增查看历史解约信息
     *
     * @return
     * @throws Exception
     */
    public String findDisslution() throws Exception {
        AppCommQvo qvo = (AppCommQvo) getJsonLay(AppCommQvo.class);
        if (qvo == null) {
            qvo = new AppCommQvo();
        }
        List<AppSignVo> ls = sysDao.getAppSignformDao().findDisslution(qvo);
        if (ls != null) {
            this.getJsonLayui().setData(ls);
            this.getJsonLayui().setCount(qvo.getItemCount());
        } else {
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("请初始配置基础数据表！");
        }
        return "jsonLayui";
    }

    /**
     * 初始化 签约时间 天数
     *
     * @return
     * @throws Exception
     */
    public String findsignSetting() throws Exception {
        String streetAreaCode = getRequest().getParameter("hospAreaCode");
        if (StringUtils.isNotBlank(streetAreaCode)) {
            AppSignSetting vo = sysDao.getAppSignformDao().findsignSetting(streetAreaCode);
            if (vo != null) {
                this.getAjson().setMsgCode("800");
                this.getAjson().setVo(vo);
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("权限签约管理未配置！");
            }
        } else {
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("参数为空！");
        }

        return "ajson";
    }

    /**
     * 查看所有的签约信息、分省、市、区县、社区等级
     * WangCheng
     *
     * @return
     */
    public String findAllSignForm() {
        try {
            AppCommQvo qvo = (AppCommQvo) getJsonLay(AppCommQvo.class);
            if (qvo == null) {
                qvo = new AppCommQvo();
            }
            CdUser user = this.getSessionUser();
            if (user != null && (
                    user.getAccount().equals("admin") || user.getAccount().equals("qzadmin") ||
                            user.getAccount().equals("ptadmin") || user.getAccount().equals("smadmin") ||
                            user.getAccount().equals("npadmin") || user.getAccount().equals("fzadmin")
            )) {
                List<AppSignVo> ls = sysDao.getAppSignformDao().findAllSignForm(qvo);
                this.getJsonLayui().setData(ls);
                this.getJsonLayui().setCount(qvo.getItemCount());
            } else {
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, qvo.getSignHospId());
                String hospcode = dept.getHospAreaCode().substring(0, 4);
                CdCode code = sysDao.getCodeDao().findSign(hospcode);
                if (code != null) {
                    String[] pay = code.getCodeTitle().split(";");
                    qvo.setSignybpay(pay[0]);
                    qvo.setSigngwpay(pay[1]);
                    List<AppSignVo> ls = sysDao.getAppSignformDao().findAllSignForm(qvo);
                    this.getJsonLayui().setData(ls);
                    this.getJsonLayui().setCount(qvo.getItemCount());
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("请初始配置基础数据表！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLayui";
    }

    /**
     * 调用家签接口查询某个居民的所属服务人群类型
     * mxx
     *
     * @return
     */
    public String findFwType() {
        try {
            String urlType = null;// 地市
            String address = null;// 接口地址
            JSONObject jsonParam = new JSONObject();// 调用的参数
            AppHealthCardRecodesVo vo = (AppHealthCardRecodesVo) getVoJson(AppHealthCardRecodesVo.class);// 接收的参数
            if (vo != null) {
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, vo.getOrgId());
                CloseableHttpClient client = HttpClients.createDefault();
                vo.setUrlType(dept.getHospAreaCode().substring(0, 4));
                if (StringUtils.isNoneBlank(vo.getUrlType())) {
                    if (vo.getUrlType().equals(AddressType.FZS.getValue())) {
                        urlType = AddressType.FZ.getValue();
                        address = PropertiesUtil.getConfValue("FZurlFwType");
                    } else if (vo.getUrlType().equals(AddressType.QZS.getValue())) {
                        urlType = AddressType.QZ.getValue();
                        address = PropertiesUtil.getConfValue("QZurlFwType");
                    } else if (vo.getUrlType().equals(AddressType.ZZS.getValue())) {
                        urlType = AddressType.ZZ.getValue();
                        address = PropertiesUtil.getConfValue("ZZurlFwType");
                    } else if (vo.getUrlType().equals(AddressType.PTS.getValue())) {
                        urlType = AddressType.PT.getValue();
                        address = PropertiesUtil.getConfValue("PTurlFwType");
                    } else if (vo.getUrlType().equals(AddressType.NPS.getValue())) {
                        urlType = AddressType.NP.getValue();
                        address = PropertiesUtil.getConfValue("NPurlFwType");
                    } else if (vo.getUrlType().equals(AddressType.SMS.getValue())) {
                        urlType = AddressType.SM.getValue();
                        address = PropertiesUtil.getConfValue("SMurlFwType");
                    } else if (vo.getUrlType().equals(AddressType.CS.getValue())) {
                        urlType = AddressType.CS.getValue();
                        address = PropertiesUtil.getConfValue("CSurlFwType");
                    }else{
                        urlType = AddressType.SXS.getValue();
                        address = PropertiesUtil.getConfValue("SXurlFwType");
                    }
                    jsonParam.put("idNo", vo.getIdNo());
                    jsonParam.put("urlType", urlType);
                    System.out.println(jsonParam.toString());
                    String result = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                    if (result != null) {// 接口调用正常
                        JSONObject jsonAll = JSONObject.fromObject(result);
                        System.out.println(jsonAll.toString());
                        if ("800".equals(jsonAll.get("msgCode").toString())) {
                            this.jsons.setCode("800");
                            this.jsons.setVo(jsonAll.get("vo").toString());
                        } else {
                            this.jsons.setCode("900");
                            this.jsons.setMsg("接口错误!");
                        }
                    } else {// 接口不通
                        this.jsons.setCode("900");
                        this.jsons.setMsg("接口不通!");
                    }
                }
            } else {
                this.jsons.setCode("900");
                this.jsons.setMsg("参数不合法!");
            }
        } catch (Exception e) {
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }

    /**
     * 组装访问创建居民健康档案的接口地址
     *
     * @return
     * @throws Exception
     */
    public String getCreateDwellerfileUrl() throws Exception {
        CdUser user = this.getSessionUser();
        if (user == null) {
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("会话失效，请重新登录！");
        }
        String username = user.getAccount();
        String password = user.getUserPassword();
        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getHospId());
        // 获取配置文件中的基卫地址并加密参数
        String cityAreaCode = dept.getHospAreaCode().substring(0, 4);
        String sqylUrl = "";// 基卫地址
        if (cityAreaCode.equals(AddressType.FZS.getValue())) {
            sqylUrl = PropertiesUtil.getConfValue("FZurlSqyl");
        } else if (cityAreaCode.equals(AddressType.QZS.getValue())) {
            sqylUrl = PropertiesUtil.getConfValue("QZurlSqyl");
        } else if (cityAreaCode.equals(AddressType.ZZS.getValue())) {
            sqylUrl = PropertiesUtil.getConfValue("ZZurlSqyl");
        } else if (cityAreaCode.equals(AddressType.PTS.getValue())) {
            sqylUrl = PropertiesUtil.getConfValue("PTurlSqyl");
        } else if (cityAreaCode.equals(AddressType.NPS.getValue())) {
            sqylUrl = PropertiesUtil.getConfValue("NPurlSqyl");
        } else if (cityAreaCode.equals(AddressType.SMS.getValue())) {
            sqylUrl = PropertiesUtil.getConfValue("SMurlSqyl");
        } else if (cityAreaCode.equals(AddressType.CS.getValue())) {
            sqylUrl = PropertiesUtil.getConfValue("CSurlSqyl");
        }else {
            sqylUrl = PropertiesUtil.getConfValue("SXurlSqyl");
        }
        String method = "logonSxzzAction.do?method=jqjddy_logon";
        String params = "{\"username\":\"" + username + "\"," + "\"password\":\"" + password + "\"}";
        String message = AESR.parseByte2HexStr(AESR.encrypt(params, "phis%*%family%*%doctor", 16));
        String createDwellerfileUrl = sqylUrl + method + "&message=" + message;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("createDwellerfileUrl", createDwellerfileUrl);
        this.getAjson().setMsgCode("800");
        this.getAjson().setMap(map);
        return "ajson";
    }

    /**
     * 组装访问修改居民健康档案的接口地址
     *
     * @return
     * @throws Exception
     */
    public String getUpdateDwellerfileUrl() throws Exception {
        String df_id = this.getRequest().getParameter("df_id");
        if (StringUtils.isBlank(df_id)) {
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("居民档案号不能为空！");
        }
        CdUser user = this.getSessionUser();
        if (user == null) {
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("会话失效，请重新登录！");
        }
        String username = user.getAccount();
        String password = user.getUserPassword();
        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getHospId());
        // 获取配置文件中的基卫地址并加密参数
        String cityAreaCode = dept.getHospAreaCode().substring(0, 4);
        String sqylUrl = "";// 基卫地址
        if (cityAreaCode.equals(AddressType.FZS.getValue())) {
            sqylUrl = PropertiesUtil.getConfValue("FZurlSqyl");
        } else if (cityAreaCode.equals(AddressType.QZS.getValue())) {
            sqylUrl = PropertiesUtil.getConfValue("QZurlSqyl");
        } else if (cityAreaCode.equals(AddressType.ZZS.getValue())) {
            sqylUrl = PropertiesUtil.getConfValue("ZZurlSqyl");
        } else if (cityAreaCode.equals(AddressType.PTS.getValue())) {
            sqylUrl = PropertiesUtil.getConfValue("PTurlSqyl");
        } else if (cityAreaCode.equals(AddressType.NPS.getValue())) {
            sqylUrl = PropertiesUtil.getConfValue("NPurlSqyl");
        } else if (cityAreaCode.equals(AddressType.SMS.getValue())) {
            sqylUrl = PropertiesUtil.getConfValue("SMurlSqyl");
        } else if (cityAreaCode.equals(AddressType.CS.getValue())) {
            sqylUrl = PropertiesUtil.getConfValue("CSurlSqyl");
        }else {//山西省
            sqylUrl = PropertiesUtil.getConfValue("SXurlSqyl");
        }
        String method = "logonSxzzAction.do?method=jqjdxg_logon";
        String params = "{\"username\":\"" + username + "\"," + "\"password\":\"" + password + "\"," + "\"df_id\":\"" + df_id + "\"}";
        String message = AESR.parseByte2HexStr(AESR.encrypt(params, "phis%*%family%*%doctor", 16));
        String updateDwellerfileUrl = sqylUrl + method + "&message=" + message;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("updateDwellerfileUrl", updateDwellerfileUrl);
        this.getAjson().setMsgCode("800");
        this.getAjson().setMap(map);
        return "ajson";
    }

    /**
     * 添加健康档案调阅记录
     */
    public String addReadFileLog() throws Exception {
        String patientId = getRequest().getParameter("patientId");
        String readWay = getRequest().getParameter("readWay");
        AppPatientUser patient = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, patientId);
        if (patient == null) {
            throw new Exception("找不到该居民信息！");
        }
        CdUser user = this.getSessionUser();
        if (user == null) {
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("会话失效，请重新登录！");
        }
        AppReadFileLog readFileLog = new AppReadFileLog();
        readFileLog.setPatientIdno(patient.getPatientIdno());
        readFileLog.setPatientDfid(patient.getPatientjmda());
        readFileLog.setPatientName(patient.getPatientName());
        readFileLog.setReadUserId(user.getUserId());
        readFileLog.setReadUserName(user.getUserName());
        readFileLog.setReadWay(readWay);
        sysDao.getServiceDo().add(readFileLog);
        return "ajson";
    }

    /**
     * 查询健康档案调阅记录
     */
    public String findReadFileLog() throws Exception {
        AppWebSignQvo qvo = (AppWebSignQvo) getVoJson(AppWebSignQvo.class);
        if (qvo != null) {
            AppSignForm signForm = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, qvo.getId());
            if (signForm != null) {
                List<AppReadFileLog> list = sysDao.getAppSignformDao().findReadFileLog(signForm.getSignPatientIdNo(), signForm.getSignFromDate(), signForm.getSignToDate());
                this.getJsonLayui().setData(list);
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("签约单不存在！");
            }
        } else {
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("参数为空！");
        }
        return "jsonLayui";
    }

    /**
     * 初始化解约预警后台数据
     * WangCheng
     *
     * @return
     */
    public String initDissolutionWarning() {
        try {
            CdDissolutionWarningVo vo = (CdDissolutionWarningVo) getVoJson(CdDissolutionWarningVo.class);
            if (vo != null) {
                if (StringUtils.isNotEmpty(vo.getOrgId()) && StringUtils.isNotEmpty(vo.getDrId())) {
                    CdDissolutionWarning ls = sysDao.getCdMessageDao().getDissolutionWarning(vo);
                    if (ls == null) {
                        CdDissolutionWarning newWarning = new CdDissolutionWarning();
                        newWarning.setOrgId(vo.getOrgId());
                        newWarning.setDrId(vo.getDrId());
                        newWarning.setRedWarningDay("3");
                        newWarning.setRedWarningState("1");
                        newWarning.setYellowWarningDay("15");
                        newWarning.setYellowWarningState("1");
                        newWarning.setGreenWarningDay("30");
                        newWarning.setGreenWarningState("1");
                        sysDao.getServiceDo().add(newWarning);
                    }
                    this.newMsgJson(this.finalSuccessrMsg);
                }
            } else {
                this.jsons.setMsg("参数为空！");
                this.jsons.setCode("1001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "json";
    }

    /**
     * 解约预警数据统计
     * WangCheng
     *
     * @return
     */
    public String dissolutionCount() {
        try {
            CdDissolutionWarningVo vo = (CdDissolutionWarningVo) getVoJson(CdDissolutionWarningVo.class);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            if (vo != null) {
                if (StringUtils.isNotEmpty(vo.getOrgId()) && StringUtils.isNotEmpty(vo.getDrId())) {
                    CdDissolutionWarning ls = sysDao.getCdMessageDao().getDissolutionWarning(vo);
                    if (ls != null) {
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, vo.getOrgId());
                        if (dept != null) {
                            String hospId = null;
                            String areaCode = null;
                            if (AreaUtils.getAreaCode(dept.getHospAreaCode()).length() > 6) {
                                hospId = vo.getOrgId();
                            } else {
                                areaCode = AreaUtils.getAreaCode(dept.getHospAreaCode());
                            }
                            if (StringUtils.isNotEmpty(ls.getRedWarningDay()) && "1".equals(ls.getRedWarningState())) {
                                int redCount = sysDao.getCdMessageDao().countDissolutionNum(hospId, areaCode, ls.getRedWarningDay());
                                resultMap.put("redDay", ls.getRedWarningDay());
                                resultMap.put("redCount", redCount);
                            }
                            if (StringUtils.isNotEmpty(ls.getYellowWarningDay()) && "1".equals(ls.getYellowWarningState())) {
                                int yellowCount = sysDao.getCdMessageDao().countDissolutionNum(hospId, areaCode, ls.getYellowWarningDay());
                                resultMap.put("yellowDay", ls.getYellowWarningDay());
                                resultMap.put("yellowCount", yellowCount);
                            }
                            if (StringUtils.isNotEmpty(ls.getGreenWarningDay()) && "1".equals(ls.getGreenWarningState())) {
                                int greenCount = sysDao.getCdMessageDao().countDissolutionNum(hospId, areaCode, ls.getGreenWarningDay());
                                resultMap.put("greenDay", ls.getGreenWarningDay());
                                resultMap.put("greenCount", greenCount);
                            }
                            this.getAjson().setMap(resultMap);
                            this.getAjson().setMsgCode("800");
                        }
                    }
                }
            } else {
                this.jsons.setMsg("参数为空！");
                this.jsons.setCode("1001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ajson";
    }

    /**
     * 导出已解约居民的签约信息
     * WangCheng
     *
     * @return
     */
    public String exportDissolutionData() {
        try {
            AppCommQvo qvo = (AppCommQvo) getJsonLay(AppCommQvo.class);
            if (qvo == null) {
                qvo = new AppCommQvo();
            } else {
                if (StringUtils.isNotBlank(qvo.getPatientName())) {
                    qvo.setPatientName(java.net.URLDecoder.decode(qvo.getPatientName(), "UTF-8"));
                }
                if (StringUtils.isNotBlank(qvo.getPatientAddress())) {
                    qvo.setPatientAddress(java.net.URLDecoder.decode(qvo.getPatientAddress(), "UTF-8"));
                }
            }
            if (StringUtils.isNotEmpty(qvo.getNumberCount())) {
                qvo.setPageSize(Integer.valueOf(qvo.getNumberCount()));
            }

            List<AppSignVo> ls = sysDao.getAppSignformDao().listDissolutionData(qvo);
            ExcelUtil<AppSignVo> ex = new ExcelUtil<AppSignVo>();
            String[] headers = {"签约单主键", "姓名", "性别", "身份证", "社保卡号", "解约时间", "解约原因", "续签时间", "服务套餐主键", "服务套餐名称", "签约团队", "签约医生", "签约来源"};
            String[] datasetName = {"id", "name", "sex", "patientIdno", "patientCard", "signSurrenderDate", "signUrrenderReason", "renewDate", "packageId", "sersmName", "signTeamName", "signDrName", "upHpis"};
            getResponse().reset();
            getResponse().setContentType("application/vnd..ms-excel");
            getResponse().setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode("解约信息.xls", "utf-8"));
            ex.exportExcel("解约信息", headers, datasetName, ls, getResponse().getOutputStream(), "");
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return null;
    }

    /**
     * 上传文件
     * WangCheng
     *
     * @return
     */
    public String importFile() {
        try {
            String orgId = getRequest().getParameter("orgId");
            ExcelReader excelReader = new ExcelReader();
            InputStream inputStream = new FileInputStream(upExcel);
            Map<Integer, String> map = excelReader.readExcelContent(inputStream, 1);//读取Excel数据内容
            if (map.size() > 0) {
                CdUser user = this.getSessionUser();
                String result = this.getSysDao().getWebSignFormDao().addExcelRenew(map, user, orgId);
                this.newMsgJson(result);
            } else {
                this.newMsgJson("EXCEL无数据!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.jsons.setMsg("系统出错,请联系管理员!");
        }
        return "jsonUpload";
    }

    /**
     * 查看是否已经续签了
     * WangCheng
     *
     * @return
     */
    public String findRenew() {
        try {
            AppArchivingCardPeopleVo vo = (AppArchivingCardPeopleVo) getJson(AppArchivingCardPeopleVo.class);
            if (vo != null) {
                AppSignForm appSignForm = sysDao.getAppSignformDao().findSignFromByIdNo(vo.getPtidno(), vo.getDissolutionType());
                if (appSignForm != null) {
                    this.jsons.setMsg("true");
                }
            } else {
                this.jsons.setMsg("参数为空！");
                this.jsons.setCode("1001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "json";
    }

    /**
     * 查询签约起始日期默认一个月的权限
     * WangCheng
     *
     * @return
     */
    public String findSignStartSate() {
        try {
            AppCommQvo vo = (AppCommQvo) getJson(AppCommQvo.class);
            if (vo != null) {
                AppSignFormSetting appSignFormSetting = sysDao.getAppSignformDao().findSignStartSate(vo.getSignAreaCode());
                if (appSignFormSetting != null) {
                    this.jsons.setVo(appSignFormSetting);
                }
            } else {
                this.jsons.setMsg("参数为空！");
                this.jsons.setCode("1001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "json";
    }

    /**
     * 获取绩效考核列表
     * WangCheng
     *
     * @return
     */
    public String getAchievementsList() {
        try {
            String address = null;
            String urlType = "";
            JSONObject jsonParam = new JSONObject();
            AppAchievementsVo appAchievementsVo = (AppAchievementsVo) getJson(AppAchievementsVo.class);
            if (appAchievementsVo != null) {
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, appAchievementsVo.getOrgId());
                CloseableHttpClient client = HttpClients.createDefault();
                appAchievementsVo.setUrlType(dept.getHospAreaCode().substring(0, 4));
                if (StringUtils.isNotEmpty(appAchievementsVo.getUrlType())) {
                    if (appAchievementsVo.getUrlType().equals(AddressType.FZS.getValue())) {
                        urlType = AddressType.FZ.getValue();
                        address = PropertiesUtil.getConfValue("FZurlList");
                    } else if (appAchievementsVo.getUrlType().equals(AddressType.QZS.getValue())) {
                        urlType = AddressType.QZ.getValue();
                        address = PropertiesUtil.getConfValue("QZurlList");
                    } else if (appAchievementsVo.getUrlType().equals(AddressType.ZZS.getValue())) {
                        urlType = AddressType.ZZ.getValue();
                        address = PropertiesUtil.getConfValue("ZZurlList");
                    } else if (appAchievementsVo.getUrlType().equals(AddressType.PTS.getValue())) {
                        urlType = AddressType.PT.getValue();
                        address = PropertiesUtil.getConfValue("PTurlList");
                    } else if (appAchievementsVo.getUrlType().equals(AddressType.NPS.getValue())) {
                        urlType = AddressType.NP.getValue();
                        address = PropertiesUtil.getConfValue("NPurlList");
                    } else if (appAchievementsVo.getUrlType().equals(AddressType.SMS.getValue())) {
                        urlType = AddressType.SM.getValue();
                        address = PropertiesUtil.getConfValue("SMurlList");
                    } else if (appAchievementsVo.getUrlType().equals(AddressType.CS.getValue())) {
                        urlType = AddressType.CS.getValue();
                        address = PropertiesUtil.getConfValue("CSurlList");
                    }else{
                        urlType = AddressType.SXS.getValue();
                        address = PropertiesUtil.getConfValue("SXurlList");
                    }
                    jsonParam.put("patientIdNo", appAchievementsVo.getPatientIdNo());
                    jsonParam.put("startTime", appAchievementsVo.getStartTime());
                    jsonParam.put("endTime", appAchievementsVo.getEndTime());
                    jsonParam.put("urlType", urlType);
                    if (address != null) {
                        String reslut = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                        if (reslut != null && reslut != "") {
                            JSONObject jsonAll = JSONObject.fromObject(reslut);
                            this.getAjson().setVo(jsonAll.get("vo").toString());
                        }
                    }
                }
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ajson";
    }

    /**
     * 获取报告医生的名字
     * WangCheng
     *
     * @return
     */
    public String getDoctorName() {
        try {
            AppCommQvo qvo = (AppCommQvo) getJson(AppCommQvo.class);
            if (qvo != null) {
                AppDrUser appDrUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, qvo.getDrId());
                if (appDrUser != null) {
                    this.jsons.setVo(appDrUser);
                }
            } else {
                this.jsons.setMsg("参数为空！");
                this.jsons.setCode("1001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "json";
    }

    /**
     * 查看居民健康报告
     * WangCheng
     *
     * @return
     */
    public String findHealthReport(){
        try {
            WebHealthReportVo webHealthReportVo = (WebHealthReportVo) getJson(WebHealthReportVo.class);
            if (webHealthReportVo != null) {
                AppHealthReport appHealthReport = sysDao.getAppHealthAssessmentDao().findHealthReport(webHealthReportVo);
                if (appHealthReport != null) {
                    this.jsons.setVo(appHealthReport);
                }
            } else {
                this.jsons.setMsg("参数为空！");
                this.jsons.setCode("1001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "json";
    }

    /**
     * 健康评估报告保存
     * WangCheng
     *
     * @return
     */
    public String addHealthReport() {
        try {
            WebHealthReportVo webHealthReportVo = (WebHealthReportVo) getJson(WebHealthReportVo.class);
            if (webHealthReportVo != null) {
                WebHealthReportEntity reportEntity = sysDao.getAppHealthAssessmentDao().addHealthReport(webHealthReportVo);
                if (reportEntity != null) {
                    this.jsons.setVo(reportEntity);
                    this.jsons.setCode("800");
                } else {
                    this.newMsgJson("保存报告失败");
                    this.jsons.setCode("900");
                }
            } else {
                this.jsons.setMsg("参数为空！");
                this.jsons.setCode("1001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "json";
    }

    /**
     * WangCheng
     *
     * @return
     */
    public String initResult() {
        try {
            WebHealthReportVo webHealthReportVo = (WebHealthReportVo) getJson(WebHealthReportVo.class);
            if (webHealthReportVo != null) {
                AppHealthReport appHealthReport = sysDao.getAppHealthAssessmentDao().findHealthReport(webHealthReportVo);
                if (appHealthReport != null) {
                    this.jsons.setVo(appHealthReport);
                }
            } else {
                this.jsons.setMsg("参数为空！");
                this.jsons.setCode("1001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "json";
    }

    /**
     * 修改健康报告
     * WangCheng
     *
     * @return
     */
    public String modifyHealthReport() {
        try {
            WebHealthReportVo webHealthReportVo = (WebHealthReportVo) getJson(WebHealthReportVo.class);
            if (webHealthReportVo != null) {
                WebHealthReportEntity reportEntity = sysDao.getAppHealthAssessmentDao().modifyHealthReport(webHealthReportVo);
                if (reportEntity != null) {
                    this.jsons.setVo(reportEntity);
                    this.jsons.setCode("800");
                } else {
                    this.newMsgJson("修改报告失败");
                    this.jsons.setCode("900");
                }
            } else {
                this.jsons.setMsg("参数为空！");
                this.jsons.setCode("1001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "json";
    }

    /**
     * 导出健康评估报告pdf
     * WangCheng
     *
     * @return
     */
    public String exportHealthReport() {
        try {
            String signLableId = getRequest().getParameter("id");
            if (StringUtils.isNotEmpty(signLableId)) {
                WebHealthReportVo vo = new WebHealthReportVo();
                vo.setSignLableId(signLableId);
                AppHealthReport appHealthReport = sysDao.getAppHealthAssessmentDao().findHealthReport(vo);
                if (appHealthReport != null) {
                    String templateId = "";
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    List<JasperPrint> ls = new ArrayList<JasperPrint>();
                    String patientName = "";//居民姓名
                    String patientGender = "";//居民性别
                    String patientAge = "";//居民年龄
                    String reportDate = "";//报告时间
                    String signToDate = "";//签约协议日期
                    String patientIdNo = "";//身份证
                    String medicalHistory = "";//既往病史
                    String signPersGroup = "";//服务人群类型
                    String serverLog = "";//服务记录
                    String summary = "";//总结
                    String healthAssessment = "";//健康评价
                    String healthGuidance = "";//健康指导
                    String doctorName = "";//报告医生姓名
                    int gxyNum = 0;//高血压服务次数
                    int tnbNum = 0;//糖尿病服务次数
                    int jktjNum = 0;//健康体检服务次数
                    int jsbNum = 0;//精神病服务次数
                    int fjhFirstNum = 0;//肺结核第一次入户服务次数
                    int fjhSecondNum = 0;//肺结核第二次服务次数
                    if (StringUtils.isNotEmpty(appHealthReport.getPatientName())) {
                        patientName = appHealthReport.getPatientName();
                        map.put("patientName", patientName);
                    }
                    if (StringUtils.isNotEmpty(appHealthReport.getPatientGender())) {
                        patientGender = appHealthReport.getPatientGender();
                        map.put("patientGender", patientGender);
                    }
                    if (StringUtils.isNotEmpty(appHealthReport.getPatientAge())) {
                        patientAge = appHealthReport.getPatientAge();
                        map.put("patientAge", patientAge);
                    }
                    if (StringUtils.isNotEmpty(appHealthReport.getReportDate())) {
                        reportDate = appHealthReport.getReportDate();
                        map.put("reportDate", reportDate);
                    }
                    if (StringUtils.isNotEmpty(appHealthReport.getSignToDate())) {
                        signToDate = appHealthReport.getSignToDate();
                        map.put("signToDate", signToDate);
                    }
                    if (StringUtils.isNotEmpty(appHealthReport.getPatientIdNo())) {
                        patientIdNo = appHealthReport.getPatientIdNo();
                        map.put("patientIdNo", patientIdNo);
                    }
                    if (StringUtils.isNotEmpty(appHealthReport.getMedicalHistory())) {
                        medicalHistory = appHealthReport.getMedicalHistory();
                        map.put("medicalHistory", medicalHistory);
                    }
                    if (StringUtils.isNotEmpty(appHealthReport.getSignPersGroup())) {
                        signPersGroup = appHealthReport.getSignPersGroup();
                        map.put("signPersGroup", signPersGroup);
                    }
                    if (StringUtils.isNotEmpty(appHealthReport.getSummary())) {
                        summary = appHealthReport.getSummary();
                        map.put("summary", summary);
                    }
                    if (StringUtils.isNotEmpty(appHealthReport.getHealthAssessment())) {
                        healthAssessment = appHealthReport.getHealthAssessment();
                        map.put("healthAssessment", healthAssessment);
                    }
                    if (StringUtils.isNotEmpty(appHealthReport.getHealthGuidance())) {
                        healthGuidance = appHealthReport.getHealthGuidance();
                        map.put("healthGuidance", healthGuidance);
                    }
                    if (StringUtils.isNotEmpty(appHealthReport.getDoctorId())) {
                        AppDrUser appDrUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, appHealthReport.getDoctorId());
                        if (appDrUser != null) {
                            doctorName = appDrUser.getDrName();
                            map.put("doctorName", doctorName);
                        }
                    }
                    JSONObject jsonObject = JSONObject.fromObject(appHealthReport.getResultJson());
                    System.out.println(JSONObject.fromObject(null).equals(jsonObject.get("gxysfRecordDTOList")));
                    //if (jsonObject.get("gxysfRecordDTOList") == null || jsonObject.get("tnbsfRecordDTOList") == null || jsonObject.get("jkjtRecordDTOList") == null || jsonObject.get("jsbsfRecordDTOList") == null || jsonObject.get("fjhsfFirstRecordDTOList") == null || jsonObject.get("fjhsfRecordDTOList") == null) {//无任何随访记录
                    if(!jsonObject.has("gxysfRecordDTOList")){
                        map.put("serverLog", "无");
                        templateId = sysDao.getCdPaperTemplateDao().findTemplateByCode("ordinaryReport");
                        ls.add(sysDao.getCdPaperTemplateDao().getJasperPrint(templateId, map));// 获取报表信息
                    } else {
                        if (jsonObject.get("gxysfRecordDTOList") != null) {
                            JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("gxysfRecordDTOList"));
                            List<GxysfRecordDTO> gxyList = JSONArray.toList(jsonArray, GxysfRecordDTO.class);
                            if (gxyList != null && gxyList.size() > 0) {
                                for (int i = 0; i < gxyList.size(); i++) {
                                    String gxySerLog = "";//高血压服务记录
                                    gxySerLog += "服务" + (i+1) + ": ";
                                    gxySerLog += "服务日期: " + gxyList.get(i).getSfrq() + " ";
                                    gxySerLog += "服务医生: " + gxyList.get(i).getSfys() + " ";
                                    gxySerLog += "服务方式: " + gxyList.get(i).getSffl() + "\n";
                                    serverLog += gxySerLog;
                                    gxyNum = (i+1);
                                }
                            }
                        }
                        if (jsonObject.get("tnbsfRecordDTOList") != null) {
                            JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("tnbsfRecordDTOList"));
                            List<TnbsfRecordDTO> tnbList = JSONArray.toList(jsonArray, TnbsfRecordDTO.class);
                            if(tnbList != null && tnbList.size() > 0){
                                for (int i = 0; i < tnbList.size(); i++) {
                                    String tnbSerLog = "";//糖尿病服务记录
                                    tnbSerLog += "服务" + (gxyNum + i + 1) + ": ";
                                    tnbSerLog += "服务日期: " + tnbList.get(i).getSfrq() + " ";
                                    tnbSerLog += "服务医生: " + tnbList.get(i).getSfys() + " ";
                                    tnbSerLog += "服务方式: " + tnbList.get(i).getSffl() + "\n";
                                    serverLog += tnbSerLog;
                                    tnbNum = (i+1);
                                }
                            }
                        }
                        if (jsonObject.get("jkjtRecordDTOList") != null) {
                            JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("jkjtRecordDTOList"));
                            List<JkjtRecordDTO> jktjList = JSONArray.toList(jsonArray, JkjtRecordDTO.class);
                            if(jktjList != null && jktjList.size() > 0){
                                for (int i = 0; i < jktjList.size(); i++) {
                                    String jktjSerLog = "";//健康体检服务记录
                                    jktjSerLog += "服务" + (gxyNum + tnbNum + i + 1) + ": ";
                                    jktjSerLog += "服务日期: " + jktjList.get(i).getTjrq() + " ";
                                    jktjSerLog += "服务医生: " + jktjList.get(i).getZrys() + " ";
                                    jktjSerLog += "服务方式: 健康体检" + "\n";
                                    serverLog += jktjSerLog;
                                    jktjNum = (i+1);
                                }
                            }
                        }
                        if (jsonObject.get("jsbsfRecordDTOList") != null) {
                            JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("jsbsfRecordDTOList"));
                            List<JsbsfRecordDTO> jsbList = JSONArray.toList(jsonArray, JsbsfRecordDTO.class);
                            if(jsbList != null && jsbList.size() > 0){
                                for (int i = 0; i < jsbList.size(); i++) {
                                    String jsbSerLog = "";//精神病服务记录
                                    jsbSerLog += "服务" + (gxyNum + tnbNum + jktjNum + i + 1) + ": ";
                                    jsbSerLog += "服务日期: " + jsbList.get(i).getSfrq() + " ";
                                    jsbSerLog += "服务医生: " + jsbList.get(i).getSfys() + " ";
                                    jsbSerLog += "服务方式: " + jsbList.get(i).getSffl() + "\n";
                                    serverLog += jsbSerLog;
                                    jsbNum = (i+1);
                                }
                            }
                        }
                        if (jsonObject.get("fjhsfFirstRecordDTOList") != null) {
                            JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("fjhsfFirstRecordDTOList"));
                            List<FjhsfRecordDTO> fjhFirstList = JSONArray.toList(jsonArray, FjhsfRecordDTO.class);
                            if(fjhFirstList != null && fjhFirstList.size() > 0){
                                for (int i = 0; i < fjhFirstList.size(); i++) {
                                    String fjhFirstSerLog = "";//肺结核第一次入户服务记录
                                    fjhFirstSerLog += "服务" + (gxyNum + tnbNum + jktjNum + jsbNum + i + 1) + ": ";
                                    fjhFirstSerLog += "服务日期: " + fjhFirstList.get(i).getSfrq() + " ";
                                    fjhFirstSerLog += "服务医生: " + fjhFirstList.get(i).getPgys() + " ";
                                    fjhFirstSerLog += "服务方式: 随访" + "\n";
                                    serverLog += fjhFirstSerLog;
                                    fjhFirstNum = (i+1);
                                }
                            }
                        }
                        if (jsonObject.get("fjhsfRecordDTOList") != null) {
                            JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("fjhsfRecordDTOList"));
                            List<FjhsfRecordDTO> fjhSecondList = JSONArray.toList(jsonArray, FjhsfRecordDTO.class);
                            if(fjhSecondList != null && fjhSecondList.size() > 0){
                                for (int i = 0; i < fjhSecondList.size(); i++) {
                                    String fjhSecondSerLog = "";//肺结核第二次服务记录
                                    fjhSecondSerLog += "服务" + (gxyNum + tnbNum + jktjNum + jsbNum + fjhFirstNum + i + 1) + ": ";
                                    fjhSecondSerLog += "服务日期: " + fjhSecondList.get(i).getSfrq() + " ";
                                    fjhSecondSerLog += "服务医生: " + fjhSecondList.get(i).getPgys() + " ";
                                    fjhSecondSerLog += "服务方式: 随访" + "\n";
                                    serverLog += fjhSecondSerLog;
                                    fjhSecondNum = (i+1);
                                }
                            }
                        }
                        map.put("serverLog",serverLog);
                        templateId = sysDao.getCdPaperTemplateDao().findTemplateByCode("gxyReport");
                        ls.add(sysDao.getCdPaperTemplateDao().getJasperPrint(templateId, map));// 获取报表信息
                    }
//                    else if (appHealthReport.getSignPersGroup().contains("高血压") && !appHealthReport.getSignPersGroup().contains("糖尿病")) {//有高血压无糖尿病
//                    } else if (!appHealthReport.getSignPersGroup().contains("高血压") && appHealthReport.getSignPersGroup().contains("糖尿病")) {//无高血压有糖尿病
//                        templateId = sysDao.getCdPaperTemplateDao().findTemplateByCode("tnbReport");
//                    } else if (appHealthReport.getSignPersGroup().contains("高血压") && appHealthReport.getSignPersGroup().contains("糖尿病")) {//有高血压有糖尿病
//                        templateId = sysDao.getCdPaperTemplateDao().findTemplateByCode("gxyAndTnbReport");
//                    }
                    byte[] bytes = new byte[500000];
                    getResponse().reset();
                    ServletOutputStream ouputStream = getResponse().getOutputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, ls);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
                    exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                    exporter.exportReport();
                    bytes = baos.toByteArray();
                    //getResponse().setContentType("application/pdf");
                    String file = "健康评估报告.pdf";
                    String fileName = new String(file.getBytes("GBK"), "iso8859-1");
                    getResponse().setHeader("Content-Disposition", "attachment; filename=" + fileName);
                    baos.flush();
                    baos.close();
                    getResponse().setContentLength(bytes.length);
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();
                }
            } else {
                this.jsons.setMsg("参数为空！");
                this.jsons.setCode("1001");
            }
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        return null;
    }

    /**
     * 查询医生团队
     * @return
     */
    public String findDrTeam(){
        try {
            WebHealthReportVo webHealthReportVo = (WebHealthReportVo) getJson(WebHealthReportVo.class);
            if(webHealthReportVo != null){
                List<AppDrTeamEntity> list = sysDao.getAppTeamDao().findOneByTeam(webHealthReportVo.getDoctorId());
                this.jsons.setRows(list);
            }else {
                this.jsons.setMsg("参数为空！");
                this.jsons.setCode("1001");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "json";
    }



    /**
     * 修改居委会编码
     */
    public void updatePatientCommittee() {
        System.out.println("=====开始更新居民信息中的居委会编码=====");
        try {
            int updatedNum = 1;
            int orderNum = 1;
            int noRelationNum = 1;
            CloseableHttpClient client = HttpClients.createDefault();
            String address = PropertiesUtil.getConfValue("NP");// 南平
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("type", "2");
            jsonParam.put("urlType", AddressType.NP.getValue());

            //String streetCode = "350702113000";// 茫荡镇区划编码
            String streetCode = "350782003000";// 武夷街道区划编码
            List<AppPatientUser> list = sysDao.getAppPatientUserDao().listPatient(streetCode);
            for (AppPatientUser patient : list) {
                System.out.println("");
                System.out.println("=====这是第" + orderNum++ + "个居民！=====" + patient.getPatientIdno());

                /*if (!"35070219950713844X".equals(patient.getPatientIdno())) {
                    continue;
                }*/

                jsonParam.put("idNo", patient.getPatientIdno());
                String reslut = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                if (StringUtils.isNotBlank(reslut)) {
                    // 将基卫中获取的区域编码转换为通用编码
                    JSONObject jsonObject = JSONObject.fromObject(reslut);
                    if (jsonObject != null) {
                        if (StringUtils.isNotBlank(jsonObject.get("entity").toString())) {
                            String entityOne = jsonObject.get("entity").toString();
                            JSONObject jsonObjectOne = JSONObject.fromObject(entityOne);
                            if (StringUtils.isNotBlank(jsonObjectOne.get("success").toString())) {
                                boolean success = Boolean.valueOf(jsonObjectOne.get("success").toString());
                                if (success) {
                                    JkdaInfo jkdaInfo = JsonUtil.fromJson(jsonObjectOne.get("entity").toString(), JkdaInfo.class);
                                    if (jkdaInfo != null) {
                                        if (StringUtils.isNotBlank(jkdaInfo.getXzqydm())) {
                                            CdAddressConfiguration addressConfiguration = this.sysDao.getCdAddressDao().findUniqueAdressByConfig(jkdaInfo.getXzqydm());
                                            if (addressConfiguration != null) {
                                                // 将通用编码更新到居民信息中
                                                if (!patient.getPatientNeighborhoodCommittee().equals(addressConfiguration.getId())) {
                                                    System.out.println("修改前：" + patient.getPatientNeighborhoodCommittee());
                                                    System.out.println("基卫编码：" + jkdaInfo.getXzqydm());
                                                    System.out.println("通用编码：" + addressConfiguration.getId());
                                                    patient.setPatientNeighborhoodCommittee(addressConfiguration.getId());
                                                    sysDao.getServiceDo().modify(patient);
                                                    System.out.println("=====已有" + updatedNum++ + "人的居委会被更新！=====");
                                                } else {
                                                    System.out.println("编码正确，无需修改！");
                                                }
                                            } else {
                                                System.out.println("#####以下身份证号的区划编码无区划对照信息#####");
                                                System.out.println("#####没有对照关系的有：" + noRelationNum++ + "个#####");
                                                System.out.println("#####" + patient.getPatientIdno() + "#####");
                                                System.out.println("##############################");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 修改错误的续签数据
     */
    public void updateErrorSignData() {
        System.out.println("=====开始修改错误的续签数据=====");
        try {
            int orderNum = 1;
            int updatedNum = 1;
            Calendar signFromDateCalendar = Calendar.getInstance();
            Calendar signToDateCalendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String sql = "SELECT * FROM app_sign_form a WHERE a.SIGN_STATE = '98' " +
                    "AND DATEDIFF(a.SIGN_FROM_DATE, a.SIGN_DATE) > 31 ORDER BY a.SIGN_FROM_DATE";
            String sql2 = "SELECT * FROM app_sign_form a WHERE a.SIGN_PATIENT_IDNO = :idno and a.SIGN_STATE IN ('0', '2')";
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> map2 = new HashMap<>();
            List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
            for (AppSignForm signForm : list) {
                System.out.println("");
                System.out.println("=====这是第" + orderNum++ + "条签约数据！=====" + signForm.getSignPatientIdNo());
                map2.clear();
                map2.put("idno", signForm.getSignPatientIdNo());
                List<AppSignForm> list2 = sysDao.getServiceDo().findSqlMap(sql2, map2, AppSignForm.class);
                if (list2 != null && list2.size() > 0) {
                    System.out.println("=====该条续约单有对应的正在使用中的签约单，无需修改！=====");
                } else {
                    String signFromDate = ExtendDate.getYMD(signForm.getSignDate()) + " 08:00:00";
                    signFromDateCalendar.setTime(dateFormat.parse(signFromDate));
                    signForm.setSignFromDate(signFromDateCalendar);// 设置协议开始日期
                    String signToDate = ExtendDate.getYMD(signForm.getSignFromDate()) + " 08:00:00";
                    signToDateCalendar.setTime(dateFormat.parse(signToDate));
                    signToDateCalendar.add(Calendar.YEAR, 1);// 加1年
                    signToDateCalendar.add(Calendar.DATE, -1);// 减1天
                    signForm.setSignToDate(signToDateCalendar);// 设置协议截止日期
                    signForm.setSignState("0");// 预签约
                    sysDao.getServiceDo().modify(signForm);
                    System.out.println("=====修改完成！" + updatedNum++ + "=====");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询居民是否已经签约（查询省库数据）
     */
    public String signfindMessage() {
        try {
            AppWebSignQvo qvo = (AppWebSignQvo) getAppJson(AppWebSignQvo.class);
            if (qvo != null) {
                AppWebSignFormListEntity vo = sysDao.getAppSignformDao().signfind(qvo);
                this.getAjson().setVo(vo);
                this.getAjson().setMsgCode("800");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 签约新增（与医保对接）
     * WangCheng
     *
     * @return
     */
    public String signRegister() {
        try {
            String address = null;
            String urlType = "";
            JSONObject jsonParam = new JSONObject();
            AppYbCommQvo appYbCommQvo = (AppYbCommQvo) getJson(AppYbCommQvo.class);
            if (appYbCommQvo != null) {
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, appYbCommQvo.getSignHospId());
                CloseableHttpClient client = HttpClients.createDefault();
                appYbCommQvo.setUrlType(dept.getHospAreaCode().substring(0, 4));
                if (StringUtils.isNotEmpty(appYbCommQvo.getUrlType())) {
                    if (appYbCommQvo.getUrlType().equals(AddressType.FZS.getValue())) {
                        urlType = AddressType.FZ.getValue();
                        address = PropertiesUtil.getConfValue("FZurlSign");
                    } else if (appYbCommQvo.getUrlType().equals(AddressType.QZS.getValue())) {
                        urlType = AddressType.QZ.getValue();
                        address = PropertiesUtil.getConfValue("QZurlSign");
                    } else if (appYbCommQvo.getUrlType().equals(AddressType.ZZS.getValue())) {
                        urlType = AddressType.ZZ.getValue();
                        address = PropertiesUtil.getConfValue("ZZurlSign");
                    } else if (appYbCommQvo.getUrlType().equals(AddressType.PTS.getValue())) {
                        urlType = AddressType.PT.getValue();
                        address = PropertiesUtil.getConfValue("PTurlSign");
                    } else if (appYbCommQvo.getUrlType().equals(AddressType.NPS.getValue())) {
                        urlType = AddressType.NP.getValue();
                        address = PropertiesUtil.getConfValue("NPurlSign");
                    } else if (appYbCommQvo.getUrlType().equals(AddressType.SMS.getValue())) {
                        urlType = AddressType.SM.getValue();
                        address = PropertiesUtil.getConfValue("SMurlSign");
                    } else if (appYbCommQvo.getUrlType().equals(AddressType.CS.getValue())) {
                        urlType = AddressType.CS.getValue();
                        address = PropertiesUtil.getConfValue("CSurlSign");
                    }
//                    address = PropertiesUtil.getConfValue("FZurlSign");
                    if (address != null) {
                        jsonParam.put("signHospId",appYbCommQvo.getSignHospId());
//                        jsonParam.put("signHospId","10102");
                        String reslut = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                        if (reslut != null && reslut != "") {
                            JSONObject jsonAll = JSONObject.fromObject(reslut);
                            if(!"900".equals(jsonAll.get("msgCode"))){
                                AppYbResultVo appYbResultVo = JsonUtil.fromJson(jsonAll.get("vo").toString(), AppYbResultVo.class);
                                if(appYbResultVo!=null){
                                    JSONObject jsonParamT = new JSONObject();
                                    String url = PropertiesUtil.getConfValue("medicalInsuranceUrl") + "medicalSignAdd";
                                    //赋值用户名密码
                                    appYbCommQvo.setUsr(appYbResultVo.getUsr());
                                    appYbCommQvo.setPwd(appYbResultVo.getPwd());
//                                    appYbCommQvo.setUsr("0170001");
//                                    appYbCommQvo.setPwd("888888");
                                    //通过套餐Id获取收费项目编号
                                    AppServeSetmeal appServeSetmeal = (AppServeSetmeal) sysDao.getServiceDo().find(AppServeSetmeal.class, appYbCommQvo.getPackageId());
                                    if (appServeSetmeal != null) {
                                        jsonParamT.put("bke045", appServeSetmeal.getSersmCode()); //家签收费项目编号
//                                        jsonParamT.put("bke045", "6043700001"); //家签收费项目编号
                                    }
                                    jsonParamT.put("usr",appYbCommQvo.getUsr()); //用户名
                                    jsonParamT.put("pwd",appYbCommQvo.getPwd());//密码
                                    jsonParamT.put("aac003",appYbCommQvo.getName());
                                    jsonParamT.put("aac002",appYbCommQvo.getPatientIdno());//身份证
                                    jsonParamT.put("aaz501",appYbCommQvo.getPatientCard());//医保卡
                                    jsonParamT.put("bkc006",appYbCommQvo.getSignDrName());//签约医生
                                    jsonParamT.put("bke598",appYbCommQvo.getSignToDate().substring(0, 10).replace("/", "").trim());//签约开始时间
                                    jsonParamT.put("bke599",appYbCommQvo.getSignToDate().substring(11, 21).replace("/", "").trim());//签约结束时间
//                                    jsonParamT.put("bke598","20190416");//签约开始时间
//                                    jsonParamT.put("bke599","20200415");//签约开始时间
                                    String json = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParamT, "utf-8");
                                    if (json != null && json != "") {
                                        JSONObject jsonStr = JSONObject.fromObject(json);
                                        if("10000".equals(jsonStr.get("code"))){
                                            AppYbResultVo appYbResultVos = JsonUtil.fromJson(jsonStr.get("vo").toString(), AppYbResultVo.class);
                                            if(appYbResultVos!=null){
                                                JSONObject jsonParamW = new JSONObject();
                                                String urls = PropertiesUtil.getConfValue("medicalInsuranceUrl") + "medicalSignAddPay";
                                                jsonParamW.put("usr",appYbCommQvo.getUsr()); //用户名
                                                jsonParamW.put("pwd",appYbCommQvo.getPwd());//密码
                                                jsonParamW.put("aac003",appYbCommQvo.getName());//姓名
                                                jsonParamW.put("aac002",appYbCommQvo.getPatientIdno());//身份证
                                                jsonParamW.put("aaz501",appYbCommQvo.getPatientCard());//医保卡
                                                jsonParamW.put("akc190",appYbResultVos.getAkc190());//医保签约流水号
                                                jsonParamW.put("bke241",appYbCommQvo.getBke241());//1 窗口 2移动端
                                                jsonParamW.put("bke286","1");//0预结算1结算
                                                String jsonW = HtmlUtils.getInstance().executeResponseJson(client, "post", urls, jsonParamW, "utf-8");
                                                if (jsonW != null && jsonW != "") {
                                                    JSONObject jsonWtr = JSONObject.fromObject(jsonW);
                                                    if("10000".equals(jsonWtr.get("code"))){
                                                        AppYbResultVo appYbResultVoT = JsonUtil.fromJson(jsonWtr.get("vo").toString(), AppYbResultVo.class);
                                                        if(appYbResultVoT != null){
                                                            //根据签约单主键获取签约单信息
                                                            AppSignForm appSignForm = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, appYbCommQvo.getId());
                                                            appSignForm.setSignState("2");
                                                            appSignForm.setSignPayState("1");
                                                            sysDao.getServiceDo().modify(appSignForm);
                                                            //保存数据到医保签约结算
                                                            AppSignSettlement settlement = new AppSignSettlement();
                                                            settlement.setSignId(appSignForm.getId());
                                                            settlement.setPatientName(appYbResultVoT.getAac003());//姓名
                                                            settlement.setPatientIdNo(appYbResultVoT.getAac002());//身份证号
                                                            settlement.setPatientCard(appYbResultVoT.getAaz501());//社保卡号
                                                            if(StringUtils.isNotBlank(appYbResultVoT.getBkc006())){
                                                                settlement.setSignDrName(appYbResultVoT.getBkc006());//签约医生姓名
                                                            }else{
                                                                settlement.setSignDrName(appYbCommQvo.getSignDrName());//签约医生姓名
                                                            }
                                                            if(StringUtils.isNotBlank(appYbResultVoT.getBke595())){
                                                                settlement.setSignHospId(appYbResultVoT.getBke595());//签约机构
                                                            }else{
                                                                settlement.setSignHospId(appYbCommQvo.getSignHospId());//签约机构
                                                            }
                                                            if(StringUtils.isNotBlank(appYbResultVoT.getBke598())){
                                                                settlement.setSignFromDate(appYbResultVoT.getBke598());//开始时间
                                                            }else{
                                                                settlement.setSignFromDate(appYbCommQvo.getSignToDate().substring(0, 10).replace("/", "").trim());//开始时间
                                                            }
                                                            if(StringUtils.isNotBlank(appYbResultVoT.getBke599())){
                                                                settlement.setSignEndDate(appYbResultVoT.getBke599());//截止时间
                                                            }else{
                                                                settlement.setSignEndDate(appYbCommQvo.getSignToDate().substring(11, 21).replace("/", "").trim());//截止时间
                                                            }
                                                            settlement.setTransactionCode(appYbResultVoT.getAae072());//交易码 医保收费流水号
                                                            settlement.setSignServicePay(new BigDecimal(appYbResultVoT.getAkc227()));//签约服务费
                                                            settlement.setFundPay(new BigDecimal(appYbResultVoT.getBkc045()));//基金支付金额
                                                            settlement.setAccountPay(new BigDecimal(appYbResultVoT.getBkc041()));//账户支付金额
                                                            settlement.setPersonPay(new BigDecimal(appYbResultVoT.getBkc040()));//个人支付金额
                                                            settlement.setPublicPay(new BigDecimal(appYbResultVoT.getBkc058()));//公卫支付金额
                                                            settlement.setSignPayDate(appYbResultVoT.getAke059()); //实际收费时间
                                                            settlement.setYiBaoNo(appYbResultVoT.getAkc190());  //医保签约流水号
                                                            settlement.setSignAddrName(appYbResultVoT.getAab034());//参保人所属分中心名称
                                                            settlement.setSignAddrUnit(appYbResultVoT.getAab001());//参保单位/乡村组
                                                            sysDao.getServiceDo().add(settlement);
                                                            jsons.setCode("800");
                                                            jsons.setMsg("登记成功");
                                                        }
                                                    }else{
                                                        jsons.setCode("900");
                                                        jsons.setMsg(jsonWtr.get("msg").toString());
                                                        //TODO 调用冲销接口
                                                        return "json";
                                                    }
                                                }else{
                                                    this.jsons.setMsg("调用医保签约支付接口异常！");
                                                    this.jsons.setCode("900");
                                                }
                                            }
                                        }else{
                                            this.jsons.setMsg(jsonStr.get("msg").toString());
                                            this.jsons.setCode("900");
                                        }
                                    }else{
                                        this.jsons.setMsg("调用医保签约新增接口异常！");
                                        this.jsons.setCode("900");
                                    }
                                }
                            }else {
                                jsons.setCode("900");
                                jsons.setMsg(jsonAll.get("msg").toString());
                            }
                            return "json";
                        }else{
                            this.jsons.setMsg("获取基卫用户信息失败！");
                            this.jsons.setCode("900");
                        }
                    }else{
                        this.jsons.setMsg("基卫获取用户信息接口地址异常！");
                        this.jsons.setCode("900");
                    }
                }else{
                    this.jsons.setMsg("地区编码获取异常！");
                    this.jsons.setCode("900");
                }
            } else {
                this.jsons.setMsg("参数为空！");
                this.jsons.setCode("1001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "json";
    }

    /**
     * 医保冲销
     * WangCheng
     *
     * @param appYbCommQvo
     * @return
     * @throws Exception
     */
    public AppYbResultVo signDestory(AppYbCommQvo appYbCommQvo) throws Exception {
        AppYbResultVo resultVo = new AppYbResultVo();
        String address = null;
        String urlType = "";
        JSONObject jsonParam = new JSONObject();
        if (appYbCommQvo != null) {
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, appYbCommQvo.getSignHospId());
            CloseableHttpClient client = HttpClients.createDefault();
            appYbCommQvo.setUrlType(dept.getHospAreaCode().substring(0, 4));
            if (StringUtils.isNotEmpty(appYbCommQvo.getUrlType())) {
                if (appYbCommQvo.getUrlType().equals(AddressType.FZS.getValue())) {
                    urlType = AddressType.FZ.getValue();
                    address = PropertiesUtil.getConfValue("FZurlDestory");
                } else if (appYbCommQvo.getUrlType().equals(AddressType.QZS.getValue())) {
                    urlType = AddressType.QZ.getValue();
                    address = PropertiesUtil.getConfValue("QZurlDestory");
                } else if (appYbCommQvo.getUrlType().equals(AddressType.ZZS.getValue())) {
                    urlType = AddressType.ZZ.getValue();
                    address = PropertiesUtil.getConfValue("ZZurlDestory");
                } else if (appYbCommQvo.getUrlType().equals(AddressType.PTS.getValue())) {
                    urlType = AddressType.PT.getValue();
                    address = PropertiesUtil.getConfValue("PTurlDestory");
                } else if (appYbCommQvo.getUrlType().equals(AddressType.NPS.getValue())) {
                    urlType = AddressType.NP.getValue();
                    address = PropertiesUtil.getConfValue("NPurlDestory");
                } else if (appYbCommQvo.getUrlType().equals(AddressType.SMS.getValue())) {
                    urlType = AddressType.SM.getValue();
                    address = PropertiesUtil.getConfValue("SMurlDestory");
                } else if (appYbCommQvo.getUrlType().equals(AddressType.CS.getValue())) {
                    urlType = AddressType.CS.getValue();
                    address = PropertiesUtil.getConfValue("CSurlDestory");
                }
                jsonParam.put("usr",appYbCommQvo.getUsr());
                jsonParam.put("pwd",appYbCommQvo.getPwd());
                jsonParam.put("patientCard", appYbCommQvo.getPatientCard());
                jsonParam.put("urlType", urlType);
                if (address != null) {
                    String reslut = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                    if (reslut != null && reslut != "") {
                        JSONObject jsonAll = JSONObject.fromObject(reslut);
                        resultVo = JsonUtil.fromJson(jsonAll.get("vo").toString(), AppYbResultVo.class);
                    }
                }
            }
        }
        return resultVo;
    }

    /**
     * 签约单导入
     * @return
     */
    public String importExcel(){
        try {
            ExcelReader excelReader = new ExcelReader();
            InputStream is2 = new FileInputStream(upExcel);
            Map<Integer, String> map = excelReader.readExcelContent(is2,1);//读取Excel数据内容
            if(map.size() >0) {
                CdUser user = this.getSessionUser();
                if(map.size()>2000){
                    this.jsons.setMsg("导入数据不能超过2000条");
                    return "jsonUpload";
                }
                String result = this.getSysDao().getAppSignformDao().addImportExcelSign(map);
                this.jsons.setMsg(result);
            }else{
                this.jsons.setMsg("exceel无数据!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.jsons.setMsg(e.getMessage());
        }
        return "jsonUpload";
    }

    /**
     * 福州判断是否是已签约已缴费
     * @return
     */
    public String flagPrint(){
        try{
            String signId = this.getRequest().getParameter("signId");
            if(StringUtils.isNotBlank(signId)){
                AppSignForm form = (AppSignForm)sysDao.getServiceDo().find(AppSignForm.class,signId);
                if(form != null){
                    if(AddressType.FZS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"))){//判断是不是已签约已缴费
                        boolean flag = false;
                        //判断所签约的服务包是否有编码
                        if(StringUtils.isNotBlank(form.getSignpackageid())){
                            List<AppServeSetmeal> listM = sysDao.getAppServeSetmealDao().findMealByIds(form.getSignpackageid());
                            if(listM != null && listM.size()>0){
                                for(AppServeSetmeal ll:listM){
                                    if(StringUtils.isNotBlank(ll.getSersmCode())){
                                        flag = true;
                                    }
                                }
                            }
                        }
                        if(flag){
                            if(!"1".equals(form.getSignPayState())){
                                this.jsons.setCode("900");
                                this.jsons.setMsg("未缴费无法打印协议");
                                return "json";
                            }
                        }
                    }
                }
            }
            this.jsons.setCode("800");
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }

    /**
     * 根据签约单主键查询签约单是否已经续签或转签
     * @return
     */
    public String renewState(){
        try{
            String signId = this.getRequest().getParameter("signId");
            if (StringUtils.isNotBlank(signId)) {
                AppSignForm form = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,signId);
                if(form != null){
                    if("0".equals(form.getSignGoToSignState())){
                        this.jsons.setCode("800");
                    }else if("1".equals(form.getSignGoToSignState())){
                        this.jsons.setCode("900");
                    }else if("2".equals(form.getSignGoToSignState())){
                        this.jsons.setCode("1000");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }
}