package com.boco.eoms.partner.assiEva.util;

import org.displaytag.decorator.TableDecorator;
import com.boco.eoms.base.api.EOMSMgr;
import com.boco.eoms.commons.system.dict.exceptions.DictServiceException;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.commons.system.dict.util.Util;
import com.boco.eoms.partner.assiEva.model.AssiEvaAuditInfo;

public class AuditInfoListDisplaytagDecorator extends TableDecorator{
	
	public String getUserId() {
		AssiEvaAuditInfo auditInfo = (AssiEvaAuditInfo)getCurrentRowObject();
		return EOMSMgr.getOrgMgrs().getUserMgr().getUser(auditInfo.getAuditUser()).getUsername();
	}

	public String getReplyresult() {
		AssiEvaAuditInfo auditInfo = (AssiEvaAuditInfo) getCurrentRowObject();
		String status = "";
		try {
			status = (String) DictMgrLocator.getDictService()
					.itemId2name(
							Util.constituteDictId("dict-assiEva",
									"templateStatus"), auditInfo.getStatus());
		} catch (DictServiceException e) {
			status = Util.idNoName();
		}
		return status;
	}
}
