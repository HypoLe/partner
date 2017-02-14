package com.boco.eoms.materials.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.materials.dao.ContactCompanyDao;
import com.boco.eoms.materials.model.ContactCompany;
import com.boco.eoms.materials.service.ContactCompanyManager;

/**
 * 往来单位信息(ContactCompany) 业务实现类
 * 
 * @author wanghongliang
 * 
 */
public class ContactCompanyManagerImpl extends BaseManager implements ContactCompanyManager {

	private ContactCompanyDao dao;

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.ContactCompanyManager#setContactCompanyDao(com.boco.eoms.materials.dao.ContactCompanyDao)
	 */
	public void setContactCompanyDao(ContactCompanyDao dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.ContactCompanyManager#getContactCompany()
	 */
	public List<ContactCompany> getContactCompany() {
		return dao.getContactCompany();
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.ContactCompanyManager#getContactCompany(java.lang.String)
	 */
	public ContactCompany getContactCompany(final String id) {
		return dao.getContactCompany(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.ContactCompanyManager#saveContactCompany(com.boco.eoms.materials.model.ContactCompany)
	 */
	public void saveContactCompany(ContactCompany contactCompany) {
		dao.saveContactCompany(contactCompany);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.ContactCompanyManager#removeContactCompany(java.lang.String)
	 */
	public void removeContactCompany(final String id) {
		dao.removeContactCompany(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.ContactCompanyManager#getContactCompany(java.lang.Integer, java.lang.Integer)
	 */
	public Map<Integer, Integer> getContactCompany(final Integer curPage,
			final Integer pageSize) {
		return dao.getContactCompany(curPage, pageSize, null);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.ContactCompanyManager#getContactCompany(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	public Map<Integer, Integer> getContactCompany(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		return dao.getContactCompany(curPage, pageSize, whereStr);
	}
}
