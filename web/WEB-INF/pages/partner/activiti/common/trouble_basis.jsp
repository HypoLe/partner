<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">

  Ext.onReady(function()
  {
   var tabConfig = {
		items : [{
			id : 'sheetinfo',
			text : '基本信息'
		}, {
			text : '流转信息',
			url : 'pnrTroubleTicket.do?method=viewHistory&processInstanceId=${processInstanceId}',
			isIframe : true
		}, {
			text : '流程图',
			url : 'pnrTroubleTicket.do?method=graphHistoryProcessInstance&processInstanceId=${processInstanceId}',
			isIframe : true
			}
		]
	};
   
	var tabs = new eoms.TabPanel('sheet-detail-page', tabConfig);
	
   }
   );
</script>
<!-- Sheet Tabs Start -->
<div id="sheet-detail-page">
  <!-- Sheet Detail Tab Start -->
  <div id="sheetinfo">
  	<logic:present name="pnrTroubleTicket" scope="request">
  	
  	<table class="formTable">
  			<caption>工单基本信息</caption>
  	</table>
  	
  	<table id="sheet" class="formTable"> 	 
	 <tr>
	  <td class="label">工单主题</td>
	  <td colspan="3">	  
			${pnrTroubleTicket.theme}
	  </td>
	 </tr>
 	<tr>		  
		  <td class="label">故障处理时限(单位:小时)</td>
		  
		  <td class="content">
		  ${pnrTroubleTicket.processLimit}
		  </td>	
		  <td class="label">故障站点</td>
			<td class="content">
				${pnrTroubleTicket.failedSite}
			</td>	  
	</tr>
	<tr>
 			<td class="label">故障发生时间</td>
			<td class="content">
			${eoms:date2String(pnrTroubleTicket.createTime)}
			</td>
			<td class="label">涉及专业</td>
			<td class="content">
		    ${specialty}
			</td>
	</tr>

 	<tr>
			<td class="label">是否集团客户</td>
			<td class="content">
			<eoms:id2nameDB id="${pnrTroubleTicket.isCustomers}" beanId="ItawSystemDictTypeDao"/>

			</td>
 		
	        <td class="label">工单子类型</td>
			<td class="content">
				<eoms:id2nameDB id="${pnrTroubleTicket.subType}" beanId="ItawSystemDictTypeDao"/>
			</td>
	</tr>
		
	<tr>
 			<td class="label">
				故障联系人
			</td>
			<td class="content">
			${pnrTroubleTicket.connectPerson}
			</td>
 			<td class="label">
				故障描述
			</td>
			<td class="content">
				${pnrTroubleTicket.faultDescription}

			</td>
	</tr>
	
	<tr>
	<td class="label">
				工单接收人
	</td>		 
  	<td class="content">
  
	<eoms:id2nameDB id="${pnrTroubleTicket.taskAssignee}" beanId="tawSystemUserDao"/>
	</td>
	<td class="label">
				照片
	</td>		 
  	<td class="content">
  	<a href="${app}/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=troublePhotos&pid=${processInstanceId}" target="_blank">
  	查看
  	</a>
  	</td>
 
	</tr>
 	<tr>
			<td class="label">附件</td>
		    <td  colspan='3'>
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="sheetAccessories" appCode="pnrActTroubleTicket" 
		             viewFlag="Y"/> 
		    </td>
	  </tr>
</table>
    </logic:present>
   
  </div>
   
</div>
<!-- Sheet Tabs End -->