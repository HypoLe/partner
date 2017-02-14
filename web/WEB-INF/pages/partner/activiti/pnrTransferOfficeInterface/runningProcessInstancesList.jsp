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
       

<bean:define id="url" value="pnrTransferInterface.do"/>
	<c:if test="${carApprove ne 'yes'}">
		<c:set var="export" value="true"></c:set>
	</c:if>
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="${export}" requestURI="pnrTransferInterface.do"
		sort="list" size="total" partialList="true" >

   		<display:column sortable="true"
			headerClass="sortable" title="工单号">
			<c:choose>
				<c:when test="${taskList.statusName != '已删除'}">
				<a href="${app}/activiti/pnrTransferInterface/pnrTransferInterface.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
					${taskList.processInstanceId}
				</a>
				</c:when>
			<c:otherwise>${taskList.processInstanceId}</c:otherwise>
				
			</c:choose>
		
			</display:column>
			
		<display:column property="theme" sortable="true"
			headerClass="sortable" title="主题" maxLength="15"/>

		<display:column property="sendTime" sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
        <display:column property="statusName" sortable="true"
            headerClass="sortable" title="当前状态"/>
		<display:column sortable="true"
			headerClass="sortable" title="处理">
			<c:choose>
				<c:when test="${taskList.taskDefKey eq 'transferTask' || taskList.taskDefKey eq 'transferHandle'||taskList.taskDefKey eq 'newTask'}">
				<a href="${app}/activiti/pnrTransferInterface/pnrTransferinterface.do?method=removeProcessInstance&processInstanceId=${taskList.processInstanceId}">
					删除
				</a>&nbsp;&nbsp;
						
				<c:if test="${taskList.taskDefKey eq 'transferTask'}">
					&nbsp;&nbsp;
	           		 <a href="${app}/activiti/pnrTransferInterface/pnrTransferInterface.do?method=rollback&handle=transferTask&taskId=${taskList.taskId}&initiator=${taskList.oneInitiator}" title="回退">
					 抓回
					</a>
					</c:if>
				<c:if test="${taskList.taskDefKey eq 'transferHandle'}">
					&nbsp;&nbsp;
	           		 <a href="${app}/activiti/pnrTransferInterface/pnrTransferinterface.do?method=rollback&handle=transferHandle&taskId=${taskList.taskId}&initiator=${taskList.initiator}" title="回退">
					 抓回
					</a>
					</c:if>
					&nbsp;&nbsp;
				<c:if test="${taskList.taskDefKey eq 'transferHandle'}">
           		 <a href="${app}/activiti/pnrTransferInterface/pnrTransferInterface.do?method=todo&taskId=${taskList.taskId}" title="回复">
				 回单
				</a>
				</c:if>
				</c:when>
			<c:otherwise>--</c:otherwise>
				
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