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

<logic:present name="irmsWlanHotDSRList" scope="request">
	<display:table id="irmsWlanHotDSRList"
					name="irmsWlanHotDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsWlanHotDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="热点名称">
						${irmsWlanRepeaterDSRList['label_cn']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="地市">
						${irmsWlanRepeaterDSRList['related_district']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="NAS_ID">
						${irmsWlanRepeaterDSRList['nas_id']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="热点类型或覆盖类型">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['hotpoint_type']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="热点地理位置经度">
						${irmsWlanRepeaterDSRList['longitude']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="热点地理位置纬度">
						${irmsWlanRepeaterDSRList['latitude']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="传输类型">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['trans_type']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备厂家">
						${irmsWlanRepeaterDSRList['related_vendor']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AP数量">
						${irmsWlanRepeaterDSRList['ap_count']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="热点详细地址">
						${irmsWlanRepeaterDSRList['address']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="WLAN网络覆盖区域">
						${irmsWlanRepeaterDSRList['wlan_coverage_area']}
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
