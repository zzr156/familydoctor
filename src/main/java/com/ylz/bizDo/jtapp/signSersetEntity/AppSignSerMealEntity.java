package com.ylz.bizDo.jtapp.signSersetEntity;
import com.ylz.bizDo.app.po.AppEconAndGov;
import com.ylz.bizDo.app.po.AppServeEcon;
import com.ylz.bizDo.app.po.AppServeGov;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**服务套餐层
 * Created by zzl on 2017/8/23.
 */
public class AppSignSerMealEntity {
    private String mId;//套餐id
    private String mName;//套餐名称
    private String mValue;//套餐值
    private String mState;//套餐是否是基础套餐
    private String mImage;//图片
    private String gValue;//组合值
    private String jjlx;//经济类型
    private String mTabState;//套餐标记状态
    private String mTotalFee;//套餐总费用
    private String mFee;//套餐实付费用
    private List<AppSignGroupEntity> groupList;//组合信息集合 一个套餐下有多个组合
    private List<AppSignEconEntity> econList;//经济类型信息集合

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getgValue() {
        return gValue;
    }

    public void setgValue(String gValue) {
        this.gValue = gValue;
    }

    public String getJjlx() {
        return jjlx;
    }

    public void setJjlx(String jjlx) {
        this.jjlx = jjlx;
    }

    public List<AppSignGroupEntity> getGroupList() {
        return groupList;
    }

    public String getmTabState() {
        return mTabState;
    }

    public void setmTabState(BigInteger mTabState) {
        if(mTabState != null){
            this.mTabState = mTabState.toString();
        }else {
            this.mTabState = "0";
        }
    }

    public String getmFee() {
        return mFee;
    }

    public void setmFee(String mFee) {
        this.mFee = mFee;
    }

    public String getmTotalFee() {
        return mTotalFee;
    }

    public void setmTotalFee(String mTotalFee) {
        this.mTotalFee = mTotalFee;
    }

    public void setGroupList(String groupList) {
        List<AppSignGroupEntity> list = new ArrayList<AppSignGroupEntity>();
        if(StringUtils.isNotBlank(this.getgValue())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = "SELECT " +
                    "ID gId," +
                    "SERG_VALUE gValue," +
                    "SERG_JC_STATE gState," +
                    "SERG_PK_VALUE pkValue," +
                    "SERG_OBJECT_VALUE objVo," +
                    "'' gPkList " +
                    "FROM APP_SERVE_GROUP WHERE 1=1";
            map.put("groups",this.getgValue().split(";"));
            sql += " AND SERG_VALUE IN :groups";
            list = dao.getServiceDo().findSqlMapRVo(sql,map,AppSignGroupEntity.class);
        }
        this.groupList = list;
    }

    public List<AppSignEconEntity> getEconList() {
        return econList;
    }

    public void setEconList(String econList) {
        List<AppSignEconEntity> list = new ArrayList<AppSignEconEntity>();
        if(StringUtils.isNotBlank(econList)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            String[] econIds = econList.split(";");
            for(String econId:econIds){
                AppEconAndGov eag = (AppEconAndGov) dao.getServiceDo().find(AppEconAndGov.class,econId);
                AppSignEconEntity see = new AppSignEconEntity();
                if(eag!=null){
                    List<AppServeEcon> ses = dao.getServiceDo().loadByPk(AppServeEcon.class,"econValue",eag.getEagEconValue());
                    if(ses!=null&&ses.size()>0){
                        see.setEconId(ses.get(0).getId());
                        see.setEconJcType(ses.get(0).getEconJcType());
                        see.setEconTabState(ses.get(0).getEconTabState());
                        see.setEconTitle(ses.get(0).getEconTitle());
                        see.setEconValue(ses.get(0).getEconValue());
                    }
                    if(StringUtils.isNotBlank(eag.getEagGovValue())){
                        String[] govIds = eag.getEagGovValue().split(";");
                        List<AppSignGovEntity> sges = new ArrayList<AppSignGovEntity>();
                        for(String govId:govIds){
                            AppSignGovEntity sge = new AppSignGovEntity();
                            List<AppServeGov> sgs = dao.getServiceDo().loadByPk(AppServeGov.class,"govValue",govId);
                            if(sgs!=null&&sgs.size()>0){
                                sge.setGovId(sgs.get(0).getId());
                                sge.setGovJcState(sgs.get(0).getGovJcType());
                                sge.setGovTabState(sgs.get(0).getGovTabState());
                                sge.setGovTitle(sgs.get(0).getGovTitle());
                                sge.setGovValue(sgs.get(0).getGovValue());
                                sges.add(sge);
                            }
                        }
                        see.setGovList(sges);
                    }
                }
                list.add(see);
            }
        }
        this.econList = list;
    }
}
