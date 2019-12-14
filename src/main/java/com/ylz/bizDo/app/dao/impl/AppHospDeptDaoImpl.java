package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppHospDeptDao;
import com.ylz.bizDo.app.entity.AppHospAuthorizationEntity;
import com.ylz.bizDo.app.po.AppAgreement;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppUpHospTable;
import com.ylz.bizDo.app.vo.AppHospDeptQvo;
import com.ylz.bizDo.cd.entity.AddressHospEntity;
import com.ylz.bizDo.cd.entity.AddressTreeHospEntity;
import com.ylz.bizDo.cd.entity.CdAddressEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppHospEntity;
import com.ylz.bizDo.jtapp.commonEntity.InternetHospOrgEntity;
import com.ylz.bizDo.jtapp.commonVo.InternetHospQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.util.AreaUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appHospDeptDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppHospDeptDaoImpl implements AppHospDeptDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppHospDept> findList(AppHospDeptQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_HOSP_DEPT  as a WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getPro()) && StringUtils.isBlank(qvo.getCity()) && StringUtils.isBlank(qvo.getArea()) && StringUtils.isBlank(qvo.getDrHospId())){
            map.put("appDrHospAreaCode", AreaUtils.getAreaCode(qvo.getPro())+"%");
            sql += " AND a.HOSP_AREA_CODE LIKE :appDrHospAreaCode";
        }
        if(StringUtils.isNotBlank(qvo.getPro()) && StringUtils.isNotBlank(qvo.getCity()) && StringUtils.isBlank(qvo.getArea()) && StringUtils.isBlank(qvo.getDrHospId())){
            map.put("appDrHospAreaCode", AreaUtils.getAreaCode(qvo.getCity())+"%");
            sql += " AND a.HOSP_AREA_CODE LIKE :appDrHospAreaCode";
        }
        if(StringUtils.isNotBlank(qvo.getPro()) && StringUtils.isNotBlank(qvo.getCity()) && StringUtils.isNotBlank(qvo.getArea()) && StringUtils.isBlank(qvo.getDrHospId())){
            map.put("appDrHospAreaCode", AreaUtils.getAreaCode(qvo.getArea())+"%");
            sql += " AND a.HOSP_AREA_CODE LIKE :appDrHospAreaCode";
        }
        if(StringUtils.isNotBlank(qvo.getPro()) && StringUtils.isNotBlank(qvo.getCity()) && StringUtils.isNotBlank(qvo.getArea()) && StringUtils.isNotBlank(qvo.getDrHospId())){
            map.put("appDrHospId",qvo.getDrHospId());
            sql += " AND a.ID = :appDrHospId";
        }
        if(StringUtils.isNotBlank(qvo.getAppHospName())){
            map.put("appHospName","%"+qvo.getAppHospName()+"%");
            sql += " AND a.HOSP_NAME LIKE :appHospName";
        }
        return sysDao.getServiceDo().findSqlMap(sql, map, AppHospDept.class, qvo);

    }

    @Override
    public List<AppHospDept> findAll() throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
//        String areaCode = "350203012";
//        map.put("areaCode","%"+areaCode+"%");
        String sql = "SELECT * FROM APP_HOSP_DEPT  AS a WHERE 1=1 ";
