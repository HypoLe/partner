/**
 * 
 */
package com.boco.eoms.partner.assess.AssReport.mgr.impl;

import java.util.List;

import com.boco.eoms.partner.assess.AssReport.dao.IAssConfirmDao;
import com.boco.eoms.partner.assess.AssReport.mgr.IAssConfirmMgr;
import com.boco.eoms.partner.assess.AssReport.model.AssConfirm;
/**
 * <p>
 * Title:操作确认费用信息方法基类
 * </p>
 * <p>
 * Description:操作确认费用信息方法基类
 * </p>
 * <p>
 * Date:Dec 2, 2010 6:19:40 AM
 * </p>
 * 
 * @author 贲伟玮
 * @version 1.0
 * 
 */
public class AssConfirmMgrImpl implements IAssConfirmMgr {


	private IAssConfirmDao assConfirmDao;

	public IAssConfirmDao getAssConfirmDao() {
		return assConfirmDao;
	}


	public void setAssConfirmDao(IAssConfirmDao assConfirmDao) {
		this.assConfirmDao = assConfirmDao;
	}

	public void saveAssConfirm(AssConfirm assConfirm) {
		assConfirmDao.saveAssConfirm(assConfirm);
	}
	
	public List getAssConfirmList( final String whereStr ) {
		return assConfirmDao.getAssConfirmList(whereStr);
	}

}
