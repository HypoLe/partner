package com.boco.eoms.partner.baseinfo.dao;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;

import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;

/**
 * <p>
 * Title:����
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
public interface PartnerDeptAreaDao extends Dao {

	public String deptIdToName(final String id);
//	根据UID查出DictId
	public String deptIdToId(final String id);
}