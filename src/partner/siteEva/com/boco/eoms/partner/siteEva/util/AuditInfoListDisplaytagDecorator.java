package com.boco.eoms.partner.siteEva.util;

import org.displaytag.decorator.TableDecorator;
import com.boco.eoms.base.api.EOMSMgr;
import com.boco.eoms.commons.system.dict.exceptions.DictServiceException;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.commons.system.dict.util.Util;
import com.boco.eoms.partner.siteEva.model.SiteEvaAuditInfo;

public class AuditInfoListDisplaytagDecorator extends TableDecorator{
	
	public String getUserId() {
		SiteEvaAuditInfo auditInfo = (SiteEvaAuditInfo)getCurrentRowObject();
		return EOMSMgr.getOrgMgrs().getUserMgr().getUser(auditInfo.getAuditUser()).getUsername();
	}
 
	public String getReplyresult() {
		SiteEvaAuditInfo auditInfo = (SiteEvaAuditInfo) getCurrentRowObject();
		String status = "";
		try {
			status = (String) DictMgrLocator.getDictService()
					.itemId2name(
							Util.constituteDictId("dict-siteEva",
									"templateStatus"), auditInfo.getStatus());
		} catch (DictServiceException e) {
			status = Util.idNoName();
		}
		return status;
	}
}
