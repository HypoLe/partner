<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<%@page import="com.boco.eoms.base.util.StaticMethod" %>
<%@page import="com.boco.eoms.sheet.base.model.BaseMain" %>
<%@page import="com.boco.eoms.partner.sheet.pnrcomplaint.model.PnrComplaintTask" %>
<%@page import="com.boco.eoms.commons.system.session.form.TawSystemSessionForm" %>
<%@page import="com.boco.eoms.sheet.base.util.Constants" %>

<%
TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
String taskStatus = StaticMethod.nullObject2String(request.getParameter("taskStatus"));
String isAdmin = StaticMethod.nullObject2String(request.getParameter("isAdmin"));
String userId = sessionform.getUserid();
PnrComplaintTask task = new PnrComplaintTask();
String operaterType = "";
//String ifsub = "";
//String ifwaitfor="";
if(request.getAttribute("task")!=null){
	 task = (PnrComplaintTask)request.getAttribute("task");
	 taskStatus = task.getTaskStatus();
	 operaterType = task.getOperateType();
	 //ifsub = StaticMethod.nullObject2String(task.getSubTaskFlag());
	 //ifwaitfor = StaticMethod.nullObject2String(task.getIfWaitForSubTask());
}

request.setAttribute("operaterType",operaterType);
BaseMain basemain = (BaseMain)request.getAttribute("sheetMain");
String sendUserId = basemain.getSendUserId();

request.setAttribute("taskStatus", taskStatus);
String taskName = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getAttribute("taskName"));
 %>

