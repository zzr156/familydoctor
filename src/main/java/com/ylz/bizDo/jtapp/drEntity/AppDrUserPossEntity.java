package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.entity.AppMenuEntity;
import com.ylz.bizDo.app.entity.AppMyServiceEntity;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppSignSetting;
import com.ylz.bizDo.app.vo.AppMenuRoleQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.AppMenuModuleRoleType;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.SericuryUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by zzl on 2018/10/17.
 */
public class AppDrUserPossEntity {
    private String id;//主键
    private String drName;//医生名称
    private String drAccount;//账号
    private String drHospId;//医院主键
    private String drCode;//医生编号
    private String drGender;//医生性别
    private String drTel;//医生电话
    private String drImageUrl;//医生头像
    private String drImageName;//头像图片文件名
    private String drIntro;//医生简介
    private String drGood;//医生擅长
    private String drType;//医生类别 1,全科医生,2护士,3公卫人员
    private String drJobTitle;//医生工作职称
    private String drState;//医生状态
    private String drHospAreaCode;//医院区域编码
    private String drRole;//权限类型
    private List drTeamId;//医生团队id
    private String drHospName;//医院名称
    private String drImageSuffix;//头像图片后缀
    private String cityCode;//城市代码
    private String easeId;//环信主键
    private String hospAddress;//医院地址
    private String drPwdState;//是否有修改过密码
    private String drHospType;//医院级别（0基层医院 1上级医院）
    private List<AppDrMAccountEntity> mAccountList;//医生多账号信息
    private String state;
    private String openJdState;//开启是否建档状态
    private String evaluationState;//pos机是否开启评价
    private String drAbroabUrl;//医生外网签名地址


    public String getHospAddress() {
        return hospAddress;
    }

    public void setHospAddress(String hospAddress) {
        this.hospAddress = hospAddress;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public void setDrAccount(String drAccount) {
        this.drAccount = drAccount;
    }

    public void setDrHospId(String drHospId) {
        this.drHospId = drHospId;
    }

    public void setDrCode(String drCode) {
        this.drCode = drCode;
    }

    public void setDrGender(String drGender) {
        this.drGender = drGender;
    }

    public void setDrTel(String drTel) {
        this.drTel = drTel;
    }

    public void setDrImageUrl(String drImageUrl) {
        this.drImageUrl = drImageUrl;
    }

    public void setDrImageName(String drImageName) {
        this.drImageName = drImageName;
    }

    public void setDrIntro(String drIntro) {
        this.drIntro = drIntro;
    }

    public void setDrGood(String drGood) {
        this.drGood = drGood;
    }

    public void setDrType(String drType) {
        this.drType = drType;
    }

    public void setDrJobTitle(String drJobTitle) {
        this.drJobTitle = drJobTitle;
    }

    public void setDrState(String drState) {
        this.drState = drState;
    }

    public String getDrImageName() {
        return drImageName;
    }

    public String getId() {
        return id;
    }

    public String getDrName() {
        return drName;
    }

    public String getDrAccount() {
        return drAccount;
    }

    public String getDrHospId() {
        return drHospId;
    }

    public String getDrCode() {
        return drCode;
    }

    public String getDrGender() {
        return drGender;
    }

    public String getDrTel() {
        return drTel;
    }

    public String getDrImageUrl() {
        return drImageUrl;
    }

    public String getDrIntro() {
        return drIntro;
    }

    public String getDrGood() {
        return drGood;
    }

    public String getDrType() {
        return drType;
    }

    public String getDrJobTitle() {
        return drJobTitle;
    }

    public String getDrState() {
        return drState;
    }

    public String getDrHospAreaCode() {
        return drHospAreaCode;
    }

    public void setDrHospAreaCode(String drHospAreaCode) {
        this.drHospAreaCode = drHospAreaCode;
    }

    public String getDrRole() {
        return drRole;
    }

    public void setDrRole(String drRole) {
        this.drRole = drRole;
    }

    /*public void setDrRole(String drRole) {
		if(StringUtils.isNotBlank(this.getId())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("drId",this.getId());
			String sql = " SELECT * FROM APP_MEUN_ROLE WHERE MENU_DR_ID =:drId";
			List<AppMeunRole> list = dao.getServiceDo().findSqlMap(sql,map,AppMeunRole.class);
			if(list!=null){
				if(list.size()==1){
					for(AppMeunRole ls:list){
						if(ls.getMenuRoleValue().equals(CommonRole.GLY.getValue())){
							drRole="2";//管理员
						}
						if(ls.getMenuRoleValue().equals(CommonRole.PTQX.getValue())){
							drRole="0";//普通权限
						}
					}
				}else if(list.size()==2){
					drRole = "1";//既是普通权限也是管理员
				}
			}
		}
		this.drRole = drRole;
	}*/

    public List getDrTeamId() {
        return drTeamId;
    }

    public void setDrTeamId(String drTeamId) {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("drId",this.getId());
            String sql = "SELECT\n" +
                    "\tb.ID id,\n" +
                    "\tb.TEAM_NAME teamName,\n" +
                    "\t(SELECT c.code_title FROM CD_CODE c where c.code_value = a.MEM_WORK_TYPE AND c.code_group = 'workType') teamWorkType\n" +
                    "FROM\n" +
                    "\tAPP_TEAM_MEMBER a\n" +
                    "INNER JOIN APP_TEAM b ON a.MEM_TEAMID = b.ID\n" +
                    "WHERE\n" +
                    "\ta.MEM_DR_ID = :drId AND b.TEAM_DEL_STATE = '0' ";
            List<Map> list = dao.getServiceDo().findSqlMap(sql,map);
            if(list!=null && list.size()>0){
                this.drTeamId = list;
            }

        }
    }

