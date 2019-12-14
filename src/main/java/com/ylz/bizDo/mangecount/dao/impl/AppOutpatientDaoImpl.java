package com.ylz.bizDo.mangecount.dao.impl;

/**
 * Created by lenovo on 2018/1/17.
 */

import com.ylz.bizDo.app.entity.AppOutpatientCountEntity;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.mangecount.dao.AppOutpatientDao;
import com.ylz.bizDo.mangecount.entity.ManageCountEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.util.AreaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appOutpatientDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppOutpatientDaoImpl implements AppOutpatientDao {
    @Autowired
    private SysDao sysDao;

    public  Map<String,Object> findSignOutpatientTypeList(ResidentVo qvo) throws Exception
    {
        HashMap rmap = new HashMap();
        List<ManageCountEntity> lsEntity = new ArrayList<ManageCountEntity>();
        List<ManageCountEntity> lsEntitytwo = new ArrayList<ManageCountEntity>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
            String sql = "";
            rmap.put("STARTDATE",qvo.getYearStart());
            rmap.put("ENDDATE",qvo.getYearEnd());
            String sqlDate = " AND c.OUTPATIENT_YEAR_MONTH >= :STARTDATE AND c.OUTPATIENT_YEAR_MONTH <= :ENDDATE ";
            sql =" SELECT\n" +
                    "\tc.OUTPATIENT_HOSP_ID outpatientHospId,\n" +
                    "\tc.OUTPATIENT_TEAM_ID outpatientTeamId,\n" +
                    "\tCAST( SUM(c.OUTPATIENT_HOSPLEVEL_ONE) as char) outpatientHosplevelOne,\n" +
                    "\tCAST( SUM(c.OUTPATIENT_HOSPLEVEL_TWO) as char) outpatientHosplevelTwo,\n" +
                    "\tCAST( SUM(c.OUTPATIENT_HOSPLEVEL_THREE) as char) outpatientHosplevelThree,\n" +
                    "  CAST( round(SUM(c.OUTPATIENT_LASTYEAR_COST),2) as char)  outpatientLastyearCost,\n" +
                    "  CAST( round(SUM(c.OUTPATIENT_THISYEAR_COST),2) as char)  outpatientThisyearCost\n" +
                    "FROM\n" +
                    "\tAPP_OUTPATIENT_COUNT c\n" +
                    "WHERE 1=1 " +sqlDate;
             if(StringUtils.isNotBlank(qvo.getTeamId())){
                 sql += " AND c.OUTPATIENT_TEAM_ID =:teamid ";
                 rmap.put("teamid",qvo.getTeamId());
             }
             if(StringUtils.isNotBlank(qvo.getHospId())) {
                 sql +=" and c.OUTPATIENT_HOSP_ID =:hospid ";
                 rmap.put("hospid", qvo.getHospId());
             }
             if (StringUtils.isNotBlank(qvo.getAreaId())){
                sql +=" and c.OUTPATIENT_AREA_CODE LIKE:raeacode ";
                 String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
                rmap.put("raeacode",areaCode+"%");
             }
            List<AppOutpatientCountEntity> maps = this.sysDao.getServiceDo().findSqlMapRVo(sql,rmap,AppOutpatientCountEntity.class);
            if(maps != null && maps.size()>0){
                List<CdCode> value = this.sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_OUTPATIENTCOUNT, CommonEnable.QIYONG.getValue());

                for(int i=0;i<value.size();i++){
                    ManageCountEntity v = new ManageCountEntity();
                    if(value.get(i).getCodeValue().equals("0")){
                        v.setName(value.get(i).getCodeTitle());
                        v.setValue( maps.get(0).getOutpatientHosplevelOne());
                        lsEntity.add(v);
                    }else if(value.get(i).getCodeValue().equals("1")){
                        v.setName(value.get(i).getCodeTitle());
                        v.setValue( maps.get(0).getOutpatientHosplevelTwo());
                        lsEntity.add(v);
                    }else if(value.get(i).getCodeValue().equals("2")){
                        v.setName(value.get(i).getCodeTitle());
                        v.setValue( maps.get(0).getOutpatientHosplevelThree());
                        lsEntity.add(v);
                    }else if(value.get(i).getCodeValue().equals("3")){
                        v.setName(value.get(i).getCodeTitle());
                        v.setValue( maps.get(0).getOutpatientLastyearCost());
                        lsEntitytwo.add(v);
                    }else if(value.get(i).getCodeValue().equals("4")){
                        v.setName(value.get(i).getCodeTitle());
                        v.setValue( maps.get(0).getOutpatientThisyearCost());
                        lsEntitytwo.add(v);
                    }
                    returnMap.put("OutpatientCost",lsEntitytwo);
                    returnMap.put("OutpatientNumber",lsEntity);

                }
                return returnMap;
            }
        return null;
    }
}
