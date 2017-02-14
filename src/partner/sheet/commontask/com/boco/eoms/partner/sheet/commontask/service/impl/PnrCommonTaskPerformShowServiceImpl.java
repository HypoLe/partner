package com.boco.eoms.partner.sheet.commontask.service.impl;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.sheet.base.model.BaseMain;
import com.boco.eoms.sheet.base.model.Performer;
import com.boco.eoms.sheet.base.service.impl.SheetPerformShowServiceImpl;
import com.boco.eoms.sheet.base.util.SheetBeanUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 16, 2012 5:08:59 PM 
 */
public class PnrCommonTaskPerformShowServiceImpl extends SheetPerformShowServiceImpl{
	/**
     * 呈现工单处理子界面
     */
	@SuppressWarnings("unchecked")
	public void showInputDealPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		super.showInputDealPage(mapping, form, request, response);
		
		String taskName = request.getParameter("taskName");
		String operateType = request.getParameter("operateType");
		//如果工单处理子界面是进行审批处理
		if("AuditTask".equals(taskName)){
			BaseMain main = (BaseMain)request.getAttribute("sheetMain");
			if(main!=null){
				//从main表取出sendObjectTotalJSON解析后出处理人并对dealPerformer、dealPerformerLeader、
				//dealPerformerType进行赋值以便如果是审批通过则流程流转到处理人
				String sendObjectTotalJSON = main.getSendObjectTotalJSON();
				Type listType = new TypeToken<List<Performer>>(){}.getType();  
				List<Performer> list = new Gson().fromJson(sendObjectTotalJSON, listType);
				String dealPerformer = "";
				String dealPerformerLeader = "";
				String dealPerformerType = "";
				for (Performer performer : list) {
					if("dealPerformer".equals(performer.getCategoryId())){
						dealPerformer = dealPerformer + performer.getId() + ",";
						dealPerformerLeader = dealPerformerLeader + performer.getId() + ",";
						dealPerformerType = dealPerformerType + performer.getNodeType() + ",";
					}
				}
				dealPerformer = dealPerformer.substring(0, dealPerformer.length()-1);
				dealPerformerLeader = dealPerformerLeader.substring(0, dealPerformerLeader.length()-1);
				dealPerformerType = dealPerformerType.substring(0, dealPerformerType.length()-1);
				
				//由于没有虚拟组的概念，暂将dealPerformer和dealPerformerLeader设置成一样
				request.setAttribute("dealPerformer", dealPerformer);
				request.setAttribute("dealPerformerLeader", dealPerformerLeader);
				request.setAttribute("dealPerformerType", dealPerformerType);
			}
		}else if("HoldTask".equals(taskName) && operateType.equals("54")){
			BaseMain main = (BaseMain)request.getAttribute("sheetMain");
			HashMap<String,Object> mainMap = SheetBeanUtils.bean2Map(main);
			Integer mainBackTime = (Integer)mainMap.get("mainBackTime");
			String mainId = StaticMethod.nullObject2String(mainMap.get("id"));
			String condition = "mainId='"+mainId+"' and operateType=95 and mainBackTime="+mainBackTime;
			List linkList = this.getLinkService().getLinksBycondition(condition, this.getLinkService().getLinkObject().getClass().getName());
			
			request.setAttribute("linkList", linkList);
			request.setAttribute("mainBackTime", mainBackTime.intValue()+1+"");
		}
	}
}
