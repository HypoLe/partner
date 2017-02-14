/**
 * 
 */
package com.boco.eoms.partner.assess.AssFlow.mgr.impl;

/**
 * <p>
 * Title:后评估模块流程管理业务类(传输实现类)
 * </p>
 * <p>
 * Description:后评估模块流程管理业务类(传输实现类)
 * </p>
 * <p>
 * Date:Nov 24, 2010 9:27:32 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class DeviceAssFlowMgrImpl extends AssFlowMgrImpl{
	public DeviceAssFlowMgrImpl() { 
			beenNameForRoleService = "IdeviceAssRoleService";
			beenNameForRoleIdList = "deviceAssRoleIdList";
			FLOW_XML_PATH = "com/boco/eoms/partner/assess/AssFlow/config/deviceAssFlow.xml";
		} 
}
