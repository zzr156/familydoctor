package com.ylz.bizDo.register.dao.impl;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppServeSetmeal;
import com.ylz.bizDo.app.po.AppSignBatch;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.register.dao.RegisterDao;
import com.ylz.bizDo.register.entity.RegisterGovEntity;
import com.ylz.bizDo.register.entity.RegisterGovTileEntity;
import com.ylz.bizDo.register.entity.RegisterTeamMemberEntity;
import com.ylz.bizDo.register.po.*;
import com.ylz.bizDo.register.vo.*;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.CopyUtils;
import com.ylz.packcommon.common.util.MyMathUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("registerDao")
@Transactional(rollbackForClassName={"Exception"})
public class RegisterDaoImpl implements RegisterDao {
    @Autowired
    private SysDao sysDao;
    /**
     * 签约的时候新增信息-费用明细主表
     * @return
     */
    @Override
    public String addRegisterInfo(RegisterSelVo registerSelVo) throws Exception{
        Calendar now = Calendar.getInstance();
        DateFormat datef = new SimpleDateFormat("yyyyMMdd");
        DateFormat times = new SimpleDateFormat("HH:mm:ss");
        String datestr =datef.format(new Date());
        String timestr =times.format(new Date());
        String time = String.valueOf(now.get(Calendar.HOUR_OF_DAY))+String.valueOf(now.get(Calendar.MINUTE))+String.valueOf(now.get(Calendar.SECOND));

        //新增病人费用主表
        PatientCost patientCost = new PatientCost();
        patientCost.setHospitalId(registerSelVo.getHospitalId());//医院ID
        patientCost.setHcCostId("0");//医保返回的单据号
        patientCost.setInnerRegisterId(registerSelVo.getId()+"_ghh");//内部挂号号
        patientCost.setPatientId(registerSelVo.getPatientId());//病人ID
        patientCost.setPatientName(registerSelVo.getName());//病人姓名
        patientCost.setTotalAmount(new BigDecimal("0"));//总金额
        patientCost.setPatientBalance(new BigDecimal("0"));//病人余额
        patientCost.setOperationDate(datestr);//操作日期
        patientCost.setOperationTime(timestr);//操作时间
        patientCost.setOperatorId(registerSelVo.getOperatorId());//操作员编号
        patientCost.setOperatorName(registerSelVo.getOperatorName());//操作员姓名
        patientCost.setOperatorDepartment(registerSelVo.getOperationDepart());//收费科室
        patientCost.setSettlementId("0"); //结算单号,0:该帐目未结算(外键、结账表结账单号）
        patientCost.setHcCenter("1");//医保中心类别（暂时只有医保：1）
        patientCost.setDataSource("web");
        sysDao.getServiceDo().add(patientCost);
        //查询批次
        AppSignBatch appSignBatch = new AppSignBatch();
        if(sysDao.getRegisterDao().fingOperatorInfo(registerSelVo.getBatchId())!=null){
            appSignBatch = sysDao.getRegisterDao().fingOperatorInfo(registerSelVo.getBatchId());
        }
        //查询补贴信息
        List<RegisterGovEntity> resList = sysDao.getRegisterDao().getPayInfo(registerSelVo.getSetMealId());
        if(resList != null && resList.size() >0) {
            List<RegisterGovTileEntity> ls = resList.get(0).getGovList();
            for (RegisterGovTileEntity list : ls) {
                //新增费用明细
                PatientCostDetail patientCostDetail = new PatientCostDetail();
                patientCostDetail.setCostDocumentsId(patientCost.getId());//单据号（外键、费用主表的单据号）
                patientCostDetail.setItemCode(list.getGovHcProjectId());//医疗收费编码或药品编码
                patientCostDetail.setItemName(list.getGovTitle());//项目名称
                patientCostDetail.setItemSpecification("");////项目规格
                patientCostDetail.setItemUnit("元");//单位
                patientCostDetail.setUnitPrice(list.getGovMoney());//单价
                patientCostDetail.setQuantity("1");//数量
                patientCostDetail.setTotalAmount(new BigDecimal(list.getGovMoney()).multiply(new BigDecimal("1")));//合计金额
                patientCostDetail.setOneself_amount(new BigDecimal("0"));//自费金额
                patientCostDetail.setBillingDate(datestr);//开单日期
                patientCostDetail.setBillingTime(timestr);//开单时间
                patientCostDetail.setWriteOffMark("Z");//Z:表示正常的没有被冲销的  +被冲销的单  -表示该记录冲销别的单
                patientCostDetail.setOrderDeptId(StringUtils.isNotBlank(appSignBatch.getBatchOperatorId()) ? appSignBatch.getBatchOperatorId() : "");//开单科室编号
                patientCostDetail.setOrderDrDepId(StringUtils.isNotBlank(appSignBatch.getBatchTeamId()) ? appSignBatch.getBatchTeamId() :"" );//开单医生科室编号
                patientCostDetail.setOrderDoctorId(StringUtils.isNotBlank(appSignBatch.getBatchOperatorId()) ? appSignBatch.getBatchOperatorId() :"");//开单医生工号
                patientCostDetail.setRemark("");//备注
                patientCostDetail.setBeWriteOffDetailId("");//冲销批准人
                patientCostDetail.setWriteOffPeople("");//冲销批准人
                patientCostDetail.setWriteOffCause("");//冲销原因
                patientCostDetail.setPackageId(registerSelVo.getSetMealId());//套餐id
                sysDao.getServiceDo().add(patientCostDetail);
            }
            //查询套餐信息
            BigDecimal total_money = new BigDecimal("0");
            String setMealIdArr[] = registerSelVo.getSetMealId().split(";");
            if (null != setMealIdArr && setMealIdArr.length > 0) {
                for (String srr : setMealIdArr) {
                    AppServeSetmeal rpiList = (AppServeSetmeal) sysDao.getServiceDo().find(AppServeSetmeal.class, srr);
                        if (null != rpiList) {
                        total_money = total_money.add(new BigDecimal(rpiList.getSersmTotalFee()));//本次收费总金额
                        /*if(!registerSelVo.getSignzfpay().equals("0")) {
                            //新增费用明细(自费)
                            PatientCostDetail patientCostDetail = new PatientCostDetail();
                            patientCostDetail.setCostDocumentsId(patientCost.getId());//单据号（外键、费用主表的单据号）
                            patientCostDetail.setItemCode("");//医疗收费编码或药品编码
                            patientCostDetail.setItemName("自费" + rpiList.getSersmName());//项目名称
                            patientCostDetail.setItemSpecification("");////项目规格
                            patientCostDetail.setItemUnit("元");//单位
                            patientCostDetail.setUnitPrice(registerSelVo.getSignzfpay());//单价
                            patientCostDetail.setQuantity("1");//数量
                            patientCostDetail.setTotalAmount(new BigDecimal(registerSelVo.getSignzfpay()));//合计金额
                            patientCostDetail.setOneself_amount(new BigDecimal(registerSelVo.getSignzfpay()));//自费金额
                            patientCostDetail.setBillingDate(datestr);//开单日期
                            patientCostDetail.setBillingTime(timestr);//开单时间
                            patientCostDetail.setWriteOffMark("Z");//Z:表示正常的没有被冲销的  +被冲销的单  -表示该记录冲销别的单
                            patientCostDetail.setOrderDeptId(StringUtils.isNotBlank(appSignBatch.getBatchOperatorId()) ? appSignBatch.getBatchOperatorId() : "");//开单科室编号
                            patientCostDetail.setOrderDrDepId(StringUtils.isNotBlank(appSignBatch.getBatchTeamId()) ? appSignBatch.getBatchTeamId() :"" );//开单医生科室编号
                            patientCostDetail.setOrderDoctorId(StringUtils.isNotBlank(appSignBatch.getBatchOperatorId()) ? appSignBatch.getBatchOperatorId() :"");//开单医生工号
                            patientCostDetail.setRemark("");//备注
                            patientCostDetail.setBeWriteOffDetailId("");//冲销批准人
                            patientCostDetail.setWriteOffPeople("");//冲销批准人
                            patientCostDetail.setWriteOffCause("");//冲销原因
                            patientCostDetail.setPackageId(registerSelVo.getSetMealId());//套餐id
                            sysDao.getServiceDo().add(patientCostDetail);
                        }*/
                    }
                }
            }
            //新增病人结账信息
            PatientSettleAccounts psa = new PatientSettleAccounts();
            psa.setPatientCostID(patientCost.getId());//病人费用表主键
            psa.setHospitalId(registerSelVo.getHospitalId());//医院、社区卫生院id
            psa.setHcDocumentBillId("0");//医保返回的单据号
            psa.setPatientId(registerSelVo.getPatientId());//病人id
            psa.setTotalAmount(total_money);//合计金额
            psa.setPerAccountPayment(new BigDecimal("0"));//个人医疗帐户支付(医保返回)
            psa.setFundPaymen(new BigDecimal("0"));//统筹基金支付(医保返回)
            psa.setOneselfPayment(new BigDecimal("0"));//自付金额(医保返回)
            psa.setPaperId("0");//打印的票据流水号
            psa.setCheckOutDate(datestr);//结帐日期
            psa.setCheckOutTime(timestr);//结帐时间
            psa.setCheckoutOperatorId(registerSelVo.getOperatorId());//结帐操作员编码
            psa.setHcCheckOutDate("");//医保结账日期
            psa.setHcCheckOutTime("");//医保结账时间
            psa.setBipAccount(new BigDecimal("0"));//商保个人账户
            psa.setCicoofund(new BigDecimal("0"));//商保统筹基金
            psa.setHecCashPayment(new BigDecimal("0"));//医保现金支付
            psa.setWriteOffCheckoutId("");//冲销结帐单号
            psa.setHcAccountBalance(new BigDecimal("0"));//医保帐户余额
            psa.setHcCenterType("1");//医保中心类别
            psa.setFundPaymentAmount(new BigDecimal("0"));//基金支付金额
            psa.setCommercialInsurancePayment(new BigDecimal("0"));//商业保险支付
            psa.setSettlementStatus("0");//0:正常结算 1：冲销或被冲销
            psa.setHcRegistrationId("0");//医保挂号流水号,由医保程序生成的挂号流水号
            psa.setRegisteredDepartmenName(registerSelVo.getOperationDepart());//挂号科室名称
            sysDao.getServiceDo().add(psa);
        }
        return "success";
    }

