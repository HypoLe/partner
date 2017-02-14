<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.boco.eoms.eva.util.EvaConstants" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod"/>
<jsp:directive.page import="com.boco.eoms.commons.system.session.form.TawSystemSessionForm" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'evaTaskAuditForm'});
});
</script>
<%
int resultSize = StaticMethod.nullObject2int(request.getAttribute("resultSize"));
TawSystemSessionForm sessionform = (TawSystemSessionForm) request
.getSession().getAttribute("sessionform");
String userName = sessionform.getUsername();
String userId = sessionform.getUserid();
%>
<html:form action="/evaTaskAudit.do?method=doMoreAudit" styleId="evaTaskAuditForm" method="post">
<fmt:bundle basename="com/boco/eoms/eva/config/ApplicationResources-eva">
	<display:table name="taskAuditList" cellspacing="0" cellpadding="0" id="taskAuditList" pagesize="${pageSize}" class="table auditInfoList" export="false"
		requestURI="${app}/eva/evaTaskAudit.do?method=unAuditTaskList" sort="list" partialList="true" size="resultSize">
		 <display:column sortable="true" > 
      		<input type="checkbox" name="ids" value="${taskAuditList.id}">
    	</display:column> 
		<display:column sortable="true" headerClass="sortable" title="模板名称">
		 	<a href="${app}/eva/evaTaskAudit.do?method=auditDetail&id=${taskAuditList.id}&taskId=${taskAuditList.taskId}&templateId=${taskAuditList.templateId}&time=${taskAuditList.auditTime}&timeType=${taskAuditList.auditCycle}&partner=${taskAuditList.partner}">${taskAuditList.templateName}</a>
		</display:column>
		<display:column property="auditCreate"  sortable="false" headerClass="sortable" title="送审时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" />
		<display:column property="auditTimeStr"  sortable="false" headerClass="sortable" title="审核周期" />
		<display:column sortable="true" headerClass="sortable" title="厂商">
			<eoms:id2nameDB id="${taskAuditList.partner}" beanId="tawSystemDeptDao" />
		</display:column>
		<display:column property="totalScore"  sortable="sortable" headerClass="sortable" title="得分" />
		<display:column sortable="true" headerClass="sortable" titleKey="eva.auditInfo.status">
		 <eoms:dict key="dict-eva" dictId="auditFlag" itemId="${taskAuditList.auditResult}" beanId="id2nameXML" />
		</display:column>
	</display:table>
	</fmt:bundle>
	<%
	if(resultSize>0){
	%>
	<table>
	<tr>
		<td>
			<input type="button" class="btn" name="反 选" value="反 选" onClick="reserveCheck('ids')">  
		</td>
		<td>
			<input type="button" class="btn" name="批 审" value="批 审" onclick="$('audit-div').toggleClass('hide')">  
		</td>
	</tr>
</table>
<div id="audit-div">
<table class="formTable" id="tplForm" width="60%">
<caption>
	<div class="header center">任务执行批量审核
	</div>

</caption>
	<tr>
		<td class="label">
		审核人	
		</td>
		<td class="content">
		<%=userName %>
		</td>
	</tr>
		<tr>
		<td class="label">
		审核
		</td>
		<td class="content">
			<INPUT type="radio" name="auditResult" value="2" checked="true">通过
		    <INPUT type="radio" name="auditResult" value="1">不通过
		</td>
	</tr>
	<tr height="50">
		<td width="30%" class="label"> 
			审核说明 
		</td>
		<td width="100%" colspan="3">
			<html:textarea property="auditRemark" styleId="auditRemark" style="width:50%"
						styleClass="textarea" alt="maxLength:400" />
		</td>
</table>
<table>
	<tr>
		<td>		
			<input type="submit" class="btn" name="提 交" value="提 交" onClick="return commitCheck()"> 
		</td>
	</tr>
</table>

</div>
<script type="text/javascript"> 
 $('audit-div').toggleClass('hide');
</script>
<%
	}
%>
</html:form>
<script type="text/javascript">   
	//全选   
	function checkAll(name) {   
		var names = document.getElementsByName(name);   
		var len = names.length;   
		if (len > 0) {   
			var i = 0;   
			for (i = 0; i < len; i++)   
				names[i].checked = true;   
			 }   
          }   

	//全不选   
	function checkAllNo(name) {   
		var names = document.getElementsByName(name);   
		var len = names.length;   
		if (len > 0) {   
			var i = 0;   
			for (i = 0; i < len; i++)   
				names[i].checked = false;   
               }   
           }   
 
	//反选   
	function reserveCheck(name) {   
		var names = document.getElementsByName(name);   
		var len = names.length;   
		if (len > 0) {   
			var i = 0;   
			for (i = 0; i < len; i++) {   
				if (names[i].checked) names[i].checked = false;   
                     else names[i].checked = true;   
				}   
            }  
        }    
    function  commitCheck()
    {   document.forms[0].action="evaTaskAudit.do?method=doMoreAudit";
    	var ids = document.getElementsByName("ids");   
		var len = ids.length;  
		var flag = false; 
		for(var i=0;i<len;i++){ 
			if(ids[i].checked==true){ 
				flag = true; 
				return true; 
				break; 
			} 
		}
		alert("没有选择记录");
      	return false;
    }

</script>   
 
<%@ include file="/common/footer_eoms.jsp"%>
