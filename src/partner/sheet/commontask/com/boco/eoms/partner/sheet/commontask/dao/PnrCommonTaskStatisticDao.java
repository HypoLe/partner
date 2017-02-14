package com.boco.eoms.partner.sheet.commontask.dao;

import java.util.List;

public interface PnrCommonTaskStatisticDao {
	
	public String getDeptIdToAreaId(final String deptid);
	
	public List getAreaIdToCompanyIdList(final String deptid);
	
	public List getSheetAreaIndexList(final String deptid, final String whereStr);
	
	public List getAreaIdList(final String parentareaid);
	
	public List getCompanyIdList(final String parentcompanyid);
	
	public List getCompanyDeptToIdList(final String deptid);
	
	public String getCompanyIdToDeptIdList(final String compid);
	
	public String getMainIdStr(final String sqlStr);
	
}
