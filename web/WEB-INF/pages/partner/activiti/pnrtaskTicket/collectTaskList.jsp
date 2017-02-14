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
       

<bean:define id="url" value="pnrTaskTicket.do"/>
	<c:if test="${carApprove ne 'yes'}">
		<c:set var="export" value="true"></c:set>
	</c:if>
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="${export}" requestURI="pnrTaskTicket.do"
		sort="list" size="total" partialList="true" >   		
   	
   		  <display:column  sortable="true"
			headerClass="sortable" title="工单号">			
		<a href="${app}/activiti/pnrTaskTicket/pnrTaskTicket.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">${taskList.processInstanceId}</a>
			</display:column>

			
		<display:column property="theme" sortable="true"
			headerClass="sortable" title="主题" />

        <display:column  sortable="true"
            headerClass="sortable" title="工单生成人">
			<eoms:id2nameDB id="${taskList.initiator}" beanId="tawSystemUserDao"/>     
        </display:column>
        <display:column  sortable="true"
            headerClass="sortable" title="所属部门">
			<eoms:id2nameDB id="${taskList.deptId}" beanId="tawSystemDeptDao"/>     
        </display:column>
		<display:column property="sendTime" sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
        <display:column sortable="true"
            headerClass="sortable" title="工单审核状态">
			 <c:if test="${taskList.statusName==1}">
			新派发
			</c:if>    
			<c:if test="${taskList.statusName==2}">
			逾期未处理
			</c:if>    
			<c:if test="${taskList.statusName==3}">
			审批未通过
			</c:if>	
        </display:column>

		<display:column sortable="true" headerClass="sortable" title="处理">
	           		 <a href="${app}/activiti/pnrTaskTicket/pnrTaskTicket.do?method=claimTask&taskId=${taskList.taskId}&dueDate=${taskList.endTime}" title="领取">
                         提取
					</a>
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