package com.ylz.bizDo.app.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.ylz.bizDo.app.entity.AppHfsSignSscEntity;
import com.ylz.bizDo.app.entity.AppWebSignFormListEntity;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppHfsSignSscQvo;
import com.ylz.bizDo.app.vo.AppSignFormVo;
import com.ylz.bizDo.app.vo.AppSignVo;
import com.ylz.bizDo.app.vo.AppWebSignQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppArchivintPeopleEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppSignPeopleEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommLyQvo;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.commonVo.WebSurrenderSignVo;
import com.ylz.bizDo.jtapp.drEntity.AppDrCountEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrPatientFwEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrPatientSignEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrSignPeoleListEntity;
import com.ylz.bizDo.jtapp.drEntity.AppSignFormEntity;
import com.ylz.bizDo.jtapp.drEntity.AppSignFormListEntity;
import com.ylz.bizDo.jtapp.drEntity.ToplimitEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppSMSignEntity;
import com.ylz.bizDo.jtapp.patientVo.AppFamilySignQvo;
import com.ylz.bizDo.jtapp.patientVo.AppGoToSignQvo;
import com.ylz.bizDo.jtapp.patientVo.AppPatientQyQvo;
import com.ylz.bizDo.jtapp.ysChangeEntity.YsChangeMsgEntity;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeCountQvo;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeSureQvo;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.bizDo.news.Entity.SignPeopleEntity;
import com.ylz.bizDo.news.vo.NewsTableQqvo;
import com.ylz.bizDo.smjq.smEntity.AppPeopleBasicEntity;
import com.ylz.bizDo.smjq.smEntity.AppSmSignEntity;
import com.ylz.bizDo.smjq.smEntity.AppSmyxPatientEntity;
import com.ylz.bizDo.smjq.smVo.AppSmPeopleBasicVo;
import com.ylz.bizDo.smjq.smVo.AppSmyxPatientVo;
import com.ylz.bizDo.web.vo.WebSignSaveVo;

/**
 * Created by zzl on 2017/6/14.
 */
public interface AppSignformDao  {

    /**
     * 个人签约
     * @param patientId 患者
     * @param teamid 团队
     * @param drId 医生id
     * @param signpackageid 服务包id（多个用“;”隔开）
     */
    public AppSignForm signFormUser(String patientId, String teamid,String drId,String signpackageid,String signUpHpis,String signatureImageUrl,String signWay,String signImageUrl,String signMobileType)throws Exception;

    /**
     * 根据用户id或团队id查询签约单列表
     * @param qvo
     * @return
     */
    public List<AppSignFormListEntity> findSignFormByUserOrTeam(AppCommQvo qvo) throws Exception;
    /**
     * 同意签约
     * @param signFormId
     * @param signPersGroup 居民分组
     * @param signHealthGroup 健康分布
     * @param labelGruops 疾病类型接口 多分号隔开
     * @param signsJjType 经济类型
     * @param patientjmda
     *@param patientjtda
     * @param signlx @return
     */
    public AppSignForm agreeSignForm(String signFormId, String signPersGroup, String signHealthGroup, String labelGruops, String fee, String payTYpe, String signsJjType, String patientjmda, String patientjtda, String signlx,String drId,String signDrAssistantId ) throws Exception;

    /**
     * 发送签约提醒
     * @param sign
     */
    public void SignFormNotices(AppSignForm sign)throws Exception;

    /**
     * 查询用户已签约单(未续约)
     * @param userId 患者id
     * @return
     */
    public AppSignForm findSignFormByUser(String userId)throws Exception;

    /**
     * 医生代签约 医生代签审核直接通过
     * @param patientId 患者id
     *  @param teamid 团队
     * @param signPersGroup 居民分组 多分号隔开
     * @param signHealthGroup 健康分布
     * @param labelGruops 疾病类型接口 多分号隔开
     * @param drId 医生id
     * @param patientAddress
     *@param patientProvince
     * @param patientCity
     * @param patientArea
     * @param patientStreet
     * @param patientNeighborhoodCommittee @return
     */
    public AppSignForm drAgreeSignForm(String patientId, String teamid, String signPersGroup, String signHealthGroup, String labelGruops, String drId,
                                       String patientAddress, String patientProvince, String patientCity, String patientArea, String patientStreet,
                                       String patientNeighborhoodCommittee,String signsJjType,String signpackageid,String signlx,
                                       String patientjtda,String patientjmda,String signUpHpis,String signatureImageUrl,String signPhotoImageUrl,String signImageUrl,String signDrAssistantId,String signMobileType) throws Exception;

