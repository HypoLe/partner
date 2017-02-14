package com.boco.eoms.partner.deviceInspect.switchcfg;

/**
 * 
 * 描述：巡检点与网络资源开关配置（对应配置文件为pnr-inspect2-switch-config.xml）
 * 作者：zhangkeqi
 * 时间：Jan 26, 2013-4:26:04 PM
 */
public class PnrDeviceInspectSwitchConfig {

	/**总开关*/
	private boolean openMainSwitch;
	/** 巡检工单开关(即巡检以工单的形式，计划、月任务巡检结束时间可以跨月，同专业、同代维组织、同月可以制定多条计划等) */
	private boolean sheetInspectSwitch;
	/**线路巡检开关*/
	private boolean openTransLineInspect;
	/**启用考核流程配置，全国时关闭，省级属地化打开*/
	private boolean openEvaluationSwitch;
	
	/** 工单权限开关(工单的已归档工单、已作废工单、工单查询是否需要带权限) */
	private boolean openSheetQueryRights;

	public boolean isOpenMainSwitch() {
		return openMainSwitch;
	}

	public void setOpenMainSwitch(boolean openMainSwitch) {
		this.openMainSwitch = openMainSwitch;
	}

	public boolean isSheetInspectSwitch() {
		return sheetInspectSwitch;
	}

	public void setSheetInspectSwitch(boolean sheetInspectSwitch) {
		this.sheetInspectSwitch = sheetInspectSwitch;
	}

	public boolean isOpenTransLineInspect() {
		return openTransLineInspect;
	}

	public void setOpenTransLineInspect(boolean openTransLineInspect) {
		this.openTransLineInspect = openTransLineInspect;
	}

	public boolean isOpenEvaluationSwitch() {
		return openEvaluationSwitch;
	}

	public void setOpenEvaluationSwitch(boolean openEvaluationSwitch) {
		this.openEvaluationSwitch = openEvaluationSwitch;
	}

	public boolean isOpenSheetQueryRights() {
		return openSheetQueryRights;
	}

	public void setOpenSheetQueryRights(boolean openSheetQueryRights) {
		this.openSheetQueryRights = openSheetQueryRights;
	}
	
	
}
