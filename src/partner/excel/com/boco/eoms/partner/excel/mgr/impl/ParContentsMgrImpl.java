package com.boco.eoms.partner.excel.mgr.impl;

import com.boco.eoms.partner.excel.dao.IParContentsExcelDao;
import com.boco.eoms.partner.excel.mgr.IParContentsExcelMgr;


/**
 * <p>Title: Excel的导入功能</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author benweiwei
 * @version 1.0(2009-10-23)
 */
public class ParContentsMgrImpl implements IParContentsExcelMgr{
	private IParContentsExcelDao iparContentsExcelDao;
	public void save(final Object object) {
		iparContentsExcelDao.save(object);
	}
	public void saveOrUpdate(Object object) {
		iparContentsExcelDao.saveOrUpdate(object);
		
	}
	public IParContentsExcelDao getIparContentsExcelDao() {
		return iparContentsExcelDao;
	}
	public void setIparContentsExcelDao(IParContentsExcelDao iparContentsExcelDao) {
		this.iparContentsExcelDao = iparContentsExcelDao;
	}

}
