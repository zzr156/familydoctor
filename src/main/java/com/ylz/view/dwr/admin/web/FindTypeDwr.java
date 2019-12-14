package com.ylz.view.dwr.admin.web;
//package com.xmgrid.view.dwr.admin.web;
//
//import com.xmgrid.allDo.SysDao;
//import com.xmgrid.bizDo.news.po.NewsRight;
//import com.xmgrid.bizDo.news.po.NewsType;
//import com.xmgrid.bizDo.news.po.NewsUser;
//import com.xmgrid.common.Constant;
//import com.xmgrid.view.dwr.common.DwrCommonServer;
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.lang.StringUtils;
//import org.directwebremoting.ScriptBuffer;
//import org.directwebremoting.annotations.RemoteMethod;
//import org.directwebremoting.annotations.RemoteProxy;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created with IntelliJ IDEA.
// * User: Administrator
// * Date: 12-12-24
// * Time: 上午6:59
// * To change this template use File | Settings | File Templates.
// */
//@RemoteProxy
//public class FindTypeDwr extends DwrCommonServer{
//    public SysDao sysDao;
//    public SysDao getSysDao() {
//        return sysDao;
//    }
//    @Resource(name="sysDao")
//    public void setSysDao(SysDao sysDao) {
//        this.sysDao = sysDao;
//    }
//
//    @RemoteMethod
//    public List<NewsType> findtype2(String id,HttpServletRequest request){
//        try
//        {
//        List<NewsType> l=new ArrayList<NewsType>();
//        NewsType t=new NewsType();
//        String sql="select * from news_type where state=1";
//        Map<String,Object> map = new HashMap<String,Object>();
//        if ( !StringUtils.isBlank(id) )
//        {sql += " and parent_id = :id ";map.put("id", id);}
//        List<NewsType> rs=sysDao.getServiceDo().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString()).addEntity(NewsType.class).setProperties(map).list();
//        NewsUser nu=(NewsUser)request.getSession().getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF);
//        String us=nu.getId();
//        List<NewsRight> nr=sysDao.getNewsRightDo().findadminfa2(us);
//        if(nr.size()!=0)
//        {
//            t.setId( "请选择..." );
//            t.setTypeName( "二级分类" );
//            l.add( t );
//            for(int i=0;i<rs.size();i++)
//            {
//                for(NewsRight n:nr)
//                {
//                    if(n.getValuer().equalsIgnoreCase(rs.get(i).getId()))
//                    {
//                        NewsType t2 = new NewsType();
//                        t2.setId( rs.get(i).getId() );
//                        t2.setTypeName( rs.get(i).getTypeName() );
//                        l.add( t2 );
//                    }
//                }
//            }
//
//
//        }else{
//            if ( rs.size()!=0)
//            {
//                t.setId( "请选择..." );
//                t.setTypeName( "二级分类" );
//                l.add( t );
//                NewsType t3 = new NewsType();
//                t3.setId( rs.get(0).getId() );
//                t3.setTypeName( rs.get(0).getTypeName() );
//                l.add( t3 );
//                for(int i=1;i<rs.size();i++)
//                {
//                    NewsType t2 = new NewsType();
//                    t2.setId(rs.get(i).getId());
//                    t2.setTypeName(rs.get(i).getTypeName());
//                    l.add( t2 );
//                }
//            }
//            else
//            {
//                t.setId( "请选择..." );
//                t.setTypeName( "<--无二级分类" );
//                l.add( t );
//            }
//        }
//        return l;
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//
//
//
//
//    @RemoteMethod
//    public List<NewsType> findtype3(String id) throws Exception {
//        List<NewsType> l = new ArrayList<NewsType>();
//        NewsType t = new NewsType();
//        String sql="select * from news_type where state=1";
//        Map<String,Object> map = new HashMap<String,Object>();
//        if ( !StringUtils.isBlank(id) )
//        {sql += " and parent2_id= :id ";map.put("id", id);}
//        List<NewsType> rs=sysDao.getServiceDo().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString()).addEntity(NewsType.class).setProperties(map).list();
//        if (rs.size()!=0)
//        {
//            t.setId("请选择...");
//            t.setTypeName("三级分类");
//            l.add(t);
//            for(int i=0;i<rs.size();i++)
//            {
//                    NewsType t2 = new NewsType();
//                    t2.setId( rs.get(i).getId() );
//                    t2.setTypeName( rs.get(i).getTypeName() );
//                    l.add( t2 );
//            }
//        }else
//        {
//            t.setId("请选择...");
//            t.setTypeName("<--无三级分类");
//            l.add(t);
//        }
//        return l;
//    }
//
//    @RemoteMethod
//    public List<NewsType> findtype4(String id) throws Exception {
//        List<NewsType> l = new ArrayList<NewsType>();
//        NewsType t = new NewsType();
//        String sql="select * from news_type where state=1";
//        Map<String,Object> map = new HashMap<String,Object>();
//        if ( !StringUtils.isBlank(id) )
//        {sql += " and parent3_id= :id ";map.put("id", id);}
//        List<NewsType> rs=sysDao.getServiceDo().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString()).addEntity(NewsType.class).setProperties(map).list();
//        if (rs.size()!=0)
//        {
//            t.setId("请选择...");
//            t.setTypeName("四级分类");
//            l.add(t);
//            for(int i=0;i<rs.size();i++) {
//                NewsType t2 = new NewsType();
//                t2.setId(rs.get(i).getId());
//                t2.setTypeName(rs.get(i).getTypeName());
//                l.add(t2);
//            }
//        }else
//        {
//            t.setId("请选择...");
//            t.setTypeName("<--无四级分类");
//            l.add(t);
//        }
//
//        return l;
//    }
//    @RemoteMethod
//    public void addUser(Map vo,HttpServletRequest request) throws Exception
//    {
//        initDu();
//        NewsUser newsUser=new NewsUser();
//        BeanUtils.populate(newsUser, vo);
//        List<NewsUser> ls=this.sysDao.getNewsUserDo().findName(newsUser.getName());
//        if ( ls != null )
//        {
////            for ( NewsUser l : ls )
////            {
////                if (newsUser.getName().equals( l.getName()))
////                {
//                    du.addScript(new ScriptBuffer("alert('此用户已经被注册')"));
//                    return;
////                }
////            }
//        }
//        du.addScript(new ScriptBuffer("alert('用户注册成功')"));
//    }
//
//}
