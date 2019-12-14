package com.ylz.bizDo.cd.dao.impl;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.cd.dao.CdAddressPeopleDao;
import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.cd.vo.CdAddressPeopleQvo;
import com.ylz.bizDo.cd.vo.CdAddressPeopleVo;
import com.ylz.bizDo.jtapp.drEntity.AppDrTargetEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrTargetQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.MyMathUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("cdAddressPeopleDao")
@Transactional(rollbackForClassName={"Exception"})
public class CdAddressPeopleDaoImpl implements CdAddressPeopleDao {
    @Autowired
    private SysDao sysDao;
    @Override
    public List<CdAddressPeople> findList(CdAddressPeopleQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT a.* FROM CP_CITY_AREA_PEOPLE a WHERE 1=1";
        if(StringUtils.isNotBlank(qvo.getAddrName())){
            map.put("areaName","%"+qvo.getAddrName()+"%");
            sql += " AND a.AREA_NAME LIKE :areaName";
        }
        sql += " ORDER BY a.AREA_CREATE_TIME DESC";
        List<CdAddressPeople> ls = sysDao.getServiceDo().findSqlMap(sql,map,CdAddressPeople.class,qvo);
        return ls;
    }

    /**
     * 根据地区编码和年份查询数据记录
     * @param areaCode
     * @param year
     * @return
     */
    @Override
    public CdAddressPeople findByYearCode(String areaCode, String year)  throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT a.* FROM CP_CITY_AREA_PEOPLE a WHERE 1=1";
        map.put("areaCode",areaCode);
        map.put("year",year);
        sql += " AND a.AREA_CODE =:areaCode";
        sql += " AND a.AREA_YEAR =:year";
        List<CdAddressPeople> ls =sysDao.getServiceDo().findSqlMap(sql,map,CdAddressPeople.class);
        if(ls!=null && ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    /**
     * 根据条件查询指标记录
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppDrTargetEntity findByQvo(AppDrTargetQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT " +
                "a.ID id," +
                "a.AREA_SNAME areaSname," +
                "a.AREA_CODE areaCode," +
                "a.AREA_POPULATION areaPopulation," +
                "a.AREA_TARGET areaTarget," +
                "a.UP_AREA_POPULATION upAreaPopulation,"+
                "a.AREA_FOCUS areaFocus," +
                "a.UP_AREA_FOCUS upAreaFocus," +
                "a.AREA_FOCUS_TARGET areaFocusTarget," +
                "a.AREA_YEAR areaYear,"+
                "a.AREA_RATE areaRate," +
                "a.AREA_SIGN_TOP signTop," +
                "a.AREA_SIGN_WAY signWay," +
                "a.AREA_DIS_SIGN_TOP disSignTop," +
                "a.AREA_DIS_SIGN_WAY disSignWay," +
                "a.AREA_FOCUS_RATE areaFocusRate,"+
                "date_format(a.AREA_CREATE_TIME,'%Y-%c-%d %H:%i:%s') time," +
                "a.AREA_NAME areaName," +
                "a.AREA_ECONOMIC economic," +
                "a.AREA_UP_ECONOMIC upEconomic," +
                "a.AREA_ECONOMIC_TARGET economicTarget," +
                "a.AREA_ECONOMIC_RATE economicRate " +
                "FROM CP_CITY_AREA_PEOPLE a WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getAreaName())){
            map.put("areaName","%"+qvo.getAreaName()+"%");
            sql += " AND a.AREA_NAME LIKE :areaName";
        }
        if(StringUtils.isNotBlank(qvo.getAreaCode())){
            map.put("areaCode",qvo.getAreaCode());
            sql += " AND a.AREA_CODE =:areaCode";
        }
        if(StringUtils.isNotBlank(qvo.getYear())){
            map.put("year",qvo.getYear());
            sql += " AND a.AREA_YEAR = :year";
        }
        sql += " ORDER BY a.AREA_CREATE_TIME DESC";
        List<AppDrTargetEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrTargetEntity.class,qvo);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    /**
     * app修改指标记录
     * @param qvo
     * @throws Exception
     */
    @Override
    public AppDrTargetEntity modifyTarget(AppDrTargetQvo qvo) throws Exception {
        if(StringUtils.isBlank(qvo.getId())){
            qvo.setId("");
        }
        CdAddressPeople table = (CdAddressPeople) sysDao.getServiceDo().find(CdAddressPeople.class,qvo.getId());
        if(table!=null){
            table.setAreaPopulation(qvo.getAreaPopulation());
            table.setAreaRate(qvo.getAreaRate());
            table.setAreaFocus(qvo.getAreaFocus());
            table.setAreaFocusRate(qvo.getAreaFocusRate());
            table.setAreaEconomic(qvo.getAreaEconomic());
            table.setAreaEconomicRate(qvo.getAreaEconomicRate());
            table.setAreaState("1");
            double rete = MyMathUtil.div(Double.parseDouble(qvo.getAreaRate()),Double.parseDouble("100"),2);
            double peopleNum = MyMathUtil.mul(Double.parseDouble(qvo.getAreaPopulation()),rete);
            table.setAreaTarget(String.valueOf((int) peopleNum));
            double focusRete = MyMathUtil.div(Double.parseDouble(qvo.getAreaFocusRate()),Double.parseDouble("100"),2);
            double focusNum = MyMathUtil.mul(Double.parseDouble(qvo.getAreaFocus()),focusRete);
            table.setAreaFocusTarget(String.valueOf((int) focusNum));
            if(StringUtils.isNotBlank(qvo.getAreaEconomicRate())){
                double economicRate = MyMathUtil.div(Double.parseDouble(qvo.getAreaEconomicRate()),Double.parseDouble("100"),2);
                double economicNum = MyMathUtil.mul(Double.parseDouble(qvo.getAreaEconomic()),economicRate);
                table.setAreaEconomicTarget(String.valueOf((int) economicNum));
            }
            if(StringUtils.isNotBlank(qvo.getAreaEconomicDbh())){
                table.setAreaEconomicDbh(qvo.getAreaEconomicDbh());
            }
            if(StringUtils.isNotBlank(qvo.getAreaEconomicJklm())){
                table.setAreaEconomicJklm(qvo.getAreaEconomicJklm());
            }
            if(StringUtils.isNotBlank(qvo.getAreaEconomicTkh())){
                table.setAreaEconomicTkh(qvo.getAreaEconomicTkh());
            }
            if(StringUtils.isNotBlank(qvo.getAreaEconomicJsjt())){
                table.setAreaEconomicJsjt(qvo.getAreaEconomicJsjt());
            }
            table.setAreaDisSignTop(qvo.getDisSignTop());
            table.setAreaDisSignWay(qvo.getDisSignWay());
            table.setAreaSignWay(qvo.getSignWay());
            table.setAreaSignTop(qvo.getSignTop());
            sysDao.getServiceDo().modify(table);
        }else{
            AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,qvo.getOrgid());
            if(dept!=null){
                table = new CdAddressPeople();
                table.setAreaCode(dept.getHospAreaCode());
                table.setAreaSname(dept.getHospName());
                table.setAreaName(dept.getHospName());
                table.setUpAreaId(dept.getHospUpcityareaId());
                table.setLevel(dept.getHospLevel());
                table.setAreaYear( ExtendDate.getYYYY(Calendar.getInstance()));
                table.setAreaCreateTime(Calendar.getInstance());
                table.setAreaPopulation(qvo.getAreaPopulation());
                table.setAreaRate(qvo.getAreaRate());
                table.setAreaFocus(qvo.getAreaFocus());
                table.setAreaFocusRate(qvo.getAreaFocusRate());
                table.setAreaEconomic(qvo.getAreaEconomic());
                table.setAreaEconomicRate(qvo.getAreaEconomicRate());
                table.setAreaState("1");
                double rete = MyMathUtil.div(Double.parseDouble(qvo.getAreaRate()),Double.parseDouble("100"),2);
                double peopleNum = MyMathUtil.mul(Double.parseDouble(qvo.getAreaPopulation()),rete);
                table.setAreaTarget(String.valueOf((int) peopleNum));
                double focusRete = MyMathUtil.div(Double.parseDouble(qvo.getAreaFocusRate()),Double.parseDouble("100"),2);
                double focusNum = MyMathUtil.mul(Double.parseDouble(qvo.getAreaFocus()),focusRete);
                table.setAreaFocusTarget(String.valueOf((int) focusNum));
                if(StringUtils.isNotBlank(qvo.getAreaEconomicRate())){
                    double economicRate = MyMathUtil.div(Double.parseDouble(qvo.getAreaEconomicRate()),Double.parseDouble("100"),2);
                    double economicNum = MyMathUtil.mul(Double.parseDouble(qvo.getAreaEconomic()),economicRate);
                    table.setAreaEconomicTarget(String.valueOf((int) economicNum));
                }
                if(StringUtils.isNotBlank(qvo.getAreaEconomicDbh())){
                    table.setAreaEconomicDbh(qvo.getAreaEconomicDbh());
                }
                if(StringUtils.isNotBlank(qvo.getAreaEconomicJklm())){
                    table.setAreaEconomicJklm(qvo.getAreaEconomicJklm());
                }
                if(StringUtils.isNotBlank(qvo.getAreaEconomicTkh())){
                    table.setAreaEconomicTkh(qvo.getAreaEconomicTkh());
                }
                if(StringUtils.isNotBlank(qvo.getAreaEconomicJsjt())){
                    table.setAreaEconomicJsjt(qvo.getAreaEconomicJsjt());
                }
                table.setAreaDisSignTop(qvo.getDisSignTop());
                table.setAreaDisSignWay(qvo.getDisSignWay());
                table.setAreaSignWay(qvo.getSignWay());
                table.setAreaSignTop(qvo.getSignTop());
                sysDao.getServiceDo().add(table);
            }

        }
        AppDrTargetEntity vo = new AppDrTargetEntity();
        if(table!=null){
            vo.setUpAreaFocus(table.getUpAreaFocus());
            vo.setTime(ExtendDate.getYMD_h_m_s(table.getAreaCreateTime()));
            vo.setId(table.getId());
            vo.setAreaYear(table.getAreaYear());
            vo.setAreaTarget(table.getAreaTarget());
            vo.setAreaSname(table.getAreaSname());
            vo.setAreaRate(table.getAreaRate());
            vo.setAreaPopulation(table.getAreaPopulation());
            vo.setAreaName(table.getAreaName());
            vo.setAreaFocusTarget(table.getAreaFocusTarget());
            vo.setAreaFocusRate(table.getAreaFocusRate());
            vo.setAreaCode(table.getAreaCode());
            vo.setUpAreaPopulation(table.getUpAreaPopulation());
            vo.setAreaFocus(table.getAreaFocus());
            vo.setSignTop(table.getAreaSignTop());
            vo.setSignWay(table.getAreaSignWay());
            vo.setDisSignTop(table.getAreaDisSignTop());
            vo.setDisSignWay(table.getAreaDisSignWay());
            vo.setState(table.getAreaState());
            return vo;
        }
       return null;
    }

