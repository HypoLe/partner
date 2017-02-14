package com.boco.eoms.partner.excel.mgr.impl;

import com.boco.eoms.partner.excel.dao.IParExcelDao;
import com.boco.eoms.partner.excel.mgr.IParExcelMgr;


/**
 * <p>Title: Excel的导入功能</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author benweiwei
 * @version 1.0(2009-10-23)
 */
public class ParExcelImpl implements IParExcelMgr{
	
	private IParExcelDao iparExcelDao;

	public IParExcelDao getIparExcelDao() {
		return iparExcelDao;
	}

	public void setIparExcelDao(IParExcelDao iparExcelDao) {
		this.iparExcelDao = iparExcelDao;
	}

	//	增加数据
	public Integer insert(final String sql){
		return iparExcelDao.insert(sql);
	}
}
