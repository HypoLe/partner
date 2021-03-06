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

<logic:present name="irmsWlanApDSRList" scope="request">
	<display:table id="irmsWlanApDSRList"
					name="irmsWlanApDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsWlanApDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="AP名称">
						${irmsWlanRepeaterDSRList['ap_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="地市">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_district']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属热点名称">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_hotpoint']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AP厂家">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['ap_vendor']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AP类型">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['ap_type']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AP MAC地址">
						${irmsWlanRepeaterDSRList['ap_mac_addr']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AP管理地址">
						${irmsWlanRepeaterDSRList['manage_ap_addr']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="空口制式">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['stress_pattern']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AP安装位置">
						${irmsWlanRepeaterDSRList['ap_location']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AP覆盖区域">
						${irmsWlanRepeaterDSRList['ap_coverage_area']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否2G/3G室分合路">
						${irmsWlanRepeaterDSRList['is_2g3g_distributo']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属热点交换机名称">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_hotping_sw']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属热点交换机端口">
						${irmsWlanRepeaterDSRList['hotpot_sw_port']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="软件版本">
						${irmsWlanRepeaterDSRList['soft_version']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属AC名称">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_back_ac_cu']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="工程状态">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['ne_working_state']}" beanId="ItawSystemDictTypeDao"/>
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
