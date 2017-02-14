package com.boco.eoms.partner.baseinfo.dao;



import java.util.List;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.partner.baseinfo.model.PnrOrgFinalistSheet;

public interface IPnrOrgFinalistSheetDao extends CommonGenericDao<PnrOrgFinalistSheet, String> {
	public List getPnrOrgFinalistSheet(final String where) throws Exception;
}