    @Override
    public CdAddressPeople findByCode(String code) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",code);
        String sql =" SELECT * FROM CP_CITY_AREA_PEOPLE WHERE 1=1";
        sql += " AND AREA_CODE =:code";
        sql += " ORDER BY AREA_YEAR DESC";
        List<CdAddressPeople> ls = sysDao.getServiceDo().findSqlMap(sql,map,CdAddressPeople.class);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    @Override
    public CdAddressPeople findUpByCode(String code) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String sql = " SELECT a.* FROM CP_CITY_AREA_PEOPLE a INNER JOIN  CP_CITY_AREA_PEOPLE b ON a.AREA_CODE = b.UP_AREA_ID WHERE 1=1";
        map.put("code",code);
        map.put("year",year);
        sql += " AND b.AREA_CODE =:code AND b.AREA_YEAR =:year";
        List<CdAddressPeople> ls = sysDao.getServiceDo().findSqlMap(sql,map,CdAddressPeople.class);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    @Override
    public List<CdAddressPeople> findByCacheList(String code,String year) throws Exception {
            return sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                    .createCriteria(CdAddressPeople.class)
                    .add(Restrictions.eq("areaCode", code))
                    .add(Restrictions.eq("areaYear", year))
                    .setCacheable(true)
                    .list();
    }

