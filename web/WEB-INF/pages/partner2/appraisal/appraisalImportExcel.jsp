<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
 
// add an event to an element
function addEvent(elem, evtType, func) {
	// first try the W3C DOM method
	if (elem.addEventListener) {
		elem.addEventListener(evtType, func, false);
	}
	// otherwise use the ‘traditional’ technique
	else {
		elem["on" + evtType] = func;
	}
}

Ext.onReady(function() {
			var oForm = document.getElementById("myImportForm");
			if (oForm) {
				addEvent(oForm, "submit", checkForm);
			}
			
			// Add validation function supplied by Ext.
			v = new eoms.form.Validation({form:'myImportForm'});
			// Write your validation here.
			v.custom = function(){
				return true;
			};
});

	function checkForm(evt){
		if (!evt) {
			evt = window.event;
		}
		var sheetArray = $('sheetAccessories').value;
		if(sheetArray == ""){
			alert("请上传1个文件以执行导入操作");
			if (evt.preventDefault)
				evt.preventDefault();
			return false;
		}
		var myX = sheetArray.split(",");
		if( myX.length != 1){
			alert("请上传1个文件以执行导入操作");
			if (evt.preventDefault)
				evt.preventDefault();
			return false;
		}else{
			var v1=eoms.form;
			var area1= document.getElementById('myImportButton');
			var area2= document.getElementById('loadIndicator');
			v1.enableArea(area2);
			v1.disableArea(area1,true);
			return true;
		}
	}

function simpleAjax(){
	var v1=eoms.form;
	v1.enableArea(sheet2);
	v1.disableArea(sheet,true);
}	
</script>


<div class="x-layout-panel-hd" style="border: 1px solid rgb(152, 192, 244); padding: 5px; width: 98%;">“考核模板”Excel文件导入</div>
<div style="border-style: none solid solid; border-color:rgb(152, 192, 244); border-width: 0pt 1px 1px; padding: 5px; background-color: rgb(239, 246, 255); width: 98%;">
<form id="myImportForm" action="appraisal.do?method=importExcel"
	enctype="multipart/form-data" styleId="theform" method="post">
<table id="sheet" class="formTable">
	<tr>
		<td class="label">导入模板</td>
		<td colspan="3"><a
			href="${app}/accessories/upload/appraisalImportTemplate.xls" >考核导入模板v1.0.xls</a><br>
		</td>
	</tr>
	<tr>
		<td class="label">导入说明</td>
		<td colspan="3">
			<font color="red">
				1.请使用提供的模板进行导入 <br />
				<br />
				</p>
			</font>
		</td>
	</tr>
	<tr>
		<td class="label">考核指标模板名*</td>
		<td colspan="3"><input type="text" class="text" name="appraisalName" alt="allowBlank:false" /></td>
	</tr>
	
	<tr>
		<td class="label">附件*</td>
		<td colspan="3"><eoms:attachment name="sheetMain" scope="request"
			idField="${sheetPageName}sheetAccessories"
			appCode="partner2" alt="allowBlank:false" /></td>
	</tr>
	<tr>
		<td colspan="4" align="center"><input id="myImportButton" type="submit"
			value="执行导入" /></td>
	</tr>
</table>
<div id="loadIndicator" class="loading-indicator" style="display:none">正在导入故障记录数据，请耐心等候</div>
<form></div></div>
<%@ include file="/common/footer_eoms.jsp"%>