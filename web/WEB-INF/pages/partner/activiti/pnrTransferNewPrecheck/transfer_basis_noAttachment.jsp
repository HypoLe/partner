<%@ page language="java" pageEncoding="UTF-8" import="java.util.*" import="com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm"%>
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
			url : 'pnrTransferNewPrecheck.do?method=viewHistory&processInstanceId=${processInstanceId}',
			isIframe : true
		}, {
			text : '流程图',
			url : 'pnrTransferNewPrecheck.do?method=graphHistoryProcessInstance&processInstanceId=${processInstanceId}',
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
						工单号
					</td>
					<td class="content">
						${pnrTransferOffice.processInstanceId}
					</td>
					<td class="label">
						工单编号
					</td>
					<td class="content">
						${pnrTransferOffice.sheetId}
					</td>
				</tr>
				<tr>
					<td class="label">
						工单名称
					</td>
					<td class="content">
						${pnrTransferOffice.theme}
					</td>
					<td class="label">
						工单发起人
					</td>
					<td class="content" colspan='3'>

						<eoms:id2nameDB id="${pnrTransferOffice.createWork}"
							beanId="tawSystemUserDao" />
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
						线路属性
					</td>
					<td class="content">
						<eoms:id2nameDB id="${pnrTransferOffice.workType}"
							beanId="ItawSystemDictTypeDao" />
					</td>
					
					<td class="label">
						承载业务级别
					</td>
					<td class="content">
						<eoms:id2nameDB id="${pnrTransferOffice.bearService}"
							beanId="ItawSystemDictTypeDao" />
					</td>
					
					
				</tr>
				
				<tr>
				    <td class="label">
						关键字
					</td>
					<td class="content">
						<eoms:id2nameDB id="${pnrTransferOffice.keyWord}"
							beanId="ItawSystemDictTypeDao" />
					</td>
					<td class="label">
						紧急程度
					</td>
					<td class="content">
							<eoms:id2nameDB id="${pnrTransferOffice.workOrderDegree}"
							beanId="ItawSystemDictTypeDao" />
					</td>
					
				</tr>
				
				
				<tr>
				  <td class="label">
						项目金额
					</td>
					<td class="content">
						${pnrTransferOffice.projectAmount}&nbsp;元
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
						实物赔补
					</td>
					<td class="content">
						<c:if test="${pnrTransferOffice.kindRestitution!=null && pnrTransferOffice.kindRestitution!=''}">
							<fmt:formatNumber value="${pnrTransferOffice.kindRestitution}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />&nbsp;元
						</c:if>
					</td>
					<td class="label">
						现金赔补
					</td>
					<td class="content">
						${pnrTransferOffice.compensate}&nbsp;元

					</td>
				</tr>
				
				<tr>
					<td class="label">
						收支比
					</td>
					<td class="content">
						<fmt:formatNumber value="${pnrTransferOffice.calculateIncomeRatio}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />					
					</td>
					<td class="label">
						线路名称
					</td>		 
	  				<td>
	  					${pnrTransferOffice.lineName}
	  				</td>
				</tr>
				
				<tr>
				<td class="label">
						项目起点
					</td>
					<td class="content">
						${pnrTransferOffice.projectStartPoint}

					</td>
			
				<td class="label">
				项目终点
				</td>		 
	  			<td>
	  				${pnrTransferOffice.projectEndPoint}
	  			</td>
				</tr>
				
				<tr>
				<td class="label">
					具体地点（标石，杆号，人井号）
				</td>		 
	  			<td>
	  				${pnrTransferOffice.specificLocation}
	  			</td>
				<td class="label">
				照片
				</td>		 
	  			<td>
	  			<!--<a href="${app}/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=troublePhotos&pid=${processInstanceId}" target="_blank">
	  				查看
	  			</a>-->
	  			  <a href="${app}/partner/arcGis/arcGis.do?method=gotoGisOrder&orderId=${pnrTransferOffice.processInstanceId}" target="_blank">
	  				   图片（定位信息）
	  			         </a>
	  			</td>
				</tr>
				
				
				
				<tr>
				<td class="label">
						预检预修描述
					</td>
					<td class="content" colspan="3">
						${pnrTransferOffice.faultDescription}
					</td>
				
				<!--
				<tr>
				<td class="label">
						事前图片
					</td>
					<td class="content">
					<a href="javascript:void(0)" onclick="selectPhoto()" target="_blank">
	  				查看 
	  			         </a> 
	  			       
					</td>
				</tr>
				-->
			</table>


		</logic:present>
	</div>
</div>
<!-- Sheet Tabs End -->
<script type="text/javascript">
  
  /**
	*  打开图片选择页面
	*/
	function selectPhoto(){	
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=showCreateWorkPhoto&pid=${processInstanceId}';
        //window.open(url);
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
</script>

