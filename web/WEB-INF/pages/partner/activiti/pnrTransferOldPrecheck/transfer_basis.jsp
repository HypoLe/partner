<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<script type="text/javascript">

  Ext.onReady(function()
  {
   var tabConfig = {
		items : [{
			id : 'sheetinfo',
			text : '基本信息'
		}, {
			text : '流转信息',
			url : 'pnrTransferOldPrecheck.do?method=viewHistory&processInstanceId=${processInstanceId}',
			isIframe : true
		}, {
			text : '流程图',
			url : 'pnrTransferOldPrecheck.do?method=graphHistoryProcessInstance&processInstanceId=${processInstanceId}',
			isIframe : true
			}
			,{
			text : '附件汇总',
			url : 'pnrTransferOldPrecheck.do?method=showSheetAccessoriesList&pid=${processInstanceId}',
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
				<caption>
					工单基本信息
				</caption>
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
					<td class="content" colspan='3'>

						<eoms:id2nameDB id="${pnrTransferOffice.taskAssignee}"
							beanId="tawSystemUserDao" />
					</td>
				</tr>
				<tr>
					<td class="label">
						预检预修申请提交时间
					</td>
					<td class="content">
						${eoms:date2String(pnrTransferOffice.submitApplicationTime)}
					</td>
					<td class="label">
						关键字
					</td>
					<td class="content">
						<eoms:id2nameDB id="${pnrTransferOffice.keyWord}"
							beanId="ItawSystemDictTypeDao" />
					</td>

				</tr>

				<tr>
					<td class="label">
						工单类型
					</td>
					<td class="content">
						${pnrTransferOffice.workOrderTypeName}
					</td>
					<td class="label">
						工单子类型
					</td>
					<td class="content">
						${pnrTransferOffice.subTypeName}
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
						预检预修描述
					</td>
					<td class="content">
						${pnrTransferOffice.faultDescription}

					</td>
				</tr>

				<tr>
					<td class="label">
				照片
				</td>		 
  			<td  colspan='3'>
  			<a href="${app}/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=troublePhotos&pid=${processInstanceId}" target="_blank">
  				查看
  			</a>
  			</td>
				</tr>
			</table>


		</logic:present>

		<!-- 遍历执行步骤 -->
		<c:if test="${not empty pnrTransferOfficeHandleList}">
			<c:forEach items="${pnrTransferOfficeHandleList}"
				var="PnrTransferOfficeHandle" varStatus="vs">
				<c:if   test= "${ PnrTransferOfficeHandle.company==null||PnrTransferOfficeHandle.company==''}"> 
     				<div style="margin-top:10px;">
					<table id="sheet" class="formTable">
						<tr>
							<td colspan="4">
								<eoms:id2nameDB id="${PnrTransferOfficeHandle.auditor}" beanId="tawSystemUserDao"/>--${PnrTransferOfficeHandle.checkTime}
							</td>
						</tr>
						<tr>
							<td class="label">
								操作人
							</td>
							<td class="content">
								<eoms:id2nameDB id="${PnrTransferOfficeHandle.auditor}" beanId="tawSystemUserDao"/>
							</td>
							
							<td class="label">
							回退原因
							</td>
							<td class="content">
								${PnrTransferOfficeHandle.handleDescription}
							</td>
						</tr>
						
					</table>
				</div>
				</c:if>
			<c:if test="${ PnrTransferOfficeHandle.company!=null&& PnrTransferOfficeHandle.company!=''}">
			<div style="margin-top:10px;">
					<table id="sheet" class="formTable">
						<tr>
							<td colspan="4">
								<eoms:id2nameDB id="${PnrTransferOfficeHandle.auditor}" beanId="tawSystemUserDao"/>
							</td>
						</tr>
						<tr>
							<td class="label">
								制定人
							</td>
							<td class="content">
								<eoms:id2nameDB id="${PnrTransferOfficeHandle.auditor}" beanId="tawSystemUserDao"/>
							</td>
							
							<td class="label">
						简要描述
							</td>
							<td class="content">
								${PnrTransferOfficeHandle.handleDescription}
							</td>
						</tr>
						
						<tr>
							<td class="label">
								电话
							</td>
							<td class="content">
								${PnrTransferOfficeHandle.telephone}
							</td>
							<td class="label">
								单位
							</td>
							<td class="content">
								${PnrTransferOfficeHandle.company}
							</td>

						</tr>
					</table>
				</div>
			</c:if>
			
			</c:forEach>
		</c:if>
	</div>
</div>
<!-- Sheet Tabs End -->