package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.CommTcmBs;
import com.ylz.packcommon.common.comEnum.CommTcmTz;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/8/31.
 */
public class AppNewTcmLookEntity {
    private String id;//中医体质辨识结果id
    private String tcmId;//答题记录Id
    private String tzType;//体质类型
    private String df;//得分
    private String result;//结果（1是 2倾向是 3 否）
    private AppNewUserTcmGuideEntity resultList;//指导列表

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTzType() {
        return tzType;
    }

    public void setTzType(String tzType) {
        this.tzType = tzType;
    }

    public String getDf() {
        return df;
    }

    public void setDf(String df) {
        this.df = df;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public AppNewUserTcmGuideEntity getResultList() {
        return resultList;
    }

    public void setResultList(String resultList) {
        AppNewUserTcmGuideEntity resultLists = new AppNewUserTcmGuideEntity();
        if(StringUtils.isNotBlank(this.getTcmId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = "SELECT ID id," +
                    "UTG_CORPOREITY_TYPE tzlx," +
                    "UTG_CREATE_ID createId," +
                    "date_format(UTG_CREATE_TIME,'%Y-%m-%d %H:%i:%s') createTime," +
                    "UTG_MODERN_CULTIVATE qzts," +
                    "UTG_DIET_AFTERCARE ysty," +
                    "UTG_DAILY_LIFE_CULTIVATE qjts," +
                    "UTG_SPORTS_HEALTH ydbj," +
                    "UTG_MERIDIAN_HEALTH  xwbj," +
                    "UTG_OTHER qt," +
                    "UTG_TCM_ID jlId," +
                    "UTG_USER_ID userId " +
                    "FROM APP_USER_TCM_GUIDE WHERE 1=1";
            map.put("tcmId",this.getTcmId());
            map.put("disType",this.getTzType());
            sql += " AND UTG_TCM_ID =:tcmId";
            sql += " AND UTG_CORPOREITY_TYPE =:disType";
            sql += " ORDER BY UTG_CORPOREITY_TYPE";
            List<AppNewUserTcmGuideEntity> ls = dao.getServiceDo().findSqlMapRVo(sql,map,AppNewUserTcmGuideEntity.class);
            if(ls!=null&&ls.size()>0){
                resultLists = ls.get(0);
            }else{
                resultLists = null;
            }
        }
        this.resultList = resultLists;
    }

    public String getTcmId() {
        return tcmId;
    }

    public void setTcmId(String tcmId) {
        this.tcmId = tcmId;
    }

    /**
     * 获取体质类型名称
     * @return
     */
    public String getTzName() throws Exception{
        if(StringUtils.isNotBlank(this.getTzType())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TZLX, this.getTzType());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    /**
     * 获取体质辨识名称
     * @return
     */
    public String getResultName() throws Exception{
        if(StringUtils.isNotBlank(this.getResult())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TZBS, this.getResult());
            if(value!=null){
                if(CommTcmBs.QXS.getValue().equals(this.getResult())){
                    if(CommTcmTz.PHZ.getValue().equals(this.getTzType())){
                        return "基本是";
                    }
                }
                return value.getCodeTitle();
            }
        }
        return "";
    }
}
