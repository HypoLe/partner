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
			url : 'netResInspect.do?method=viewHistory&processInstanceId=${processInstanceId}',
			isIframe : true
		}, {
			text : '流程图',
			url : 'netResInspect.do?method=graphHistoryProcessInstance&processInstanceId=${processInstanceId}',
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
  	<table class="formTable">
  			<caption>工单基本信息</caption>
  	</table>
  	<table id="sheet" class="formTable">
  		<tr>
			<td class="label" style="width:10%">
				工单号
			</td>
			<td class="content" style="width:20%">
				${netResInspect.processInstanceId}
			</td>
			<td class="label">
				工单名称
			</td>
			<td class="content" colspan="3">
				${netResInspect.theme}
			</td>
		</tr>
		<tr>
			<td class="label" style="width:10%">资源类型</td>
			  <td class="content"  style="width:20%">
			  	<eoms:id2nameDB id="${netResInspect.resourceType}" beanId="ItawSystemDictTypeDao" />
			  </td>
			  
			  <td class="label" style="width:10%">问题类型</td>
			  <td class="content" style="width:20%">
			  	<eoms:id2nameDB id="${netResInspect.questionType}" beanId="ItawSystemDictTypeDao" />
			  </td>
			  <td class="label" style="width:10%">上报日期</td>
			  <td class="content" style="width:20%">
			  	${eoms:date2String(netResInspect.reportedDate)}
			  </td>
		</tr>
		<tr>
			<td class="label" style="width:10%">发现人员</td>
			  <td class="content"  style="width:20%">
			  	<eoms:id2nameDB id="${netResInspect.reportedUserId}" beanId="tawSystemUserDao" />
			  </td>
			  
			  <td class="label" style="width:10%">手机号</td>
			  <td class="content" style="width:20%">
			  	${netResInspect.reportedPhoneNum}
			  </td>
			  <td class="label" style="width:10%">发现部门</td>
			  <td class="content" style="width:20%">
			  	<eoms:id2nameDB id="${netResInspect.reportedDeptId}" beanId="tawSystemDeptDao"/>
			  </td>
		</tr>
		<tr>
		  <td class="label" style="width:10%">资源地址</td>
		  <td colspan="5" style="width:20%">
		  	<eoms:id2nameDB id="${netResInspect.city}" beanId="tawSystemAreaDao" />市
		  	<eoms:id2nameDB id="${netResInspect.county}" beanId="tawSystemAreaDao" />
		  	${netResInspect.street}乡镇
		  </td>
		</tr>
		
		<tr>
		  <td class="label" style="width:10%">描述</td>
		  <td colspan="5" style="width:20%">
		  	${netResInspect.reportedDescribe}
		  </td>
		</tr>
  		</table>
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