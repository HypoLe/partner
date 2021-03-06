<%@ page language="java" pageEncoding="UTF-8"%>
<c:if test="${auditListsize gt 0}">
<%int jNum=0;%>
<div style="height:20;"></div>
<div id="auditHistory">
<!-- 审核信息 -->
    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;">
		<legend>
			审核信息
		</legend>	
<table class="formTable">
  			<caption>审核信息</caption>
</table>
 <div class="tabtool-history-detail">&nbsp;
	<a href="#" onclick="javascript:auditSwitcher.toggle();return false">查看详情</a>
 </div>
 
<c:forEach var="item" items="${auditList}"  varStatus="status"> 

<% jNum += 1;%>
<br/><br/>
<!--标题-->       
<div class="history-item-title">

<%=jNum%>. ${eoms:date2String(item.checkTime)}&nbsp;
 <eoms:id2nameDB id="${item.receivePerson}" beanId="tawSystemUserDao"/>&nbsp;
<img class="switchIcon" src="${app}/images/icons/closed.gif" align="absmiddle"/>

</div>
<!-- 隐藏的内容 -->
<div class="history-item-content hide">
<table id="sheet1" class="formTable" >
		
 		<tr>
			<td class="label">
				审核时间
			</td>
			<td class="content" >
			${eoms:date2String(item.checkTime)}
			</td>
			<td class="label">
				审核人
			</td>
			<td class="content">
			<eoms:id2nameDB id="${item.auditor}" beanId="tawSystemUserDao"/>
			</td>
		</tr>		
		<tr>
 			<td class="label">
				审核意见
			</td>
			<td class="content" colspan="3">
			
				${item.opinion}
			
			</td>
		</tr>
</table>
</div>
<!-- 隐藏的内容 -->
<div style="height:5;"></div>
</c:forEach>
	</fieldset>
</div>
</c:if>