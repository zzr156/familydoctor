package com.ylz.bizDo.cd.dao.impl;

import com.ylz.bizDo.cd.dao.CdAddressDao;
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
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.util.AreaUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("cdAddressDao")
@Transactional(rollbackForClassName={"Exception"})
public class CdAddressDaoImpl implements CdAddressDao{
	
	@Autowired
	public SysDao sysDao;
	/**
	 * 查询顶级行政信息
	 */
	@Override
	public List<CdAddressSvo> findAll() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		//map.put("pid", pid);
		String sql ="SELECT a.CITY_AREA_ID id,a.UP_CITY_AREA_ID pid,a.AREA_CODE ctcode,a.AREA_NAME areaName,a.AREA_SNAME areaSname FROM CP_CITY_AREA a  where 1=1";
		/*if(pid!=""||pid!=null){
			sql+="WHERE a.UP_CITY_AREA_ID=:pid";
		}else{
			sql+="WHERE a.UP_CITY_AREA_ID IS NULL";
		}*/
		sql+=" AND a.UP_CITY_AREA_ID IS NULL AND a.STATE ='1' ";
		sql+=" GROUP BY a.UP_CITY_AREA_ID,a.CITY_AREA_ID ORDER BY a.AREA_CODE";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, CdAddressSvo.class);
	}
	
	public List<CdAddressSvo> findByNum() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		//map.put("pid", pid);
		String sql ="SELECT a.CITY_AREA_ID id,a.UP_CITY_AREA_ID pid,a.AREA_CODE ctcode,a.AREA_NAME areaName,a.AREA_SNAME areaSname FROM CP_CITY_AREA a  where 1=1";
		/*if(pid!=""||pid!=null){
			sql+="WHERE a.UP_CITY_AREA_ID=:pid";
		}else{
			sql+="WHERE a.UP_CITY_AREA_ID IS NULL";
		}*/
		sql +=" AND LENGTH(a.AREA_CODE)= 4";
		sql+=" GROUP BY a.UP_CITY_AREA_ID,a.CITY_AREA_ID ORDER BY a.AREA_CODE";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, CdAddressSvo.class);
	}
	
	/**
	 * 验证编码是否已使用
	 */
	@Override
	public CdAddress findIsCdAddress(String code, String id) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="SELECT a.AREA_CODE ctcode FROM cp_city_area a  where 1=1";
		if(StringUtils.isNotBlank(code)){
			map.put("Ccode", code);
			sql+=" AND a.AREA_CODE=:Ccode";
		}
		if (StringUtils.isNotBlank(id)){
			map.put("id", id);
			sql+=" AND a.CITY_AREA_ID =:id";  
		}
		List ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, CdAddress.class);
		if(ls!=null && ls.size() > 0){
			return (CdAddress) ls.get(0);
		}
		return null;
	}
	/**
	 * 分页查询树
	 */
	@Override
	public List<CdAddressSvo> findList(CdAddressVo qvo) throws Exception {
		String sql ="SELECT a.CITY_AREA_ID id,a.UP_CITY_AREA_ID pid,a.AREA_CODE ctcode,a.AREA_NAME areaName,a.AREA_SNAME areaSname FROM CP_CITY_AREA a  where a.state='1' and 1=1 ";
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(qvo.getCityName())){
			map.put("cityname", "%"+qvo.getCityName()+"%"); 
			sql+="ADD a.AREA_NAME LIKE:cityname OR a.AREA_SNAME LIKE:cityname";
		}
		if(StringUtils.isNotBlank(qvo.getUpId())){
			map.put("upId",qvo.getUpId());
			sql += " AND a.UP_CITY_AREA_ID = :upId ";
		}else{
			sql += " AND a.UP_CITY_AREA_ID IS NULL ";
		}
		sql+=" GROUP BY a.UP_CITY_AREA_ID,a.CITY_AREA_ID ORDER BY a.AREA_CODE";
		List<CdAddressSvo> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,CdAddressSvo.class);
		return ls;
	}
	/**
	 * 查询子目录
	 */
	@Override
	public List<CdAddressSvo> findById(String id) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "SELECT a.CITY_AREA_ID id,a.UP_CITY_AREA_ID pid,a.AREA_CODE ctcode,a.AREA_NAME areaName,a.AREA_SNAME areaSname FROM CP_CITY_AREA a  where a.UP_CITY_AREA_ID=:upId";
		map.put("upId",id);
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, CdAddressSvo.class);
	}
	@Override
	public List<CdAddressSvo> findGroupList(String num) throws Exception {
		HashMap map=new HashMap();
        map.put("num",num);
        String sql="SELECT a.CITY_AREA_ID id,a.AREA_SNAME areaSname FROM cp_city_area a  where 1=1 AND length(a.AREA_CODE)=:num";
        sql += " ORDER BY a.AREA_CODE";
        List<CdAddressSvo> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, CdAddressSvo.class);
        return ls;
	}
	@Override
	public List<CdAddressSvo> findByPidList(String pid) throws Exception {
		HashMap map=new HashMap();
		String sql = "SELECT a.CITY_AREA_ID id,a.AREA_SNAME areaSname FROM cp_city_area a  where 1=1";
		if(StringUtils.isNotBlank(pid)){
			map.put("pid",pid);
			sql +=" AND a.UP_CITY_AREA_ID =:pid";
		}else{
			sql +=" AND a.UP_CITY_AREA_ID IS NULL";
		}
        sql += " ORDER BY a.AREA_CODE";
        List<CdAddressSvo> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, CdAddressSvo.class);
        return ls;
	}

	@Override
	public List<CdAddressSvo> findByNumLength(int num) throws Exception {
		HashMap map=new HashMap();
		map.put("num",num);
		map.put("state","1");
		String sql="SELECT a.CITY_AREA_ID id,a.AREA_SNAME areaSname FROM CP_CITY_AREA a  where 1=1 AND LENGTH(AREA_CODE) =:num AND STATE= :state ";
		sql += " ORDER BY a.AREA_CODE";
		List<CdAddressSvo> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, CdAddressSvo.class);
		return ls;
	}


	@Override
	public List<CdAddressEntity> findByUpIdOrNot(String upId) throws Exception {
		HashMap map = new HashMap();
		map.put("state", CommonEnable.QIYONG.getValue());
		String sql = "SELECT\n" +
				"\tt.CITY_AREA_ID id,\n" +
				"\tt.AREA_NAME areaName,\n" +
				"\tt.AREA_SNAME areaSname,\n" +
				"\t'1' areaState\n" +
				"FROM\n" +
				"\tCP_CITY_AREA t\n" +
				" WHERE 1=1 ";
		if(StringUtils.isNotBlank(upId)){
			map.put("upId",upId);
			sql += " AND T.UP_CITY_AREA_ID = :upId ";
		}else{
			sql += " AND T.UP_CITY_AREA_ID IS NULL ";
		}
		sql += " AND T.STATE =:state ORDER BY T.AREA_CODE  ";
		List<CdAddressEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, CdAddressEntity.class);
		return ls;
	}


	public List<CdAddressEntity> findByUpIdOrNot(String upId,String source) throws Exception {
		HashMap map = new HashMap();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT\n" +
				"IF (\n" +
				"\tt2.area_code_jw IS NULL\n" +
				"\tOR trim(t2.AREA_CODE_JW) = '',\n" +
				"\tt1.CITY_AREA_ID,\n" +
				"\tt2.area_code_jw\n" +
				") id,\n" +
				"IF (\n" +
				"\tt2.area_name_jw IS NULL\n" +
				"\tOR trim(t2.AREA_CODE_JW) = '',\n" +
				"\tt1.area_sname,\n" +
				"\tt2.AREA_NAME_JW\n" +
				") areaSname,\n" +
				"IF (\n" +
				"\tt2.area_name_jw IS NULL\n" +
				"\tOR trim(t2.AREA_CODE_JW) = '',\n" +
				"\tt1.area_name,\n" +
				"\tt2.AREA_NAME_JW\n" +
				") areaName,\n" +
				" '1' areaState,\n" +
				"IF (\n" +
				"\tt2.AREA_CODE_JW IS NULL\n" +
				"\tOR trim(t2.AREA_CODE_JW) = '',\n" +
				"\t'1',\n" +
				"\t'2'\n" +
				") areaSource \n" +
				"FROM\n" +
				"\tCP_CITY_AREA t1\n" +
				"LEFT JOIN CP_CITY_AREA_CONFIGURATION t2  ON t1.AREA_CODE = t2.CITY_AREA_ID \n" +
				"WHERE\n" +
				"\t1 = 1");
		if(StringUtils.isNotBlank(source) && "2".equals(source)){
			if(StringUtils.isNotBlank(upId)){
				map.put("upId",upId);
				sql.append(" AND T2.UP_CITY_AREA_ID_JW = :upId");
			}else{
				sql.append(" AND T1.UP_CITY_AREA_ID IS NULL ");
			}
			sql.append(" AND T1.STATE =:state ORDER BY T1.city_area_id ");
		}else{
			if(StringUtils.isNotBlank(upId)){
				map.put("upId",upId);
				sql.append(" AND T1.UP_CITY_AREA_ID = :upId");
			}else{
				sql.append(" AND T1.UP_CITY_AREA_ID IS NULL ");
			}
			sql.append(" AND T1.STATE =:state ORDER BY T1.city_area_id ");
		}
		map.put("state", CommonEnable.QIYONG.getValue());
		List<CdAddressEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, CdAddressEntity.class);
		return ls;
	}

	@Override
	public List<CdAddress> findByState(String state) throws Exception {
		HashMap map = new HashMap();
		map.put("state", state);
		String sql = "SELECT * FROM\n" +
				"\tCP_CITY_AREA t\n" +
				" WHERE 1=1 ";
		sql += " AND T.STATE =:state AND AREA_POPULATION IS NULL ORDER BY T.AREA_CODE  ";
		List<CdAddress> ls=sysDao.getServiceDo().findSqlMap(sql,map,CdAddress.class);
		return  ls;
	}

	@Override
	public CdAddress findByCode(String patientProvinceName) throws Exception {
		return (CdAddress) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
				.createCriteria(CdAddress.class)
				.add(Restrictions.eq("id", patientProvinceName))
				.uniqueResult();
	}

	@Override
	public CdAddressConfiguration findByCodeJw(String patientProvinceName) throws Exception{
		return (CdAddressConfiguration) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
				.createCriteria(CdAddressConfiguration.class)
				.add(Restrictions.eq("areaCodeJw", patientProvinceName))
				.uniqueResult();
	}

	@Override
	public List<CdAddressSvo> findCity() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pid","350000000000");
		map.put("state","1");
		String sql = " SELECT a.CITY_AREA_ID id," +
				"a.AREA_SNAME areaSname FROM CP_CITY_AREA a  where 1=1 AND STATE= :state ";
		sql +=" AND a.UP_CITY_AREA_ID =:pid";
		sql += " ORDER BY a.AREA_CODE";
		List<CdAddressSvo> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,CdAddressSvo.class);
		return ls;
	}

	@Override
	public CdAddress findByCacheCode(String code) throws Exception {
		return (CdAddress) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
				.createCriteria(CdAddress.class)
				.add(Restrictions.eq("id", code))
				.setCacheable(true)
				.uniqueResult();
	}

	@Override
	public  List<CdAddress> findUpCasheAreaCode(String code) throws Exception{
		return sysDao.getServiceDo().getSessionFactory().getCurrentSession()
				.createCriteria(CdAddress.class)
				.add(Restrictions.eq("pid", code))
				.setCacheable(true)
				.list();
	}

	@Override
	public  List<CdAddress> findUpCasheAreaLevel(String code) throws Exception{
		return sysDao.getServiceDo().getSessionFactory().getCurrentSession()
				.createCriteria(CdAddress.class)
				.add(Restrictions.eq("level", code))
				.setCacheable(true)
				.list();
	}

	@Override
	public CdAddress findByName(String patientProvinceName,String upId) throws Exception {
		Criteria criteria = sysDao.getServiceDo().getSessionFactory().getCurrentSession().createCriteria(CdAddress.class);
		if(StringUtils.isNotBlank(upId)){
			criteria.add(Restrictions.eq("pid", upId));
		}
		return (CdAddress) criteria.add(Restrictions.eq("areaSname", patientProvinceName))
				.setCacheable(true)
				.uniqueResult();
	}

	@Override
	public List<AddressHospEntity> findByUpIdOrNotTs(String upId) throws Exception {
			HashMap map = new HashMap();
			map.put("state", CommonEnable.QIYONG.getValue());
			String sql = "SELECT\n" +
					"\tt.AREA_CODE id,\n" +
					"\tt.AREA_SNAME name,\n" +
					"\t'1' state,\n" +
					"\t'1' level\n" +
					"FROM\n" +
					"\tCP_CITY_AREA t\n" +
					" WHERE 1=1 ";
			if(StringUtils.isNotBlank(upId)){
				map.put("upId",upId);
				sql += " AND T.UP_CITY_AREA_ID = :upId ";
			}else{
				sql += " AND T.UP_CITY_AREA_ID IS NULL ";
			}
			sql += " AND T.STATE =:state ORDER BY T.AREA_CODE  ";
			List<AddressHospEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, AddressHospEntity.class);
			return ls;
		}


	@Override
	public List<AddressTreeEntity> findTreeSetting(String id) throws Exception {
		HashMap map = new HashMap();
		map.put("state", CommonEnable.QIYONG.getValue());
		String sql = "SELECT\n" +
				"\tt.CITY_AREA_ID id,\n" +
				"\tt.AREA_SNAME name\n" +
				"FROM\n" +
				"\tCP_CITY_AREA t\n" +
				" WHERE 1=1 ";
		if(StringUtils.isNotBlank(id)){
			map.put("upId",id);
			sql += " AND T.UP_CITY_AREA_ID = :upId ";
		}else{
			sql += " AND T.UP_CITY_AREA_ID IS NULL ";
		}
		sql += " AND T.STATE =:state ORDER BY T.AREA_CODE  ";
		List<AddressTreeEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, AddressTreeEntity.class);
		return ls;
	}


	@Override
	public int findByupIdCount(String id) throws Exception {
		return ((Number)sysDao.getServiceDo().getSessionFactory().getCurrentSession()
				.createCriteria(CdAddress.class)
				.add(Restrictions.eq("pid", id))
				.setCacheable(true)
				.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

	@Override
	public List<AddressHospEntity> findAssociateJw(String upId,String source) throws Exception {
		HashMap map = new HashMap();
		StringBuilder sql = new StringBuilder();
		if(StringUtils.isNotBlank(source) && "2".equals(source)){
			sql.append("SELECT if(t2.area_code_jw is null or trim(t2.AREA_CODE_JW)='',t1.CITY_AREA_ID,t2.area_code_jw) id,if(t2.area_name_jw is null or trim(t2.AREA_CODE_JW)='',t1.area_sname,t2.AREA_NAME_JW) name,'1' state,t1.level_name level, " +
					" if(t2.AREA_CODE_JW is null or trim(t2.AREA_CODE_JW)='' ,'1','2') source" +
					" FROM CP_CITY_AREA t1 left join CP_CITY_AREA_CONFIGURATION t2 " +
					"on t1.AREA_CODE=t2.CITY_AREA_ID " +
					"where 1=1");
			if(StringUtils.isNotBlank(upId)){
				map.put("upId",upId);
				sql.append(" AND T2.UP_CITY_AREA_ID_JW = :upId");
			}else{
				sql.append(" AND T1.UP_CITY_AREA_ID IS NULL ");
			}
			sql.append(" AND T1.STATE =:state ORDER BY T1.city_area_id ");
		}else{
			sql.append("SELECT if(t2.area_code_jw is null or trim(t2.AREA_CODE_JW)='',t1.CITY_AREA_ID,t2.area_code_jw) id,if(t2.area_name_jw is null or trim(t2.AREA_CODE_JW)='',t1.area_sname,t2.AREA_NAME_JW) name,'1' state,t1.level_name level, " +
					" if(t2.AREA_CODE_JW is null or trim(t2.AREA_CODE_JW)='' ,'1','2') source" +
					" FROM CP_CITY_AREA t1 left join CP_CITY_AREA_CONFIGURATION t2 " +
					"on t1.AREA_CODE=t2.CITY_AREA_ID " +
					"where 1=1");
			if(StringUtils.isNotBlank(upId)){
				map.put("upId",upId);
				sql.append(" AND T1.UP_CITY_AREA_ID = :upId");
			}else{
				sql.append(" AND T1.UP_CITY_AREA_ID IS NULL ");
			}
			sql.append(" AND T1.STATE =:state ORDER BY T1.city_area_id ");
		}
		map.put("upId",upId);
		map.put("state", CommonEnable.QIYONG.getValue());
//		String sql = "SELECT if(t2.area_code_jw is null or trim(t2.AREA_CODE_JW)='',t1.CITY_AREA_ID,t2.area_code_jw) id,if(t2.area_name_jw is null or trim(t2.AREA_CODE_JW)='',t1.area_sname,t2.AREA_NAME_JW) name,'1' state,'1' level, " +
//				" if(t2.AREA_CODE_JW is null or trim(t2.AREA_CODE_JW)='' ,'1','2') source" +
//				" FROM CP_CITY_AREA t1 left join CP_CITY_AREA_CONFIGURATION t2 " +
//				"on t1.AREA_CODE=t2.CITY_AREA_ID " +
//				"where 1=1";
		List<AddressHospEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AddressHospEntity.class);
		return ls;
	}

	public List<AddressHospEntity> findAssociateJw2(String upId,String source) throws Exception {
		HashMap map = new HashMap();
		StringBuilder sql = new StringBuilder();
		if(StringUtils.isNotBlank(source) && "2".equals(source)){
			sql.append("SELECT if(t2.area_code_jw is null or trim(t2.AREA_CODE_JW)='',t1.CITY_AREA_ID,t2.area_code_jw) id,if(t2.area_name_jw is null or trim(t2.AREA_CODE_JW)='',t1.area_sname,t2.AREA_NAME_JW) name,'1' state,t1.level_name level, " +
					" if(t2.AREA_CODE_JW is null or trim(t2.AREA_CODE_JW)='' ,'1','2') source" +
					" FROM CP_CITY_AREA t1 left join CP_CITY_AREA_CONFIGURATION t2 " +
					"on t1.AREA_CODE=t2.CITY_AREA_ID " +
					"where 1=1");
			if(StringUtils.isNotBlank(upId)){
				map.put("upId",upId);
				sql.append(" AND T2.UP_CITY_AREA_ID_JW = :upId");
			}else{
				sql.append(" AND T1.UP_CITY_AREA_ID IS NULL ");
			}
			sql.append(" AND T1.STATE =:state ORDER BY T1.city_area_id ");
		} else{
			sql.append("SELECT t1.CITY_AREA_ID id, t1.area_sname name, '1' state, t1.level_name level, '1' source " +
					" FROM CP_CITY_AREA t1 left join CP_CITY_AREA_CONFIGURATION t2 " +
					" on t1.AREA_CODE = t2.CITY_AREA_ID " +
					" where 1 = 1 ");
			if(StringUtils.isNotBlank(upId)){
				map.put("upId",upId);
				sql.append(" AND T1.UP_CITY_AREA_ID = :upId");
			}else{
				sql.append(" AND T1.UP_CITY_AREA_ID IS NULL ");
			}
			sql.append(" AND T1.STATE =:state ORDER BY T1.city_area_id ");
		}
		map.put("upId",upId);
		map.put("state", CommonEnable.QIYONG.getValue());
		List<AddressHospEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AddressHospEntity.class);
		return ls;
	}

	@Override
	public List<AddressHospEntity> findUpIdAssociateJw(String cityAreaId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select IF (" +
				"area_code_jw IS NULL" +
				"OR trim(AREA_CODE_JW) = ''," +
				"CITY_AREA_ID," +
				"area_code_jw" +
				") id, IF (" +
				"AREA_CODE_JW IS NULL" +
				" OR trim(AREA_CODE_JW) = ''," +
				"'1'," +
				"'2'" +
				") source from CP_CITY_AREA_CONFIGURATION where city_area_id =:cityAreaId");
		HashMap map = new HashMap();
		return null;
	}

	@Override
	public List<CdAddressEntity> findSelfAndChildren(String areaCode) throws Exception {
		String sql = "select city_area_id id, area_sname areaSname from cp_city_area " +
				"	where city_area_id =:id or up_city_area_id =:id order by level_name asc";
		HashMap map = new HashMap();
		map.put("id",areaCode);
		List<CdAddressEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,CdAddressEntity.class);
		if(list.size() > 0 ){
			return list;
		}
		return null;
	}

	@Override
	public List<AddressHospEntity> findConfiguration(String areaCode) throws Exception {
		//两种情况如果第一种查不到再用第二种，第一种根据area_code_jw作为查询条件,第二种用city_area_id作查询条件
		String sql = "select area_code_jw id,area_name_jw name from cp_city_area_configuration" +
				" where up_city_area_id_jw = (select area_code_jw from cp_city_area_configuration where area_code_jw =:areaCode)";
		HashMap map = new HashMap();
		map.put("areaCode",areaCode);
		List<AddressHospEntity> list = new ArrayList<>();
		list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AddressHospEntity.class);
		if(list.size() > 0){
			return list;
		}
		sql = "select area_code_jw id,area_name_jw name from cp_city_area_configuration" +
				" where up_city_area_id_jw = (select area_code_jw from cp_city_area_configuration where city_area_id =:areaCode)";
		list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AddressHospEntity.class);
		if(list.size() > 0){
			return list;
		}
		return null;
	}

	@Override
	public CdAddressConfiguration findUniqueAdressByConfig(String areaCode) throws Exception {
		return (CdAddressConfiguration) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
				.createCriteria(CdAddressConfiguration.class)
				.add(Restrictions.eq("areaCodeJw", areaCode))
				.setCacheable(true)
				.uniqueResult();
	}

	@Override
	public CdAddress findBySname(String nameO, String nameT) throws Exception {
		Map<String,Object> map = new HashMap<>();
		String sql = "SELECT * FROM CP_CITY_AREA WHERE 1=1 ";
		if(StringUtils.isNotBlank(nameO)){
			map.put("nameO",nameO);
			sql += " AND AREA_SNAME =:nameO ";
		}
		if(StringUtils.isNotBlank(nameT)){
			map.put("nameT",nameT);
			sql += "OR AREA_SNAME =:nameT ";
		}
		List<CdAddress> list = sysDao.getServiceDo().findSqlMap(sql,map,CdAddress.class);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 通过上级行政编码获取地址信息
	 * WangCheng
	 * @param upId
	 * @return
	 */
	@Override
	public CdAddress findByUpId(String upId) throws Exception{
		Criteria criteria = this.sysDao.getServiceDo().getSessionFactory().getCurrentSession().createCriteria(CdAddress.class);
		criteria.add(Restrictions.eq("pid",upId));
		CdAddress cdAddress = (CdAddress)criteria.uniqueResult();
		return cdAddress;
	}

	/**
	 * 查询下级行政区划
	 *
	 * @param areaCode 行政区划编码
	 * @return
	 */
	public List<CdAddress> findSubAreas(String areaCode)  throws Exception{
		Map<String, Object> map = new HashMap<>();
		String sql = "select * from CP_CITY_AREA where 1 = 1 ";
		if (StringUtils.isNotBlank(areaCode)) {
			sql += " and up_city_area_id =:areaCode ";
			map.put("areaCode", areaCode);
		}
		return sysDao.getServiceDo().findSqlMap(sql, map, CdAddress.class);
	}

	@Override
	public CdAddress findByNameOrUpCode(String nameO, String nameT, String upCode) throws Exception {
		Map<String,Object> map = new HashMap<>();
		String sql = "SELECT * FROM CP_CITY_AREA WHERE 1=1 ";
		if(StringUtils.isNotBlank(nameO)){//全称查询
			map.put("nameO",nameO);
			sql += " AND AREA_NAME =:nameO ";
		}
		if(StringUtils.isNotBlank(nameT)){//简称查询
			map.put("nameT",nameT);
			sql += " AND AREA_SNAME =:nameT ";
		}
		if(StringUtils.isNotBlank(upCode)){
			map.put("UP_CITY_AREA_ID",upCode);
			sql += " AND UP_CITY_AREA_ID =:UP_CITY_AREA_ID ";
		}
		List<CdAddress> list = sysDao.getServiceDo().findSqlMap(sql,map,CdAddress.class);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<CdAddress> findCommitteeByAreaCode(String areaCode) throws Exception {
		Map<String, Object> map = new HashMap<>();
		String sql = " select * from CP_CITY_AREA where LEVEL_NAME = '5' and STATE = '1' ";
		if (StringUtils.isNotBlank(areaCode)) {
			sql += " and AREA_CODE like :areaCode ";
			map.put("areaCode", AreaUtils.getAreaCodePrefix(areaCode) + "%");
		}
		List<CdAddress> list = sysDao.getServiceDo().findSqlMap(sql, map, CdAddress.class);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public List<InternetHospAreaEntity> findByNetUpId(String upId) throws Exception {
		HashMap map = new HashMap();
		map.put("state", CommonEnable.QIYONG.getValue());
		String sql = "SELECT\n" +
				"\tt.CITY_AREA_ID id,\n" +
				"\tt.AREA_NAME areaName,\n" +
				"\tt.AREA_SNAME areaSname,\n" +
				"\tt.STATE areaState," +
				"\tt.LEVEL_NAME level\n" +
				"FROM\n" +
				"\tCP_CITY_AREA t\n" +
				" WHERE 1=1 ";
		if(StringUtils.isNotBlank(upId)){
			map.put("upId",upId);
			sql += " AND T.UP_CITY_AREA_ID = :upId ";
		}else{
			sql += " AND T.UP_CITY_AREA_ID IS NULL ";
		}
		sql += " AND T.STATE =:state ORDER BY T.AREA_CODE  ";
		List<InternetHospAreaEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, InternetHospAreaEntity.class);
		return ls;
	}

	@Override
	public List<GaiRuiAreaEntity> findListByUpId(String upId) throws Exception {
		HashMap map = new HashMap();
		map.put("state", CommonEnable.QIYONG.getValue());
		String sql = "SELECT\n" +
				"\tt.CITY_AREA_ID areaId,\n" +
				"\tt.AREA_NAME areaAllName,\n" +
				"\tt.AREA_SNAME areaName,\n" +
				"\tt.UP_CITY_AREA_ID upId\n" +
				"FROM\n" +
				"\tCP_CITY_AREA t\n" +
				" WHERE 1=1 ";
		if(StringUtils.isNotBlank(upId)){
			map.put("upId",upId);
			sql += " AND T.UP_CITY_AREA_ID = :upId ";
		}else{
			sql += " AND T.UP_CITY_AREA_ID IS NULL ";
		}
		sql += " AND T.STATE =:state ORDER BY T.AREA_CODE  ";
		List<GaiRuiAreaEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, GaiRuiAreaEntity.class);
		return ls;
	}

	@Override
	public CdAddress findByNameAndUp(String areaSName, String upId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("sName","%"+areaSName+"%");
		String sql = "SELECT * FROM CP_CITY_AREA WHERE 1=1 AND AREA_SNAME like :sName ";
		if (StringUtils.isNotBlank(upId)) {
			map.put("upId",upId);
			sql += " AND UP_CITY_AREA_ID =:upId ";
		}
		List<CdAddress> list = sysDao.getServiceDo().findSqlMap(sql,map,CdAddress.class);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据qvo查询区域列表
	 * @param qvo
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<CdAddressEntity> findByQvo(AppAddressQvo qvo) throws Exception {
		HashMap map = new HashMap();
		map.put("state", CommonEnable.QIYONG.getValue());
		String sql = "SELECT\n" +
				"\tt.CITY_AREA_ID id,\n" +
				"\tt.AREA_NAME areaName,\n" +
				"\tt.AREA_SNAME areaSname,\n" +
				"\t'1' areaState\n" +
				"FROM\n" +
				"\tCP_CITY_AREA t\n" +
				" WHERE 1=1 ";
		if(StringUtils.isNotBlank(qvo.getUpId())){
			map.put("upId",qvo.getUpId());
			sql += " AND T.UP_CITY_AREA_ID = :upId ";
		}else{
			sql += " AND T.UP_CITY_AREA_ID IS NULL ";
		}
		if(StringUtils.isNotBlank(qvo.getCityCode())){
			map.put("cityCode",qvo.getCityCode()+"%");
			sql += " AND t.CITY_AREA_ID LIKE :cityCode ";
		}
		sql += " AND T.STATE =:state ORDER BY T.AREA_CODE  ";
		List<CdAddressEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, CdAddressEntity.class);
		return ls;
	}
}
