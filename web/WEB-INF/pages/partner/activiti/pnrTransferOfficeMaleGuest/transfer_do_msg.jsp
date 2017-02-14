<%@ page language="java" pageEncoding="UTF-8"%>
<c:if test="${listsize gt 0}">
<%int jNum=0;%>
<div style="height:20;"></div>
<div id="history" class="panel">
<table class="formTable">
  			<caption>工单处理信息</caption>
</table>
 <div class="tabtool-history-detail">&nbsp;
	<a href="#" onclick="javascript:switcher.toggle();return false">查看详细信息</a>
 </div>
<c:forEach var="item" items="${PnrTransferHandleList}"  varStatus="status"> 

<% jNum += 1;%>

<!--标题-->       
<div class="history-item-title">

<%=jNum%>. ${eoms:date2String(item.receiveTime)}&nbsp;
 <eoms:id2nameDB id="${item.receivePerson}" beanId="tawSystemUserDao"/>&nbsp;
<img class="switchIcon" src="${app}/images/icons/closed.gif" align="absmiddle"/>

</div>
<!-- 隐藏的内容 -->
<div class="history-item-content hide">
<table id="sheet1" class="formTable" >
		
 		<tr>
			<td class="label">
				填单时间
			</td>
			<td class="content" >
			${eoms:date2String(item.receiveTime)}
			</td>
			<td class="label">
				处理人
			</td>
			<td class="content">
			${item.doPersons}
			</td>
		</tr>		
		<tr>
 			<td class="label">
				处理描述
			</td>
			<td class="content">
			
				${item.handleDescription}
			
			</td>
			<td class="label">
				故障处理情况
			</td>
			<td class="content">
			
				${item.faultHandle}
			
			</td>
		</tr>
		<tr>
 			<td class="label">
				处理原因
			</td>
			<td class="content">
			<c:if test="${item.faultCause eq '2043'}">
			主干电缆障碍-宽带
			</c:if>
			<c:if test="${item.faultCause eq '2042'}">
			交接分线设备障碍-宽带
			</c:if>
			<c:if test="${item.faultCause eq '2044'}">
			光缆故障-宽带
			</c:if>
			<c:if test="${item.faultCause eq '2048'}">
			电缆被盗-宽带
			</c:if>
			<c:if test="${item.faultCause eq '2046'}">
			线路割接影响-宽带
			</c:if>
			<c:if test="${item.faultCause eq '2041'}">
			配线电缆故障-宽带
			</c:if>
			<c:if test="${item.faultCause eq '1229'}">
			主干、配线铜线距离超长-宽带
			</c:if>
			<c:if test="${item.faultCause eq '507'}">
			分光器-宽带
			</c:if>
			<c:if test="${item.faultCause eq '509'}">
			光电缆-宽带
			</c:if>
			<c:if test="${item.faultCause eq '391'}">
			主干故障-固话
			</c:if>
			<c:if test="${item.faultCause eq '392'}">
			配线故障-固话
			</c:if>
			<c:if test="${item.faultCause eq '743'}">
			 电缆被盗-固话
			</c:if>
			<c:if test="${item.faultCause eq '9'}">
			光电缆-固话
			</c:if>
			<c:if test="${item.faultCause eq '7'}">
			分光器-固话
			</c:if>
			
			
			</td>
		</tr>
</table>
</div>
<!-- 隐藏的内容 -->
<div style="height:5;"></div>
</c:forEach>

</div>
</c:if>