    @Override
    public CdAddressPeople findByCacheCode(String code,String year) throws Exception {
        return (CdAddressPeople)sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(CdAddressPeople.class)
                .add(Restrictions.eq("areaCode", code))
                .add(Restrictions.eq("areaYear", year))
                .setCacheable(true)
                .uniqueResult();
    }

    @Override
    public CdAddressPeopleVo findByLevelCode(String code, String year,String level) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT " +
                "  CAST(IFNULL(SUM(f.AREA_ECONOMIC_JKLM),0) AS CHAR) areaEconomicJklm," +
                "  CAST(IFNULL(SUM(f.AREA_ECONOMIC_DBH),0) AS CHAR) areaEconomicDbh," +
                "  CAST(IFNULL(SUM(f.AREA_ECONOMIC_JSJT),0) AS CHAR) areaEconomicJsjt," +
                "  CAST(IFNULL(SUM(f.AREA_ECONOMIC_TKH),0) AS CHAR) areaEconomicTkh" +
                "  FROM " +
                "  cp_city_area_people f " ;
        if("3".equals(level)){
            sql +=  "WHERE f.AREA_YEAR =:year " +
                    "AND f.UP_AREA_ID =:code ";
            map.put("code",code);
        }
        if("2".equals(level)){
            sql +=  "WHERE f.AREA_YEAR =:year " +
                    "AND f.LEVEL_NAME = '4' ";
        }

        map.put("year",year);
        List<CdAddressPeopleVo> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,CdAddressPeopleVo.class);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }
}
