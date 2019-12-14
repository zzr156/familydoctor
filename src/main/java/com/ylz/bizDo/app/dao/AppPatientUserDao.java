package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.AppHealthCardRecodesVo;
import com.ylz.bizDo.app.entity.AppPatientDAHVo;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.jtapp.commonEntity.*;
import com.ylz.bizDo.jtapp.commonVo.AppCommLyQvo;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.commonVo.AppPeopleTypeQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrTzEntity;
import com.ylz.bizDo.jtapp.drEntity.AppLyPeopleEntity;
import com.ylz.bizDo.jtapp.drEntity.JmInfo;
import com.ylz.bizDo.jtapp.drEntity.PatientInfo;
import com.ylz.bizDo.jtapp.drVo.AppDrCount;
import com.ylz.bizDo.jtapp.drVo.AppDrTzQvo;
import com.ylz.bizDo.jtapp.drVo.AppLyQvo;
import com.ylz.bizDo.jtapp.patientEntity.*;
import com.ylz.bizDo.jtapp.ysChangeEntity.YsChangeCountEntity;
import com.ylz.bizDo.jtapp.ysChangeEntity.YsChangePatientEntity;
import com.ylz.bizDo.jtapp.ysChangeEntity.YsChangePeopleEntity;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeCountQvo;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeSureQvo;
import com.ylz.packcommon.common.exception.DaoException;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/14.
 */
public interface AppPatientUserDao  {
    public boolean findPatientByIdNo(String idNo,String type)throws Exception;
    public AppPatientUser findPatientIdNo(String idNo)throws Exception;
    public AppPatientUser findPatientIdNo(String idNo,String type)throws Exception;
    public boolean findPatientByCard(String card,String type)throws Exception;
    public boolean findPatientByCardNotUser(String card,String patientId)throws Exception;
    public boolean findPatientByTel(String tel,String type)throws Exception;
    public AppPatientUserEntity findByUser(String userAccount, String userPass, String selectType)throws Exception;
    public AppPatientUserWechatEntity findByWechatUser(String userAccount, String selectType)throws Exception;

    public AppPatientUser findByTelPhone(String tel)throws Exception;

    public AppPatientUser findByUserId(String drPatientId)throws Exception;

    public AppPatientUserEntity findUserId(String id)throws Exception;
    public Map<String,Object> getPatientInfoLogin(AppPatientUserEntity patient,AppPatientPerfectDataEntity vo,String type) throws Exception;
    public AppPatientUserEntity findByUserIdNo(String userIdNo)throws Exception;

    public PatientInfo findUserInfo(String patientId,String state,String signId)throws Exception;
    public void addTest() throws Exception;
    //通过患者id查找团队下的医生
    public List<AppConDrEntiry> findDrById(String id) throws Exception;

    public void addTestDevice()throws Exception;

    public void addTestData()throws Exception;

    public List<AppPatientUser> findAll()throws Exception;


    /**
     * 查询咨询医生列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppConDrEntiry> findDrByIdList(AppDrCount qvo) throws Exception;


    public AppPatientUserEntity findByUserIdNoCardTel(String userIdNo, String userCard, String userTel) throws Exception;
    public AppPatientRegisterEntity findByUserIdNoCardTelRegister(String userIdNo, String userCard, String userTel) throws Exception;
    /**
     * 根据服务人群、健康情况、疾病类型查询
     * @param qvo
     * @throws Exception
     */
    public List<AppPeopleEntity> findByType(AppPeopleTypeQvo qvo) throws Exception;

    /**
     * 完善资料
     * @return
     */
    public String addPerfectData(AppPatientPerfectDataEntity vo) throws Exception;

    /**
     * 注册并完善资料
     * @return
     */
    public String addExternalPerfectData(AppPatientPerfectDataEntity vo) throws Exception;

    /**
     * 统计服务人群,健康情况,疾病类型总数
     * @param qvo
     * @return
     */
    public List<AppPeopleHealthEntity> findTypeCount(AppPeopleTypeQvo qvo) throws Exception;

