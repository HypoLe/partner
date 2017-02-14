<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
</script>

<script type="text/javascript">
function openImport(){
	var handler = document.getElementById("openQuery");
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开查询界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭查询界面";
	}
}
</script>

<div align="center">
	<b>直放站-数据同步-
	<font color="red">
		<c:if test="${synchType eq 'add'}">新增</c:if>
		<c:if test="${synchType eq 'delete'}">删除</c:if>
	</font>
	-列表页面</b>
</div><br><br/>

<!-- 同步结果开始 -->
 <div style="margin-top:20px;border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;">
 	最近一次同步时间：<font color="red">${dataSynchResult['datasynchTime']}</font>，本次同步共
		<c:if test="${synchType eq 'add'}">新增<font color="red">${dataSynchResult['dataSynchAddResultCount']}</font></c:if>
		<c:if test="${synchType eq 'delete'}">删除<font color="red">${dataSynchResult['dataSynchDeleteResultCount']}</font></c:if>
 	条数据。
 </div>
<!-- 同步结果结束 -->

<logic:present name="irmsBsWepDSRList" scope="request">
	<display:table id="irmsBsWepDSRList"
					name="irmsBsWepDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsBsWepDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="机房名称">
						${irmsBsWepDSRList['related_room']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备类型">
						<eoms:id2nameDB id="${irmsBsWepDSRList['device_type']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备子类">
						<eoms:id2nameDB id="${irmsBsWepDSRList['device_subclass']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备名称">
						${irmsBsWepDSRList['label_dev']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备编码">
						${irmsBsWepDSRList['device_sequence']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="资产编号">
						${irmsBsWepDSRList['equipmentcode']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="型号">
						${irmsBsWepDSRList['model']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="生产厂商">
						${irmsBsWepDSRList['related_vendor']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="品牌">
						${irmsBsWepDSRList['trademark']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="供应商">
						${irmsBsWepDSRList['supplier']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="代维公司">
						${irmsBsWepDSRList['manage_company']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="开始使用时间">
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="预计报废时间">
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="使用状态">
						<eoms:id2nameDB id="${irmsBsWepDSRList['status']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="风机额定功率（KVA）">
						${irmsBsWepDSRList['rating_power']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="额定电压（V）">
						<eoms:id2nameDB id="${irmsBsWepDSRList['rating_voltage']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="风机形式">
						<eoms:id2nameDB id="${irmsBsWepDSRList['fan_form']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="维护负责人">
						${irmsBsWepDSRList['preserver']}
				</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
<%@ include file="/common/footer_eoms.jsp"%>
