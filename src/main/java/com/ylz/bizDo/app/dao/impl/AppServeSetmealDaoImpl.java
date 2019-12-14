package com.ylz.bizDo.app.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ylz.bizDo.jtapp.gaiRuiEntity.GaiRuiMealEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ylz.bizDo.app.dao.AppServeSetmealDao;
import com.ylz.bizDo.app.po.AppEconAndGov;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppServeEcon;
import com.ylz.bizDo.app.po.AppServeGov;
import com.ylz.bizDo.app.po.AppServeGroup;
import com.ylz.bizDo.app.po.AppServeObject;
import com.ylz.bizDo.app.po.AppServePackage;
import com.ylz.bizDo.app.po.AppServeSetmeal;
import com.ylz.bizDo.app.vo.AppServeSetmealQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.jtapp.commonEntity.AppGrantInAidEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drEntity.AppServeEntity;
import com.ylz.bizDo.jtapp.signSersetEntity.AppSignSerMealEntity;
import com.ylz.bizDo.jtapp.signSersetEntity.AppSignServeMealEntity;
import com.ylz.bizDo.jtapp.signSersetVo.AppSignSerQvo;
import com.ylz.bizDo.web.po.WebServeSetmeal;
import com.ylz.bizDo.web.vo.WebSignVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.comEnum.CommonGroupCxType;
import com.ylz.packcommon.common.comEnum.LabelManageType;
import com.ylz.packcommon.common.comEnum.PerformanceType;
import com.ylz.packcommon.common.comEnum.ResidentMangeType;

/**
 * Created by zzl on 2017/8/15.
 */
