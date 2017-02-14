package com.boco.eoms.partner.sheet.commontask.dao.hibernate;

import com.boco.eoms.partner.sheet.commontask.dao.IPnrCommonTaskMainDAO;
import com.boco.eoms.sheet.base.dao.hibernate.MainDAO;
/** 
 * Description: 通用任务工单
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 23, 2012 3:26:12 PM 
 */
 public class PnrCommonTaskMainDAOHibernate extends MainDAO implements IPnrCommonTaskMainDAO {
 	  
 	
	  
	   /* (non-Javadoc)
	    * @see com.boco.eoms.sheet.base.dao.hibernate.MainDAO#getMainName()
	    */
	  public String getMainName() {
		return "PnrCommonTaskMain";
	  }
 
 }