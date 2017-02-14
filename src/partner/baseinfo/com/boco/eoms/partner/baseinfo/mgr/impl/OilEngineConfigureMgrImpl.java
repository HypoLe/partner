package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.OilEngineConfigure;
import com.boco.eoms.partner.baseinfo.mgr.IOilEngineConfigureMgr;
import com.boco.eoms.partner.baseinfo.dao.IOilEngineConfigureDao;

/**
 * <p>
 * Title:油机配置
 * </p>
 * <p>
 * Description:合作伙伴资源配置管理 油机配置
 * </p>
 * <p>
 * Sun Dec 13 21:42:25 CST 2009
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */
public class OilEngineConfigureMgrImpl implements IOilEngineConfigureMgr {
 
	private IOilEngineConfigureDao  oilEngineConfigureDao;
 	
	public IOilEngineConfigureDao getOilEngineConfigureDao() {
		return this.oilEngineConfigureDao;
	}
 	
	public void setOilEngineConfigureDao(IOilEngineConfigureDao oilEngineConfigureDao) {
		this.oilEngineConfigureDao = oilEngineConfigureDao;
	}
 	
    public List getOilEngineConfigures() {
    	return oilEngineConfigureDao.getOilEngineConfigures();
    }
    
    public List isUnique(final String whereStr) {
    	return oilEngineConfigureDao.isUnique(whereStr);
    }
    
    
    public OilEngineConfigure getOilEngineConfigure(final String id) {
    	return oilEngineConfigureDao.getOilEngineConfigure(id);
    }
    
    public void saveOilEngineConfigure(OilEngineConfigure oilEngineConfigure) {
    	oilEngineConfigureDao.saveOilEngineConfigure(oilEngineConfigure);
    }
    
    public void removeOilEngineConfigure(final String id) {
    	oilEngineConfigureDao.removeOilEngineConfigure(id);
    }
    
    public Map getOilEngineConfigures(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return oilEngineConfigureDao.getOilEngineConfigures(curPage, pageSize, whereStr);
	}
	
}