<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="com.boco.eoms.partner.tempTask.model.PnrTempTaskAudit"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String auditTime = "";
%>
<script type="text/javascript">
	Ext.onReady(function(){
			var tabs = new Ext.TabPanel('info-page');
        	tabs.addTab('plan-info', '临时任务信息 ');
        	tabs.addTab('audit-info', '临时任务审核信息 ');
        	tabs.addTab('finish-info', '已完成工作任务信息 ');
    		tabs.activate(0);
	});
</script>
<fmt:bundle basename="com/boco/eoms/partner/tempTask/config/applicationResources-partner-tempTask">
<div id="info-page">
  <div id="plan-info" class="tabContent">
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="pnrTempTaskMain.form.heading"/></div>
	</caption>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.tempTaskName" />
		</td>
		<td class="content">
			${pnrTempTaskMainForm.tempTaskName}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.tempTaskNO" />
		</td>
		<td class="content">
			${pnrTempTaskMainForm.tempTaskNO}
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.startTime" />
		</td>
		<td class="content">
			${pnrTempTaskMainForm.startTime}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.endTime" />
		</td>
		<td class="content">
			${pnrTempTaskMainForm.endTime}
		</td>
	</tr>

	<tr>
	<td colspan="4">
	<div id="workDiv">
	<span style="font-weight:bold;" >工作任务:</span>
	
	<table class="formTable">
	<c:forEach var="work" items="${workList}" varStatus="stauts">
	
	<tr>
			<th colspan="4"><b>第${stauts.count} 项：</b></th>
	</tr>
		<tr>
			<td class="label" style="vertical-align:middle">
				<fmt:message key="pnrTempTaskWork.workContent" />
			</td>
			<td class="content">
				${work.workContent}
			</td>
			<td class="label" style="vertical-align:middle">
				工作完成标准
			</td>
			<td class="content">
				${work.workStandard}
			</td>
		</tr>
	<tr>		
		<td class="label" style="vertical-align:middle">
			工作任务类型
		</td>
		<td class="content" >
				<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${work.workType}" beanId="id2nameXML" />					
		</td>	
		<td class="label" style="vertical-align:middle">
			工作任务执行周期
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-agreement" dictId="workCycle" itemId="${work.cycle}" beanId="id2nameXML" />			
		</td>					
	</tr>
	<!-- 		
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskWork.startTime" />
		</td>
		<td class="content">
			${work.startTimeStr}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskWork.endTime" />
		</td>
		<td class="content">
			${work.endTimeStr}
		</td>	
	</tr>
	 -->	
		<tr>		
			<td class="label" style="vertical-align:middle">
				考核指标名称
			</td>
			<td class="content">
				${work.workEvaName}
			</td>			
				<td class="label" style="vertical-align:middle">
					指标详细算法
				</td>
				<td class="content" >
					${work.workEvaContent}		
				</td>		
		</tr>			
		<tr>
			<td class="label" style="vertical-align:middle">
				考核指标开始时间
			</td>
			<td class="content">
				${work.workEvaStartTimeStr}
			</td>
			<td class="label" style="vertical-align:middle">
				考核指标结束时间
			</td>
			<td class="content">
				${work.workEvaEndTimeStr}
			</td>			
		</tr>		
		<tr>
				<td class="label" style="vertical-align:middle">
					所占分数
				</td>
				<td class="content">
					${work.workEvaWeight}
				</td>	
				<td class="label" style="vertical-align:middle">
					考核指标周期
				</td>
				<td class="content">
					<eoms:dict key="dict-eva" dictId="cycle" itemId="${work.workEvaCycle}" beanId="id2nameXML" />				
				</td>						
		</tr>
				
	</c:forEach>
		</table>
	</div>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.accessories" />
		</td>
		<td class="content" colspan="3">
 			<eoms:attachment name="pnrTempTaskMainForm" property="accessoriesId" scope="request" idField="${pnrTempTaskMainForm.accessoriesId}" appCode="tempTask" viewFlag="Y"/> 
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			考核模板名称
		</td>
		<td class="content"  colspan="3">
		<c:if test="${pnrTempTaskMainForm.evaTemplateId ==null }">
			<c:if test="${result == true }">
							<input type="button" class="btn" value="新增考核任务" 
					onclick="window.open('${app}/eva/evaTemplates.do?method=newTemplateFromAgree&agreementId=${pnrTempTaskMainForm.id}&partner=${parDeptId}&evaDeptId=${evaDeptId}&agrwor=tempTask')"/>
			</c:if>
			<c:if test="${result != true }">
							<input type="button" class="btn" value="新增考核任务" 
					onclick="window.open('${app}/eva/evaTemplates.do?method=newTemplateFromAgree&agreementId=${pnrTempTaskMainForm.id}&partner=${parDeptId}&evaDeptId=${evaDeptId}&agrwor=tempTask')"  disabled="disabled"/>
			</c:if>			
		</c:if>
		<c:if test="${pnrTempTaskMainForm.evaTemplateId !=null }">
		<a href="${app}/eva/evaTasks.do?method=xqueryByTpid&templateId=${pnrTempTaskMainForm.evaTemplateId}" target="_blank">
			<eoms:id2nameDB id="${pnrTempTaskMainForm.evaTemplateId}" beanId="evaTemplateDao" />
		</a>
		</c:if>
		</td>
	</tr>		
