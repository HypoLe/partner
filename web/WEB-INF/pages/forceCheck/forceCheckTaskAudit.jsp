<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.forceCheck.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">
<script type="text/javascript">
Ext.onReady(function() {
 v = new eoms.form.Validation({form:'pnrForceCheckTaskForm'});
});

function submitForm(){
    var taskId = '${pnrForceCheckTaskForm.id}';
	var url=eoms.appPath+'/forceCheck/forceCheckTask.do?method=submit&taskId='+taskId;
	location.href=url}
</script>
<eoms:xbox id="pollingManTree" dataUrl="${app}/xtree.do?method=userFromDept" 
	  	rootId="-1" 
	  	rootText="巡检负责人" 
	  	valueField="pollingMan" handler="pollingManName"
		textField="pollingManName"
		checktype="user" single="true"
		data='${pollingManData}'></eoms:xbox>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:form action="/forceCheckTask.do?method=save" styleId="pnrForceCheckTaskForm" method="post">
<fmt:bundle basename="com/boco/eoms/forceCheck/config/applicationResources-forceCheck"> 
	<html:hidden name="pnrForceCheckTaskForm" property="id" />
   <table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="forceCheckTask.form.title"/></div>
	</caption>
      <tr>
        <td class="label">
			<fmt:message key="forceCheckTask.taskName" />&nbsp;*	
		</td>
		<td class="content" colspan="3">
		<html:text property="taskName" styleId="taskName"
						styleClass="text max" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${pnrForceCheckTaskForm.taskName}" />
		</td>
	  </tr>	
      <tr>
          <td class="label">
            <fmt:message key="forceCheckTask.startTime" />
          </td>
          <td class="content">
            <input type="text" name="startTime" size="20" VALUE='${pnrForceCheckTaskForm.startTime}' onclick="popUpCalendar(this, this,null,null,null,true,-1);" 
                   id="startTime" readonly="true"  class="text">
          </td>
          <td class="label">
            <fmt:message key="forceCheckTask.endTime" />
          </td>
          <td class="content">
            <input type="text" name="endTime" size="20" VALUE='${pnrForceCheckTaskForm.endTime}' onclick="popUpCalendar(this, this,null,null,null,true,-1);" 
                   id="endTime" readonly="true"  class="text">
          </td>
      </tr>
      	  <tr>
        <td class="label">
			<fmt:message key="forceCheckTask.siteName" />&nbsp;*
		</td>
		<td class="content">
		<html:text property="siteName" styleId="siteName"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${pnrForceCheckTaskForm.siteName}" />
		</td>
        <td class="label">
			<fmt:message key="forceCheckTask.pollingMan" />&nbsp;*
		</td>
		<td class="content">
			<input type="hidden" id="pollingMan" name="pollingMan" value="${pnrForceCheckTaskForm.pollingMan}" />
			<input class="input text" type="text" name="pollingManName" id="pollingManName" 
						alt="allowBlank:false,vtext:'请选择巡检负责人'" value="<eoms:id2nameDB id="${pnrForceCheckTaskForm.pollingMan}" beanId="tawSystemUserDao" />" readonly="true" />
		</td>
	  </tr>
      </table>   

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty pnrForceCheckForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确认删除?')){
						var url=eoms.appPath+'/forceCheck/forceCheckTask.do?method=remove&id=${pnrforceCheckTaskForm.id}';
						location.href=url}"	/>		
			</c:if>							
		</td>
		<td>
					<input type="button" class="btn" value="<fmt:message key="button.submit"/>" onclick="submitForm()"/>
		</td>
		
	</tr>
</table>
<logic:present name="pnrForceCheckMainList" scope="request">
 <c:forEach items="${pnrForceCheckMainList}"  var="pnrForceCheckMainList" varStatus="i" >
 <div class="ui-widget-header" >外力盯防现场值班信息</div>
  <fieldset>
			<legend>值班日志${pnrForceCheckMainList.inOrder}</legend>

<table class="formTable">
	<tr>
	<td class="label">值班开始时间</td>
	<td format="{0,date,yyyy-MM-dd HH:mm:ss}">${pnrForceCheckMainList.signInTime}
					</td>
	<td class="label">值班结束时间</td>
	<td>${pnrForceCheckMainList.signOutTime}</td>
					
	</tr>	
	<tr>
	<td class="label">值班日志</td>
	
	<td  colspan="3">
	<pre><bean:write name="pnrForceCheckMainList" property="content" scope="page"/></pre>
	</td>
	</tr>
	
</table>
</fieldset>
 </c:forEach>     
</logic:present>
</fmt:bundle>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>