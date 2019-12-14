package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/8/23.
 */
public class AppTcmLookEntity {
    private String id;//中医体质辨识结果id
    private String tcmId;//答题记录Id
    private String tzType;//体质类型
    private String df;//得分
    private String result;//结果（1是 2倾向是 3 否）
    private List<AppTcmResultEntity> resultList;//指导列表

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

    public List<AppTcmResultEntity> getResultList() {
        return resultList;
    }

    public void setResultList(String resultList) {
        List<AppTcmResultEntity> resultLists = new ArrayList<AppTcmResultEntity>();
        if(StringUtils.isNotBlank(this.getTcmId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = "SELECT ID id," +
                    "HM_TITLE title," +
                    "HM_CONTENT content," +
                    "HM_CREATE_TIME createTime," +
                    "HM_DR_ID createId," +
                    "HM_IMAGE_URL imageUrl," +
                    "HM_MED_TYPE bjlx " +
                    "FROM APP_HEALTH_MEDDLE WHERE 1=1";
            map.put("tcmId",this.getTcmId());
            map.put("disType",this.getTzType());
            sql += " AND HM_TCM_ID =:tcmId";
            sql += " AND HM_DIS_TYPE =:disType";
            sql += " ORDER BY HM_MED_TYPE";
            resultLists = dao.getServiceDo().findSqlMapRVo(sql,map,AppTcmResultEntity.class);
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
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }
}