    public String getDrHospName() {
        return drHospName;
    }

    public void setDrHospName(String drHospName) {
        if(StringUtils.isNotBlank(this.getDrHospId()) ){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppHospDept hospDept = (AppHospDept) dao.getServiceDo().find(AppHospDept.class,this.getDrHospId());
            if(hospDept!=null){
                drHospName = hospDept.getHospName();
            }
        }

        this.drHospName = drHospName;
    }

    public String getDrImageSuffix() {
        return drImageSuffix;
    }

    public void setDrImageSuffix(String drImageSuffix) {
        this.drImageSuffix = drImageSuffix;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdAddress p = dao.getCdAddressDao().findByCode(cityCode);
        if(p != null){
            String code = AreaUtils.getAreaCode(p.getCtcode(),"2");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
            if(value != null) {
                this.cityCode = value.getCodeTitle();
            }
        }
    }

    public String getEaseId() {
        return easeId;
    }

    public void setEaseId(String easeId) {
        try{
            SericuryUtil p = new SericuryUtil();
            this.easeId = p.encrypt(easeId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getAreaCode() {
        if(StringUtils.isNotBlank(this.drRole) &&  StringUtils.isNotBlank(this.drHospAreaCode)){
            if(!this.getDrRole().contains(";")) {
                return AreaUtils.getAreaCodeAll(this.getDrHospAreaCode(), this.getDrRole());
            }else{
                String[] arr = this.getDrRole().split(";");
                String index = "9";
                for(int i=0;i<arr.length;i++){
                    if(Integer.parseInt(arr[i])<Integer.parseInt(index)){
                        index = arr[i];
                    }
                }
                return AreaUtils.getAreaCodeAll(this.getDrHospAreaCode(), index);
            }
        }
        return "";
    }

    public String getAreaName() throws Exception{
        if(StringUtils.isNotBlank(this.drRole) &&  StringUtils.isNotBlank(this.drHospAreaCode)){
            if(!this.getDrRole().contains(";")) {
                SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
                String code = AreaUtils.getAreaCodeAll(this.getDrHospAreaCode(), this.getDrRole());
                CdAddress p = dao.getCdAddressDao().findByCode(code);
                if(p != null){
                    return p.getAreaSname();
                }

            }else{
                String[] arr = this.getDrRole().split(";");
                String index = "9";
                for(int i=0;i<arr.length;i++){
                    if(Integer.parseInt(arr[i])<Integer.parseInt(index)){
                        index = arr[i];
                    }
                }
                SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
                String code = AreaUtils.getAreaCodeAll(this.getDrHospAreaCode(), index);
                CdAddress p = dao.getCdAddressDao().findByCode(code);
                if(p != null){
                    return p.getAreaSname();
                }
            }
        }
        return "";
    }

    public String getManageState() throws Exception{
        String result = "";
        if(StringUtils.isNotBlank(this.drRole) &&  StringUtils.isNotBlank(this.drHospAreaCode)){
            if(!this.getDrRole().contains(";")) {
                result = AreaUtils.getAreaCodeAll(this.getDrHospAreaCode(), this.getDrRole());
            }else{
                String[] arr = this.getDrRole().split(";");
                String index = "9";
                for(int i=0;i<arr.length;i++){
                    if(Integer.parseInt(arr[i])<Integer.parseInt(index)){
                        index = arr[i];
                    }
                }
                result = AreaUtils.getAreaCodeAll(this.getDrHospAreaCode(), index);
            }
        }
        if(StringUtils.isNotBlank(result)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            Calendar now = Calendar.getInstance();
            CdAddressPeople people = dao.getCdAddressPeopleDao().findByCacheCode(result,String.valueOf(now.get(Calendar.YEAR)));
            if(people != null){
                return people.getAreaState();
            }
        }
        return "";
    }

    public String getDrHospMotoe() {
        if(StringUtils.isNotBlank(this.getDrHospId()) && StringUtils.isNotBlank(this.getDrHospAreaCode())){
            String areaCode = AreaUtils.getAreaCode(this.drHospAreaCode,"2");
            if(areaCode.equals("3503") || areaCode.equals("3504") ||
                    areaCode.equals("3505") || areaCode.equals("3506") || areaCode.equals("3507")){
                String[] hospId = getDrHospId().split("_");
                return hospId[1];
            }else {
                return this.getDrHospId();
            }
        }
        return "";
    }

    /**
     * 权限菜单
     * @return
     */
    public List<AppMenuEntity> getStrDrMenuRole(){
        List<AppMenuEntity> ls = new ArrayList<AppMenuEntity>();
        if(StringUtils.isNotBlank(this.getDrHospAreaCode())){
            AppMenuRoleQvo qvo = new AppMenuRoleQvo();
            qvo.setHospId(this.getDrHospId());
            qvo.setMenuType(AppMenuModuleRoleType.POSS.getValue());
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            ls = dao.getAppModuleRoleDao().findMenuRole(qvo);
            if(ls == null){
                qvo.setHospId(null);
                String code = AreaUtils.getAreaCodeAll(this.getDrHospAreaCode(), "2");
                qvo.setAreaCode(code);
                ls = dao.getAppModuleRoleDao().findMenuRole(qvo);
            }
        }
        return  ls;
    }



    /**
     * 我的服务
     * @return
     */
    public List<AppMyServiceEntity> getMyServiceMenu() throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        AppMenuRoleQvo qvo = new AppMenuRoleQvo();
        qvo.setDrPaiteintId(this.getId());
        List<AppMyServiceEntity> ls = dao.getAppMyServiceMenuDao().findMenuRole(qvo);
        if(ls == null){
            if(this.getStrDrMenuRole() != null && this.getStrDrMenuRole().size() >0){
                dao.getAppMyServiceMenuDao().addMySerViceMenu(this.getStrDrMenuRole(),this.getId());
                ls = dao.getAppMyServiceMenuDao().findMenuRole(qvo);
                return ls;
            }
        }else{
            List<AppMenuEntity> list = this.getStrDrMenuRole();
            if(list != null){
                if(list.size()  == ls.size() ){

                }else{
                    if(list != null && list.size()>0) {
                        dao.getAppMyServiceMenuDao().delServiceMenu(this.getId());
                        dao.getAppMyServiceMenuDao().addMySerViceMenu(list, this.getId());
                        ls = dao.getAppMyServiceMenuDao().findMenuRole(qvo);
                        return ls;
                    }
                }
            }
        }
        return ls;
    }

    /**
     * 推荐服务
     * @return
     */
    public List<AppMenuEntity> getRecommendedService(){
        List<AppMenuEntity> ls = new ArrayList<AppMenuEntity>();
        if(this.getStrDrMenuRole() != null && this.getStrDrMenuRole().size() >0){
            for(AppMenuEntity p : this.getStrDrMenuRole()){
                if(p.getMenuModule().equals("1")){
                    ls.add(p);
                }
            }
        }
        return ls;
    }

    public String getDrPwdState() {
        return drPwdState;
    }

    public void setDrPwdState(String drPwdState) {
        this.drPwdState = drPwdState;
    }

    public String getDrHospType() {
        return drHospType;
    }

    public void setDrHospType(String drHospType) {
        this.drHospType = drHospType;
    }

    public List<AppDrMAccountEntity> getmAccountList() {
        return mAccountList;
    }

    public void setmAccountList(List<AppDrMAccountEntity> mAccountList) {
        this.mAccountList = mAccountList;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOpenJdState() {
        return openJdState;
    }

    public void setOpenJdState(String openJdState) throws Exception {
        if(StringUtils.isNotBlank(this.getDrHospAreaCode())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            String cityCode = AreaUtils.getAreaCode(this.getDrHospAreaCode(),"2");
            AppSignSetting sett = dao.getAppSignSettingDao().findByAreaCode(cityCode);
            if(sett != null){
                openJdState = sett.getSignsOpenJd();
            }
        }
        this.openJdState = openJdState;
    }

    public String getEvaluationState() {
        return evaluationState;
    }

    public void setEvaluationState(String evaluationState) throws Exception {
        if(StringUtils.isNotBlank(this.getDrHospAreaCode())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            String cityCode = AreaUtils.getAreaCode(this.getDrHospAreaCode(),"2");
            AppSignSetting sett = dao.getAppSignSettingDao().findByAreaCode(cityCode);
            if(sett != null){
                evaluationState = sett.getSerevaluationState();
            }
        }
        this.evaluationState = evaluationState;
    }

    public String getDrAbroabUrl() {
        return drAbroabUrl;
    }

    public void setDrAbroabUrl(String drAbroabUrl) {
        this.drAbroabUrl = drAbroabUrl;
    }
}
