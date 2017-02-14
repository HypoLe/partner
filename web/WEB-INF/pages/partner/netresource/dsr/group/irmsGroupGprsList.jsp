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

<logic:present name="irmsGroupGprsDSRList" scope="request">
	<display:table id="irmsGroupGprsDSRList"
					name="irmsGroupGprsDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsGroupGprsDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="客户名称">
						${irmsWlanRepeaterDSRList['customer_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产品实例标识">
						${irmsWlanRepeaterDSRList['related_pro_instance_id']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="APN名称">
						${irmsWlanRepeaterDSRList['apn_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="APN号码">
						${irmsWlanRepeaterDSRList['related_apn_id']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务带宽(速率)">
						${irmsWlanRepeaterDSRList['business_bw']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务开放范围">
						${irmsWlanRepeaterDSRList['business_open_limi']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务承载方式">
						${irmsWlanRepeaterDSRList['business_hosting_way']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="关联产品实例标识">
						${irmsWlanRepeaterDSRList['relative_pro_instance_id']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="IP地址分配方式">
						${irmsWlanRepeaterDSRList['ip_allocation_mode']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="地址数量">
						${irmsWlanRepeaterDSRList['use_ip_num']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否双GGSN">
						${irmsWlanRepeaterDSRList['is_both_ggsn']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="传输接入方式">
						${irmsWlanRepeaterDSRList['tran_access_way']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="企业服务器IP地址">
						${irmsWlanRepeaterDSRList['enterprise_service']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的名称">
						${irmsWlanRepeaterDSRList['label_cn1']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的GRE本端地址">
						${irmsWlanRepeaterDSRList['gre_address_name1']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的GRE企业端地址">
						${irmsWlanRepeaterDSRList['gre_enterprise_add1']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的GRE名称">
						${irmsWlanRepeaterDSRList['gre_name1']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的GRE隧道key">
						${irmsWlanRepeaterDSRList['gre_tunnrl_key1']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的名称">
						${irmsWlanRepeaterDSRList['label_cn2']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的GRE本端地址">
						${irmsWlanRepeaterDSRList['gre_address_name2']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的GRE企业端地址">
						${irmsWlanRepeaterDSRList['gre_enterprise_add2']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的GRE名称">
						${irmsWlanRepeaterDSRList['gre_name2']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的GRE隧道key">
						${irmsWlanRepeaterDSRList['gre_tunnrl_key2']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="终端IP地址池">
						${irmsWlanRepeaterDSRList['apn_address_pool']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否使用客户侧radius">
						${irmsWlanRepeaterDSRList['is_use_radius']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="RADIUS服务器地址">
						${irmsWlanRepeaterDSRList['radius_ip_add']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否允许终端间互访">
						${irmsWlanRepeaterDSRList['is_terminal_visits']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路名称">
						${irmsWlanRepeaterDSRList['circuit_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="光路名称">
						${irmsWlanRepeaterDSRList['optical_circuit_name']}
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
