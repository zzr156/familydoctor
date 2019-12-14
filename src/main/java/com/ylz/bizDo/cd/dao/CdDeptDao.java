package com.ylz.bizDo.cd.dao;


import com.ylz.bizDo.cd.po.CdDept;
import com.ylz.bizDo.cd.po.CdRole;
import com.ylz.bizDo.cd.vo.DeptQvo;
import com.ylz.bizDo.cd.vo.DeptSelect;
import com.ylz.bizDo.cd.vo.DeptSvo;
import com.ylz.bizDo.cd.vo.DeptVo;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.List;


public interface CdDeptDao {
	public List<CdDept> findAll() throws Exception;//查询全部
	public List<CdDept> findByState() throws Exception;//查询全部
	public List<DeptSvo> findSByState() throws Exception;//查询全部
	public List<CdRole> findRole() throws Exception;//查询角色表
	public List<CdDept> findmohu(DeptQvo vo) throws Exception;
	//public List<DeptSvo> findById(String id);//根据id查
	public List<CdDept> finRM(String id) throws Exception;
	public List<DeptSvo> findByType(String deptType) throws Exception;
	public List<DeptSvo> findById(String id) throws Exception;
	/**
	 * 查询当前排序
	 */
	public  String findsort(String sid) throws Exception;
	/**
	 * 查询编号是否 存在
	 * @param userName
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public CdDept findIsCdDept(String snumber, String id,String baseId) throws Exception;
	/**
	 * 查询父节点编码
	 */
	public String findByIdTypeNum(String id) throws Exception;
	/**
	 * 根据编码查询部门
	 * @param ddKhbm
	 * @return
	 */

	/**
	 * 查询编码最大值
	 * @param typePid
	 * @return
	 */
	public String findMaxNum(String typePid) throws Exception;

	public CdDept findBySnumber(String ddKhbm) throws Exception;
	/**
	 * 根据主键查询下一级部门集合(托管实体)
	 * @param deptId
	 * @return
	 */
	public List<DeptVo> findParentId(String deptId) throws Exception;
	/**
	 * 根据主键查询下一级顶级部门集合(托管实体)
	 * @param deptId
	 * @return
	 */
	public List<DeptVo> findParentIdTop() throws Exception;
	
	public String findMaxNum() throws Exception;
	public CdDept findBySId(String id) throws Exception;

    public List<DeptSvo> findByCyDw() throws Exception;

    public List<DeptSelect> findDeptByName(String name) throws Exception;
}