<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	var tabConfig = {
		items : [{
			id : 'sheetinfo',
			text : '<bean:message bundle="sheet" key="sheet.sheetInfo"/>'
		}, {
			text : '<bean:message bundle="sheet" key="sheet.historyView"/>',
			url : 'pnrcomplaint.do?method=showSheetDealList&sheetKey=${sheetMain.id}&taskName=${taskName}&ifWaitForSubTask=${task.ifWaitForSubTask}'
		}, {
			text : '<bean:message bundle="sheet" key="sheet.flowchar"/>',
			url : 'pnrcomplaint.do?method=showWorkFlow&linkServiceName=iPnrComplaintLinkManager&dictSheetName=dict-sheet-pnrcomplaint&description=mainOperateType&sheetKey=${sheetMain.id}',
			isIframe : true
		  }
		,{
			text : '<bean:message bundle="sheet" key="sheet.filesView"/>',
			url : 'pnrcomplaint.do?method=showSheetAccessoriesList&id=${sheetMain.id}',
			isIframe : true
		}
		<c:if test="${sheetMain.status ==1 && sheetMeteringMenuRight }">
		 ,{
		 	text : '计次明细',
			url : '${app}/netresource/pnrResMeteringAction.do?method=pnrResMeteringDetail&eventId=${sheetMain.id}&sheet=002',
			isIframe : true
		 }
		 </c:if>
		 ]
	};
	var tabs = new eoms.TabPanel('sheet-detail-page', tabConfig);
    <%if(taskStatus.equals("2") || taskStatus.equals("3") || taskStatus.equals("8")){%>
    	<%if(taskName.equals("cc")){ %>
			var url2="pnrcomplaint.do?method=newShowCCPage&sheetKey=${sheetMain.id}&piid=${sheetMain.piid}&taskId=${taskId}&operateRoleId=${operateRoleId}&TKID=${TKID}&preLinkId=${preLinkId}&taskName=${taskName}&correlationKey=${sheetMain.correlationKey}&operateType=-10&taskStatus=${taskStatus}";
			eoms.util.appendPage("sheet-deal-content",url2);	
	<%}%>
	<%if(taskName.equals("reply")){ %>
		var url2="pnrcomplaint.do?method=showRemarkPage&sheetKey=${sheetMain.id}&piid=${sheetMain.piid}&taskId=${taskId}&operateRoleId=${operateRoleId}&TKID=${TKID}&preLinkId=${preLinkId}&taskName=${taskName}&correlationKey=${sheetMain.correlationKey}";
		eoms.util.appendPage("sheet-deal-content",url2);	
	<%}%>
	<%if(taskName.equals("advice")){ %>
		var url2="pnrcomplaint.do?method=showRemarkPage&sheetKey=${sheetMain.id}&piid=${sheetMain.piid}&taskId=${taskId}&operateRoleId=${operateRoleId}&TKID=${TKID}&preLinkId=${preLinkId}&taskName=${taskName}&correlationKey=${sheetMain.correlationKey}";
		eoms.util.appendPage("sheet-deal-content",url2);	
	<%}}%>

});
function forceOperation(obj){
	if(obj == 1){
		var url2="pnrcomplaint.do?method=showForceHoldPage&sheetKey=${sheetMain.id}&piid=${sheetMain.piid}&taskId=${taskId}&operateRoleId=${operateRoleId}&TKID=${TKID}&preLinkId=${preLinkId}&taskName=${taskName}&correlationKey=${sheetMain.correlationKey}&operateType=forceHold";
		eoms.util.appendPage("sheet-deal-content",url2);	
	}else if(obj == 2){
	    
		var url2="pnrcomplaint.do?method=showForceHoldPage&sheetKey=${sheetMain.id}&piid=${sheetMain.piid}&taskId=${taskId}&operateRoleId=${operateRoleId}&TKID=${TKID}&preLinkId=${preLinkId}&taskName=${taskName}&correlationKey=${sheetMain.correlationKey}&operateType=nullity";
		eoms.util.appendPage("sheet-deal-content",url2);	
	}else{
	   
	    var url2="pnrcomplaint.do?method=showForceHoldPage&sheetKey=${sheetMain.id}&piid=${sheetMain.piid}&taskId=${taskId}&operateRoleId=${operateRoleId}&TKID=${TKID}&preLinkId=${preLinkId}&taskName=${taskName}&correlationKey=${sheetMain.correlationKey}&operateType=forceNullity";
		eoms.util.appendPage("sheet-deal-content",url2);	
    }
   }
    function eventOperation(obj){
	if(obj == 1){
	     var url2="pnrcomplaint.do?method=showPhaseAdvicePage&sheetKey=${sheetMain.id}&piid=${sheetMain.piid}&taskId=${taskId}&taskName=advice&operateFlag=driveForward&operateRoleId=${operateRoleId}&TKID=${TKID}&preLinkId=${preLinkId}";
             eoms.util.appendPage("sheet-deal-content",url2,true);
	}
   }
   function operateType(url){
		var R = GetQueryString("operateType",url);
		var operateTypeObj = document.getElementById("operateType");
		operateTypeObj.value = opertateTypeValue;
		var divObj = document.getElementById("templateButtonDiv");
		if (opertateTypeValue == "") {
			divObj.style.display = "none";
		} else {
			divObj.style.display = "";
		}
   }
  function GetQueryString(name,url) {   
      var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");   
      var r = url.substr(1).match(reg);   
      if (r != null) return unescape(r[2]); return "";   
  }  
</script>

<h3 class="sheet-title">

</h3>

