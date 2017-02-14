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
			url : 'pnrTransferOfficeMaleGuest.do?method=viewHistory&processInstanceId=${processInstanceId}',
			isIframe : true
		}, {
			text : '流程图',
			url : 'pnrTransferOfficeMaleGuest.do?method=graphHistoryProcessInstance&processInstanceId=${processInstanceId}',
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
	 <a href="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=troublePhotos&pid=${processInstanceId}" target="_blank">
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
	
	<tr>
			<td class="label">
				机线员
			</td>
			<td class="content">
				${pnrTransferOffice.engineer}
	  </td>
			<td class="label">
				装机地址
			</td>
			<td class="content">
				${pnrTransferOffice.installAddress}
			</td>
		</tr>
		<tr>
			<td class="label">
				接入方式
			</td>
			<td class="content">
				${pnrTransferOffice.pattern}
			</td>
			<td class="label">
				地市
			</td>
			<td class="content">
				${pnrTransferOffice.city}
			</td>
		</tr>
		<tr>
			<td class="label">
				局站名称
			</td>
			<td class="content">
				${pnrTransferOffice.stationName}
			</td>
			<td class="label">
				交接箱名称
			</td>
			<td class="content">
				${pnrTransferOffice.spliceBoxName}
			</td>
		</tr>
		<tr>
			<td class="label">
			主干电缆编码
			</td>
			<td class="content">
			${pnrTransferOffice.cableNumber}
			</td>
			<td class="label">
			分线盒编码
			</td>
			<td class="content">
				${pnrTransferOffice.branchBoxNumber}
			</td>
		</tr>
		<tr>
			<td class="label">
			一级分光器编码
			</td>
			<td class="content">
				${pnrTransferOffice.firstOpticalNumber}
			</td>
			<td class="label">
			一级分光器端口
			</td>
			<td class="content">
				${pnrTransferOffice.firstOpticalPort}
			</td>
		</tr>
		<tr>
			<td class="label">
			二级分光器编码
			</td>
			<td class="content">
				${pnrTransferOffice.secondOpticalNumber}
			</td>
			<td class="label">
			二级分光器端口
			</td>
			<td class="content">
				${pnrTransferOffice.secondOpticalPort}
			</td>
		</tr>
		<tr>
			<td class="label">
				光交接箱编码
			</td>
			<td class="content">
				${pnrTransferOffice.spliceBoxNumber}
			</td>
			<td class="label">
				光交接箱端口
			</td>
			<td class="content">
				${pnrTransferOffice.spliceBoxPort}
			</td>
		</tr>
		<tr>
			<td class="label">
				障碍业务号码
			</td>
			<td class="content">
				${pnrTransferOffice.barrierNumber}
			</td>
			<td class="label">
				工单号
			</td>
			<td class="content">
				${pnrTransferOffice.processInstanceId}
			</td>
	</tr>
</table>
    </logic:present>
   
  </div>
   
</div>
<!-- Sheet Tabs End -->