</table>

  </div>
  <div id="audit-info" class="tabContent">

  <table class="formTable">
  			<caption>
				<div class="header center">
					工作计划审核信息
				</div>
			</caption>
			  <%
  	List auditList = (List)request.getAttribute("auditList");
  PnrTempTaskAudit pnrTempTaskAudit = null;
  for(int i=0;i<auditList.size();i++){
	  pnrTempTaskAudit = (PnrTempTaskAudit)auditList.get(i);
  %>
			<tr>
			<th colspan="4">&nbsp;</th>
			</tr>
			<tr>
			<td class="label">
					审核者
				</td>
				<td class="content">
				<%
				if("user".equals(pnrTempTaskAudit.getToOrgType())){
				%>
					<eoms:id2nameDB id="<%=pnrTempTaskAudit.getToOrgId() %>" beanId="tawSystemUserDao" />	
				<%
				}else if("dept".equals(pnrTempTaskAudit.getToOrgType())){
				%>
					<eoms:id2nameDB id="<%=pnrTempTaskAudit.getToOrgId() %>" beanId="tawSystemDeptDao" />
				<%
				}
				%>
				</td>

				<td class="label">
					审核时间
				</td>
				<td class="content">
				<%
				if(pnrTempTaskAudit.getOperateTime()!=null){
					auditTime = dateFormat.format(pnrTempTaskAudit.getOperateTime());
				}else{
					auditTime = "";
				}
				%>
					<%=auditTime %>
					
				</td>
			</tr>
			<tr>
			<td class="label">
					审核结果
				</td>
				<td class="content" colspan="3">
				<%
				if("1".equals(pnrTempTaskAudit.getAuditResult())){
				%>
					驳回
				<%
				}else if("2".equals(pnrTempTaskAudit.getAuditResult())){
				%>
				    通过
				<%
				}else{
				%>
					待审核
				<%
				}
				%>
				</td>
			</tr>
			<%
				if(pnrTempTaskAudit.getRemark() != null){
			%>
			<tr>
				<td class="label">
					审核说明
				</td>
				<td class="content" colspan="3"s>
					<%=pnrTempTaskAudit.getRemark() %>
				</td>
			</tr>
			<%
				}
  }
			%>
	</table>
	<br>
  </div>
</div>
<div id="finish-info" class="tabContent">
	<div id="workDiv">
	<c:if test="${workExeListsize==0}">
		暂无完成任务
	</c:if>
	<table class="formTable">
	<c:forEach var="work" items="${workExeList}" varStatus="stauts">
			<tr>
					<th colspan="4"><b>第${stauts.count} 项：</b></th>
			</tr>
			<tr>
				<td class="label" style="vertical-align:middle">
					<fmt:message key="pnrTempTaskWork.startTime" />
				</td>
				<td class="content">
					${work.startTimeStr}
				</td>
				<td class="label" style="vertical-align:middle">
					<fmt:message key="pnrTempTaskWork.endTime" />
				</td>
				<td class="content">
					${work.endTimeStr}
				</td>	
				<tr>
				<td class="label" style="vertical-align:middle">
					<fmt:message key="pnrTempTaskWork.workContent" />
				</td>
				<td class="content" colspan="3">
					${work.workContent}
				</td>
				</tr>
				<tr>	
				<td class="label" style="vertical-align:middle">
					工作完成标准
				</td>
				<td class="content" colspan="3">
					${work.workStandard}
				</td>
				</tr>
				<td class="label" style="vertical-align:middle">
					完成情况说明
				</td>
				<td class="content" colspan="3">
					<c:forEach items="${exeCont}" var="item"> 
						<c:if test="${item.key==work.id}">
							${item.value} <br/> 
						</c:if>
					</c:forEach> 
				</td>
				</tr>				
	</c:forEach>
		</table>
	</div>
</div>	
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>