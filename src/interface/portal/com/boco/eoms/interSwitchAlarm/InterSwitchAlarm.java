package com.boco.eoms.interSwitchAlarm;

import java.util.HashMap;

import com.boco.eoms.util.InterfaceUtil;

public class InterSwitchAlarm {
	/**
	 * 
	 * 连通测试
	 * 
	 * @param isAlive
	 * @param
	 * @return isAliveResult(string类型。为空表示相应服务可用，非空表示该服务当前出现问题，错误返回信息用相应明文进行说明)
	 * @throws 执行报错
	 *             RuleToolXMLException
	 */
	public String isAlive() {
		// BocoLog.trace(this, 35, "收到握手信号");
		System.out.println("收到握手信号");
		String isAliveResult = "";

		return isAliveResult;
	}

	// serSupplier string(16) 服务提供方 M 参见附录A.3
	// serCaller string(16) 服务调用方 M 参见附录A.3
	// callerPwd string(32) 口令 M,N /
	// callTime string(20) 服务调用时间 M /
	// alarmId string(80) 网管告警流水号 M 网管系统简称_告警ID
	// alarmStaId string(80) 网管告警ID M /
	// oriAlarmId string(40) 原始告警号 M,N /
	// alarmTitle string(400) 告警标题 M /
	// alarmCreateTime string(20) 告警产生时间 M /
	// neType string(20) 网元类型 M /
	// 网络层次
	// neName string(32) 网元名称 M /
	// alarmLevel string(20) 告警级别 M 梳理后网管定义的告警级别（四级）
	/**
	 * 
	 * 告警接收 E-OMS系统提供该接口服务用于接收需要自动派发故障工单的网管告警信息
	 * 
	 * @paramserSupplier string(16) 服务提供方 M 参见附录A.3
	 * @paramserCaller string(16) 服务调用方 M 参见附录A.3
	 * @paramcallerPwd string(32) 口令 M,N /
	 * @paramcallTime string(20) 服务调用时间 M /
	 * @param alarmId
	 *            string(80) 网管告警流水号 M 网管系统简称_告警ID
	 * @param alarmStaId
	 *            string(80) 网管告警ID M /
	 * @param oriAlarmId
	 *            string(40) 原始告警号 M,N /
	 * @param alarmTitle
	 *            string(400) 告警标题 M /
	 * @param alarmCreateTime
	 *            string(20) 告警产生时间 M /
	 * @param neType
	 *            string(20) 网元类型 M /
	 * @param neName
	 *            string(32) 网元名称 M /
	 * @param alarmLevel
	 *            string(20) 告警级别 M 梳理后网管定义的告警级别（四级）
	 * @return result
	 *         输出为一个字符串，格式为“sheetNo=工单号;errList=错误描述”。如果派单成功：“工单号”不为空串，“错误描述”为空串；如果派单失败，“工单号”为空串，“错误列表”为错误描述。约定“sheetNo=”和“errList=”不管派单成功与否都必须有，只是值可以为空串。
	 * @throws 执行报错RuleToolXMLException
	 */
	public String newAlarm(String serSupplier, String serCaller,
			String callerPwd, String callTime, String opDetail)
			throws Exception {
		String xpath = "//opDetail/recordInfo/fieldInfo";
		HashMap sheetMap = new HashMap();
		sheetMap.put("serSupplier", serSupplier);
		sheetMap.put("serCaller", serCaller);
		sheetMap.put("callerPwd", callerPwd);
		sheetMap.put("callTime", callTime);
		InterfaceUtil interfaceUtil = new InterfaceUtil();
		sheetMap = interfaceUtil
				.xmlElements(opDetail, sheetMap, "FieldContent",xpath);

		// String sheetKey = CommonFaultBO.performAdd(sheetMap);
		// System.out.println("dddddddddddddd:"+sheetKey);
		// String result = "sheetNo="+sheetKey + ";errList=";
		return "";
	}

	// 参数名称 参数类型 中文名称 限定 说明
	// serSupplier string(16) 服务提供方 M 参见附录A.3
	// serCaller string(16) 服务调用方 M 参见附录A.3
	// callerPwd string(32) 口令 M,N /
	// callTime string(20) 服务调用时间 M /
	// alarmId
	// string(80) 网管告警ID M 网管系统简称_告警ID
	// alarmStatus Integer(2) 告警状态 M 1：自动清除；2：手工清除；
	// clearTime string(20) 告警清除时间 M /
	// staffNo string(32) 手工清除告警人员的工号 M,N /

	/**
	 * 
	 * 告警同步 (syncAlarm)
	 * 当已派发了故障工单的网管告警的状态发生变化时，网管系统调用E-OMS系统提供的接口服务，同步两个系统中的告警状态，并给出告警清除时间。
	 * 
	 * @param serSupplier
	 *            string(16) 服务提供方 M 参见附录A.3
	 * @param serCaller
	 *            string(16) 服务调用方 M 参见附录A.3
	 * @param callerPwd
	 *            string(32) 口令 M,N /
	 * @param callTime
	 *            string(20) 服务调用时间 M /
	 * @param alarmId
	 *            string(80) 网管告警ID M 网管系统简称_告警ID
	 * @param alarmStatus
	 *            Integer(2) 告警状态 M 1：自动清除；2：手工清除；
	 * @paramclearTime string(20) 告警清除时间 M /
	 * @param staffNo
	 *            string(32) 手工清除告警人员的工号 M,N /
	 * @return 输出为字符串。格式为“sheetNo=工单号;如果调用失败，则如果该告警在电子运维系统中不存在对应的工单则sheetNo为空串，，只是值可以为空串。
	 * @throws 执行报错
	 *             RuleToolXMLException
	 */
	public String syncAlarm(String serSupplier, String serCaller,
			String callerPwd, String callTime, String opDetail)
			throws Exception {

		HashMap sheetMap = new HashMap();
		sheetMap.put("serSupplier", serSupplier);
		sheetMap.put("serCaller", serCaller);
		sheetMap.put("callerPwd", callerPwd);
		sheetMap.put("callTime", callTime);
		InterfaceUtil interfaceUtil = new InterfaceUtil();
		String xpath = "//opDetail/recordInfo/fieldInfo";
		sheetMap = interfaceUtil
				.xmlElements(opDetail, sheetMap, "FieldContent",xpath);
		// String id = CommonFaultBO.updateMain(sheetMap);
		// String result = "sheetNo="+id+";errList=";
		return "";
	}

}
