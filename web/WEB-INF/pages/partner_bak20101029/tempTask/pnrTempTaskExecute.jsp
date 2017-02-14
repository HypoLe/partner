<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<html:form action="pnrTempTaskWorks.do?method=saveExecute" styleId="pnrTempTaskExeForm" method="post">

<fmt:bundle basename="com/boco/eoms/partner/tempTask/config/applicationResources-partner-tempTask">

<table class="formTable">
	<caption>
		<div class="header center">工作任务执行</div>
	</caption>
		<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.tempTaskName" />
		</td>
		<td class="content">
			${pnrTempTaskMain.tempTaskName}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.tempTaskNO" />
		</td>
		<td class="content">
			${pnrTempTaskMain.tempTaskNO}
		</td>
	</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskWork.startTime" />
		</td>
		<td class="content">
			${pnrTempTaskWork.startTimeStr}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskWork.endTime" />
		</td>
		<td class="content">
			${pnrTempTaskWork.endTimeStr}
		</td>	
		<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskWork.workContent" />
		</td>
		<td class="content" colspan="3">
			${pnrTempTaskWork.workContent}
		</td>
		</tr>
		<tr>	
		<td class="label" style="vertical-align:middle">
			工作完成标准
		</td>
		<td class="content" colspan="3">
			${pnrTempTaskWork.workStandard}
		</td>
		</tr>
		<tr>	  
		<td class="label" style="vertical-align:middle">
			完成情况说明
		</td>
		<td class="content" colspan="3">
		<textarea name="exeContent" id="exeContent" maxLength="1000" rows="3" style="width:95%;" alt="allowBlank:true,vtext:''" >${pnrTempTaskExeForm.exeContent}</textarea>	
		</td>
		</tr>
		</table>
	<html:hidden property="workId" value="${pnrTempTaskExeForm.workId}" />
	<html:hidden property="tempTaskId" value="${pnrTempTaskExeForm.tempTaskId}" />
	</table>
        <input type="submit" value="提交"  class="button" />
</fmt:bundle>
</html:form>


<%@ include file="/common/footer_eoms.jsp"%>