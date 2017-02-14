<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
 var jq=jQuery.noConflict();
Ext.onReady(function(){
            v = new eoms.form.Validation({form:'lineFaultForm'});
            });
            

Ext.onReady(function() {
	v1 = new eoms.form.Validation({form:'importForm'});
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
function openImport(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开导入界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭导入界面";
	}
}

function saveImport() {
	//表单验证
	if(!v1.check()) {
		return;
	}
	
	new Ext.form.BasicForm('importForm').submit({
	method : 'post',
	url : "${app}/authorities/analyseFault.do?method=importRecord",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			alert(action.result.infor);
		},
		failure : function(form, action) {
			alert(action.result.infor);
		}
    });
	
}
</script>
<eoms:xbox id="ownerUser" dataUrl="${app}/xtree.do?method=area"  
		rootId="-1" rootText="地域"  valueField="area" handler="areaText" 
		textField="areaText" checktype="area" single="true" />
		
				<div style="border: 1px solid #98c0f4; padding: 5px;"
			class="x-layout-panel-hd">
			<img src="${app}/images/icons/layout_add.png" align="absmiddle"
				style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer"
				onclick="openImport(this);">从Excel导入</span>
		</div>
		<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="charge.do?method=importRecord" method="post" enctype="multipart/form-data" id="importForm" name="importForm">
			<fieldset>
				<legend>从Excel导入</legend>
				<table id="sheet" class="formTable">
				<tr>
							<td class="label">
								模板下载
							</td>
							<td>
								<input type="button" class="btn" value="下载导入文件模板" 
										onclick="javascript:location.href='${app}/authorities/analyseFault.do?method=download'"/>
							</td>
						</tr>
					<tr>
						<td class="label">选择Excel文件</td>
						<td width="35%">
							<input type="file" id="importFile" name="importFile" class="file"  alt="allowBlank:false"/>
						</td>
						<td width="35%">
							<input type="button" onclick="saveImport()" value="确定"/>
						</td>
					</tr>
				</table>
			</fieldset>
	</form>
</div>

<br/>
<form action="analyseFault.do?method=save" method="post"  id="lineFaultForm" name="lineFaultForm">
<table class="formTable">
	<tr>
		<td colspan="4"><div class="ui-widget-header" >故障信息录入</div></td>
	</tr>
	<tr>
		<td class="label">故障ID</td>
	<td><input class="text" type="text" name="faultID"
					id="faultID" alt="allowBlank:false" /></td>
	</tr>

	<tr>
		<td class="label">故障发生地市</td>
		<td class="content">
		<input class="text" type="text" name="areaText"
					id="areaText" alt="allowBlank:false" />
		<input type="hidden" name="area" id="area"/>			
					</td>
		<td class="label">光缆级别</td>
		
		<td><eoms:comboBox name="lineLevel" id="lineLevel"
				initDicId="1200401" alt="allowBlank:false" /></td>
	
	</tr>
	<tr>
	<td class="label">故障开始日期</td>
	<td><input class="text" type="text" name="startDate"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="vtype:'lessThen',link:'endDate',vtext:'开始时间不能晚于结束时间！'"
					 id="startDate" alt="allowBlank:false" readonly="readonly"/>
					</td>
	<td class="label">故障结束日期</td>
	<td><input class="text" type="text" name="endDate"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="vtype:'moreThen',link:'startDate',vtext:'结束时间不能早于开始时间！'"
					 id="endDate" alt="allowBlank:false" readonly="readonly"/></td>
					
	</tr>
	<tr>
	<td class="label">光缆长度</td>
	<td><input class="text" type="text" name="lineLength"
					id="lineLength" alt="allowBlank:false" /></td>
						<td class="label">备注</td>
	<td><input class="text" type="text" name="remark"
					id="remark"  alt="vtype:'number',allowBlank:false" /></td>
	</tr>
	<tr>
	<td class="label">故障原因</td>
	<td>
	<textarea  name="faultReason" id="faultReason" class="textarea max"></textarea></td>
	</tr>
	
	
</table>
<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="保存"></html:submit>

<%@ include file="/common/footer_eoms.jsp"%>