package com.boco.eoms.partner.property.dao.hibernate;

import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.property.dao.IPnrRentBillsCountDao;
import com.boco.eoms.partner.property.model.PnrRentBillsCount;

/**
 * 类说明：网络资源--空间资源--租赁费用统计 Dao实现类
 * 创建人： fengguangping
 * 创建时间：2012-09-28 10:36:40
 */
public class PnrRentBillsCountDaoHibernateImpl extends CommonGenericDaoImpl<PnrRentBillsCount, String>
		implements IPnrRentBillsCountDao,ID2NameDAO  {
		public String id2Name(String id) throws DictDAOException {
			return "";
		}
}