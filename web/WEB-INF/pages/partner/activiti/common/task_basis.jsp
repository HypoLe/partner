<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">

Ext.onReady(function(){
  
   var tabConfig = {
		items : [{
			id : 'sheetinfo',
			text : '基本信息'
		}, {
			text : '流转信息',
			url : 'pnrTaskTicket.do?method=viewHistory&processInstanceId=${processInstanceId}',
			isIframe : true
		}, {
			text : '流程图',
			url : 'pnrTaskTicket.do?method=graphHistoryProcessInstance&processInstanceId=${processInstanceId}',
			isIframe : true
			}
		]
	};
   
	var tabs = new eoms.TabPanel('sheet-detail-page', tabConfig);
   
   });
</script>   
	<!-- Sheet Tabs Start -->
<div id="sheet-detail-page">
  <!-- Sheet Detail Tab Start -->
  <div id="sheetinfo">
  	<logic:present name="pnrTaskTicket" scope="request">
	 <!-- 工单基本信息start --> 
	 
<table class="formTable">
<tr>
	 	  <td class="label">工单主题</td>
		  <td class="content" colspan="3">
			${pnrTaskTicket.theme}
		  </td>
</tr>

<tr>
		   <td class="label">工单子类型</td>		
			
		   <td class="content">
		   <eoms:id2nameDB id="${pnrTaskTicket.subType}" beanId="ItawSystemDictTypeDao"/>
		   </td>  
		  
		   <td class="label">站点</td>
		   <td class="content">
		    ${pnrTaskTicket.site}
		   </td>	  
</tr>
<tr>
		   <td class="label">计划开始时间</td>
		   <td class="content">
		   ${eoms:date2String(pnrTaskTicket.startTime)}
		   </td>
		   <td class="label">计划结束时间</td>
		   <td class="content">
		   ${eoms:date2String(pnrTaskTicket.endTime)}
		   </td>		  
</tr>
<tr>
 			
			<td class="label">涉及专业</td>
			<td class="content">
				${specialty}
			
			</td>
			
 			<td class="label">
				工单接收部门
			</td>
			<td class="content">			
			 ${deptNames}
			</td>
</tr>
<tr>
 			<td class="label">
				工单内容
			</td>
			<td class="content" colspan="3">
			${pnrTaskTicket.content}
			</td>
</tr>	
<tr>
			<td class="label">附件</td>
		    <td  colspan='3'>
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="sheetAccessories" appCode="pnrActTaskTicket" 
		             viewFlag="Y"/> 
		    </td>
</tr>
</table>
</logic:present>
</div>
</div>