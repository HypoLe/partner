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
			<td class="label">故障是否恢复</td>
			<td class="content" >
			 <eoms:id2nameDB id="${item.isRecover}" beanId="ItawSystemDictTypeDao"/>

			</td>
 		
			<td class="label">
				填单时间
			</td>
			<td class="content" >
			${eoms:date2String(item.receiveTime)}
			</td>
		</tr>		
		<tr>
		
 			<td class="label">
				处理人
			</td>
			<td class="content">
			${item.doPersons}
			</td>
 			<td class="label">
				处理描述
			</td>
			<td class="content">
			
				${item.handleDescription}
			
			</td>
		</tr>
		
		<tr>
			<td class="label">附件</td>
		    <td  colspan='3' >
		    <eoms:attachment name="sheetReply${status.index}" property="sheetAccessories" 
		            scope="request" idList="sheetAccessories" idField="sheetAccessories" appCode="pnrActTransferOffice" 
		             viewFlag="Y"/> 
		    </td>
	   </tr>
	   
</table>
</div>
<!-- 隐藏的内容 -->
<div style="height:5;"></div>
</c:forEach>

</div>
</c:if>