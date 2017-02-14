package com.boco.eoms.sheet.base.dao.hibernate;

import java.util.List;

import com.boco.eoms.sheet.base.dao.IPnrSheetQueryDao;
import com.boco.eoms.sheet.base.model.PnrSheetQuery;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 28, 2013 5:49:56 PM
 */
public class PnrSheetQueryDaoImpl  extends BaseSheetDaoHibernate  implements IPnrSheetQueryDao {

	/**
	 * 获取工单查询数据
	 * @param sheetType
	 * @param mainId
	 */
	@SuppressWarnings("unchecked")
	public PnrSheetQuery getPnrSheetQuery(String sheetType,String mainId){
		String hql = "from PnrSheetQuery where sheetType=? and mainId=?";
		List<PnrSheetQuery> list = this.getHibernateTemplate().find(hql, new Object[]{sheetType,mainId});
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
}
