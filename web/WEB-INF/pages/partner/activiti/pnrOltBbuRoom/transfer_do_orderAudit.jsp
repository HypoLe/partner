<%@ page language="java" pageEncoding="UTF-8"%>
<c:if test="${orderAuditListsize gt 0}">
<%int jNum=0;%>
<div style="height:20;"></div>
<div id="orderAuditHistory">
<!-- 施工队处理信息 -->
    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;">
		<legend>
			验收结果
		</legend>	
 <div class="tabtool-history-detail">&nbsp;
	<a href="#" onclick="javascript:transferSitcher.toggle();return false">查看详情</a>
 </div>
<div style="height:3;"></div>
<c:forEach var="item" items="${orderAuditList}"  varStatus="status"> 

<% jNum += 1;%>
<!-- <br/><br/> -->
<!--标题-->       
<div class="history-item-title">

<%=jNum%>. ${eoms:date2String(item.checkTime)}&nbsp;
 <eoms:id2nameDB id="${item.auditor}" beanId="tawSystemUserDao"/>&nbsp;
<img class="switchIcon" src="${app}/images/icons/closed.gif" align="absmiddle"/>

</div>
<!-- 隐藏的内容 -->
<div class="history-item-content hide">
<table id="sheet1" class="formTable" >
		
 		<tr>
			<td class="label">
				验收时间
			</td>
			<td class="content">
				${eoms:date2String(item.checkTime)}
			</td>	
 			<td class="label">
				验收人
			</td>
			<td class="content">
			<c:if test="${empty item.doPerson}"><eoms:id2nameDB id="${item.auditor}" beanId="tawSystemUserDao" /></c:if><!-- 当审批人为空时显示为审核人 -->			
			<c:if test="${not empty item.doPerson}">${item.doPerson}</c:if>
			</td>
		</tr>		
		<tr>
 			<td class="label">
				验收描述
			</td>
			<td class="content" colspan="3">
				${item.handleDescription}
			</td>
		</tr>
</table>
</div>
<!-- 隐藏的内容 -->
<!-- <div style="height:5;"></div> -->
</c:forEach>
</fieldset>
</div>
</c:if>