    /**
     * 查询变更医生统计服务人群,健康情况,疾病类型总数
     * @param qvo
     * @return
     */
    public List<AppPeopleHealthEntity> findTypeCountChange(YsChangeCountQvo qvo) throws Exception;

    /**
     * 查询变更医生根据服务人群、健康情况、疾病类型查询
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<YsChangeCountEntity> findByTypeChange(YsChangeCountQvo qvo) throws Exception;
    /**
     * 查询变更医生根据服务人群、健康情况、疾病类型查询 模糊查询
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<YsChangePeopleEntity> findPeople(YsChangeCountQvo qvo) throws Exception;

    /**
     * 查询变更人员列表信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<YsChangePatientEntity> findChangePatient(YsChangeSureQvo qvo) throws Exception;


    /**
     * 体征居民列表查询接口
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppDrTzEntity> findTzJmList(AppDrTzQvo qvo) throws Exception;

    /**
     * 查询履约人员列表信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppLyPeopleEntity> findLyPeopleList(AppLyQvo qvo) throws Exception;

    /**
     * 履约人员列
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppPeopleHealthEntity> findLyCount(AppPeopleTypeQvo qvo) throws Exception;

    public List<AppComLyPeopleEntity> findByLyType(AppPeopleTypeQvo qvo) throws Exception;

    public List<AppLyPatientEntity> findLyPatient(AppPeopleTypeQvo qvo) throws Exception;

    /**
     * 根据经济类型查询人员
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<JmInfo> findByEconomicType(AppCommQvo qvo) throws Exception;

    public PatientInfo findUserInfoO(String patientId,String state,String type,String teamId) throws Exception;

    /**
     * 居民健康档案签约状态
     * @param patientIdNo
     * @param patientJmda
     * @return
     */
    public List<AppSignForm> getAppSignState(String patientIdNo,String patientJmda) throws Exception;

    public int Apppatientlist()throws Exception;

    public List<AppPatientUser> AppPatientFind(int itemCount,int pageSize)throws  Exception;

    /**
     *  用户 id 查询 签约机构编码
     * @param id
     * @return
     * @throws Exception
     */
    public AppHealthCardRecodesVo AppDeptCode(String id)throws  Exception;

    public void AppPatientUpdate(AppPatientDAHVo vo)throws Exception;

    /**
     * 根据身份证和社保卡查询居民数据
     * @param userIdNo
     * @param userCard
     * @return
     */
    public AppPatientUserEntity findByUserIdNoAndCard(String userIdNo, String userCard) throws Exception;

    public PatientInfo findUserInfooo(String patientIdno,String drId) throws Exception;

    /**
     * 根据用户主键查询
     * @param drPatientId
     * @return
     */
    public Map<String,Object> findPatentUserName(String drPatientId) throws Exception;

    /**
     * 根据姓名、性别、出生日期搜索居民是否签约和是否建档
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppPatientSignAndJdEntity> findSignAndJdByQvo(AppCommQvo qvo) throws Exception;

    /**
     * 根据身份证和姓名查询居民数据
     * @param idno
     * @param name
     * @return
     */
    public AppPatientUser findByIdnoAndName(String idno,String name) throws Exception;

    /**
     * 查询某个街道、乡镇的居民信息列表
     *
     * @param streetCode 街道乡镇编码
     * @return
     * @throws Exception
     */
    public List<AppPatientUser> listPatient(String streetCode) throws Exception;

    /**
     * 根据居民身份证和激活状态查询居民信息
     * @param userId
     * @param idNo
     * @param jhState
     * @return
     */
    public boolean findByIdNoAndJh(String userId,String idNo,String jhState) throws Exception;

    public List<AppTestPatientEntity> findRepeatPatient() throws Exception;

    public List<AppTestPatientEntity> findRepeatPatientByIdno(String idno) throws Exception;

    /**
     * 查询状态为998的居民数据
     * @return
     */
    public List<AppPatientUser> findByUpHpis() throws Exception;

}
