<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript"> 
	function add(){
	window.location.href="${app}/baseinfo/baseStation.do?method=goToAdd"
	}
	function modify(){
		window.location.href="${app}/baseinfo/baseStation.do?method=goToEdit&id=${baseStation.id }"
	}
</script>
<form action="/baseStation.do?method=add" method="post">
	<table id="sheet" class="formTable">
		<tr>
		<caption>
		<div class="header center">基站信息</div>
	</caption>	
		</tr>
		<tr>
			<td class="label">
			 基站名称:
			</td>
			<td class="content">
				<input class="text" type="text" name="stationName" readonly="true" 
					id="stationName" alt="allowBlank:false"  value="${baseStation.baseStationName }"/>
			</td>
			<td class="label">
				站型:
			</td>
			<td class="content" >
			<input class="text" type="text" name="stationForm" readonly="true" 
					id="stationForm" alt="allowBlank:false"  value="<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${baseStation.stationForm}"/>"/>
			</td>
		</tr>
	<tr>	
	<td class="label">
			维护级别:
		</td>
		 <td >
            <input class="text" type="text" name="maintenanceLevel" id="maintenanceLevel" readonly="true" 
					    value="<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${baseStation.maintenanceLevel}"/>"
					 alt="allowBlank:false" />
  		</td>
			<td class="label">
			 机房类型:
			</td>
			<td >
			<input class="text" type="text" name="stationHouseType" readonly="true" 
					id="stationHouseType" alt="allowBlank:false"  value="<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${baseStation.stationHouseType}"/>"/>
			</td>
	</tr>
		<tr>
			<td class="label">
				设备厂家:
			</td>
			<td >
				<input type="text" class="text" name="manufacturer" readonly="true" 
					id="manufacturer" alt="allowBlank:true" value="${baseStation.manufacturer}"/>
			</td>
			<td class="label">
			载波设备:
			</td>
			<td>
				<input type="text" class="text" name="carrierFacility" readonly="true" 
					id="carrierFacility" alt="allowBlank:true" value="${baseStation.carrierFacility}"/>
			</td>
			</tr>
			
			<tr>
			<td class="label">经度</td>
			<td><input type="text" class="text" name="longitude" readonly="true" 
					id="longitude" alt="allowBlank:true" value="${baseStation.longitude }"/></td>
			<td class="label">纬度</td>
			<td><input type="text" class="text" name="latitude" readonly="true" 
					id="latitude" alt="allowBlank:true" value="${baseStation.latitude }"/></td>
			</tr>
			
			<tr>
			<td class="label">
			载波数量:
			</td>
			<td >
				<input type="text" class="text" name="carrierNum" readonly="true" 
					id="carrierNum" alt="allowBlank:true" value="${baseStation.carrierNum }"/>
			</td>
			<td class="label">
			添加人:
			</td>
			<td >
				<input type="text" class="text" name="addman" readonly="true" 
					id="addman" alt="allowBlank:true" value="${baseStation.addman }"/>
			</td>
		</tr>
		<tr><td  class="label">添加时间</td><td><input type="text" class="text" name="addtime" readonly="true" 
					id="addtime" alt="allowBlank:true" value="${baseStation.addtime }"/></td></tr>
			<tr><td class="label" colspan="1">备注</td><td colspan="3" class="content">
	${baseStation.remark}
	<tr>
		</table>
		<c:if test="${type != 'check'}">
		<input type="button" onclick="modify()" value="修改">
		</c:if>
</form>
<%@ include file="/common/footer_eoms.jsp"%>
