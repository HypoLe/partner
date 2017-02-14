package com.boco.eoms.partner.property.dao.hibernate;

import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.property.dao.IPnrPropertyAgreementDao;
import com.boco.eoms.partner.property.model.PnrPropertyAgreement;

/**
 * 类说明：电费费用记录物业合同 Dao实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-08-27 16:57:47
 */
public class PnrPropertyAgreementDaoHibernateImpl extends CommonGenericDaoImpl<PnrPropertyAgreement, String>
		implements IPnrPropertyAgreementDao,ID2NameDAO  {
		public String id2Name(String id) throws DictDAOException {
			return "";
		}
}