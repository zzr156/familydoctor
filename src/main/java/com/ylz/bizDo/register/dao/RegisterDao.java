package com.ylz.bizDo.register.dao;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppSignBatch;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.register.entity.RegisterGovEntity;
import com.ylz.bizDo.register.entity.RegisterTeamMemberEntity;
import com.ylz.bizDo.register.po.*;
import com.ylz.bizDo.register.vo.*;

import java.util.List;

public interface RegisterDao {
    /**
     * 签约的时候新增信息
     * @return
     */
    public String addRegisterInfo(RegisterSelVo registerSelVo) throws Exception;

    /**
     * 医保登记成功后保存信息
     * @return
     */
    public String addReturnRegisterInfo(PatientPayFee ppf) throws Exception;

    /**
     * 根据内部挂好好查询主表
     */
    public PatientCost getPatientCostByGhh(String innerGhh) throws Exception;

    /**
     * 查询签约信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<RegisterSelVo> findSignXxWeb(AppCommQvo qvo) throws Exception;

    /**
     * 根据批次ID去操作员信息
     * @param batchid
     * @return
     */
    public AppSignBatch fingOperatorInfo(String batchid) throws Exception;

    /**
     * 根据套餐ID查询所需的支付信息
     * @param mealValueId
     * @return
     */
    public List<RegisterGovEntity> getPayInfo(String mealValueId) throws Exception;

    /**
     * 根据签约ID查询费医保登记用明细
     * @return
     */
    public List<PatientCostDetail> getPayInfoList(String singFormId) throws Exception;

    /**
     *查询病人的结账信息
     * @return
     */
    public RegisterListVo findRegisterInfoList(RegisterSelVo return_qvo) throws Exception;

    /**
     * 根据项目编号查询医保收费项目
     * @param projectId
     * @return
     */
    public HealthcareChargesProject hcProjectInfo(String projectId) throws Exception;

    /**
     * 签约医生团队信息
     * @param teamId
     * @return
     */
    public String getDoctorList(String teamId) throws Exception;

    /**
     *医保登记成功后修改信息
     */
    public String updateInfo(RegisterUpdateParramVo rupVo) throws Exception;

    /**
     * 获取系统配置的参数
     * @param hcp
     * @return
     */
    public HealthCareParameter getHCParameter(HealthCareParameter hcp) throws Exception;

    /**
     * 查询医保项目列表
     * @param hcp
     * @return
     */
    public List<HealthcareChargesProject> getHcpList(HcProjectVo hcp) throws Exception;

    /**
     * 获取医生信息
     * @param drId
     * @return
     */
    public String getDrInfo(String drId) throws Exception;

    /**
     *根据身份证查询签约信息
     * @param patientidno
     * @return
     * @throws Exception
     */
    public AppSignForm findpatientIdNo(String patientidno)throws  Exception;

    /**
     * 费用冲销
     */
    public void changeRegistered() throws Exception;

    /**
     * 获取改签信息
     * @param patientidno
     */
    public PatientSettleAccounts getChangeRegisterInfo(String patientidno) throws Exception;

    /**
     *查询病人的改签信息
     * @return
     */
    public RegisterListVo findChangeRegisterInfoList(String innerId) throws Exception;

    /**
     * 退签
     * @param rupVo
     * @return
     * @throws Exception
     */
    public String cancelRegisterInfo(RegisterUpdateParramVo rupVo) throws Exception;

    /**
      * 查询医生信息
      * @param drIdNo
     * @return
     */
    public AppDrUser getDocInfo(String drIdNo) throws Exception;

    /**
     *  cjw 居民 id 查询费用表
     * @return
     * @throws Exception
     */
    public PatientCost getPatientCost(String patientid,String innerRegisterId)throws Exception;
}
