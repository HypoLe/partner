package com.boco.eoms.sheet.base.dao;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.sheet.base.model.PnrSheetQuery;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 28, 2013 5:49:26 PM
 */
public interface IPnrSheetQueryDao extends Dao {
	
	/**
	 * 获取工单查询数据
	 * @param sheetType
	 * @param mainId
	 */
	public PnrSheetQuery getPnrSheetQuery(String sheetType,String mainId);
}	
