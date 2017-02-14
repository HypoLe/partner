<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style type="text/css">
#toOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
#toAuditOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
</style>
<script type="text/javascript">
 var workNum = ${fn:length(workList)};
</script>
<html:form action="/pnrWorkplanMains.do?method=save" styleId="pnrWorkplanMainForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/workplan/config/applicationResources-partner-workplan">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="pnrWorkplanMain.form.heading"/></div>
	</caption>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.workplanName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content"  colspan="3">
			<html:text property="workplanName" styleId="workplanName"
						styleClass="text"  style="width:98%;" 
						alt="allowBlank:false,vtext:''" value="${pnrWorkplanMainForm.workplanName}" />
		</td>
		<!-- 
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.workplanNO" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="workplanNO" styleId="workplanNO"
						styleClass="text"  style="width:98%;" 
						alt="allowBlank:false,vtext:''" value="${pnrWorkplanMainForm.workplanNO}" />
		</td>
		 -->
	</tr>
	<html:hidden property="workplanNO" value="${pnrWorkplanMainForm.workplanNO}" />
	<tr>
		<td class="label" style="vertical-align:middle">
			服务协议名称：&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" >
			<html:text property="agreementName" styleId="agreementName"
						styleClass="text"  style="width:98%;" 
						alt="allowBlank:false,vtext:''" value="${pnrWorkplanMainForm.agreementName}" readonly="true" />
		</td>	
		<td class="label" style="vertical-align:middle">
			服务协议编号：&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" >
			<html:text property="agreementNO" styleId="agreementNO"
						styleClass="text"  style="width:98%;" 
						alt="allowBlank:false,vtext:''" value="${pnrWorkplanMainForm.agreementNO}" readonly="true" />
			<html:hidden property="agreementId" value="${pnrWorkplanMainForm.agreementId}" />
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.startTime" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="startTime" styleId="startTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''"  style="width:98%;" value="${pnrWorkplanMainForm.startTime}" onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true" />
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.endTime" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="endTime" styleId="endTime"
						styleClass="text medium"
						 value="${pnrWorkplanMainForm.endTime}"  style="width:98%;" onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:'moreThen',link:'startTime',vtext:'结束时间应晚于开始时间'" readonly="true" />
		</td>
	</tr>

	<tr>
	<td colspan="4">
	<span style="font-weight:bold;" >工作任务</span><img align=right src="${app}/images/icons/add.gif" alt="添加工作任务" onclick="javascript:addWork();" />
	<div id="workTemplate">
	<table class="formTable">
		<tr>	
			<td class="label" style="vertical-align:middle">
				<fmt:message key="pnrWorkplanWork.workContent" />
			</td>
			<td class="content" colspan="3">
				<textarea name="workContent" id="workContent" maxLength="2000" rows="1" style="width:100%;" alt="allowBlank:false,vtext:''" ></textarea>			
				<html:hidden property="workId" value="" />
			</td>
		</tr>
		<tr>	
			<td class="label" style="vertical-align:middle">
				工作完成标准
			</td>
			<td class="content" colspan="3">
				<textarea name="workStandard" id="workStandard" maxLength="2000" rows="1" style="width:100%;" alt="allowBlank:false,vtext:''" ></textarea>			
			</td>
		</tr>	
		
		<tr>	
			<td class="label" style="vertical-align:middle">
				工作任务类型
			</td>
			<td class="content"  colspan="3">
					<eoms:dict key="dict-partner-agreement" dictId="workType" defaultId=""
						 selectId="workType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择工作任务类型'" />			
			</td>						
		</tr>			
		<tr>
			<td class="label" style="vertical-align:middle">
				<fmt:message key="pnrWorkplanWork.startTime" />
			</td>
			<td class="content">
				<html:text property="workStartTime" styleId="workStartTime"
							styleClass="text medium"
							 onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:''" style="width:98%;" readonly="true"  value="" />
			</td>
			<td class="label" style="vertical-align:middle">
				<fmt:message key="pnrWorkplanWork.endTime" />
			</td>
			<td class="content">
				<html:text property="workEndTime" styleId="workEndTime"
							styleClass="text medium"  style="width:100%;" 
							 onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:'moreThen',link:'workStartTime',vtext:'结束时间应晚于开始时间'" readonly="true"  value="" />
			</td>
		</tr>
