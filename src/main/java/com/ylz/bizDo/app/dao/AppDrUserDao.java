package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.vo.AppDrUserQvo;
import com.ylz.bizDo.cd.entity.AddressHospEntity;
import com.ylz.bizDo.jtapp.aioEntity.AppDrInfoAioEntity;
import com.ylz.bizDo.jtapp.drEntity.*;
import com.ylz.bizDo.jtapp.drVo.AppDrHospDrQvo;
import com.ylz.bizDo.jtapp.ysChangeEntity.YsChangeDrEntity;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeCountQvo;
import com.ylz.bizDo.smjq.smEntity.AppSmLoginEntity;
import com.ylz.bizDo.web.po.WebDrUser;
import com.ylz.packcommon.common.exception.DaoException;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;

/**
 * 医生表
 */
public interface AppDrUserDao {
    //根据条件查询list数据
    public List<AppDrUser> findList(AppDrUserQvo qvo) throws Exception;

    public List<AppDrUserEntity> findByUser(String userAccount, String md5UserPassword, String selectType)throws Exception;

    //根据医生类型和当前机构查找数据
    public List<AppDrUser> findByType(String drType,String deptId)throws Exception;

    //无分页查询
    public List<AppDrUser> findAll(String hospId) throws Exception;

    public List<AppDrUser> findByEase() throws Exception;

    //根据电话查询数据
    public AppDrUser findByTelPhone(String tel)throws Exception;
    /**
     * 根据医生id和团队id查询医生详细信息
     * @param id
     * @return
     */
    public DrInfo findDrInfoByid(String id,String teamId)throws Exception;

    /**
     * 根据医生id和团队id查询医生详细信息
     * @param drId
     * @param teamId
     * @return
     */
    public DrInfoEntity findDrInfo(String drId, String teamId)throws Exception;

    //根据主键查询数据
    public AppDrUser findByUserId(String drPatientId)throws Exception;
    //根据主键查询非托管
    public AppDrUserEntity findUserId(String id)throws Exception;

    public List<AppDrUser> findByHosp(String hospId) throws Exception;
    //团队添加成员医生查询
    public List<AppDrUser> findListByHospId(String hospId,String drId) throws Exception;
    /**
     * 查询医院下的医生
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppDrChangeInfoEntity> findDrList(AppDrHospDrQvo qvo) throws Exception;

    public boolean findDrUserByTel(String tel)throws Exception;
    /**
     * 根据医院主键查询医生
     * @param upId
     * @return
     */
    public List<AddressHospEntity> findByHospIdNotTs(String upId)throws Exception;


    public AppDrUserEntity findUserById(String drId)throws Exception;

    public AppDrUser findByUserWeb(String account,String md5UserPassword)throws Exception;
    public AppDrUser findByUserWebOrg(String account,String orgid)throws Exception;

    /**
     * 查询变更医生列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<YsChangeDrEntity> findChangeDrList(YsChangeCountQvo qvo) throws Exception;

    public List<AppDrUser> findByAccount(String account)throws Exception;

    public List<AppDrMAccountEntity> findByDrTel(String drTel)throws Exception;

    public List<AppDrInfoAioEntity> findDrInfoByidList(String teamId)throws Exception;

    public Map<String,Object> findDrUserName(String drPatientId)throws Exception;

    /**
     * 基卫同步用户接口
     * @param vo
     */
    public void jwSyncDrUser(WebDrUser vo)throws Exception;

    /**
     * 三明医生登入返回信息
     */
    public AppSmLoginEntity findDrinfoxx(String drId) throws Exception;

    public List<AppDrUserPossEntity> findByUserPoss(String userAccount, String md5UserPassword, String selectType)throws Exception;

    //查询机构下所有有效医生
    public List<AppDrUser> findAllByState(String hospId) throws Exception;

    /**
     * 根据医生账号查询医生数据
     * @param account
     * @param drName
     * @return
     * @throws Exception
     */
    public AppDrUser findDrByCode(String account,String drName) throws Exception;

    /**
     * 医生数据导入
     * @param map
     * @return
     * @throws Exception
     */
    public String addImportExcelDr(Map<Integer, String> map) throws Exception;
}
