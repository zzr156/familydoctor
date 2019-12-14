package com.ylz.view.performance.action;

import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.bizDo.performance.vo.PerformanceVo;
import com.ylz.packaccede.common.action.CommonAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 绩效管理
 */
@SuppressWarnings("all")
@Action(
        value="performance",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class PerformanceAction extends CommonAction{


    /**
     * 首页统计
     * qvo hospId 社区主键
     * @return
     */
    public String appIndexCount(){
        try{
            PerformanceVo qvo = (PerformanceVo)this.getAppJson(PerformanceVo.class);
            if(qvo != null ){
                Map<String,Object> map = this.getSysDao().getAppPerFormanceDao().findIndexCount(qvo);
                Map<String,Object> maps =this.getSysDao().getAppPerFormanceDao().findTeamTop(qvo);
                map.putAll(maps);
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 工作进度统计(总计划,未完成,完成,完成率,周,月数据)
     * @param qvo areaId 区域 hospId 医院主键 teamId 团队主键 drId 医生主键
     * @return
     */
    public String appWorkPlan(){
        try{
            PerformanceVo qvo = (PerformanceVo)this.getAppJson(PerformanceVo.class);
            if(qvo != null){
                Map<String,Object> map = this.getSysDao().getAppPerFormanceDao().findWorkPlanCount(qvo);
                this.getAjson().setMap(map);
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 随访工作量
     * @return
     */
    public String appFollowPlanWork(){
        try{
            PerformanceVo qvo = (PerformanceVo)this.getAppJson(PerformanceVo.class);
            if(qvo != null){
                Map<String,Object> map = this.getSysDao().getAppPerFormanceDao().findFollowWorkPlanCount(qvo);
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 签约居民健康分布情况
     * @param qvo hospId 医院主键 drId 医生主键 labelGruops 疾病类型 多使用分号隔开 labelGruopsColor 疾病类型颜色 多使用分号隔开
     * @return
     */
    public String findHealthGroup(){
        try{

            PerformanceVo qvo = (PerformanceVo)this.getAppJson(PerformanceVo.class);
            if(qvo != null){
                 this.getAjson().setRows(this.getSysDao().getAppPerFormanceDao().findHealthGroup(qvo));
                 this.getAjson().setMsgCode("800");
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 健康干预工作量统计
     * @param qvo hospId 医院主键 drId 医生主键
     * @return
     */
    public String findHealthMeddle(){
        try{
            PerformanceVo qvo = (PerformanceVo)this.getAppJson(PerformanceVo.class);
            if(qvo != null){
                this.getAjson().setMap(this.getSysDao().getAppPerFormanceDao().findHealthMeddle(qvo));
                this.getAjson().setMsgCode("800");
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 拒管居民动态
     * @param qvo hospId 医院主键 drId 医生主键 startYyyyMm 开始月份 endYyyyMm 结束月份
     */
    public String findRefuseSign(){
        try{

            PerformanceVo qvo = (PerformanceVo)this.getAppJson(PerformanceVo.class);
            if(qvo != null){
                this.getAjson().setMap(this.getSysDao().getAppPerFormanceDao().findRefuseSign(qvo));
                this.getAjson().setMsgCode("800");
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 随访任务量统计
     * @param qvo hospId 医院主键 drId 医生主键
     * @return
     */
    public String sfTaskLoad(){
        try{
            PerformanceVo qvo = (PerformanceVo)getAppJson(PerformanceVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                Map<String,Object> map = this.sysDao.getAppPerFormanceDao().findByTime(qvo);
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }



    /**
     * 工作进度，随访，健康干预
     * @param qvo hospId 社区主键
     * @return
     */
    public String findTeamNo(){
        try{
            PerformanceVo qvo = (PerformanceVo)getAppJson(PerformanceVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                Map<String,Object> map = this.sysDao.getAppPerFormanceDao().findTeamNo(qvo);
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 居民分析
     * @return
     */
    public String appResidentAnalysis(){
        try{
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                Map<String,Object> map = new HashMap<String,Object>();
                Map<String,Object> mapHealth = this.getSysDao().getAppPerFormanceDao().findHealthGroupCount(qvo);//健康分布
                Map<String,Object> mapPers = this.getSysDao().getAppPerFormanceDao().findPersGroupCount(qvo);//服务分布

                Map<String,Object> ageMap = sysDao.getAppPerFormanceDao().findAgeCount(qvo);//年龄分布
                Map<String,Object> economicMap = sysDao.getAppPerFormanceDao().findEconomicCount(qvo);//人口经济性质分布
                Map<String,Object> genderMap = sysDao.getAppPerFormanceDao().findGenderCount(qvo);//性别分布

                map.put("mapHealth",mapHealth);
                map.put("mapPers",mapPers);

                map.put("ageMap",ageMap);
                map.put("economicMap",economicMap);
                map.put("genderMap",genderMap);

                this.getAjson().setMap(map);
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }



    /**
     * 统计遵从医嘱
     * @param qvo drId 医生主键
     * @param qvo startDate 开始日期
     * @param qvo endDate 结束日期
     * @return
     */
    public String findFollowDoctor(){
        try{
            PerformanceVo qvo = (PerformanceVo)getAppJson(PerformanceVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<Map<String, Object>> list = this.sysDao.getAppPerFormanceDao().findFollowDoctor(qvo);
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

}
