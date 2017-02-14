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
			url : 'pnrTransferOffice.do?method=viewHistory&processInstanceId=${processInstanceId}',
			isIframe : true
		}, {
			text : '流程图',
			url : 'pnrTransferOffice.do?method=graphHistoryProcessInstance&processInstanceId=${processInstanceId}',
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
	 <a href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=troublePhotos&pid=${processInstanceId}" target="_blank">
				查看</a>
	  </td>
	 </tr>
 	<tr>		  
		  <td class="label">故障处理时限(单位:小时)</td>
		  
		  <td class="content">
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
	
					<c:if test="${pnrTransferOffice.faultSource =='101230203'}">
						<tr>
							<td class="label">
								故障来源
							</td>
							<td class="content">
								<eoms:id2nameDB id="${pnrTransferOffice.faultSource}"
									beanId="ItawSystemDictTypeDao" />
							</td>
							<td class="label">
								关联巡检众筹转派工单号
							</td>
							<td class="content">
								${pnrTransferOffice.netResInspectId}
							</td>
						</tr>
					</c:if>
					<c:if test="${pnrTransferOffice.faultSource !='101230203'}">
						<tr>
							<td class="label">
								故障来源
							</td>
							<td class="content" colspan="5" style="width:90%">
								<eoms:id2nameDB id="${pnrTransferOffice.faultSource}"
									beanId="ItawSystemDictTypeDao" />
							</td>
						</tr>
					</c:if>
	

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