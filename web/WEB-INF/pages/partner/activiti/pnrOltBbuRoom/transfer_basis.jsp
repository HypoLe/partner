<%@ page language="java" pageEncoding="UTF-8" import="java.util.*" import="com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<script type="text/javascript">
  Ext.onReady(function()
  {
   var tabConfig = {
		items : [{
			id : 'sheetinfo',
			text : '基本信息'
		}, {
			text : '流转信息',
			url : 'pnrOltBbuRoom.do?method=viewHistory&processInstanceId=${processInstanceId}',
			isIframe : true
		}, {
			text : '流程图',
			url : 'pnrOltBbuRoom.do?method=graphHistoryProcessInstance&processInstanceId=${processInstanceId}',
			isIframe : true
			}
		]
	};
	var tabs = new eoms.TabPanel('sheet-detail-page', tabConfig);
   }
	
   );
</script>

<script type="text/javascript">
	function checkMainMaterialForDetails(processInstanceId){
			//alert(processInstanceId);
			
			var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkMainMaterialForDetails';
			var _sHeight = 300;
		    var _sWidth = 820;
		    var sTop=(window.screen.availHeight-_sHeight)/2;
		    var sLeft=(window.screen.availWidth-_sWidth)/2;
			var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
			window.showModalDialog(url,window,sFeatures);
			
		}
		
	function checkAssistMaterialForDetails(processInstanceId){
			//alert(processInstanceId);
			
			var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkAssistMaterialForDetails';
			var _sHeight = 300;
		    var _sWidth = 820;
		    var sTop=(window.screen.availHeight-_sHeight)/2;
		    var sLeft=(window.screen.availWidth-_sWidth)/2;
			var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
			window.showModalDialog(url,window,sFeatures);
			
		}
