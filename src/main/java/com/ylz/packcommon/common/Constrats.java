package com.ylz.packcommon.common;


public class Constrats {
	public static final Integer DEAAULT_NO = 1;
	public static final String KEY_BUZOU_ONE="keytool -genkey -keyalg RSA -dname "+'"'+"cn=%1$s,ou=zks,o=none,l=china,st=beijing,c=cn"+'"'+" -alias %2$s -storetype PKCS12 -keypass %3$s -keystore %4$s.p12 -storepass %5$s -validity %5$s";
	public static final String KEY_BUZOU_TWO="keytool -export -alias %1$s -file %2$s.cer -keystore %3$s.p12 -storepass %4$s -storetype PKCS12 -rfc";
	public static final String KEY_BUZOU_THREE="keytool -import -v -alias %1$s -file %2$s.cer -keystore %3$s.jks -storepass password";
	public static final String KEY_BUZOU_Y="cmd /c echo Y | ";

	//流程描述
	public static final String FLOW_ADD = "单位送检";
	public static final String FLOW_MODIFY="修改";
	public static final String MSG_LOGIN="短信验证码为:%1$s,有效期两分钟!";
	public static final String MSG_KEY="Ca证书下载密码:%1$s,请谨慎保管!";
	public static final String MSG_WEBSITE ="xmcdc";

	//餐前血糖下限
	public static final double TZ_KFXTXX=3.9;
	//餐前血糖中限
	public static final double TZ_KFXTZX=6.1;
	//餐前血糖上限
	public static final double TZ_KFXTSX=7;
	//餐后血糖下限
	public static final double TZ_CHXTXX=3.9;
	//餐后血糖中限
	public static final double TZ_CHXTZX=7.8;
	//餐后血糖上限
	public static final double TZ_CHXTSX=11.1;
	//舒张压下限
	public static final int TZ_SZYXX=60;
	//舒张压上限
	public static final int TZ_SZYSX=90;
	//收缩压下限
	public static final int TZ_SSYXX=90;
	//收缩压上限
	public static final int TZ_SSYSX=140;

