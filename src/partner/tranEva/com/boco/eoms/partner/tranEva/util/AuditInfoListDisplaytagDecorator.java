package com.boco.eoms.partner.tranEva.util;

import org.displaytag.decorator.TableDecorator;
import com.boco.eoms.base.api.EOMSMgr;
import com.boco.eoms.commons.system.dict.exceptions.DictServiceException;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.commons.system.dict.util.Util;
import com.boco.eoms.partner.tranEva.model.TranEvaAuditInfo;

public class AuditInfoListDisplaytagDecorator extends TableDecorator{
	
	public String getUserId() {
		TranEvaAuditInfo auditInfo = (TranEvaAuditInfo)getCurrentRowObject();
		return EOMSMgr.getOrgMgrs().getUserMgr().getUser(auditInfo.getAuditUser()).getUsername();
	}

	public String getReplyresult() {
		TranEvaAuditInfo auditInfo = (TranEvaAuditInfo) getCurrentRowObject();
		String status = "";
		try {
			status = (String) DictMgrLocator.getDictService()
					.itemId2name(
							Util.constituteDictId("dict-tranEva",
									"templateStatus"), auditInfo.getStatus());
		} catch (DictServiceException e) {
			status = Util.idNoName();
		}
		return status;
	}
}
