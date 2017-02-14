package com.boco.eoms.partner.deviceAssess.dao;

import java.util.List;
import com.boco.eoms.base.dao.Dao;

public interface QueryEomsSheetJdbc extends Dao{
//	获得故障工单数据
	public List getFaultSheetList(); 
}
