package com.ylz.bizDo.cd.dao;

import com.ylz.bizDo.cd.entity.AddressHospEntity;
import com.ylz.bizDo.cd.entity.AddressTreeEntity;
import com.ylz.bizDo.cd.entity.CdAddressEntity;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressConfiguration;
import com.ylz.bizDo.cd.vo.CdAddressSvo;
import com.ylz.bizDo.cd.vo.CdAddressVo;
import com.ylz.bizDo.jtapp.commonEntity.InternetHospAreaEntity;
import com.ylz.bizDo.jtapp.commonVo.AppAddressQvo;
import com.ylz.bizDo.jtapp.gaiRuiEntity.GaiRuiAreaEntity;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.List;

public interface CdAddressDao {
	public List<CdAddressSvo> findList(CdAddressVo qvo) throws Exception;//分页查询
	public List<CdAddressSvo> findAll() throws Exception;//查询全部
	public List<CdAddressSvo> findByNum() throws Exception;
	/**
	 * 查询编号是否 存在
	 * @param code
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public CdAddress findIsCdAddress(String code, String id) throws Exception;
	public List<CdAddressSvo> findById(String id) throws Exception;
	/**
	 * 根据编号长度查找对应行政级别
	 * @param num
	 * @return
	 */
	public List<CdAddressSvo> findGroupList(String num) throws Exception;
	/**
	 * 根据父id查询子类数据
	 * @param pid
	 * @return
	 */
	public List<CdAddressSvo> findByPidList(String pid) throws Exception;


    public List<CdAddressSvo> findByNumLength(int num) throws Exception;

	/**
	 * 根据父id查询子类数据
	 * @param upId
	 * @return
	 */
	public List<CdAddressEntity> findByUpIdOrNot(String upId) throws Exception;

	public List<CdAddressEntity> findByUpIdOrNot(String upId,String source) throws Exception;

	public List<AddressHospEntity> findByUpIdOrNotTs(String upId) throws Exception;

	public List<CdAddress> findByState(String state) throws Exception;

	public CdAddress findByCode(String patientProvinceName) throws Exception;

	public CdAddressConfiguration findByCodeJw(String patientProvinceName) throws Exception;

	public List<CdAddressSvo> findCity() throws Exception;

	public CdAddress findByCacheCode(String code) throws Exception;

	public  List<CdAddress> findUpCasheAreaCode(String code) throws Exception;

	public  List<CdAddress> findUpCasheAreaLevel(String level) throws Exception;

    public CdAddress findByName(String patientProvinceName,String upId) throws Exception;

    public List<AddressTreeEntity> findTreeSetting(String id) throws Exception;

	public int findByupIdCount(String id) throws Exception;

	public List<AddressHospEntity> findAssociateJw(String upId,String source) throws Exception;

	public List<AddressHospEntity> findAssociateJw2(String upId,String source) throws Exception;
	//跟对照表关联判断当前机构city_area_id是否正确
	public List<AddressHospEntity> findUpIdAssociateJw(String cityAreaId) throws Exception;
	//查询自身及其子目录
	public List<CdAddressEntity> findSelfAndChildren(String areaCode) throws Exception;
	//直接取对照表里的居委会
	public List<AddressHospEntity> findConfiguration(String areaCode) throws Exception;

	public CdAddressConfiguration findUniqueAdressByConfig(String areaCode) throws Exception;

	public CdAddress findBySname(String nameO,String nameT) throws Exception;

	/**
	 * 通过上级行政编码获取地址信息
	 * WangCheng
	 * @param upId
	 * @return
	 */
	public CdAddress findByUpId(String upId) throws Exception;

	/**
	 * 查询下级行政区划
	 *
	 * @param areaCode 行政区划编码
	 * @return
	 */
	public List<CdAddress> findSubAreas(String areaCode) throws Exception;

	/**
	 * 根据全称、简称、上级code查询
	 * @param nameO
	 * @param nameT
	 * @param upCode
	 * @return
	 */
	public CdAddress findByNameOrUpCode(String nameO,String nameT,String upCode) throws Exception;

	/**
	 * 根据区域编码查询村、居委会
	 *
	 * @param areaCode 区域编码
	 * @return
	 * @throws Exception
	 */
	public List<CdAddress> findCommitteeByAreaCode(String areaCode) throws Exception;

	/**
	 * 根据上级行政区划查询区域
	 * @param upId
	 * @return
	 * @throws Exception
	 */
	public List<InternetHospAreaEntity> findByNetUpId(String upId) throws Exception;

	/**
	 * 根据上级行政区划查询区域列表（盖瑞）
	 * @param upId
	 * @return
	 * @throws Exception
	 */
	public List<GaiRuiAreaEntity> findListByUpId(String upId) throws Exception;

	/**
	 * 根据区域名称和上级行政区划查询
	 * @param areaSName
	 * @param upId
	 * @return
	 * @throws Exception
	 */
	public CdAddress findByNameAndUp(String areaSName,String upId) throws Exception;

	/**
	 * 根据qvo查询区域列表
	 * @param qvo
	 * @return
	 * @throws Exception
	 */
	public List<CdAddressEntity> findByQvo(AppAddressQvo qvo) throws Exception;
}
