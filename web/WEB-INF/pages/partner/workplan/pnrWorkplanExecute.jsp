<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<html:form action="pnrWorkplanWorks.do?method=saveExecute" styleId="pnrWorkplanExeForm" method="post">

<fmt:bundle basename="com/boco/eoms/partner/workplan/config/applicationResources-partner-workplan">

<table class="formTable">
	<caption>
		<div class="header center">工作任务执行</div>
	</caption>
		<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.workplanName" />
		</td>
		<td class="content">
			${pnrWorkplanMain.workplanName}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.workplanNO" />
		</td>
		<td class="content">
			${pnrWorkplanMain.workplanNO}
		</td>
	</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanWork.startTime" />
		</td>
		<td class="content">
			${pnrWorkplanWork.startTimeStr}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanWork.endTime" />
		</td>
		<td class="content">
			${pnrWorkplanWork.endTimeStr}
		</td>	
		<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanWork.workContent" />
		</td>
		<td class="content" colspan="3">
			${pnrWorkplanWork.workContent}
		</td>
		</tr>
		<tr>	
		<td class="label" style="vertical-align:middle">
			工作完成标准
		</td>
		<td class="content" colspan="3">
			${pnrWorkplanWork.workStandard}
		</td>
		</tr>
		<tr>	
		<td class="label" style="vertical-align:middle">
			完成情况说明
		</td>
		<td class="content" colspan="3">
		<textarea name="exeContent" id="exeContent" maxLength="1000" rows="3" style="width:95%;" alt="allowBlank:true,vtext:''" >${pnrWorkplanExeForm.exeContent}</textarea>	
		</td>
		</tr>
		</table>
	<html:hidden property="workId" value="${pnrWorkplanExeForm.workId}" />
	<html:hidden property="workplanId" value="${pnrWorkplanExeForm.workplanId}" />
	</table>
        <input type="submit" value="提交"  class="button" />
</fmt:bundle>
</html:form>


<%@ include file="/common/footer_eoms.jsp"%>