	//健康监测预警消息
	public static final String NOTE_GLU_HIGH="您在%1$s测量的血糖值(%2$s)高于预警值（%3$s），请注意";
	public static final String NOTE_GLU_LOW="您在%1$s测量的血糖值(%2$s)低于预警值（%3$s），请注意";
	public static final String DOC_GLU_HIGH="患者%1$s在%2$s测量的血糖值(%3$s)高于预警值（%4$s），请注意";
	public static final String DOC_GLU_LOW="患者%1$s在%2$s测量的血糖值(%3$s)低于预警值（%4$s），请注意";
	public static final String NOTE_PRESSURE_ONE="您在%1$s测量的收缩压(%2$s)高于预警值（%3$s），请注意";
	public static final String NOTE_PRESSURE_TWO="您在%1$s测量的收缩压(%2$s)低于预警值（%3$s），请注意";
	public static final String NOTE_PRESSURE_THREE="您在%1$s测量的舒张压(%2$s)高于预警值（%3$s），请注意";
	public static final String NOTE_PRESSURE_FOUR="您在%1$s测量的舒张压(%2$s)低于预警值（%3$s），请注意";
	public static final String DOC_PRESSURE_ONE="患者%1$s在%2$s测量的收缩压(%3$s)高于预警值（%4$s），请注意";
	public static final String DOC_PRESSURE_TWO="患者%1$s在%2$s测量的收缩压(%3$s)低于预警值（%4$s），请注意";
	public static final String DOC_PRESSURE_THREE="患者%1$s在%2$s测量的舒张压(%3$s)高于预警值（%4$s），请注意";
	public static final String DOC_PRESSURE_FOUR="患者%1$s在%2$s测量的舒张压(%3$s)低于预警值（%4$s），请注意";
	/**
	 *
		 appChangePwd 密码修改
		 appChangeTel 修改手机
		 appChangeInfo 修改个人信息
		 apForgetPwd app忘记密码
		 appWorksheet 工作日设置
		 setSigns 医生设置所管居民未更新体征数据的预警时间
		 fsTzxxToPatient 发送消息给患者
		 dialBack  回拨
		 delTzSet 删除体征设置
		 submitChange 提交变更
		 sureChange 同意或拒绝变更
		 saveConsultRecord 保存咨询记录
		 appAddDevice 添加设备
		 appModifyPressure 修改血压计
		 appModifyGlu 修改血糖仪
		 appAddDrug 添加药品
		 appDrugGuide 添加用药指导
		 appAlertAgain 再次提醒
		 appAddPersonDrug 添加药品到个人药库
		 appModifyDrug 修改药品
		 appDeleteDrug 删除药品
		 enshrineHD 医生收藏健康教育模板接口
		 deleteED 删除健康教育模板
		 delGuide 删除健康指导模板
		 modifyHealthED 修改健康教育模板
		 modifyHealthGuide 修改健康指导模板
		 fsHealthRole 根据权限发送健康教育
		 fsHealthToAll 一键发送健康教育
		 appAddOldCare 新增居家养老管理单
		 appModifyOldCare 修改居家养老管理单
		 appAddManageCare 添加居家养老频率
		 appModifyManageCare 修改居家养老频率
		 appDeleteManageCare 删除居家养老频率
		 addFollowPlan 添加随访计划
		 callFollowPlan 延期随访计划
		 startFollowPlan 开始随访计划
		 subReferral 提交转诊信息
		 auditReferral 同意、拒绝、终止、回转转诊请求
		 followVisit 随访消息提醒
		 followNow 当天临时随访提醒
		 drugRunOutToDr 药品存量预警
		 agreeSignForm 同意签约接口
		 refuseSignForm 拒接签约接口
		 surrenderOkSignForm 待解约同意接口
		 surrenderNoSignForm 待解约拒绝接口
		 guideTemplate 新增指导/干预模板
		 addHealthGuide 给增患者发指导
		 saveHealthEducation 医生新增健康教育模板NEWS_TABLE表
		 appSignRegisterTemp 医生代理注册(临时)
		 appSignRegister 医生代理注册
		 drAgreeSignForm 医生代签约
		 appModifyGroup 修改居民分组
		 appRefuseSign 添加拒管居民
		 appModifyRefuseSign 修改拒管居民
		 appDeleteRefuseSign 删除拒管居民
		 saveMeddle 发送健康指导给患者
		 modifySignServiceBColor 修改居家养老颜色
		 saveSignSet 市权限签约管理设置
		 signDelPatient 删除居民签约单
		 saveSignSerSet 保存签约管理设置
		 renewalReminder 医生发送续签
		 modify 修改指标
		 addTeam 添加团队
		 addTeamMem 添加团队成员
		 delTeam 删除团队
		 delTeamMem 删除团队成员
		 modifyTeam 修改团队接口
		 modifyTeamMem 修改团队成员信息
		 appBindBpDev 修改团队成员信息
		 appBindGluDev 绑定血糖仪
		 appDeldevice 设备删除
		 appFollowWarnDay 随访当天预警设置
		 appFollowWarn 随访预警设置
		 appAddFollowPlan 新增随访计划
		 saveHdBloo 保存高血压随访记录
		 saveDiabetes 保存糖尿病随访记录
		 saveNewChild 保存新生儿随访记录
		 saveTcm 保存中医体质辨识
		 fsGuide 发送中医药保健指导
		 addTcmGuide 添加中医药体质辨识指导
		 modifyGuide 修改中医药保健指导
		 saveTcmGuide 添加中医药体质指导
		 addTcmGuide1 添加中医药体质指导
	 */
	public static final String ACT_LIST="\tappChangePwd,\n" +
			"\tappChangeTel,\n" +
			"\tappChangeInfo,\n" +
			"\tapForgetPwd,\n" +
			"\tappWorksheet,\n" +
			"\tsetSigns,\n" +
			"\tfsTzxxToPatient,\n" +
			"\tdialBack,\n" +
			"\tdelTzSet,\n" +
			"\tsubmitChange,\n" +
			"\tsureChange,\n" +
			"\tsaveConsultRecord,\n" +
			"\tappAddDevice,\n" +
			"\tappModifyPressure,\n" +
			"\tappModifyGlu,\n" +
			"\tappAddDrug,\n" +
			"\tappDrugGuide,\n" +
			"\tappAlertAgain,\n" +
			"\tappAddPersonDrug,\n" +
			"\tappModifyDrug,\n" +
			"\tappDeleteDrug,\n" +
			"\tenshrineHD,\n" +
			"\tdeleteED,\n" +
			"\tdelGuide,\n" +
			"\tmodifyHealthED,\n" +
			"\tmodifyHealthGuide,\n" +
			"\tfsHealthRole,\n" +
			"\tfsHealthToAll,\n" +
			"\tappAddOldCare,\n" +
			"\tappModifyOldCare,\n" +
			"\tappAddManageCare,\n" +
			"\tappModifyManageCare,\n" +
			"\tappDeleteManageCare,\t \n" +
			"\taddFollowPlan,\n" +
			"\tcallFollowPlan,\n" +
			"\tstartFollowPlan,\n" +
			"\tsubReferral,\n" +
			"\tauditReferral,\n" +
			"\tfollowVisit,\n" +
			"\tfollowNow,\n" +
			"\tdrugRunOutToDr,\n" +
			"\tagreeSignForm,\n" +
			"\trefuseSignForm,\n" +
			"\tsurrenderOkSignForm,\n" +
			"\tsurrenderNoSignForm,\n" +
			"\tguideTemplate,\n" +
			"\taddHealthGuide,\n" +
			"\tsaveHealthEducation,\n" +
			"\tappSignRegisterTemp,\n" +
			"\tappSignRegister,\n" +
			"\tdrAgreeSignForm,\n" +
			"\tappModifyGroup,\n" +
			"\tappRefuseSign,\n" +
			"\tappModifyRefuseSign,\n" +
			"\tappDeleteRefuseSign,\n" +
			"\tsaveMeddle,\n" +
			"\tmodifySignServiceBColor,\n" +
			"\tsaveSignSet,\n" +
			"\tsignDelPatient,\n" +
			"\tsaveSignSerSet,\n" +
			"\trenewalReminder,\n" +
			"\tmodify,\n" +
			"\taddTeam,\n" +
			"\taddTeamMem,\n" +
			"\tdelTeam,\n" +
			"\tdelTeamMem,\n" +
			"\tmodifyTeam,\n" +
			"\tmodifyTeamMem,\n" +
			"\tappBindBpDev,\n" +
			"\tappBindGluDev,\n" +
			"\tappDeldevice,\n" +
			"\tappFollowWarnDay,\n" +
			"\tappFollowWarn,\n" +
			"\tappAddFollowPlan,\n" +
			"\tsaveHdBloo,\n" +
			"\tsaveDiabetes,\n" +
			"\tsaveNewChild,\n" +
			"\tsaveTcm,\n" +
			"\tfsGuide,\n" +
			"\taddTcmGuide,\n" +
			"\tmodifyGuide,\n" +
			"\tsaveTcmGuide,\n" +
			"\taddTcmGuide1";
}
