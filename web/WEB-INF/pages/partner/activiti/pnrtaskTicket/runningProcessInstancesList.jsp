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


<% String source = request.getParameter("6578706f7274");
 if (source == null) {
  %>
<c:if test="${findForward != null && findForward == 'list'}">
	<jsp:include page="/WEB-INF/pages/wfworksheet/pnrhiddentrouble/listsendUndoJS.jsp"/>
</c:if>
<%} %>
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
       

<bean:define id="url" value="pnrTaskTicket.do"/>
	<c:if test="${carApprove ne 'yes'}">
		<c:set var="export" value="true"></c:set>
	</c:if>
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="${export}" requestURI="pnrTaskTicket.do"
		sort="list" size="total" partialList="true" >
   		

   		<display:column  sortable="true"
			headerClass="sortable" title="工单号" >	
			
			<c:choose>
			<c:when test="${taskList.statusName != '已删除'}">
			<a href="${app}/activiti/pnrTaskTicket/pnrTaskTicket.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
			${taskList.processInstanceId}
			</a>
			</c:when>
			<c:otherwise>
				${taskList.processInstanceId}
			</c:otherwise>
			</c:choose>		
		</display:column>
			
		<display:column property="theme" sortable="true"
			headerClass="sortable" title="主题" />

		<display:column property="sendTime" sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
        <display:column property="statusName" sortable="true"
            headerClass="sortable" title="工单状态"/>
     	<display:column  sortable="true"
			headerClass="sortable" title="处理" style="width:10%;text-align:center">			
			<c:choose>
			<c:when test="${taskList.statusName == '工单池提取工单'}">
			<a href="${app}/activiti/pnrTaskTicket/pnrTaskTicket.do?method=removeProcessInstance&processInstanceId=${taskList.processInstanceId}">
			删除
			</a>
			</c:when>
			<c:otherwise>
			--
			</c:otherwise>
			</c:choose>
			
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