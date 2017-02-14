package com.boco.eoms.partner.sheet.commontask.service;

import java.util.List;
import java.util.Map;

public interface PnrCommonTaskStatisticMgr {
	
	public String getDeptIdToAreaId(final String deptid);
	
	public List getAreaIdToCompanyIdList(final String deptid);
	
	public List getSheetAreaIndexList( final String deptid ,final String whereStr);
	
	public List getAreaIdList(final String parentareaid);
	
	public List getCompanyIdList(final String parentcompanyid);
	
	public List getCompanyDeptToIdList(final String deptid);
	
	public String getCompanyIdToDeptIdList(final String compid);
	
	public Map querySheetIndexList(final Integer total, final Integer curPage, final Integer pageSize,final String deptid, final String flag, final String whereStr);
	
}
