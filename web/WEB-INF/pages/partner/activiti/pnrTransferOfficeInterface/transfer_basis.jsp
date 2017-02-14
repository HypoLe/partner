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
			url : 'pnrTransferInterface.do?method=viewHistory&processInstanceId=${processInstanceId}',
			isIframe : true
		}, {
			text : '流程图',
			url : 'pnrTransferInterface.do?method=graphHistoryProcessInstance&processInstanceId=${processInstanceId}',
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
	  <td class="label">照片</td>
	  <td class="content">	  
	 <a href="${app}/activiti/pnrTransferInterface/pnrTransferInterface.do?method=troublePhotos&pid=${processInstanceId}" target="_blank">
				查看</a>
	  </td>
	 </tr>
 	<tr>		  
		  <td class="label">
				故障处理时限*
			</td>
			<td class="content">
				<input type="text" class="text" id="sheetCompleteLimit"
					name="sheetCompleteLimit" value="${pnrTransferOffice.processLimit}"
					alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'" />
				(单位:小时)
			</td>
		  ${pnrTransferOffice.processLimit}
		  </td>	
 			<td class="label">故障发生时间</td>
			<td class="content">
			${eoms:date2String(pnrTransferOffice.createTime)}
			</td>
		
	</tr>

 	<tr>
					<td class="label">
						工单子类型
					</td>
					<td class="content">
						<eoms:id2nameDB id="${pnrTransferOffice.subType}"
							beanId="ItawSystemDictTypeDao" />
					</td>

					<td class="label">
						工单接收人
					</td>
					<td class="content" colspan='3'>

						<eoms:id2nameDB id="${pnrTransferOffice.taskAssignee}"
							beanId="tawSystemUserDao" />
					</td>

	</tr>
		
	<tr>
 			<td class="label">
				故障联系人
			</td>
			<td class="content">
			${pnrTransferOffice.connectPerson}
			</td>
 			<td class="label">
				故障描述
			</td>
			<td class="content">
				${pnrTransferOffice.faultDescription}

			</td>
	</tr>

 	<tr>
			<td class="label">附件</td>
		    <td  colspan='3'>
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="sheetAccessories" appCode="pnrActTransferOffice" 
		             viewFlag="Y"/> 
		    </td>
	  </tr>
</table>
    </logic:present>
   
  </div>
   
</div>
<!-- Sheet Tabs End -->