package com.boco.eoms.mobile.sheet.dao;

import java.util.Map;

import com.boco.eoms.base.dao.Dao;

public interface SheetDao extends Dao {
	public Map<String,Integer> getUnDoneCheckInfo(String userId);
}
