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


<div class="x-layout-panel-hd" style="border: 1px solid rgb(152, 192, 244); padding: 5px; width: 98%;">“线路故障功能”Excel文件导入</div>
<div style="border-style: none solid solid; border-color:rgb(152, 192, 244); border-width: 0pt 1px 1px; padding: 5px; background-color: rgb(239, 246, 255); width: 98%;">
<form id="myImportForm" action="baseStationMain.do?method=importExcel&listType=success"
	enctype="multipart/form-data" styleId="theform" method="post">
<table id="sheet" class="formTable">
	<tr>
		<td class="label">导入模板</td>
		<td colspan="3"><a
			href="${app}/accessories/upload/baseStationImportTemplate.xls" >基站代维规模导入模板v2.0.xls</a><br>
		</td>
	</tr>
	<tr>
		<td class="label">导入说明</td>
		<td colspan="3">
			<font color="red">
				1.模板的A列“分公司（市）和F列“归属县公司”是互相关联的。 <br />
				2.在配置正确的情况下，T列、U列、V列才会有值；在配置不正确的情况下，则会有“#N/A”出现，请保证不会出现这种情况以确保数据的正确录入。
				<br />
				3.模板中标注了(请勿修改)字样的列，请不要随意修改，以保证数据完整性。
				</p>
			</font>
		</td>
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