<!-- Sheet Tabs Start -->
<div id="sheet-detail-page">
  <!-- Sheet Detail Tab Start -->
  <div id="sheetinfo">
  	<logic:present name="sheetMain" scope="request">
	<%@ include file="/WEB-INF/pages/wfworksheet/pnrcomplaint/basedetailnew.jsp"%>
	<br/>
	<table id="sheet" class="formTable" > 
 
	 	<tr>
	        <td class="label">受理时限</td>
			<td class="content">
				<bean:write name="sheetMain" property="sheetAcceptLimit" formatKey="date.formatDateTimeAll" bundle="sheet" scope="request"/>
			</td>
			<td class="label">完成时限</td>
			<td class="content">
				<bean:write name="sheetMain" property="sheetCompleteLimit" formatKey="date.formatDateTimeAll" bundle="sheet" scope="request"/>
			</td>
	  	</tr>
	  	<tr>
			<td class="label">地市</td>
			<td class="content"> 
				<eoms:id2nameDB id="${sheetMain.mainCity}" beanId="tawSystemAreaDao" />
			</td>
			<td class="label">区县</td>
			<td class="content"> 
				<eoms:id2nameDB id="${sheetMain.mainCountry}" beanId="tawSystemAreaDao" />
			</td>
		</tr>
	  	<tr>
			<td class="label">是否计费</td>
			<td class="content"> 
				<c:choose>
					<c:when test="${sheetMain.mainIfcharging eq '1' }">是</c:when>
					<c:when test="${sheetMain.mainIfcharging eq '0' }">否</c:when>
				</c:choose>
			</td>
			<td class="label">区域特征</td>
			<td class="content"> 
				<eoms:id2nameDB id="${sheetMain.mainRegiontype}" beanId="ItawSystemDictTypeDao" />
			</td>
		</tr>
		<tr>
			<td class="label">故障产生时间</td>
			<td class="content"> 
				<bean:write name="sheetMain" property="mainFaultOccurTime" formatKey="date.formatDateTimeAll" bundle="sheet" scope="request"/>
			</td>
			<td class="label">故障地点</td>
			<td class="content"> 
				${sheetMain.mainLocation}
			</td>
		</tr>

		<tr>
			<td class="label">故障号码</td>
			<td class="content"> 
				${sheetMain.mainComplaintNum}
			</td>
			<td class="label">专业类型</td>
			<td class="content"> 
				<eoms:id2nameDB id="${sheetMain.mainSpecialty}" beanId="ItawSystemDictTypeDao" />
			</td>
		</tr>
		<tr>
			<td class="label">投诉时间</td>
			<td class="content"> 
				<bean:write name="sheetMain" property="mainComplaintTime" formatKey="date.formatDateTimeAll" bundle="sheet" scope="request"/>
			</td>
			<td class="label">紧急程度</td>
			<td class="content">
				<eoms:id2nameDB id="${sheetMain.mainUrgencyLevel}" beanId="ItawSystemDictTypeDao" />
			</td>
		</tr>
		<tr>
			<td class="label">是否需要附件</td>
			<td class="content"> 
				<eoms:id2nameDB id="${sheetMain.mainFileNeeded}" beanId="ItawSystemDictTypeDao" />
			</td>
			<td class="label">是否大面积投诉</td>
			<td class="content"> 
				<eoms:id2nameDB id="${sheetMain.mainLargeArea}" beanId="ItawSystemDictTypeDao" />
			</td>
		</tr>
		<tr>
			<td class="label">客户姓名</td>
			<td class="content"> 
				${sheetMain.mainCustomerName}
			</td>
			<td class="label">客户电话</td>
			<td class="content">
				${sheetMain.mainCustomPhone}
			</td>
		</tr>
		<tr>
			<td class="label">客户品牌</td>
			<td class="content"> 
				<eoms:id2nameDB id="${sheetMain.mainCustomBrand}" beanId="ItawSystemDictTypeDao" />
			</td>
			<td class="label">客户级别</td>
			<td class="content"> 
				<eoms:id2nameDB id="${sheetMain.mainCustomLevel}" beanId="ItawSystemDictTypeDao" />
			</td>
		</tr>
		
		<c:if test="${!empty sheetMain.mainEomsSheetId}">
			<tr>
				<td class="label">EOMS工单流水号</td>
				<td class="content"> 
					${sheetMain.mainEomsSheetId}
				</td>
				<td class="label">EOMS客服流水号</td>
				<td class="content"> 
					${sheetMain.mainCustomerServiceNo}
				</td>
			</tr>
		</c:if>
		
		<tr>
			<td class="label">投诉分类</td>
			<td colspan="3"> 
				${sheetMain.mainComplaintType}
			</td>
		</tr>
		<tr>
			<td class="label">终端描述</td>
			<td colspan="3"> 
				${sheetMain.mainTerminalType}
			</td>
		</tr>
     	<tr>
     		<td class="label">投诉内容</td>
			<td colspan="3">
				<pre>${sheetMain.mainComplaintDesc}</pre>
			</td>
     	</tr>
     	<tr>
     		<td class="label">预处理情况</td>
			<td colspan="3">
				<pre>${sheetMain.mainPreDealResult}</pre>
			</td>
     	</tr>
	  <tr>
			<td class="label"><bean:message bundle="sheet" key="mainForm.accessories"/></td>
		    <td  colspan='3'>
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="sheetAccessories" appCode="pnrcomplaint" 
		             viewFlag="Y"/> 
		    </td>
	  </tr>
