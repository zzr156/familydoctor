package com.ylz.packcommon.webservices.server.impl;


import com.ylz.packcommon.webservices.server.IHelloService;


public class HelloServiceImpl implements IHelloService {
//	public SysDao sysDao;

	public String sayHello(String ttt) {
		return "Hello, "+ttt;
	}
	
	
//	@SuppressWarnings("unchecked")
//	public List<User> findH(UserLoginVo qvo) {
//		HQuery hql = new HQuery("from User  as a where 1 = 1");
//		if(StringUtils.isNotBlank(qvo.getName()))
//            hql.addHqlSplit(" and a.username like ? ").addParas("%"+qvo.getName()+"%",Types.VARCHAR);
//		//分页查询
//		this.qvo=(UserLoginVo) sysDao.getUserDo().getNewQvo(qvo, hql);
//		//
//		List<User> ls=sysDao.getUserDo().findHquery(hql);
//		if(ls!=null)
//			return ls;
//		return null;
//	}
	
	/**
	 * 分页
	 * 使用webservicesc查询必须返回查询条件
	 * qvo记录当前查询条件状态
	 */
//	private UserLoginVo qvo;
//
//
//	public UserLoginVo getQvo() {
//		return qvo;
//	}
//
//
//	public void setQvo(UserLoginVo qvo) {
//		this.qvo = qvo;
//	}


//	public SysDao getSysDao() {
//		return sysDao;
//	}
//	@Resource(name="sysDao")
//	public void setSysDao(SysDao sysDao) {
//		this.sysDao = sysDao;
//	}






	
	


}
