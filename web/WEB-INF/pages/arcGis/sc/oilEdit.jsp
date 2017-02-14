<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" 	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" 	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
function formForSubmit(){
Ext.get(document.body).mask("信息提交中，请稍后");
		var id = jq("#id").val();
		var longitude = jq("#longitude").val();
		var latitude = jq("#latitude").val();
		var dataReceiver = jq("#dataReceiver").val();
		var url="${app}/partner/process/process.do?method=submitChangeForGis";
		Ext.Ajax.request({  
		       url : url ,
			   method: 'POST',
			   params:{id:id,longitude:longitude,latitude:latitude,dataReceiver:dataReceiver},
				success: function (result, request) { 
				resjson = result.responseText;
				var json = eval( '(' + resjson + ')' ); //转换为json对象
				var type=json.type;
				if(type=="success"){
				Ext.get(document.body).unmask();
					alert("已成功提交调度信息!");
					window.close();
				}else{
					alert("提交失败！");
					Ext.get(document.body).unmask();
				}
				 },
				 failure: function ( result, request) { 
						 Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					 } 
			 });
			 }
			 
	function selectRes(){
		var url = '${app}/partner/res/PnrResConfig.do?method=searchResBySheetOil';
        //window.open(url);
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}	
	/**
	* 设置资源名称和ID
	*/
	function setRes(returnValue){
		if(returnValue){
			var list = returnValue.split(',');
	        	var resId = list[0];
	        	var resName = list[1];
	        	var resLongitude = list[2];
	        	var resLatitude = list[3];
	        	document.getElementById('siteId').value = resId;
	        	document.getElementById('siteName').value = resName;
	        	document.getElementById('longitude').value = resLongitude;
	        	document.getElementById('latitude').value = resLatitude;
        }
	}			 
			 
</script>

<form action="${app}/partner/arcGis/arcGis.do?method=oiLEdit"    id="oilEngineForm" method="post"  >
	<table class="formTable">
			<caption>
					<div class="header center">
						油机调度信息
					</div>
			</caption>
		<tr>
				<td class="label">
					代维公司&nbsp;*
				</td>
				<td class="content" >
					<eoms:id2nameDB  id="${oilEngine.maintainCompany}" beanId="tawSystemDeptDao"/>
				</td>
				<td class="label">
					所属区域&nbsp;*
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${oilEngine.area}" beanId="tawSystemAreaDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					油机编号&nbsp;*
				</td>
				<td class="content">
					${oilEngine.oilEngineNumber}
				</td>
				<td class="label">
					油机型号&nbsp;*
				</td>
				<td class="content">
					${oilEngine.oilEngineModel}
				</td>
		</tr>
		<tr>
				<td class="label">
					油机厂商&nbsp;*
				</td>
				<td class="content">
				${oilEngine.oilEngineFactory}
				</td>
				<td class="label">
					油机类型&nbsp;*
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${oilEngine.oilEngineType}" beanId="ItawSystemDictTypeDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					额定功率(KW)&nbsp;*
				</td>
				<td class="content">
					${oilEngine.powerRating}
				</td>
				<td class="label">
					标准油耗(L/小时)&nbsp;*
				</td>
				<td class="content">
					${oilEngine.standardFuelConsumption}
				</td>
		</tr>
		<tr>
				<td class="label">
					燃料种类&nbsp;*
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${oilEngine.fuleType}" beanId="ItawSystemDictTypeDao"/>
				</td>
				<td class="label">
					存放地点&nbsp;*
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${oilEngine.place}" beanId="tawSystemAreaDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					产权归属&nbsp;*
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${oilEngine.oilEngineProperty}" beanId="ItawSystemDictTypeDao"/>
				</td>
				<td class="label">
					油机状态&nbsp;*
				</td>
				<td class="content">
						<eoms:id2nameDB  id="${oilEngine.oilEngineStatus}" beanId="ItawSystemDictTypeDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
				 	备注
				</td>
				<td class="content" colspan="3"  >
						${oilEngine.notes}
				</td>
		</tr>
		<tr>
				<td class="label">
					归属站点&nbsp;*
				</td>
			<td class="content" colspan="3">
				<input class="text" type="text" name="siteName" readonly="true" 
					id="siteName" alt="allowBlank:true" value="${oilEngine.siteId}"/>
				<input type="hidden" name="siteId" id="siteId" value="" />
				 <input type="button" class="btn" value="选择" onclick="selectRes()" />
				 <font color="red">选择站点以后自动关联站点经纬度</font>
			</td>
		</tr>			
		
			<tr>
				<td class="label">
					经度&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="longitude"   id="longitude"   class="text"  value="${oilEngine.longitude}"
					alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'">
				</td>
				<td class="label">
					纬度&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="latitude"    id="latitude"    class="text"  value="${oilEngine.latitude}"
					 alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'">
				</td>
		</tr>
		
		<tr>
					<td class="label">
						选择审批人&nbsp;*
					</td>
					<td class="content" colspan="3">
						<input type="text" name="reviewer" id="reviewer" class="text" readonly="readonly" alt="allowBlank:false" />
						<input type="button" name="userButton" id="userButton"	 value="请选择审核人" class="btn"  alt="allowBlank:false" />
						<input type="hidden" name="dataReceiver" id="dataReceiver" />
						<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=userFromDept" rootId="1"	rootText='用户树' valueField="dataReceiver" 
						handler="userButton" textField="reviewer" checktype="user" single="true">
						</eoms:xbox>
					</td>
				</tr>
			<input id="id" type="hidden" name="id" value="${oilEngine.id }">
	</table>
	<input type="button" value="保存" onclick="formForSubmit();"> 		
	<%--<input type="button" value="删除" onclick="removeoilEngine('${oilEngine.id }')"> 		
	--%>
</form>
<%@ include file="/common/footer_eoms.jsp"%>
