<%@ page language="java" pageEncoding="UTF-8"%>
<c:if test="${approveListsize gt 0}">
<%int jNum=0;%>
<div style="height:20;"></div>
<div id="approveHistory" class="panel">
<table class="formTable">
  			<caption>审批信息</caption>
</table>
 <div class="tabtool-history-detail">&nbsp;
	<a href="#" onclick="javascript:approveSwitcher.toggle();return false">查看详情</a>
 </div>
 <div style="height:3;"></div>
<c:forEach var="item" items="${approveHandleList}"  varStatus="status"> 

<% jNum += 1;%>

<!--标题-->       
<div class="history-item-title">

<%=jNum%>. ${eoms:date2String(item.checkTime)}&nbsp;
	<eoms:id2nameDB id="${item.auditor}" beanId="tawSystemUserDao" />
 <!--<eoms:id2nameDB id="${item.receivePerson}" beanId="tawSystemUserDao"/>&nbsp;-->
 <img class="switchIcon" src="${app}/images/icons/closed.gif" align="absmiddle"/>

</div>
<!-- 隐藏的内容 -->
<div class="history-item-content hide">
<table id="sheet1" class="formTable" >
		
 		<tr>
			<td class="label">
				审批时间
			</td>
			<td class="content" >
			${eoms:date2String(item.checkTime)}
			</td>
			<td class="label">
				审批人
			</td>
			<td class="content">
			<c:if test="${empty item.doPerson}"><eoms:id2nameDB id="${item.auditor}" beanId="tawSystemUserDao" /></c:if><!-- 当审批人为空时显示为审核人 -->			
			<c:if test="${not empty item.doPerson}">${item.doPerson}</c:if>			
			</td>
		</tr>		
		<tr>
 			<td class="label">
				审批描述
			</td>
			<td class="content">			
				${item.handleDescription}			
			</td>
			<td class="label">
				审批环节
			</td>
			<td class="content" >
				<c:if test="${item.linkName eq 'cityLineExamine'}">市线路发起</c:if>				
				<c:if test="${item.linkName eq 'cityLineDirectorAudit'}">市线路主任审核</c:if>				
				<c:if test="${item.linkName eq 'cityManageExamine'}">市运维主管审核</c:if>				
				<c:if test="${item.linkName eq 'cityManageDirectorAudit'}">市运维主任审核</c:if>				
				<c:if test="${item.linkName eq 'cityViceAudit'}">市公司副总审核</c:if>
				<c:if test="${item.linkName eq 'provinceLineExamine'}">省线路主管审核</c:if>
				<c:if test="${item.linkName eq 'provinceLineViceAudit'}">省线路分管经理审核</c:if>
				<c:if test="${item.linkName eq 'provinceManageExamine'}">省运维主管审核</c:if>
				<c:if test="${item.linkName eq 'usertask11'}">专家会审</c:if>			
				<c:if test="${item.linkName eq 'provinceManageViceAudit'}">省运维分管经理审批</c:if>	
				<c:if test="${item.linkName eq 'sendOrder'}">代维公司转派</c:if>
				<c:if test="${item.linkName eq 'worker'}">施工队现场处理</c:if>
				<c:if test="${item.linkName eq 'daiweiAudit'}">工单发起人审核</c:if>
				<c:if test="${item.linkName eq 'orderAudit'}">市运维主管抽查</c:if>		
			</td>
			
		</tr>
</table>
</div>
<!-- 隐藏的内容 -->
<!-- <div style="height:3;"></div> -->
</c:forEach>

</div>
</c:if>