/**
 * 
 */
package com.boco.eoms.partner.assess.AssReport.mgr;

import java.util.List;

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
public interface IAssConfirmMgr {
	
	public void saveAssConfirm(AssConfirm assConfirm);

	public List getAssConfirmList( final String whereStr ) ;
}