</script>
<!-- Sheet Tabs Start -->
<div id="sheet-detail-page">
	<!-- Sheet Detail Tab Start -->
	<div id="sheetinfo">
	<logic:present name="pnrTransferOffice" scope="request">

	<!-- 工单基本信息 -->
    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;border-color: #98C0F4;">
		<legend>
			工单基本信息
		</legend>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" style="width:10%">
					工单名称*
				</td>
				<td colspan="5" style="width:90%">
					${pnrTransferOffice.theme}
				</td>
			</tr>

			<tr>
				<td class="label" style="width: 10%">
					工单发起人*
				</td>
				<td class="content" style="width: 23%">
					<eoms:id2nameDB id="${pnrTransferOffice.createWork}"
							beanId="tawSystemUserDao" />
				</td>

				<td class="label" style="width: 10%">
					发起人部门*
				</td>
				<td class="content" style="width: 23%">
					<eoms:id2nameDB id="${pnrTransferOffice.initiatorDept}" beanId="tawSystemDeptDao" />
				</td>

				<td class="label" style="width: 10%">
					发起人电话*
				</td>
				<td class="content" style="width: 24%">
					${pnrTransferOffice.initiatorMobilePhone}
				</td>
			</tr>
			
			<tr>
				<td class="label" style="width: 10%">
					主场景*
				</td>
				<td class="content" style="width: 23%">
					 ${pnrTransferOffice.workOrderTypeName}
				</td>

				<td class="label" style="width: 10%">
					子场景*
				</td>
				<td class="content" style="width: 23%">
					 ${pnrTransferOffice.subTypeName}
				</td>

				<td class="label" style="width: 10%">
					批次*
				</td>
				<td class="content" style="width: 24%">
					${pnrTransferOffice.batch}
				</td>
			</tr>
		</table>
	</fieldset>
	
	<!-- 机房基本信息 -->
    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;border-color: #98C0F4;">
		<legend>
			机房基本信息
		</legend>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" style="width: 10%">
					地市*
				</td>
				<td class="content" style="width: 23%">
					${pnrTransferOffice.jfCity}
				</td>

				<td class="label" style="width: 10%">
					区县*
				</td>
				<td class="content" style="width: 23%">
					${pnrTransferOffice.jfCountry}
				</td>

				<td class="label" style="width: 10%">
					设备位置/局址名称*
				</td>
				<td class="content" style="width: 24%">
					${pnrTransferOffice.jfAddressName}
				</td>
			</tr>
			
			<c:if test="${pnrTransferOffice.jobOrderType=='olt'||pnrTransferOffice.jobOrderType=='oltbbu'}">
				<tr>
					<td class="label" style="width: 10%">
						机房内OLT数量*
					</td>
					<td class="content" style="width: 23%">
						${pnrTransferOffice.oltNumber}
					</td>
					<td class="label" style="width: 10%">
						总用户数*
					</td>
					<td class="content" style="width: 23%">
						${pnrTransferOffice.totalUserNumber}
					</td>
					<td class="label" style="width: 10%">
						语音用户数*
					</td>
					<td class="content" style="width: 24%">
						${pnrTransferOffice.voiceUser}
					</td>
				</tr>
				
				<tr>
					<td class="label" style="width: 10%">
						宽带用户数*
					</td>
					<td class="content" style="width: 23%">
						${pnrTransferOffice.broadbandUser}
					</td>
					<td class="label" style="width: 10%">
						IPTV用户数*
					</td>
					<td class="content" style="width: 23%">
						${pnrTransferOffice.iptvUser}
					</td>
					<c:if test="${pnrTransferOffice.jobOrderType=='olt'}">
						<td class="label" style="width: 10%">
							机房内有无BBU*
						</td>
						<td class="content" style="width: 24%">
							${pnrTransferOffice.isNoBbu}
						</td>
					</c:if>
					<c:if test="${pnrTransferOffice.jobOrderType=='oltbbu'}">
						<td class="label" style="width: 10%">
							机房内BBU数量*
						</td>
						<td class="content" style="width: 23%">
							${pnrTransferOffice.bbuNumber}
						</td>
					</c:if>
				</tr>
			</c:if>
			<c:if test="${pnrTransferOffice.jobOrderType=='bbu'}">
				<tr>
					<td class="label" style="width: 10%">
						机房内BBU数量*
					</td>
					<td class="content" style="width: 23%" colspan="5">
						${pnrTransferOffice.bbuNumber}
					</td>
				</tr>
			</c:if>

		</table>
	</fieldset>
	
	<!-- 优化信息 -->
    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;border-color: #98C0F4;">
		<legend>
			优化信息
		</legend>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" style="width: 10%">
					是否需要杆路投资*
				</td>
				<td class="content" style="width: 23%">
					${pnrTransferOffice.isNeedRodInvestment}
				</td>

				<td class="label" style="width: 10%">
					是否需要光缆投资*
				</td>
				<td class="content" style="width: 23%">
					${pnrTransferOffice.isNeedCableInvestment}
				</td>

				<td class="label" style="width: 10%">
					新建杆路长度*（1000M以内）
				</td>
				<td class="content" style="width: 24%">
					${pnrTransferOffice.newBuiltRodLength}					
				</td>
			</tr>
			
			<tr>
				<td class="label" style="width: 10%">
					新建杆路投资*
				</td>
				<td class="content" style="width: 23%">
					${pnrTransferOffice.newBuiltRodInvestment}
				</td>

				<td class="label" style="width: 10%">
					原光缆路由简述*（如A-B-C-D）
				</td>
				<td class="content" style="width: 23%">
					${pnrTransferOffice.originalCableRoute}
				</td>

				<td class="label" style="width: 10%">
					新光缆路由简述*（如A-E-F-D）
				</td>
				<td class="content" style="width: 24%">
					${pnrTransferOffice.newCableRoute}					
				</td>
			</tr>
			
			<tr>
				<td class="label" style="width: 10%">
					新建段落（如E-F）
				</td>
				<td class="content" style="width: 23%">
					${pnrTransferOffice.newParagraph}
				</td>

				<td class="label" style="width: 10%">
					光缆布放芯数*
				</td>
				<td class="content" style="width: 23%">
					${pnrTransferOffice.cableClothCoreNumber}
				</td>

				<td class="label" style="width: 10%">
					光缆布放长度*（5KM以内）
				</td>
				<td class="content" style="width: 24%">
					${pnrTransferOffice.cableClothLength}					
				</td>
			</tr>
			
			<tr>
				<td class="label" style="width: 10%">
					光缆投资估算*
				</td>
				<td class="content" style="width: 23%">
					${pnrTransferOffice.cableInvestment}
				</td>

				<td class="label" style="width: 10%">
					线路总投资估算*
				</td>
				<td class="content" style="width: 23%">
					${pnrTransferOffice.lineTotalInvestment}
				</td>
				<c:if test="${pnrTransferOffice.jobOrderType=='olt'||pnrTransferOffice.jobOrderType=='oltbbu'}">
					<td class="label" style="width: 10%">
						设备板卡需求*
					</td>
					<td class="content" style="width: 24%">
						${pnrTransferOffice.boardDemand}					
					</td>
				</c:if>
				<c:if test="${pnrTransferOffice.jobOrderType=='bbu'}">
					<td class="label" style="width: 10%">
						传输设备板卡需求*
					</td>
					<td class="content" style="width: 24%">
						${pnrTransferOffice.transBoardDemand}					
					</td>
				</c:if>
			</tr>
			<tr>
				<c:if test="${pnrTransferOffice.jobOrderType=='olt'||pnrTransferOffice.jobOrderType=='oltbbu'}">
					<td class="label" style="width: 10%">
						设备光模块需求*
					</td>
					<td class="content" style="width: 23%">
						${pnrTransferOffice.lightModuleDemand}
					</td>
				</c:if>
				<c:if test="${pnrTransferOffice.jobOrderType=='bbu'}">
					<td class="label" style="width: 10%">
						传输设备光模块需求*
					</td>
					<td class="content" style="width: 23%">
						${pnrTransferOffice.transLightModuleDemand}
					</td>
				</c:if>
				<td class="label" style="width: 10%">
					设备类投资估算* 
				</td>
				<td class="content" style="width: 23%" colspan="3">
					${pnrTransferOffice.equipmentInvestment}
				</td>
			</tr>
			<tr>
				<td class="label" style="width:10%">
					备注*
				</td>
				<td colspan="5" style="width:90%">
					<span style="max-height:300px;word-break:break-all;overflow:auto;">${pnrTransferOffice.jfRemark}</span>
				</td>
			</tr>

		</table>
	</fieldset>
	
	<!-- 项目描述 -->
    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;border-color: #98C0F4;">
		<legend>
			项目描述
		</legend>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" style="width:10%">
					事前照片
				</td>
				<td colspan="2" style="width:40%">
					<a href="javascript:;" onclick="selectPhoto()">
	  				查看 
	  			         </a>
				</td>
				<td class="label" style="width:10%">
					事中、事后照片
				</td>
				<td colspan="2" style="width:40%">
					<a href="${app}/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=troublePhotos&pid=${processInstanceId}" target="_blank">
	  				查看
	  			</a>
	  			<!--    <a href="${app}/partner/arcGis/arcGis.do?method=gotoGisOrder&orderId=${pnrTransferOffice.processInstanceId}" target="_blank">
	  				   图片（定位信息）
	  			         </a>-->
				</td>
			</tr>
		</table>
	</fieldset>
	
	<c:if test="${showNeedScene eq 'yes'}">
		<!-- 新建 -->
	    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;border-color: #98C0F4;">
			<legend>
				新建工单-场景模板
			</legend>	
			<iframe id="taskTicketStatisticsList" name="taskTicketList" frameborder="0" style="width:100%;height:100%"  src="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkChildSceneForDetails&linkType=need&processInstanceId=${pnrTransferOffice.processInstanceId}"></iframe>
		</fieldset>
	</c:if>
	<c:if test="${showWorkerScene eq 'yes'}">
		<!-- 施工队处理 -->
	    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;border-color: #98C0F4;">
			<legend>
				施工队回单-场景模板
			</legend>	
			<iframe id="taskTicketStatisticsList1" name="taskTicketList1" frameborder="0" style="width:100%;height:100%"  src="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkChildSceneForDetails&linkType=worker&processInstanceId=${pnrTransferOffice.processInstanceId}"></iframe>
		</fieldset>
	</c:if>
	
	<c:if test="${showOrderAuditScene eq 'yes'}">
		<!-- 抽查 -->
	    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;border-color: #98C0F4;">
			<legend>
				验收-场景模板
			</legend>	
			<iframe id="taskTicketStatisticsList2" name="taskTicketList2" frameborder="0" style="width:100%;height:100%"  src="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkChildSceneForDetails&linkType=orderAudit&processInstanceId=${pnrTransferOffice.processInstanceId}"></iframe>
		</fieldset>
	</c:if>
