package com.boco.activiti.partner.process.service;

import java.util.Map;

import com.boco.activiti.partner.process.model.NetResInspectUser;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;

/**
 * 
 	* @author WANGJUN
 	* @ClassName: INetResInspectUserService
 	* @Version 1.0 
 	* @ModifiedBy 
 	* @Copyright BOCO
 	* @date Sep 7, 2016 2:39:53 PM
 	* @description 巡检众筹新注册用户
 */
public interface INetResInspectUserService extends CommonGenericService<NetResInspectUser> {
	/**
	 * 	 通过地市ID获取到联通的区县ID
	 	 * @author WANGJUN
	 	 * @title: getDeptIdByAreaId
	 	 * @date Sep 7, 2016 4:07:45 PM
	 	 * @return String
	 */
	public String getDeptIdByAreaId(String areaId);
	
	/**
	 * 	 保存巡检众筹用户
	 	 * @author WANGJUN
	 	 * @title: saveNetResInspectUser
	 	 * @date Sep 8, 2016 8:42:09 AM
	 	 * @param param
	 	 * @return String
	 */
	public String saveNetResInspectUser(Map<String,String> param);
}
