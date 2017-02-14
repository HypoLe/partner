<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<style type="text/css">
  .labelpartner {
	background: #DCDCDC;
    border: 1px solid #000;
    color: #000000;
    font-family: Arial, Helvetica, sans-serif;
    font-weight: normal;
    margin: 10px auto;
    padding: 3px;
    text-align: left;
    vertical-align: bottom;
    }
</style>


<table id="sheet" class="formTable">
	<tr>
		<caption>
		<div class="header center">线路故障核减详细信息</div>
		</caption>

	</tr>
	<tr>
		<td class="label">故障ID</td>
		<td class="content">${line.faultID}</td>
		<td class="label">故障原因</td>
		<td class="content">${line.faultReason}</td>
	</tr>
	<td class="label">故障ID</td>
	<td class="content">${line.faultID}</td>
	<td class="label">故障原因</td>
	<td class="content">${line.faultReason}</td>
	</tr>
	<td class="label">发生地市</td>
	<td class="content">${line.area}</td>
	<td class="label">线路长度</td>
	<td class="content">${line.lineLength}</td>
	</tr>
	</tr>
	<td class="label">发生地市</td>
	<td class="content"><eoms:id2nameDB id="${line.area}"
		beanId="tawSystemAreaDao" /></td>
	<td class="label">线路长度</td>
	<td class="content">${line.lineLength}</td>
	</tr>
	</tr>
	<td class="label">故障发生时间</td>
	<td class="content">
	<bean:write format="yyyy-MM-dd HH:mm:ss"  name="line" property="startDate" />
	</td>
	<td class="label">故障结束时间</td>
	<td class="content">
	<bean:write format="yyyy-MM-dd HH:mm:ss"  name="line" property="endDate" />
	</td>
	</tr>
	</tr>
	<td class="label">光缆级别</td>
	<td class="content">
	<eoms:id2nameDB id="${line.lineLevel}"
		beanId="ItawSystemDictTypeDao" />
	</td>
	<td class="label">核减提交人</td>
	<td class="content">
	<eoms:id2nameDB id="${check.sendCheckUser}"
		beanId="tawSystemUserDao" />
	</td>
	</tr>
	<c:if test="${type eq 0}">
	<tr>
	<td class="label">故障原因</td>
	<td colspan="3">
	<textarea  name="faultReason" id="faultReason" class="textarea max" readonly="readonly">${check.checkReason}</textarea></td>
	</tr>
	</c:if><c:if test="${type ne 0}">
	<tr>
	<td class="label">故障原因</td>
	<td colspan="3">
	<textarea  name="faultReason" id="faultReason" class="textarea max" readonly="readonly">${check.checkReason}</textarea></td>
	</tr>
	<td class="label">审核意见</td>
	<td colspan="3">
	<textarea  name="faultReason" id="faultReason" class="textarea max" readonly="readonly">${check.checkAllow}</textarea></td>
	</tr>
	</c:if>
	
</table>

<%@ include file="/common/footer_eoms.jsp"%>