@Service("appServeSetmealDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppServeSetmealDaoImpl implements AppServeSetmealDao {
    @Autowired
    private SysDao sysDao;

    /**
     * 条件查询
     * @param qvo
     * @return
     * @throws Exception
     */
	@Override
	public List<AppServeSetmeal> findList(AppServeSetmealQvo qvo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "SELECT * FROM APP_SERVE_SETMEAL WHERE 1=1";
		if (StringUtils.isNotBlank(qvo.getContent())) {
			map.put("content", "%" + qvo.getContent() + "%");
			if (CommonGroupCxType.FWNR.getValue().equals(qvo.getType())) {
				sql += " AND SERSM_PK_TITLE LIKE :content";
			}
			if (CommonGroupCxType.FWRQ.getValue().equals(qvo.getType())) {
				sql += " AND SERSM_OBJECT_TITLE LIKE :content";
			}
			if (CommonGroupCxType.TCMC.getValue().equals(qvo.getType())) {
				sql += " AND SERSM_NAME LIKE :content";
			}
		}
		if (StringUtils.isNotBlank(qvo.getOpenArea())) {
			List<CdAddress> lss = sysDao.getServiceDo().loadByPk(CdAddress.class, "areaSname", qvo.getOpenArea());
			String areaName = "";
			if (lss != null && lss.size() > 0) {
				areaName = lss.get(0).getId();
			}
			if (StringUtils.isNotBlank(areaName)) {
				map.put("shen", areaName.substring(0, 2) + "0000000000");
				map.put("shi", areaName.substring(0, 4) + "00000000");
				map.put("qu", areaName.substring(0, 6) + "000000");
				map.put("jiedao", areaName.substring(0, 9) + "000");
				map.put("areaName", areaName);
				sql += " AND ( find_in_set(:shen, SERSM_OPEN_AREA) OR "
						+ "find_in_set(:shi, SERSM_OPEN_AREA) OR find_in_set(:qu, SERSM_OPEN_AREA) "
						+ "OR find_in_set(:jiedao, SERSM_OPEN_AREA) OR find_in_set(:areaName, SERSM_OPEN_AREA)) ";
			}
		}
		if ("2".equals(qvo.getRoleType())) {
			map.put("sj", qvo.getAreaCode().substring(0, 2) + "0000000000");
			map.put("city", qvo.getAreaCode().substring(0, 4) + "00000000");
			map.put("street", qvo.getAreaCode().substring(0, 6) + "000000");
			map.put("area", qvo.getAreaCode().substring(0, 9) + "000");
			map.put("areaCode", qvo.getAreaCode());
			map.put("hospId", qvo.getHospId());
			map.put("level", "0");
			map.put("openState", "1");
			/*
			 * //对于市级医院来说查询自己+系统开放的
			 * if(StringUtils.isBlank(qvo.getAreaCode())){//市权限 sql +=
			 * " AND ( SERSM_CREATE_DEPT =:hospId OR ( SERSM_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))"
			 * ; }else{//医院权限查询自己的+市的+系统开放的SERSM_OPEN_AREA sql +=
			 * " AND ( SERSM_CREATE_DEPT =:hospId OR SERSM_AREA_CODE =:areaCode OR (SERSM_OPEN_STATE =:openState AND ID IN (SELECT a.OPEN_SER_ID FROM APP_OPEN_OBJECT a WHERE a.OPEN_HOSP_ID =:hospId )))"
			 * ; }
			 */
			sql += " AND (SERSM_CREATE_DEPT =:hospId OR (SERSM_OPEN_STATE =:openState "
					+ "AND (find_in_set(:sj, SERSM_OPEN_AREA) OR find_in_set(:city, SERSM_OPEN_AREA) OR find_in_set(:street, SERSM_OPEN_AREA)"
					+ " OR find_in_set(:area, SERSM_OPEN_AREA) OR find_in_set(:areaCode, SERSM_OPEN_AREA))))";
		} else if ("9999".equals(qvo.getRoleType())) {
			String openArea = qvo.getOpenArea();
			if (qvo.getOpenArea().length() > 4) {
				openArea = qvo.getAreaCode().substring(0, 4);
			}
			map.put("likeAreaCodeStart", openArea + "%");
			map.put("likeAreaCodeOther", "%;" + openArea + "%");
			map.put("hospId", qvo.getHospId());
			map.put("level", "0");
			map.put("openState", "1");
			sql = sql + " AND (SERSM_CREATE_DEPT =:hospId OR (SERSM_OPEN_AREA like :likeAreaCodeStart OR SERSM_OPEN_AREA like :likeAreaCodeOther))";
			// sql = sql + " AND ID = '38d4dd31-bf1d-4372-a4a8-5cf5865d296f' " ;
			sql = sql + " ORDER BY SERSM_VALUE ";
			List<AppServeSetmeal> ls = this.sysDao.getServiceDo().findSqlMap(sql, map, AppServeSetmeal.class);
			return ls;
		}
		sql += " ORDER BY SERSM_VALUE DESC";
		List<AppServeSetmeal> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppServeSetmeal.class, qvo);
		return ls;
	}

    /**
     * 查询套餐信息
     * @return
     * @throws Exception
     */
    @Override
    public List<AppSignSerMealEntity> findSerAllMeal(AppSignSerQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("hospId",qvo.getHospId());
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
        map.put("areaCode",qvo.getAreaCode());
        map.put("level","0");
        map.put("openState","1");
        //对于市级医院来说查询自己+系统开放的
        if(StringUtils.isBlank(qvo.getAreaCode())){//市权限
            sql += " AND ( a.SERSM_CREATE_DEPT =:hospId OR ( a.SERSM_OPEN_STATE =:openState AND a.ID IN (SELECT b.OPEN_SER_ID FROM APP_OPEN_OBJECT b WHERE b.OPEN_HOSP_ID =:hospId )))";
        }else{//医院权限查询自己的+市的+系统开放的
            sql += " AND ( a.SERSM_CREATE_DEPT =:hospId OR a.SERSM_AREA_CODE =:areaCode OR (a.SERSM_OPEN_STATE =:openState AND a.ID IN (SELECT b.OPEN_SER_ID FROM APP_OPEN_OBJECT b WHERE b.OPEN_HOSP_ID =:hospId )))";
        }
        List<AppSignSerMealEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppSignSerMealEntity.class);
        return ls;
    }

    /**
     * 查询最新编号
     * @return
     */
    @Override
    public String findCode() throws Exception{
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT SERSM_VALUE FROM APP_SERVE_SETMEAL WHERE SERSM_VALUE regexp '^[0-9]+$' ORDER BY SERSM_CREATE_TIME DESC limit 1";
        List<Map> list = sysDao.getServiceDo().findSqlMap(sql,map);
        if(list!=null && list.size()>0){
            return list.get(0).get("SERSM_VALUE").toString();
        }
        return null;
    }

    /**
     * 根据医院id查询服务套餐
     * @param hospId
     * @return
     */
    @Override
    public List<AppServeEntity> findSeverMeal(String hospId) throws Exception{
        List<AppServeEntity> list = new ArrayList<>();
        if(StringUtils.isNotBlank(hospId)){
            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,hospId);
            if(dept!=null){
                Map<String,Object> map = new HashMap<>();
                map.put("hospId",hospId);
                map.put("sj",dept.getHospAreaCode().substring(0,2)+"0000000000");
                map.put("city",dept.getHospAreaCode().substring(0,4)+"00000000");
                map.put("street",dept.getHospAreaCode().substring(0,6)+"000000");
                map.put("area",dept.getHospAreaCode().substring(0,9)+"000");
                map.put("areaCode",dept.getHospAreaCode());
                map.put("level","0");
                map.put("openState","1");
                String sql = " SELECT " +
                        "a.ID serveId," +
                        "a.SERSM_NAME serveName," +
                        "a.SERSM_GROUP_ID groupId," +
                        "a.SERSM_TOTAL_FEE fee," +
                        "'' groupList " +
                        "FROM APP_SERVE_SETMEAL a " +
                        "WHERE 1=1 ";

                sql += " AND (a.SERSM_CREATE_DEPT =:hospId OR (a.SERSM_OPEN_STATE =:openState " +
                        "AND (find_in_set(:sj, a.SERSM_OPEN_AREA) OR find_in_set(:city, a.SERSM_OPEN_AREA) OR find_in_set(:street, a.SERSM_OPEN_AREA)" +
                        " OR find_in_set(:area, a.SERSM_OPEN_AREA) OR find_in_set(:areaCode, a.SERSM_OPEN_AREA))))";
                list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeEntity.class);
            }

        }
        return list;
    }


    /**
     * 导入套餐数据excel
     * @param map
     * @param user
     * @return
     */
    @Override
    public String addImportExcelMeal(Map<Integer, String> map, CdUser user) throws Exception{
        String result = "成功导入"+map.size()+"条";
        List<WebSignVo> ls = new ArrayList<WebSignVo>();

        AppServePackage pk = null;
        AppServeObject serveObject = new AppServeObject();//服务对象
        AppServeGroup group = null;
        AppServeSetmeal meal = null;
        try{
            //组合使用变量
            String gpkId = "";
            String gpkTitle = "";
            String gpkValue = "";
            String gpkType = "";

            //套餐使用变量
            String mpkTitle = "";
            String mpkValue = "";
            String mpkType = "";

            String groupValue = "";
            String groupId = "";

            String mobjecTitle = "";
            String mobjecValue = "";
            String mobjecType = "";

            String book = "";
            String mealId = "";

            for (int i = 0; i < map.size(); i++) {
                int num = i+1;
                int groupNum = 0;
                String[] ss = map.get(i).split("\\|");

                for(int j=0;j<ss.length;j++){
                    if(StringUtils.isBlank(ss[j])){
                        groupNum+=1;
                    }
                    if(j==16){
                        break;
                    }
                }
                //groupNum<10添加套餐、组合、人群、内容
                if(groupNum<10){//在组合之前有数据，添加新的服务包
                    if(StringUtils.isBlank(ss[1])){//姓名
                        throw new Exception("导入失败：第"+num+"行2列[服务包编号]不能为空!");
                    }
                    if(StringUtils.isBlank(ss[2])){
                        throw new Exception("导入失败：第"+num+"行3列[服务包名称]不能为空!");
                    }
                    if(StringUtils.isBlank(ss[10])){
                        throw new Exception("导入失败：第"+num+"行11列[服务组合编号]不能为空!");
                    }
                    if(StringUtils.isBlank(ss[12])){
                        throw new Exception("导入失败：第"+num+"行13列[服务对象编号]不能为空!");
                    }
                    if(StringUtils.isBlank(ss[13])){
                        throw new Exception("导入失败：第"+num+"行14列[服务对象名称]不能为空!");
                    }


                    if(meal!=null){//新的服务包开始，旧的服务包保存
                        meal.setSersmCreateTime(Calendar.getInstance());
                        meal.setSersmObjectValue(mobjecValue);
                        meal.setSersmObjectTitle(mobjecTitle);
                        meal.setSersmObjectType(mobjecType);
                        meal.setSersmPkValue(mpkValue);
                        meal.setSersmPkTitle(mpkTitle);
                        meal.setSersmPkType(mpkType);
                        meal.setSersmGroupId(groupId);
                        meal.setSersmGroupValue(groupValue);
                        meal.setSersmBook(book);
                        meal.setSersmOpenState("0");
                        if(StringUtils.isBlank(meal.getId())){
                            if(StringUtils.isNotBlank(mealId)){
                                modifyMeal(meal,mealId);
                                mealId="";
                            }else{
                                sysDao.getServiceDo().add(meal);
                            }
                        }else{
                            sysDao.getServiceDo().modify(meal);
                        }
                        mpkTitle = "";
                        mpkValue = "";
                        mpkType = "";

                        groupValue = "";
                        groupId = "";

                        mobjecTitle = "";
                        mobjecValue = "";
                        mobjecType = "";

                        book = "";
                    }
                    if(StringUtils.isBlank(ss[0])){//添加服务内容、服务对象、服务组合、服务套餐
                        meal = new AppServeSetmeal();
                        meal.setSersmValue(ss[1]);
                        meal.setSersmName(ss[2]);
                        meal.setSersmDownState(ss[3]);
                        meal.setSersmTotalFee(ss[4]);
                        meal.setSersmYxTimeType(ss[5]);
                        if("4".equals(ss[5])){
                            if(StringUtils.isNotBlank(ss[8])){
                                meal.setSersmStartTime(ss[8]);
                            }
                            if(StringUtils.isNotBlank(ss[9])){
                                meal.setSersmEndTime(ss[9]);
                            }
                        }
                        meal.setSersmBgDr(ss[6]);
                        meal.setSersmJcState(ss[7]);
                        if(user!=null){
                            meal.setSersmCreateId(user.getUserId());
                            if("admin".equals(user.getAccount())){
                                meal.setSersmAreaCode("0");
                                meal.setSersmLevel("0");//系统等级
                                meal.setSersmOpenState("0");//默认不开启
                            }else{
                                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                                meal.setSersmAreaCode(dept.getHospAreaCode());
                                meal.setSersmCreateDept(dept.getId());
                                if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                                    meal.setSersmLevel("1");//市级
                                }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                                    meal.setSersmLevel("2");//医院级
                                }
                            }
                        }
                    }else{//修改服务套餐
                        meal =(AppServeSetmeal) sysDao.getServiceDo().find(AppServeSetmeal.class,ss[0]);
                        if(meal!=null){
                            meal.setSersmValue(ss[1]);
                            meal.setSersmName(ss[2]);
                            meal.setSersmDownState(ss[3]);
                            meal.setSersmTotalFee(ss[4]);
                            meal.setSersmYxTimeType(ss[5]);
                            meal.setSersmBgDr(ss[6]);
                            meal.setSersmJcState(ss[7]);
                        }else {//有主键又查无数据
                            mealId = ss[0];
                            meal = new AppServeSetmeal();
                            meal.setSersmValue(ss[1]);
                            meal.setSersmName(ss[2]);
                            meal.setSersmDownState(ss[3]);
                            meal.setSersmTotalFee(ss[4]);
                            meal.setSersmYxTimeType(ss[5]);
                            if("4".equals(ss[5])){
                                if(StringUtils.isNotBlank(ss[8])){
                                    meal.setSersmStartTime(ss[8]);
                                }
                                if(StringUtils.isNotBlank(ss[9])){
                                    meal.setSersmEndTime(ss[9]);
                                }
                            }
                            meal.setSersmBgDr(ss[6]);
                            meal.setSersmJcState(ss[7]);
                            if(user!=null){
                                meal.setSersmCreateId(user.getUserId());
                                if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                                        "zzadmin".equals(user.getAccount())||
                                        "fzadmin".equals(user.getAccount())||
                                        "ptadmin".equals(user.getAccount())||
                                        "npadmin".equals(user.getAccount())||
                                        "qzadmin".equals(user.getAccount())){
                                    meal.setSersmAreaCode("0");
                                    meal.setSersmLevel("0");//系统等级
                                    meal.setSersmOpenState("0");//默认不开启
                                }else{
                                    AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                                    meal.setSersmAreaCode(dept.getHospAreaCode());
                                    meal.setSersmCreateDept(dept.getId());
                                    if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                                        meal.setSersmLevel("1");//市级
                                    }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                                        meal.setSersmLevel("2");//医院级
                                    }
                                }
                            }
                        }
                    }
                    //根据编号查询服务人群数据
                    AppServeObject object1 = sysDao.getAppServeObjectDao().findByValue(ss[12]);
                    //添加人群
                    if(object1==null){
                        object1 = new AppServeObject();
                        object1.setSeroValue(ss[12]);
                        object1.setSeroName(ss[13]);
                        object1.setSeroLabelType(ss[14]);
                        object1.setSeroFwType(ss[15]);
                        object1.setSeroState(ss[16]);
                        if(user!=null){
                            object1.setSeroCreateId(user.getUserId());
                            if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                                    "zzadmin".equals(user.getAccount())||
                                    "fzadmin".equals(user.getAccount())||
                                    "ptadmin".equals(user.getAccount())||
                                    "npadmin".equals(user.getAccount())||
                                    "qzadmin".equals(user.getAccount())){
                                object1.setSeroType("0");
                                object1.setSeroAreaCode("0");
                                object1.setSeroLevel("0");//系统等级
                                object1.setSeroOpenState("0");//默认不开启
                            }else{
                                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                                object1.setSeroAreaCode(dept.getHospAreaCode());
                                object1.setSeroDeptId(dept.getId());
                                object1.setSeroType("1");

                                if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                                    object1.setSeroLevel("1");//市级
                                }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                                    object1.setSeroLevel("2");//医院级
                                }
                            }
                        }
                        sysDao.getServiceDo().add(object1);
                    }
                    //根据编号查询服务内容
                    AppServePackage pkg =  sysDao.getAppServePackageDao().findByValue(ss[17]);
                    //添加内容
                    if(pkg==null){
                        pkg = new AppServePackage();
                        pkg.setSerpkValue(ss[17]);
                        pkg.setSerpkName(ss[18]);
                        pkg.setSerpkOpenState(ss[19]);
                        if("1".equals(ss[19])){
                            pkg.setSerpkTime(ss[20]);
                            if(StringUtils.isNotBlank(ss[21])){
                                String[] sss = ss[21].split(";");
                                if(sss.length==2){
                                    pkg.setSerpkNum(sss[0]);
                                    pkg.setSerpkIntervalType(sss[1]);
                                }else{
                                    pkg.setSerpkNum(sss[0]);
                                }
                            }
                        }
                        pkg.setSerpkBaseType(ss[22]);
                        pkg.setSerpkRemark(ss[23]);
                        if(user!=null){
                            pkg.setSerpkCreateId(user.getUserId());
                            if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                                    "zzadmin".equals(user.getAccount())||
                                    "fzadmin".equals(user.getAccount())||
                                    "ptadmin".equals(user.getAccount())||
                                    "npadmin".equals(user.getAccount())||
                                    "qzadmin".equals(user.getAccount())){
                                pkg.setSerpkType("0");//服务类型
                                pkg.setSerpkAreaCode("0");
                                pkg.setSerpkLeve("0");//系统等级
                                pkg.setSerpkState("0");//默认不开启
                            }else{
                                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                                pkg.setSerpkAreaCode(dept.getHospAreaCode());
                                pkg.setSerpkDeptId(dept.getId());
                                pkg.setSerpkType("1");
                                if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                                    pkg.setSerpkLeve("1");//市级
                                }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                                    pkg.setSerpkLeve("2");//医院级
                                }
                            }
                        }
                        sysDao.getServiceDo().add(pkg);
                    }
                    //添加组合
                    //根据组合编号查询组合信息
                    group = sysDao.getAppServeGroupDao().findByValue(ss[10]);
                    if(group==null){
                        group = new AppServeGroup();//服务组合
                        group.setSergValue(ss[10]);
                        group.setSergGroupFee(ss[11]);
                        group.setSergOpenState("0");
                        if(user!=null){
                            group.setSergCreateId(user.getUserId());
                            if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                                    "zzadmin".equals(user.getAccount())||
                                    "fzadmin".equals(user.getAccount())||
                                    "ptadmin".equals(user.getAccount())||
                                    "npadmin".equals(user.getAccount())||
                                    "qzadmin".equals(user.getAccount())){
                                group.setSergLevel("0");//系统等级
                                group.setSergAreaCode("0");
                                group.setSergOpenState("0");//默认不开启
                            }else{
                                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                                group.setSergAreaCode(dept.getHospAreaCode());
                                group.setSergDeptId(dept.getId());
                                if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                                    group.setSergLevel("1");//市级
                                }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                                    group.setSergLevel("2");//医院级
                                }
                            }
                        }
                        group.setSergObjectValue(object1.getSeroValue());
                        group.setSergObjectId(object1.getId());
                        group.setSergObjectTitle(object1.getSeroName());
                        group.setSergObjectType(object1.getSeroType());
                    }

                    //组合服务内容变量赋值
                    if(StringUtils.isBlank(gpkId)){
                        gpkId = pkg.getId();
                    }else{
                        gpkId += ";"+pkg.getId();
                    }

                    if(StringUtils.isBlank(gpkValue)){
                        gpkValue = pkg.getSerpkValue();
                    }else{
                        gpkValue +=";"+ pkg.getSerpkValue();
                    }

                    if(StringUtils.isBlank(gpkTitle)){
                        gpkTitle = pkg.getSerpkName();
                    }else{
                        gpkTitle += "," + pkg.getSerpkName();
                    }

                    if(StringUtils.isBlank(gpkType)){
                        gpkType = pkg.getSerpkBaseType();
                    }else{
                        gpkType += ";" + pkg.getSerpkBaseType();
                    }

                    if(i==map.size()-1){//最后一行时且是一条服务包数据
                        if(StringUtils.isBlank(group.getId())){
                            group.setSergPkId(gpkId);
                            group.setSergPkValue(gpkValue);
                            group.setSergPkTitle(gpkTitle);
                            group.setSergPkType(gpkType);
                            sysDao.getServiceDo().add(group);
                        }
                        book += group.getSergObjectTitle()+":"+group.getSergPkTitle()+"<br/>";
                        if(StringUtils.isBlank(groupId)){
                            groupId = group.getId();
                        }else{
                            groupId += ";"+group.getId();
                        }
                        if(StringUtils.isBlank(groupValue)){
                            groupValue = group.getSergValue();
                        }else{
                            groupValue += ";"+group.getSergValue();
                        }

                        if(StringUtils.isBlank(mobjecValue)){
                            mobjecValue = group.getSergObjectValue();
                        }else{
                            mobjecValue += ";"+group.getSergObjectValue();
                        }
                        if(StringUtils.isBlank(mobjecTitle)){
                            mobjecTitle = group.getSergObjectTitle();
                        }else{
                            mobjecTitle += ","+group.getSergObjectTitle();
                        }
                        if(StringUtils.isBlank(mobjecType)){
                            mobjecType = group.getSergObjectType();
                        }else{
                            mobjecType += ";"+group.getSergObjectType();
                        }

                        if(StringUtils.isBlank(mpkValue)){
                            mpkValue = group.getSergPkValue();
                        }else{
                            mpkValue += ";"+group.getSergPkValue();
                        }
                        if(StringUtils.isBlank(mpkTitle)){
                            mpkTitle = group.getSergPkTitle();
                        }else{
                            mpkTitle += ";"+group.getSergPkTitle();
                        }

                        if(StringUtils.isBlank(mpkType)){
                            mpkType = group.getSergPkType();
                        }else{
                            mpkType += ";"+group.getSergPkType();
                        }
                        gpkId = "";
                        gpkValue = "";
                        gpkTitle = "";
                        gpkType = "";
                        if(meal!=null){
                            meal.setSersmCreateTime(Calendar.getInstance());
                            meal.setSersmObjectValue(mobjecValue);
                            meal.setSersmObjectTitle(mobjecTitle);
                            meal.setSersmObjectType(mobjecType);
                            meal.setSersmPkValue(mpkValue);
                            meal.setSersmPkTitle(mpkTitle);
                            meal.setSersmPkType(mpkType);
                            meal.setSersmGroupId(groupId);
                            meal.setSersmGroupValue(groupValue);
                            meal.setSersmBook(book);
                            meal.setSersmOpenState("0");
                            if(StringUtils.isBlank(meal.getId())){
                               if(StringUtils.isNotBlank(mealId)){
                                   modifyMeal(meal,mealId);
                                   mealId="";
                               }else{
                                   sysDao.getServiceDo().add(meal);
                               }
                            }else{
                                sysDao.getServiceDo().modify(meal);
                            }
                            mpkTitle = "";
                            mpkValue = "";
                            mpkType = "";

                            groupValue = "";
                            groupId = "";

                            mobjecTitle = "";
                            mobjecValue = "";
                            mobjecType = "";

                            book = "";
                        }
                    }
                }else if(groupNum==10){//添加服务组合数据
                    if(StringUtils.isBlank(group.getId())){
                        group.setSergPkId(gpkId);
                        group.setSergPkValue(gpkValue);
                        group.setSergPkTitle(gpkTitle);
                        group.setSergPkType(gpkType);
                        sysDao.getServiceDo().add(group);
                    }
                    book += group.getSergObjectTitle()+":"+group.getSergPkTitle()+"<br/>";
                    if(StringUtils.isBlank(groupId)){
                        groupId = group.getId();
                    }else{
                        groupId += ";"+group.getId();
                    }
                    if(StringUtils.isBlank(groupValue)){
                        groupValue = group.getSergValue();
                    }else{
                        groupValue += ";"+group.getSergValue();
                    }

                    if(StringUtils.isBlank(mobjecValue)){
                        mobjecValue = group.getSergObjectValue();
                    }else{
                        mobjecValue += ";"+group.getSergObjectValue();
                    }
                    if(StringUtils.isBlank(mobjecTitle)){
                        mobjecTitle = group.getSergObjectTitle();
                    }else{
                        mobjecTitle += ","+group.getSergObjectTitle();
                    }
                    if(StringUtils.isBlank(mobjecType)){
                        mobjecType = group.getSergObjectType();
                    }else{
                        mobjecType += ";"+group.getSergObjectType();
                    }

                    if(StringUtils.isBlank(mpkValue)){
                        mpkValue = group.getSergPkValue();
                    }else{
                        mpkValue += ";"+group.getSergPkValue();
                    }
                    if(StringUtils.isBlank(mpkTitle)){
                        mpkTitle = group.getSergPkTitle();
                    }else{
                        mpkTitle += ";"+group.getSergPkTitle();
                    }

                    if(StringUtils.isBlank(mpkType)){
                        mpkType = group.getSergPkType();
                    }else{
                        mpkType += ";"+group.getSergPkType();
                    }
                    gpkId = "";
                    gpkValue = "";
                    gpkTitle = "";
                    gpkType = "";

                    //根据编号查询服务人群数据
                    AppServeObject object1 = sysDao.getAppServeObjectDao().findByValue(ss[12]);
                    //添加人群
                    if(object1==null){
                        object1 = new AppServeObject();
                        object1.setSeroValue(ss[12]);
                        object1.setSeroName(ss[13]);
                        object1.setSeroLabelType(ss[14]);
                        object1.setSeroFwType(ss[15]);
                        object1.setSeroState(ss[16]);
                        if(user!=null){
                            object1.setSeroCreateId(user.getUserId());
                            if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                                    "zzadmin".equals(user.getAccount())||
                                    "fzadmin".equals(user.getAccount())||
                                    "ptadmin".equals(user.getAccount())||
                                    "npadmin".equals(user.getAccount())||
                                    "qzadmin".equals(user.getAccount())){
                                object1.setSeroType("0");
                                object1.setSeroAreaCode("0");
                                object1.setSeroLevel("0");//系统等级
                                object1.setSeroOpenState("0");//默认不开启
                            }else{
                                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                                object1.setSeroAreaCode(dept.getHospAreaCode());
                                object1.setSeroDeptId(dept.getId());
                                object1.setSeroType("1");

                                if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                                    object1.setSeroLevel("1");//市级
                                }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                                    object1.setSeroLevel("2");//医院级
                                }
                            }
                        }
                        sysDao.getServiceDo().add(object1);
                    }

                    //根据编号查询服务内容
                    AppServePackage pkg =  sysDao.getAppServePackageDao().findByValue(ss[17]);
                    //添加内容
                    if(pkg==null){
                        pkg = new AppServePackage();
                        pkg.setSerpkValue(ss[17]);
                        pkg.setSerpkName(ss[18]);
                        pkg.setSerpkOpenState(ss[19]);
                        if("1".equals(ss[19])){
                            pkg.setSerpkTime(ss[20]);
                            if(StringUtils.isNotBlank(ss[21])){
                                String[] sss = ss[21].split(";");
                                if(sss.length==2){
                                    pkg.setSerpkNum(sss[0]);
                                    pkg.setSerpkIntervalType(sss[1]);
                                }else{
                                    pkg.setSerpkNum(sss[0]);
                                }
                            }
                        }
                        pkg.setSerpkBaseType(ss[22]);
                        pkg.setSerpkRemark(ss[23]);

                        if(user!=null){
                            pkg.setSerpkCreateId(user.getUserId());
                            if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                                    "zzadmin".equals(user.getAccount())||
                                    "fzadmin".equals(user.getAccount())||
                                    "ptadmin".equals(user.getAccount())||
                                    "npadmin".equals(user.getAccount())||
                                    "qzadmin".equals(user.getAccount())){
                                pkg.setSerpkType("0");//服务类型
                                pkg.setSerpkAreaCode("0");
                                pkg.setSerpkLeve("0");//系统等级
                                pkg.setSerpkState("0");//默认不开启
                            }else{
                                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                                pkg.setSerpkAreaCode(dept.getHospAreaCode());
                                pkg.setSerpkDeptId(dept.getId());
                                pkg.setSerpkType("1");
                                if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                                    pkg.setSerpkLeve("1");//市级
                                }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                                    pkg.setSerpkLeve("2");//医院级
                                }
                            }
                        }
                        sysDao.getServiceDo().add(pkg);
                    }
                    //添加组合
                    //根据组合编号查询组合信息
                    group = sysDao.getAppServeGroupDao().findByValue(ss[10]);
                    if(group==null){
                        group = new AppServeGroup();//服务组合
                        group.setSergValue(ss[10]);
                        group.setSergGroupFee(ss[11]);
                        group.setSergOpenState("0");

                        if(user!=null){
                            group.setSergCreateId(user.getUserId());
                            if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                                    "zzadmin".equals(user.getAccount())||
                                    "fzadmin".equals(user.getAccount())||
                                    "ptadmin".equals(user.getAccount())||
                                    "npadmin".equals(user.getAccount())||
                                    "qzadmin".equals(user.getAccount())){
                                group.setSergLevel("0");//系统等级
                                group.setSergAreaCode("0");
                                group.setSergOpenState("0");//默认不开启
                            }else{
                                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());

                                group.setSergAreaCode(dept.getHospAreaCode());
                                group.setSergDeptId(dept.getId());
                                if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                                    group.setSergLevel("1");//市级
                                }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                                    group.setSergLevel("2");//医院级
                                }
                            }
                        }

                        group.setSergObjectValue(object1.getSeroValue());
                        group.setSergObjectId(object1.getId());
                        group.setSergObjectTitle(object1.getSeroName());
                        group.setSergObjectType(object1.getSeroType());
                    }
                  /*  //添加组合
                    group = new AppServeGroup();//服务组合
                    group.setSergValue(ss[10]);
                    group.setSergGroupFee(ss[11]);
                    group.setSergOpenState("0");

                    //添加人群
                    AppServeObject object1 = new AppServeObject();
                    object1.setSeroValue(ss[12]);
                    object1.setSeroName(ss[13]);
                    object1.setSeroLabelType(ss[14]);
                    object1.setSeroFwType(ss[15]);
                    object1.setSeroState(ss[16]);

                    //添加内容
                    AppServePackage pkg = new AppServePackage();
                    pkg.setSerpkValue(ss[17]);
                    pkg.setSerpkName(ss[18]);
                    pkg.setSerpkOpenState(ss[19]);
                    if("1".equals(ss[19])){
                        pkg.setSerpkTime(ss[20]);
                        if(StringUtils.isNotBlank(ss[20])){
                            String[] sss = ss[21].split(";");
                            if(sss.length==2){
                                pkg.setSerpkNum(sss[0]);
                                pkg.setSerpkIntervalType(sss[1]);
                            }else{
                                pkg.setSerpkNum(sss[0]);
                            }
                        }
                    }
                    pkg.setSerpkBaseType(ss[22]);
                    pkg.setSerpkRemark(ss[23]);

                    if(user!=null){
                        group.setSergCreateId(user.getUserId());
                        object1.setSeroCreateId(user.getUserId());
                        pkg.setSerpkCreateId(user.getUserId());
                        if("admin".equals(user.getAccount())){
                            group.setSergLevel("0");//系统等级
                            group.setSergAreaCode("0");
                            group.setSergOpenState("0");//默认不开启

                            object1.setSeroType("0");
                            object1.setSeroAreaCode("0");
                            object1.setSeroLevel("0");//系统等级
                            object1.setSeroOpenState("0");//默认不开启

                            pkg.setSerpkType("0");//服务类型
                            pkg.setSerpkAreaCode("0");
                            pkg.setSerpkLeve("0");//系统等级
                            pkg.setSerpkState("0");//默认不开启

                        }else{
                            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());

                            group.setSergAreaCode(dept.getHospAreaCode());
                            group.setSergDeptId(dept.getId());

                            object1.setSeroAreaCode(dept.getHospAreaCode());
                            object1.setSeroDeptId(dept.getId());
                            object1.setSeroType("1");

                            pkg.setSerpkAreaCode(dept.getHospAreaCode());
                            pkg.setSerpkDeptId(dept.getId());
                            pkg.setSerpkType("1");

                            if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                                group.setSergLevel("1");//市级
                                object1.setSeroLevel("1");//市级
                                pkg.setSerpkLeve("1");//市级
                            }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                                group.setSergLevel("2");//医院级
                                object1.setSeroLevel("2");//医院级
                                pkg.setSerpkLeve("2");//医院级
                            }
                        }
                    }
                    sysDao.getServiceDo().add(object1);
                    sysDao.getServiceDo().add(pkg);

                    group.setSergObjectValue(object1.getSeroValue());
                    group.setSergObjectId(object1.getId());
                    group.setSergObjectTitle(object1.getSeroName());
                    group.setSergObjectType(object1.getSeroType());*/

                   /* if(StringUtils.isBlank(mobjecValue)){
                        mobjecValue=object1.getSeroValue();
                    }else{
                        mobjecValue+=";"+object1.getSeroValue();
                    }
                    if(StringUtils.isBlank(mobjecTitle)){
                        mobjecTitle = object1.getSeroName();
                    }else{
                        mobjecTitle += ","+object1.getSeroName();
                    }
                    if(StringUtils.isBlank(mobjecType)){
                        mobjecType = object1.getSeroState();
                    }else{
                        mobjecType += ";"+object1.getSeroState();
                    }*/
                    //组合服务内容变量赋值
                    if(StringUtils.isBlank(gpkId)){
                        gpkId = pkg.getId();
                    }else{
                        gpkId += ";"+pkg.getId();
                    }

                    if(StringUtils.isBlank(gpkValue)){
                        gpkValue = pkg.getSerpkValue();
                    }else{
                        gpkValue +=";"+ pkg.getSerpkValue();
                    }

                    if(StringUtils.isBlank(gpkTitle)){
                        gpkTitle = pkg.getSerpkName();
                    }else{
                        gpkTitle += "," + pkg.getSerpkName();
                    }

                    if(StringUtils.isBlank(gpkType)){
                        gpkType = pkg.getSerpkBaseType();
                    }else{
                        gpkType += ";" + pkg.getSerpkBaseType();
                    }
                    //套餐服务内容变量赋值
                  /*  if(StringUtils.isBlank(mpkValue)){
                        mpkValue = pkg.getSerpkValue();
                    }else{
                        mpkValue +=";"+ pkg.getSerpkValue();
                    }

                    if(StringUtils.isBlank(mpkTitle)){
                        mpkTitle = pkg.getSerpkName();
                    }else{
                        mpkTitle += "," + pkg.getSerpkName();
                    }

                    if(StringUtils.isBlank(mpkType)){
                        mpkType = pkg.getSerpkBaseType();
                    }else{
                        mpkType += ";" + pkg.getSerpkBaseType();
                    }*/
                    if(i==map.size()-1){//最后一条且是服务组合数据，保存服务组合
                        if(StringUtils.isBlank(group.getId())){
                            group.setSergPkId(gpkId);
                            group.setSergPkValue(gpkValue);
                            group.setSergPkTitle(gpkTitle);
                            group.setSergPkType(gpkType);
                            sysDao.getServiceDo().add(group);
                        }
                        book += group.getSergObjectTitle()+":"+group.getSergPkTitle()+"<br/>";
                        if(StringUtils.isBlank(groupId)){
                            groupId = group.getId();
                        }else{
                            groupId += ";"+group.getId();
                        }
                        if(StringUtils.isBlank(groupValue)){
                            groupValue = group.getSergValue();
                        }else{
                            groupValue += ";"+group.getSergValue();
                        }

                        if(StringUtils.isBlank(mobjecValue)){
                            mobjecValue = group.getSergObjectValue();
                        }else{
                            mobjecValue += ";"+group.getSergObjectValue();
                        }
                        if(StringUtils.isBlank(mobjecTitle)){
                            mobjecTitle = group.getSergObjectTitle();
                        }else{
                            mobjecTitle += ","+group.getSergObjectTitle();
                        }
                        if(StringUtils.isBlank(mobjecType)){
                            mobjecType = group.getSergObjectType();
                        }else{
                            mobjecType += ";"+group.getSergObjectType();
                        }

                        if(StringUtils.isBlank(mpkValue)){
                            mpkValue = group.getSergPkValue();
                        }else{
                            mpkValue += ";"+group.getSergPkValue();
                        }
                        if(StringUtils.isBlank(mpkTitle)){
                            mpkTitle = group.getSergPkTitle();
                        }else{
                            mpkTitle += ";"+group.getSergPkTitle();
                        }

                        if(StringUtils.isBlank(mpkType)){
                            mpkType = group.getSergPkType();
                        }else{
                            mpkType += ";"+group.getSergPkType();
                        }
                        gpkId = "";
                        gpkValue = "";
                        gpkTitle = "";
                        gpkType = "";
                        if(meal!=null){
                            meal.setSersmCreateTime(Calendar.getInstance());
                            meal.setSersmObjectValue(mobjecValue);
                            meal.setSersmObjectTitle(mobjecTitle);
                            meal.setSersmObjectType(mobjecType);
                            meal.setSersmPkValue(mpkValue);
                            meal.setSersmPkTitle(mpkTitle);
                            meal.setSersmPkType(mpkType);
                            meal.setSersmGroupId(groupId);
                            meal.setSersmGroupValue(groupValue);
                            meal.setSersmBook(book);
                            meal.setSersmOpenState("0");
                            if(StringUtils.isBlank(meal.getId())){
                                if(StringUtils.isNotBlank(mealId)){
                                    modifyMeal(meal,mealId);
                                    mealId="";
                                }else{
                                    sysDao.getServiceDo().add(meal);
                                }
                            }else{
                                sysDao.getServiceDo().modify(meal);
                            }

                            mpkTitle = "";
                            mpkValue = "";
                            mpkType = "";

                            groupValue = "";
                            groupId = "";

                            mobjecTitle = "";
                            mobjecValue = "";
                            mobjecType = "";

                            book = "";
                        }
                    }
                }else if(groupNum==17){
                    AppServePackage pkg = sysDao.getAppServePackageDao().findByValue(ss[17]);
                    if(pkg==null){
                        pkg = new AppServePackage();
                        pkg.setSerpkValue(ss[17]);
                        pkg.setSerpkName(ss[18]);
                        pkg.setSerpkOpenState(ss[19]);
                        if("1".equals(ss[19])){
                            pkg.setSerpkTime(ss[20]);
                            if(StringUtils.isNotBlank(ss[21])){
                                String[] sss = ss[21].split(";");
                                if(sss.length==2){
                                    pkg.setSerpkNum(sss[0]);
                                    pkg.setSerpkIntervalType(sss[1]);
                                }else{
                                    pkg.setSerpkNum(sss[0]);
                                }
                            }
                        }
                        pkg.setSerpkBaseType(ss[22]);
                        pkg.setSerpkRemark(ss[23]);

                        if(user!=null){
                            pkg.setSerpkCreateId(user.getUserId());
                            if("admin".equals(user.getAccount())|| "smadmin".equals(user.getAccount())||
                                    "zzadmin".equals(user.getAccount())||
                                    "fzadmin".equals(user.getAccount())||
                                    "ptadmin".equals(user.getAccount())||
                                    "npadmin".equals(user.getAccount())||
                                    "qzadmin".equals(user.getAccount())){
                                pkg.setSerpkType("0");//服务类型
                                pkg.setSerpkAreaCode("0");
                                pkg.setSerpkLeve("0");//系统等级
                                pkg.setSerpkState("0");//默认不开启
                            }else{
                                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                                pkg.setSerpkAreaCode(dept.getHospAreaCode());
                                pkg.setSerpkDeptId(dept.getId());
                                pkg.setSerpkType("1");
                                if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                                    pkg.setSerpkLeve("1");//市级
                                }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                                    pkg.setSerpkLeve("2");//医院级
                                }
                            }
                        }
                        sysDao.getServiceDo().add(pkg);
                    }

                    //组合服务内容变量赋值
                    if(StringUtils.isBlank(gpkId)){
                        gpkId = pkg.getId();
                    }else{
                        gpkId += ";"+pkg.getId();
                    }

                    if(StringUtils.isBlank(gpkValue)){
                        gpkValue = pkg.getSerpkValue();
                    }else{
                        gpkValue +=";"+ pkg.getSerpkValue();
                    }

                    if(StringUtils.isBlank(gpkTitle)){
                        gpkTitle = pkg.getSerpkName();
                    }else{
                        gpkTitle += "," + pkg.getSerpkName();
                    }

                    if(StringUtils.isBlank(gpkType)){
                        gpkType = pkg.getSerpkBaseType();
                    }else{
                        gpkType += ";" + pkg.getSerpkBaseType();
                    }
                    //套餐服务内容变量赋值
