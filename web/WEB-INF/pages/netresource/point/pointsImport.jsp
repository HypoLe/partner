<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>


<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'importPointsForm'});
	setTabs();
});

	function setTabs() {
		//Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.remove();}});
		var tabs = new Ext.TabPanel('info-page');
		tabs.addTab('base-info', '标点信息Excel批量导入');
   		tabs.activate(0);
	}

	function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
	            }
    }
	
</script>
<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function() {
	v1 = new eoms.form.Validation({form:'importPointsForm'});
	v1.custom = function() {
		var reg = "(.xls)$";
		var file = jq("#importFile").val();
		var right = file.match(reg);
		if(right == null) {
			alert("请选择Excel文件!");
			return false;
		} else {
			return true;
		}
	}
});

  
function saveImport() {
	//表单验证
	if(!v1.check()) {
		return;
	}
	
	new Ext.form.BasicForm('importPointsForm').submit({
	method : 'post',
	url : "${app}/netresource/point/pointsImport.do?method=importData",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			alert(action.result.infor);
			jq("#importFile").val("");
		},
		failure : function(form, action) {
			alert(action.result.infor);
			jq("#importFile").val("");
		}
    });
	
}
</script>

<div id="info-page">
	<div id="base-info" class="tabContent">
		<div class="header center">
			<b>标点信息Excel批量导入</b>
		</div>
		<br />
		<div style="border: 1px solid #98c0f4; padding: 5px;"
			class="x-layout-panel-hd">
			<img src="${app}/images/icons/layout_add.png" align="absmiddle"
				style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer">从Excel导入</span>
		</div>

		<div id="listQueryObject"
			style="border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form action="pointsImport.do?method=importData" method="post"
				enctype="multipart/form-data" id="importPointsForm" name="importPointsForm">
					<table class="formTable">
						<tr>
							<td class="label">
								选择Excel文件
							</td>
							<td width="35%">
								<input id="importFile" type="file" name="importFile"
									class="file" alt="allowBlank:false" style='width:30%;'/>
								&nbsp;&nbsp;
								<font color='red'> * 注意事项：上传的 Excel 必须为 2003版本 的 xls 格式文件！</font>
							</td>
						</tr>
						<tr>
							<td class="label">
								Excel模板下载
							</td>
							<td>
								<input type="button" class="btn" value="标点批量导入模板下载" 
										onclick="javascript:location.href='${app}/netresource/point/pointsImport.do?method=download'"/>
								&nbsp;&nbsp;
								<font color='red'> * 注意事项：请详细阅读Excel模板里关于导入字段的红色字体说明！</font>
							</td>
						</tr>
					</table>
					<input type="button" class="btn" onclick="saveImport()" value="导入" />
			</form>
		</div>
		
	</div>
	
</div>

<%@ include file="/common/footer_eoms.jsp"%>