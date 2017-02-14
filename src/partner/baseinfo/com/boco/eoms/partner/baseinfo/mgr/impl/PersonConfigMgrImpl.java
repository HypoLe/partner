package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.PersonConfig;
import com.boco.eoms.partner.baseinfo.mgr.IPersonConfigMgr;
import com.boco.eoms.partner.baseinfo.dao.IPersonConfigDao;

/**
 * <p>
 * Title:人员配置
 * </p>
 * <p>
 * Description:人员配置
 * </p>
 * <p>
 * Tue Dec 08 15:14:23 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class PersonConfigMgrImpl implements IPersonConfigMgr {
 
	private IPersonConfigDao  personConfigDao;
 	
	public IPersonConfigDao getPersonConfigDao() {
		return this.personConfigDao;
	}
 	
	public void setPersonConfigDao(IPersonConfigDao personConfigDao) {
		this.personConfigDao = personConfigDao;
	}
 	
    public List getPersonConfigs() {
    	return personConfigDao.getPersonConfigs();
    }
    
    public PersonConfig getPersonConfig(final String id) {
    	return personConfigDao.getPersonConfig(id);
    }
    
    public void savePersonConfig(PersonConfig personConfig) {
    	personConfigDao.savePersonConfig(personConfig);
    }
    
    public void removePersonConfig(final String id) {
    	personConfigDao.removePersonConfig(id);
    }
    
    public Map getPersonConfigs(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return personConfigDao.getPersonConfigs(curPage, pageSize, whereStr);
	}
	public List getPersonConfigs(final String where) {
		return personConfigDao.getPersonConfigs(where);
	}
}