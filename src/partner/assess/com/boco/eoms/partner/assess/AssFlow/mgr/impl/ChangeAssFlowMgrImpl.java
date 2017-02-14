/**
 * 
 */
package com.boco.eoms.partner.assess.AssFlow.mgr.impl;

/**
 * <p>
 * Title:后评估模块流程管理业务类(线路实现类)
 * </p>
 * <p>
 * Description:后评估模块流程管理业务类(线路实现类)
 * </p>
 * <p>
 * Date:Nov 24, 2010 9:27:32 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class ChangeAssFlowMgrImpl extends AssFlowMgrImpl{
	public ChangeAssFlowMgrImpl() { 
			beenNameForRoleService = "IchangeAssRoleService";
			beenNameForRoleIdList = "changeAssRoleIdList";
			FLOW_XML_PATH = "com/boco/eoms/partner/assess/AssFlow/config/changeAssFlow.xml";
		} 
}
