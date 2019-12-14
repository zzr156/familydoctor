package com.ylz.bizDo.cd.dao.impl;

import com.ylz.bizDo.cd.dao.CdMessageDao;
import com.ylz.bizDo.cd.po.CdDissolutionWarning;
import com.ylz.bizDo.cd.po.CdMessage;
import com.ylz.bizDo.cd.vo.CdDissolutionWarningVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息管理业务实现
 *
 * @author admin
 */
@Service("cdMessageDao")
@Transactional(rollbackForClassName={"Exception"})
public class CdMessageDapImpl implements CdMessageDao {
    @Autowired
    public SysDao sysDao;

    @Override
    public List<CdMessage> find() throws Exception {
        HashMap map = new HashMap();
        String sql = "SELECT a.id id,a.dataOut_Remind cqtx,a.qf_timeOut qfcs,a.result result FROM CD_MESSAGE a WHERE 1=1";
        List<CdMessage> list = sysDao.getServiceDo().findSqlMapRVo(sql, map, CdMessage.class);
        return list;
    }

    /**
     * 获取当前机构下医生的解约预警设置
     * WangCheng
     * @param vo
     * @return
     */
    @Override
    public CdDissolutionWarning getDissolutionWarning(CdDissolutionWarningVo vo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "select * from cd_dissolution_warning a where 1=1";
        if (StringUtils.isNotEmpty(vo.getOrgId())) {
            map.put("orgId", vo.getOrgId());
            sql += " and a.ORG_ID = :orgId";
        }
        if (StringUtils.isNotEmpty(vo.getDrId())) {
			map.put("drId",vo.getDrId());
			sql += " and a.DR_ID = :drId";
        }
        List<CdDissolutionWarning> list = this.sysDao.getServiceDo().findSqlMap(sql,map,CdDissolutionWarning.class);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 计算即将解约的签约量
     * WangCheng
     * @param orgId
     * @param warningDay
     * @return
     * @throws Exception
     */
    public int countDissolutionNum(String orgId,String areaCode,String warningDay) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        StringBuffer sql = new StringBuffer("select count(*) from app_sign_form a where 1=1");
        if(StringUtils.isNotEmpty(warningDay)){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH,+Integer.valueOf(warningDay));
            String countDate = ExtendDate.getYMD(calendar);
            map.put("countDate",countDate);
            sql.append(" AND a.SIGN_TO_DATE < :countDate AND a.SIGN_STATE in ('0','2') AND a.SIGN_GOTO_SIGN_STATE!='2'");
        }
//        sql.append(" AND a.SIGN_TYPE = '1'");
        if(StringUtils.isNotEmpty(orgId)){
            map.put("SIGN_HOSP_ID",orgId);
            sql.append(" AND a.SIGN_HOSP_ID = :SIGN_HOSP_ID");
        }
        if(StringUtils.isNotEmpty(areaCode)){
            map.put("SIGN_AREA_CODE",areaCode+"%");
            sql.append(" AND a.SIGN_AREA_CODE LIKE :SIGN_AREA_CODE");
        }
        int result = sysDao.getServiceDo().findCount(sql.toString(),map);
        return result;
    }

}