//                    if(StringUtils.isBlank(mpkValue)){
//                        mpkValue = pkg.getSerpkValue();
//                    }else{
//                        mpkValue +=";"+ pkg.getSerpkValue();
//                    }

//                    if(StringUtils.isBlank(mpkTitle)){
//                        mpkTitle = pkg.getSerpkName();
//                    }else{
//                        mpkTitle += "," + pkg.getSerpkName();
//                    }

//                    if(StringUtils.isBlank(mpkType)){
//                        mpkType = pkg.getSerpkBaseType();
//                    }else{
//                        mpkType += ";" + pkg.getSerpkBaseType();
//                    }
                    if(i==map.size()-1){
                        if(StringUtils.isBlank(group.getId())){
                            group.setSergPkId(gpkId);
                            group.setSergPkValue(gpkValue);
                            group.setSergPkTitle(gpkTitle);
                            group.setSergPkType(gpkType);
                            sysDao.getServiceDo().add(group);
                        }
                        book += group.getSergObjectTitle()+":"+group.getSergPkTitle()+"<br/>";
                        if(StringUtils.isBlank(groupId)){
                            groupId = group.getId();
                        }else{
                            groupId += ";"+group.getId();
                        }
                        if(StringUtils.isBlank(groupValue)){
                            groupValue = group.getSergValue();
                        }else{
                            groupValue += ";"+group.getSergValue();
                        }

                        if(StringUtils.isBlank(mobjecValue)){
                            mobjecValue = group.getSergObjectValue();
                        }else{
                            mobjecValue += ";"+group.getSergObjectValue();
                        }
                        if(StringUtils.isBlank(mobjecTitle)){
                            mobjecTitle = group.getSergObjectTitle();
                        }else{
                            mobjecTitle += ","+group.getSergObjectTitle();
                        }
                        if(StringUtils.isBlank(mobjecType)){
                            mobjecType = group.getSergObjectType();
                        }else{
                            mobjecType += ";"+group.getSergObjectType();
                        }

                        if(StringUtils.isBlank(mpkValue)){
                            mpkValue = group.getSergPkValue();
                        }else{
                            mpkValue += ";"+group.getSergPkValue();
                        }
                        if(StringUtils.isBlank(mpkTitle)){
                            mpkTitle = group.getSergPkTitle();
                        }else{
                            mpkTitle += ";"+group.getSergPkTitle();
                        }

                        if(StringUtils.isBlank(mpkType)){
                            mpkType = group.getSergPkType();
                        }else{
                            mpkType += ";"+group.getSergPkType();
                        }

                        gpkId = "";
                        gpkValue = "";
                        gpkTitle = "";
                        gpkType = "";

                        if(meal!=null){
                            meal.setSersmCreateTime(Calendar.getInstance());
                            meal.setSersmObjectValue(mobjecValue);
                            meal.setSersmObjectTitle(mobjecTitle);
                            meal.setSersmObjectType(mobjecType);
                            meal.setSersmPkValue(mpkValue);
                            meal.setSersmPkTitle(mpkTitle);
                            meal.setSersmPkType(mpkType);
                            meal.setSersmGroupId(groupId);
                            meal.setSersmGroupValue(groupValue);
                            meal.setSersmBook(book);
                            meal.setSersmOpenState("0");
                            if(StringUtils.isBlank(meal.getId())){
                                if(StringUtils.isNotBlank(mealId)){
                                    modifyMeal(meal,mealId);
                                    mealId="";
                                }else{
                                    sysDao.getServiceDo().add(meal);
                                }
                            }else{
                                sysDao.getServiceDo().modify(meal);
                            }
                        }
                    }
                }

                /*WebSignVo vo = new WebSignVo();
                int num = i+1;
                String[] ss = map.get(i).split("\\|");
                if(StringUtils.isNotBlank(ss[1])){//姓名
                    vo.setPatientName(ss[1]);
                }else {
                    result = "第"+num+"行2列[姓名]不能为空!";
                    return result;
                }
                if(StringUtils.isNotBlank(ss[2])){//身份证
                    String resultIdNo = CardUtil.IDCardValidate(ss[2].toLowerCase());
                    if(StringUtils.isNotBlank(resultIdNo)){
                        result ="第"+num+"行3列[身份证]格式:"+resultIdNo;
                        return result;
                    }else{
                        vo.setPatientIdno(ss[2]);
                        AppPatientUser patientUser = this.sysDao.getAppPatientUserDao().findPatientIdNo(ss[2]);
                        if(patientUser != null){
                            AppSignForm signForm = this.sysDao.getAppSignformDao().findSignFormByUserState(patientUser.getId());
                            if(signForm != null){
                                result ="第"+num+"行:居民"+patientUser.getPatientName()+"已在"+ ExtendDate.getYMD(signForm.getSignFromDate())+"进行签约,不能重复签约!";
                                return result;
                            }
                        }
                    }
                }else {
                    result ="第"+num+"行3列[身份证]不能为空!";
                    return result;
                }
                if(StringUtils.isNotBlank(ss[3])){//社保卡号
                    vo.setPatientCard(ss[3]);
                }
                if(StringUtils.isNotBlank(ss[4])){//联系电话
                    if(AccountValidatorUtil.isMobile(ss[4])){
                        vo.setPatientTel(ss[4]);
                    }else{
                        result = "第"+num+"行5列[联系电话]格式错误!";
                        return result;
                    }
                }
                if(StringUtils.isNotBlank(ss[5])){//详细地址
                    vo.setPatientAddress(ss[5]);
                }

                if(StringUtils.isNotBlank(ss[6])){//签约时间
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    boolean dateflag=true;
                    try{
                        format.parse(ss[6]);
                    } catch (ParseException e){
                        dateflag=false;
                    }finally{
                        if(dateflag){
                            vo.setSignDate(ss[6]);
                            vo.setSignFromDate(ExtendDate.getCalendar(ss[6]));//签约开始时间
                            Calendar calendar = Calendar.getInstance();
                            Calendar end = Calendar.getInstance();
                            end.setTime(vo.getSignFromDate().getTime());
                            end.add(Calendar.YEAR,1);
                            end.add(Calendar.DATE,-1);
                            vo.setSignToDate(end);//签约结束时间
                        }else{
                            result = "第"+num+"行7列[签约时间]格式错误!";
                            return result;
                        }
                    }
                }else {
                    result = "第"+num+"行7列[签约时间]不能为空!";
                    return result;
                }

                if(StringUtils.isNotBlank(ss[7])){//服务类型
                    String[] resultGroup = ss[7].split(";");
                    vo.setPersGroup(resultGroup);
                }else {
                    result = "第"+num+"行8列[服务类型]不能为空!";
                    return result;
                }

                if(StringUtils.isNotBlank(ss[8])){//是否VIP
                    vo.setSignType(ss[8]);
                }else {
                    result = "第"+num+"行9列[是否VIP]不能为空!";
                    return result;
                }

                if(StringUtils.isNotBlank(ss[10])){//医院主键
                    vo.setHospId(ss[10]);
                }else {
                    result = "第"+num+"行11列[医院主键]不能为空!";
                    return result;
                }

                if(StringUtils.isNotBlank(ss[12])){//团队主键
                    vo.setTeamId(ss[12]);
                }else {
                    result = "第"+num+"行13列[团队主键]不能为空!";
                    return result;
                }

                if(StringUtils.isNotBlank(ss[14])){//全科医生主键
                    vo.setSignDrAssistantId(ss[14]);
                }else {
                    result = "第"+num+"行15列[全科医生主键]不能为空!";
                    return result;
                }

                if(StringUtils.isNotBlank(ss[18])){//健康管理师主键
                    vo.setDrId(ss[18]);
                }else {
                    result = "第"+num+"行19列[健康管理师主键]不能为空!";
                    return result;
                }


                if(StringUtils.isNotBlank(ss[20])){//填报人主键
                    vo.setBatchOperatorId(ss[20]);
                }else {
                    result = "第"+num+"行21列[填报人主键]不能为空!";
                    return result;
                }
                ls.add(vo);
            }
            if(ls!= null && ls.size()>0){

            }*/
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("导入失败,请联系管理员!");
//            result = "导入失败,请联系管理员!";
        }
        return result;
    }

    @Override
    public AppServeSetmeal findByValue(String value) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("value",value);
        String sql = "SELECT * FROM APP_SERVE_SETMEAL WHERE SERSM_VALUE = :value";
        List<AppServeSetmeal> list = sysDao.getServiceDo().findSqlMap(sql,map,AppServeSetmeal.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<AppServeSetmeal> findListByIds(String ids) throws Exception{
        Map<String,Object> map = new HashMap<>();

        String sql = "SELECT * FROM APP_SERVE_SETMEAL WHERE 1=1 ";
        if(StringUtils.isNotBlank(ids)){
            String[] idss = ids.split(";");
            map.put("idss",idss);
            sql += " AND id IN (:idss) ";
        }else{
            return null;
        }
        List<AppServeSetmeal> list = sysDao.getServiceDo().findSqlMap(sql,map,AppServeSetmeal.class);
        return list;
    }

    @Override
    public List<AppSignServeMealEntity> findSignMeal(String ids) throws Exception{
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT ID id," +
                "SERSM_NAME title," +
                "SERSM_VALUE value," +
                "SERSM_GROUP_ID groupId," +
                "'' groupList " +
                "FROM APP_SERVE_SETMEAL WHERE 1=1 ";
        if(StringUtils.isNotBlank(ids)){
            map.put("idss",ids.split(";"));
            sql += " AND ID IN (:idss) ";
        }else{
            return null;
        }
        List<AppSignServeMealEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppSignServeMealEntity.class);
        return list;
    }

    @Override
    public List<AppServeSetmeal> findAllByNotGroup() throws Exception{
        String sql = "SELECT * FROM app_serve_setmeal WHERE SERSM_PK_VALUE is null AND SERSM_OBJECT_VALUE is null";
        List<AppServeSetmeal> list = sysDao.getServiceDo().findSqlMap(sql,new HashMap<String, Object>(),AppServeSetmeal.class);
        return list;
    }
    public void modifyMeal(AppServeSetmeal meal,String mealId) throws Exception{
        WebServeSetmeal webmeal = new WebServeSetmeal();
        webmeal.setId(mealId);
        webmeal.setSersmAreaCode(meal.getSersmAreaCode());
        webmeal.setSersmBgDr(meal.getSersmBgDr());
        webmeal.setSersmBook(meal.getSersmBook());
        webmeal.setSersmCreateDept(meal.getSersmCreateDept());
        webmeal.setSersmCreateId(meal.getSersmCreateId());
        webmeal.setSersmCreateTime(meal.getSersmCreateTime());
        webmeal.setSersmDownState(meal.getSersmDownState());
        webmeal.setSersmEndTime(meal.getSersmEndTime());
        webmeal.setSersmFee(meal.getSersmFee());
        webmeal.setSersmGroupId(meal.getSersmGroupId());
        webmeal.setSersmGroupValue(meal.getSersmGroupValue());
        webmeal.setSersmImageName(meal.getSersmImageName());
        webmeal.setSersmImageUrl(meal.getSersmImageUrl());
        webmeal.setSersmJcState(meal.getSersmJcState());
        webmeal.setSersmJjId(meal.getSersmJjId());
        webmeal.setSersmJjType(meal.getSersmJjType());
        webmeal.setSersmLevel(meal.getSersmLevel());
        webmeal.setSersmName(meal.getSersmName());
        webmeal.setSersmObjectTitle(meal.getSersmObjectTitle());
        webmeal.setSersmObjectType(meal.getSersmObjectType());
        webmeal.setSersmObjectValue(meal.getSersmObjectValue());
        webmeal.setSersmOneFee(meal.getSersmOneFee());
        webmeal.setSersmOpenArea(meal.getSersmOpenArea());
        webmeal.setSersmOpenState(meal.getSersmOpenState());
        webmeal.setSersmPkTitle(meal.getSersmPkTitle());
        webmeal.setSersmPkType(meal.getSersmPkType());
        webmeal.setSersmPkValue(meal.getSersmPkValue());
        webmeal.setSersmStartTime(meal.getSersmStartTime());
        webmeal.setSersmSubsidyWay(meal.getSersmSubsidyWay());
        webmeal.setSersmTabState(meal.getSersmTabState());
        webmeal.setSersmTotalFee(meal.getSersmTotalFee());
        webmeal.setSersmTotalOneFee(meal.getSersmTotalOneFee());
        webmeal.setSersmValue(meal.getSersmValue());
        webmeal.setSersmYxTimeType(meal.getSersmYxTimeType());
        sysDao.getServiceDo().add(webmeal);
    }

    /**
     * 根据服务人群、经济类型查询套餐财政补贴类型
     * @param qvo
     * @return
     */
    @Override
    public List<AppGrantInAidEntity> findGrantInAidByMeal(AppCommQvo qvo) throws Exception{
        List<AppGrantInAidEntity> list = new ArrayList<>();
        AppServeSetmeal meal = (AppServeSetmeal) sysDao.getServiceDo().find(AppServeSetmeal.class,qvo.getSignpackageid());
        if(meal != null){
            double totalPrice = Double.parseDouble(meal.getSersmTotalFee());//服务包费用
            //有经济补贴
            if(StringUtils.isNotBlank(meal.getSersmJjId())){
                String[] jjIds = meal.getSersmJjId().split(";");
                for(String jjId:jjIds){
                    AppGrantInAidEntity vo = new AppGrantInAidEntity();
                    double price = 0;
                    boolean flag = false;
                    AppEconAndGov eag = (AppEconAndGov)sysDao.getServiceDo().find(AppEconAndGov.class,jjId);
                    if(eag != null){
                        vo.setId(eag.getId());
                        if(StringUtils.isNotBlank(eag.getEagEconId())){
                            AppServeEcon econ = (AppServeEcon)sysDao.getServiceDo().find(AppServeEcon.class,eag.getEagEconId());
                            if(econ != null){
                                if(StringUtils.isNotBlank(econ.getEconLabelType())){
                                    //服务人群补贴
                                    if(LabelManageType.FWRQ.getValue().equals(econ.getEconLabelType())){
                                        if(qvo.getSignPersGroup().indexOf(econ.getEconFwType())==-1){
                                            break;
                                        }
                                    }else if(LabelManageType.JJLX.getValue().equals(econ.getEconLabelType())){
                                        //经济类型补贴
                                        if(qvo.getSignsJjType().indexOf(econ.getEconFwType())==-1){
                                            break;
                                        }
                                    }
                                }
                                vo.setEconName(econ.getEconTitle());
                            }
                        }
                        if(StringUtils.isNotBlank(eag.getEagGovId())){
                            String[] govIds = eag.getEagGovId().split(";");
                            String govName = "";
                            for(String govId:govIds){
                                String govAndprice = "";
                                AppServeGov gov = (AppServeGov)sysDao.getServiceDo().find(AppServeGov.class,govId);
                                if(gov != null){
                                    if(StringUtils.isNotBlank(gov.getGovMoney())){
                                        price += Double.parseDouble(gov.getGovMoney());
                                        if(gov.getGovTitle().indexOf("全补贴")!=-1){
                                            flag = true;
                                            govAndprice = gov.getGovTitle()+"("+totalPrice+")";
                                        }else{
                                            govAndprice = gov.getGovTitle()+"("+gov.getGovMoney()+")";
                                        }
                                    }else{
                                        if(gov.getGovTitle().indexOf("全补贴")!=-1){
                                            flag = true;
                                            govAndprice = gov.getGovTitle()+"("+totalPrice+")";
                                        }else{
                                            govAndprice = gov.getGovTitle();
                                        }
                                    }
                                    if(StringUtils.isBlank(govName)){
                                        govName = govAndprice;
                                    }else{
                                        govName += ";"+govAndprice;
                                    }
                                }
                            }
                            vo.setGovNames(govName);
                        }
                    }
                    if(flag){
                        vo.setPrice(String.valueOf(totalPrice));
                    }else{
                        vo.setPrice(String.valueOf(price));
                    }
                    list.add(vo);
                }
            }
        }
        return list;
    }
    
    /************************************* 新履约统计 start *******************************************/
    @Override
	public Map<String, Map<String, List<String>>> dealAppServeSetmeal(List<AppServeSetmeal> appServeSetmealList) throws Exception{
		Map<String, Map<String, List<String>>> returnMap = new HashMap<String, Map<String, List<String>>>();
		for (AppServeSetmeal appServeSetmeal : appServeSetmealList) {
			Map<String, List<String>> appServeGroupMap = new HashMap<String, List<String>>();
			if ((appServeSetmeal != null) && (appServeSetmeal.getSersmGroupId() != null)) {
				String[] groupIds = appServeSetmeal.getSersmGroupId().split(";");
				String[] arrayOfString1;
				int j = (arrayOfString1 = groupIds).length;
				for (int i = 0; i < j; i++) {
					String groupId = arrayOfString1[i];
					AppServeGroup group = (AppServeGroup) this.sysDao.getServiceDo().find(AppServeGroup.class, groupId);
					if (group != null) {
						if (!StringUtils.isBlank(group.getSergObjectId())) {
							AppServeObject appServeObject = (AppServeObject) this.sysDao.getServiceDo().find(AppServeObject.class, group.getSergObjectId());
							List<String> valueList = new ArrayList<String>();
							if(StringUtils.isNotBlank(group.getSergPkId())){
                                String[] pkIds = group.getSergPkId().split(";");
                                String[] arrayOfString2;
                                int m = (arrayOfString2 = pkIds).length;
                                for (int k = 0; k < m; k++) {
                                    String pkId = arrayOfString2[k];

                                    AppServePackage appServePackage = (AppServePackage) this.sysDao.getServiceDo().find(AppServePackage.class, pkId);
                                    String content = "";
                                    if (StringUtils.isBlank(appServePackage.getSerpkItem())) {
                                        continue;
                                    }
									// 频次，如果是空的话，就是一次
									String pc = "1";
									if (StringUtils.isNotBlank(appServePackage.getSerpkTime())) {
										pc = appServePackage.getSerpkTime();
									}
                                    if (PerformanceType.DQSF.getValue().equals(appServePackage.getSerpkItem())) {
                                        if (ResidentMangeType.GXY.getValue().equals(appServeObject.getSeroFwType())) {
											content = appServePackage.getSerpkItem() + "_" + ResidentMangeType.GXY.getValue() + "&&&" + pc;
                                        } else if (ResidentMangeType.TNB.getValue().equals(appServeObject.getSeroFwType())) {
											content = appServePackage.getSerpkItem() + "_" + ResidentMangeType.TNB.getValue() + "&&&" + pc;
                                        } else if (ResidentMangeType.YZJSZY.getValue().equals(appServeObject.getSeroFwType())) {
											content = appServePackage.getSerpkItem() + "_" + ResidentMangeType.YZJSZY.getValue() + "&&&" + pc;
                                        } else if (ResidentMangeType.JHB.getValue().equals(appServeObject.getSeroFwType())) {
											content = appServePackage.getSerpkItem() + "_" + ResidentMangeType.JHB.getValue() + "&&&" + pc;
                                        } else if (ResidentMangeType.ETLZLS.getValue().equals(appServeObject.getSeroFwType())) {
											content = appServePackage.getSerpkItem() + "_" + ResidentMangeType.ETLZLS.getValue() + "&&&" + pc;
                                        } else {
											content = appServePackage.getSerpkItem() + "&&&" + pc;
                                        }
                                    } else {
										content = appServePackage.getSerpkItem() + "&&&" + pc;
                                    }
                                    valueList.add(content);
                                }
                                appServeGroupMap.put(appServeObject.getSeroFwType(), valueList);
                            }
						}
					}
				}
				returnMap.put(appServeSetmeal.getId(), appServeGroupMap);
			}
		}
		return returnMap;
	}

	@Override
	public Map<String, List<String>> countAppServeSetmeals(String packageId, Map<String, Map<String, List<String>>> resultMap) throws Exception{
		List<Map<String, List<String>>> list = new ArrayList<Map<String, List<String>>>();
		String[] packageIds = packageId.split(";");
		Map<String, List<String>> map;
		for (int i = 0; i < packageIds.length; i++) {
			String pid = packageIds[i];

			map = (Map) resultMap.get(pid);
			if ((map != null) && (map.size() > 0)) {
				list.add(map);
			}
		}
		if (list.size() == 1) {
			return (Map) list.get(0);
		}
		Map<String, List<String>> returnMap = new HashMap();
		for (Map<String, List<String>> m : list) {
			for (String key : m.keySet()) {
				if (!returnMap.containsKey(key)) {
					returnMap.put(key, (List) ((Map) m).get(key));
				} else {
					List<String> oldValueList = (List) returnMap.get(key);
					List<String> newValueList = (List) ((Map) m).get(key);
					List<String> resuleList = new ArrayList();
					if (oldValueList.size() > newValueList.size()) {
						resuleList = mergaList(oldValueList, newValueList);
					} else {
						resuleList = mergaList(newValueList, oldValueList);
					}
					returnMap.put(key, resuleList);
				}
			}
		}
		return returnMap;
	}
	
	private List<String> mergaList(List<String> moreValueList, List<String> lessValueList) throws Exception{
		List<String> list = new ArrayList<String>();
		List<String> returnList = new ArrayList<String>();
		list.addAll(moreValueList);
		list.addAll(lessValueList);
		returnList.addAll(list);
		for (int i = 0; i < list.size(); i++) {
			String[] moreStrOnes = ((String) list.get(i)).split("&&&");
			for (int j = i + 1; j < list.size(); j++) {
				if (((String) list.get(j)).startsWith(moreStrOnes[0] + "&&&")) {
					String pcNum = StringUtils.substringAfter((String) list.get(j), "&&&");
					if (Integer.valueOf(moreStrOnes[1]).intValue() < Integer.valueOf(pcNum).intValue()) {
						returnList.remove(list.get(i));
					} else {
						returnList.remove(list.get(j));
					}
				}
			}
		}
		return returnList;
	}

	@Override
	public List<String> countAppServeObjects(Map<String, List<String>> map, String labelValue) throws Exception{
		List<String> returnList = new ArrayList<String>();
		List<String> resultList = new ArrayList<String>();
		if (StringUtils.isBlank(labelValue)) {
			return returnList;
		}
		String[] labelValues = labelValue.split(",");
		for (int i = 0; i < labelValues.length; i++) {
			String lv = labelValues[i];
			List<String> serverContentAndPcNum = (List) map.get(lv);
			if (serverContentAndPcNum != null) {
				returnList.addAll(serverContentAndPcNum);
				resultList.addAll(serverContentAndPcNum);
			}
		}
		if ((resultList != null) && (resultList.size() > 0)) {
			for (int i = 0; i < resultList.size(); i++) {
				String[] moreStrOnes = ((String) resultList.get(i)).split("&&&");
				for (int j = i + 1; j < resultList.size(); j++) {
					if (((String) resultList.get(j)).startsWith(moreStrOnes[0] + "&&&")) {
						String pcNum = StringUtils.substringAfter((String) resultList.get(j), "&&&");

						returnList.remove(resultList.get(i));
						returnList.remove(resultList.get(j));
						if (StringUtils.isNotBlank(moreStrOnes[1]) && !"null".equals(moreStrOnes[1]) && StringUtils.isNotBlank(pcNum) && !"null".equals(pcNum)) {
							if (Integer.valueOf(moreStrOnes[1]).intValue() > Integer.valueOf(pcNum).intValue()) {
								returnList.add(moreStrOnes[0] + "&&&" + Integer.valueOf(moreStrOnes[1]));
							} else {
								returnList.add(moreStrOnes[0] + "&&&" + Integer.valueOf(pcNum));
							}
						}
					}
				}
			}
		}
		return returnList;
	}
	/************************************* 新履约统计 end *******************************************/
    /**
     * 根据服务包id 查询服务信息
     * @param packId
     * @return
     * @throws Exception
     */
    @Override
    public List<AppServeEntity> findSeverMealByIds(String packId) throws Exception {
        List<AppServeEntity> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("serveIds",packId.split(";"));
        String sql = " SELECT " +
                "a.ID serveId," +
                "a.SERSM_NAME serveName," +
                "a.SERSM_GROUP_ID groupId," +
                "a.SERSM_TOTAL_FEE fee," +
//                    "'"+this.getSignFormId() +"' signFormId,"+
                "'' groupList " +
                "FROM APP_SERVE_SETMEAL a " +
                "WHERE a.ID IN (:serveIds)";
        list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeEntity.class);
        return list;
    }

    @Override
    public AppServeEntity findOneSeverMealByIds(String packId) throws Exception {
        List<AppServeEntity> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("serveId",packId);
        String sql = " SELECT " +
                "a.ID serveId," +
                "a.SERSM_NAME serveName," +
                "a.SERSM_GROUP_ID groupId," +
                "a.SERSM_TOTAL_FEE fee," +
//                    "'"+this.getSignFormId() +"' signFormId,"+
                "'' groupList " +
                "FROM APP_SERVE_SETMEAL a " +
                "WHERE a.ID = :serveId";
        list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeEntity.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据服务包主键查询集合（多个服务包主键用“;”）pkId不能为空
     * @param pkId
     * @return
     * @throws Exception
     */
    @Override
    public List<AppServeSetmeal> findMealByIds(String pkId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("pkIds",pkId.split(";"));
        String sql = "select * from APP_SERVE_SETMEAL WHERE ID IN (:pkIds) ";
        List<AppServeSetmeal> list = sysDao.getServiceDo().findSqlMap(sql,map,AppServeSetmeal.class);
        return list;
    }

    @Override
    public List<GaiRuiMealEntity> findListMealByHospId(String hospId) throws Exception {
        List<GaiRuiMealEntity> list = new ArrayList<>();
        if(StringUtils.isNotBlank(hospId)){
            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,hospId);
            if(dept!=null){
                Map<String,Object> map = new HashMap<>();
                map.put("hospId",hospId);
                map.put("sj",dept.getHospAreaCode().substring(0,2)+"0000000000");
                map.put("city",dept.getHospAreaCode().substring(0,4)+"00000000");
                map.put("street",dept.getHospAreaCode().substring(0,6)+"000000");
                map.put("area",dept.getHospAreaCode().substring(0,9)+"000");
                map.put("areaCode",dept.getHospAreaCode());
                map.put("level","0");
                map.put("openState","1");
                String sql = " SELECT " +
                        "a.ID serveId," +
                        "a.SERSM_NAME serveName," +
                        "a.SERSM_GROUP_ID groupId," +
                        "a.SERSM_TOTAL_FEE fee," +
                        "a.SERSM_VALUE serveValue," +
                        "'' groupList " +
                        "FROM APP_SERVE_SETMEAL a " +
                        "WHERE 1=1 ";

                sql += " AND (a.SERSM_CREATE_DEPT =:hospId OR (a.SERSM_OPEN_STATE =:openState " +
                        "AND (find_in_set(:sj, a.SERSM_OPEN_AREA) OR find_in_set(:city, a.SERSM_OPEN_AREA) OR find_in_set(:street, a.SERSM_OPEN_AREA)" +
                        " OR find_in_set(:area, a.SERSM_OPEN_AREA) OR find_in_set(:areaCode, a.SERSM_OPEN_AREA))))";
                list = sysDao.getServiceDo().findSqlMapRVo(sql,map,GaiRuiMealEntity.class);
            }

        }
        return list;
    }

    @Override
    public String findMealIdsByValue(String value) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("value",value.split(";"));
        String sql = "SELECT\n" +
                "\tGROUP_CONCAT(ID) mealIds \n" +
                "FROM\n" +
                "\tapp_serve_setmeal\n" +
                "WHERE\n" +
                "\tSERSM_VALUE IN (:value) ";
        List<Map> list = sysDao.getServiceDo().findSqlMap(sql,map);
        if(list != null && list.size()>0){
            if(list.get(0)!= null && list.get(0).get("mealIds") != null){
                return list.get(0).get("mealIds").toString();
            }
        }
        return null;
    }
}
