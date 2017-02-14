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

function addTask(){
	var url=eoms.appPath+'/forceCheck/forceCheckTask.do?method=addTask';
	location.href=url}
function signIn(){
    if('${mainId}'){
       alert("您还有尚未完成的值班，请先签退");
    return false;
    }
    var form = document.getElementById("pnrforceCheckMainForm");
 	form["taskId"].value = '${pnrForceCheckTaskForm.id}';
 	form["signInLongitude"].value = document.getElementById("signInLongitude").value;
 	form["signInLatitude"].value  = document.getElementById("signInLatitude").value;
 	form["content"].value  = document.getElementById("contenttest").value;
 	form.submit();
}
function signOut(){
    if('${mainId}'){
    var form = document.getElementById("pnrforceCheckMainFormOut");
 	form["signOutLongitude"].value = document.getElementById("signOutLongitude").value;
 	form["signOutLatitude"].value  = document.getElementById("signOutLatitude").value;
 	form["content"].value  = document.getElementById("contenttest").value;
 	form.submit();}else{
    alert("您还没有进行签到，请先签到");
 	}
}
function done(){
    var taskId = '${pnrForceCheckTaskForm.id}';
    if('${mainId}'){
    alert("您还有尚未完成的值班，请先签退");
    return false;
    }
    if ('${listIsEmpty}'){
    alert("您当前的任务还没有进行值班，请先签到");
    return false;
    }
    var url=eoms.appPath+'/forceCheck/forceCheckTask.do?method=done&taskId=' + taskId;
	location.href=url;
}
</script>

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
			<fmt:message key="forceCheckTask.taskName" />
		</td>
		<td class="content" colspan="3">
		${pnrForceCheckTaskForm.taskName}
		</td>
	  </tr>	 
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckTask.forcecheckRoute" />
		</td>
		<td class="content">
		${pnrForceCheckTaskForm.forcecheckRoute}
		</td>
        <td class="label">
			<fmt:message key="forceCheckTask.forcecheckRouteStage" />
		</td>
		<td class="content">
		${pnrForceCheckTaskForm.forcecheckRouteStage}
		</td>
	  </tr>
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckTask.forcecheckPlace" />
		</td>
		<td class="content">
		${pnrForceCheckTaskForm.forcecheckPlace}
		</td>
        <td class="label">
			<fmt:message key="forceCheckTask.siteName" />
		</td>
		<td class="content">
		${pnrForceCheckTaskForm.siteName}
		</td>
	  </tr>
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckTask.pollingMan" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrForceCheckTaskForm.pollingMan}" beanId="tawSystemUserDao" />				
		</td>
        <td class="label">
			<fmt:message key="forceCheckTask.status" />&nbsp;*
		</td>
		<td class="content">
		  <eoms:comboBox name="status" id="status" initDicId="1200301" defaultValue="${pnrForceCheckTaskForm.taskStauts}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
		</td>
	  </tr> 
		<tr>
		<td class="label">
			<fmt:message key="forceCheckTask.represent" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="represent" styleId="represent" onblur="testCharSize(this,255);"
						styleClass="text max" cols="40" rows="4"
						value="${pnrForceCheckTaskForm.represent}" />
		</td>
	</tr>	  
      </table>   
   <table class="formTable">
         	<caption>
		<div class="header center">盯防值班记录</div>
	</caption>
          <tr>
		      <input type="hidden" name="id" value="${mainId}">
              <td class="label">
			      <fmt:message key="forceCheckMain.signInTime" />
              </td>
              <td class="content">
               ${signInTime}
              </td>
              <td class="label">
			      <fmt:message key="forceCheckMain.signOutTime" />
              </td>
              <td class="content">
               ${signOutTime}
              </td>
          </tr>
          <tr>
             <td class="label">
			      <fmt:message key="forceCheckMain.signInLongitude" />
		     </td>
		     <td class="content">
		        <html:text property="signInLongitude" styleId="signInLongitude"
						styleClass="text medium" onblur="testCharSize(this,32);"
						value="${signInLongitude}" />
		     </td>
             <td class="label">
			      <fmt:message key="forceCheckMain.signInLatitude" />
		     </td>
		     <td class="content">
		        <html:text property="signInLatitude" styleId="signInLatitude"
						styleClass="text medium" onblur="testCharSize(this,32);"
						value="${signInLatitude}" />
		     </td>		     
          </tr>
          <tr>
             <td class="label">
			      <fmt:message key="forceCheckMain.signOutLongitude" />
		     </td>
		     <td class="content">
		        <html:text property="signOutLongitude" styleId="signOutLongitude"
						styleClass="text medium" onblur="testCharSize(this,32);"
						value="${signOutLongitude}" />
		     </td>
             <td class="label">
			      <fmt:message key="forceCheckMain.signOutLatitude" />
		     </td>
		     <td class="content">
		        <html:text property="signOutLatitude" styleId="signOutLatitude"
						styleClass="text medium" onblur="testCharSize(this,32);"
						value="${signOutLatitude}" />
		     </td>		     
          </tr> 
		<tr>
		<td class="label">
			<fmt:message key="forceCheckMain.content" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="contenttest" styleId="contenttest" onblur="testCharSize(this,255);"
						styleClass="text max" cols="40" rows="4"
						value="${contenttest}" />
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
					<input type="button" class="btn" value="<fmt:message key="forceCheckTask.signIn"/>" onclick="signIn()"/>
		</td>
		<td>
					<input type="button" class="btn" value="<fmt:message key="forceCheckTask.signOut"/>" onclick="signOut()"/>
		</td>
		<td>
					<input type="button" class="btn" value="<fmt:message key="forceCheckTask.done"/>" onclick="done()"/>
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
<div style="display: none">
	<html:form action="/forceCheckMain.do?method=signIn" styleId="pnrforceCheckMainForm" method="post" >
		<input type="hidden" name="id" value="">
		<input type="hidden" name="taskId" value="">
		<input type="hidden" name="signInLongitude" value="">
		<input type="hidden" name="signInLatitude" value="">
		<input type="hidden" name="content" value="">
	</html:form>	
	<html:form action="/forceCheckMain.do?method=signOut" styleId="pnrforceCheckMainFormOut" method="post" >
		<input type="hidden" name="id" value="${mainId}">
		<input type="hidden" name="signOutLongitude" value="">
		<input type="hidden" name="signOutLatitude" value="">
		<input type="hidden" name="content" value="">
	</html:form>
</div>
<%@ include file="/common/footer_eoms.jsp"%>