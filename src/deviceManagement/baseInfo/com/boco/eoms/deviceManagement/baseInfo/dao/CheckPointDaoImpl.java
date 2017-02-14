package com.boco.eoms.deviceManagement.baseInfo.dao;


import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckPoint;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;

public class CheckPointDaoImpl extends CommonGenericDaoImpl<CheckPoint, String>
		implements CheckPointDao,ID2NameDAO  {
		public String id2Name(String id) throws DictDAOException {
			CheckPoint cp = this.find(id);
			return cp.getResourceName();
		}
}