</table>
    </logic:present>
  </div>
</div>
<!-- Sheet Tabs End -->

<!-- 工单处理环节开始 -->
<% if(taskStatus.equals("2") || taskStatus.equals("3") || taskStatus.equals("8")){%>

<!-- Sheet Deal Content Start -->
<!-- 下面是流程中的步骤URL -->

 <!-- 回复到派单人 -->
<c:url var="urlShowReplyTaskPage" value="pnrcomplaint.do">
	<c:param name="method" value="showDealPage"/>
	<c:param name="sheetKey" value="${sheetMain.id}"/>
	<c:param name="piid" value="${sheetMain.piid}"/>
	<c:param name="taskId" value="${taskId}"/>
	<c:param name="taskName" value="${taskName}"/>
	<c:param name="operateRoleId" value="${operateRoleId}"/>
	<c:param name="TKID" value="${TKID}"/>
	<c:param name="taskStatus" value="${taskStatus}"/>
	<c:param name="preLinkId" value="${preLinkId}"/>
	<c:param name="dealTemplateId" value="${dealTemplateId}"/>
	<c:param name="operateType" value="95"/>  
</c:url>	

 <!-- 派发 -->
<c:url var="urlSendTaskPage" value="pnrcomplaint.do">
	<c:param name="method" value="showDealPage"/>
	<c:param name="sheetKey" value="${sheetMain.id}"/>
	<c:param name="piid" value="${sheetMain.piid}"/>
	<c:param name="taskId" value="${taskId}"/>
	<c:param name="taskName" value="${taskName}"/>
	<c:param name="operateRoleId" value="${operateRoleId}"/>
	<c:param name="TKID" value="${TKID}"/>
	<c:param name="taskStatus" value="${taskStatus}"/>
	<c:param name="preLinkId" value="${preLinkId}"/>
	<c:param name="operateType" value="10"/>  
	<c:param name="dealTemplateId" value="${dealTemplateId}"/>  
</c:url>	

 <!-- 待归档 -->
<c:url var="urlShowHoldTaskPage" value="pnrcomplaint.do">
	<c:param name="method" value="showDealPage"/>
	<c:param name="sheetKey" value="${sheetMain.id}"/>
	<c:param name="piid" value="${sheetMain.piid}"/>
	<c:param name="taskId" value="${taskId}"/>
	<c:param name="taskName" value="${taskName}"/>
	<c:param name="operateRoleId" value="${operateRoleId}"/>
	<c:param name="TKID" value="${TKID}"/>
	<c:param name="taskStatus" value="${taskStatus}"/>
	<c:param name="preLinkId" value="${preLinkId}"/>
	<c:param name="dealTemplateId" value="${dealTemplateId}"/>  
	<c:param name="operateType" value="18"/>
</c:url>	



<!-- 流程中的步骤已结束 -->

<!-- 下面是公共的URL -->
<!-- 退回 -->
<c:url var="urlShowRejectDealPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="54"/>
</c:url>


<!-- 移交，由于不走showDealPage方法，所以不需要设置operateType,也不需要保存模板 -->
<c:url var="urlShowTransferkPage" value="pnrcomplaint.do">
  <c:param name="method" value="showTransferWorkItemPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="8"/>
</c:url>

<!-- 阶段回复 直接到另一个页面,不需要保存模板-->
<c:url var="urlShowPhaseReplyPage" value="pnrcomplaint.do">
  <c:param name="method" value="showPhaseBackToUpPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="reply"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="preLinkId" value="${preLinkId}"/> 
  <c:param name="operateType" value="${operaterType}"/> 
</c:url>

<!--任务分派-->
<c:url var="urlShowInputSplit" value="pnrcomplaint.do">
  <c:param name="method" value="showInputSplit"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="subtaskName" value="InstrumentStorageSubTask"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="operateType" value="10"/>
  <c:param name="preLinkId" value="${preLinkId}"/> 
</c:url>

<!-- 处理回复通过 -->
<c:url var="urlShowDealReplyAcceptPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealReplyAcceptPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="6"/>
</c:url>

<!-- 处理回复不通过 -->
<c:url var="urlShowDealReplyRejectPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealReplyRejectPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="subtaskName" value="instrumentstorageSubTask"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="7"/>
</c:url>
<!-- 驳回上一级 -->
<c:url var="urlShowRejectBackPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="4"/>
</c:url>

<!-- 确认受理 -->
<c:url var="urlShowAcceptDealPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="61"/>  
</c:url>

<!-- 被驳回 -->
<c:url var="urlBackDealPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="46"/>
  <c:param name="dealTemplateId" value="${dealTemplateId}"/>
  <c:param name="backFlag" value="yes"/>
</c:url>

<!-- 草稿 -->
<c:url var="urlShowDraftPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="3"/>
  <c:param name="dealTemplateId" value="${dealTemplateId}"/>
</c:url>


<!-- 分派回复 -->
<c:url var="urlShowDispatchPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="11"/>
  <c:param name="dealTemplateId" value="${dealTemplateId}"/>  
</c:url>

<!-- 会审 -->
<c:url var="urlShowInputSplitAudit" value="pnrcomplaint.do">
  <c:param name="method" value="showInputSplit"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="subtaskName" value="instrumentstorageSubTask"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="preLinkId" value="${preLinkId}"/> 
  <c:param name="operateType" value="30"/>  
</c:url>

<!-- 会审回复 -->
<c:url var="urlShowDispatchAuditPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="subtaskName" value="instrumentstorageSubTask"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="55"/>
  <c:param name="dealTemplateId" value="${dealTemplateId}"/>  
</c:url>

<!-- 受理工单 -->
<c:url var="urlShowAcceptTaskPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="99"/>
</c:url>

<!-- 处理工单 -->
<c:url var="urlShowFaultDealTaskPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="95"/>
</c:url>

<!-- 完成确认 -->
<c:url var="urlShowConfirmTaskPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="96"/>
</c:url>

<!-- 驳回到派发人 -->
<c:url var="urlShowReject2SendPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="94"/>
</c:url>

<!-- 驳回到受理人 -->
<c:url var="urlShowReject2AcceptPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="97"/>
</c:url>

<!-- 驳回到处理人 -->
<c:url var="urlShowReject2DealPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="93"/>
</c:url>

<!-- 退回到任务完成确认 -->
<c:url var="urlShowReject2ConfirmPage" value="pnrcomplaint.do">
  <c:param name="method" value="showDealPage"/>
  <c:param name="sheetKey" value="${sheetMain.id}"/>
  <c:param name="piid" value="${sheetMain.piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="taskStatus" value="${taskStatus}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="54"/>
</c:url>

<script type="text/javascript">
	Ext.onReady(function(){
		eoms.Sheet.setPageLoader("dealSelector","sheet-deal-content");
		var url = "";
		try{
			 url = $("dealSelector").value + "&taskStatus=${taskStatus}";
		}catch(e){
		}
		eoms.util.appendPage("sheet-deal-content",url);		
	});
</script>
<div class="sheet-deal">
	<div class='sheet-deal-header'>
	<!-- 下面是各个处理环节的下拉框功能 -->
		<table>
			<tr>
				 <td width="50%">  
				 	<%if(!taskName.equals("cc") && !taskName.equals("reply") && !taskName.equals("advice")) { %>
				 		<bean:message bundle="sheet" key="sheet.deal"/>:
				 	<%}%>
				 	
					<% if(taskName.equals("DealTask")){ %>
						<select id="dealSelector">
							<% if(taskStatus.equals(Constants.TASK_STATUS_READY)){%>
						 		<!-- 确认受理 -->
						  		<option value="${urlShowAcceptDealPage}"><bean:message bundle="sheet" key="common.accept"/> </option>
							 <% } else if(taskStatus.equals(Constants.TASK_STATUS_CLAIMED)){ %>
								 <option value="${urlShowReplyTaskPage}">回复</option>
								 <option value="${urlSendTaskPage}">派发</option>
	  							 <option value="${urlShowPhaseReplyPage}">阶段回复</option>
							<%}%>
					 	</select>
						
					<% }if(taskName.equals("HoldTask")){%>	
							<select id="dealSelector">
								<option value="${urlShowHoldTaskPage}"><bean:message bundle="pnrcomplaint" key="operateType.holdTask"/> </option>
								<option value="${urlShowReject2ConfirmPage}">退回</option>
						 	</select>
					<% }else if(taskName.equals("RejectTask")){%>	
						<select id="dealSelector">
						  <option value="${urlBackDealPage}"><bean:message bundle="sheet" key="common.reSend"/></option>
						</select>
			  		<% }else if(taskName.equals("DraftTask")){%>	
						<select id="dealSelector">
						  <option value="${urlShowDraftPage}"><bean:message bundle="pnrcomplaint" key="operateType.DraftTask"/></option>
						</select>
					<!-- 受理工单 -->
			   		<% } else if(taskName.equals("AcceptTask")){%>	
						<select id="dealSelector">
						  <option value="${urlShowAcceptTaskPage}">受理</option>
						  <option value="${urlShowReject2SendPage}">驳回</option>
						</select>
			   		<% } else if(taskName.equals("FaultDealTask")){%>	
						<select id="dealSelector">
							<option value="${urlShowFaultDealTaskPage}">处理</option>
					  		<option value="${urlShowReject2AcceptPage}">驳回</option>
						</select>
			   		<% } else if(taskName.equals("ConfirmTask")){%>	
						<select id="dealSelector">
						  <option value="${urlShowConfirmTaskPage}">完成确认</option>
						  <option value="${urlShowReject2DealPage}">驳回</option>
						</select>
			   		<% } %>
			</td>
		</tr>
		</table>
		
	<!-- 各个处理环节的下拉框功能结束 -->
	</div>
	<div class="sheet-deal-content" id="sheet-deal-content"></div>
</div>
<!-- 工单处理环节结束 -->
<script type="text/javascript">
	Ext.onReady(function(){
		var url = "";
		try{
			 url = $("dealSelector").value + "&taskStatus=${taskStatus}";
		}catch(e){
		}
		var dealTemplateId = "${dealTemplateId}";
        if (dealTemplateId != null && dealTemplateId != "") {
   			url = "pnrcomplaint.do?method=referenceTemplate&sheetKey=${sheetMain.id}&piid=${sheetMain.piid}&taskId=${taskId}&taskName=${taskName}&operateRoleId=${operateRoleId}&TKID=${TKID}&preLinkId=${preLinkId}&operateType=${operateType}&taskStatus=${taskStatus}&dealTemplateId="+dealTemplateId
        }
		eoms.util.appendPage("sheet-deal-content",url);		
	});
</script>
<% }%>


