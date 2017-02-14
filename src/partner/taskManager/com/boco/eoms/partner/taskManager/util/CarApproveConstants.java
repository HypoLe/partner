package com.boco.eoms.partner.taskManager.util;

import java.util.Map;

import com.google.common.collect.Maps;

public class CarApproveConstants {

	public static final String CAR_APPROVE_NEW  = "new";               //新增申请
	public static final String CAR_APPROVE_PASS = "pass";              //申请通过
	public static final String CAR_APPROVE_RETURN = "return";          //申请被退回
	public static final String CAR_APPROVE_BACK = "back"; 			   //车辆归还
	public static final String CAR_APPROVE_AGAIN = "again";            //重新申请
	
	public static Map<String,String> carApproveMap = Maps.newHashMap();
	
	static{
		
		carApproveMap.put(CAR_APPROVE_NEW, "处理环节: 新增申请 操作类型: 提交申请");
		carApproveMap.put(CAR_APPROVE_PASS, "处理环节: 车辆审批 操作类型: 审批通过");
		carApproveMap.put(CAR_APPROVE_RETURN, "处理环节: 车辆审批 操作类型: 审批驳回");
		carApproveMap.put(CAR_APPROVE_BACK, "处理环节: 车辆归还 操作类型: 车辆归还");
		carApproveMap.put(CAR_APPROVE_AGAIN, "处理环节: 车辆申请 操作类型: 提交申请");
		
	}
}