    /**
     * 医保登记成功后新增收费信息
     * @return
     */
    @Override
    public String addReturnRegisterInfo(PatientPayFee ppf) throws Exception{
       //新增收费信息
       PatientPayFee patientPayFee = new PatientPayFee();
       patientPayFee.setId(ppf.getId());
       patientPayFee.setHospitalId(ppf.getHospitalId());//医院内、区卫生院id
       patientPayFee.setPatientId(ppf.getPatientId());//病人id号
       patientPayFee.setPayAmount(ppf.getPayAmount());//交费金额
       patientPayFee.setPaymentMode(ppf.getPaymentMode());//支付方式
       patientPayFee.setPaymentModeName(ppf.getPaymentModeName());//支付方式名称
       patientPayFee.setPaperId(ppf.getPaperId());//打印的票据流水号
       patientPayFee.setOperationDate(ppf.getOperationDate());//操作日期
       patientPayFee.setOperationTime(ppf.getOperationTime());//操作时间
       patientPayFee.setOperatorId(ppf.getOperatorId());//操作员ID
       patientPayFee.setOperatorName(ppf.getOperatorName());//操作员员姓名
       patientPayFee.setPatientBalance(new BigDecimal("0"));//病人余额
       patientPayFee.setPaymentRemark("");//交费备注(0交预交金、1、退预交金）
       sysDao.getServiceDo().add(patientPayFee);
       return null;
    }

    /**
     * 根据内部挂号号查询主表
     * @param innerGhh
     * @return
     */
    @Override
    public PatientCost getPatientCostByGhh(String innerGhh) throws Exception{
        String sql = "select * from  APP_PATIENT_COST where PC_INNER_REGISTER_ID=:PC_INNER_REGISTER_ID";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("PC_INNER_REGISTER_ID",innerGhh);
        List<PatientCost> s = sysDao.getServiceDo().findSqlMap(sql, map, PatientCost.class);
        if (s != null && !s.isEmpty()) {
            return s.get(0);
        }
        return null;
    }