    /**
     * 医生首页统计
     * @param teamId 团队主键
     * @return
     */
    public AppDrCountEntity findSignCount(String teamId,String userId)throws Exception;

    /**
     * 个人代签约
     * @param patientId 代签人
     * @param patientIds 多患者id使用;号隔开
     *  @param teamid 团队 代签人自己的团队
     *  @param drId 医生id
     */
    public AppSignBatch patientSignFormUser(String patientId, String patientIds, String teamid,String drId,String signpackageid,String signUpHpis,String signImageUrl) throws Exception;

    public List<AppSignForm> changeGroup(AppCommQvo qvo) throws Exception;

    public List<AppSignForm> findAll() throws Exception;

    public List<AppSignForm> findSignFormByTeam(String teamId, String signState) throws Exception;

    /**
     * 查询签约结束时间的集合
     */
    public List<AppSignForm> findByGreen() throws Exception;
    public List<AppSignForm> findByYellow() throws Exception;
    public List<AppSignForm> findByRed() throws Exception;

    /**
     * 续签
     * @param patientId
     * @return
     */
    public AppSignForm reSignForm(String patientId,AppCommQvo qvo) throws Exception;

    public AppSerial getAppSerial(String code, String type) throws Exception;

    public Map<String,Object> getNum(String s, String type) throws Exception;

    public AppSignForm getSignFormUserId(String userId) throws Exception;

    /**
     * 查询签约信息
     * @param userId
     * @return
     */
    public AppDrPatientSignEntity findPatient(String userId,String signState) throws Exception;

    /**
     * 查询待签约记录
     * @param userId
     * @return
     */
    public AppSignForm findSignOne(String userId) throws Exception;

    /**
     * 查询居民服务类型
     * @param userId
     * @param requestUserId
     * @param requestUserName
     * @return
     * @throws Exception
     */
    public AppDrPatientFwEntity findFwType(String userId,String cityCode,String requestUserId,String requestUserName,String userType) throws Exception;

    /**
     * 根据个人姓名、身份证、电话查询签约信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSMSignEntity findSignXx(AppPatientQyQvo qvo) throws Exception;

    /**
     * 根据患者主键查询签约单
     * @return
     */
    public List<AppSignForm> findByPatientId(String patientId) throws Exception;

    /**
     * 查询用户已签约单(未续约)
     * @param userId 患者id
     * @return
     */
    public AppSignForm findSignFormByUserState(String userId) throws Exception;

    /**
     * 删除居民签约单
     * @param patientId
     * @param signDelType
     * @param signDelReason
     * @param signDieDate
     */
    public void signDelPatient(String patientId,String signDelType, String signDelReason, String signDieDate) throws Exception;

    /**
     * 查询web数据是否存在
     * @param patientId
     * @param teamid
     * @param drId
     * @return
     */
    public AppSignForm findBySignweb(String patientId, String teamid, String drId) throws Exception;

    /**
     * 申请变更
     * @param qvo
     * @return
     * @throws Exception
     */
    public String changeState(YsChangeCountQvo qvo) throws Exception;

    /**
     * 变更记录
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<YsChangeMsgEntity>  findChangeList(YsChangeCountQvo qvo) throws Exception;

    /**
     * 同意或拒绝变更
     * @param qvo
     * @return
     * @throws Exception
     */
    public String sureChange(YsChangeSureQvo qvo) throws Exception;

    /**
     * 查询变更记录
     * @param drId
     * @return
     * @throws Exception
     */
    public List<YsChangeMsgEntity> findChangeHList(String drId) throws Exception;

    /**
     * 查询签约人员信息
     * @param qvo
     * @return
     */
    public List<SignPeopleEntity> findPeople(NewsTableQqvo qvo) throws Exception;

