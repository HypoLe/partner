package com.boco.eoms.sheet.base.webapp.action;

import java.util.HashMap;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.sheet.base.model.EomsDoneSheetView;
import com.boco.eoms.sheet.base.model.EomsUndoSheetView;
import com.boco.eoms.sheet.base.util.SheetBeanUtils;

/**
 * modify by chenyuanshu 2012-04-19
 * 由于某省直接把eoms里的url放到门户里，所以需要将token取出供后续页面中使用
 *
 */

public class ProcessAllListDisplaytagDecoratorHelper extends TableDecorator {
	
	@SuppressWarnings("unchecked")
	public String getSheetId() {
		String url = StaticMethod.nullObject2String(this.getPageContext().getAttribute("url"));
		String Token = StaticMethod.nullObject2String(this.getPageContext().getAttribute("Token"));
		HashMap taskMap = new HashMap();
		boolean ifDoneList = false;
		try {
			EomsUndoSheetView taskView = (EomsUndoSheetView) getCurrentRowObject();
			taskMap = SheetBeanUtils.bean2Map(taskView);
		} catch (Exception e) {
			ifDoneList = true;
			EomsDoneSheetView taskView = (EomsDoneSheetView) getCurrentRowObject();
			taskMap = SheetBeanUtils.bean2Map(taskView);
		}

		String sheetType = StaticMethod.nullObject2String(taskMap
				.get("sheetType"));
		if(!sheetType.equals("")){
			sheetType = sheetType.trim();
		}
		url = url.replaceAll("eomsallsheetlist.do", sheetType + ".do");
		// 由于新业务试点工单的特殊性，所以必须将之replace掉
		if (sheetType.equals("businesspilot")) {
			sheetType = "business";
		}

		url = "../" + sheetType + "/" + url;
		String sheetKey = StaticMethod.nullObject2String(
				taskMap.get("sheetKey")).trim();
		String taskId = StaticMethod.nullObject2String(taskMap.get("id"))
				.trim();
		String taskName = StaticMethod.nullObject2String(
				taskMap.get("taskName")).trim();
		String operateRoleId = StaticMethod.nullObject2String(
				taskMap.get("operateRoleId")).trim();
		String TKID = StaticMethod.nullObject2String(taskMap.get("id")).trim();
		String piid = StaticMethod.nullObject2String(taskMap.get("processId"))
				.trim();
		String taskStatus = StaticMethod.nullObject2String(
				taskMap.get("taskStatus")).trim();
		String preLinkId = StaticMethod.nullObject2String(
				taskMap.get("preLinkId")).trim();
		String sheetId = StaticMethod.nullObject2String(taskMap.get("sheetId"))
				.trim();
		String url2 = "";
		if (ifDoneList == false) {
			url2 = url + "?method=showDetailPage&sheetKey=" + sheetKey
					+ "&taskId=" + taskId + "&taskName=" + taskName
					+ "&operateRoleId=" + operateRoleId + "&TKID=" + TKID
					+ "&piid=" + piid + "&taskStatus=" + taskStatus
					+ "&preLinkId=" + preLinkId + Token;
		} else {
			url2 = url + "?method=showDetailPage&sheetKey=" + sheetKey + Token;
		}
		return "<a  href='" + url2 + "' target='_blank'>" + sheetId + "</a>";

	}
	
	/**
     * 根据超时提醒改变行的样式
     */
    @SuppressWarnings("unchecked")
	@Override
	public String addRowClass(){
    	
    	EomsUndoSheetView sheetView = (EomsUndoSheetView) getCurrentRowObject();
        String urlstr = "";
        String overtimeType=StaticMethod.nullObject2String(sheetView.getOvertimeType()).trim();
        if(overtimeType.equals("1")){ //已超时
        	urlstr="serious";
        }else if(overtimeType.equals("2")){ //即将超时
        	urlstr="alert colorrow";
        }
        return urlstr;
    }
}