//        sql += " AND a.HOSP_AREA_CODE LIKE :areaCode";
        List<AppHospDept> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);
        return ls;
    }

    @Override
    public List<AppHospDept> findByState(String state) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("state",state);
        String sql = "SELECT * FROM APP_HOSP_DEPT  as a WHERE 1=1 AND a.HOSP_STATE = :state ";
        return sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);
    }

    @Override
    public List<AppHospDept> findByHospUpCityAreaId(String upAreaId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("hospUpCityAreaId",upAreaId);
        String sql = "SELECT * FROM APP_HOSP_DEPT AS a WHERE 1=1 AND a.HOSP_UPCITYAREA_ID =:hospUpCityAreaId ";
        map.put("HOSP_STATE","1");
        sql +=" AND a.HOSP_STATE = :HOSP_STATE";
        return sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);
    }

    @Override
    public List<AppHospDept> findByAreaCode(String areaCode) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("areaCode",AreaUtils.getAreaCodePrefix(areaCode)+"%");
        String sql = "SELECT * FROM APP_HOSP_DEPT  AS a WHERE 1=1 AND a.HOSP_AREA_CODE LIKE :areaCode ";
        map.put("HOSP_STATE","1");
        sql +=" AND a.HOSP_STATE = :HOSP_STATE";
        return sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);

    }

    @Override
    public List<AppHospDept> findByAreaCodeLike(String areaCode) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        areaCode = AreaUtils.getAreaCodePrefix(areaCode);
        map.put("areaCode",areaCode+"%");
        map.put("HOSP_STATE","1");
        String sql = "SELECT * FROM APP_HOSP_DEPT  AS a WHERE 1=1 AND a.HOSP_AREA_CODE LIKE :areaCode AND a.HOSP_STATE = :HOSP_STATE ORDER BY HOSP_AREA_CODE ";
        return sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);

    }

    @Override
    public AppHospDept findByAreaCodeByOne(String areaCode) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("areaCode",areaCode);
        String sql = "SELECT * FROM APP_HOSP_DEPT  AS a WHERE 1=1 AND a.HOSP_AREA_CODE = :areaCode ";
        map.put("HOSP_STATE","1");
        sql +=" AND a.HOSP_STATE = :HOSP_STATE";
        List<AppHospDept> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);
        if(ls != null && ls.size()>0){
            return ls.get(0);
        }
        return null;

    }

    @Override
    public void modify(AppHospDept vo) throws Exception {
        AppHospDept vos = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class,vo.getId());
        //session出现实体重复时的处理
        vos.setHospName(vo.getHospName());
        vos.setHospCode(vo.getHospCode());
        vos.setHospState(vo.getHospState());
        vos.setHospTel(vo.getHospTel());
        vos.setHospAddress(vo.getHospAddress());
        vos.setHospIntro(vo.getHospIntro());
        vos.setHospImageurl(vo.getHospImageurl());
        vos.setHospPageStyle(vo.getHospPageStyle());
        vos.setLabelState(vo.getLabelState());
        this.sysDao.getServiceDo().removePoFormSession(vos);
        this.sysDao.getServiceDo().modify(vos);

    }

    @Override
    public List<AppHospEntity> findByAreaId(String upId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT ID id,HOSP_NAME hospName,'1' hospState FROM APP_HOSP_DEPT  as a WHERE 1=1 ";
        if(StringUtils.isNotBlank(upId)){
            map.put("upId",upId);
            sql += " AND a.HOSP_UPCITYAREA_ID = :upId";
        }
        map.put("HOSP_STATE","1");
        sql +=" AND a.HOSP_STATE = :HOSP_STATE";
        sql += " ORDER BY HOSP_AREA_CODE ASC ";
        List<AppHospEntity> ls = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppHospEntity.class);
        return ls;
    }

    @Override
    public List<AppHospEntity> findByAreaIdNotZero(String upId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT ID id,HOSP_NAME hospName,'0' hospState FROM APP_HOSP_DEPT  as a WHERE 1=1 ";
        if(StringUtils.isNotBlank(upId)){
            map.put("upId",upId);
            sql += " AND a.HOSP_UPCITYAREA_ID = :upId";
        }
        map.put("HOSP_STATE","1");
        sql +=" AND a.HOSP_STATE = :HOSP_STATE";
        sql += " ORDER BY HOSP_AREA_CODE ASC ";
        List<AppHospEntity> ls = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppHospEntity.class);
        return ls;
    }

    @Override
    public List<AddressHospEntity> findByAreaTsId(String upId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT ID id,HOSP_NAME name,'1' state,'2' level FROM APP_HOSP_DEPT  as a WHERE 1=1 ";
        if(StringUtils.isNotBlank(upId)){
            map.put("upId",upId);
            sql += " AND a.HOSP_UPCITYAREA_ID = :upId";
        }
        map.put("HOSP_STATE","1");
        sql +=" AND a.HOSP_STATE = :HOSP_STATE";
        sql += " ORDER BY HOSP_AREA_CODE ASC ";
        List<AddressHospEntity> ls = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AddressHospEntity.class);
        return ls;
    }

    /**
     * 查询开启签约服务的医院
     * @return
     */
    @Override
    public List<AppHospDept> findBySerState() throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_HOSP_DEPT WHERE 1=1";
        map.put("serState","1");
        map.put("HOSP_STATE","1");
        sql +=" AND HOSP_STATE = :HOSP_STATE";
        sql += " AND HOSP_SER_STATE =:serState";
        List<AppHospDept> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);
        return ls;
    }

    /**
     * 查询areaCode不等于空
     * @return
     */
    @Override
    public List<AppHospDept> findByAreaCodeNotNull() throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_HOSP_DEPT WHERE 1=1";
        sql += " AND HOSP_AREA_CODE <> ''";
        List<AppHospDept> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);
        return ls;
    }

    @Override
    public List<AddressTreeHospEntity> findTreeSetting(String id) throws Exception{
        HashMap map = new HashMap();
        map.put("state", CommonEnable.QIYONG.getValue());
        String sql = "SELECT\n" +
                "\tt.HOSP_AREA_CODE id,\n" +
                "\tt.HOSP_NAME name\n" +
                "FROM\n" +
                "\tAPP_HOSP_DEPT t\n" +
                " WHERE 1=1 ";
        if(StringUtils.isNotBlank(id)) {
            map.put("upId", id);
            sql += " AND T.HOSP_UPCITYAREA_ID = :upId ";
        }
        sql += " AND T.HOSP_STATE =:state ORDER BY T.HOSP_AREA_CODE  ";
        List<AddressTreeHospEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, AddressTreeHospEntity.class);
        return ls;
    }

    @Override
    public List<AppHospDept> findByQvo(AppHospDeptQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("areaCode",AreaUtils.getAreaCode(qvo.getAreaCode())+"%");
        String sql = "SELECT * FROM APP_HOSP_DEPT  AS a WHERE 1=1 AND a.HOSP_AREA_CODE LIKE :areaCode ";
        if(StringUtils.isNotBlank(qvo.getRole())){
            sql += " and a.HOSP_LEVEL =:role ";
            map.put("role",qvo.getRole());
        }
        return sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class,qvo);
    }

    /**
     * cjw  查询区县
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<CdAddressEntity> findByCpctiy(AppHospDeptQvo qvo) throws Exception
    {
        Map<String,Object> map = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(qvo.getAreaCode())){
            String sql =" SELECT c.AREA_CODE id ,c.AREA_SNAME hospName from CP_CITY_AREA c where  c.LEVEL_NAME ='3'  and c.AREA_CODE like:areaCode ";
            String areacode=qvo.getAreaCode().substring(0,4);
            map.put("areaCode",areacode+"%");
            List<CdAddressEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map,CdAddressEntity.class);
            if(ls!=null &&ls.size()>0){
                return ls;
            }
        }
        return null ;
    }

    /**
     * cjw 签约授权
     */
    public List<AppHospDept> findauthorization(AppHospAuthorizationEntity vo)throws Exception
    {
        List<AppHospDept> hosplist = new ArrayList<AppHospDept>();
        Map<String,Object> map = new HashMap<String,Object>();
        String sql="";
        String[] code;
        if(vo.getType().equals("0")){
            code =vo.getAreaValue().split(";") ;
        }else{
            code =vo.getAreaCode().split(";") ;
        }

       /* if(vo.getType().equals("0")){ // 机构
            sql=" select * from APP_HOSP_DEPT d where d.ID in(:AreaCode)";
            map.put("AreaCode",code);
            List<AppHospDept> ls=sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);
            if(ls!=null && ls.size()>0){
                return ls;
            }
        }else{*/
            for(int i=0;i<code.length;i++){
                    String codeValue= code[i].substring(0,6);
                    sql=" SELECT * from APP_HOSP_DEPT d where  HOSP_LEVEL ='4' and d.HOSP_AREA_CODE LIKE:AreaCode ";
                    map.put("AreaCode",codeValue+"%");
                    List<AppHospDept> ls=sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);
                    if(ls!=null && ls.size()>0){
                        hosplist.addAll(ls);
                }
            }
            if(hosplist!=null){
                return hosplist;
            }
       // }
        return null;

    }


    /**
     * 查询本市下的上级医院列表
     * @param hospId
     * @return
     * @throws Exception
     */
    @Override
    public List<AppHospDept> findHospByArea(String hospId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String areaCode="";
        if(org.apache.commons.lang3.StringUtils.isNotBlank(hospId)){
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,hospId);
            if(dept!=null){
                areaCode = dept.getHospAreaCode();
                if(org.apache.commons.lang3.StringUtils.isNotBlank(areaCode)){
                    areaCode = AreaUtils.getAreaCode(areaCode);
                    if(areaCode.length()>4){
                        areaCode = areaCode.substring(0,4);
                    }
                }

            }
        }
        if(StringUtils.isNotBlank(areaCode)){
            map.put("areaCode",areaCode+"%");
            String sql = "SELECT * FROM APP_HOSP_DEPT WHERE 1=1 " +
                    "AND HOSP_AREA_CODE LIKE :areaCode AND HOSP_LEVEL_TYPE IN ('0','1','2','3','4','5','6')";
            List<AppHospDept> list = sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);
            List<AppHospDept> listDept = new ArrayList<>();
            if(list!=null && list.size()>0){
                for(AppHospDept ll:list){
                    AppUpHospTable table = getFindUpHosp(hospId,ll.getId());
                    if(table==null){
                        listDept.add(ll);
                    }
                }
            }
            return listDept;
        }
        return null;
    }

    public AppUpHospTable getFindUpHosp(String hospId, String upHospId) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("hospId",hospId);
        map.put("upHospId",upHospId);
        String sql = "SELECT * FROM APP_UP_HOSP_TABLE WHERE 1=1 AND UP_ID=:hospId AND UP_HOSP_ID=:upHospId";
        List<AppUpHospTable> list = sysDao.getServiceDo().findSqlMap(sql,map,AppUpHospTable.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;

    }

    /**
     * 查询上级医院列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppUpHospTable> findUpHospList(AppHospDeptQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("hospId",qvo.getDrHospId());
        String sql = "SELECT * FROM APP_UP_HOSP_TABLE WHERE 1=1 AND UP_ID=:hospId";
        List<AppUpHospTable> list = sysDao.getServiceDo().findSqlMap(sql,map,AppUpHospTable.class,qvo);
        return list;
    }

    @Override
    public AppUpHospTable findOneUpHosp(String upId, String upHospId) throws Exception {
        AppUpHospTable table = getFindUpHosp(upId,upHospId);
        return table;
    }

    @Override
    public List<AppHospDept> findUpHospListRead(String areaId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("HOSP_UPCITYAREA_ID",areaId);
        map.put("HOSP_STATE","1");
        String sql = "SELECT * FROM APP_HOSP_DEPT t WHERE t.HOSP_UPCITYAREA_ID = :HOSP_UPCITYAREA_ID AND HOSP_STATE =:HOSP_STATE";
        return sysDao.getServiceReadDo().findSqlMap(sql,map,AppHospDept.class);
    }

    @Override
    public List<AppHospDept> findByAreaCodeLike(String areaCode, String findState) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String ss = areaCode;
        areaCode = AreaUtils.getAreaCode(areaCode);
        map.put("areaCode",areaCode+"%");
        map.put("HOSP_STATE","1");
        String sql = "SELECT * FROM APP_HOSP_DEPT  AS a WHERE 1=1 AND a.HOSP_AREA_CODE LIKE :areaCode AND a.HOSP_STATE = :HOSP_STATE  ";
        if("1".equals(findState)){//查询居委会下的机构
            map.put("ss",ss);
            sql += " AND a.HOSP_AREA_CODE != :ss ";
        }
        sql +=  " ORDER BY HOSP_AREA_CODE";
        return sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);
    }

    @Override
    public List<AppHospDept> findHospByStreet(String areaCode,String type) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("areaCode",areaCode);
        map.put("HOSP_STATE","1");
        String sql = "SELECT * FROM APP_HOSP_DEPT  AS a WHERE 1=1 AND a.HOSP_STATE = :HOSP_STATE  ";
        if("1".equals(type)){
            sql += " AND a.HOSP_UPCITYAREA_ID = :areaCode";
        }else {
            sql += " AND a.HOSP_AREA_CODE = :areaCode";
        }
        sql += " ORDER BY HOSP_AREA_CODE";
        return sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);
    }

    @Override
    public List<AppHospDept> findManageContByAreaCode(String[] areaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("areaCode",areaCode);
        String sql = "SELECT * FROM\n" +
                "APP_HOSP_DEPT D \n" +
                "WHERE d.HOSP_AREA_CODE IS NOT NULL AND LEFT(d.HOSP_AREA_CODE,4) IN (:areaCode) ";
        List<AppHospDept> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);
        return ls;
    }

    /**
     * 获取该区县下属的所有社区
     * WangCheng
     * @param upId
     * @param source
     * @return
     * @throws Exception
     */
    @Override
    public List<AddressHospEntity> listAllCommunity(String upId,String source) throws Exception{
        HashMap map = new HashMap();
        StringBuilder sql = new StringBuilder("SELECT a.HOSP_AREA_CODE AS id, a.HOSP_NAME AS name," +
                " '1' AS state, a.HOSP_LEVEL AS level, '1' AS source FROM app_hosp_dept a" +
                " LEFT JOIN CP_CITY_AREA_CONFIGURATION b ON a.HOSP_AREA_CODE = b.CITY_AREA_ID WHERE 1 = 1");
        if(StringUtils.isNotBlank(upId)){
            map.put("upId",upId);
            sql.append(" AND a.HOSP_UPCITYAREA_ID = :upId");
        }
        map.put("state", CommonEnable.QIYONG.getValue());
        sql.append(" AND a.HOSP_STATE =:state ORDER BY a.HOSP_AREA_CODE desc");
        List<AddressHospEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AddressHospEntity.class);
        if(list != null && list.size() > 0){
            return list;
        }
        return null;
    }

    /**
     * 机构数据导入
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public String addImportExcelHosp(Map<Integer, String> map) throws Exception {
        String result1 = "成功导入"+map.size()+"条";
        for (int i = 0; i < map.size(); i++) {//循环每条记录数据
            int num = i + 1;
            String[] ss = map.get(i).split("\\|");//取每条记录的每一个字段
            //先根据是否是队长判断该条记录是否要添加团队数据
            if(StringUtils.isBlank(ss[0])){
                throw new Exception("导入失败：第"+num+"条，机构名称不能为空");
            }else if(StringUtils.isBlank(ss[1])){
                throw new Exception("导入失败：第"+num+"条，机构编码不能为空");
            }else if(StringUtils.isBlank(ss[2])){
                throw new Exception("导入失败：第"+num+"条，机构行政区划不能为空");
            }else if(StringUtils.isBlank(ss[3])){
                throw new Exception("导入失败：第"+num+"条，机构上级行政区划不能为空");
            }else if(StringUtils.isBlank(ss[6])){
                throw new Exception("导入失败：第"+num+"条，机构级别不能为空");
            }else if(StringUtils.isBlank(ss[7])) {
                throw new Exception("导入失败：第"+num+"条，机构类型不能为空");
            }else{
                //通过机构编码查询此机构编码是否唯一
                AppHospDept dept = sysDao.getAppHospDeptDao().findHospByCode(ss[1]);
                if(dept != null){//有查询到，说明该编码已经被使用
                    throw new Exception("导入失败：第"+num+"条，机构编码已被使用");
                }
                dept = new AppHospDept();
                dept.setHospName(ss[0]);
                dept.setHospCode(ss[1]);
                dept.setHospAreaCode(ss[2]);
                dept.setHospCityareaId(ss[2]);
                dept.setHospUpcityareaId(ss[3]);
                if(StringUtils.isNotBlank(ss[4])){
                    dept.setHospAddress(ss[4]);
                }
                if(StringUtils.isNotBlank(ss[5])){
                    dept.setHospTel(ss[5]);
                }
                dept.setHospLevel(ss[6]);
                dept.setHospLevelType(ss[7]);
                dept.setHospState("1");
                if(ss.length<9){

                }else{
                    dept.setHospSerState(ss[8]);
                }
                sysDao.getServiceDo().add(dept);
                //添加协议表
                String agreeId = "60d31529-6902-4046-8ee1-7d851360301e";
                if("350702".equals(dept.getHospAreaCode().substring(0,6))){
                    agreeId = "6435b6d2-ed19-11e7-90ce-c85b76ccd5e1";
                }else if(AddressType.FZS.getValue().equals(dept.getHospAreaCode().substring(0,4))){
                    agreeId = "3d3cbe49-6a50-4543-83b2-084de760c137";
                }else if(AddressType.PTS.getValue().equals(dept.getHospAreaCode().substring(0,4))){
                    agreeId = "7bad07be-b5cc-48dc-9dd3-edb65b74afb8";
                }else if(AddressType.SMS.getValue().equals(dept.getHospAreaCode().substring(0,4))){
                    agreeId = "60d31529-6902-4046-8ee1-7d851360301e";
                }else if(AddressType.QZS.getValue().equals(dept.getHospAreaCode().substring(0,4))){
                    agreeId = "60d31529-6902-4046-8ee1-7d851360301e";
                }else if(AddressType.ZZS.getValue().equals(dept.getHospAreaCode().substring(0,4))){
                    agreeId = "034773b9-9dea-43db-bfaa-8cebed6c4783";
                }else if(AddressType.NPS.getValue().equals(dept.getHospAreaCode().substring(0,4))){
                    agreeId = "6435df82-ed19-11e7-90ce-c85b76ccd5e1";
                }
                AppAgreement v = (AppAgreement) sysDao.getServiceDo().find(AppAgreement.class,agreeId);
                if(v != null){
                    AppAgreement agreement = new AppAgreement();
                    agreement.setMentUseType("2");
                    agreement.setMentCityId(AreaUtils.getAreaCode(dept.getHospAreaCode(),"2"));
                    agreement.setMentContent(v.getMentContent());
                    agreement.setMentState("1");
                    agreement.setMentType("1");
                    agreement.setMentHospId(dept.getId());
                    agreement.setMentTitle(v.getMentTitle());
                    sysDao.getServiceDo().add(agreement);
                }
            }
        }
        return result1;

    }

    @Override
    public AppHospDept findHospByCode(String code) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        String sql = " select * from app_hosp_dept where 1=1 AND HOSP_CODE =:code ";
        List<AppHospDept> list = sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<InternetHospOrgEntity> findNetOrgByQvo(InternetHospQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT ID id," +
                "HOSP_NAME hospName," +
                "HOSP_CODE hospCode," +
                "HOSP_AREA_CODE hospAreaCode," +
                "HOSP_ADDRESS hospAddress," +
                "HOSP_TEL hospTel," +
                "HOSP_INTRO hospIntro," +
                "HOSP_LEVEL hospLevel " +
                "FROM APP_HOSP_DEPT WHERE 1=1 " +
                "AND HOSP_LEVEL = '4' ";
        //2位省，4市，6位区，9街道
        if(AreaUtils.getAreaCode(qvo.getAreaCode()).length()==2){
            map.put("areaCode",AreaUtils.getAreaCode(qvo.getAreaCode())+"%");
        }else if(AreaUtils.getAreaCode(qvo.getAreaCode()).length()==4){
            map.put("areaCode",AreaUtils.getAreaCode(qvo.getAreaCode())+"%");
        }else if(AreaUtils.getAreaCode(qvo.getAreaCode()).length()==6){
            map.put("areaCode",AreaUtils.getAreaCode(qvo.getAreaCode())+"%");
        }else if(AreaUtils.getAreaCode(qvo.getAreaCode()).length()==9){
            map.put("areaCode",AreaUtils.getAreaCode(qvo.getAreaCode())+"%");
        }else if(AreaUtils.getAreaCode(qvo.getAreaCode()).length()<6 && AreaUtils.getAreaCode(qvo.getAreaCode()).length()>4){
            map.put("areaCode",qvo.getAreaCode().substring(0,6)+"%");
        }else if(AreaUtils.getAreaCode(qvo.getAreaCode()).length()<9 && AreaUtils.getAreaCode(qvo.getAreaCode()).length()>6){
            map.put("areaCode",qvo.getAreaCode().substring(0,9)+"%");
        }
        sql += " AND HOSP_AREA_CODE LIKE :areaCode  AND HOSP_STATE = '1' ";
        List<InternetHospOrgEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,InternetHospOrgEntity.class,qvo);
        return list;
    }
}
