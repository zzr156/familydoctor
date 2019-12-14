package com.ylz.task;

import com.ylz.bizDo.app.entity.AppHealthCardRecodesVo;
import com.ylz.bizDo.app.entity.AppPatientDAHVo;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamManagEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lenovo on 2018/1/11.
 *
 */
public class OutpatientJob {

    @Autowired
    public SysDao sysDao=(SysDao) SpringHelper.getBean("sysDao");
    // 门诊次数  年度费用 调度
    public void AppOutpatientCount(){
        try{
            String state = PropertiesUtil.getConfValue("manageState");
            List<String> ls = new ArrayList<String>();
            if(state.equals(CommonEnable.QIYONG.getValue())){
                String startDate = "2017-08-01";
                String endDate = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            }else{
                String date = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(date,date);
            }
            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findByManageCont();
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().AppOutpatientCount(s,lsTeam);
                }
            }
        }catch (Exception e ){
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    // 调度居民健康档案  jq  to  jw
    public  void AppFindPatient()
    {
     try{
         int pageSize= 10;
         int count =sysDao.getAppPatientUserDao().Apppatientlist();
         if(count!=0){
             double result = MyMathUtil.div(Double.valueOf(count), Double.valueOf(pageSize), 1);
             double math= Math.ceil(result); // 向上取整
             int sum = (int)math;
             for(int j=0;j<sum;j++){  // 总数循环 分页
                 List<AppPatientDAHVo> listvo = new ArrayList();
                 List<AppPatientUser> list = sysDao.getAppPatientUserDao().AppPatientFind(j+1,pageSize);
                 if(list != null && list.size()>0){
                     for(AppPatientUser user:list){  //  调接口赋值集合
                         AppPatientDAHVo dahvo =  findjmda(user);
                         if(dahvo !=null){
                             listvo.add(dahvo);
                         }
                     }
                     for(int h=0;h<listvo.size();h++){  //  集合循环update
                         sysDao.getAppPatientUserDao().AppPatientUpdate(listvo.get(h));
                         System.out.print("");
                     }
                     System.out.print("");
                 }
             }
         }
     }catch (Exception e){
         new ActionException(this.getClass(), e.getMessage());
     }
    }

    public AppPatientDAHVo findjmda(AppPatientUser user){
        try{
            String address = null;
            String urlType="";
            JSONObject jsonParam = new JSONObject();
            AppHealthCardRecodesVo vo = sysDao.getAppPatientUserDao().AppDeptCode(user.getId());
            vo.setIdNo(user.getPatientIdno());
            if(vo!=null){
                CloseableHttpClient client = HttpClients.createDefault();
                if(StringUtils.isNoneBlank(vo.getUrlType())){
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
                    jsonParam.put("idNo",vo.getIdNo());
                    if(vo.getUrlType().equals("3501")){
                        jsonParam.put("orgId",vo.getOrgId());
                    }else{
                        jsonParam.put("orgId",vo.getOrgId().substring(3,vo.getOrgId().length()));
                    }
                    jsonParam.put("type","2");
                    jsonParam.put("urlType",urlType);
                    if(address!=null){
                        String reslut = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                        if(reslut!=null && reslut != ""){
                            JSONObject jsonAll = JSONObject.fromObject(reslut);
                            if(jsonAll.get("entity")!=""){
                                String entvo=jsonAll.get("entity").toString();
                                JSONObject entVo = JSONObject.fromObject(entvo);
                                String entity =entVo.get("entity").toString();
                                JSONObject entvoAll = JSONObject.fromObject(entity);
                                System.out.print(entvoAll.get("jmdah"));
                                String jmdah = entvoAll.get("jmdah").toString();
                                String jtdah = entvoAll.get("jtdah").toString();
                                AppPatientDAHVo dah= new AppPatientDAHVo();
                                dah.setId(user.getId());
                                dah.setPatientjmda(jmdah);
                                dah.setPatientjtda(jtdah);
                                dah.setPatientIdNo(user.getPatientIdno());
                                return dah;

                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            new ActionException(this.getClass(), e.getMessage());
        }
        return null;
    }



}
