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
			url : 'pnrOverhaulRemake.do?method=viewHistory&processInstanceId=${processInstanceId}',
			isIframe : true
		}, {
			text : '流程图',
			url : 'pnrOverhaulRemake.do?method=graphHistoryProcessInstance&processInstanceId=${processInstanceId}',
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
	  <td class="label">
	  	工单号
	  </td>  
	  <td class="content">	  
			${pnrTransferOffice.processInstanceId}
	  </td>
	  <td class="label">
		工单编码
	  </td>
	  <td class="content" >
			${pnrTransferOffice.sheetId}	
		</td>
	 </tr> 	 
	 <tr>
	  <td class="label">
	  	工单主题
	  </td>
	  
	  <td class="content">	  
			${pnrTransferOffice.theme}
	  </td>
	  <td class="label">
						工单接收人
	  </td>
	  <td class="content" >

	<eoms:id2nameDB id="${pnrTransferOffice.taskAssignee}"
							beanId="tawSystemUserDao" />
		</td>
	 </tr>
 	<tr>		  
		 <td class="label">
				预检预修处理时限*
		</td>
		<td class="content">
				${pnrTransferOffice.processLimit}小时
		</td>
 		<td class="label">
 			预检预修申请提交时间
 		</td>
		<td class="content">
			${eoms:date2String(pnrTransferOffice.submitApplicationTime)}
		</td>
		
	</tr>

 <!-- 	<tr>
			<td class="label">
						工单类型
			</td>
			<td class="content">
				${pnrTransferOffice.workOrderTypeName}
			</td>

			<td class="label">
						工单子类型

			</td>
			<td class="content" >
			${pnrTransferOffice.subTypeName}
			</td>

	</tr>  -->
		
	<tr>
 			<td class="label">
				预检预修联系人
			</td>
			<td class="content">
			${pnrTransferOffice.connectPerson}
			</td>
 			<td class="label">
				网络现状描述
			</td>
			<td class="content">
				${pnrTransferOffice.networkStatus}

			</td>
	</tr>

		<tr>
				<td class="label">
					项目预算
				</td>
				<td class="content">
					<fmt:formatNumber value="${pnrTransferOffice.projectAmount}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />&nbsp;元
				</td>

				<td class="label">
					实物赔补
				</td>
				<td class="content">
					<c:if test="${pnrTransferOffice.kindRestitution!=null && pnrTransferOffice.kindRestitution!=''}">
						<fmt:formatNumber value="${pnrTransferOffice.kindRestitution}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />&nbsp;元
					</c:if>
				</td>
			</tr>
			
			<tr>
				<td class="label">
					现金赔补
				</td>
				<td class="content">
					<c:if test="${pnrTransferOffice.compensate!=null&&pnrTransferOffice.compensate!=''}">
						<fmt:formatNumber value="${pnrTransferOffice.compensate}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />&nbsp;元
					</c:if>
				</td>
 				<td class="label">
					收支比
				</td>
				<td class="content">
					<fmt:formatNumber value="${pnrTransferOffice.calculateIncomeRatio}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />					
				</td>
			</tr>

</table>
    </logic:present>
   
  </div>
   
</div>
<!-- Sheet Tabs End -->