package com.boco.activiti.partner.process.service;

import com.boco.activiti.partner.process.model.TransferMaleGuestInform;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;

/**
 * 公客工单通知service
 * @author wangyue
 *
 */
public interface IPnrTransferMaleGuestInformService extends CommonGenericService<TransferMaleGuestInform> {
	/**
	 * 催单方法
	  * @author wangyue
	  * @title: reminder
	  * @date Nov 21, 2014 11:21:36 AM
	  * @param workNumber void
	 */
	public void reminder(String workNumber);
}
