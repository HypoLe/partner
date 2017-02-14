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

function selDuelog(){
	 var undo=document.location.href;
	 undo = undo.substring(0,undo.indexOf("?")+1)+"method=listDueBacklog&definitionKey=troubleShooting";
	 location.href = undo;
	}
	
	window.setTimeout(selDuelog, 10000);
		
</script>
       

<bean:define id="url" value="pnrTroubleTicket.do"/>
	<c:if test="${carApprove ne 'yes'}">
		<c:set var="export" value="true"></c:set>
	</c:if>
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="flase" requestURI="pnrTroubleTicket.do"
		sort="list" size="total" partialList="true">
   		
   		  <display:column  sortable="true" headerClass="sortable" title="工单号">			
				<c:choose>
			<c:when test="${taskList.statusName != '已删除'}">
			<a href="${app}/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
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

        <display:column  sortable="true"
            headerClass="sortable" title="处理人">
            <eoms:id2nameDB id="${taskList.initiator}" beanId="tawSystemUserDao"/> 
        </display:column>    
        <display:column  sortable="true"
            headerClass="sortable" title="所属部门">
            <eoms:id2nameDB id="${taskList.deptId}" beanId="tawSystemDeptDao"/> 
        </display:column>    
		<display:column property="endTime" sortable="true"
			headerClass="sortable" title="结束时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
		<display:column sortable="true"
			headerClass="sortable" title="剩余时间">
			${taskList.timeDifference}						
			</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="当前状态">
			<c:choose>
			<c:when test="${taskList.statusName eq '新建派发'}">
			待处理
			</c:when>
			<c:otherwise>
			${taskList.statusName}
			</c:otherwise>
			</c:choose>
		</display:column>

		<display:column sortable="true" headerClass="sortable" title="督办">					
	           		<a href="${app}/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=reminders&processInstanceId=${taskList.processInstanceId}" title="催单">
					 催单
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