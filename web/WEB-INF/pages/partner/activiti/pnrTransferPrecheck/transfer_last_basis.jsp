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
			url : 'pnrTransferPrecheck.do?method=viewHistory&processInstanceId=${processInstanceId}',
			isIframe : true
		}, {
			text : '流程图',
			url : 'pnrTransferPrecheck.do?method=graphHistoryProcessInstance&processInstanceId=${processInstanceId}',
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
  	<logic:present name="pnrTransferOffice" scope="request">
  	
  	<table class="formTable">
  			<caption>工单基本信息</caption>
  	</table>
  	
  	<table id="sheet" class="formTable"> 	 
	 <tr>
	  <td class="label">工单主题</td>
	  <td class="content">	  
			${pnrTransferOffice.theme}
	  </td>
	  <td class="label">
				预检预修处理时限
			</td>
			<td class="content">
				${pnrTransferOffice.processLimit}
				(单位:小时)
		  </td>	
	 </tr>
 	<tr>		  
		 
					<td class="label">
						工单接收人
					</td>
					<td class="content" >

						<eoms:id2nameDB id="${pnrTransferOffice.taskAssignee}"
							beanId="tawSystemUserDao" />
					</td>
 			<td class="label">预检预修申请提交时间</td>
			<td class="content">
			${eoms:date2String(pnrTransferOffice.createTime)}
			</td>
		
	</tr>

 	<tr>
					<td class="label">
						工单类型
					</td>
					<td class="content">
						<eoms:id2nameDB id="${pnrTransferOffice.workOrderType}"
							beanId="ItawSystemDictTypeDao" />
					</td>

					<td class="label">
						工单子类型
					</td>
					<td class="content">
						<eoms:id2nameDB id="${pnrTransferOffice.subType}"
							beanId="ItawSystemDictTypeDao" />
					</td>

	</tr>
	<tr>
 			<td class="label">
				紧急程度
			</td>
			<td class="content">
			<eoms:id2nameDB id="${pnrTransferOffice.workOrderDegree}"
							beanId="ItawSystemDictTypeDao" />
			</td>
 			<td class="label">
				预检预修联系人
			</td>
			<td class="content">
			${pnrTransferOffice.connectPerson}
			</td>
	</tr>
	<tr>
 			<td class="label">
				预检预修描述
			</td>
			<td class="content" colspan="3">
				${pnrTransferOffice.faultDescription}

			</td>
	</tr>

</table>
    </logic:present>
   
  </div>
   
</div>
<!-- Sheet Tabs End -->