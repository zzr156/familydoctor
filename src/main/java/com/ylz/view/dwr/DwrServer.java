package com.ylz.view.dwr;

import com.ylz.bizDo.cd.po.CdDept;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.view.dwr.common.DwrCommonServer;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.annotation.Resource;
import java.util.List;


@SuppressWarnings("deprecation")
@RemoteProxy
public class DwrServer extends DwrCommonServer
{
    public SysDao sysDao;
    public SysDao getSysDao() {
        return sysDao;
    }
    @Resource(name="sysDao")
    public void setSysDao(SysDao sysDao) {
        this.sysDao = sysDao;
    }
	@RemoteMethod
	public void HelloDwr()
	{
        initDu();
        du.addScript(new ScriptBuffer("alert('HelloDwr')"));
	}
	
	@RemoteMethod
	public List<CdDept> findCdDept(String jgId)
	{
		List<CdDept> cdDept = null;
//		CdInstitution cdInstitution = sysDao.getCdInstitutionDo().find(jgId);
//		if(cdInstitution !=null && cdInstitution.getCdDeptlist()!=null
//				&& cdInstitution.getCdDeptlist().size()>0){
//			cdDept  =cdInstitution.getCdDeptlist();
//		}
		return cdDept;
	}
	
}
