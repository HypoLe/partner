<%@ page language="java" pageEncoding="UTF-8"%>
<c:if test="${approveBackListsize gt 0}">
<%int jNum=0;%>

<div id="approveBackHistory">
<fieldset style="border: 1px solid #dcdcdc; padding: 10px;">
		<legend style="color: #444;font-size: 13px;line-height: 22px;">
			驳回信息
		</legend>
 <div class="tabtool-history-detail">&nbsp;
	<a href="#" onclick="javascript:approveBackSwitcher.toggle();return false">查看详情</a>
 </div>
 <div style="height:3;"></div>
<c:forEach var="item" items="${approveBackList}"  varStatus="status"> 

<% jNum += 1;%>

<!--标题-->       
<div class="history-item-title-back">

<%=jNum%>. ${eoms:date2String(item.checkTime)}&nbsp;
 <eoms:id2nameDB id="${item.auditor}" beanId="tawSystemUserDao"/>
<img class="switchIcon" src="${app}/images/icons/closed.gif" align="absmiddle"/>

</div>
<!-- 隐藏的内容 -->
<div class="history-item-content hide">
<table id="sheet1" class="formTable">
		
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
<!-- <div style="height:3;"></div> -->
</c:forEach>
</div>
 <div style="height:3;"></div>
</c:if>