    /**
     * 查询患者续签签约单
     * @param signFormId
     * @return
     * @throws Exception
     */
    public List<AppSignForm> renewalReminder(String signFormId) throws Exception;

    /**
     * 查询可续约签约单列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppSignFormEntity> findRenewalList(AppCommQvo qvo) throws Exception;

    /**
     * 查询医生签约上限
     * @param drUser
     * @return
     * @throws Exception
     */
    public ToplimitEntity findDrCount(AppDrUser drUser) throws Exception;

    /**
     * 判断团队或医生是否签约上限
     * @param teamId
     * @return
     * @throws Exception
     */
    public String findTeamCount(String teamId) throws Exception;

    public ToplimitEntity findToTeamCount(String teamId) throws Exception;

    /**
     * 根据医生id和患者id查询签约单信息
     * @param patientId
     * @param drId
     * @return
     * @throws Exception
     */
    public AppSignForm findByPatientDr(String patientId,String drId) throws Exception;

    /**
     * 患者转签
     * @param qvo
     * @return
     * @throws Exception
     */
    public String goToSign(AppGoToSignQvo qvo) throws Exception;

    /**
     * 续签提醒
     * @return
     */
    public List<AppSignForm> findByGreen(String userId) throws Exception;
    public List<AppSignForm> findByYellow(String userId) throws Exception;
    public List<AppSignForm> findByRed(String userId) throws Exception;


    /**
     * cjw
     * 查询是否签约
     */
    public AppWebSignFormListEntity signfind (AppWebSignQvo qvo) throws  Exception;

    /**
     * 获取签约最旧那条
     * @param patientId
     * @param drId
     * @return
     */
    public String findMinDate(String patientId, String drId) throws Exception;

    /** cjw
     * 签约筛查
     * @param hfsvo
     * @return
     * @throws Exception
     */
    public List<AppHfsSignSscEntity> findsignsx(AppHfsSignSscQvo hfsvo) throws Exception;

    /**Dmoa
     * 签约查询
     */
    public WebSignSaveVo signLook(AppWebSignQvo qvo)throws Exception;

    public List<AppSignVo> findSignXxWeb(AppCommQvo qvo) throws Exception;

    /**
     * 查看所有的签约信息、分省、市、区县、社区等级
     * WangCheng
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppSignVo> findAllSignForm(AppCommQvo qvo) throws Exception;

    /**
     * 建档立卡贫困人口签约查询
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppSignVo> findPoorSign(AppCommQvo qvo) throws Exception;

    public Integer findSignXxCount(AppCommQvo qvo) throws Exception;

    /**
     * 查询履约人员列表
     * @param qvo
     * @return
     */
    public List<AppDrSignPeoleListEntity> findSignFormList(AppCommLyQvo qvo) throws Exception;

    /**
     * 查询过期签约单列表
     * @return
     */
    public List<AppSignForm> findOverdue() throws Exception;

    /**
     * 续签
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSignForm renewSign(AppCommQvo qvo) throws Exception;


    /**
     * 申请变更
     * cjw
     * @param qvo
     * @return
     * @throws Exception
     */
    public String changeStates(YsChangeCountQvo qvo) throws Exception;



    /**
     * cjw
     * 身份证查询 签约数据
     */
    public AppSignForm findpatientIdNo(String patientidno)throws  Exception;



    public List<AppSignForm> findByDate()throws Exception;

    /**
     * 家庭版签约申请
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppSignForm> signFormFamily(AppFamilySignQvo qvo) throws Exception;

    /**
     * 根据签约批次id查询签约列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppSignFormListEntity> findByBatchId(AppCommQvo qvo) throws Exception;
    /**
     * 根据用户id或团队id查询家庭签约单列表
     * @param qvo
     * @return
     */
    public List<AppSignFormListEntity> findSignFormByUserOrTeamT(AppCommQvo qvo)throws Exception;


    /**
     * cxw
     * 通过身份证查询与医保未交互的签约信息
     * @param PtIdNo
     * @return
     * @throws Exception
     */
    public AppSignForm findSignWebByPtIdNo(String PtIdNo,String signState) throws Exception;

