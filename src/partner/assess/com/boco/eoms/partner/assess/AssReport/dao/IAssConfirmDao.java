/**
 * 
 */
package com.boco.eoms.partner.assess.AssReport.dao;

import java.util.List;

import com.boco.eoms.partner.assess.AssReport.model.AssConfirm;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Dec 2, 2010 6:23:42 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssConfirmDao {

	/**
	 * 考核确认费用信息
	 * 
	 * @param AssConfirm
	 *                考核确认费用信息
	 *            
	 * @return void 
	 */
	public void saveAssConfirm(AssConfirm assConfirm) ;

	/**
	 * 按条件得到考核确认费用信息
	 * 
	 * @param whereStr
	 *                条件
	 *            
	 * @return list 
	 */	
	public List getAssConfirmList( final String whereStr ) ;

}
