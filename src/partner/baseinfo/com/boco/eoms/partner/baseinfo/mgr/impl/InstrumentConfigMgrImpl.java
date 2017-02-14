package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.InstrumentConfig;
import com.boco.eoms.partner.baseinfo.mgr.IInstrumentConfigMgr;
import com.boco.eoms.partner.baseinfo.dao.IInstrumentConfigDao;

/**
 * <p>
 * Title:仪器仪表配置
 * </p>
 * <p>
 * Description:仪器仪表配置
 * </p>
 * <p>
 * Mon Dec 14 14:07:13 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class InstrumentConfigMgrImpl implements IInstrumentConfigMgr {
 
	private IInstrumentConfigDao  instrumentConfigDao;
 	
	public IInstrumentConfigDao getInstrumentConfigDao() {
		return this.instrumentConfigDao;
	}
 	
	public void setInstrumentConfigDao(IInstrumentConfigDao instrumentConfigDao) {
		this.instrumentConfigDao = instrumentConfigDao;
	}
 	
    public List getInstrumentConfigs() {
    	return instrumentConfigDao.getInstrumentConfigs();
    }
    
    public InstrumentConfig getInstrumentConfig(final String id) {
    	return instrumentConfigDao.getInstrumentConfig(id);
    }
    
    public void saveInstrumentConfig(InstrumentConfig instrumentConfig) {
    	instrumentConfigDao.saveInstrumentConfig(instrumentConfig);
    }
    
    public void removeInstrumentConfig(final String id) {
    	instrumentConfigDao.removeInstrumentConfig(id);
    }
    
    public Map getInstrumentConfigs(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return instrumentConfigDao.getInstrumentConfigs(curPage, pageSize, whereStr);
	}
	
    public List getInstrumentConfigs(final String where){
    	return instrumentConfigDao.getInstrumentConfigs(where);
    }
}