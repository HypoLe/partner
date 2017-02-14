package com.boco.activiti.partner.process.service;

import com.boco.activiti.partner.process.model.PnrActMaterial;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
/**
 * 
   * @author wangyue
   * @ClassName: IPnrTransferNewPrecheckService
   * @Version 版本 
   * @Copyright boco
   * @date Feb 4, 2015 10:18:21 AM
   * @description 本地网和干线预检预修工单与图片关系--service接口
 */
public interface IPnrActMaterialService extends CommonGenericService<PnrActMaterial> {
	
	public void deletePnrActMaterialsByProcessInstanceId(String processInstanceId);

}