</logic:present>

<c:if test="${!empty sheetAccessories && sheetAccessories ne null}">

<div id="sheetAccessories"> 
    
       <!-- 附件列表 -->
    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;border-color: #98C0F4;">
		<legend>
			附件列表
		</legend>
			<table id="sheet" class="formTable">
  				<tr>
  				  <td style="background:#EDF5FD">
  				  		<input type="checkbox" name="checkAccessories" id="checkAccessories" onclick="check(this)">
  				  </td>
  				  <td class="label content" nowrap="nowrap">
  				 	操作步骤
                  </td>
  				  <td class="label content" nowrap="nowrap">
  				 	附件名称
                  </td>
                  <td class="label" nowrap="nowrap">
  				    上传人
  				  </td>
  				  <td class="label content" nowrap="nowrap">
  				  	 上传时间
                  </td>
  				</tr>
  	<logic:present name="sheetAccessories">
  	<logic:iterate id="tawCommonsAccessoriesForm" name="sheetAccessories" type="com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm" scope="request">
  				<tr>
  				  <td>
  				  		<input type="checkbox" name="accessoriesIds" id="accessoriesIds" value="${tawCommonsAccessoriesForm.id}">
  				  </td>
  				  <td>
  				 	 ${tawCommonsAccessoriesForm.activeTemplateId}
  				  </td>
  				  <td>
  				    	<a href="${app}/accessories/tawCommonsAccessoriesConfigs.do?method=download&id=${tawCommonsAccessoriesForm.id}">${tawCommonsAccessoriesForm.accessoriesCnName}</a>
  				  </td>
  				  <td>
  				     <eoms:id2nameDB id="${tawCommonsAccessoriesForm.accessoriesUploader}" beanId="tawSystemUserDao"/>
  				  </td>
  				  <td>
  				     <bean:write name="tawCommonsAccessoriesForm" property="accessoriesUploadTime" format="yyyy-MM-dd HH:mm:ss"/>
                  </td>  
  				</tr>
	  </logic:iterate>
	  		<table>
	  			<tr>
	  				<td>
	  					<input type="button" name="button" class="button" value="批量导出" onclick="download()" >
	  				</td>
	  			</tr>
	  		</table>	
	 </logic:present> 
  			</table>
	</fieldset>
  
