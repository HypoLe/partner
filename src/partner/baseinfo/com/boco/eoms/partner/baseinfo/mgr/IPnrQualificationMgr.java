package com.boco.eoms.partner.baseinfo.mgr;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.baseinfo.model.PnrQualification;

/**  
 * @Title: IPnrQualificationMgr.java
 * @Package com.boco.eoms.partner.baseinfo.mgr
 * @Description: XXX
 * @author fengguangping fengguangping@boco.com.cn
 * @date Mar 18, 2013  5:45:00 PM  
 */
public interface IPnrQualificationMgr extends CommonGenericService<PnrQualification>{
	public List<PnrQualification> findPnrQualificationsByDeptUUid(String id);
	public String createPnrQualificationSystemNo(String id);
}
