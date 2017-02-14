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

<logic:present name="irmsGroupRentDSRList" scope="request">
	<display:table id="irmsGroupRentDSRList"
					name="irmsGroupRentDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsGroupRentDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="客户名称">
						${irmsWlanRepeaterDSRList['customer_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A端客户名称">
						${irmsWlanRepeaterDSRList['pointa_customer_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产品实例标识">
						${irmsWlanRepeaterDSRList['related_product_instance_id']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="传输接入方式">
						${irmsWlanRepeaterDSRList['tran_access_way']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务带宽(速率)">
						${irmsWlanRepeaterDSRList['business_bw']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A端客户地址">
						${irmsWlanRepeaterDSRList['pointa_customer_addr']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A 端业务设备名称">
						${irmsWlanRepeaterDSRList['related_pointa_business_device_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A 端业务设备端口">
						${irmsWlanRepeaterDSRList['related_pointa_business_device_port']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A 端业务设备DDF/ODF端口">
						${irmsWlanRepeaterDSRList['pointa_business_device_ddforodf_port']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A端接入机房设备名称">
						${irmsWlanRepeaterDSRList['related_access_room_device_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A端接入机房设备端口">
						${irmsWlanRepeaterDSRList['related_access_room_device_port']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A端接入机房设备ODF端口">
						${irmsWlanRepeaterDSRList['related_access_room_device_odf_port']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路名称">
						${irmsWlanRepeaterDSRList['related_circuit_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路接口类型">
						${irmsWlanRepeaterDSRList['related_circuit_interface_type']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路级别">
						${irmsWlanRepeaterDSRList['related_circuit_level']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路要求">
						${irmsWlanRepeaterDSRList['circuit_requirement']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路保护方式">
						${irmsWlanRepeaterDSRList['circuit_protect_way']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="局向">
						${irmsWlanRepeaterDSRList['office_redirection']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端客户名称">
						${irmsWlanRepeaterDSRList['pointz_customer_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端客户地址">
						${irmsWlanRepeaterDSRList['pointz_customer_addr']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端业务设备名称">
						${irmsWlanRepeaterDSRList['related_pointz_business_device_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端业务设备端口">
						${irmsWlanRepeaterDSRList['related_pointz_business_device_port']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端业务设备DDF/ODF端口">
						${irmsWlanRepeaterDSRList['pointz_business_device_ddforodf_port']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端接入机房设备名称">
						${irmsWlanRepeaterDSRList['related_pointz_acccess_room_device_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端接入机房设备端口">
						${irmsWlanRepeaterDSRList['related_pointz_acccess_room_device_port']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端接入机房设备ODF端口">
						${irmsWlanRepeaterDSRList['related_pointz_acccess_room_device_odf_port']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="光路名称">
						${irmsWlanRepeaterDSRList['related_optical_circuit_name']}
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
