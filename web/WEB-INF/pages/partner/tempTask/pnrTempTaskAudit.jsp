<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<html:form action="pnrTempTaskMains.do?method=auditDo" styleId="pnrTempTaskAuditForm" method="post">

<fmt:bundle basename="com/boco/eoms/partner/tempTask/config/applicationResources-partner-tempTask">

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
			${pnrTempTaskMainForm.startTimeStr}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.endTime" />
		</td>
		<td class="content">
			${pnrTempTaskMainForm.endTimeStr}
		</td>
	</tr>

	<tr>
	<td colspan="4">
	<div id="workDiv">
	<span style="font-weight:bold;" >工作任务：</span>
	
	<c:forEach var="work" items="${workList}" varStatus="stauts">
			<div id="${work.id}">
			<table class="formTable">
				<tr>
			<th colspan="4"><b>第${stauts.count} 项：</b></th>
				</tr>
				<tr>		
					<td class="label" style="vertical-align:middle" >
						工作内容
					</td>
					<td class="content">
						${work.workContent}
						<html:hidden property="workContent" value="${work.workContent}" />
					</td>
					<td class="label" style="vertical-align:middle" >
						执行人
					</td>
					<td class="content">
							${work.toOrgUserName}
						<html:hidden property="toOrgUserName" value="${work.toOrgUserName}" />
						<html:hidden property="toOrgUser" value="${work.toOrgUser}" />
					</td>
					</tr>
					<tr>
					<td class="label" style="vertical-align:middle" >
						工作完成标准
					</td>
					<td class="content" colspan="3">
							${work.workStandard}
						<html:hidden property="workStandard" value="${work.workStandard}" />
					</td>				
				</tr>
				<tr>		
					<td class="label" style="vertical-align:middle">
						工作任务类型
					</td>
					<td class="content" >
							<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${work.workType}" beanId="id2nameXML" />
						<html:hidden property="workType" value="${work.workType}" />				
					</td>
					<td class="label" style="vertical-align:middle">
						工作任务执行周期
					</td>
					<td class="content">
						<eoms:dict key="dict-partner-agreement" dictId="workCycle" itemId="${work.cycle}" beanId="id2nameXML" />
						<html:hidden property="workCycle" value="${work.cycle}" />	
					</td>	
				</tr>	
				<tr>		
						<td class="label" style="vertical-align:middle">
							考核指标名称
						</td>
						<td class="content">
							${work.workEvaName}
						<html:hidden property="workEvaName" value="${work.workEvaName}" />
						</td>		
						<td class="label" style="vertical-align:middle">
							算法分类
						</td>
						<td class="content">
							<eoms:dict key="dict-eva" dictId="algorithmType" itemId="${work.algorithmType}" beanId="id2nameXML" />
						<html:hidden property="algorithmType" value="${work.algorithmType}" />
						</td>	
				</tr>
				<tr>
						<td class="label" style="vertical-align:middle">
							指标详细算法
						</td>
						<td class="content" colspan="3">
							${work.workEvaContentByType}
						<html:hidden property="workEvaContent" value="${work.workEvaContent}" />
						</td>						
				</tr>
				<tr>
					<td class="label" style="vertical-align:middle">
						考核开始时间
					</td>
					<td class="content">
						${work.workEvaStartTimeStr}
						<html:hidden property="workEvaStartTime" value="${work.workEvaStartTimeStr}" />
					</td>
					<td class="label" style="vertical-align:middle">
						考核结束时间
					</td>
					<td class="content">
						${work.workEvaEndTimeStr}
						<html:hidden property="workEvaEndTime" value="${work.workEvaEndTimeStr}" />			
					</td>			
				</tr>
				<tr>		
					<td class="label" style="vertical-align:middle">
						所占分数
					</td>
					<td class="content">
							${work.workEvaWeight}
						<html:hidden property="workEvaWeight" value="${work.workEvaWeight}" />
					</td>	
					<td class="label" style="vertical-align:middle">
						考核周期
					</td>
					<td class="content">
						<eoms:dict key="dict-eva" dictId="cycle" itemId="${work.workEvaCycle}" beanId="id2nameXML" />
						<html:hidden property="workEvaCycle" value="${work.workEvaCycle}" />
					</td>	
				</tr>
				<html:hidden property="workId" value="${work.id}" />
				</table>
				<hr />
			</div>
	</c:forEach>
		
	</div>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.accessories" />
		</td>
		<td class="content" colspan="3">
 			<eoms:attachment name="pnrTempTaskMainForm" property="accessoriesId" scope="request" idField="accessoriesId" appCode="tempTask"  viewFlag="Y"/> 
		</td>
	</tr>
	
	<tr>
		<td class="label">
			审核结果			
		</td>
		<td class="content" colspan="3">
				<INPUT type="radio" name="auditResult" value="2" checked="true">通过 
	      		<INPUT type="radio" name="auditResult" value="1">不通过
		</td>
	</tr>
	
	<tr>
      <td class="label">
        	审核意见
      </td>
      <td class="content" colspan="3">
      <!-- property中配一个空属性 -->
			<textarea name="remark" maxLength="1000" rows="1" style="width:98%;" id="remark"></textarea>       
      </td>
    </tr>
	<html:hidden property="id" value="${id}" />
	</table>
        <input type="submit" value="提交"  class="button" />
</fmt:bundle>
</html:form>


<%@ include file="/common/footer_eoms.jsp"%>