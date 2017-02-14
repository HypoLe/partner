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
			url : 'pnrHiddendangerReported.do?method=viewHistory&processInstanceId=${processInstanceId}',
			isIframe : true
		}, {
			text : '流程图',
			url : 'pnrHiddendangerReported.do?method=graphHistoryProcessInstance&processInstanceId=${processInstanceId}',
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
						工单名称
					</td>
					<td class="content" colspan="3">
					</td>
					
				</tr>
				<tr>
					<td class="label">
						上班日期
					</td>
					<td class="content">
					</td>
					<td class="label">
						专业
					</td>
					<td class="content">
					</td>
					
				</tr>
				<tr>
					<td class="label">
						发现人员
					</td>
					<td class="content">
					</td>
					<td class="label">
						手机号
					</td>
					<td class="content">
					</td>

				</tr>
				<tr>
					<td class="label">
						人员编号
					</td>
					<td class="content">
					</td>
					<td class="label">
						发现部门
					</td>
					<td class="content">
					</td>

				</tr>
				<tr>
					<td class="label">
						资源类型
					</td>
					<td class="content">
					</td>
					<td class="label">
						资源地址
					</td>
					<td class="content">
					</td>

				</tr>
				<tr>
					<td class="label">
						资源名称
					</td>
					<td class="content">
					</td>
					<td class="label">
						问题类型
					</td>
					<td class="content">
					</td>

				</tr>
				<tr>
					<td class="label">
						描述
					</td>
					<td class="content" colspan="3">
					</td>
				</tr>
				<tr>
					<td class="label">
						照片清单
					</td>
					<td class="content" colspan="3">
						<a href="javascript:void(0)" onclick="selectPhoto()" target="_blank">
	  					查看照片
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
	
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferArteryPrecheck.do?method=showCreateWorkPhoto&pid=${processInstanceId}';
        //window.open(url);
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
</script>

