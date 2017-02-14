<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List,com.boco.eoms.summary.model.TawzjMonth,com.boco.eoms.base.util.StaticMethod,com.boco.eoms.commons.system.user.model.TawSystemUser"%>
<script type="text/javascript">
function check(){
var frm = document.forms[0];
	if(frm.team.value == null ||frm.team.value == ""){
	alert( "组名不能为空" );
	frm.team.focus();
	return false;
	}
	if(frm.work.value == null ||frm.work.value == ""){
	alert( "月总结日常维护工作不能为空！" );
	frm.work.focus();
	return false;
	}
	if(frm.specialWork.value == null ||frm.specialWork.value == ""){
	alert( "月总结专项工作不能为空" );
	frm.specialWork.focus();
	return false;
	}
	if(frm.legacy.value == null ||frm.legacy.value == ""){
	alert( "月总结遗留问题不能为空" );
	frm.legacy.focus();
	return false;
	}
	if(frm.monthWork.value == null ||frm.monthWork.value == ""){
	alert( "月计划日常维护工作不能为空" );
	frm.monthWork.focus();
	return false;
	}
	if(frm.planSpecialWork.value == null ||frm.planSpecialWork.value == ""){
	alert( "月计划专项工作不能为空" );
	frm.planSpecialWork.focus();
	return false;
	}if(frm.receiverName.value == null ||frm.receiverName.value == ""){
    	alert("请选择审批人！！");
    	return false;
    }
    return true;
	
}	
function getTeam(){
var frm = document.forms[0];
var team=frm.team.value; 
frm.teamname.value=team; 

}
</script>

<html:form action="/tawzjMonths.do?method=save" styleId="tawzjMonthForm" method="post"> 

<fmt:bundle basename="config/ApplicationResources-summary">

<table class="formTable">
	<caption>
		<div class="header center"><html:text property="team" styleId="team" styleClass="text medium"
						 value="${tawzjMonthForm.team}"  onblur="getTeam();"/><fmt:message key="tawzjMonth.team"/>
						<%=(String)request.getAttribute("month")%><fmt:message key="tawzjMonth.result"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.work" />
		</td>
		<td class="content">
			<html:textarea property="work" styleId="work" style="width:13.5cm;" 
						styleClass="text medium" rows="3" value="${tawzjMonthForm.work}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.specialWork" />
		</td>
		<td class="content">
			<html:textarea property="specialWork" styleId="specialWork" style="width:13.5cm;" 
						styleClass="text medium" rows="3" value="${tawzjMonthForm.specialWork}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.legacy" />
		</td>
		<td class="content">
			<html:textarea property="legacy" styleId="legacy" style="width:13.5cm;" 
						styleClass="text medium" rows="3" value="${tawzjMonthForm.legacy}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.accessoriesId" />
		</td>
		<td class="content">
			<c:choose>
				<c:when test="${ not empty tawzjMonthForm.id }">
					<eoms:attachment name="tawzjMonthForm" property="accessoriesId" 
           				scope="request" idField="accessoriesId" appCode="9" />  
				</c:when>
				<c:otherwise>
					<eoms:attachment idList="" idField="accessoriesId" appCode="9"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>

</table>
<table class="formTable">
	<caption>
		<div class="header center"><input name="teamname" id="teamname" type="text" value= "" readonly="true"> <fmt:message key="tawzjMonth.team"/>
						<%=(String)request.getAttribute("nextMonth")%><fmt:message key="tawzjMonth.plan"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.work" />
		</td>
		<td class="content">
			<html:textarea property="monthWork" styleId="monthWork" style="width:13.5cm;" 
						styleClass="text medium" rows="3" value="${tawzjMonthForm.monthWork}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.specialWork" />
		</td>
		<td class="content">
			<html:textarea property="planSpecialWork" styleId="planSpecialWork" style="width:13.5cm;" 
						styleClass="text medium" rows="3" value="${tawzjMonthForm.planSpecialWork}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.accessoriesId" />
		</td>
		<td class="content">
			<c:choose>
				<c:when test="${ not empty tawzjMonthForm.id }">
					<eoms:attachment name="tawzjMonthForm" property="planAccessoriesId" 
           				scope="request" idField="planAccessoriesId" appCode="9" />  
				</c:when>
				<c:otherwise>
					<eoms:attachment idList="" idField="planAccessoriesId" appCode="9"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>

</table>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" onclick="return check();"/>
			<input type="button" class="btn" value="<fmt:message key="button.app"/>" onclick="app();"/>
		
		<select size="1" name="auditer" class="select">
		<option value="-1" selected>请选择审核人</option>
		<% List userlist =(List) request.getAttribute("checkUserList");
		if (userlist != null) {
		for(int k = 0 ; k<userlist.size();k++){
		TawSystemUser user = (TawSystemUser)userlist.get(k);
		%>
        <option value="<%=user.getUserid() %>" ><%=user.getDeptname() %>&nbsp;<%=user.getUsername()%></option>
        <% 
	 	}
	 	}
		%>
	 </select>
	</tr>
</table>
</fmt:bundle>
<html:hidden property="id" value="${tawzjMonthForm.id}" />
<html:hidden property="status" value="0" />
<html:hidden property="deleted" value="0" />
</html:form>
<script type="text/javascript">  
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'tawzjMonthForm'});
	var	userTreeAction='${app}/xtree.do?method=userFromDept';
			new xbox({
				btnId:'receiverName',dlgId:'dlg-audit',dlgTitle:"请选择人员",
				treeDataUrl:userTreeAction,treeRootId:'-1',treeRootText:"所有人员",treeChkMode:'single',treeChkType:'user',
				showChkFldId:'receiverName',saveChkFldId:'auditer'
			}); 
})
	function app(){
	var frm=document.forms[0];
	if(check()){
     	document.forms[0].action ="${app}/summary/tawzjMonths.do?method=save&&status=1";
    	document.forms[0].submit();
     
     }else{
     return false
     }
	}
	
	getTeam();
</script>
<%@ include file="/common/footer_eoms.jsp"%>