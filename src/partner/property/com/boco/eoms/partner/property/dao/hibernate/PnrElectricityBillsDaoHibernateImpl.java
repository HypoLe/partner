package com.boco.eoms.partner.property.dao.hibernate;

import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.property.dao.IPnrElectricityBillsDao;
import com.boco.eoms.partner.property.model.PnrElectricityBills;

/**
 * 类说明：物业合同管理-电费费用记录 Dao实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-08-27 16:57:45
 */
public class PnrElectricityBillsDaoHibernateImpl extends CommonGenericDaoImpl<PnrElectricityBills, String>
		implements IPnrElectricityBillsDao,ID2NameDAO  {
		public String id2Name(String id) throws DictDAOException {
			return "";
		}
}