    /**
     * 查询续签数据
     * @return
     */
    public List<AppSignForm> findChangeRenewalList() throws Exception;

    /**
     * 通过用户id查询 签约信息 type 0预签约 2已签约  02 预+已签约（IN）
     * @param userId
     * @param type
     * @return
     */
    public AppSignForm findSignFormByUserId(String userId,String type) throws Exception;

    /**
     * 个人端提交转签
     * @param qvo
     * @return
     * @throws Exception
     */
    public String subChangeUser(AppGoToSignQvo qvo) throws Exception;

    /**
     * 根据医生id和用户id查询签约单
     * @return
     */
    public AppSignForm findFormByDr(String patientId,String drId) throws Exception;

    public String findPkRemark(String pkid) throws Exception;

    public List<AppSignForm> findFormByDr(String drId) throws Exception;

    // 审核查询
    public List<AppSignVo> findSignToexamine(AppCommQvo qvo) throws Exception;

    public List<AppSignForm> findSignFormListByIds(String[] ids) throws Exception;

    public AppSignForm findSignFormByState(String patientId) throws Exception;

    public List<AppSignForm> findSignFormByTeamId(String teamId) throws Exception;
    /**
     * 死亡解约
     * @param qvo
     * @return
     * @throws Exception
     */
    public AppSignForm surrenderSign(WebSurrenderSignVo qvo) throws Exception;

    /**
     * 通过居民身份证查询有效签约信息
     * @param patientIdNo
     * @return
     */
    public AppSignForm findFormByJmda(String patientIdNo) throws Exception;

    /**
     * 根据居民档案号查询居民是否死亡
     * @param jmdah
     * @return
     * @throws Exception
     */
    public String findDieState(String jmdah) throws Exception;



    /**
     * 解约 查询列表
     * WangCheng
     */
    public List<AppSignVo> findDisslution(AppCommQvo qvo) throws Exception;

    /**
     * 查询解约列表数据（导出专用）
     * WangCheng
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppSignVo> listDissolutionData(AppCommQvo qvo) throws Exception;

    /**
     * 根据居民主键查询居民是否是已签约、预签约、代签约
     * @param patientId
     * @return
     */
    public AppSignForm findSignById(String patientId) throws Exception;


    public AppSignSetting findsignSetting(String areaCode)throws Exception;


    public Map<String,Object> mealCost(String signpackageid,AppSignForm sign,String signPersGroup,String signsJjType) throws Exception;

    /**
     * 保存签约单图片上传数据
     * @param signImageUrl
     * @param signId
     * @param upHpis
     * @throws Exception
     */
    public void saveImage(String signImageUrl,String signId,String upHpis,String type,String areaCode) throws Exception;

    /**
     * 查询三明签约信息取签约团队和机构
     * @param patientId
     * @return
     */
    public AppSmSignEntity findSignBypatientId(String patientId) throws Exception;

    /**
     * 查询机构糖尿病高血压居民
     * @param qvo
     * @return
     */
    public List<AppPeopleBasicEntity> findPeopleByOrg(AppSmPeopleBasicVo qvo) throws Exception;

    /**
     * 修改建档立卡人群签约状态
     * @param idno 身份证号
     * @param signId 签约单id
     * @param drId 医生id
     * @param teamId 团队id
     * @param signState 签约状态
     * @param groups 服务人群
     * @param areaCode 区域编码
     * @param hospId 机构主键
     * @param signDate 签约时间
     * @param user 居民信息
     */
    public void addOrModifyArchivingSign(String idno, String signId, String drId, String teamId, String signState, String[] groups, String areaCode, String hospId, Calendar signDate,AppPatientUser user) throws Exception;

    public List<AppArchivintPeopleEntity> findSignArchivingByTeamId(ResidentVo qvo) throws Exception;

    public List<AppArchivintPeopleEntity> findPeopleBySign(ResidentVo qvo) throws Exception;

    public Map<String,Object> findMapBySign(ResidentVo qvo) throws Exception;

