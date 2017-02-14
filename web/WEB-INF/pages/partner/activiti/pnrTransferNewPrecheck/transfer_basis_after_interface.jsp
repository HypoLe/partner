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

	<!-- 基本信息 -->
    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;">
		<legend>
			基本信息
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
					 待定
				</td>

				<td class="label" style="width: 10%">
					子场景*
				</td>
				<td class="content" style="width: 23%">
					 待定
				</td>

				<td class="label" style="width: 10%">
					承载业务级别*
				</td>
				<td class="content" style="width: 24%">
					<eoms:id2nameDB id="${pnrTransferOffice.bearService}"
							beanId="ItawSystemDictTypeDao" />
				</td>
			</tr>
			
			<tr>
				<td class="label" style="width: 10%">
					紧急程度*
				</td>
				<td class="content" style="width: 23%">
					 <eoms:id2nameDB id="${pnrTransferOffice.workOrderDegree}"
							beanId="ItawSystemDictTypeDao" />
				</td>
				<td class="label" style="width: 10%">
					关键字*
				</td>
				<td class="content" style="width: 23%">
					  <eoms:id2nameDB id="${pnrTransferOffice.keyWord}"
							beanId="ItawSystemDictTypeDao" />
				</td>

				<td class="label" style="width: 10%">
					预检预修申请提交时间*
				</td>
				<td class="content" style="width: 24%">
					${eoms:date2String(pnrTransferOffice.submitApplicationTime)}
				</td>
			</tr>
			
			<tr>
				<td class="label" style="width: 10%">
					部门负责人*
				</td>
				<td class="content" style="width: 23%">
					 ${pnrTransferOffice.deptHead}
				</td>
				<td class="label" style="width: 10%">
					部门负责人电话*
				</td>
				<td class="content" style="width: 23%">
					  ${pnrTransferOffice.deptHeadMobilePhone}
				</td>
				<td class="label" style="width: 10%">
					应急/常规*
				</td>
				<td class="content" style="width: 24%">
					 <eoms:id2nameDB id="${pnrTransferOffice.precheckFlag}"
							beanId="ItawSystemDictTypeDao" />
				</td>
			</tr>

		</table>
	</fieldset>
	
	<!-- 金额信息 -->
    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;">
		<legend>
			金额信息
		</legend>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" style="width: 10%">
					项目金额估算*
				</td>
				<td class="content" style="width: 23%">
					<fmt:formatNumber value="${pnrTransferOffice.projectAmount}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />&nbsp;元
				</td>

				<td class="label" style="width: 10%">
					实物赔补*
				</td>
				<td class="content" style="width: 23%">
					<c:if test="${pnrTransferOffice.kindRestitution!=null && pnrTransferOffice.kindRestitution!=''}">
						<fmt:formatNumber value="${pnrTransferOffice.kindRestitution}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />&nbsp;元
					</c:if>
				</td>
				<td class="label" style="width: 10%">
					现金赔补*
				</td>
				<td class="content" style="width: 23%">
					<c:if test="${pnrTransferOffice.compensate!=null&&pnrTransferOffice.compensate!=''}">
						<fmt:formatNumber value="${pnrTransferOffice.compensate}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />&nbsp;元
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="label" style="width: 10%">
					收支比
				</td>
				<td class="content" style="width: 24%">
					<fmt:formatNumber value="${pnrTransferOffice.calculateIncomeRatio}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />					
				</td>
			</tr>
		</table>
	</fieldset>
	
	<!-- 项目描述 -->
    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;">
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
		<!-- 场景模板-材料清单 -->
	    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;border-color: #98C0F4;">
			<legend>
				新建工单-场景模板
			</legend>	
			<iframe id="taskTicketStatisticsList" name="taskTicketList" frameborder="0" style="width:100%;height:100%"  src="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkChildSceneForDetails&linkType=need&processInstanceId=${pnrTransferOffice.processInstanceId}"></iframe>
		</fieldset>
	</c:if>
	
	<c:if test="${showWorkerScene eq 'yes'}">
		<!-- 场景模板-材料清单 -->
	    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;border-color: #98C0F4;">
			<legend>
				施工队回单-场景模板
			</legend>	
			<iframe id="taskTicketStatisticsList1" name="taskTicketList1" frameborder="0" style="width:100%;height:100%"  src="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkChildSceneForDetails&linkType=worker&processInstanceId=${pnrTransferOffice.processInstanceId}"></iframe>
		</fieldset>
	</c:if>
	
	<c:if test="${showDaiweiAuditScene eq 'yes'}">
		<!-- 区县抽查 -->
	    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;border-color: #98C0F4;">
			<legend>
				区县抽检-场景模板
			</legend>	
			<iframe id="taskTicketStatisticsList2" name="taskTicketList2" frameborder="0" style="width:100%;height:100%"  src="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkChildSceneForDetails&linkType=daiweiAudit&processInstanceId=${pnrTransferOffice.processInstanceId}"></iframe>
		</fieldset>
	</c:if>
	
</logic:present>

<c:if test="${!empty sheetAccessories && sheetAccessories ne null}">

<div id="sheetAccessories"> 
    
       <!-- 附件列表 -->
    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;">
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

