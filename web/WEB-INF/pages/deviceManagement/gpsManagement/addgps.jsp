<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
 var myjs=jQuery.noConflict();
Ext.onReady(function(){
            v = new eoms.form.Validation({form:'gpsForm'});
            });
</script>
<form action="gpsManagement.do?method=save" method="post"  id="gpsForm" name="gpsForm">
<table class="formTable">
	<tr>
		<td colspan="4"><div class="ui-widget-header" >GPS终端设备信息录入</div></td>
	</tr>
	<tr>
		<td class="label">GSM号码*</td>
		<td class="content">
		<input class="text" type="text" name="gsmid"
					id="sheetId" alt="allowBlank:false" /></td>
		<td class="label">终端设备类型*</td>
		<td>
		<eoms:comboBox name="type" id="type"  initDicId="12103" defaultValue=""
			    alt="allowBlank:false"  />
		</td>
	</tr>
	<tr>
	<td class="label">生产厂家*</td>
	<td><input class="text" type="text" name="factory"
					id="sheetId" alt="allowBlank:false" /></td>
	<td class="label">型号*</td>
	<td><input class="text" type="text" name="gpsmodel"
					id="sheetId" alt="allowBlank:false" /></td>
					
	</tr>
	<tr>
	<td class="label">机身序列号*</td>
	<td colspan="3"><input class="text" type="text" name="gpsselfnumber"
					id="sheetId" alt="allowBlank:false" /></td>
	</tr>
	<tr>
	<td class="label">备注</td>
	<td colspan="3">
	<input type="textarea" id="remark" name="remark"
							class="textarea" style="height:50;width:100%"/>
	</td>
	</tr>
</table>
<input type="submit" value="保存"/>
<input type="button" value="关闭" onclick="javascript:window.close();" class="clsbtn2">
</form>
<%@ include file="/common/footer_eoms.jsp"%>