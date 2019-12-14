package com.ylz.bizDo.cd.dao.impl;


import com.ylz.bizDo.cd.dao.CdCodeDao;
import com.ylz.bizDo.cd.entity.CdCodeEntity;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.vo.CdCodeQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppCodeEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppGroupEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppMeddleEntity;
import com.ylz.bizDo.jtapp.drVo.AppGroupVo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("codeDao")
@Transactional(rollbackForClassName={"Exception"})
public class CdCodeDaoImpl implements CdCodeDao {
	@Autowired
	public SysDao sysDao;
	
	//根据group 状态查询基础数据
	@Override
	public List<CdCode> findGroupList(String group,String state) throws Exception{
		HashMap map=new HashMap();
        map.put("group",group);
        map.put("state",state);
        String sql="SELECT * FROM CD_CODE a WHERE 1=1 AND a.code_group=:group ";
        if(StringUtils.isNotBlank(state)){
        	sql+=" AND a.code_state=:state";
        }
        sql += " ORDER BY a.code_group,a.code_sort ";
        List<CdCode> ls=sysDao.getServiceDo().findSqlMap(sql, map, CdCode.class);
        return ls;
	}

	//根据qvo 状态查询基础数据
	@Override
	public List<CdCode> findGroupListQvo(CdCodeQvo qvo) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "SELECT * FROM CD_CODE  as a WHERE 1=1 ";
		if(StringUtils.isNotBlank(qvo.getCdGroupName())){
			map.put("cdGroup", "%"+qvo.getCdGroupName()+"%");
			sql += " AND a.code_remark like :cdGroup";
		}
		if(StringUtils.isNotBlank(qvo.getCdName())){
			map.put("cdName", qvo.getCdName()+"%");
			sql += " AND a.code_title like :cdName";
		}
		if(StringUtils.isNotBlank(qvo.getCdState())){
			map.put("cdState", qvo.getCdState());
			sql += " AND a.code_state = :cdState";
		}
		sql += " AND a.code_group != 'jsAdmin' ";
		sql += " ORDER BY a.code_group,a.code_sort ";
		List ls = sysDao.getServiceDo().findSqlMap(sql, map, CdCode.class, qvo);
		return ls;
	}

	//根据组名,值获取code数据\
	@Override
	public CdCode findCodeGroupValue(String group, String value) throws Exception {
		HashMap map=new HashMap();
        map.put("group",group);
        map.put("value",value);
        String sql="SELECT * FROM CD_CODE a WHERE 1=1 AND a.code_group=:group AND a.code_value=:value";
        sql += " ORDER BY a.code_group,a.code_sort ";
        List<CdCode> ls=sysDao.getServiceDo().findCachSqlMap(sql, map, CdCode.class);
        if(ls!=null&&ls.size()>0){
        	return ls.get(0);
        }
        return null;
	}

	//根据组名,值获取code数据
	@Override
	public CdCode findCodeGroupValue(String group) throws Exception {
		HashMap map=new HashMap();
		map.put("group",group);
		String sql="SELECT * FROM CD_CODE a WHERE 1=1 AND a.code_group=:group ";
		sql += " ORDER BY a.code_group,a.code_sort ";
		List<CdCode> ls=sysDao.getServiceDo().findCachSqlMap(sql, map, CdCode.class);
		if(ls!=null&&ls.size()>0){
			return ls.get(0);
		}
		return null;
	}
	@Override
	public List<CdCodeEntity> findCmmCodeGroup(String strJson) throws Exception {
		HashMap map=new HashMap();
		String sql = "select code_remark codeRemark ,code_group codeGroup from CD_CODE where 1=1";
		if(StringUtils.isNotBlank(strJson)){
			map.put("strJson", "%"+strJson+"%");
			sql += " AND code_remark like :strJson";
		}
		sql += " GROUP BY code_remark,code_group";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, CdCodeEntity.class);
	}

	@Override
	public List<AppGroupEntity> findAppGroup(AppGroupVo vo) throws Exception {
		HashMap map=new HashMap();
		String sql = "select code_title title ,code_value value from CD_CODE where 1=1";
		if(StringUtils.isNotBlank(vo.getGroupCode())){
			map.put("code", vo.getGroupCode());
			sql += " AND code_group = :code";
		}
		if(StringUtils.isNotBlank(vo.getState())){
			map.put("state", vo.getState());
			sql += " AND code_state = :state";
		}
		if(StringUtils.isNotBlank(vo.getGroupName())){
			map.put("cdGroup", "%"+vo.getGroupName()+"%");
			sql += " AND code_remark like :cdGroup";
		}
		sql += " ORDER BY code_sort ";
		return sysDao.getServiceDo().findSqlMapRVo(sql, map, AppGroupEntity.class);
	}

	@Override
	public List<AppMeddleEntity> findMeddle(String group, String state) throws Exception {
		HashMap map=new HashMap();
		map.put("group",group);
		map.put("state",state);
		String sql="SELECT a.id id,a.code_title title,a.code_value value,a.code_state state FROM CD_CODE a WHERE 1=1 AND a.code_group=:group ";
		if(StringUtils.isNotBlank(state)){
			sql+=" AND a.code_state=:state";
		}
		sql += " ORDER BY a.code_group,a.code_sort ";
		List<AppMeddleEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, AppMeddleEntity.class);
		return ls;
	}

	@Override
	public List<AppMeddleEntity> findMeddlel(String group, String state) throws Exception {
		HashMap map=new HashMap();
		map.put("group",group);
		map.put("state",state);
		String sql="SELECT a.id id,a.code_title title,a.code_value value,a.code_state state FROM CD_CODE a WHERE 1=1 AND a.code_group=:group ";
		if(StringUtils.isNotBlank(state)){
			sql+=" AND a.code_state=:state";
		}
		sql +=" AND a.code_value <> '99'";
		sql += " ORDER BY a.code_group,a.code_sort ";
		List<AppMeddleEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, AppMeddleEntity.class);
		return ls;
	}


	@Override
	public List<CdCode> findGroupListTopSix(String group, String state) throws Exception {
		HashMap map=new HashMap();
		map.put("group",group);
		map.put("state",state);
		String sql="SELECT * FROM CD_CODE a WHERE 1=1 AND a.code_group=:group ";
		if(StringUtils.isNotBlank(state)){
			sql+=" AND a.code_state=:state";
		}
		sql += " ORDER BY a.code_group,a.code_sort LIMIT 6";
		List<CdCode> ls=sysDao.getServiceDo().findSqlMap(sql, map, CdCode.class);
		return ls;
	}

	/**
	 * Dmao
	 * 签约判断
	 * @param code
	 * @return
	 */
	@Override
	public CdCode findSign(String code) throws Exception{
		HashMap map=new HashMap();
		if(StringUtils.isNotBlank(code)){
			String sql=" SELECT * from  CD_CODE c  where c.code_value ='"+code+"' and c.code_group='SignCode' ";
			List<CdCode> ls=sysDao.getServiceDo().findSqlMap(sql, map, CdCode.class);
			if(ls!=null && ls.size()>0){
				return ls.get(0);
			}
		}
		return null;
	}

	/**
	 * 查询亲人关系
	 * @return
	 */
	@Override
	public List<AppCodeEntity> findCodeFamily() throws Exception {
		String sql = "SELECT ID id," +
				"CODE_VALUE codeValue," +
				"CODE_TITLE codeTitle," +
				"CODE_GROUP codeGroup," +
				"CODE_REMARK codeRemark," +
				"CODE_SORT codeSort," +
				"CODE_STATE codeState," +
				"CODE_TITLE codeSex " +
				"FROM CD_CODE WHERE 1=1 AND CODE_GROUP='familyRelation' AND CODE_STATE = '1'";
		List<AppCodeEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,new HashMap<String, Object>(),AppCodeEntity.class);
		return list;
	}

	@Override
	public List<CdCode> findJsGroupListQvo(CdCodeQvo qvo) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "SELECT * FROM CD_CODE  as a WHERE 1=1 ";
		if(StringUtils.isNotBlank(qvo.getCdGroupName())){
			map.put("cdGroup", "%"+qvo.getCdGroupName()+"%");
			sql += " AND a.code_remark like :cdGroup";
		}
		if(StringUtils.isNotBlank(qvo.getCdName())){
			map.put("cdName", qvo.getCdName()+"%");
			sql += " AND a.code_title like :cdName";
		}
		if(StringUtils.isNotBlank(qvo.getCdState())){
			map.put("cdState", qvo.getCdState());
			sql += " AND a.code_state = :cdState";
		}
		if(StringUtils.isNotBlank(qvo.getCdValue())){
			map.put("cdValue",qvo.getCdValue());
			sql += " AND a.code_value = :cdValue ";
		}
		sql += " AND a.code_group ='jsAdmin' ";
		sql += " ORDER BY a.code_group,a.code_sort ";
		List ls = sysDao.getServiceDo().findSqlMap(sql, map, CdCode.class, qvo);
		return ls;
	}
}
