package com.boco.eoms.partner.property.dao.hibernate;

import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.property.dao.IPnrRentBillsDao;
import com.boco.eoms.partner.property.model.PnrRentBills;

/**
 * 类说明：物业合同管理-租赁费用 Dao实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-08-27 16:57:47
 */
public class PnrRentBillsDaoHibernateImpl extends CommonGenericDaoImpl<PnrRentBills, String>
		implements IPnrRentBillsDao,ID2NameDAO  {
		public String id2Name(String id) throws DictDAOException {
			return "";
		}
}