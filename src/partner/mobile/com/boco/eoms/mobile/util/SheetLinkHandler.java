package com.boco.eoms.mobile.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.mobile.sheet.service.ISheetServiceMgr;
import com.boco.eoms.sheet.base.model.BaseLink;
import com.boco.eoms.sheet.base.model.BaseMain;

public class SheetLinkHandler {

	public static void handlerLinkData(HttpServletRequest request,HttpServletResponse response){
		List HISTORY = (List) request.getAttribute("HISTORY");
		Map numberMap = (Map) request.getAttribute("numberMap");
		List deptIdarray = (List) request.getAttribute("deptIdtable");
		Map deptIdMap = (Map) request.getAttribute("deptIdMap");
		String taskName = (String) request.getAttribute("taskName");
		String ifWaitForSubTask = (String) request.getAttribute("ifWaitForSubTask");
		BaseMain main = (BaseMain) request.getAttribute("sheetMain");
		BaseLink link = (BaseLink) request.getAttribute("linkClassName");
		String beanName = (String) request.getAttribute("beanName");
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("HISTORY",HISTORY);
		returnMap.put("numberMap",numberMap);
		returnMap.put("deptIdarray",deptIdarray);
		returnMap.put("deptIdMap",deptIdMap);
		returnMap.put("taskName",taskName);
		returnMap.put("ifWaitForSubTask",ifWaitForSubTask);
		returnMap.put("main",main);
		returnMap.put("link",link);
		returnMap.put("beanName",beanName);
		
		id2Name(main,HISTORY,beanName);
		String returnValue = MobileCommonUtils.replaceToJson(returnMap);
		System.out.println(returnValue);
	}
	
	public static void id2Name(BaseMain main,List HISTORY,String beanName){
		ISheetServiceMgr serviceMgr = (ISheetServiceMgr)ApplicationContextHolder.getInstance().getBean("iSheetServiceMgr");
		if(null == beanName||"".equals(beanName)){
			return;
		}
		serviceMgr.id2NameMain(main,beanName);
		for(int i = 0;i<HISTORY.size();i++){
			serviceMgr.id2NameLink((BaseLink)HISTORY.get(i),beanName);
		}

	}
}
