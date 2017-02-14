package com.boco.eoms.partner.inspect.util;

import java.util.Map;

import com.boco.eoms.partner.deviceInspect.switchcfg.PnrDeviceInspectSwitchConfig;
import com.google.common.collect.Maps;

/** 
 * Description: 巡检常量
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 11, 2012 10:30:04 AM 
 */
public class InspectConstants {
	/** 巡检工单开关(即巡检已工单的形式，计划、月任务巡检结束时间可以跨月，同专业、同代维组织、同月可以制定多条计划等) */
	private static boolean sheetInspectSwitch;
	
	private static PnrDeviceInspectSwitchConfig switchConfig; //系统开关
	
	//省级地域ID
	//public final static String PROVINCE_AREA_ID = "24";
	//移动地市区县管理人员角色ID
	public final static String PNR_MANAGER_ROLE = "441";
	
	public final static Integer BURST_RES_CYCLE_DAY = 30;
	//是否
	public final static Integer YES = 1; //是
	public final static Integer NO = 0;  //否
	
	//审批
	public final static Integer APPROVE_STATUS_REFUSE = 0;  //驳回
	public final static Integer APPROVE_STATUS_PASSED = 1;  //通过
	public final static Integer APPROVE_STATUS_WAIT = 2;    //待审批
	public final static Integer APPROVE_STATUS_NOCOMMIT = 3;//未提交
	
	//周期
	public static final String PERIOD_OF_WEEK = "week";                //周
	public static final String PERIOD_OF_MONTH = "month";              //月
	public static final String PERIOD_OF_DOUBLE_MONTH = "doubleMonth"; //双月
	public static final String PERIOD_OF_QUARTER = "quarter";          //季度
	public static final String PERIOD_OF_HALF_YEAR = "halfOfYear";     //半年
	public static final String PERIOD_OF_YEAR = "year";                //年
	public static final String PERIOD_OF_HALF_MONTH = "halfOfMonth";          //半月
	
	//计划类型
	public final static Integer PLAN_TYPE_CHANGE = 0;  //变更计划
	public final static Integer PLAN_TYPE_NORMAL = 1;  //普通计划
	
	public static Map<Integer,String> yesOrNoMap = Maps.newHashMap(); 
	public static Map<Integer,String> approveStatusMap = Maps.newHashMap(); 
	public static Map<String,String> cycleMap = Maps.newHashMap(); 
	public static Map<Integer,String> planTypeMap = Maps.newHashMap(); 
	
	static{
		yesOrNoMap.put(YES, "是");
		yesOrNoMap.put(NO, "否");
		
		approveStatusMap.put(APPROVE_STATUS_REFUSE, "驳回");
		approveStatusMap.put(APPROVE_STATUS_PASSED, "通过");
		approveStatusMap.put(APPROVE_STATUS_WAIT, "待审批");
		approveStatusMap.put(APPROVE_STATUS_NOCOMMIT, "未提交");
		
		cycleMap.put(PERIOD_OF_WEEK, "周");
		cycleMap.put(PERIOD_OF_MONTH, "月");
		cycleMap.put(PERIOD_OF_DOUBLE_MONTH, "双月");
		cycleMap.put(PERIOD_OF_QUARTER, "季度");
		cycleMap.put(PERIOD_OF_HALF_YEAR, "半年");
		cycleMap.put(PERIOD_OF_YEAR, "年");
		cycleMap.put(PERIOD_OF_HALF_MONTH, "半月");
		
		planTypeMap.put(PLAN_TYPE_CHANGE, "变更计划");
		planTypeMap.put(PLAN_TYPE_NORMAL, "普通计划");
	}

	/**上传巡检图片类型常量--环境照片*/
	public final static String IMAGE_UPLOAD_ENVIRONMENT = "IMAGE_UPLOAD_ENVIRONMENT";
	/**上传巡检图片类型常量--设备照片*/
	public final static String IMAGE_UPLOAD_DEVICE = "IMAGE_UPLOAD_DEVICE";
	/**上传巡检图片类型常量--签字表照片照片*/
	public final static String IMAGE_UPLOAD_SIGNSHEET = "IMAGE_UPLOAD_SIGNSHEET";
	/**维护员提交巡检结果--照片缩略图(福建二期手机接口部分)*/
	public final static String TASK_ITEM_PHOTO_THUMBNAIL = "TASK_ITEM_PHOTO_THUMBNAIL";
	/**维护员提交巡检结果--照片(福建二期手机接口部分)*/
	public final static String TASK_ITEM_PHOTO = "TASK_ITEM_PHOTO";
	
	public static boolean getSheetInspectSwitch() {
		return sheetInspectSwitch;
	}
	public static void setSheetInspectSwitch(boolean sheetInspectSwitch) {
		InspectConstants.sheetInspectSwitch = sheetInspectSwitch;
	}
	public static PnrDeviceInspectSwitchConfig getSwitchConfig() {
		return switchConfig;
	}
	public static void setSwitchConfig(PnrDeviceInspectSwitchConfig switchConfig) {
		InspectConstants.switchConfig = switchConfig;
	}

}
