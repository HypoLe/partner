package com.boco.eoms.partner.sheet.commontask.dao;

import java.util.Map;

public interface PnrCommonTaskStatisticDaoHibernate {
		
	public Map querySheetIndexList(final Integer total, final Integer curPage, final Integer pageSize, final String mainid);
	
}
