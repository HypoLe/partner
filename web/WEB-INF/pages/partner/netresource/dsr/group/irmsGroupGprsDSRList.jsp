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

<logic:present name="irmsGroupGprsDSRList" scope="request">
	<display:table id="irmsGroupGprsDSRList"
					name="irmsGroupGprsDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsGroupGprsDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="客户名称">
						${irmsGroupGprsDSRList['customer_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产品实例标识">
						${irmsGroupGprsDSRList['related_pro_instance_id']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="APN名称">
						${irmsGroupGprsDSRList['apn_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="APN号码">
						${irmsGroupGprsDSRList['related_apn_id']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务带宽(速率)">
						${irmsGroupGprsDSRList['business_bw']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务开放范围">
						${irmsGroupGprsDSRList['business_open_limi']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务承载方式">
						<eoms:id2nameDB id="${irmsGroupGprsDSRList['business_hosting_way']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="关联产品实例标识">
						${irmsGroupGprsDSRList['relative_pro_instance_id']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="IP地址分配方式">
						${irmsGroupGprsDSRList['ip_allocation_mode']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="地址数量">
						${irmsGroupGprsDSRList['use_ip_num']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否双GGSN">
						${irmsGroupGprsDSRList['is_both_ggsn']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="传输接入方式">
						${irmsGroupGprsDSRList['tran_access_way']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="企业服务器IP地址">
						${irmsGroupGprsDSRList['enterprise_service']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的名称">
						${irmsGroupGprsDSRList['label_cn1']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的GRE本端地址">
						${irmsGroupGprsDSRList['gre_address_name1']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的GRE企业端地址">
						${irmsGroupGprsDSRList['gre_enterprise_add1']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的GRE名称">
						${irmsGroupGprsDSRList['gre_name1']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的GRE隧道key">
						${irmsGroupGprsDSRList['gre_tunnrl_key1']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的名称">
						${irmsGroupGprsDSRList['label_cn2']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的GRE本端地址">
						${irmsGroupGprsDSRList['gre_address_name2']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的GRE企业端地址">
						${irmsGroupGprsDSRList['gre_enterprise_add2']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的GRE名称">
						${irmsGroupGprsDSRList['gre_name2']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的GRE隧道key">
						${irmsGroupGprsDSRList['gre_tunnrl_key2']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="终端IP地址池">
						${irmsGroupGprsDSRList['apn_address_pool']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否使用客户侧radius">
						<eoms:id2nameDB id="${irmsGroupGprsDSRList['is_use_radius']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="RADIUS服务器地址">
						${irmsGroupGprsDSRList['radius_ip_add']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否允许终端间互访">
						<eoms:id2nameDB id="${irmsGroupGprsDSRList['is_terminal_visits']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路名称">
						${irmsGroupGprsDSRList['circuit_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="光路名称">
						${irmsGroupGprsDSRList['optical_circuit_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="接入客户日期">
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="备注">
						${irmsGroupGprsDSRList['remark']}
				</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
<%@ include file="/common/footer_eoms.jsp"%>