<!-- 
		<tr>	
		<td class="label" style="vertical-align:middle">
			工作考核标准
		</td>
		<td class="content" colspan="3">
			<textarea name="evaStandard" id="evaStandard" maxLength="2000" rows="3" style="width:90%;" alt="allowBlank:false,vtext:''" ></textarea>			
		</td>
		</tr>
 -->		
		<tr>
			<th colspan="4"><img align=right src="${app}/images/icons/add.gif" alt="添加工作任务" onclick="javascript:addWork();" /><img align=right src="${app}/images/icons/list-delete.gif" alt="删除上方内容" onclick="removeNodes(parentNode.parentNode.parentNode);"/></th>
		</tr>
		</table>
		
	</div>
	<div id="workDiv">
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
				<textarea name="workContent" id="workContent" maxLength="2000" rows="1" style="width:100%;" alt="allowBlank:false,vtext:''" >${work.workContent}</textarea>			
				<html:hidden property="workId" value="${work.id}" />
			</td>
		</tr>
		<tr>	
			<td class="label" style="vertical-align:middle">
				工作完成标准
			</td>
			<td class="content" colspan="3">
				<textarea name="workStandard" id="workStandard" maxLength="1000" rows="1" style="width:100%;" alt="allowBlank:false,vtext:''" >${work.workStandard}</textarea>			
			</td>
		</tr>
		<tr>	
			<td class="label" style="vertical-align:middle">
				工作任务类型
			</td>
			<td class="content"  colspan="3">
					<eoms:dict key="dict-partner-agreement" dictId="workType" defaultId="${work.workType}"
						 selectId="workType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择工作任务类型'" />			
			</td>						
		</tr>			
		<tr>
			<td class="label" style="vertical-align:middle">
				<fmt:message key="pnrWorkplanWork.startTime" />
			</td>
			<td class="content">
				<html:text property="workStartTime" styleId="workStartTime"
							styleClass="text medium"  style="width:98%;"
							 onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:''" readonly="true"  value="${work.startTimeStr}" />
			</td>
			<td class="label" style="vertical-align:middle">
				<fmt:message key="pnrWorkplanWork.endTime" />
			</td>
			<td class="content">
				<html:text property="workEndTime" styleId="workEndTime"
							styleClass="text medium" style="width:100%;" 
							 onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:'moreThen',link:'workStartTime',vtext:'结束时间应晚于开始时间'" readonly="true"  value="${work.endTimeStr}" />
			</td>
		</tr>
		<tr>
			<th colspan="4"><img align=right src="${app}/images/icons/add.gif" alt="添加工作任务" onclick="javascript:addWork();" /><img align=right src="${app}/images/icons/list-delete.gif" alt="删除上方内容" onclick="removeNodes(parentNode.parentNode.parentNode);"/></th>
		</tr>
	</c:forEach>
	</table>
	</div>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.accessories" />
		</td>
		<td class="content" colspan="3">
			<c:choose>
				<c:when test="${ not empty pnrWorkplanMainForm.id }">
					<eoms:attachment name="pnrWorkplanMainForm" property="accessoriesId" 
           				scope="request" idField="accessoriesId" appCode="workplan" /> 
				</c:when>
				<c:otherwise>
					<eoms:attachment idList="" idField="accessoriesId" appCode="workplan"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
<tr id='userTree'>	
		<%
		String partyBId = String.valueOf(request.getAttribute("partyBId"));
		String toOrgPanels = "[{text:'部门与人员',dataUrl:'/xtree.do?method=userFromDept',rootId='"+partyBId+"'}]";
		String toAuditOrgPanels = "[{text:'部门与人员',dataUrl:'/xtree.do?method=userFromDept', rootId:'1'}]";
		%>	
<td class="label">
	执行人&nbsp;<font color='red'>*</font>
</td>
<td class="">
		<eoms:chooser id="toOrg" category="[{id:'toOrg',text:'执行',allowBlank:false,limit:1,vtext:'请选择执行人'}]" panels="<%=toOrgPanels%>"/>
</td>
<td class="label">
	审核人&nbsp;<font color='red'>*</font>
</td>
<td class="" >
		<eoms:chooser id="toAuditOrg" category="[{id:'toAuditOrg',text:'审核',allowBlank:false,limit:1,vtext:'请选择审核人'}]" panels="<%=toAuditOrgPanels%>"/>
</td>
</tr>
</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="提交" />
			<!-- 
			<c:if test="${not empty pnrWorkplanMainForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/pnrWorkplanMain/pnrWorkplanMains.do?method=remove&id=${pnrWorkplanMainForm.id}';
						location.href=url}"	/>
			</c:if>
			 -->
		</td>
	</tr>
</table>
<html:hidden property="id" value="${pnrWorkplanMainForm.id}" />
<html:hidden property="createTime" value="${pnrWorkplanMainForm.createTime}" />
<html:hidden property="createUser" value="${pnrWorkplanMainForm.createUser}" />
<html:hidden property="createDept" value="${pnrWorkplanMainForm.createDept}" />
<html:hidden property="state" value="${pnrWorkplanMainForm.state}" />
</html:form>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrWorkplanMainForm'});
});
	var workStr = document.getElementById("workTemplate").innerHTML;
	document.getElementById("workTemplate").removeNode(true);
function addWork() {
	Ext.DomHelper.append('workDiv', 
		{tag:'div', 			
		html:workStr
        }
    );
}

function removeNodes(obj) {   
   	obj.removeNode(true);  
	}

</script>
<%@ include file="/common/footer_eoms.jsp"%>