    //居民签约信息查询
    @Override
    public List<RegisterSelVo> findSignXxWeb(AppCommQvo qvo) throws Exception {
        HashMap map = new HashMap();
        StringBuffer sql = new StringBuffer();
        sql.append("  select ");
        sql.append("  a.ID id, c.ID patientId, a.SIGN_TEAM_ID teamId, a.SIGN_BATCH_ID batchId, a.SIGN_NUM signNum, c.PATIENT_NAME name, ");
        sql.append("  c.PATIENT_IDNO patientIdno, c.PATIENT_AGE age, a.SIGN_HOSP_ID hospitalId, a.SIGN_PACKAGEID setMealId, ");
        sql.append("  c.PATIENT_TEL tel, a.SIGN_ZF_PAY signzfpay, date_format(a.SIGN_FROM_DATE,'%Y-%c-%d %h:%i:%s') signDateStart,date_format(a.SIGN_TO_DATE,'%Y-%c-%d %h:%i:%s') signDateEnd, ");
        sql.append("  a.SIGN_JJ_TYPE signsJjType, a.SIGN_LX signlx, a.SIGN_WEB_STATE signWebState, ");
        sql.append("  date_format(a.SIGN_DATE,'%Y-%c-%d %h:%i:%s') signDate,a.SIGN_STATE signState,a.SIGN_CZ_PAY signczpay,a.SIGN_DR_ID signDrId,");
        sql.append("  (SELECT DR_NAME from APP_DR_USER g where g.ID=a.SIGN_DR_ID) signDrName, ");
        sql.append("  (SELECT DR_CODE from APP_DR_USER g where g.ID=a.SIGN_DR_ID) signDrCode, ");
        sql.append("  c.PATIENT_GENDER sex,c.PATIENT_CARD patientCard,a.SIGN_TYPE signType, c.PATIENT_ADDRESS patientAddress, ");
        sql.append("  a.SIGN_TEAM_NAME signTeamName,s.BATCH_OPERATOR_NAME batchOperatorName, ");
        sql.append("  (SELECT GROUP_CONCAT(LABEL_VALUE) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID) signPersGroup, ");
        sql.append("  a.SIGN_PAY_STATE signPayState ");
        sql.append("  FROM  ");
        sql.append("  APP_SIGN_FORM a ");
        sql.append("  LEFT JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID ");
        sql.append("  LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID LEFT  ");
        sql.append("  JOIN APP_SIGN_BATCH s ON a.SIGN_BATCH_ID=s.ID ");
        sql.append("  where 1=1 and a.SIGN_STATE in ('2','0')  ");
        if (StringUtils.isNotBlank(qvo.getPatientIdno())) {
            sql.append(" AND c.PATIENT_IDNO=:PATIENT_IDNO ");
            map.put("PATIENT_IDNO", qvo.getPatientIdno());
        }
        sql.append(" order by SIGN_DATE desc limit 1");
        List<RegisterSelVo> ls = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, RegisterSelVo.class);
        if(null!=ls && ls.size()>0){
            return ls;
        }
        return ls;
    }

    /**
     * 获取批次表的信息
     * @param batchid
     * @return
     */
    @Override
    public AppSignBatch fingOperatorInfo(String batchid) throws Exception{
        AppSignBatch asb = new AppSignBatch();
        String sql = "select * from APP_SIGN_BATCH where ID =:ID ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ID",batchid);
        List<AppSignBatch> s = sysDao.getServiceDo().findSqlMap(sql.toString(), map, AppSignBatch.class);
        if (s != null && !s.isEmpty()) {
            return s.get(0);
        }
        return null;
    }

    /**
     * 根据套餐ID查询套餐、补贴信息
     * @param mealValueId
     * @return
     */
    @Override
    public List<RegisterGovEntity>getPayInfo(String mealValueId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tSERSM_JJ_ID sersmJjId,\n" +
                "\t'' eagGovValue,\n" +
                "\t'' govList,\n" +
                "\t SERSM_PK_VALUE sersmPkValue \n"+
                "FROM\n" +
                "\tAPP_SERVE_SETMEAL t\n" +
                "WHERE 1=1 \n";
        if(StringUtils.isNotBlank(mealValueId)){
            String[] mealValueIds = mealValueId.split(";");
            map.put("ID",mealValueIds);
            sql += " AND t.ID in (:ID) ";
        }
        List<RegisterGovEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,RegisterGovEntity.class);
        if(ls != null && ls.size() >0){
            return ls;
        }
        return null;
    }

    /**
     *查询费用明细
     * @return
     */
    @Override
    public List<PatientCostDetail> getPayInfoList(String singFormId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "select \n" +
                "\tb.*\n" +
                "from\n" +
                "\tAPP_PATIENT_COST a\n" +
                "LEFT JOIN \n" +
                "\tAPP_PATIENT_COST_DETAIL b\n" +
                "on a.ID = b.PCD_COST_DOCUMENTS_ID\n" +
                "where 1=1\n"+
                "AND b.PCD_WRITE_OFF_MARK = 'Z'";
                if(StringUtils.isNotBlank(singFormId)){
                    sql+=sql="and a.PC_INNER_REGISTER_ID =:PC_INNER_REGISTER_ID";
                    map.put("PC_INNER_REGISTER_ID",singFormId+"_ghh");
                }
        List<PatientCostDetail> pcdList = sysDao.getServiceDo().findSqlMap(sql,map,PatientCostDetail.class);
        if(null!=pcdList && pcdList.size()>0){
            return pcdList;
        }
        return null;
    }

    /**
     * 返回病人的结账信息
     * @return
     */
    @Override
    public RegisterListVo findRegisterInfoList(RegisterSelVo return_qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT \n" +
                "\ta.ID mainId,\n" +
                "  a.PSA_PATIENT_COST_ID patientCostID, \n" +
                "  a.PSA_HOSPITAL_ID hospitalId,\n" +
                "  a.PSA_HEALTHCARE_DOCUMENT_BILL_ID hcDocumentBillId,\n" +
                "  a.PSA_PATIENT_ID patientId, \n" +
                "  a.PSA_CHECK_OUT_DATE checkOutDate,\n" +
                "  a.PSA_CHECK_OUT_TIME checkOutTime,\n" +
                "  a.PSA_CHECKOUT_OPERATOR_ID checkoutOperatorId,\n" +
                "  a.PSA_HEALTH_CARE_CENTER_TYPE hcCenterType,\n" +
                "  a.PSA_HEALTH_CARE_REGISTRATION_ID hcRegistrationId,\n" +
                "  a.PSA_REGISTERED_DEPARTMEN_NAME registeredDepartmenName,\n" +
                "  b.PC_INNER_REGISTER_ID singFormId"+
                "  FROM " +
                "  APP_PATIENT_SETTLE_ACCOUNTS a \n" +
                "  LEFT JOIN APP_PATIENT_COST b on a.PSA_PATIENT_COST_ID = b.ID\n" +
                "  where 1=1\n" +
                "  AND a.PSA_HEALTHCARE_DOCUMENT_BILL_ID = '0'\n";
                if(StringUtils.isNotBlank(return_qvo.getId())){
                   sql+= "ANd b.PC_INNER_REGISTER_ID =:PC_INNER_REGISTER_ID";
                   map.put("PC_INNER_REGISTER_ID",return_qvo.getId()+"_ghh");
                }
        List<RegisterListVo> rlVoList = sysDao.getServiceDo().findSqlMapRVo(sql,map,RegisterListVo.class);
        if(rlVoList!=null && rlVoList.size()>0){
            RegisterListVo plVo = rlVoList.get(0);
            List<PatientCostDetail> pcdList = sysDao.getRegisterDao().getPayInfoList(plVo.getSingFormId().substring(0,plVo.getSingFormId().indexOf("_ghh")));
            if(pcdList!=null && pcdList.size()>0){
                plVo.setCount(String.valueOf(pcdList.size()));
                //签约信息
                String resultStr = "";
                // 新加 医生信息
                String doctorListStr = sysDao.getRegisterDao().getDrInfo(return_qvo.getSignDrId());
                System.out.print(doctorListStr);
                String doctorxx = doctorListStr.split(";")[1]+";"+"*"+";"+"*"+";"+"*"+";"+doctorListStr.split(";")[0];//组装医生姓名（组成14行协议）
                String doctorvalue ="";
                for(PatientCostDetail pcd :pcdList){
                    if(StringUtils.isNotBlank(pcd.getItemCode())){
                        HealthcareChargesProject hcp = sysDao.getRegisterDao().hcProjectInfo(pcd.getItemCode());
                        if(null!=hcp){
                            //医保项目编号+是否医保项目+其他费+项目名称+按"季度"签约收费+季度+单位+数量+总价+医生姓名
                            // hcp.getMedicalInsuranceProject()
                            resultStr+=pcd.getItemCode()+";"+"Y"+";"+"其它费"+";"+hcp.getProjectName()+";"
                                       +"按季签约收费"+";"+"季"+";"+MyMathUtil.div(Double.valueOf(pcd.getUnitPrice()),4,2)+";"+MyMathUtil.mul(Double.valueOf(pcd.getQuantity()),4)+";"+pcd.getTotalAmount()+";"+doctorxx+";";
                        }
                    }
                }
                if(StringUtils.isBlank(doctorvalue)){
                    doctorvalue = doctorListStr;
                    System.out.print("测试！"+doctorvalue);
                }else{
                    doctorvalue =doctorvalue+ doctorListStr;
                    System.out.print("测试111！"+doctorvalue);
                }
                plVo.setResultStr(resultStr.substring(0,resultStr.lastIndexOf(";")));
                plVo.setDoctorListStr(doctorvalue.substring(0, doctorvalue.lastIndexOf(";")));
            }
            return plVo;
        }
        return null;
    }

    /**
     * 获取医保收费项目信息
     * @param projectId
     * @return
     */
    @Override
    public HealthcareChargesProject hcProjectInfo(String projectId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "select * from APP_HEALTHCARE_CHARGES_PROJECT where 1=1\n";
        if(StringUtils.isNotBlank(projectId)){
            sql+="AND HCP_PROJECT_ID=:HCP_PROJECT_ID";
            map.put("HCP_PROJECT_ID",projectId);
        }
        List<HealthcareChargesProject> hcpList = sysDao.getServiceDo().findSqlMap(sql,map,HealthcareChargesProject.class);
        if(null != hcpList && hcpList.size()>0){
            return hcpList.get(0);
        }
        return null;
    }

    /**
     * 获取医生列表
     * @param teamId
     * @return
     */
    @Override
    public String getDoctorList(String teamId) throws Exception{
        String doctorListStr = "";
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "  t.MEM_TEAMID memTeamid,\n"+
                "  t.MEM_DR_ID memDrId,\n" +
                "  u.DR_CODE drCode,\n"+
                "  t.MEM_DR_NAME memDrName,\n" +
                "  t.MEM_STATE memState,\n" +
                "  t.MEM_WORK_TYPE memWorkType,\n" +
                "  '' memWorkName,\n" +
                "  u.DR_TEL drtel, \n" +
                "  u.DR_IDNO drIdNo \n" +
                "FROM\n" +
                "  APP_TEAM_MEMBER t,\n" +
                "  APP_DR_USER u\n" +
                "WHERE\n" +
                "  t.MEM_DR_ID = u.ID\n"+
                "  AND t.MEM_STATE = '1'";
        if(StringUtils.isNotBlank(teamId)){
            sql+="AND t.MEM_TEAMID =:teamId ";
            map.put("teamId",teamId);
        }
        List<RegisterTeamMemberEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,RegisterTeamMemberEntity.class);
        if(null!=ls && ls.size()>0){
            for(RegisterTeamMemberEntity atme : ls){
                doctorListStr+=atme.getDrIdNo()+";"+atme.getMemDrName()+";";
                if("0".equals(atme.getMemState())){
                    doctorListStr+="团队成员";
                }else if("1".equals(atme.getMemState())){
                    doctorListStr+="队长";
                }
                doctorListStr+=";"+atme.getMemWorkName()+";";
            }
            if(!"".equals(doctorListStr)){
                return doctorListStr+"drCount"+ls.size();
            }
        }
        return null;
    }

    /**
     *医保登记成功后修改信息
     * @return
     */
    @Override
    public String updateInfo(RegisterUpdateParramVo rupVo) throws Exception{
        //修改病人费用表信息
        //1、查询结账信息
        PatientSettleAccounts psa = (PatientSettleAccounts)sysDao.getServiceDo().find(PatientSettleAccounts.class,rupVo.getMainId());
        if(null!=psa){
            PatientPayFee ppf = new PatientPayFee();
            ppf.setHospitalId(psa.getHospitalId());//医院内、区卫生院id
            ppf.setPatientId(psa.getPatientId());//病人id号

            ppf.setPaperId(rupVo.getDjlsh0());//打印的票据流水号
            ppf.setOperationDate(rupVo.getSfrq00());//操作日期
            ppf.setOperationTime(rupVo.getSfsj00());//操作时间
            ppf.setOperatorId(rupVo.getUser().getUserId());//操作员ID
            ppf.setOperatorName(rupVo.getUser().getUserName());//操作员员姓名
            ppf.setPatientBalance(new BigDecimal("0"));//病人余额
            ppf.setPaymentRemark("");//交费备注(0交预交金、1、退预交金）
            //2、修改信息
            psa.setHcDocumentBillId(rupVo.getDjlsh0());//医保返回的单据号
            if(rupVo.getZhzfe0()!=null && Float.parseFloat(rupVo.getZhzfe0())>0){
                psa.setPerAccountPayment(new BigDecimal(rupVo.getZhzfe0()));//个人账户支付
                ppf.setPayAmount(new BigDecimal(rupVo.getZhzfe0()));//交费金额
                //生成缴费表信息
                ppf.setPaymentMode("1");//支付方式
                ppf.setPaymentModeName("个人账户支付");//支付方式名称
                addReturnRegisterInfo(ppf);
            }
            if(rupVo.getJjzfe0()!=null && Float.parseFloat(rupVo.getJjzfe0())>0){
                psa.setFundPaymen(new BigDecimal(rupVo.getJjzfe0()));//统筹基金支付
                ppf.setPayAmount(new BigDecimal(rupVo.getJjzfe0()));//交费金额
                //生成缴费表信息
                ppf.setPaymentMode("2");//支付方式
                ppf.setPaymentModeName("统筹基金支付");//支付方式名称
                addReturnRegisterInfo(ppf);
            }
            if(rupVo.getGrzfe0()!=null && Float.parseFloat(rupVo.getGrzfe0())>0){
                psa.setOneselfPayment(new BigDecimal(rupVo.getGrzfe0()));//个人支付
                ppf.setPayAmount(new BigDecimal(rupVo.getGrzfe0()));//交费金额
                //生成缴费表信息
                ppf.setPaymentMode("3");//支付方式
                ppf.setPaymentModeName("现金");//支付方式名称
                addReturnRegisterInfo(ppf);
            }
            if(rupVo.getDbzhzf()!=null && Float.parseFloat(rupVo.getDbzhzf())>0){
                psa.setBipAccount(new BigDecimal(rupVo.getDbzhzf()));//商保个人账户
                ppf.setPayAmount(new BigDecimal(rupVo.getDbzhzf()));//交费金额
                //生成缴费表信息
                ppf.setPaymentMode("4");//支付方式
                ppf.setPaymentModeName("商保个人账户");//支付方式名称
                addReturnRegisterInfo(ppf);
            }
            if(rupVo.getDbjjzf()!=null && Float.parseFloat(rupVo.getDbjjzf())>0){
                psa.setCicoofund(new BigDecimal(rupVo.getDbjjzf()));//商保统筹基金
                ppf.setPayAmount(new BigDecimal(rupVo.getDbjjzf()));//交费金额
                //生成缴费表信息
                ppf.setPaymentMode("5");//支付方式
                ppf.setPaymentModeName("商保统筹基金");//支付方式名称
                addReturnRegisterInfo(ppf);
            }
            psa.setHcCheckOutDate(rupVo.getSfrq00());//医保结账日期
            psa.setHcCheckOutTime(rupVo.getSfsj00());//医保结账时间
            psa.setRegisteredDepartmenName("签约");
            sysDao.getServiceDo().modify(psa);
            //修改签约状态
            AppSignForm fvo = sysDao.getRegisterDao().findpatientIdNo(rupVo.getBrzjbh());
            if(fvo!=null ) { // update 签约状态
                fvo.setSignState("2");
                fvo.setSignPayState("1");
                sysDao.getServiceDo().modify(fvo);
            }
            return "SUCCESS";
        }
       return null;
    }

    /**
     * 获取参数
     * @param hcp
     * @return
     */
    @Override
    public HealthCareParameter getHCParameter(HealthCareParameter hcp) throws Exception{
        HealthCareParameter hc_param = new HealthCareParameter();
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "select \n" +
                "  \t* \n" +
                "  from \n" +
                "  APP_HEALTHCARE_PARAMETER\n" +
                "  where 1=1\n" ;
        if(StringUtils.isNotBlank(hcp.getHospId())){
            sql+="  and HP_HOSP_ID=:HP_HOSP_ID \n" ;
            map.put("HP_HOSP_ID",hcp.getHospId());
        }
        if(StringUtils.isNotBlank(hcp.getParameterName())){
            sql+="  and HP_PARAM_NAME=:HP_PARAM_NAME ";
            map.put("HP_PARAM_NAME",hcp.getParameterName());
        }
        List<HealthCareParameter> hcList = sysDao.getServiceDo().findSqlMap(sql,map,HealthCareParameter.class);
        if(null!=hcList && hcList.size()>0) {
            return hcList.get(0);
        }
        return null;
    }

    /**
     * 查询医保项目列表
     * @param hcp
     * @return
     */
    @Override
    public List<HealthcareChargesProject> getHcpList(HcProjectVo hcp) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                /*"\ta.HCP_PROJECT_ID projectId,\n" +
                "\ta.HCP_PROJECT_NAME projectName,\n" +
                "\ta.HCP_AVAILABLE available,\n" +
                "\ta.HCP_MEDICAL_INSURANCE_PROJECT medicalInsuranceProject\n" +*/
                " * FROM\n" +
                "\tAPP_HEALTHCARE_CHARGES_PROJECT a \n" +
                "WHERE 1=1 ";
        if(StringUtils.isNotBlank(hcp.getProjectName())){
            sql+="AND a.HCP_PROJECT_NAME LIKE :areaCode ";
            map.put("areaCode", "%"+hcp.getProjectName()+"%");
        }
        return sysDao.getServiceDo().findSqlMap(sql,map,HealthcareChargesProject.class,hcp);
    }

    /**
     * 获取医生信息
     * @param drId
     * @return
     */
    public String getDrInfo(String drId) throws Exception{
        String doctorListStr = "";
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tt.MEM_TEAMID memTeamid,\n" +
                "\tt.MEM_DR_ID memDrId,\n" +
                "\tu.DR_CODE drCode,\n" +
                "\tt.MEM_DR_NAME memDrName,\n" +
                "\tt.MEM_STATE memState,\n" +
                "\tt.MEM_WORK_TYPE memWorkType, t.DR_ROLE drrole, \n" +
                "\t'' memWorkName,\n" +
                "\tu.DR_TEL drtel,\n" +
                "\tu.DR_IDNO drIdNo\n" +
                "FROM\n" +
                "\tAPP_TEAM_MEMBER t,\n" +
                "\tAPP_DR_USER u\n" +
                "WHERE\n" +
                "\tt.MEM_DR_ID = u.ID\n" ;
        if(StringUtils.isNotBlank(drId)){
            sql+="AND u.ID =:ID ";
            map.put("ID",drId);
        }
        List<RegisterTeamMemberEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,RegisterTeamMemberEntity.class);
        if(null!=ls && ls.size()>0){
            RegisterTeamMemberEntity  rtme = ls.get(0);
            doctorListStr+=rtme.getDrIdNo()+";"+rtme.getMemDrName()+";"+rtme.getMemState()+";"+rtme.getMemWorkName()+";";
        }
        if(!"".equals(doctorListStr)){
            return doctorListStr;
            //return doctorListStr+"drCount1";
        }
        return null;
    }

    /**
     *根据身份证查询签约信息
     * @param patientidno
     * @return
     * @throws Exception
     */
    @Override
    public AppSignForm findpatientIdNo(String patientidno)throws  Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql ="";
        if(StringUtils.isNotBlank(patientidno)){
            sql=" SELECT * from APP_SIGN_FORM u where u.SIGN_STATE in ('2','0') AND u.SIGN_PATIENT_IDNO=:patientidno";
            map.put("patientidno",patientidno);
            List<AppSignForm> vo= sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
            if(vo !=null){
                return vo.get(0);
            }
        }
        return null ;
    }

    /**
     *
     */
    public void changeRegistered() throws Exception{

    }
    /**
     * 获取改签信息
     */
    public PatientSettleAccounts getChangeRegisterInfo(String patientidno) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        String sql ="";
        if(StringUtils.isNotBlank(patientidno)) {
            /*sql = "SELECT\n" +
                    "\ta.*\n" +
                    "FROM\n" +
                    "\tapp_patient_settle_accounts a,\n" +
                    "\tapp_patient_cost b,\n" +
                    "\tapp_sign_form c,\n" +
                    "  app_patient_cost_detail d\n" +
                    "WHERE\n" +
                    "\ta.PSA_PATIENT_COST_ID = b.ID\n" +
                    "AND b.PC_INNER_REGISTER_ID = CONCAT(c.ID, '_ghh')\n" +
                    "AND a.PSA_HEALTHCARE_DOCUMENT_BILL_ID <> '0'\n" +
                    "AND b.ID = d.PCD_COST_DOCUMENTS_ID\n" +
                    "AND d.PCD_WRITE_OFF_MARK = 'Z'\n"+
                    "AND c.SIGN_PATIENT_IDNO=:SIGN_PATIENT_IDNO";*/
            sql = "SELECT\n" +
                    "\ta.*\n" +
                    "FROM\n" +
                    "\tapp_patient_settle_accounts a,\n" +
                    "  app_patient_user b\n" +
                    "WHERE\n" +
                    "\ta.PSA_PATIENT_ID = b.ID\n" +
                    "AND a.PSA_SETTLEMENT_STATUS = '0'" +
                    "AND b.PATIENT_IDNO= :SIGN_PATIENT_IDNO";
            map.put("SIGN_PATIENT_IDNO",patientidno);
            List<PatientSettleAccounts> vo= sysDao.getServiceDo().findSqlMap(sql, map, PatientSettleAccounts.class);
            if(vo !=null){
                return vo.get(0);
            }
        }
        return null;
    }

    /**
     * 退签
     * @param rupVo
     * @return
     * @throws Exception
     */
    public String cancelRegisterInfo(RegisterUpdateParramVo rupVo) throws Exception{
        Calendar now = Calendar.getInstance();
        DateFormat datef = new SimpleDateFormat("yyyyMMdd");
        DateFormat times = new SimpleDateFormat("HH:mm:ss");
        String datestr =datef.format(new Date());
        String timestr =times.format(new Date());
        String time = String.valueOf(now.get(Calendar.HOUR_OF_DAY))+String.valueOf(now.get(Calendar.MINUTE))+String.valueOf(now.get(Calendar.SECOND));
        AppSignForm signForm = findpatientIdNo(rupVo.getBrzjbh());
        if(null!=signForm) {
            RegisterListVo rlvVo = findChangeRegisterInfoList(signForm.getId());
            signForm.setSignPayState("0");
            //修改病人费用表信息
            //1、查询结账信息,生成一条负的记录
            PatientSettleAccounts psa = (PatientSettleAccounts) sysDao.getServiceDo().find(PatientSettleAccounts.class, rlvVo.getMainId());
        // 新
            if (null != psa) {
                psa.setHcRegistrationId("0");
                psa.setHcDocumentBillId("0");
//                psa.setSettlementStatus("0");

                sysDao.getServiceDo().modify(psa);
                PatientSettleAccounts  psapo = new PatientSettleAccounts();
                CopyUtils.Copy(psa,psapo);

                PatientPayFee ppf = new PatientPayFee();
                ppf.setHospitalId(psa.getHospitalId());//医院内、区卫生院id
                ppf.setPatientId(psa.getPatientId());//病人id号
                ppf.setPaperId(rupVo.getDjlsh0());//打印的票据流水号
                ppf.setOperationDate(rupVo.getSfrq00());//操作日期
                ppf.setOperationTime(rupVo.getSfsj00());//操作时间
                ppf.setOperatorId(rupVo.getUser().getUserId());//操作员ID
                ppf.setOperatorName(rupVo.getUser().getUserName());//操作员员姓名
                ppf.setPatientBalance(new BigDecimal("0"));//病人余额
                ppf.setPaymentRemark("");//交费备注(0交预交金、1、退预交金）
                //2、修改信息
                psapo.setHcDocumentBillId(rupVo.getDjlsh0());//医保返回的单据号
                if (rupVo.getZhzfe0() != null && Float.parseFloat(rupVo.getZhzfe0()) < 0) {
                    psapo.setPerAccountPayment(new BigDecimal(rupVo.getZhzfe0()));//个人账户支付
                    ppf.setPayAmount(new BigDecimal(rupVo.getZhzfe0()));//交费金额
                    //生成缴费表信息
                    ppf.setPaymentMode("1");//支付方式
                    ppf.setPaymentModeName("个人账户支付");//支付方式名称
                    addReturnRegisterInfo(ppf);
                }
                if (rupVo.getJjzfe0() != null && Float.parseFloat(rupVo.getJjzfe0()) < 0) {
                    psapo.setFundPaymen(new BigDecimal(rupVo.getJjzfe0()));//统筹基金支付
                    ppf.setPayAmount(new BigDecimal(rupVo.getJjzfe0()));//交费金额
                    //生成缴费表信息
                    ppf.setPaymentMode("2");//支付方式
                    ppf.setPaymentModeName("统筹基金支付");//支付方式名称
                    addReturnRegisterInfo(ppf);
                }
                if (rupVo.getGrzfe0() != null && Float.parseFloat(rupVo.getGrzfe0()) < 0) {
                    psapo.setOneselfPayment(new BigDecimal(rupVo.getGrzfe0()));//个人支付
                    ppf.setPayAmount(new BigDecimal(rupVo.getGrzfe0()));//交费金额
                    //生成缴费表信息
                    ppf.setPaymentMode("3");//支付方式
                    ppf.setPaymentModeName("现金");//支付方式名称
                    addReturnRegisterInfo(ppf);
                }
                if (rupVo.getDbzhzf() != null && Float.parseFloat(rupVo.getDbzhzf()) < 0) {
                    psapo.setBipAccount(new BigDecimal(rupVo.getDbzhzf()));//商保个人账户
                    ppf.setPayAmount(new BigDecimal(rupVo.getDbzhzf()));//交费金额
                    //生成缴费表信息
                    ppf.setPaymentMode("4");//支付方式
                    ppf.setPaymentModeName("商保个人账户");//支付方式名称
                    addReturnRegisterInfo(ppf);
                }
                if (rupVo.getDbjjzf() != null && Float.parseFloat(rupVo.getDbjjzf()) < 0) {
                    psapo.setCicoofund(new BigDecimal(rupVo.getDbjjzf()));//商保统筹基金
                    ppf.setPayAmount(new BigDecimal(rupVo.getDbjjzf()));//交费金额
                    //生成缴费表信息
                    ppf.setPaymentMode("5");//支付方式
                    ppf.setPaymentModeName("商保统筹基金");//支付方式名称
                    addReturnRegisterInfo(ppf);
                }
                psapo.setHcCheckOutDate(rupVo.getSfrq00());//医保结账日期
                psapo.setHcCheckOutTime(rupVo.getSfsj00());//医保结账时间
                psapo.setRegisteredDepartmenName("退约");
                // 新
                psapo.setSettlementStatus("1");
                psapo.setHcRegistrationId("0");
                sysDao.getServiceDo().add(psapo);
            }
            //冲销费用主表
            String innerGhh = signForm.getId() + "_ghh";
            PatientCost patientCosts = getPatientCostByGhh(innerGhh);
            if (null != patientCosts) {
                //冲销原来的费用
//                PatientCost patientCost = new PatientCost();
//                patientCost.setHospitalId(registerSelVo.getHospitalId());//医院ID
//                patientCost.setHcCostId("0");//医保返回的单据号
//                patientCost.setInnerRegisterId(registerSelVo.getId()+"_ghh");//内部挂号号
//                patientCost.setPatientId(registerSelVo.getPatientId());//病人ID
//                patientCost.setPatientName(registerSelVo.getName());//病人姓名
//                patientCost.setTotalAmount(new BigDecimal("0"));//总金额
//                patientCost.setPatientBalance(new BigDecimal("0"));//病人余额
//                patientCost.setOperationDate(datestr);//操作日期
//                patientCost.setOperationTime(timestr);//操作时间
//                patientCost.setOperatorId(registerSelVo.getOperatorId());//操作员编号
//                patientCost.setOperatorName(registerSelVo.getOperatorName());//操作员姓名
//                patientCost.setOperatorDepartment(registerSelVo.getOperationDepart());//收费科室
//                patientCost.setSettlementId("0"); //结算单号,0:该帐目未结算(外键、结账表结账单号）
//                patientCost.setHcCenter("0");//医保中心类别（暂时只有医保：1）
//                patientCost.setDataSource("web");
//                sysDao.getServiceDo().add(patientCost);
                //冲销费用明细,生成一条负的记录
                List<PatientCostDetail> ls = getPayInfoList(signForm.getId());
                if (null != ls && ls.size() > 0) {
                    //查询批次
                    AppSignBatch appSignBatch = new AppSignBatch();
                    if (sysDao.getRegisterDao().fingOperatorInfo(signForm.getSignBatchId()) != null) {
                        appSignBatch = sysDao.getRegisterDao().fingOperatorInfo(signForm.getSignBatchId());
                    }
                    Iterator it = ls.iterator();
                    while (it.hasNext()) {
                        /* 之前
                        PatientCostDetail pcd = new PatientCostDetail();
                        PatientCostDetail dto = (PatientCostDetail) it.next();
                            pcd = dto;
                        dto.setWriteOffMark("+");//Z:表示正常的没有被冲销的  +被冲销的单  -表示该记录冲销别的单
                        sysDao.getServiceDo().modify(dto);
                        //sysDao.getServiceDo().removePoFormSession(dto);
                        //生成一条负的记录
                        pcd.setQuantity("1");//数量
                        pcd.setTotalAmount(dto.getTotalAmount().negate());//合计金额
                        pcd.setOneself_amount(dto.getOneself_amount().multiply(new BigDecimal("-1")));//自费金额
                        pcd.setBillingDate(datestr);//开单日期
                        pcd.setBillingTime(timestr);//开单时间
                        pcd.setWriteOffMark("-");//Z:表示正常的没有被冲销的  +被冲销的单  -表示该记录冲销别的单
                        pcd.setWriteOffPeople(rupVo.getUser().getUserName());//冲销批准人
                        sysDao.getServiceDo().add(pcd);*/
              // 新
                        PatientCostDetail pcd = new PatientCostDetail();
                        PatientCostDetail dto = (PatientCostDetail) it.next();
                        CopyUtils.Copy(dto,pcd);
                        //生成一条负的记录
                        pcd.setId(null);
                        pcd.setQuantity("1");//数量
                        pcd.setTotalAmount(dto.getTotalAmount().negate());//合计金额
                        pcd.setOneself_amount(dto.getOneself_amount().multiply(new BigDecimal("-1")));//自费金额
                        pcd.setBillingDate(datestr);//开单日期
                        pcd.setBillingTime(timestr);//开单时间
                        pcd.setWriteOffMark("+");//Z:表示正常的没有被冲销的  +被冲销的单  -表示该记录冲销别的单
                        pcd.setWriteOffPeople(rupVo.getUser().getUserName());//冲销批准人
                        sysDao.getServiceDo().add(pcd);
                    }
                }
            }
            //修改签约状态
            AppSignForm fvo = sysDao.getRegisterDao().findpatientIdNo(rupVo.getBrzjbh());
            if(fvo!=null ) { // update 签约状态
                fvo.setSignState("0");
                fvo.setSignPayState("0");
                sysDao.getServiceDo().modify(fvo);
            }
            return "success";
        }
        return null;
    }
    /**
     *查询病人的改签信息
     * @return
     */
    public RegisterListVo findChangeRegisterInfoList(String innerId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT \n" +
                "\ta.ID mainId,\n" +
                "  a.PSA_PATIENT_COST_ID patientCostID, \n" +
                "  a.PSA_HOSPITAL_ID hospitalId,\n" +
                "  a.PSA_HEALTHCARE_DOCUMENT_BILL_ID hcDocumentBillId,\n" +
                "  a.PSA_PATIENT_ID patientId, \n" +
                "  a.PSA_CHECK_OUT_DATE checkOutDate,\n" +
                "  a.PSA_CHECK_OUT_TIME checkOutTime,\n" +
                "  a.PSA_CHECKOUT_OPERATOR_ID checkoutOperatorId,\n" +
                "  a.PSA_HEALTH_CARE_CENTER_TYPE hcCenterType,\n" +
                "  a.PSA_HEALTH_CARE_REGISTRATION_ID hcRegistrationId,\n" +
                "  a.PSA_REGISTERED_DEPARTMEN_NAME registeredDepartmenName,\n" +
                "  b.PC_INNER_REGISTER_ID singFormId"+
                "  FROM " +
                "  APP_PATIENT_SETTLE_ACCOUNTS a \n" +
                "  LEFT JOIN APP_PATIENT_COST b on a.PSA_PATIENT_COST_ID = b.ID\n" +
                "  where 1=1\n" +
                "  AND a.PSA_HEALTHCARE_DOCUMENT_BILL_ID <> '0'\n" +
                "  AND a.PSA_SETTLEMENT_STATUS = '0'";
        if(StringUtils.isNotBlank(innerId)){
            sql+= "ANd b.PC_INNER_REGISTER_ID =:PC_INNER_REGISTER_ID";
            map.put("PC_INNER_REGISTER_ID",innerId+"_ghh");
        }
        List<RegisterListVo> rlVoList = sysDao.getServiceDo().findSqlMapRVo(sql,map,RegisterListVo.class);
        if(rlVoList!=null && rlVoList.size()>0) {
            return rlVoList.get(0);
        }
        return null;
    }

    /**
     * 查询医生信息
     * @param DrIdNo
     * @return
     */
    public AppDrUser getDocInfo(String DrIdNo) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("ID",DrIdNo.split(";")[0]);
        String sql = "SELECT * FROM APP_DR_USER WHERE 1=1" +
                " AND ID=:ID ";
        List<AppDrUser> list = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
        if(null!=list && list.size()>0)
        {
            return list.get(0);
        }
        return null;
    }


    /**
     *  cjw 居民 id 查询费用表
     * @return
     * @throws Exception
     */
    public PatientCost getPatientCost(String patientid,String innerRegisterId)throws Exception
    {
        if(StringUtils.isNotBlank(patientid) && StringUtils.isNotEmpty(innerRegisterId)){
            Map<String,Object> map = new HashMap<>();
            String sql ="";
            sql =" SELECT * from APP_PATIENT_COST  c where c.PC_PATIENT_ID =:patientid and c.PC_INNER_REGISTER_ID=:innerRegisterId";
            map.put("patientid",patientid);
            map.put("innerRegisterId",innerRegisterId+"_ghh");
            List<PatientCost> ls = sysDao.getServiceDo().findSqlMap(sql,map,PatientCost.class);
            if(ls!=null && ls.size()>0){
                return ls.get(0);
            }
        }
        return null ;
    }
}
