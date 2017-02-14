<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%response.setHeader("cache-control","public"); %>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">

</script>

<table  id="tab" class="table list" cellspacing="0" cellpadding="0">
	<thead>
		<tr>
			<th>代维资源专业</th>
			<th>代维资源类别</th>
			<th>网络资源专业</th>
			<th>网络资源设备类型</th>
			<th>类别区分值</th>
			<th>关联网络资源</th>
		</tr>
	</thead>
	<c:forEach items="${deviceTypeList}" var="list">
		<tr>
			<td><eoms:id2nameDB id="${specialty}" beanId="ItawSystemDictTypeDao" />	</td>
			<td><eoms:id2nameDB id="${resType}" beanId="ItawSystemDictTypeDao" /></td>
			<td>
				${list.device_specialty_name }
			</td>
			<td>
				${list.device_type_name }
			</td>
			<td>
				<eoms:id2nameDB id="${list.netres_field_value}" beanId="ItawSystemDictTypeDao" />			
			</td>
			<td>
				<a href="${app}/partner/deviceInspect/inspectMapping.do?method=waitRelationInspectDeviceList&resid=${id}&mappingid=${list.id }&tableName=${list.netres_table_name }">
		        	<img src="${app}/images/icons/edit.gif" />
		        </a>
			</td>
		</tr>
	</c:forEach>
	
</table>
</br>
<input type="button" class="btn" value="返回" onclick="javascript:history.back();" />
<%@ include file="/common/footer_eoms.jsp"%>