package com.boco.eoms.partner.baseinfo.dao.hibernate;

import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.baseinfo.dao.IPnrSourceStandardConfigDao;
import com.boco.eoms.partner.baseinfo.model.PnrSourceStandardConfig;

/**
 * 类说明：代维资源配置模块 Dao实现类
 * 创建人： fengguangping
 * 创建时间：2012-12-27 16:18:56
 */
public class PnrSourceStandardConfigDaoHibernateImpl extends CommonGenericDaoImpl<PnrSourceStandardConfig, String>
		implements IPnrSourceStandardConfigDao,ID2NameDAO  {
		public String id2Name(String id) throws DictDAOException {
			return "";
		}
}