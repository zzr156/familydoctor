package com.ylz.bizDo.jtapp.signSersetEntity;

import com.ylz.bizDo.app.po.AppServeObject;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**组合层信息
 * Created by zzl on 2017/8/23.
 */
public class AppSignGroupEntity {
    private String gId;//组合id
    private String gValue;//组合值
    private String gState;//组合状态
    private String pkValue;//服务包值
    private AppSignObjEntity objVo;//服务人群信息
    private List<AppSignSerPkEntity> gPkList;//服务包集合

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getgValue() {
        return gValue;
    }

    public void setgValue(String gValue) {
        this.gValue = gValue;
    }

    public String getgState() {
        return gState;
    }

    public void setgState(String gState) {
        this.gState = gState;
    }

    public String getPkValue() {
        return pkValue;
    }

    public void setPkValue(String pkValue) {
        this.pkValue = pkValue;
    }

    public AppSignObjEntity getObjVo() {
        return objVo;
    }

    public void setObjVo(String objVo) throws Exception {
        AppSignObjEntity vv = new AppSignObjEntity();
        if(StringUtils.isNotBlank(objVo)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            List<AppServeObject> tables = dao.getServiceDo().loadByPk(AppServeObject.class,"seroValue",objVo);
            if(tables!=null&&tables.size()>0){
                vv.setoId(tables.get(0).getId());
                vv.setoStateName(tables.get(0).getStateName());
                vv.setoState(tables.get(0).getSeroState());
                vv.setoValue(tables.get(0).getSeroValue());
                vv.setoName(tables.get(0).getSeroName());
            }
        }
        this.objVo = vv;
    }

    public List<AppSignSerPkEntity> getgPkList() {
        return gPkList;
    }

    public void setgPkList(String gPkList) {
        List<AppSignSerPkEntity> list = new ArrayList<AppSignSerPkEntity>();
        if(StringUtils.isNotBlank(this.getPkValue())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = "SELECT " +
                    "ID pid," +
                    "SERPK_NAME pName," +
                    "SERPK_VALUE pValue," +
                    "SERPK_BASE_TYPE pState," +
                    "SERPK_IMAGE_URL pImage " +
                    "FROM APP_SERVE_PACKAGE WHERE 1=1";
            map.put("pkValues",this.getPkValue().split(";"));
            sql += " AND SERPK_VALUE IN :pkValues";
            list = dao.getServiceDo().findSqlMapRVo(sql,map,AppSignSerPkEntity.class);
        }
        this.gPkList = list;
    }
}
