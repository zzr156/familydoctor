package com.ylz.bizDo.news.dao;

import com.ylz.bizDo.app.po.AppUserHealthED;
import com.ylz.bizDo.jtapp.commonEntity.AppHealthEntiry;
import com.ylz.bizDo.jtapp.commonVo.AppHealthEducationQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrHealthListEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrHealthListQvo;
import com.ylz.bizDo.jtapp.drVo.AppDrQvo;
import com.ylz.bizDo.jtapp.drVo.AppHealthToQvo;
import com.ylz.bizDo.jtapp.patientEntity.AppHealthListEntity;
import com.ylz.bizDo.jtapp.patientVo.AppPatientHealthListQvo;
import com.ylz.bizDo.news.po.NewsTable;
import com.ylz.bizDo.news.vo.NewsMsgQvo;
import com.ylz.bizDo.news.vo.NewsTablePo;
import com.ylz.bizDo.news.vo.NewsTableQvo;
import com.ylz.bizDo.smjq.smVo.AppNcdHEQvo;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.List;

public interface NewsTableDao   {
	/**
	 * 查询新闻发布内容
	 * @return
	 */
	public List<NewsTablePo> findAllNewsTable(NewsTableQvo qvo) throws Exception;
	
	public List<NewsTablePo> findAllNewsTable(NewsMsgQvo qvo) throws Exception;

	public int findNewsTableCount(String cjsjStart,String cjsjEnd) throws Exception;
	//根据id查询
	public AppHealthEntiry findById(String id) throws Exception;

	//根据患者id查询列表
	public List<AppHealthListEntity> findByUserId(AppPatientHealthListQvo qvo) throws Exception;
	//个人查询收藏健康教育列表
	public List<AppHealthListEntity> findByIsEnshrine(AppPatientHealthListQvo qvo) throws Exception;

	public List<AppDrHealthListEntity> findDrByEnshrine(AppDrHealthListQvo qvo) throws Exception;

	//医生查询所有健康教育
	public List<AppDrHealthListEntity> findAll(AppDrHealthListQvo qvo) throws Exception;

	//医生新增健康教育
	public void saveHealth(AppHealthEducationQvo qvo) throws Exception;

	//查询健康教育模板列表
	public List<AppHealthEntiry> findList(AppDrQvo qvo) throws Exception;
	//公共新增健康教育模板
	public NewsTable addHealth(AppHealthEducationQvo qvo) throws Exception;
	//修改健康教育模板
	public AppHealthEntiry modifyHealth(AppHealthEducationQvo qvo) throws Exception;

	/**
	 * 根据权限发送健康教育
	 */
	public void fsHealthRole(AppHealthEducationQvo qvo)  throws Exception;

	/**
	 * 一键发送健康教育
	 * @param qvo
	 * @throws Exception
	 */
	public String fsHealthAll(AppHealthToQvo qvo) throws Exception;

	/**
	 * 查询定期发送数据
	 * @return
	 * @throws Exception
	 */
	public List<AppUserHealthED> findByPush()  throws Exception ;

	/**
	 * 保存慢病健康教育和推送
	 * @return
	 * @throws Exception
	 */
	public String saveHealthByNcd(AppNcdHEQvo qvo) throws Exception;


}