    /**
     * 查询三明尤溪高血压和糖尿病居民姓名（暂时三明尤溪专用）
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppSmyxPatientEntity> findPeopleList(AppSmyxPatientVo qvo) throws Exception;
    
	/**
	 * 获取某地区 某年今天已签约的服务包和数量
	 * 
	 * @version 2018-05-08
	 * @author lyy
	 * @param areaCode
	 * @param year
	 * @return
	 */
	public List<Map> findSetmealCountByAreaCodeAndYear(String areaCode, String year) throws Exception;

	/**
	 * 获取某医生 某年已签约的服务包和数量
	 *
	 * @version 2018-05-08
	 * @author lyy
	 * @param drId
	 * @param year
	 * @return
	 */
	public List<Map> findSetmealCountByDrIdAndYear(String drId, String year) throws Exception;

    /**
     * 更新居民身份查询服务类型
     * @param idno
     * @param cityCode
     * @param requestUserId
     * @param requestUserName
     * @param userType
     * @return
     * @throws Exception
     */
    public AppDrPatientFwEntity findFwTypeByIdno(String idno,String cityCode,String requestUserId,String requestUserName,String userType) throws Exception;

    /**
     * 查询某个居民在签约协议期限内的健康档案调阅记录
     * @param patientIdno  居民身份证号
     * @param signFromDate 协议开始日期
     * @param signToDate   协议截止日期
     * @return
     */
    public List<AppReadFileLog> findReadFileLog(String patientIdno, Calendar signFromDate, Calendar signToDate) throws Exception;
    //查询已经过期却未续签的签约数据
    public List<AppSignForm> findOverdueSign() throws Exception;

    /**
     * 根据ID查询签约信息
     *
     * @param id 签约单ID
     * @return 签约信息（包含服务人群名称、特色服务包名称等）
     */
    public AppSignFormVo findAppSignFormById(String id) throws Exception;

    /**
     * 根据团队id判断是否有签约数据
     * @param teamId
     * @return
     */
    public boolean findSignByteam(String teamId) throws Exception;

    /**
     * 根据居民身份证查看是否有已经签约未过期的签约信息
     * WangCheng
     * @param patientIdno
     * @return
     */
    public AppSignForm findSignFrom(String patientIdno) throws Exception;

    /**
     * 根据身份证查看是否存在已经续签的签约单信息
     * WangCheng
     * @param patientIdno
     * @param dissolutionType
     * @return
     */
    public AppSignForm findSignFromByIdNo(String patientIdno,String dissolutionType) throws Exception;

    /**
     * 查询签约起始日期默认一个月的权限
     * WangCheng
     * @param signAreaCode
     * @return
     */
    public AppSignFormSetting findSignStartSate(String signAreaCode) throws Exception;

    /**
     * 根据传递的签约状态数组查询签约记录
     * @param signState
     * @param patientId
     * @return
     */
    public AppSignForm findSignBySignState(String[] signState,String patientId) throws Exception;

    /**
     * 查询机构糖尿病、高血压、精神病、结核病居民
     * @param qvo
     * @return
     */
    public List<AppPeopleBasicEntity> findPeopleByOrgNew(AppSmPeopleBasicVo qvo) throws Exception;

    public Map<String,Object> findMapCount(AppSmPeopleBasicVo qvo) throws Exception;
    /**
     * 查询三明尤溪精神病和结核病居民姓名（暂时三明尤溪专用）
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppSmyxPatientEntity> findPeopleListNew(AppSmyxPatientVo qvo) throws Exception;

    public List<AppSignForm> findSignByRepeatSign() throws Exception;

    /**
     * 根据居民身份证查询签约信息
     * @param idno
     * @return
     * @throws Exception
     */
    public AppSignForm findByPatientIdno(String idno) throws Exception;

    /**
     * 导入签约数据
     * @param map
     * @return
     * @throws Exception
     */
    public String addImportExcelSign(Map<Integer, String> map) throws Exception;

    /**
     * 根据居民主键查询所有有效签约单（用于处理签约单重复）
     * @param userId
     * @return
     * @throws Exception
     */
    public List<AppSignForm> findListSignByUserId(String userId) throws Exception;

    /**
     * 姓名、身份证、档案号、是否签约、服务类型、签约行政区划、行政区划级别
     * 签约底层人员信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppSignPeopleEntity> findByHospQvo(ResidentVo qvo) throws Exception;
}
