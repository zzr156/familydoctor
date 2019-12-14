package com.ylz.packaccede.allDo;


import com.jasson.im.api.APIClient;
import com.ylz.bizDo.Motoe.dao.AppSignMotoeDao;
import com.ylz.bizDo.app.dao.*;
import com.ylz.bizDo.assessment.dao.*;
import com.ylz.bizDo.cd.dao.*;
import com.ylz.bizDo.dd.dao.DdJlqkQchDao;
import com.ylz.bizDo.dd.distribution.ManageMentCodePolicy;
import com.ylz.bizDo.mangecount.dao.*;
import com.ylz.bizDo.message.dao.MessageDao;
import com.ylz.bizDo.news.dao.NewsTableDao;
import com.ylz.bizDo.news.dao.NewsTypeDao;
import com.ylz.bizDo.performance.bizDo.AppPerFormanceDao;
import com.ylz.bizDo.plan.bizDo.AppFollowPlanDao;
import com.ylz.bizDo.plan.bizDo.AppNewFollowPlanDao;
import com.ylz.bizDo.plan.bizDo.AppSaveFollowTableDao;
import com.ylz.bizDo.register.dao.RegisterDao;
import com.ylz.bizDo.sys.service.SysLogBusinessDo;
import com.ylz.bizDo.web.dao.WebResidentAnalysisDao;
import com.ylz.bizDo.web.dao.WebSignAnalysisDao;
import com.ylz.bizDo.web.dao.WebSignFormDao;
import com.ylz.bizDo.web.dao.WebSignUpDao;
import com.ylz.packaccede.common.bizDo.Read.ServiceReadDo;
import com.ylz.packaccede.common.bizDo.ServiceDo;
import com.ylz.packcommon.common.dao.impl.BaseMultiDao;
import com.ylz.packcommon.common.util.IoUtils;
import com.ylz.pay.UniformPayMentBean;
import com.ylz.task.async.MsgPhoneAsyncBean;
import com.ylz.task.async.SchedulingAsyncBean;
import com.ylz.task.async.SecurityCardAsyncBean;
import org.springframework.stereotype.Component;

//import com.ylz.bizDo.register.dao.RegisterDao;

/**
 * @Repository持久层 Spring已经配置hibernate管理 不需要管理
 * @Service业务层 bizDo使用
 * @Controller控制层 Spring已经配置struts2管理 不需要管理
 * @Component中立的类 例如助手类
 */
@Component
public class SysDao {
    private ServiceDo serviceDo;
    private ServiceReadDo serviceReadDo;
    private CdMenuDo menuDo;
    private CdMenuSonDo sonDo;
    private CdRoleDo roleDo;
    private CdRoleMenuDo roleMenuDo;
    private CdRoleMenuSonDo roleSonDo;
	private IoUtils ioUtils;
    private CdUserDo userDo;
    private CdUserXxxDao cdUserXxxDao;
    private CdDeptDao cdDeptDao;
    private CdPositionDao positionDao;
    private CdCodeDao codeDao;//基础数据dao
	private CdMsgDao cdMsgDao;
	private ManageMentCodePolicy manageMentCodePolicy;
	private CdMessageDao cdMessageDao;
	private SysLogDeletDo sysLogDeletDo; //删除日志
	//新闻
	private NewsTypeDao newsTypeDao;
	private NewsTableDao newsTableDao;
//	flow
	//private FlowDao flowDao;
	private DdJlqkQchDao ddJlqkQchDao;
	private CdCarouselimgDao cdCarouselimgDao;
	//地址信息
	private CdAddressDao cdAddressDao;
	private APIClient apiClient;
	private MsgPhoneAsyncBean msgPhoneAsyncBean;
	private SecurityCardAsyncBean securityCardAsyncBean;
	private CdShortMessageDao cdShortMessageDao;
	private BaseMultiDao baseMultiDao;
	private AppAgreementDao appAgreementDao;
	private AppBloodgluDao appBloodgluDao;
	private AppBloodpressureDao appBloodpressureDao;
	private AppDrPatientKeyDao appDrPatientKeyDao;
	private AppDrUserDao appDrUserDao;
	private AppHospDeptDao appHospDeptDao;
	private AppLabelGroupDao appLabelGroupDao;
	private AppLabelManageDao appLabelManageDao;
	private AppMeunRoleDao appMeunRoleDao;
	private AppMyFamilyDao appMyFamilyDao;
	private AppPatientUserDao appPatientUserDao;
	private AppResidentManageDao appResidentManageDao;
	private AppRoleDao appRoleDao;
	private AppSignBatchDao appSignBatchDao;
	private AppSignformDao appSignformDao;
	private AppTeamDao appTeamDao;
	private AppTeamMemberDao appTeamMemberDao;
	private AppUserBloodgluDao appUserBloodgluDao;
	private AppUserBloodpressureDao appUserBloodpressureDao;
	private AppWorkdaySettingDao appWorkdaySettingDao;
	private AppDeviceDao appDeviceDao;
	private AppNoticeDao appNoticeDao;
	private AppHealthGuideDao appHealthGuideDao;
	private AppGuideTemplateDao appGuideTemplateDao;
	private AppUserHealthEDDao appUserHealthEDDao;
	private AppFollowPlanDao appFollowPlanDao;
	private AppWarningSettingDao appWarningSettingDao;
	private AppDrugDao appDrugDao;
	private AppMsgCodeDao appMsgCodeDao;
	private AppDrugGuideDao appDrugGuideDao;
	private AppHealthEnshrineDao appHealthEnshrineDao;
	private AppChildHealthPlanDao appChildHealthPlanDao;
	private AppConsultDao appConsultDao;
	private AppPregnantPlanDao appPregnantPlanDao;
	private CdAddressPeopleDao cdAddressPeopleDao;
	private AppResidentAnalysisDao appResidentAnalysisDao;
	private AppSignAnalysisDao appSignAnalysisDao;
	private AppPerFormanceDao appPerFormanceDao;
	private AppWorkingPlanDao appWorkingPlanDao;
	private AppSeekHelpDao appSeekHelpDao;
	private AppRefuseSignDao appRefuseSignDao;
	private AppHealthMeddleDao appHealthMeddleDao;
	private MessageDao messageDao;
	private AppHomeCareSettingDao appHomeCareSettingDao;
	private AppHospExtendDao appHospExtendDao;
	private AppSystemVsersionDao appSystemVsersionDao;
	private AppCityAreaDao appCityAreaDao;
	private AppRankAnalysisDao appRankAnalysisDao;
	private AppStatisticalAnalysisDao appStatisticalAnalysisDao;
	private AppWorkPlanDao appWorkPlanDao;
	private AppServeSettingDao appServeSettingDao;
	private AppChildInoculationPlanDao appChildInoculationPlanDao;
	private AppDrEvaluationDao appDrEvaluationDao;
	private AppNewFollowPlanDao appNewFollowPlanDao;
	private AppOrderDao appOrderDao;
	private UniformPayMentBean uniformPayMentBean;
	private WebSignFormDao webSignFormDao;
	private AppSignSettingDao appSignSettingDao;
	private AppMenuDao appMenuDao;
	private AppModuleRoleDao appModuleRoleDao;
	private AppModuleRoleSonDao appModuleRoleSonDao;
	private AppTcmSyndromeDao appTcmSyndromeDao;
	private AppServeGroupDao appServeGroupDao;
	private AppSecurtySettingDao appSecurtySettingDao;
	private AppServeGovDao appServeGovDao;
	private AppMunicipalAuthorityDao appMunicipalAuthorityDao;
	private AppMyServiceMenuDao appMyServiceMenuDao;
	private AppServeRoleDao appServeRoleDao;
	private AppPerformanceStatisticsDao appPerformanceStatisticsDao;
	private AppSaveFollowTableDao appSaveFollowTableDao;
	private  AppHealthDataDao appHealthDataDao;
	private AppServePackageDao appServePackageDao;
	private AppServeObjectDao appServeObjectDao;
	private AppServeSetmealDao appServeSetmealDao;
	private AppServeEconDao appServeEconDao;
	private AppWorkTypeDao appWorkTypeDao;
	private AppSignServeTeamDao appSignServeTeamDao;
	private AppEconAndGovDao appEconAndGovDao;
	private AppServeTabDao appServeTabDao;
	private AppTcmGuideDao appTcmGuideDao;
	private AppManageCountDao appManageCountDao;
	private AppExerciseCountDao appExerciseCountDao;
	private SchedulingAsyncBean schedulingAsyncBean;
	private AppSignsWarningSettingDao appSignsWarningSettingDao;
	private AppSignControlDao appSignControlDao;
	private AppPerformanceRegularFollowupDao appPerformanceRegularFollowupDao;
	//签约医保登记
	private RegisterDao registerDao;
	private WebSignAnalysisDao webSignAnalysisDao;
	private WebResidentAnalysisDao webResidentAnalysisDao;
	private AppHospitalDepartmentsDao appHospitalDepartmentsDao;
	private AppReferralTableDao appRefarralTableDao;
	private AppSignMotoeDao appSignMotoeDao;
	private AppOutpatientCountDao appOutpatientCountDao;
	private AppSignSetDao appSignSetDao;
	private AppOutpatientDao appOutpatientDao ;
	private WebSignUpDao WebSignUpDao;
	private AppHealthFileDao appHealthFileDao;
	private AppBasicDataDao appBasicDataDao;
	private AppBasicDataTempDao appBasicDataTempDao;
	private AppPerformanceTableDao appPerformanceTableDao;
	private AppPerformeRankCountDao appPerformeRankCountDao;
	private AppSubmissionRepetitionDao appSubmissionRepetitionDao;

