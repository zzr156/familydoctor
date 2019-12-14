package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.LabelManageType;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/12/26.
 */
public class AppComLyPeopleEntity {
    private String typeValue;//类型值
    private String typeName;//类型名称
    private String count;//人数
    private String type;//类型（1健康情况 2疾病类型 3服务人群）
    private String pcNum;//履约批次号
    private List<AppPeopleLyEntity> lyList;//

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        if(count == null){
            this.count = "0";
        }else{
            this.count = String.valueOf(count);
        }

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPcNum() {
        return pcNum;
    }

    public void setPcNum(String pcNum) {
        this.pcNum = pcNum;
    }

    public List<AppPeopleLyEntity> getLyList() {
        return lyList;
    }

    public void setLyList(String lyList) {
        List<AppPeopleLyEntity> list = new ArrayList<>();
        if(StringUtils.isNotBlank(this.getPcNum())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("lyNum",this.getPcNum());
            map.put("value",this.getTypeValue());
            if(LabelManageType.JKQK.getValue().equals(this.getType())){//健康情况
                String sql = "SELECT " +
                        "b.ID patientId," +
                        "b.PATIENT_NAME patientName," +
                        "b.PATIENT_IMAGEURL patientImgUrl," +
                        "b.PATIENT_GENDER patientSex," +
                        "b.PATIENT_AGE patientAge," +
                        "(SELECT g.LABEL_TITLE from APP_LABEL_MANAGE g where g.LABEL_TYPE ='1' and g.LABEL_VALUE=c.SIGN_HEALTH_GROUP) labelTitle " +
                        "FROM APP_PERFORMANCE_RECORD a INNER JOIN APP_PATIENT_USER b ON a.APR_PATIENT_ID = b.ID " +
                        "INNER JOIN APP_SIGN_FORM c ON a.APR_SIGN_ID = c.ID " +
                        "WHERE a.APR_PC_NUM =:lyNum AND c.SIGN_HEALTH_GROUP=:value" ;
                list = dao.getServiceDo().findSqlMapRVo(sql,map,AppPeopleLyEntity.class);
            }else if(LabelManageType.JBLX.getValue().equals(this.getType())){//疾病情况
                String sql = "SELECT " +
                        "b.ID patientId," +
                        "b.PATIENT_NAME patientName," +
                        "b.PATIENT_IMAGEURL patientImgUrl," +
                        "b.PATIENT_GENDER patientSex," +
                        "b.PATIENT_AGE patientAge," +
                        "c.LABEL_COLOR labelColor," +
                        "c.LABEL_TITLE labelTitle " +
                        "FROM APP_PERFORMANCE_RECORD a INNER JOIN APP_PATIENT_USER b ON a.APR_PATIENT_ID = b.ID " +
                        "INNER JOIN APP_LABEL_DISEASE c ON a.APR_SIGN_ID = c.LABEL_SIGN_ID " +
                        "WHERE a.APR_PC_NUM =:lyNum AND c.LABEL_VALUE =:value" ;
                list = dao.getServiceDo().findSqlMapRVo(sql,map,AppPeopleLyEntity.class);
            }else if(LabelManageType.FWRQ.getValue().equals(this.getType())){//服务人群
                String sql = "SELECT " +
                        "b.ID patientId," +
                        "b.PATIENT_NAME patientName," +
                        "b.PATIENT_IMAGEURL patientImgUrl," +
                        "b.PATIENT_GENDER patientSex," +
                        "b.PATIENT_AGE patientAge," +
                        "(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE='2' and g.LABEL_SIGN_ID=a.APR_SIGN_ID) labelColor," +
                        "(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE='2' and g.LABEL_SIGN_ID=a.APR_SIGN_ID) labelTitle " +
                        "FROM APP_PERFORMANCE_RECORD a INNER JOIN APP_PATIENT_USER b ON a.APR_PATIENT_ID = b.ID " +
                        "INNER JOIN APP_LABEL_GROUP c ON a.APR_SIGN_ID = c.LABEL_SIGN_ID " +
                        "WHERE a.APR_PC_NUM =:lyNum AND c.LABEL_VALUE =:value" ;
                list = dao.getServiceDo().findSqlMapRVo(sql,map,AppPeopleLyEntity.class);
            }
        }
        this.lyList = list;
    }
}