<c:if test="${sheetMain.status==0}"> 
    <div class="sheet-deal-content" id="sheet-deal-content"></div>
 <% if(isAdmin.equals("true")){%>
 	<div id="buttonDisplay" style="display:block">
   <input type="button" title="执行该操作，工单将进入强制归档状态，其他人不能在处理工单" value="<bean:message bundle="sheet" key="button.forceHold"/>"  onclick="$('buttonDisplay').style.display='none';forceOperation(1);">
   <input type="button" title="执行该操作，工单将进入强制作废状态，其他人不能在处理工单" value="<bean:message bundle="sheet" key="button.forceNullity"/>"  onclick="$('buttonDisplay').style.display='none';forceOperation(3);">
   <input type="button" value="<bean:message bundle="sheet" key="event.advice"/>" onclick="$('buttonDisplay').style.display='none';eventOperation(1);">
    </div>
 <% }else if((taskStatus == null || taskStatus.equals(""))&&(userId.equals(sendUserId))){%> 
 <div id="advice" style="display:block">
     <input type="button" title="执行该操作，工单将进入作废状态，其他人不能在处理工单" value="<bean:message bundle="sheet" key="button.nullity"/>" onclick="$('advice').style.display='none';forceOperation(2);">
     <input type="button" value="<bean:message bundle="sheet" key="event.advice"/>" onclick="$('advice').style.display='none';eventOperation(1);">
  </div>
 <% }%> 
</c:if>
<%@ include file="/common/footer_eoms.jsp"%>
