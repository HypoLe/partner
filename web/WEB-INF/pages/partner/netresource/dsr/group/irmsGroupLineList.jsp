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
	直放站-数据同步-
	<font color="red">
		<c:if test="${synchType eq 'add'}">新增</c:if>
		<c:if test="${synchType eq 'delete'}">删除</c:if>
	</font>
	-列表页面
</div><br><br/>

<!-- 同步结果开始 -->
 <div style="margin-top:20px;border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;">
 	最近一次同步时间：<font color="red"><font color="red">${dataSynchResult['datasynchTime']}</font>，本次同步共
		<c:if test="${synchType eq 'add'}">新增<font color="red">${dataSynchResult['dataSynchAddResultCount']}</font></c:if>
		<c:if test="${synchType eq 'delete'}">删除<font color="red">${dataSynchResult['dataSynchDeleteResultCount']}</font></c:if>
 	个站点。
 </div>
<!-- 同步结果结束 -->

<logic:present name="irmsGroupLineDSRList" scope="request">
	<display:table id="irmsGroupLineDSRList"
					name="irmsGroupLineDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsGroupLineDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="客户名称">
						${irmsWlanRepeaterDSRList['customer_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产品实例标识">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_product_instance_id']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户业务接入地址">
						${irmsWlanRepeaterDSRList['customer_business_access_addr']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务带宽(速率)">
						${irmsWlanRepeaterDSRList['business_bw']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="传输接入方式">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['tran_access_way']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户端客户设备名称">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_client_device_name']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户端客户设备端口编号">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_client_device_port_no']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="移动接入设备名称">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_mobile_access_device_name']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="移动接入设备端口编号">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_mobile_access_device_port_no']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="接入点设备VLAN编号">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_access_device_vlan_id']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="接入点设备SLAN编号">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_access_device_slan_id']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="接入点设备CLAN编号">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_access_device_clan_id']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="局端机房名称">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_office_side_room_name']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="局端设备名称">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_office_side_device_name']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="局端设备端口">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_office_side_device_port']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户终端IP地址（公网/私网）">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_client_ip']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备互联IP地址">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_device_interconnect_ip']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路名称">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_circuit_name']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="光路名称">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_optical_circuit_name']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="接入客户日期">
						${irmsWlanRepeaterDSRList['customer_access_date']}
				</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
