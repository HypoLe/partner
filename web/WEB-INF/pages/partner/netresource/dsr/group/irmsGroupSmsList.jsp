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

<logic:present name="irmsGroupSmsDSRList" scope="request">
	<display:table id="irmsGroupSmsDSRList"
					name="irmsGroupSmsDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsGroupSmsDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="客户名称">
						${irmsWlanRepeaterDSRList['customer_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产品实例标识">
						${irmsWlanRepeaterDSRList['related_product_instance_id']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="企业代码">
						${irmsWlanRepeaterDSRList['sms_enterprise_cod']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="服务号码类型">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['sms_service_num_type']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="短信服务号码">
						${irmsWlanRepeaterDSRList['sms_service_num']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务代码">
						${irmsWlanRepeaterDSRList['sms_busi_code']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="服务范围">
						${irmsWlanRepeaterDSRList['sms_service_scope']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="服务地市">
						${irmsWlanRepeaterDSRList['service_district']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="协议类型">
						${irmsWlanRepeaterDSRList['sms_protocol_type']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="传输接入方式">
						${irmsWlanRepeaterDSRList['tran_access_way']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务承载方式">
						${irmsWlanRepeaterDSRList['business_hosting_way']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="关联产品实例标识">
						${irmsWlanRepeaterDSRList['product_instance_id']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="接入网关机房名称">
						${irmsWlanRepeaterDSRList['access_gateway_room_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="接入网关名称">
						${irmsWlanRepeaterDSRList['sms_access_gateway']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="网关IP地址">
						${irmsWlanRepeaterDSRList['gateway_ip']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="企业服务器IP地址">
						${irmsWlanRepeaterDSRList['enterprise_server_ip']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="提供服务端口号">
						${irmsWlanRepeaterDSRList['service_port_no']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="登录网关用户名">
						${irmsWlanRepeaterDSRList['gateway_login_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路名称">
						${irmsWlanRepeaterDSRList['related_circuit_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="光路名称">
						${irmsWlanRepeaterDSRList['related_optical_circuit_name']}
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