</div>
</c:if>
	</div>
</div>
<!-- Sheet Tabs End -->
<script type="text/javascript">
 switcher = new detailSwitcher();
  switcher.init({
	container:'sheetAccessories',
  	handleEl:'div.history-item-title'
  });
  
  function check(obj) {
  	var accessoriesObj = document.getElementsByName("accessoriesIds");
  	if (obj.checked) {
	  	for (var i = 0; i < accessoriesObj.length; i ++) {
	  		var accessories = accessoriesObj[i];
	  		if(!accessoriesObj[i].checked) {
	  			accessoriesObj[i].checked = true;
	  		}
	  	}
  	} else {
  		for (var i = 0; i < accessoriesObj.length; i ++) {
	  		var accessories = accessoriesObj[i];
	  		if(accessoriesObj[i].checked) {
	  			accessoriesObj[i].checked = false;
	  		}
	  	}
  	}
  	
  }
  function download() {
  	var accessoriesObj = document.getElementsByName("accessoriesIds");
	var ishaveAccess = false;
	for (var i = 0; i < accessoriesObj.length; i ++) {
  		var accessories = accessoriesObj[i];
  		if(accessoriesObj[i].checked) {
  			ishaveAccess = true;
  			break;
  		}
	}
  	if (!ishaveAccess) {
  		alert("请选择要下载的附件！");
  		return false;
  	}
  	var ids = "";
  	for (var i = 0; i < accessoriesObj.length; i ++) {
  		var accessories = accessoriesObj[i];
  		if(accessoriesObj[i].checked) {
  			ids = ids + "," + accessoriesObj[i].value;
  		}
  	}
  	window.location.href ='${app}/accessories/tawCommonsAccessoriesConfigs.do?method=downloadzip&idlist=' + ids;
  }
  
  /**
	*  打开图片选择页面
	*/
	function selectPhoto(){	
		var url = '${app}/activiti/pnrOltBbuRoom/pnrOltBbuRoom.do?method=showCreateWorkPhoto&pid=${processInstanceId}';
        //window.open(url);
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
</script>

