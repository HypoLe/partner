package com.boco.eoms.partner.baseinfo.dao.hibernate;


import org.springframework.orm.ObjectRetrievalFailureException;

import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.baseinfo.dao.PartnerDeptAreaDao;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;

/**
 * <p>
 * Title:���� dao��hibernateʵ��
 * </p>
 * <p>
 * Description:����
 * </p>
 * <p>
 * Mon Feb 09 10:57:12 CST 2009
 * </p>
 * 
 * @author LEE
 * @version 3.5
 * 
 */
public class PartnerDeptAreaDaoHibernate extends PartnerDeptDaoHibernate
		implements PartnerDeptAreaDao, ID2NameDAO {

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 * 
	 */
	public String deptIdToName(String id) {
		if (id != null && !"".equals(id)) {
			PartnerDept partnerDept = this.getPartnerDept(id);
			if (partnerDept != null && partnerDept.getId() != null)
				return partnerDept.getAreaName();
		}
		return "";
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerDeptDao#getPartnerDept(java.lang.String)
	 * 
	 */
	public PartnerDept getPartnerDept(final String id) {
		try {
			return (PartnerDept) getObject(PartnerDept.class, id);
		} catch (ObjectRetrievalFailureException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String deptIdToId(String id) {
		if (id != null && !"".equals(id)) {
			PartnerDept partnerDept = this.getPartnerDept(id);
			if (partnerDept != null && partnerDept.getId() != null)
				return partnerDept.getAreaId();
		}
		return "";
	}

}