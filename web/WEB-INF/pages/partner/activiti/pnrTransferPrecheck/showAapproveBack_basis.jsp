<%@ page language="java" pageEncoding="UTF-8"%>
<c:if test="${approveBackListsize gt 0}">
<%int jNum=0;%>
<div style="height:20;"></div>
<div id="approveBackHistory" class="panel">
<table class="formTable">
  			<caption>驳回信息</caption>
</table>
 <div class="tabtool-history-detail">&nbsp;
	<a href="#" onclick="javascript:approveBackSwitcher.toggle();return false">查看详情</a>
 </div>
 
<c:forEach var="item" items="${approveBackList}"  varStatus="status"> 

<% jNum += 1;%>
<br/><br/>
<!--标题-->       
<div class="history-item-title">

<%=jNum%>. ${eoms:date2String(item.checkTime)}&nbsp;
 
<img class="switchIcon" src="${app}/images/icons/closed.gif" align="absmiddle"/>

</div>
<!-- 隐藏的内容 -->
<div class="history-item-content hide">
<table id="sheet1" class="formTable" >
		
 		<tr>
			<td class="label">
				驳回时间
			</td>
			<td class="content" >
			${eoms:date2String(item.checkTime)}
			</td>
			<td class="label">
				处理人
			</td>
			<td class="content">
			<eoms:id2nameDB id="${item.auditor}" beanId="tawSystemUserDao"/>&nbsp;
			</td>
		</tr>		
		<tr>
 			<td class="label">
				驳回原因
			</td>
			<td class="content" colspan="3">
			
				${item.handleDescription}
			
			</td>
		</tr>
</table>
</div>
<!-- 隐藏的内容 -->
<div style="height:5;"></div>
</c:forEach>

</div>
</c:if>