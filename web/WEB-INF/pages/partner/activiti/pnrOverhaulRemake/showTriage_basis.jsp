<%@ page language="java" pageEncoding="UTF-8"%>
<c:if test="${triageListsize gt 0}">
<%int jNum=0;%>
<div style="height:20;"></div>
<div id="triageHistory" class="panel">
<table class="formTable">
  			<caption>专家会审信息</caption>
</table>
 <div class="tabtool-history-detail">&nbsp;
	<a href="#" onclick="javascript:triageSwitcher.toggle();return false">查看详情</a>
 </div>
 
<c:forEach var="item" items="${triageList}"  varStatus="status"> 

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
				会审时间
			</td>
			<td class="content" >
			${eoms:date2String(item.checkTime)}
			</td>
			<td class="label">
				会审人
			</td>
			<td class="content">
			<eoms:id2nameDB id="${item.auditor}" beanId="tawSystemUserDao"/>
			</td>
		</tr>		
		<tr>
			<td class="label">
				审核状态
			</td>
			<td class="content">
			<eoms:id2nameDB id="${item.state}" beanId="ItawSystemDictTypeDao"/>
			</td>
 			<td class="label">
				会审意见
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