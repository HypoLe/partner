package com.boco.eoms.partner.sheet.commontask.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.sheet.base.model.BaseLink;
import com.boco.eoms.sheet.base.service.IPreEngineDataHandler;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 18, 2012 11:19:28 AM 
 */
public class PnrCommonTaskPreEngineDataHandlerImpl implements IPreEngineDataHandler {
	
	@SuppressWarnings("unchecked")
	public void prePerformAddBusiMap(HttpServletRequest request,
			HashMap mainMap, HashMap linkMap, HashMap operate) {
		String phaseId = request.getParameter("phaseId");
		if(!"DraftTask".equals(phaseId)){
			//新增提交选择了审批人的情况
			if(isChooseAuditPerformer(operate)){
				changePerformer2AuditPerformer(operate);
			}
		}else{
			//如果是保存草稿应该把dealPerformer相关内容清空，这样流程才会流转到派单人
			operate.put("dealPerformer", "");
			operate.put("dealPerformerLeader", "");
			operate.put("dealPerformerType", "");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> prePerformAddOtherDate(HttpServletRequest request,
			HashMap mainMap, List<BaseLink> linkList, HashMap operate) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public void prePerformClaimBusiMap(HttpServletRequest request,
			HashMap mainMap, HashMap linkMap, HashMap operate) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	public List<Object> prePerformClaimOtherDate(HttpServletRequest request,
			HashMap mainMap, List<BaseLink> linkList, HashMap operate) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public void prePerformDealBusiMap(HttpServletRequest request,
			HashMap mainMap, HashMap linkMap, HashMap operate) {
		if(isChooseAuditPerformer(operate)){
			changePerformer2AuditPerformer(operate);
		}
		//草稿环节或者被驳回环节，设置重新派发和重新提交审批操作的operateType
		String taskName = StaticMethod.nullObject2String(linkMap.get("activeTemplateId"));
		if("DraftTask".equals(taskName)){//如果当前环节是草稿环节
			if(isChooseAuditPerformer(operate)){
				linkMap.put("operateType", "12");
			}else{
				linkMap.put("operateType", "3");
			}
		}else if("RejectTask".equals(taskName)){//如果当前环节是被驳回环节
			if(isChooseAuditPerformer(operate)){
				linkMap.put("operateType", "5");
			}else{
				linkMap.put("operateType", "6");
			}
		}else if("DealTask".equals(taskName)){//如果当前环节是被驳回环节
			String operateType = StaticMethod.nullObject2String(request.getParameter("operateType"));
			if(operateType.equals("95")){
				Date sheetCompleteLimit = (Date)mainMap.get("sheetCompleteLimit");
				Date operateTime = (Date)linkMap.get("operateTime");
				if(sheetCompleteLimit.after(operateTime)){
					mainMap.put("mainOverTimeFlag",new Integer(0));//0代表及时
					mainMap.put("mainTimeOut","0");//mainSingleTotalTime
				}else{
					mainMap.put("mainOverTimeFlag",new Integer(1));//1代表及时
					 long timeoutlong = operateTime.getTime() - sheetCompleteLimit.getTime();
					 timeoutlong = timeoutlong / (1000 * 60);//处理时长
					 mainMap.put("mainTimeOut",timeoutlong+"");//mainSingleTotalTime
					//mainTimeOut/** 超时时间(完成时间-完成时限) */
				}
				
				Date sendTime = (Date)mainMap.get("sendTime");
				//计算处理时长
				long dealtimelong = operateTime.getTime() - sendTime.getTime();
				dealtimelong = dealtimelong / (1000 * 60);//处理时长
				mainMap.put("mainSingleTotalTime",dealtimelong+"");//mainSingleTotalTime
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> prePerformDealOtherDate(HttpServletRequest request,
			HashMap mainMap, List<BaseLink> linkList, HashMap operate) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public void prePerformFroceHold(HttpServletRequest request,
			HashMap mainMap, HashMap linkMap, HashMap operate) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 则将operate中的dealPerformer相关属性替换为审批对象相关属性
	 * 因为流程代码中只会识别operate中的dealPerformer，而不会识别auditPerformer
	 * @param operate
	 */
	@SuppressWarnings("unchecked")
	private void changePerformer2AuditPerformer(HashMap operate){
		String auditPerformer = StaticMethod.nullObject2String(operate.get("auditPerformer"));
		operate.put("dealPerformer", auditPerformer);
		operate.put("dealPerformerLeader", operate.get("auditPerformerLeader"));
		operate.put("dealPerformerType", operate.get("auditPerformerType"));
		operate.put("phaseId", "AuditTask");
	}
	
	/**
	 * 判断是否选择了审批人
	 * @param operate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean isChooseAuditPerformer(HashMap operate){
		String auditPerformer = StaticMethod.nullObject2String(operate.get("auditPerformer"));
		if(!"".equals(auditPerformer)){
			return true;
		}
		return false;
	}

}
