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

<div class="message">
<p><b>导入说明</b><br />
1.模板的A列“分公司（市）和F列“归属县公司”是互相关联的。 <br />
2.在配置正确的情况下，M列和N列才会有值；在配置不正确的情况下，M列和N列会有“#N/A”出现，请保证不会出现这种情况以确保数据的正确录入。
<br />
3.请保证模板里面的时间满足：yyyy-MM-dd HH:mm:ss的格式以保证数据的正确录入<br />
</p>
<br>
</div>

<form id="myImportForm" action="circuit.do?method=importExcel"
	enctype="multipart/form-data" styleId="theform" method="post">
<table id="sheet" class="formTable">
	<caption>“线路故障功能”Excel文件导入</caption>
	<tr>
		<td class="label">线路故障功能导入模板</td>
		<td colspan="3"><a
			href="${app}/accessories/upload/circuitImportTemplate.xls">线路故障导入模板v2.0.xls</a><br>
		</td>
	</tr>
	<tr>
		<td class="label">附件*</td>
		<td colspan="3"><eoms:attachment name="sheetMain" scope="request"
			idField="${sheetPageName}sheetAccessories"
			appCode="partner2Circuit" alt="allowBlank:false" /></td>
	</tr>
	<tr>
		<td colspan="4"><input id="myImportButton" type="submit"
			value="执行导入" /></td>
	</tr>
</table>
<div id="loadIndicator" class="loading-indicator" style="display:none">正在导入故障记录数据，请耐心等候</div>
<form><%@ include file="/common/footer_eoms.jsp"%>