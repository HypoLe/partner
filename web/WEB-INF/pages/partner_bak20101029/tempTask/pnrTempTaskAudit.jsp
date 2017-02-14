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
	<span style="font-weight:bold;" >工作任务：</span>
	
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
		<td class="content">
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
			<td class="content" >
				${work.workEvaName}
			</td>			
				<td class="label" style="vertical-align:middle">
					指标详细算法
				</td>
				<td class="content">
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
	
<!-- 
		<tr>	
		<td class="label" style="vertical-align:middle">
			工作考核标准
		</td>
		<td class="content" colspan="3">
			${work.evaStandard}
		</td>
		</tr>
 -->		
	</c:forEach>
		</table>
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