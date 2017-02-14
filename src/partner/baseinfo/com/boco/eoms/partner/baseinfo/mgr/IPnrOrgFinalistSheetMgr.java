package com.boco.eoms.partner.baseinfo.mgr;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.baseinfo.model.PnrOrgFinalistSheet;

public interface IPnrOrgFinalistSheetMgr extends CommonGenericService<PnrOrgFinalistSheet> {
	public List getPnrOrgFinalistSheet(final String where) throws Exception;
}
