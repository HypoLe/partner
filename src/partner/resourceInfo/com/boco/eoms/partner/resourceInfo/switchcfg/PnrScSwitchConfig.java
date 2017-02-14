package com.boco.eoms.partner.resourceInfo.switchcfg;

/**
 * 
 * 描述：四川版本开关配置（对应配置文件为pnr-resourceInfo-switch-config.xml）
 * 作者：zhangkeqi
 * 时间：Jan 26, 2013-4:26:04 PM
 */
public class PnrScSwitchConfig {

	/**总开关*/
	private boolean openScSwitch;
	/** 巡检工单开关(即巡检以工单的形式，计划、月任务巡检结束时间可以跨月，同专业、同代维组织、同月可以制定多条计划等) */
//	private boolean sheetInspectSwitch;

	public boolean isOpenScSwitch() {
		return openScSwitch;
	}

	public void setOpenScSwitch(boolean OpenScSwitch) {
		this.openScSwitch = OpenScSwitch;
	}

//	public boolean isSheetInspectSwitch() {
//		return sheetInspectSwitch;
//	}
//
//	public void setSheetInspectSwitch(boolean sheetInspectSwitch) {
//		this.sheetInspectSwitch = sheetInspectSwitch;
//	}
	
	
}
