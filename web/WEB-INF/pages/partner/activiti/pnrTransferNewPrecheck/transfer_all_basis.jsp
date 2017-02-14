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
			url : 'pnrTransferNewPrecheck.do?method=viewHistory&processInstanceId=${processInstanceId}',
			isIframe : true
		}, {
			text : '流程图',
			url : 'pnrTransferNewPrecheck.do?method=graphHistoryProcessInstance&processInstanceId=${processInstanceId}',
			isIframe : true
			},{
			text : '附件汇总',
			url : 'pnrTransferNewPrecheck.do?method=showSheetAccessoriesList&pid=${processInstanceId}',
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
				<input type="text" class="text" id="sheetCompleteLimit"
					name="sheetCompleteLimit" value="<fmt:formatNumber value='${pnrTransferOffice.processLimit!=null&&pnrTransferOffice.processLimit!=""?pnrTransferOffice.processLimit:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />"
					alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'" />
				(单位:小时)
		</td>
 		<td class="label">
 			预检预修申请提交时间
 		</td>
		<td class="content">
			${eoms:date2String(pnrTransferOffice.submitApplicationTime)}
		</td>
		
	</tr>
		
	<tr>
 			<td class="label">
				预检预修联系人
			</td>
			<td class="content" colspan="3">
			${pnrTransferOffice.connectPerson}
			</td>
	</tr>

</table>
    </logic:present>
   
  </div>
   
</div>
<!-- Sheet Tabs End -->