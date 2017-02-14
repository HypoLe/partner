<%@ page language="java" pageEncoding="UTF-8"%>
<c:if test="${transferListsize gt 0}">
<%int jNum=0;%>
<div style="height:20;"></div>
<div id="transferHistory">
<!-- 施工队处理信息 -->
    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;">
		<legend>
			施工队处理信息
		</legend>	
 <div class="tabtool-history-detail">&nbsp;
	<a href="#" onclick="javascript:transferSitcher.toggle();return false">查看详情</a>
 </div>
<div style="height:3;"></div>
<c:forEach var="item" items="${transferList}"  varStatus="status"> 

<% jNum += 1;%>
<!-- <br/><br/> -->
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
				是否处理
			</td>
			<td class="content">
			<eoms:id2nameDB id="${item.isRecover}" beanId="ItawSystemDictTypeDao"/>
			</td>
 			<td class="label">
				处理描述
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