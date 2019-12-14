package com.ylz.bizDo.mangecount.dao.impl;

import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.cd.po.CdAddressRate;
import com.ylz.bizDo.jtapp.commonEntity.AppPlannedPeopleEntity;
import com.ylz.bizDo.mangecount.dao.AppCityAreaDao;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 居民分析
 */
@Service("appCityAreaDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppCityAreaDaoImpl implements AppCityAreaDao{

    private static final String month ="11";

    @Autowired
    private SysDao sysDao;

    @Override
    public void getCityAreaInit() throws Exception {
        try{
            String year = this.lastYear();
            String nowYear = this.nowYear();
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = "SELECT * FROM CP_CITY_AREA where LEVEL_NAME <> '5' AND AREA_CODE like '35%' ";
            String sqlRate = "SELECT * FROM CP_CITY_AREA_RATE ";
            List<CdAddressRate> lsRate =  sysDao.getServiceDo().findSqlMap(sqlRate,map,CdAddressRate.class);
            List<CdAddress> ls = sysDao.getServiceDo().findSqlMap(sql,map,CdAddress.class);
            if(ls != null && ls.size() >0){
                for(CdAddress p : ls){
                    CdAddressPeople vo = sysDao.getCdAddressPeopleDao().findByCacheCode(p.getCtcode(),nowYear);
                    if(vo != null){
                        AppPlannedPeopleEntity v = this.sysDao.getSecurityCardAsyncBean().getPlannedPeople(year,month,p.getCtcode());
                        if(v != null){
                            vo.setUpAreaPopulation(String.valueOf(v.getReg()));
                            vo.setAreaPopulation(String.valueOf(v.getReg()));
                            double target =  Double.parseDouble(vo.getAreaRate())/100;
                            double mbs = v.getReg()*target;
                            long mbss = Math.round(mbs);
                            vo.setAreaTarget(String.valueOf(mbss));
                            // TOODO :重点人数 暂时根据户籍人数算出目标数后 在根据总户籍人数-目标数 得出重点人数 上线的时候,如果有接口就从接口来,没有接口自己手填
                            int result = v.getReg() - Integer.parseInt(vo.getAreaTarget());
                            vo.setUpAreaFocus(String.valueOf(result));
                            vo.setAreaFocus(String.valueOf(result));
                            vo.setAreaUpEconomic(String.valueOf(result));
                            vo.setAreaEconomic(String.valueOf(result));
                            double targetFoucus =  Double.parseDouble(vo.getAreaFocusRate())/100;
                            double mbsFoucus = result*targetFoucus;
                            long mbssFoucus = Math.round(mbsFoucus);
                            //目标数
                            double targetEconomic = Double.parseDouble(vo.getAreaEconomicRate())/100;
                            double mbsEconomic = result*targetEconomic;
                            long mbssEconomic = Math.round(mbsEconomic);
                            vo.setAreaEconomicTarget(String.valueOf(mbssEconomic));
                            vo.setAreaFocusTarget(String.valueOf(mbssFoucus));
                        }
                        this.sysDao.getServiceDo().modify(vo);
                    }else{
                        vo.setAreaCode(p.getCtcode());
                        vo.setAreaCreateTime(Calendar.getInstance());
                        vo.setAreaName(p.getAreaName());
                        vo.setAreaSname(p.getAreaSname());
                        vo.setAreaYear(nowYear);
                        vo.setUpAreaId(p.getPid());
                        vo.setLevel(p.getLevel());
                        vo.setAreaState(CommonEnable.JINYONG.getValue());
                        if(lsRate != null && lsRate.size() >0){
                            for(CdAddressRate rate : lsRate){
                                if(p.getLevel().equals("1") || p.getLevel().equals("2")){
                                    if(p.getCtcode().equals(rate.getId())){
                                        vo.setAreaFocusRate(rate.getAreaPointPeopleRate());
                                        vo.setAreaRate(rate.getAreaPopulationRate());
                                        vo.setAreaEconomicRate(rate.getAreaEconomicRate());
//                                    vo.setAreaEconomicTargerRate(rate.getAreaPeopleEconomicRate());
                                        break;
                                    }
                                }else {
                                    String areaCode = AreaUtils.getAreaCodeAll(p.getCtcode(),"2");
                                    if(areaCode.contains(rate.getId())){
                                        vo.setAreaFocusRate(rate.getAreaPointPeopleRate());
                                        vo.setAreaRate(rate.getAreaPopulationRate());
                                        vo.setAreaEconomicRate(rate.getAreaEconomicRate());
                                        break;
                                    }
                                }
                            }
                        }
                        AppPlannedPeopleEntity v = this.sysDao.getSecurityCardAsyncBean().getPlannedPeople(year,month,p.getCtcode());
                        if(v != null){
                            vo.setUpAreaPopulation(String.valueOf(v.getReg()));
                            vo.setAreaPopulation(String.valueOf(v.getReg()));
                            double target =  Double.parseDouble(vo.getAreaRate())/100;
                            double mbs = v.getReg()*target;
                            long mbss = Math.round(mbs);
                            vo.setAreaTarget(String.valueOf(mbss));
                            // TOODO :重点人数 暂时根据户籍人数算出目标数后 在根据总户籍人数-目标数 得出重点人数 上线的时候,如果有接口就从接口来,没有接口自己手填
                            int result = v.getReg() - Integer.parseInt(vo.getAreaTarget());
                            vo.setUpAreaFocus(String.valueOf(result));
                            vo.setAreaFocus(String.valueOf(result));
                            vo.setAreaUpEconomic(String.valueOf(result));
                            vo.setAreaEconomic(String.valueOf(result));
                            double targetFoucus =  Double.parseDouble(vo.getAreaFocusRate())/100;
                            double mbsFoucus = result*targetFoucus;
                            long mbssFoucus = Math.round(mbsFoucus);
                            //目标数
                            double targetEconomic = Double.parseDouble(vo.getAreaEconomicRate())/100;
                            double mbsEconomic = result*targetEconomic;
                            long mbssEconomic = Math.round(mbsEconomic);
                            vo.setAreaEconomicTarget(String.valueOf(mbssEconomic));
                            vo.setAreaFocusTarget(String.valueOf(mbssFoucus));
                        }
                        this.sysDao.getServiceDo().add(vo);
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getCityAreaPeopleInit() throws Exception {
        try{
            String year = this.lastYear();
            String nowYear = this.nowYear();
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = "SELECT * FROM CP_CITY_AREA_PEOPLE where AREA_POPULATION IS NULL";
            List<CdAddressPeople> ls = sysDao.getServiceDo().findSqlMap(sql,map,CdAddressPeople.class);
            if(ls != null && ls.size() >0){
                for(CdAddressPeople vo: ls){
                    AppPlannedPeopleEntity v = this.sysDao.getSecurityCardAsyncBean().getPlannedPeople(year,month,vo.getAreaCode());
                    if(v != null){
                        vo.setUpAreaPopulation(String.valueOf(v.getReg()));
                        vo.setAreaPopulation(String.valueOf(v.getReg()));
                        double target =  Double.parseDouble(vo.getAreaRate())/100;
                        double mbs = v.getReg()*target;
                        long mbss = Math.round(mbs);
                        vo.setAreaTarget(String.valueOf(mbss));
                        //重点人数 暂时根据户籍人数算出目标数后 在根据总户籍人数-目标数 得出重点人数 上线的时候,如果有接口就从接口来,没有接口自己手填
                        int result = v.getReg() - Integer.parseInt(vo.getAreaTarget());
                        vo.setUpAreaFocus(String.valueOf(result));
                        vo.setAreaFocus(String.valueOf(result));
                        double targetFoucus =  Double.parseDouble(vo.getAreaFocusRate())/100;
                        double mbsFoucus = result*targetFoucus;
                        long mbssFoucus = Math.round(mbsFoucus);
                        vo.setAreaFocusTarget(String.valueOf(mbssFoucus));
                        //人口经济性质
                        vo.setAreaUpEconomic(String.valueOf(result));
                        vo.setAreaEconomic(String.valueOf(result));
                        double targetEconomic = Double.parseDouble(vo.getAreaEconomicRate())/100;
                        double mbsEconomic = result*targetEconomic;
                        long mbssEconomic = Math.round(mbsEconomic);
                        vo.setAreaEconomicTarget(String.valueOf(mbssEconomic));

                    }
                    this.sysDao.getServiceDo().modify(vo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 上一年
     * @return
     */
    private String lastYear() throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Calendar c = Calendar.getInstance();
        //过去一年
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String year = format.format(y);
        return year;
    }

    /**
     * 当前年
     * @return
     */
    private String nowYear() throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Calendar c = Calendar.getInstance();
        Date y = c.getTime();
        String year = format.format(y);
        return year;
    }

    public static void main(String[] args) {
        double s = Double.parseDouble("30")/100;
        System.out.println(s);
    }
    @Override
    public void getCityAreaInitNew() throws Exception {
        try{
            String year = this.lastYear();
            String nowYear = this.nowYear();
//            String year = "2018";
//            String nowYear = "2019";
            Map<String,Object> map = new HashMap<String,Object>();
            //查询确定是哪个市
            String cityCode = PropertiesUtil.getConfValue("cityJdCode");
            map.put("cityCode",cityCode+"%");
            String sql = "SELECT * FROM CP_CITY_AREA where LEVEL_NAME <> '5' AND AREA_CODE like :cityCode ";
            String sqlRate = "SELECT * FROM CP_CITY_AREA_RATE WHERE CITY_AREA_ID LIKE :cityCode ";
            List<CdAddressRate> lsRate =  sysDao.getServiceDo().findSqlMap(sqlRate,map,CdAddressRate.class);
            List<CdAddress> ls = sysDao.getServiceDo().findSqlMap(sql,map,CdAddress.class);
            if(ls != null && ls.size() >0){
                for(CdAddress p : ls){
                    CdAddressPeople vo = sysDao.getCdAddressPeopleDao().findByCacheCode(p.getCtcode(),year);
                    if(vo != null){
                        //查到上一年度的数据，在判断今年是否已生成数据,如果没有直接把上一年的数据带入当前年份
                        CdAddressPeople vvo = sysDao.getCdAddressPeopleDao().findByCacheCode(p.getCtcode(),nowYear);
                        if(vvo == null){//率取上一年度，参考取
                            vvo = new CdAddressPeople();//没有,new一个对象set赋值或copy
                            vvo.setAreaYear(nowYear);
                            vvo.setAreaCode(vo.getAreaCode());
                            vvo.setAreaName(vo.getAreaName());
                            vvo.setAreaSname(vo.getAreaSname());
                            vvo.setLevel(vo.getLevel());
                            vvo.setAreaPopulation(vo.getAreaPopulation());
                            vvo.setAreaTarget(vo.getAreaTarget());
                            vvo.setAreaRate(vo.getAreaRate());
                            vvo.setUpAreaId(vo.getUpAreaId());
                            vvo.setAreaFocus(vo.getAreaFocus());
                            vvo.setAreaFocusRate(vo.getAreaFocusRate());
                            vvo.setUpAreaPopulation(vo.getUpAreaPopulation());
                            vvo.setAreaFocusTarget(vo.getAreaFocusTarget());
                            vvo.setUpAreaFocus(vo.getUpAreaFocus());
                            vvo.setAreaState(vo.getAreaState());
                            vvo.setAreaSignTop(vo.getAreaSignTop());
                            vvo.setAreaSignWay(vo.getAreaSignWay());
                            vvo.setAreaDisSignTop(vo.getAreaDisSignTop());
                            vvo.setAreaDisSignWay(vo.getAreaDisSignWay());
                            vvo.setAreaEconomic(vo.getAreaEconomic());
                            vvo.setAreaUpEconomic(vo.getAreaUpEconomic());
                            vvo.setAreaEconomicTarget(vo.getAreaEconomicTarget());
                            vvo.setAreaEconomicRate(vo.getAreaEconomicRate());
                            vvo.setAreaEconomicJklm(vo.getAreaEconomicJklm());
                            vvo.setAreaEconomicDbh(vo.getAreaEconomicDbh());
                            vvo.setAreaEconomicTkh(vo.getAreaEconomicTkh());
                            vvo.setAreaEconomicJsjt(vo.getAreaEconomicJsjt());
                            vvo.setAreaCreateTime(Calendar.getInstance());
                            AppPlannedPeopleEntity v = this.sysDao.getSecurityCardAsyncBean().getPlannedPeopleNew(year,month,p.getCtcode(),cityCode);
                            if(v != null){
                                vvo.setUpAreaPopulation(String.valueOf(v.getReg()));
                                vvo.setAreaPopulation(String.valueOf(v.getReg()));
                                double target =  Double.parseDouble(vo.getAreaRate())/100;
                                double mbs = v.getReg()*target;
                                long mbss = Math.round(mbs);
                                vvo.setAreaTarget(String.valueOf(mbss));
                                // TOODO :重点人数 暂时根据户籍人数算出目标数后 在根据总户籍人数-目标数 得出重点人数 上线的时候,如果有接口就从接口来,没有接口自己手填
                                int result = v.getReg() - Integer.parseInt(vo.getAreaTarget());
                                vvo.setUpAreaFocus(String.valueOf(result));
                                vvo.setAreaFocus(String.valueOf(result));
                                vvo.setAreaUpEconomic(String.valueOf(result));
                                vvo.setAreaEconomic(String.valueOf(result));
                                if(StringUtils.isNotBlank(vo.getAreaFocusRate())){
                                    double targetFoucus =  Double.parseDouble(vo.getAreaFocusRate())/100;
                                    double mbsFoucus = result*targetFoucus;
                                    long mbssFoucus = Math.round(mbsFoucus);
                                    vvo.setAreaFocusTarget(String.valueOf(mbssFoucus));
                                }
                                if(StringUtils.isNotBlank(vo.getAreaEconomicRate())){
                                    //目标数
                                    double targetEconomic = Double.parseDouble(vo.getAreaEconomicRate())/100;
                                    double mbsEconomic = result*targetEconomic;
                                    long mbssEconomic = Math.round(mbsEconomic);
                                    vvo.setAreaEconomicTarget(String.valueOf(mbssEconomic));
                                }
                            }
                            sysDao.getServiceDo().add(vvo);
                        }else{//查到当前年份数据，获取计生更新数据
                            AppPlannedPeopleEntity v = this.sysDao.getSecurityCardAsyncBean().getPlannedPeopleNew(year,month,p.getCtcode(),cityCode);
                            if(v != null){
                                vvo.setUpAreaPopulation(String.valueOf(v.getReg()));
                                vvo.setAreaPopulation(String.valueOf(v.getReg()));
                                double target =  Double.parseDouble(vo.getAreaRate())/100;
                                double mbs = v.getReg()*target;
                                long mbss = Math.round(mbs);
                                vvo.setAreaTarget(String.valueOf(mbss));
                                // TOODO :重点人数 暂时根据户籍人数算出目标数后 在根据总户籍人数-目标数 得出重点人数 上线的时候,如果有接口就从接口来,没有接口自己手填
                                int result = v.getReg() - Integer.parseInt(vo.getAreaTarget());
                                vvo.setUpAreaFocus(String.valueOf(result));
                                vvo.setAreaFocus(String.valueOf(result));
                                vvo.setAreaUpEconomic(String.valueOf(result));
                                vvo.setAreaEconomic(String.valueOf(result));
                                if(StringUtils.isNotBlank(vo.getAreaFocusRate())){
                                    double targetFoucus =  Double.parseDouble(vo.getAreaFocusRate())/100;
                                    double mbsFoucus = result*targetFoucus;
                                    long mbssFoucus = Math.round(mbsFoucus);
                                    vvo.setAreaFocusTarget(String.valueOf(mbssFoucus));
                                }
                                //目标数
                                if(StringUtils.isNotBlank(vo.getAreaEconomicRate())){
                                    double targetEconomic = Double.parseDouble(vo.getAreaEconomicRate())/100;
                                    double mbsEconomic = result*targetEconomic;
                                    long mbssEconomic = Math.round(mbsEconomic);
                                    vvo.setAreaEconomicTarget(String.valueOf(mbssEconomic));
                                }
                            }
                            sysDao.getServiceDo().modify(vvo);
                        }
                    }else{//如果没有查到则到计生查询数据
                        vo = new CdAddressPeople();
                        vo.setAreaCode(p.getCtcode());
                        vo.setAreaCreateTime(Calendar.getInstance());
                        vo.setAreaName(p.getAreaName());
                        vo.setAreaSname(p.getAreaSname());
                        vo.setAreaYear(nowYear);
                        vo.setUpAreaId(p.getPid());
                        vo.setLevel(p.getLevel());
                        vo.setAreaState(CommonEnable.JINYONG.getValue());
                        if(lsRate != null && lsRate.size() >0){
                            for(CdAddressRate rate : lsRate){
                                if("1".equals(p.getLevel()) || "2".equals(p.getLevel())){
                                    if(p.getCtcode().equals(rate.getId())){
                                        vo.setAreaFocusRate(rate.getAreaPointPeopleRate());
                                        vo.setAreaRate(rate.getAreaPopulationRate());
                                        vo.setAreaEconomicRate(rate.getAreaEconomicRate());
//                                    vo.setAreaEconomicTargerRate(rate.getAreaPeopleEconomicRate());
                                        break;
                                    }
                                }else {
                                    String areaCode = AreaUtils.getAreaCodeAll(p.getCtcode(),"2");
                                    if(areaCode.contains(rate.getId())){
                                        vo.setAreaFocusRate(rate.getAreaPointPeopleRate());
                                        vo.setAreaRate(rate.getAreaPopulationRate());
                                        vo.setAreaEconomicRate(rate.getAreaEconomicRate());
                                        break;
                                    }
                                }
                            }
                        }

                        AppPlannedPeopleEntity v = this.sysDao.getSecurityCardAsyncBean().getPlannedPeopleNew(year,month,p.getCtcode(),cityCode);
                        if(v != null){
                            vo.setUpAreaPopulation(String.valueOf(v.getReg()));
                            vo.setAreaPopulation(String.valueOf(v.getReg()));
                            double target =  Double.parseDouble(vo.getAreaRate())/100;
                            double mbs = v.getReg()*target;
                            long mbss = Math.round(mbs);
                            vo.setAreaTarget(String.valueOf(mbss));
                            // TOODO :重点人数 暂时根据户籍人数算出目标数后 在根据总户籍人数-目标数 得出重点人数 上线的时候,如果有接口就从接口来,没有接口自己手填
                            int result = v.getReg() - Integer.parseInt(vo.getAreaTarget());
                            vo.setUpAreaFocus(String.valueOf(result));
                            vo.setAreaFocus(String.valueOf(result));
                            vo.setAreaUpEconomic(String.valueOf(result));
                            vo.setAreaEconomic(String.valueOf(result));
                            if(StringUtils.isNotBlank(vo.getAreaFocusRate())){
                                double targetFoucus =  Double.parseDouble(vo.getAreaFocusRate())/100;
                                double mbsFoucus = result*targetFoucus;
                                long mbssFoucus = Math.round(mbsFoucus);
                                vo.setAreaFocusTarget(String.valueOf(mbssFoucus));
                            }

                            //目标数
                            if(StringUtils.isNotBlank(vo.getAreaEconomicRate())){
                                double targetEconomic = Double.parseDouble(vo.getAreaEconomicRate())/100;
                                double mbsEconomic = result*targetEconomic;
                                long mbssEconomic = Math.round(mbsEconomic);
                                vo.setAreaEconomicTarget(String.valueOf(mbssEconomic));
                            }

                        }
                        this.sysDao.getServiceDo().add(vo);
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
