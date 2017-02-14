<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
 <base target="_self"/>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
function selectSheet(){
		var objName= document.getElementsByName("radio1");
		var string = '';
		 for (var i = 0; i<objName.length; i++){
                if (objName[i].checked==true){ 
                string = objName[i].value.trim();
                break;
                }
        } 
		window.returnValue=string;
		window.close();
	}
</script>
       

<bean:define id="url" value="pnrTransferArteryPrecheck.do"/>
	<c:if test="${carApprove ne 'yes'}">
		<c:set var="export" value="false"></c:set>
	</c:if>
	<display:table name="historicTasks" cellspacing="0" cellpadding="0"
		id="historicTasks" pagesize="${historicTasksPageSize}" class="listTable taskList"
		export="${export}" requestURI="pnrTransferArteryPrecheck.do"
		sort="list" size="historicTasksTotal" partialList="true" >
		<display:column property="name" sortable="true"
			headerClass="sortable" title="工单状态" />
		<display:column property="startTime" sortable="true"
			headerClass="sortable" title="开始时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
        <display:column property="endTime" sortable="true"
            headerClass="sortable" title="结束时间"
            format="{0,date,yyyy-MM-dd HH:mm:ss}" />
        <display:column  sortable="true"
            headerClass="sortable" title="操作人">
        <eoms:id2nameDB id="${historicTasks.assignee}" beanId="tawSystemUserDao"/>            
        </display:column>

		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
	</display:table>
	</br>
	<c:if test="${carApprove eq 'yes'}">
		<input type="button" class="btn" value="确定" onclick="selectSheet();"/>
	</c:if>
<%@ include file="/common/footer_eoms.jsp"%>