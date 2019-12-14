package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.AppHospAuthorizationEntity;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppUpHospTable;
import com.ylz.bizDo.app.vo.AppHospDeptQvo;
import com.ylz.bizDo.cd.entity.AddressHospEntity;
import com.ylz.bizDo.cd.entity.AddressTreeHospEntity;
import com.ylz.bizDo.cd.entity.CdAddressEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppHospEntity;
import com.ylz.bizDo.jtapp.commonEntity.InternetHospOrgEntity;
import com.ylz.bizDo.jtapp.commonVo.InternetHospQvo;

import java.util.List;
import java.util.Map;

/**
 * 医院表
 */
public interface AppHospDeptDao {
    //根据条件查询list数据
    public List<AppHospDept> findList(AppHospDeptQvo qvo) throws Exception;
    //无分页查询全部
    public List<AppHospDept> findAll() throws Exception;

    public List<AppHospEntity> findByAreaId(String upId) throws Exception;
    public List<AppHospEntity> findByAreaIdNotZero(String upId) throws Exception;

    public List<AppHospDept> findByState(String state) throws Exception;
    //上级机构查询下级机构
    public List<AppHospDept> findByHospUpCityAreaId(String upAreaId) throws Exception;
    public List<AppHospDept> findByAreaCode(String areaCode) throws Exception;
    public List<AppHospDept> findByAreaCodeLike(String areaCode) throws Exception;
    public AppHospDept findByAreaCodeByOne(String areaCode) throws Exception;
    //修改医院信息
    public void modify(AppHospDept vo) throws Exception;

    public List<AddressHospEntity> findByAreaTsId(String upId) throws Exception;

    /**
     * 查询开启签约服务的医院
     * @return
     */
    public List<AppHospDept> findBySerState() throws Exception;

    /**
     * 查询areaCode不等于空
     * @return
     */
    public List<AppHospDept> findByAreaCodeNotNull() throws Exception;

    public List<AddressTreeHospEntity> findTreeSetting(String id) throws Exception;

    public List<AppHospDept> findByQvo(AppHospDeptQvo qvo) throws Exception;


    /**
     * cjw 查询 区县
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<CdAddressEntity> findByCpctiy(AppHospDeptQvo qvo) throws Exception;

    /**
     * cjw 签约授权
     */
    public List<AppHospDept> findauthorization(AppHospAuthorizationEntity vo)throws Exception ;

    /**
     * 查询本市上级医院
     * @param hospId
     * @return
     * @throws Exception
     */
    public List<AppHospDept> findHospByArea(String hospId) throws Exception;

    /**
     * 查询上级医院列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppUpHospTable> findUpHospList(AppHospDeptQvo qvo) throws Exception;

    public AppUpHospTable findOneUpHosp(String upId,String upHospId) throws Exception;

    public List<AppHospDept> findUpHospListRead(String areaId) throws Exception;

    public List<AppHospDept> findByAreaCodeLike(String areaCode,String findState) throws Exception;

    public List<AppHospDept> findHospByStreet(String areaCode,String type) throws Exception;

    public List<AppHospDept> findManageContByAreaCode(String[] areaCode) throws Exception;

    /**
     * 获取该区县下属的所有社区
     * WangCheng
     * @param upId
     * @param source
     * @return
     * @throws Exception
     */
    public List<AddressHospEntity> listAllCommunity(String upId,String source) throws Exception;

    /**
     * 机构数据导入
     * @param map
     * @return
     * @throws Exception
     */
    public String addImportExcelHosp(Map<Integer, String> map) throws Exception;

    /**
     * 根据机构编码查询机构数据
     * @param code
     * @return
     * @throws Exception
     */
    public AppHospDept findHospByCode(String code) throws Exception;

    /**
     * 根据条件查询（分页）
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<InternetHospOrgEntity> findNetOrgByQvo(InternetHospQvo qvo) throws Exception;
}
