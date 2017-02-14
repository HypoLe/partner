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
				<c:if test="${item.linkName eq 'need'}">区县维护中心主管工单发起</c:if>
				<c:if test="${item.linkName eq 'cityManageExamine'}">市公司运维部主管审批</c:if>				
				<c:if test="${item.linkName eq 'provinceManageExamine'}">省公司运维部主管审批</c:if>				
				<c:if test="${item.linkName eq 'worker'}">区县维护中心主管回单</c:if>				
				<c:if test="${item.linkName eq 'orderAudit'}">市公司运维部主管验收</c:if>				
				<c:if test="${item.linkName eq 'daiweiAudit'}">省公司运维部主管审核</c:if>
				<c:if test="${item.linkName eq 'manualArchive'}">省公司运维部主管手动归档</c:if>
			</td>
			
		</tr>
</table>
</div>
<!-- 隐藏的内容 -->
<!-- <div style="height:3;"></div> -->
</c:forEach>

</div>
</c:if>