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
   
    /**
	*  打开图片选择页面
	*/
	function selectPhoto(){
		var url = '${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=showCreateWorkPhoto&pid=${processInstanceId}';
        //window.open(url);
		var _sHeight = 600;
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
						项目名称
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
						立项时间
					</td>
					<td class="content">
						${eoms:date2String(pnrTransferOffice.submitApplicationTime)}
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
		 
			<tr>
					<td class="label">
						事前图片
					</td>
					<td class="content">
						<a href="javascript:void(0)" onclick="selectPhoto()" target="_blank">
	  						查看
	  					</a>
					</td>
					<td class="label">
						 事中、事后照片
					</td>		 
  					<td>
  					 <a href="${app}/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=troublePhotos&pid=${processInstanceId}" target="_blank">
  							查看
  						</a>
  						
  					<!--  <a href="${app}/partner/arcGis/arcGis.do?method=gotoGisOrder&orderId=${pnrTransferOffice.processInstanceId}" target="_blank">
	  				   图片（定位信息）
	  			         </a>-->	
  					</td>
				</tr>
		
			</table>


		</logic:present>
		<c:if test="${!empty sheetAccessories && sheetAccessories ne null}">

<div id="sheetAccessories" class="panel"> 
     <div class="history-item" width="100%" ><!-- add space to hack ie-->&nbsp;
	   
       <div class="history-item-content">
  		 <table class="formTable" width="100%" cellpadding="0" cellspacing="0">
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
  		  </div>
	    </div>
</div>
</c:if>

	</div>
</div>
<!-- Sheet Tabs End -->
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
  
</script>