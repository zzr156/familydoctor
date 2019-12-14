package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.AppArchivingCardAddrEntity;
import com.ylz.bizDo.app.entity.AppArchivingCardPeopeEntity;
import com.ylz.bizDo.app.entity.AppArchivingPatientEntity;
import com.ylz.bizDo.app.po.AppArchivingCardPeople;
import com.ylz.bizDo.app.vo.AppArchivingCardPeopleQvo;
import com.ylz.bizDo.cd.po.CdUser;

import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/7/17.
 */
public interface AppArchivingCardPeopeDao {

    public List<AppArchivingCardPeopeEntity> findPeopleList(AppArchivingCardPeopleQvo qvo)throws Exception;

    /**
     * 查询建档立卡人数、未建档人数、未建档已签约人数、已建档未签约人数、
     * 脱贫人数
     * @param qvo
     * @return
     */
    public Map<String,Object> findMap(AppArchivingCardPeopleQvo qvo)throws Exception;

    public AppArchivingPatientEntity archivingLook(AppArchivingCardPeopleQvo qvo)throws Exception;
    //findPeopleListT
    public List<AppArchivingCardPeopeEntity> findPeopleListT(AppArchivingCardPeopleQvo qvo)throws Exception;
    //findMapT
    public Map<String,Object> findMapT(AppArchivingCardPeopleQvo qvo)throws Exception;

    /**
     * 根据身份证查询
     * @param patientIdno
     * @return
     */
    public AppArchivingCardPeople findPeopleByIdno(String patientIdno,String jdSourceType)throws Exception;

    /**
     * 查询未建档建档立卡居民
     * @return
     */
    public List<AppArchivingCardPeople> findNotJd() throws Exception;

    /**
     * 根据居民id修改建档立卡花名册签约状态
     * @param patientId
     */
    public void changeArchiSignState(String patientId,String notSignReason)throws Exception;

    /**
     * 查询所有签约字段为空的数据
     * @return
     */
    public List<AppArchivingCardPeople> findBySignId() throws Exception;

    /**
     * 根据街道行政区划查询社区名称集合
     * @param upId
     * @return
     */
    public List<AppArchivingCardAddrEntity> findJnameList(String upId)throws Exception;

    /**
     * 根据来源和归属机构查询建档立卡数
     * @param sourceType
     * @param orgId
     * @return
     */
    public int findCountBySourceType(String sourceType,String orgId,String areaCode)throws Exception;

    /**
     * 添加导入建档立卡居民
     * @param map
     * @param areaCode
     * @return
     */
    public String addImportExcel(Map<Integer, String> map, String areaCode)  throws Exception;

    /**
     * 修改导入建档立卡居民
     * @param map
     * @param areaCode
     * @return
     */
    public String modifyImportExcel(Map<Integer, String> map,String areaCode) throws Exception;

    /**
     * 删除导入建档立卡居民
     * @param map
     * @param areaCode
     * @return
     */
    public String delImportExcel(Map<Integer, String> map, String areaCode) throws Exception;

    /**
     * 查询所有签约字段不为空的数据
     * @return
     */
    public List<AppArchivingCardPeople> findByNotSignId() throws Exception;

}
