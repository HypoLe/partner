<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<html:form action="pnrWorkplanMains.do?method=auditDo" styleId="pnrWorkplanAuditForm" method="post">

<fmt:bundle basename="com/boco/eoms/partner/workplan/config/applicationResources-partner-workplan">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="pnrWorkplanMain.form.heading"/></div>
	</caption>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.workplanNO" />
		</td>
		<td class="content" >
			${pnrWorkplanMainForm.workplanNO}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.workplanName" />
		</td>
		<td class="content">
			${pnrWorkplanMainForm.workplanName}
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			服务协议名称：
		</td>
		<td class="content" >
			${pnrWorkplanMainForm.agreementName}
		</td>	
		<td class="label" style="vertical-align:middle">
			服务协议编号：
		</td>
		<td class="content" >
			${pnrWorkplanMainForm.agreementNO}
			<html:hidden property="agreementId" value="${pnrWorkplanMainForm.agreementId}" />
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.startTime" />
		</td>
		<td class="content">
			${pnrWorkplanMainForm.startTime}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.endTime" />
		</td>
		<td class="content">
			${pnrWorkplanMainForm.endTime}
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
			<fmt:message key="pnrWorkplanWork.workContent" />
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
	<tr>		
		<td class="label" style="vertical-align:middle">
			工作任务类型
		</td>
		<td class="content"  colspan="3">
				<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${work.workType}" beanId="id2nameXML" />					
		</td>				
	</tr>	
	<!--
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanWork.startTime" />
		</td>
		<td class="content">
			${work.startTimeStr}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanWork.endTime" />
		</td>
		<td class="content">
			${work.endTimeStr}
		</td>	
	</tr>
 
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
			<fmt:message key="pnrWorkplanMain.accessories" />
		</td>
		<td class="content" colspan="3">
 			<eoms:attachment name="pnrWorkplanMainForm" property="accessoriesId" scope="request" idField="accessoriesId" appCode="workplan"  viewFlag="Y"/> 
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