	private AssessmentContentDao assessmentContentDao;// 考核项目内容表
	private AssessmentDao assessmentDao;// 考核主表
	private AssessmentDetailDao assessmentDetailDao;// 考核明细表
	private AssessmentTeamShareDao assessmentTeamShareDao;// 团队共享表
	private AssessLogDao assessLogDao;// 考核操作记录
	private ReviewLogDao reviewLogDao;// 审核操作记录
	private AppSignSubtableDao appSignSubtableDao;// 附件图片表
	private AppArchivingCardPeopeDao appArchivingCardPeopeDao;
	private SysLogBusinessDo sysLogBusinessDo;
	private AppMarkingLogDao appMarkingLogDao;
	private AppPossBindingDao appPossBindingDao;
	private AppHepVideoDao appHepVideoDao;

	private AppExerciseCountNewDao appExerciseCountNewDao;
	private AppArchivingCheckDao appArchivingCheckDao;

	private AppHealthAssessmentDao appHealthAssessmentDao;

	private CdPaperTemplateDao cdPaperTemplateDao;

	private AppMsgDao appMsgDao;
	private AppSignSettlementDao appSignSettlementDao;
	private AppLabelArchivingDao appLabelArchivingDao;

	public CdPaperTemplateDao getCdPaperTemplateDao() {
		return cdPaperTemplateDao;
	}

	public void setCdPaperTemplateDao(CdPaperTemplateDao cdPaperTemplateDao) {
		this.cdPaperTemplateDao = cdPaperTemplateDao;
	}

	public AppHealthAssessmentDao getAppHealthAssessmentDao() {
		return appHealthAssessmentDao;
	}

	public void setAppHealthAssessmentDao(AppHealthAssessmentDao appHealthAssessmentDao) {
		this.appHealthAssessmentDao = appHealthAssessmentDao;
	}

	public RegisterDao getRegisterDao() {
		return registerDao;
	}

	public void setRegisterDao(RegisterDao registerDao) {
		this.registerDao = registerDao;
	}

	public ServiceReadDo getServiceReadDo() {
		return serviceReadDo;
	}

	public void setServiceReadDo(ServiceReadDo serviceReadDo) {
		this.serviceReadDo = serviceReadDo;
	}

	public WebSignFormDao getWebSignFormDao() {
		return webSignFormDao;
	}

