<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/header_self.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<script language="javaScript" type="text/javascript" src="${app}/scripts/module/partner/ajax.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
</script>

<%@ include file="/common/body.jsp"%>

<display:table name="ptaskList" cellspacing="0" cellpadding="0"
	id="ptaskList" pagesize="${ppageSize}" class="listTable taskList"
	export="${export}" requestURI="pnrTransferOfficeMaleGuest.do"
	sort="list" size="ptotal" partialList="true">
    <display:column  sortable="true" headerClass="sortable" title="工单号">			
			${ptaskList.processInstanceId}	
	</display:column>
	
	<display:column sortable="true"
			headerClass="sortable" title="工单主题"  >
				${ptaskList.theme}
			</display:column>
			
       <display:column  sortable="true"
			headerClass="sortable" title="业务号码" >
			${ptaskList.barrierNumber}
			</display:column>
	
		<display:column  sortable="true"
			headerClass="sortable" title="故障发生时间">
				<fmt:formatDate value="${ptaskList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>				
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" >
				<fmt:formatDate value="${ptaskList.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="工单历时">
				${ptaskList.workTask}
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="处理时限(小时)">
				${ptaskList.processLimit}
		</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="当前状态">
			${ptaskList.statusName}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="联系人" >
		${ptaskList.connectPerson}
		</display:column>
		
		<display:column  sortable="true" headerClass="sortable" title="地址" maxLength="15">
			${ptaskList.installAddress}
		</display:column>
		
		<display:column  sortable="true" headerClass="sortable" title="当前处理人" >
			<eoms:id2nameDB id="${ptaskList.executor}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="所属区域">
			<eoms:id2nameDB id="${ptaskList.deptId}" beanId="tawSystemDeptDao"/>
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="局站名称">
			${ptaskList.stationName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="代维公司" >
			<eoms:id2nameDB id="${ptaskList.initiator}" beanId="tawSystemUserDao"/>
		</display:column>	
	
	<display:column sortable="false" headerClass="sortable" title="归集" media="html">
		<c:if test="${ptaskList.maleGuestState eq '1'}">
			主工单
		</c:if>	
		<c:if test="${ptaskList.maleGuestState eq '2'}">
			子工单			
		</c:if>
	</display:column>		
	<display:setProperty name="export.pdf" value="false"/>
	<display:setProperty name="export.xml" value="false"/>
	<display:setProperty name="export.csv" value="false"/>
</display:table>
<%@ include file="/common/footer_eoms.jsp"%>