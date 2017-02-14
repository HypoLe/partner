package com.boco.eoms.partner.excel.dao.hibernate;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.partner.excel.dao.IParContentsExcelDao;
/**
 * <p>Title: Excel的导入功能</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author benweiwei
 * @version 1.0(2009-10-23)
 */
public class ParExcelDaoHibernate extends BaseDaoHibernate implements IParContentsExcelDao,
		ID2NameDAO {

	public void save(final Object object) {
			getHibernateTemplate().save(object);
	}
	public String id2Name(String id) throws DictDAOException {
		return null;
	}
	public void saveOrUpdate(Object object) {
		getHibernateTemplate().saveOrUpdate(object);
	}

	
}