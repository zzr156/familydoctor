package com.ylz.bizDo.jtapp.signSersetEntity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 查询医院签约管理设置记录
 * Created by zzl on 2017/8/24.
 */
public class AppSignSettingEntity {
    private String id;//主键id
    private String signsOpenWork;//是否开启选择工作类型
    private String signsWorkType;//工作类型
    private String signsFree;//签约费用
    private String signsAreaCode;//区域编码
    private String signsIsOrNot;//是否对接基卫
    private String signsMealValue;//套餐
    private List<AppSignSerMealEntity> mealList;//套餐信息

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignsOpenWork() {
        return signsOpenWork;
    }

    public void setSignsOpenWork(String signsOpenWork) {
        this.signsOpenWork = signsOpenWork;
    }

    public String getSignsWorkType() {
        return signsWorkType;
    }

    public void setSignsWorkType(String signsWorkType) {
        this.signsWorkType = signsWorkType;
    }

    public String getSignsFree() {
        return signsFree;
    }

    public void setSignsFree(String signsFree) {
        this.signsFree = signsFree;
    }

    public String getSignsAreaCode() {
        return signsAreaCode;
    }

    public void setSignsAreaCode(String signsAreaCode) {
        this.signsAreaCode = signsAreaCode;
    }

    public String getSignsIsOrNot() {
        return signsIsOrNot;
    }

    public void setSignsIsOrNot(String signsIsOrNot) {
        this.signsIsOrNot = signsIsOrNot;
    }

    public String getSignsMealValue() {
        return signsMealValue;
    }

    public void setSignsMealValue(String signsMealValue) {
        this.signsMealValue = signsMealValue;
    }

    public List<AppSignSerMealEntity> getMealList() {
        return mealList;
    }

    public void setMealList(String mealList) {
        List<AppSignSerMealEntity> list = new ArrayList<AppSignSerMealEntity>();
        if(StringUtils.isNotBlank(this.getSignsMealValue())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = "SELECT " +
                    "a.ID mId," +
                    "a.SERSM_NAME mName," +
                    "a.SERSM_VALUE mValue," +
                    "a.SERSM_JC_STATE mState," +
                    "a.SERSM_IMAGE_URL mImage, " +
                    "a.SERSM_GROUP_VALUE gValue," +
                    "a.SERSM_JJ_TYPE jjlx," +
                    "'' groupList," +
                    "a.SERSM_TOTAL_FEE mTotalFee," +
                    "a.SERSM_FEE mFee," +
                    "a.SERSM_JJ_ID econList," +
                    "(SELECT COUNT(1) FROM APP_SERVE_TAB WHERE SERTAB_SER_ID = a.ID AND SERTAB_DEPT_ID =:hospId) mTabState  " +
                    "FROM APP_SERVE_SETMEAL a WHERE 1=1";
            map.put("value",this.getSignsMealValue().split(";"));
            sql += " AND a.SERSM_VALUE IN :value";
            list = dao.getServiceDo().findSqlMapRVo(sql,map,AppSignSerMealEntity.class);
        }
        this.mealList = list;
    }
}