	public void setWebSignFormDao(WebSignFormDao webSignFormDao) {
		this.webSignFormDao = webSignFormDao;
	}

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}
	public BaseMultiDao getBaseMultiDao() {
		return baseMultiDao;
	}
	public void setBaseMultiDao(BaseMultiDao baseMultiDao) {
		this.baseMultiDao = baseMultiDao;
	}
	public ServiceDo getServiceDo() {
		return serviceDo;
	}
	public void setServiceDo(ServiceDo serviceDo) {
		this.serviceDo = serviceDo;
	}
	public CdMenuDo getMenuDo() {
		return menuDo;
	}
	public void setMenuDo(CdMenuDo menuDo) {
		this.menuDo = menuDo;
	}
	public CdMenuSonDo getSonDo() {
		return sonDo;
	}
	public void setSonDo(CdMenuSonDo sonDo) {
		this.sonDo = sonDo;
	}
	public CdRoleDo getRoleDo() {
		return roleDo;
	}
	public void setRoleDo(CdRoleDo roleDo) {
		this.roleDo = roleDo;
	}
	public CdRoleMenuDo getRoleMenuDo() {
		return roleMenuDo;
	}
	public void setRoleMenuDo(CdRoleMenuDo roleMenuDo) {
		this.roleMenuDo = roleMenuDo;
	}
	public CdRoleMenuSonDo getRoleSonDo() {
		return roleSonDo;
	}
	public void setRoleSonDo(CdRoleMenuSonDo roleSonDo) {
		this.roleSonDo = roleSonDo;
	}
	public IoUtils getIoUtils() {
		return ioUtils;
	}
	public void setIoUtils(IoUtils ioUtils) {
		this.ioUtils = ioUtils;
	}
	public CdUserDo getUserDo() {
		return userDo;
	}
	public void setUserDo(CdUserDo userDo) {
		this.userDo = userDo;
	}
	public CdDeptDao getCdDeptDao() {
		return cdDeptDao;
	}
	public void setCdDeptDao(CdDeptDao cdDeptDao) {
		this.cdDeptDao = cdDeptDao;
	}
	public CdPositionDao getPositionDao() {
		return positionDao;
	}
	public void setPositionDao(CdPositionDao positionDao) {
		this.positionDao = positionDao;
	}
	public CdCodeDao getCodeDao() {
		return codeDao;
	}
	public void setCodeDao(CdCodeDao codeDao) {
		this.codeDao = codeDao;
	}
	public CdMsgDao getCdMsgDao() {
		return cdMsgDao;
	}
	public void setCdMsgDao(CdMsgDao cdMsgDao) {
		this.cdMsgDao = cdMsgDao;
	}
	public ManageMentCodePolicy getManageMentCodePolicy() {
		return manageMentCodePolicy;
	}
	public void setManageMentCodePolicy(ManageMentCodePolicy manageMentCodePolicy) {
		this.manageMentCodePolicy = manageMentCodePolicy;
	}
	public NewsTypeDao getNewsTypeDao() {
		return newsTypeDao;
	}
	public void setNewsTypeDao(NewsTypeDao newsTypeDao) {
		this.newsTypeDao = newsTypeDao;
	}
	public NewsTableDao getNewsTableDao() {
		return newsTableDao;
	}
	public void setNewsTableDao(NewsTableDao newsTableDao) {
		this.newsTableDao = newsTableDao;
	}
	public DdJlqkQchDao getDdJlqkQchDao() {
		return ddJlqkQchDao;
	}
	public void setDdJlqkQchDao(DdJlqkQchDao ddJlqkQchDao) {
		this.ddJlqkQchDao = ddJlqkQchDao;
	}
	public CdCarouselimgDao getCdCarouselimgDao() {
		return cdCarouselimgDao;
	}
	public void setCdCarouselimgDao(CdCarouselimgDao cdCarouselimgDao) {
		this.cdCarouselimgDao = cdCarouselimgDao;
	}
	public CdAddressDao getCdAddressDao() {
		return cdAddressDao;
	}
	public void setCdAddressDao(CdAddressDao cdAddressDao) {
		this.cdAddressDao = cdAddressDao;
	}
	public CdUserXxxDao getCdUserXxxDao() {
		return cdUserXxxDao;
	}
	public void setCdUserXxxDao(CdUserXxxDao cdUserXxxDao) {
		this.cdUserXxxDao = cdUserXxxDao;
	}

	public APIClient getApiClient() {
		return apiClient;
	}
	public void setApiClient(APIClient apiClient) {
		this.apiClient = apiClient;
	}
	public MsgPhoneAsyncBean getMsgPhoneAsyncBean() {
		return msgPhoneAsyncBean;
	}
	public void setMsgPhoneAsyncBean(MsgPhoneAsyncBean msgPhoneAsyncBean) {
		this.msgPhoneAsyncBean = msgPhoneAsyncBean;
	}
	

	public CdShortMessageDao getCdShortMessageDao() {
		return cdShortMessageDao;
	}
	public void setCdShortMessageDao(CdShortMessageDao cdShortMessageDao) {
		this.cdShortMessageDao = cdShortMessageDao;
	}

	public CdMessageDao getCdMessageDao() {
		return cdMessageDao;
	}

	public void setCdMessageDao(CdMessageDao cdMessageDao) {
		this.cdMessageDao = cdMessageDao;
	}

	public AppAgreementDao getAppAgreementDao() {
		return appAgreementDao;
	}

	public void setAppAgreementDao(AppAgreementDao appAgreementDao) {
		this.appAgreementDao = appAgreementDao;
	}

	public AppBloodgluDao getAppBloodgluDao() {
		return appBloodgluDao;
	}

	public void setAppBloodgluDao(AppBloodgluDao appBloodgluDao) {
		this.appBloodgluDao = appBloodgluDao;
	}

	public AppBloodpressureDao getAppBloodpressureDao() {
		return appBloodpressureDao;
	}

	public void setAppBloodpressureDao(AppBloodpressureDao appBloodpressureDao) {
		this.appBloodpressureDao = appBloodpressureDao;
	}

	public AppDrPatientKeyDao getAppDrPatientKeyDao() {
		return appDrPatientKeyDao;
	}

	public void setAppDrPatientKeyDao(AppDrPatientKeyDao appDrPatientKeyDao) {
		this.appDrPatientKeyDao = appDrPatientKeyDao;
	}

	public AppDrUserDao getAppDrUserDao() {
		return appDrUserDao;
	}

	public void setAppDrUserDao(AppDrUserDao appDrUserDao) {
		this.appDrUserDao = appDrUserDao;
	}

	public AppHospDeptDao getAppHospDeptDao() {
		return appHospDeptDao;
	}

	public void setAppHospDeptDao(AppHospDeptDao appHospDeptDao) {
		this.appHospDeptDao = appHospDeptDao;
	}

	public AppLabelGroupDao getAppLabelGroupDao() {
		return appLabelGroupDao;
	}

	public void setAppLabelGroupDao(AppLabelGroupDao appLabelGroupDao) {
		this.appLabelGroupDao = appLabelGroupDao;
	}

	public AppLabelManageDao getAppLabelManageDao() {
		return appLabelManageDao;
	}

	public void setAppLabelManageDao(AppLabelManageDao appLabelManageDao) {
		this.appLabelManageDao = appLabelManageDao;
	}

	public AppMeunRoleDao getAppMeunRoleDao() {
		return appMeunRoleDao;
	}

	public void setAppMeunRoleDao(AppMeunRoleDao appMeunRoleDao) {
		this.appMeunRoleDao = appMeunRoleDao;
	}

	public AppMyFamilyDao getAppMyFamilyDao() {
		return appMyFamilyDao;
	}

	public void setAppMyFamilyDao(AppMyFamilyDao appMyFamilyDao) {
		this.appMyFamilyDao = appMyFamilyDao;
	}

	public AppPatientUserDao getAppPatientUserDao() {
		return appPatientUserDao;
	}

	public void setAppPatientUserDao(AppPatientUserDao appPatientUserDao) {
		this.appPatientUserDao = appPatientUserDao;
	}

	public AppResidentManageDao getAppResidentManageDao() {
		return appResidentManageDao;
	}

	public void setAppResidentManageDao(AppResidentManageDao appResidentManageDao) {
		this.appResidentManageDao = appResidentManageDao;
	}

	public AppRoleDao getAppRoleDao() {
		return appRoleDao;
	}

	public void setAppRoleDao(AppRoleDao appRoleDao) {
		this.appRoleDao = appRoleDao;
	}

	public AppSignBatchDao getAppSignBatchDao() {
		return appSignBatchDao;
	}

	public void setAppSignBatchDao(AppSignBatchDao appSignBatchDao) {
		this.appSignBatchDao = appSignBatchDao;
	}

	public AppSignformDao getAppSignformDao() {
		return appSignformDao;
	}

	public void setAppSignformDao(AppSignformDao appSignformDao) {
		this.appSignformDao = appSignformDao;
	}

	public AppTeamDao getAppTeamDao() {
		return appTeamDao;
	}

	public void setAppTeamDao(AppTeamDao appTeamDao) {
		this.appTeamDao = appTeamDao;
	}

	public AppTeamMemberDao getAppTeamMemberDao() {
		return appTeamMemberDao;
	}

	public void setAppTeamMemberDao(AppTeamMemberDao appTeamMemberDao) {
		this.appTeamMemberDao = appTeamMemberDao;
	}

	public AppUserBloodgluDao getAppUserBloodgluDao() {
		return appUserBloodgluDao;
	}

	public void setAppUserBloodgluDao(AppUserBloodgluDao appUserBloodgluDao) {
		this.appUserBloodgluDao = appUserBloodgluDao;
	}

	public AppUserBloodpressureDao getAppUserBloodpressureDao() {
		return appUserBloodpressureDao;
	}

	public void setAppUserBloodpressureDao(AppUserBloodpressureDao appUserBloodpressureDao) {
		this.appUserBloodpressureDao = appUserBloodpressureDao;
	}

	public AppWorkdaySettingDao getAppWorkdaySettingDao() {
		return appWorkdaySettingDao;
	}

	public void setAppWorkdaySettingDao(AppWorkdaySettingDao appWorkdaySettingDao) {
		this.appWorkdaySettingDao = appWorkdaySettingDao;
	}

	public AppDeviceDao getAppDeviceDao() {
		return appDeviceDao;
	}

	public void setAppDeviceDao(AppDeviceDao appDeviceDao) {
		this.appDeviceDao = appDeviceDao;
	}

	public AppNoticeDao getAppNoticeDao() {
		return appNoticeDao;
	}

	public void setAppNoticeDao(AppNoticeDao appNoticeDao) {
		this.appNoticeDao = appNoticeDao;
	}

	public AppHealthGuideDao getAppHealthGuideDao() {
		return appHealthGuideDao;
	}

	public void setAppHealthGuideDao(AppHealthGuideDao appHealthGuideDao) {
		this.appHealthGuideDao = appHealthGuideDao;
	}

	public AppGuideTemplateDao getAppGuideTemplateDao() {
		return appGuideTemplateDao;
	}

	public void setAppGuideTemplateDao(AppGuideTemplateDao appGuideTemplateDao) {
		this.appGuideTemplateDao = appGuideTemplateDao;
	}

	public AppUserHealthEDDao getAppUserHealthEDDao() {
		return appUserHealthEDDao;
	}

	public void setAppUserHealthEDDao(AppUserHealthEDDao appUserHealthEDDao) {
		this.appUserHealthEDDao = appUserHealthEDDao;
	}

	public AppFollowPlanDao getAppFollowPlanDao() {
		return appFollowPlanDao;
	}

	public void setAppFollowPlanDao(AppFollowPlanDao appFollowPlanDao) {
		this.appFollowPlanDao = appFollowPlanDao;
	}

	public AppWarningSettingDao getAppWarningSettingDao() {
		return appWarningSettingDao;
	}

	public void setAppWarningSettingDao(AppWarningSettingDao appWarningSettingDao) {
		this.appWarningSettingDao = appWarningSettingDao;
	}

	public AppDrugDao getAppDrugDao() {
		return appDrugDao;
	}

	public void setAppDrugDao(AppDrugDao appDrugDao) {
		this.appDrugDao = appDrugDao;
	}

	public AppMsgCodeDao getAppMsgCodeDao() {
		return appMsgCodeDao;
	}

	public void setAppMsgCodeDao(AppMsgCodeDao appMsgCodeDao) {
		this.appMsgCodeDao = appMsgCodeDao;
	}

	public AppDrugGuideDao getAppDrugGuideDao() {
		return appDrugGuideDao;
	}

	public void setAppDrugGuideDao(AppDrugGuideDao appDrugGuideDao) {
		this.appDrugGuideDao = appDrugGuideDao;
	}

	public AppHealthEnshrineDao getAppHealthEnshrineDao() {
		return appHealthEnshrineDao;
	}

	public void setAppHealthEnshrineDao(AppHealthEnshrineDao appHealthEnshrineDao) {
		this.appHealthEnshrineDao = appHealthEnshrineDao;
	}


	public AppChildHealthPlanDao getAppChildHealthPlanDao() {
		return appChildHealthPlanDao;
	}

	public void setAppChildHealthPlanDao(AppChildHealthPlanDao appChildHealthPlanDao) {
		this.appChildHealthPlanDao = appChildHealthPlanDao;
	}

	public AppConsultDao getAppConsultDao() {
		return appConsultDao;
	}

	public void setAppConsultDao(AppConsultDao appConsultDao) {
		this.appConsultDao = appConsultDao;
	}

	public AppPregnantPlanDao getAppPregnantPlanDao() {
		return appPregnantPlanDao;
	}

	public void setAppPregnantPlanDao(AppPregnantPlanDao appPregnantPlanDao) {
		this.appPregnantPlanDao = appPregnantPlanDao;
	}

	public CdAddressPeopleDao getCdAddressPeopleDao() {
		return cdAddressPeopleDao;
	}

	public void setCdAddressPeopleDao(CdAddressPeopleDao cdAddressPeopleDao) {
		this.cdAddressPeopleDao = cdAddressPeopleDao;
	}

	public AppResidentAnalysisDao getAppResidentAnalysisDao() {
		return appResidentAnalysisDao;
	}

	public void setAppResidentAnalysisDao(AppResidentAnalysisDao appResidentAnalysisDao) {
		this.appResidentAnalysisDao = appResidentAnalysisDao;
	}

	public AppSignAnalysisDao getAppSignAnalysisDao() {
		return appSignAnalysisDao;
	}

	public void setAppSignAnalysisDao(AppSignAnalysisDao appSignAnalysisDao) {
		this.appSignAnalysisDao = appSignAnalysisDao;
	}

	public AppPerFormanceDao getAppPerFormanceDao() {
		return appPerFormanceDao;
	}

	public void setAppPerFormanceDao(AppPerFormanceDao appPerFormanceDao) {
		this.appPerFormanceDao = appPerFormanceDao;
	}

	public AppWorkingPlanDao getAppWorkingPlanDao() {
		return appWorkingPlanDao;
	}

	public void setAppWorkingPlanDao(AppWorkingPlanDao appWorkingPlanDao) {
		this.appWorkingPlanDao = appWorkingPlanDao;
	}

	public AppSeekHelpDao getAppSeekHelpDao() {
		return appSeekHelpDao;
	}

	public void setAppSeekHelpDao(AppSeekHelpDao appSeekHelpDao) {
		this.appSeekHelpDao = appSeekHelpDao;
	}

	public AppRefuseSignDao getAppRefuseSignDao() {
		return appRefuseSignDao;
	}

	public void setAppRefuseSignDao(AppRefuseSignDao appRefuseSignDao) {
		this.appRefuseSignDao = appRefuseSignDao;
	}

	public AppHealthMeddleDao getAppHealthMeddleDao() {
		return appHealthMeddleDao;
	}

	public void setAppHealthMeddleDao(AppHealthMeddleDao appHealthMeddleDao) {
		this.appHealthMeddleDao = appHealthMeddleDao;
	}

	public SecurityCardAsyncBean getSecurityCardAsyncBean() {
		return securityCardAsyncBean;
	}

	public void setSecurityCardAsyncBean(SecurityCardAsyncBean securityCardAsyncBean) {
		this.securityCardAsyncBean = securityCardAsyncBean;
	}

	public AppHomeCareSettingDao getAppHomeCareSettingDao() {
		return appHomeCareSettingDao;
	}

	public void setAppHomeCareSettingDao(AppHomeCareSettingDao appHomeCareSettingDao) {
		this.appHomeCareSettingDao = appHomeCareSettingDao;
	}

	public AppHospExtendDao getAppHospExtendDao() {
		return appHospExtendDao;
	}

	public void setAppHospExtendDao(AppHospExtendDao appHospExtendDao) {
		this.appHospExtendDao = appHospExtendDao;
	}

	public AppSystemVsersionDao getAppSystemVsersionDao() {
		return appSystemVsersionDao;
	}

	public void setAppSystemVsersionDao(AppSystemVsersionDao appSystemVsersionDao) {
		this.appSystemVsersionDao = appSystemVsersionDao;
	}

	public AppCityAreaDao getAppCityAreaDao() {
		return appCityAreaDao;
	}

	public void setAppCityAreaDao(AppCityAreaDao appCityAreaDao) {
		this.appCityAreaDao = appCityAreaDao;
	}

	public AppRankAnalysisDao getAppRankAnalysisDao() {
		return appRankAnalysisDao;
	}

	public void setAppRankAnalysisDao(AppRankAnalysisDao appRankAnalysisDao) {
		this.appRankAnalysisDao = appRankAnalysisDao;
	}

	public AppStatisticalAnalysisDao getAppStatisticalAnalysisDao() {
		return appStatisticalAnalysisDao;
	}

	public void setAppStatisticalAnalysisDao(AppStatisticalAnalysisDao appStatisticalAnalysisDao) {
		this.appStatisticalAnalysisDao = appStatisticalAnalysisDao;
	}

	public AppWorkPlanDao getAppWorkPlanDao() {
		return appWorkPlanDao;
	}

	public void setAppWorkPlanDao(AppWorkPlanDao appWorkPlanDao) {
		this.appWorkPlanDao = appWorkPlanDao;
	}

	public AppServeSettingDao getAppServeSettingDao() {
		return appServeSettingDao;
	}

	public void setAppServeSettingDao(AppServeSettingDao appServeSettingDao) {
		this.appServeSettingDao = appServeSettingDao;
	}

	public AppChildInoculationPlanDao getAppChildInoculationPlanDao() {
		return appChildInoculationPlanDao;
	}

	public void setAppChildInoculationPlanDao(AppChildInoculationPlanDao appChildInoculationPlanDao) {
		this.appChildInoculationPlanDao = appChildInoculationPlanDao;
	}

	public AppDrEvaluationDao getAppDrEvaluationDao() {
		return appDrEvaluationDao;
	}

	public void setAppDrEvaluationDao(AppDrEvaluationDao appDrEvaluationDao) {
		this.appDrEvaluationDao = appDrEvaluationDao;
	}

	public AppNewFollowPlanDao getAppNewFollowPlanDao() {
		return appNewFollowPlanDao;
	}

	public void setAppNewFollowPlanDao(AppNewFollowPlanDao appNewFollowPlanDao) {
		this.appNewFollowPlanDao = appNewFollowPlanDao;
	}

	public AppOrderDao getAppOrderDao() {
		return appOrderDao;
	}

	public void setAppOrderDao(AppOrderDao appOrderDao) {
		this.appOrderDao = appOrderDao;
	}

	public UniformPayMentBean getUniformPayMentBean() {
		return uniformPayMentBean;
	}

	public void setUniformPayMentBean(UniformPayMentBean uniformPayMentBean) {
		this.uniformPayMentBean = uniformPayMentBean;
	}

	public AppSaveFollowTableDao getAppSaveFollowTableDao() {
		return appSaveFollowTableDao;
	}

	public void setAppSaveFollowTableDao(AppSaveFollowTableDao appSaveFollowTableDao) {
		this.appSaveFollowTableDao = appSaveFollowTableDao;
	}

	public AppSignSettingDao getAppSignSettingDao() {
		return appSignSettingDao;
	}

	public void setAppSignSettingDao(AppSignSettingDao appSignSettingDao) {
		this.appSignSettingDao = appSignSettingDao;
	}

	public AppHealthDataDao getAppHealthDataDao() {
		return appHealthDataDao;
	}

	public void setAppHealthDataDao(AppHealthDataDao appHealthDataDao) {
		this.appHealthDataDao = appHealthDataDao;
	}

	public AppMenuDao getAppMenuDao() {
		return appMenuDao;
	}

	public void setAppMenuDao(AppMenuDao appMenuDao) {
		this.appMenuDao = appMenuDao;
	}

	public AppModuleRoleDao getAppModuleRoleDao() {
		return appModuleRoleDao;
	}

	public void setAppModuleRoleDao(AppModuleRoleDao appModuleRoleDao) {
		this.appModuleRoleDao = appModuleRoleDao;
	}

	public AppModuleRoleSonDao getAppModuleRoleSonDao() {
		return appModuleRoleSonDao;
	}

	public void setAppModuleRoleSonDao(AppModuleRoleSonDao appModuleRoleSonDao) {
		this.appModuleRoleSonDao = appModuleRoleSonDao;
	}

	public AppTcmSyndromeDao getAppTcmSyndromeDao() {
		return appTcmSyndromeDao;
	}

	public void setAppTcmSyndromeDao(AppTcmSyndromeDao appTcmSyndromeDao) {
		this.appTcmSyndromeDao = appTcmSyndromeDao;
	}

	public AppServePackageDao getAppServePackageDao() {
		return appServePackageDao;
	}

	public void setAppServePackageDao(AppServePackageDao appServePackageDao) {
		this.appServePackageDao = appServePackageDao;
	}

	public AppServeObjectDao getAppServeObjectDao() {
		return appServeObjectDao;
	}

	public void setAppServeObjectDao(AppServeObjectDao appServeObjectDao) {
		this.appServeObjectDao = appServeObjectDao;
	}

	public AppServeGroupDao getAppServeGroupDao() {
		return appServeGroupDao;
	}

	public void setAppServeGroupDao(AppServeGroupDao appServeGroupDao) {
		this.appServeGroupDao = appServeGroupDao;
	}

	public AppSecurtySettingDao getAppSecurtySettingDao() {
		return appSecurtySettingDao;
	}

	public void setAppSecurtySettingDao(AppSecurtySettingDao appSecurtySettingDao) {
		this.appSecurtySettingDao = appSecurtySettingDao;
	}

	public AppServeSetmealDao getAppServeSetmealDao() {
		return appServeSetmealDao;
	}

	public void setAppServeSetmealDao(AppServeSetmealDao appServeSetmealDao) {
		this.appServeSetmealDao = appServeSetmealDao;
	}

	public AppServeEconDao getAppServeEconDao() {
		return appServeEconDao;
	}

	public void setAppServeEconDao(AppServeEconDao appServeEconDao) {
		this.appServeEconDao = appServeEconDao;
	}

	public AppServeGovDao getAppServeGovDao() {
		return appServeGovDao;
	}

	public void setAppServeGovDao(AppServeGovDao appServeGovDao) {
		this.appServeGovDao = appServeGovDao;
	}

	public AppWorkTypeDao getAppWorkTypeDao() {
		return appWorkTypeDao;
	}

	public void setAppWorkTypeDao(AppWorkTypeDao appWorkTypeDao) {
		this.appWorkTypeDao = appWorkTypeDao;
	}

	public AppSignServeTeamDao getAppSignServeTeamDao() {
		return appSignServeTeamDao;
	}

	public void setAppSignServeTeamDao(AppSignServeTeamDao appSignServeTeamDao) {
		this.appSignServeTeamDao = appSignServeTeamDao;
	}

	public AppMunicipalAuthorityDao getAppMunicipalAuthorityDao() {
		return appMunicipalAuthorityDao;
	}

	public void setAppMunicipalAuthorityDao(AppMunicipalAuthorityDao appMunicipalAuthorityDao) {
		this.appMunicipalAuthorityDao = appMunicipalAuthorityDao;
	}

	public AppMyServiceMenuDao getAppMyServiceMenuDao() {
		return appMyServiceMenuDao;
	}

	public void setAppMyServiceMenuDao(AppMyServiceMenuDao appMyServiceMenuDao) {
		this.appMyServiceMenuDao = appMyServiceMenuDao;
	}

	public AppEconAndGovDao getAppEconAndGovDao() {
		return appEconAndGovDao;
	}

	public void setAppEconAndGovDao(AppEconAndGovDao appEconAndGovDao) {
		this.appEconAndGovDao = appEconAndGovDao;
	}

	public AppServeTabDao getAppServeTabDao() {
		return appServeTabDao;
	}

	public void setAppServeTabDao(AppServeTabDao appServeTabDao) {
		this.appServeTabDao = appServeTabDao;
	}

	public AppTcmGuideDao getAppTcmGuideDao() {
		return appTcmGuideDao;
	}

	public void setAppTcmGuideDao(AppTcmGuideDao appTcmGuideDao) {
		this.appTcmGuideDao = appTcmGuideDao;
	}

	public AppManageCountDao getAppManageCountDao() {
		return appManageCountDao;
	}

	public void setAppManageCountDao(AppManageCountDao appManageCountDao) {
		this.appManageCountDao = appManageCountDao;
	}

	public SchedulingAsyncBean getSchedulingAsyncBean() {
		return schedulingAsyncBean;
	}

	public void setSchedulingAsyncBean(SchedulingAsyncBean schedulingAsyncBean) {
		this.schedulingAsyncBean = schedulingAsyncBean;
	}

	public AppServeRoleDao getAppServeRoleDao() {
		return appServeRoleDao;
	}

	public void setAppServeRoleDao(AppServeRoleDao appServeRoleDao) {
		this.appServeRoleDao = appServeRoleDao;
	}

	public AppSignsWarningSettingDao getAppSignsWarningSettingDao() {
		return appSignsWarningSettingDao;
	}

	public void setAppSignsWarningSettingDao(AppSignsWarningSettingDao appSignsWarningSettingDao) {
		this.appSignsWarningSettingDao = appSignsWarningSettingDao;
	}

	public AppPerformanceStatisticsDao getAppPerformanceStatisticsDao() {
		return appPerformanceStatisticsDao;
	}

	public void setAppPerformanceStatisticsDao(AppPerformanceStatisticsDao appPerformanceStatisticsDao) {
		this.appPerformanceStatisticsDao = appPerformanceStatisticsDao;
	}

	public SysLogDeletDo getSysLogDeletDo() {
		return sysLogDeletDo;
	}

	public void setSysLogDeletDo(SysLogDeletDo sysLogDeletDo) {
		this.sysLogDeletDo = sysLogDeletDo;
	}

	public AppSignControlDao getAppSignControlDao() {
		return appSignControlDao;
	}

	public void setAppSignControlDao(AppSignControlDao appSignControlDao) {
		this.appSignControlDao = appSignControlDao;
	}

	public AppPerformanceRegularFollowupDao getAppPerformanceRegularFollowupDao() {
		return appPerformanceRegularFollowupDao;
	}

	public void setAppPerformanceRegularFollowupDao(AppPerformanceRegularFollowupDao appPerformanceRegularFollowupDao) {
		this.appPerformanceRegularFollowupDao = appPerformanceRegularFollowupDao;
	}

	public AppReferralTableDao getAppRefarralTableDao() {
		return appRefarralTableDao;
	}

	public void setAppRefarralTableDao(AppReferralTableDao appRefarralTableDao) {
		this.appRefarralTableDao = appRefarralTableDao;
	}

	public WebSignAnalysisDao getWebSignAnalysisDao() {
		return webSignAnalysisDao;
	}

	public void setWebSignAnalysisDao(WebSignAnalysisDao webSignAnalysisDao) {
		this.webSignAnalysisDao = webSignAnalysisDao;
	}

	public WebResidentAnalysisDao getWebResidentAnalysisDao() {
		return webResidentAnalysisDao;
	}

	public void setWebResidentAnalysisDao(WebResidentAnalysisDao webResidentAnalysisDao) {
		this.webResidentAnalysisDao = webResidentAnalysisDao;
	}

	public AppHospitalDepartmentsDao getAppHospitalDepartmentsDao() {
		return appHospitalDepartmentsDao;
	}

	public void setAppHospitalDepartmentsDao(AppHospitalDepartmentsDao appHospitalDepartmentsDao) {
		this.appHospitalDepartmentsDao = appHospitalDepartmentsDao;
	}

	public AppSignMotoeDao getAppSignMotoeDao() {
		return appSignMotoeDao;
	}

	public void setAppSignMotoeDao(AppSignMotoeDao appSignMotoeDao) {
		this.appSignMotoeDao = appSignMotoeDao;
	}

	public AppExerciseCountDao getAppExerciseCountDao() {
		return appExerciseCountDao;
	}

	public void setAppExerciseCountDao(AppExerciseCountDao appExerciseCountDao) {
		this.appExerciseCountDao = appExerciseCountDao;
	}

	public AppOutpatientCountDao getAppOutpatientCountDao() {
		return appOutpatientCountDao;
	}

	public void setAppOutpatientCountDao(AppOutpatientCountDao appOutpatientCountDao) {
		this.appOutpatientCountDao = appOutpatientCountDao;
	}

	public AppSignSetDao getAppSignSetDao() {
		return appSignSetDao;
	}

	public void setAppSignSetDao(AppSignSetDao appSignSetDao) {
		this.appSignSetDao = appSignSetDao;
	}

	public AppOutpatientDao getAppOutpatientDao() {
		return appOutpatientDao;
	}

	public void setAppOutpatientDao(AppOutpatientDao appOutpatientDao) {
		this.appOutpatientDao = appOutpatientDao;
	}

	public com.ylz.bizDo.web.dao.WebSignUpDao getWebSignUpDao() {
		return WebSignUpDao;
	}

	public void setWebSignUpDao(com.ylz.bizDo.web.dao.WebSignUpDao webSignUpDao) {
		WebSignUpDao = webSignUpDao;
	}

	public AppHealthFileDao getAppHealthFileDao() {
		return appHealthFileDao;
	}

	public void setAppHealthFileDao(AppHealthFileDao appHealthFileDao) {
		this.appHealthFileDao = appHealthFileDao;
	}

	public AppBasicDataDao getAppBasicDataDao() {
		return appBasicDataDao;
	}

	public void setAppBasicDataDao(AppBasicDataDao appBasicDataDao) {
		this.appBasicDataDao = appBasicDataDao;
	}

	public AppBasicDataTempDao getAppBasicDataTempDao() {
		return appBasicDataTempDao;
	}

	public void setAppBasicDataTempDao(AppBasicDataTempDao appBasicDataTempDao) {
		this.appBasicDataTempDao = appBasicDataTempDao;
	}

	public AppPerformanceTableDao getAppPerformanceTableDao() {
		return appPerformanceTableDao;
	}

	public void setAppPerformanceTableDao(AppPerformanceTableDao appPerformanceTableDao) {
		this.appPerformanceTableDao = appPerformanceTableDao;
	}

	public AppPerformeRankCountDao getAppPerformeRankCountDao() {
		return appPerformeRankCountDao;
	}

	public void setAppPerformeRankCountDao(AppPerformeRankCountDao appPerformeRankCountDao) {
		this.appPerformeRankCountDao = appPerformeRankCountDao;
	}

	public AppSubmissionRepetitionDao getAppSubmissionRepetitionDao() {
		return appSubmissionRepetitionDao;
	}

	public void setAppSubmissionRepetitionDao(AppSubmissionRepetitionDao appSubmissionRepetitionDao) {
		this.appSubmissionRepetitionDao = appSubmissionRepetitionDao;
	}

	public AssessmentContentDao getAssessmentContentDao() {
		return assessmentContentDao;
	}

	public void setAssessmentContentDao(AssessmentContentDao assessmentContentDao) {
		this.assessmentContentDao = assessmentContentDao;
	}

	public AssessmentDao getAssessmentDao() {
		return assessmentDao;
	}

	public void setAssessmentDao(AssessmentDao assessmentDao) {
		this.assessmentDao = assessmentDao;
	}

	public AssessmentDetailDao getAssessmentDetailDao() {
		return assessmentDetailDao;
	}

	public void setAssessmentDetailDao(AssessmentDetailDao assessmentDetailDao) {
		this.assessmentDetailDao = assessmentDetailDao;
	}

    public AssessmentTeamShareDao getAssessmentTeamShareDao() {
        return assessmentTeamShareDao;
    }

    public void setAssessmentTeamShareDao(AssessmentTeamShareDao assessmentTeamShareDao) {
        this.assessmentTeamShareDao = assessmentTeamShareDao;
    }

    public AssessLogDao getAssessLogDao() {
		return assessLogDao;
	}

	public void setAssessLogDao(AssessLogDao assessLogDao) {
		this.assessLogDao = assessLogDao;
	}

	public ReviewLogDao getReviewLogDao() {
		return reviewLogDao;
	}

	public void setReviewLogDao(ReviewLogDao reviewLogDao) {
		this.reviewLogDao = reviewLogDao;
	}

	public AppSignSubtableDao getAppSignSubtableDao() {
		return appSignSubtableDao;
	}

	public void setAppSignSubtableDao(AppSignSubtableDao appSignSubtableDao) {
		this.appSignSubtableDao = appSignSubtableDao;
	}

	public AppArchivingCardPeopeDao getAppArchivingCardPeopeDao() {
		return appArchivingCardPeopeDao;
	}

	public void setAppArchivingCardPeopeDao(AppArchivingCardPeopeDao appArchivingCardPeopeDao) {
		this.appArchivingCardPeopeDao = appArchivingCardPeopeDao;
	}

	public SysLogBusinessDo getSysLogBusinessDo() {
		return sysLogBusinessDo;
	}

	public void setSysLogBusinessDo(SysLogBusinessDo sysLogBusinessDo) {
		this.sysLogBusinessDo = sysLogBusinessDo;
	}

	public AppMarkingLogDao getAppMarkingLogDao() {
		return appMarkingLogDao;
	}

	public void setAppMarkingLogDao(AppMarkingLogDao appMarkingLogDao) {
		this.appMarkingLogDao = appMarkingLogDao;
	}

	public AppPossBindingDao getAppPossBindingDao() {
		return appPossBindingDao;
	}

	public void setAppPossBindingDao(AppPossBindingDao appPossBindingDao) {
		this.appPossBindingDao = appPossBindingDao;
	}

	public AppExerciseCountNewDao getAppExerciseCountNewDao() {
		return appExerciseCountNewDao;
	}

	public void setAppExerciseCountNewDao(AppExerciseCountNewDao appExerciseCountNewDao) {
		this.appExerciseCountNewDao = appExerciseCountNewDao;
	}

	public AppArchivingCheckDao getAppArchivingCheckDao() {
		return appArchivingCheckDao;
	}

	public void setAppArchivingCheckDao(AppArchivingCheckDao appArchivingCheckDao) {
		this.appArchivingCheckDao = appArchivingCheckDao;
	}

	public AppHepVideoDao getAppHepVideoDao() {
		return appHepVideoDao;
	}

	public void setAppHepVideoDao(AppHepVideoDao appHepVideoDao) {
		this.appHepVideoDao = appHepVideoDao;
	}

	public AppMsgDao getAppMsgDao() {
		return appMsgDao;
	}

	public void setAppMsgDao(AppMsgDao appMsgDao) {
		this.appMsgDao = appMsgDao;
	}

	public AppSignSettlementDao getAppSignSettlementDao() {
		return appSignSettlementDao;
	}

	public void setAppSignSettlementDao(AppSignSettlementDao appSignSettlementDao) {
		this.appSignSettlementDao = appSignSettlementDao;
	}

	public AppLabelArchivingDao getAppLabelArchivingDao() {
		return appLabelArchivingDao;
	}

	public void setAppLabelArchivingDao(AppLabelArchivingDao appLabelArchivingDao) {
		this.appLabelArchivingDao = appLabelArchivingDao;
	}
}
