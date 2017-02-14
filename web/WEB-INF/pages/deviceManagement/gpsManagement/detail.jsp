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
<form action="gpsManagement.do?method=edit" method="post"  id="gpsForm" name="gpsForm">
<table class="formTable">
	<tr>
		<td colspan="4"><div class="ui-widget-header" >GPS终端设备信息修改 </div></td>
	</tr>
	<tr>
		<td class="label">GSM号码</td>
		<td class="content">
		${gpsModel.gsmid}
		<td class="label">终端设备类型</td>
		<td>
		<eoms:id2nameDB id="${gpsModel.gpstype}" beanId="ItawSystemDictTypeDao" />
		</td>
	</tr>
	<tr>
	<td class="label">生产厂家</td>
	<td>${gpsModel.factory}</td>
	<td class="label">型号</td>
	<td>${gpsModel.gpsmodel}</td>
					
	</tr>
	<tr>
	<td class="label" >机身序列号</td>
	<td colspan="3">${gpsModel.gpsselfnumber}</td>
	</tr>
	<tr>
	<td class="label" >备注</td>
	<td colspan="3">
	${gpsModel.remark}
	</td>
	</tr>
	<input type="hidden" name="id" value="${gpsModel.id}"/>
</table>
<input type="button" value="返回" onclick="javascript:self.history.back();" class="clsbtn2">
</form>
<%@ include file="/common/footer_eoms.jsp"%>